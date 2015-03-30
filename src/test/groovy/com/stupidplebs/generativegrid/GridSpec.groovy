package com.stupidplebs.generativegrid

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import spock.lang.Ignore;
import spock.lang.Shared;
import spock.lang.Specification

class GridSpec extends Specification {
	@Shared def random = new Random()
	
	def "null height should throw NullPointerException"() {
		when:
		new Grid(null, random.nextInt(100)+1, [])
		
		then:
		NullPointerException e = thrown()
		e.message == "height cannot be null"
		
	}
	
	def "null width should throw NullPointerException"() {
		when:
		new Grid(random.nextInt(100)+1, null, [])
		
		then:
		NullPointerException e = thrown()
		e.message == "width cannot be null"
		
	}
	
	def "null blackPairs should throw NullPointerException"() {
		when:
		new Grid(random.nextInt(100)+1, random.nextInt(100)+1, null)
		
		then:
		NullPointerException e = thrown()
		e.message == "blackPairs cannot be null"
		
	}
	
	def "non-positive height should throw IllegalArgumentException"() {
		when:
		new Grid(height, random.nextInt(100)+1, [])
		
		then:
		IllegalArgumentException e = thrown()
		e.message == "height must be a positive integer"
		
		where:
		height                        | _
		0                             | _
		Math.abs(random.nextInt())*-1 | _
		
	}

	def "non-positive width should throw IllegalArgumentException"() {
		when:
		new Grid(random.nextInt(100)+1, width, [])
		
		then:
		IllegalArgumentException e = thrown()
		e.message == "width must be a positive integer"
		
		where:
		width                         | _
		0                             | _
		Math.abs(random.nextInt())*-1 | _
		
	}
	
	def "getWidth and getHeight should return the width of the grid"() {
		given:
		def height = random.nextInt(100)+1
		def width = random.nextInt(100)+1
		
		and:
		def grid = new Grid(height, width, [])
		
		expect:
		grid.height == height
		grid.width == width
		
	}
	
	def "null cell parameter to get should throw NullPointerException"() {
		given:
		def height = random.nextInt(100)+1
		def width = random.nextInt(100)+1
		
		and:
		def grid = new Grid(height, width, [])
		
		when:
		grid.getColor(null)
		
		then:
		NullPointerException e = thrown()
		e.message == "pair cannot be null"
		
	}
	
	def "cell with x greater than grid width should throw IllegalArgumentException"() {
		given:
		def height = random.nextInt(100)+1
		def width = random.nextInt(100)+1

		and:
		def grid = new Grid(height, width, [])
		
		when:
		grid.getColor(new Pair(grid.width+1, random.nextInt(grid.height)))
		
		then:
		IllegalArgumentException e = thrown()
		e.message == "pair.x cannot be greater than grid width"

	}

	def "cell with y greater than grid height should throw IllegalArgumentException"() {
		given:
		def height = random.nextInt(100)+1
		def width = random.nextInt(100)+1

		and:
		def grid = new Grid(height, width, [])
		
		when:
		grid.getColor(new Pair(random.nextInt(grid.width), grid.height+1))
		
		then:
		IllegalArgumentException e = thrown()
		e.message == "pair.y cannot be greater than grid height"

	}
	
	def "getHeight and getWidth should return the height and width as supplied by the GridPopulator"() {
		given:
		def height = random.nextInt(100) + 1
		def width = random.nextInt(100) + 1
		
		and:
		def grid = new Grid(height, width, [])
		
		expect:
		grid.height == height
		grid.width == width
		
	}

	def "getColor should return BLACK if cell is was a black coordinate"() {
		given:
		def height = 4
		def width = 4 
		def blackPairs = [
				Pair.of(0, 0),
				Pair.of(1, 1),
				Pair.of(2, 2),
				Pair.of(3, 3)
				]
		
		and:
		def grid = new Grid(height, width, blackPairs)
		
		expect:
		grid.getColor(new Pair(x, y)) == Color.BLACK
		
		where:
		[x, y] << [(0..3), (0..3)].combinations().findAll { x, y -> x == y }

	}
	
	def "getColor should return WHITE if cell is was not a black coordinate"() {
		given:
		def height = 4
		def width = 4 
		def blackPairs = [
				Pair.of(0, 0),
				Pair.of(1, 1),
				Pair.of(2, 2),
				Pair.of(3, 3)
				]

		and:
		def grid = new Grid(height, width, blackPairs)
		
		expect:
		grid.getColor(new Pair(x, y)) == Color.WHITE
		
		where:
		[x, y] << [(0..3), (0..3)].combinations().findAll { x, y -> x != y }

	}
	
