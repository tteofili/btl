package com.github.tteofili.btl.nlp.classification;

import org.apache.lucene.analysis.MockAnalyzer;
import org.apache.lucene.util.BytesRef;
import org.junit.Test;

/**
 * Testcase for {@link PositionNBClassifier}
 */
public class PositionNBClassifierTest extends ClassificationTestBase<BytesRef> {

    @Test
    public void testBasicUsage() throws Exception {
        PositionNBClassifier positionNBClassifier = new PositionNBClassifier();
//        AtomicReader atomicReader = null;
//        String textFieldName = null;
//        String classFieldName = null;
//        Analyzer analyzer = null;
//        positionNBClassifier.train(atomicReader, textFieldName, classFieldName, analyzer);
        checkCorrectClassification(positionNBClassifier, "thomas says: \"I don't care\"", new BytesRef("thomas"), new MockAnalyzer(random()), textFieldName, categoryFieldName);
    }
}
