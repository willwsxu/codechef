package longContests.july17;

/* N trainers, each wants to teach T lectures
 * There can be only one lecture per day, Trainer arrives at different day
 * A train will be sad if he can complete all his lectures.
 * find a way to minimize sadness of all trainers
*/
import static java.lang.System.out;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

// IPCTRAIN greedy, heap sort
// sort trainer by arriving day, add eligible trainer to heap sort by sadness
// each day pick the train with highest sadness (greedy)
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
        for (int i=0; i<N; i++) {
            Trainer tr=new Trainer();
            tr.sDay=sc.ni();
            tr.lectures=sc.ni();
            tr.sadness=sc.ni();
            trainers.add(tr);
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
