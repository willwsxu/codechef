
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import static java.lang.Integer.max;
import static java.lang.Integer.min;
import static java.lang.System.out;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.Stack;
import java.util.StringTokenizer;
import java.util.concurrent.LinkedBlockingQueue;


class GothamPD {
    int N, Q, R;
    Graph g;
    boolean dirty=true;
    GothamPD(int n, int q)  // for unit testing
    {
        N=n; Q=q; R=1;
    }
    GothamPD()
    {
        N=sc.nextInt();  // 1 ≤ N ≤ 100,000, PD
        Q=sc.nextInt();  // 1 ≤ Q ≤ 200,000
        readNodes();
        readQuery(Q);
    }
    private void readNodes()
    {        
        g=new Graph();
        R=sc.nextInt();  // 1 ≤ R ≤ N
        int key=sc.nextInt();// 1 ≤ R, ui, vi, key, ki ≤ 2^31− 1
        g.addNode(R-1, key);
        for (int i=0; i<N-1; i++) {
            int u=sc.nextInt();
            int v=sc.nextInt();
            int k=sc.nextInt();
            g.add(u-1, v-1, k);
        }
        dirty=true;
    }
    
    BreadthFirstPaths bf;
    int query(int v, int k, int last_answer, StringBuilder sb)
    {
        if ( bf==null ) { // first time
            bf = new BreadthFirstPaths(g, R-1);    
            dirty=false;
        }
        v = (v^last_answer)-1;
        k ^=last_answer;
        //out.println(" v "+v+" k "+k);
        //assert(v>0);
        Iterable<Integer> p = bf.pathTo(v);  // 1 4 2
        int minmax[]=g.findMinMax(p, k);
        sb.append(minmax[0]);
        sb.append(" ");
        sb.append(minmax[1]);
        sb.append("\n");
        //out.println(minmax[0]+" "+minmax[1]);
        
        return minmax[0]^minmax[1];
    }
    void add(int u, int v, int k, int last_answer)
    {
        u ^=last_answer;
        v ^=last_answer;
        k ^=last_answer;
        //out.println(" add u "+u+" v "+v+" k "+k);
        g.add(u-1, v-1, k);  // must call this first to expand graph node
        dirty=true;
        if ( bf !=null) // did initial bsf
            bf.bfsMore(v-1);
    }
    private void readQuery(int Q)
    {
        StringBuilder sb = new StringBuilder();
        int last_answer = 0;
        for (int i=0; i<Q; i++) {
            int t = sc.nextInt();
            t ^= last_answer;
            if (t==0) {  // add
                int v=sc.nextInt();
                int u=sc.nextInt();
                int k=sc.nextInt();
                add(u, v, k, last_answer);    
            } else {  // query
                int v=sc.nextInt();
                int k=sc.nextInt();    
                last_answer = query(v, k, last_answer, sb);
            }
        } 
        out.print(sb.toString());
    }
    public static void test()
    {
        GothamPD gpd = new GothamPD(6, 4);
        gpd.g=new Graph();
        StringBuilder sb = new StringBuilder();
        gpd.g.addNode(0, 2);  // 1 2, index from 0
        gpd.add(5, 1, 3, 0);    // 5 1 3
        gpd.add(2, 1, 4, 0);    // 2 1 4
        gpd.add(3, 2, 5, 0);    // 3 2 5
        gpd.add(4, 2, 1, 0);    // 4 2 1
        gpd.add(6, 3, 3, 0);    // 6 3 3
        out.println("Edges "+gpd.g.E());
        int last_answer = gpd.query(4, 2, 0, sb);      // 1 4 2
        // 6 0 12 0
        int t=6^last_answer;
        assert(t==0);
        gpd.add(12, 0, 0, last_answer); // 0 6 10 6, v u k
        // 7 12 7
        t = 7^last_answer;
        assert(t==1);
        last_answer = gpd.query(12, 7, last_answer, sb); // 1 10 1
        gpd.bf.pathTo(9).forEach(gpd.g::printKey);
        out.println();
        // 4 0 7
        t = 4^last_answer;
        assert(t==1);
        last_answer = gpd.query(0, 7, last_answer, sb); // 1 5 2
        out.print(sb.toString());
        gpd.g.add(Integer.MAX_VALUE-1, 9, Integer.MAX_VALUE-2);
        gpd.bf.bfsMore(9);
        gpd.bf.pathTo(Integer.MAX_VALUE-1).forEach(gpd.g::printKey);
    }
    static void largeTest()
    {
        Instant start = Instant.now();
        int n=100000;
        int q=200000;
        GothamPD gpd = new GothamPD(n, q);
        gpd.g=new Graph();
        StringBuilder sb = new StringBuilder();
        Random rnd=new Random();
        gpd.g.addNode(0, rnd.nextInt(100));
        for (int i=1; i<n; i++){
            int prev=i-1;//rnd.nextInt(i);// ensure connect to previous connected node
            gpd.add(i+1, prev+1, rnd.nextInt(100), 0);
        }
        Instant mid = Instant.now();
        out.println(n+" test takes usec "+ChronoUnit.MICROS.between(start, mid)); 
        Set<Integer> nodes= new HashSet<>();
        int bound=1<<31-n-1;
        for (int i=1; i<n; i++) {
            int u=rnd.nextInt(bound);
            while (nodes.contains(u+n+1))
                u=rnd.nextInt(bound);
            u += (n+1);
            nodes.add(u);
            int v=rnd.nextInt(n);
            //out.println("add "+u+" "+v);
            gpd.add(u, v+1, rnd.nextInt(100), 0);
            gpd.query(u, 99, 0, sb);
        }
        Instant end = Instant.now();
        out.println(n+" query takes usec "+ChronoUnit.MICROS.between(mid, end));  
        //out.print(sb.toString()); 
        gpd.bf.pathTo(n-1).forEach(gpd.g::printKey);
    }
    
