import java.io.*;
import java.util.*;
 
 
public class Main {
 
    static Reader input = new Reader();
    static BufferedWriter log = new BufferedWriter(new OutputStreamWriter(System.out));
 
    static int grid[][] = {{0, 0, 1, -1, 1, 1, -1, -1}, {1, -1, 0, 0, 1, -1, 1, -1}};
    static int dp2[];
    static int dp[][];
    static int mod = 998244353;
    static int nu;
    static long res;
    static ArrayList<Integer> conCom;
    static int num[], lowesLink[];
    static boolean inStack[];
    static Stack<Integer> st;
    static LinkedList<ArrayList<Integer>> stronConCom;
    static boolean cycle[], vi[];
    static ArrayList<Integer> g[];
    static int stop;
 
    public static void main(String[] args) throws IOException {
        //Reader input = new Reader("inp.txt");
        //BufferedWriter log = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("output.txt")));
        int test = input.nextInt();
        next:
        for (int te = 1; te <= test; te++) {
            int n = input.nextInt();
            int x = input.nextInt();
            int y = input.nextInt();
            boolean ca = true;
            for (int i = 0; i < n - 1; i++) {
                int xx = input.nextInt();
                int yy = input.nextInt();
                if (xx >= x) {
                    if (yy >= y) {
                        ca = false;
                    }
                }
            }
            if (ca) {
                log.write(x + "\n");
            } else {
                log.write("-1\n");
            }
        }
        log.flush();
    }
 
    public static int[] bfs(int node) {
        Queue<Integer> q = new LinkedList<>();
        q.add(node);
        int distances[] = new int[g.length];
        Arrays.fill(distances, -1);
        distances[node] = 0;
        while (!q.isEmpty()) {
            ArrayList<Integer> nodes = g[q.peek()];
            int cost = distances[q.poll()];
            for (Integer node1 : nodes) {
                if (distances[node1] == -1) {
                    q.add(node1);
                    distances[node1] = cost + 1;
                }
            }
        }
        return distances;
    }
 
    static void tarjan(int node, int par) {
        num[node] = lowesLink[node] = nu++;
        st.add(node);
        inStack[node] = true;
        for (Integer ch : g[node]) {
            if (ch == par) continue;
            if (num[ch] == 0) {
                tarjan(ch, node);
                lowesLink[node] = Math.min(lowesLink[node], lowesLink[ch]);
            } else if (inStack[ch]) {
                lowesLink[node] = Math.min(lowesLink[node], num[ch]);
            }
        }
        if (lowesLink[node] == num[node]) {
            int x = -1;
            stronConCom.add(new ArrayList<>());
            while (x != node) {
                x = st.pop();
                inStack[x] = false;
                stronConCom.getLast().add(x);
            }
        }
    }
 
    static long add(long a, long b) {
        a += b;
        if (a >= mod) {
            a -= mod;
        }
        return a;
    }
 
    static long mul(long a, long b) {
        return (long) ((long) ((a % mod) * (b % mod)) % mod);
    }
 
    static long modinv(long x) {
        return fast_pow(x, mod - 2, mod);
    }
 
    static long Div(long x, long y) {
        return mul(x, modinv(y));
 
    }
 
    static long mod(long a, long b) {
        long r = a % b;
        return r < 0 ? r + b : r;
    }
 
    static long Sub(long x, long y) {
        long z = x - y;
        if (z < 0) {
            z += mod;
        }
        return z;
    }
 
    static long fast_pow(long a, long p, long mod) {
        long res = 1;
        while (p > 0) {
            if (p % 2 == 0) {
                a = ((a % mod) * (a % mod)) % mod;
                p /= 2;
            } else {
                res = ((res % mod) * (a % mod)) % mod;
                p--;
            }
        }
        return res;
    }
 
 
    public static pair[] dijkstra(int node, ArrayList<pair> a[]) {
        PriorityQueue<tri> q = new PriorityQueue<>(new Comparator<tri>() {
            @Override
            public int compare(tri o1, tri o2) {
                if (o1.y > o2.y) {
                    return 1;
                } else if (o1.y < o2.y) {
                    return -1;
                } else {
                    return 0;
                }
            }
        });
        q.add(new tri(node, 0, -1));
        pair distance[] = new pair[a.length];
        while (!q.isEmpty()) {
            tri p = q.poll();
            int cost = p.y;
            if (distance[p.x] != null) {
                continue;
            }
            distance[p.x] = new pair(p.pa, cost);
            ArrayList<pair> nodes = a[p.x];
            for (pair node1 : nodes) {
                if (distance[node1.x] == null) {
                    tri pa = new tri(node1.x, cost + node1.y, p.x);
                    q.add(pa);
                }
            }
        }
        return distance;
    }
 
