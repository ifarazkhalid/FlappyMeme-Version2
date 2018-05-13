package com.farazkhalid.flappybird;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import org.w3c.dom.css.Rect;

//import android.media;


import java.util.Random;

import sun.security.provider.SHA;

public class FlappyBirdGame extends ApplicationAdapter {
	SpriteBatch batch;
	//Texture is just an image, while creating games, image are referred to as Texture.
	//Using texture, we'll display the object and the background.
	Texture background;
	//create an array so that both birds sprites can be stored.

//	ShapeRenderer shapeRenderer;

	Texture[] birds;

	//checks for which sprite we should use.
	int flapState = 0;

	//The Y-axis on which the bird stands.
	float birdY = 0;
	float velocity = 0;
	Circle birdCircle;
	int score = 0;
	int scoringTube = 0;
	BitmapFont font;
	int n = 0;


	int gameState = 0;
	float gravity = 2;
	Texture gameOver;
	Texture gameOver1;
	Texture gameOver2;
	Texture gameOver3;
	Texture gameOver4;
	Texture gameOver5;
	Texture gameOver6;
	Texture gameOver7;
	Texture gameOver8;
	Texture topTube;
	Texture bottomTube;
	float gap = 400;
	float maxTubeOffset;
	Random randomGenerator;
	Random randomNumber;
	float tubeVelocity = 4;
	int numberOfTubes = 4;
	float[] tubeX = new float[numberOfTubes];
	float[] tubeOffset = new float[numberOfTubes];
	float distanceBetweenTubes;


	Rectangle[] topTubeRectangles;
	Rectangle[] bottomTubeRectangles;



	@Override
	public void create() {
		batch = new SpriteBatch();
		background = new Texture("bg.png");
//		shapeRenderer = new ShapeRenderer();
		birdCircle = new Circle();
		gameOver = new Texture("realmeme1.jpg");
		gameOver1 = new Texture("realmeme2.jpg");
		gameOver2 = new Texture("realmeme2.png");
		gameOver3 = new Texture("realmeme3.png");
		gameOver4 = new Texture("realmeme4.jpg");
		gameOver5 = new Texture("realmeme5.jpg");
		gameOver6 = new Texture("realmeme6.jpg");
		gameOver7 = new Texture("realmeme7.jpg");
		gameOver7 = new Texture("realmeme8.jpg");


		font = new BitmapFont();
		font.setColor(Color.WHITE);
		font.getData().setScale(10);


		birds = new Texture[2];
		birds[0] = new Texture("bird.png");
		birds[1] = new Texture("bird2.png");


		topTube = new Texture("toptube.png");
		bottomTube = new Texture("bottomtube.png");
		maxTubeOffset = Gdx.graphics.getHeight() / 2 - gap / 2 - 100;
		randomGenerator = new Random();
		randomNumber = new Random();

		topTubeRectangles = new Rectangle[numberOfTubes];
		bottomTubeRectangles = new Rectangle[numberOfTubes];

		distanceBetweenTubes = Gdx.graphics.getWidth() * 3 / 4;

		startGame();


	}

	public void startGame() {
		birdY = Gdx.graphics.getHeight() / 2 - birds[0].getHeight() / 2;

		for (int i = 0; i < numberOfTubes; i++) {

			tubeOffset[i] = (randomGenerator.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - gap - 200);

			tubeX[i] = Gdx.graphics.getWidth() / 2 - topTube.getWidth() / 2 + Gdx.graphics.getWidth() + i * distanceBetweenTubes;

			topTubeRectangles[i] = new Rectangle();
			bottomTubeRectangles[i] = new Rectangle();

		}
	}

