
import static java.lang.System.out;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// ArrayList of Integer is very slow comparing to int array
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
    Mar17SumDistance(int N)
    {
        dist1 = new int[N-1];  // weight from node 1 to 2, etc
        dist2 = new int[N-2];  // weight from node 1 to 3, etc
        dist3 = new int[N-3];  // weight from node 1 to 4, etc
        // borrow idea to pre calc shortest distance between nodes
        fillArray(dist1);
        fillArray(dist2);
        fillArray(dist3);

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
        //out.println(total);
        alldist[0] = dist1;
        // case t-s=2
        for (int i=0; i<N-2; i++) {
            int d = dist1[i]+dist1[i+1];
            if ( d<dist2[i])
                dist2[i] = d;  // update and reuse dist2
            total += dist2[i];
        }
        //out.println(total);
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
        //out.println(total);
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
    
    // find all distances from node s to N, i.e. s to s+1, s to s+2, etc
    long calc(int dist[], int s, int N)
    {    
        long total=0;  // sub total
        dist[s+1]= dist1[s]; // dist from s to s+1
        total += dist[s+1];
        if (s<N-2) {  // at least two distance (3 nodes)
            dist[s+2]=dist2[s];
            total += dist[s+2];
        }
        if (s<N-3) {  // at least 3 distance (4 nodes)
            dist[s+3]=dist3[s];
            total += dist[s+3];
        }
        Cache cache=null;
        for (int t=s+4; t<N; t++) // node 5 to N
        {
            dist[t] = dist[t-1]+dist1[t-1];
            int d2 = dist[t-2]+dist2[t-2];
            int d3 = dist[t-3]+dist3[t-3];
            if (dist[t]>d2)
                dist[t] = d2;
            if (dist[t]>d3)
                dist[t] = d3;
            total += dist[t];
            
            if (s>2 && t-s>6 && t<N-1) {  // differene of distance to dist0 would become constant
                long diff3=0;
                for (int i=0; i< caches.size(); i++) {
                    int dist0N[] = caches.get(i).dist;
                    long diff1 = dist[t]-dist0N[t];  // don't use int as it will overflow
                    long diff2 = dist[t-1]-dist0N[t-1];
                    diff3 = dist[t-2]-dist0N[t-2];
                    if ( diff1==diff2 && diff2==diff3) {
                        cache = caches.get(i);
                        break;
                    }
                }
                if (cache != null) {
                    total += cache.sumDist[t+1];
                    total += diff3*(N-1-t);
                    break;                    
                }
            }
            //loops++;
        }
        if (cache==null && N-s>20) {
            cache = new Cache();
            cache.calcSum(dist);
            caches.add(cache);
            //out.println("add cache "+s);
        }
        return total;
    }
    
    //long    loops=0;
    // save different patterns of distance difference, from 0 to N
    class Cache{
        int[]   dist;
        long[]  sumDist;
        
        void calcSum(int[] d) {
            int N = d.length;
            this.dist = new int[N];
            sumDist = new long[N];
            for (int i=0; i<N; i++)
                dist[i]=d[i];
            sumDist[N-1] = dist[N-1];
            for (int k=N-2; k>=0; k--)
                sumDist[k] = dist[k]+sumDist[k+1];
        }
    }
    List<Cache> caches = new ArrayList<>(10);
    void calcByStartingNode(int N)
    {
        // case t-s=2, pre calc shortest path from node s to s+2
        for (int i=0; i<N-2; i++) {
            int d = dist1[i]+dist1[i+1];
            if ( d<dist2[i])
                dist2[i] = d;  // update and reuse dist2
        }
        // case t-s=3, pre calc shortest path from node s to s+3
        for (int i=0; i<N-3; i++) {
            int d =  dist2[i]+dist1[i+2];
            int d2 = dist1[i]+dist2[i+1];
            if ( d>d2)
                d=d2;
            if ( d < dist3[i])
                dist3[i]=d;
        }
        int[] dist = new int[N];  // all distance from 0 to N
        long total=0;
        
        //codechef.CodeChef.writeFile(dist0N, 0, N, false);
        //codechef.CodeChef.writeFile(dist1N, 0, N, true);
        for (int s=0; s<N-1; s++) {
            total += calc(dist, s, N);
            /*if (s<4) {
                for (int m=s; m<N; m++)
                    dist[m] -= dist0N[m];
                codechef.CodeChef.writeFile(dist, 0, N, true);
            }*/
        }
        out.println(total);
        //out.println("loops "+loops+" caches "+caches.size());
    }
    static void autoTest()
    {        
        int TC = scan.nextInt();  // between 1 and 10^4
        for (int i=0; i<TC; i++) {
            int N = scan.nextInt();  // between 4 and 10^5
            new Mar17SumDistance(N);
        }        
    }
    
    //Mar 15 test3.txt: calcByStartingNode 21.4 sec, calcByInterval 35 sec, distance is 1 for all 3 types
    //Mar 15 test2.txt: calcByStartingNode 48 sec
    //Mar 16 test2.txt: <1 sec after reduce unnecessary loops, 177105986880403, loops 470390
    //Mar 16 test3.txt: 12 sec after reduce unnecessary loops, 55558888938889, loops 3333233334
    //Mar 16 test3.txt: <1 sec after checking repeat pattern of last 6, 55558888938889, loops 599968      
    public static void main(String[] args)
    {
        scan = ContestHelper.getFileScanner("sumdist-t5.txt");
        //Instant start = Instant.now();
        autoTest();
        //Instant end = Instant.now();
        //out.println("usec "+ChronoUnit.MICROS.between(start, end));       
    }    
}