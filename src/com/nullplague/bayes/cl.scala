package com.nullplague.bayes

import scala.io.Source
import scala.io.StdIn.readLine

object cl {

    def categorize(url: String) = {
        val html = Source.fromURL("http://seattle.craigslist.org" + url)
        val s = html.mkString
        
        val pattern = """<[^>]*>""".r
        val dehtml = pattern.replaceAllIn(s, " ")
        println(dehtml)
        //val inmap = bayes.normalize(bayes.count(dehtml))
        val inmap = bayes.count(dehtml)

        print("Category: ")
        val cat = readLine()
        
        if (!new java.io.File(cat).exists)
        {
            val newfile = new java.io.FileOutputStream(cat)
            newfile.close()
        }

        val catmap = inout.read(scala.io.Source.fromFile(cat))
        val addedmap = bayes.add(inmap, catmap)
        
        val out = new java.io.FileOutputStream(cat)
        var out_stream = new java.io.PrintStream(out)
        
        addedmap.foreach(m => out_stream.println(m._1 + "," + m._2))
        out_stream.close
        out.close

        val history = new java.io.RandomAccessFile(".history", "rw")
        history.seek(history.length)
        history.write((url + "\n").getBytes)
    }
    
    def main(args: Array[String]): Unit = {
        val html = Source.fromURL("http://seattle.craigslist.org/est/sof")
        if (!new java.io.File(".history").exists)
        {
            val newfile = new java.io.FileOutputStream(".history")
            newfile.close()
        }
        val history = inout.readSet(scala.io.Source.fromFile(".history"))
        println(history)
        val s = html.mkString
        
        val pattern = """<a\s+href="(/est/sof/[^"]*)""".r
        val urls = pattern.findAllIn(s).matchData.map(m => m.group(1))
        var unique = Set[String]() ++ urls
        unique = unique -- history
        unique.foreach(categorize(_))
    }

}