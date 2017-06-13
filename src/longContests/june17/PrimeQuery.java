package longContests.june17;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import static java.lang.System.out;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;

class PrimeQuery {
    PrimeSieve prm = new PrimeSieve(1000000);
    int a[];
    PrimeQuery()
    {
        int n=ni();//1 ≤ N, Q ≤ 10^5
        a=ria(n, sc);//2 ≤ a[i] ≤ 10^6 , where 1 ≤ i ≤ N
        int q=ni();
        int Q[][]=new int[q][4];
        fillMatrix(Q, sc);
        
        for  (int i=0; i<q; i++) {
            out.println( F(Q[i][0], Q[i][1], Q[i][2], Q[i][3]) );
        }
    }
    
    long F(int L, int R, int X, int Y)
    {
        long res=0;
        int x=Collections.binarySearch(prm.primes(), X);
        if (x<0)
            x=-(x+1);
        int y=Collections.binarySearch(prm.primes(), Y);
        if (y<0)
            y=-(y+1);
        else
            y++;
        for (int i=x; i<y; i++) {
            int p=prm.primes().get(i);
            //if (prm.primes().contains(i)) 
            {
                for (int j=L-1; j<R; j++) {
                    int number=a[j];
                    int exponent=0;
                    while (number%p==0) {
                        exponent++;
                        number /= p;                        
                    }
                    res += exponent;
                    //out.println("p"+p+" exp="+exponent);
                }
            }
        }
        return res;
    }
    
    static void fillMatrix(int [][] a, MyScannerX reader)
    {
        for (int i=0; i<a.length; i++)
            for (int j=0; j<a[i].length; j++) {
                a[i][j]=reader.nextInt();
            }
    }
    public static int ni()
    {
        return sc.nextInt();
    }
    public static int[] ria(int N, MyScannerX sc) { // read int array
        int L[]=new int[N];
        for (int i=0; i<N; i++)
            L[i]=sc.nextInt();
        return L;
    }
    
    //static Scanner sc = new Scanner(System.in);
    static MyScannerX sc = new MyScannerX();
    public static void main(String[] args)
    {   
        new PrimeQuery();
    }
}


class PrimeSieve
{
    private List<Integer> primes= new ArrayList<>(10000);
    public PrimeSieve(int num)
    {
        BitSet bs = new BitSet(num);
        for (int i=2; i<num; i++) {
            if (!bs.get(i)) {
                for (long j=(long)i*i; j<(long)num; j+=i)  // i*i can overflow int
                    bs.set((int)j);  // cross out multiple of i
                primes.add(i);
            }
        }
    }
    public List<Integer> primes()
    {
        return primes;
    }
    
}

class MyScannerX {
    BufferedReader br;
    StringTokenizer st;

    public MyScannerX() {
        br = new BufferedReader(new InputStreamReader(System.in));
    }

    public String next() {
        while (st == null || !st.hasMoreElements()) {
            try {
                st = new StringTokenizer(br.readLine());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return st.nextToken();
    }

    public int nextInt() {
        return Integer.parseInt(next());
    }

    public long nextLong() {
        return Long.parseLong(next());
    }

    public double nextDouble() {
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
}