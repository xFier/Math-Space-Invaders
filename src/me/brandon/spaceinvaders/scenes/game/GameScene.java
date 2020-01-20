package me.brandon.spaceinvaders.scenes.game;

import java.util.ArrayList;
import java.util.Random;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.VPos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.util.Duration;
import me.brandon.spaceinvaders.Main;
import me.brandon.spaceinvaders.scenes.BaseScene;
import me.brandon.spaceinvaders.scenes.game.missile.AlienMissile;
import me.brandon.spaceinvaders.scenes.game.missile.Missile;
import me.brandon.spaceinvaders.scenes.game.missile.SpaceshipMissile;
import me.brandon.spaceinvaders.scenes.game.text.LivesText;
import me.brandon.spaceinvaders.scenes.game.text.ScoreText;
import me.brandon.spaceinvaders.scenes.game.text.WaveText;
import me.brandon.spaceinvaders.scenes.gameover.GameOverScene;
import me.brandon.spaceinvaders.scenes.mathematics.MathematicsScene;
import me.brandon.spaceinvaders.utils.PlainText;
import me.brandon.spaceinvaders.utils.StaticData;

public class GameScene extends BaseScene
{
	private Main application; // Main application.

	private ImageView spaceship; // The spaceship object.
	private Image spaceshipImage; // The spaceship image, useful incase the
									// object is null or can't be reached & we
									// need to calculate data based on the image
									// size.
	private String spaceShipHeldKey; // The key that is currently being held by
										// the user, limited to left or right,

	private KeyFrame spaceshipKeyFrame; // Target values for a specific point in
										// time relating to the Spaceship,
										// interpolated with a timeline.
	private KeyFrame alienKeyFrame; // Target values for a specific point in
									// time relating to the Aliens, interpolated
									// with a timeline.
	private KeyFrame missileKeyFrame; // Target values for a specific point in
										// time relating to the Missiles,
										// interpolated with a timeline.
	private Timeline timeline; // Overall timeline for animation
	private Duration duration; // Duration/Interval used for the spaceship &
								// missile animation speeds. Basically the time
								// between animation updates.
	private ImageView[][] aliens; // Two-dimensional arraylist containing active
									// aliens.
	private Image alienImage; // The alien image, useful to be stored here so we
								// can check the size of the alien image in
								// calculations even when the specific alien may
								// be "dead"/null.
	private boolean alienRightwardsMovement; // True if the aliens are moving
												// rightwards.
	private double alienTopLeftX; // The top left X value of the alien array.
	private double alienTopLeftY; // The top left Y value of the alien array.
	private int alienPixelVerticalMovement;//Pixels the aliens move vertically
	private int alienPixelHorizontalMovement;//Pixels the aliens move horizontally
	private int aliensKilled;//Number of aliens killed
	private int lastMathQuestionNumberOfAliens;//Last question asked at number of aliens killed

	private boolean paused; // Is the game paused or not?
	private boolean gameOver;//Game over?

	private ArrayList<Missile> missiles; // ArrayList of the missiles in the
											// game.
	private long lastMissileFired; // When the last missile was fired, used in
									// cooldowns to limit the fire-rate of the
									// spaceship.
	private long lastAlienMissileFired;//When was the last alien missile fired?
	private long alienMissileCooldown;//Time to wait between alien missile fires

	private int wave;
	private PlainText waveText;
	private int score;
	private PlainText scoreText;
	private int lives;
	private PlainText livesText;

	public GameScene(Main application)
	{
		super(application.getGameGroup());

		this.application = application;

		initializeVariables();

		setupGame();
	}
	
