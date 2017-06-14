package smackdown.qualifier2017;


import codechef.PointGraph;
import codechef.MyScanner;
import static java.lang.System.out;

/*
 * Brief Desc: Give snake coordiantes in a 2D grid, each snake sighting is represented by 2 points
 * either horizontal or vertical. Find out if the two sightings are of same snake
 *  if both these conditions are satisfied:
 *  The union of the set of cells in the first snake and the set of cells in the second snake, should form a connected component in this graph.
 *  No vertex should have degree more than 2 in the graph.
 * cases: 
 * same direction, either horizontal or vertical, including single cell
 *           one same line, overlap or no
 * different direction, must share one end point, out of 4.
 */
class Snake
{
    enum DIR {HORI, VERT, DOT};
    int x1, y1, x2, y2;
    DIR d=DIR.HORI;
    Snake(int x1, int y1, int x2, int y2)
    {
        if ( x1==x2 && y1==y2)
            d=DIR.DOT;
        else if ( x1==x2) {
            if ( y2<y1) {
                int y=y1;
                y1 = y2;
                y2=y;
            }
            d=DIR.VERT;
        } else {
            if ( x2<x1) {
                int x=x1;
                x1 = x2;
                x2=x;
            }
            d=DIR.HORI;
        }     
        this.x1=x1; this.y1=y1;
        this.x2=x2; this.y2=y2;
    }
    boolean checkX(Snake s2) {
        //out.println("checkX "+this.toString()+";"+s2.toString());
        if ( y1 != s2.y1 )
            return false;
        if (x2 <s2.x1 || s2.x2<x1)
            return false;
        return true;
    }
    boolean checkY(Snake s2) {
        //out.println("checkY "+this.toString()+";"+s2.toString());
        if ( x1 != s2.x1 )
            return false;
        if (y2 <s2.y1 || s2.y2<y1)
            return false;
        return true;
    }
    boolean checkXY(Snake s2) { // s2 is vertical
        //out.println("checkXY "+this.toString()+";"+s2.toString());
        if (x1 == s2.x1 && y1==s2.y1)
            return true;
        if (x1 == s2.x2 && y1==s2.y2)
            return true;
        if (x2 == s2.x1 && y2==s2.y1)
            return true;
        if (x2 == s2.x2 && y2==s2.y2)
            return true;
        return false;
    }
    boolean same(Snake s2) {
        if (d==DIR.DOT || s2.d==DIR.DOT || d==s2.d) {  // same direction
            if (d==DIR.HORI || s2.d==DIR.HORI)
                return checkX(s2);
            else
                return checkY(s2);
        } else
        {
            if (d==DIR.HORI)
                return this.checkXY(s2);
            else
                return s2.checkXY(this);            
        }
    }
    @Override
    public String toString()
    {
        return "("+x1+","+y1+"):("+x2+","+y2+")";
    }    
}
class SameSnake {
    PointGraph g=new PointGraph();

    void addCells(int x1, int y1, int x2, int y2)
    {
        g.addNode(x1, y1);
        g.addNode(x2, y2);
        if ( x1==x2) {
            if ( y2<y1) {
                int y=y1;
                y1 = y2;
                y2=y;
            }
            for (int y=y1+1; y<=y2; y++)
                g.add(x1, y-1, x2, y);
        } else {
            if ( x2<x1) {
                int x=x1;
                x1 = x2;
                x2=x;
            }
            for (int x=x1+1; x<=x2; x++)
                g.add(x-1, y1, x, y2);            
        }
    }
    
    static void test()
    {
        SameSnake s = new SameSnake();
        s.addCells(2, 1, 8, 1);
        s.addCells(11, 1, 7, 1);
        s.g.print();
        out.println(s.g.sameSnake()==true);
        Snake s1=new Snake(2, 1, 8, 1);
        Snake s2=new Snake(11, 1, 7, 1);
        out.println(s1.same(s2)==true);
        
        s = new SameSnake();
        s.addCells(2, 1, 8, 1);
        s.addCells(11, 1, 9, 1);
        s.g.print();
        out.println(s.g.sameSnake()==false);
        s1=new Snake(2, 1, 8, 1);
        s2=new Snake(11, 1, 9, 1);
        out.println(s1.same(s2)==false);
        out.println(s2.same(s1)==false);
        
        s = new SameSnake();
        s.addCells(2, 1, 8, 1);
        s.addCells(3, 1, 3, -2);
        s.g.print();
        out.println(s.g.sameSnake()==false);
        s1=new Snake(2, 1, 8, 1);
        s2=new Snake(3, 1, 3, -2);
        out.println(s1.same(s2)==false);
        
        s = new SameSnake();
        s.addCells(2, 1, 8, 1);
        s.addCells(2, 1, 2, -2);
        s.g.print();
        out.println(s.g.sameSnake()==true);
        s1=new Snake(2, 1, 8, 1);
        s2=new Snake(2, 1, 2, -2);
        out.println(s1.same(s2)==true);
                
        s = new SameSnake();
        s.addCells(2, 1, 2, 1);
        s.addCells(2, 1, 2, -2);
        s.g.print();
        out.println(s.g.sameSnake()==true);
        s1=new Snake(2, 1, 2, 1);
        s2=new Snake(2, 1, 2, -2);
        out.println(s1.same(s2)==true);
        
        s = new SameSnake();
        s.addCells(2, 0, 2, 0);
        s.addCells(2, 1, 2, -2);
        s.g.print();
        out.println(s.g.sameSnake()==true);
        s1=new Snake(2, 0, 2, 0);
        s2=new Snake(2, 1, 2, -2);
        out.println(s1.same(s2)==true);
        
        s = new SameSnake();
        s.addCells(2, 0, 2, 0);
        s.addCells(2, 1, 2, 1);
        s.g.print();
        out.println(s.g.sameSnake()==false);
        s1=new Snake(2, 0, 2, 0);
        s2=new Snake(2, 1, 2, 1);
        out.println(s1.same(s2)==false);
        
        s = new SameSnake();
        s.addCells(2, 0, 2, 0);
        s.addCells(2, 0, 2, 0);
        s.g.print();
        out.println(s.g.sameSnake()==true);
        s1=new Snake(2, 0, 2, 0);
        s2=new Snake(2, 1, 2, -1);
        out.println(s1.same(s2)==true);
        out.println(s2.same(s1)==true);
        s2=new Snake(3, 0, 1, 0);
        out.println(s1.same(s2)==true);
        out.println(s2.same(s1)==true);
        s2=new Snake(2, 0, 2, 0);
        out.println(s1.same(s2)==true);
        s2=new Snake(3, 1, 1, 1);
        out.println(s1.same(s2)==false);
        s2=new Snake(3, 1, 3, -1);
        out.println(s1.same(s2)==false);
    }
        
    static MyScanner sc = new MyScanner();
    public static void main(String[] args)
    {      
        //test();
        int T=sc.ni();     // 1 ≤ T ≤ 10^5
        StringBuilder sb=new StringBuilder();
        for (int i=0; i<T; i++) {
            int xy[]=sc.ria(8); // -10^9 ≤ Xij,Yij ≤ 10^9
            Snake s1=new Snake(xy[0], xy[1], xy[2], xy[3]);
            Snake s2=new Snake(xy[4], xy[5], xy[6], xy[7]);
            sb.append(s1.same(s2)?"yes\n":"no\n");
        }
        out.println(sb.toString());
    }
}
