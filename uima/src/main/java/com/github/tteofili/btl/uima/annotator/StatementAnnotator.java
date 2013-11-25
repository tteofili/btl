package com.github.tteofili.btl.uima.annotator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.uima.analysis_component.CasAnnotator_ImplBase;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.cas.CAS;
import org.apache.uima.cas.TypeSystem;
import org.apache.uima.cas.text.AnnotationFS;
import org.apache.uima.util.Level;

/**
 * Add javadoc here
 */
public class StatementAnnotator extends CasAnnotator_ImplBase {

    private final static Pattern p = Pattern.compile(".*\\\"(.*)\\\".*");

    @Override
    public void process(CAS cas) throws AnalysisEngineProcessException {
        // for each sentence
        TypeSystem typeSystem = cas.getTypeSystem();
        for (AnnotationFS annotation : cas.getAnnotationIndex(typeSystem.getType(AnnotationUtils.SENTENCE_ANNOTATION))) {
            /* create a name annotation for each occurrence of a name in the dictionary */
//            if (Collections.binarySearch(namesList, annotation.getCoveredText()) >= 0 && isNP(annotation)) {
//                AnnotationFS nameAnnotation = cas.createAnnotation(typeSystem.
//                        getType(AnnotationUtils.NAME_ANNOTATION), annotation.getBegin(), annotation.getEnd());
//                cas.addFsToIndexes(nameAnnotation);
//            }
            // find all the text fragments between apexes
            try {
                Matcher m = p.matcher(annotation.getCoveredText());
                while (m.find()) {
                    cas.createAnnotation(typeSystem.getType(AnnotationUtils.STATEMENT_ANNOTATION), m.start(1), m.end(1));
                }
            } catch (Exception e) {
                this.getContext().getLogger().log(Level.WARNING, "cannot create StatementAnnotation", e);
            }


            // for each of them create a DeclarationAnnotation

            // for each declaration look for the person who made that

            // if found then create a StatementAnnotation (w/ author & declaration)

            // if not skip that
        }
    }
}
