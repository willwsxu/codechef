
import static java.lang.System.out;
import java.util.Arrays;
import java.util.Scanner;

// SMARKET medium hard 
class Apr17StableMarket {
    int blocks[];        // precompute order of blocks
    int blkIndex[];      // point index of blocks
    int blkPos[];        // position in its block, 1 mean firt number of the block
    int maxBlk=0;
    int minBlk=Integer.MAX_VALUE;
    Apr17StableMarket(int p[])
    {
        blocks = new int[p.length];
        blkIndex = new int[p.length];
        blkPos = new int[p.length];
        int bIndex=0;
        int pos=1;
        int order=1;
        int lastP=p[0];
        
        blkPos[0]=pos;
        blkIndex[0]=0;
        for (int i=1; i<p.length; i++)
        {
            if ( lastP !=p[i]) {
                blocks[bIndex++]=order;
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
            blkIndex[i]=bIndex;
            blkPos[i]=pos;
        }     
        blocks[bIndex++]=order;
        if ( order<minBlk )
            minBlk = order;
        if ( order>maxBlk )
            maxBlk = order;
        /*out.println(Arrays.toString(p));
        out.println(Arrays.toString(blkIndex));
        out.println(Arrays.toString(blkPos));
        out.println(Arrays.toString(blocks));*/
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
        if (blocks[start]-blkPos[n1-1]+1>=ord)
            ++blk;
        if (blkPos[n2-1]>=ord)
            ++blk;
        for (int i=start+1; i<end; i++)
            if (blocks[i]>=ord)
                ++blk;
        return blk;
    }
    int blocks(int p[], int n1, int n2, int ord)
    {
        /*out.println(Arrays.toString(p));
        out.println(n1);
        out.println(n2);
        out.println(ord);*/
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
        scan = ContestHelper.getFileScanner("stablemarket-t.txt");
        int TC = scan.nextInt();  // between 1 and 5
        for (int i=0; i<TC; i++) {
            int N = scan.nextInt();   // 1 ≤ N, Q ≤ 10^5
            int Q = scan.nextInt();
            int prices[] = new int[N]; // stock prices, 1 to 10^6
            for (int j=0; j<N; j++)
                prices[j] = scan.nextInt();
            Apr17StableMarket mkt = new Apr17StableMarket(prices);
            for (int j=0; j<Q; j++) {
                int n1 = scan.nextInt();
                int n2 = scan.nextInt();
                int ord = scan.nextInt();
                //out.println(mkt.blocks(prices, n1, n2, ord));
                out.println(mkt.blocks(n1, n2, ord));
            }
        }
    }
}
