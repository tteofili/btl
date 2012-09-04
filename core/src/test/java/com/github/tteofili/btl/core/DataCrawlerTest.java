package com.github.tteofili.btl.core;

import org.junit.Test;

/**
 * Add javadoc here
 */
public class DataCrawlerTest {

    @Test
    public void simpleExecutionTest() throws Exception {
        DataCrawler dataCrawler = new DataCrawler();
        dataCrawler.retrieveData();
    }
}
