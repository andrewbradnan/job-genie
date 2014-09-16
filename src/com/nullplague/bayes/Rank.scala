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
    def maps() : Array[(bayes.Scores, String)] = {
        val f = files(".cat")
        for (file <- f)
            yield (inout.read(scala.io.Source.fromFile(file)), file.getName)
    }
    def rank(in: bayes.Scores) : (Float, String) = {
        val f = files(".cat")
        var ranks = SortedMap[Float,String]()
        var rank = 0.0f
        for (cat <- maps()) 
        {
            val score = bayes.mul(in, cat._1)
            ranks = ranks + ((score, cat._2))
        }
        
        ranks.last
     }
    def main(args: Array[String]): Unit = {
        rank(inout.read(stdin))
    }

}