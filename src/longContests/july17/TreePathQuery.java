package longContests.july17;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import static java.lang.System.out;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

// Given a weighted tree of N vertices, query XOR of weight<=k from u to v
// PSHTTR, medium
// Fenwick Binary Index Tree for XOR, Euler Tour/LCA, offline sorting
// Use Thread class to provide a large call stack for dfs
class TreePathQuery implements Runnable{
            
    int u,v,k;
    void read3()
    {
        u=sc.nextInt(); // u,v<=N
        v=sc.nextInt();
        k=sc.nextInt(); // 1 ≤ C, K ≤ 10^9        
    }
    void bruteforce()
    {
        int N=sc.nextInt(); // 1 ≤ N, M ≤ 10^5
        TreeXorBF tree = new TreeXorBF(N);
        for (int i=0; i<N-1; i++) {
            read3();
            tree.add(u-1, v-1, k);
        }
        tree.dfs(0, 0);
        int M=sc.nextInt();    
        StringBuilder sb=new StringBuilder();
        for (int i=0; i<M; i++) {
            read3();
            sb.append(tree.queryXor(u-1, v-1, k));
            sb.append("\n");
        }
        out.println(sb.toString());    
    }
    void offline()
    {
        int N=sc.nextInt(); // 1 ≤ N, M ≤ 10^5
        TreePathXor treeX=new TreePathXor(N);
        for (int i=0; i<N-1; i++) {  // N-1 edges
            read3();
            treeX.add(u, v, k);
        }
        int M=sc.nextInt();
        for (int i=0; i<M; i++) {
            read3();
            treeX.addQ(u, v, k);
        }
        treeX.solve();
    }
    
    static MyScannerX sc = new MyScannerX();
        
    public void run()
    {
        //TreePathXor.test();
        
        int T=sc.nextInt(); // 1 ≤ T ≤ 5
        while (T-->0)
            new TreePathQuery().offline();
    }
    
