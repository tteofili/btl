package com.github.tteofili.btl.crawler;

import java.net.URI;

/**
 * a page crawled from the web
 */
public interface Page {
  URI getURI();
  String getText();
  String getXmlText();
}
