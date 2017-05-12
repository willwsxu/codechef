
package codechef;


// 1 based array to store segemment tree for Range Sum Query
class SegTree
{
    int sum[];
   SegTree(int N) 
   {
       sum=new int[2*N+1];
       buildSum(1, 0, N-1);
   }
   int buildSum(int node, int first, int last)
   {
       if (first==last)
           sum[node]=compute(first);
       else {
           int mid = (first+last)/2;
           sum[node]=buildSum(2*node, first, mid)+buildSum(2*node+1, mid+1, last);
       }
       return sum[node];
   }
   int compute(int ind)
   {
       return 0;
   }
}
