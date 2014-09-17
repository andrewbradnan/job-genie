package com.nullplague.bayes
import scala.io.StdIn.readLine

object find {
	def link(url: String) = <p><a href={cl.root + url}>{cl.root + url}</a></p>
    def report(found: Set[String]) = <html>
    	<body>
    		{found.map(url => link(url)) }
    	</body>
</html>

    def main(args: Array[String]): Unit = {
        cl.makeExist(".ranks")
        val history = inout.readSet(scala.io.Source.fromFile(".ranks"))
        val jobs = cl.jobs(history)
        val found = jobs.filter(j => {
        	val txt = cl.html(j)
   	        val inmap = bayes.normalize(bayes.count(txt))
        	val ranked_as = rank.rank(inmap)._2
   	        println("Ranking " + j + " " + ranked_as)
        	ranked_as == args(0)
        })
        println(report(found))
    }
}