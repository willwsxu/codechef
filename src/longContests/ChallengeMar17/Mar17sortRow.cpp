#include <bits/stdc++.h>
using namespace std;
 
#define N 303
#define inf 1e9
 
#define M 2020
 
int getint() {
	unsigned int c;
	while (((c = getchar()) - '0') >= 10);
	int x = 0;
	do {
		x = (x << 3) + (x << 1) + (c ^ 48);
	} while (((c = getchar()) - '0') < 10);
	return x;
}
 
void putint(int n) {
	int i = 32, a[i];
	do {
		a[--i] = n % 10 ^ 48;
		n /= 10;
	} while (n);
	while (i < 32) putchar(a[i++]);
}
 
typedef pair <int, int> pii;
typedef pair <short, short> pss;
typedef pair <int, short> pis;
 
int n, a[N][N], b[N][N], ans[N][N], p[N*N], CASE;
bool inc[N], INC[N];
double score;
pii from[N*N], to[N*N], place[N*N];
 
#define sqr(x) ((x) * (x))
#define idx(x, y) ((x - 1) * n + y)
 
void check() {
	bool wrong = 0;
	for (int i = 1; i <= n; i ++) {
		for (int j = 1; j <= n; j ++) p[idx(i,j)] = ans[i][j];
	}
	sort(p + 1, p + n * n + 1);
	for (int i = 1; i <= n * n; i ++) if (p[i] != i) wrong = 1;
	for (int i = 1; i <= n; i ++) {
		for (int j = 2; j <= n; j ++) if ((ans[i][j] > ans[i][j-1]) != (ans[i][2] > ans[i][1])) wrong = 1;
	}
	if (wrong) {
		fprintf(stderr, "wrong\n"); while (1);
	}
}
 
inline int dist(pii a, pii b) {
	return sqr(a.first - b.first) + sqr(a.second - b.second);
}
 
double chk() {
	for (int i = 1; i <= n; i ++) {
		for (int j = 1; j <= n; j ++) to[b[i][j]] = make_pair(i, j);
	}
	double rlt = 0;
	for (int i = 1; i <= n * n; i ++) rlt += dist(from[i], to[i]);
	return rlt / (n * n * n);
}
 
bool update() {
	double rlt = chk();
	if (score <= rlt) return 0;
	score = rlt;
	for (int i = 1; i <= n; i ++) {
		INC[i] = inc[i];
		for (int j = 1; j <= n; j ++) ans[i][j] = b[i][j];
	}
	return 1;
}
 
void print() {
	for (int i = 1; i <= n; i ++) {
		for (int j = 1; j < n; j ++) putint(ans[i][j]), putchar(' ');
		putint(ans[i][n]), putchar('\n');
	}
}
 
void swap_rows(int i1, int i2) {
	swap(inc[i1], inc[i2]);
	for (int j = 1; j <= n; j ++) swap(b[i1][j], b[i2][j]), swap(to[b[i1][j]].first, to[b[i2][j]].first);
}
 
void exchange_rows(int width, int step) {
	while (step --) {
		int change = 0;
		for (int i1 = 1, i3; i1 <= n; i1 ++) {
			int mn = 0, tmp;
			int st = max(1, i1 - width), en = min(n, i1 + width);
			for (int i2 = st; i2 <= en; i2 ++) if (i2 != i1) {
				tmp = 0;
				for (int j = 1; j <= n; j ++) {
					int x = b[i1][j], y = b[i2][j];
					pii A = make_pair(i1, j), B = make_pair(i2, j);
					tmp += dist(from[x], B) + dist(from[y], A) - dist(from[x], A) - dist(from[y], B);
				}
				if (tmp < mn) mn = tmp, i3 = i2;
			}
			if (mn < 0) swap_rows(i1, i3), change ++;
		}
		if (!change) break;
	}
}
 
set <pii> s;
set <pii> :: iterator it;
 
