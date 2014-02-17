package com.github.tteofili.btl.nlp.annotator;

import org.apache.uima.cas.CAS;
import org.apache.uima.cas.text.AnnotationFS;
import org.apache.uima.cas.text.AnnotationIndex;
import org.apache.uima.test.junit_extension.AnnotatorTester;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Testcase for {@link NameAnnotator}
 */
public class NameAnnotatorTest {
    @Test
    public void testTextWithName() throws Exception {
        String descFilePath = getClass().getResource("/uima-test/TestAggregateNameAnnotatorAEDescriptor.xml").getFile();
        CAS cas = AnnotatorTester.performTest(descFilePath, "the new major Todd declared : \"I am not corrupted!\"", "en");
        assertNotNull(cas);
        AnnotationIndex<AnnotationFS> annotationIndex = cas.getAnnotationIndex(cas.getTypeSystem().getType(AnnotationUtils.NAME_ANNOTATION));
        assertNotNull(annotationIndex);
        assertEquals(1, annotationIndex.size());
    }
}
