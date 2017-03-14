
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
    int dist1[];
    int [] dist2;
    int [] dist3;
    int [][] alldist;
    // find out smallest distance from node s to t, ts=t-s   
    int minDistance2(int[][] alldist, int s, int ts)
    {
        int t = s+ts;
        // step one node back
        int d = alldist[ts-2][s]+dist1[t-1]; 
        // step two nodes back
        int d2 = alldist[ts-3][s]+dist2[t-2];
        if ( d>d2 )
            d = d2; 
        // step three nodes back
        int d3 = alldist[ts-4][s]+dist3[t-3];
        return d>d3?d3:d;
    }  
    Mar17SumDistance(int N, boolean biglytest)
    {
        dist1 = new int[N-1];  // weight from node 1 to 2, etc
        dist2 = new int[N-2];  // weight from node 1 to 3, etc
        dist3 = new int[N-3];  // weight from node 1 to 4, etc
        if (biglytest) {
            // borrow idea to pre calc shortest distance between nodes
            for (int i=0; i<dist1.length; i++)
                dist1[i]=i+1;
            for (int i=0; i<dist2.length; i++) {
                dist2[i]=i+2;
            }
            for (int i=0; i<dist3.length; i++)
                dist3[i]=i+3;        
        } else {
            fillArray(dist1);
            fillArray(dist2);
            fillArray(dist3);
        }
        //calcByInterval(N);
        calcByStartingNode(N);
    }
    
    void calcByInterval(int N)
    {
        long total=0;
        // smallest distance between note s and t
        //List<int[]> alldist = new ArrayList<>(N-1);
        alldist = new int[N-1][];
        // case t-s=1
        for (int i=0; i<N-1; i++) {
            total += dist1[i];
        }
        alldist[0] = dist1;
        // case t-s=2
        for (int i=0; i<N-2; i++) {
            int d = dist1[i]+dist1[i+1];
            if ( d<dist2[i])
                dist2[i] = d;  // update and reuse dist2
            total += dist2[i];
        }
        alldist[1] = dist2;
        // case t-s=3
        for (int i=0; i<N-3; i++) {
            int d = alldist[1][i]+alldist[0][i+2];
            int d2 = alldist[0][i]+alldist[1][i+1];
            if ( d>d2)
                d=d2;
            if ( d < dist3[i])
                dist3[i]=d;
            total += dist3[i];
        }
        alldist[2] = dist3;
        
        // case t-s from 4 to N-1
        for (int ts=3; ts <7; ts++) {
            if (ts>N-2)
                break;
            alldist[ts] = new int[N-ts];
        }
        for (int ts=4; ts <=N-1; ts++) {
            if ( ts>7 )
                alldist[ts-1] = alldist[ts-5]; // reuse memory as we only need first 3 rows and last three rows
            for (int i=0; i<N-ts; i++) {  
                alldist[ts-1][i] = minDistance2(alldist, i, ts); // minDistance take 9 sec when N=100000
                total += alldist[ts-1][i];
            }
        }
        /*else {  // this change made performance worse, why?
            for (int ts=4; ts < 8; ts++) {
                int size = N-ts;
                dist = new int[size];
                for (int i=0; i<size; i++) {
                    dist[i] = minDistance(alldist, i, ts);
                    total += dist[i];
                }
                alldist.add(dist);
            }
            for (int ts=8; ts <=N-1; ts++) {
                int size = N-ts;
                dist = alldist.get(ts-5); // reuse memory as we only need first 3 rows and last three rows
                for (int i=0; i<size; i++) {
                    dist[i] = minDistance(alldist, i, ts);
                    total += dist[i];
                }
                alldist.add(dist);
            }
        }*/
        out.println(total);
    }
    
    // node 0 to N-1
    void calcByStartingNode(int N)
    {
        int[] dist = new int[N-1];
        long total=0;
        for (int s=0; s<N-1; s++) {
            dist[s]= dist1[s];
            total += dist[s];
            if (s<N-2) {  // at leat two distance (3 nodes)
                dist[s+1] = dist1[s]+dist1[s+1];
                if ( dist[s+1]>dist2[s])
                    dist[s+1]=dist2[s];
                total += dist[s+1];
            }
            if (s<N-3) {  // at leat 3 distance (4 nodes)
                dist[s+2] = dist[s+1]+dist1[s+2];
                int d2 = dist1[s]+dist2[s+1];
                int d3 = dist1[s]+dist1[s+1]+dist1[s+2];
                if (dist[s+2]>d2)
                    dist[s+2] = d2;
                if (dist[s+2]>d3)
                    dist[s+2] = d3;
                if ( dist[s+2]>dist3[s])
                    dist[s+2]=dist3[s];
                total += dist[s+2];
            }
            for (int t=s+4; t<N; t++) // node 5 to N
            {   //clac dist[t-1]
                dist[t-1] = dist[t-2]+dist1[t-1];
                int d2 = dist1[t-3]+dist2[t-2];
                int d3 = dist1[t-3]+dist1[t-2]+dist1[t-1];
                if (dist[t-1]>d2)
                    dist[t-1] = d2;
                if (dist[t-1]>d3)
                    dist[t-1] = d3;
                if ( dist[t-1]>dist3[s])
                    dist[t-1]=dist3[s];
                total += dist[t-1];                
            }
        }
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
        //perfTest();
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