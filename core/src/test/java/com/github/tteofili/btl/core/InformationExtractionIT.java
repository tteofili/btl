package com.github.tteofili.btl.core;

import java.io.File;
import java.net.URL;
import java.nio.file.Paths;
import java.util.Collection;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.MatchAllDocsQuery;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.FSDirectory;
import org.apache.uima.UIMAFramework;
import org.apache.uima.analysis_engine.AnalysisEngine;
import org.apache.uima.cas.CAS;
import org.apache.uima.cas.Feature;
import org.apache.uima.cas.FeatureStructure;
import org.apache.uima.cas.text.AnnotationFS;
import org.apache.uima.resource.ResourceSpecifier;
import org.apache.uima.util.XMLInputSource;
import org.junit.Test;

import com.github.tteofili.btl.crawler.Crawler;
import com.github.tteofili.btl.crawler.Page;
import com.github.tteofili.btl.crawler.SimpleDescendingCrawler;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * IT for IE tasks
 */
public class InformationExtractionIT {
    @Test
    public void testCrawlingAndIE() throws Exception {
        Crawler crawler = new SimpleDescendingCrawler();
        Page rootPage = crawler.getPage(new URL("http://www.repubblica.it/politica"));
        assertNotNull(rootPage);
        Collection<Page> pages = crawler.getLinkedPages(rootPage);
        assertNotNull(pages);
        XMLInputSource in = new XMLInputSource(getClass().getResource("/uima/BtlAggregateAEDescriptor.xml"));
        ResourceSpecifier specifier = UIMAFramework.getXMLParser().parseResourceSpecifier(in);
        AnalysisEngine ae = UIMAFramework.produceAnalysisEngine(specifier);
        for (Page p : pages) {
            CAS cas = ae.newCAS();
            cas.setDocumentText(p.getText());
            ae.process(cas);
            for (AnnotationFS a : cas.getAnnotationIndex()) {
                if (a.getType().getName().startsWith("com.github.tteofili.btl") || a.getType().getName().startsWith("org.apache.uima.alchemy")) {
                    System.out.println("a: " + a.getType() + " -> " + a.getCoveredText());
                    for (Feature f : a.getType().getFeatures()) {
                        if ("uima.cas.AnnotationBase:sofa".equals(f.getName())) {
                            // ignore it
                        } else if (!f.getRange().isPrimitive()) {
                            FeatureStructure featureValue = a.getFeatureValue(f);
                            if (featureValue instanceof AnnotationFS) {
                                System.out.println("a: " + a.getType() + "/" + f.getName() + "-> " + ((AnnotationFS) featureValue).getCoveredText());
                            } else {
                                System.out.println("a: " + a.getType() + "/" + f.getName() + "-> " + featureValue);
                            }
                        } else {
                            System.out.println("a: " + a.getType() + "/" + f.getName() + "-> " + a.getFeatureValueAsString(f));
                        }
                    }

                }
            }
            cas.release();
        }

        FSDirectory directory = FSDirectory.open(Paths.get(getClass().getResource("/").getFile()));
        IndexReader reader = DirectoryReader.open(directory);
        IndexSearcher indexSearcher = new IndexSearcher(reader);
        try {
            TopDocs topDocs = indexSearcher.search(new MatchAllDocsQuery(), Integer.MAX_VALUE);
            assertTrue(topDocs.totalHits > 0);
            for (ScoreDoc scoreDoc : topDocs.scoreDocs) {
                Document doc = indexSearcher.doc(scoreDoc.doc);
                String author = doc.get("author");
                assertNotNull(author);
                String statement = doc.get("statement");
                assertNotNull(statement);
                System.out.println(author + ": \"" + statement + "\"");
            }
        } finally {
            reader.close();
            directory.close();
        }
    }
}
