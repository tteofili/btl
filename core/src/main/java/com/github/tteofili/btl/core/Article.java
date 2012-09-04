package com.github.tteofili.btl.core;

import java.util.Date;

/**
 * A web article
 */
public interface Article extends WebDocument {

    public String getAuthor();

    public Date getPublishDate();

}
