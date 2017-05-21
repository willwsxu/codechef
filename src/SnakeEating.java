
import static java.lang.System.out;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;
import java.util.stream.IntStream;

/*

 */

class SnakeEating {
    Integer L[];
    long prefix[];
    int Q;
    SnakeEating(Integer a[], int q)
    {
        L=a;
        Arrays.sort(L, Comparator.reverseOrder());
        Q=q;
        prefix = new long[L.length];
        prefix[0]=L[0];
        for (int i=1; i<L.length; i++)
            prefix[i]=prefix[i-1]+L[i];
        //out.println(Arrays.toString(L));
        //out.println(Arrays.toString(prefix));
    }
    long diff(int start, int next, int k) {
        long diff=prefix[next];
        if (start>0)
            diff -= prefix[start-1];
        int eater=next-start+1;
        int eaten=L.length-next-1;
        diff = (long)k*eater-diff; // use cast to fix overflow
        if (diff>eaten) 
            return 1;
        else if (diff==eaten)
            return 0;
        return -1;
    }
    // find position that enough snakes can be eaten to grow snake from start to pos to length k
    int binarySnake(int start, int pos, int end, int k)
    {
        if ( pos>end) {
            //out.println("pos == "+pos+" end "+end);
            return 0;
        }
        if (pos==end || pos==end-1) {
            //out.println("pos == "+pos+" end "+end);
            if ( diff(start, end, k)<=0)
                return end-start+1;
            if ( diff(start, pos, k)>0)
                pos--;
            return pos-start+1;            
        }
        int next=(pos+end)/2;
        long diff=prefix[next];
        if (start>0)
            diff -= prefix[start-1];
        int eater=next-start+1;
        int eaten=L.length-next-1;
        diff = (long)k*eater-diff; // use cast to fix overflow
        //out.println("pos "+pos+" end="+end+" diff="+diff+" snakes="+eaten);
        if (diff>eaten) {
            return binarySnake(start, pos, next-1, k);
        }
        else if (diff==eaten) {
            //out.println("diff == "+diff+" count "+eater);
            return eater;
        } else {
            return binarySnake(start, next, end, k);            
        }
    }
    int query(int k)
    {
        int count=0;
        int x=Arrays.binarySearch(L, k, Comparator.reverseOrder());
        //out.println("bs "+x);
        if (x<0) {
            x = -(x+1);
            count=x;
        }else {
            count = ++x;
        }
        // snake from x, eat snake at the end
        if (x>=L.length-1)  // edge test case
            return count;
        int c=binarySnake(x, x, L.length-1, k);
        //out.println("count "+count+" x="+x+" c="+c);
        return count+c;
    }
    void query()
    {
        StringBuilder sb = new StringBuilder();
        for (int i=0; i<Q; i++) {
            int k=sc.nextInt(); // 1 ≤ Ki ≤ 109
            sb.append(query(k));
            sb.append("\n");
        }
        out.print(sb.toString());
    }
    static void test()
    {
        SnakeEating sn = new SnakeEating(new Integer[]{21, 9, 5, 8, 10}, 2);
        out.println(sn.query(10)==3);
        out.println(sn.query(15)==1);
        sn = new SnakeEating(new Integer[]{1, 2, 3, 4, 5}, 2);
        out.println(sn.query(100)==0);
        out.println(sn.query(8)==1);
        out.println(sn.query(6)==2);
        out.println(sn.query(1)==5);
        out.println(sn.query(2)==4);
        out.println(sn.query(3)==4);
        out.println(sn.query(5)==2);
        sn = new SnakeEating(new Integer[]{1,4,7,10,13,16,19,22,25}, 2);
        out.println(sn.query(26)==2);
        sn = new SnakeEating(new Integer[]{1,2,3,4,5,7,10,13,16,19,22,25}, 2);
        out.println(sn.query(25)==3);
        sn = new SnakeEating(new Integer[]{1,2,3,4,5,7,9,11,13,15,17,19,21}, 2);
        out.println(sn.query(22)==3);
        
        sn = new SnakeEating(new Integer[]{15}, 3);
        out.println(sn.query(15)==1);
        out.println(sn.query(16)==0);
        out.println(sn.query(1)==1);       
        sn = new SnakeEating(new Integer[]{1000000000, 1500000000}, 3);
        out.println(sn.query(1000000000)==2);  // 2
        out.println(sn.query(1500000000)==1);  // 1
        out.println(sn.query(1500000001)==1);  // 1
        out.println(sn.query(1500000002)==0);  // 0
        
        Integer[] large=new Integer[100000];
        int v=1000000000;
        for (int i=0; i<large.length; i++) {
            large[i]=v;
            v -=3;
        }
        sn = new SnakeEating(large, 3);
        out.println(sn.query(v+3)==100000);
        out.println(sn.query(v+4)==99999);
        out.println(sn.query(v+7)==99999);
        out.println(sn.query(v+8)==99998);
        out.println(sn.query(1000000001)==258);
    }
    static int[] sortIaR(int a[])  // sort int array reverse
    {
        return IntStream.of(a).boxed()
                .sorted(Comparator.reverseOrder())
                .mapToInt(i->i).toArray();        
    }
    static Integer[] ria(int N) { // read int array
        Integer L[]=new Integer[N];
        for (int i=0; i<N; i++)
            L[i]=sc.nextInt();
        return L;
    }
    
    static Scanner sc = new Scanner(System.in);
    public static void main(String[] args)
    {      
        //test();
        int T=sc.nextInt(); // 1 ≤ T ≤ 5
        for (int i=0; i<T; i++) {
            int N=sc.nextInt(); // 1 ≤ N, Q ≤ 10^5
            int Q=sc.nextInt();
            Integer L[]=ria(N);     // 1 ≤ Li ≤ 10^9
            new SnakeEating(L, Q).query();
        }
    }
}
