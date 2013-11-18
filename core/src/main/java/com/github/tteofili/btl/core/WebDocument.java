package com.github.tteofili.btl.core;

import java.util.SortedSet;

/**
 * A document crawled from the web
 */
public interface WebDocument {

    public SortedSet<TextFragment> getTextFragments();

}
