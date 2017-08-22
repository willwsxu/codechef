
package codechef;

import static java.lang.System.out;
import java.util.Arrays;

public class FenwickTreeXor  // binary index tree
{
    int bit[];
    int n;
    
    int LSOne(int x)  // least significant bit
    {
        return x & (-x);
    }
    public FenwickTreeXor(int n)
    {
        bit = new int[n+1];
        this.n=n;
    }
    public void add(int x, int v)
    {
        //out.println("add bit "+x+" v="+v);
        while (x<=n) {
            bit[x] ^= v;
            //out.println("bit "+x+" "+bit[x]);
            x += LSOne(x);
        }
    }
    public int get(int x)
    {
        if (x>n)
            return 0;
        int ret=0;
        while (x>0) {
            ret ^= bit[x];
            x -= LSOne(x);
        }
        return ret;
    }
    void print()
    {
        out.println(Arrays.toString(bit));
    }
    
    static void test()
    {
        FenwickTreeXor ft = new FenwickTreeXor(6);
        ft.add(1, 2);
        ft.add(2, 4);
        ft.add(3, 8);
        ft.add(4, 16);
        ft.add(5, 32);
        ft.add(6, 64);
        out.println(ft.get(3)==14);
        out.println(ft.get(6)==126);
    }
}

