package LunchFeb17;


import static java.lang.System.out;
import java.util.Scanner;


class BearMilkyCookies {
    
    static String validate(String [] words)
    {
        int cookies=0;
        for (int j=0; j<words.length; j++) {
            if (words[j].equals("cookie"))
                cookies++;
            else
                cookies=0;
            if (cookies>1) {
                return "NO";
            }
        }
        return cookies>0?"NO":"YES";
    }
    public static void main(String[] args)
    {
        Scanner scan = new Scanner(System.in);
        int TC = scan.nextInt();
        for (int i=0; i<TC; i++) {
            int N = scan.nextInt();
            String line = scan.nextLine();
            if (line.isEmpty())
                line = scan.nextLine();
            String[] words = line.split(" ");
            if ( N != words.length)
                out.println("NO");
            else
                out.println(validate(words));
        }        
    }
}
