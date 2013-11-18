package com.github.tteofili.btl.crawler;

import org.junit.Ignore;
import org.junit.Test;

/**
 * Testcase for {@link WebpipeCrawler}
 */
public class WebpipeCrawlerTest {

  @Test
  @Ignore
  public void simpleExecutionTest() throws Exception {
    WebpipeCrawler webpipeCrawler = new WebpipeCrawler();
    String crawlingFilePath = getClass().getResource("/webpipes/repubblica.it/www.repubblica.it.pubblico.xml").getFile();
    webpipeCrawler.retrieveData(crawlingFilePath);
  }
}
