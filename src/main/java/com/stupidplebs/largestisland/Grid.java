package com.stupidplebs.largestisland;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Grid {
    private final Integer height;
    private final Integer width;
    private final Set<Pair> blackPairs = new HashSet<>();

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

        this.height = height;
        this.width = width;
        for (final Pair pair : blackPairs) {
            this.blackPairs.add(pair);
        }
        
    }

    public Collection<Pair> getLargestIsland(final Pair pair) {
        requireNonNull(pair, "pair cannot be null");

        if (!this.blackPairs.contains(pair)) {
            throw new IllegalArgumentException(String.format(
                    "(%d,%d) is not BLACK", pair.getX(), pair.getY()));
        }

        return visit(pair, new HashSet<>());

    }

    private Collection<Pair> visit(final Pair pair, final Set<Pair> visited) {
        // mark that this pair has been visited so it's not visited again
        visited.add(pair);

        final Set<Pair> blackPairs = new HashSet<>();
        blackPairs.add(pair);

        for (final Pair neighbor : getNeighbors(pair)) {
            if (this.blackPairs.contains(neighbor) && !visited.contains(neighbor)) {
                blackPairs.addAll(visit(neighbor, visited));
            }

        }

        return blackPairs;

    }

    private Iterable<Pair> getNeighbors(final Pair pair) {
        final List<Pair> neighbors = new ArrayList<>();

        // north
        if (pair.getY() + 1 < height) {
            neighbors.add(Pair.of(pair.getX(), pair.getY() + 1));
        }

        // south
        if (pair.getY() - 1 >= 0) {
            neighbors.add(Pair.of(pair.getX(), pair.getY() - 1));
        }

        // east
        if (pair.getX() + 1 < width) {
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

        for (int y = height - 1; y >= 0; y--) {
            for (int x = 0; x < width; x++) {
                sb.append(blackPairs.contains(Pair.of(x, y)) ? 'B' : '-');
            }
            if (y > 0) {
                sb.append('\n');
            }
        }

        return sb.toString();

    }

}
