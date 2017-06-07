
import static java.lang.System.out;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.LongStream;

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
        lim=sc.nextInt();  // 1 ≤ K, Ai ≤ 10^18
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
    
    // todo: binary mid adjusting
    int binarysearch(List<Long> s, long p, int lo, int hi) {
        //out.println("p="+p+" lo="+lo+" hi="+hi);
        if (lo>=hi)
            return lo;
        int mid = (lo+hi)/2;
        if (hi-lo==1)
            mid=hi;
        if ( safe_multi(p, s.get(mid))>lim)
            return binarysearch(s, p, lo, mid-1);
        else
            return binarysearch(s, p, mid, hi);
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
        for (Long i: s1) {
            int pos = binarysearch(s2, i, 0, s2.size()-1);
            //out.println(i+":"+(pos));
            if (pos <s2.size() && safe_multi(s2.get(pos), i)>lim)
                pos--;
            //out.println(i+":"+(pos));
            count += ++pos;
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
                prefix[i] = multiply(prefix[i+1], val[i]);
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
        long newprod = multiply(prod, val[k]);
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
            if ( prod<=k)
                return 1;
            return 0;// reachable ?
        } 
        long ans=0;
        if (k+1<n && multiply(prod, prefix[k+1])<=k) {
            ans += 1<<(n-k-1);
            //out.println(" combo k="+k+" ans="+ans);
        } else
            ans += completeSearch2(k+1, prod);
        long newprod = multiply(prod, val[k]);
        ans += completeSearch2(k+1, newprod);
        //out.println(" end k="+k+" ans="+ans);
        return ans;
    }
    
    long seqProduct(int ind, int num) // sequential prod
    {
        long prod=1;
        for (int i=ind; i<ind+num; i++) {
            long p=prod*val[i];            
            if ( p<prod || p>lim)
                return Long.MAX_VALUE;
            prod=p;
        }
        return prod;
    }
    static long multiply(long p, long m)
    {
        long p2=p*m;  
        if ( p2<p )
            return Long.MAX_VALUE;
        return p2;
    }
    static long safe_multi(long p, long m)
    {
        if (Long.MAX_VALUE/m<p)
            return Long.MAX_VALUE;
        return p*m;
    }
    static long aChooseb(int a, int b)
    {
        if (a<b)
            return 0; // error
        if (a-b<b)  // 5C3 = 5C2
            b=a-b;
        long p=1;
        int bb=b;
        for (int i=0; i<b; i++) {
            p *=(a-i);
            while (bb>1 && p%bb==0) {
                p /= bb--;
            }
        }
        while (bb>1 && p%bb==0) {
            p /= bb--;
        }
        return p;
    }
    static void testChoose()
    {
        out.println(aChooseb(5,1));
        out.println(aChooseb(5,2));
        out.println(aChooseb(5,3));
        out.println(aChooseb(5,4));
        out.println(aChooseb(5,5));
        out.println(aChooseb(30,15));
    }
    // lo inclusive, hi exclusive
    int binarysearch(long p, int lo, int hi) {
        if (lo>=hi-1)
            return lo;
        int mid = (lo+hi)/2;
        if ( multiply(p, val[mid])>lim)
            return binarysearch(p, lo, mid);
        else
            return binarysearch(p, mid, hi);
    }
    int findMaxMulti(int n) {  // max number to multiply   
        long p=1;
        for (int i=0; i<n; i++) {
            long p2 = p*val[i];
            if (p2>lim || p2<p)
                return i;
            p=p2;
        }
        return n;
    }
    // 1 2 3 4 5 6 7 8
    // multi 3,
    //long dp[][][][];
    long recurse(int start, int end, int multi, long prod, int multimax)
    {
        //out.print("-recur "+start+" end "+end+" multiply times "+multi+" prod "+prod);
        long newProd=multiply(prod, val[start]);
        if (newProd>lim) {
            //out.println(" count=0, newProd "+newProd);
            return 0;
        }
        else if ( multi==1) {
            int found=binarysearch(prod, start, end);
            long count=found-start;
            if ( multiply(prod, val[found])<=lim)
                count++;
            //out.println(" binarysearch "+found+" count "+count);
            //dp[start][end][multi]=count;
            return count;
        //} else if (dp[start][end][multi][multimax]>0) {
            //out.println(" dp "+" count "+dp[start][end][multi][multimax]);
            //return dp[start][end][multi][multimax];
        } else {
            long count=0;
            for (int i=start+1; i<=end-multi+1; i++) {
                count += recurse(i, end, multi-1, newProd, multimax);
                if (multi==2)  // let binary search do the work when only one to multiply
                    break;
            }
            //dp[start][end][multi][multimax]=count;
            //out.println(" recurse count "+count);
            return count;
        }
    }
    long solve()
    {
        if (n<=0)
            return 0;
        //if (end<10)
        //    return bruteforce(end+1);
        long total=n;
        int maxMulti=findMaxMulti(n);
        //out.println(" numbers "+n+" multi "+maxMulti);
        if ( maxMulti<=1 )
            return total;
        else if (maxMulti>=n)
            return (1<<n)-1;  // watch out for precedence
        //dp=new long[n][n+1][maxMulti+1][maxMulti+1];
        // 2 3 4 5 6 maxMulti=3
        // recurse maxMulti=2, 0 5, 1 5, 2 5, 3 5
        // recurse maxMulti=3, 0 1 5, 0 2 5, 0 3 5; 1 2 5, 1 3 5; 2 3 5
        for (int i=2; i<=maxMulti; i++) {
            long largest=seqProduct(n-i, i);
            if ( largest <= lim) {
                long count = aChooseb(n, i);
                //out.println(n+" aChooseb "+i+" = "+count);
                total += count;
                continue;
            }
            for (int j=0; j<=n-i; j++) {
                long smallest=seqProduct(j, i);
                if ( smallest > lim) {
                    //out.println("too big "+smallest+" multi "+i+" j="+j);
                    break;
                }
                total += recurse(j, n, i, 1, i);
            }
        }
        return total;
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
    static void test()
    {
        testChoose();
        long A[] = new long[]{1, 2, 3};
        bruteforce(A, 4);  // 5
        out.println("new "+new SubSeqProd(A, 4).solve());
        out.println("complete search "+new SubSeqProd(A, 4).completeSearch(true)); 
        //out.println("complete search2 "+new SubSeqProd(A, 4).completeSearch(false));
        out.println("meet middle "+new SubSeqProd(A, 4).meetMiddle()); 
        bruteforce(A, 6);  // 7
        out.println("new "+new SubSeqProd(A, 7).solve());
        out.println("complete search "+new SubSeqProd(A, 7).completeSearch(true)); 
        //out.println("complete search2 "+new SubSeqProd(A, 7).completeSearch(false)); 
        out.println("meet middle "+new SubSeqProd(A, 7).meetMiddle());
        
        A = new long[]{10,9,8,7,6,5,4,3,2,1};
        bruteforce(A, 10);  //10+9+3+3
        out.println("new "+new SubSeqProd(A, 10).solve());  
        //out.println("complete search "+new SubSeqProd(A, 10).completeSearch(false)); 
        out.println("meet middle "+new SubSeqProd(A, 10).meetMiddle());      
        
        A = new long[]{6, 5,4,3,2};
        bruteforce(A, 40);
        out.println("new "+new SubSeqProd(A, 40).solve());
        //out.println("complete search "+new SubSeqProd(A, 40).completeSearch(false)); 
        out.println("meet middle "+new SubSeqProd(A, 40).meetMiddle());     
        
        A = new long[]{10,9,8,7,6,5,4,3,2};
        bruteforce(A, 72576);
        out.println("new "+new SubSeqProd(A, 72576).solve());
        //out.println("complete search "+new SubSeqProd(A, 72576).completeSearch(false)); 
        out.println("meet middle "+new SubSeqProd(A, 72576).meetMiddle());     
        
        A = new long[]{100, 200, 300};
        bruteforce(A, 4);  // 0
        bruteforce(A, 100);// 1
        out.println("new "+new SubSeqProd(A, 100).solve());
        //out.println("complete search "+new SubSeqProd(A, 100).completeSearch(false));  
        out.println("meet middle "+new SubSeqProd(A, 100).meetMiddle());  
        bruteforce(A, 200);// 2
        bruteforce(A, 300);// 3
        out.println("new "+new SubSeqProd(A, 300).solve());
        //out.println("complete search "+new SubSeqProd(A, 300).completeSearch(false)); 
        out.println("meet middle "+new SubSeqProd(A, 300).meetMiddle());   
        
        A = new long[]{100000000000000000L, 200000000000000000L, 4000000000000000000L};
        bruteforce(A, 4);  // 0
        out.println("new "+new SubSeqProd(A, 4).solve());
        //out.println("complete search "+new SubSeqProd(A, 4).completeSearch(false));  
        out.println("meet middle "+new SubSeqProd(A, 4).meetMiddle());  
        A = new long[]{10,9,8,7,6,5,4,3,2,1, 11, 12, 13,14,15,16,17,18,19,20,30,29,28,27,26,25,24,23,22,21};
        //bruteforce(A, 4000);//9783
        out.println("new "+new SubSeqProd(A, 4000).solve());  // 9783
        //out.println("complete search "+new SubSeqProd(A, 4000).completeSearch(false));   
        out.println("meet middle "+new SubSeqProd(A, 4000).meetMiddle());  
        out.println("completeSearch3 "+(completeSearch3(A, 0, 1, 4000)-1)); 
        
        out.println("meet middle "+new SubSeqProd(A, 10000000000000000L).meetMiddle());    // 508131459
        out.println("completeSearch3 "+(completeSearch3(A, 0, 1, 10000000000000000L)-1));  // 508131459
        
        out.println("meet middle "+new SubSeqProd(A, 100000000000000000L).meetMiddle());    // 642958481
        out.println("completeSearch3 "+(completeSearch3(A, 0, 1, 100000000000000000L)-1));  // 642797745
        /*
        A = new long[]{10,9,8,7,6,5,4,3,2,31, 11, 12, 13,14,15,16,17,18,19,20,30,29,28,27,26,25,24,23,22,21};
        //out.println("new "+new SubSeqProd(A, 1000000000000000000L).solve());  // 672779816
        //out.println("complete search "+new SubSeqProd(A, 1000000000000000000L).completeSearch(false));     // 672295666
        out.println("meet middle "+new SubSeqProd(A, 1000000000000000000L).meetMiddle());    // 677351272
        out.println("completeSearch3 "+(completeSearch3(A, 0, 1, 1000000000000000000L)-1));  // 672295666
        
        A = new long[]{10,9,8,7,6,5,4,3,2,3, 11, 12, 13,14,15,16,17,18,19,20,19,19,18,17,16,15,14,13,12,11};
        //out.println("new "+new SubSeqProd(A, 2000000000000000000L).solve());  // 909178996
        //out.println("complete search "+new SubSeqProd(A, 2000000000000000000L).completeSearch(false));     // 908224121
        out.println("meet middle "+new SubSeqProd(A, 2000000000000000000L).meetMiddle());   //911970255
        out.println("completeSearch3 "+(completeSearch3(A, 0, 1, 2000000000000000000L)-1)); //909565081
        
        A = new long[]{10,9,8,7,6,5,4,3,2,3, 11, 12, 13,4,5,6,7,8,9,10,9,9,8,7,16,15,14,13,12,11};
        //out.println("new "+new SubSeqProd(A, 2000000000000000000L).solve());  // 1051752556
        //out.println("complete search "+new SubSeqProd(A, 2000000000000000000L).completeSearch(false));     // 1051641446
        out.println("meet middle "+new SubSeqProd(A, 2000000000000000000L).meetMiddle()); //1051674203
        */
    }
    static Scanner sc = new Scanner(System.in);
    public static void main(String[] args)
    {      
        //test();
        out.println(new SubSeqProd().meetMiddle());
        //out.println(new SubSeqProd().completeSearch(true));
    }
}

/*
5 40
6 5 4 3 2
*/