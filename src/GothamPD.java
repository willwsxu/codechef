
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

/*
 * Brief Desc: N police stations, each has a security key. Node R is headquarter
 * all stations are connected and can be reached from R
 * two types of query, Each query will be encoded using the xor between its real 
    values and the value of the last answer.
   0 v u k: A new station with id u and encryption key k is added and connected by a telephone line to v
   1 v k: security test from station v to R. find key that minimize or maximize key^k from all nodes on the path
*/
// https://discuss.codechef.com/questions/98122/how-to-solve-gothampd-from-may17
// http://opendatastructures.org/ods-java/13_1_BinaryTrie_digital_sea.html
class GothamPD {
    int N, Q, R;
    Graph g;
    //boolean dirty=true;
    GothamPD(int n, int q)  // for unit testing
    {
        N=n; Q=q; R=1; // 1 ≤ N ≤ 100,000, PD
        g=new Graph();
    }
    GothamPD()
    {
        this(sc.ni(), sc.ni());
        readNodes();
        readQuery(Q); // 1 ≤ Q ≤ 200,000
    }
    void addHQ(int k)
    {
        g.addNode(R-1, k);// 1 ≤ R, ui, vi, key, ki ≤ 2^31− 1  
    }
    private void readNodes()
    {        
        R=sc.ni();  // 1 ≤ R ≤ N
        addHQ(sc.ni());
        for (int i=0; i<N-1; i++) {
            int u=sc.nextInt();
            int v=sc.nextInt();
            int k=sc.nextInt();
            g.add(u-1, v-1, k);
        }
        //dirty=true;
    }
    