    static class tri {
 
        int x;
        int y;
        int pa;
 
        public tri(int x, int y, int pa) {
            this.x = x;
            this.y = y;
            this.pa = pa;
        }
 
    }
 
    static class SparseTable {
        int[][] st; // Sparse table
        int[] log; // Logarithm lookup table
 
        public SparseTable(int[] arr) {
            int n = arr.length;
            int logn = (int) (Math.log(n) / Math.log(2)) + 1;
 
            st = new int[n][logn];
            log = new int[n + 1];
 
            // Fill the logarithm lookup table
            for (int i = 2; i <= n; i++) {
                log[i] = log[i / 2] + 1;
            }
 
            // Fill the first column of the sparse table
            for (int i = 0; i < n; i++) {
                st[i][0] = arr[i];
            }
 
            // Compute the sparse table
            for (int j = 1; j < logn; j++) {
                for (int i = 0; i + (1 << j) <= n; i++) {
                    st[i][j] = Math.min(st[i][j - 1], st[i + (1 << (j - 1))][j - 1]);
                }
            }
        }
 
        public int query(int l, int r) {
            int k = log[r - l + 1];
            return Math.min(st[l][k], st[r - (1 << k) + 1][k]);
        }
    }
 
    static void dfs(int node, ArrayList<Integer> g[], ArrayList<Integer> con, boolean vi[]) {
        vi[node] = true;
        con.add(node);
        for (Integer x : g[node]) {
            if (!vi[x]) {
                dfs(x, g, con, vi);
            }
        }
    }
 
    static long sqrt(long v) {
        long max = (long) 4e9;
        long min = 0;
        long ans = 0;
        while (max >= min) {
            long mid = (max + min) / 2;
            if (mid * mid > v) {
                max = mid - 1;
            } else {
                ans = mid;
                min = mid + 1;
            }
        }
        return ans;
    }
 
    static long cbrt(long v) {
        long max = (long) 3e6;
        long min = 0;
        long ans = 0;
        while (max >= min) {
            long mid = (max + min) / 2;
            if (mid * mid > v) {
                max = mid - 1;
            } else {
                ans = mid;
                min = mid + 1;
            }
        }
        return ans;
    }
 
    static int revI(int n) {
        int re = 0;
        while (n > 0) {
            re = re * 10 + n % 10;
            n /= 10;
        }
        return re;
    }
 
    static class Edge {
        int u, v;
        long cost;
 
        public Edge(int u, int v, long cost) {
            this.u = u;
            this.v = v;
            this.cost = cost;
        }
 
        public String toString() {
            return u + " " + v + " " + cost;
        }
    }
 
    static class SegmentTree {
 
        int size;
        int seg[];
        int arr[];
 
        public SegmentTree(int size, int a[]) {
            this.size = size;
            seg = new int[size * 4];
            build(1, 1, size, a);
        }
 
        private void build(int idx, int s, int e, int a[]) {
            if (s == e) {
                seg[idx] = a[s];
                return;
            }
            build(idx << 1, s, (s + e) / 2, a);
            build(idx << 1 | 1, (s + e) / 2 + 1, e, a);
            seg[idx] = seg[idx << 1] + seg[idx << 1 | 1];
        }
 
        void updateIndex(int idx, int s, int e, int ind, int val) {
            if (ind < s || ind > e) {
                return;
            }
            if (s == ind && ind == e) {
                seg[idx] += val;
                return;
            }
            updateIndex(idx << 1, s, (s + e) / 2, ind, val);
            updateIndex(idx << 1 | 1, (s + e) / 2 + 1, e, ind, val);
            seg[idx] = seg[idx << 1] + seg[idx << 1 | 1];
        }
 
