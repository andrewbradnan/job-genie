package com.nullplague.bayes
import io.Source.stdin
import java.io._
import scala.collection.immutable.SortedMap

object rank {

    def files(filter: String) = {
    	val files = new File(".").listFiles()
    	//If this pathname does not denote a directory, then listFiles() returns null. 

    	files.filter(_.getName.reverse.startsWith(filter.reverse))
    }
    def maps() : bayes.DB = {
        val f = files(".cat")
        val rt = bayes.DB()
        for (file <- f)
            rt += file.getName -> new Category(file.getName, inout.read(scala.io.Source.fromFile(file)))
        rt
    }
    def rank(in: bayes.Scores) : Option[(Float, String)] = {
        val f = files(".cat")
        var ranks = SortedMap[Float,String]()
        var rank = 0.0f
        for (cat <- maps()) 
        {
            val counts = cat._2.counts
            val name = cat._2.name
            val score = bayes.mul(in, counts)
            ranks = ranks + ((score, name))
        }
        
        if (ranks.size > 0) Some(ranks.last) else None
     }
    def main(args: Array[String]): Unit = {
        rank(inout.read(stdin))
    }

}