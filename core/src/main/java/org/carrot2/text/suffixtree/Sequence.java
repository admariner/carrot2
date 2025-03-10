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
package org.carrot2.text.suffixtree;

/**
 * A sequence of elements from which a {@link SuffixTree} can be built. Elements are indexed with
 * integers starting at position 0. Elements themselves must be represented as integers, where equal
 * values at different indices indicate equal objects, whatever the underlying objects might be.
 */
public interface Sequence {
  /** Returns the number of elements in the sequence. */
  int size();

  /**
   * Returns a unique integer code for object at index <code>i</code> (the first element has 0
   * index).
   */
  int objectAt(int i);
}
