package com.nullplague.bayes

class Category(val name: String, val counts: bayes.Scores) {
	val total_words = counts.foldLeft(bayes.zero) { (total, pr) => total + pr._2 }
	val total = counts(bayes.ktotal).toFloat
	
	def apply(word: String) = counts(word)
    def p(word: String) = { val rt = counts.getOrElse(word, bayes.zero) / total
	    //println(word + " " + rt)
	    rt}
}