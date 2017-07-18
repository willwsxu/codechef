
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import static java.lang.System.out;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;


class TreePathQuery {
    TreeBase tree;
            
    TreePathQuery()
    {
        int N=sc.nextInt(); // 1 ≤ N, M ≤ 10^5
        tree = new TreeBase(N);
        for (int i=0; i<N-1; i++) {
            int u=sc.nextInt();
            int v=sc.nextInt();
            long c=sc.nextLong(); // 1 ≤ C, K ≤ 10^9
            tree.add(u-1, v-1, c);
        }
        tree.dfs(0, 0);
        query(sc.nextInt());
    }
    void query(int M)
    {
        StringBuilder sb=new StringBuilder();
        for (int i=0; i<M; i++) {
            int u=sc.nextInt();
            int v=sc.nextInt();
            long k=sc.nextInt();
            sb.append(tree.queryXor(u-1, v-1, k));
            sb.append("\n");
        }
        out.println(sb.toString());
    }
    
    static MyScannerX sc = new MyScannerX();
    //static Scanner sc = new Scanner(System.in);
    
    public static void main(String[] args)
    {
        int T=sc.nextInt(); // 1 ≤ T ≤ 5
        while (T-->0)
            new TreePathQuery();
    }
}


class SimpleGraphX { // unweighted, bidirectional
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

// node is 0 based
class TreeBase extends SimpleGraphX
{
    int r; // root
    int parent[]; // parent node, first is root
    long pw[];// weight of node to parent
    int L[]; // levels, L[r]=0, L[u]=L[p[u]]+1
    
    int getParent(int p)
    {
        return parent[p];
    }
    TreeBase(int N)
    {
        super(N);
        r=0;
        parent=new int[N];
        L=new int[V()];
        pw=new long[V()];
        parent[0]=-1;
        pw[0]=0;
        L[0]=0;
    }
    
    void add(int p, int c, long w)
    {
        if (p>c) {
            add(c, p, w);
            return;
        }
        //out.println("add "+p+","+c);
        addEdge(p, c);
        parent[c]=p;
        pw[c]=w;
    }
    void dfs(int v, int lvl) {
        //out.println("dfs "+v+" level "+lvl);
        L[v]=lvl;
        for (int u: adj(v))
            if (u !=0 && u!=getParent(v))
                dfs(u, lvl+1);
    }
    
    long ans=0;
    long xor(long ans, long val, long k) {
        //out.println("xor ans="+ans+" val="+val+" k="+k);
        if (val<=k)
            ans ^= val;
        //out.println("xor ans="+ans+" val="+val+" k="+k);
        return ans;
    }
    int moveUp(int u, int l, long k) {
        do {
            ans = xor (ans, pw[u], k);
            u=parent[u];
        } while (L[u]>l);
        return u;
    }
    long queryXor(int u, int v, long k)
    {
        //out.println("q u="+u+" v="+v+" level u="+L[u]+" v="+L[v]);
        ans=0;
        if (L[u]>L[v]) {
            u=moveUp(u, L[v], k);
        } else if (L[v]>L[u]) {
            v=moveUp(v, L[u], k);
        }
        if (parent[u]==v || parent[v]==u || u==v)
            return ans;
        //out.println("q u="+u+" v="+v+" level u="+L[u]+" v="+L[v] +" ans="+ans);
        do {
            ans = xor (ans, pw[u], k);
            ans = xor (ans, pw[v], k);
            u=parent[u];
            v=parent[v];
        } while (u!=v);
        //out.println("q u="+u+" v="+v+" level u="+L[u]+" v="+L[v] +" ans="+ans);
        return ans;
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
}
/*
5
2 1 1
2 3 2 
2 4 5
3 5 10
7
5 4 5
5 4 10
5 4 1
1 2 1
4 1 10
1 5 8
2 2 1
*/