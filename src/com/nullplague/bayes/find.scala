package com.nullplague.bayes
import scala.io.StdIn.readLine

object find {
    def main(args: Array[String]): Unit = {
        if (!new java.io.File(".ranks").exists)
        {
            val newfile = new java.io.FileOutputStream(".ranks")
            newfile.close()
        }
        val history = inout.readSet(scala.io.Source.fromFile(".ranks"))
        val jobs = cl.jobs(history)
        for (j <- jobs) {
        	val txt = cl.html(j)
   	        val inmap = bayes.normalize(bayes.count(txt))
   	        println("Ranking " + j)
        	if (rank.rank(inmap)._2 == args(0))
        	{
        	    println(txt)
        	    readLine
        	}
        }
    }
}