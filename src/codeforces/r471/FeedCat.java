package codeforces.r471;

/*
 * 955A
 * The cat's current hunger level is H points, moreover each minute without food increases his hunger by D points
 * bun costs C roubles and decreases hunger by N points
 * special 20% discount for buns starting from 20:00 to 23:59
 * Determine the minimum amount of money Andrew has to spend in order to feed his cat
 *
 * The first line contains two integers hh and mm (00 ≤ hh ≤ 23, 00 ≤ mm ≤ 59) — the time of Andrew's awakening.
 * The second line contains four integers H, D, C and N (1 ≤ H ≤ 105, 1 ≤ D, C, N ≤ 102).
 */

import codechef.IOR;

public class FeedCat {
    int buns(int H, int N)
    {
        return (H+N-1)/N;
    }
    double minCost(int minutes, int H, int D, int C, int N) {
        int night=20*60;
        double costNow= buns(H, N)*C;
        double costDiscount=costNow;
        if (minutes<night) {
            costDiscount = buns(H+D*(night-minutes), N)*C*.8;
        }
        else
            costDiscount *= 0.8;
        return costNow>costDiscount?costDiscount:costNow;
    }
    
    public static void main(String[] args)
    {      
        int hr=IOR.ni();
        int min=IOR.ni();
        int H=IOR.ni();
        int D=IOR.ni();
        int C=IOR.ni();
        int N=IOR.ni();
        System.out.println(new FeedCat().minCost(hr*60+min, H, D, C, N));
        //System.out.println(new FeedCat().minCost(19*60, 255, 1, 100, 1));
        //System.out.println(new FeedCat().minCost(17*60+41, 1000, 6, 15, 11));
    }
}
