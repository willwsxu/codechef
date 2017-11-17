
package acsl.c1;

// two tasks, align two triangle to match sides proportionally

import static java.lang.System.out;
import java.util.Arrays;
import java.util.Scanner;

// Two triangles are similar if their sides are proportional
// triangle sides a, b, c, triangle d, e, f are similar if a/d=b/e=c/f
// Divide into smaller steps:
// Define a Triangle class to store its sides, and labels
// find order of side value
// match side order between 2 triangles
// find out the ratio of sides, simplied using gcd
// Other task:
// convert from side labels to triangle name
// check if triangle is valid
public class SimilarTriangle {
    
    static class Triangle
    {
        String[] sidesName=new String[]{"AB", "BC", "CA"}; // default triangle side label
        int sides[]=new int[3]; // len of sides
        int order[]=new int[3]; // order of side len
        Triangle(int ab, int bc, int ca)
        {
            sides[0]=ab;
            sides[1]=bc;
            sides[2]=ca;
            // if side len is 4, 2, 6, the order is 1, 0, 2
            // first sort side as 2, 4, 6
            int sides2[]=new int[]{ab,bc,ca};  // create a new copy for sorting
            Arrays.sort(sides2);
            for (int i=0; i<order.length;i++) { // go through eash side
                for (int j=0; j<sides2.length; j++) {
                    if ( sides2[j]==sides[i]) { // find its order in sorted array
                        order[i]=j;   // sides[i] has order j, e.g. sides[0]=1
                        sides2[j]=-1; // mark it as used, this is necessary when sides can be same
                        // if sides are 2, 2, 1, order is 1,2,0
                        break;  // break out to process next i
                    }
                }
            }
            //out.println(Arrays.toString(sides));
            //out.println(Arrays.toString(order));
        }
        
        void setLabel(String []label) {  // new side labels
            sidesName=label;
        }
        // if current order is 2, 1, 0. new order to match is 0, 1, 2
        // if current side len is 6, 4, 2, label is AB, BC, CA
        // result len is 2, 4, 6, label is CA, CB, AB
        void matchOrder(Triangle match)
        {
            String[] sidesName2=new String[]{sidesName[0], sidesName[1],sidesName[2]};  // save original copy
            int sides2[]=new int[]{sides[0], sides[1], sides[2]};  // save original len
            for (int i=0; i<sides.length; i++) {
                for (int j=0; j<sides.length; j++) {
                    if (order[i]==match.order[j]) { // order[0]==match.order[2]
                        sides[j]=sides2[i];         // move side at i to j, e.g. move side 0 to 2
                        sidesName[j]=sidesName2[i]; // move label too
                    }
                }
            }
            for (int i=0; i<sides.length; i++)
                order[i]=match.order[i];
            //out.println(Arrays.toString(sides));
            //out.println(Arrays.toString(order));
            //out.println(Arrays.toString(sidesName));
        }
        
        boolean valid() // sum of any two sides > third side
        {
            return sides[0]+sides[1]>sides[2] && sides[0]+sides[2]>sides[1] && sides[2]+sides[1]>sides[0];
        }
        
        // a>=b
        static int gcd(int a, int b) {  // find greatest common devisor using Euclid algorithm
            if (a<b)
                return gcd(b,a);
            return b==0?a:gcd(b, a%b);
        }
        boolean similar(Triangle other)
        {
            int d[]=new int[3];
            for (int i=0; i<sides.length; i++)
                d[i]=gcd(sides[i], other.sides[i]);
            if (sides[0]/d[0]!=sides[1]/d[1] || other.sides[0]/d[0]!=other.sides[1]/d[1])
                return false;
            if (sides[0]/d[0]!=sides[2]/d[2] || other.sides[0]/d[0]!=other.sides[2]/d[2])
                return false;
            if (sides[2]/d[2]!=sides[1]/d[1] || other.sides[2]/d[2]!=other.sides[1]/d[1])
                return false;
            return true;
        }
        String name=null;
        // if side label is AB, AC, BC, triangle name is BAC
        // consider first two sides sharing angle A, so A in middle of the two sides
        String getName()
        {
            if (name==null) {
                StringBuilder sb=new StringBuilder();  // use it to append trianlge point
                for (int i=0; i<2; i++) {    // check 2 char in first side
                    for (int j=0; j<2; j++) {// check 2 char in second side
                        if (sidesName[1].charAt(j)==sidesName[0].charAt(i)) {  // shared point of two sides
                            sb.append(sidesName[0].charAt(1-i));  // add point left of middle one
                            sb.append(sidesName[0].charAt(i));    // add middle point
                            sb.append(sidesName[1].charAt(1-j));  // add point right of middle
                            name=sb.toString();
                            return name; // done, exit outter loop
                        }
                    }
                }
            }
            return name;
        }
    }
    
    public static void test()
    {
        Triangle abc = new Triangle(2,4,6);
        new Triangle(22,4,6);
        Triangle def = new Triangle(2,4,1);
        def.setLabel(new String[]{"DE", "EF", "FD"});
        def.matchOrder(abc);
        out.println(def.gcd(12,8));
        out.println(def.gcd(12,12));
        out.println(def.gcd(12,1));
        out.println(def.gcd(12,0));
        out.println(def.gcd(12,144));
        out.println(def.similar(abc));
        Triangle abc3 = new Triangle(2,4,8);
        out.println(def.similar(abc3));
        //
        new SimilarTriangle(new int[]{2, 3, 4, 8, 6, 4});
        new SimilarTriangle(new int[]{2, 3, 4, 4, 6, 8 });
        new SimilarTriangle(new int[]{2, 3, 4, 8, 4, 6 });
        new SimilarTriangle(new int[]{2, 3, 4, 8, 4, 5 });
        new SimilarTriangle(new int[]{2, 3, 1, 9, 3, 6 });
    }
    SimilarTriangle(int sides6[])
    {
        Triangle abc = new Triangle(sides6[0], sides6[1],sides6[2]);  // create 2 objects
        Triangle def = new Triangle(sides6[3], sides6[4],sides6[5]);
        if (!abc.valid() || !def.valid()) {  // check if either triangle is valid
            out.println("NOT VALID");
            return;  // no more work
        }
        def.setLabel(new String[]{"DE", "EF", "FD"}); // change label
        def.matchOrder(abc);    // match side len oder of def to abc
        if ( def.similar(abc) )
            out.println(def.getName());
        else
            out.println("NONE");
    }
    static Scanner sc=new Scanner(System.in).useDelimiter("\\s*[,\n]\\s*");
    public static void main(String[] args)
    {  
        //test();
        
        int T=5;
        while(T-->0) {
            int sides[]=new int[6];
            for (int i=0; i<sides.length; i++)
                sides[i]=sc.nextInt();
            new SimilarTriangle(sides);
        }
    }
}
/*
3, 4, 5, 6, 8, 10
6, 8, 10, 5, 4, 3
5, 7, 12, 1, 2, 3
9, 12, 15, 3, 4, 6
5, 12, 13, 26, 10, 24
*/
/*
1.  3, 4, 5, 6, 8, 10						1.  DEF
2.  6, 8, 10, 5, 4, 3						2.  DFE
3.  5, 7, 12, 1, 2, 3						3.  NOT VALID
4.  9, 12, 15, 3, 4, 6						4.  NONE
5.  5, 12, 13, 26, 10, 24					5.  EFD
*/