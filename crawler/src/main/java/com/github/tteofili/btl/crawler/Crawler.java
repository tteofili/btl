package com.github.tteofili.btl.crawler;

import java.net.URL;
import java.util.Collection;

/**
 * a {@link Crawler} is able to get entire {@link Site}s and/or single/multiple {@link Page}s from the web
 */
public interface Crawler {

    public Page getPage(URL url) throws CrawlingException;

    public Site getSite(URL url) throws CrawlingException;

    public Site getSite(URL url, int depth) throws CrawlingException;

    public Collection<Page> getChildren(Page page) throws CrawlingException;

    public PageTree getDescendants(Page page, int depth) throws CrawlingException;

}
