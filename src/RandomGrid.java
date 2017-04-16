
import static java.lang.System.out;
import java.util.Scanner;


class RandomGrid {
    String grid[];
    String moves;
    int moves(int r, int c)
    {
        int N=grid.length;
        int i=0;
        forloop:
        for ( i=0; i<moves.length(); i++) {
            switch(moves.charAt(i)){
                case 'D':
                    if (r++==N-1)
                        break forloop;
                    if (grid[r].charAt(c)=='#')
                        break forloop;
                    break;
                case 'U':
                    if (r--==0)
                        break forloop;
                    if (grid[r].charAt(c)=='#')
                        break forloop;
                    break;
                case 'L':
                    if (c--==0)
                        break forloop;
                    if (grid[r].charAt(c)=='#')
                        break forloop;
                    break;
                case 'R':
                    if (c++==N-1)
                        break forloop;
                    if (grid[r].charAt(c)=='#')
                        break forloop;
                    break;
            }
        }
        return i;
    }
    RandomGrid(String[] g, String m)
    {
        grid=g;
        moves=m;
        int xor=0;
        for (int i=0; i<g.length; i++) 
            for (int j=0; j<g.length; j++)
                if (g[i].charAt(j)=='.')
                    xor ^= moves(i,j);
        out.println(xor);
    }
    static Scanner sc = new Scanner(System.in);
    public static void main(String[] args)
    {
        //sc = codechef.ContestHelper.getFileScanner("digitscount-t.txt");
        int TC = sc.nextInt();  // between 1 and 2
        for (int i=0; i<TC; i++) {
            int L = sc.nextInt();   // 1 ≤ L ≤ 5000
            int N = sc.nextInt();   // 1 ≤ L ≤ 1000
            String moves= sc.next();
            String grid[]=new String[N];
            for (int j=0; j<N; j++)
                grid[j]=sc.next();
            new RandomGrid(grid, moves);
        }
    }
}
