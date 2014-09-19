package com.nullplague.bayes

import scala.io.BufferedSource
import scala.collection.mutable.Map

object bayes {
	type Scores = Map[String, Int]
    def Scores() = Map[String, Int]()
    val zero = 0
    val one = 1
    val ktotal = "__total"
    type DB = Map[String, Category]	// "html.cat" -> ("html" -> 4, "fullstack" -> 5, ...)
    def DB() = Map[String, Category]()
	type Probabilities = Map[String, Float]
	
    def p(in: Scores, cat: Category, db: DB) : Float = { // pcat * pwordcat[1..N]
	    pcat(db, cat.name) * pwords(in, cat)
	}
    def p(in: Scores, db: DB) : Probabilities = { // pcat * pwordcat[1..N]
	    db.map{ case (cat, counts) => cat -> pcat(db, cat) * pwords(in, db(cat)) }
	}
	def pwords(in: Scores, cat: Category) = {
	    in.foldLeft(zero) { (count, pr) => 
	        val word = pr._1
	        count * cat.p(word) }
	}
    // category probability overall
    def pcat(db: DB, cat: String) : Float = db(cat)(ktotal) / sum(db, ktotal)
    def sum(db: DB, word: String) : Float = {
        db.foldLeft(zero) { (count, pr) => 
            val cat = pr._2
            count + cat(ktotal) }
    }
    // add two maps together
    def add(a: Scores, b: Scores) : Scores = {
        val rt = Scores()
        rt ++ a
        for((word, count) <- a) rt(word) = count + b.getOrElse(word, zero)
       	for((word, count) <- b) rt(word) = a.getOrElse(word,b(word))
       	rt
    }
    var counts = Scores()
    val stop_words = Set("a", "about", "above", "above", "across", "after", "afterwards", "again", "against", "all", "almost", "alone", "along", "already", "also","although","always","am","among", "amongst", "amoungst", "amount",  "an", "and", "another", "any","anyhow","anyone","anything","anyway", "anywhere", "are", "around", "as",  "at", "back","be","became", "because","become","becomes", "becoming", "been", "before", "beforehand", "behind", "being", "below", "beside", "besides", "between", "beyond", "bill", "both", "bottom","but", "by", "call", "can", "cannot", "cant", "co", "con", "could", "couldnt", "cry", "de", "describe", "detail", "do", "done", "down", "due", "during", "each", "eg", "eight", "either", "eleven","else", "elsewhere", "empty", "enough", "etc", "even", "ever", "every", "everyone", "everything", "everywhere", "except", "few", "fifteen", "fify", "fill", "find", "fire", "first", "five", "for", "former", "formerly", "forty", "found", "four", "from", "front", "full", "further", "get", "give", "go", "had", "has", "hasnt", "have", "he", "hence", "her", "here", "hereafter", "hereby", "herein", "hereupon", "hers", "herself", "him", "himself", "his", "how", "however", "hundred", "ie", "if", "in", "inc", "indeed", "interest", "into", "is", "it", "its", "itself", "keep", "last", "latter", "latterly", "least", "less", "ltd", "made", "many", "may", "me", "meanwhile", "might", "mill", "mine", "more", "moreover", "most", "mostly", "move", "much", "must", "my", "myself", "name", "namely", "neither", "never", "nevertheless", "next", "nine", "no", "nobody", "none", "noone", "nor", "not", "nothing", "now", "nowhere", "of", "off", "often", "on", "once", "one", "only", "onto", "or", "other", "others", "otherwise", "our", "ours", "ourselves", "out", "over", "own","part", "per", "perhaps", "please", "put", "rather", "re", "same", "see", "seem", "seemed", "seeming", "seems", "serious", "several", "she", "should", "show", "side", "since", "sincere", "six", "sixty", "so", "some", "somehow", "someone", "something", "sometime", "sometimes", "somewhere", "still", "such", "system", "take", "ten", "than", "that", "the", "their", "them", "themselves", "then", "thence", "there", "thereafter", "thereby", "therefore", "therein", "thereupon", "these", "they", "thickv", "thin", "third", "this", "those", "though", "three", "through", "throughout", "thru", "thus", "to", "together", "too", "top", "toward", "towards", "twelve", "twenty", "two", "un", "under", "until", "up", "upon", "us", "very", "via", "was", "we", "well", "were", "what", "whatever", "when", "whence", "whenever", "where", "whereafter", "whereas", "whereby", "wherein", "whereupon", "wherever", "whether", "which", "while", "whither", "who", "whoever", "whole", "whom", "whose", "why", "will", "with", "within", "without", "would", "yet", "you", "your", "yours", "yourself", "yourselves", "the")
    // count the words in a list of strings
    def countStrings(strs: List[String]) = {
        strs.filter(word => !stop_words.exists(word == _)).
		foldLeft(counts) {
			(count, word) => count + (word -> (count.getOrElse(word, zero) + one)) }
    }
    def count(lines: List[String]) : Scores = {
		Scores() ++ countStrings(lines.flatMap(_.split("\\W+")))
    }
    def count(lines: String) : Scores = {
		Scores() ++ countStrings(lines.split("\\W+").toList)
    }
    def normalize(in: Scores) : Map[String, Float] = {
		val total = in.foldLeft(zero) {(total,pr) => total + pr._2 }
		val avg = total / in.size
		in.map(pr => (pr._1, pr._2 / avg.toFloat))
    }
    // multiply a * b
    def mul(a: Scores, b: Scores) : Float = {
        val m = Scores()
   		for((word, count) <- a) m(word) = count * b.getOrElse(word, zero)
   		m.foldLeft(zero) { (total, pair) => total + pair._2 }
    }
    // open a BufferedSource from stdin or filename
    def open(io: Any) : BufferedSource = { 
        io match {
            case x : String =>  scala.io.Source.fromFile(x)
            case b: scala.io.BufferedSource => b
        }
    }
    def process[IN1,IN2](a: IN1, b: IN2, fn: (Scores, Scores) => Unit) = {
        val x = open(a)
        val y = open(b)
        fn(inout.read(x), inout.read(y))
    }

}