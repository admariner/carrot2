/*
 * Carrot2 project.
 *
 * Copyright (C) 2002-2025, Dawid Weiss, Stanisław Osiński.
 * All rights reserved.
 *
 * Refer to the full license file "carrot2.LICENSE"
 * in the root folder of the repository checkout or at:
 * https://www.carrot2.org/carrot2.LICENSE
 */
package org.carrot2.language;

import org.carrot2.attrs.AcceptingVisitor;

/**
 * A parameter supplying a {@link StopwordFilter}.
 *
 * @see DefaultDictionaryImpl
 * @see EphemeralDictionaries
 */
public interface StopwordFilterDictionary extends AcceptingVisitor {
  StopwordFilter compileStopwordFilter();
}
