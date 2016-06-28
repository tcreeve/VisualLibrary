package com.bones;

public class Sign {
	String msg;
	
	public Sign(){
		msg = "";
	}
	
	public Sign(String m){
		msg = m;
	}
	
	public String getMessage(){
		return msg;
	}
	
	public void setMessage(String m){
		msg = m;
	}

}
