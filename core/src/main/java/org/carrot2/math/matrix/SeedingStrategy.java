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
package org.carrot2.math.matrix;

import org.carrot2.math.mahout.matrix.DoubleMatrix2D;

/** Defines the seeding routine to be used as part of a matrix factorization algorithm. */
public interface SeedingStrategy {
  /**
   * Initializes values of the provided U and V matrices. The A matrix is the input matrix to be
   * factorized.
   *
   * @param A matrix to be factorized
   * @param U left factorized matrix to be seeded
   * @param V right factorized matrix to be seeded
   */
  public void seed(DoubleMatrix2D A, DoubleMatrix2D U, DoubleMatrix2D V);
}
