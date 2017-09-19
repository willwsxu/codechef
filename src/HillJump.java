
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
class HillJump {
    long A[];
    int  blocksize=0;
    int  blocks=0;
    long adj[];  // adjustment of A, per block
    int  next[]; // next hill it can jump to
    int  nextInBlobk[];  // last hill it can jjump to within the same block
    int  jumpsInBlock[]; // how many jumps is needed to reach end of block
    HillJump()
    {
        int N=sc.nextInt(); // 1 ≤ N, Q ≤ 100,000
        blocksize=(int)ceil(sqrt(N));
        blocks = (N+blocksize-1)/blocksize;
        //out.println("blocks "+blocks+" size "+blocksize);
        adj = new long[blocks];
        int Q=sc.nextInt();
        A=sc.rla(N);  // hill height, 1 ≤ Ai ≤ 1,000,000
        next=new int[N];
        calcNext(0, N-1);
        for (int i=0; i<Q;i++) {
            int type =sc.nextInt();
            if (type==1)
                out.println(jump2(sc.nextInt()-1, sc.nextInt())+1);
            else {
                int L=sc.nextInt();
                int R=sc.nextInt();
                int X=sc.nextInt(); // -1,000,000 ≤ X ≤ 1,000,000
                update2(L, R, X);
            }
        }
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
                if (A[j]>A[i]) {
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
    void update2(int L, int R, int X)
    {
        for (int j=L-1; j<R; j++)
        {
            A[j] += X;
        }  
        calcNext(max(0, L-101), L-2);  // L-100 ≤ i < L
        calcNext(max(0, R-100), R-1);  // R-100 < i ≤ R
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
    
    static MyScanner sc = new MyScanner();
    public static void main(String[] args)
    {    
        new HillJump();
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