package com.stupidplebs.generativegrid;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Grid {
    private final Color[][] grid;

    public Grid(final Integer height, final Integer width,
            final Iterable<Pair> blackPairs) {
        requireNonNull(height, "height cannot be null");
        requireNonNull(width, "width cannot be null");
        requireNonNull(blackPairs, "blackPairs cannot be null");

        if (width <= 0) {
            throw new IllegalArgumentException(
                    "width must be a positive integer");
        }
        if (height <= 0) {
            throw new IllegalArgumentException(
                    "height must be a positive integer");
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

    public Integer getHeight() {
        return grid.length;
    }

    public Integer getWidth() {
        return grid[0].length;
    }

    public Collection<Pair> getLargestIsland(final Pair pair) {
        requireNonNull(pair, "pair cannot be null");

        if (!isBlack(pair)) {
            throw new IllegalArgumentException(String.format(
                    "(%d,%d) is not BLACK", pair.getX(), pair.getY()));
        }

        return visit(pair, new boolean[getHeight()][getWidth()]);

    }

    private Collection<Pair> visit(final Pair pair, final boolean[][] visited) {
        visited[pair.getY()][pair.getX()] = true;

        final Set<Pair> blackPairs = new HashSet<>();
        blackPairs.add(pair);

        for (final Pair neighbor : getNeighbors(pair)) {
            if (isBlack(neighbor) && !hasBeenVisited(neighbor, visited)) {
                blackPairs.addAll(visit(neighbor, visited));
            }

        }

        return blackPairs;

    }

    private Boolean isBlack(final Pair pair) {
        return grid[pair.getY()][pair.getX()] == Color.BLACK;
    }

    private Boolean hasBeenVisited(final Pair pair, final boolean[][] visited) {
        return visited[pair.getY()][pair.getX()];
    }

    private Iterable<Pair> getNeighbors(final Pair pair) {
        final List<Pair> neighbors = new ArrayList<>();

        // north
        if (pair.getY() + 1 < getHeight()) {
            neighbors.add(Pair.of(pair.getX(), pair.getY() + 1));
        }

        // south
        if (pair.getY() - 1 >= 0) {
            neighbors.add(Pair.of(pair.getX(), pair.getY() - 1));
        }

        // east
        if (pair.getX() + 1 < getWidth()) {
            neighbors.add(Pair.of(pair.getX() + 1, pair.getY()));
        }

        // west
        if (pair.getX() - 1 >= 0) {
            neighbors.add(Pair.of(pair.getX() - 1, pair.getY()));
        }

        return neighbors;

    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();

        for (int y = getHeight() - 1; y >= 0; y--) {
            for (int x = 0; x < getWidth(); x++) {
                sb.append(grid[y][x]);
            }
            if (y > 0) {
                sb.append('\n');
            }
        }

        return sb.toString();

    }

}