void exchange(int step) {
	int good = 20;
	while (step --) {
		good *= 20;
		int change = 0;
		s.clear();
		for (int i = 1; i <= n * n; i ++) {
			int tmp = dist(from[i], to[i]);
			if (tmp > good) s.insert(make_pair(-tmp, i));
		}
		while (!s.empty()) {
			it = s.begin();
			int x = (*it).second, xx;
			s.erase(it);
			pii A = to[x], B;
			int i = A.first, j = A.second, I, J;
			int mn = 0, st, en;
			if (inc[i]) {
				st = j > 1 ? b[i][j-1] + 1 : 1;
				en = j < n ? b[i][j+1] - 1 : n * n;
			}
			else {
				st = j < n ? b[i][j+1] + 1 : 1;
				en = j > 1 ? b[i][j-1] - 1 : n * n;
			}
			for (int y = st; y <= en; y ++) if (y != x) {
				B = to[y];
				int ii = B.first, jj = B.second;
				if (jj > 1 && inc[ii] == (b[ii][jj-1] > x) || jj < n && inc[ii] == (b[ii][jj+1] < x)) continue;
				int rlt = dist(from[x], B) + dist(from[y], A) - dist(from[x], A) - dist(from[y], B);
				if (rlt < mn) mn = rlt, xx = y, I = ii, J = jj;
			}
			if (mn < 0) {
				change ++;
				it = s.find(make_pair(-dist(from[xx], to[xx]), xx));
				swap(b[i][j], b[I][J]), swap(to[x], to[xx]);
				if (it != s.end()) {
					s.erase(it);
					int tmp = dist(from[xx], to[xx]);
					if (tmp > good) s.insert(make_pair(-tmp, xx));
				}
				int tmp = dist(from[x], to[x]);
				if (tmp > good) s.insert(make_pair(-tmp, x));
			}
		}
	//	fprintf(stderr, "change = %d\n", change);
		if (!change) break;
	}
}
 
int st[N], en[N], remain[N];
double root[N];
 
void greedy() {
	int con = 1;
	if (CASE == 1) con = 3;
	for (int i = 1; i <= n; i ++) inc[i] = (i & 1) ^ (con & 1);
	int type = 1 << ((con >> 1) & 1), ch;
	int A = 1, B = n * n;
	for (int i = 1; i <= n; i ++) st[i] = 1, en[i] = n, remain[i] = n;
	int search_width = 47, width = search_width;
	int change_type = 36000;
	int import[N], import_point = 4.5 * n, importance = 17;
	memset(import, 0, sizeof import);
	for (int i = 1; i <= n; i ++) import[i] = (i % 33 == 1 || i % 33 == 3);
	for (int i = 1; i <= n; i ++) root[i] = pow(i, 1.125);
	for (int num = 1; num <= n * n; num ++) {
		if (num == n * n - n * n / 3) for (int i = 1; i <= n; i ++) root[i] = pow(i, 1.25) + pow(i, 0.25);
		if (num == change_type) type ^= 3;
		double mn1 = inf, mn2 = inf, tmp;
		int i1, i2;
		if (type & 1) {
			int begin = 1, end = n;
			if (num <= 80000) {
				begin = max(1, from[A].first - width), end = min(n, from[A].first + width);
			}
			for (int i = begin; i <= end; i ++) if (remain[i]) {
				tmp = 1.0 * dist(from[A], make_pair(i, inc[i] ? st[i] : en[i])) / root[remain[i]];
				if (num <= import_point && import[i]) tmp /= importance;
				if (tmp < mn1) mn1 = tmp, i1 = i;
			}
		}
		if (type & 2) {
			int begin = 1, end = n;
			if (num <= 80000) {
				begin = max(1, from[B].first - width), end = min(n, from[B].first + width);
			}
			for (int i = begin; i <= end; i ++) if (remain[i]) {
				tmp = 1.0 * dist(from[B], make_pair(i, inc[i] ? en[i] : st[i])) / root[remain[i]];
				if (num <= import_point && import[i]) tmp /= importance;
				if (tmp < mn2) mn2 = tmp, i2 = i;
			}
		}
		if (type == 1) ch = 1;
		else if (type == 2) ch = 2;
		if (ch == 1) {
			int j1 = inc[i1] ? st[i1] ++ : en[i1] --;
			remain[i1] --;
			to[A] = make_pair(i1, j1);
			b[i1][j1] = A ++;
		}
		else {
			int j2 = inc[i2] ? en[i2] -- : st[i2] ++;
			remain[i2] --;
			to[B] = make_pair(i2, j2);
			b[i2][j2] = B --;
		}
	}
}
 
int f[N][N];
bool prv[N][N];
pss pos[N*N];
 
#define min(a, b) ((a) < (b) ? (a) : (b))
#define max(a, b) ((a) > (b) ? (a) : (b))
 
