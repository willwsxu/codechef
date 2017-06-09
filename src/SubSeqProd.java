
import static java.lang.System.out;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.LongStream;
import java.util.stream.Stream;

// Technique meet in the middle, http://www.infoarena.ro/blog/meet-in-the-middle

// any sub sequence, product < K
// use bit for subset, int is enough for N=30
// CHEFCODE medium, https://discuss.codechef.com/questions/98092/chefcode-editorial
class SubSeqProd {
    long val[];
    long lim;
    int  n;
    long prefix[];  // prefix prod fron N to 1
    
    SubSeqProd()
    {
        n=sc.nextInt();  // 1 ≤ N ≤ 30
        // NZEC Runtime exception if nextInt is used for long var lim!!!
        lim=sc.nextLong();  // 1 ≤ K, Ai ≤ 10^18
        val = new long[n];
        for (int j=0; j<n; j++)
            val[j] = sc.nextLong();
        init();
    }
    SubSeqProd(long a[], long k)
    {
        //out.println("SubSeqProd "+k);
        val=a;
        lim=k;
        n=a.length;
        init();
    }
    private void init()
    {
        Arrays.sort(val);
        while (n>0 && val[n-1]>lim) {
            n--;
        }
    }
        
    void recurseProd(List<Long> s, long[] v, int k, long prod)
    {
        if (prod>lim)
            return;
        if ( k==v.length ) {
            s.add(prod);
            //out.println("count "+count+" prod="+prod);
            return;
        }
        recurseProd(s, v, k+1, prod);
        recurseProd(s, v, k+1, safe_multi(prod, v[k]));        
    }
    
    static long completeSearch3(long[] v, int k, long prod, long lim)
    {
        if (prod>lim)
            return 0;
        if ( k==v.length ) {
            //out.println("count "+count+" prod="+prod);
            return 1;
        }
        long ans=completeSearch3(v, k+1, prod, lim);
        ans+=completeSearch3(v, k+1, safe_multi(prod, v[k]), lim);   
        return ans;
    }
    
    // lo and hi ar inclusive
    static int binarysearch(List<Long> s, long p, long x, int lo, int hi) {
        //out.println("p="+p+" lo="+lo+" hi="+hi);
        if (lo>=hi)
            return lo;
        int mid = (lo+hi)/2;
        if (hi-lo==1)
            mid=hi;
        if ( safe_multi(p, s.get(mid))>x)
            return binarysearch(s, p, x, lo, mid-1);
        else
            return binarysearch(s, p, x, mid, hi);
    }
    
    static int upperbound(List<Long> s, long p, long x, int lo, int hi) {
        //out.println("p="+p+" lo="+lo+" hi="+hi);
        if (hi-lo<=1) {
            if (hi<s.size() && safe_multi(p, s.get(hi))<=x) {
                //out.println("hi+1="+hi);
                return hi+1;
            }
            return hi;
        }
        int mid = (lo+hi)/2;
        if ( safe_multi(p, s.get(mid))>x)
            return upperbound(s, p, x, lo, mid);
        else
            return upperbound(s, p, x, mid, hi);
    }
    
    long meetMiddle()
    {
        if (n<=1)
            return n;
        long v1[]=new long[(n+1)/2];
        long v2[]=new long[n/2];
        for (int i=0; i<n; i++) {
            if (i%2==0)
                v1[i/2]=val[i];
            else
                v2[i/2]=val[i];
        }
        List<Long> s1=new ArrayList<>();
        List<Long> s2=new ArrayList<>();
        recurseProd(s1, v1, 0, 1);
        recurseProd(s2, v2, 0, 1);
        Collections.sort(s1);
        Collections.sort(s2);
        if (s1.size()>s2.size()) { // swap so s1 is smaller
            List<Long> temp=s1;
            s1=s2;
            s2=temp;
        }
        //out.println(Arrays.toString(v1)+"-"+s1);
        //out.println(Arrays.toString(v2)+"-"+s2);
        //out.println("two sets size "+s1.size()+" "+s2.size());
        long count =0;
        if (s1.isEmpty())
            return s2.size()-1;  // avoid runtime exeption in next line
        
        for (Long i: s1) {
            if (i==null)
                continue;
            //int pos = binarysearch(s2, i, 0, s2.size()-1);
            int pos = upperbound(s2, i, lim, 0, s2.size()-1);
            //out.println(i+":"+(pos));
            count += pos;
        }
        return count-1;
    }
    long count=0;
    
