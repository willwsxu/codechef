
import static java.lang.System.out;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

class LunchMar17Species {
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
    LunchMar17Species(String [] x)
    {
        int N = x.length;
        for (int i=0; i<N*N; i++)
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
        visited = new boolean[N*N];
        long total=1;
        for (int i=0; i<N*N; i++)
        {
            if (visited[i])
                continue;
            visited[i]=true;
            List<Integer> node = graph.get(i);
            if (node.isEmpty()) {
                int r = i/N;
                int c = i%N;
                if (x[r].charAt(c)=='?')
                    total *=3;
                if (total >= MOD)
                    total -= MOD;
                continue;
            }
            StringBuilder build = new StringBuilder();
            dfs(x, i, N, build);
            String cells = build.toString();
            boolean b=cells.indexOf('B')>=0;
            boolean p=cells.indexOf('P')>=0;
            if (cells.indexOf('G')>=0 || b&&p) {
                total=0;
                break;
            }
            if (b || p)
                total *= 1;
            else
                total *= 2;
            if (total >= MOD)
                total -= MOD;
        }
        out.println(total);
    }
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
            new LunchMar17Species(board);
        }
    }
    public static void main(String[] args)
    {
        //scan = codechef.CodeChef.getFileScanner("robot.txt");
        //Instant start = Instant.now();
        autoTest();
        //Instant end = Instant.now();
        //out.println("usec "+ChronoUnit.MICROS.between(start, end));       
    } 
}
