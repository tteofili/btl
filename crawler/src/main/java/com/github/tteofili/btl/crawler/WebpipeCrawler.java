package com.github.tteofili.btl.crawler;

import it.uniroma3.webpipe.crawler.executor.CrawlerExecutor;
import it.uniroma3.webpipe.crawler.io.CrawlingSpecificationReader;
import it.uniroma3.webpipe.crawler.specification.CrawlingSpecification;

import java.io.File;
import java.io.FileReader;

/**
 * A Webpipe based {@link Crawler}
 */
public class WebpipeCrawler {

  public void retrieveData(String crawlingFilePath) throws Exception {

//        WebpipeExecutor webpipeExecutor = new WebpipeExecutor();
//        WebpipeSpecification specification = new WebpipeSpecification();
    CrawlingSpecificationReader crawlingSpecificationReader = new CrawlingSpecificationReader();
    // TODO : release the reader
    FileReader reader = new FileReader(new File(crawlingFilePath));
    try {
      CrawlingSpecification crawlingSpecification = crawlingSpecificationReader.readSpecification(reader);
      CrawlerExecutor crawlerExecutor = new CrawlerExecutor(crawlingSpecification, false);
      crawlerExecutor.execute();
    } finally {
      reader.close();
    }
//        specification.setCrawlerSpecification(crawlingSpecification);

//        WrappingSpecification wrappingSpecification = new WrappingSpecification();
//        specification.setWrappingSpecification(wrappingSpecification);
//        webpipeExecutor.executeWebpipe(specification);

  }
}
