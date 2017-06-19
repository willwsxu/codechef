
//import codechef.IGraph;
import static java.lang.System.out;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// EASY, Graph
class KnightCovering {
    GraphKnight g;
    int n,m;
    KnightCovering(int n, int m)
    {//1 ≤ N ≤ 3, 1 ≤ M ≤ 50
        this.n=n;   this.m=m;
        if (n==1)
            return;
        g=new GraphKnight(n, m);
    }
    int solve()
    {
        if (n<=1)
            return m;
        /*if (n*m<=4)
            return n*m;
        if (n*m==6)
            return 4;
        if ( n==2) {
            int sixpack=m/6;
            m /=6;
            if (m==5)
                return 4+sixpack*4;
            else if (m<=2)
                return n*m+sixpack*4;
            else
                return n*m-2*(m-2)+sixpack*4;            
        }*/
        return g.cover();
    }
    static void test()
    {
        out.println(new KnightCovering(1, 1).solve()==1);
        out.println(new KnightCovering(1, 10).solve()==10);
        out.println(new KnightCovering(2, 1).solve()==2);
        out.println(new KnightCovering(2, 3).solve()==4);
        out.println(new KnightCovering(2, 4).solve()==4);
        out.println(new KnightCovering(2, 5).solve()==4);
        out.println(new KnightCovering(2, 6).solve()==4);
        out.println(new KnightCovering(2, 7).solve()==6);
        out.println(new KnightCovering(2, 8).solve()==8);
        out.println(new KnightCovering(3, 3).solve()==4);
        out.println();
        out.println(new KnightCovering(2, 9).solve());
    }
    
    static Scanner sc = new Scanner(System.in);
    public static void main(String[] args)
    {      
        test();
        /*int T = sc.nextInt();  // 1 ≤ T ≤ 150
        while (T-->0)
            out.println(new KnightCovering(sc.nextInt(), sc.nextInt()).solve());*/
    }
}

class GraphSimple// implements IGraph 
{ // unweighted, bidirectional
    protected int   V; // number of vertices
    private   int   E; // number of edges

    protected List<List<Integer>> adj;
    public GraphSimple(int V)
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

class GraphKnight extends GraphSimple
{
    boolean covered[][];
    int n,m;
    GraphKnight(int n, int m)
    {
        super(n*m);
        this.n=n;   this.m=m;
        covered=new boolean[n][m];
        marked=new boolean[n*m];
        for (int i=0; i<n;i++) {
            for (int j=0; j<m; j++ ) {
                addEdge(i, j, i+2, j-1);
                addEdge(i, j, i+2, j+1);
                addEdge(i, j, i+1, j+2);
                addEdge(i, j, i+1, j-2);
            }
        }
    }
    
    void addEdge(int i1, int j1, int i2, int j2)
    {
        if (isValid(i2, j2))
            addEdge(i1*m+j1, i2*m+j2);        
    }
    boolean isValid(int i, int j)
    {
        if (i<0 || j<0 || i>=n || j>=m)
            return false;
        return true;
    }
    void remove (int u)
    {
        for (int v: adj(u)) { 
            out.println(adj(v));
            adj(v).remove(new Integer(u));  
            out.println(adj(v)+" remove "+u + " from "+v);
        }
    }
    
    boolean marked[];
    int findMax() {
        int u=-1;
        int mx=0;
        for (int i=0; i<adj.size(); i++) {
            if (marked[i])
                continue;
            if (adj.get(i).size()>mx) {
                mx=adj.get(i).size();
                u=i;
            }
        }
        return u;
    }
    int cover()
    {
        int covered=0;
        while (true) {
            int u=findMax();
            if (u<0)
                break;
            covered+=adj.get(u).size();
            marked[u]=true;
            List<Integer> q=new ArrayList<>();
            for (int v: adj(u)) {
                marked[v]=true;
                q.add(v);  // can not call remove here as u list is in use
            }
            for (int v:q)
                remove(v);
            out.println("K="+u+" "+covered);
        }
        return n*m-covered;
    }
}