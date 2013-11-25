package com.github.tteofili.btl.uima.annotator;

import org.apache.uima.analysis_component.CasAnnotator_ImplBase;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.cas.CAS;
import org.apache.uima.cas.Type;
import org.apache.uima.cas.text.AnnotationFS;

/**
 * Add javadoc here
 */
public class PersonAnnotator extends CasAnnotator_ImplBase {

    @Override
    public void process(CAS cas) throws AnalysisEngineProcessException {
        // use openNLP person name finder
        Type openNlpPersonType = cas.getTypeSystem().getType(AnnotationUtils.OPENNLP_PERSON_ANNOTATION);
        for (AnnotationFS a : AnnotationUtils.getAnnotations(openNlpPersonType, cas)) {

//            try {
//                SentenceAnnotation sentenceAnnotation = AnnotationUtils.getSentenceContaining(a);
//                if (sentenceAnnotation) {
                    AnnotationFS nameAnnotation = cas.createAnnotation(cas.getTypeSystem().
                            getType(AnnotationUtils.PERSON_ANNOTATION), a.getBegin(), a.getEnd());
                    cas.addFsToIndexes(nameAnnotation);
//                }
//            } catch (CASException e) {
//                throw new AnalysisEngineProcessException(e);
//            }
        }

        // look for pronouns like (he/she) + verb (eventually filtering for some verbs
    }

}