	public void initializeVariables()
	{
		this.spaceshipImage = new Image("me/brandon/spaceinvaders/resources/spaceinvaders-spaceship.png");
		this.spaceship = new ImageView(this.spaceshipImage);
		this.spaceship.setX(StaticData.SCREEN_WIDTH / 2 - (this.spaceship.getImage().getWidth() / 2));
		this.spaceship.setY(700);
		this.spaceShipHeldKey = "";

		this.alienImage = new Image("me/brandon/spaceinvaders/resources/spaceinvaders-alien.png");
		this.aliens = new ImageView[StaticData.ALIEN_WIDTH][StaticData.ALIEN_HEIGHT];
		this.alienRightwardsMovement = true;
		this.alienTopLeftX = StaticData.STARTING_ALIEN_TOP_LEFT_X;
		this.alienTopLeftY = StaticData.STARTING_ALIEN_TOP_LEFT_Y;
		this.alienPixelVerticalMovement = StaticData.ALIEN_PIXEL_VERTICAL_MOVEMENT;
		this.alienPixelHorizontalMovement = StaticData.ALIEN_PIXEL_HORIZONTAL_MOVEMENT;

		placeAliens();

		this.paused = false;
		this.gameOver = false;

		this.missiles = new ArrayList<Missile>();
		this.lastMissileFired = System.currentTimeMillis();
		this.lastAlienMissileFired = System.currentTimeMillis();
		this.alienMissileCooldown = StaticData.INITIAL_ALIEN_MISSILE_COOLDOWN;
		this.aliensKilled = 0;
		this.lastMathQuestionNumberOfAliens = 0;
		
		this.score = 0;
		this.scoreText = new ScoreText(this.rootGroup, this.score);
		this.scoreText.drawText(50, 0, VPos.TOP);
		
		this.wave = 1;
		this.waveText = new WaveText(this.rootGroup, this.wave);
		this.waveText.drawText(350, 0, VPos.TOP);
		
		this.lives = StaticData.PLAYER_LIVES;
		this.livesText = new LivesText(this.rootGroup, this.lives);
		this.livesText.drawText(600, 0, VPos.TOP);
		
		//25ms between updates
		this.duration = new Duration(25);
		this.alienKeyFrame = new KeyFrame(this.duration, x -> handleAlienMovement());
		this.missileKeyFrame = new KeyFrame(this.duration, x -> handleMissileMovement());
		this.spaceshipKeyFrame = new KeyFrame(this.duration, x -> handleSpaceshipMovement());
		this.timeline = new Timeline(this.alienKeyFrame, this.missileKeyFrame, this.spaceshipKeyFrame);
		this.timeline.setCycleCount(Animation.INDEFINITE);
	}

	public void setupGame()
	{
		//Key press & release events
		setOnKeyPressed(e -> handlePressedGameControls(e));
		setOnKeyReleased(e -> handleReleasedGameControls(e));
		
		//Adds spaceship & text to the root group, so it's displayed
		this.rootGroup.getChildren().add(this.spaceship);
		this.rootGroup.getChildren().add(this.waveText);
		this.rootGroup.getChildren().add(this.livesText);
		this.rootGroup.getChildren().add(this.scoreText);
		
		//Starts the animation & game loop
		this.timeline.play();
	}

	//Key presses
	public void handlePressedGameControls(KeyEvent e)
	{
		KeyCode keyPressed = e.getCode();

		switch (keyPressed)
		{
			case A:
			case LEFT://Move left
				if (this.spaceShipHeldKey.equals("LEFT"))//Already moving left, return
					return;

				if (!this.paused)//If the game is not paused, set the held key to LEFT
					this.spaceShipHeldKey = "LEFT";

				break;
			case D:
			case RIGHT://Move Right
				if (this.spaceShipHeldKey.equals("RIGHT"))//Already moving right, return
					return;

				if (!this.paused)//If not paused, set held key to RIGHT
					this.spaceShipHeldKey = "RIGHT";

				break;
			case SPACE:
			case W:
			case S:
			case UP:
			case DOWN://Fire missile
				if (System.currentTimeMillis() - this.lastMissileFired < StaticData.SPACESHIP_MISSILE_COOLDOWN)//If attempting to fire too quickly, return.
					break;

				if (this.paused)//Can't fire when paused
					break;

				this.lastMissileFired = System.currentTimeMillis();//Can now fire a missile, so the last one was fired now

				SpaceshipMissile missile = new SpaceshipMissile(this.rootGroup);//Create the missile

				this.missiles.add(missile);//Add the missile to the list of missile
				
				//Spawn the missile at the centre of the spaceship
				missile.spawn(this.spaceship.getX() + (this.spaceshipImage.getWidth() / 2) - (missile.getMissile().getWidth() / 2), this.spaceship.getY() - missile.getMissile().getHeight());

				break;
			case ESCAPE://Toggle pausing
				if (this.paused)
				{
					this.timeline.play();//Unpause
					this.application.getPrimaryStage().setTitle("Space Invaders - Mathematics Edition");
				}
				else
				{
					this.timeline.pause();//Pause
					this.application.getPrimaryStage().setTitle("Space Invaders - Mathematics Edition (Paused)");
				}
				this.paused = !this.paused;

				break;
			default:
				break;
		}
	}