        int sumInRange(int idx, int s, int e, int l, int r) {
            if ((l > e) || s > r) {
                return 0;
            }
            if (s >= l && e <= r) {
                return seg[idx];
            }
            return sumInRange(idx << 1, s, (s + e) / 2, l, r) + sumInRange(idx << 1 | 1, (s + e) / 2 + 1, e, l, r);
        }
    }
 
    static class Tree {
        private ArrayList<Integer> distinctElements;
        private SegmentTree seg;
        private int size;
 
        public Tree(int a[]) {
            distinctElements = new ArrayList<>();
            Arrays.sort(a);
            distinctElements.add(a[0]);
            for (int i = 1; i < a.length; i++) {
                if (a[i] != distinctElements.get(distinctElements.size() - 1)) {
                    distinctElements.add(a[i]);
                }
            }
            Collections.sort(distinctElements);
            size = distinctElements.size() + 5;
            seg = new SegmentTree(size);
        }
 
        public void add(int val) {
            int idx = getIndex(val);
            seg.updateIndex(1, 1, size, idx, 1);
        }
 
        public void remove(int val) {
            int idx = getIndex(val);
            seg.updateIndex(1, 1, size, idx, -1);
        }
 
        private int getIndex(int val) {
            int max = distinctElements.size() - 1;
            int min = 0;
            int ans = -1;
            while (max >= min) {
                int mid = (max + min) / 2;
                if (distinctElements.get(mid) == val) {
                    return mid + 2;
                } else if (distinctElements.get(mid) > val) {
                    max = mid - 1;
                } else {
                    min = mid + 1;
                }
            }
            return ans;
        }
 
        public int countNumbersGreaterThan(int val) {
            int idx = getIndex(val);
            return seg.sumInRange(1, 1, size, idx + 1, size);
        }
 
        public int countNumbersLessThan(int val) {
            int idx = getIndex(val);
            return seg.sumInRange(1, 1, size, 1, idx - 1);
        }
 
        private static class SegmentTree {
 
            int size;
            int seg[];
            int arr[];
 
            public SegmentTree(int size) {
                this.size = size;
                seg = new int[size * 4];
            }
 
            void updateIndex(int idx, int s, int e, int ind, int val) {
                if (ind < s || ind > e) {
                    return;
                }
                if (s == ind && ind == e) {
                    seg[idx] += val;
                    return;
                }
                updateIndex(idx << 1, s, (s + e) / 2, ind, val);
                updateIndex(idx << 1 | 1, (s + e) / 2 + 1, e, ind, val);
                seg[idx] = seg[idx << 1] + seg[idx << 1 | 1];
            }
 
            int sumInRange(int idx, int s, int e, int l, int r) {
                if ((l > e) || s > r) {
                    return 0;
                }
                if (s >= l && e <= r) {
                    return seg[idx];
                }
                return sumInRange(idx << 1, s, (s + e) / 2, l, r) + sumInRange(idx << 1 | 1, (s + e) / 2 + 1, e, l, r);
            }
        }
    }
 
 
    public static void prefixSum(ArrayList<Integer> a) {
        for (int i = 1; i < a.size(); i++) {
            a.set(i, a.get(i) + a.get(i - 1));
        }
    }
 
    public static void suffixSum(ArrayList<Integer> a) {
        for (int i = a.size() - 2; i > 0; i--) {
            a.set(i, a.get(i) + a.get(i + 1));
        }
    }
 
    public static void prefixSum(long[] a) {
        for (int i = 1; i < a.length; i++) {
            a[i] = a[i] + a[i - 1];
        }
    }
 
    public static void suffixSum(long[] a) {
        for (int i = a.length - 2; i > -1; i--) {
            a[i] = a[i] + a[i + 1];
        }
    }
 
    static class HashedString {
        // Change M and B if you want
        public static final long M = (long) 1e9 + 9;
        public static final long B = 9973;
 
