package com.bones;

public class placeItems {
	
	//adds a bookshelf, facing west, to the room
	public static boolean addBookshelfToRoomWest(Room r, Bookshelf b, int x, int y){
		b.setDirection('w');
		if(x+b.getBookshelfLength() > r.getLength()){
			return false;
		} else if(x<-1)
			return false;
		if(y+b.getBookshelfWidth() > r.getWidth())
			return false;
		else if(y<-1)
			return false;
		
		for(int i=0; i<(b.getBookshelfLength()+1); i++){
			for(int j=0; j<b.getBookshelfWidth(); j++){
				if(!r.returnSquareFromGrid(x-i, y+j).floorIsEmpty())
					return false;
				else if(r.returnSquareFromGrid(x-i,  y+j).returnKeepEmptyFloor())
					return false;
			}
		}
	
		for(int i=0; i<b.getBookshelfLength(); i++){
			for(int j=0; j<b.getBookshelfWidth(); j++){
				r.addBookshelfToSquare(b, x-i, y+j);
			}
		}
		
		for(int k=0; k<b.getBookshelfWidth(); k++){
			r.setKeepSquareEmpty(x-b.getBookshelfLength(), y+k, true);
		}
		b.setCoords(x, y);
		return true;
	}

	//adds a bookshelf, facing east, to the room
	public static boolean addBookshelfToRoomEast(Room r, Bookshelf b, int x, int y){
		b.setDirection('e');
		if(x-b.getBookshelfLength()+1 < 0){
			return false;
		} else if(x > r.getLength()){
			return false;
		}
		if(y-b.getBookshelfWidth() < 0){
			return false;
		} else if(y>r.getWidth()){
			return false;
		}
		
		for(int i=0; i<(b.getBookshelfLength()+1); i++){
			for(int j=0; j<(b.getBookshelfWidth()); j++){
//				System.out.println((x+i)+", "+(y-j)+" :"+r.returnSquareFromGrid(x+i,  y-j).toString());
				if(!r.returnSquareFromGrid(x+i, y-j).floorIsEmpty())
					return false;
				else if(r.returnSquareFromGrid(x+i, y-j).returnKeepEmptyFloor())
					return false;
			}
		}

		for(int i=0; i<b.getBookshelfLength(); i++){
			for(int j=0; j<b.getBookshelfWidth(); j++){
				r.addBookshelfToSquare(b, x+i, y-j);
			}
		}
		for(int k=0; k<b.getBookshelfWidth(); k++){
			r.setKeepSquareEmpty(x+b.getBookshelfLength(), y-k, true);
		}
		b.setCoords(x, y);
		return true;
	}
	
	//adds a bookshelf, facing south, to the room
	public static boolean addBookshelfToRoomSouth(Room r, Bookshelf b, int x, int y){
		b.setDirection('s');
		if(y+b.getBookshelfLength()>r.getLength()){
			return false;
		} else if(y<0){
			return false;
		}
		if(x+b.getBookshelfWidth()>r.getWidth()){
			return false;
		} else if(x<0){
			return false;
		}
		
		for(int i=0; i<b.getBookshelfLength()+1; i++){
			for(int j=0; j<(b.getBookshelfWidth()); j++){
				if(!r.returnSquareFromGrid(x+j, y+i).floorIsEmpty())
					return false;
				else if(r.returnSquareFromGrid(x+j, y+i).returnKeepEmptyFloor())
					return false;
			}
		}

		for(int i=0; i<b.getBookshelfLength(); i++){
			for(int j=0; j<b.getBookshelfWidth(); j++){
				r.addBookshelfToSquare(b, x+j, y+i);
			}
		}
		for(int k=0; k<b.getBookshelfWidth(); k++){
			r.setKeepSquareEmpty(x+k, y+b.getBookshelfLength(), true);
		}
		b.setCoords(x, y);
		return true;
	}
	