    long completeSearch(boolean one) {
        val = LongStream.of(val).boxed()
                .sorted(Comparator.reverseOrder())
                .mapToLong(i->i).toArray();
        prefix=new long[n];
        if (n>0) {
            prefix[n-1]=val[n-1];
            for (int i=n-2; i>=0; i--)
                prefix[i] = safe_multi(prefix[i+1], val[i]);
            //out.println(Arrays.toString(prefix));
        }
        //out.println(Arrays.toString(val));
        if (one) {
            completeSearch(0, 1);
            return count-1;
        } else
            return completeSearch2(0, 1)-1;
    }
    
    void completeSearch(int k, long prod)
    {
        if (prod>lim)
            return;
        if ( k==n ) {
            count++;
            //out.println("count "+count+" prod="+prod);
            return;
        }
        completeSearch(k+1, prod);
        long newprod = safe_multi(prod, val[k]);
        completeSearch(k+1, newprod);
    }
    
    long completeSearch2(int k, long prod)
    {
        if (prod>lim)
            return 0;
        else if (prod==lim) {
            //out.println(" prod==lim k="+k+" ans="+(n-k+1));
            return n-k+1;
        } 
        else if ( k==n ) {
            //out.println("count "+count+" prod="+prod);
            if ( prod<=lim)
                return 1;
            return 0;// reachable ?
        } 
        long ans=0;
        if (k+1<n && safe_multi(prod, prefix[k+1])<=lim) {
            ans += 1<<(n-k-1);
            //out.println(" combo k="+k+" ans="+ans);
        } else
            ans += completeSearch2(k+1, prod);
        long newprod = safe_multi(prod, val[k]);
        ans += completeSearch2(k+1, newprod);
        //out.println(" end k="+k+" ans="+ans);
        return ans;
    }
    
