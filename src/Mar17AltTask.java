
import static java.lang.System.out;
import java.util.Scanner;


// minimal time need to complete all tasks altertively
class Mar17AltTask {
    
    static Scanner scan = new Scanner(System.in);
    static void fillArray(int [] a)
    {
        for (int i=0; i<a.length; i++)
                a[i] = scan.nextInt();
        
    }
    public static void main(String[] args)
    {
        int TC = scan.nextInt();
        for (int i=0; i<TC; i++) {
            int N = scan.nextInt();
            int [] Xenny = new int[N];  // time taking Xenny to complete each task
            int [] Yana = new int[N];
            fillArray(Xenny);
            fillArray(Yana);
            long t1=0; // Xenny start first
            long t2=0; // Yana start first
            for (int j=0; j<N; j++)
            {
                if (j%2==0) {
                    t1 += Xenny[j];
                    t2 += Yana[j];
                } else {
                    t1 += Yana[j];
                    t2 += Xenny[j];
                }
            }
            out.println(t1<t2?t1:t2);
        }        
    }
}
