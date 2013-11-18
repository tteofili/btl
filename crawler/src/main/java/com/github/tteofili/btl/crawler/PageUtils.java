package com.github.tteofili.btl.crawler;


import com.gargoylesoftware.htmlunit.html.HtmlPage;
import java.net.URI;
import java.util.List;

/**
 *
 */
public class PageUtils {

    public static Page fromWebPage(final HtmlPage htmlPage) {
        return new Page() {

            @Override
            public URI getURL() {
                return URI.create(htmlPage.getUrl().toString());
            }

            @Override
            public String getText() {
                return htmlPage.asText();
            }

            @Override
            public String getXmlText() {
                return htmlPage.asXml();
            }

            @Override
            public List<?> getByXpath(String xpathString) {
                return htmlPage.getByXPath(xpathString);
            }
        };
    }
}
