package com.nullplague.bayes
import io.Source.stdin
import scala.collection.mutable.Map

object mul {

    def main(args: Array[String]): Unit = {
        if (args.length >= 1) {
            println(bayes.process(args(0), if (args.length == 2) args(1) else stdin, bayes.mul))
        }
    }

}