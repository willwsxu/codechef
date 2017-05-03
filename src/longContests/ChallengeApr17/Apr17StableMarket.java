package longContests.ChallengeApr17;


import static java.lang.Integer.min;
import static java.lang.System.out;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

// Editorial IDEA: convert list of numbers into block orders
// e.g A:20, 10, 10, 7, 7, 7, 10 ->B: 1, 1, 2, 1, 2, 3, 1
// # of block order K will be same as # of Bi=K
// use array of list to store position of each order
// BST, Binary Search the L and R position in the List, by order
// Take care of special case when left is in middle of block. precalculate order for each elem
// SMARKET medium hard 
// Performance tip: save output in StringBuider, call out.println once


// slight improvement over brute force
class BlockOrder
{
    List<Integer> blocks=new ArrayList<>();        // precompute order of blocks
    int blkIndex[];      // point to index of blocks
    int blkPos[];        // position in its block, 1 mean firt number of the block
    int maxBlk=0;
    int minBlk=Integer.MAX_VALUE;

    BlockOrder(int p[])
    {
        blkIndex = new int[p.length];
        blkPos = new int[p.length];
        int pos=1;
        int order=1;
        int lastP=p[0];

        blkPos[0]=pos;
        blkIndex[0]=0;
        for (int i=1; i<p.length; i++)
        {
            if ( lastP !=p[i]) {
                blocks.add(order);
                if ( order<minBlk )
                    minBlk = order;
                if ( order>maxBlk )
                    maxBlk = order;
                order=1;
                lastP=p[i];
                pos=1;
            }
            else {
                order++;
                pos++;
            }
            blkIndex[i]=blocks.size();
            blkPos[i]=pos;
        }     
        blocks.add(order);
        if ( order<minBlk )
            minBlk = order;
        if ( order>maxBlk )
            maxBlk = order;            
    }
    int blocks(int n1, int n2, int ord)
    {
        if ( ord>maxBlk)
            return 0;
        int start = blkIndex[n1-1];
        int end = blkIndex[n2-1];
        if (start==end)  // same block
            return n2-n1+1>=ord?1:0;
        int blk=0;
        if (blocks.get(start)-blkPos[n1-1]+1>=ord)
            ++blk;
        if (blkPos[n2-1]>=ord)
            ++blk;
        for (int i=start+1; i<end; i++)
            if (blocks.get(i)>=ord)
                ++blk;
        return blk;
    }
}

