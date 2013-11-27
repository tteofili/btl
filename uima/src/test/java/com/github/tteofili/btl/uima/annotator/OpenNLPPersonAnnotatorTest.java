package com.github.tteofili.btl.uima.annotator;

import org.apache.uima.cas.CAS;
import org.apache.uima.cas.text.AnnotationFS;
import org.apache.uima.cas.text.AnnotationIndex;
import org.apache.uima.test.junit_extension.AnnotatorTester;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Testcase for OpenNLP person name finder
 */
public class OpenNLPPersonAnnotatorTest {
    @Test
    public void testTextWithPersonName() throws Exception {
        String descFilePath = getClass().getResource("/uima-test/TestAggregateOpenNLPPersonAnnotatorAEDescriptor.xml").getFile();
        CAS cas = AnnotatorTester.performTest(descFilePath, "Ieri sera l'agenzia di rating S&P ha messo in guardia il Leone da un possibile downgrade, ma il numero uno Mario Greco ha blandito gli investitori promettendo dividendi crescenti", "it");
        assertNotNull(cas);
        AnnotationIndex<AnnotationFS> annotationIndex = cas.getAnnotationIndex(cas.getTypeSystem().getType(AnnotationUtils.OPENNLP_PERSON_ANNOTATION));
        assertNotNull(annotationIndex);
        assertEquals(1, annotationIndex.size());
        for (AnnotationFS annotationFS : annotationIndex) {
            assertEquals("Mario Greco", annotationFS.getCoveredText());
        }
    }
}
