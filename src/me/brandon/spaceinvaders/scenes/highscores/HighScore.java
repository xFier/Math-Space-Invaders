package me.brandon.spaceinvaders.scenes.highscores;

/**
 * High Score Object
 * 
 * @author Brandon Gous
 *
 */
public class HighScore
{
	//Data relating to a high score
	private String name;
	private int wave;
	private int score;
	private long time;
	
	//Constructor
	public HighScore(String name, int wave, int score, long time)
	{
		this.name = name;
		this.wave = wave;
		this.score = score;
		this.time = time;
	}
	
	//Getters & Setters
	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public int getWave()
	{
		return wave;
	}

	public void setWave(int wave)
	{
		this.wave = wave;
	}

	public int getScore()
	{
		return score;
	}

	public void setScore(int score)
	{
		this.score = score;
	}

	public long getTime()
	{
		return time;
	}

	public void setTime(long time)
	{
		this.time = time;
	}

}
