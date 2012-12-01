package com.github.tteofili.btl.crawler;

import it.uniroma3.webpipe.crawler.executor.CrawlerExecutor;
import it.uniroma3.webpipe.crawler.io.CrawlingSpecificationReader;
import it.uniroma3.webpipe.crawler.specification.CrawlingSpecification;
import it.uniroma3.webpipe.pilot.executor.WebpipeExecutor;
import it.uniroma3.webpipe.pilot.repository.WebpipeSpecification;

import java.io.File;
import java.io.FileReader;

/**
 * Add javadoc here
 */
public class WebpipeCrawler {

    public void retrieveData() throws Exception {

//        WebpipeExecutor webpipeExecutor = new WebpipeExecutor();
//        WebpipeSpecification specification = new WebpipeSpecification();
        CrawlingSpecificationReader crawlingSpecificationReader = new CrawlingSpecificationReader();
        String crawlingFilePath = getClass().getResource("/webpipes/repubblica.it/www.repubblica.it.pubblico.xml").getFile();
        // TODO : release the reader
        FileReader reader = new FileReader(new File(crawlingFilePath));
        CrawlingSpecification crawlingSpecification = crawlingSpecificationReader.readSpecification(reader);
        CrawlerExecutor crawlerExecutor = new CrawlerExecutor(crawlingSpecification, false);
        crawlerExecutor.execute();
//        specification.setCrawlerSpecification(crawlingSpecification);

//        WrappingSpecification wrappingSpecification = new WrappingSpecification();
//        specification.setWrappingSpecification(wrappingSpecification);
//        webpipeExecutor.executeWebpipe(specification);

    }
}
