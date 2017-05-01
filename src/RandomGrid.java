
import static java.lang.System.out;
import java.util.Scanner;

class GridHelper
{
    enum DIRECTION {UP, DOWN, LEFT, RIGHT, ERROR};
    int R, C; // RxC dimention
    GridHelper(int r, int c)
    {
        R=r; C=c;
    }
    int[] next(int r, int c, DIRECTION d) {
        switch(d){
            case DOWN:
                if (++r==R)
                    return null;
                break;
            case UP:
                if (r--==0)
                    return null;
                break;
            case LEFT:
                if (c--==0)
                    return null;
                break;
            case RIGHT:
                if (++c==C)
                    return null;
                break;
        }
        return new int[]{r, c};
    }
    DIRECTION getDir(char ch)
    {
        switch(ch){
            case 'D': return DIRECTION.DOWN;
            case 'U': return DIRECTION.UP;
            case 'L': return DIRECTION.LEFT;
            case 'R': return DIRECTION.RIGHT;
        }
        return DIRECTION.ERROR;
    }
    DIRECTION getReverse(char ch)
    {
        switch(ch){
            case 'D': return DIRECTION.UP;
            case 'U': return DIRECTION.DOWN;
            case 'L': return DIRECTION.RIGHT;
            case 'R': return DIRECTION.LEFT;
        }
        return DIRECTION.ERROR;
    }
    static void print(int g[][])
    {
        for (int i=0; i<g.length; i++) {
            for (int j=0; j<g[0].length; j++)
                out.print(g[i][j]+" ");
            out.println();
        }
    }
}

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
    int bruteforce()
    {
        int xor=0;
        for (int i=0; i<grid.length; i++) 
            for (int j=0; j<grid.length; j++)
                if (grid[i].charAt(j)=='.')
                    xor ^= moves(i,j);
        return xor;
    }
    GridHelper gh;
    int[][]mg;  // grid for calculating moves
    // from blocked cell, reverse steps, e.g if (3,3) is blocked, moves is "LURRD"
    // reverse steps as "RDLLU", (3,4), (4,4), (4,3), (4,2), (3,2)
    // with 1, 2, 3, 4, 5 moves
    int sparse()
    {
        mg=new int[grid.length][grid.length];
        // find all cells thatr will end up being blocked
        for (int i=0; i<grid.length; i++) 
            for (int j=0; j<grid.length; j++)
                if (grid[i].charAt(j)!='.') {
                    int step=0;
                    for ( int k=0; k<moves.length(); k++) {
                        int cell[]= gh.next(i, j, gh.getReverse(moves.charAt(k)));
                        if (cell==null)
                            break;
                        if ( grid[cell[0]].charAt(cell[1]) == '#' )
                            break;
                        ++step;
                        if ( mg[cell[0]][cell[1]]==0 || mg[cell[0]][cell[1]]>step)
                            mg[cell[0]][cell[1]]=step;  // update only if it is smaller
                    }
                }
        GridHelper.print(mg);
        int xor=0;
        for (int i=0; i<grid.length; i++) 
            for (int j=0; j<grid.length; j++) {
                if (grid[i].charAt(j)!='.')
                    continue;
                if (mg[i][j]>0)
                    xor ^= mg[i][j];
                else
                    xor ^= moves(i,j);
            }
        return xor;
    }
    RandomGrid(String[] g, String m)
    {
        grid=g;
        moves=m;
        int N=grid.length;
        gh=new GridHelper(N, N);
        int blocked=0;
        for (int i=0; i<grid.length; i++) 
            for (int j=0; j<grid.length; j++)
                if (grid[i].charAt(j)!='.')
                    ++blocked;
        //if (blocked>N*N/10)
        //    out.println(bruteforce());
        //else
            out.println(sparse());
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
