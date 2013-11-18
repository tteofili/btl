package com.github.tteofili.btl.crawler;

/**
 * Add javadoc here
 */
@SuppressWarnings("serial")
public class CrawlingException extends Exception {

  public CrawlingException(String msg) {
    super(msg);
  }

  public CrawlingException(Exception e) {
    super(e);
  }
}
