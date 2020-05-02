/*
 * Carrot2 project.
 *
 * Copyright (C) 2002-2020, Dawid Weiss, Stanisław Osiński.
 * All rights reserved.
 *
 * Refer to the full license file "carrot2.LICENSE"
 * in the root folder of the repository checkout or at:
 * https://www.carrot2.org/carrot2.LICENSE
 */
package org.carrot2.language.extras;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Supplier;
import org.apache.lucene.analysis.br.BrazilianStemmer;
import org.carrot2.language.ExtendedWhitespaceTokenizer;
import org.carrot2.language.LanguageComponentsProviderImpl;
import org.carrot2.language.LexicalData;
import org.carrot2.language.Stemmer;
import org.carrot2.language.Tokenizer;
import org.carrot2.text.preprocessing.LabelFormatter;
import org.carrot2.text.preprocessing.LabelFormatterImpl;
import org.carrot2.util.ResourceLookup;

/** */
public class BrazilianLanguageComponents extends LanguageComponentsProviderImpl {
  public static final String NAME = "Brazilian";

  public BrazilianLanguageComponents() {
    super("Carrot2 (extras)", NAME);
  }

  @Override
  public Map<Class<?>, Supplier<?>> load(String language, ResourceLookup resourceLookup)
      throws IOException {
    LexicalData lexicalData = loadLexicalData(NAME, resourceLookup);

    LinkedHashMap<Class<?>, Supplier<?>> components = new LinkedHashMap<>();
    components.put(
        Stemmer.class, () -> new LuceneStemmerAdapter(new BrazilianStemmerAdapter()::stems, 5));
    components.put(Tokenizer.class, ExtendedWhitespaceTokenizer::new);
    components.put(LexicalData.class, () -> lexicalData);
    components.put(LabelFormatter.class, () -> new LabelFormatterImpl(" "));

    return components;
  }

  private class BrazilianStemmerAdapter extends BrazilianStemmer {
    public int stems(char[] chars, int len) {
      String word = new String(chars, 0, len);
      String stem = super.stem(word);

      if (Objects.equals(word, stem)) {
        return len;
      } else {
        stem.getChars(0, stem.length(), chars, 0);
        return stem.length();
      }
    }
  }
}