	@Override
	public void render() {
//		if (Gdx.input.justTouched()) {
//			Gdx.app.log("Touched", "Yep");
//
//			velocity = -20;
//		}

		batch.begin();
		batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

//		if (n == 0) {
//					int n = randomNumber.nextInt(3) + 1;
//		}

//		int n;
//		n=2;


		if (gameState == 1) {



			if (tubeX[scoringTube] < Gdx.graphics.getWidth() / 2) {
				score++;

				if (scoringTube < numberOfTubes - 1) {
					scoringTube++;
				} else {
					scoringTube = 0;
				}
			}

			if (Gdx.input.justTouched()) {
				velocity = -30;
			}

			for (int i = 0; i < numberOfTubes; i++) {
				if (tubeX[i] < -topTube.getWidth()) {
					tubeX[i] += numberOfTubes * distanceBetweenTubes;
					tubeOffset[i] = (randomGenerator.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - gap - 200);

				} else {
					tubeX[i] = tubeX[i] - tubeVelocity;
				}

				tubeX[i] = tubeX[i] - tubeVelocity;

				batch.draw(topTube, tubeX[i],
						Gdx.graphics.getHeight() / 2 + gap / 2 + tubeOffset[i]);

				batch.draw(bottomTube, tubeX[i],
						Gdx.graphics.getHeight() / 2 - gap / 2 - bottomTube.getHeight() + tubeOffset[i]);

				topTubeRectangles[i] = new Rectangle(tubeX[i], Gdx.graphics.getHeight() / 2 + gap / 2 + tubeOffset[i], topTube.getWidth(), topTube.getHeight());
				bottomTubeRectangles[i] = new Rectangle(tubeX[i], Gdx.graphics.getHeight() / 2 - gap / 2 - bottomTube.getHeight() + tubeOffset[i], bottomTube.getWidth(), bottomTube.getHeight());


			}


			if (birdY > 0) {
				velocity += gravity;
				birdY -= velocity;
			} else {
				gameState = 2;
			}


		} else if (gameState == 0) {
			if (Gdx.input.justTouched()) {
				gameState = 1;
			}
		} else if (gameState == 2) {


//			batch.draw(gameOver, Gdx.graphics.getWidth() / 2 - gameOver.getWidth() / 2, Gdx.graphics.getHeight() / 2 - gameOver.getHeight() / 2);

			if (n == 1) {
				batch.draw(gameOver, Gdx.graphics.getWidth() / 2 - gameOver.getWidth() / 2, Gdx.graphics.getHeight() / 2 - gameOver.getHeight() / 2);
			}
			if (n ==2) {
				batch.draw(gameOver1, Gdx.graphics.getWidth() / 2 - gameOver.getWidth() / 2, Gdx.graphics.getHeight() / 2 - gameOver.getHeight() / 2);
			}
			if (n ==3) {
				batch.draw(gameOver2, Gdx.graphics.getWidth() / 2 - gameOver.getWidth() / 2, Gdx.graphics.getHeight() / 2 - gameOver.getHeight() / 2);
			}
			if (n ==4) {
				batch.draw(gameOver3, Gdx.graphics.getWidth() / 2 - gameOver.getWidth() / 2, Gdx.graphics.getHeight() / 2 - gameOver.getHeight() / 2);
			}
			if (n ==5) {
				batch.draw(gameOver4, Gdx.graphics.getWidth() / 2 - gameOver.getWidth() / 2, Gdx.graphics.getHeight() / 2 - gameOver.getHeight() / 2);
			}

			if (n ==6) {
				batch.draw(gameOver5, Gdx.graphics.getWidth() / 2 - gameOver.getWidth() / 2, Gdx.graphics.getHeight() / 2 - gameOver.getHeight() / 2);
			}

			if (n ==7) {
				batch.draw(gameOver6, Gdx.graphics.getWidth() / 2 - gameOver.getWidth() / 2, Gdx.graphics.getHeight() / 2 - gameOver.getHeight() / 2);
			}

			if (n ==8) {
				batch.draw(gameOver7, Gdx.graphics.getWidth() / 2 - gameOver.getWidth() / 2, Gdx.graphics.getHeight() / 2 - gameOver.getHeight() / 2);
			}

			if (n ==9) {
				batch.draw(gameOver8, Gdx.graphics.getWidth() / 2 - gameOver.getWidth() / 2, Gdx.graphics.getHeight() / 2 - gameOver.getHeight() / 2);
			}


			if (Gdx.input.justTouched()) {
				n = randomNumber.nextInt(9) + 1;
				gameState = 1;
				startGame();
				score = 0;
				scoringTube = 0;
				velocity = 0;
			}

		}
			if (flapState == 0) {
				flapState = 1;
			} else {
				flapState = 0;
			}
			//velocity increases and then the height is substracted again.


			batch.draw(birds[flapState], Gdx.graphics.getWidth() / 2 - birds[flapState].getWidth() / 2,
					birdY);

			font.draw(batch, String.valueOf(score), 100, 200);

			batch.end();


			birdCircle.set(Gdx.graphics.getWidth() / 2, birdY + birds[flapState].getHeight() / 2, birds[flapState].getWidth() / 2);

//		shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
//		shapeRenderer.setColor(Color.BLACK);
//		shapeRenderer.circle(birdCircle.x, birdCircle.y, birdCircle.radius);

			for (int i = 0; i < numberOfTubes; i++) {
//			shapeRenderer.rect(tubeX[i], Gdx.graphics.getHeight() / 2 + gap / 2 + tubeOffset[i],topTube.getWidth(), topTube.getHeight());
//			shapeRenderer.rect(tubeX[i], Gdx.graphics.getHeight() / 2 - gap / 2 - bottomTube.getHeight() + tubeOffset[i], bottomTube.getWidth(), bottomTube.getHeight());

				if (Intersector.overlaps(birdCircle, topTubeRectangles[i]) || Intersector.overlaps(birdCircle, bottomTubeRectangles[i])) {
					gameState = 2;
				}
			}

//		shapeRenderer.end();
		}

//	@Override
//	public void dispose () {
//		batch.dispose();
//		img.dispose();
//	}
	}
