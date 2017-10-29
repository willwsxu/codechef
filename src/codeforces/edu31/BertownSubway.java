package codeforces.edu31;

// Oct 27, 2017 Education round 31
import codechef.DSU;
import codechef.IOR;
import static java.lang.System.out;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

// C
// n train stations, Pi is the only destination from i,
// for each station i, there exists only one station j such that pj=i
// The convenience is the number of ordered pairs (x, y) such that person can start at station x and, 
//   after taking some subway trains (possibly zero), arrive at station y (1 ≤ x, y ≤ n).
// output max convenience, after switch at most two destination
// IDEA
// each station can only have one way to go in or out. 
// stations form a forest of connected components
// convenience of each component is s^2, if component size is s
// Use DSU to find all components
// save the edges with two nodes already connected
// find components with extra edges, sort by size in rreverse, combine the top two by switch destination of two stations in each
// sum convenience of all components
public class BertownSubway {
    
    DSU dsu;
    
    BertownSubway(int p[])
    {
        boolean used[]=new boolean[p.length];
        Arrays.fill(used, false);
        dsu = new DSU(p.length);
        for (int i=0; i<p.length; i++) {
            if (dsu.find(i)!=dsu.find(p[i]-1)) {
                dsu.union(i, p[i]-1);
                used[i]=true;  // edge is used in dsu
            }
        }
        //dsu.printComponents();
        List<Integer> sizes = dsu.componentSize();
        //out.println(sizes);
        long total=0;  // use long to prevent overflow
        if (sizes.size()==1)
            total =  (long)sizes.get(0)*sizes.get(0);
        else {
            List<Integer> merge=new ArrayList<>();
            for (int i=0; i<p.length; i++) {
                if (used[i])
                    continue;
                merge.add(dsu.getSize(i));  // store components with extra unused edge
            }
            if (merge.size()>=2) {  // combine size of top two components
                Collections.sort(merge, Collections.reverseOrder());
                int newSize=merge.get(0)+merge.get(1);
                sizes.remove(merge.get(0));
                sizes.remove(merge.get(1));
                sizes.add(newSize);
            }
            for (int i=0; i<sizes.size(); i++)
                total += (long)sizes.get(i)*sizes.get(i);
        }
        out.println(total);
    }
    public static  void test()
    {
        new BertownSubway(new int[]{2,1,3});
        new BertownSubway(new int[]{1,5,4,3,2});
        int p[]=new int[]{98,52,63,2,18,96,31,58,84,40,41,45,66,100,46,71,26,48,81,20,73,91,68,76,13,93,17,29,64,95,79,21,55,75,19,85,54,51,89,78,15,87,43,59,36,1,90,35,65,56,62,28,86,5,82,49,3,99,33,9,92,32,74,69,27,22,77,16,44,94,34,6,57,70,23,12,61,25,8,11,67,47,83,88,10,14,30,7,97,60,42,37,24,38,53,50,4,80,72,39};
        new BertownSubway(p);  //5416
    }
    public static  void judge()
    {
        int n=IOR.ni();
        new BertownSubway(IOR.ria(n));
    }
    public static void main(String[] args)
    {      
        judge();
    }
}