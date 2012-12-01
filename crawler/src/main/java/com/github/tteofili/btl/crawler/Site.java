package com.github.tteofili.btl.crawler;

import java.net.URL;

/**
 * Add javadoc here
 */
public interface Site extends PageTree {

    public URL getSiteURL();

    public Version getVersion();

}
