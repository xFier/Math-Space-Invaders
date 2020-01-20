package me.brandon.spaceinvaders.utils;

public class StaticData
{
	//Static variables used in various stages of the game, useful to store them all here. Can be static while maintaining the OOP structure due to the fact these don't change in runtime.
	public static int SCREEN_WIDTH = 750;
	public static int SCREEN_HEIGHT = 850;
	public static int ALIEN_WIDTH = 10;
	public static int ALIEN_HEIGHT = 4;
	public static int ALIEN_PIXEL_GAP = 15;
	public static int ALIEN_PIXEL_BUFFER = 25;
	public static int SPACESHIP_PIXEL_BUFFER = 10;
	public static int ALIEN_PIXEL_HORIZONTAL_MOVEMENT = 3;
	public static int ALIEN_PIXEL_VERTICAL_MOVEMENT = 5;
	public static int STARTING_ALIEN_TOP_LEFT_X = 10;
	public static int STARTING_ALIEN_TOP_LEFT_Y = 125;
	public static int SPACESHIP_MISSILE_PIXEL_MOVEMENT = 15;
	public static long SPACESHIP_MISSILE_COOLDOWN = 150;
	public static int SPACESHIP_MOVEMENT_PIXELS = 5;
	public static int ALIEN_MISSILE_PIXEL_MOVEMENT = 10;
	public static int PLAYER_LIVES = 3;
	public static int INITIAL_ALIEN_MISSILE_COOLDOWN = 2500;
	public static int ALIENS_BETWEEN_QUESTIONS = 35;
}