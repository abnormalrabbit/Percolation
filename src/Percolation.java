import edu.princeton.cs.algs4.WeightedQuickUnionUF;
public class Percolation {
    private int[][] grid;
    private WeightedQuickUnionUF uf;
    //private WeightedQuickUnionUF uf1;
    private int opensite;
    private int len;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n){
       if (n <= 0){
           throw new IllegalArgumentException("n should be larger than 0");
       }
       grid = new int[n][n];
       uf = new WeightedQuickUnionUF(n * n + 2);
       len = n;
       //uf1 = new WeightedQuickUnionUF(n * n + 2);
    }

    // not open: 0, open : 1
    public void open(int row, int col){
        isValid(row,col);
        grid[row - 1][col - 1] = 1;
        if (isOpen(row, col)) return;
        int index = (row - 1) * len + col;
        // if the site in in the first row we should union it to virtual top
        if (row == 1){
           uf.union(0,index);
        }
        // if the site in in the last row we should union it to virtual bottom
        if (row == len){
           uf.union(index,len * len + 1);
        }
        // union the site to its neighbor four sites
        if (isOpen(row -1, col)){
            uf.union(index,index - len);
        }
        if (isOpen(row + 1, col)){
            uf.union(index, index + len);
        }
        if (isOpen(row, col - 1)){
            uf.union(index, index - 1);
        }
        if (isOpen(row, col + 1)){
            uf.union(index, index + 1);
        }
        opensite++;
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col){
        isValid(row, col);
        return grid[row - 1][col - 1] == 1;
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col){
        if (isOpen(row, col)){
            int index = (row - 1) * len + col;
            return uf.find(index) == uf.find(0);
        }
        return false;
    }

    // returns the number of open sites
    public int numberOfOpenSites(){
       return opensite;
    }

    // does the system percolate?
    public boolean percolates(){
        return uf.find(len * len + 1) == uf.find(0);
    }

    // test client (optional)
    public static void main(String[] args){

    }

    //assert if the row and col number is valid
    private void isValid(int row, int col){
        if (row < 1 || row > len || col < 1 || col > len){
            throw new IndexOutOfBoundsException("index out of range");
        }
    }
}
