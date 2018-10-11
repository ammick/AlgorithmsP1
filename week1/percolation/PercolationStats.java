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

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private double[] openSitesPercolationData;
    private Double meanx;
    private Double standardDeviation;
    private Double confidenceLo;
    private Double confidenceHi;
    private int _trials;

    // perform trials independent experiments on an n-by-n grid
    public PercolationStats(int sideLength, int trials) {
        if (sideLength <= 0 || trials <= 0) {
            throw new IllegalArgumentException();
        }
        meanx = null;
        standardDeviation = null;
        confidenceLo = null;
        confidenceHi = null;
        _trials = trials;
        double totalSites = sideLength * sideLength;

        openSitesPercolationData = new double[trials];
        Percolation perc;
        for (int i = 0; i < trials; i++) {
            perc = new Percolation(sideLength);

            while (!perc.percolates()) {
                perc.open(StdRandom.uniform(1, sideLength + 1),
                          StdRandom.uniform(1, sideLength + 1));
            }
            openSitesPercolationData[i] = perc.numberOfOpenSites() / totalSites;
        }

    }

    // sample mean of percolation threshold
    public double mean() {
        if (meanx == null) {
            meanx = StdStats.mean(openSitesPercolationData);
        }
        return meanx;
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        if (standardDeviation == null) {
            standardDeviation = StdStats.stddev(openSitesPercolationData);
        }
        return standardDeviation;
    }

    // low  endpoint of 95% confidence interval
    public double confidenceLo() {
        if (confidenceLo == null) {
            confidenceLo = mean() - (1.96 * stddev() / Math.sqrt(_trials));
        }
        return confidenceLo;
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        if (confidenceHi == null) {
            confidenceHi = mean() + (1.96 * stddev() / Math.sqrt(_trials));
        }
        return confidenceHi;
    }

    // test client (described below)
    public static void main(String[] args) {
        int sideLength = Integer.parseInt(args[0]);
        int numberOfTrials = Integer.parseInt(args[1]);
        PercolationStats ps = new PercolationStats(sideLength, numberOfTrials);
        System.out.println("mean                    = " + ps.mean());
        System.out.println("stddev                  = " + ps.stddev());
        System.out.println("95% confidence interval = " +
                                   String.format("[%s, %s]",
                                                 Double.toString(ps.confidenceLo()),
                                                 Double.toString(ps.confidenceHi())));
    }
}