	//adds a bookshelf, facing north, to the room
	public static boolean addBookshelfToRoomNorth(Room r, Bookshelf b, int x, int y){
		b.setDirection('n');
		if(r.getLength() <= y){
			return false;
		} else if(y-b.getBookshelfLength()<0){
			return false;
		}
		if(r.getWidth() <= x){
			return false;
		} else if(x-b.getBookshelfWidth()<0){
			return false;
		}

		for(int i=0; i<b.getBookshelfLength()+1; i++){
			for(int j=0; j<(b.getBookshelfWidth()); j++){
				if(!r.returnSquareFromGrid(x-j, y-i).floorIsEmpty())
					return false;
				else if(r.returnSquareFromGrid(x-j,  y-i).returnKeepEmptyFloor())
					return false;
			}
		}
		
		for(int i=0; i<b.getBookshelfLength(); i++){
			for(int j=0; j<b.getBookshelfWidth(); j++){
				r.addBookshelfToSquare(b, x-j, y-i);
			}
		}
		for(int k=0; k<b.getBookshelfWidth(); k++){
			r.setKeepSquareEmpty(x-k, y-b.getBookshelfLength(), true);
		}
		b.setCoords(x, y);
		return true;
	}

	//adds a bookshelf according to the direction recorded in the bookshelf itself
	public static boolean addBookshelfByDirection(Room r, Bookshelf b, int x, int y){
		if(b.getDirection() == 'n')
			return addBookshelfToRoomNorth(r, b, x, y);
		else if(b.getDirection() == 's')
			return addBookshelfToRoomSouth(r, b, x, y);
		else if(b.getDirection() == 'e')
			return addBookshelfToRoomEast(r, b, x, y);
		return addBookshelfToRoomWest(r, b, x, y);
	}
	
	//deletes a bookshelf according the coordinates inside the room
	public static boolean deleteBookshelfByCoords(Room r, int x, int y){
		if(!r.returnSquareFromGrid(x, y).hasBookshelf()){
			return false;
		}
		Bookshelf b = r.returnSquareFromGrid(x, y).getBookshelf();
		
		for(int i=0; i<r.getWidth(); i++){
			for(int j=0; j<r.getLength(); j++){
				Bookshelf testB = r.returnSquareFromGrid(i, j).getBookshelf();
				if((testB != null) && (testB.equals(b))){
					r.returnSquareFromGrid(i, j).deleteBookshelf();
				}
			}
		}
		
		//if bookshelf is north, delete empty spot y-1
		//else if bookshelf is south, delete empty spot y+1
		//else if bookshelf is east, delete empty spot x+1
		//else if bookshelf is west, delete empty spot x-1		
		if(b.getDirection() == 'n'){
			int xTemp = b.getSetX();
			int yTemp = b.getSetY()-b.getBookshelfLength();
			
			for(int i=0; i<b.getBookshelfWidth(); i++){
				r.returnSquareFromGrid(xTemp-i, yTemp).setKeepEmptyFloor(false);
			}
		} else if(b.getDirection() == 's'){
			int xTemp = b.getSetX();
			int yTemp = b.getSetY()+b.getBookshelfLength();
			
			for(int i=0; i<b.getBookshelfWidth(); i++){
				r.returnSquareFromGrid(xTemp+i, yTemp).setKeepEmptyFloor(false);
			}
		} else if(b.getDirection() == 'e'){
			int xTemp = b.getSetX()+b.getBookshelfLength();
			int yTemp = b.getSetY();
			
			for(int i=0; i<b.getBookshelfWidth(); i++){
				r.returnSquareFromGrid(xTemp, yTemp-i).setKeepEmptyFloor(false);;
			}
		} else if(b.getDirection() == 'w'){
			int xTemp = b.getSetX()-b.getBookshelfLength();
			int yTemp = b.getSetY();
			
			for(int i=0; i<b.getBookshelfWidth(); i++){
//				System.out.println(xTemp+", "+(yTemp+i));
				r.returnSquareFromGrid(xTemp, yTemp+i).setKeepEmptyFloor(false);;
			}
		}
		
		return true;
	}
	
