package com.stupidplebs.generativegrid

import java.util.Collection;

class ProbabilisticIslandGenerator {
	def random = new Random()
	
	def width
	def height

	def generateIsland(probability) {
		def x = random.nextInt(width)
		def y = random.nextInt(height)
		
		def startingPair = Pair.of(x, y)
		
		visit(startingPair, new boolean[height][width], probability)
		
	}
	
	def visit(pair, visited, probability) {
		visited[pair.y][pair.x] = true
		
		def blackPairs = [] as Set
		blackPairs << pair

		for (final Pair neighbor : getNeighbors(pair, visited)) {
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
