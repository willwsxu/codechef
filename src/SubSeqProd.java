
import static java.lang.System.out;
import java.util.Arrays;
import java.util.Scanner;


// any sub sequence, product < K
// use bit for subset, int is enough for N=30
class SubSeqProd {
    long val[];
    long lim;
    SubSeqProd(long a[], long k)
    {
        val=a;
        lim=k;
    }
    long product(int ind, int num)
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
        if ( p2<p || p2>lim)
            return Long.MAX_VALUE;
        return p2;
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
    long recurse(int start, int end, int multi, long prod)
    {
        out.println("recur "+start+" end "+end+" multiply times "+multi+" prod "+prod);
        long newProd=multiply(prod, val[start]);
        if (newProd>lim) {
            out.println("newProd "+newProd);
            return 0;
        }
        else if ( multi==1) {
            int found=binarysearch(prod, start, end);
            long count=found-start;
            if ( multiply(prod, val[found])<=lim)
                count++;
            out.println("binarysearch "+found+" count "+count);
            return count;
        } else {
            long count=0;
            for (int i=start+1; i<=end-multi+1; i++) {
                count += recurse(i, end, multi-1, newProd);
                if (multi==2)  // let binary search do the work when only one to multiply
                    break;
            }
            return count;
        }
    }
    long solve()
    {
        Arrays.sort(val);
        int end=val.length-1;
        while(end>=0 &&val[end]>lim)
            end--;
        if (end<0)
            return 0;
        //if (end<10)
        //    return bruteforce(end+1);
        int n=end+1;
        long total=n;
        int maxMulti=findMaxMulti(n);
        out.println(" numbers "+n+" multi "+maxMulti);
        if ( maxMulti<=1 )
            return total;
        else if (maxMulti>end)
            return (1<<n)-1;  // watch out for precedence
        for (int i=2; i<=maxMulti; i++) {
            for (int j=0; j<=n-maxMulti; j++)
                total += recurse(j, n, i, 1);
        }
        return total;
    }
    long bruteforce(int N)
    {
        long count=0;
        outterfor:
        for (long i=1; i< (1<<N); i++) {  // bit for all subset of A
            long prod=1;
            for (int j=0; j<N; j++) {
                if (((1<<j)&i)>0) {
                    long prod2 = prod *val[j]; // check over flow
                    if ( prod2<prod || prod2>lim)
                        continue outterfor;
                    prod = prod2;
                }
            }
            if (prod<=lim)
                count++;
        }   
        return count;
    }
    static void bruteforce(long A[], long K)
    {
        Arrays.sort(A);
        int N=A.length;
        while (N>0 && A[N-1]>K) {
            N--;
        }
        long count=0;
        outterfor:
        for (long i=1; i< (1<<N); i++) {  // bit for all subset of A
            long prod=1;
            for (int j=0; j<N; j++) {
                if (((1<<j)&i)>0) {
                    long prod2 = prod *A[j]; // check over flow
                    if ( prod2<prod || prod2>K)
                        continue outterfor;
                    prod = prod2;
                }
            }
            if (prod<=K)
                count++;
        }
        out.println(count);
    }
    static void test()
    {
        long A[] = new long[]{1, 2, 3};
        bruteforce(A, 4);  // 5
        out.println("new "+new SubSeqProd(A, 4).solve());
        bruteforce(A, 6);  // 7
        out.println("new "+new SubSeqProd(A, 7).solve());
        
        A = new long[]{10,9,8,7,6,5,4,3,2,1};
        bruteforce(A, 10);  //10+9+3+3
        out.println("new "+new SubSeqProd(A, 10).solve());
        /*
        A = new long[]{100, 200, 300};
        bruteforce(A, 4);  // 0
        bruteforce(A, 100);// 1
        bruteforce(A, 200);// 2
        bruteforce(A, 300);// 3
        out.println("new "+new SubSeqProd(A, 300).solve());
        
        A = new long[]{100000000000000000L, 200000000000000000L, 4000000000000000000L};
        bruteforce(A, 4);  // 0
        out.println("new "+new SubSeqProd(A, 4).solve());
        A = new long[]{10,9,8,7,6,5,4,3,2,1, 11, 12, 13,14,15,16,17,18,19,20,30,29,28,27,26,25,24,23,22,21};
        //bruteforce(A, 4000);//9783
        out.println("new "+new SubSeqProd(A, 4000).solve());  // 84610
        */
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
        new SubSeqProd(A, K).solve();*/
    }
}