	def "getPairsByColor should return all BLACK pairs when asked"() {
		given:
		def height = 4
		def width = 4 
		def blackPairs = [
				Pair.of(0, 0),
				Pair.of(1, 1),
				Pair.of(2, 2),
				Pair.of(3, 3)
				]

		and:
		def grid = new Grid(height, width, blackPairs)
		
		expect:
		grid.getPairsByColor(Color.BLACK) == [
			Pair.of(0, 0), Pair.of(1, 1), Pair.of(2, 2), Pair.of(3, 3)
			] as Set

	}
	
	def "getPairsByColor should return all WHITE pairs when asked"() {
		given:
		def height = 4
		def width = 4 
		def blackPairs = [
				Pair.of(0, 0),
				Pair.of(1, 1),
				Pair.of(2, 2),
				Pair.of(3, 3)
				]

		and:
		def grid = new Grid(height, width, blackPairs)
		
		expect:
		grid.getPairsByColor(Color.WHITE) == [
			Pair.of(1, 0), Pair.of(2, 0), Pair.of(3, 0),
			Pair.of(0, 1), Pair.of(2, 1), Pair.of(3, 1),
			Pair.of(0, 2), Pair.of(1, 2), Pair.of(3, 2),
			Pair.of(0, 3), Pair.of(1, 3), Pair.of(2, 3)
			] as Set

	}
	
	def "null pair parameter to getLargestIsland should throw NullPointerException"() {
		given:
		def height = 4
		def width = 4 
		def blackPairs = []

		and:
		def grid = new Grid(height, width, blackPairs)

		when:
		grid.getLargestIsland(null)

		then:
		NullPointerException e = thrown()
		e.message == "pair cannot be null"

	}
	
	def "dump"() {
		given:
		def height = 4
		def width = 6
		def blackPairs = [
			Pair.of(0, 0),
			Pair.of(1, 1),
			Pair.of(2, 2),
			Pair.of(3, 3)
			]

		and:
		def grid = new Grid(height, width, blackPairs)

		expect:
		grid.prettyPrint() == ["---B--", "--B---", "-B----", "B-----"].join('\n')
		
	}
	
	def "getLargestIsland should throw IllegalArgumentException if supplied Pair is not BLACK"() {
		given:
		def height = 4
		def width = 4
		def blackPairs = [
			Pair.of(0, 0),
			Pair.of(1, 1),
			Pair.of(2, 2),
			Pair.of(3, 3)
			]

		and:
		def grid = new Grid(height, width, blackPairs)

		when:
		grid.getLargestIsland(Pair.of(x, y))		

		then:
		IllegalArgumentException e = thrown()
		e.message == "($x,$y) is not BLACK"

		where:
		[x, y] << [(0..3), (0..3)].combinations().findAll { x, y -> x != y }
				
	}
	
	def "getLargestIsland explicit island"() {
		given:
		def height = 4
		def width = 10
		def blackPairs = [
				Pair.of(0, 0),
				Pair.of(0, 1),
				Pair.of(1, 0),
				Pair.of(1, 1)
			]
		
		and:
		def grid = new Grid(height, width, blackPairs)

		when:
		def island = grid.getLargestIsland(startingPair)

		then:
		island == [Pair.of(0, 0),
				Pair.of(0, 1),
				Pair.of(1, 0),
				Pair.of(1, 1)] as Set

		where:
		startingPair << [Pair.of(0,0), Pair.of(0, 1), Pair.of(1, 0), Pair.of(1, 1)]
			
	}
	
	def "probabilistic island generator"() {
		given:
		def height = random.nextInt(100)+1
		def width = random.nextInt(100)+1
		
		def islandGenerator = new ProbabilisticIslandGenerator([height: height, width: width])
		def blackPairs = islandGenerator.generateIsland(0.5)

		def startingPair = blackPairs.toList()[random.nextInt(blackPairs.size())]
			
		and:
		def grid = new Grid(height, width, blackPairs)

		when:
//		println grid.prettyPrint() + ""
		def island = grid.getLargestIsland(startingPair)

		then:
		island == blackPairs
		
		where:
		i << (1..50)
		
	}
	
	def "ring island generator"() {
		given:
		def grid = new Grid(row.height, row.width, row.blackPairs)

		when:
//		println grid.prettyPrint() + ""
		def island = grid.getLargestIsland(row.startingPair)

		then:
		island == row.blackPairs
		
		where:
		row << (1..10).collect {
			def height = random.nextInt(100)+1
			def width = random.nextInt(100)+1
			
			def islandGenerator = new RingIslandGenerator([height: height, width: width])
			def blackPairs = islandGenerator.generateIsland()
	
			def startingPair = blackPairs.toList()[random.nextInt(blackPairs.size())]
			
			[height: height, width: width, blackPairs: blackPairs, startingPair: startingPair]
			
		}

	}
	
}
