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
        pi(int f, int s)
        {
            first = f;
            second =s;
        }
    }
    static final int MX = 1000; // max vertices
    pi [][]dp = new pi[MX][25];
    pi [][]f = new pi[1<<10][25];  // at most levels can be unlocked each time
    // editorial impl
    void dfs(int v)
    {
        for (int u: levels.get(v))
            dfs(u);
        int n = levels.get(v).size();
        out.println(v+1+":"+n);
        // initalize f[][] with infinities
	for (int msk = 0; msk < (1 << n); msk++)
            for (int p = 0; p <= maxH; p++)
		f[msk][p] = new pi(MX, maxH);
        // first of all we should complete level 'v'
	// we will put the time taken into the base state
	// of our second dp - f[0][p], that corresponds to
	// a state with none of the children completed
		
	// we have enough time on the first workday:
	for (int p = 0; p + gameHours.get(v) <= maxH; p++) 
            f[0][p] = new pi(0, p + gameHours.get(v));
	// we don't have enough time on the first workday:
	for (int p = maxH + 1 - gameHours.get(v); p <= maxH; p++) 
            f[0][p] = new pi(1, gameHours.get(v));
        
        for (int msk = 0; msk < (1 << n); msk++)
            for (int i = 0; i < n; i++)
                if (((msk >> i) & 1) == 0)
                    for (int p = 0; p <= maxH; p++) {
                        // we have already completed all the subtrees
                        // described by 'msk' and the next subtree will be 'i'.

                        // the state right after we have finished all the
                        // subtrees in 'msk'
                        pi a = f[msk][p];

                        // dp value that we want to update (after adding subtree)
                        pi b = f[msk | (1 << i)][p];

                        // time spent in newly added subtree, we will have
                        // a.second hours worked on the first workday (the last
                        // workday to complete all subtrees in 'msk')
                        pi c = dp[levels.get(v).get(i)][a.second];

                        // overall we will spend (a.first + c.first) additional
                        // workdays and will have worked exactly c.second hours
                        // during the last one
                        if ( a.first + c.first < b.first)
                            f[msk | (1 << i)][p] = new pi(a.first + c.first, c.second);
                    }	
        // update values of global dp[][] using the values of second dp
	for (int p = 0; p <= maxH; p++) 
            dp[v][p] = f[(1 << n) - 1][p];
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
        out.println(dp[0][maxH].first);
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
