package r408;


import static java.lang.System.out;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;


public class FindBone796B {
    
    static Scanner scan = new Scanner(System.in);
    public static void main(String[] args)
    {        
        int N = scan.nextInt();  // 2 ≤ n ≤ 10^6, 1 ≤ m ≤ n, 1 ≤ k ≤ 3·10^5
        int m = scan.nextInt();  // hole
        int k = scan.nextInt();  // swaps
        Set<Integer> holes = new HashSet<>();
        for (int i=0; i<m; i++)
            holes.add(scan.nextInt());
        if ( holes.contains(1) ) {
            out.println(1);
            return;
        }
        int last=1;
        for (int i=0; i<k; i++) {
            String line = scan.nextLine();
            if (line.isEmpty())
                line = scan.nextLine();
            String[] swap = line.split(" ");
            int c1 = Integer.parseInt(swap[0]);
            int c2 = Integer.parseInt(swap[1]);
            int other=0;
            if (c1==last)  {
                other = c2;
            } else if (c2==last)  {
                other = c1;
            }
            else // no bones
                continue;
            last = other;
            if (holes.contains(other)) {
                out.println(other);
                break;
            }
            if (i==k-1) 
                out.println(other);
        }
    }
}
/*
5 1 5
2
2 3
3 4
2 1
4 5
*/
