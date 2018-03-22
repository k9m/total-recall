package org.ing.hackathon.totalrecall.docprocessor.nlp;

import edu.stanford.nlp.pipeline.Annotation;
import org.k9m.k9nlp.model.document.DocumentProfile;
import org.k9m.k9nlp.model.document.sentence.entity.KeywordType;
import org.k9m.k9nlp.stanford.DocumentProcessor;
import org.k9m.k9nlp.stanford.DocumentProcessorFactory;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

import static org.k9m.k9nlp.model.document.sentence.entity.KeywordType.*;

@Service
public class BasicNlpClassifier implements NlpClassifier {
  @Override
  public DocumentProfile classify(final String documentId, final String textBody) {
    final DocumentProcessorFactory documentProcessorFactory = DocumentProcessorFactory.getInstance();
    Annotation document = documentProcessorFactory.constructDocument(textBody);
    DocumentProfile documentProfile = new DocumentProcessor(documentId, document).processDocument();
//    removeUnwantedKeywords(documentProfile);
    return documentProfile;
  }

  private static void removeUnwantedKeywords(DocumentProfile documentProfile) {
    documentProfile.getSentences().forEach(s -> {
              s.getKeywords().stream().filter(k -> !allowedTypes.contains(KeywordType.valueOf(k.getPos())));
            });

  }

  private static final List<KeywordType> allowedTypes = Arrays.asList(

          NN,// 	noun, singular or mass 	door
          NNS,// 	noun plural 	doors
          NNP,// 	proper noun, singular 	John
          NNPS,// 	proper noun, plural 	Vikings
          PDT,// 	predeterminer 	both the boys
          POS,// 	possessive ending 	friend�s
          PRP,// 	personal pronoun 	I, he, it
          PRP$,// 	possessive pronoun 	my, his
          RB,// 	adverb 	however, usually, naturally, here, good
          RBR,// 	adverb, comparative 	better
          RBS,// 	adverb, superlative 	best
          RP,// 	particle 	give up
          TO,// 	to 	to go, to him
          UH,//	interjection 	uhhuhhuhh
          VB,// 	verb, base form 	take
          VBD,// 	verb, past tense 	took
          VBG,// 	verb, gerund/present participle 	taking
          VBN,// 	verb, past participle 	taken
          VBP,// 	verb, sing. present, non-3d 	take
          VBZ,// 	verb, 3rd person sing. present 	takes
          WDT,// 	wh-determiner 	which
          WP,// 	wh-pronoun 	who, what
          WP$,// 	possessive wh-pronoun 	whose
          WRB// 	wh-abverb 	where, when

  );

}
