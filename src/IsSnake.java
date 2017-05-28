
import static java.lang.System.out;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

class IsSnake {
    SnakeGraph g;
    int blackcells=0;
    IsSnake(String[] s)
    {
        int N=s[0].length();
        g=new SnakeGraph(2*N);
        for (int i=0; i<2; i++) {
            for (int j=0; j<N; j++) {
                if (s[i].charAt(j)=='.')
                    continue;
                blackcells++;
                int u=i*N+j+1; // vertex from 1 to 2N
                if (j<N-1 && s[i].charAt(j+1)=='#') // check if there edge to right
                    g.addEdge(u, u+1);
                if (i==0 && s[i+1].charAt(j)=='#')
                    g.addEdge(u, u+N);
            }
        }
        g.check();
    }
    
    static void test()
    {
        IsSnake snake=new IsSnake(new String[]{"##",".."});
        out.println(snake.isOnePath());
        
        snake=new IsSnake(new String[]{"#.",".#"});
        out.println(!snake.isOnePath());
        
        snake=new IsSnake(new String[]{"#.","##"});
        out.println(snake.isOnePath());
        
        snake=new IsSnake(new String[]{"##","##"});
        out.println(snake.isOnePath());
        
        snake=new IsSnake(new String[]{"##","#."});
        out.println(snake.isOnePath());
        
        snake=new IsSnake(new String[]{"##",".#"});
        out.println(snake.isOnePath());
        
        snake=new IsSnake(new String[]{"#.###..","#######"});
        out.println(!snake.isOnePath());
        
        snake=new IsSnake(new String[]{"##.#..",".###.."});
        out.println(snake.isOnePath());
        
        snake=new IsSnake(new String[]{"##.##",".#.#."});
        out.println(!snake.isOnePath());
        
        snake=new IsSnake(new String[]{"#.####..","########"});
        out.println(snake.isOnePath());
    }
    boolean isOnePath()
    {
        //out.println("vis "+g.getVisCount()+" black="+blackcells);
        return g.getVisCount()==blackcells;
    }
    
    static Scanner sc = new Scanner(System.in);
    public static void main(String[] args)
    {      
        //test();
        int T=sc.nextInt(); // 1 ≤ T ≤ 500
        for (int i=0; i<T;i++) {
            int N=sc.nextInt(); //1 ≤ n ≤ 500
            String[] snake=new String[2];
            snake[0]=sc.next();
            snake[1]=sc.next();
            if (snake[0].length()!=N || snake[1].length()!=N) {
                out.println("Bad N");
                continue;
            }
            out.println(new IsSnake(snake).isOnePath()?"yes":"no");
        }
    }
}

// vertex from 1
class SimpleGraph { // unweighted, bidirectional
    protected int   V; // number of vertices
    private   int   E; // number of edges

    List<List<Integer>> adj;
    SimpleGraph(int V)
    {
        adj=new ArrayList<>(V+1);
        this.V = V;
        E=0;
        for (int v = 0; v <= V; v++) // Initialize all lists
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
    public List<Integer> adj(int u)
    {
        return adj.get(u);
    }
}

class SnakeGraph extends SimpleGraph
{
    boolean vis[];
    int n[]=new int[4];// nodes of degree 0, 1, 2, 3
    int v1; //first node of degree 1
    int v2; //first node of degree 2;
    int visCount=0;
    public SnakeGraph(int v){
        super(v);
        vis = new boolean[v+1];
    }
    void check()
    {
        for (int i=1; i<=V; i++)
        {
            //out.println(i+" node edges "+adj.get(i));
            n[adj.get(i).size()]++;
            if ( n[1]==1 && v1==0) {
                v1=i;
            }
            if ( n[2]==1 && v2==0)
                v2=i;
        }
        //out.println(Arrays.toString(n)+" v1="+v1+" v2="+v2);
        if ( v1>0)
            singlePath(v1);
        else if (v2>0)
            singlePath(v2);
    }
    void singlePath(int u)
    {
        //out.println("visit "+u+" count "+visCount);
        if (vis[u])
            return;
        visCount++;
        vis[u]=true;
        List<Integer> q=new ArrayList<>();
        for (int v:adj.get(u)) {
            if (vis[v])
                continue;
            if (adj.get(v).size()<=2) {
                q.add(v);
            }
        }
        for (int v:adj.get(u)) {
            if (vis[v])
                continue;
            if (adj.get(v).size()<=2) 
                continue;
            if (q.isEmpty()) {
                q.add(v);
            } else {
                //out.println(v+" remove "+u);
                adj.get(v).remove(new Integer(u));
            }
        }
        for (int v: q) {
            singlePath(v);
            break;
        }
    }
    int getVisCount()
    {
        return visCount;
    }
}
