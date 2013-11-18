package com.github.tteofili.btl.uima.annotator;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;
import org.apache.commons.io.IOUtils;
import org.apache.uima.UimaContext;
import org.apache.uima.analysis_component.CasAnnotator_ImplBase;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.cas.CAS;
import org.apache.uima.cas.Type;
import org.apache.uima.cas.TypeSystem;
import org.apache.uima.cas.text.AnnotationFS;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.util.Level;

/**
 * Annotates first names using a name dictionary and PoSs
 */
public class NameAnnotator extends CasAnnotator_ImplBase {

    private static final String NAMESDICT_PATH = "dictpath";
    private static final String POS_FEATURENAME = "posTag";

    private List<String> namesList;

    @Override
    public void initialize(UimaContext aContext) throws ResourceInitializationException {
        super.initialize(aContext);
        InputStream input = null;
        try {
            input = getClass().getResourceAsStream(String.valueOf(aContext
                    .getConfigParameterValue(NAMESDICT_PATH)));
            namesList = IOUtils.readLines(input);
            Collections.sort(namesList); // sort collection to make binary search possible
        } catch (IOException e) {
            aContext.getLogger().log(Level.SEVERE, "error reading dictionary file");
            throw new ResourceInitializationException(e);
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    // already closed, do nothing
                }
            }
        }
    }

    @Override
    public void process(CAS cas) throws AnalysisEngineProcessException {
        TypeSystem typeSystem = cas.getTypeSystem();
        for (AnnotationFS annotation : cas.getAnnotationIndex(typeSystem.getType(AnnotationUtils.TOKEN_ANNOTATION))) {
            /* create a name annotation for each occurrence of a name in the dictionary */
            if (Collections.binarySearch(namesList, annotation.getCoveredText()) >= 0 && isNP(annotation)) {
                AnnotationFS nameAnnotation = cas.createAnnotation(typeSystem.
                        getType(AnnotationUtils.NAME_ANNOTATION), annotation.getBegin(), annotation.getEnd());
                cas.addFsToIndexes(nameAnnotation);
            }
        }
    }

    private boolean isNP(AnnotationFS annotation) {
        Type type = annotation.getType();
        String pos = annotation.getFeatureValueAsString(type.getFeatureByBaseName(POS_FEATURENAME));
        return pos != null && "np".equalsIgnoreCase(pos);
    }

}
