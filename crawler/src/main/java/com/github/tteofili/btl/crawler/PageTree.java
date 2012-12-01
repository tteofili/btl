package com.github.tteofili.btl.crawler;

import java.util.Collection;

/**
 * Add javadoc here
 */
public interface PageTree {

    public Page getRoot();

    public Collection<Page> getPagesAtDepth(int depth);

    public PageTree getSubTree(Page page);
}
