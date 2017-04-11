
import static java.lang.System.out;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Scanner;

// single-source shortest paths
class Apr17CliqueDist {
    
    static Scanner scan = new Scanner(System.in);
    public static void main(String[] args)
    {        
        //scan = codechef.CodeChef.getFileScanner("dishLife0417.txt");
        int TC = scan.nextInt();  // between 1 and 10
        for (int i=0; i<TC; i++) {
            int N = scan.nextInt();   // 2 ≤ K ≤ N ≤ 10^5
            int K = scan.nextInt();
            long X = scan.nextLong(); // 1 to 10^9
            int M = scan.nextInt();     // 1 to 10^5 new roads
            int s = scan.nextInt();
            WeightedGraph g = new WeightedGraph(N);
            for (int j=0; j<M; j++) {
                int v = scan.nextInt();
                int w = scan.nextInt();
                long wt = scan.nextLong();
                g.addDirectEdge(new Edge(v-1, w-1, wt)); // index from 0
            }
            SSSPclique sp = new SSSPclique(g, K, X, s-1);
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
        adj[w].add(new Edge(w, v, e.weight()));
        E++;
    }
}

// shortest path from s, clique 1 to k with same weight
// vertex is 0 based
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
    private final WeightedGraph g;
    private Edge[] edgeTo;
    private long[] distTo;
    private PriorityQueue<PQItem> pq;
    SSSPclique(WeightedGraph g, int k, long w, int s)
    {
        this.g = g;
        K=k;
        Kw=w;
        edgeTo = new Edge[g.V()];
        distTo = new long[g.V()];
        Comparator<PQItem> cmp = Comparator.comparingLong(a->a.weight);
        pq = new PriorityQueue<>(g.V(), cmp);
        for (int v = 0; v < g.V(); v++) {
            if (v<K)
                distTo[v] = w;
            else
                distTo[v] = Long.MAX_VALUE;
        }
        distTo[s] = 0;
        
        pq.add(new PQItem(s, 0));
        while (!pq.isEmpty())
            relax(pq.poll().v);
    }
    
    private void relax(int v)
    {
        for(Edge e : g.adj(v))
        {
            int w = e.to();
            if (distTo[w] > distTo[v] + e.weight())
            {
                distTo[w] = distTo[v] + e.weight();
                edgeTo[w] = e;
                if (pq.contains(w)) {
                    pq.remove(new Integer(w));
                }
                pq.add(new PQItem(w, distTo[w]));
            }
        }
    }
    public long distTo(int v) // standard client query methods
    {
        return distTo[v];
    }
}
