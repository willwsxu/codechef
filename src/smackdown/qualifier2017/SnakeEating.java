package smackdown.qualifier2017;


import static codechef.Calculation.prefixSum;
import static codechef.Calculation.reverse;
import static codechef.ContestHelper.ria;
import static java.lang.System.out;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;
import java.util.Scanner;

/*
 * Snake a can eat b if len(a)>=len(b), len(a) increase by 1 after eating
 * Given initial array of snake length, find out how many snakes can have len>=k
 */
// SNAKEEAT EASY
// Binary search method with duplicating values, Good exercise
// custom binary search to correctly find lowerbound. 
// Java binarysearch does not support lower/upper bound
abstract class SnakeBase
{
    int L[];
    long prefix[];  // start from 1
    int Q;
    final int maxK=1000000000;    
    
    void trace(int p1, int p3, int k)
    {
        long total=0;
            out.println(k-L[p1-1]);
            out.println(k-L[p1]);
        for (int i=p1+1; i<=p3; i++) {
            total += L[i];
            out.println(k-L[i]);
        }
        total=(long)k*(p3-p1)-total;
        out.println("total eat "+total);
    }
    
    static int lowerBound(int[] a, long k) {
        int n = a.length;
        if (a[n-1] < k)
            return n;
        int l = -1, r = n - 1;
        while (r - l > 1) {
            int mid = (l + r) >> 1;
            if (a[mid] >= k) {
                r = mid;
            } else {
                l = mid;
            }
        }
        return r;
    }        
    // not efficient when there are many elements of same value, TLE for this project
    static int lowerBound2(int[] L, int k) {
        int p3=Arrays.binarySearch(L, k);
        if (p3<0) {
            p3 = -(p3+1);
        }
        while (p3>0) {
            if (L[p3-1]==k)
                p3--;
            else
                break;
        }
        return p3;
    }    
    
    static int findAfter(long[] p, int[] a, int end, long k) {
        if( k-a[end]>end ) return 0;
        int l=0,r=end;
        while( r-l>1 ){
            int mid=(l+r)>>1;  // mid to end inclusive at both ends
            long req = (end-mid+1)*k-(p[end+1]-p[mid]);
            if( req<=mid ){
                r=mid;
            } else {
                l=mid;
            }
        }
        return end-r+1;
    }
    SnakeBase()
    {
        int N=sc.nextInt(); // 1 ≤ N, Q ≤ 10^5
        Q=sc.nextInt();
        L=ria(N, sc);     // 1 ≤ Li ≤ 10^9
    }
    SnakeBase(int a[], int q)
    {
        L=a;        Q=q;
    }
    static Scanner sc = new Scanner(System.in);
    public static void solve(int version)
    {
        int T=sc.nextInt(); // 1 ≤ T ≤ 5
        while (T-->0) {
            StringBuilder sb = new StringBuilder();
            SnakeBase sn;
            if (version==1)
                sn=new SnakeEating();  //WA
            else
                sn=new SnakeEating3();     // pass test
            for (int j=0; j<sn.Q; j++) {
                int k=sc.nextInt(); // 1 ≤ Ki ≤ 10^9
                sb.append(sn.query(k));
                sb.append("\n");
            }
            out.print(sb.toString());   
        }
    }
    abstract int query(int k);
}

// borrow idea from a contentant https://www.codechef.com/viewsolution/13648938
class SnakeEating3 extends SnakeBase
{
    SnakeEating3(int a[], int q)
    {
        super(a, q);
        Arrays.sort(L);
        prefix=prefixSum(L);
    }
    SnakeEating3()
    {
        super();
        Arrays.sort(L);
        prefix=prefixSum(L);
    }
    int query(int k)  // sorted ascending
    {
        int p3=lowerBound(L, k);
        int res=L.length-p3;
        if (p3<=1)
            return res;
        int p1= findAfter(prefix, L, p3-1, k);
        //out.println("p1="+p1+" p3="+p3+" k="+k);//+" L[p1]="+L[p1]+" L[p3]"+L[p3]);

        return res+p1;
    }
    
    static void testFindCurr(int[] a, int k)
    {
        int c1=SnakeBase.lowerBound(a, k);
        int c2=SnakeBase.lowerBound2(a, k);
        out.println("find k="+k+":"+c1+"=="+c2);        
    }
    static void test4()
    {
        int[] a=new int[]{21, 5, 8,8,8, 10};   
        Arrays.sort(a);
        out.println(Arrays.toString(a));     
        testFindCurr(a, 22);
        testFindCurr(a, 21);
        testFindCurr(a, 20);
        testFindCurr(a, 8);
        testFindCurr(a, 6);
        testFindCurr(a, 5);
        testFindCurr(a, 1);
    }
    static void test3()// test static functions
    {
        int[] a=new int[]{21, 9, 5, 8, 10};
        Arrays.sort(a);
        out.println(Arrays.toString(a));
        SnakeEating3 sn=new SnakeEating3(a, 2);
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
    }
}

