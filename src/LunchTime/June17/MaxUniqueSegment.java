package LunchTime.June17;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import static java.lang.System.out;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

// easy, two pointers
class MaxUniqueSegment {
    MaxUniqueSegment(int N)
    {
        this(sc.ria(N), sc.ria(N));
        // 0 ≤ Ci < N,  0 ≤ Wi ≤ 1000000000
    }
    MaxUniqueSegment(int C[], int W[])
    {
        int N=C.length;
        long[] sum=prefixSum(W);
        int start=0;
        Set<Integer> unique=new HashSet<>();
        long maxSum=0;
        for (int i=0; i<N;i++) {
            if (unique.contains(C[i])) {
                long mx=sum[i]-sum[start];
                if (mx>maxSum)
                    maxSum=mx;
                for (; start<i; start++) {
                    if ( C[start]==C[i])
                        break;
                    unique.remove(new Integer(C[start]));
                }
                start++;
            }
            else
                unique.add(C[i]);
        }
        long mx=sum[N]-sum[start];
        if (mx>maxSum)
            maxSum=mx;
        out.println(maxSum);
    }
    
    static void test()
    {
        new MaxUniqueSegment(new int[]{0, 1, 2, 0, 2}, new int[]{5, 6, 7, 8, 2});//21
        new MaxUniqueSegment(new int[]{4,5,3,0, 1, 2, 0, 2}, new int[]{1,2,3,5, 6, 7, 18, 2});//31
    }
    
    public static long[] prefixSum(int a[])  // set first elem to 0
    {
        long s[]=new long[a.length+1];
        s[0]=0;
        for (int i=1; i<=a.length; i++)
            s[i] = s[i-1]+a[i-1];
        return s;
    }
    static MyScanner sc=new MyScanner();
    public static void main(String[] args)
    {  
        int T = sc.ni();  // 1 ≤ T ≤ 100
        while (T-->0) {
            int N=sc.ni(); // 1 ≤ N ≤ 1000000
            new MaxUniqueSegment(N);
        }
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