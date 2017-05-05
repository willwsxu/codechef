
package codechef;

import static java.lang.System.out;
import java.util.ArrayList;
import java.util.List;


public class Divisors
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
    public Divisors(long num)
    {
        value=num;
        for (int i=0; i<primes.primes().size(); i++) {
            long pf = primes.primes().get(i);
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
    public List<Divisors> computeNext()
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
    public int numDiv()
{
        if (value==1)
            return 1;
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
    public static void test1(long num)
    {
        Divisors div = new Divisors(num);
        out.println(div.value()+" has divisors "+div.numDiv());
        div.printFactors();        
    }
    public static void test(long A, long B)
    {
        for (long i=A; i<=B; i++) {
            Divisors div = new Divisors(i);
            div.printFactors();    
        }
    }
    public static void test()
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
