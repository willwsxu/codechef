
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

public class GothamPD {
    int N, Q;
    GraphEx g;
    boolean dirty=true;
    GothamPD(int n, int q)
    {
        N=n; Q=q;
    }
    GothamPD()
    {
        N=sc.nextInt();  // 1 ≤ N ≤ 100,000, PD
        Q=sc.nextInt();  // 1 ≤ Q ≤ 200,000
    }
    void readNodes()
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
    int query(int v, int k, int last_answer)
    {
        if ( dirty ) {
            bf = new BreadthFirstPaths(g, 0);    
            dirty=false;
        }
        v = (v^last_answer)-1;
        k ^=last_answer;
        assert(v>0);
        Iterable<Integer> p = bf.pathTo(v);  // 1 4 2
        //p.forEach (out::println) ;
        int minX=Integer.MAX_VALUE;
        int maxX=0;
        for (int w: p) {
            int k2=(w+1)^k;
            minX=min(minX, k2);
            maxX=max(maxX, k2);
        }
        out.println(minX+" "+maxX);
        
        return minX^maxX;
    }
    void add(int u, int v, int k, int last_answer)
    {
        u ^=last_answer;
        v ^=last_answer;
        k ^=last_answer;
        //out.println("u "+u+" v "+v+" k "+k);
        g.setKey(u-1, k);
        g.addEdge(u-1, v-1);    
        dirty=true;
    }
    void readQuery(GraphEx g, int Q)
    {
        int last_answer = 0;
        for (int i=0; i<Q; i++) {
            int t = sc.nextInt();
            t ^= last_answer;
            if (t==0) {  // add
                int u=sc.nextInt();
                int v=sc.nextInt();
                int k=sc.nextInt();
                add(u, v, k, last_answer);    
            } else {  // query
                int v=sc.nextInt();
                int k=sc.nextInt();    
                last_answer = query(v, k, last_answer);
            }
        }        
    }
    public static void test()
    {
        GothamPD gpd = new GothamPD(6, 4);
        gpd.g=new GraphEx(6);
        gpd.g.setKey(0, 2);  // 1 2, index from 0
        gpd.add(5, 1, 3, 0);    // 5 1 3
        gpd.add(2, 1, 4, 0);    // 2 1 4
        gpd.add(3, 2, 5, 0);    // 3 2 5
        gpd.add(4, 2, 1, 0);    // 4 2 1
        gpd.add(6, 3, 3, 0);    // 6 3 3
        out.println("Edges "+gpd.g.E());
        int last_answer = gpd.query(4, 2, 0);      // 1 4 2
        // 6 0 12 0
        int t=6^last_answer;
        assert(t==0);
        gpd.add(0, 12, 0, last_answer);
        //gpd.bf.pathTo(9).forEach(out::println);
        // 7 12 7
        t = 7^last_answer;
        last_answer = gpd.query(12, 7, last_answer);
        assert(t==1);
        // 4 0 7
    }
    
    //static MyReader sc = new MyReader();  // for large input
    static Scanner sc = new Scanner(System.in);
    public static void main(String[] args)
    {      
        test();
    }
}

class Graph { // unweighted, bidirectional
    protected int   V; // number of vertices
    private int         E; // number of edges

    List<List<Integer>> adj=new ArrayList<>(2501);
    Graph(int V)
    {
        this.V = V;
        E=0;
        for (int v = 0; v < V; v++) // Initialize all lists
            adj.add( new ArrayList<>(10));
    }
    public int V() { return V; }
    public int E() { return E; }
    void expand(int newsize)
    {
        if ( newsize<=adj.size())
            return;
        V = newsize;
        while (adj.size()<newsize)
            adj.add( new ArrayList<>(10));        
    }
    public void addEdge(int u, int v)
    {
        expand(max(u,v)+1);
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
    public BreadthFirstPaths(Graph G, int s)
    {
        marked = new boolean[G.V()];
        edgeTo = new int[G.V()];
        this.s = s;
        bfs(G, s);
    }
    private void bfs(Graph G, int s)
    {
        LinkedBlockingQueue<Integer> queue = new LinkedBlockingQueue<Integer>();
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
    int K[]; // encriptionKey for each node
    GraphEx(int V)
    {
        super(V);
        K=new int[V];
    }

    void setKey(int v, int k)
    {
        if (v<V)
            K[v]=k;
        else 
            out.println("Error bad node "+v);
    }
}

