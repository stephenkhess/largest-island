package com.stupidplebs.generativegrid

class RingIslandGenerator {
	def width
	def height

	def generateIsland() {
		def island = [] as Set
		
		island.addAll((0..width-1).collect { Pair.of(it, 0) } ) // top
		island.addAll((0..height-1).collect { Pair.of(0, it) } ) // left
		island.addAll((0..height-1).collect { Pair.of(width-1, it) } ) // right
		island.addAll((0..width-1).collect { Pair.of(it, height-1) }) // bottom
	
		island
			
	}

}
