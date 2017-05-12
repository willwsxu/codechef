
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
        SortedList pq = new SortedList(true);
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
        //SortedList.perfTest(100000);
        //test();
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

// ArrayList is 25x faster than linkedlist for sorted list
class SortedList
{
    private List<Integer> ls;
    private PriorityQueue<Integer> pq;
    Comparator<Integer> cmp;
    SortedList(boolean reverseOrder)
    {
        if (reverseOrder)
            cmp = Comparator.reverseOrder();
        else
            cmp = Comparator.naturalOrder();
        ls = new ArrayList<>(10000);
        //pq = new PriorityQueue<>(10000, cmp);
    }
    public boolean add(Integer e)
    {
        if (ls!=null)
        {
        int i=Collections.binarySearch(ls, e, cmp);
        if (i<0)
            i=-(i+1);
        ls.add(i, e);
        } else {
            pq.add(e);
        }
        return true;
    }
    public int peek()
    {
        if (ls!=null)
            return ls.get(0);
        else
            return pq.peek();
    }
    
    public boolean remove(Integer e)
    {
        if (ls!=null) {
        int i=Collections.binarySearch(ls, e, cmp);
        if (i>=0) {
            ls.remove(i);
            return true;
        }
        } else {
            pq.remove(e);
        }
        return false;
    }
    public static void test()
    {
        SortedList sll = new SortedList(true);
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
        SortedList sll = new SortedList(true);
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
            sll.remove(new Integer(d));
        }
        Instant end = Instant.now();   
        out.println("usec "+ChronoUnit.MICROS.between(mid, end));   
        // when N=100000, LinkedList 83 sec, ArrayList 1.3 sec, PriorityQueue 3.6 sec
    }
}