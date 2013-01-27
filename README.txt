HARK Tweet v0.3.2.1

This is the distributed version of CMU ARK Twitter Part-of-Speech Tagger v0.3.2 in Hadoop setting. HARK
stands for (Hadoop-based)ARK :)

More details about the tool is at https://github.com/brendano/ark-tweet-nlp/. Explanation on the algorithms
and related research is at http://www.ark.cs.cmu.edu/TweetNLP/. 


(Updated 28.01.2013: I am refactoring the code and will commit the stable version soon. Stay tuned guys !!)


Centralized setting (derived from original repository at https://github.com/brendano/ark-tweet-nlp):
===========

Requires Java 6.  To run the tagger from a unix shell:

    ./scripts/runTagger.sh examples/example_tweets.txt

The tagger outputs tokens, predicted part-of-speech tags, and confidences.
See:

    ./scripts/runTagger.sh --help

We also include a script that invokes just the tokenizer:

    ./scripts/twokenize.sh examples/example_tweets.txt

You may have to adjust the parameters to "java" depending on your system.


Distributed setting
==========
.. (to be updated)


Information
===========

Version 0.3 of the tagger is much faster and more accurate.  Please see the
tech report on the website for details.

For the Java API, see src/cmu/arktweetnlp; especially Tagger.java.
See also documentation in docs/ and src/cmu/arktweetnlp/package.html.

This tagger is described in the following two papers, available at the website.
Please cite these if you write a research paper using this software.

Part-of-Speech Tagging for Twitter: Annotation, Features, and Experiments
Kevin Gimpel, Nathan Schneider, Brendan O'Connor, Dipanjan Das, Daniel Mills,
  Jacob Eisenstein, Michael Heilman, Dani Yogatama, Jeffrey Flanigan, and 
  Noah A. Smith
In Proceedings of the Annual Meeting of the Association
  for Computational Linguistics, companion volume, Portland, OR, June 2011.
http://www.ark.cs.cmu.edu/TweetNLP/gimpel+etal.acl11.pdf

Part-of-Speech Tagging for Twitter: Word Clusters and Other Advances
Olutobi Owoputi, Brendan O'Connor, Chris Dyer, Kevin Gimpel, and
  Nathan Schneider.
Technical Report, Machine Learning Department. CMU-ML-12-107. September 2012.

