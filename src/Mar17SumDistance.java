
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
    // find out smallest distance from node s to t, ts=t-s
    int minDistance(List<List<Integer>> alldist, int s, int ts)
    {
        int t = s+ts;
        // step one node back
        int d = alldist.get(ts-2).get(s)+alldist.get(0).get(t-1);        
        // step two nodes back
        int d2 = alldist.get(ts-3).get(s)+alldist.get(1).get(t-2);
        if ( d>d2 )
            d = d2;       
        // step three nodes back
        int d3 = alldist.get(ts-4).get(s)+alldist.get(2).get(t-3);
        return d>d3?d3:d;
    }
    Mar17SumDistance(int N)
    {
        int [] dist1 = new int[N-1];  // weight from node 1 to 2, etc
        int [] dist2 = new int[N-2];  // weight from node 1 to 3, etc
        int [] dist3 = new int[N-3];  // weight from node 1 to 4, etc
        fillArray(dist1);
        fillArray(dist2);
        fillArray(dist3);
        long total=0;
        // smallest distance between note s and t
        List<List<Integer>> alldist = new ArrayList<>();
        // case t-s=1
        List<Integer> dist = new ArrayList<>();
        for (int i=0; i<N-1; i++) {
            dist.add(dist1[i]);
            total += dist1[i];
        }
        alldist.add(dist);
        // case t-s=2
        dist = new ArrayList<>();
        for (int i=0; i<N-2; i++) {
            int d = dist1[i]+dist1[i+1];
            if ( d>dist2[i])
                d = dist2[i];
            total += d;
            dist.add(d);
        }
        alldist.add(dist);
        // case t-s=3
        dist = new ArrayList<>();
        for (int i=0; i<N-3; i++) {
            int d = alldist.get(1).get(i)+alldist.get(0).get(i+2);
            int d2 = alldist.get(0).get(i)+alldist.get(1).get(i+1);
            if ( d>d2)
                d=d2;
            if ( d > dist3[i])
                d = dist3[i];
            total += d;
            dist.add(d);
        }
        alldist.add(dist);
        // case t-s from 4 to N-1
        for (int ts=4; ts <=N-1; ts++) {
            dist = new ArrayList<>();
            for (int i=0; i<N-ts; i++) {
                int d = minDistance(alldist, i, ts);
                total += d;
                dist.add(d);
            }
            alldist.add(dist);
        }
        /*
        for(int i=0; i<N-1; i++) {
            for (int j=0; j<alldist.get(i).size(); j++)
                total += alldist.get(i).get(j);
        }*/
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
/*
3
4
1 1 1
1 1
1
5
1 2 3 4
2 3 4
3 4
6
1 2 3 4 5
2 3 4 5
3 4 5
4 5
*/