package com.stupidplebs.generativegrid;

public enum Color {
    BLACK('B'), WHITE('-');
    
    private final Character c;
    
    private Color(final Character c) {
    	this.c = c;
    }
    
    @Override
    public String toString() {
    	return c.toString();
    }
    
}
