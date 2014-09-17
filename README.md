This project will sort all your job listings for you.  Saving you from  reading the same things over and over.  It uses a bayesian network to learn job classifications and then will sort new jobs once it has generated a network.  It only needs 4-5 job listings to get a good read on a category.

Installation: all you need is a working scala installation.  On Mac from the console...

# this will install java if you don't have it
java 
# this will install brew (package manager)
ruby -e "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/master/install)"
# install scala
brew install scala

# Build
cd src/com/nullplague/bayes
./c.sh
# learn mode.  Enter [some.cat] for each job listing.  The network will become more accurate the more examples it see's.  Make as many categories as you see fit.  For dev jobs I have good.cat, test.cat, pm.cat, sql.cat, sec.cat, etc.  Just hit enter to end.
scala -classpath . com.nullplague.bayes.cl

#to find jobs run this
scala -classpath . com.nullplague.bayes.find good.cat
