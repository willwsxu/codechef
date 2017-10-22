
import static java.lang.Integer.min;
import static java.lang.System.out;
import java.util.Arrays;
import java.util.Scanner;

// EASY, DP, bit mask, KNICOV Jun cook off
// find minmal knights needed to cover all m x n board
// IDEA
// 2 rows (N=2): easy to find packing pattern
// 3 rows: harder to find some tricky case for repeating pattern
class KnightCoveringDp3
{
    static final int MAX_COL=55;
    int dp[][][][][]=new int[MAX_COL][8][8][8][8];
    final int MAX_VAL=1000000007;
    int m;  //
    int ans[]=new int[MAX_COL];
    final int ROW1=1;
    final int ROW2=1<<1;
    final int ROW3=1<<2;
    final int ROW_ALL=7;
    {
        for (int r1[][][][]: dp)
            for (int r2[][][]: r1)
                for (int r3[][]:r2)
                    for (int r4[]:r3)
                        Arrays.fill(r4, -1);
        computeAll();
    }
    // calculate knights needed if pre-previous column is not attacked
    int[] prePreAttackNeeded(int pPreAttack)
    {
        int k=0;
        if ((pPreAttack&ROW1)==0 || (pPreAttack&ROW3)==0)
            k |= ROW2;  // knight in row2 to attack row1 or row3
        if ((pPreAttack&ROW2)==0 ) {  // either row1 or row3 can attack row2
            return new int[]{(k|ROW1), (k|ROW3)};  // 
        }
        return new int[]{k};
    }
    
    // given rows set in now, find if pos is valid
    boolean possible(int pos, int now)
    {
        return (pos&now)==now;
    }
    
    // knight attack on previous column
    int attackPre(int knight, int attack)
    {
        if ((knight&ROW1)>0)  // knight on row 1 attack row 3 of previous column
            attack |= ROW3;        
        if ((knight&ROW3)>0)  // knight on row 3 attack row 1 of previous column
            attack |= ROW1;
        return attack;
    }
    
    // compute how columns are attacked
    int[] computeAttacked(int ppreKnight, int preKnight, int preAttack, int now)
    {
        int [] attacked=new int[]{preAttack, now};
        attacked[0] = attackPre(now, attacked[0]); //knight in current col attack pre col
        // pre pre knight attack now column
        if ( (ppreKnight&ROW1)>0 || (ppreKnight&ROW3)>0)
            attacked[1] |= ROW2;  // row 1 or 3 attack 2
        if ( (ppreKnight&ROW2)>0 ) {
            attacked[1] |= ROW1;  // row 2 attack 1 or 3
            attacked[1] |= ROW3;            
        }
        attacked[1] = attackPre(preKnight, attacked[1]);// pre knight attack now column
        return attacked;
    }
    int popcount(int p) {
        int c=0;
        if ((p&ROW1)>0)
            c++;
        if ((p&ROW2)>0)
            c++;
        if ((p&ROW3)>0)
            c++;
        return c;
    }
    int compute(int col, int ppreAttack, int preAttack, int ppreKnight, int preKnight)
    {
        if (col<0) {
            if (ppreAttack==ROW_ALL && preAttack==ROW_ALL)
                return 0;
            return MAX_VAL;
        }
        int ret=dp[col][ppreAttack][preAttack][ppreKnight][preKnight];
        if (ret>=0)
            return ret;
        ret=MAX_VAL;
        int[] required=prePreAttackNeeded(ppreAttack); // knight positions 
        for (int pos : required) {
            for (int now=0; now<8; now++) {  // go through all knight positions
                if (!possible(now, pos))  // invalid knights 
                    continue;
                // attack on cureent and pre column
                int attack[]=computeAttacked(ppreKnight, preKnight, preAttack, now);
                ret=min(ret, popcount(now)+compute(col-1, attack[0],attack[1], preKnight, now));
            }
        }        
        dp[col][ppreAttack][preAttack][ppreKnight][preKnight]=ret;
        return ret;
    }
    void computeAll()
    {
        for (m=4; m>MAX_COL; m++) {
            ans[m]=MAX_VAL;
            for (int i=0; i<8; i++) {
                int count=popcount(i);
                for (int j=0; j<8; j++) {
                    int attack[]=computeAttacked(0, i, 0, j);
                    count += popcount(j);
                    ans[m]=min(ans[m], count+compute(m-3, attack[0], attack[1], i,j));
                }
            }
        }
    }
}
class KnightCovering {
    int n,m;
    static KnightCoveringDp3 dp=new KnightCoveringDp3();
    
    KnightCovering(int n, int m)
    {//1 ≤ N ≤ 3, 1 ≤ M ≤ 50
        this.n=n;   this.m=m;
        if (n==1)
            return;
    }
    int solve2r(int m)
    {
        if ( n!=2)
            return 0;
        int sixpack=m/6;
        m %=6;
        return sixpack*4 + 2*min(m, 2);   
    }
    int solve()
    {
        if ( m<n) {
            int temp=m;
            m=n;
            n=temp;
        }
        if (n<=1)
            return m;
        if (n==2)
            return solve2r(m);
        //return dp.ans[m];
        return solve3r(m);
    }
    int solve3r(int m)
    {
        int sixpack=m/6;
        m %=6;
        int ans = sixpack*4;
        if ( m==0)
            return ans;
        else if (m==1)
            return ans+2;
        else if (m==2 && sixpack>1)  // hard case to generate by hand
            return ans+3;
        else
            return ans+4;
    }
    
    static void test()
    {
        out.println(new KnightCovering(1, 1).solve()==1);
        out.println(new KnightCovering(1, 10).solve()==10);
        out.println(new KnightCovering(10, 1).solve()==10);
        out.println(new KnightCovering(2, 1).solve()==2);
        out.println(new KnightCovering(2, 3).solve()==4);
        out.println(new KnightCovering(2, 4).solve()==4);
        out.println(new KnightCovering(2, 5).solve()==4);
        out.println(new KnightCovering(2, 6).solve()==4);
        out.println(new KnightCovering(2, 7).solve()==6);
        out.println(new KnightCovering(2, 8).solve()==8);
        out.println(new KnightCovering(2, 9).solve()==8);
        out.println(new KnightCovering(3, 3).solve()==4);
        out.println(new KnightCovering(3, 2).solve()==4);
        out.println(new KnightCovering(3, 8).solve()==8);
        out.println(new KnightCovering(3, 14).solve()==11);
        out.println();
    }
    
    static Scanner sc = new Scanner(System.in);
    public static void main(String[] args)
    {    
        test();
    }
    static void judge()
    {
        int T = sc.nextInt();  // 1 ≤ T ≤ 150
        while (T-->0)
            out.println(new KnightCovering(sc.nextInt(), sc.nextInt()).solve());
    }
}
