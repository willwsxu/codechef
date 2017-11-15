package codeforces.edu31;

import codechef.MyScanner;
import static java.lang.System.out;
import java.util.PriorityQueue;

// 884D http://www.geeksforgeeks.org/greedy-algorithms-set-3-huffman-coding/
// n boxes, n colors of balls, each color may have 1 to 10^9 balls, 1 ≤ n ≤ 200000
// initially all balls in 1st box, goal is to group balls per color 
// each move pick all balls in 1 box and distrubute to k boxes, k=2 or 3
// pentalty is the balls on each move
// output minimal total penalty
// IDEA
// reverse processing, merge from n items to 1
// combine least 2 or 3 depend on if size is even or odd
// add sub total back, use priority queue to improve speed
// same as the algorithm of building a Huffman code with the alphabet of size 3
public class BoxBalls {
    
    PriorityQueue<Long> pq = new PriorityQueue<>(); // TLE if use SortedList
    BoxBalls(int []a)
    {
        for (int i:a)
            pq.add((Long)(long)i);
        long penalty=0;
        while (pq.size()>1) {
            //out.println(pq.toString());
            long subtotal =pq.poll();
            subtotal += pq.poll();
            if (pq.size()%2==1) {
                subtotal += pq.poll();
            }
            pq.add(subtotal);
            //out.println("sub :"+subtotal);
            penalty += subtotal;
        }
        out.println(penalty);
    }
    public static  void test()
    {
        new BoxBalls(new int[]{1, 2, 3});           // 6
        new BoxBalls(new int[]{2, 3, 4, 5});        //19
        new BoxBalls(new int[]{1,2, 3, 4, 5, 6});   //34
    }
    public static void main(String[] args)
    {      
        judge();
    }
    public static  void judge()
    { 
        MyScanner sc=new MyScanner();
        new BoxBalls(sc.ria(sc.ni()));
    }
}
