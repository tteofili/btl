package com.github.tteofili.btl.crawler;

import java.net.URL;
import java.util.Collection;

/**
 * a {@link Crawler} is able to get entire {@link Site}s and/or single/multiple {@link Page}s from the web
 */
public interface Crawler {

    /**
     * get a {@link Page} given its {@link URL}
     *
     * @param url the page URL
     * @return a {@link Page} located at the given {@link URL}
     * @throws CrawlingException
     */
    public Page getPage(URL url) throws CrawlingException;

    public Site getSite(URL url) throws CrawlingException;

    /**
     * get all the {@link Page}s linked by the given root {@link Page}
     *
     * @param page the starting page to get linked {@link Page}s from
     * @return a {@link Collection} of linked {@link Page}s
     * @throws CrawlingException
     */
    public Collection<Page> getLinkedPages(Page page) throws CrawlingException;
}
