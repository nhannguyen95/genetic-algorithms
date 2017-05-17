package problems;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

public class Knapsack01 {
    // w - weight
    // v - value
    final int N = 10;
    int W = 165;
    int weights[] = { 23, 31, 29, 44, 53, 38, 63, 85, 89, 82 };
    int values[] = { 92, 57, 49, 68, 60, 43, 67, 84, 87, 72 };
    // result: [1,1,1,1,0,1,0,0,0,0], v = 309, w = 165
    
    public static void main(String[] args) {
        new Knapsack01();
    }
    
    int ROOTS = 100;
    int GENERATIONS = 100;
    public Knapsack01() {
        khoitao();
        for(int gen = 0; gen < GENERATIONS; gen++) {
            System.out.println("gen " + gen);
            danhgia();
            luachon();
            laighep();
            dotbien();
        }
        
        // print result
        danhgia();
        Collections.sort(roots, new Comparator<Root>() {
            @Override
            public int compare(Root o1, Root o2) {
                return o2.value - o1.value;
            }
        });
        Root root = roots.get(0);
        for(int i = 0; i < N; i++)
            System.out.print(root.bins[i] + " ");
        System.out.println();
        System.out.println("value = " + root.value);
        System.out.println("weight = " + root.weight);
    }
    
    public void danhgia() {
        for(int i = 0; i < ROOTS; i++) {
            Root root = roots.get(i);
            ArrayList<Integer> picked = new ArrayList<Integer>();
            for(int j = 0; j < N; j++)
                if (root.bins[j] == 1) picked.add(j);
            
            // satisfy weight condition
            while(root.weight > W) {
                Collections.shuffle(picked);
                int j = picked.get(0);
                root.bins[j] = 0;
                root.weight -= weights[j];
                root.value -= values[j];
                picked.remove(0);
            }
        }
    }
    
    ArrayList<Root> parents = new ArrayList<Root>();
    public void luachon() {
        // sort individuals
//        Collections.sort(roots, new Comparator<Root>() {
//            @Override
//            public int compare(Root o1, Root o2) {
//                return o2.value - o1.value;
//            }
//        });
        
        // apply roulette-wheel method to select parents for the next generation
        
        int fsum = 0;
        for(Root root : roots) fsum += root.value;
        
        parents.clear();
        while(parents.size() < ROOTS) {
            int limit = new Random().nextInt(fsum + 1); // [0, fsum]
            int partial_sum = 0;
            for(Root root : roots) {
                partial_sum += root.value;
                if (partial_sum >= limit) { 
                    parents.add(root);
                    break;
                }
            }
        }   
    }
    
    public void laighep() {
        roots.clear();
        for(int i = 0; i < ROOTS / 2; i++) {
            Root dad = parents.get(new Random().nextInt(ROOTS));
            Root mom = parents.get(new Random().nextInt(ROOTS));
            // 1 cutting point method
            Root root1 = new Root(dad);
            Root root2 = new Root(mom);
            for(int j = N/2; j < N; j++) {
                int tmp = root1.bins[j];
                root1.bins[j] = root2.bins[j];
                root2.bins[j] = tmp;
            }
            root1.recompute_wv();
            root2.recompute_wv();
            roots.add(root1);
            roots.add(root2);
        }
    }
    
    public void dotbien() {
        int i = new Random().nextInt(ROOTS);
        int j = new Random().nextInt(N);
        roots.get(i).bins[j] = 1 - roots.get(i).bins[j];
        roots.get(i).recompute_wv();
    }
    
    ArrayList<Root> roots = new ArrayList<Root>();
    public void khoitao() {
        for(int i = 0; i < ROOTS; i++) {
            // generate randomly chronosome
            Root root = new Root();
            root.weight = 0;
            root.value = 0;
            for(int j = 0; j < N; j++) {
                int pick = (new Random().nextBoolean()) ? 1 : 0;
                root.bins[j] = pick;
                root.weight += weights[j] * pick;
                root.value += values[j] * pick;
            }
            roots.add(root);
        }
    }
    
    public class Root {
        int bins[];
        int weight;
        int value;
        
        public Root() {
            this.bins = new int [N];
            this.weight = weight;
            this.value = value;
        }
        
        public Root(Root r) {
            this.bins = new int [N];
            for(int i = 0; i < N; i++) 
                this.bins[i] = r.bins[i];
            this.weight = r.weight;
            this.value = r.value;
        }
        
        public void recompute_w() {
            weight = 0;
            for(int i = 0; i < N; i++)
                weight += weights[i] * bins[i];
        }
        
        public void recompute_v() {
            value = 0;
            for(int i = 0; i < N; i++)
                value += values[i] * bins[i];
        }
        
        public void recompute_wv() {
            recompute_w();
            recompute_v();
        }
    }
}