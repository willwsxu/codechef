package longContests.long201709;


import codechef.MyScanner;
import static java.lang.System.out;
import java.util.Arrays;

//SEACO easy (if you know difference array well) process from back
//m commands over int Array A size N. Two tyes of comands
//l r (l ≤ l ≤ r ≤ n) — Increase all elements of the array by one, whose indices belongs to the range [l, r]
//l r (1 ≤ l ≤ r ≤ m) — Execute all the commands whose indices are in the range [l, r]. 
//output values of A after all commands,  modulo 10^9 + 7
//IDEA:
// use one difference array for command count
// Use another difference array for A
// process from back as later commands can loop through previous ones
//can implement with segment tree or fenwick tree
class SerejaCommands {
    int cmdsFr[];  // 
    int cmdsTo[];
    int cmdsType[];
    int cmdsCount[];
    int A[];
    int diffCount[];  // difference array, diffCount[i]=cmdsCount[i]-cmdsCount[i+1]
    int diffA[];      // diffA[i]=A[i]-A[i+1]
    static final int MOD=1000000007;
    
    SerejaCommands(int cmds[], int n)
    {
        A=new int[n];
        init(cmds);
    }
    SerejaCommands()
    {
        A=new int[sc.ni()];
        int cmds[]=sc.ria(sc.ni()*3);
        init(cmds);
    }
    private void init(int cmds[])
    {
        //out.println(Arrays.toString(cmds));
        int m=cmds.length/3;
        cmdsFr = new int[m];
        cmdsTo = new int[m];
        cmdsType = new int[m];
        cmdsCount = new int[m+1];// extra space needed for difference array
        for (int i=0; i<m; i++) {
            cmdsType[i]=cmds[3*i];
            cmdsFr[i]=cmds[3*i+1];
            cmdsTo[i]=cmds[3*i+2];
        }                
    }
    private void print()
    {
        StringBuilder sb=new StringBuilder();
        for (int i: A) {
            sb.append(i);
            sb.append(" ");
        }
        out.println(sb.toString());
    }
    
    void bruteforce()  //O(nm)
    {
        Arrays.fill(cmdsCount,1);
        for (int i=cmdsFr.length-1; i>=0; i--) {
            if (cmdsType[i]==2) {
                for (int j=cmdsFr[i]-1; j<cmdsTo[i]; j++) {
                    cmdsCount[j] += cmdsCount[i];
                    cmdsCount[j] %= MOD;  // can be too large, require MOD
                }
            } else {
                for (int j=cmdsFr[i]-1; j<cmdsTo[i]; j++)
                {
                    A[j] += cmdsCount[i];
                    A[j] %= MOD;  // can be too large, require MOD
                }
            }
        }    
        print();
    }
    void differenceArray()  //O(n+m)
    {
        cmdsCount[cmdsFr.length]=1; // so it works when i=m-1
        diffCount = new int[cmdsFr.length];
        diffA = new int[A.length];
        for (int i=cmdsFr.length-1; i>=0; i--) {
            cmdsCount[i]=(diffCount[i]+cmdsCount[i+1])%MOD;  // calc count of command i from difference array
            if (cmdsType[i]==2) { // apply count to command difference array between from and to.
                diffCount[cmdsTo[i]-1] = (diffCount[cmdsTo[i]-1]+cmdsCount[i])%MOD;  // add to index at cmdsTo
                if ( cmdsFr[i]>1 )
                    diffCount[cmdsFr[i]-2] = (diffCount[cmdsFr[i]-2]-cmdsCount[i]+MOD)%MOD; // subtract from index at cmdsFr
            } else { // apply count to difference array between from and to
                diffA[cmdsTo[i]-1] = (diffA[cmdsTo[i]-1]+cmdsCount[i])%MOD;
                if (cmdsFr[i]>1)
                    diffA[cmdsFr[i]-2] = (diffA[cmdsFr[i]-2] - cmdsCount[i]+MOD)%MOD;
            }
        }
        A[A.length-1] = diffA[A.length-1]%MOD;
        for (int i=A.length-2; i>=0; i--) { // compute A from difference array
            A[i] = (diffA[i]+A[i+1])%MOD;
        }
        
        print();    
    }
    
    public static void autotest(int n, int m)
    {
        int cmds[]=new int[3*m];
        for (int i=0; i<m; i++) {
            cmds[3*i]=2;
            cmds[3*i+1]=1;
            cmds[3*i+2]=i;
        }
        cmds[0]=1;
        cmds[2]=n;
        new SerejaCommands(cmds,n).differenceArray();
    }
    public static void test()
    {    
        int cmds[]=new int[]{1, 1, 2, 1, 4, 5, 2, 1, 2, 2, 1, 3, 2, 3, 4};
        new SerejaCommands(cmds,5).bruteforce();
        new SerejaCommands(cmds,5).differenceArray();
        int cmds2[]=new int[]{1,1,10,2,1,1,2,1,2,2,1,3,2,1,4,2,1,5,2,1,6,2,1,7,2,1,8,2,1,9};
        new SerejaCommands(cmds2,10).bruteforce();
        new SerejaCommands(cmds2,10).differenceArray();
        int cmds3[]=new int[]{1, 1, 1, 1, 1, 1};
        new SerejaCommands(cmds3,1).bruteforce();
        new SerejaCommands(cmds3,1).differenceArray();
        autotest(10,10);
        autotest(1000,1000);     //344211605
        autotest(100000,100000); //303861760
    }
    
    public static void judge()
    {
        int TC = sc.ni();  // between 1 and 20
        while (TC-- > 0)
            new SerejaCommands().differenceArray();        
    }
    static MyScanner sc=new MyScanner();
    public static void main(String[] args)
    {
        judge();
    }
}
