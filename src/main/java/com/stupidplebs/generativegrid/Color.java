package com.stupidplebs.generativegrid;

public enum Color {
	BLACK('B'), WHITE('-');
	
	final Character c;
	
	private Color(Character c) {
		this.c = c;
	}

	public Character getChar() {
		return c;
	}
	
}
