
import static java.lang.System.out;
import java.util.Scanner;

class Candies {
    int rounds(int total, int count)
    {
        int r=0;
        while (total>=count) {
            r++;
            total -= count;
            count +=2;
        }
        return r;
    }
    void solve (int A, int B)
    {
        int r1=rounds(A, 1);
        int r2=rounds(B, 2);
        if (r1>r2)
            out.println("Limak");
        else
            out.println("Bob");
    }
    
    static Scanner sc = new Scanner(System.in);
    public static void main(String[] args)
    {
        //sc = codechef.ContestHelper.getFileScanner("digitscount-t.txt");
        int TC = sc.nextInt();  // 1 ≤ T ≤ 1000
        for (int i=0; i<TC; i++) {
            int A = sc.nextInt();   // 1 ≤ A, B ≤ 1000
            int B = sc.nextInt();
            new Candies().solve(A, B);
        }
    }
}