    BreadthFirstPaths bf;
    int calcXOR(int v, int k,StringBuilder sb)
    {
        Iterable<Integer> p = bf.pathTo(v);  // 1 4 2
        int minmax[]=g.findMinMax(p, k);
        sb.append(minmax[0]);
        sb.append(" ");
        sb.append(minmax[1]);
        sb.append("\n");
        out.println(minmax[0]+" "+minmax[1]);
        
        return minmax[0]^minmax[1];        
    }
    int query(int v, int k, int last_answer, StringBuilder sb)
    {
        if ( bf==null ) { // first time
            bf = new BreadthFirstPaths(g, R-1);    
            //dirty=false;
        }
        v = (v^last_answer)-1;
        k ^=last_answer;
        //out.println(" v "+v+" k "+k);
        //assert(v>0);
        return calcXOR(v, k, sb);
    }
    void add(int u, int v, int k, int last_answer)
    {
        u ^=last_answer;
        v ^=last_answer;
        k ^=last_answer;
        //out.println(" add u "+u+" v "+v+" k "+k);
        g.add(u-1, v-1, k);  // must call this first to expand graph node
        //dirty=true;
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
        StringBuilder sb = new StringBuilder();
        gpd.addHQ(2);  // 1 2, index from 0
        gpd.add(5, 1, 3, 0);    // 5 1 3
        gpd.add(2, 1, 4, 0);    // 2 1 4
        gpd.add(3, 2, 5, 0);    // 3 2 5
        gpd.add(4, 2, 1, 0);    // 4 2 1
        gpd.add(6, 3, 3, 0);    // 6 3 3
        out.println("Edges "+gpd.g.E());
        int last_answer = gpd.query(4, 2, 0, sb);      // 1 4 2
        out.println("q1 "+last_answer+" "+(last_answer==6));
        // 6 0 12 0
        int t=6^last_answer;
        assert(t==0);
        gpd.add(12, 0, 0, last_answer); // 0 6 10 6, v u k
        // 7 12 7
        t = 7^last_answer;
        assert(t==1);
        last_answer = gpd.query(12, 7, last_answer, sb); // 1 10 1
        out.println("q2 "+last_answer+" "+(last_answer==5));
        gpd.bf.pathTo(9).forEach(gpd.g::printKey);
        out.println();
        // 4 0 7
        t = 4^last_answer;
        assert(t==1);
        last_answer = gpd.query(0, 7, last_answer, sb); // 1 5 2
        out.println("q3 "+last_answer+" "+(last_answer==1));
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
        StringBuilder sb = new StringBuilder();
        Random rnd=new Random();
        gpd.addHQ(rnd.nextInt(100));
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

class DFS
{
    Map<Integer, BinaryTrie> tries=new HashMap<>();
    private Graph G;
    DFS(Graph g, int s)
    {
        G=g;
        BinaryTrie bt=new BinaryTrie();
        bt.add(G.nodes.get(s).key);
        tries.put(s, bt);
        dfs(s);
    }
    void dfs(int u) {
        BinaryTrie btu=tries.get(u);
        for (int v: G.adj(u)) {
            if (tries.get(v)==null) {
                //out.println("trie add "+G.nodes.get(v).key+" to node "+v+" from "+u);
                BinaryTrie bt=btu.persistAdd(G.nodes.get(v).key);
                tries.put(v, bt);
                dfs(v);
            }
        }
    }
}
class BreadthFirstPaths
{
    class State
    {
        boolean marked;
        int     edgeTo;
        BinaryTrie  bt;
        State(int e, BinaryTrie b)
        {
            marked=true;
            edgeTo=e;
            bt=b;
        }
    }
    Map<Integer, State> states=new HashMap<>();
    private final int s; // source
    private Graph G;
    LinkedBlockingQueue<Integer> queue;
    public BreadthFirstPaths(Graph G, int s)
    {
        this.s = s;
        this.G =G;
        
        queue = new LinkedBlockingQueue<Integer>();
        bfs(s);
    }
    private void bfs(int s)
    {
        if (!states.containsKey(s)) {
            BinaryTrie bt=new BinaryTrie();
            bt.add(G.nodes.get(s).key);
            states.put(s, new State(0, bt));
        }
        queue.add(s); // and put it on the queue.
        while (!queue.isEmpty())
        {
            int v = queue.poll(); // Remove next vertex from the queue.
            State st=states.get(v);
            int k=G.nodes.get(v).key;
            for (int w : G.adj(v)) {
                if ( states.get(w)==null )
                {
                    //out.println("path "+w +" to "+v);
                    states.put(w, new State(v, st.bt.persistAdd(k)));
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


class BinaryTrie  // for int
{
    private static int  R=2;
    private static int  w=31; // depth for int, 31 bits
    
    private Node root=new Node();
    private static class Node
    {
        String  name;
        int     val=0;
        private Node[] child = new Node[R];
    }
    boolean add(int ix)
    {
        //out.println("trie add "+ix);
        Node u = root;
        boolean a=false;
        for (int i = 0; i < w; i++) {
            int c = (ix >>> w-i-1) & 1;
            if (u.child[c]==null) {
                u.child[c]=new Node();
                a=true;
            }
            u=u.child[c];
        }
        u.val=ix;
        return a;
    }
    
    BinaryTrie persistAdd(int ix)
    {
        //out.println("trie persistAdd "+ix);
        BinaryTrie bt=new BinaryTrie();
        bt.root.child[0]=root.child[0];
        bt.root.child[1]=root.child[1];
        
        Node u = bt.root;
        for (int i = 0; i < w; i++) {
            int c = (ix >>> w-i-1) & 1;
            Node n=new Node();
            if (u.child[c]==null) {
                u.child[c]=n;
            } else {
                n.child[0]=u.child[c].child[0];
                n.child[1]=u.child[c].child[1];
                u.child[c]=n;                
            }
            u=u.child[c];
        }
        u.val=ix;
        return bt;
    }
    boolean find(int ix)
    {        
        Node u = root;
        int i=0;
        for (i = 0; i < w; i++) {
            int c = (ix >>> w-i-1) & 1;
            if (u.child[c] == null) break;
            u = u.child[c];
        }
        return i==w;
    }
    int xorMin(int ix)
    {
        Node u = root;
        int i=0;
        for (i = 0; i < w; i++) {
            int c = (ix >>> w-i-1) & 1;
            if (u.child[c] == null) 
                c=1-c;
            u = u.child[c];
            if (u==null)
                return 0;
        }
        return u.val;        
    }
    int xorMax(int ix)
    {
        Node u = root;
        int i=0;
        for (i = 0; i < w; i++) {
            int c = (ix >>> w-i-1) & 1;
            if (u.child[1-c] != null) 
                c=1-c;
            u = u.child[c];
            if (u==null)
                return 0;
        }
        return u.val;        
    }
    static void test()
    {
        BinaryTrie bt=new BinaryTrie();
        out.println(bt.find(1)==false);
        out.println(bt.add(1)==true);
        out.println(bt.find(1)==true);
        out.println(bt.add(5)==true);
        out.println(bt.add(5)==false);
        out.println(bt.find(5)==true);
        out.println(bt.add(6)==true);
        out.println(bt.add(11)==true);
        out.println(bt.add(20)==true);
        out.println(bt.add(22)==true);
        out.println(bt.add(26)==true);
        out.println(bt.xorMin(13)==11);
        out.println(bt.xorMax(13)==22);
        out.println(bt.add(14)==true);
        out.println(bt.add(18)==true);
        out.println(bt.xorMin(13)==14);
        out.println(bt.xorMax(13)==18);
    }
    static void test2()
    {
        out.println("persistent trie");
        BinaryTrie bt=new BinaryTrie();
        out.println(bt.add(1)==true);
        BinaryTrie bt2=bt.persistAdd(5);
        BinaryTrie bt3=bt2.persistAdd(6);
        BinaryTrie bt4=bt3.persistAdd(11);
        BinaryTrie bt5=bt4.persistAdd(20);
        BinaryTrie bt6=bt5.persistAdd(22);
        BinaryTrie bt7=bt6.persistAdd(26);
        out.println(bt.find(5)==false);
        out.println(bt2.find(6)==false);
        out.println(bt3.find(11)==false);
        out.println(bt4.find(20)==false);
        out.println(bt5.find(22)==false);
        out.println(bt6.find(26)==false);
        out.println(bt6.find(22)==true);
        out.println(bt6.find(20)==true);
        
        out.println(bt7.find(1)==true);
        out.println(bt7.find(5)==true);
        out.println(bt7.find(6)==true);
        out.println(bt7.find(11)==true);
        out.println(bt7.find(20)==true);
        out.println(bt7.find(22)==true);
        out.println(bt7.find(26)==true);
        out.println(bt7.xorMin(13)==11);
        out.println(bt7.xorMax(13)==22);
    }
    static void test3()
    {
        out.println("persistent trie");
        BinaryTrie bt=new BinaryTrie();
        out.println(bt.add(2)==true);
        BinaryTrie bt5=bt.persistAdd(3);
        BinaryTrie bt2=bt.persistAdd(4);
        BinaryTrie bt3=bt2.persistAdd(5);
        BinaryTrie bt4=bt2.persistAdd(1);
        BinaryTrie bt6=bt3.persistAdd(3);     
        out.println(bt4.xorMin(2));  
        out.println(bt4.xorMax(2));
    }
}

class GPD_TRIE extends GothamPD
{
    DFS dfs;
    GPD_TRIE()
    {
        super();
    }
    GPD_TRIE(int n, int q)
    {
        super(n, q);
    }
    
    @Override
    int query(int v, int k, int last_answer, StringBuilder sb)
    {
        if ( dfs==null ) { // first time
            dfs = new DFS(g, R-1);   
        }
        v = (v^last_answer)-1;
        k ^=last_answer;
        //out.println(" v "+v+" k "+k);
        //assert(v>0);
        return calcXOR2(v, k, sb);
    }
    int calcXOR2(int v, int k, StringBuilder sb)
    {
        BinaryTrie bt = dfs.tries.get(v);
        int mn=bt.xorMin(k)^k;
        int mx=bt.xorMax(k)^k;
        sb.append(mn);
        sb.append(" ");
        sb.append(mx);
        sb.append("\n");
        //out.println("xor node "+v+" key "+k+" min="+mn+" max="+mx);
        return mn^mx;
    }
    @Override
    void add(int u, int v, int k, int last_answer)
    {
        super.add(u,v,k,last_answer);
        if ( dfs !=null) {
            u ^=last_answer;
            v ^=last_answer;
            k ^=last_answer;
            BinaryTrie btv=dfs.tries.get(v-1);
            if (btv==null)
                out.println("error no trie"+v);
            dfs.tries.put(u-1, btv.persistAdd(k));
        }
    }
    static void test2()
    {
        GothamPD gpd = new GPD_TRIE(6, 4);
        StringBuilder sb = new StringBuilder();
        gpd.addHQ(2);  // 1 2, index from 0
        gpd.add(5, 1, 3, 0);    // 5 1 3
        gpd.add(2, 1, 4, 0);    // 2 1 4
        gpd.add(3, 2, 5, 0);    // 3 2 5
        gpd.add(4, 2, 1, 0);    // 4 2 1
        gpd.add(6, 3, 3, 0);    // 6 3 3
        out.println("GPD_TRIE Edges "+gpd.g.E());
        int last_answer = gpd.query(4, 2, 0, sb);      // 1 4 2
        out.println("trie q1 "+last_answer+" "+(last_answer==6));
        
        // 6 0 12 0
        int t=6^last_answer;
        out.println(t==0);
        gpd.add(12, 0, 0, last_answer); // 0 6 10 6, v u k
        // 7 12 7
        t = 7^last_answer;
        out.println(t==1);
        last_answer = gpd.query(12, 7, last_answer, sb); // 1 10 1
        out.println("trie q2 "+last_answer+" "+(last_answer==5));
        // 4 0 7
        t = 4^last_answer;
        out.println(t==1);
        last_answer = gpd.query(0, 7, last_answer, sb); // 1 5 2
        out.println("trie q3 "+last_answer+" "+(last_answer==1));
        out.print(sb.toString());
        gpd.g.add(Integer.MAX_VALUE-1, 9, Integer.MAX_VALUE-2);
    }
    public static void main(String[] args)
    {     
        //BinaryTrie.test3();
        //GPD_TRIE.test2();
        //largeTest();
        new GPD_TRIE();
    }
}