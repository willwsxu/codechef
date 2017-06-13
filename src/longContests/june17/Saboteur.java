package longContests.june17;


import static java.lang.System.out;
import java.util.ArrayList;
import java.util.List;


public class Saboteur {
    SimpleGraphX g;
    Saboteur()
    {
        int N=ni();//1 ≤ N ≤ 10^4     
        int M=ni();//N - 1 ≤ M ≤ 5 * 10^4
        int[]cost=ria(N);//1 ≤ costi ≤ 10^5
        g=new SimpleGraphX(N);
        for (int i=0; i<M; i++) {
            g.addEdge(ni()-1, ni()-1);
        }
    }
    void solve()
    {
        Cycle cy = new Cycle(g);
    }
        
    public static int ni()
    {
        return sc.nextInt();
    }
    public static int[] ria(int N) { // read int array
        int L[]=new int[N];
        for (int i=0; i<N; i++)
            L[i]=sc.nextInt();
        return L;
    }
    //static Scanner sc = new Scanner(System.in);  too slow for large input
    static MyScanner sc=new MyScanner();
    public static void main(String[] args)
    {   
        new Saboteur().solve();   
    }
}

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

// Sedgewick
class Cycle
{
    private boolean[] marked;
    private boolean hasCycle;
    public Cycle(IGraphX G)
    {
        marked = new boolean[G.V()];
        for (int s = 0; s < G.V(); s++)
            if (!marked[s])
                dfs(G, s, s);
    }
    private void dfs(IGraphX G, int v, int u)
    {
        marked[v] = true;
        for (int w : G.adj(v))
            if (!marked[w])
                dfs(G, w, v);
            else if (w != u) {
                out.println("cycle "+w+" "+v+" parent="+u);
                hasCycle = true;
            }
    }
    public boolean hasCycle()
    { return hasCycle; }
}

class CCx
{
    private IGraphX g;
    private int visId[];  // dual purpose, for component id and is visited
    private int id=0;
    
    public CCx(IGraphX g)
    {
        this.g=g;
        visId = new int[g.V()];
        for (int s = 0; s < g.V(); s++)
            // no need to visit nodes without any edge
            if (visId[s]==0 && !g.adj(s).isEmpty())
            {
                dfs(s, ++id);
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
    public int numCompoments()  // total disjoin components
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