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
package org.carrot2.math.mahout;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class MatrixVectorView extends AbstractVector {
  private Matrix matrix;
  private int row;
  private int column;
  private int rowStride;
  private int columnStride;

  public MatrixVectorView(Matrix matrix, int row, int column, int rowStride, int columnStride) {
    super(viewSize(matrix, row, column, rowStride, columnStride));
    if (row < 0 || row > matrix.rowSize()) {
      throw new IndexException(row, matrix.rowSize());
    }
    if (column < 0 || column > matrix.columnSize()) {
      throw new IndexException(column, matrix.columnSize());
    }

    this.matrix = matrix;
    this.row = row;
    this.column = column;
    this.rowStride = rowStride;
    this.columnStride = columnStride;
  }

  private static int viewSize(Matrix matrix, int row, int column, int rowStride, int columnStride) {
    if (rowStride != 0 && columnStride != 0) {
      int n1 = (matrix.numRows() - row) / rowStride;
      int n2 = (matrix.numCols() - column) / columnStride;
      return Math.min(n1, n2);
    } else if (rowStride > 0) {
      return (matrix.numRows() - row) / rowStride;
    } else {
      return (matrix.numCols() - column) / columnStride;
    }
  }

  @Override
  public boolean isDense() {
    return true;
  }

  @Override
  public boolean isSequentialAccess() {
    return true;
  }

  @Override
  public Iterator<Element> iterator() {
    final LocalElement r = new LocalElement(0);
    return new Iterator<Element>() {
      private int i;

      @Override
      public boolean hasNext() {
        return i < size();
      }

      @Override
      public Element next() {
        if (i >= size()) {
          throw new NoSuchElementException();
        }
        r.index = i++;
        return r;
      }

      @Override
      public void remove() {
        throw new UnsupportedOperationException("Can't remove from a view");
      }
    };
  }

  @Override
  public Iterator<Element> iterateNonZero() {
    return iterator();
  }

  @Override
  public double getQuick(int index) {
    return matrix.getQuick(row + rowStride * index, column + columnStride * index);
  }

  @Override
  public Vector like() {
    return matrix.like(size(), 1).viewColumn(0);
  }

  @Override
  public void setQuick(int index, double value) {
    matrix.setQuick(row + rowStride * index, column + columnStride * index, value);
  }

  @Override
  public int getNumNondefaultElements() {
    return size();
  }

  @Override
  public Vector clone() {
    MatrixVectorView r = (MatrixVectorView) super.clone();
    r.matrix = matrix.clone();
    r.row = row;
    r.column = column;
    r.rowStride = rowStride;
    r.columnStride = columnStride;
    return r;
  }
}
