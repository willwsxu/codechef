package smackdown.roundb;


import static java.lang.System.out;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.Stack;

interface IGraphX
{
    int V();
    public List<Integer> adj(int u);
}

class SimpleGraphX implements IGraphX { // unweighted, bidirectional
    protected int   V; // number of vertices
    private   int   E; // number of edges

    private List<List<Integer>> adj;
    public SimpleGraphX(int V)
    {
        adj=new ArrayList<>(V);
        this.V = V;
        E=0;
        for (int v = 0; v < V; v++) // Initialize all lists
            adj.add( new ArrayList<>(10));
    }
    public int V() { return V; }
    public int E() { return E; }
    
    public void addEdge(int u, int v)
    {
        //out.println("add edge "+u+","+v);
        adj.get(u).add(v);
        adj.get(v).add(u);
        E++;
    }
    public List<Integer> adj(int u)
    {
        return adj.get(u);
    }
}

// only process edge if both nodes has degree>d
class DSU //Union Find
{
    private IGraphX g;
    private int sz[];  // dual purpose, for component id and is visited
    private int id[];
    private int comp;
    public DSU(IGraphX g)
    {
        this.g=g;
        sz = new int[g.V()];
        id = new int[g.V()];
        Arrays.fill(sz, 1);
        comp=g.V();
        for (int s = 0; s < g.V(); s++)
            id[s]=s;
    }
    int find(int p) {
        while (p!=id[p])
            p=id[p];
        return p;
    }
    void union(int u, int v)
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

// SNGraph, Medium, DSU (Disjoint Set Union)
class SnakeGraph {
    SimpleGraphX g;
    SnakeGraph()
    {
        int n=sc.nextInt(); // 1 ≤ n ≤ 10^5
        int m=sc.nextInt();//0 ≤ m ≤ min(n * (n - 1) / 2, 2 * 10^5)
        g=new SimpleGraphX(n);
        for (int j=0; j<m; j++) {
            int u=sc.nextInt();
            int v=sc.nextInt();
            g.addEdge(u-1, v-1);
        }
    }
    void solve()
    {
        PriorityQueue<Map.Entry<Integer, Integer>> nodes = new PriorityQueue<>(100000, (e1,e2)->e2.getValue()-e1.getValue());
        
        for (int s = 0; s < g.V(); s++) {
            // no need to visit nodes without any edge
            if (!g.adj(s).isEmpty())
            {
                nodes.add(new SimpleEntry<>(s,g.adj(s).size()));
            }
        }
        DSU cc=new DSU(g);
        Stack<Integer> ans=new Stack<>();
        for (int d=g.V()-1; d>=0; d--) {
            ans.add(cc.numCompoments()-1);
            while (!nodes.isEmpty()) {
                Map.Entry<Integer, Integer> e=nodes.peek();
                if (e.getValue()<d)
                    break;
                for (int w: g.adj(e.getKey())) {
                    if (g.adj(w).size()<d)
                        continue;
                    cc.union(w, e.getKey());
                }
                nodes.poll();
            }
        } 
        StringBuilder sb = new StringBuilder();    
        for (int x:ans)   {
            sb.append(x);
            sb.append(" ");
        }
        out.println(sb.toString());
    }
   
    static void manualtest()
    {
    }
    
    static Scanner sc = new Scanner(System.in);
    public static void main(String[] args)
    {      
        int T=sc.nextInt();     // 1 ≤ T ≤ 3
        while (T-->0)
            new SnakeGraph().solve();
    } 
}
