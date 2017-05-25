
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
    int maxK=1000000000;
    // Editorial idea
    SnakeEating(Integer a[], int q, boolean ascending)
    {
        int n=a.length;
        L=new Integer[n+1];
        Arrays.sort(a);
        Q=q;
        prefix = new long[n+1];
        prefix[0]=0;
        L[0]=0;
        for (int i=0; i<n; i++) {
            L[i+1]=a[i];
            prefix[i+1]=prefix[i]+maxK-a[i];
        }
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
            //out.println("error start > end"+start+"pos == "+pos+" end "+end);
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
    
    /*
        0 1  2  3  4  5   N=5
        0 14 13 12 11 10
        0 14 27 39 50 50 prefix
    K=5, p3=4
    k=6
    */
    int binaryEatSnakeAsc(int p1, int p2, int p3, int k)
    {
        //out.println("p1="+p1+" p2="+p2);
        if (p1>=p2)
            return p1;
        int mid = (p1+p2)/2;
        long eat=prefix[p3]-prefix[mid]-(long)(p3-mid)*(maxK-k);
        //out.println("eat="+eat+" mid="+mid);
        if (eat>mid)
            return binaryEatSnakeAsc(mid+1, p2, p3, k);
        else 
            return binaryEatSnakeAsc(p1, mid, p3, k);
    }
    // keep track index of snakes to be eaten(p1), and no less than k (p3)
    int queryAsc(int k)  // sorted ascending
    {
        int p3=Arrays.binarySearch(L, k);
        if (p3<0) {
            p3 = -(p3+1);
        }
        if ( p3<=2)
            return L.length-p3;
        p3--;
        int p1= binaryEatSnakeAsc(1, p3, p3, k);
        /*out.println("p1="+p1+" p3="+p3+" k="+k+" L[p1]="+L[p1]+" L[p3]"+L[p3]);
        long total=0;
            out.println(k-L[p1-1]);
            out.println(k-L[p1]);
        for (int i=p1+1; i<=p3; i++) {
            total += L[i];
            out.println(k-L[i]);
        }
        total=(long)k*(p3-p1)-total;
        out.println("total eat "+total);*/
        return L.length-p1-1;
    }
    void query()
    {
        //StringBuilder sb = new StringBuilder();
        for (int i=0; i<Q; i++) {
            int k=sc.nextInt(); // 1 ≤ Ki ≤ 10^9
            //sb.append(query(k));
            //sb.append("\n");
            //out.println(query(k));
            out.println(bruteforce(k));
        }
        //out.print(sb.toString());
    }
    void queryAsc()
    {
        for (int i=0; i<Q; i++) {
            int k=sc.nextInt(); // 1 ≤ Ki ≤ 10^9
            out.println(queryAsc(k));
        }
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
        Integer[] large=new Integer[10000];
        int v=1000000000;
        for (int i=0; i<large.length; i++) {
            large[i]=rnd.nextInt(v)+1;
        }
        for (int i=0; i<100000; i++)
        {
            SnakeEating sn2 = new SnakeEating(large, 2, true);
            SnakeEating sn = new SnakeEating(large, 2);
            int k=rnd.nextInt(v)+1;
            int a1=sn.bruteforce(k);
            int a2=sn2.queryAsc(k);
            if (a1 !=a2) {
                out.println(i+" not equal k="+ k+" a1="+a1+" a2="+a2);
                out.println(Arrays.toString(large));
                break;
            }
        }       
    }
    
    static void test2()
    {
        sc = codechef.ContestHelper.getFileScanner("snake-eat-t.txt");
        /*SnakeEating sn = new SnakeEating(new Integer[]{1, 2, 3, 4, 5}, 2, true);
        out.println(sn.queryAsc(100)==0);
        out.println(sn.queryAsc(8)==1);
        out.println(sn.queryAsc(6)==2);
        out.println(sn.queryAsc(5)==2);
        out.println(sn.queryAsc(3)==4);
        out.println(sn.queryAsc(2)==4);
        out.println(sn.queryAsc(1)==5);*/
        
        int N=sc.nextInt(); // 1 ≤ N, Q ≤ 10^5
        int Q=sc.nextInt();
        Integer L[]=ria(N);     // 1 ≤ Li ≤ 10^9
        SnakeEating sn = new SnakeEating(L, Q, true);
        int k=sc.nextInt();
        out.println(sn.queryAsc(k));
        SnakeEating sn1 = new SnakeEating(L, 2);
        out.println(sn1.bruteforce(k));
        out.println(sn1.query(k));
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
        out.println(sn.query(1)==2);
        out.println(sn.query(2000000000)==0);
        
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
        //test2();
        int T=sc.nextInt(); // 1 ≤ T ≤ 5
        for (int i=0; i<T; i++) {
            int N=sc.nextInt(); // 1 ≤ N, Q ≤ 10^5
            int Q=sc.nextInt();
            Integer L[]=ria(N);     // 1 ≤ Li ≤ 10^9
            new SnakeEating(L, Q, true).queryAsc();
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