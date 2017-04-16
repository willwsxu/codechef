
import static java.lang.System.out;
import java.util.Arrays;
import java.util.Scanner;


class Apr17DigitCount {
    
    int A[];
    // count digits
    boolean digitsCheck(long num)
    {
        int a[]=new int[10];
        Arrays.fill(a, 0);
        while (num>0) {
            a[(int)(num%10)]++;
            num /=10;
        }
        for (int i=0; i<10; i++)
            if (A[i]==a[i])
                return false;  // bad if there is a match
        return true;
    }
    
    long bruteforce(long L, long R)
    {
        long good=0;
        for (long num=L; num<=R; num++)
            if (digitsCheck(num))
                good++;
        return good;        
    }
    // all digits must appreat at least once
    long subtask2(long L, long R)
    {
        if (R<1023456789) // 1023456798 1023456879 1023456897 1023456978 1023456987 1023457689
            return 0;//9 81 18 81 9 702
        return bruteforce(1023456789, R);
    }
    Apr17DigitCount(long L, long R, int A[])
    {
        this.A=A;
        int sum = 0;
        for (int s:A)
            sum += s;
        
        long good=0;
        if (sum==0)
            good = subtask2(L, R);
        else
            good = bruteforce(L, R);
        out.println(good);
    }
    static Scanner sc = new Scanner(System.in);
    public static void main(String[] args)
    {        
        //sc = codechef.ContestHelper.getFileScanner("digitscount-t.txt");
        int TC = sc.nextInt();  // between 1 and 20
        for (int i=0; i<TC; i++) {
            long L = sc.nextLong();   // 1 ≤ L ≤ 10^8
            long R = sc.nextLong();   // 1 ≤ R<=L
            int A[] = new int[10];    // digit count, 1 to 18
            for (int j=0; j<10; j++)
                A[j] = sc.nextInt(); 
            new Apr17DigitCount(L, R, A);
        }
    }
}
