/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package codechef;

import static java.lang.System.out;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;


// copied from subArray
class Point  // pair of int
{
    int first;
    int second;
    Point(int f, int s)
    {
        first=f;
        second=s;
    }
    @Override
    public boolean equals(Object s)
    {
        if (s instanceof Point) {
            Point other =(Point)s;
            return first==other.first && second==other.second;
        }
        return false;
    }
    @Override
    public int hashCode()
    {
        return (int)(first*second);
    }
    @Override
    public String toString()
    {
        return first+":"+second;
    }
}

// copied from GothamPD
public class PointGraph { // unweighted, bidirectional
    private   int   E; // number of edges

    class Node
    {
        int key;
        List<Point> adj;
        Node(int k) {
            key=k;
            adj=new ArrayList<>(10);
        }
        @Override
        public String toString()
        {
            return adj.toString();
        }
        void add(Point p) {
            if (adj.contains(p))
                return;
            adj.add(p);
        }
    }
    Map<Point, Node> nodes;
    public PointGraph()
    {
        nodes=new HashMap<>();
        E=0;
    }
    
    public Point addNode(int x, int y) // opposite of row, col
    {
        //out.println("node "+u);
        Point p=new Point(x,y);
        if (nodes.containsKey(p))
            return p;
        nodes.put(p, new Node(0));  // no key
        return p;
    }
    //public int V() { return nodes.size(); }
    public int E() { return E; }
    public Set<Point> V() { return nodes.keySet(); }
    
    public void addEdge(Point u, Point v)
    {
        //out.println("edge "+u+" to "+v);
        nodes.get(u).add(v);
        nodes.get(v).add(u);
        E++;
    }
    public void add (int x1, int y1, int x2, int y2) 
    {     
        Point p1 = addNode(x1,y1);
        Point p2 = addNode(x2,y2);
        addEdge(p1, p2);
    }
    public List<Point> adj(Point u)
    {
        return nodes.get(u).adj;
    }
    void printKey(int i)
    {
        out.println((i+1)+" "+nodes.get(i).key);
    }
    int degree()
    {
        int d=0;
        for (Map.Entry<Point, Node> e: nodes.entrySet()) {
            if (d<e.getValue().adj.size())
                d=e.getValue().adj.size();
        }
        return d;
    }
    void print()
    {
        out.println(nodes);
    }
    
    boolean sameSnake() // connected and degress <=2
    {
        PointCC cc=new PointCC(this);
        if (!cc.connected()) {
            return false;
        }
        if (degree()>2) {
            //out.println("degree "+g.degree());
            return false;
        }
        return true;
    }
}
class PointCC
{
    private Map<Point, Boolean> marked;
    PointGraph g;
    public PointCC(PointGraph G)
    {
        g=G;
        marked = new HashMap<>();
        for (Point s: G.V()) {
            marked.put(s, Boolean.FALSE);
        }
        for (Point s: G.V()) {
            dfs(G, s);
            break;  // just start with 1
        }
    }
    private void dfs(PointGraph G, Point v)
    {
        marked.put(v, Boolean.TRUE);
        for (Point w : G.adj(v))
            if (!marked.get(w))
                dfs(G, w);
    }
    boolean connected()
    {
        for (Map.Entry e: marked.entrySet()) {
            if (e.getValue().equals(Boolean.FALSE)) {
                //out.println("not connected "+e);
                return false;
            }
        }
        return true;
    }
}