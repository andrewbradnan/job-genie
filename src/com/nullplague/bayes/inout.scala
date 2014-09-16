package com.nullplague.bayes

import scala.collection.mutable.Map

object inout {
	def read(io: scala.io.BufferedSource) : bayes.Scores = {
        var rt = bayes.Scores()
        val content = io.getLines.map(_.split(","))
        while(content.hasNext) {
            val strs = content.next()
            val count = strs(1).toFloat
        	rt += (strs(0) -> count)
        }
        bayes.normalize(rt)
	}
	def readSet(io: scala.io.BufferedSource) : Set[String] = {
        var rt = Set[String]()
        val content = io.getLines
        while(content.hasNext)
        	rt += content.next().split(',')(0)
        rt
	}

}