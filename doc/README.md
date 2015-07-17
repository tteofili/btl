btl
===

data lifecycle overview
=======================
* raw data gets crawled from web sources
* each raw page is stored as it is (by URL)
* each page gets sanitized (web scraping) and put in separate storage for clean text data
* each page gets analyzed by the nlp pipeline to extract relevant metadata
* enriched page gets stored in the final storage to be used by the webapp

declaration extraction process
==============================
phase1a
- crawl data from web (repubblica / twitter)
- apply existing model for identification of authors
- apply existing model or simple rules for identification of statements
- apply raw algorithms for declarations detections
- each declaration has author, statement, referenced sentence and page URL
- each declaration is stored in a lucene index
- system exposes indexed information
phase1b (optional)
- system highlights items that still need to be evaluated
- evaluating an item means checking the declaration (author, sentence) in its sentence and may require visiting the page URL
- user selects if author identification was successful
- underlying algorithm for author detection is eventually updated
- user eventually selects the right author
- underlying algorithm for author detection is eventually updated
- user selects if sentence identification was successful
- underlying algorithm for sentence detection is eventually updated
- user eventually selects the right statement
- underlying algorithm for sentence detection is eventually updated
- good declarations are then persisted to the clean index
phase2
- system shows just good declarations to external users

statements
==========

sentences in the form : 'Bruno Bianchi ha dichiarato "non abdicherò di fronte all'ennesimo scandalo"'
or '"sono tutti dei gran minchioni" ha dichiarato Pippo Pappi' have to be tagged as
statements (with an author).

negations
=========
find sentences or declarations that (partially) result in contradictions with or negate a previously defined statement.
- phase1 group statements that either have same concepts or same words or both
-- options:
--- 1. cluster statements: k-means with words (term vectors)
--- 2. concept tag statements and cluster by tag
--- 3. parse tree for each statement and cluster by object


approach for author detection
=============================

algorithms need to find the author in the sentence, so it's a NER task.

options
- maximum entropy
- naive bayes with the positions in the source text

local positions naive bayes
===========================
- prior * likelihood
- candidate classes = all the existing tokens in the text
-- stemming tokens would reduce fragmentation of the distribution
- for all the candidate classes
-- prior = no. of docs whose assigned class is within a certain range from the given normalized position / no. of docs with a class
-- likelihood = TBD





