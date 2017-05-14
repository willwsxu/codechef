
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
    int binarysearch(int num, int lo, int hi) {
        if (lo>=hi)
            return lo;
        int mid = (lo+hi)/2;
        long prod=product(mid, num);
        if (prod>lim)
            return binarysearch(num, lo, mid-1);
        else
            return binarysearch(num, mid, hi);
    }
    void solve()
    {
        Arrays.sort(val);
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
        bruteforce(A, 6);  // 7
        A = new long[]{10,9,8,7,6,5,4,3,2,1};
        bruteforce(A, 10);  //10+9+3+3
        
        A = new long[]{100, 200, 300};
        bruteforce(A, 4);  // 0
        bruteforce(A, 100);// 1
        bruteforce(A, 200);// 2
        bruteforce(A, 300);// 3
        
        A = new long[]{100000000000000000L, 200000000000000000L, 4000000000000000000L};
        bruteforce(A, 4);  // 0
        A = new long[]{10,9,8,7,6,5,4,3,2,1, 11, 12, 13,14,15,16,17,18,19,20,30,29,28,27,26,25,24,23,22,21};
        bruteforce(A, 4000);//9783
    }
    static Scanner sc = new Scanner(System.in);
    public static void main(String[] args)
    {      
        test();
        /*
        int N=sc.nextInt();  // 1 ≤ N ≤ 30
        int K=sc.nextInt();  // 1 ≤ K, Ai ≤ 10^18
        long A[] = new long[N];
        for (int j=0; j<N; j++)
            A[j] = sc.nextLong();
        bruteforce(A, K);*/
    }
}
