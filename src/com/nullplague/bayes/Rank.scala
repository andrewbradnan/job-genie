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
    def db : bayes.DB = {
        val f = files(".cat")
        val rt = bayes.DB()
        for (file <- f)
            rt += file.getName -> new Category(file.getName, inout.read(scala.io.Source.fromFile(file)))
        rt
    }
    def ranks(in: bayes.Scores, db: bayes.DB) = {
    	bayes.p(in, db)
    }
    def rank(in: bayes.Scores) : Option[(Float, String)] = {
        val r = ranks(in, db)
        if (r.size > 0) Some(r.last) else None
    }
    def main(args: Array[String]): Unit = {
        println(ranks(inout.read(stdin), db))
    }

}