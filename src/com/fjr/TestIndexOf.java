package com.fjr;

public class TestIndexOf {
	
	public static void main(String[] args) {
		
		
		String makan = "ujangan)killll"; 
		int b  = makan.indexOf(")");
		System.out.println(b ); 
		
		String m = makan.substring(0, b); 
		System.out.println(m); 
 	}

}
