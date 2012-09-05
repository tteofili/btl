package com.github.tteofili.btl.crawler;

import org.junit.Test;

/**
 * Add javadoc here
 */
public class WebpipeCrawlerTest {

    @Test
    public void simpleExecutionTest() throws Exception {
        WebpipeCrawler webpipeCrawler = new WebpipeCrawler();
        webpipeCrawler.retrieveData();
    }
}