        // pow[i] contains B^i % M
        private static ArrayList<Long> pow = new ArrayList<>();
 
        // pHash[i] is the hash of the first i characters of the given string
        private long[] pHash;
 
        public HashedString(String s) {
            if (pow.isEmpty()) {
                pow.add(1L);
            }
            while (pow.size() < s.length()) {
                pow.add((pow.get(pow.size() - 1) * B) % M);
            }
            pHash = new long[s.length() + 1];
            pHash[0] = 0;
            for (int i = 0; i < s.length(); i++) {
                pHash[i + 1] = ((pHash[i] * B) % M + s.charAt(i)) % M;
            }
        }
 
        public long getHash(int start, int end) {
            long rawVal = pHash[end + 1] - (pHash[start] * pow.get(end - start + 1));
            return (rawVal % M + M) % M;
        }
    }
 
    static class SegmentTreeMax {
 
        int size;
        long seg[];
        long lazy[];
        long arr[];
 
        public SegmentTreeMax(int size, long a[]) {
            this.size = size;
            arr = a;
            seg = new long[size * 4];
            lazy = new long[size * 4];
            build(1, 1, size, a);
        }
 
        private void build(int idx, int s, int e, long a[]) {
            if (s == e) {
                seg[idx] = a[s];
                return;
            }
            build(idx * 2, s, (s + e) / 2, a);
            build(idx * 2 + 1, (s + e) / 2 + 1, e, a);
            seg[idx] = seg[idx * 2] + seg[idx * 2 + 1];
        }
 
        void process(int idx, int s, int e) {
            seg[idx] += (e - s + 1) * lazy[idx];
            if (s < e) {
                lazy[idx * 2] += lazy[idx];
                lazy[idx * 2 + 1] += lazy[idx];
            }
            lazy[idx] = 0;
        }
 
        void updateIndex(int idx, int s, int e, int ind, long val) {
            if (ind < s || ind > e) {
                return;
            }
            if (s == ind && ind == e) {
                seg[idx] += val;
                return;
            }
            updateIndex(idx * 2, s, (s + e) / 2, ind, val);
            updateIndex(idx * 2 + 1, (s + e) / 2 + 1, e, ind, val);
            seg[idx] = seg[idx * 2] + seg[idx * 2 + 1];
        }
 
        void updateRange(int idx, int s, int e, int l, int r, long val) {
            process(idx, s, e);
            if ((l > e) || s > r) {
                return;
            }
            if (s >= l && e <= r) {
                lazy[idx] += val;
                process(idx, s, e);
                return;
            }
            updateRange(idx * 2, s, (s + e) / 2, l, r, val);
            updateRange(idx * 2 + 1, (s + e) / 2 + 1, e, l, r, val);
            seg[idx] = seg[idx * 2] + seg[idx * 2 + 1];
        }
 
        long sumInRange(int idx, int s, int e, int l, int r) {
            process(idx, s, e);
            if ((l > e) || s > r) {
                return 0;
            }
            if (s >= l && e <= r) {
                return seg[idx];
            }
            return sumInRange(idx * 2, s, (s + e) / 2, l, r) + sumInRange(idx * 2 + 1, (s + e) / 2 + 1, e, l, r);
        }
    }
 
    static class SegmentTreeMin {
 
        int size;
        int seg[];
        int lazy[];
 
        public SegmentTreeMin(int size, int a[]) {
            this.size = size;
            seg = new int[size * 4];
            lazy = new int[size * 4];
            build(1, 1, size, a);
        }
 
        private void build(int idx, int s, int e, int a[]) {
            if (s == e) {
                seg[idx] = a[s];
                return;
            }
            build(idx * 2, s, (s + e) / 2, a);
            build(idx * 2 + 1, (s + e) / 2 + 1, e, a);
            seg[idx] = Math.min(seg[idx * 2], seg[idx * 2 + 1]);
        }
 
        void process(int idx, int s, int e) {
            seg[idx] += lazy[idx];
            if (s < e) {
                lazy[idx * 2] += lazy[idx];
                lazy[idx * 2 + 1] += lazy[idx];
            }
            lazy[idx] = 0;
        }
 
