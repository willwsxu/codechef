
import static java.lang.System.out;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.List;
import java.util.Scanner;


class DivisorTree {
    
    static Scanner sc = new Scanner(System.in);
    public static void main(String[] args)
    {
        Divisors.test();
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
    int numDiv()
    {
        if (pfs.isEmpty())
            return 1;  // a prime number
        
        int ans=1;
        for (int i=0; i<pfs.size(); i++) {
            PrimeFactor pf = pfs.get(i);
            ans *= (pf.power+1);
        }
        return ans-1;
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
        test1(13);     
        test1(100); 
        test1(1000000000000L);
        test1(999999999999L);
    }
}
