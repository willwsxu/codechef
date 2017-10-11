package longContests.long201709;


import codechef.DSU;
import codechef.Edge;
import codechef.GraphWeighted;
import codechef.MyScanner;
import static java.lang.System.out;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
    DSU dsu;
    GraphWeighted g;
    List<Edge>  edges=new ArrayList<>();
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
        dsu =new DSU(n);
        g=new GraphWeighted(n);        
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
                    g.addEdge(new Edge(q[i]-1, q[j]-1, q[v]));
                }
                else {
                    edges.add(new Edge(q[i]-1, q[j]-1, q[v]));
                }
            }
        }
        if (edges.isEmpty())
            return true;
        for (int i=0; i<parity.length; i++)
            dfs(i, -1, 0);   // in case graph is not al connected
        //out.println(Arrays.toString(parity));
        //out.println(edges.size());
        for (Edge e: edges) {
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
        for (Edge v: g.adj(u))    {
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
    static MyScanner sc=new MyScanner();
    public static void main(String[] args)
    {    
        judge();
    }
}