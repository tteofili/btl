package com.github.tteofili.btl.crawler;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

import java.net.URL;
import java.util.Collection;

/**
 *
 */
public class SimpleDescendingCrawler implements Crawler {
  @Override
  public Page getPage(URL url) throws CrawlingException {
    Page page = null;
    try {
      final WebClient webClient = new WebClient(BrowserVersion.CHROME);
      final HtmlPage htmlPage = webClient.getPage(url);
      page = PageUtils.fromWebPage(htmlPage);
//      htmlPage.getTitleText();
//      final String pageAsXml = htmlPage.asXml();
//      pageAsXml.contains("<body class=\"composite\">");
//      final String pageAsText = htmlPage.asText();
//      pageAsText.contains("Support for the HTTP and HTTPS protocols");

//      webClient.closeAllWindows();
    } catch (Exception e) {
      throw new CrawlingException(e);
    }
    return page;
  }

  @Override
  public Site getSite(URL url) throws CrawlingException {
    return null;
  }

  @Override
  public Site getSite(URL url, int depth) throws CrawlingException {
    return null;
  }

  @Override
  public Collection<Page> getChildren(Page page) throws CrawlingException {
    return null;
  }

  @Override
  public PageTree getDescendants(Page page, int depth) throws CrawlingException {
    return null;
  }
}
