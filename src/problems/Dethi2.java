package problems;

import java.util.Random;

public class Dethi2 {
    public static void main(String[] args) {
        new Dethi2();
    }
    
    Random rand = new Random();
    int W = 3000;
    int N = 24;
    int weights[] = {1493,1404,559,553,588,1040,717,1106,444,573,523,1128,577,929,1470,975,482,662,1140,437,408,519,1002,1414};
    int values [] = {8295231,7531427,7742592,7495552,8609577,6554570,5596915,7153314,6391023,7158529,8807929,9718686,8984477,7856291,7069579,8104601,9501510,5795110,8357981,9424747,7698497,7755148,8330829,6543455};
    // result: 001110001110100010011100, interest = 33.569.581
    
    int GENERATIONS = 100;
    public Dethi2() {
        khoitao();
        for(int gen = 0; gen < GENERATIONS; gen++) {
            danhgia();
            print();
            luachon();
            laighep();
            dotbien();
        }
    }
    
    public void print() {
        int max_index = 0;
        int max_fitness = f[0];
        for(int i = 1; i < ROOTS; i++) 
            if (f[i] > max_fitness) {
                max_fitness = f[i];
                max_index = i;
            }
        for(int i = 0; i < N; i++)
            System.out.print(roots[max_index][i] + " ");
        System.out.print(" --- ");
        System.out.println(max_fitness);
        
    }
    
    int ROOTS = 100;
    int roots[][] = new int [ROOTS][N];
    public void khoitao() {
        for(int i = 0; i < ROOTS; i++)
            for(int j = 0; j < N; j++)
                roots[i][j] = rand.nextInt(2);
    }
    
    int f[] = new int[ROOTS];
    public void danhgia() {
        for(int i = 0; i < ROOTS; i++) {
            int w = 0;
            int v = 0;
            for(int j = 0; j < N; j++) {
                w += weights[j] * roots[i][j];
                v += values[j] * roots[i][j];
            }
            f[i] = v - 1000000 * (w / 100);
        }
    }
    
    int parents[][] = new int[ROOTS][N];
    public void luachon() {
        // etilism selection
        newgen[0] = getBestRoot(roots);
        
        // tournament selection
        for(int i = 0; i < ROOTS; i++)
            parents[i] = tournamentSelection(5);
    }
    
    public int[] tournamentSelection(int nrs_of_candidates) {
        int [][] candidates = new int[nrs_of_candidates][N];
        for(int i = 0; i < nrs_of_candidates; i++)
            candidates[i] = roots[rand.nextInt(ROOTS)];
        return getBestRoot(candidates);
    }
    
    public int[] getBestRoot(int [][] roots) {
        int best_index = 0;
        int best_fitness = 0;
        for(int i = 0; i < roots.length; i++) {
            int w = 0;
            int v = 0;
            for(int j = 0; j < roots[0].length; j++) {
                w += weights[j] * roots[i][j];
                v += values[j] * roots[i][j];
            }
            int f = v - 1000000 * ( w / 100 );
            if (i == 0) {
                best_index = 0;
                best_fitness = f;
            } else {
                if (f > best_fitness) {
                    best_fitness = f;
                    best_index = i;
                }
            }
        }
        return roots[best_index];
    }
    
    int newgen[][] = new int[ROOTS][N];
    public void laighep() {
        
        for(int i = 1; i < ROOTS; i++) {
            int dad[] = parents[rand.nextInt(ROOTS)];
            int mom[] = parents[rand.nextInt(ROOTS)];
            
            // 1 cutting point crossover method
            int k = rand.nextInt(N);
            for(int j = 0; j < N; j++)
                newgen[i][j] = (j < k) ? dad[j] : mom[j];
        }
        
        roots = newgen;
    }
    
    public void dotbien() {
        int i = rand.nextInt(ROOTS);
        int j = rand.nextInt(N);
        roots[i][j] = 1 - roots[i][j];
    }
}
