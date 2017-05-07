
import static java.lang.System.out;
import java.util.Arrays;
import java.util.Scanner;


// any sub sequence, product < K
// use bit for subset, int is enough for N=30
class SubSeqProd {
    
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
                    if ( prod2<prod)
                        break outterfor;
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
        bruteforce(A, 4);
        bruteforce(A, 6);
        A = new long[]{10,9,8,7,6,5,4,3,2,1};
        bruteforce(A, 10);  //10+9+3+3
        
        A = new long[]{100, 200, 300};
        bruteforce(A, 4);
        bruteforce(A, 100);
        bruteforce(A, 200);
        bruteforce(A, 300);
        
        A = new long[]{100000000000000000L, 200000000000000000L, 4000000000000000000L};
        bruteforce(A, 4);
    }
    static Scanner sc = new Scanner(System.in);
    public static void main(String[] args)
    {      
        //test();/*
        int N=sc.nextInt();  // 1 ≤ N ≤ 30
        int K=sc.nextInt();  // 1 ≤ K, Ai ≤ 10^18
        long A[] = new long[N];
        for (int j=0; j<N; j++)
            A[j] = sc.nextLong();
        bruteforce(A, K);
    }
}
