package com.github.tteofili.btl.uima.annotator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import org.apache.uima.SentenceAnnotation;
import org.apache.uima.TokenAnnotation;
import org.apache.uima.cas.CAS;
import org.apache.uima.cas.CASException;
import org.apache.uima.cas.CASRuntimeException;
import org.apache.uima.cas.FSIterator;
import org.apache.uima.cas.FeatureStructure;
import org.apache.uima.cas.Type;
import org.apache.uima.cas.text.AnnotationFS;
import org.apache.uima.cas.text.AnnotationIndex;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.tcas.Annotation;

/**
 * utility class for btl annotations
 */
public class AnnotationUtils {

    protected static final String TOKEN_ANNOTATION = "org.apache.uima.TokenAnnotation";
    protected static final String SENTENCE_ANNOTATION = "org.apache.uima.SentenceAnnotation";
    protected static final String NAME_ANNOTATION = "com.github.tteofili.btl.uima.NameAnnotation";
    protected static final String STATEMENT_ANNOTATION = "com.github.tteofili.btl.uima.StatementAnnotation";
    protected static final String DECLARATION_ANNOTATION = "com.github.tteofili.btl.uima.DeclarationAnnotation";
    protected static final String PERSON_ANNOTATION = "com.github.tteofili.btl.uima.PersonAnnotation";
    protected static final String OPENNLP_PERSON_ANNOTATION = "opennlp.uima.Person";

    /**
     * @param type
     * @param cas
     * @return
     */
    public static List<FeatureStructure> getAllFSofType(int type, JCas cas) {
        List<FeatureStructure> featureStructures = new ArrayList<FeatureStructure>();
        for (FSIterator<FeatureStructure> it = cas.getFSIndexRepository().getAllIndexedFS(
                cas.getCasType(type)); it.hasNext(); ) {
            featureStructures.add(it.next());
        }
        return featureStructures;
    }

    /**
     * @param type
     * @param cas
     * @return
     */
    public static List<FeatureStructure> getAllFSofType(Type type, JCas cas) {
        List<FeatureStructure> featureStructures = new ArrayList<FeatureStructure>();
        for (FSIterator<FeatureStructure> it = cas.getFSIndexRepository().getAllIndexedFS(type); it
                .hasNext(); ) {
            featureStructures.add(it.next());
        }
        return featureStructures;
    }

    /**
     * @param type
     * @param cas
     * @return
     */
    public static List<Annotation> getAnnotations(int type, JCas cas) {
        List<Annotation> annotations = new ArrayList<Annotation>();
        for (Annotation annotation : cas.getAnnotationIndex(type)) {
            annotations.add(annotation);
        }
        return annotations;
    }

    /**
     * @param type
     * @param cas
     * @return
     */
    public static List<AnnotationFS> getAnnotations(Type type, CAS cas) {
        List<AnnotationFS> annotations = new ArrayList<AnnotationFS>();
        for (AnnotationFS annotation : cas.getAnnotationIndex(type)) {
            annotations.add(annotation);
        }
        return annotations;
    }

    /**
     * @return
     * @throws CASException
     * @throws CASRuntimeException
     */
    public static List<TokenAnnotation> getTokensInsideSentence(SentenceAnnotation sentence)
            throws CASRuntimeException, CASException {
        // filter and takes only the token annotations inside the sentence
        AnnotationIndex<Annotation> tokenIndex = sentence.getCAS().getJCas().getAnnotationIndex(
                TokenAnnotation.type);
        List<TokenAnnotation> tokens = new ArrayList<TokenAnnotation>();
        for (Annotation ta : tokenIndex) {
            if (ta.getBegin() < sentence.getEnd() && ta.getEnd() > sentence.getBegin()) {
                tokens.add((TokenAnnotation) ta);
            }
        }
        return tokens;
    }

    /**
     * @param sentence
     * @param startingAddress
     * @return
     * @throws CASRuntimeException
     * @throws CASException
     */
    public static List<TokenAnnotation> getTokensInsideSentence(SentenceAnnotation sentence,
                                                                int startingAddress) throws CASRuntimeException, CASException {
        // filter and takes only the token annotations inside the sentence
        AnnotationIndex<Annotation> tokenIndex = sentence.getCAS().getJCas().getAnnotationIndex(
                TokenAnnotation.type);
        List<TokenAnnotation> tokens = new ArrayList<TokenAnnotation>();
        for (Annotation ta : tokenIndex) {
            if (ta.getBegin() < sentence.getEnd() && ta.getEnd() > sentence.getBegin()
                    && ta.getBegin() > startingAddress) {
                tokens.add((TokenAnnotation) ta);
            }
        }
        return tokens;
    }

    /**
     * get the sentence containing the desired annotation
     *
     * @param annotation
     * @return
     * @throws CASException
     */
    public static SentenceAnnotation getSentenceContaining(AnnotationFS annotation) throws CASException {
        SentenceAnnotation foundSentence = null;
        List<Annotation> sentenceAnnotations = getAnnotations(SentenceAnnotation.type, annotation
                .getCAS().getJCas());
        for (Annotation sentence : sentenceAnnotations) {
            if (annotation.getBegin() > sentence.getBegin() && annotation.getEnd() < sentence.getEnd()) {
                foundSentence = (SentenceAnnotation) sentence;
                break;
            }
        }

        return foundSentence;
    }

    /**
     * get sentence surrounding a particular position inside the document
     *
     * @param i
     * @param cas
     * @return
     */
    public static SentenceAnnotation getSentenceAtPosition(int i, JCas cas) {
        SentenceAnnotation foundSentence = null;
        List<Annotation> sentenceAnnotations = getAnnotations(SentenceAnnotation.type, cas);
        for (Annotation sentence : sentenceAnnotations) {
            if (sentence.getBegin() < i && sentence.getEnd() > i) {
                foundSentence = (SentenceAnnotation) sentence;
                break;
            }
        }

        return foundSentence;
    }

    /**
     * get annotations of passed type contained inside the sentence
     *
     * @param sentence
     * @param type
     * @return
     */
    public static List<AnnotationFS> getContainedAnnotations(SentenceAnnotation sentence, Type type) throws CASException {
        List<AnnotationFS> foundAnnotations = new ArrayList<AnnotationFS>();
        for (AnnotationFS annotation : getAnnotations(type, sentence.getCAS())) {
            if (annotation.getBegin() >= sentence.getBegin() && annotation.getEnd() <= sentence.getEnd()) {
                foundAnnotations.add(annotation);
            }
        }
        return foundAnnotations;
    }

    public static Collection<AnnotationFS> getAnnotationsContainedIn(AnnotationFS targetAnnotation) {
        Collection<AnnotationFS> containedAnnotations = new LinkedList<AnnotationFS>();
        for (AnnotationFS a : targetAnnotation.getCAS().getAnnotationIndex()) {
            if (a.getBegin() >= targetAnnotation.getBegin() && a.getEnd() <= targetAnnotation.getEnd() && !a.getType().equals(targetAnnotation.getType())) {
                containedAnnotations.add(a);
            }
        }
        return containedAnnotations;
    }

    public static Collection<AnnotationFS> getAnnotationsContainedIn(AnnotationFS targetAnnotation, Type type) {
        Collection<AnnotationFS> containedAnnotations = new LinkedList<AnnotationFS>();
        for (AnnotationFS a : targetAnnotation.getCAS().getAnnotationIndex()) {
            if (a.getBegin() >= targetAnnotation.getBegin() && a.getEnd() <= targetAnnotation.getEnd() && type.equals(a.getType())) {
                containedAnnotations.add(a);
            }
        }
        return containedAnnotations;
    }
}
