package model;

/**
 * Game of Life
 */
public class GameOfLife {

    private final int n;//linear size
    private final int cell[];

    public GameOfLife(int linearSize) {
        this.n = linearSize;
        cell = new int[n * n];
    }

    public void randomInitialize(double p) {
        for (int i = 0; i < n * n; i++) {
            int k = 0;
            if (Math.random() < p) {  k = 1;  }
            cell[i] = k;
        }
    }

    public void updateState() {
        int dummy[] = new int[n * n];
        for (int r = 0; r < n * n; r++) {
            int c = countLivingNeigbours(r);
            switch (c) {
                case 3:
                    dummy[r] = 1;
                    break;
                case 2:
                    dummy[r] = cell[r];
                    break;
                default:
                    dummy[r] = 0;
                    break;
            }
        }
        for(int i=0;i<n*n;i++){ cell[i]=dummy[i]; }
    }

    /**
     * 隣接セルのうち、値が1であるセルの数を数える
     * @param r
     * @return 
     */
    private int countLivingNeigbours(int r) {
        int x = r % n;
        int y = r / n;
        int c = 0;
        for (int j = 0; j < 3; j++) {
            int yy = (y - 1 + j + n) % n;
            for (int i = 0; i < 3; i ++) {
                int xx = (x - 1 + i + n) % n;
                c += cell[yy * n + xx];
            }
        }
        c-=cell[r];
        return c;
    }

    public int getState(int r) {  return cell[r];  }

    public int getN() {  return n;  }
    
    
}
