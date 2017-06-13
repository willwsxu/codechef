package longContests.june17;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import static java.lang.System.out;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;


class TripLets {
    TripLets()
    {
        int p=sc.nextInt();//1 ≤ p, q, r ≤ 100000
        int q=sc.nextInt();
        int r=sc.nextInt();
        int a[]=ria(p, sc);//1 ≤ every array element ≤ 1000000000
        int b[]=ria(q, sc);
        int c[]=ria(r, sc);
        solve(a, b, c);
        //bruteforce(a,b,c);
    }
    TripLets(int a[], int b[], int c[])
    {
        solve(a,b,c);
    }
    static final int MOD=1000000007;
    void solve(int a[], int b[], int c[])
    {
        Arrays.sort(a);
        Arrays.sort(b);
        Arrays.sort(c);
        long sa[]=prefixSum(a);
        long sc[]=prefixSum(c);
        /*out.println(Arrays.toString(a));
        out.println(Arrays.toString(b));
        out.println(Arrays.toString(c));
        out.println(Arrays.toString(sa));
        out.println(Arrays.toString(sc));*/
        long ans=0;
        int ia=0, ic=0;
        for (int i=0; i<b.length; i++) {
            ia=upperBound(a, ia, a.length, b[i]);
            ic=upperBound(c, ic, c.length, b[i]);
            if (ia==0 || ic==0)
                continue;
            //out.println("ia="+ia+" ic="+ic);
            //out.println("sa[ia]="+sa[ia]+" sc[ic]="+sc[ic]);
            long p=(sc[ic]+(long)ic*b[i])%MOD;  // cast long to prevent multiplication overflow
            p *=   (sa[ia]+(long)ia*b[i])%MOD;
            ans = (ans+p)%MOD;
        }
        out.println(ans);
    }
    static int upperBound(int a[], int from, int to, int x)
    {
        int i=Arrays.binarySearch(a, from, to, x);
        if (i<0)
            return -(i+1);
        while (i<to) {
            if ( a[i]==x)  // 
                i++;
            else
                break;
        }
        return i;
    }
    static void bruteforce(int a[], int b[], int c[])
    {
        long total=0;
        for (int i=0; i<b.length; i++) {
            for (int j=0; j<a.length; j++) {
                if (a[j]>b[i])
                    continue;
                long ab=(a[j]+b[i])%MOD;
                for (int k=0; k<c.length; k++) {
                    if (c[k]>b[i])
                        continue;
                    long bc=(c[k]+b[i])%MOD;
                    //out.println("ab="+ab+" bc="+bc);
                    total = (total+(ab*bc)%MOD)%MOD;
                }                
            }            
        }   
        out.println(total);
    }
    static void testBS()
    {
        out.println(upperBound(new int[]{2,4,6,8}, 0,3, 5)==2);
        out.println(upperBound(new int[]{2,4,6,8}, 0,2,4)==2);
        out.println(upperBound(new int[]{2,4,4,6,8}, 0,3,4)==3);// edge case
        out.println(upperBound(new int[]{2,4,6,8}, 0,2,1)==0);
        out.println(upperBound(new int[]{2,4,6,8}, 2,4,9)==4);
        out.println(upperBound(new int[]{2,4,6,8,8}, 2,4,9)==4);       
        out.println(upperBound(new int[]{2,4,6,8,8}, 2,5,9)==5);   
    }
    static void test()
    {        
        new TripLets(new int[]{3,2,1}, new int[]{2}, new int[]{6,4,5});
        out.print("brute ");
        bruteforce(new int[]{3,2,1}, new int[]{2}, new int[]{6,4,5});
        
        new TripLets(new int[]{3,1,2,8}, new int[]{4}, new int[]{6,4,5});
        out.print("brute ");
        bruteforce(new int[]{3,2,1,8}, new int[]{4}, new int[]{6,4,5});
        
        new TripLets(new int[]{3,1,2,8}, new int[]{5}, new int[]{6,4,5});
        new TripLets(new int[]{3,1,2,8}, new int[]{4}, new int[]{6,4,5});
        new TripLets(new int[]{3,1,2,8}, new int[]{5,4}, new int[]{6,4,5}); // 543
        out.print("brute ");
        bruteforce(new int[]{3,1,2,8}, new int[]{5,4}, new int[]{6,4,5}); // 543
        new TripLets(new int[]{100000000,200000000,300000000}, new int[]{500000000}, 
                new int[]{400000000,500000000,600000000}); // 70000196
        out.print("brute ");
        bruteforce(new int[]{100000000,200000000,300000000}, new int[]{500000000}, 
                new int[]{400000000,500000000,600000000}); // 70000196
        new TripLets(new int[]{100000000,200000000,300000000}, new int[]{500000000,400000000}, 
                new int[]{400000000,500000000,600000000}); // 990000273
        out.print("brute ");
        bruteforce(new int[]{100000000,200000000,300000000}, new int[]{500000000,400000000}, 
                new int[]{400000000,500000000,600000000}); // 990000273
        int a[]=new int[100000];
        int b[]=new int[100002];
        int c[]=new int[100004];
        for (int i=0; i<a.length; i++)
            a[i]=MOD-100007+i;
        for (int i=0; i<c.length; i++)
            c[i]=a[0]+i*2;
        for (int i=0; i<b.length; i++)
            b[i]=c[10]+i;
        Instant start = Instant.now();
        new TripLets(a,b,c);
        Instant end = Instant.now();
        out.println("usec "+ChronoUnit.MICROS.between(start, end));   
        //bruteforce(a,b,c);
    }
    public static long[] prefixSum(int a[])  // set first elem to 0
    {
        long s[]=new long[a.length+1];
        s[0]=0;
        for (int i=1; i<=a.length; i++)
            s[i] = s[i-1]+a[i-1];
        return s;
    }
    public static int[] ria(int N, MyScanner sc) { // read int array
        int L[]=new int[N];
        for (int i=0; i<N; i++)
            L[i]=sc.nextInt();
        return L;
    }
    //static Scanner sc = new Scanner(System.in);  too slow for large input
    static MyScanner sc=new MyScanner();
    static void autotest()
    {
        int T=sc.nextInt();  // 1 ≤ T ≤ 10
        while (T-->0)
            new TripLets();        
    }
    public static void main(String[] args)
    {   
        autotest();
    }
}

// credit to http://codeforces.com/blog/entry/7018
class MyScanner {
      BufferedReader br;
      StringTokenizer st;
 
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

   }