	public void handleReleasedGameControls(KeyEvent e)
	{
		if (this.spaceShipHeldKey.equals(""))//If there is no held key, we don't need to release anything.
			return;

		KeyCode keyReleased = e.getCode();

		switch (keyReleased)
		{
			case A:
			case LEFT:
			case D:
			case RIGHT://If we release any spaceship movement key
				if (!this.paused)
					this.spaceShipHeldKey = "";//Set the held key to nothing
				break;
			default:
				break;
		}
	}
	
	//Handles movement of aliens
	public void handleAlienMovement()
	{
		
		if (this.alienRightwardsMovement)//If aliens are moving right
		{
			this.alienTopLeftX += this.alienPixelHorizontalMovement;//Move corner X right
		}
		else
		{
			this.alienTopLeftX -= this.alienPixelHorizontalMovement;//Move corner X left
		}

		for (int width = 0; width < StaticData.ALIEN_WIDTH; width++)
		{
			for (int height = 0; height < StaticData.ALIEN_HEIGHT; height++)//For all aliens
			{
				if (this.aliens[width][height] == null)//If alien is dead, skip
					continue;

				if (this.alienRightwardsMovement)//Moving right
					this.aliens[width][height].setX(this.aliens[width][height].getX() + this.alienPixelHorizontalMovement);
				else//Moving left
					this.aliens[width][height].setX(this.aliens[width][height].getX() - this.alienPixelHorizontalMovement);
			}
		}
		
		//If moving right and off screen
		if (this.alienRightwardsMovement && this.alienTopLeftX + ((StaticData.ALIEN_WIDTH - 1) * StaticData.ALIEN_PIXEL_GAP) + (StaticData.ALIEN_WIDTH * this.alienImage.getWidth()) >= StaticData.SCREEN_WIDTH - StaticData.ALIEN_PIXEL_BUFFER)
		{
			//Back up one movement, so we're on screen at this point
			this.alienTopLeftX -= this.alienPixelHorizontalMovement;

			for (int width = 0; width < StaticData.ALIEN_WIDTH; width++)
			{
				for (int height = 0; height < StaticData.ALIEN_HEIGHT; height++)//For all aliens
				{
					if (this.aliens[width][height] == null)//If the alien is dead, skip
						continue;
					
					//Move the alien down & back on screen
					this.aliens[width][height].setX(this.alienTopLeftX + (width * StaticData.ALIEN_PIXEL_GAP) + ((width) * this.alienImage.getWidth()));
					this.aliens[width][height].setY(this.aliens[width][height].getY() + this.alienPixelVerticalMovement);
					
					if (this.aliens[width][height].getY() >= 600)//Alien is too far down, end game.
					{
						initiateEndGame();
					}
				}
			}
			
			//Aliens are moving left now
			this.alienRightwardsMovement = !this.alienRightwardsMovement;
		}
		else if (!this.alienRightwardsMovement && this.alienTopLeftX <= StaticData.ALIEN_PIXEL_BUFFER)//If aliens are moving left and offscreen
		{
			this.alienTopLeftX += this.alienPixelHorizontalMovement;//back up, so we're back on screen

			for (int width = 0; width < StaticData.ALIEN_WIDTH; width++)
			{
				for (int height = 0; height < StaticData.ALIEN_HEIGHT; height++)//For all aliens
				{
					if (this.aliens[width][height] == null)//if alien is dead, skip
						continue;
					
					//Move alien back on screen and down
					this.aliens[width][height].setX(this.alienTopLeftX + (width * StaticData.ALIEN_PIXEL_GAP) + ((width) * this.alienImage.getWidth()));
					this.aliens[width][height].setY(this.aliens[width][height].getY() + this.alienPixelVerticalMovement);
				
					if (this.aliens[width][height].getY() >= 600)//Alien is too far down, end game.
					{
						initiateEndGame();
					}
				}
			}

			this.alienRightwardsMovement = !this.alienRightwardsMovement;//Aliens are moving right now
		}
	}
	
