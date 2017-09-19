
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import static java.lang.Integer.max;
import static java.lang.Integer.min;
import static java.lang.Math.ceil;
import static java.lang.Math.sqrt;
import static java.lang.System.out;
import java.util.Arrays;
import java.util.StringTokenizer;
/*There are N hills located on a straight line. The height of the ith one is denoted by Hi. 
  A participant standing at the top of the ith hill jumps to the nearest hill to the right 
  which is strictly higher than the one he is standing on. If the nearest one is further 
  than 100 hills away, he doesn't move anywhere.

Giving Q queries of 2 types:

The first type is given by (P,K) , a participant standing on the Pth hill is willing to 
 perform K successive moves, what hill he will be ending at?

The second type is given by (L,R,X), for each hill between the Lth one and the Rth one (inclusive) 
should be increased by X (it may be negative).
*/
// HillJUMP, medium, square root sqrt decomposition
// pre-calculation next[] (i jump to next[i]) boost performance a lot, pass 7 out of 8 tests.
// Step 2. Add adjustment per block to speed up update command
class HillJump {
    long A[];
    int  blocksize=1;
    int  blocks=0;
    long adjBlock[];  // adjustment of A, per block
    int  next[]; // next hill it can jump to
    int  nextInBlobk[];  // last hill it can jump to within the same block
    int  jumpsInBlock[]; // how many jumps is needed to reach end of block
    HillJump()
    {
        int N=sc.nextInt(); // 1 ≤ N, Q ≤ 100,000
        int Q=sc.nextInt();
        init(N, Q);
        A=sc.rla(N);  // hill height, 1 ≤ Ai ≤ 1,000,000
        calcNext(0, N-1);
        StringBuilder sb=new StringBuilder();
        for (int i=0; i<Q;i++) {
            int type =sc.nextInt();
            if (type==1) {
                sb.append(jump2(sc.nextInt()-1, sc.nextInt())+1);
                sb.append('\n');
            } else {
                int L=sc.nextInt();
                int R=sc.nextInt();
                int X=sc.nextInt(); // -1,000,000 ≤ X ≤ 1,000,000
                update2(L, R, X);
            }
        }
        out.print(sb.toString());
    }
    
    HillJump(long[] a, int cmd[], int Q)
    {
        init(a.length, Q);
        A=a;
        calcNext(0, a.length-1);
        StringBuilder sb=new StringBuilder();
        int j=0;
        for (int i=0; i<Q;i++) {
            if (cmd[j++]==1) {
                sb.append(jump2(cmd[j++]-1, cmd[j++])+1);
                sb.append('\n');                
            } else {
                update2(cmd[j++], cmd[j++], cmd[j++]);                
            }
        }
        out.print(sb.toString());
    }
    
    void init(int N, int Q)
    {
        blocksize=(int)ceil(sqrt(N));
        blocks = (N+blocksize-1)/blocksize;
        //out.println("blocks "+blocks+" size "+blocksize);
        adjBlock = new long[blocks];
        next=new int[N];        
    }
    
