package ChallengeMar17;


import static java.lang.Integer.min;
import static java.lang.System.out;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// idea from editorial
// binary search
class Mar17CookingSchedule {
    
    Mar17CookingSchedule(String sched, int k)
    {
        int cost10=0, cost01=0;
        for (int i=0; i<sched.length(); i++) {
            if (i%2==0) {
                if (sched.charAt(i)=='0')
                    cost10++; // convert to 101010...
                else
                    cost01++; // convert to 010101...
            } else {
                if (sched.charAt(i)=='1')
                    cost10++; // convert to 101010...
                else
                    cost01++; // convert to 010101...                
            }            
        }
        if (min(cost10, cost01)<=k) {
            out.println(1);
            return;
        }
        int lo=2, hi=0;
        int run_len=1;
        List<Integer> runs = new ArrayList<>(1000);
        for (int i=1; i<sched.length(); i++) {
            if ( sched.charAt(i) != sched.charAt(i-1)) {
                runs.add(run_len);  // add a block len to vector
                if ( run_len>hi)
                    hi = run_len;
                run_len=1;
            }
            else
                run_len++;
        }
        runs.add(run_len);
        if ( run_len>hi )
            hi = run_len;
        //out.println(runs);
        int res = hi;
        while (lo<=hi) { // find the smallest block size via BST
            int mid = (lo+hi)/2;
            int cost=0;
            for (int len: runs)
                cost += len/(mid+1);// 111111 ->110101, not 100100
            if (cost<=k) {
                res = min(res,mid);
                hi = mid-1;
            } else {
                lo = mid +1;
            }
        }
        out.println(res);
    }
    
    static Scanner scan = new Scanner(System.in);
    static void autoTest()
    {        
        int TC = scan.nextInt();  // between 1 and 11,000
        for (int i=0; i<TC; i++) {
            int N = scan.nextInt();  // between 1 and 10^6
            int K = scan.nextInt();  // between 1 and N
            String schedule=scan.nextLine();
            if (schedule.isEmpty()) 
                schedule=scan.nextLine();  // N==schedule.length
            new Mar17CookingSchedule(schedule, K);
        }
    }  
    public static void main(String[] args)
    {
        //scan = codechef.CodeChef.getFileScanner("sumdist-t5.txt");
        //Instant start = Instant.now();
        autoTest();
        //Instant end = Instant.now();
        //out.println("usec "+ChronoUnit.MICROS.between(start, end));       
    }    
}
