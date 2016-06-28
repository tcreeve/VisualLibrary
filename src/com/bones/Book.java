package com.bones;

public class Book {
	private String title;
	private String author;
	private String isbn;
	private String textDirec;
	private int slot;
	private int shelf;
	
	public Book(){
		title = "";
		author = "";
		isbn = "";
		textDirec = "";
	}
	
	public Book(String bookTitle, String bookAuthor, String bookISBN, String fileLocation){
		title = bookTitle;
		author = bookAuthor;
		isbn = bookISBN;
		textDirec = fileLocation;
	}

	public String getTitle(){
		return title;
	}
	
	public void setTitle(String t){
		title = t;
	}
	
	public String getAuthor(){
		return author;
	}
	
	public void setAuthor(String a){
		author = a;
	}
	
	public String getISBN(){
		return isbn;
	}
	
	public void setISBN(String i){
		isbn = i;
	}
	
	public String getFileLocation(){
		return textDirec;
	}
	
	public void setFileLocation(String l){
		textDirec = l;
	}
	
	public int getSlotNum(){
		return slot;
	}
	
	public void setSlotNum(int s){
		slot = s;
	}
	
	public int getShelfNum(){
		return shelf;
	}
	
	public void setShelfNum(int s){
		shelf = s;
	}
	
	public String toString(){
		return title+" by "+author+" \n"+isbn+" "+textDirec;
	}
}