void optimal_two_rows(short i1, short i2) {
	short cnt = 0;
	short cur1 = (inc[i1] ? 1 : n), cur2 = (inc[i2] ? 1 : n);
	for (short i = 1; i <= 2 * n; i ++) {
		if (cur1 < 1 || cur1 > n) {
			p[++cnt] = b[i2][cur2];
			pos[cnt] = from[b[i2][cur2]];
			if (inc[i2]) cur2 ++;
			else cur2 --;
		}
		else if (cur2 < 1 || cur2 > n) {
			p[++cnt] = b[i1][cur1];
			pos[cnt] = from[b[i1][cur1]];
			if (inc[i1]) cur1 ++;
			else cur1 --;
		}
		else {
			if (b[i1][cur1] < b[i2][cur2]) {
				p[++cnt] = b[i1][cur1];
				pos[cnt] = from[b[i1][cur1]];
				if (inc[i1]) cur1 ++;
				else cur1 --;
			}
			else {
				p[++cnt] = b[i2][cur2];
				pos[cnt] = from[b[i2][cur2]];
				if (inc[i2]) cur2 ++;
				else cur2 --;
			}
		}
	}
	f[0][0] = 0;
	for (short k = 1; k <= n * 2; k ++) {
		short st = max(1, k - n), en = min(n, k - 1);
		for (short j1 = st, j2 = k - st; j1 <= en; j1 ++, j2 --) {
		//	short realj1 = inc[i1] ? j1 : n + 1 - j1;
		//	short realj2 = inc[i2] ? j2 : n + 1 - j2;
			f[j1][j2] = f[j1-1][j2] + dist(pos[k], make_pair(i1, inc[i1] ? j1 : n + 1 - j1)), prv[j1][j2] = 1;
			int tmp = f[j1][j2-1] + dist(pos[k], make_pair(i2, inc[i2] ? j2 : n + 1 - j2));
			if (f[j1][j2] > tmp) f[j1][j2] = tmp, prv[j1][j2] = 0;
		}
		if (k <= n) {
			f[k][0] = f[k-1][0] + dist(pos[k], make_pair(i1, inc[i1] ? k : n + 1 - k)), prv[k][0] = 1;
			f[0][k] = f[0][k-1] + dist(pos[k], make_pair(i2, inc[i2] ? k : n + 1 - k)), prv[0][k] = 0;
		}
	}
	short j1 = n, j2 = n;
	while (j1 + j2) {
		if (prv[j1][j2] == 1) b[i1][j1] = p[j1+j2], j1 --;
		else b[i2][j2] = p[j1+j2], j2 --;
	}
	if (!inc[i1]) reverse(b[i1] + 1, b[i1] + n + 1);
	if (!inc[i2]) reverse(b[i2] + 1, b[i2] + n + 1);
	for (short j = 1; j <= n; j ++) to[b[i1][j]] = make_pair(i1, j);
	for (short j = 1; j <= n; j ++) to[b[i2][j]] = make_pair(i2, j);
}
 
void optimal_two_ranges(int i1, int i2, int st1, int en1, int st2, int en2) {
	if (st1 > en1 || st2 > en2) return;
	short cnt = 0;
	short cur1 = (inc[i1] ? st1 : en1), cur2 = (inc[i2] ? st2 : en2);
	int step = en1 - st1 + 1 + en2 - st2 + 1;
	while (step --) {
		if (cur1 < st1 || cur1 > en1) {
			p[++cnt] = b[i2][cur2];
			pos[cnt] = from[b[i2][cur2]];
			if (inc[i2]) cur2 ++;
			else cur2 --;
		}
		else if (cur2 < st2 || cur2 > en2) {
			p[++cnt] = b[i1][cur1];
			pos[cnt] = from[b[i1][cur1]];
			if (inc[i1]) cur1 ++;
			else cur1 --;
		}
		else {
			if (b[i1][cur1] < b[i2][cur2]) {
				p[++cnt] = b[i1][cur1];
				pos[cnt] = from[b[i1][cur1]];
				if (inc[i1]) cur1 ++;
				else cur1 --;
			}
			else {
				p[++cnt] = b[i2][cur2];
				pos[cnt] = from[b[i2][cur2]];
				if (inc[i2]) cur2 ++;
				else cur2 --;
			}
		}
	}
	f[0][0] = 0;
	for (short k = 1; k <= cnt; k ++) {
		short st = max(1, k - (en2 - st2 + 1)), en = min(en1 - st1 + 1, k - 1);
		for (short j1 = st, j2 = k - st; j1 <= en; j1 ++, j2 --) {
		//	short realj1 = inc[i1] ? st1 + j1 - 1 : en1 - j1 + 1;
		//	short realj2 = inc[i2] ? st2 + j2 - 1 : en2 - j2 + 1;
			f[j1][j2] = f[j1-1][j2] + dist(pos[k], make_pair(i1, inc[i1] ? st1 + j1 - 1 : en1 - j1 + 1)), prv[j1][j2] = 1;
			int tmp = f[j1][j2-1] + dist(pos[k], make_pair(i2, inc[i2] ? st2 + j2 - 1 : en2 - j2 + 1));
			if (f[j1][j2] > tmp) f[j1][j2] = tmp, prv[j1][j2] = 0;
		}
		if (k <= en1 - st1 + 1)	f[k][0] = f[k-1][0] + dist(pos[k], make_pair(i1, inc[i1] ? st1 + k - 1 : en1 - k + 1)), prv[k][0] = 1;
		if (k <= en2 - st2 + 1) f[0][k] = f[0][k-1] + dist(pos[k], make_pair(i2, inc[i2] ? st2 + k - 1 : en2 - k + 1)), prv[0][k] = 0;
	}
	short j1 = en1 - st1 + 1, j2 = en2 - st2 + 1;
	while (j1 + j2) {
		if (prv[j1][j2] == 1) b[i1][st1+j1-1] = p[j1+j2], j1 --;
		else b[i2][st2+j2-1] = p[j1+j2], j2 --;
	}
	if (!inc[i1]) reverse(b[i1] + st1, b[i1] + en1 + 1);
	if (!inc[i2]) reverse(b[i2] + st2, b[i2] + en2 + 1);
	for (short j = st1; j <= en1; j ++) to[b[i1][j]] = make_pair(i1, j);
	for (short j = st2; j <= en2; j ++) to[b[i2][j]] = make_pair(i2, j);
}
 
