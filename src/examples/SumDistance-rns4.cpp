#include<bits/stdc++.h>
using namespace std;
 
#define N 100010
 
int a[N], b[N], c[N], id[N];
long long x[N], y[N], z[N], arr[N], brr[N];
int n, T;
 
const long long INF = 1e15;
 
 
int sz, tree[N];
void Init(int n){ sz = n + 1; memset(tree, 0, sz * sizeof(int)); }
void add(int k){ for(; k < sz; k += k & -k) tree[k] ++; }
int sum(int k){ int res = 0; for(; k > 0; k -= k & -k) res += tree[k]; return res; }
 
 
bool cmp(int i, int j){
    if( (x[i] - y[i]) != (x[j] - y[j]) ) return (x[i] - y[i]) < (x[j] - y[j]);
    return i < j;
}
 
long long calc(int st, int en){
    if(st == en) return 0;
    if(st == en - 1) return a[st];
    if(st == en - 2) return a[st] + a[st+1] + b[st];
    if(st == en - 3) return a[st] + a[st+1] + a[st+2] + b[st] + b[st+1] + c[st];
 
 
    int mid = (st + en) / 2;
    long long rlt = calc(st, mid - 2) + calc(mid + 2, en);
    int uu = mid - 1 - st, vv = en - mid - 1;
 
    x[mid - 1] = 0; x[mid] = x[mid+1] = INF;
    y[mid] = 0; y[mid-1] = a[mid-1]; y[mid+1] = INF;
    z[mid + 1] = 0; z[mid-1] = b[mid-1], z[mid] = a[mid];
 
    int cnt = 0;
    for (int i = mid - 2; i >= st; i --) {
        x[i] = min(min(x[i+1]+a[i], x[i+2]+b[i]), x[i+3]+c[i]);
        y[i] = min(min(y[i+1]+a[i], y[i+2]+b[i]), y[i+3]+c[i]);
        z[i] = min(min(z[i+1]+a[i], z[i+2]+b[i]), z[i+3]+c[i]);
        arr[cnt] = x[i] - z[i];
        brr[cnt] = y[i] - z[i];
        id[cnt ++] = i;
        rlt += x[i] + y[i] + z[i];
        rlt += z[i] * vv;
    }
 
    x[mid] = -a[mid-1]; x[mid+1] = -b[mid-1];
    y[mid-1] = -INF; y[mid+1] = -a[mid];
    z[mid-1] = z[mid] = -INF;
    for (int i = mid + 2; i <= en; i ++) {
        x[i] = max(max(x[i-1]-a[i-1], x[i-2]-b[i-2]), x[i-3]-c[i-3]);
        y[i] = max(max(y[i-1]-a[i-1], y[i-2]-b[i-2]), y[i-3]-c[i-3]);
        z[i] = max(max(z[i-1]-a[i-1], z[i-2]-b[i-2]), z[i-3]-c[i-3]);
        arr[cnt] = x[i] - z[i];
        brr[cnt] = y[i] - z[i];
        id[cnt ++] = i;
        rlt -= x[i] + y[i] + z[i];
        rlt -= z[i] * uu;
    }
    rlt += b[mid-1] + a[mid-1] + a[mid];
 
    sort(id, id + cnt, cmp);
    sort(arr, arr + cnt);
    int u = unique(arr, arr + cnt) - arr;
    sort(brr, brr + cnt);
    int v = unique(brr, brr + cnt) - brr;
 
    Init(cnt);
    for(int i = 0; i < cnt; i ++){
        int cur = id[i];
        int pos = lower_bound(arr, arr + u, x[cur] - z[cur]) - arr + 1;
        if(cur < mid)  add(pos);
        else rlt -=  sum(pos) * (x[cur] - z[cur]);
    }
 
    Init(cnt);
    for(int i = cnt; i --; ){
        int cur = id[i];
        int pos = lower_bound(arr, arr + u, x[cur] - z[cur]) - arr + 1;
        if(cur > mid)  add(pos);
        else rlt += ( sum(cnt) -  sum(pos-1)) * (x[cur] - z[cur]);
    }
 
    Init(cnt);
    for(int i = 0; i < cnt; i ++){
        int cur = id[i];
        int pos = lower_bound(brr, brr + v, y[cur] - z[cur]) - brr + 1;
        if(cur > mid)  add(pos);
        else rlt += ( sum(cnt) -  sum(pos-1)) * (y[cur] - z[cur]);
    }
 
    Init(cnt);
    for(int i = cnt; i --; ){
        int cur = id[i];
        int pos = lower_bound(brr, brr + v, y[cur] - z[cur]) - brr + 1;
        if(cur < mid)  add(pos);
        else rlt -=  sum(pos) * (y[cur] - z[cur]);
    }
 
    return rlt;
}
 
int main(){
 
    scanf("%d", &T);
    while(T --) {
        scanf("%d", &n);
        for (int i = 1; i < n; i ++) scanf("%d", &a[i]);
        for (int i = 1; i < n - 1; i ++) {
            scanf("%d", &b[i]);
            b[i] = min(b[i], a[i] + a[i+1]);
        }
        for (int i = 1; i < n - 2; i ++) {
            scanf("%d", &c[i]);
            c[i] = min(c[i], min(a[i] + b[i+1], b[i] + a[i+2]));
        }
        printf("%lld\n", calc(1, n));
    }
}