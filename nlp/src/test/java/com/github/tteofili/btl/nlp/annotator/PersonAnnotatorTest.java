package com.github.tteofili.btl.nlp.annotator;

import org.apache.uima.cas.CAS;
import org.apache.uima.cas.text.AnnotationFS;
import org.apache.uima.cas.text.AnnotationIndex;
import org.apache.uima.test.junit_extension.AnnotatorTester;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Testcase for {@link PersonAnnotator}
 */
public class PersonAnnotatorTest {
    @Test
    public void testTextWithPersonName() throws Exception {
        String descFilePath = getClass().getResource("/uima-test/TestAggregatePersonAnnotatorAEDescriptor.xml").getFile();
        CAS cas = AnnotatorTester.performTest(descFilePath, "Ieri sera l'agenzia di rating S&P ha messo in guardia il Leone da un possibile downgrade, ma il numero uno Mario Greco ha blandito gli investitori promettendo dividendi crescenti", "it");
        assertNotNull(cas);
        AnnotationIndex<AnnotationFS> annotationIndex = cas.getAnnotationIndex(cas.getTypeSystem().getType(AnnotationUtils.PERSON_ANNOTATION));
        assertNotNull(annotationIndex);
        assertEquals(1, annotationIndex.size());
        for (AnnotationFS annotationFS : annotationIndex) {
            assertEquals("Mario Greco", annotationFS.getCoveredText());
        }
    }
}
