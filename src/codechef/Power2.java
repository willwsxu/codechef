
package codechef;

// effieicnt calc of power 2, with modulo
public class Power2
{
    static final int  MOD=1000000007;    // 10^9 + 7

    private static int power2[]=new int[300000+5];
    static
    {
        power2[0]=1;
        for (int i=1; i< power2.length; i++) {
            power2[i] = (2 * power2[i-1])%MOD;
        }
        //out.println(Arrays.toString(power2));
    }
    public static int get(int i) {
        return power2[i];
    }
}
