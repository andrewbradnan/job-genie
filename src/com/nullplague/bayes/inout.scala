package com.nullplague.bayes

import scala.collection.mutable.Map

object inout {
	def read(io: scala.io.BufferedSource) : Map[String, Int] = {
        var rt = scala.collection.mutable.Map[String, Int]()
        //var total = 0
        val content = io.getLines.map(_.split(","))
        while(content.hasNext) {
            val strs = content.next()
            val count = strs(1).toInt
        	rt += (strs(0) -> count)
        	//total = total + count
        }
        //rt.map(pr => (pr._1, pr._2/total.toFloat))
        rt
	}
	def readSet(io: scala.io.BufferedSource) : Set[String] = {
        var rt = Set[String]()
        val content = io.getLines
        while(content.hasNext)
        	rt += content.next()
        rt
	}

}