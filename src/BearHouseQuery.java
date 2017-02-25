
import static java.lang.System.out;
import java.util.Scanner;

class BearHouseQuery {
    int square;
    int triangleBase;
    int triangleHeight;
    
    int q=0;
    Scanner scan = new Scanner(System.in);
    
    String query(Scanner scan, int x, int y)
    {
        if (q++>100)
            return "NO";
        String answer;
        out.println("? "+x+" " +y);
        out.flush();
        answer = scan.nextLine();
        if (answer.isEmpty())
            answer = scan.nextLine();  
        return answer;
    }
    
    int querySquare(int min, int max, int y)
    {
        if (max-min==1)
            return min;
        int mid = (min+max)/2;
        if (query(scan, mid, y).equals("NO"))
            return querySquare(min, mid, y);
        else
            return querySquare(mid, max, y);
    }
        
    int queryY(int min, int max)
    {
        if (max-min<=1)
            return min;
        int mid = (min+max)/2;
        if (query(scan, 0, mid).equals("NO"))
            return queryY(min, mid);
        else
            return queryY(mid, max);
    }
    
    public void calcArea()
    {
        square = querySquare(1, 500, 0);  
        triangleBase = querySquare(square+1, 1001, square*2);
        triangleHeight = queryY(square*2+1, 1001);
        int area = (square*square*4)+triangleBase*(triangleHeight-square*2);
        out.println( "! "+area);
    }
    
    public static void main(String[] args)
    {
        new BearHouseQuery().calcArea();
    }
}
