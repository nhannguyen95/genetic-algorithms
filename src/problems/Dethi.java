package problems;

import java.util.Random;

public class Dethi {
    public static void main(String[] args) {
        new Dethi();
    }
    
    Random rand = new Random();
    int W = 3000;
    int N = 24;
    int weights[] = {708,1114,877,1296,1436,1103,596,1319,1333,1139,1110,809,521,524,693,1469,1312,743,621,1220,1310,730,1345,1190};
    int values [] = {8563444,7443263,5424072,9166459,8059225,5713778,8984108,7060541,7415134,9924097,9087537,8448427,5825191,7564185,9030718,8716740,7921133,7001763,9019521,5892741,8354937,7057153,6612025,6102504};
    // result: 100000100001111000100000, interest = 43.435.594
    
    int GENERATIONS = 100;
    public Dethi() {
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
        System.out.println(max_fitness);
    }
    
    int ROOTS = 100;
    int roots[][] = new int[ROOTS][N];
    public void khoitao() {
        for(int i = 0; i < ROOTS; i++)
            for(int j = 0; j < N; j++)
                roots[i][j] = rand.nextInt(2);
    }
    
    int f[] = new int[ROOTS];
    public void danhgia() {
        for(int i = 0; i < ROOTS; i++) {
            int weight = 0;
            int value = 0;
            for(int j = 0; j < N; j++) {
                weight += weights[j] * roots[i][j];
                value += values[j] * roots[i][j];
            }
            f[i] = value - 1000000 * ( (weight <= W ? 0 : weight - W) / 100);
        }
    }
    
    public void luachon() {
        // using "truncate selection" with a threshold
        int tmp[] = f.clone();
        Arrays.sort(tmp); // ascending
        int threshold = tmp[N / 70 * 100];
        
        for(int i = 0; i < ROOTS; i++)
            if (f[i] < threshold) { // we mush have eliminated this
                // but to keep the size of population constant, we randomly replace
                roots[i] = roots[rand.nextInt(ROOTS)].clone();
            }
    }
    
    int NRS_OF_CROSSOVER = 30;
    public void laighep() {
        for(int i = 0; i < NRS_OF_CROSSOVER; i++) {
            int dad = rand.nextInt(ROOTS);
            int mom = rand.nextInt(ROOTS);
            // apply 1 cutting point method
            for(int j = N/2; j < N; j++) {
                int tmp = roots[dad][j];
                roots[dad][j] = roots[mom][j];
                roots[mom][j] = tmp;
            }
            // apply n cutting point method
//            for(int j = 0; j < N; j++) {
//                if (rand.nextBoolean()) {
//                    int tmp = roots[dad][j];
//                    roots[dad][j] = roots[mom][j];
//                    roots[mom][j] = tmp;
//                }
//            }
        }
    } 
    
    public void dotbien() {
        int i = rand.nextInt(ROOTS);
        int j = rand.nextInt(N);
        roots[i][j] = 1 - roots[i][j]; // inverse the bit
    }   
}
