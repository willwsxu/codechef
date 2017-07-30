
package codechef;

import java.util.ArrayList;
import java.util.List;

public class GraphWeighted {
    //static final int MAX_NODE=1000;
    private final int   V; // number of vertices
    private int         E; // number of edges
    List<List<Edge>>        adj;// adjacency lists

    public GraphWeighted(int V)
    {
        this.V = V;
        E=0;
        adj = new ArrayList<>(V);
        for (int v = 0; v < V; v++) // Initialize all lists
            adj.add( new ArrayList<>(10));
    }
    public int V() { return V; }
    public int E() { return E; }
    public List<Edge> adj(int v) {
        return adj.get(v);
    }
    public void addEdge(Edge e) // undirected
    {
        int v = e.either(), w = e.other(v);
        adj(v).add(e);
        adj(w).add(e);
        E++;
    }
    public void addDirectEdge(int v, int w, long wt)
    {
        adj(v).add(new Edge(v, w, wt));
        E++;        
    }
    public void addDirectEdge(Edge e)
    {
        int v = e.either(), w = e.other(v);
        adj(v).add(e);
        E++;
        //out.println(" edge from "+(v+1)+" to "+(w+1)+" w="+e.weight());
    }
}

