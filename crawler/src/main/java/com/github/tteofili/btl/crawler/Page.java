package com.github.tteofili.btl.crawler;

import java.net.URI;
import java.util.List;

/**
 * a page crawled from the web
 */
public interface Page {
  URI getURL();
  String getText();
  String getXmlText();
  List<?> getByXpath(String xpathString);
}
