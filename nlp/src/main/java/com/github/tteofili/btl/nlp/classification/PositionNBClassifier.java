package com.github.tteofili.btl.nlp.classification;

import java.io.IOException;
import java.util.List;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.classification.ClassificationResult;
import org.apache.lucene.classification.Classifier;
import org.apache.lucene.index.LeafReader;
import org.apache.lucene.search.Query;
import org.apache.lucene.util.BytesRef;

/**
 * A positions based naive bayes classifier
 */
public class PositionNBClassifier implements Classifier<BytesRef> {
    public ClassificationResult<BytesRef> assignClass(String s) throws IOException {
        return null;
    }

    public List<ClassificationResult<BytesRef>> getClasses(String s) throws IOException {
        return null;
    }

    public List<ClassificationResult<BytesRef>> getClasses(String s, int i) throws IOException {
        return null;
    }

    public void train(LeafReader leafReader, String s, String s1, Analyzer analyzer) throws IOException {

    }

    public void train(LeafReader leafReader, String s, String s1, Analyzer analyzer, Query query) throws IOException {

    }

    public void train(LeafReader leafReader, String[] strings, String s, Analyzer analyzer, Query query) throws IOException {

    }
//    private AtomicReader atomicReader;
//    private String textFieldName;
//    private String classFieldName;
//    private int docsWithClassSize;
//    private Analyzer analyzer;
//    private IndexSearcher indexSearcher;
//    private Query query;
//
//    /**
//     * {@inheritDoc}
//     */
//    @Override
//    public void train(AtomicReader atomicReader, String textFieldName, String classFieldName, Analyzer analyzer, Query query)
//            throws IOException {
//        this.atomicReader = atomicReader;
//        this.indexSearcher = new IndexSearcher(this.atomicReader);
//        this.textFieldName = textFieldName;
//        this.classFieldName = classFieldName;
//        this.analyzer = analyzer;
//        this.docsWithClassSize = countDocsWithClass();
//        this.query = query;
//    }
//
//    @Override
//    public void train(AtomicReader atomicReader, String[] textFieldNames, String classFieldName, Analyzer analyzer, Query query) throws IOException {
//        train(atomicReader, textFieldNames[0], classFieldName, analyzer, query);
//    }
//
//    @Override
//    public void train(AtomicReader atomicReader, String textFieldName, String classFieldName, Analyzer analyzer) throws IOException {
//        train(atomicReader, textFieldName, classFieldName, analyzer, null);
//    }
//
//    private int countDocsWithClass() throws IOException {
//        int docCount = MultiFields.getTerms(this.atomicReader, this.classFieldName).getDocCount();
//        if (docCount == -1) { // in case codec doesn't support getDocCount
//            TotalHitCountCollector totalHitCountCollector = new TotalHitCountCollector();
//            Query q;
//            if (query != null) {
//                BooleanQuery bq = new BooleanQuery();
//                WildcardQuery wq = new WildcardQuery(new Term(classFieldName, String.valueOf(WildcardQuery.WILDCARD_STRING)));
//                bq.add(wq, BooleanClause.Occur.MUST);
//                bq.add(query, BooleanClause.Occur.MUST);
//                q = bq;
//            } else {
//                q = new WildcardQuery(new Term(classFieldName, String.valueOf(WildcardQuery.WILDCARD_STRING)));
//            }
//            indexSearcher.search(q,
//                    totalHitCountCollector);
//            docCount = totalHitCountCollector.getTotalHits();
//        }
//        return docCount;
//    }
//
//    private Map<String, int[]> tokenizeDoc(String doc) throws IOException {
//        Map<String, int[]> result = new HashMap<String, int[]>();
//        TokenStream tokenStream = analyzer.tokenStream(textFieldName, doc);
//        try {
//            PositionIncrementAttribute positionIncrementAttribute = tokenStream.addAttribute(PositionIncrementAttribute.class);
//            CharTermAttribute charTermAttribute = tokenStream.addAttribute(CharTermAttribute.class);
//            OffsetAttribute offsetAttribute = tokenStream.addAttribute(OffsetAttribute.class);
//            tokenStream.reset();
//            while (tokenStream.incrementToken()) {
//                int increment = positionIncrementAttribute.getPositionIncrement();
//                int[] res = {offsetAttribute.startOffset(), offsetAttribute.endOffset()};
//                String text = charTermAttribute.toString();
//                result.put(text, res);
//                System.err.println(text + " has positions " + res[0] + ", " + res[1] + " (increment : " + increment + ")");
//            }
//            tokenStream.end();
//        } finally {
//            IOUtils.closeWhileHandlingException(tokenStream);
//        }
//        return result;
//    }
//
//    /**
//     * {@inheritDoc}
//     */
//    @Override
//    public ClassificationResult<BytesRef> assignClass(String inputDocument) throws IOException {
//        if (atomicReader == null) {
//            throw new IOException("You must first call Classifier#train");
//        }
//        double max = 0d;
//        BytesRef foundClass = null;
//
////        Terms terms = MultiFields.getTerms(atomicReader, classFieldName);
////        TermsEnum termsEnum = terms.iterator(null);
////        BytesRef next;
//        Map<String, int[]> tokenizedDocWithPos = tokenizeDoc(inputDocument);
//
//        // possible classes are the given text tokens
//
//        for (Map.Entry<String, int[]> entry : tokenizedDocWithPos.entrySet()) {
//            double clVal = calculatePrior(entry) * calculateLikelihood(tokenizedDocWithPos, entry);
//            if (clVal > max) {
//                max = clVal;
//                foundClass = new BytesRef(inputDocument.substring(entry.getValue()[0], entry.getValue()[1]));
//            }
//        }
//        return new ClassificationResult<BytesRef>(foundClass, max);
//    }
//
//
//    private double calculateLikelihood(Map<String, int[]> tokenizedDoc, Map.Entry<String, int[]> c) throws IOException {
//        // for each word
//        double result = 1d;
//        for (Map.Entry<String, int[]> word: tokenizedDoc.entrySet()) {
//            // search with text:word AND class:c
//            int hits = 0;//getWordFreqForClass(word, c);
//
//            // num : count the no of times the word appears in documents of class c (+1)
//            double num = hits + 1; // +1 is added because of add 1 smoothing
//
//            // den : for the whole dictionary, count the no of times a word appears in documents of class c (+|V|)
//            double den = 0;//getTextTermFreqForClass(c) + docsWithClassSize;
//
//            // P(w|c) = num/den
//            double wordProbability = num / den;
//            result *= wordProbability;
//        }
//
//        // P(d|c) = P(w1|c)*...*P(wn|c)
//        return result;
//    }
//
//    private double getTextTermFreqForClass(BytesRef c) throws IOException {
//        Terms terms = MultiFields.getTerms(atomicReader, textFieldName);
//        long numPostings = terms.getSumDocFreq(); // number of term/doc pairs
//        double avgNumberOfUniqueTerms = numPostings / (double) terms.getDocCount(); // avg # of unique terms per doc
//        int docsWithC = atomicReader.docFreq(new Term(classFieldName, c));
//        return avgNumberOfUniqueTerms * docsWithC; // avg # of unique terms in text field per doc * # docs with c
//    }
//
//    private int getWordFreqForClass(String word, BytesRef c) throws IOException {
//        BooleanQuery booleanQuery = new BooleanQuery();
//        booleanQuery.add(new BooleanClause(new TermQuery(new Term(textFieldName, word)), BooleanClause.Occur.MUST));
//        booleanQuery.add(new BooleanClause(new TermQuery(new Term(classFieldName, c)), BooleanClause.Occur.MUST));
//        if (query != null) {
//            booleanQuery.add(query, BooleanClause.Occur.MUST);
//        }
//        TotalHitCountCollector totalHitCountCollector = new TotalHitCountCollector();
//        indexSearcher.search(booleanQuery, totalHitCountCollector);
//        return totalHitCountCollector.getTotalHits();
//    }
//
//    private double calculatePrior(Map.Entry<String, int[]> entry) throws IOException {
//        return (double) docCount(entry) / docsWithClassSize;
//    }
//
//    private int docCount(Map.Entry<String, int[]> countedClass) throws IOException {
//        // find all the docs having that token in that relative position
//        return atomicReader.docFreq(new Term(textFieldName, countedClass.getKey()));
//
////        return indexSearcher.search(new Span());
//        return 0;
//    }
}
