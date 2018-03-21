package org.ing.hackathon.totalrecall.nlp;

import edu.stanford.nlp.pipeline.Annotation;
import org.k9m.k9nlp.model.document.DocumentProfile;
import org.k9m.k9nlp.stanford.DocumentProcessor;
import org.k9m.k9nlp.stanford.DocumentProcessorFactory;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class BasicNlpClassifier implements NlpClassifier {
    @Override
    public DocumentProfile classify(String textBody) {
        final DocumentProcessorFactory documentProcessorFactory = DocumentProcessorFactory.getInstance();
        Annotation document = documentProcessorFactory.constructDocument(textBody);
        String documentId = UUID.randomUUID().toString();
        DocumentProfile documentProfile = new DocumentProcessor(documentId, document).processDocument();
        return documentProfile;
    }
}
