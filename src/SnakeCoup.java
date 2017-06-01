
import static java.lang.System.out;
import java.util.Scanner;

/*
 * Brief Desc: In 2 x n grid house, some live snakes and some empty. 
 * find minimal sound proof fences needed to prevent any communication
 * Greedy method
 */
//SNCOUP, Easy - Medium
class SnakeCoup {
    
    static int greedy(String[] h, int n)
    {
        // add horizontal fence if there are snakes on both rows
        boolean hori=h[0].indexOf('*')>=0&&h[1].indexOf('*')>=0;     
        int f=hori?1:0;
        //out.println("horizontal "+f);
        boolean star[]=new boolean[]{false,false};
        for (int i=0; i<n; i++)  {
            for (int k=0; k<2;k++) {
                // put up a vertical fence if any row has more than 1 snake
                if (h[k].charAt(i)=='*') {
                    if (star[k]) {
                        f++;
                        star[0]=star[1]=false;// reset
                        break;
                    }
                }
            }
            for (int k=0; k<2;k++)
                if (h[k].charAt(i)=='*')
                    star[k]=true;  // set current state
            //out.println(f);
        }
        return f;
    }
    // check 4 column patterns to add vertical fences, need to consider if there is horizontal
    static int fences(String[] h, int n)
    {
        int f=0;
        boolean hori=false;
        for (int i=0; i<n; i++)  {
            if (!hori) {
                if (h[0].charAt(i)=='*' && h[1].charAt(i)=='*') {
                    hori=true;
                    f++;
                    out.println("horizontal fence");
                    break;
                }                
            }
        }
        //4 patterns: .., *., .*,**
        int prev=0; // compare to value is previous none empty
        for (int i=0; i<n; i++)  {
            if (h[0].charAt(i)=='.' && h[1].charAt(i)=='.')
                continue;
            if (h[0].charAt(i)=='*' && h[1].charAt(i)=='*') {
                if ( prev!=0)
                    f++;
                prev=3;
            } else if (h[0].charAt(i)=='*' && h[1].charAt(i)=='.') {
                if ( prev==3 ||prev==1)
                    f++;
                else if (prev==2 &&!hori)
                    f++;
                prev=1;
            } else {
                if ( prev==3 ||prev==2)
                    f++;
                else if (prev==1 &&!hori)
                    f++;
                prev=2;                
            }
            out.println("pattern "+prev);
        }
        return f;
    }
    static void manualTest()
    {
        out.println(greedy(new String[]{".*.*.*.*.*","*.*.*.*.*."},10)==5); 
        out.println(greedy(new String[]{".*.*.*.*.*",".........."},10)==4); 
        out.println(greedy(new String[]{".*.*.*.*.*","**********"},10)==10);  
        out.println(greedy(new String[]{"....","...*"},4)==0);     
        out.println(greedy(new String[]{"*...","...*"},4)==1);    
        out.println(greedy(new String[]{"..***","***.."},5)==5);    
        out.println(greedy(new String[]{"***..","..***"},5)==5);                
    }
    static void autotest()
    {
        int T=sc.nextInt();     // 1 ≤ T ≤ 10
        for (int i=0; i<T; i++) {
            int n=sc.nextInt(); // 1 ≤ n ≤ 10^5
            String[] h=new String[2];
            h[0]=sc.next();
            h[1]=sc.next();
            out.println(greedy(h,n));
        }        
    }
    
    static Scanner sc = new Scanner(System.in);
    public static void main(String[] args)
    {      
        autotest();
    }
}
