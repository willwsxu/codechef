
package codeforces.edu31;

// n boxes, n colors of balls, each color may have 1 to 10^9 balls

import codechef.IOR;
import codechef.SortedList;
import static java.lang.System.out;


// initially all boxes in 1 box, goal is to group balls per color 
// each move pick all balls in 1 box and distrubute to k boxes, k=2 or 3
// pentalty is the balls on each move
// output minimal total penalty
public class BoxBalls {
    
    SortedList sorted = new SortedList(false, 200000);//1 ≤ n ≤ 200000
    BoxBalls(int []a)
    {
        for (int i:a)
            sorted.add(i);
        long penalty=0;
        while (sorted.size()>1) {
            long subtotal = sorted.get(0)+sorted.get(1);
            int rem = 2;
            if (sorted.size()%2==1) {
                subtotal += sorted.get(2);
                rem++;
            }
            sorted.removeAll(rem);
            sorted.add(subtotal);
            penalty += subtotal;
        }
        out.println(penalty);
    }
    public static  void test()
    {
        new BoxBalls(new int[]{1, 2, 3});
        new BoxBalls(new int[]{2, 3, 4, 5});
        new BoxBalls(new int[]{1,2, 3, 4, 5, 6});
    }
    public static void main(String[] args)
    {      
        judge();
    }
    public static  void judge()
    {
        new BoxBalls(IOR.ria(IOR.ni()));
    }
}