    static long safe_multi(long p, long m)
    {
        if (m==0)
            return 0;
        if (Long.MAX_VALUE/m<p)
            return Long.MAX_VALUE;
        return p*m;
    }
    long bruteforce()
    {
        //out.println("bruteforce");
        long count=0;
        outterfor:
        for (long i=1; i< (1<<n); i++) {  // bit for all subset of A
            long prod=1;
            for (int j=0; j<n; j++) {
                if (((1<<j)&i)>0) {
                    long prod2 = prod *val[j]; // check over flow
                    if ( prod2<prod || prod2>lim)
                        continue outterfor;
                    prod = prod2;
                    //out.println("bruteforce i="+i+" j="+j);
                }
            }
            if (prod<=lim)
                count++;
        }   
        return count;
    }
    static void bruteforce(long A[], long K)
    {
        long count = new SubSeqProd(A, K).bruteforce();
        out.println(count);
    }
    static void test1(){
        long A[] = new long[]{7, 2, 3};
        out.println("meet middle "+new SubSeqProd(A, 1).meetMiddle());   
        out.println("meet middle "+new SubSeqProd(A, 2).meetMiddle());   
        out.println("meet middle "+new SubSeqProd(A, 6).meetMiddle()); 
        out.println("meet middle "+new SubSeqProd(A, 42).meetMiddle());              
    }
    static void test2(){
        List<Long> s = Stream.of(10L,9L,9L,8L,7L,6L,5L,4L,3L,2L,1L).sorted().collect(Collectors.toCollection(ArrayList::new));
        out.println(binarysearch(s, 1, 9, 0, s.size()-1));
        out.println(upperbound(s, 1, 9, 0, s.size())==10);
        out.println(upperbound(s, 1, 10, 0, s.size())==11);
        out.println(upperbound(s, 2, 10, 0, s.size())==5);
        out.println(upperbound(s, 2, 12, 0, s.size())==6);
        out.println(upperbound(s, 2, 11, 0, s.size())==5);
        out.println(upperbound(s, 2, 14, 0, s.size()));
    }
    static void test()
    {
        long A[] = new long[]{1, 2, 3};
        bruteforce(A, 4);  // 5
        out.println("complete search "+new SubSeqProd(A, 4).completeSearch(true)); 
        out.println("meet middle "+new SubSeqProd(A, 4).meetMiddle()); 
        bruteforce(A, 6);  // 7
        out.println("complete search "+new SubSeqProd(A, 7).completeSearch(true)); 
        out.println("meet middle "+new SubSeqProd(A, 7).meetMiddle());
        
        A = new long[]{10,9,8,7,6,5,4,3,2,1};
        bruteforce(A, 10);  //10+9+3+3
        out.println("meet middle "+new SubSeqProd(A, 10).meetMiddle());      
        
        A = new long[]{6, 5,4,3,2};
        bruteforce(A, 40);
        out.println("meet middle "+new SubSeqProd(A, 40).meetMiddle());     
        
        A = new long[]{10,9,8,7,6,5,4,3,2};
        bruteforce(A, 72576);
        out.println("meet middle "+new SubSeqProd(A, 72576).meetMiddle());     
        
        A = new long[]{100, 200, 300};
        bruteforce(A, 4);  // 0
        bruteforce(A, 100);// 1
        out.println("meet middle "+new SubSeqProd(A, 100).meetMiddle());  
        bruteforce(A, 200);// 2
        bruteforce(A, 300);// 3
        out.println("meet middle "+new SubSeqProd(A, 300).meetMiddle());   
        
        A = new long[]{100000000000000000L, 200000000000000000L, 4000000000000000000L};
        bruteforce(A, 4);  // 0
        out.println("meet middle "+new SubSeqProd(A, 4).meetMiddle());  
        A = new long[]{10,9,8,7,6,5,4,3,2,1, 11, 12, 13,14,15,16,17,18,19,20,30,29,28,27,26,25,24,23,22,21};
        //bruteforce(A, 4000);//9783
        out.println("meet middle "+new SubSeqProd(A, 4000).meetMiddle());  
        out.println("completeSearch3 "+(completeSearch3(A, 0, 1, 4000)-1)); 
        out.println("complete search "+new SubSeqProd(A, 4000).completeSearch(true));    
        
        out.println("meet middle "+new SubSeqProd(A, 10000000000000000L).meetMiddle());    // 508131459
        //out.println("completeSearch3 "+(completeSearch3(A, 0, 1, 10000000000000000L)-1));  // 508131459
        
        out.println("meet middle "+new SubSeqProd(A, 100000000000000000L).meetMiddle());    // 642797745
        //out.println("completeSearch3 "+(completeSearch3(A, 0, 1, 100000000000000000L)-1));  // 642797745
        
        out.println("meet middle "+new SubSeqProd(A, 1000000000000000000L).meetMiddle());    // 767476931
        out.println("completeSearch3 "+(completeSearch3(A, 0, 1, 1000000000000000000L)-1));  // 767476931
        out.println("complete search "+new SubSeqProd(A, 1000000000000000000L).completeSearch(true));    
        out.println("complete search2 "+new SubSeqProd(A, 1000000000000000000L).completeSearch(false));    
        
        A = new long[]{10,9,8,7,6,5,4,3,2,31, 11, 12, 13,14,15,16,17,18,19,20,30,29,28,27,26,25,24,23,22,21};
        out.println("complete search "+new SubSeqProd(A, 1000000000000000000L).completeSearch(false));     // 672295666
        out.println("meet middle "+new SubSeqProd(A, 1000000000000000000L).meetMiddle());      // 672295666
        //out.println("completeSearch3 "+(completeSearch3(A, 0, 1, 1000000000000000000L)-1));  // 672295666
        
        A = new long[]{10,9,8,7,6,5,4,3,2,3, 11, 12, 13,14,15,16,17,18,19,20,19,19,18,17,16,15,14,13,12,11};
        //out.println("complete search "+new SubSeqProd(A, 2000000000000000000L).completeSearch(false));     // 908224121
        out.println("meet middle "+new SubSeqProd(A, 2000000000000000000L).meetMiddle());     //908224121
        //out.println("completeSearch3 "+(completeSearch3(A, 0, 1, 2000000000000000000L)-1)); //909565081
        
        A = new long[]{10,9,8,7,6,5,4,3,2,3, 11, 12, 13,4,5,6,7,8,9,10,9,9,8,7,16,15,14,13,12,11};
        //out.println("complete search "+new SubSeqProd(A, 2000000000000000000L).completeSearch(false));     // 1051641446
        out.println("meet middle "+new SubSeqProd(A, 2000000000000000000L).meetMiddle()); //1051641446
       
    }
    static Scanner sc = new Scanner(System.in);
    public static void main(String[] args)
    {      
        test();
        //out.println(new SubSeqProd().meetMiddle());
        //out.println(new SubSeqProd().completeSearch(true));
    }
}

/*
5 400000000000
6 5 4 3 2
*/