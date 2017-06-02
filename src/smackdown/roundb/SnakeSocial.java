package smackdown.roundb;



/*
 * Brief Desc: snake houses in a n x m grid
 * A bill has been passed for making a smooth transition from capitalism to socialism. 
 * At the end of each hour, the wealth of a snake will grow and will become equal to 
 * the highest wealth that its neighbor had (at the start of the hour). That is, 
 * consider the maximum wealth among its neighbors at the start of the hour, and this 
 * snake's wealth will become equal to that at the end of the hour. Note that this 
 * increase in wealth will happen simultaneously for each snake. Two houses are said 
 * to be neighbors of each other if they share a side or corner with each other. 
 * Find out the minimum number of hours required for this transition.
 * Strategy:
 * find the cells with highest wealth and put them in a list, transform all its neighbors.
 * Add all new wealthy cells into list, and repeat until no more cells transformed
 ************************************
 * Use Graph theory, multi source BFS:
 * Construct graph from grid, find the nodes with max wealth, BFS and the furtherest distance
 * will be the answer. 
 * Create a artificial node to connect to max nodes, so it becomes single source BFS
*/
import static java.lang.Integer.max;
import static java.lang.System.out;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.ConcurrentLinkedQueue;

// SNSOCIAL, Easy - Medium, BFS
class SnakeSocial {
    int a[][];
    int maxV;
    static final int x[]=new int[]{-1,-1,-1,0,0,1,1,1};
    static final int y[]=new int[]{-1,0,1,-1,1,-1,0,1};
    List<Map.Entry<Integer, Integer>> maxEntry=new ArrayList<>();
    
    SnakeSocial()
    {
        int n=sc.nextInt();     // 1 ≤ n, m ≤ 50
        int m=sc.nextInt();
        int a[][]=new int[n][m];// 1 ≤ a[i][j] ≤ 10^6
        int mx=fillMatrix(a, sc);  
        init(a,mx);
    }
    private void init(int a[][], int m)
    {
        for (int i=0; i<a.length; i++)
            for (int j=0; j<a[i].length; j++) {
                if ( a[i][j]==m)
                    maxEntry.add(new SimpleEntry<Integer,Integer>(i,j));
            }
        this.a=a;
        maxV=m;
    }
    boolean valid(int i, int j) {        
        if (i<0 || j<0 || i>=a.length || j>=a[0].length)
            return false;
        return true;
    }
    void update(int i, int j, List<Map.Entry<Integer, Integer>> newEntry)
    {
        if ( !valid(i,j) )
            return;
        if(a[i][j]>=maxV)
            return;
        a[i][j]=maxV;
        newEntry.add(new SimpleEntry<Integer,Integer>(i,j));
    }
    int adhoc(List<Map.Entry<Integer, Integer>> entry)
    {
        List<Map.Entry<Integer, Integer>> newEntry=new ArrayList<>();
        for (Map.Entry<Integer, Integer> e: entry) {
            int i=e.getKey();
            int j=e.getValue();
            for (int k=0; k<x.length; k++)
                update(i+x[k], j+y[k], newEntry);
        }
        if ( newEntry.isEmpty())
            return 0;
        else {
            return 1+adhoc(newEntry);
        }
    }
    boolean vis[][];
    int d[][];
    int bfs()
    {
        int n=a.length;
        int m=a[0].length;
        vis = new boolean[n][m];
        d=new int[n][m];
        ConcurrentLinkedQueue<Map.Entry<Integer, Integer>> q=new ConcurrentLinkedQueue<>();
        for (Map.Entry<Integer, Integer> e: maxEntry) {
            q.add(e);
            vis[e.getKey()][e.getValue()]=true;
        }
        int ans=0;
        while (!q.isEmpty()) {
            Map.Entry<Integer, Integer> e=q.poll();
            ans=max(ans, d[e.getKey()][e.getValue()]);
            for (int k=0; k<x.length; k++) {
                int i=e.getKey()+x[k];
                int j=e.getValue()+y[k];
                if ( valid(i,j) && !vis[i][j] ) {
                    q.add(new SimpleEntry<Integer,Integer>(i,j) );
                    d[i][j] = d[e.getKey()][e.getValue()]+1;
                    vis[i][j]=true;
                }                    
            }
        }
        return ans;
    }
    
    void solve()
    {
        out.println(bfs());    
        //out.println(adhoc(maxEntry));        
    }
    static int fillMatrix(int [][] a, Scanner reader) // shared
    {
        int m=0; // max value
        for (int i=0; i<a.length; i++)
            for (int j=0; j<a[i].length; j++) {
                a[i][j]=reader.nextInt();
                if ( a[i][j]>m)
                    m=a[i][j];
            }
        return m;
    }
    static Scanner sc = new Scanner(System.in);
    public static void main(String[] args)
    {      
        int T=sc.nextInt();     // 1 ≤ T ≤ 4
        while (T-->0)
            new SnakeSocial().solve();
    }
}
