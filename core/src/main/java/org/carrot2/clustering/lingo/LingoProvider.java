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
package org.carrot2.clustering.lingo;

import org.carrot2.clustering.ClusteringAlgorithmProvider;

public class LingoProvider implements ClusteringAlgorithmProvider {
  @Override
  public String name() {
    return LingoClusteringAlgorithm.NAME;
  }

  @Override
  public LingoClusteringAlgorithm get() {
    return new LingoClusteringAlgorithm();
  }
}
