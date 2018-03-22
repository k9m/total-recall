package org.ing.hackathon.totalrecall.docprocessor.nlp;

import org.k9m.k9nlp.model.document.DocumentProfile;

public interface NlpClassifier {

  DocumentProfile classify(String documentId, String textBody);
}
