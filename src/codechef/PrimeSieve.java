
package codechef;

import static java.lang.System.out;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;


public class PrimeSieve
{
    private List<Integer> primes= new ArrayList<>(10000);
    public PrimeSieve(int num)
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
    public List<Integer> primes()
    {
        return primes;
    }
    
    public static void test()
    {
        PrimeSieve ps = new PrimeSieve(100);
        out.println(ps.primes);
        out.println("100 primes #"+ps.primes.size());
        ps = new PrimeSieve(1000000);
        out.println("1000000 primes #"+ps.primes.size());
    }
}
