package LunchTime.may2017;


import static java.lang.Long.min;
import static java.lang.System.out;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/*
 * Given a tree with each node having some value. You need to find the cost of 
 * path from each node v to the root of the tree. Cost of each node u in path is 
 * the minimum of value of each node lying below u and is in path from (u, v).
 */

// POSTTREE, EASY (more like MEDIUM)
// Binary Lifting on a tree, lowest common ancestor, DFS
// https://discuss.codechef.com/questions/99371/posttree-editorial
// http://codeforces.com/blog/entry/22325
// http://codeforces.com/topic/22414/en11
class PostTree {
    
    long cn[];// cost of node
    long cp[];// cost of path
    int parent[];
    int w[];
    int level[]=new int[10000];
    PostTree(int parent[], int w[])
    {
        this.parent=parent;
        this.w=w;
        //cost(w.length-1);
        //init();
        g = new TreePath(parent, w);
        print(g.cp);
    }
    PostTree()
    {
        int N=sc.nextInt(); // 1 ≤ N ≤ 100,000
        parent=new int[N];
        parent[0]=-1;
        for (int i=1; i<N; i++) { 
            parent[i]=sc.nextInt()-1;
        }
        w=ria(N, sc); //-1,000,000,000 ≤ Av ≤ 1,000,000,000
        //init();
        //out.println(Arrays.toString(parent));
        g = new TreePath(parent, w);
        print(g.cp);
    }
    TreePath g;
    /*
    private void init()
    {
        int N=w.length;
        cn=new long[N];
        for (int i=0; i<N; i++)
            cn[i]=w[i];    
        g = new SimpleGraphX(N);
        for (int i=1; i<N; i++) { 
            g.addEdge(i, parent[i]);
        }
        cp=new long[N];
        cp[0]=cn[0];
        vis=new boolean[N];
        dfs(0, 0);
        print(cp);
    }
    */
    void print(long x[])
    {
        for (int i=0; i<x.length;i++)
            out.print(x[i]+" ");
        out.println();        
    }
    /*
    boolean vis[];
    void dfs(int v, int lvl) {
        vis[v]=true;
        level[v]=lvl;
        if ( v>0) {
            int p=parent[v];
            while (p>=0 && w[p]>w[v] )
                p=parent[p];
            if (p<0)
                cp[v]=0;
            else
                cp[v]=cp[p];
            cp[v] += (level[v]-level[p])*w[v] ;
            //out.println((v+1)+"="+cp[v]+" p="+p);
        }
        for (int w: g.adj(v))
            if (!vis[w])
                dfs(w, lvl+1);
    }
    void update(int p)
    {
        while (p>1) {
            int g=parent[p];
            if (cn[g]>cn[p])
                cn[g]=cn[p];
            else
                break;
        }
    }
    void cost(int n)
    {
        update(n);
        if (n>1)
            cost(n-1);
        //out.println("n="+n+" c "+c[n]);
    }
     */ 
    public static int[] ria(int N, Scanner sc) { // read int array
        int L[]=new int[N];
        for (int i=0; i<N; i++)
            L[i]=sc.nextInt();
        return L;
    }
    
    static Scanner sc = new Scanner(System.in);
    public static void main(String[] args)
    {      
        new PostTree();
    }
}
/*
8
1 1 1 1 5 8 6
1 2 3 4 5 15 70 10
ans
1 3 4 5 6 21 96 26
*/

interface IGraphX
{
    int V();
    public List<Integer> adj(int u);
}

// vertex from 0
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

class TreePath extends SimpleGraphX
{
    int r; // root
    int parent[]; // parent node, first is root
    int pw[];// weight from current node to parent
    int wt[];// weight of node
    int L[]; // levels, L[r]=0, L[u]=L[p[u]]+1
    long cp[];// cost of path
    TreePath(int[]p, int []w)
    {
        super(p.length);
        parent=p;
        L=new int[V()];
        for (int i=1; i<V(); i++) { 
            addEdge(i, p[i]);
        }
        wt=w;
        cp=new long[V()];
        cp[0]=w[0];
        dfs(0,0);
    }
    void dfs(int v, int lvl) {
        L[v]=lvl;
        if ( v>0) {
            int p=parent[v];
            while (p>=0 && wt[p]>wt[v] )
                p=parent[p];
            if (p<0)
                cp[v]=0;
            else
                cp[v]=cp[p];
            cp[v] += (long)(L[v]-L[p])*wt[v] ;
            //out.println((v+1)+"="+cp[v]+" p="+p);
        }
        for (int w: adj(v))
            if (w!=parent[v])
                dfs(w, lvl+1);
    }
}