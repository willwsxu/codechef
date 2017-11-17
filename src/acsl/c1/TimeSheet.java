
package acsl.c1;

import static acsl.c1.SimilarTriangle.sc;
import static java.lang.System.out;
import java.text.DecimalFormat;
import java.util.Scanner;

public class TimeSheet {
    int timeperiod[]=new int[14]; // start and stop time, for a week, from Sun to Sat
    int timeCode(char code) {
        if (code >='1' && code <='9')
            return code -'0';
        return code -'A'+10;  // code A to H
    }
    double totalPeriods(int day1, int day2)
    {
        int p=0;
        for (int i=day1; i<day2; i++) {
            p+=timeperiod[2*i+1]-timeperiod[2*i];
        }
        return p/2.0;
    }
    int calculatePay(int location){
        double total=totalPeriods(0, 7);
        double cents=0;
        //out.println("hours "+total);
        int rate1=0, rate2=0, threshold=0;
        if (location<200) {//$10.00 per hour with time and a half for any hours over 30
            rate1=1000;
            rate2=rate1+rate1/2;
            threshold=30;
        } else if (location<300) {//$7.50 per hour with double time for hours worked over 40
            rate1=750;
            rate2=2*rate1;
            threshold=40;            
        } else if (location<400) {//$9.25 for the first 20 hours and $10.50 for the rest
            rate1=925;
            rate2=1050;
            threshold=20;                        
        } else if (location<500) {//$13.50 per hour on Sundays (day 1) and Saturdays (day 7) and $6.75 otherwise
            double weekend=timeperiod[1]-timeperiod[0];
            weekend += timeperiod[13]-timeperiod[12];
            weekend /= 2.0;
            cents = weekend*1350 + (total-weekend)*675;
            return (int)cents;
        } else {//$8.00 per hour for the first 6 hours per day and $12.00 per hour after that
            for (int i=0; i<timeperiod.length/2; i++) {
                double hour = (timeperiod[2*i+1]-timeperiod[2*i])/2.0; // 30 min periods
                if (hour>6) {
                    cents += 1200 * (hour-6);
                    hour=6;
                }
                cents += 800*hour;
            }
            return (int)cents;
        }
        // first 3 rate is on weekly basis
        if (total>threshold) {
            cents = (total-threshold)*rate2;
            total = threshold;
        }
        cents += total*rate1;
        return (int)cents;
    }
    String toDollar(int cents)
    {
        DecimalFormat df = new DecimalFormat("#.00");
        StringBuilder sb = new StringBuilder();
        sb.append('$');
        sb.append(df.format(cents/100.0));
        return sb.toString();
    }
    TimeSheet(String[] entry)
    {
        for (int i=1; i<entry.length; i++) {
            timeperiod[i-1]=timeCode(entry[i].charAt(0));
        }
        int cents = calculatePay(Integer.parseInt(entry[0]));
        out.println(toDollar(cents));
    }
    public static void test()
    {
        new TimeSheet(new String[]{"125", "1","7", "5","H", "3","G", "4","E", "2","F", "1","B", "4","A"});
        new TimeSheet(new String[]{"214", "1","H", "5","H", "3","G", "4","E", "2","F", "1","B", "4","G"});
        new TimeSheet(new String[]{"318", "1","H", "3","D", "A","G", "9","E", "3","3", "1","B", "4","A"});
        new TimeSheet(new String[]{"423", "1","7", "5","H", "3","G", "4","E", "2","F", "1","B", "4","A"});
        new TimeSheet(new String[]{"529", "1","G", "5","H", "2","G", "4","E", "2","F", "1","B", "2","A"});
    }
    static Scanner sc=new Scanner(System.in).useDelimiter("\\s*[,\n]\\s*");
    public static void main(String[] args)
    {          
        int T=5;  // 5 lines of input
        while(T-->0) {
            String entry[]=new String[15];
            for (int i=0; i<entry.length; i++)
                entry[i]=sc.next();
            new TimeSheet(entry);
        }
    }
}
/*
150, 1, H, 2, G, 1, F, 2, E, 3, H, B, D, 8, H
211, 3, F, 1, G, 4, H, 6, H, 2, D, 5, H, F, H
349, 1 ,H, 2, D, 9, G, 7, H, 3, F, 1, A, 8, C
424, 1, H, 2, B, D, D, A, H, 9, G, 1, 7, 3, 4
550, 1, G, 3, F, 5, H, 2, D, 4, E, 6, H, 4, H
*/
/*
1.	 150, 1, H, 2, G, 1, F, 2, E, 3, H, B, D, 8, H			1.  $457.50
2.	211, 3, F, 1, G, 4, H, 6, H, 2, D, 5, H, F, H			2.  $285.00
3.	349, 1 ,H, 2, D, 9, G, 7, H, 3, F, 1, A, 8, C			3.  $337.25
4.	424, 1, H, 2, B, D, D, A, H, 9, G, 1, 7, 3, 4			4.  $212.63
5.	550, 1, G, 3, F, 5, H, 2, D, 4, E, 6, H, 4, H			5.  $344.00
*/