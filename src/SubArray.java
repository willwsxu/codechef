
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
    //PriorityQueue<Integer> pq=new PriorityQueue<>(100000, Comparator.reverseOrder());
    void cacheSum(StringBuilder sb)
    {
        tail=N-K;        
        SortedList pq = new SortedList(true, 100000);
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
    static void testList()
    {
        StringBuilder sb=new StringBuilder();
        int A[]=new int[]{3, 2, 2, 3, 3, 4, 3};
        ListBackedPQ pq = new ListBackedPQ(A, 4, true);
        out.println(pq.peek());
        pq.add(6);
        pq.remove(3);
        pq.add(5);
        pq.remove(2);
        out.println(pq.peek());
        pq.add(4);
        pq.remove(1);
        pq.add(3);
        pq.remove(0);
        pq.add(2);
        pq.remove(6);
        out.println(pq.peek());
        pq.add(1);
        pq.remove(5);
        out.println(pq.peek());
        pq.add(0);
        pq.remove(4);
        out.println(pq.peek());
        pq.add(6);
        pq.remove(3);
        out.println(pq.peek());
        out.println();
    }
    
    static Scanner sc = new Scanner(System.in);
    public static void main(String[] args)
    {      
        ListBackedPQ.perfTest(100000);
        /*int N=sc.nextInt();  // 1 ≤ N, K, P ≤ 10^5
        int K=sc.nextInt();
        int P=sc.nextInt();  // P=p.length
        int []A=new int[N];
        for (int j=0; j<N; j++)
            A[j]=sc.nextInt();  // 0 or 1
        String p=sc.next();
        new SubArray(A, K, p).solve();*/
    }
}

// ArrayList is 25x faster than linkedlist for sorted list
// ArrayList is also faster than int[] impl, up to 3 times
class SortedList
{
    //private List<Integer> ls;
    private int[] ls;
    int size;
    Comparator<Integer> cmp;
    SortedList(boolean reverseOrder, int capacity)
    {
        if (reverseOrder)
            cmp = Comparator.reverseOrder();
        else
            cmp = Comparator.naturalOrder();
        //ls = new ArrayList<>(10000);
        ls = new int[capacity+10];
    }
    public void add(int i, int e)
    {
        for (int j=size-1; j>=i; j--)
            ls[j+1]=ls[j];
        ls[i]=e;
        size++;
    }
    public void remove(int i, int e)
    {
        for (int j=i; j<size-1; j++)
            ls[j]=ls[j+1];
        size--;
    }
    public boolean add(int e)
    {
        //int i=Collections.binarySearch(ls, e, cmp);
        int i=Arrays.binarySearch(ls, 0, size, e);
        if (i<0)
            i=-(i+1);
        //ls.add(i, e);
        add(i, e);
        return true;
    }
    public int peek()
    {
        return ls[0];//ls.get(0);
    }
    
    public boolean remove(int e)
    {
        //int i=Collections.binarySearch(ls, e, cmp);
        int i=Arrays.binarySearch(ls, 0, size, e);
        if (i>=0) {
            //ls.remove(i);
            remove(i, e);
            return true;
        }
        return false;
    }
    public static void test()
    {
        SortedList sll = new SortedList(true, 100000);
        sll.add(10);
        sll.add(20);
        sll.add(30);
        sll.add(5);
        sll.add(15);
        System.out.println(sll);
        System.out.println(sll.peek());
        sll.remove(new Integer(30));
        System.out.println(sll.peek());
        sll.add(4);
        sll.remove(0);
        System.out.println(sll);
    }
    public static void perfTest(int N)
    {
        Instant start = Instant.now();
        SortedList sll = new SortedList(true, 100000);
        for (int i=0; i<N; i++) {
            sll.add(i%(N/3)+1);
        }        
        Instant mid = Instant.now();
        out.println("usec "+ChronoUnit.MICROS.between(start, mid));
        // when N=100000, LinkedList 11 sec, ArrayList .45 sec, PriorityQueue 15msec
        
        for (int i=0; i<N; i++) {
            int a=i%(N/7)+1;
            int d=i%(N/3)+1;
            sll.add(a);
            sll.remove(d);
        }
        Instant end = Instant.now();   
        out.println("usec "+ChronoUnit.MICROS.between(mid, end));   
        // when N=100000, LinkedList 83 sec, ArrayList 1.3 sec, PriorityQueue 3.6 sec
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
        // when N=100000, LinkedList 11 sec, ArrayList .45 sec, PriorityQueue 15msec
        
        int K=N-2;
        for (int i=0; i<N; i++) {
            pq.add((K+i)%N);
            pq.remove(i);
        }
        Instant end = Instant.now();   
        out.println("usec "+ChronoUnit.MICROS.between(mid, end));   
        // when N=100000, LinkedList 83 sec, ArrayList 1.3 sec, PriorityQueue 3.6 sec
    }
}