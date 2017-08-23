
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import static java.lang.Integer.min;
import static java.lang.Math.ceil;
import static java.lang.Math.sqrt;
import static java.lang.System.out;
import java.util.Arrays;
import java.util.StringTokenizer;


class HillJump {
    long A[];
    int  blocksize=0;
    int  blocks=0;
    long adj[];  // adjustment of A, per block
    int  next[]; // next hill it can jump to
    int  nextInBlobk[];
    int  jumpsInBlock[];
    HillJump()
    {
        int N=sc.nextInt(); // 1 ≤ N, Q ≤ 100,000
        blocksize=(int)ceil(sqrt(N));
        blocks = (N+blocksize-1)/blocksize;
        out.println("blocks "+blocks+" size "+blocksize);
        adj = new long[blocks];
        int Q=sc.nextInt();
        A=sc.rla(N);  // hill height, 1 ≤ Ai ≤ 1,000,000
        for (int i=0; i<Q;i++) {
            int type =sc.nextInt();
            if (type==1)
                out.println(jump(sc.nextInt()-1, sc.nextInt())+1);
            else {
                int L=sc.nextInt();
                int R=sc.nextInt();
                int X=sc.nextInt(); // -1,000,000 ≤ X ≤ 1,000,000
                update(L, R, X);
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