	public static boolean placeWallsAroundRoom(Room r){
		int xlength = r.getLength();
		int ylength = r.getWidth();
		
		for(int x=0; x<xlength; x++){
			for(int y=0; y<ylength; y++){
				if(x==0){
					if(!r.returnSquareFromGrid(x, y).floorIsEmpty())
						return false;
				} else if(y==0){
					if(!r.returnSquareFromGrid(x, y).floorIsEmpty())
						return false;
				} else if(x==xlength-1){
					if(!r.returnSquareFromGrid(x, y).floorIsEmpty())
						return false;
				} else if(y==ylength-1){
					if(!r.returnSquareFromGrid(x, y).floorIsEmpty())
						return false;
				}
			}
		}
		
		for(int x=0; x<xlength; x++){
			for(int y=0; y<ylength; y++){
				if(x==0){
					r.addWallToSquare(new Wall(), x, y);
				} else if(y==0){
					r.addWallToSquare(new Wall(), x, y);
				} else if(x==xlength-1){
					r.addWallToSquare(new Wall(), x, y);
				} else if(y==ylength-1){
					r.addWallToSquare(new Wall(), x, y);
				}
			}
		}
		return true;
	}
	
	public static boolean putBookInShelf(Book book, Bookshelf shelf){
		for(int i=0; i<shelf.getNumberOfShelvesInBookshelf(); i++){
			for(int j=0; j<shelf.getBookshelfLength(); j++){
				if(shelf.getBookFromSlot(i, j)==null){
					shelf.placeBookInSlot(i, j, book);
					return true;
				}
			}
		}
		return false;
	}
	
	public static boolean placeSignOnCeiling(Room r, Sign s, int x, int y){
		if(r.returnSquareFromGrid(x, y).ceilingEmpty()){
			r.addSignToSquare(s, x, y);
			return true;
		}
		return false;
	}
	
	public static boolean placeUserInRoom(Room r, User u){
		if(r.returnSquareFromGrid(u.getX(), u.getY()).floorIsEmpty()){
			r.addUserToSquare(u, u.getX(), u.getY());
			return true;
		} else {
			if(u.getX() == 19){
				if(u.getY() == 19){
					u.setX(1);
					u.setY(1);
				} else{
					u.setX(1);
					u.setY(u.getY()+1);
				}
			} else {
				u.setX(u.getX()+1);
			}
			return(placeUserInRoom(r, u));
		}
	}
	
	public static void main(String[] args){
		//create rooms
		Room r = new Room();
		placeWallsAroundRoom(r);
		
		//create a bookshelf for each direction
		Bookshelf shelfNorth = new Bookshelf();
		shelfNorth.setID(1);
		Bookshelf shelfSouth = new Bookshelf();
		shelfSouth.setID(2);
		Bookshelf shelfEast = new Bookshelf();
		shelfEast.setID(3);
		Bookshelf shelfWest = new Bookshelf();
		shelfWest.setID(4);
		
		//set bookshelves in room
		if(!addBookshelfToRoomNorth(r, shelfNorth, 10, 10)){
			System.out.println("Didn't add north shelf");
		}
		if(!addBookshelfToRoomSouth(r, shelfSouth, 10, 1)){
			System.out.println("Didn't add south shelf");
		}
		if(!addBookshelfToRoomEast(r, shelfEast, 1, 5)){
			System.out.println("Didn't add east shelf");
		}
		if(!addBookshelfToRoomWest(r, shelfWest, 18, 10)){
			System.out.println("Didn't add west shelf");
		}
		
		boolean succeed = deleteBookshelfByCoords(r, shelfWest.getSetX(), shelfWest.getSetY());
		
		//print room
		System.out.println(r.toString());
		
		System.out.println(succeed+"");
	}

}
