package com.github.tteofili.btl.uima.annotator;

import org.apache.uima.analysis_component.CasAnnotator_ImplBase;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.cas.CAS;

/**
 * Add javadoc here
 */
public class StatementAnnotator extends CasAnnotator_ImplBase {

    @Override
    public void process(CAS aCAS) throws AnalysisEngineProcessException {
        // for each sentence

        // find all the text fragments between apexes

        // for each of them create a DeclarationAnnotation

        // for each declaration look for the person who made that

        // if found then create a StatementAnnotation (w/ author & declaration)

        // if not skip that
    }
}
