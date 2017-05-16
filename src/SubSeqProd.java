
import static java.lang.System.out;
import java.util.Arrays;
import java.util.Scanner;


// any sub sequence, product < K
// use bit for subset, int is enough for N=30
class SubSeqProd {
    long val[];
    long lim;
    int  n;
    SubSeqProd(long a[], long k)
    {
        //out.println("SubSeqProd "+k);
        val=a;
        lim=k;
        Arrays.sort(a);
        n=a.length;
        while (n>0 && a[n-1]>k) {
            n--;
        }
    }
        
    long seqProduct(int ind, int num) // sequential prod
    {
        long prod=1;
        for (int i=ind; i<ind+num; i++) {
            long p=prod*val[i];            
            if ( p<prod || p>lim)
                return Long.MAX_VALUE;
            prod=p;
        }
        return prod;
    }
    long multiply(long p, long m)
    {
        long p2=p*m;  
        if ( p2<p )
            return Long.MAX_VALUE;
        return p2;
    }
    static long aChooseb(int a, int b)
    {
        if (a<b)
            return 0; // error
        if (a-b<b)  // 5C3 = 5C2
            b=a-b;
        long p=1;
        int bb=b;
        for (int i=0; i<b; i++) {
            p *=(a-i);
            while (bb>1 && p%bb==0) {
                p /= bb--;
            }
        }
        while (bb>1 && p%bb==0) {
            p /= bb--;
        }
        return p;
    }
    static void testChoose()
    {
        out.println(aChooseb(5,1));
        out.println(aChooseb(5,2));
        out.println(aChooseb(5,3));
        out.println(aChooseb(5,4));
        out.println(aChooseb(5,5));
        out.println(aChooseb(30,15));
    }
    // lo inclusive, hi exclusive
    int binarysearch(long p, int lo, int hi) {
        if (lo>=hi-1)
            return lo;
        int mid = (lo+hi)/2;
        if ( multiply(p, val[mid])>lim)
            return binarysearch(p, lo, mid);
        else
            return binarysearch(p, mid, hi);
    }
    int findMaxMulti(int n) {  // max number to multiply   
        long p=1;
        for (int i=0; i<n; i++) {
            long p2 = p*val[i];
            if (p2>lim || p2<p)
                return i;
            p=p2;
        }
        return n;
    }
    // 1 2 3 4 5 6 7 8
    // multi 3,
    //long dp[][][][];
    long recurse(int start, int end, int multi, long prod, int multimax)
    {
        //out.print("-recur "+start+" end "+end+" multiply times "+multi+" prod "+prod);
        long newProd=multiply(prod, val[start]);
        if (newProd>lim) {
            //out.println(" count=0, newProd "+newProd);
            return 0;
        }
        else if ( multi==1) {
            int found=binarysearch(prod, start, end);
            long count=found-start;
            if ( multiply(prod, val[found])<=lim)
                count++;
            //out.println(" binarysearch "+found+" count "+count);
            //dp[start][end][multi]=count;
            return count;
        //} else if (dp[start][end][multi][multimax]>0) {
            //out.println(" dp "+" count "+dp[start][end][multi][multimax]);
            //return dp[start][end][multi][multimax];
        } else {
            long count=0;
            for (int i=start+1; i<=end-multi+1; i++) {
                count += recurse(i, end, multi-1, newProd, multimax);
                if (multi==2)  // let binary search do the work when only one to multiply
                    break;
            }
            //dp[start][end][multi][multimax]=count;
            //out.println(" recurse count "+count);
            return count;
        }
    }
    long solve()
    {
        if (n<=0)
            return 0;
        //if (end<10)
        //    return bruteforce(end+1);
        long total=n;
        int maxMulti=findMaxMulti(n);
        //out.println(" numbers "+n+" multi "+maxMulti);
        if ( maxMulti<=1 )
            return total;
        else if (maxMulti>=n)
            return (1<<n)-1;  // watch out for precedence
        //dp=new long[n][n+1][maxMulti+1][maxMulti+1];
        // 2 3 4 5 6 maxMulti=3
        // recurse maxMulti=2, 0 5, 1 5, 2 5, 3 5
        // recurse maxMulti=3, 0 1 5, 0 2 5, 0 3 5; 1 2 5, 1 3 5; 2 3 5
        for (int i=2; i<=maxMulti; i++) {
            long largest=seqProduct(n-i, i);
            if ( largest <= lim) {
                long count = aChooseb(n, i);
                //out.println(n+" aChooseb "+i+" = "+count);
                total += count;
                continue;
            }
            for (int j=0; j<=n-i; j++) {
                long smallest=seqProduct(j, i);
                if ( smallest > lim) {
                    //out.println("too big "+smallest+" multi "+i+" j="+j);
                    break;
                }
                total += recurse(j, n, i, 1, i);
            }
        }
        return total;
    }
    long bruteforce()
    {
        //out.println("bruteforce");
        long count=0;
        outterfor:
        for (long i=1; i< (1<<n); i++) {  // bit for all subset of A
            long prod=1;
            for (int j=0; j<n; j++) {
                if (((1<<j)&i)>0) {
                    long prod2 = prod *val[j]; // check over flow
                    if ( prod2<prod || prod2>lim)
                        continue outterfor;
                    prod = prod2;
                    //out.println("bruteforce i="+i+" j="+j);
                }
            }
            if (prod<=lim)
                count++;
        }   
        return count;
    }
    static void bruteforce(long A[], long K)
    {
        long count = new SubSeqProd(A, K).bruteforce();
        out.println(count);
    }
    static void test()
    {
        testChoose();
        long A[] = new long[]{1, 2, 3};
        bruteforce(A, 4);  // 5
        out.println("new "+new SubSeqProd(A, 4).solve());
        bruteforce(A, 6);  // 7
        out.println("new "+new SubSeqProd(A, 7).solve());
        
        A = new long[]{10,9,8,7,6,5,4,3,2,1};
        bruteforce(A, 10);  //10+9+3+3
        out.println("new "+new SubSeqProd(A, 10).solve());        
        
        A = new long[]{6, 5,4,3,2};
        bruteforce(A, 40);
        out.println("new "+new SubSeqProd(A, 40).solve());
        
        A = new long[]{10,9,8,7,6,5,4,3,2};
        bruteforce(A, 72576);
        out.println("new "+new SubSeqProd(A, 72576).solve());
        
        A = new long[]{100, 200, 300};
        bruteforce(A, 4);  // 0
        bruteforce(A, 100);// 1
        out.println("new "+new SubSeqProd(A, 100).solve());
        bruteforce(A, 200);// 2
        bruteforce(A, 300);// 3
        out.println("new "+new SubSeqProd(A, 300).solve());
        
        A = new long[]{100000000000000000L, 200000000000000000L, 4000000000000000000L};
        bruteforce(A, 4);  // 0
        out.println("new "+new SubSeqProd(A, 4).solve());
        A = new long[]{10,9,8,7,6,5,4,3,2,1, 11, 12, 13,14,15,16,17,18,19,20,30,29,28,27,26,25,24,23,22,21};
        //bruteforce(A, 4000);//9783
        out.println("new "+new SubSeqProd(A, 4000).solve());  // 9783
        
        A = new long[]{10,9,8,7,6,5,4,3,2,31, 11, 12, 13,14,15,16,17,18,19,20,30,29,28,27,26,25,24,23,22,21};
        out.println("new "+new SubSeqProd(A, 1000000000000000000L).solve());  // 672779816
        
        A = new long[]{10,9,8,7,6,5,4,3,2,3, 11, 12, 13,14,15,16,17,18,19,20,19,19,18,17,16,15,14,13,12,11};
        out.println("new "+new SubSeqProd(A, 2000000000000000000L).solve());  // 909178996
        
        A = new long[]{10,9,8,7,6,5,4,3,2,3, 11, 12, 13,4,5,6,7,8,9,10,9,9,8,7,16,15,14,13,12,11};
        out.println("new "+new SubSeqProd(A, 2000000000000000000L).solve());  // 1051752556
        
    }
    static Scanner sc = new Scanner(System.in);
    public static void main(String[] args)
    {      
        test();
        /*int N=sc.nextInt();  // 1 ≤ N ≤ 30
        int K=sc.nextInt();  // 1 ≤ K, Ai ≤ 10^18
        long A[] = new long[N];
        for (int j=0; j<N; j++)
            A[j] = sc.nextLong();
        out.println(new SubSeqProd(A, K).solve());*/
    }
}

/*
5 40
6 5 4 3 2
*/