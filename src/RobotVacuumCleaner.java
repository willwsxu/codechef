
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/*
 * 
 */

public class RobotVacuumCleaner {
    
    static int solve(ArrayList<String> sh) {
        Collections.sort(sh);
        Collections.reverse(sh);
        int countS=0;
        int countH=0;
        int total=0;
        for (String s : sh) {
            for (int i=0; i<s.length(); i++) {
                if (s.charAt(i)=='s') {
                    countS++;
                } else {
                    total += countS;
                }
            }
            System.out.println(s+" score="+total);
        }
        return total;
    }
    public static void main(String[] args)
    {
        ArrayList<String> str=new ArrayList<>();
        str.add("ssh");
        str.add("hs");
        str.add("s");
        str.add("hhhs");
        System.out.println(solve(str));
    }
}
