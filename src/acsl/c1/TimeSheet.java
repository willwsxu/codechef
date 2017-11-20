
package acsl.c1;

import static java.lang.System.out;
import java.text.DecimalFormat;
import java.util.Scanner;

// Time sheet with location and start/stop time each day from Sun to Sat, a week
// Time code is 30 min period
// calculate pay per rule specific to location
// use double for time
// need to round cents correctly
public class TimeSheet {
    int timeperiod[]=new int[14]; // start and stop time, for a week, from Sun to Sat
    int timeCode(char code) {  // convert time code to int
        if (code >='1' && code <='9')
            return code -'0';
        return code -'A'+10;  // code A to H
    }
    double totalHours(int day1, int day2)  // hours from day1 (inclusive) to day2 (exclusive)
    {
        int p=0;
        for (int i=day1; i<day2; i++) {
            p+=timeperiod[2*i+1]-timeperiod[2*i];
        }
        return p/2.0;  // each period is .5 hours
    }
    double calculatePay(int location){
        double total=totalHours(0, 7);  // total hours per week
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
            //out.println("weekend hours "+weekend+" pay "+cents);
            return cents;
        } else {//$8.00 per hour for the first 6 hours per day and $12.00 per hour after that
            for (int i=0; i<timeperiod.length/2; i++) {
                double hour = (timeperiod[2*i+1]-timeperiod[2*i])/2.0; // 30 min periods
                if (hour>6) {
                    cents += 1200 * (hour-6);
                    hour=6;
                }
                cents += 800*hour;
            }
            return cents;
        }
        // first 3 rate is on weekly basis
        if (total>threshold) {
            cents = (total-threshold)*rate2;
            total = threshold;
        }
        cents += total*rate1;
        return cents;
    }
    // rount to nearest cent, 1 dp precision
    int round(double cents) {
        cents *= 10;  // convert cent to 10th cent
        return ((int)cents+5)/10;  // .5 or up to 1
    }
    // format cents as dollar with 2 dp
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
        // first elem is location, rest is start and stop period for 7 days
        for (int i=1; i<entry.length; i++) {
            timeperiod[i-1]=timeCode(entry[i].charAt(0));  // convert time code to int for easy calculation of time
        }
        int cents = round(calculatePay(Integer.parseInt(entry[0])));  // calculate pay, round to cents
        out.println(toDollar(cents)); // format output
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