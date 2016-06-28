package com.bones;
import java.util.ArrayList;
import java.util.List;


public class Room {
	private final int ROOM_WIDTH = 20;
	private final int ROOM_LENGTH = 20;
	private Square [][] grid = new Square[ROOM_WIDTH][ROOM_LENGTH];
	private int roomNum;
	
	public Room(){
		for(int i=0; i<ROOM_WIDTH; i++){
			for(int j=0; j<ROOM_LENGTH; j++){
				grid[i][j] = new Square();
			}
		}
		roomNum = 1;
	}
	
	public Room(Square[][] roomArray, int room){
		grid = roomArray;
		roomNum = room;
	}
	
	public Square returnSquareFromGrid(int x, int y){
		return grid[x][y];
	}
	
	public int getWidth(){
		return ROOM_WIDTH;
	}
	
	public int getLength(){
		return ROOM_LENGTH;
	}
	
	public Square[][] getGrid(){
		return grid;
	}
	
	public void setGrid(Square[][] g){
		grid = g;
	}
	
	public void addBookshelfToSquare(Bookshelf b, int x, int y){
		grid[x][y].placeBookshelf(b);
	}
	
	public void addDoorToSquare(Door d, int x, int y){
		grid[x][y].placeDoor(d);
	}
	
	public void addWallToSquare(Wall w, int x, int y){
		grid[x][y].placeWall(w);
	}
	
	public void addSignToSquare(Sign s, int x, int y){
		grid[x][y].setSign(s);
	}
	
	public void addUserToSquare(User user, int x, int y){
		for(int xr=0; xr<ROOM_WIDTH; xr++){
			for(int yr=0; yr<ROOM_LENGTH; yr++){
				grid[xr][yr].setUser(null);
			}
		}
		grid[x][y].setUser(user);
		user.setX(x);
		user.setY(y);
	}
	
	public int[] getUserSquareCoords(){
		for(int x=0; x<ROOM_WIDTH; x++){
			for(int y=0; y<ROOM_LENGTH; y++){
				if(grid[x][y].haveUser()){
					int coords[] = {x, y};
					return coords;
				}
			}
		}
		return null;
	}

	public Bookshelf checkAroundUserForBookshelf(){
		int coords[] = getUserSquareCoords();
		if(grid[coords[0]][coords[1]-1].hasBookshelf()){
			//bookshelf to north of user (y-1)
			Bookshelf b = grid[coords[0]][coords[1]-1].getBookshelf();
			if(b.getDirection() == 's'){
				return b;
			}
		} else if(grid[coords[0]][coords[1]+1].hasBookshelf()){
			//bookshelf to south of user (y+1)
			Bookshelf b = grid[coords[0]][coords[1]+1].getBookshelf();
			if(b.getDirection() == 'n'){
				return b;
			}
		} else if(grid[coords[0]-1][coords[1]].hasBookshelf()){
			//bookshelf to west of user
			Bookshelf b = grid[coords[0]-1][coords[1]].getBookshelf();
			if(b.getDirection() == 'e'){
				return b;
			}
		} else if(grid[coords[0]+1][coords[1]].hasBookshelf()){
			//bookshelf to west of user
			Bookshelf b = grid[coords[0]+1][coords[1]].getBookshelf();
			if(b.getDirection() == 'w'){
				return b;
			}
		}
		return null;
	}
	
	public void moveUser(char direction){
		int coords[] = getUserSquareCoords();
		if(direction == 'n'){
			if(grid[coords[0]][coords[1]-1].floorIsEmpty()){
				addUserToSquare(grid[coords[0]][coords[1]].getUser(), coords[0], coords[1]-1);
			}
		} else if(direction == 's'){
			if(grid[coords[0]][coords[1]+1].floorIsEmpty()){
				addUserToSquare(grid[coords[0]][coords[1]].getUser(), coords[0], coords[1]+1);
			}
			
		} else if(direction == 'w'){
			if(grid[coords[0]-1][coords[1]].floorIsEmpty()){
				addUserToSquare(grid[coords[0]][coords[1]].getUser(), coords[0]-1, coords[1]);
			}
		} else if(direction == 'e'){
			if(grid[coords[0]+1][coords[1]].floorIsEmpty()){
				addUserToSquare(grid[coords[0]][coords[1]].getUser(), coords[0]+1, coords[1]);
			}
		}
	}
	
	public int getRoomNum(){
		return roomNum;
	}
	
	public void setRoomNum(int n){
		roomNum = n;
	}
	
	public boolean containsBookshelf(){
		for(int x=0; x<ROOM_WIDTH;x++){
			for(int y=0; y<ROOM_LENGTH; y++){
				if(grid[x][y].hasBookshelf())
					return true;
			}
		}
		return false;
	}
	
	public Bookshelf findBookshelfByID(int id){
		for(int x=0; x<ROOM_WIDTH; x++){
			for(int y=0; y<ROOM_LENGTH; y++){
				if(grid[x][y].hasBookshelf()){
					if(grid[x][y].getBookshelf().getID()==id)
						return grid[x][y].getBookshelf();
				}
			}
		}
		return null;
	}
	
	public List<Bookshelf> getAllBookshelves(){
		List<Bookshelf> allShelves = new ArrayList<Bookshelf>();
		for(int x=0; x<ROOM_WIDTH; x++){
			for(int y=0; y<ROOM_LENGTH; y++){
				if(grid[x][y].hasBookshelf()){
					Bookshelf addShelf = grid[x][y].getBookshelf();
					boolean add = true;
					for(Bookshelf shelf : allShelves){
						if(shelf == addShelf)
							add = false;
					}
					if(add)
						allShelves.add(addShelf);
				}
			}
		}
		return allShelves;
	}
	
	public List<Sign> getAllSigns(){
		List<Sign> allSigns = new ArrayList<Sign>();
		for(int x=0; x<ROOM_WIDTH; x++){
			for(int y=0; y<ROOM_LENGTH; y++){
				if(grid[x][y].hasSign()){
					allSigns.add(grid[x][y].getSign());
				}
			}
		}
		return allSigns;
	}
	
	public List<Wall> getAllWalls(){
		List<Wall> allWalls = new ArrayList<Wall>();
		for(int x=0; x<ROOM_WIDTH; x++){
			for(int y=0; y<ROOM_LENGTH; y++){
				if(grid[x][y].hasWall()){
					allWalls.add(grid[x][y].getWall());
				}
			}
		}
		return allWalls;
	}
	
	public List<Door> getAllDoors(){
		List<Door> allDoors = new ArrayList<Door>();
		for(int x=0; x<ROOM_WIDTH; x++){
			for(int y=0; y<ROOM_LENGTH; y++){
				if(grid[x][y].hasDoor()){
					allDoors.add(grid[x][y].getDoor());
				}
			}
		}
		return allDoors;
	}
	
	public boolean keepSquareEmpty(int x, int y){
		return grid[x][y].returnKeepEmptyFloor();
	}
	
	public void setKeepSquareEmpty(int x, int y, boolean empty){
		grid[x][y].setKeepEmptyFloor(empty);
	}
	
	public String toString(){
		String str = "";
		for(int i=0; i<ROOM_LENGTH; i++){
			str+="|";
			for(int j=0; j<ROOM_WIDTH; j++){
				str+=grid[j][i].toString()+"|";
			}
			str+="\n";
		}
		return str;
	}

}
