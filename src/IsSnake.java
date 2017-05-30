

/* 
 * Snake imprint as 2xN cells on a plate, # is snake boday, . is not
 * snake body parts don't overlap
 * find out if a plate has complete snake body, celld are connected if they share a side
*/
import static java.lang.System.out;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/*4 basic column patterns, #. .# ## .. (p1 to P4)
We don't need to consider p4 as graph is connected
it is always OK if only one end has pattern p1 or p2
if both ends has p1 or p2, 4 basic patterns to consider
.##  good (odd p3)
##
.#   bad  (odd p3)
###

.### bad  (even p3)
###
.##  good (even p3)
####

 Borrow idea from editorial. key is to seaprate as 2 tasks
 Find out if all cells are connected
 If so, analyze patterns to determine if it can form a single path without visiting a node twice
*/
// ISSNAKE easy
class IsSnake {
    SimpleGraph g;
    String[] sp;
    int blackcells=0;
    IsSnake(String[] s)
    {
        sp=s;
        int N=s[0].length();
        g=new SimpleGraph(2*N);
        for (int i=0; i<2; i++) {
            for (int j=0; j<N; j++) {
                if (s[i].charAt(j)=='.')
                    continue;
                blackcells++;
                int u=i*N+j; // vertex from 0 to 2N-1
                if (j<N-1 && s[i].charAt(j+1)=='#') // check if there edge to right
                    g.addEdge(u, u+1);
                if (i==0 && s[i+1].charAt(j)=='#')
                    g.addEdge(u, u+N);
            }
        }
    }
    int pattern(String[] s, int i)
    {
        if (s[0].charAt(i)=='#' && s[1].charAt(i)=='.')
            return 1;
        if (s[0].charAt(i)=='.' && s[1].charAt(i)=='#')
            return 2;
        if (s[0].charAt(i)=='#' && s[1].charAt(i)=='#')
            return 3;
        return 0;
    }
    boolean patternValid(String[] s)
    {
        int p=0; // 0 is p4
        int p3=0; // count p3
        for (int i=0; i<s[0].length(); i++) {
            int next=pattern(s, i);
            //out.println("before p="+p+" next="+next+" p3="+p3);
            switch(next) {
                case 0: p3=0;
                    break;
                case 1:
                case 2:
                    //out.println("p="+p+" next="+next+" p3="+p3);
                    if (p3%2==0) {
                        if (p==3-next) {                            
                            return false;
                        }
                    } else {
                        if (p==next)
                            return false;                        
                    }
                    p=next;
                    p3=0;
                    break;
                case 3:
                    p3++;
                    break;
            }
            //out.println("after  p="+p+" next="+next+" p3="+p3);
        }
        return true;
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
        
        snake=new IsSnake(new String[]{".#.","###"});
        out.println(!snake.isOnePath());
        snake=new IsSnake(new String[]{"##.",".##"});
        out.println(snake.isOnePath());
        
        snake=new IsSnake(new String[]{"#","."});
        out.println(snake.isOnePath());
        snake=new IsSnake(new String[]{".","."});
        out.println(!snake.isOnePath());
        
        snake=new IsSnake(new String[]{"....","##.#"});
        out.println(!snake.isOnePath());
        snake=new IsSnake(new String[]{"...","#.#"});
        out.println(!snake.isOnePath());
    }
    boolean isOnePath()
    {
        if ( blackcells<1 )
            return false;
        if ( blackcells==1 )
            return true;
        CC c = new CC(g);
        if (c.numCompoments(1)!=blackcells) {
            return false;
        }
        //out.println("connected");
        return patternValid(sp);
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

interface IGraph
{
    int V();
    public List<Integer> adj(int u);
}

// vertex from 0
class SimpleGraph implements IGraph { // unweighted, bidirectional
    protected int   V; // number of vertices
    private   int   E; // number of edges

    private List<List<Integer>> adj;
    SimpleGraph(int V)
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
    public List<Integer> adj(int u)
    {
        return adj.get(u);
    }
}

class CC
{
    private IGraph g;
    private int visId[];  // dual purpose, for component id and is visited
    private int id=0;
    public CC(IGraph g)
    {
        this.g=g;
        visId = new int[g.V()];
        for (int s = 0; s < g.V(); s++)
            // no need to visit nodes without any edge
            if (visId[s]==0 && !g.adj(s).isEmpty())
            {
                dfs(s, ++id);
            }
    }
    private void dfs(int v, int id) {
        //out.println("dfs v"+v+" id="+id);
        visId[v]=id;
        for (int w: g.adj(v))
            if (visId[w]==0)
                dfs(w, id);
    }
    public int numCompoments(int ID)  // components with id=ID
    {
        int count=0;
        for (int s = 0; s < g.V(); s++)
            if (visId[s]==ID)
                count++;
        //out.println("singleGraph "+id);
        return count;
    }
    public boolean connected(int v, int w)
    {
        return visId[v]==visId[w] && visId[v]!=0;
    }
}