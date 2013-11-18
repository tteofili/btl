package com.github.tteofili.btl.crawler;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import java.net.URL;
import java.util.Collection;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Testcase for {@link SimpleDescendingCrawler}
 */
public class SimpleDescendingCrawlerTest {

    @Test
    public void testPageRetrieve() throws Exception {
        WebClient webClient = new WebClient(BrowserVersion.CHROME);
        webClient.getOptions().setThrowExceptionOnScriptError(false);
        webClient.getOptions().setCssEnabled(false);
        webClient.getOptions().setGeolocationEnabled(false);
        webClient.getOptions().setJavaScriptEnabled(false);
        SimpleDescendingCrawler simpleDescendingCrawler = new SimpleDescendingCrawler(webClient);
        Page page = simpleDescendingCrawler.getPage(new URL("http://www.repubblica.it/politica/la-situazione/" +
                "2011/11/28/news/arrivano_i_sottosegretari_e_il_premier_prepara_una_nuova_missione_ue-25731599"));
        assertNotNull(page);
        assertNotNull(page.getXmlText());
        assertNotNull(page.getText());
        webClient.closeAllWindows();
    }

    @Test
    public void testPageAndDescendantsRetrieve() throws Exception {
        WebClient webClient = new WebClient(BrowserVersion.CHROME);
        webClient.getOptions().setThrowExceptionOnScriptError(false);
        webClient.getOptions().setCssEnabled(false);
        webClient.getOptions().setGeolocationEnabled(false);
        webClient.getOptions().setJavaScriptEnabled(false);
        SimpleDescendingCrawler simpleDescendingCrawler = new SimpleDescendingCrawler(webClient);
        Page page = simpleDescendingCrawler.getPage(new URL("http://www.repubblica.it/politica/la-situazione"));
        assertNotNull(page);
        Collection<Page> children = simpleDescendingCrawler.getLinkedPages(page);
        assertNotNull(children);
        assertTrue(children.size() > 0);
        webClient.closeAllWindows();
    }
}
