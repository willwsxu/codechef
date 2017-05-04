
package codechef;

import static java.lang.System.out;
import java.util.Arrays;


public class GridHelper
{
    enum DIRECTION {UP, DOWN, LEFT, RIGHT, ERROR};
    int R, C; // RxC dimention
    public GridHelper(int r, int c)
    {
        R=r; C=c;
    }
    boolean isValid(int r, int c)
    {
        return r>=0 && r<R && c>=0 && c<C;
    }
    public int[] next(int r, int c, DIRECTION d) {
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
    
    public boolean next(int[]rc, DIRECTION d)
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
    public DIRECTION getDir(char ch)
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
    public DIRECTION getReverse(char ch)
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
    public static void print(int g[][])
    {
        for (int i=0; i<g.length; i++) {
            for (int j=0; j<g[0].length; j++)
                out.print(g[i][j]+" ");
            out.println();
        }
    }
    public static void fill(int g[][], int v)
    {
        for (int r[]: g)
            Arrays.fill(r, v);
    }
    // Grid box that covers all the moves
    // borrow from CookOffMar17Robot
    public static int[] movingBox(String udlr)
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
    public boolean inBox(int r, int c, int[]mb)
    {
        return inBox_(r, c, R, C, mb);
    }
    public static boolean inBox_(int r, int c, int R, int C, int[]mb)
    {
        return mb[0]+r>=0 && mb[1]+c>=0 && mb[2]+r<R && mb[3]+c<C;
    }
}
