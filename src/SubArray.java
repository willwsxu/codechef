/*
 * Brief Desc: N integers of 0 and 1, find the max sum given a window size K
 * two requests can be made, either shift number array to right by 1 (circular)
 *   or find out max 1 of any window frame
*/
import static java.lang.System.out;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Scanner;

// https://discuss.codechef.com/questions/98090/chefsuba-editorial
// CHEFSUBA, Easy medium, Segment trees, Deque, 
// Rotation trick, double array size

// my alternative solution is circular list combining with priority queue
class SubArray extends iox {    
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
        N=sc.nextInt();  // 1 ≤ N, K, P ≤ 10^5
        K=sc.nextInt();
        P=sc.nextInt();  // request len
        A=ria(N);      // 0 or 1
        request=sc.next();  
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
    
    void cacheSum(StringBuilder sb)
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
        CircularSum.test();
        /*StringBuilder sb=new StringBuilder();
        new SubArray().cacheSum(sb);
        out.print(sb.toString());*/
    }
}
class iox
{    
    static Scanner sc = new Scanner(System.in);    
        
    public static int ni()
    {
        return sc.nextInt();
    }
    public static long nl()
    {
        return sc.nextLong();
    }
    
    public static int[] ria(int N) { // read int array
        int L[]=new int[N];
        for (int i=0; i<N; i++)
            L[i]=sc.nextInt();
        return L;
    }
}

// copied from shared class
class Pi  // pair of int
{
    int first;
    int second;
    Pi(int f, int s)
    {
        first=f;
        second=s;
    }
    @Override
    public boolean equals(Object s)
    {
        if (s instanceof Pi) {
            Pi other =(Pi)s;
            return first==other.first && second==other.second;
        }
        return false;
    }
    @Override
    public int hashCode()
    {
        return (int)(first*second);
    }
    @Override
    public String toString()
    {
        return first+":"+second;
    }
}

// Avoid priority Queue remove method call as it is slow
// super fast, 5x better than SortedList class
// Priority Queue stores K elements of array, when item becomes invalid, mark b[] false
class ListBackedPQ
{    
    int A[];
    boolean []b;
    Comparator<Pi> cmp;
    PriorityQueue<Pi> pq;
    ListBackedPQ(int a[], int k, boolean reverseOrder)
    {
        if (reverseOrder)
            cmp = (r1,r2)->r2.second-r1.second;
        else
            cmp = (r1,r2)->r1.second-r2.second;
        pq = new PriorityQueue<>(a.length, cmp);
        A=a;
        b=new boolean[a.length];
        // first k are valid
        for (int i=0; i<k; i++) {
            pq.add(new Pi(i, A[i]));
            b[i]=true;
        }
    }
    void remove(int ind)
    {
        b[ind]=false;
    }
    void add(int ind)
    {
        b[ind]=true;
        pq.add(new Pi(ind, A[ind]));
    }
    public int peek()
    {
        Pi p=pq.peek();
        while (!b[p.first]) {
            pq.poll();  // discard items no longer valid
            p=pq.peek();
        }
        return p.second;
    }
    public static void perfTest(int N)
    {
        Instant start = Instant.now();
        int A[]=new int[N];
        for (int i=0; i<N; i++) {
            A[i]=i%(N/3)+1;
        }    
        ListBackedPQ pq=new ListBackedPQ(A, N-2, true);
        Instant mid = Instant.now();
        out.println("usec "+ChronoUnit.MICROS.between(start, mid));
        // when N=100000, 20 msec
        out.println("size "+N+" pq size "+pq.pq.size());
        
        int K=N-2;
        for (int i=0; i<N; i++) {
            pq.remove(i);
            pq.add((K+i)%N);
        }
        Instant end = Instant.now();   
        out.println("usec "+ChronoUnit.MICROS.between(mid, end));   
        // when N=100000, 12 msec
        out.println("size "+N+" pq size "+pq.pq.size());
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
        out.println(pq.peek()+" "+Arrays.toString(pq.A));
        pq.shiftL();    pq.shiftL();
        out.println(pq.peek()+" "+pq.pq.toString());
        pq.shiftL();    pq.shiftL();    pq.shiftL();
        out.println(pq.peek()+" "+pq.pq.toString());
        pq.shiftL();
        out.println(pq.peek()+" "+pq.pq.toString());
        pq.shiftL();
        out.println(pq.peek()+" "+pq.pq.toString());
        pq.shiftL();
        out.println(pq.peek()+" "+pq.pq.toString());
        out.println();
    }
}

// calculate sum of k elements of array size n
class CircularSum
{
    int []A2x;
    int pos, n;
    CircularSum(int A[], int k)
    {
        n=A.length;
        int n2=2*n;
        if (k>n)
            k=n;
        A2x=new int[n2];
        for (int i=0; i<n; i++) {
            A2x[i]=A[i];
            A2x[i+n]=A[i];
        }
        for (int i=n2-2; i>=0; i--) {
            A2x[i]+=A2x[i+1];
            if (i+k<n2)
                A2x[i] -= A[(i+k)%n];
        }
        pos=n; // start at n
    }
    
    public static void test()
    {
        CircularSum cir=new CircularSum(new int[]{1,0,1,0,1}, 1);
        out.println(Arrays.toString(cir.A2x));
        CircularSum cir2=new CircularSum(new int[]{1,0,1,0,1}, 3);
        out.println(Arrays.toString(cir2.A2x));
    }
    
    void shiftR() // 2n-1 dropped off, n-1 is added
    {
        pos--;
        if (pos<0)
            pos=n;
    }
}

class SegTreeRMQ  // Range min/max query
{
    int st[], a[];
    int n;
    public SegTreeRMQ(int a[]) 
    {
        n=a.length;
        st=new int[4*n+1];
        build(1, 0, n-1);
    }
    private int left(int p)
    {
        return p<<1;
    }
    private int right(int p)
    {
        return (p<<1)+1;
    }
    private void build(int node, int first, int last)
    {
        if (first==last)
            st[node]=first;
        else {
            build(left(node), first, (first+last)/2);
            build(right(node), (first+last)/2, last);
            int p1=st[left(node)];
            int p2=st[right(node)];
            st[node]=a[p1]>a[p2]?p1:p2; // pick max of p1 and p2
        }
    }
    int rmq(int p, int L, int R, int i, int j)
    {
        if (i>R || j<L)
            return -1; // segment outside of query range
        if (L>=i && R<=j)
            return st[p]; // i L R j, inside range
        int p1=rmq(left(p), L, (L+R)/2, i, j);
        int p2=rmq(right(p), (L+R)/2+1, j, i, j);
        if (p1<0)
            return p2;
        if (p2<0)
            return p1;
        return a[p1]>a[p2]?p1:p2;
    }
    
    public int rmq(int i, int j) {
        return rmq(1, 0, n-1, i, j);
    }
}