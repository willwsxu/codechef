package smackdown.roundb;


import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Scanner;

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
class DSU
{
    private IGraphX g;
    private int visId[];  // dual purpose, for component id and is visited
    private int id=0;
    PriorityQueue<Map.Entry<Integer, Integer>> degree = new PriorityQueue<>(100000, (e1,e2)->e2.getValue()-e1.getValue());
    public DSU(IGraphX g)
    {
        this.g=g;
        visId = new int[g.V()];
        for (int s = 0; s < g.V(); s++)
            // no need to visit nodes without any edge
            if (!g.adj(s).isEmpty())
            {
                degree.add(new SimpleEntry(s,g.adj(s).size()));
            }
    }
    public void dfs(int d)
    {
        for (Map.Entry<Integer, Integer> e:degree) {
            if (e.getValue()>d && visId[e.getKey()]==0)
                dfs(e.getKey(), ++id);
        }
    }
    private void dfs(int v, int id) {
        //out.println("dfs v"+v+" id="+id);
        visId[v]=id;
        for (int w: g.adj(v))
            if (visId[w]==0)
                dfs(w, id);
    }
    public int numCompoments(int ID)  // components with id=ID
    {
        int count=0;
        for (int s = 0; s < g.V(); s++)
            if (visId[s]==ID)
                count++;
        //out.println("singleGraph "+id);
        return count;
    }    
    public int numCompoments()
    {
        int count=0;
        for (int s = 0; s < g.V(); s++)
            if (visId[s]==0)
                count++;
        //out.println("singleGraph "+id);
        return count+id;
    }
    public boolean connected(int v, int w)
    {
        return visId[v]==visId[w] && visId[v]!=0;
    }
}

// SNGraph, Medium, DSU (Disjoint Set Union)
class SnakeGraph {
    SnakeGraph()
    {
        int n=sc.nextInt(); // 1 ≤ n ≤ 10^5
        int m=sc.nextInt();//0 ≤ m ≤ min(n * (n - 1) / 2, 2 * 10^5)
        SimpleGraphX g=new SimpleGraphX(n);
        for (int j=0; j<m; j++) {
            int u=sc.nextInt();
            int v=sc.nextInt();
            g.addEdge(u-1, v-1);
        }
    }
    static void solve(SimpleGraphX g)
    {
        DSU cc=new DSU(g);
        StringBuilder sb = new StringBuilder();
        sb.append(cc.numCompoments()-1);
        for (int i=1; i<g.V(); i++) {
            
        }        
    }
   
    static void manualtest()
    {
    }
    
    static Scanner sc = new Scanner(System.in);
    public static void main(String[] args)
    {      
        int T=sc.nextInt();     // 1 ≤ T ≤ 3
        while (T-->0)
            new SnakeGraph();
    } 
}
