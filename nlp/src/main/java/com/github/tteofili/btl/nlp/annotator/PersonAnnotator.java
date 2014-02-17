package com.github.tteofili.btl.nlp.annotator;

import java.util.Collection;
import org.apache.uima.analysis_component.CasAnnotator_ImplBase;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.cas.CAS;
import org.apache.uima.cas.Type;
import org.apache.uima.cas.text.AnnotationFS;

/**
 * a person annotator
 */
public class PersonAnnotator extends CasAnnotator_ImplBase {

    @Override
    public void process(CAS cas) throws AnalysisEngineProcessException {
        // use name annotations
        Type nameType = cas.getTypeSystem().getType(AnnotationUtils.OPENNLP_PERSON_ANNOTATION);
        for (AnnotationFS a : AnnotationUtils.getAnnotations(nameType, cas)) {


//            try {
//                SentenceAnnotation sentenceAnnotation = AnnotationUtils.getSentenceContaining(a);
//                if (sentenceAnnotation) {
            Collection<AnnotationFS> containedNameAnnotations = AnnotationUtils.getAnnotationsContainedIn(a, cas.getTypeSystem().getType(AnnotationUtils.NAME_ANNOTATION));
            if (containedNameAnnotations.size() > 0) {
                AnnotationFS nameAnnotation = cas.createAnnotation(cas.getTypeSystem().
                        getType(AnnotationUtils.PERSON_ANNOTATION), a.getBegin(), a.getEnd());
                cas.addFsToIndexes(nameAnnotation);
            }
//                }
//            } catch (CASException e) {
//                throw new AnalysisEngineProcessException(e);
//            }
        }

        // look for pronouns like (he/she) + verb (eventually filtering for some verbs
    }

}
