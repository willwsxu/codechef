
package acsl.c1;

// two tasks, align two triangle to match sides proportionally

import static java.lang.System.out;
import java.util.Arrays;
import java.util.Scanner;

// find out the ratio, simplied
public class SimilarTriangle {
    
    static class Triangle
    {
        String[] sidesName=new String[]{"AB", "BC", "CA"};
        int sides[]=new int[3];
        int order[]=new int[3];
        Triangle(int ab, int bc, int ca)
        {
            sides[0]=ab;
            sides[1]=bc;
            sides[2]=ca;
            int sides2[]=new int[]{ab,bc,ca};  // create a new copy for sorting
            Arrays.sort(sides2);
            for (int i=0; i<order.length;i++) {
                for (int j=0; j<sides2.length; j++) {
                    if ( sides2[j]==sides[i]) {
                        order[i]=j;
                        sides2[j]=-1; // mark it as used
                        break;
                    }
                }
            }
            //out.println(Arrays.toString(sides));
            //out.println(Arrays.toString(order));
        }
        
        void setLabel(String []label) {
            sidesName=label;
        }
        
        void matchOrder(Triangle match)
        {
            String[] sidesName2=new String[]{"","",""};
            int sides2[]=new int[3];
            for (int i=0; i<sides.length; i++) {
                for (int j=0; j<sides.length; j++) {
                    if (order[i]==match.order[j]) {
                        sides2[j]=sides[i];
                        sidesName2[j]=sidesName[i];
                    }
                }
            }
            for (int i=0; i<sides.length; i++)
                order[i]=match.order[i];
            sides=sides2;
            sidesName=sidesName2;
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
        String getName()
        {
            if (name==null) {
                StringBuilder sb=new StringBuilder();
                for (int i=0; i<2; i++) {
                    for (int j=0; j<2; j++) {
                        if (sidesName[1].charAt(j)==sidesName[0].charAt(i)) {
                            sb.append(sidesName[0].charAt(1-i));
                            sb.append(sidesName[0].charAt(i));
                            sb.append(sidesName[1].charAt(1-j));
                            name=sb.toString();
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
        Triangle abc = new Triangle(sides6[0], sides6[1],sides6[2]);
        Triangle def = new Triangle(sides6[3], sides6[4],sides6[5]);
        if (!abc.valid() || !def.valid()) {
            out.println("NOT VALID");
            return;
        }
        def.setLabel(new String[]{"DE", "EF", "FD"});
        def.matchOrder(abc);
        if ( def.similar(abc) )
            out.println(def.getName());
        else
            out.println("NONE");
    }
    static Scanner sc=new Scanner(System.in).useDelimiter("\\s*[,\n]\\s*");
    public static void main(String[] args)
    {  
        test();
    }
}
