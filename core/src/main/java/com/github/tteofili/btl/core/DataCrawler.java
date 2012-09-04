package com.github.tteofili.btl.core;

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
public class DataCrawler {

    public void retrieveData() throws Exception {

        WebpipeExecutor webpipeExecutor = new WebpipeExecutor();
        WebpipeSpecification specification = new WebpipeSpecification();
        CrawlingSpecification crawlingSpecification = new CrawlingSpecification();
        PageFactory pageFactory = PageFactories.newRepositoryPageFactory();
        ModelFactory modelFactory = new ModelFactoryImpl();
        Website website = new WebsiteImpl(modelFactory, pageFactory);
        Model model = new ModelImpl(website);
        crawlingSpecification.setModel(model);
        specification.setCrawlerSpecification(crawlingSpecification);
        WrappingSpecification wrappingSpecification = new WrappingSpecification();
        specification.setWrappingSpecification(wrappingSpecification);
        webpipeExecutor.executeWebpipe(specification);

    }
}
