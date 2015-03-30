package com.stupidplebs.generativegrid;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.google.common.base.Joiner;

public class Grid {
	private final static Joiner NEWLINE_JOINER = Joiner.on('\n');
	
	private final Color[][] grid;

	public Grid(final Integer height, final Integer width, final Iterable<Pair> blackPairs) {
		requireNonNull(height, "height cannot be null");
		requireNonNull(width, "width cannot be null");
		requireNonNull(blackPairs, "blackPairs cannot be null");
		
		if (width <= 0) {
			throw new IllegalArgumentException("width must be a positive integer");
		}
		if (height <= 0) {
			throw new IllegalArgumentException("height must be a positive integer");
		}
		
		// initialize the 2-d grid
		this.grid = new Color[height][];
		for (int y = 0; y < height; y++) {
			this.grid[y] = new Color[width];
			for (int x = 0; x < width; x++) {
				this.grid[y][x] = Color.WHITE;
			}
		}
		
		// now map in the black pairs
		for (final Pair blackPair : blackPairs) {
			this.grid[blackPair.getY()][blackPair.getX()] = Color.BLACK;
		}
		
	}
	
	public String prettyPrint() {
		final List<String> lines = new ArrayList<>();
		
		for (int y = getHeight()-1; y >= 0; y--) {
			final StringBuilder line = new StringBuilder();
			
			for (int x = 0; x < getWidth(); x++) {
				line.append(grid[y][x].getChar());
			}
			lines.add(line.toString());
		}
		
		return NEWLINE_JOINER.join(lines);
		
	}
	
	public Integer getHeight() {
		return grid.length;
	}
	
	public Integer getWidth() {
		return grid[0].length;
	}
	
	public Color getColor(final Pair pair) {
		requireNonNull(pair, "pair cannot be null");
		if (pair.getX() > getWidth()) {
			throw new IllegalArgumentException("pair.x cannot be greater than grid width");
		}
		if (pair.getY() > getHeight()) {
			throw new IllegalArgumentException("pair.y cannot be greater than grid height");
		}
		
		return grid[pair.getY()][pair.getX()];
		
	}
	
	public Collection<Pair> getPairsByColor(final Color color) {
		final Set<Pair> pairs = new HashSet<>();
		
		for (int x = 0; x < getWidth(); x++) {
			for (int y = 0; y < getHeight(); y++) {
				if (color == grid[y][x]) {
					pairs.add(Pair.of(x, y));
				}
			}
		}
		
		return pairs;
		
	}
	
	public Collection<Pair> getLargestIsland(final Pair pair) {
		requireNonNull(pair, "pair cannot be null");
		
		if (grid[pair.getY()][pair.getX()] != Color.BLACK) {
			throw new IllegalArgumentException(String.format(
					"(%d,%d) is not BLACK", pair.getX(), pair.getY()));
		}

		return visit(pair, new boolean[getHeight()][getWidth()]);
		
	}
	
	private Collection<Pair> visit(final Pair pair, final boolean[][] visited) {
		visited[pair.getY()][pair.getX()] = true;
		
		final Set<Pair> blackPairs = new HashSet<>();
		blackPairs.add(pair);

		for (final Pair neighbor : getNeighbors(pair, visited)) {
			if (getColor(neighbor) == Color.BLACK && 
					!visited[neighbor.getY()][neighbor.getX()]) {
				blackPairs.addAll(visit(neighbor, visited));
			}
			
		}
		
		return blackPairs;
		
	}

	private Iterable<Pair> getNeighbors(final Pair pair, 
			final boolean[][] visited) {
		final List<Pair> neighbors = new ArrayList<>();

		// north
		if (pair.getY()+1 < getHeight()) {
			neighbors.add(Pair.of(pair.getX(), pair.getY()+1));
		}
		
		// south
		if (pair.getY()-1 >= 0) {
			neighbors.add(Pair.of(pair.getX(), pair.getY()-1));
		}
		
		// east
		if (pair.getX()+1 < getWidth()) {
			neighbors.add(Pair.of(pair.getX()+1, pair.getY()));
		}
		
		// west
		if (pair.getX()-1 >= 0) {
			neighbors.add(Pair.of(pair.getX()-1, pair.getY()));
		}
		
		return neighbors;
		
	}
	
}
