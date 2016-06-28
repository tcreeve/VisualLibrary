package com.bones;
import java.util.List;
import java.util.ArrayList;

public class Bookshelf {
	private final int LENGTH = 2;
	private final int WIDTH = 5;
	private final int NUM_SHELVES = 4;
	private Book [][] slots = new Book[NUM_SHELVES][WIDTH];
	private char direction;
	private int id;
	private int look_x = 0;
	private int look_y = 0;
	private int setX = -1;
	private int setY = -1;
	
	public Bookshelf(){
		for(int i=0; i<NUM_SHELVES; i++){
			for(int j=0; j<WIDTH; j++){
				slots[i][j] = null;
			}
		}
		direction = 'w';
		id = 0;
	}
	
	public Bookshelf(char frontDirection, int num){
		id = num;
		for(int i=0; i<NUM_SHELVES; i++){
			for(int j=0; j<WIDTH; j++){
				slots[i][j] = null;
			}
		}
		direction = frontDirection;
	}

/*	private Book [][] slots = new Book[NUM_SHELVES][WIDTH];
*/
	public int getSetX(){
		return setX;
	}
	
	public int getSetY(){
		return setY;
	}
	
	public void setCoords(int x, int y){
		setX = x;
		setY = y;
	}
	public int getBookshelfLength(){
		return LENGTH;
	}
	
	public int getBookshelfWidth(){
		return WIDTH;
	}
	
	public int getNumberOfShelvesInBookshelf(){
		return NUM_SHELVES;
	}
	
	public Book getBookFromSlot(int shelfNum, int slotNum){
		return slots[shelfNum][slotNum];
	}
	
	public boolean placeBookInSlot(int shelfNum, int slotNum, Book b){
		if(slots[shelfNum][slotNum] == null){
			slots[shelfNum][slotNum] = b;
			return true;
		}
		return false;
	}
	
	public String seeBooksInShelf(int shelfNum){
		String str = "";
		for(int i=0; i<WIDTH; i++){
			if(slots[shelfNum][i]!=null){
				str+=slots[shelfNum][i].toString()+"\n";
			}
		}
		str+="\n";
		return str;
	}
	
	public String seeEntireBookshelf(){
		String str="";
		for(int i=0; i<NUM_SHELVES; i++){
			str+="Shelf "+i+"\n";
			str+=seeBooksInShelf(i);
		}
		return str;
	}
	
	public int getLookX(){
		return look_x;
	}
	
	public int getLookY(){
		return look_y;
	}
	
	public void setLookX(int x){
		look_x = x;
	}
	
	public void setLookY(int y){
		look_y = y;
	}
	
	public void addLookCoords(){
		if(look_x < WIDTH-1){
			look_x++;
		} else if(look_y < NUM_SHELVES-1){
			look_y++;
			look_x = 0;
		}
	}
	
	public void subLookCoords(){
		if(look_x > 0){
			look_x--;
		} else if(look_y > 0){
			look_y--;
			look_x = WIDTH-1;
		}
	}
	
	public void upShelfLook(){
		if(look_y > 0)
			look_y--;
	}
	
	public void downShelfLook(){
		if(look_y < NUM_SHELVES-1)
			look_y++;
	}

	public char getDirection(){
		return direction;
	}
	
	public void setDirection(char d){
		direction = d;
	}
	
	public int getID(){
		return id;
	}
	
	public void setID(int i){
		id = i;
	}
	
	public List<Book> getAllBooks(){
		List<Book> booksInBookshelf = new ArrayList<Book>();
		for(int i=0; i<NUM_SHELVES; i++){
			for(int j=0; j<WIDTH; j++){
				if(slots[i][j] != null){
					booksInBookshelf.add(slots[i][j]);
				}
			}
		}
		return booksInBookshelf;
	}
	
	public boolean isFull(){
		for(int shelf=0; shelf<NUM_SHELVES; shelf++){
			for(int slot=0; slot<WIDTH; slot++){
				if(slots[shelf][slot] == null)
					return false;
			}
		}
		return true;
	}
	
	public boolean addBook(Book book){
		if(isFull())
			return false;
		for(int shelf=0; shelf<NUM_SHELVES; shelf++){
			for(int slot=0; slot<WIDTH; slot++){
				if(slots[shelf][slot] == null){
					slots[shelf][slot] = book;
					book.setShelfNum(shelf);
					book.setSlotNum(slot);
					return true;
				}
			}
		}
		return false;
	}
	
	public boolean addBookToSlot(Book b, int shelf, int slot){
		if(slots[shelf][slot] != null)
			return false;
		slots[shelf][slot] = b;
		return true;
	}

/*
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Book b1 = new Book("Inkheart", "Cornelia Funke", "0-439-63164-0", "");
//		Bookshelf(List<Book> bookList, char frontDirection, int xCoord, int yCoord, int room, int num){
		List<Book> emptyList = new ArrayList<Book>();
		Bookshelf bs = new Bookshelf('n', 0);
		bs.placeBookInSlot(0, 4, b1);
		Book b2 = new Book("Seeing I", "Johnathan Blum & Kate Orman", "0-563-40586-4", "");
		bs.placeBookInSlot(0, 0, b2);
		System.out.println(bs.seeEntireBookshelf());
	}
*/
}
