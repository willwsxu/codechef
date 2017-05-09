
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import static java.lang.Integer.max;
import static java.lang.Integer.min;
import static java.lang.System.out;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;
import java.util.concurrent.LinkedBlockingQueue;

/*

 */


// Buffered Read class did not help Stable Market TLE issue (
class MyReader
{
    BufferedReader br;
    String line;
    List<String> items=new ArrayList<>();
    MyReader(String f)
    {
        try {
            br = new BufferedReader(new FileReader(new File(f)));
        } catch (IOException e)
        {
            out.println("MyReader bad file "+f);
        }
        //readline();
    }
    MyReader()
    {
        br = new BufferedReader(new InputStreamReader(System.in));
        //readline();            
    }
    void readline()
    {
        try {
            line = br.readLine();
            while (line.isEmpty())
                line = br.readLine();
            //out.println(line);
        }catch (IOException e)
        {
            out.println("MyReader read exception "+e);
        }
        String [] w = line.split("\\s+");
        for(String s:w) {
            if (!s.isEmpty())
                items.add(s);
        }
        //out.println(items);
    }
    int nextInt()
    {
        if (items.isEmpty())
            readline();
        try {
        int i=Integer.parseInt(items.get(0));
        items.remove(0);
        return i;
        } catch (NumberFormatException e) {
            out.println(items.toString()+e);
            return 0;
        }
    }
    long nextLong()
    {    
        if (items.isEmpty())
            readline();
        long i=Long.parseLong(items.get(0));
        items.remove(0);
        return i;
    }
}

class GothamPD {
    int N, Q;
    GraphEx g;
    boolean dirty=true;
    GothamPD(int n, int q)  // for unit testing
    {
        N=n; Q=q;
    }
    GothamPD()
    {
        N=sc.nextInt();  // 1 ≤ N ≤ 100,000, PD
        Q=sc.nextInt();  // 1 ≤ Q ≤ 200,000
        readNodes();
        readQuery(g, Q);
    }
    private void readNodes()
    {        
        g=new GraphEx(N);
        int R=sc.nextInt();  // 1 ≤ R ≤ N
        int key=sc.nextInt();// 1 ≤ R, ui, vi, key, ki ≤ 2^31− 1
        g.setKey(R-1, key);
        for (int i=0; i<N-1; i++) {
            int u=sc.nextInt();
            int v=sc.nextInt();
            int k=sc.nextInt();
            g.setKey(u-1, k);
            g.addEdge(u-1, v-1);
        }
        dirty=true;
    }
    
    BreadthFirstPaths bf;
    int query(int v, int k, int last_answer, StringBuilder sb)
    {
        if ( bf==null ) { // first time
            bf = new BreadthFirstPaths(g, 0);    
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
        g.addEdge(u-1, v-1);  // must call this first to expand graph node
        g.setKey(u-1, k);  
        dirty=true;
        if ( bf !=null) // did initial bsf
            bf.bfsMore(v-1);
    }
    private void readQuery(GraphEx g, int Q)
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
        gpd.g=new GraphEx(6);
        StringBuilder sb = new StringBuilder();
        gpd.g.setKey(0, 2);  // 1 2, index from 0
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
        // 4 0 7
        t = 4^last_answer;
        assert(t==1);
        last_answer = gpd.query(0, 7, last_answer, sb); // 1 5 2
        out.print(sb.toString());
    }
    
    static MyReader sc = new MyReader();  // for large input
    //static Scanner sc = new Scanner(System.in);
    public static void main(String[] args)
    {     
        //test();
        new GothamPD();
    }
}

class Graph { // unweighted, bidirectional
    protected int   V; // number of vertices
    private   int   E; // number of edges
    private   int   capacity;

    List<List<Integer>> adj;
    Graph(int V, int capacity)
    {
        this.capacity=capacity;
        adj=new ArrayList<>(capacity);
        this.V = V;
        E=0;
        for (int v = 0; v < V; v++) // Initialize all lists
            adj.add( new ArrayList<>(10));
    }
    public int V() { return V; }
    public int E() { return E; }
    public int capacity() { return capacity; }
    
    boolean expand(int newsize)
    {
        if ( newsize<=adj.size())
            return false;
        V = newsize;
        while (adj.size()<newsize)
            adj.add( new ArrayList<>(10));  
        return true;
    }
    public void addEdge(int u, int v)
    {
        int newsize=max(u,v)+1;
        if (newsize>adj.size())
            expand(newsize);
        adj.get(u).add(v);
        adj.get(v).add(u);
        E++;
    }
    public List<Integer> adj(int u)
    {
        return adj.get(u);
    }
}

class BreadthFirstPaths
{
    private boolean[] marked; // Is a shortest path to this vertex known?
    private int[] edgeTo; // last vertex on known path to this vertex
    private final int s; // source
    private Graph G;
    LinkedBlockingQueue<Integer> queue;
    public BreadthFirstPaths(Graph G, int s)
    {
        marked = new boolean[G.capacity()];
        edgeTo = new int[G.capacity()];
        this.s = s;
        this.G =G;
        
        queue = new LinkedBlockingQueue<Integer>();
        bfs(s);
    }
    private void bfs(int s)
    {
        marked[s] = true; // Mark the source
        queue.add(s); // and put it on the queue.
        while (!queue.isEmpty())
        {
            int v = queue.poll(); // Remove next vertex from the queue.
            for (int w : G.adj(v))
            if (!marked[w]) // For every unmarked adjacent vertex,
            {
                edgeTo[w] = v; // save last edge on a shortest path,
                marked[w] = true; // mark it because path is known,
                queue.add(w); // and add it to the queue.
            }
        }
    }
    // 
    public void bfsMore(int n)  // 
    {
        assert(marked[n]);
        bfs(n);
    }
    public boolean hasPathTo(int v)
    { 
        return marked[v]; 
    }
    public Iterable<Integer> pathTo(int v)
    {
        if (!hasPathTo(v)) return null;
        Stack<Integer> path = new Stack<Integer>();
        for (int x = v; x != s; x = edgeTo[x])
            path.push(x);
        path.push(s);
        return path;
    }
}

class GraphEx extends Graph
{
    private int K[]; // encriptionKey for each node
    static final int capacity=200005;  // trade off memory for performance
    GraphEx(int V)
    {
        super(V, capacity);
        K=new int[capacity];
    }

    @Override
    boolean expand(int newsize)
    {
        if ( !super.expand(newsize))
            return false;
        if ( K.length<newsize ) {
            //out.println("expand "+newsize+" V "+V);
            int newK[]=new int[newsize];
            for (int i=0; i<K.length; i++)
                newK[i]=K[i];
            K=newK;
        }
        return true;
    }
    void setKey(int v, int k)
    {
        if (v<V)
            K[v]=k;
        else 
            out.println("Error bad node "+v);
    }
    
    int[] findMinMax(Iterable<Integer> p, int k)
    {
        //p.forEach (out::println) ;
        int minX=Integer.MAX_VALUE;
        int maxX=0;
        for (int w: p) {
            int k2=K[w]^k;
            minX=min(minX, k2);
            maxX=max(maxX, k2);
        }
        return new int[]{minX, maxX};
    }
    void printKey(int i)
    {
        out.println((i+1)+" "+K[i]);
    }
}