	//Handles movement of missiles
	public void handleMissileMovement()
	{
		handleAlienMissiles();//Alien missile spawning
		
		for (Missile missile : this.missiles)//For all missiles
		{
			if (missile instanceof SpaceshipMissile)//If missile is a spaceship missile
			{
				missile.getMissile().setY(missile.getMissile().getY() - StaticData.SPACESHIP_MISSILE_PIXEL_MOVEMENT);//Move up
			}
			else
			{
				missile.getMissile().setY(missile.getMissile().getY() + StaticData.ALIEN_MISSILE_PIXEL_MOVEMENT);//Move down
			}
		}

		handleAlienCollisions();//Missiles that collide with aliens
		handleSpaceshipCollisions();//Missiles that collide with the spaceship
	}
	
	//Spawn alien missiles
	public void handleAlienMissiles()
	{
		//If the game is not paused, and the aliens have waited long enough to fire again
		if (!this.paused && System.currentTimeMillis() - this.lastAlienMissileFired - new Random().nextInt(500) + 1 > this.alienMissileCooldown)
		{
			this.lastAlienMissileFired = System.currentTimeMillis();//Aliens can fire again, so they last fired now.
			
			AlienMissile missile = new AlienMissile(this.rootGroup);//New missile
			
			this.missiles.add(missile);//Adds the missile to the list
			
			ArrayList<ImageView> livingAliens = new ArrayList<ImageView>();//Living aliens
			
			for (int width = 0; width < StaticData.ALIEN_WIDTH; width++)
			{
				for (int height = 0; height < StaticData.ALIEN_HEIGHT; height++)//For all aliens
				{
					if (this.aliens[width][height] != null)
						livingAliens.add(this.aliens[width][height]);//If alien is not null, add it to living aliens
				}
			}
			
			ImageView targetAlien = livingAliens.get(new Random().nextInt(livingAliens.size()));//Target alien to spawn missile from is a random alien from the list of living aliens
			
			//Spawns missile at middile of alien
			missile.spawn(targetAlien.getX() + (this.alienImage.getWidth() / 2) - (missile.getMissile().getWidth() / 2), targetAlien.getY() + missile.getMissile().getHeight() + this.alienImage.getHeight());
		}
	}
	
	//Alien missile collisoons with the spaceship
	public void handleSpaceshipCollisions()
	{
		ArrayList<Missile> missilesToRemove = new ArrayList<Missile>();

		for (Missile missile : this.missiles)//For all missiles
		{
			if (!(missile instanceof AlienMissile))//If it's not an alien missile it doesn't matter
				continue;

			if (!missile.isActive())//Missile isn't active, we don't bother with it
				continue;

			if (missile.getMissile().getY() > StaticData.SCREEN_HEIGHT)//If missile is off screen, remove it and skip to next missile
			{
				missile.despawn();
				missilesToRemove.add(missile);
				this.rootGroup.getChildren().remove(missile);
				continue;
			}
			
			//If missile intersects the spaceship
			if (Math.abs(missile.getMissile().getX() - this.spaceship.getX() - (this.spaceshipImage.getWidth() / 2)) * 2 < (missile.getMissile().getWidth() + this.spaceshipImage.getWidth()) && Math.abs(missile.getMissile().getY() - this.spaceship.getY()) * 2 < (missile.getMissile().getHeight() + this.spaceshipImage.getHeight()))
			{
				setLives(this.lives - 1);//Decrease player lives
				missile.despawn();//Despawn missile
				missilesToRemove.add(missile);//Prepare to remove the missile from the list after iteration through it has finished
				this.rootGroup.getChildren().remove(missile);//Stops displaying the missile
			}
		}
	

		this.missiles.removeAll(missilesToRemove);//Actually deletes the missiles
		
		if (this.lives <= 0)//If lives is 0 (or less, for redundancy)
		{
			initiateEndGame();//End game
		}
	}
	
