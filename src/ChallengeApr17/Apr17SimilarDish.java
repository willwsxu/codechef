package ChallengeApr17;


import static java.lang.System.out;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

// simple/easy
class Apr17SimilarDish {
    
    static void compareDish(String[] dish1, String[]dish2)
    {
        Set<String> dset = new TreeSet<>();
        for (int i=0; i<dish2.length; i++)  // length is 4
            dset.add(dish2[i]);
        int count=0;
        for (int i=0; i<dish1.length; i++)
            if (dset.contains(dish1[i]))
                count++;
        if (count>=dish2.length/2)
            out.println("similar");
        else
            out.println("dissimilar");
    }
    static Scanner scan = new Scanner(System.in);
    public static void main(String[] args)
    {        
        int TC = scan.nextInt();  // between 1 and 200
        for (int i=0; i<TC; i++) {
            String line1 = scan.nextLine();
            if (line1.isEmpty())
                line1 = scan.nextLine();    
            
            String line2 = scan.nextLine();
            if (line2.isEmpty())
                line2 = scan.nextLine();   
            compareDish(line1.split(" "), line2.split(" "));
        }
    }
}
