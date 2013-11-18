package com.github.tteofili.btl.crawler;


import com.gargoylesoftware.htmlunit.html.HtmlPage;

import java.net.URI;

/**
 *
 */
public class PageUtils {

  public static Page fromWebPage(final HtmlPage htmlPage) {
    return new Page() {

      @Override
      public URI getURI() {
        return URI.create(htmlPage.getDocumentURI());
      }

      @Override
      public String getText() {
        return htmlPage.asText();
      }

      @Override
      public String getXmlText() {
        return htmlPage.asXml();
      }
    };
  }
}
