package com.stupidplebs.largestisland

import spock.lang.Specification

class ColorSpec extends Specification {
	def "toString should output letter 'B' for Color.BLACK"() {
		given:
		def color = Color.BLACK
		
		expect:
		color.toString() == 'B'
		
	}

	def "toString should output '-' for Color.WHITE"() {
		given:
		def color = Color.WHITE
		
		expect:
		color.toString() == '-'

	}

}
