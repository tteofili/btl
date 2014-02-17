package com.github.tteofili.btl.nlp.annotator;

import org.apache.uima.cas.CAS;
import org.apache.uima.cas.text.AnnotationFS;
import org.apache.uima.cas.text.AnnotationIndex;
import org.apache.uima.test.junit_extension.AnnotatorTester;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Testcase for {@link com.github.tteofili.btl.nlp.annotator.StatementAnnotator}
 */
public class StatementAnnotatorTest {
    @Test
    public void testTextWithStatementOnSingleSentence() throws Exception {
        String descFilePath = getClass().getResource("/uima-test/TestAggregateStatementAnnotatorAEDescriptor.xml").getFile();
        CAS cas = AnnotatorTester.performTest(descFilePath, "the new major Todd declared : \"I am not corrupted!\" but people is not convinced for some reason", "en");
        assertNotNull(cas);
        AnnotationIndex<AnnotationFS> annotationIndex = cas.getAnnotationIndex(cas.getTypeSystem().getType(AnnotationUtils.STATEMENT_ANNOTATION));
        assertNotNull(annotationIndex);
        assertEquals(1, annotationIndex.size());
        for (AnnotationFS annotationFS : annotationIndex) {
            assertEquals("I am not corrupted!", annotationFS.getCoveredText());
        }
    }

    @Test
    public void testTextWithStatementOnItalianSentence() throws Exception {
        String descFilePath = getClass().getResource("/uima-test/TestAggregateStatementAnnotatorAEDescriptor.xml").getFile();
        CAS cas = AnnotatorTester.performTest(descFilePath, "\"Ecco i vincitori\", già noti un mese prima\n" +
                "La mia vita prigioniera\n" +
                "in fuga dall'amore violento\n" +
                "Il potere del Qatar ha il volto di Mozah\n" +
                "Kate Winslet\n" +
                "felice madre farfallona\n" +
                "Alle donne non basta l'abito anti-stupro\n" +
                "Il catalogo mondiale delle molestie\n" +
                "Fortunato il paese che ha le sue eroine\n" +
                "I mattoni di Benassi dal Polesine allo spazio\n" +
                "Mea culpa dei creativi\n" +
                "contro gli spot sessisti\n" +
                "Gli occhi dei ragazzi contro il razzismo\n" +
                "Il giorno della fiducia a Letta, fallisce faccia a faccia Alfano-Berlusconi\"/\n" +
                "Berlusconi, Giunta dice sì a decadenza.\n", "it");
        assertNotNull(cas);
        AnnotationIndex<AnnotationFS> annotationIndex = cas.getAnnotationIndex(cas.getTypeSystem().getType(AnnotationUtils.STATEMENT_ANNOTATION));
        assertNotNull(annotationIndex);
        assertEquals(1, annotationIndex.size());
        for (AnnotationFS annotationFS : annotationIndex) {
            assertEquals("Ecco i vincitori", annotationFS.getCoveredText());
        }
    }

    @Test
    public void testTextWithStatementOnItalianSentenceWithStrangeQuotes() throws Exception {
        String descFilePath = getClass().getResource("/uima-test/TestAggregateStatementAnnotatorAEDescriptor.xml").getFile();
        CAS cas = AnnotatorTester.performTest(descFilePath, "Lui: \"Decisione indegna per eliminarmi\" \"/\n" +
                "M5S alla 'ex' Paola De Pin: \"Venduta\" .", "it");
        assertNotNull(cas);
        AnnotationIndex<AnnotationFS> annotationIndex = cas.getAnnotationIndex(cas.getTypeSystem().getType(AnnotationUtils.STATEMENT_ANNOTATION));
        assertNotNull(annotationIndex);
        assertEquals(2, annotationIndex.size());
        for (AnnotationFS annotationFS : annotationIndex) {
            assertNotNull(annotationFS.getCoveredText());
        }
    }


    @Test
    public void testTextWithStatementOnMultipleSentences() throws Exception {
        String descFilePath = getClass().getResource("/uima-test/TestAggregateStatementAnnotatorAEDescriptor.xml").getFile();
        CAS cas = AnnotatorTester.performTest(descFilePath, "Ma adesso, a perorare la causa del boss di Corleone, il cui avvocato si è visto respingere il ricorso presentato a Strasburgo, è addirittura l'Unione delle Camere Penali, per la quale è \"inaccettabile\" che Provenzano, \"ormai ridotto ad uno stato quasi vegetativo\", sia ancora al 41 bis. L'associazione dei penalisti chiede l'intervento \"immediato\" dei magistrati competenti, del Dap, e del Ministro della Giustizia, \"se veramente si vuole dimostrare di aver voltato pagina rispetto ai diritti dei detenuti, specialmente quelli in condizioni di salute estreme: senza distinzioni, senza discriminazioni, senza privilegi\".", "it");
        assertNotNull(cas);
        AnnotationIndex<AnnotationFS> annotationIndex = cas.getAnnotationIndex(cas.getTypeSystem().getType(AnnotationUtils.STATEMENT_ANNOTATION));
        assertNotNull(annotationIndex);
        assertEquals(4, annotationIndex.size());
        for (AnnotationFS annotationFS : annotationIndex) {
            assertNotNull(annotationFS.getCoveredText());
        }
    }
}