class Apr17StableMarket {
    int[] B;  // transform number into orders of block
    int[] C;  // Block order for every element, used for special case when L is in middle of a block
    int maxOrder=0;
    List<Integer>[] blocks;  //one list per order, each list store original position (sorted)
    Apr17StableMarket(int p[])
    {
        B = new int[p.length];
        C = new int[p.length];
        maxOrder=transformOrder(p, B);
        C[B.length-1]=B[B.length-1]; // from back, calculate order for each
        for (int i=B.length-2; i>=0; i--) {
            if (B[i]>=B[i+1])
                C[i]=B[i];
            else
                C[i]=C[i+1];
        }
        blocks = blockOrderList(maxOrder, B);
    }
    int query(int L, int R, int order)
    {// L and R is 1 based
        if (order<1 || L<1 || R< 1) {
            out.println("Error L, R, order ");
            return 0;
        }
        if ( order>maxOrder ) {
            return 0;
        }
        int le = Collections.binarySearch(blocks[order-1], new Integer(L-1));
        int r = Collections.binarySearch(blocks[order-1], new Integer(R-1));
        if (r<0)
            r = -(r+2);  // move up 1
        if (le<0)
            le = -(le+1);
        int ans = r-le+1;
        if (B[L-1]>1) { // left is in middle of a block
            int partialOrder = min(C[L-1]-B[L-1]+1, R-L+1); // watch out when R and L in the same block
            if ( partialOrder>=order ) { // B: 23123, no adjust if order is 2
                if (order <B[L-1] ) // if order is 1, add 1
                    ans++;
            } else if (order>=B[L-1] && order <=C[L-1]) // B: 23123 adjust down if order is 3
                ans--; //B: 212 no adjust if order is 3, B:2 adjust down if order 2
        }
        //out.println(le+" "+r+" "+ ans+" order "+order+" B "+B[L-1]+" C "+C[L-1]);
        return ans;
    }
    static List<Integer>[] blockOrderList(int maxOrder, int B[])
    {
        List<Integer>[] blocks = new List[maxOrder];
        for (int i=0; i<maxOrder; i++)
            blocks[i]=new ArrayList<>();
        for (int i=0; i<B.length; i++) { //pi(order, position)
            blocks[B[i]-1].add(i);
        }
        return blocks;
    }
    static int transformOrder(int p[], int B[] )
    {
        int lastP=-1;
        int mo=0; // max block order
        for (int i=0; i<p.length; i++)
        {
            if ( lastP !=p[i]) {
                B[i]=1;
                lastP=p[i];
            }
            else {
                B[i]=B[i-1]+1;
            }
            if (mo<B[i])
                mo=B[i];
        }  
        return mo;
    }
    static  void print(List<Integer>[] blk) {
        for (List l:blk)
            out.println(l);
    }
    static void test()
    {
        int p[]=new int[]{20, 10, 10, 7, 7, 7, 10};
        Apr17StableMarket mkt = new Apr17StableMarket(p);
        out.println(Arrays.toString(mkt.B));
        out.println(Arrays.toString(mkt.C));
        out.println("max order "+mkt.maxOrder);
        print(mkt.blocks);
        mkt.query(1, 6, 1); // 3
        mkt.query(1, 5, 1); // 3
        mkt.query(3, 5, 1); // 2
        mkt.query(3, 5, 2); // 1
        mkt.query(3, 5, 3); // 0
        mkt.query(3, 4, 1); // 2
        mkt.query(3, 4, 2); // 0
        mkt.query(5, 6, 1); // 1
        mkt.query(5, 6, 2); // 1
        mkt.query(5, 6, 3); // 0
        mkt.query(5, 5, 1); // 1
        mkt.query(5, 5, 2); // 0
        mkt.query(5, 7, 1); // 2
        mkt.query(5, 7, 2); // 1
        mkt.query(5, 7, 3); // 0
        mkt.query(5, 7, 4); // 0
        out.println();
    }
    int bruteforce(int p[], int n1, int n2, int ord)
    {
        int order=0;
        int lastP=0;
        int blk=0;
        for (int i=n1-1; i<n2; i++)
        {
            if ( lastP !=p[i]) {
                if (order>=ord) 
                    ++blk;
                order=1;
                lastP=p[i];
            }
            else
                order++;
        }
        if (order>=ord)
            ++blk;
        return blk;
    }
    static Scanner scan = new Scanner(System.in);
    public static void main(String[] args)
    {        
        //test();
        //scan = codechef.ContestHelper.getFileScanner("stablemarket-t.txt");
        int TC = scan.nextInt();  // between 1 and 5
        for (int i=0; i<TC; i++) {
            int N = scan.nextInt();   // 1 ≤ N, Q ≤ 10^5
            int Q = scan.nextInt();
            int prices[] = new int[N]; // stock prices, 1 to 10^6
            for (int j=0; j<N; j++)
                prices[j] = scan.nextInt();
            //BlockOrder mkt = new BlockOrder(prices);
            Apr17StableMarket mkt = new Apr17StableMarket(prices);
            StringBuilder sb = new StringBuilder();
            for (int j=0; j<Q; j++) {
                int n1 = scan.nextInt();
                int n2 = scan.nextInt();
                int ord = scan.nextInt();
                //out.println(mkt.blocks(prices, n1, n2, ord));
                //out.println(mkt.blocks(n1, n2, ord));
                sb.append(mkt.query(n1, n2, ord));
                sb.append("\n");
            }
            out.print(sb);   // speed up 2 sec to print once per test
        }
    }
}

class SegTree
{
    
}