    static MyScanner sc = new MyScanner();  // for large input
    public static void main(String[] args)
    {     
        //test();
        //largeTest();
        new GothamPD();
    }
}

class Graph { // unweighted, bidirectional
    private   int   E; // number of edges

    class Node
    {
        int key;
        List<Integer> adj;
        Node(int k) {
            key=k;
            adj=new ArrayList<>(10);
        }
    }
    Map<Integer, Node> nodes;
    Graph()
    {
        nodes=new HashMap<>();
        E=0;
    }
    public void addNode(int u, int k)
    {
        //out.println("node "+u);
        nodes.put(u, new Node(k));
    }
    public int V() { return nodes.size(); }
    public int E() { return E; }
    
    public void addEdge(int u, int v)
    {
        //out.println("edge "+u+" to "+v);
        nodes.get(u).adj.add(v);
        nodes.get(v).adj.add(u);
        E++;
    }
    public void add (int u, int v, int k) 
    {        
        addNode(u,k);
        addEdge(u, v);
    }
    public List<Integer> adj(int u)
    {
        return nodes.get(u).adj;
    }
    int[] findMinMax(Iterable<Integer> p, int k)
    {
        //p.forEach (out::println) ;
        int minX=Integer.MAX_VALUE;
        int maxX=0;
        for (int w: p) {
            int k2=nodes.get(w).key^k;
            minX=min(minX, k2);
            maxX=max(maxX, k2);
        }
        return new int[]{minX, maxX};
    }
    void printKey(int i)
    {
        out.println((i+1)+" "+nodes.get(i).key);
    }
}

class BreadthFirstPaths
{
    class State
    {
        boolean marked;
        int     edgeTo;
        State(int e)
        {
            marked=true;
            edgeTo=e;
        }
    }
    Map<Integer, State> states=new HashMap<>();
    //private boolean[] marked; // Is a shortest path to this vertex known?
    //private int[] edgeTo; // last vertex on known path to this vertex
    private final int s; // source
    private Graph G;
    LinkedBlockingQueue<Integer> queue;
    public BreadthFirstPaths(Graph G, int s)
    {
        //marked = new boolean[G.capacity()];
        //edgeTo = new int[G.capacity()];
        this.s = s;
        this.G =G;
        
        queue = new LinkedBlockingQueue<Integer>();
        bfs(s);
    }
    private void bfs(int s)
    {
        //marked[s] = true; // Mark the source
        if (!states.containsKey(s))
            states.put(s, new State(0));
        queue.add(s); // and put it on the queue.
        while (!queue.isEmpty())
        {
            int v = queue.poll(); // Remove next vertex from the queue.
            for (int w : G.adj(v)) {
                //if (!marked[w]) // For every unmarked adjacent vertex,
                if ( states.get(w)==null )
                {
                    //out.println("path "+w +" to "+v);
                    states.put(w, new State(v));
                    //edgeTo[w] = v; // save last edge on a shortest path,
                    //marked[w] = true; // mark it because path is known,
                    queue.add(w); // and add it to the queue.
                }
            }
        }
    }
    // 
    public void bfsMore(int n)  // 
    {
        //assert(marked[n]);
        bfs(n);
    }
    public boolean hasPathTo(int v)
    { 
        return states.get(v) != null; 
    }
    public Iterable<Integer> pathTo(int v)
    {
        if (!hasPathTo(v)) return null;
        Stack<Integer> path = new Stack<Integer>();
        for (int x = v; x != s; x = states.get(x).edgeTo) {
            path.push(x);
            //out.println(" pathTo "+x);
        }
        path.push(s);
        return path;
    }
}

// credit to http://codeforces.com/blog/entry/7018
class MyScanner {
    BufferedReader br;
    StringTokenizer st;

    MyScanner(String f)
    {
        try {
            br = new BufferedReader(new FileReader(new File(f)));
        } catch (IOException e)
        {
            out.println("MyScanner bad file "+f);
        }
    }
    public MyScanner() {
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