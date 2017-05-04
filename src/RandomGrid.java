
import static java.lang.System.out;
import java.util.Arrays;
import java.util.Scanner;

class GridHelper
{
    enum DIRECTION {UP, DOWN, LEFT, RIGHT, ERROR};
    int R, C; // RxC dimention
    GridHelper(int r, int c)
    {
        R=r; C=c;
    }
    boolean isValid(int r, int c)
    {
        return r>=0 && r<R && c>=0 && c<C;
    }
    int[] next(int r, int c, DIRECTION d) {
        switch(d){
            case DOWN:
                if (++r>=R)
                    return null;
                break;
            case UP:
                if (r--<=0)
                    return null;
                break;
            case LEFT:
                if (c--<=0)
                    return null;
                break;
            case RIGHT:
                if (++c>=C)
                    return null;
                break;
        }
        return new int[]{r,c};
    }
    
    boolean next(int[]rc, DIRECTION d)
    {
        switch(d){
            case DOWN:
                ++rc[0];
                break;
            case UP:
                --rc[0];
                break;
            case LEFT:
                --rc[1];
                break;
            case RIGHT:
                ++rc[1];
                break;
            default:
                out.println("ERROR direction "+d);
        }   
        return isValid(rc[0],rc[1]);
    }
    DIRECTION getDir(char ch)
    {
        switch(ch){
            case 'D': return DIRECTION.DOWN;
            case 'U': return DIRECTION.UP;
            case 'L': return DIRECTION.LEFT;
            case 'R': return DIRECTION.RIGHT;
        }
        out.println("Error direction "+ch);
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
        out.println("Error direction "+ch);
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
    static void fill(int g[][], int v)
    {
        for (int r[]: g)
            Arrays.fill(r, v);
    }
    // Grid box that covers all the moves
    // borrow from CookOffMar17Robot
    static int[] movingBox(String udlr)
    {
        int r=0, c=0;
        int []mm=new int[4]; // minR, minC, maxR, maxC
        Arrays.fill(mm, 0);
        for (int i=0; i<udlr.length(); i++) {
            switch(udlr.charAt(i)){
                case 'U':
                    r--;
                    break;
                case 'D':
                    r++;
                    break;
                case 'L':
                    c--;
                    break;
                case 'R':
                    c++;
                    break;
                default:
                    out.println("ERROR move "+udlr.charAt(i));
            }
            if ( mm[0]>r)
                mm[0]=r;
            if ( mm[2]<r)
                mm[2]=r;
            if ( mm[1]>c)
                mm[1]=c;
            if (mm[3]<c)
                mm[3]=c;
        }
        return mm;
    }
    // can you move within box from (r, c)
    boolean inBox(int r, int c, int[]mb)
    {
        return inBox_(r, c, R, C, mb);
    }
    static boolean inBox_(int r, int c, int R, int C, int[]mb)
    {
        return mb[0]+r>=0 && mb[1]+c>=0 && mb[2]+r<R && mb[3]+c<C;
    }
}

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
    int bruteforce()
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
    
    int openGrid()
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
    int sparse()
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
                        }
                    }
                }
        int xor=0;
        for (int i=0; i<grid.length; i++) 
            for (int j=0; j<grid.length; j++) {
                if (grid[i].charAt(j)!='.')
                    continue;
                //if (mg[i][j]>0)
                    xor ^= mg[i][j];
                //else if (mg[i][j]<0)
                //    xor ^= moves(i,j);
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
            out.println(openGrid());
        else if (blocked>N*N/10)
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
        int TC = sc.nextInt();  // between 1 and 2
        for (int i=0; i<TC; i++) {
            int L = sc.nextInt();   // 1 ≤ L ≤ 5000
            int N = sc.nextInt();   // 1 ≤ L ≤ 1000
            String moves= sc.next();
            String grid[]=new String[N];
            for (int j=0; j<N; j++)
                grid[j]=sc.next();
            new RandomGrid(grid, moves).solve();
        }
    }
}
