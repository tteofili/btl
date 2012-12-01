package com.github.tteofili.btl.uima.annotator;

import org.apache.uima.analysis_component.CasAnnotator_ImplBase;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.cas.CAS;

/**
 * Add javadoc here
 */
public class PersonAnnotator extends CasAnnotator_ImplBase {

    @Override
    public void process(CAS cas) throws AnalysisEngineProcessException {
        // use openNLP person name finder

        // look for pronouns like (he/she) + verb (eventually filtering for some verbs
    }

}
