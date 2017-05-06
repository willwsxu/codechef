package longContests.may17;


import static java.lang.System.out;
import java.util.Scanner;

//UNICOURS beginner
class UnivCourse {
    
    static Scanner sc = new Scanner(System.in);
    public static void main(String[] args)
    {        
        //sc = codechef.ContestHelper.getFileScanner("digitscount-t.txt");
        int TC = sc.nextInt();  // between 1 and 20
        for (int i=0; i<TC; i++) {
            int N=sc.nextInt();  // 1 ≤ n ≤ 105
            int maxA=0;
            for (int j=0; j<N; j++) {
                int a=sc.nextInt(); // 0 ≤ ai < i, pre-requisite required for course i
                if (maxA<a)
                    maxA=a;
            }
            out.println(N-maxA);  // max # of courses without any pre-req
        }
    }
}
