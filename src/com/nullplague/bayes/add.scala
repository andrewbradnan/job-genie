package com.nullplague.bayes
import scala.io.Source.stdin

// usage: 
// add one.cat two.cat >> three.cat
// more.cat > add some.cat >> three.cat
object add {

    def main(args: Array[String]): Unit = {
        if (args.length >= 1) {
             bayes.process(args(0), if (args.length == 2) args(1) else stdin, bayes.add)
        }
    }

}