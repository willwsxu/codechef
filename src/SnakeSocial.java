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
*/
import static java.lang.System.out;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

// SNSOCIAL
class SnakeSocial {
    int a[][];
    int maxV;
    SnakeSocial(int a[][], int m)
    {
        List<Map.Entry<Integer, Integer>> maxEntry=new ArrayList<>();
        for (int i=0; i<a.length; i++)
            for (int j=0; j<a[i].length; j++) {
                if ( a[i][j]==m)
                    maxEntry.add(new SimpleEntry<Integer,Integer>(i,j));
            }
        this.a=a;
        maxV=m;
        out.println(scan(maxEntry));
    }
    void update(int i, int j, List<Map.Entry<Integer, Integer>> newEntry)
    {
        if (i<0 || j<0 || i>=a.length || j>=a[0].length)
            return;
        if(a[i][j]>=maxV)
            return;
        a[i][j]=maxV;
        newEntry.add(new SimpleEntry<Integer,Integer>(i,j));
    }
    int scan(List<Map.Entry<Integer, Integer>> entry)
    {
        List<Map.Entry<Integer, Integer>> newEntry=new ArrayList<>();
        for (Map.Entry<Integer, Integer> e: entry) {
            int i=e.getKey();
            int j=e.getValue();
            update(i-1, j-1, newEntry);
            update(i-1, j, newEntry);
            update(i-1, j+1, newEntry);
            update(i, j-1, newEntry);
            update(i, j+1, newEntry);
            update(i+1, j-1, newEntry);
            update(i+1, j, newEntry);
            update(i+1, j+1, newEntry);
        }
        if ( newEntry.isEmpty())
            return 0;
        else {
            return 1+scan(newEntry);
        }
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
        for (int i=0; i<T; i++) {
            int n=sc.nextInt();     // 1 ≤ n, m ≤ 50
            int m=sc.nextInt();
            int a[][]=new int[n][m];// 1 ≤ a[i][j] ≤ 10^6
            int mx=fillMatrix(a, sc);
            new SnakeSocial(a, mx);
        }
    }
}
