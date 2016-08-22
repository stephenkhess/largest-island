package com.stupidplebs.largestisland.generators

import com.stupidplebs.largestisland.Pair


class ProbabilisticIslandGenerator {
    def random = new Random()

    /**
     * generate an island given the probability that a neighbor will be BLACK
     * 
     * @param probability
     * @return
     */
    def generateIsland(height, width, probability) {
        // generate a random place to start from
        def x = random.nextInt(width)
        def y = random.nextInt(height)
        def startingPair = Pair.of(x, y)

        visit(startingPair, new boolean[height][width], probability)

    }

    def visit(pair, visited, probability) {
        // mark pair as visited
        visited[pair.y][pair.x] = true

        def blackPairs = [pair] as Set

        // iterate over N, E, S, and W, making a neighbor BLACK if
        // random.nextDouble is <= the probability of it being BLACK
        for (neighbor in getNeighbors(pair, visited)) {
            if (!visited[neighbor.y][neighbor.x] &&
            random.nextDouble() <= probability) {
                blackPairs.addAll(visit(neighbor, visited, probability))
            }
        }

        blackPairs

    }

    def getNeighbors(pair, visited) {
        def neighbors = []

        // north
        if (pair.y+1 < visited.length) {
            neighbors << Pair.of(pair.x, pair.y+1)
        }

        // south
        if (pair.y-1 >= 0) {
            neighbors << Pair.of(pair.x, pair.y-1)
        }

        // east
        if (pair.x+1 < visited[0].length) {
            neighbors << Pair.of(pair.x+1, pair.y)
        }

        // west
        if (pair.x-1 >= 0) {
            neighbors << Pair.of(pair.x-1, pair.y)
        }

        neighbors

    }

}
