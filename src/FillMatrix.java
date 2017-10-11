
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import static java.lang.System.out;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

// Sept 2017 long challenge, Easy medium
// disjoint set union, bipartite graph, spanning forest
// A NxN matrix B (of integers) is said to be good if there exists an array A such that B[i][j] = |A[i] - A[j]|, where |x| denotes absolute value of integer x.
// Give Q entries of B with values 0 or 1, is it possible to have valid B to fill rest of entries with any values
// Idea
// The answer is "yes" if and only if you can assign every number in [1,n][1,n] even or odd, such that:
//   When Bi,j=0, i and j have the same label;
//   When Bi,j=1, i and j have different label.
// Use DSU to check if i and j is already connected, directly or indirectly
// use dfs to calculate parity, set first to 0, then per Bij weight
// if there is redundant edges left, we need to check if it conflicts exisitng parity
//   smart use of ^ to calculate parity
class FillMatrix {
    
    int parity[];
    int q[];
    DSUx dsu;
    GraphWeightedX g;
    List<EdgeX>  edges=new ArrayList<>();
    FillMatrix()
    {
        init(sc.ni());
        int Q=sc.ni();
        q=sc.ria(3*Q);
        //out.println(Arrays.toString(q));
    }
    FillMatrix(int n, int q[])
    {
        init(n);
        this.q=q;
    }
    private void init(int n)
    {
        parity=new int[n];
        Arrays.fill(parity,-1);
        dsu =new DSUx(n);
        g=new GraphWeightedX(n);        
    }
    boolean solve()
    {
        int Q=q.length/3;
        for (int k=0; k<Q; k++) {
            int i=3*k;
            int j=3*k+1;
            int v=3*k+2;
            if (q[i]==q[j]) {
                if (q[v]!=0) {
                    //out.println("i="+q[i]+" j="+q[j]+" not zero.k="+k);
                    return false;
                }
            } else {
                if (dsu.find(q[i]-1) != dsu.find(q[j]-1)) {
                    dsu.union(q[i]-1, q[j]-1);
                    g.addEdge(new EdgeX(q[i]-1, q[j]-1, q[v]));
                }
                else {
                    edges.add(new EdgeX(q[i]-1, q[j]-1, q[v]));
                }
            }
        }
        if (edges.isEmpty())
            return true;
        for (int i=0; i<parity.length; i++)
            dfs(i, -1, 0);   // in case graph is not al connected
        //out.println(Arrays.toString(parity));
        //out.println(edges.size());
        for (EdgeX e: edges) {
            int u=e.from();
            int v=e.to();
            if ((parity[u] ^ parity[v] ^ (int)e.weight())==1)
                return false;
        }
        return true;
    }
    
    void dfs(int u, int parent, int weight)
    {
        if (parity[u]>=0 || g.adj(u).isEmpty()) // already visited, or not connected vertex
            return;
        if ( parent ==-1) {
            //out.println("new tree "+(u+1));
            parity[u]=0;  // set default to even
        }
        else
            parity[u] = parity[parent] ^ weight;  // weight =0, same parity, else opposite parity
        for (EdgeX v: g.adj(u))    {
            dfs(v.other(u), u, (int)v.weight());
        }         
    }
    
    public static void test()
    {
        out.println(new FillMatrix(3, new int[]{1,2,1,2,3,1,1,3,1}).solve()==false);
        out.println(new FillMatrix(3, new int[]{1,2,1,2,3,1,1,3,0}).solve()==true);
        out.println(new FillMatrix(4, new int[]{1,2,1,2,3,1,1,4,1}).solve()==true);
        out.println(new FillMatrix(10, new int[]{1,2,1,2,3,1,1,4,1,2,4,1}).solve()==false);
        out.println(new FillMatrix(10, new int[]{1,2,1,2,3,1,5,4,1,4,5,0}).solve()==false);//not connected graph
    }
    public static void judge()
    {
        int T=sc.ni();
        while (T-->0) {
            out.println(new FillMatrix().solve()?"yes":"no");
        }        
    }
    static MyScannerX sc=new MyScannerX();
    public static void main(String[] args)
    {    
        judge();
    }
}



class MyScannerX {
    BufferedReader br;
    StringTokenizer st;

    public MyScannerX() {
        br = new BufferedReader(new InputStreamReader(System.in));
    }

    String next() {
        while (st == null || !st.hasMoreElements()) {
            try {
                st = new StringTokenizer(br.readLine());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return st.nextToken();
    }

    int nextInt() {
        return Integer.parseInt(next());
    }

    long nextLong() {
        return Long.parseLong(next());
    }

    double nextDouble() {
        return Double.parseDouble(next());
    }

    String nextLine(){
        String str = "";
        try {
           str = br.readLine();
        } catch (IOException e) {
           e.printStackTrace();
        }
        return str;
    }
    
    public int ni()
    {
        return nextInt();
    }     
    public long nl()
    {
        return nextLong();
    }   
    public int[] ria(int N) { // read int array
        int L[]=new int[N];
        for (int i=0; i<N; i++)
            L[i]=nextInt();
        return L;
    }
    public long[] rla(int N) { // read long array
        long L[]=new long[N];
        for (int i=0; i<N; i++)
            L[i]=nextLong();
        return L;
    }
}

class DSUx //Union Find
{
    private int sz[];  // dual purpose, for component id and is visited
    private int id[];
    private int comp;
    public DSUx(int n)
    {
        sz = new int[n];
        id = new int[n];
        Arrays.fill(sz, 1);
        comp=n;
        for (int s = 0; s < n; s++)
            id[s]=s;
    }
    public int find(int p) {
        while (p!=id[p])
            p=id[p];
        return p;
    }
    public void union(int u, int v)
    {
        u=find(u);
        v=find(v);
        if (u==v)
            return;
        if (sz[u]<sz[v]) {  // add small component to larger one
            id[u]=v;
            sz[v] += sz[u];
        } else {
            id[v]=u;
            sz[u] += sz[v];            
        }
        comp--;
    }
    
    public int numCompoments()  // components with id=ID
    {
        return comp;
    }    
    public boolean connected(int v, int w)
    {
        return find(w)==find(v);
    }
}

class GraphWeightedX {
    //static final int MAX_NODE=1000;
    private final int   V; // number of vertices
    private int         E; // number of edges
    List<List<EdgeX>>        adj;// adjacency lists

    public GraphWeightedX(int V)
    {
        this.V = V;
        E=0;
        adj = new ArrayList<>(V);
        for (int v = 0; v < V; v++) // Initialize all lists
            adj.add( new ArrayList<>(10));
    }
    public int V() { return V; }
    public int E() { return E; }
    public List<EdgeX> adj(int v) {
        return adj.get(v);
    }
    public void addEdge(EdgeX e) // undirected
    {
        int v = e.either(), w = e.other(v);
        adj(v).add(e);
        adj(w).add(e);
        E++;
    }
}
class EdgeX
{
    private final int v; // one vertex
    private final int w; // the other vertex
    private final long weight; // edge weight
    public EdgeX(int v, int w, long weight)
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