
import static java.lang.System.out;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.List;
import java.util.Scanner;


class DivisorTree {
    
    static int tree(Divisors div)
    {
        if (div.numDiv()<=2) {
            if (div.value==1)
                return 1;
            return div.numDiv();
        }
        
        List<Divisors> divs = div.computeNext();
        int max=3;
        for (int i=0; i<divs.size(); i++) {   
            int m = tree(divs.get(i));
            if (m>max)
                max = m;
        }
        //out.println(max+"+"+div.numDiv());
        return max+div.numDiv();
    }
    static Scanner sc = new Scanner(System.in);
    public static void main(String[] args)
    {
        long A = sc.nextLong();  // between 1 and B
        long B = sc.nextLong();   // 1 ≤ B ≤ 10^12, B-A<=10^5
        
        int score=0;
        for (long i=A; i<=B; i++) {
            Divisors div = new Divisors(i);
            int s = tree(div);
            if (s>2)
                s--;
            score += s;
        }
        out.println(score);
    }
}

class PrimeSieve
{
    List<Integer> primes= new ArrayList<>(10000);
    PrimeSieve(int num)
    {
        BitSet bs = new BitSet(num);
        for (int i=2; i<num; i++) {
            if (!bs.get(i)) {
                for (long j=(long)i*i; j<(long)num; j+=i)  // i*i can overflow int
                    bs.set((int)j);  // cross out multiple of i
                primes.add(i);
            }
        }
    }
    static void test()
    {
        PrimeSieve ps = new PrimeSieve(100);
        out.println(ps.primes);
        out.println("100 primes #"+ps.primes.size());
        ps = new PrimeSieve(1000000);
        out.println("1000000 primes #"+ps.primes.size());
    }
}

class Divisors
{
    class PrimeFactor
    {
        long base;
        int power;
        PrimeFactor(long p, int c)
        {
            base=p; power=c;
        }
    }
    List<PrimeFactor> pfs = new ArrayList<>();
    static PrimeSieve primes = new PrimeSieve(1000000);
    long value;
    Divisors(long num)
    {
        value=num;
        for (int i=0; i<primes.primes.size(); i++) {
            long pf = primes.primes.get(i);
            if (pf*pf>num)
                break;
            int power=0;
            while (num%pf==0) {
                power++;
                num /= pf;
            }
            if (power>0)
                pfs.add(new PrimeFactor(pf, power));
        }
        if (num<value && num>1)
            pfs.add(new PrimeFactor(num, 1));
    }
    Divisors(List<PrimeFactor> pfs)
    {
        this.pfs=pfs;
        value = value();
    }
    
    long value()
    {
        if (pfs.isEmpty())
            return value;  // a prime number
        long ans=1;
        for (int i=0; i<pfs.size(); i++) {
            PrimeFactor pf = pfs.get(i);
            for (int j=0; j<pf.power; j++)
                ans *= pf.base;
        }
        return ans;
    }
    List<Divisors> computeNext()
    {
        List<Divisors> divs = new ArrayList<>();
        for (int i=0; i<pfs.size(); i++) {
            List<PrimeFactor> p = new ArrayList<>();
            for (int j=0; j<pfs.size(); j++)
            {
                PrimeFactor pf = pfs.get(j);
                if (j==i) {
                    if ( pf.power>1)
                        p.add(new PrimeFactor(pf.base, pf.power-1));
                }
                else
                    p.add(new PrimeFactor(pf.base, pf.power));
            }
            divs.add(new Divisors(p));
        }
        return divs;
    }
    int numDiv()
    {
        if (pfs.isEmpty()) {
            if (value==1)
                return 1;
            return 2;  // a prime number
        }
        
        int ans=1;
        for (int i=0; i<pfs.size(); i++) {
            PrimeFactor pf = pfs.get(i);
            ans *= (pf.power+1);
        }
        return ans;
    }
    void printFactors()
    {
        for (int i=0; i<pfs.size(); i++) {
            PrimeFactor pf = pfs.get(i);
            out.print("("+pf.base+","+pf.power+")");
        }       
        out.println();
    }
    static void test1(long num)
    {
        Divisors div = new Divisors(num);
        out.println(div.value()+" has divisors "+div.numDiv());
        div.printFactors();        
    }
    static void test()
    {
        test1(30);
        test1(9);  
        test1(12);       
        test1(13);     
        test1(100); 
        test1(1000000000000L);
        test1(999999999999L);
        Divisors d = new Divisors(12);
        List<Divisors> divs = d.computeNext();
        for (int i=0; i<divs.size(); i++) {   
            Divisors div = divs.get(i);
            out.println(div.value()+" has divisors "+div.numDiv());
            div.printFactors();        
        }
    }
}
