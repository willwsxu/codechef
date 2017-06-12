package longContests.june17;


import static java.lang.System.out;
import java.util.Scanner;


class CoinRank {
    CoinRank()
    {
        this(sc.nextInt(), sc.nextInt());//1 ≤ U, V ≤ 10^9
    }
    CoinRank(int x, int y)
    {
        long n=x+y;
        long total=(1+n)*n/2;
        out.println(total+x+1);
    }
        
    static Scanner sc = new Scanner(System.in);
    public static void main(String[] args)
    {      
        int T=sc.nextInt(); // 1 ≤ T≤ 100
        while (T-->0)
            new CoinRank();
    }
}
