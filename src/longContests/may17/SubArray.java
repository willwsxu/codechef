package longContests.may17;

/*
 * Brief Desc: N integers of 0 and 1, find the max sum given a window size K
 * two requests can be made, either shift number array to right by 1 (circular)
 *   or find out max 1 of any window frame
*/
import codechef.CircularSum;
import codechef.IOR;
import codechef.ListBackedPQ;
import codechef.SegTreeRMQ;
import static java.lang.System.out;
import java.util.Arrays;

// classical problem, 3 implementations
// https://discuss.codechef.com/questions/98090/chefsuba-editorial
// CHEFSUBA, Easy medium, Segment trees, , 
// Rotation trick, double array size
// Deque http://www.geeksforgeeks.org/sliding-window-maximum-maximum-of-all-subarrays-of-size-k/
// Deque solution is implemented in c++
// my alternative solution is circular list combining with priority queue
class SubArray  {    
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
    SubArray()
    {
        N=IOR.ni();  // 1 ≤ N, K, P ≤ 10^5
        K=IOR.ni();
        P=IOR.ni();  // request len
        A=IOR.ria(N);      // 0 or 1
        request=IOR.ns();  
        if (K>N) // important case
            K=N;      
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
    
    void pqList(StringBuilder sb)
    { 
        Asum = new int[N];
        for (int i=0; i<K; i++) {
            Asum[0] += A[i];  // store sum of next K elements
        }
        for (int i=1; i<N; i++) {
            Asum[i] = Asum[i-1]+A[(i+K-1)%N]-A[i-1];
        }
        CircularListMax pq = new CircularListMax(Asum, N-K+1);
        for (int i=0; i<request.length(); i++) {
            if (request.charAt(i)=='?') {
                sb.append(pq.peek());
                sb.append("\n");
            }
            else if (N>K && K>1){ // when 
                pq.shiftL();
            }
        }
    }
    
    void segTree(StringBuilder sb)
    {
        CircularSum sum=new CircularSum(A, K);
        //out.println(Arrays.toString(sum.getA2x()));
        SegTreeRMQ rmq = new SegTreeRMQ(sum.getA2x());
        for (int i=0; i<request.length(); i++) {
            if (request.charAt(i)=='?') {
                int ans=rmq.rmqVal(sum.getHead(), sum.getTail());
                //out.println("L="+sum.getHead()+" R="+sum.getTail()+" rmq="+ans);
                sb.append(ans);
                sb.append("\n");
            }
            else if (N>K && K>1){ //
                sum.shiftR();
            }
        }
    }
    
    static void test1cache(int A[], StringBuilder sb, int k, String q)
    {
        SubArray sa=new SubArray(A, k, q);
        sa.pqList(sb);
        sb.append(Arrays.toString(sa.Asum));   
        sb.append("\n");        
    }
    static void test1RMQ(int A[], StringBuilder sb, int k, String q)
    {
        SubArray sa=new SubArray(A, k, q);
        sa.segTree(sb);
        sb.append("\n");        
    }
    static void test()
    {
        StringBuilder sb=new StringBuilder();
        int A[]=new int[]{1,1,0,1,0,1,1};
        SubArray sa=new SubArray(A, 4, "?!!?!!!?!?!?!?");
        sa.bruteforce(sb);         sb.append("\n");
        sa=new SubArray(A, 1, "?!!?!!!?!?!?!?"); sa.bruteforce(sb); sb.append("\n");
        sa=new SubArray(A, 2, "?!!?!!!?!?!?!?"); sa.bruteforce(sb); sb.append("\n");
        sa=new SubArray(A, 3, "?!!?!!!?!?!?!?"); sa.bruteforce(sb); sb.append("\n");
        sa=new SubArray(A, 5, "?!!?!!!?!?!?!?"); sa.bruteforce(sb); sb.append("\n");
        sa=new SubArray(A, 6, "?!!?!!!?!?!?!?"); sa.bruteforce(sb); sb.append("\n");
        sa=new SubArray(A, 7, "?!!?!!!?!?!?!?"); sa.bruteforce(sb); sb.append("\n");
        sa=new SubArray(A, 8, "?!!?!!!?!?!?!?"); sa.bruteforce(sb);
        out.println(sb.toString()+"brute force");
        
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
        out.println("RMQ test");
        sb = new StringBuilder();
        test1RMQ(A, sb, 4, "?!!?!!!?!?!?!?");
        test1RMQ(A, sb, 1, "?!!?!!!?!?!?!?");
        test1RMQ(A, sb, 2, "?!!?!!!?!?!?!?");
        test1RMQ(A, sb, 3, "?!!?!!!?!?!?!?");
        test1RMQ(A, sb, 5, "?!!?!!!?!?!?!?");
        test1RMQ(A, sb, 6, "?!!?!!!?!?!?!?");
        test1RMQ(A, sb, 7, "?!!?!!!?!?!?!?");
        test1RMQ(A, sb, 8, "?!!?!!!?!?!?!?");
        out.println(sb.toString());
    }
    static void testPQList()
    {
        int A[]=new int[]{3, 2, 2, 3, 3, 4, 3};
        CircularListMax.test(A, 4);
        CircularListMax.test(A, 1);
        CircularListMax.test(A, 7);
    }
    
    public static void main(String[] args)
    {   
        StringBuilder sb=new StringBuilder();
        new SubArray().segTree(sb);
        out.print(sb.toString());
    }
}

// find max item in k elements of array size n
class CircularListMax extends ListBackedPQ
{
    int head, tail, size;
    
    CircularListMax(int a[], int k){
        super(a, k, true);
        size=a.length;
        head=0; tail=k-1;
    }
    
    // head and tail pointer moves to left when
    void shiftL() // element moves to right, a[size-1] becomes new head
    {
        remove(tail);      
        //out.println("remove "+A[tail]+" at "+tail);  
        head = (head+size-1)%size;    
        add(head);
        //out.println("add "+A[head]+" at "+head); 
        tail = (tail+size-1)%size;  
        //out.println(Arrays.toString(b));
    }
    static void test(int A[], int frame)
    {
        CircularListMax pq = new CircularListMax(A, A.length-frame+1);
        pq.printSumA();
        pq.shiftL();    pq.shiftL();
        out.println(pq.toString());
        pq.shiftL();    pq.shiftL();    pq.shiftL();
        out.println(pq.toString());
        pq.shiftL();
        out.println(pq.toString());
        pq.shiftL();
        out.println(pq.toString());
        pq.shiftL();
        out.println(pq.toString());
        out.println();
    }
}
