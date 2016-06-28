package com.bones;
import java.applet.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.List;
import java.util.ArrayList;

import javax.swing.JFileChooser;

public class createGUI extends Applet implements KeyListener, MouseListener{
	
	private static final long serialVersionUID = 1L; //not entirely sure what this is 
    //    for, but the warning was bothering me
	
	
	int width, height;
	private static int pixelSize = 10;
	private String view = "room";
	int x = 1;
	int y = 1;
	char rotation = 's';
	int shelf_id=1;
	int rotate_id = 1;
	
	private List<Room> allRooms = new ArrayList<Room>();
	private Room r;
	private Bookshelf b;
	private Book book;
	File bookFile;
//	private List<Button> buttonList = new ArrayList<Button>();
		
	public Room getRoom(){
		return r;
	}
	
	public void init(){
		setSize(300, 300);
		width = getSize().width;
		height = getSize().height;
		setBackground(Color.black);
		
		allRooms = librarySaveOrLoad.loadLibrary();
		if(allRooms == null){
			allRooms = new ArrayList<Room>();
			r = new Room();
			b = new Bookshelf();
			book = null;
			User user = new User();
			placeItems.placeUserInRoom(r, user);
			placeItems.placeWallsAroundRoom(r);
			b = null;
			allRooms.add(r);
		} else{			
			r = allRooms.get(0);
			List<Bookshelf> tempShelfList = r.getAllBookshelves();
			int m = 1;
			boolean shelfExist = false;
			do{
				shelfExist = false;
				for(Bookshelf shelf : tempShelfList){
					if(shelf.getID() == m){
						shelfExist = true;
						m++;
					}
				}
			} while(shelfExist);
			shelf_id = m;
			placeItems.placeUserInRoom(r, new User());
		}
		addKeyListener(this);
		addMouseListener(this);
	}
	