	//End the game
	public void initiateEndGame()
	{
		if (!gameOver)//If the game has not already ended
		{
			this.timeline.stop();//Stop the game loop & animations
			this.gameOver = true;//Game has ended = true
			this.application.resetGroups(this.rootGroup);//Resets other groups
			//Moves to the game over scene
			this.application.getPrimaryStage().setScene(new GameOverScene(this.application, this.wave, this.score, System.currentTimeMillis()));
		}
	}

	//Handles spaceship missile collisions with aliens
	public void handleAlienCollisions()
	{
		ArrayList<Missile> missilesToRemove = new ArrayList<Missile>();

		for (int width = 0; width < StaticData.ALIEN_WIDTH; width++)
		{
			for (int height = 0; height < StaticData.ALIEN_HEIGHT; height++)//For all aliens
			{
				for (Missile missile : this.missiles)//For all missiles
				{
					if (!(missile instanceof SpaceshipMissile))//If it's not a spaceship missile we skip
						continue;

					if (!missile.isActive())//Not active, so we skip
						continue;

					if (missile.getMissile().getY() < 0)//Missile off screen, so we despawn & skip
					{
						missile.despawn();
						missilesToRemove.add(missile);
						this.rootGroup.getChildren().remove(missile);
						continue;
					}

					if (this.aliens[width][height] == null)//Alien is null, so we skip
						continue;
					
					//If missile intersects with alien
					if (Math.abs(missile.getMissile().getX() - this.aliens[width][height].getX()) * 2 < (missile.getMissile().getWidth() + this.alienImage.getWidth()) && Math.abs(missile.getMissile().getY() - this.aliens[width][height].getY()) * 2 < (missile.getMissile().getHeight() + this.alienImage.getHeight()))
					{
						this.rootGroup.getChildren().remove(this.aliens[width][height]);//Remove the alien from the group
						this.aliens[width][height] = null;//Delete the alien
						setScore(this.score + 10);//Increase score
						this.aliensKilled += 1;//Increase aliens killed
						missile.despawn();//Remove the missile
						missilesToRemove.add(missile);
						this.rootGroup.getChildren().remove(missile);
					}
				}
			}
		}

		this.missiles.removeAll(missilesToRemove);//Actually remove all the missiles
		
		//Every X aliens killed, and we're not asking a question twice for the same kill count
		if (this.aliensKilled % StaticData.ALIENS_BETWEEN_QUESTIONS == 0 && this.aliensKilled != this.lastMathQuestionNumberOfAliens)
		{
			openMathsEquation();
		}
		
		//If all aliens are dead, advance to next wave
		if (!aliensStillAlive())
		{
			advanceWave();
		}
		
	}
	
	public void openMathsEquation()
	{
		this.lastMathQuestionNumberOfAliens = this.aliensKilled;
		
		this.timeline.pause();//Pauses the game while we answer the maths question
		
		this.application.resetGroups(this.rootGroup);
		this.application.getPrimaryStage().setScene(new MathematicsScene(this.application, this.wave, this));//Opens the maths scene
	}
	
	//Moves to next wave
	public void advanceWave()
	{
		
		for (Missile missile : this.missiles)//Removes all missiles
		{
			missile.despawn();
			this.rootGroup.getChildren().remove(missile);
		}
		
		this.missiles.clear();//Deletes all missiles
		
		setScore(this.score + 100);//Increase score
		setWave(this.wave + 1);//Increase wave
		
		if (this.wave % 2 == 0)//Increase lives every other wave
			setLives(this.lives + 1);
		
		//Reset alien positions
		this.alienTopLeftX = StaticData.STARTING_ALIEN_TOP_LEFT_X;
		this.alienTopLeftY = StaticData.STARTING_ALIEN_TOP_LEFT_Y;
		
		if (this.wave % 2 == 0)	//Increase vertical movement
			this.alienPixelVerticalMovement += 1;
		
		if (this.alienPixelVerticalMovement > 50)//Set limit on max vertical movement
			this.alienPixelVerticalMovement = 50;
		
		this.alienPixelHorizontalMovement++;//Increase horizontal movement
		
		if (this.alienPixelHorizontalMovement > 6)//Enforce limit on max horizontal movement
			this.alienPixelHorizontalMovement = 6;
		
		this.alienMissileCooldown -= 25;//Allow missiles to fire faster
		
		if (this.alienMissileCooldown < 500)//Fastest they can be limit enforcement
			this.alienMissileCooldown = 500;
		
		placeAliens();//Place new wave of aliens
	}
	
