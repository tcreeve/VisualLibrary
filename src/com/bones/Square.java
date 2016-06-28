package com.bones;

public class Square {
	Sign sign;
	Bookshelf b;
	Wall w;
	Door d;
	User u;
	boolean keepEmptyFloor = false;
	
	public Square(){
		sign = null;
		b = null;
		w = null;
		d = null;
		u = null;
	}
	
	public Square(Sign hasSign, Bookshelf bookshelf){
		sign = hasSign;
		b = bookshelf;
		w = null;
		d = null;
		u = null;
	}
	
	public Square(Sign hasSign, User user){
		sign = hasSign;
		u = user;
		b = null;
		d = null;
		w = null;
	}
	
	public Square(Wall wall){
		w = wall;
		sign = null;
		u = null;
		b = null;
		d = null;
	}
	
	public Square(Door door){
		w = null;
		sign = null;
		u = null;
		b = null;
		d = door;
	}
	
	
	public void deleteUser(){
		u = null;
	}
	
	public User getUser(){
		return u;
	}
	
	public void deleteBookshelf(){
		b = null;
	}
	
	public Bookshelf getBookshelf(){
		return b;
	}
	
	public void deleteSign(){
		sign = null;
	}
	
	public Sign getSign(){
		return sign;
	}
	
	public void deleteWall(){
		w = null;
	}
	
	public Wall getWall(){
		return w;
	}
	
	public void deleteDoor(){
		d = null;
	}
	
	public Door getDoor(){
		return d;
	}
	
	public boolean floorIsEmpty(){
		if(b==null && w==null && d==null)
			return true;
		return false;
	}
	
	public boolean ceilingEmpty(){
		if(w==null && sign==null)
			return true;
		return false;
	}
	
	public void setKeepEmptyFloor(boolean flag){
		keepEmptyFloor = flag;
	}
	
	public boolean returnKeepEmptyFloor(){
		return keepEmptyFloor;
	}
	
	public boolean hasSign(){
		if(sign==null)
			return false;
		return true;
	}
	
	public boolean hasWall(){
		if(w==null)
			return false;
		return true;
	}
	
	public boolean hasDoor(){
		if(d==null)
			return false;
		return true;
	}
	
	public boolean hasBookshelf(){
		if(b==null)
			return false;
		return true;
	}
	
	public void setSign(Sign s){
		sign = s;
	}
	
	public void placeBookshelf(Bookshelf bookshelf){
		b = bookshelf;
		w = null;
		d = null;
	}
	
	public void placeWall(Wall wall){
		w = wall;
		b = null;
		d = null;
	}
	
	public void placeDoor(Door door){
		d = door;
		b = null;
		w = null;
	}
	
	public void idBookshelf(int id){
		b.setID(id);
	}
	
	//id wall
	//id door
	
	public boolean haveUser(){
		if(u != null)
			return true;
		return false;
	}
	
	public void setUser(User user){
		u = user;
	}
	
	public String toString(){
		String str = "";
		if(b != null){
			str+="b ";
		} else if(w != null){
			str+= "w ";
		} else if(d != null){
			str+= "d ";
		} else if(u != null){
			str+="u ";
		} else if(keepEmptyFloor){
			str+="e ";
		}else{
			str+="  ";
		}
		if(sign==null)
			str+=" ";
		else
			str+="s";
		return str;
	}

}
