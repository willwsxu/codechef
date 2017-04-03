
import static java.lang.System.out;
import java.util.ArrayList;
import java.util.List;
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
            levels.add(new ArrayList<>(10));
            if (count>0) {
                for (int k=0; k<count; k++) {
                    int v = scan.nextInt();
                    levels.get(j).add(v);
                }
            }
        }        
    }
    int calcMinDays(List<Integer> games)
    {
        if (games.size()==1)
            return 1;
        return games.size();
    }
    int minDays=0;
    void dfs(int v)
    {
        out.println(v);
        for (int u: levels.get(v-1))
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
        dfs(1);
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