	public void roomView(Graphics g){
		for(int x=0; x<r.getWidth(); x++){
			for(int y=0; y<r.getLength(); y++){
				int drawX = x*pixelSize;
				int drawY = y*pixelSize;
				
				g.setColor(Color.white);
				g.drawRect(drawX, drawY, pixelSize, pixelSize);
				Square s = r.returnSquareFromGrid(x, y);
				if(s.haveUser()){
					g.setColor(Color.cyan);
					g.fillRect(drawX, drawY, pixelSize, pixelSize);
				} else if(s.hasWall()){
					g.setColor(Color.lightGray);
					g.fillRect(drawX, drawY, pixelSize, pixelSize);
				} else if(s.hasBookshelf()){
					g.setColor(Color.yellow);
					g.fillRect(drawX, drawY, pixelSize, pixelSize);
				} else if(s.returnKeepEmptyFloor()){
					g.setColor(Color.orange);
					g.fillRect(drawX, drawY, pixelSize, pixelSize);
				} else if(s.hasSign()){
					g.setColor(Color.magenta);
					g.fillRect(drawX, drawY, pixelSize, pixelSize);
				}
			}
		}
		int startx = 3;
		int starty = pixelSize * r.getLength() + 10;
		int width = 125;
		int height = 25;
		String message = "Place new bookshelf";
			
		
		Font f = new Font("sansarif", Font.BOLD, 12);
		g.setColor(Color.orange);
		g.fillRect(startx, starty, width, height);	//button corners: 
		g.setColor(Color.black);				//(pixelSize*r.getWidth()+10, pixelSize*r.getLength+10)
		g.drawString(message, startx+3, starty+15);	//(startx+125, starty)
												//startx+125, starty+25
		startx +=140;
		message = "Upload Book";
		f = new Font("sansarif", Font.BOLD, 12);
		if(r.containsBookshelf())
			g.setColor(Color.orange);
		else
			g.setColor(Color.lightGray);
		g.fillRect(startx, starty, width, height);
		g.setColor(Color.black);
		g.drawString(message, startx+3, starty+15);
	}											
	public void bookshelfView(Graphics g){
		for(int shelf=0; shelf<b.getNumberOfShelvesInBookshelf(); shelf++){
			for(int slot=0; slot<b.getBookshelfWidth(); slot++){
				g.setColor(Color.white);
				g.drawRect(slot*10, shelf*10, 10, 10);
				
				Book book = b.getBookFromSlot(shelf, slot);
				if(book != null){
					g.fillRect(slot*10, shelf*10, 10, 10);
				} else if((b.getLookX() == slot) && (b.getLookY() == shelf)){
					g.setColor(Color.cyan);
					g.fillRect(slot*10, shelf*10, 10, 10);
				}
			}
		}
	}
	public void bookView(Graphics g){
		g.setColor(Color.white);
		Font f = new Font("sansarif", Font.BOLD, 12);
		g.setFont(f);
		//g.setFont(new Font());
		String title = "Title: "+book.getTitle();
		g.drawString(title, 12, 12);
		String author = "Author: "+book.getAuthor();
		g.drawString(author, 12, 25);
	}
	public void placeShelfView(Graphics g){
		b = new Bookshelf();
		b.setID(shelf_id);
		b.setDirection(rotation);
		boolean placed = false;
		while(!placed){
			placed = placeItems.addBookshelfByDirection(r, b, x, y);
			if(!placed){
				if(x < r.getWidth()-1){
					x++;
				} else if(y < r.getLength()-1){
					x=0;
					y++;
				} else{
					x = 1;
					y = 1;
				}
			} else{
				System.out.println(x+", "+y);
			}
		}
		for(int x=0; x<r.getWidth(); x++){
			for(int y=0; y<r.getLength(); y++){
				int drawX = x*pixelSize;
				int drawY = y*pixelSize;
				
				g.setColor(Color.white);
				g.drawRect(drawX, drawY, pixelSize, pixelSize);
				
				Square s = r.returnSquareFromGrid(x, y);
				if(s.haveUser()){
					g.setColor(Color.cyan);
					g.fillRect(drawX, drawY, pixelSize, pixelSize);
				} else if(s.hasWall()){
					g.setColor(Color.lightGray);
					g.fillRect(drawX, drawY, pixelSize, pixelSize);
				} else if(s.hasBookshelf()){
					if(s.getBookshelf()==b){
						g.setColor(Color.pink);
					} else{
						g.setColor(Color.yellow);
					}
					g.fillRect(drawX, drawY, pixelSize, pixelSize);
				} else if(s.returnKeepEmptyFloor()){
					g.setColor(Color.orange);
				} else if(s.hasSign()){
					g.setColor(Color.magenta);
					g.fillRect(drawX, drawY, pixelSize, pixelSize);
				}
			}
		}
		Font f = new Font("sansarif", Font.BOLD, 12);
		g.setColor(Color.white);
		g.drawString(rotation+"", r.getWidth()*pixelSize+5, 5);
		placeItems.deleteBookshelfByCoords(r, x, y);
		for(Bookshelf bs : r.getAllBookshelves()){
			System.out.println("Bookshelf "+bs.getID());
		}
	}
	public void placeBookView(Graphics g){
		for(int x=0; x<r.getWidth(); x++){
			for(int y=0; y<r.getLength(); y++){
				int drawX = x*pixelSize;
				int drawY = y*pixelSize;
				
				g.setColor(Color.white);
				g.drawRect(drawX, drawY, pixelSize, pixelSize);
				
				Square s = r.returnSquareFromGrid(x, y);
				if(s.haveUser()){
					g.setColor(Color.cyan);
					g.fillRect(drawX, drawY, pixelSize, pixelSize);
				} else if(s.hasWall()){
					g.setColor(Color.lightGray);
					g.fillRect(drawX, drawY, pixelSize, pixelSize);
				} else if(s.hasBookshelf()){
					if(s.getBookshelf() == b){
						g.setColor(Color.pink);
					} else{
						g.setColor(Color.yellow);
					}
					g.fillRect(drawX, drawY, pixelSize, pixelSize);
				} else if(s.hasSign()){
					g.setColor(Color.magenta);
					g.fillRect(drawX, drawY, pixelSize, pixelSize);
				}
			}
		}
	}
	public void paint(Graphics g){
		if(view.equals("room")){
			roomView(g);
		} else if(view.equals("shelf")){
			bookshelfView(g);
		} else if(view.equals("book")){
			bookView(g);
		} else if(view.equals("placeShelf")){
			placeShelfView(g);
		} else if(view.equals("placeBook")){
			placeBookView(g);
		}
		
	}
	
	public void keyTyped(KeyEvent e){

	}
	
