package com.github.tteofili.btl.uima.annotator;

import java.util.regex.Pattern;
import org.apache.uima.analysis_component.CasAnnotator_ImplBase;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.cas.CAS;
import org.apache.uima.cas.TypeSystem;
import org.apache.uima.cas.text.AnnotationFS;
import org.apache.uima.util.Level;

/**
 * a statement annotator
 */
public class StatementAnnotator extends CasAnnotator_ImplBase {

    private final static Pattern p = Pattern.compile(".*\\\"(\\w+\\s*)\\\".*");


    @Override
    public void process(CAS cas) throws AnalysisEngineProcessException {
        // for each sentence
        TypeSystem typeSystem = cas.getTypeSystem();
        for (AnnotationFS annotation : cas.getAnnotationIndex(typeSystem.getType(AnnotationUtils.SENTENCE_ANNOTATION))) {

            String sentence = annotation.getCoveredText();
            this.getContext().getLogger().log(Level.INFO, "working on sentence: " + sentence);

            // find all the text fragments between apexes
            try {
                int start = 0;
                while ((start = sentence.indexOf("\"", start)) >= 0) {

                    // get rid of "/ tokens
                    while (sentence.charAt(start + 1) == '/') {
                        start = sentence.indexOf("\"", start + 1);
                    }
                    int end = sentence.indexOf("\"", start + 1);
                    while (sentence.charAt(end + 1) == '/') {
                        end = sentence.indexOf("\"", end + 1);
                    }

                    if (start > -1 && end > start) {
                        AnnotationFS statementAnnotation = cas.createAnnotation(typeSystem.getType(AnnotationUtils.STATEMENT_ANNOTATION), annotation.getBegin() + start + 1, annotation.getBegin() + end);
                        cas.addFsToIndexes(statementAnnotation);
                        this.getContext().getLogger().log(Level.INFO, "statement found: " + statementAnnotation.getCoveredText());
                        start = end + 1;
                    } else {
                        break;
                    }
                }
            } catch (Exception e) {
                this.getContext().getLogger().log(Level.WARNING, "cannot create StatementAnnotation", e);
            }

        }
    }
}
