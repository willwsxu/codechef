package longContests.ChallengeApr17;



import codechef.Edge;
import codechef.GraphWeighted;
import codechef.MyScanner;
import static java.lang.System.out;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;

// single-source shortest paths, Dijkstra's algorithm
// easy
class Apr17CliqueDist {
    static MyScanner scan = new MyScanner();
    static void testAddEdge()
    {
        Instant start = Instant.now();
        GraphWeighted g = new GraphWeighted(1000000);
        for (int i=0; i<g.V()-30; i++)
        {
            for (int j=i+1; j<=i+30; j++)
                g.addDirectEdge(i, j, i);
        }
        Instant mid0 = Instant.now();
        out.println("testAddEdge usec "+ChronoUnit.MICROS.between(start, mid0));    
    }
    static int testClique=500;
    static int discard=0;
    static void addTest(SSSPclique sp, int v, int w, long wt, int k)
    {        
        v += k-testClique;
        w += k-testClique;
        if (v<k && w<k) {
            discard++;
            return;
        }
        sp.addEdge(v, w, wt);
    }
    public static void main(String[] args)
    {        
        //Instant start = Instant.now();
        //codechef.ContestHelper.redirect("out.txt");
        
        int TC = scan.ni();  // between 1 and 3
        for (int i=0; i<TC; i++) { 
            int N = scan.ni();   // 2 ≤ K ≤ N ≤ 10^5
            int K = scan.ni();
            long X = scan.nl(); // 1 to 10^9
            int M = scan.ni();     // 1 to 10^5 new roads
            int s = scan.ni();
            // test
            //N += K-testClique;
            SSSPclique sp = new SSSPclique(N, K, X, s-1);
            for (int j=0; j<M; j++) {
                int v = scan.ni();  // index from 1
                int w = scan.ni();
                long wt = scan.nl();
                //addTest(sp, v, w, wt, K);
                sp.addEdge(v-1, w-1, wt);
            }
            //out.println("discard edges "+discard); //test
            sp.run();
            for (int k=0; k<N; k++) {
                out.print(sp.distTo(k));
                if (k<N-1)
                    out.print(" ");
            }
            out.println();
            //Instant end = Instant.now();
            //out.println("usec "+ChronoUnit.MICROS.between(start, end));  
            //out.println("case #"+(i+1));
        }
    }
}

// shortest path from s, clique 1 to k with same weight
// vertex is 0 based
// modified from Dijkstra algorithm
class SSSPclique
{
    class PQItem{
        int v;
        long weight;
        PQItem(int v, long w)
        {
            this.v = v;
            weight=w;
        }
        public long getWeight() {
            return weight;
        }
        @Override
        public boolean equals(Object o)
        {
            if ( !(o instanceof Integer))
                return false;
            Integer w = (Integer)o;
            return v==w;
        }
    }
    private final int   K; // clique, 2 to vertices
    private final long  Kw; // weight between clique vertices
    private final int   s; // source node
    private final GraphWeighted g;
    private Edge[] edgeTo;
    private long[] distTo;
    private PriorityQueue<PQItem> pq;
    
    // s, v, w index from 0
    SSSPclique(int N, int k, long wt, int s)
    {
        g = new GraphWeighted(N);
        K=k;
        Kw=wt;
        this.s=s;
        edgeTo = new Edge[g.V()];
        distTo = new long[g.V()];
        Comparator<PQItem> cmp = Comparator.comparingLong(a->a.weight);
        pq = new PriorityQueue<>(g.V()*2, cmp);
        for (int v = 0; v < g.V(); v++) {
            if (v<K && v!=s && s<K) {
                g.addDirectEdge(s, v, wt);
            }
            distTo[v] = Long.MAX_VALUE;
        }
        distTo[s] = 0;
        
        pq.add(new PQItem(s, 0));
    }
    
    private void relax(PQItem pqi)
    {
        int v = pqi.v;
        if (distTo[v] != pqi.getWeight())  // ignore dup node
            return;
        for(Edge e : g.adj(v))
        {
            int w = e.to();
            //out.println(" dist to v "+(v+1)+" is "+distTo[v]+" w="+w);
            if (distTo[w] > distTo[v] + e.weight())
            {
                distTo[w] = distTo[v] + e.weight();
                edgeTo[w] = e;
                //if (pq.contains(w)) {
                //    pq.remove(new Integer(w));
                //}
                // don't bother to delete old one, 10% better performance
                pq.add(new PQItem(w, distTo[w]));
                //out.println("update dist to w "+(w+1)+" is "+distTo[w]);
            }
        }
    }
    public long distTo(int v) // standard client query methods
    {
        return distTo[v];
    }
    
    Set<Integer> in = new HashSet<>();
    public void addEdge(int v, int w, long wt)
    {            
        if ( v==s && s<K) {
            g.addDirectEdge(v, w, wt); 
        }
        else if ( w==s && s<K) {
            g.addDirectEdge(w, v, wt); 
        }
        else {
            g.addDirectEdge(v, w, wt);
            g.addDirectEdge(w, v, wt);                    
        }
        if ( s>= K) {
            if ( v<K )
                in.add(v);
            else if ( w<K )
                in.add(w);
        }
    }
    private void cliqueEdges()
    {
        out.println("edges "+g.E());
        if ( s>=K ) { // s is not in clique
            for (int k: in) {
                for (int j : in) {
                    if (k != j)
                        g.addDirectEdge(k, j, Kw);   
                }
            }
        }
        in.clear();// only need it once
        out.println("edges "+g.E());   
    }

    private void runClique()
    {
        if (s<K)
            return;
        Comparator<PQItem> cmp = Comparator.comparingLong(a->a.weight);
        PriorityQueue<PQItem> pqc = new PriorityQueue<>(K, cmp); 
        for (int j : in) {       
            pqc.add(new PQItem(j, distTo[j]));
        }
        PQItem minQ = pqc.poll();
        while (!pqc.isEmpty()) {
            PQItem next = pqc.poll();
            if (next.weight<=minQ.weight+Kw)
                continue;
            distTo[next.v] = minQ.weight+Kw;
            pq.add(new PQItem(next.v, distTo[next.v]));
            while (!pq.isEmpty()) {
                relax(pq.poll());
            }
        }
    }
    public void run()
    {
        //cliqueEdges();
        while (!pq.isEmpty()) {
            relax(pq.poll());
        }
        runClique();
        long minClique=Long.MAX_VALUE;
        for (int v = 0; v < K; v++) {
            if ( distTo[v] < minClique)
                minClique = distTo[v];
        }
        for (int v = 0; v < K; v++) {
            if ( distTo[v] ==Long.MAX_VALUE)
                distTo[v] = minClique +Kw;
        }
    }
}
