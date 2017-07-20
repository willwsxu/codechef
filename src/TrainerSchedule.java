
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import static java.lang.System.out;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.StringTokenizer;


class TrainerSchedule {
    class Trainer
    {
        int sDay; // start day
        int lectures; // 1 ≤ Di, Ti ≤ D
        int sadness;  //1 ≤ Si ≤ 10^5
    }
    int D, N;
    TrainerSchedule()
    {
        N=sc.ni(); //1 ≤ N, D ≤ 10^5
        D=sc.ni();
        List<Trainer> trainers=new ArrayList<>(N);
        //long sadness=0;
        for (int i=0; i<N; i++) {
            Trainer tr=new Trainer();
            tr.sDay=sc.ni();
            tr.lectures=sc.ni();
            tr.sadness=sc.ni();
            trainers.add(tr);
            /*
            int days=D-tr[i].sDay+1;
            if (tr[i].lectures>days) {
                sadness += (long)tr[i].sadness*(tr[i].lectures-days);
            }*/
        }
        out.println(scheduling(trainers));
    }
    
    long scheduling(List<Trainer> trainers)
    {
        trainers.sort((c1,c2)->c1.sDay-c2.sDay);  // sort by starting day
        int n=0;
        // sort trainers by sadness in reverse order
        PriorityQueue<Trainer> pq = new PriorityQueue<>((c1,c2)->c2.sadness-c1.sadness);
        for (int d=1; d<=D; d++) {
            for (; n<trainers.size() && trainers.get(n).sDay==d; n++) {
                pq.add(trainers.get(n));
            }
            if (!pq.isEmpty()) {
                Trainer tr = pq.peek();
                if (tr.lectures==1)
                    pq.poll();
                else
                    tr.lectures--;
            }
        }
        long sadness=0;
        for (Trainer tr : pq)
            sadness += (long)tr.sadness*tr.lectures;
        return sadness;
    }
    static MyScanner sc=new MyScanner();
           
    public static void main(String[] args)
    {      
        int T = sc.nextInt();  // 1 ≤ T ≤ 10
        while (T-->0)
            new TrainerSchedule();
    } 
}

// credit to http://codeforces.com/blog/entry/7018
class MyScanner {
    BufferedReader br;
    StringTokenizer st;

    MyScanner(String f)
    {
        try {
            br = new BufferedReader(new FileReader(new File(f)));
        } catch (IOException e)
        {
            out.println("MyScanner bad file "+f);
        }
    }
    public MyScanner() {
        br = new BufferedReader(new InputStreamReader(System.in));
    }

    String next() {
        while (st == null || !st.hasMoreElements()) {
            try {
                st = new StringTokenizer(br.readLine());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return st.nextToken();
    }

    int nextInt() {
        return Integer.parseInt(next());
    }

    long nextLong() {
        return Long.parseLong(next());
    }

    double nextDouble() {
        return Double.parseDouble(next());
    }

    String nextLine(){
        String str = "";
        try {
           str = br.readLine();
        } catch (IOException e) {
           e.printStackTrace();
        }
        return str;
    }
    
    public int ni()
    {
        return nextInt();
    }     
    public long nl()
    {
        return nextLong();
    }   
    public int[] ria(int N) { // read int array
        int L[]=new int[N];
        for (int i=0; i<N; i++)
            L[i]=nextInt();
        return L;
    }
    public long[] rla(int N) { // read long array
        long L[]=new long[N];
        for (int i=0; i<N; i++)
            L[i]=nextLong();
        return L;
    }
}