        void updateIndex(int idx, int s, int e, int ind, int val) {
            if (ind < s || ind > e) {
                return;
            }
            if (s == ind && ind == e) {
                seg[idx] += val;
                return;
            }
            updateIndex(idx * 2, s, (s + e) / 2, ind, val);
            updateIndex(idx * 2 + 1, (s + e) / 2 + 1, e, ind, val);
            seg[idx] = Math.min(seg[idx * 2], seg[idx * 2 + 1]);
        }
 
        void updateRange(int idx, int s, int e, int l, int r, long val) {
            process(idx, s, e);
            if ((l > e) || s > r) {
                return;
            }
            if (s >= l && e <= r) {
                lazy[idx] += val;
                process(idx, s, e);
                return;
            }
            updateRange(idx * 2, s, (s + e) / 2, l, r, val);
            updateRange(idx * 2 + 1, (s + e) / 2 + 1, e, l, r, val);
            seg[idx] = Math.min(seg[idx * 2], seg[idx * 2 + 1]);
        }
 
        int minInRange(int idx, int s, int e, int l, int r) {
            process(idx, s, e);
            if ((l > e) || s > r) {
                return Integer.MAX_VALUE;
            }
            if (s >= l && e <= r) {
                return seg[idx];
            }
            return Math.min(minInRange(idx * 2, s, (s + e) / 2, l, r), minInRange(idx * 2 + 1, (s + e) / 2 + 1, e, l, r));
        }
    }
 
    static boolean isPalindrome(String w) {
        for (int i = 0; i < w.length() / 2; i++) {
            if (w.charAt(i) != w.charAt(w.length() - i - 1)) return false;
        }
        return true;
    }
 
    public static boolean isValid(int i, int j, int n, int m) {
        return (i > -1 && i < n) && (j > -1 && j < m);
 
    }
 
    static void dfs(int x, int y, char w[][], boolean vi[][]) {
        vi[x][y] = true;
        for (int i = 0; i < 4; i++) {
            int xx = x + grid[0][i];
            int yy = y + grid[1][i];
            if (isValid(xx, yy, w.length, w[0].length) && !vi[xx][yy] && w[xx][yy] != '#') {
                dfs(xx, yy, w, vi);
            }
        }
    }
 
    static int cmpDouble(double a, double b) {
        if (Math.abs(a - b) <= 1e-7) {
            return 0;
        }
        return a < b ? -1 : 1;
    }
 
 
    public static long GCD(long x, long y) {
        while (y != 0) {
            long c = x % y;
            x = y;
            y = c;
        }
        return x;
    }
 
    static boolean isPrime(long num) {
        if (num <= 1) {
            return false;
        }
        if (num == 2) {
            return true;
        }
        if (num % 2 == 0) {
            return false;
        }
        if (num == 3) {
            return true;
        }
        for (long i = 3; i * i <= num; i += 2) {
            if (num % i == 0) {
                return false;
            }
        }
        return true;
    }
 
 
    public static int[] swap(int data[], int left, int right) {
 
        // Swap the data
        int temp = data[left];
        data[left] = data[right];
        data[right] = temp;
 
        // Return the updated array
        return data;
    }
 
    public static int[] reverse(int data[], int left, int right) {
 
        // Reverse the sub-array
        while (left < right) {
            int temp = data[left];
            data[left++] = data[right];
            data[right--] = temp;
        }
 
        // Return the updated array
        return data;
    }
 
 
    public static boolean findNextPermutation(int data[]) {
 
        if (data.length <= 1) {
            return false;
        }
 
        int last = data.length - 2;
 
        // find the longest non-increasing suffix
        // and find the pivot
        while (last >= 0) {
            if (data[last] < data[last + 1]) {
                break;
            }
            last--;
        }
 
        // If there is no increasing pair
        // there is no higher order permutation
        if (last < 0) {
            return false;
        }
 
        int nextGreater = data.length - 1;
 
        // Find the rightmost successor to the pivot
        for (int i = data.length - 1; i > last; i--) {
            if (data[i] > data[last]) {
                nextGreater = i;
                break;
            }
        }
 
        // Swap the successor and the pivot
        data = swap(data, nextGreater, last);
 
        // Reverse the suffix
        data = reverse(data, last + 1, data.length - 1);
 
        // Return true as the next_permutation is done
        return true;
    }
 
