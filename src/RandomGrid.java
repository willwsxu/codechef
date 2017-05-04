/* Brief Description: In a grid with free (.) and blocked(#) cells, Given a sequence
 * of moves. e.g. "LRUD", calculate how many moved each cell can make before beong blocked
 * output: XOR of moves of all cells
 * Editorial strategy:
 * if P>=0.1, brute force is good enough as cell can rarely finish all moved, expected length is L/10
 *  worse case N*N*K/10=5*10^8
 * if P<0.1, go reverse from a blocked cell, worse case N*N*K/10=5*10^8
 * if P=0, we can preprocess the box that fit for all moves, min and max row and col
 * Test show bruteforce use least time even when P is 0.01
*/
import codechef.GridHelper;
import static java.lang.System.out;
import java.util.Arrays;
import java.util.Scanner;

// medium, randomized algorithms
class RandomGrid {
    String grid[];
    String moves;
    int moves(int r, int c)
    {
        int i=0;
        int []next=new int[]{r,c};
        for ( i=0; i<moves.length(); i++) {
            next = gh.next(next[0], next[1], gh.getDir(moves.charAt(i)));
            if (next==null)
                break;
            if (grid[next[0]].charAt(next[1])=='#')
                break;
        }
        return i;
    }
    int bruteforce()  // subtask 3, p>=0.1, less than 1 sec
    {
        gh.fill(mg, moves.length());  // initialized to max value possible
        int xor=0;
        for (int i=0; i<grid.length; i++) 
            for (int j=0; j<grid.length; j++)
                if (grid[i].charAt(j)=='.') {
                    mg[i][j]=moves(i,j);
                    xor ^= mg[i][j];
                }
        return xor;
    }
    
    int openGrid()  // subtask 2, P=0, 4 sec. less than 1 sec if use sparse
    {
        gh.fill(mg, moves.length());  // initialized to max value possible
        int xor=0;
        for (int i=0; i<grid.length; i++) 
            for (int j=0; j<grid.length; j++)
                if (grid[i].charAt(j)=='.') {
                    if (!gh.inBox(i, j, movingBox))
                        mg[i][j]=moves(i,j);
                    xor ^= mg[i][j];
                }
        return xor;
    }
    int N;
    GridHelper gh;
    int movingBox[]; // grid box required to allow all the moves
    int[][]mg;  // grid for calculating moves
    // from blocked cell, reverse steps, e.g if (3,3) is blocked, moves is "LURRD"
    // reverse steps as "RDLLU", (3,4), (4,4), (4,3), (4,2), (3,2)
    // with 1, 2, 3, 4, 5 moves
    int sparse()  //P<0.1, up to 9 sec, P<0.01, up to 3 sec
    {
        gh.fill(mg, moves.length());  // initialized to max value possible
        // find all cells that will end up being blocked
        for (int i=-1; i<=N; i++) 
            for (int j=-1; j<=N; j++)
                if (i<0 || j<0 || i==N || j==N || grid[i].charAt(j)!='.') { // blocked 
                    int []cell=new int[]{i,j};
                    for ( int k=0; k<moves.length(); k++) {
                        if ( gh.next(cell, gh.getReverse(moves.charAt(k))) 
                                && grid[cell[0]].charAt(cell[1]) == '.')
                        {
                            if ( mg[cell[0]][cell[1]]>k)
                                mg[cell[0]][cell[1]]=k;  // update only if it is smaller
                        }// don't break when cell is blocked
                    }
                }
        int xor=0;
        for (int i=0; i<grid.length; i++) 
            for (int j=0; j<grid.length; j++) {
                if (grid[i].charAt(j)!='.')
                    continue;
                xor ^= mg[i][j];
            }
        return xor;
    }
    RandomGrid(String[] g, String m)
    {
        grid=g;
        moves=m;
        N=grid.length;
        gh=new GridHelper(N, N);
        movingBox = GridHelper.movingBox(m);
        mg=new int[N][N];
    }
    void solve()
    {
        int blocked=0;
        for (int i=0; i<grid.length; i++) 
            for (int j=0; j<grid.length; j++)
                if (grid[i].charAt(j)!='.')
                    ++blocked;
        if (blocked==0)
            out.println(sparse());
        else if (blocked>=N*N/100)
            out.println(bruteforce());
        else
            out.println(sparse());        
    }
    static void test()
    {
        int[] mbox = GridHelper.movingBox("LLURRRDDD");
        out.println(Arrays.toString(mbox));
        boolean in = GridHelper.inBox_(1,1, 5, 5, mbox);
        out.println("1,1, 5, 5 in "+in);
        in = GridHelper.inBox_(1,2, 4, 3, mbox);
        out.println("1,2, 4, 3 in "+in);
        in = GridHelper.inBox_(1,2, 3, 3, mbox);
        out.println("1,2, 3, 3 in "+in);
        in = GridHelper.inBox_(1,2, 4, 2, mbox);
        out.println("1,2, 4, 2 in "+in);
        
        RandomGrid rg=new RandomGrid(new String[]{".....", ".....", ".....", "....."}, "LLURRRDDD");
        out.println(rg.bruteforce());
        GridHelper.print(rg.mg);
        out.println(rg.openGrid());
        GridHelper.print(rg.mg);
        out.println(rg.sparse());
        GridHelper.print(rg.mg);
    }
    static Scanner sc = new Scanner(System.in);
    public static void main(String[] args)
    {
        //test();
        //sc = codechef.ContestHelper.getFileScanner("randomgrid-t.txt");
        int TC = sc.nextInt();  // between 1 and 3
        for (int i=0; i<TC; i++) {
            int L = sc.nextInt();   // 1 ≤ L ≤ 5000
            int N = sc.nextInt();   // 1 ≤ N ≤ 1000
            String moves= sc.next();
            String grid[]=new String[N];
            for (int j=0; j<N; j++)
                grid[j]=sc.next();
            new RandomGrid(grid, moves).solve();
        }
    }
}