	public void keyPressedRoom(KeyEvent e){
		if(e.getKeyCode() == KeyEvent.VK_LEFT){
			//move user left (x-1);
			r.moveUser('w');
			System.out.println('w');
		} else if(e.getKeyCode() == KeyEvent.VK_RIGHT){
			//move user right (x+1)
			r.moveUser('e');
			System.out.println('e');
		} else if(e.getKeyCode() == KeyEvent.VK_UP){
			//move user up (y-1)
			r.moveUser('n');
			System.out.println('n');
		} else if(e.getKeyCode() == KeyEvent.VK_DOWN){
			//move user down (y+1)
			r.moveUser('s');
			System.out.println('w');
		} else if(e.getKeyCode() == KeyEvent.VK_ENTER){
			b = r.checkAroundUserForBookshelf();
			if(b != null){
				view = "shelf";
			}
		} else if(e.getKeyCode() == KeyEvent.VK_ESCAPE){
			librarySaveOrLoad.saveLibrary(allRooms);
			System.exit(0);
		}
	}
	public void keyPressedShelf(KeyEvent e){
		System.out.println("slot: "+b.getLookX()+" shelf: "+b.getLookY());
		if(e.getKeyCode() == KeyEvent.VK_ESCAPE){
			view = "room";
		} else if(e.getKeyCode() == KeyEvent.VK_RIGHT){
			b.addLookCoords();
		} else if(e.getKeyCode() == KeyEvent.VK_LEFT){
			b.subLookCoords();
		} else if(e.getKeyCode() == KeyEvent.VK_UP){
			b.upShelfLook();
		} else if(e.getKeyCode() == KeyEvent.VK_DOWN){
			b.downShelfLook();
		} else if(e.getKeyCode() == KeyEvent.VK_ENTER){
			book = b.getBookFromSlot(b.getLookY(),b.getLookX());
			if(book != null){
				view = "book";
			}
		}
	}
	public void keyPressedBook(KeyEvent e){
		if(e.getKeyCode() == KeyEvent.VK_ESCAPE){
			view = "shelf";
		}
	}
	public void keyPressedPlaceShelf(KeyEvent e){
		if(e.getKeyCode() == KeyEvent.VK_ESCAPE){
			view = "room";
		} else if(e.getKeyCode() == KeyEvent.VK_UP){
			y--;
		} else if(e.getKeyCode() == KeyEvent.VK_DOWN){
			y++;
		} else if(e.getKeyCode() == KeyEvent.VK_LEFT){
			x--;
		} else if(e.getKeyCode() == KeyEvent.VK_RIGHT){
			x++;
		} else if(e.getKeyCode() == KeyEvent.VK_ENTER){
			placeItems.addBookshelfByDirection(r, b, x, y);
			b.setCoords(x, y);
			view = "room";
			shelf_id++;
		} else if(e.getKeyCode() == KeyEvent.VK_R){
			changeRotation();
		}
	}
	public void keyPressedPlaceBook(KeyEvent e){
		if(e.getKeyCode() == KeyEvent.VK_ESCAPE){
			view = "room";
		} else if(e.getKeyCode() == KeyEvent.VK_ENTER){
			if(b.addBook(book)){
				System.out.println("added to bookshelf "+b.getID());
			}
			view = "room";
		} else if(e.getKeyCode() == KeyEvent.VK_RIGHT){
			addRotateShelfID();
		} else if(e.getKeyCode() == KeyEvent.VK_LEFT){
			subRotateShelfID();
		}
		b = r.findBookshelfByID(rotate_id);
	}
	
	public void keyPressed(KeyEvent e){
		System.out.println("pressed");
		if(view.equals("room")){
			keyPressedRoom(e);
		} else if(view.equals("shelf")){
			keyPressedShelf(e);
		} else if(view.equals("book")){
			keyPressedBook(e);
		} else if(view.equals("placeShelf")){
			keyPressedPlaceShelf(e);
		} else if(view == "placeBook"){
			keyPressedPlaceBook(e);
		}
		repaint();
	}
	public void keyReleased(KeyEvent e){
		
	}
	
	public void mouseEntered( MouseEvent e ) { }
	public void mouseExited( MouseEvent e ) { }
	public void mousePressed( MouseEvent e ) { }
	public void mouseReleased( MouseEvent e ) { }
	public void mouseClicked( MouseEvent e ) {
		int x = e.getX();
		int y = e.getY();
		
		System.out.println(x+", "+y);
		
		if((3 < x) && (x < 125) && (210 < y) && (y < 235)){
			placeShelfButtonClicked(e);
		} else if((140 < x) && (x < 265) && (210 < y) && (y < 235) && r.containsBookshelf()){
			downloadBookButtonClicked(e);
		}	
		repaint();
	}

	public void placeShelfButtonClicked(MouseEvent e){
		System.out.println("place shelf view");
		view = "placeShelf";
	}
	public void downloadBookButtonClicked(MouseEvent e){
		System.out.println("download");
		JFileChooser fc = new JFileChooser();
		int returnVal = fc.showOpenDialog(this);
		
		if(returnVal == JFileChooser.APPROVE_OPTION){
			bookFile = fc.getSelectedFile();
			book = new Book();
			if(librarySaveOrLoad.saveBookFile(bookFile)){
//				IOUtil.
				book.setFileLocation(bookFile.getAbsolutePath());
				book.setTitle(bookFile.getName());
				System.out.println(book.getFileLocation());
				view = "placeBook";
				rotate_id = 1;
				b = r.findBookshelfByID(rotate_id);
			}
		}
	}
	
	public void addRotateShelfID(){
		if(rotate_id == shelf_id -1){
			rotate_id = 1;
		} else
			rotate_id++;
	}
	
	public void subRotateShelfID(){
		if(rotate_id == 1){
			rotate_id = shelf_id - 1;
		} else
			rotate_id--;
	}
	
	public void changeRotation(){
		if(rotation == 's'){
			rotation = 'w';
		} else if(rotation == 'w'){
			rotation = 'n';
		} else if(rotation == 'n'){
			rotation = 'e';
		} else{
			rotation = 's';
		}
	}
/*
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
*/
}
