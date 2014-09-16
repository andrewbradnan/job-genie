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
    def main(args: Array[String]): Unit = {
        val counts = inout.read(stdin)
        val f = files(".cat")
        var ranks = SortedMap[Float,String]()
        var rank = 0.0f
        for (file <- f) 
        {
            val catmap = inout.read(scala.io.Source.fromFile(file))
            val score = bayes.mul(counts, catmap)
            ranks = ranks + ((score, file.getName.split('.')(0)))
        }
        
        println(ranks)
        
    }

}