// This class is not 100% working
class SnakeEating1 extends SnakeBase
{
    SnakeEating1(int a[], int q)
    {
        super(a,q);
        Arrays.sort(L);  
        L=reverse(L);
        prefix=prefixSum(L);
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
        int x=Arrays.binarySearch(L, k); // need custom binary search
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
    void query(Scanner sc)
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
    static void test()
    {
        SnakeEating1 sn = new SnakeEating1(new int[]{21, 9, 5, 8, 10}, 2);
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
        sn = new SnakeEating1(new int[]{1, 2, 3, 4, 5}, 2);
        out.println(sn.query(100)==0);
        out.println(sn.query(8)==1);
        out.println(sn.query(6)==2);
        out.println(sn.query(5)==2);
        out.println(sn.query(3)==4);
        out.println(sn.query(2)==4);
        out.println(sn.query(1)==5);
        sn = new SnakeEating1(new int[]{1,4,7,10,13,16,19,22,25}, 2);
        out.println(sn.query(26)==2);
        sn = new SnakeEating1(new int[]{1,2,3,4,5,7,10,13,16,19,22,25}, 2);
        out.println(sn.query(25)==3);
        sn = new SnakeEating1(new int[]{1,2,3,4,5,7,9,11,13,15,17,19,21}, 2);
        out.println(sn.query(22)==3);
        sn = new SnakeEating1(new int[]{1,2,3,4,5,7,9,11,13,15,17,19,20,20,20,21}, 2);
        out.println(sn.query(20)==7);
        out.println(sn.query(7)==12);
        out.println(sn.query(6)==13);
        
        sn = new SnakeEating1(new int[]{15}, 3);
        out.println(sn.query(15)==1);
        out.println(sn.query(16)==0);
        out.println(sn.query(1)==1);       
        sn = new SnakeEating1(new int[]{1000000000, 1500000000}, 3);
        out.println(sn.query(1000000000)==2);  // 2
        out.println(sn.query(1500000000)==1);  // 1
        out.println(sn.query(1500000001)==1);  // 1
        out.println(sn.query(1500000002)==0);  // 0
        out.println(sn.query(1)==2);
        out.println(sn.query(2000000000)==0);
        
        int[] large=new int[100000];
        int v=1000000000;
        for (int i=0; i<large.length; i++) {
            large[i]=v;
            v -=3;
        }
        sn = new SnakeEating1(large, 3);
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
}

class SnakeEating extends SnakeBase{
    // Editorial idea
    SnakeEating(int a[], int q)
    {
        super(a, q);
        init();
    }
    SnakeEating()
    {
        super();
        init();
    }
    void init()
    {
        int n=L.length;
        Arrays.sort(L);
        prefix = new long[n+1];
        prefix[0]=0;
        for (int i=0; i<n; i++)
            prefix[i+1]=prefix[i]+maxK-L[i];
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
        if (p2-p1<=1)
            return p2;
        int mid = (p1+p2)>>1;  // include mid as snakes to eat
        long eat=prefix[p3+1]-prefix[mid]-(long)(p3-mid+1)*(maxK-k);
        //out.println("eat="+eat+" mid="+mid);
        if (eat>mid)
            return binaryEatSnakeAsc(mid, p2, p3, k);
        else 
            return binaryEatSnakeAsc(p1, mid, p3, k);
    }
    // keep track index of snakes to be eaten(p1), and no less than k (p3)
    int query(int k)  // sorted ascending
    {
        int p3=lowerBound(L, k);
        int res=L.length-p3;
        if ( p3<=1)
            return res;
        p3--;  // first snake <k
        if (k-L[p3]>p3)
            return res;
        int p1= binaryEatSnakeAsc(0, p3, p3, k);
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
        return res+p3-p1+1;
    }
    
    static void autotest()
    {
        Random rnd=new Random();
        int N=100000;
        int n=rnd.nextInt(N)+1;
        int[] large=new int[n];
        int v=1000000000;
        for (int i=0; i<large.length; i++) {
            large[i]=rnd.nextInt(v)+1;
        }
        SnakeEating sn2 = new SnakeEating(large, 2);
        SnakeEating3 sn = new SnakeEating3(large, 2);
        for (int i=0; i<n*2; i++)
        {
            int k=rnd.nextInt(v)+1;
            int a1=sn.query(k);
            int a2=sn2.query(k);
            if (a1 !=a2) {
                out.println(i+" not equal k="+ k+" a1="+a1+" a2="+a2);
                out.println(Arrays.toString(large));
                break;
            }
        }    
        out.println("autotest done "+n*2);   
    }
    
    static void test2()
    {
        int[] a=new int[]{1, 2, 3, 4, 5};
        SnakeEating sn = new SnakeEating(a, 2);
        out.println(Arrays.toString(sn.L));
        out.println(Arrays.toString(sn.prefix));
        SnakeEating3 sn3 = new SnakeEating3(a, 2);
        out.println(sn.query(8)==1);
        out.println(sn.query(9)==1);
        out.println(sn.query(10)==0);
        out.println(sn3.query(8)==1);
        out.println(sn3.query(9)==1);
        out.println(sn3.query(10)==0);
        out.println(sn.query(6)==2);
        out.println(sn.query(5)==2);
        out.println(sn.query(3)==4);
        out.println(sn.query(2)==4);
        out.println(sn.query(1)==5);
        
        /*sc = codechef.ContestHelper.getFileScanner("snake-eat-t.txt");
        int N=sc.nextInt(); // 1 ≤ N, Q ≤ 10^5
        int Q=sc.nextInt();
        Integer L[]=ria(N);     // 1 ≤ Li ≤ 10^9
        SnakeEating sn = new SnakeEating(L, Q);
        int k=sc.nextInt();
        out.println(sn.query(k));
        SnakeEating1 sn1 = new SnakeEating1(L, 2);
        out.println(sn1.bruteforce(k));
        out.println(sn1.query(k));*/
    }
    
    
    public static void main(String[] args)
    {      
        //autotest();
        //test2();
        solve(2);
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