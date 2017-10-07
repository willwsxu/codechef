package codeforces.vkcup2017r1;




import static java.lang.Math.abs;
import static java.lang.System.out;
import java.util.Scanner;

// 795A
// n people, some pupil, some adult. separate into groups. a group must have 1 adult
// if a group has x people, cost of tickets=c1+c2(x-1)^2
// optimize grouping to find minimal price
// IDEA: binary search, local minimum may not be the best answer as we may have picked the wrong half
//  need to verify if there is no better solution
public class AmusementPark {
    
    int adult;
    int pupil;
    int n;      // 1 ≤ n ≤ 200 000
    long c1;    // 1 ≤ c1, c2 ≤ 10^7
    long c2;    // use long type to avoid overflow in calculation
    long minCost=0;
    
    long formula(int x)
    {
        return c1+c2*(x-1)*(x-1);
    }
    long calc(int group)
    {
        if (group==1)
        {
            return formula(n);
        }
        int avg = n/group;
        int rem = n%group;
        long cost = formula(avg);
        cost *= (group-rem);
        return cost + rem*formula(avg+1);
    }
    long cost(int lo, int hi, long lowCost, long hiCost)
    {
        if (abs(lo-hi)<=1) {
            //out.println("first "+lo+" second "+hi+" low clost "+lowCost+" hi cost "+hiCost);
            // check if answer is best, if not do binary search again or just try one by one
            if (lo>1) {
                long c=calc(lo-1);
                if (c<lowCost)
                    return cost(lo-1, 1, c, calc(1));
            }
            if (lo<adult) {
                long c=calc(lo+1);
                if (c<lowCost)
                    return cost(lo+1, adult, c, calc(adult));                
            }
            return lowCost;
        }
        int mid = (lo+hi)/2;
        long midCost = calc(mid);
        //out.println("first "+lo+" second "+hi+ " mid cost "+midCost+" low clost "+lowCost+" hi cost "+hiCost);
        if (midCost<lowCost)
            return cost(mid, lo, midCost, lowCost);
        else if ( midCost > lowCost )
            return cost(lo, mid, lowCost, midCost);
        else {
            //out.println("bad mid cost "+midCost+" low clost "+lowCost+" hi cost "+hiCost);
            return midCost;
        }
    }
    AmusementPark(int a, int p, int c1, int c2)
    {
        adult = a;
        pupil = p;
        n = a+p;
        this.c1 = c1;
        this.c2 = c2;
        long cost1 = calc(1);
        long cost2 = calc(a);
        if (cost1>cost2)
            cost1=cost(a, 1, cost2, cost1);
        else if (cost1<cost2)
            cost1=cost(1, a, cost1, cost2);
        out.println(cost1);
    }
    
    static Scanner scan = new Scanner(System.in);
    static void judge()
    {        
        int n = scan.nextInt();  // between 1 and 20000
        int c1 = scan.nextInt();  // between 1 and 10^7
        int c2 = scan.nextInt();  // between 1 and 10^7
        String people = scan.nextLine();
        if (people.isEmpty())
            people = scan.nextLine();
        int pupil=0;
        int adult=0;
        for(int i=0; i<people.length(); i++)
        {
            if (people.charAt(i)=='1')
                adult++;
            else
                pupil++;
        }
        new AmusementPark(adult, pupil, c1, c2);
    }
    static void test()
    {
        new AmusementPark(9559, 10442, 1000, 100); // 9333800
        new AmusementPark(9, 1, 2, 2); // 20
    }
    public static void main(String[] args)
    {
        judge();
    }
}
/*
10 2 2
1111101111
ans 20
*/