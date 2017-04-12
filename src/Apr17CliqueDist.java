
import static java.lang.System.out;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.Set;

// single-source shortest paths
class Apr17CliqueDist {
    
    static Scanner scan = new Scanner(System.in);
    public static void main(String[] args)
    {        
        scan = codechef.CodeChef.getFileScanner("cliqueDist-t.txt");
        int TC = scan.nextInt();  // between 1 and 10
        for (int i=0; i<TC; i++) {
            int N = scan.nextInt();   // 2 ≤ K ≤ N ≤ 10^5
            int K = scan.nextInt();
            long X = scan.nextLong(); // 1 to 10^9
            int M = scan.nextInt();     // 1 to 10^5 new roads
            int s = scan.nextInt();
            SSSPclique sp = new SSSPclique(N, K, X, s-1);
            for (int j=0; j<M; j++) {
                int v = scan.nextInt();  // index from 1
                int w = scan.nextInt();
                long wt = scan.nextLong();
                sp.addEdge(v-1, w-1, wt);
            }
            sp.run();
            for (int k=0; k<N; k++)
                out.print(sp.distTo(k)+" ");
            out.println();
        }
    }
}

class Edge
{
    private final int v; // one vertex
    private final int w; // the other vertex
    private final long weight; // edge weight
    public Edge(int v, int w, long weight)
    {
        this.v = v;
        this.w = w;
        this.weight = weight;
    }
    public long weight()
    { return weight; }
    public int either()
    { return v; }
    public int other(int vertex)
    {
        if (vertex == v) return w;
        else if (vertex == w) return v;
        else throw new RuntimeException("Inconsistent edge");
    }
    // direct edge
    public int from()
    { return v; }
    public int to()
    { return w; }
}

class WeightedGraph {
    //static final int MAX_NODE=1000;
    private final int   V; // number of vertices
    private int         E; // number of edges
    List<Edge>[]        adj;// adjacency lists
    //Map<String, Integer> map = new HashMap<>(MAX_NODE);

    WeightedGraph(int V)
    {
        this.V = V;
        E=0;
        adj = new List[V];  // -Xlint:unchecked
        for (int v = 0; v < V; v++) // Initialize all lists
            adj[v] = new ArrayList<>();
    }
    public int V() { return V; }
    public int E() { return E; }
    public List<Edge> adj(int v) {
        return adj[v];
    }
    public void addEdge(Edge e)
    {
        int v = e.either(), w = e.other(v);
        adj[v].add(e);
        adj[w].add(e);
        E++;
    }
    public void addDirectEdge(Edge e)
    {
        int v = e.either(), w = e.other(v);
        adj[v].add(e);
        E++;
        //out.println(" edge from "+(v+1)+" to "+(w+1)+" w="+e.weight());
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
    private final int   K; // 1 to vertices clique
    private final long  Kw; // weight between clique
    private final int   s; // source node
    private final WeightedGraph g;
    private Edge[] edgeTo;
    private long[] distTo;
    private PriorityQueue<PQItem> pq;
    
    SSSPclique(int N, int k, long w, int s)
    {
        g = new WeightedGraph(N);
        K=k;
        Kw=w;
        this.s=s;
        edgeTo = new Edge[g.V()];
        distTo = new long[g.V()];
        Comparator<PQItem> cmp = Comparator.comparingLong(a->a.weight);
        pq = new PriorityQueue<>(g.V(), cmp);
        for (int v = 0; v < g.V(); v++) {
            if (v<K && v!=s && s<K) {
                g.addDirectEdge(new Edge(s, v, w));
            }
            distTo[v] = Long.MAX_VALUE;
        }
        distTo[s] = 0;
        
        pq.add(new PQItem(s, 0));
    }
    
    private void relax(int v)
    {
        for(Edge e : g.adj(v))
        {
            int w = e.to();
            //out.println(" dist to v "+(v+1)+" is "+distTo[v]+" w="+w);
            if (distTo[w] > distTo[v] + e.weight())
            {
                distTo[w] = distTo[v] + e.weight();
                edgeTo[w] = e;
                if (pq.contains(w)) {
                    pq.remove(new Integer(w));
                }
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
        if ( v<K && s<K) {
            g.addDirectEdge(new Edge(v, w, wt)); 
        }
        else if ( w<K && s<K) {
            g.addDirectEdge(new Edge(w, v, wt)); 
        }
        else {
            g.addDirectEdge(new Edge(v, w, wt));
            g.addDirectEdge(new Edge(w, v, wt));                    
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
        //out.println("edges "+g.E());
        if ( s>=K ) { // s is not in clique
            for (int k: in) {
                for (int j : in) {
                    if (k != j)
                        g.addDirectEdge(new Edge(k, j, Kw));   
                }
            }
        }
        in.clear();// only need it once
        //out.println("edges "+g.E());   
    }

    public void run()
    {
        cliqueEdges();
        while (!pq.isEmpty())
            relax(pq.poll().v);
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
