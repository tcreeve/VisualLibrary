package com.bones;
import java.util.ArrayList;
import java.util.List;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Paths;
//import org.apache.commons.io.FileUtils;

public class librarySaveOrLoad {
	static String home = System.getProperty("user.home")+"\\.library";
	static String newline = "\n";
	static File dir = new File(home+".library");
	
	public static void saveLibrary(List<Room> roomList){
		boolean success = new java.io.File(System.getProperty("user.home"), ".library").mkdirs();
		if(success){
			System.out.println("directory .library created in user home");
		}
		File saveFile = new File(home+"\\libsave.svl");
		try{
			PrintWriter of = new PrintWriter(saveFile);
			for(Room room : roomList){
				saveRoom(room, of);
			}			
			of.close();
		} catch(FileNotFoundException e){
			System.out.println("File not found");
		}
	}
	
	public static void saveRoom(Room r, PrintWriter out){
		List<Wall> getWalls = r.getAllWalls();
		out.print("<Room Start>"+newline);
		List<Bookshelf> getShelves = r.getAllBookshelves();
		for(Bookshelf shelf : getShelves){
			saveBookshelf(shelf, out);
		}
		out.print("<Room End>"+newline);
	}
	
	public static void saveDoors(Door d){
		String stmt = "\t<Door Start>"+newline;
	}
	
	public static void saveBookshelf(Bookshelf b, PrintWriter out){
		out.print("\t<Bookshelves Start>"+newline);
		out.print("\t<"+b.getID()+";"+b.getSetX()+";"+b.getSetY()+";"+b.getDirection()+">"+newline);
		List<Book> booksInShelf = b.getAllBooks();
		for(Book book : booksInShelf){
			saveBook(book, out);
		}
		out.print("\t<Bookshelves End>"+newline);
	}
	
	public static void saveBook(Book b, PrintWriter out){
		out.print("\t\t<Book;"+b.getTitle()+";"+b.getAuthor()+";"+b.getISBN()+";"+b.getFileLocation()+";"
				+b.getSlotNum()+";"+b.getShelfNum()+">"+newline);
	}

	public static boolean saveBookFile(File source){
		createFolderIfNotThere();
		String pathTo = home+"\\"+source.getName();
		//File newBookLocation = new File(pathTo);
		File dest = new File(pathTo);

		try {
			FileUtils.copyFile(source, dest);
			return true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		
	}
	
	public static void createFolderIfNotThere(){
		dir = new File(home+".library");
		if(!dir.exists()){
			dir.mkdir();
		}
	}

	public static String[] parseLoadString(String s){
		return s.split(";");
	}
	
	public static List<Room> loadLibrary(){
		List<Room> allRooms = new ArrayList<Room>();
		Room r = null;
		Bookshelf shelf = null;
		Book book = null;
		try{
			BufferedReader reader = new BufferedReader(new FileReader(home+"\\libsave.svl"));
			String line = null;
			while((line = reader.readLine()) != null){
				if(line.contains("Room Start")){
					r = new Room();
					allRooms.add(r);
					placeItems.placeWallsAroundRoom(r);
				} else if(line.contains("Bookshelves Start")){
					shelf = new Bookshelf();
				} else if(line.contains("Book;")){
					book = new Book();
					String[] values = parseLoadString(line);
					book.setTitle(values[1]);
					book.setAuthor(values[2]);
					book.setISBN(values[3]);
					book.setFileLocation(values[4]);
					int slotNum = Integer.parseInt(values[5]);
					book.setSlotNum(slotNum);
					int end = values[6].indexOf(">");
					int shelfNum = Integer.parseInt(values[6].substring(0, end));
					book.setShelfNum(shelfNum);
					shelf.addBookToSlot(book, shelfNum, slotNum);
					book = null;
				} else if(line.contains("Bookshelves End")){
					shelf = null;
				} else if(line.contains("Room End")){
					r = null;
				} else if((shelf != null) && (r != null)){
					String[] values = parseLoadString(line);
					int start = values[0].indexOf('<');
					int id = Integer.parseInt(values[0].substring(start+1));
					shelf.setID(id);
					shelf.setCoords(Integer.parseInt(values[1]), Integer.parseInt(values[2]));
					shelf.setDirection(values[3].charAt(0));
					//r.addBookshelfToSquare(shelf, shelf.getSetX(), shelf.getSetY());
					placeItems.addBookshelfByDirection(r, shelf, shelf.getSetX(), shelf.getSetY());
				}
			}
			reader.close();
		} catch(FileNotFoundException e){
			System.out.println("File doesn't exist");
			return null;
		} catch(IOException io){
			System.out.println("File corrupted");
			io.printStackTrace();
			return null;
		}
		return allRooms;
	}
}
