package com.stupidplebs.generativegrid

import spock.lang.Shared
import spock.lang.Specification

class PairSpec extends Specification {
	@Shared def random = new Random()

	def "null x parameter should throw NullPointerException"() {
		when:
		Pair.of(null, random.nextInt())
		
		then:
		NullPointerException e = thrown()
		e.message == "x cannot be null"
		
	}

	def "null y parameter should throw NullPointerException"() {
		when:
		Pair.of(random.nextInt(), null)
		
		then:
		NullPointerException e = thrown()
		e.message == "y cannot be null"
		
	}
	
	def "negative x parameter should throw IllegalArgumentException"() {
		given:
		def x = (random.nextInt(100)+1) * -1
		def y = random.nextInt(100)
		
		when:
		def pair = Pair.of(x, y)
		
		then:
		IllegalArgumentException e = thrown()
		e.message == "x cannot be negative"

	}
	
	def "negative y parameter should throw IllegalArgumentException"() {
		given:
		def x = random.nextInt(100)
		def y = (random.nextInt(100)+1) * -1
		
		when:
		def pair = Pair.of(x, y)
		
		then:
		IllegalArgumentException e = thrown()
		e.message == "y cannot be negative"

	}
	
	def "0 should be accepted for both x and y"() {
		given:
		def pair = Pair.of(0, 0)
		
		expect:
		pair.x == 0
		pair.y == 0
		
	}
	
	def "getters should return x and y passed to constructor"() {
		given:
		def x = Math.abs(random.nextInt())
		def y = Math.abs(random.nextInt())

		and:		
		def pair = Pair.of(x, y)
		
		expect:
		pair.x == x
		pair.y == y
		
	}

	def "instance should be unequal to null"() {
		given:
		def pair = Pair.of(Math.abs(random.nextInt()), Math.abs(random.nextInt()))
		
		and:
		def other = null
		
		expect:
		!pair.equals(other)
		
	}
	
	def "instance should be equal to itself"() {
		given:
		def pair = Pair.of(Math.abs(random.nextInt()), Math.abs(random.nextInt()))
		
		and:
		def other = pair
		
		expect:
		pair.equals(other)
		
	}
	
	def "instance should be unequal to a non-Coordinate object"() {
		given:
		def pair = Pair.of(Math.abs(random.nextInt()), Math.abs(random.nextInt()))
		
		and:
		def other = "this is not an instance of Pair"
		
		expect:
		!pair.equals(other)
		
	}
	
	def "instances differing on x should be unequal and have unequal hashCodes"() {
		given:
		def y = Math.abs(random.nextInt())

		and:		
		def pair = Pair.of(1, y)
		
		and:
		def other = Pair.of(2, y)
		
		expect:
		!pair.equals(other)
		pair.hashCode() != other.hashCode()
		
	}
	
	def "instances differing on y should be unequal and have unequal hashCodes"() {
		given:
		def x = Math.abs(random.nextInt())

		and:		
		def pair = Pair.of(x, 1)
		
		and:
		def other = Pair.of(x, 2)
		
		expect:
		!pair.equals(other)
		pair.hashCode() != other.hashCode()
		
	}
	
	def "instances with equal x and y should be equal and have equal hashCodes"() {
		given:
		def x = Math.abs(random.nextInt())
		def y = Math.abs(random.nextInt())
		
		and:
		def pair = Pair.of(x, y)
		
		and:
		def other = Pair.of(x, y)
		
		expect:
		pair.equals(other)
		pair.hashCode() == other.hashCode()
		
	}
	
	def "toString should output class name and all fields"() {
		given:
		def x = Math.abs(random.nextInt())
		def y = Math.abs(random.nextInt())
		
		and:
		def pair = Pair.of(x, y)

		expect:
		pair.toString() == "Pair{$x,$y}"
		
	}
	
}
