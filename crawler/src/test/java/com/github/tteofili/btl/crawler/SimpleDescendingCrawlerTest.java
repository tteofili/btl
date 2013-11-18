package com.github.tteofili.btl.crawler;

import org.junit.Test;

import java.net.URL;

import static org.junit.Assert.assertNotNull;

/**
 *
 */
public class SimpleDescendingCrawlerTest {

  @Test
  public void testCrawling() throws Exception {
    SimpleDescendingCrawler simpleDescendingCrawler = new SimpleDescendingCrawler();
    Page page = simpleDescendingCrawler.getPage(new URL("http://www.repubblica.it/politica"));
    assertNotNull(page);
    assertNotNull(page.getXmlText());
    assertNotNull(page.getText());
//    System.err.println(page.getText());
  }
}
