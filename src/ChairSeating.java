
import static java.lang.System.out;

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
    }
    
    int len; // chair count
    Seating findNextSeating(String chairs, int startInd)
    {
        int empty=0;
        Seating seating = new Seating();
        seating.empties=0;
        seating.start = startInd;
        seating.end = startInd;
        for (int i=0; i<len; i++) 
        {
            if (chairs.charAt((i+startInd)%len)=='1') {
                seating.end = (i+startInd)%len;
                if (empty==0)  // previous chair is seated
                    continue;
                else { //previous chair is NOT seated
                    seating.empties += empty;
                    empty=0;
                }
            } else {
                empty++;
            }
        }
        out.println("start "+seating.start+" end "+seating.end+" empty "+seating.empties);
        return seating;
    }
    
    int chairMoving(String chairs)
    {
        len = chairs.length();
        // edge condition: if last char is empty and first char is 1
        boolean foundEmpty=chairs.charAt(len-1)=='0';
        Seating best=null;
        for (int i=0; i<len; i++) {
            if ( chairs.charAt(i)=='1') {
                if ( foundEmpty ) {
                    Seating s = findNextSeating(chairs, i);
                    if (best==null || best.empties>s.empties)
                        best = s;
                }
                else
                    continue;
            } else
                foundEmpty = true;
        }
        return best.empties;
    }
    
    public static void main(String[] args)
    {
        out.println(new ChairSeating().chairMoving("0100"));
        out.println(new ChairSeating().chairMoving("10100"));
        out.println(new ChairSeating().chairMoving("10101"));
        out.println(new ChairSeating().chairMoving("00101"));
        out.println(new ChairSeating().chairMoving("100010011101"));
    }
}
