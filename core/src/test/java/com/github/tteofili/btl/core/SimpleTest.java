package com.github.tteofili.btl.core;

import com.github.tteofili.btl.crawler.Crawler;
import com.github.tteofili.btl.crawler.Page;
import com.github.tteofili.btl.crawler.SimpleDescendingCrawler;
import java.net.URL;
import java.util.Collection;
import org.apache.uima.UIMAFramework;
import org.apache.uima.analysis_engine.AnalysisEngine;
import org.apache.uima.cas.CAS;
import org.apache.uima.cas.text.AnnotationFS;
import org.apache.uima.resource.ResourceSpecifier;
import org.apache.uima.util.XMLInputSource;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

/**
 * Add javadoc here
 */
public class SimpleTest {
    @Test
    public void testCrawlingAndIE() throws Exception {
        Crawler crawler = new SimpleDescendingCrawler();
        Page rootPage = crawler.getPage(new URL("http://www.repubblica.it/politica/la-situazione/"));
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
                if (!a.getType().getName().startsWith("org.apache.uima") && !a.getType().getName().startsWith("uima.tcas")) {
                    System.out.println("a: " + a.getType() + " -> " + a.getCoveredText());
                }
            }
            cas.release();
        }
    }
}
