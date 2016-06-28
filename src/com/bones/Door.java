package com.bones;

public class Door {
	int x, y;
	Door linkedTo;
	Room inside;
	
	public Door(){
		x = -1;
		y = -1;
	}
	
	public Door(int setx, int sety){
		x = setx;
		y = sety;
	}
	
	public void setDoorLinkedTo(Door d){
		linkedTo = d;
	}
	
	public Door getDoorLinkedTo(){
		return linkedTo;
	}
	
	public Room getRoomInsideOf(){
		return inside;
	}
	
	public void setRoomInsideOf(Room r){
		inside = r;
	}
}
