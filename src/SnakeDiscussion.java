
import static java.lang.Integer.max;
import static java.lang.Integer.min;
import static java.lang.Math.abs;
import static java.lang.System.out;
import java.util.Scanner;

/*
 * Snake as line segment on a 2D grid, either horizontal or vertical
 * Each snake want to discuss issues with every other by share a common cell.
 * In one second, you can move a snake horizontally or vertically by one unit. 
 * Also, you can move as many snakes simultaneously as you wish. Find the 
 * minimum number of seconds required to make this discussion possible.
 * Math theory:  It can be proven that all the line segments should have a 
 * common intersection point if we want to satisfy the condition of intersection 
 * of all line segments pairwise. 
 */

// SNDISCUS, Medium, Math
// https://discuss.codechef.com/questions/99970/sndiscus-editorial
class SnakeDiscussion {
    
    int x1[], y1[], x2[], y2[];
    SnakeDiscussion()
    {
        int N=sc.nextInt(); // 1 ≤ n ≤ 50
        x1=new int[N];        y1=new int[N];
        x2=new int[N];        y2=new int[N];
        for (int i=0; i<N; i++) { // snake location
            x1[i]=sc.nextInt();  // 1 ≤ x1, y1, x2, y2 ≤ 50
            y1[i]=sc.nextInt();
            x2[i]=sc.nextInt();
            y2[i]=sc.nextInt();
        }        
    }
    int calc2(int x, int x1, int x2)  // dist from x to x1-x2, horizontal or vertical
    {
        if (x1>x2) {
            int t=x1;
            x1=x2;
            x2=t;
        }
        if (x<x1)
            return x1-x;
        else if (x>x2)
            return x-x2;
        return 0;
    }
    int calc(int x, int y)  // max distance from (x,y) to each line
    {
        int dist=0;
        for (int i=0; i<x1.length; i++){
            if (x1[i]==x2[i]) {  // vertical
                dist=max(dist, abs(x-x1[i])+calc2(y, y1[i],y2[i]));
            } else
                dist=max(dist, abs(y-y1[i])+calc2(x, x1[i],x2[i]));
        }
        return dist;
    }
    void solve()
    {
        int size=50;
        int d=Integer.MAX_VALUE;
        for (int i=1; i<=size; i++) {
            for (int j=1; j<=size; j++) {
                d=min(d, calc(i,j));
            }
        }
        out.println(d);
    }
    static Scanner sc = new Scanner(System.in);
    public static void main(String[] args)
    {      
        int T=sc.nextInt(); // 1 ≤ T ≤ 50
        while (T-->0)
            new SnakeDiscussion().solve();
    }
}
