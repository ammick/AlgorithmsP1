/******************************************************************************
 *  Name:    Mikhail Troshchenko
 *  NetID:   mtroshchenko
 *  Precept: P01
 *
 *  Partner Name:    N/A
 *  Partner NetID:   N/A
 *  Partner Precept: N/A
 *
 *  Description:  Modeling Percolation using an N-by-N grid and Union-Find data
 *                structures to determine the threshold.
 ******************************************************************************/

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private WeightedQuickUnionUF gridUF;
    private WeightedQuickUnionUF isFullGrid;
    private int sideLength;
    private int countOpen;
    private boolean[] isOpen;
    private int indexTopRowVirtual;
    private int indexBottomRowVirtual;

    // create n-by-n grid, with all sites blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException();
        }

        countOpen = 0;
        sideLength = n;
        isOpen = new boolean[n * n];
        gridUF = new WeightedQuickUnionUF(sideLength * sideLength + 2);
        isFullGrid = new WeightedQuickUnionUF(sideLength * sideLength + 1);
        indexTopRowVirtual = sideLength * sideLength;
        for (int i = 0; i < sideLength; i++) {
            gridUF.union(i, indexTopRowVirtual);
            isFullGrid.union(i, indexTopRowVirtual);
        }
        indexBottomRowVirtual = indexTopRowVirtual + 1;
        for (int j = sideLength * (sideLength - 1); j < sideLength * sideLength; j++) {
            gridUF.union(j, indexBottomRowVirtual);
        }
    }


    // open site (row, col) if it is not open already
    public void open(int row, int col) {
        if (IsCoordsIllegal(row, col)) {
            throw new IllegalArgumentException();
        }
        int index = GetIndexFromCoords(row, col);

        if (isOpen[index]) {
            return;
        }

        isOpen[index] = true;
        countOpen++;
        ConnectIfOpen(index, row - 1, col); // left
        ConnectIfOpen(index, row + 1, col); // right
        ConnectIfOpen(index, row, col + 1); // top
        ConnectIfOpen(index, row, col - 1); // bottom
    }

    // is site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (IsCoordsIllegal(row, col)) {
            throw new IllegalArgumentException();
        }
        return isOpen[GetIndexFromCoords(row, col)];
    }

    // is site (row, col) full?
    public boolean isFull(int row, int col) {
        if (IsCoordsIllegal(row, col)) {
            throw new IllegalArgumentException();
        }

        int index = GetIndexFromCoords(row, col);
        return isOpen[index] && isFullGrid.connected(indexTopRowVirtual, index);
    }

    // number of open sites
    public int numberOfOpenSites() {
        return countOpen;
    }

    // does the system percolate?
    public boolean percolates() {
        if (sideLength == 1) {
            return isOpen(1, 1);
        }
        else {
            return gridUF.connected(indexTopRowVirtual, indexBottomRowVirtual);
        }
    }

    private int GetIndexFromCoords(int row, int col) {
        return (row - 1) * sideLength + col - 1;
    }

    private boolean IsCoordsIllegal(int row, int col) {
        if (row < 1 || col < 1 || row > sideLength || col > sideLength) {
            return true;
        }
        return false;
    }

    private void ConnectIfOpen(int index, int rowOfNeighbor, int colOfNeighbor) {
        if (IsCoordsIllegal(rowOfNeighbor, colOfNeighbor)) {
            return;
        }

        int indexNeighbor = GetIndexFromCoords(rowOfNeighbor, colOfNeighbor);

        if (!isOpen[indexNeighbor]) {
            return;
        }

        gridUF.union(index, indexNeighbor);
        isFullGrid.union(index, indexNeighbor);
    }
}
