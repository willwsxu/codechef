
import static java.lang.System.out;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// ArrayList of Interger is very slow comparing to int array
class Mar17SumDistance {
    
    static Scanner scan = new Scanner(System.in);
    static void fillArray(int [] a)
    {
        for (int i=0; i<a.length; i++) {
            a[i] = scan.nextInt();  // between 1 and 10^4
        }
    }
    // find out smallest distance from node s to t, ts=t-s
    int minDistance2(List<List<Integer>> alldist, int s, int ts)
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
        
    int minDistance(List<int[]> alldist, int s, int ts)
    {
        int t = s+ts;
        // step one node back
        int d = alldist.get(ts-2)[s]+alldist.get(0)[t-1];        
        // step two nodes back
        int d2 = alldist.get(ts-3)[s]+alldist.get(1)[t-2];
        if ( d>d2 )
            d = d2;       
        // step three nodes back
        int d3 = alldist.get(ts-4)[s]+alldist.get(2)[t-3];
        return d>d3?d3:d;
    }
    Mar17SumDistance(int N, boolean biglytest)
    {
        int [] dist1 = new int[N-1];  // weight from node 1 to 2, etc
        int [] dist2 = new int[N-2];  // weight from node 1 to 3, etc
        int [] dist3 = new int[N-3];  // weight from node 1 to 4, etc
        if (biglytest) {
            for (int i=0; i<dist1.length; i++)
                dist1[i]=i+1;
            for (int i=0; i<dist2.length; i++)
                dist2[i]=i+2;
            for (int i=0; i<dist3.length; i++)
                dist3[i]=i+3;        
        } else {
            fillArray(dist1);
            fillArray(dist2);
            fillArray(dist3);
        }
        long total=0;
        // smallest distance between note s and t
        List<int[]> alldist = new ArrayList<>(N-1);
        // case t-s=1
        for (int i=0; i<N-1; i++) {
            total += dist1[i];
        }
        alldist.add(dist1);
        // case t-s=2
        for (int i=0; i<N-2; i++) {
            int d = dist1[i]+dist1[i+1];
            if ( d<dist2[i])
                dist2[i] = d;  // update and reuse dist2
            total += dist2[i];
        }
        alldist.add(dist2);
        // case t-s=3
        for (int i=0; i<N-3; i++) {
            int d = alldist.get(1)[i]+alldist.get(0)[i+2];
            int d2 = alldist.get(0)[i]+alldist.get(1)[i+1];
            if ( d>d2)
                d=d2;
            if ( d < dist3[i])
                dist3[i]=d;
            total += dist3[i];
        }
        alldist.add(dist3);
        
        // case t-s from 4 to N-1
        for (int ts=4; ts <=N-1; ts++) {
            int []dist = new int[N-ts];
            for (int i=0; i<N-ts; i++) {
                int d = minDistance(alldist, i, ts);
                total += d;
                dist[i]=d;
            }
            alldist.add(dist);
            if ( ts>7 )
                alldist.set(ts-5, null); // clear memory as we only need first 3 rows and last three rows
        }
        /*
        for(int i=0; i<N-1; i++) {
            for (int j=0; j<alldist.get(i).size(); j++)
                total += alldist.get(i).get(j);
        }*/
        out.println(total);
    }
    static void autoTest()
    {        
        int TC = scan.nextInt();  // between 1 and 10^4
        for (int i=0; i<TC; i++) {
            int N = scan.nextInt();  // between 4 and 10^5
            new Mar17SumDistance(N, false);
        }        
    }
    static void perfTest()
    {
        Instant start = Instant.now();
        new Mar17SumDistance(100000, true);
        Instant end = Instant.now();
        out.println("usec "+ChronoUnit.MICROS.between(start, end));        
    }
    public static void main(String[] args)
    {
        autoTest();
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
*/