package com.github.tteofili.btl.uima.annotator;

import org.apache.uima.UIMAFramework;
import org.apache.uima.collection.CollectionProcessingEngine;
import org.apache.uima.collection.metadata.CpeDescription;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.util.InvalidXMLException;
import org.apache.uima.util.ProcessTrace;
import org.apache.uima.util.ProcessTraceEvent;
import org.apache.uima.util.XMLInputSource;
import org.junit.Test;

import java.io.IOException;

public class CrawledFilesCPETest {

  private CollectionProcessingEngine mCPE;

  @Test
  public void testCrawledFilesCPE() throws Exception {
    testCPE(getClass().getResource("/uima/CrawledFilesCPE.xml").getFile());
  }

  private void testCPE(String descriptorPath) throws InvalidXMLException, IOException,
          ResourceInitializationException, InterruptedException {

    // parse CPE descriptor
    System.out.println("Parsing CPE Descriptor");
    CpeDescription cpeDesc = UIMAFramework.getXMLParser().parseCpeDescription(
            new XMLInputSource(descriptorPath));
    // instantiate CPE
    System.out.println("Instantiating CPE");
    mCPE = UIMAFramework.produceCollectionProcessingEngine(cpeDesc);

//    TestStatusCallbackListener listener = new TestStatusCallbackListener();
//    mCPE.addStatusCallbackListener(listener);

    // Start Processing
    System.out.println("Running CPE");
    mCPE.process();

//    while (!listener.isFinished()) {
//      Thread.sleep(1000);
//    }
    ProcessTrace pt = mCPE.getPerformanceReport();
    for (ProcessTraceEvent pte : pt.getEvents()) {
      System.out.println("[" + pte.getType() + "]  " + pte.getComponentName() + " in "
              + pte.getDuration() + "ms");
    }

  }

}