    // pre calculate next jump between from and to, inclusive
    void calcNext(int from, int to)
    {
        for (int i=from; i<=to; i++) {
            next[i]=i;  // initial value, no jump
            for (int j=i+1; j<A.length; j++) {
                if (j-i>100) {
                    break;
                }
                if (A[j]+adjBlock[j/blocksize]>A[i]+adjBlock[i/blocksize]) {
                    next[i]=j;
                    break;
                }
            }  
        }
    }
    int ceilingBlocks(int m)
    {
        return (m+blocksize-1)/blocksize;
    }
    int getBlockEnd(int L)
    {
        int m=L/blocksize;
        return (L%blocksize==0?m:m+1)*blocksize;// L=30, block size=10, end=30
    }
    int getBlockStart(int R)
    {
        int m=ceilingBlocks(R);
        return (m-1)*blocksize;// R=30, block size=10, begin=20
    }
    void update(int L, int R, int X)
    {
        int endBlock=getBlockEnd(L);
        for (int j=L-1; j<min(R,endBlock); j++)
        {
            A[j] += X;
        }  
        out.println("endBlock "+endBlock);
        out.println(Arrays.toString(A));
        if (R<endBlock)
            return;
        int start = getBlockStart(R);
        out.println("start "+start);
        if (start<endBlock) {
            out.println("error getBlockStart");
            return;
        }
        for (int j=start; j<R; j++)
        {
            A[j] += X;
        }  
        out.println(Arrays.toString(A)); 
        if (start==endBlock)
            return;
        for (int j=endBlock; j<start; j++)
        {
            A[j] += X;
        } 
        out.println(Arrays.toString(A)); 
    }
    void updateBlock(int L, int R, int X)
    {
        if (R-L+1<blocksize || blocksize<2)
            return;
        int startBlock=ceilingBlocks(L-1); // block size 10, 1..10 ->0,1, 2..10->1,1, 1..9->0,0
        int endBlock=R/blocksize;
        for (int i=startBlock; i<endBlock; i++)
            adjBlock[i] +=X;
    }
    void updateCell(int L, int R, int X) 
    {        
        for (int j=L-1; j<R; j++)
        {
            A[j] += X;
        }  
    }
    void update2(int L, int R, int X)
    {
        updateBlock(L,R,X);
        if ( blocksize<2 || R-L<(blocksize<<1))
            updateCell(L,R,X);
        else {
            if (L%blocksize!=1)
                updateCell(L, ceilingBlocks(L-1)*blocksize, X);
            if (R%blocksize!=1)
                updateCell(R/blocksize*blocksize+1, R, X);
        }
        calcNext(max(0, L-101), L-2);  // L-100 ≤ i < L
        calcNext(max(0, R-100), R-1);  // R-100 < i ≤ R
        out.println(Arrays.toString(adjBlock));
        out.println(Arrays.toString(A));
        out.println(Arrays.toString(next));
    }
    
    int jump(int i, int k) {
        //out.println(Arrays.toString(A));
        int j=i+1;
        for (; j<A.length; j++) {
            if (j-i>100)
                return i;
            if (A[j]>A[i]) {
                i=j;
                if (--k==0)
                    return j;
            }
        }
        return i;
    }
    int jump2(int i, int k) {
        while (i<A.length&&k>0) {
            if (next[i]==i)
                return i;
            i=next[i];
            k--;
        }
        return i;
    }
    
    public static void test()
    {
        new HillJump(new long[]{1,2,3,4,5}, new int[]{1,1,2,2,3,4,-1,1,1,2}, 3);
        new HillJump(new long[]{1,2,3,4,5,4,3,2,1}, new int[]{1,1,2,2,4,6,-1,1,1,3}, 3);
    }
    
    static MyScanner sc = new MyScanner();
    public static void main(String[] args)
    {    
        //new HillJump();
        test();
    }
}

class MyScanner {
    BufferedReader br;
    StringTokenizer st;

    MyScanner(String f)
    {
        try {
            br = new BufferedReader(new FileReader(new File(f)));
        } catch (IOException e)
        {
            out.println("MyScanner bad file "+f);
        }
    }
    public MyScanner() {
        br = new BufferedReader(new InputStreamReader(System.in));
    }

    String next() {
        while (st == null || !st.hasMoreElements()) {
            try {
                st = new StringTokenizer(br.readLine());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return st.nextToken();
    }

    int nextInt() {
        return Integer.parseInt(next());
    }

    long nextLong() {
        return Long.parseLong(next());
    }

    double nextDouble() {
        return Double.parseDouble(next());
    }

    String nextLine(){
        String str = "";
        try {
           str = br.readLine();
        } catch (IOException e) {
           e.printStackTrace();
        }
        return str;
    }
    
    public int ni()
    {
        return nextInt();
    }     
    public long nl()
    {
        return nextLong();
    }   
    public int[] ria(int N) { // read int array
        int L[]=new int[N];
        for (int i=0; i<N; i++)
            L[i]=nextInt();
        return L;
    }
    public long[] rla(int N) { // read long array
        long L[]=new long[N];
        for (int i=0; i<N; i++)
            L[i]=nextLong();
        return L;
    }
}