	//Returns true if any aliens are still alive
	public boolean aliensStillAlive()
	{
		for (int width = 0; width < StaticData.ALIEN_WIDTH; width++)
		{
			for (int height = 0; height < StaticData.ALIEN_HEIGHT; height++)//For all aliens
			{
				if (this.aliens[width][height] != null)//If at least one alien is not null, there's some alive, so return true
					return true;
			}
		}
		return false;//else return false
	}
	
	//Handle Spaceship movement with game loop
	public void handleSpaceshipMovement()
	{
		if (this.spaceShipHeldKey.equals("LEFT"))//Player holding down left? Move spaceship left
		{
			this.spaceship.setX(this.spaceship.getX() - StaticData.SPACESHIP_MOVEMENT_PIXELS);
		}
		else if (this.spaceShipHeldKey.equals("RIGHT"))//Player holding down right? Move spaceship right
		{
			this.spaceship.setX(this.spaceship.getX() + StaticData.SPACESHIP_MOVEMENT_PIXELS);
		}

		if (this.spaceship.getX() <= StaticData.SPACESHIP_PIXEL_BUFFER)//If spaceship off screen, place it back on screen
		{
			this.spaceship.setX(StaticData.SPACESHIP_PIXEL_BUFFER);
		}
		else if (this.spaceship.getX() + this.spaceship.getImage().getWidth() >= StaticData.SCREEN_WIDTH - StaticData.SPACESHIP_PIXEL_BUFFER)//If spaceship off screen, place it back on screen
		{
			this.spaceship.setX(StaticData.SCREEN_WIDTH - StaticData.SPACESHIP_PIXEL_BUFFER - this.spaceship.getImage().getWidth());
		}
	}
	
	//Place aliens
	public void placeAliens()
	{		
		double x = this.alienTopLeftX;//x coord

		for (int width = 0; width < StaticData.ALIEN_WIDTH; width++)
		{
			double y = this.alienTopLeftY;//y coord

			for (int height = 0; height < StaticData.ALIEN_HEIGHT; height++)//For all aliens
			{
				this.aliens[width][height] = new ImageView(this.alienImage);//New alien
				this.aliens[width][height].setX(x);//Set x
				this.aliens[width][height].setY(y);//Set y
				this.rootGroup.getChildren().add(this.aliens[width][height]);//Add the alien to the group
				y += StaticData.ALIEN_PIXEL_GAP + this.alienImage.getHeight();//Increment y;
			}

			x += StaticData.ALIEN_PIXEL_GAP + this.alienImage.getWidth();//Increment x;
		}
	}
	
	//Getters and setters
	public void setScore(int score)
	{
		this.score = score;
		this.scoreText.setText("Score: " + this.score);
	}
	
	public void setLives(int lives)
	{
		this.lives = lives;
		this.livesText.setText("Lives Left: " + this.lives);
	}
	
	public void setWave(int wave)
	{
		this.wave = wave;
		this.waveText.setText("Wave: " + this.wave);
	}
	
	public Timeline getTimeline()
	{
		return this.timeline;
	}

	public double getAlienTopLeftY()
	{
		return alienTopLeftY;
	}

	public void setAlienTopLeftY(double alienTopLeftY)
	{
		this.alienTopLeftY = alienTopLeftY;
	}

	public ImageView[][] getAliens()
	{
		return aliens;
	}

	public void setAliens(ImageView[][] aliens)
	{
		this.aliens = aliens;
	}

	public int getScore()
	{
		return score;
	}
	
	
	
}
