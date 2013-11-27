package com.github.tteofili.btl.crawler;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import java.net.URI;
import java.net.URL;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 */
public class SimpleDescendingCrawler implements Crawler {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private final WebClient webClient;

    public SimpleDescendingCrawler(WebClient webClient) {
        this.webClient = webClient;
    }

    public SimpleDescendingCrawler() {
        this.webClient = new WebClient(BrowserVersion.CHROME);
        webClient.getOptions().setThrowExceptionOnScriptError(false);
        webClient.getOptions().setCssEnabled(false);
        webClient.getOptions().setGeolocationEnabled(false);
        webClient.getOptions().setJavaScriptEnabled(false);
    }

    @Override
    public Page getPage(URL url) throws CrawlingException {
        Page page;
        try {
            final HtmlPage htmlPage = webClient.getPage(url);
            page = PageUtils.fromWebPage(htmlPage);
        } catch (Exception e) {
            throw new CrawlingException(e);
        }
        return page;
    }

    @Override
    public Site getSite(URL url) throws CrawlingException {
        throw new CrawlingException("not yet implemented");
    }

    @Override
    public Collection<Page> getLinkedPages(Page page) throws CrawlingException {
        Collection<Page> children = new TreeSet<Page>();
        try {
            List<String> crawlerURLs = new LinkedList<String>();
            List<HtmlAnchor> anchors = (List<HtmlAnchor>) page.getByXpath("//a");
            for (HtmlAnchor htmlAnchor : anchors) {
                String hrefAttribute = htmlAnchor.getHrefAttribute();
                if (hrefAttribute != null) {
                    // only get pages whose URL is "under" the given page
                    URI rootUrl = page.getURL();
                    if (hrefAttribute.startsWith(rootUrl.toString()) && !crawlerURLs.contains(hrefAttribute)) {
                        try {
                            HtmlPage htmlPage = webClient.getPage(hrefAttribute);
                            children.add(PageUtils.fromWebPage(htmlPage));
                            crawlerURLs.add(hrefAttribute);
                            if (log.isInfoEnabled()) {
                                log.info("page {} added", hrefAttribute);
                            }
                        } catch (Exception e) {
                            if (log.isErrorEnabled()) {
                                log.error("cannot retrieve page {}, {}", hrefAttribute, e.getMessage());
                            }
                        }
                    } else if (hrefAttribute.startsWith(rootUrl.getPath())) {
                        try {
                            String url = rootUrl.getScheme() + "://" + rootUrl.getHost() + hrefAttribute;
                            if (!crawlerURLs.contains(url)) {
                                HtmlPage htmlPage = webClient.getPage(url);
                                children.add(PageUtils.fromWebPage(htmlPage));
                                crawlerURLs.add(url);
                                if (log.isInfoEnabled()) {
                                    log.info("page {} added", url);
                                }
                            }
                        } catch (Exception e) {
                            if (log.isErrorEnabled()) {
                                log.error("cannot retrieve page {}, {}", hrefAttribute, e.getMessage());
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            throw new CrawlingException(e);
        }
        return children;
    }

}
