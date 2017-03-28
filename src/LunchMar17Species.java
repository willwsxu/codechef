
import static java.lang.System.out;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

class GraphList
{
    // name each cell of the grid as node, from 0 to N*N-1
    // translate n=i*N+j
    List<List<Integer>> graph=new ArrayList<>(2501);
    void add(String [] x, int current, int i, int j, int N)
    {
        if ( x[i].charAt(j) == '.' )
            return;
        int next = (i)*N+j;
        graph.get(current).add(next);
        graph.get(next).add(current);
    }
    boolean []visited;
    void dfs(String [] x, int current, int N, StringBuilder build) {
        int r = current/N;
        int c = current%N;
        build.append(x[r].charAt(c));
        visited[current]=true;
        for (int next: graph.get(current))
        {
            if (!visited[next])
                dfs(x, next, N, build);
        }
    }
    void print()
    {
        for (int i=0; i<graph.size(); i++)
            out.println(i+":"+graph.get(i));
    }
    void solve(String [] x)
    {
        int N = x.length;
        visited = new boolean[N*N];
        Arrays.fill(visited, false);
        for (int i=0; i<visited.length; i++)
            graph.add(new ArrayList<>());
        for (int i=0; i<N; i++) {
            for (int j=0; j<N; j++) {
                if (x[i].charAt(j) =='.')
                    continue;
                int current = i*N+j;
                if (i<N-1)
                    add(x, current, i+1, j, N);
                if (j<N-1)
                    add(x, current, i, j+1, N);
            }
        }
        //print();
        long total=1;
        for (int i=0; i<visited.length; i++)
        {
            if (visited[i])
                continue;
            StringBuilder build = new StringBuilder();
            dfs(x, i, N, build);
            String cells = build.toString();
            if (cells.length()==1) {
                if (cells.charAt(0)=='?')
                    total *=3;
            } else {
                boolean b=cells.indexOf('B')>=0;
                boolean p=cells.indexOf('P')>=0;
                if (cells.indexOf('G')>=0 || b&&p) {
                    total=0;
                    break;
                }
                if (!b && !p )
                    total *= 2;
            }
            if (total >= LunchMar17Species.MOD)
                total -= LunchMar17Species.MOD;
        }
        out.println(total);
    }
}
class GraphMatrix
{
    boolean vis[][];
    void visit(String[] x, int r, int c, Map<Character, Integer> connected)
    {
        if (r<0 || c<0 || r>=x.length || c>=x.length)
            return;
        if (vis[r][c] || x[r].charAt(c)=='.')
            return;
        dfs(x, r, c, connected);
    }
    void dfs(String[] x, int r, int c, Map<Character, Integer> connected)
    {
        vis[r][c]=true;
        connected.compute(x[r].charAt(c), (k,v)->v==null?1:v+1 );
        visit(x, r-1, c, connected);
        visit(x, r+1, c, connected);
        visit(x, r, c-1, connected);
        visit(x, r, c+1, connected);
    }
    GraphMatrix(String []x)
    {
        int N = x.length;
        vis = new boolean[x.length][x.length];
        long total=1;
        for (int i=0; i<N; i++) {
            for (int j=0; j<N; j++) {
                if (x[i].charAt(j) =='.' || vis[i][j])
                    continue;
                Map<Character, Integer> connected = new HashMap<>();
                dfs(x, i, j, connected);
                //print(connected);
                if (connected.keySet().contains('G')) {
                    if (connected.get('G')>1 || connected.keySet().size()>1) {
                        out.println(0);
                        return;
                    }
                    continue;
                }
                boolean b=connected.keySet().contains('B');
                boolean p = connected.keySet().contains('P');
                if (b && p) {
                    out.println(0);
                    return;                
                }
                if (!b && !p) {
                    if (connected.get('?')>1)
                        total *=2;
                    else
                        total *=3;
                    if (total >= LunchMar17Species.MOD)
                        total -= LunchMar17Species.MOD;
                }
            }
        }
        out.println(total);
    }
    void print(Map<Character, Integer> connected)
    {
        connected.entrySet().stream().forEach(entry->System.out.println(entry.getKey()+" count "+entry.getValue()));
    }
}
class LunchMar17Species {    
    static final int MOD = 1000000007;
    static Scanner scan = new Scanner(System.in);
    public static void autoTest()
    {
        int TC = scan.nextInt();  // between 1 and 50
        for (int i=0; i<TC; i++) {
            int N = scan.nextInt();  // between 2 to 50
            String []board = new String[N];
            for (int j=0; j<N; j++) {
                board[j] = scan.nextLine();
                if (board[j].isEmpty())
                    board[j] = scan.nextLine();
            }
            //for(String x: board)
            //    out.println(x);
            //new GraphList().solve(board);
            new GraphMatrix(board);
        }
    }
    public static void main(String[] args)
    {
        autoTest(); 
    } 
}
