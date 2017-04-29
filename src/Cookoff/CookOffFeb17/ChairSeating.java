package CookOffFeb17;


import static java.lang.System.out;
import java.util.Scanner;
import java.lang.management.ManagementFactory; 
import java.lang.management.MemoryUsage; 
import java.lang.management.MemoryMXBean;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author wxu
 */
// seating is circular
// input is a string of 0 and 1. 0 means empty chair, 1 means seated by a person
class ChairSeating {
    class Seating
    {
        int start;  // first chair seated
        int end;    // last chair seated, can be smalled start as it 
        int empties;// empty chairs in between
        Seating(int st, int en, int em)
        {
            start = st;
            end =en;
            empties = em;
        }
    }
    
    int len; // chair count
    Seating best = null;
    int findNextSeating(String chairs, int startInd)
    {
        int empty=0;
        int empties=0;
        int start = startInd;
        int end = startInd;
        for (int i=0; i<len; i++) 
        {
            if (chairs.charAt((i+startInd)%len)=='1') {
                end = (i+startInd)%len;
                if (empty==0)  // previous chair is seated
                    continue;
                else { //previous chair is NOT seated
                    empties += empty;
                    empty=0;
                }
            } else {
                empty++;
            }
        }
        if (best==null)
            best = new Seating(start, end, empties);
        else if (best.empties>empties)
        {
            best.empties = empties;
            best.end = end;
            best.start = start;
        }
        //out.println("start "+seating.start+" end "+seating.end+" empty "+seating.empties);
        return empties;
    }
    
    int chairMoving(String chairs)
    {
        len = chairs.length();
        // edge condition: if last char is empty and first char is 1
        boolean foundEmpty=chairs.charAt(len-1)=='0';
        for (int i=0; i<len; i++) {
            if ( chairs.charAt(i)=='1') {
                if ( foundEmpty ) {
                    foundEmpty = false;
                    findNextSeating(chairs, i);
                }
                else
                    continue;
            } else
                foundEmpty = true;
        }
        return best.empties;
    }
    
    // find max continuous 0 and total 0
    int chairMoving2(String chairs)
    {
        len = chairs.length();
        int total0=0;
        int cont0=0;// continuous
        int max = 0;
        if (chairs.charAt(len-1)=='0' && chairs.charAt(0)=='0') {
            while (chairs.charAt(len-1)=='0') {
                len--;
                cont0++;
                total0++;
            }
        }
        for (int i=0; i<len; i++) {
            if ( chairs.charAt(i)=='1') {
                if ( cont0>max ) {
                    max = cont0;
                }
                if (cont0>0)
                    cont0=0;
            } else {
                cont0++;
                total0++;
            }
        }
        if ( cont0>max ) {
            max = cont0;
        }
        return total0-max;
    }
    void clear()
    {
        len=0;
        best=null;
    }
    
    static void manualTest()
    {
        out.println(new ChairSeating().chairMoving2("0100"));
        out.println(new ChairSeating().chairMoving2("10100"));
        out.println(new ChairSeating().chairMoving2("10101"));
        out.println(new ChairSeating().chairMoving2("00101"));
        out.println(new ChairSeating().chairMoving2("100010011101"));        
    }
    static void autoTesting()
    {
        Scanner scan = new Scanner(System.in);
        ChairSeating chairs = new ChairSeating();
        int TC = scan.nextInt();
        for (int i=0; i<TC; i++) {
            int N = scan.nextInt();
            String ch = scan.nextLine();
            if (ch.length()!=N)
                ch = scan.nextLine();
            chairs.clear();
            out.println(chairs.chairMoving2(ch));
        }        
    }
    public static void main(String[] args)
    {
	//MemoryMXBean memory = ManagementFactory.getMemoryMXBean();
	//MemoryUsage heap = memory.getHeapMemoryUsage();
        //out.println("Memory max "+heap.getMax()+" used "+heap.getUsed()+" commited "+heap.getCommitted());
        autoTesting();
        //manualTest();
        //heap = memory.getHeapMemoryUsage();
        //out.println("Memory max "+heap.getMax()+" used "+heap.getUsed()+" commited "+heap.getCommitted());
    }
}