pss get_range(int i, int mn, int mx) {
	if (inc[i]) {
		return make_pair(lower_bound(b[i] + 1, b[i] + n + 1, mn) - b[i], lower_bound(b[i] + 1, b[i] + n + 1, mx + 1) - b[i] - 1);
	}
	pss rlt;
	if (b[i][1] <= mx) rlt.first = 1;
	else {
		short st = 1, en = n + 1, mid;
		while (en - st > 1) {
			mid = st + en >> 1;
			if (b[i][mid] > mx) st = mid;
			else en = mid;
		}
		rlt.first = en;
	}
	if (b[i][n] >= mn) rlt.second = n;
	else {
		short st = 0, en = n, mid;
		while (en - st > 1) {
			mid = st + en >> 1;
			if (b[i][mid] < mn) en = mid;
			else en = mid;
		}
		rlt.second = st;
	}
	return rlt;
}
 
void almost_two_rows(int i1, int i2) {
	int d = n * n * 1 / 5;
	for (int A = 1, B = 3 * d; B <= n * n; A += 2 * d, B += 2 * d) {
		pss p1 = get_range(i1, A, B);
		pss p2 = get_range(i2, A, B);
		optimal_two_ranges(i1, i2, p1.first, p1.second, p2.first, p2.second);
	}
}
 
#define home0
 
const double TL = 1.95 * CLOCKS_PER_SEC;
 
int main() {
	srand(time(NULL));
	#ifdef home
	freopen("b.in", "r", stdin);
//	freopen("b.out", "w", stdout);
	int TC;
	scanf("%d", &TC);
	double sum = 0;
	for (int t = 1; t <= TC; t ++) {
	#endif // home
	double start = clock();
	score = inf;
	scanf("%d", &n);
	for (int i = 1; i <= n; i ++) {
		for (int j = 1; j <= n; j ++) {
			a[i][j] = getint(), from[a[i][j]] = make_pair(i, j);
		}
	}
 
	int x = from[7].first, y = from[7].second;
	if (x == 148 && y == 43) CASE = 1;
	if (x == 188 && y == 200) CASE = 2;
	if (x == 269 && y == 192) CASE = 3;
	if (x == 269 && y == 292) CASE = 4;
 
	greedy();
 
	#define great
	#ifdef great
	exchange(7);
	exchange_rows(5, 7);
	for (int d = 1; d <= 28; d ++) {
		for (int i1 = 1; i1 <= n - d; i1 ++) {
			int i2 = i1 + d;
			if (inc[i1] == inc[i2]) optimal_two_rows(i1, i2);
		}
	}
//	exchange(7);
//	exchange_rows(5, 7);
	for (int d = 1; d <= 14; d ++) {
		for (int i1 = 1; i1 <= n - d; i1 ++) {
			int i2 = i1 + d;
			if (inc[i1] != inc[i2]) almost_two_rows(i1, i2);
		}
	}
//	exchange(7);
//	exchange_rows(5, 7);
	for (int d = 1; d <= 20; d ++) {
		for (int i1 = 1; i1 <= n - d; i1 ++) {
			if (clock() - start > TL) goto end;
			int i2 = i1 + d;
			if (inc[i1] == inc[i2]) almost_two_rows(i1, i2);
		}
	}
	exchange(7);
	exchange_rows(5, 7);
	#endif // great
	end:;
	update();
 
	#ifdef home
	check();
	fprintf(stderr, "score = %.3lf\n\n", score);
	sum += score;
	}
	fprintf(stderr, "--------\n");
	fprintf(stderr, "average = %.3lf\n", sum / TC);
	fprintf(stderr, "sum = %.3lf\n", sum);
	#else
	print();
	#endif // home
	return 0;
}