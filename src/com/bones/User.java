package com.bones;

public class User {
	private final int BEGINNING_X = 8;
	private final int BEGINNING_Y = 8;
	private int currentX;
	private int currentY;
	
	public User(){
		currentX = BEGINNING_X;
		currentY = BEGINNING_Y;
	}
	
	public User(int x, int y){
		currentX = x;
		currentY = y;
	}
	
	public int getX(){
		return currentX;
	}
	
	public void setX(int x){
		currentX = x;
	}
	
	public int getY(){
		return currentY;
	}
	
	public void setY(int y){
		currentY = y;
	}
}
