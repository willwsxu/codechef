
import static java.lang.System.out;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;
import java.util.Scanner;
import java.util.stream.IntStream;

/*

 */

class SnakeEating {
    Integer L[];
    long prefix[];  // start from 1
    int Q;
    SnakeEating(Integer a[], int q)
    {
        L=a;
        Arrays.sort(L, Comparator.reverseOrder());
        Q=q;
        prefix = new long[L.length+1];
        prefix[0]=0;
        for (int i=0; i<L.length; i++)
            prefix[i+1]=prefix[i]+L[i];
        //out.println(Arrays.toString(L));
        //out.println(Arrays.toString(prefix));
    }
    long diff(int start, int next, int k) {
        long diff=prefix[next+1];
        diff -= prefix[start];
        int eater=next-start+1;
        int eaten=L.length-next-1;
        diff = (long)k*eater-diff; // use cast to fix overflow
        //out.println("start "+start+" next="+next+" diff="+diff+" snakes="+eaten);
        if (diff>eaten) 
            return -1;
        else if (diff==eaten)
            return 0;
        return 1;
    }
    // find position that enough snakes can be eaten to grow snake from start to pos to length k
    int binarySnake(int start, int pos, int end, int k)
    {
        if ( pos>end) {
            out.println("error start > end"+start+"pos == "+pos+" end "+end);
            return 0;
        }
        if (pos==end || pos==end-1) {
            //out.println("pos == "+pos+" end "+end);
            if ( diff(start, end, k)>=0)
                return end-start+1;
            if ( diff(start, pos, k)<0)
                pos--;
            return pos-start+1;            
        }
        int next=(pos+end)/2;
        long diff=diff(start, next, k);
        //out.println("pos "+pos+" end="+end+" diff="+diff+" snakes="+eaten);
        if (diff<0) {
            return binarySnake(start, pos, next-1, k);
        }
        else if (diff==0) {
            //out.println("diff == "+diff+" count "+eater);
            return next-start+1;
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
        {
            //out.println("bs done at "+x+" count "+count+" len="+L.length+" k="+k+" last="+L[L.length-1]);
            return count;
        }
        int c=binarySnake(x, x, L.length-1, k);
        //out.println("count "+count+" x="+x+" c="+c);
        return count+c;
    }
    void query()
    {
        //StringBuilder sb = new StringBuilder();
        for (int i=0; i<Q; i++) {
            int k=sc.nextInt(); // 1 ≤ Ki ≤ 10^9
            //sb.append(query(k));
            //sb.append("\n");
            out.println(query(k));
        }
        //out.print(sb.toString());
    }
    int bruteforce(int k)
    {
        int count=0;
        for (;count<L.length; count++)
            if (L[count]<k)
                break;
        int left=L.length-count;
        while (left>0) {
            //out.println("len "+L.length+" count="+count+" left="+left);
            if (L[count]+left-1<k)  // minus it self
                break;
            left -= (k-L[count++]+1);            
        }
        return count;
    }
    static void autotest()
    {
        Random rnd=new Random();
        Integer[] large=new Integer[100000];
        int v=1000000000;
        for (int i=0; i<large.length; i++) {
            large[i]=rnd.nextInt(v)+1;
        }
        SnakeEating sn = new SnakeEating(large, 2);
        for (int i=0; i<100000; i++)
        {
            int k=rnd.nextInt(v)+1;
            if (sn.query(k)!=sn.bruteforce(k)) {
                out.println("not equal k"+ k);
                out.println(Arrays.toString(large));
            }
        }       
    }
    static void test()
    {
        SnakeEating sn = new SnakeEating(new Integer[]{21, 9, 5, 8, 10}, 2);
        out.println(sn.query(10)==3);
        out.println(sn.query(11)==2);
        out.println(sn.query(13)==2);
        out.println(sn.query(14)==1);
        out.println(sn.query(25)==1);
        out.println(sn.query(26)==0);
        out.println(sn.query(1)==5);
        out.println(sn.query(5)==5);
        out.println(sn.query(6)==4);
        out.println(sn.query(9)==4);
        out.println();
        sn = new SnakeEating(new Integer[]{1, 2, 3, 4, 5}, 2);
        out.println(sn.query(100)==0);
        out.println(sn.query(8)==1);
        out.println(sn.query(6)==2);
        out.println(sn.query(5)==2);
        out.println(sn.query(3)==4);
        out.println(sn.query(2)==4);
        out.println(sn.query(1)==5);
        sn = new SnakeEating(new Integer[]{1,4,7,10,13,16,19,22,25}, 2);
        out.println(sn.query(26)==2);
        sn = new SnakeEating(new Integer[]{1,2,3,4,5,7,10,13,16,19,22,25}, 2);
        out.println(sn.query(25)==3);
        sn = new SnakeEating(new Integer[]{1,2,3,4,5,7,9,11,13,15,17,19,21}, 2);
        out.println(sn.query(22)==3);
        sn = new SnakeEating(new Integer[]{1,2,3,4,5,7,9,11,13,15,17,19,20,20,20,21}, 2);
        out.println(sn.query(20)==7);
        out.println(sn.query(7)==12);
        out.println(sn.query(6)==13);
        
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
        out.println("v="+v);
        out.println(sn.query(v+3)==100000);
        out.println(sn.query(v+4)==99999);
        out.println(sn.query(v+7)==99999);
        out.println(sn.query(v+8)==99998);
        out.println(sn.query(1000000001)==258);
        out.println(sn.query(1000000000));
        out.println(sn.query(999999999));
        out.println(sn.query(999999997));
        out.println(sn.query(999999800));
        out.println(sn.query(999999600));
        out.println(sn.query(999999300));
        out.println(sn.query(999999000));
        out.println(sn.query(999998000));
        out.println(sn.query(999990000));
        out.println(sn.query(999900000));
        out.println(sn.query(999800000));
        out.println(sn.query(999700005));
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
        //autotest();
        int T=sc.nextInt(); // 1 ≤ T ≤ 5
        for (int i=0; i<T; i++) {
            int N=sc.nextInt(); // 1 ≤ N, Q ≤ 10^5
            int Q=sc.nextInt();
            Integer L[]=ria(N);     // 1 ≤ Li ≤ 10^9
            new SnakeEating(L, Q).query();
        }
    }
}
/*
3
5 2
21 9 5 8 10
10
15
5 1
1 2 3 4 5
100
16 3
1 2 3 4 5 7 9 11 13 15 17 19 20 20 20 21
20
7
6
*/