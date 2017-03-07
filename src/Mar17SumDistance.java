
import static java.lang.System.out;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


class Mar17SumDistance {
    
    static Scanner scan = new Scanner(System.in);
    static void fillArray(int [] a)
    {
        for (int i=0; i<a.length; i++) {
            a[i] = scan.nextInt();  // between 1 and 10^4
        }
    }
    Mar17SumDistance(int N)
    {
        int [] dist1 = new int[N-1];  // weight from node 1 to 2, etc
        int [] dist2 = new int[N-2];  // weight from node 1 to 3, etc
        int [] dist3 = new int[N-3];  // weight from node 1 to 4, etc
        fillArray(dist1);
        fillArray(dist2);
        fillArray(dist3);
        // smallest distance between note s and t
        List<List<Integer>> alldist = new ArrayList<>();
        // case t-s=1
        List<Integer> dist = new ArrayList<>();
        for (int i=0; i<N-1; i++)
            dist.add(dist1[i]);
        alldist.add(dist);
        // case t-s=2
        dist = new ArrayList<>();
        for (int i=0; i<N-2; i++) {
            int d = dist1[i]+dist1[i+1];
            dist.add(d>dist2[i]?dist2[i]:d);
        }
        alldist.add(dist);
        // case t-s=3
        dist = new ArrayList<>();
        for (int i=0; i<N-3; i++) {
            int d1 = alldist.get(1).get(i)+alldist.get(0).get(i+2);
            int d2 = alldist.get(0).get(i)+alldist.get(1).get(i+1);
            int d = d1>d2?d2:d1;
            dist.add(d>dist3[i]?dist3[i]:d);
        }
        alldist.add(dist);
        // case t-s from 4 to N-1
        for (int ts=4; ts <=N-1; ts++) {
            dist = new ArrayList<>();
            for (int i=0; i<N-ts; i++) {
                int d1 = alldist.get(ts-2).get(i)+alldist.get(0).get(i+ts-1);
                int d2 = alldist.get(ts-3).get(i)+alldist.get(1).get(i+ts-2);
                int d = d1>d2?d2:d1;
                int d3 = alldist.get(ts-4).get(i)+alldist.get(2).get(i+ts-3);
                dist.add(d>d3?d3:d);
            }
            alldist.add(dist);
        }
        long total=0;
        for(int i=0; i<N-1; i++) {
            for (int j=0; j<alldist.get(i).size(); j++)
                total += alldist.get(i).get(j);
        }
        out.println(total);
    }
    public static void main(String[] args)
    {
        int TC = scan.nextInt();
        for (int i=0; i<TC; i++) {
            int N = scan.nextInt();  // between 4 and 10^5
            new Mar17SumDistance(N);
        }        
    }    
}
