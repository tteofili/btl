package com.github.tteofili.btl.crawler;

import java.io.File;
import java.io.FileReader;
import java.net.URL;

import it.uniroma3.webpipe.crawler.io.CrawlingSpecificationReader;
import it.uniroma3.webpipe.crawler.specification.CrawlingSpecification;
import it.uniroma3.webpipe.pilot.executor.WebpipeExecutor;
import it.uniroma3.webpipe.pilot.repository.WebpipeSpecification;
import it.uniroma3.webpipe.wrapper.executor.WrappingSpecification;
import it.uniroma3.website.model.Model;
import it.uniroma3.website.model.ModelFactory;
import it.uniroma3.website.model.PageFactory;
import it.uniroma3.website.model.Website;
import it.uniroma3.website.model.internal.ModelFactoryImpl;
import it.uniroma3.website.model.internal.ModelImpl;
import it.uniroma3.website.model.internal.WebsiteImpl;
import it.uniroma3.website.pagefactory.PageFactories;

/**
 * Add javadoc here
 */
public class WebpipeCrawler {

    public void retrieveData() throws Exception {

        WebpipeExecutor webpipeExecutor = new WebpipeExecutor();
        WebpipeSpecification specification = new WebpipeSpecification();
        CrawlingSpecificationReader crawlingSpecificationReader = new CrawlingSpecificationReader();
        String crawlingFilePath = getClass().getResource("/wepipes/repubblica.it/www.repubblica.it.pubblico.xml").getFile();
        // TODO : release the reader
        FileReader reader = new FileReader(new File(crawlingFilePath));
        CrawlingSpecification crawlingSpecification = crawlingSpecificationReader.readSpecification(reader);
        specification.setCrawlerSpecification(crawlingSpecification);

//        WrappingSpecification wrappingSpecification = new WrappingSpecification();
//        specification.setWrappingSpecification(wrappingSpecification);
        webpipeExecutor.executeWebpipe(specification);

    }
}
