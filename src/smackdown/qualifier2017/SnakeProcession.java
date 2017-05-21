package smackdown.qualifier2017;


import java.util.Scanner;

/*
 */

class SnakeProcession {
    
    static boolean check(String r)
    {
        //System.out.println(r);
        int head=0;
        for (int j=0; j<r.length(); j++)
        {
            switch(r.charAt(j)) {
                case 'T':
                    if (head<1)
                        return false;
                    head--;
                    break;
                case 'H':
                    if (head>0)
                        return false;
                    head++;
                    break;
                default:
                    //System.out.println(r.charAt(j));
            }
        }
        //System.out.println(head);
        return head==0;
    }
    static Scanner sc = new Scanner(System.in);
    public static void main(String[] args)
    {      
        //test();
        int R=sc.nextInt(); //  ≤ R ≤ 500
        for (int i=0; i<R; i++) {
            int L=sc.nextInt();
            String r=sc.next(); // 1 ≤ length of each report ≤ 500
            System.out.println(check(r)?"Valid":"Invalid");
        }
    }
}
/*
6
18
..H..T...HTH....T.
3
...
10
H..H..T..T
2
HT
11
.T...H..H.T
7
H..T..H
Valid
Valid
Invalid
Valid
Invalid
Invalid
*/