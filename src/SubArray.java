
import static java.lang.System.out;
import java.util.Scanner;


class SubArray {
    
    int start=0;
    int N;  // Array size
    int P;  // number of requests
    int K;  // frame
    int A[];
    String request;
    SubArray(int []A, int K, String p)
    {
        this.N=A.length;
        this.P=p.length();
        this.request = p;
        this.K=K;
        this.A=A;
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
    void bruteforce()
    {
        StringBuilder sb=new StringBuilder();
        for (int i=0; i<request.length(); i++) {
            if (request.charAt(i)=='?') {
                sb.append(findMaxFrame());
                sb.append("\n");
            }
            else {
                start =(start+N-1)%N; // shift
            }
        }
        out.print(sb.toString());
    }
    static void test()
    {
        int A[]=new int[]{1,1,0,0,0,1,1};
        SubArray sa=new SubArray(A, 4, "?!!?!!!?!?!?!?");
        sa.bruteforce();
        out.println();
        sa=new SubArray(A, 1, "?!!?!!!?!?!?!?");
        sa.bruteforce();
        out.println();
        sa=new SubArray(A, 2, "?!!?!!!?!?!?!?");
        sa.bruteforce();
        out.println();
        sa=new SubArray(A, 3, "?!!?!!!?!?!?!?");
        sa.bruteforce();
        out.println();
        sa=new SubArray(A, 5, "?!!?!!!?!?!?!?");
        sa.bruteforce();
        out.println();
        sa=new SubArray(A, 6, "?!!?!!!?!?!?!?");
        sa.bruteforce();
        out.println();
        sa=new SubArray(A, 7, "?!!?!!!?!?!?!?");
        sa.bruteforce();
        out.println();
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
        new SubArray(A, K, p).bruteforce();
    }
}
