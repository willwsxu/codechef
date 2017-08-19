
import static java.lang.System.out;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


class FibonacciArray {
    List<List<Integer>> fib1=new ArrayList<>();
    int index=0;
    int len=0;
    long total=0;
    static final int MOD=1000000007;
    
    FibonacciArray(int N)
    {
        int[] a=ria(N);
        int s=a.length>1?a[1]:0;
        int t=a.length>2?a[2]:0;
        out.println(solve(0, a[0],s,t,a));
    }
    FibonacciArray(List<Integer> arr)
    {
        len=arr.size();
        fib1.add(arr);
        if (len>2) {
            transform(arr, fib1);
            index++;
        }
        total +=fib1.size();
    }
    void transform(List<Integer> arr, List<List<Integer>> fib) {
        int count=0;
        while (arr.get(index)>count && arr.get(index+1)>count) {
            count++;
            List<Integer> d2=new ArrayList<>(len);
            for (int i=0; i<index; i++)
                d2.add(arr.get(i));
            d2.add(arr.get(index)-count);
            d2.add(arr.get(index+1)-count);
            if ( index+2<len)
                d2.add(arr.get(index+2)+count);
            else
                d2.add(count);
            for (int i=index+3; i<len; i++)
                d2.add(arr.get(i));
            fib.add(d2);
            out.println("ind="+index+" len="+len+d2);
        }        
    }
    long solve()
    {
        if (len==1)
            return 1;
        while (index+1<len) {
            List<List<Integer>> fib2=new ArrayList<>();
            for (int i=0; i<fib1.size(); i++) {
                transform(fib1.get(i), fib2);
            }
            if (fib2.isEmpty())
                break;
            total += fib2.size();
            total %= MOD;
            fib1.clear();
            fib1=fib2;
            len=fib1.get(0).size();
            index++;
            //out.println("ind="+index+" len="+len+" total="+total);
        }
        return total;
    }
    static long solve(int index, int first, int second, int third, int arr[]) {
        if (first==0 && second==0 && third==0) {
            //out.println("index "+index);
            return 1;
        }
        int newThird=0;
        if (index+3<arr.length)
            newThird = arr[index+3];
        //out.println("index="+index+" #1="+first+" #2="+second+" #3="+third);
        long sum= solve(index+1, second, third, newThird, arr);
        while (first>0 && second>0) {
            first--;
            second--;
            third++;
            sum += solve(index+1, second, third, newThird, arr);
        }
        //out.println("index="+index+" sum="+sum);
        return sum;
    }
    static void test()
    {
        List<Integer> arr=new ArrayList<Integer>();
        arr.add(2);
        out.println(new FibonacciArray(arr).solve());
        arr.add(3);
        out.println(new FibonacciArray(arr).solve());
        arr.clear();
        for (int i=0; i<8; i++)
            arr.add(4);
        out.println(new FibonacciArray(arr).solve());
    }
    static void test2() {
        /*List<Integer> arr=new ArrayList<Integer>();
        for (int i=0; i<3; i++)
            arr.add(4);
        out.println(new FibonacciArray(arr).solve());
        out.println(solve(0, 1,0,0,new int[]{1}));
        out.println(solve(0, 1,1,0,new int[]{1,1}));
        out.println(solve(0, 2,3,0,new int[]{2,3}));
        out.println(solve(0, 3,4,0,new int[]{3,4}));
        out.println(solve(0, 4,4,4,new int[]{4,4,4}));*/
        out.println(solve(0, 2,3,1,new int[]{2,3,1}));
        out.println(solve(0, 0, 1,2,new int[]{0,1,2,3}));
        out.println(solve(0, 2,2,0,new int[]{2,2}));
    }
        
    public static List<Integer> riL(int N) { // read int array list
        List<Integer> L=new ArrayList<>();
        for (int i=0; i<N; i++)
            L.add(sc.nextInt());
        return L;
    }
    public static int[] ria(int N) { // read int array
        int L[]=new int[N];
        for (int i=0; i<N; i++)
            L[i]=sc.nextInt();
        return L;
    }
    static Scanner sc = new Scanner(System.in);
    static void judge()
    {
        int T=sc.nextInt(); // 1 ≤ T ≤ 5
        for (int i=0; i<T; i++) { 
            int n=sc.nextInt();// array size
            new FibonacciArray(n);            
        }        
    }
    public static void main(String[] args)
    {    
        judge();
    }
}
