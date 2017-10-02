package r408;


import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class BankHacking796C {
    
    static Scanner scan = new Scanner(System.in);
    public static void main(String[] args)
    {        
        int N = scan.nextInt();  // 1 ≤ n ≤ 3·10^5
        int k = scan.nextInt();  // swaps
        List<Long> A = new ArrayList<>(300000); // strength - 10^9 ≤ ai ≤ 10^9
        for (int i=0; i<N; i++)
            A.add(scan.nextLong());
        for (int i=0; i<N-1; i++) {
            String line = scan.nextLine();
            if (line.isEmpty())
                line = scan.nextLine();
            String[] conn = line.split(" ");
            int c1 = Integer.parseInt(conn[0]);
            int c2 = Integer.parseInt(conn[1]);
            
        }
    }
}
