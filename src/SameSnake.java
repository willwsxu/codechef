
import static java.lang.System.out;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

/*
 */
class Snake
{
    Point[] p;
    boolean bHorizontal=true;
    Snake(int x1, int y1, int x2, int y2)
    {
        if ( x1==x2) {
            if ( y2<y1) {
                int y=y1;
                y1 = y2;
                y2=y;
            }
            bHorizontal=true;
        } else {
            if ( x2<x1) {
                int x=x1;
                x1 = x2;
                x2=x;
            }
            bHorizontal=false;
        }        
    }
    boolean same(Snake s2) {
        if (bHorizontal==s2.bHorizontal) {  // same direction
            
        } else
        {
            
        }
        return false;
    }
}
class SameSnake {
    PointGraph g=new PointGraph();
    SameSnake()
    {        
    }
    void addCells(int x1, int y1, int x2, int y2)
    {
        g.addNode(x1, y1);
        g.addNode(x2, y2);
        if ( x1==x2) {
            if ( y2<y1) {
                int y=y1;
                y1 = y2;
                y2=y;
            }
            for (int y=y1+1; y<=y2; y++)
                g.add(x1, y-1, x2, y);
        } else {
            if ( x2<x1) {
                int x=x1;
                x1 = x2;
                x2=x;
            }
            for (int x=x1+1; x<=x2; x++)
                g.add(x-1, y1, x, y2);            
        }
    }
    boolean same()
    {
        PointCC cc=new PointCC(g);
        if (!cc.connected()) {
            return false;
        }
        if (g.degree()>2) {
            //out.println("degree "+g.degree());
            return false;
        }
        return true;
    }
    
    static void test()
    {
        SameSnake s = new SameSnake();
        s.addCells(2, 1, 8, 1);
        s.addCells(11, 1, 7, 1);
        s.g.print();
        out.println(s.same()==true);
        
        s = new SameSnake();
        s.addCells(2, 1, 8, 1);
        s.addCells(11, 1, 9, 1);
        s.g.print();
        out.println(s.same()==false);
        
        s = new SameSnake();
        s.addCells(2, 1, 8, 1);
        s.addCells(3, 1, 3, -2);
        s.g.print();
        out.println(s.same()==false);
        
        s = new SameSnake();
        s.addCells(2, 1, 8, 1);
        s.addCells(2, 1, 2, -2);
        s.g.print();
        out.println(s.same()==true);
                
        s = new SameSnake();
        s.addCells(2, 1, 2, 1);
        s.addCells(2, 1, 2, -2);
        s.g.print();
        out.println(s.same()==true);
        
        s = new SameSnake();
        s.addCells(2, 0, 2, 0);
        s.addCells(2, 1, 2, -2);
        s.g.print();
        out.println(s.same()==true);
        
        s = new SameSnake();
        s.addCells(2, 0, 2, 0);
        s.addCells(2, 1, 2, 1);
        s.g.print();
        out.println(s.same()==false);
        
        s = new SameSnake();
        s.addCells(2, 0, 2, 0);
        s.addCells(2, 0, 2, 0);
        s.g.print();
        out.println(s.same()==true);
    }
        
    static int[] ria(int N) { // read int array
        int L[]=new int[N];
        for (int i=0; i<N; i++)
            L[i]=sc.nextInt();
        return L;
    }
    static Scanner sc = new Scanner(System.in);
    public static void main(String[] args)
    {      
        //test();
        int T=sc.nextInt();     // 1 ≤ T ≤ 10^5
        StringBuilder sb=new StringBuilder();
        for (int i=0; i<T; i++) {
            int xy[]=ria(8); // -109 ≤ Xij,Yij ≤ 109
            SameSnake s = new SameSnake();
            s.addCells(xy[0], xy[1], xy[2], xy[3]);
            s.addCells(xy[4], xy[5], xy[6], xy[7]);
            sb.append(s.same()?"yes\n":"no\n");
        }
        out.println(sb.toString());
    }
}

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
class PointGraph { // unweighted, bidirectional
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
    PointGraph()
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