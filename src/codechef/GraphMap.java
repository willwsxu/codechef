
package codechef;

import static java.lang.System.out;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class GraphMap { // unweighted, bidirectional
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
    public GraphMap()
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
    public void printKey(int i)
    {
        out.println((i+1)+" "+nodes.get(i).key);
    }
    public int getKey(int v)
    {
        return nodes.get(v).key;
    }
}
