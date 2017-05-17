package problems;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

public class Dethi {
    public static void main(String[] args) {
        new Dethi();
    }
    
    Random rand = new Random();
    int W = 3000;
    int N = 24;
    int weights[] = {1493,1404,559,553,588,1040,717,1106,444,573,523,1128,577,929,1470,975,482,662,1140,437,408,519,1002,1414};
    int values [] = {8295231,7531427,7742592,7495552,8609577,6554570,5596915,7153314,6391023,7158529,8807929,9718686,8984477,7856291,7069579,8104601,9501510,5795110,8357981,9424747,7698497,7755148,8330829,6543455};
    // result: 001110001110100010011100, interest = 33.569.581
    
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
            f[i] = value - 1000000 * (weight / 100);
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
