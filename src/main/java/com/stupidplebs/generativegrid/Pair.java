package com.stupidplebs.generativegrid;

import static java.util.Objects.requireNonNull;

import java.util.Objects;

public class Pair {
	private final Integer x;
	private final Integer y;
	
	private Pair(final Integer x, final Integer y) {
		this.x = x;
		this.y = y;
	}
	
	public static Pair of(final Integer x, final Integer y) {
		requireNonNull(x, "x cannot be null");
		requireNonNull(y, "y cannot be null");
		
		if (x < 0) {
			throw new IllegalArgumentException("x cannot be negative");
		}
		if (y < 0) {
			throw new IllegalArgumentException("y cannot be negative");
		}

		return new Pair(x, y);
		
	}
	
	public Integer getX() {
		return x;
	}
	
	public Integer getY() {
		return y;
	}
	
	@Override
	public boolean equals(final Object obj) {
		if (null == obj) {
			return false;
		}
		if (this == obj) {
			return true;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		
		final Pair other = (Pair)obj;
		
		return Objects.equals(x, other.x) && 
				Objects.equals(y, other.y);
		
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(x, y);
	}
	
	@Override
	public String toString() {
		return String.format("Pair{%d,%d}", x, y);
	}
		
}
