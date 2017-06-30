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
    
    PostTree(int parent[], int w[])
    {
        //-1,000,000,000 ≤ Av ≤ 1,000,000,000
        parent[0]=-1;
        TreePath g = new TreePath(parent, w);
        print(g.cp);
    }
    PostTree(int N)
    {
        this(IOR.ria1(N), IOR.ria(N));
    }

    void print(long x[])
    {
        for (int i=0; i<x.length;i++)
            out.print(x[i]+" ");
        out.println();        
    }
    public static int[] ria(int N, Scanner sc) { // read int array
        int L[]=new int[N];
        for (int i=0; i<N; i++)
            L[i]=sc.nextInt();
        return L;
    }
    
    public static void main(String[] args)
    {      
        new PostTree(IOR.ni()); // 1 ≤ N ≤ 100,000
    }
}
/*
8
1 1 1 1 5 8 6
1 2 3 4 5 15 70 10
ans
1 3 4 5 6 21 96 26
*/

class IOR {
    
    private static Scanner sc = new Scanner(System.in);    
        
    public static int ni()
    {
        return sc.nextInt();
    }
    public static long nl()
    {
        return sc.nextLong();
    }
    public static String ns()
    {
        return sc.next();
    }
    
    public static int[] ria(int N) { // read int array
        int L[]=new int[N];
        for (int i=0; i<N; i++)
            L[i]=sc.nextInt();
        return L;
    }
    
    public static int[] ria1(int N) { // read int array, from 1
        int L[]=new int[N];
        for (int i=1; i<N; i++)
            L[i]=sc.nextInt();
        return L;
    }
}

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
    //int pw[];// weight from current node to parent
    int wt[];// weight of node
    int L[]; // levels, L[r]=0, L[u]=L[p[u]]+1
    long cp[];// cost of path
    
    int getParent(int p)
    {
        return parent[p]-1;
    }
    TreePath(int[]p, int []w)
    {
        super(p.length);
        parent=p;
        r=0;
        L=new int[V()];
        for (int i=1; i<V(); i++) { 
            addEdge(i, getParent(i));
        }
        wt=w;
        cp=new long[V()];
        cp[0]=w[0];
        dfs(r,0);
    }
    
    void pathCost(int v)
    {
        int p=getParent(v);
        while (p>=0 && wt[p]>wt[v] )
            p=getParent(p);
        long lvl=L[v];
        if (p<0) {
            cp[v]=0;
            lvl++;
        } else {
            cp[v]=cp[p];
            lvl -= L[p];
        }
        cp[v] += lvl*wt[v] ;
        //out.println((v+1)+"="+cp[v]+" p="+p);        
    }
    void dfs(int v, int lvl) {
        L[v]=lvl;
        if ( v>0) {
            pathCost(v);
        }
        for (int w: adj(v))
            if (w!=getParent(v))
                dfs(w, lvl+1);
    }
}