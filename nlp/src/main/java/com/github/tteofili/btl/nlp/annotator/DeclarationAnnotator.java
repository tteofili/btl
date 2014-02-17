package com.github.tteofili.btl.nlp.annotator;

import java.util.List;
import org.apache.uima.SentenceAnnotation;
import org.apache.uima.analysis_component.CasAnnotator_ImplBase;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.cas.CAS;
import org.apache.uima.cas.CASException;
import org.apache.uima.cas.Type;
import org.apache.uima.cas.TypeSystem;
import org.apache.uima.cas.text.AnnotationFS;

/**
 * a declaration annotator
 */
public class DeclarationAnnotator extends CasAnnotator_ImplBase {

    @Override
    public void process(CAS cas) throws AnalysisEngineProcessException {
        // for each statement
        TypeSystem typeSystem = cas.getTypeSystem();
        for (AnnotationFS statement : cas.getAnnotationIndex(typeSystem.getType(AnnotationUtils.STATEMENT_ANNOTATION))) {
            try {
                SentenceAnnotation sentence = AnnotationUtils.getSentenceContaining(statement);
                // look for the person who made that
                List<AnnotationFS> personAnnotations = AnnotationUtils.getContainedAnnotations(sentence, typeSystem.getType(AnnotationUtils.PERSON_ANNOTATION));
                // if found then create a declaration annotation (w/ author & statement)
                if (personAnnotations.size() == 1) {
                    AnnotationFS person = personAnnotations.get(0);
                    Type type = typeSystem.getType(AnnotationUtils.DECLARATION_ANNOTATION);
                    AnnotationFS declaration = cas.createAnnotation(type, statement.getBegin(), statement.getEnd());
                    declaration.setFeatureValue(type.getFeatureByBaseName("author"), person);
                    declaration.setFeatureValue(type.getFeatureByBaseName("statement"), statement);

                    cas.addFsToIndexes(declaration);
                }
                // if not skip that
            } catch (CASException e) {
                throw new AnalysisEngineProcessException(e);
            }

        }


    }
}
