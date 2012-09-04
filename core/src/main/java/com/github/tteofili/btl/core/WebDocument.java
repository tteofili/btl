package com.github.tteofili.btl.core;

import java.util.Collection;

/**
 * A document crawled from the web
 */
public interface WebDocument {

    public Collection<TextFragment> getTextFragments();

}
