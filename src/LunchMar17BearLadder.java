
import static java.lang.System.out;
import java.util.Scanner;


class LunchMar17BearLadder {
    
    static Scanner scan = new Scanner(System.in);
    public static void autoTest()
    {
        int Q = scan.nextInt();  // between 1 and 10^3
        for (int i=0; i<Q; i++) {
            int a = scan.nextInt();  // between 1 to 10^9
            int b = scan.nextInt();
            int min = a>b?b:a;
            int max = a>b?a:b;
            if (max-min==1 && min%2==1 || max-min==2)
                out.println("YES");
            else
                out.println("NO");
        }
    }
    public static void main(String[] args)
    {
        //scan = codechef.CodeChef.getFileScanner("robot.txt");
        //Instant start = Instant.now();
        autoTest();
        //Instant end = Instant.now();
        //out.println("usec "+ChronoUnit.MICROS.between(start, end));       
    } 
}
