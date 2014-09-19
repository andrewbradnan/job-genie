package com.nullplague.bayes

import scala.io.Source
import scala.io.StdIn.readLine

object cl {
	val root = "http://seattle.craigslist.org"
	val jobs = "/est/sof"
	val h = ".history"
    def html(url: String) = {
        val html = Source.fromURL(root + url)
        val s = html.mkString
        
        val pattern = """<[^>]*>""".r
        val dehtml = pattern.replaceAllIn(s, " ")
        """\n\s*\n""".r.replaceAllIn(dehtml, "\n")	// no blank lines
    }
    def categorize(url: String) = {
        val txt = html(url)
        println(txt)
        //val inmap = bayes.normalize(bayes.count(txt))
        val inmap = bayes.count(txt)

        val r = rank.rank(inmap)
        r match {
            case Some(x) => println(x) 
        	case _ =>
        }
        
        print("Category: ")
        val cat = readLine()
        
        makeExist(cat)
        val catmap = inout.read(scala.io.Source.fromFile(cat))
        val addedmap = bayes.add(inmap, catmap)
        
        val out = new java.io.FileOutputStream(cat)
        var out_stream = new java.io.PrintStream(out)
        
        addedmap.foreach(m => out_stream.println(m._1 + "," + m._2))
        out_stream.close
        out.close

        val history = new java.io.RandomAccessFile(h, "rw")
        history.seek(history.length)
        history.write((url + "\n").getBytes)
    }

    def jobs(history: Set[String]) : Set[String] = {
        val html = Source.fromURL(root + jobs)
        val s = html.mkString
        
        val pattern = ("<a\\s+href=\"(" + jobs + "[^\"]*)").r
        val urls = pattern.findAllIn(s).matchData.map(m => m.group(1))
        var unique = Set[String]() ++ urls
        unique -- history
    }
    def makeExist(file: String) : Unit = {
        if (!new java.io.File(file).exists)
        {
            val newfile = new java.io.FileOutputStream(file)
            newfile.close()
        }
    }
    def main(args: Array[String]): Unit = {
        makeExist(h)
        val history = inout.readSet(scala.io.Source.fromFile(h))

        val urls = jobs(history)
        urls.foreach(categorize(_))
    }

}