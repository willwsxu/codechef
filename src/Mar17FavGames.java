    /**
 * First, notice that the game-play given in the statement just
 * describes a DFS order, so the levels should be completed in
 * an order of DFS.
 * We will use dynamic programming to solve the problem.
 * dp[v][p] = (a, b)
 * Consider subtree of vertex 'v', Chef has already worked 'p' hours
 * during the first workday. 'a' is the minimal number of additional workdays
 * necessary to complete the whole subtree of vertex 'v' and 'b' is the
 * minimal number of hours Chef has to work on the last of (a + 1) workdays.
 * dp[root][h] is the answer
 * To compute the values of this dynamic programming we can use...
 *                                         ... another dynamic programming :D
 * f[msk][p] = (a, b)
 * msk in a bitmask that describes some subset of vertex 'v' sons
 * Assume we have started in vertex 'v' and Chef has already worked 'p' hours
 * during first workday, after he has completed all the subtrees described by
 * 'msk'. The meaning of 'a' and 'b' here is the same as in dp[v][p].
 * To calculate the second dynamic programming we can just extend the 'msk' by
 * one bit every time. Then dp[v][p] is just f[full mask][p].
 **/

import static java.lang.System.out;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Scanner;


public class Mar17FavGames {
    List<List<Integer>> levels;
    List<Integer> gameHours;
    int maxH;
    Mar17FavGames(int N, int h)
    {
        maxH=h;
        levels = new ArrayList<>(N);
        gameHours = new ArrayList<>(N);
        for (int j=0; j<N; j++) 
            gameHours.add(scan.nextInt());
        for (int j=0; j<N; j++) {
            int count = scan.nextInt();
            if (count>0) {
                levels.add(new ArrayList<>(10));
                for (int k=0; k<count; k++) {
                    int v = scan.nextInt();
                    levels.get(j).add(v-1);  // chnage vertex to count from 0
                }
            }
            else
                levels.add(new ArrayList<>());
        }        
    }
    int calcMinDays(List<Integer> games)
    {
        if (games.size()==1)
            return 1;
        return games.size();
    }
    
    class pi{  // int pair
        int first;
        int second;
    }
    static final int MX = 1000; // max vertices
    pi [][]dp = new pi[MX][25];
    // editorial impl
    void dfs(int v)
    {
        out.println(v+1);
        for (int u: levels.get(v))
            dfs(u);
    }
    void solve()
    {
        int days=1;
        for (int i=0; i<levels.size(); i++)
        {
            days += calcMinDays(levels.get(i));
        }
        out.println(days);
        dfs(0);
    }
    
    static Scanner scan = new Scanner(System.in);
    static void autoTest()
    {        
        int TC = scan.nextInt();  // between 1 and 10
        for (int i=0; i<TC; i++) {
            int N = scan.nextInt();  // game levels, between 1 and 10^3
            int h = scan.nextInt();  // hours to play per day, 1 to 24
            new Mar17FavGames(N, h).solve();
        }
    }
    public static void main(String[] args)
    {
        scan = codechef.CodeChef.getFileScanner("favGames-t.txt");
        autoTest();
    }
}
