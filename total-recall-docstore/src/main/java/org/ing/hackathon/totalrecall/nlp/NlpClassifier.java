package org.ing.hackathon.totalrecall.nlp;

import org.k9m.k9nlp.model.document.DocumentProfile;
import org.springframework.stereotype.Service;

public interface NlpClassifier {

    public DocumentProfile classify(String entity);
}
