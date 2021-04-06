import java.util.Random;
import java.math.*;
public class PercolationStats {
    // perform independent trials on an n-by-n grid
    private double[] percent;
    public PercolationStats(int n, int trials){
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
        double total = 0;
        for (int i = 0; i < percent.length; i++){
            total += percent[i];
        }
        double mean = total / percent.length;
        return mean;
    }

    // sample standard deviation of percolation threshold
    public double stddev(){
        double deviationSum = 0;
        for (int i = 0; i < percent.length; i++){
            deviationSum += (percent[i] - mean()) * (percent[i] - mean()) / (percent.length - 1);
        }
        double sd = Math.sqrt(deviationSum);
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

    }
}