    static double calcArea(pair a, pair b, pair c) {
        double ret = Math.abs((a.x * (double) (b.y - c.y)) + (b.x * (double) (c.y - a.y)) + (c.x * (double) (a.y - b.y))) / 2.0;
        return ret;
    }
 
 
    static class MultiSet<T> {
 
        HashMap<T, Integer> fre;
        TreeSet<T> set;
        int size;
 
        public MultiSet() {
            set = new TreeSet<>();
            fre = new HashMap<>();
            size = 0;
        }
 
        public void add(T elem) {
            if (fre.get(elem) == null || fre.get(elem) == 0) {
                fre.put(elem, 0);
                set.add(elem);
            }
            fre.put(elem, fre.get(elem) + 1);
            size++;
        }
 
        public void remove(T elem) {
            fre.put(elem, fre.get(elem) - 1);
            if (fre.get(elem) == 0) {
                set.remove(elem);
            }
            size--;
        }
 
 
        public boolean contains(T elem) {
            return set.contains(elem);
        }
    }
 
    static class Reader extends PrintWriter {
 
        private BufferedReader r;
        private StringTokenizer st;
        // standard input
 
        public Reader() {
            this(System.in, System.out);
        }
 
        public Reader(InputStream i, OutputStream o) {
            super(o);
            r = new BufferedReader(new InputStreamReader(i));
        }
        // USACO-style file input
 
        public Reader(String problemName) throws IOException {
            super(problemName + ".out");
            r = new BufferedReader(new FileReader(problemName));
        }
 
        // returns null if no more input
        String nextLine() {
            String str = "";
            try {
                str = r.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return str;
        }
 
        public String next() {
            try {
                while (st == null || !st.hasMoreTokens()) {
                    st = new StringTokenizer(r.readLine());
                }
                return st.nextToken();
            } catch (Exception e) {
            }
            return null;
        }
 
        public int nextInt() {
            return Integer.parseInt(next());
        }
 
        public double nextDouble() {
            return Double.parseDouble(next());
        }
 
        public long nextLong() {
 
            return Long.parseLong(next());
        }
    }
 
    static class pair {
        public int x, y, d, c;
 
        public pair(int x, int y) {
            this.x = x;
            this.y = y;
            this.d = d;
        }
 
        public String toString() {
            return x + " " + y;
        }
    }
 
    static class DSU {
        int[] parent, groupSize;
        int fre[];
        int numberOfNodes, numberOfGroups, maxGroup;
 
        public DSU(int numberOfNodes) {
            fre = new int[numberOfNodes + 1];
            this.numberOfNodes = numberOfNodes;
            parent = new int[numberOfNodes + 1];
            groupSize = new int[numberOfNodes + 1];
            numberOfGroups = numberOfNodes;
            maxGroup = 1;
            for (int i = 1; i <= numberOfNodes; i++) {
                parent[i] = i;
                groupSize[i] = 1;
                fre[groupSize[i]]++;
            }
        }
 
        public int getLeader(int x) {
            return parent[x] = (parent[x] == x ? x : getLeader(parent[x]));
        }
 
        public boolean sameGroup(int x, int y) {
            return getLeader(x) == getLeader(y);
        }
 
        public void mergeGroups(int x, int y) {
            int leader1 = getLeader(x);
            int leader2 = getLeader(y);
            if (leader1 != leader2) {
                numberOfGroups--;
                if (groupSize[leader1] < groupSize[leader2]) {
                    int temp = leader1;
                    leader1 = leader2;
                    leader2 = temp;
                }
                fre[groupSize[leader1]]--;
                fre[groupSize[leader2]]--;
                parent[leader2] = leader1;
                groupSize[leader1] += groupSize[leader2];
                fre[groupSize[leader1]]++;
                maxGroup = Math.max(maxGroup, groupSize[leader1]);
            }
        }
 
        public int getSize(int x) {
            return groupSize[getLeader(x)];
        }
    }
 
 
}