    public static void main(String[] args)
    {
        Thread t =new Thread(null, new TreePathQuery(), "whatever", 1<<26);
        t.start();
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
    int E[];    // Euler tour
    int EL[];   // level per index
    int EH[];   // first occurrence
    
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
        E = new int[2*N];
        EL = new int[2*N];
        EH = new int[N];
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
    int idx=0;
    void dfs(int v, int lvl) {
        //out.println("dfs "+v+" level "+lvl+" idx="+idx);
        EH[v]=idx;
        E[idx]=v;
        EL[idx++]=lvl;
        L[v]=lvl;
        for (int u: adj(v)) {
            if (u !=0 && u!=getParent(v)) {
                dfs(u, lvl+1);
                E[idx]=v;
                EL[idx++]=lvl;
            }
        }
    }

    void print()
    {
        out.println("Euler first: "+Arrays.toString(EH));
        out.println("Euler tour: "+Arrays.toString(E));
        out.println("Euler level: "+Arrays.toString(EL));
    }
}    

class TreeXorBF extends TreeBase  // brute force XOR
{
    public TreeXorBF(int N)
    {
        super(N);
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
1
6
2 1 1
2 3 2 
2 4 5
3 5 10
3 6 1000000000
7
5 4 5
5 4 10
5 4 1
1 2 1
4 1 10
1 5 8
2 2 1
[7, 13, 0, 1, 4, 3, 0]
*/

class FenwickTreeXor  // binary index tree
{
    int bit[];
    int n;
    
    int LSOne(int x)  // least significant bit
    {
        return x & (-x);
    }
    public FenwickTreeXor(int n)
    {
        bit = new int[n+1];
        this.n=n;
    }
    public void add(int x, int v)
    {
        //out.println("add bit "+x+" v="+v);
        while (x<=n) {
            bit[x] ^= v;
            //out.println("bit "+x+" "+bit[x]);
            x += LSOne(x);
        }
    }
    public int get(int x)
    {
        if (x>n)
            return 0;
        int ret=0;
        while (x>0) {
            ret ^= bit[x];
            x -= LSOne(x);
        }
        return ret;
    }
    void print()
    {
        out.println(Arrays.toString(bit));
    }
    
    static void test()
    {
        FenwickTreeXor ft = new FenwickTreeXor(6);
        ft.add(1, 2);
        ft.add(2, 4);
        ft.add(3, 8);
        ft.add(4, 16);
        ft.add(5, 32);
        ft.add(6, 64);
        out.println(ft.get(3)==14);
        out.println(ft.get(6)==126);
    }
}

class TreePathXor extends SimpleGraphX
{
    class Edge
    {
        int u,v;
        int wt;
        Edge(int u, int v, int wt)
        {
            this.u=u; this.v=v; this.wt=wt;
        }
    }
    List<Edge> edgeList = new ArrayList<>();
    List<Edge> qList = new ArrayList<>();
    
    int st[];   // start index of Euler tour
    int en[];   // end index of Euler tour
    int index=0;
    public TreePathXor(int n) {  // vertex from 1
        super(n+1);
        st = new int[n+1];
        en = new int[n+1];
    }

    public void solve()
    {
        dfs(1, -1);
        //out.println(Arrays.toString(st));
        //out.println(Arrays.toString(en));
        
        List<Map.Entry<Integer,Integer>> sorted=new ArrayList<>();
        for (Edge e: edgeList)
        {
            if (st[e.u]<st[e.v])  // add weight to vertex v given edge is u->v
                sorted.add(new AbstractMap.SimpleEntry<Integer, Integer>(e.wt, -e.v));
            else
                sorted.add(new AbstractMap.SimpleEntry<Integer, Integer>(e.wt, -e.u));
        }
        int j=0;
        for (Edge e: qList) {
            sorted.add(new AbstractMap.SimpleEntry<Integer, Integer>(e.wt, ++j));         
        }
        // sort by weight, then by vertex, so edges are added in order of weight and before query of same weight.
        Collections.sort(sorted, (c1,c2)->c1.getValue()-c2.getValue());
        Collections.sort(sorted, (c1,c2)->c1.getKey()-c2.getKey());
        //out.println(sorted);
        int ans[]=new int[qList.size()];
        FenwickTreeXor ft=new FenwickTreeXor(edgeList.size()+1);
        for(Map.Entry<Integer,Integer> e: sorted) {
            if (e.getValue()<0) {
                int v=-e.getValue();
                ft.add(st[v], e.getKey());
                //ft.print();
                ft.add(en[v]+1, e.getKey()); // exclude it from next vertex as it is not in the path
                //ft.print();
            } else {
                int idx = e.getValue();
                //out.println("idx "+e);
                ans[idx-1]=ft.get(st[qList.get(idx-1).u])^ft.get(st[qList.get(idx-1).v]);
            }
        }
        StringBuilder sb = new StringBuilder();
        for (int i: ans) {
            sb.append(i);
            sb.append('\n');
        }
        out.print(sb.toString());
    }
    void dfs(int v, int p) {
        //out.println("dfs v="+v+" p="+p);
        st[v] = ++index;
        for (int nxt : adj(v)) {
            if (nxt==p)
                continue;
            dfs(nxt, v);
        }
        en[v]=index;
    }
    void add(int u, int v, int w) {
        addEdge(u, v);
        edgeList.add(new Edge(u, v, w));
    }
    void addQ(int u, int v, int w) {
        qList.add(new Edge(u, v, w));
    }
    void print()
    {
        out.println(Arrays.toString(st));
        out.println(Arrays.toString(en));
    }
    
    static void test()
    {
        final int MAXN=100005;
        TreePathXor treex=new TreePathXor(MAXN);
        treex.add(1, 2, 2);
        treex.add(2, 3, 4);
        treex.add(2, 4, 8);
        treex.addQ(1, 3, 10);
        treex.addQ(1, 4, 10);
        treex.addQ(3, 4, 10);
        treex.solve();
        for (int i=4; i<MAXN-5; i++) {
            treex.add(i, i+1, i+1000000000);
        }
        treex.addQ(1, MAXN-6, 2000000000);
        treex.solve();
    }
}