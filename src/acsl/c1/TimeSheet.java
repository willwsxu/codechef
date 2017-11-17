
package acsl.c1;

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
        int rate1=0, rate2=0, threshold=0;
        if (location<200) {//$10.00 per hour with time and a half for any hours over 30
            rate1=1000;
            rate2=2*rate1;
            threshold=30;
        } else if (location<300) {//$7.50 per hour with double time for hours worked over 40
            
        } else if (location<400) {//$9.25 for the first 20 hours and $10.50 for the rest
            
        } else if (location<500) {//$13.50 per hour on Sundays (day 1) and Saturdays (day 7) and $6.75 otherwise
        } else {//$8.00 per hour for the first 6 hours per day and $12.00 per hour after that
        }
        if (total>threshold) {
            cents = (total-threshold)*rate2;
            total -= threshold;
        }
        cents += total*rate1;
        return (int)cents;
    }
}
