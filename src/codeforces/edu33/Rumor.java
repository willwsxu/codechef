/*
 * Given n people, some are friends with each other will share any info
 * Vova wants to spread a rumor to all people. She can bribe someone to start the rumor
 * What is the minimal bribe she has to pay
 */
package codeforces.edu33;

import codechef.DSU;
import java.util.ArrayList;
import java.util.List;

class Rumor {
    DSU dsu;
    int bribe[];
    
    Rumor(int b[]) //1 ≤ n ≤ 10^5, 0 ≤ m ≤ 10^5
    {
        bribe=b;
        dsu=new DSU(b.length+1);
    }
    void readEdge()
    {
        
    }
    static void test()
    {
        Rumor r=new Rumor(new int[]{2,5,3,4,8});
        r.dsu.union(1,4);
        r.dsu.union(4,5);
    }
    
    GraphSimple g;
    boolean visited[];
    void dfsAll()
    {
        visited = new boolean[g.V()];
        for (int i=0; i<g.V(); i++) {
            
        }
    }
    int dfs(int u, int b)
    {
        if (visited[u])
            return b;
        for (int v: g.adj(u)) {
            
        }
    }
}
class GraphSimple{ // unweighted, bidirectional
    protected int   V; // number of vertices
    private   int   E; // number of edges

    private List<List<Integer>> adj;
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
    public void addEdgeDirect(int u, int v)
    {
        //out.println("add edge "+u+","+v);
        adj.get(u).add(v);
        E++;
    }
    public List<Integer> adj(int u)
    {
        return adj.get(u);
    }
}