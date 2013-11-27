package com.github.tteofili.btl.uima.annotator;

import org.apache.uima.cas.CAS;
import org.apache.uima.cas.FeatureStructure;
import org.apache.uima.cas.Type;
import org.apache.uima.cas.text.AnnotationFS;
import org.apache.uima.cas.text.AnnotationIndex;
import org.apache.uima.test.junit_extension.AnnotatorTester;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Testcase for {@link DeclarationAnnotator}
 */
public class DeclarationAnnotatorTest {
    @Test
    public void testTextWithStatementOnSingleSentence() throws Exception {
        String descFilePath = getClass().getResource("/uima/BtlAggregateAEDescriptor.xml").getFile();
        CAS cas = AnnotatorTester.performTest(descFilePath, "Francesco Storace , intervistato qualche settimana prima, " +
                "dichiarava idignato: \"Non sono corrotto!\" e chiunque dica il contrario è in malafede", "it");
        assertNotNull(cas);
        Type declarationType = cas.getTypeSystem().getType(AnnotationUtils.DECLARATION_ANNOTATION);
        AnnotationIndex<AnnotationFS> annotationIndex = cas.getAnnotationIndex(declarationType);
        assertNotNull(annotationIndex);
        assertEquals(1, annotationIndex.size());
        for (AnnotationFS annotationFS : annotationIndex) {
            assertEquals("Non sono corrotto!", annotationFS.getCoveredText());
            FeatureStructure author = annotationFS.getFeatureValue(declarationType.getFeatureByBaseName("author"));
            assertNotNull(author);
            assertEquals("Francesco Storace", ((AnnotationFS) author).getCoveredText());
        }
    }

    @Test
    public void testTextWithStatementOnItalianSentence() throws Exception {
        String descFilePath = getClass().getResource("/uima/BtlAggregateAEDescriptor.xml").getFile();
        CAS cas = AnnotatorTester.performTest(descFilePath, "la 'ex' M5S Paola De Pin: \"Io no venduta\" .", "it");
        assertNotNull(cas);
        Type declarationType = cas.getTypeSystem().getType(AnnotationUtils.DECLARATION_ANNOTATION);
        AnnotationIndex<AnnotationFS> annotationIndex = cas.getAnnotationIndex(declarationType);
        assertNotNull(annotationIndex);
        assertEquals(1, annotationIndex.size());
        for (AnnotationFS annotationFS : annotationIndex) {
            assertEquals("Io no venduta", annotationFS.getCoveredText());
            FeatureStructure author = annotationFS.getFeatureValue(declarationType.getFeatureByBaseName("author"));
            assertNotNull(author);
            assertEquals("M5S Paola De Pin", ((AnnotationFS) author).getCoveredText());
        }
    }


//    @Test
//    public void testTextWithStatementOnMultipleSentences() throws Exception {
//        String descFilePath = getClass().getResource("/uima/BtlAggregateAEDescriptor.xml").getFile();
//        CAS cas = AnnotatorTester.performTest(descFilePath, "Ma adesso, a perorare la causa del boss di Corleone, il cui avvocato si è visto respingere il ricorso presentato a Strasburgo, è addirittura l'Unione delle Camere Penali, per la quale è \"inaccettabile\" che Provenzano, \"ormai ridotto ad uno stato quasi vegetativo\", sia ancora al 41 bis. L'associazione dei penalisti chiede l'intervento \"immediato\" dei magistrati competenti, del Dap, e del Ministro della Giustizia, \"se veramente si vuole dimostrare di aver voltato pagina rispetto ai diritti dei detenuti, specialmente quelli in condizioni di salute estreme: senza distinzioni, senza discriminazioni, senza privilegi\".", "it");
//        assertNotNull(cas);
//        AnnotationIndex<AnnotationFS> annotationIndex = cas.getAnnotationIndex(cas.getTypeSystem().getType(AnnotationUtils.DECLARATION_ANNOTATION));
//        assertNotNull(annotationIndex);
//        assertEquals(4, annotationIndex.size());
//        for (AnnotationFS annotationFS : annotationIndex) {
//            assertNotNull(annotationFS.getCoveredText());
//        }
//    }
}
