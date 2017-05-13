/*
 * Adjacency list Graph
 */
package codechef;

import static java.lang.Integer.max;
import java.util.ArrayList;
import java.util.List;


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
