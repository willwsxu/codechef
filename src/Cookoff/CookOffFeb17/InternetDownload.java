package CookOffFeb17;


import static java.lang.System.out;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author WXU
 */
class InternetDownload {
    class Usage {
        int minute;
        int mb;
        public Usage(int m, int b)
        {
            minute=m;
            mb=b;
        }
    }
    List<Usage> usage = new ArrayList<>();
    
    void addUsage(int min, int band)
    {
        usage.add(new Usage(min,band));
    }
    int calcCharge(int free)
    {
        int charge=0;
        for (int i=0; i<usage.size(); i++)
        {
            if (usage.get(i).minute <= free) {
                free -= usage.get(i).minute;
                continue;
            }
            charge += (usage.get(i).minute-free)*usage.get(i).mb;
            if ( free>0)
                free=0;
        }
        return charge;
    }
    
    public static void main(String[] args)
    {
        InternetDownload charge;
        
        Scanner scan = new Scanner(System.in);
        int TC = scan.nextInt();
        for (int i=0; i<TC; i++) {
            charge = new InternetDownload();
            int N = scan.nextInt();
            int free = scan.nextInt();
            for (int j=0; j<N; j++) {
                int min = scan.nextInt();
                int usage = scan.nextInt();
                charge.addUsage(min, usage);
            }
            out.println(charge.calcCharge(free));
        }
    }
}
