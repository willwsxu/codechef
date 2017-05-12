
import static java.lang.System.out;
import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;
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
        //out.println("N "+N+" K "+K);
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
    int head=0;
    int tail=0;
    PriorityQueue<Integer> pq=new PriorityQueue<>(100000, Comparator.reverseOrder());
    void cacheSum(StringBuilder sb)
    {
        tail=N-K;
        Asum = new int[N];
        for (int i=0; i<K; i++) {
            Asum[0] += A[i];  // store sum of next K elements
        }
        pq.add(Asum[0]);
        for (int i=1; i<N; i++) {
            Asum[i] = Asum[i-1]+A[(i+K-1)%N]-A[i-1];
            if (i<=N-K)
                pq.add(Asum[i]);
        }
        //out.println("head "+head+" tail "+tail);
        for (int i=0; i<request.length(); i++) {
            if (request.charAt(i)=='?') {
                sb.append(pq.peek());
                sb.append("\n");
            }
            else if (N>K){
                head = (head+N-1)%N;
                if (Asum[tail] !=Asum[head]) {
                    pq.remove(new Integer(Asum[tail]));
                    //out.println("remove "+Asum[tail]+" at "+tail);
                    pq.add(Asum[head]);
                    //out.println("add "+Asum[head]+" at "+head);
                }
                tail = (tail+N-1)%N;
                //out.println(pq);
            }
        }
    }
    void solve()
    {
        StringBuilder sb=new StringBuilder();
        //bruteforce(sb);
        cacheSum(sb);
        out.print(sb.toString());
    }
    
    static void test1cache(int A[], StringBuilder sb, int k, String q)
    {
        SubArray sa=new SubArray(A, k, q);
        sa.cacheSum(sb);
        sb.append(Arrays.toString(sa.Asum));   
        sb.append("\n");        
    }
    static void test()
    {
        StringBuilder sb=new StringBuilder();
        int A[]=new int[]{1,1,0,1,0,1,1};
        SubArray sa=new SubArray(A, 4, "?!!?!!!?!?!?!?");
        sa.bruteforce(sb);
        sb.append("\n");
        sa=new SubArray(A, 1, "?!!?!!!?!?!?!?");
        sa.bruteforce(sb);
        sb.append("\n");
        sa=new SubArray(A, 2, "?!!?!!!?!?!?!?");
        sa.bruteforce(sb);
        sb.append("\n");
        sa=new SubArray(A, 3, "?!!?!!!?!?!?!?");
        sa.bruteforce(sb);
        sb.append("\n");
        sa=new SubArray(A, 5, "?!!?!!!?!?!?!?");
        sa.bruteforce(sb);
        sb.append("\n");
        sa=new SubArray(A, 6, "?!!?!!!?!?!?!?");
        sa.bruteforce(sb);
        sb.append("\n");
        sa=new SubArray(A, 7, "?!!?!!!?!?!?!?");
        sa.bruteforce(sb);
        sb.append("\n");
        sa=new SubArray(A, 8, "?!!?!!!?!?!?!?");
        sa.bruteforce(sb);
        out.println(sb.toString());
        
        sb = new StringBuilder();
        // 1,1,0,1,0,1,1->3, 2, 2, 3, 3, 4, 3
        test1cache(A, sb, 4, "?!!?!!!?!?!?!?");
        test1cache(A, sb, 1, "?!!?!!!?!?!?!?");
        test1cache(A, sb, 2, "?!!?!!!?!?!?!?");
        test1cache(A, sb, 3, "?!!?!!!?!?!?!?");
        test1cache(A, sb, 5, "?!!?!!!?!?!?!?");
        test1cache(A, sb, 6, "?!!?!!!?!?!?!?");
        test1cache(A, sb, 7, "?!!?!!!?!?!?!?");
        test1cache(A, sb, 8, "?!!?!!!?!?!?!?");
        out.println(sb.toString());
    }
    static Scanner sc = new Scanner(System.in);
    public static void main(String[] args)
    {      
        //test();/*
        int N=sc.nextInt();  // 1 ≤ N, K, P ≤ 10^5
        int K=sc.nextInt();
        int P=sc.nextInt();  // P=p.length
        int []A=new int[N];
        for (int j=0; j<N; j++)
            A[j]=sc.nextInt();  // 0 or 1
        String p=sc.next();
        new SubArray(A, K, p).solve();
    }
}
