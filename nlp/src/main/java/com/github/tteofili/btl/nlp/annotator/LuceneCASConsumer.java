package com.github.tteofili.btl.nlp.annotator;

import java.io.File;
import java.io.IOException;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.FieldType;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.apache.uima.UimaContext;
import org.apache.uima.analysis_component.CasAnnotator_ImplBase;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.cas.CAS;
import org.apache.uima.cas.FeatureStructure;
import org.apache.uima.cas.Type;
import org.apache.uima.cas.TypeSystem;
import org.apache.uima.cas.text.AnnotationFS;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.util.Level;

/**
 * A Lucene CAS Consumer for declarations
 */
public class LuceneCASConsumer extends CasAnnotator_ImplBase {

    private FSDirectory directory;
    private FieldType ft;

    @Override
    public void initialize(UimaContext context) throws ResourceInitializationException {
        super.initialize(context);
        String indexPath = getClass().getResource(String.valueOf(context.getConfigParameterValue("indexpath"))).getFile();
        try {
            directory = FSDirectory.open(new File(indexPath));
            ft = new FieldType();
            ft.setIndexed(true);
            ft.setStored(true);
            ft.setStoreTermVectors(true);
            ft.setStoreTermVectorPositions(true);
            ft.setStoreTermVectorOffsets(true);
        } catch (IOException e) {
            throw new ResourceInitializationException(e);
        }

    }

    @Override
    public void process(CAS cas) throws AnalysisEngineProcessException {
        IndexWriter writer = null;
        try {
            writer = new IndexWriter(directory, new IndexWriterConfig(Version.LUCENE_46, new StandardAnalyzer(Version.LUCENE_46)));
            TypeSystem typeSystem = cas.getTypeSystem();
            Type declarationType = typeSystem.getType(AnnotationUtils.DECLARATION_ANNOTATION);
            for (AnnotationFS declaration : cas.getAnnotationIndex(typeSystem.getType(AnnotationUtils.DECLARATION_ANNOTATION))) {
                FeatureStructure authorFS = declaration.getFeatureValue(declarationType.getFeatureByBaseName("author"));
                String author = ((AnnotationFS) authorFS).getCoveredText();
                FeatureStructure statementFS = declaration.getFeatureValue(declarationType.getFeatureByBaseName("statement"));
                String statement = ((AnnotationFS) statementFS).getCoveredText();
                Document doc = new Document();
                doc.add(new Field("author", author, ft));
                doc.add(new Field("statement", statement, ft));
                writer.addDocument(doc);
                getContext().getLogger().log(Level.INFO, "PERSISTED DECLARATION -> " + author + " : \"" + statement + "\"");
            }
        } catch (IOException e) {
            throw new AnalysisEngineProcessException(e);
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    // do nothing
                }
            }
        }
    }

    @Override
    public void destroy() {
        super.destroy();
        directory.close();
    }
}
