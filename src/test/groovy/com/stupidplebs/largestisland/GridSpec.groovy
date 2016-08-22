package com.stupidplebs.largestisland

import spock.lang.Shared
import spock.lang.Specification

import com.stupidplebs.largestisland.generators.ProbabilisticIslandGenerator
import com.stupidplebs.largestisland.generators.RingIslandGenerator

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

    def "toString should output 'B' for BLACK and '-' for WHITE"() {
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
        grid.toString() == [
            "---B--",
            "--B---",
            "-B----",
            "B-----"
        ].join('\n')
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
        [x, y]<< [(0..3), (0..3)].combinations().findAll { x, y -> x != y }
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
        island == [
            Pair.of(0, 0),
            Pair.of(0, 1),
            Pair.of(1, 0),
            Pair.of(1, 1)] as Set

        where:
        startingPair << [
            Pair.of(0,0),
            Pair.of(0,1),
            Pair.of(1,0),
            Pair.of(1,1)
        ]
    }

    def "probabilistic island generator"() {
        given:
        def islandGenerator = new ProbabilisticIslandGenerator()

        and:
        def height = random.nextInt(100)+1
        def width = random.nextInt(100)+1
        def blackPairs = islandGenerator.generateIsland(height, width, 0.5)

        and: "select a random starting pair from the black pairs"
        def startingPair = blackPairs.toList()[random.nextInt(blackPairs.size())]

        and:
        def grid = new Grid(height, width, blackPairs)

        when:
//        println grid.toString() + "\n"
        def island = grid.getLargestIsland(startingPair)

        then:
        island == blackPairs

        where:
        i << (1..5)

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
