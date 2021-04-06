//import java.util.Random;
//import java.math.*;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.StdOut;
public class PercolationStats {
    // perform independent trials on an n-by-n grid
    private double[] percent;
    public PercolationStats(int n, int trials){
        if (n <= 0 || trials <= 0){
            throw new java.lang.IllegalArgumentException("n should be positive integer");
        }
        percent = new double[trials];
        //trials: number of experiments
        for (int i = 0; i < trials; i++) {
            Percolation p = new Percolation(n);
            // randomly choose a site
            while (!p.percolates()) {
                Random r = new Random();
                int row = r.nextInt(n) + 1;
                int col = r.nextInt(n) + 1;
                p.open(row, col);
            }
            percent[i] = (double)p.numberOfOpenSites() / (n * n) ;
        }
    }

    // sample mean of percolation threshold
    public double mean(){
        double mean = mean(percent);
        return mean;
    }

    // sample standard deviation of percolation threshold
    public double stddev(){
        double sd = stddev(percent);
        return sd;
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo(){
        double left = mean() - 1.96 * stddev() / Math.sqrt(percent.length);
        return left;
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi(){
        double right = mean() + 1.96 * stddev() / Math.sqrt(percent.length);
        return right;
    }

    // test client (see below)
    public static void main(String[] args){
        int N = Integer.parseInt(args[0]);
        int T = Integer.parseInt(args[1]);
        PercolationStats stats = new PercolationStats(N, T);
        StdOut.println("mean = " + stats.mean());
        StdOut.println("stddev = " + stats.stddev());
        StdOut.println("95% confidence interval = [" + stats.confidenceLo() + ", " + stats.confidenceHi() + "]");
    }
}
