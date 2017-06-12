package longContests.june17;


import static java.lang.System.out;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Scanner;


class ChefFeast {
    ChefFeast() {
        this(                          // 1 ≤ N ≤ 10^5
             ria(sc.nextInt(), sc));  // -10^8 ≤ Ai ≤ 10^8
    }
    ChefFeast(int a[])
    {
        int n=a.length;
        long pos=0;
        int count=0;
        long total=0;
        PriorityQueue<Integer> pq=new PriorityQueue<>(100000, Comparator.reverseOrder());
        for (int i=0;i<n;i++) {
            if (a[i]>=0) {
                pos += a[i];
                count++;
            }
            else {
                total += a[i];
                pq.add(a[i]);
            }
        }
        while (!pq.isEmpty()) {
            int y=pq.peek();
            //out.println(y+" "+pos);
            if ((pos+y)*(count+1)>pos*count+y) {
                count++;
                pq.poll();
                pos += y;
                total -= y;
            }
            else 
                break;
        }
        total += pos*count;
        out.println(total);
    }
    
    static void test()
    {
        new ChefFeast(new int[]{-2, 0, -8});
        new ChefFeast(new int[]{1, 2, 3});
        new ChefFeast(new int[]{-5, 2, 6});
        new ChefFeast(new int[]{-2, 2, 6});
        new ChefFeast(new int[]{-10, -3, 3, 6});
    }
    
    public static int[] ria(int N, Scanner sc) { // read int array
        int L[]=new int[N];
        for (int i=0; i<N; i++)
            L[i]=sc.nextInt();
        return L;
    }
    static Scanner sc = new Scanner(System.in);
    public static void main(String[] args)
    {      
        //test();
        int T=sc.nextInt(); // 1 ≤ T ≤ 8
        while (T-->0)
            new ChefFeast();
    }
}
