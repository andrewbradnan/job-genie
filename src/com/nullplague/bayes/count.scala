package com.nullplague.bayes

import io.Source.stdin

object count {

    

    def main(args: Array[String]): Unit = 
    {
    	val counts = if (args.length == 0)
    		bayes.count(stdin.getLines)
    	else
    		bayes.count(scala.io.Source.fromFile(args(0)).getLines)
    	
    	for((word,count) <- counts) println(word + "," + count)
    }

}