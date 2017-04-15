package ChallengeMar17;


import static java.lang.System.out;
import java.util.Arrays;
import java.util.Scanner;

// EXTRAN  easy
// remove a number so the sequence become consecutive
class Mar17ExtraNum {
    static Scanner scan = new Scanner(System.in);
    static void fillArraySort(long [] a)
    {
        for (int i=0; i<a.length; i++) {
            a[i] = scan.nextLong();  
        }
        Arrays.sort(a);
    }
    public static void main(String[] args)
    {
        int TC = scan.nextInt();
        for (int i=0; i<TC; i++) {
            int N = scan.nextInt();  // between 3 and 10^5
            long [] seq = new long[N];
            fillArraySort(seq);
            if (seq[0]+1 != seq[1])
                out.println(seq[0]);
            else {
                for (int j=1; j<N; j++)
                {
                    if ( seq[j] != seq[j-1]+1) {
                        out.println(seq[j]);
                        break;
                    }
                }
            }
        }        
    }    
}
