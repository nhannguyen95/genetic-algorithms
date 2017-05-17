package problems;

import java.util.Random;

public class Dethi2 {
    public static void main(String[] args) {
        new Dethi2();
    }
    
    Random rand = new Random();
    int W = 3000;
    int N = 24;
    int weights[] = {708,1114,877,1296,1436,1103,596,1319,1333,1139,1110,809,521,524,693,1469,1312,743,621,1220,1310,730,1345,1190};
    int values [] = {8563444,7443263,5424072,9166459,8059225,5713778,8984108,7060541,7415134,9924097,9087537,8448427,5825191,7564185,9030718,8716740,7921133,7001763,9019521,5892741,8354937,7057153,6612025,6102504};
    // result: 100000100001111000100000, interest = 43.435.594
    
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
            f[i] = v - 1000000 * ( (w <= W ? 0 : w - W) / 100);
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
            int f = v - 1000000 * ( (w <= W ? 0 : w - W) / 100 );
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
