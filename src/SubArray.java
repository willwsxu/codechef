
import static java.lang.System.out;
import java.util.Arrays;
import java.util.Scanner;


class SubArray {    
    int start=0;
    int N;  // Array size
    int P;  // number of requests
    int K;  // frame
    int A[];
    int Asum[];
    String request;
    SubArray(int []A, int K, String p)
    {
        this.N=A.length;
        this.P=p.length();
        this.request = p;
        this.A=A;
        if (K>N) // important case
            K=N;
        this.K=K;
    }
    int count1(int s)
    {
        int count=0;
        for (int i=0; i<K; i++) {
            if (A[(i+s)%N]==1)
                count++;
        }
        return count;
    }
    int findMaxFrame()
    {
        int max=0;
        for (int i=0; i<=N-K; i++) {
            int m=count1(i+start);
            if (max<m)
                max=m;
        }
        return max;
    }
    void bruteforce(StringBuilder sb)
    {
        for (int i=0; i<request.length(); i++) {
            if (request.charAt(i)=='?') {
                sb.append(findMaxFrame());
                sb.append("\n");
            }
            else {
                start =(start+N-1)%N; // shift
            }
        }
    }
    void cacheSum(StringBuilder sb)
    {
        Asum = new int[N];
        for (int i=0; i<K; i++) {
            Asum[0] += A[i];  // store sum of next K elements
        }
        for (int i=1; i<N; i++) {
            Asum[i] = Asum[i-1]+A[(i+K-1)%N]-A[i-1];
        }
        out.println(Arrays.toString(Asum));
    }
    void solve()
    {
        StringBuilder sb=new StringBuilder();
        bruteforce(sb);
        out.print(sb.toString());
    }
    static void test()
    {
        StringBuilder sb=new StringBuilder();
        int A[]=new int[]{1,1,0,1,0,1,1};
        SubArray sa=new SubArray(A, 4, "?!!?!!!?!?!?!?");
        sa.bruteforce(sb);
        out.println();
        sa=new SubArray(A, 1, "?!!?!!!?!?!?!?");
        sa.bruteforce(sb);
        out.println();
        sa=new SubArray(A, 2, "?!!?!!!?!?!?!?");
        sa.bruteforce(sb);
        out.println();
        sa=new SubArray(A, 3, "?!!?!!!?!?!?!?");
        sa.bruteforce(sb);
        out.println();
        sa=new SubArray(A, 5, "?!!?!!!?!?!?!?");
        sa.bruteforce(sb);
        out.println();
        sa=new SubArray(A, 6, "?!!?!!!?!?!?!?");
        sa.bruteforce(sb);
        out.println();
        sa=new SubArray(A, 7, "?!!?!!!?!?!?!?");
        sa.bruteforce(sb);
        out.println();
        sa=new SubArray(A, 8, "?!!?!!!?!?!?!?");
        sa.bruteforce(sb);
        out.println(sb.toString());
        
        sa=new SubArray(A, 4, "?!!?!!!?!?!?!?");
        sa.cacheSum(sb);
    }
    static Scanner sc = new Scanner(System.in);
    public static void main(String[] args)
    {      
        test();/*
        int N=sc.nextInt();  // 1 ≤ N, K, P ≤ 10^5
        int K=sc.nextInt();
        int P=sc.nextInt();  // P=p.length
        int []A=new int[N];
        for (int j=0; j<N; j++)
            A[j]=sc.nextInt();  // 0 or 1
        String p=sc.next();
        new SubArray(A, K, p).solve();*/
    }
}

// 1 based array to store segemment tree for Range Sum Query
class SegTree
{
    int sum[];
   SegTree(int N) 
   {
       sum=new int[2*N+1];
       buildSum(1, 0, N-1);
   }
   int buildSum(int node, int first, int last)
   {
       if (first==last)
           sum[node]=compute(first);
       else {
           int mid = (first+last)/2;
           sum[node]=buildSum(2*node, first, mid)+buildSum(2*node+1, mid+1, last);
       }
       return sum[node];
   }
   int compute(int ind)
   {
       return 0;
   }
}