import java.io.*;
import java.util.Arrays;
import java.util.Random;
import java.util.StringTokenizer;
 
public class Main {
    public static void main(String[] args) {
        InputReader in = new InputReader(System.in);
        PrintWriter out = new PrintWriter(System.out);
        Task solver = new Task();
        solver.solve(in.nextInt(), in, out);
//        solver.duiPai();
        out.close();
    }
 
    static class Task {
        public void solve(int testNumber, InputReader in, PrintWriter out) {
            for (int t = 0; t < testNumber; t++) {
                int n = in.nextInt();
                int[][] a = new int[n][2];
                for (int i = 0; i < a.length; i++) {
                    a[i] = new int[]{in.nextInt(), in.nextInt()};
                }
                out.println(new Solution().solve(a));
            }
        }
 
        public void duiPai() {
            Random random = new Random();
            for (int t = 0; t < 100; t++) {
                int n = random.nextInt(10) + 5;
                int[] a = genArr(n, 1, 9);
                System.out.println(Arrays.toString(a));
            }
        }
 
        public int[] genArr(int n, int l, int r) {
            int[] arr = new int[n];
            Random random = new Random();
            for (int i = 0; i < n; i++) {
                arr[i] = random.nextInt(r - l + 1) + l;
            }
            return arr;
        }
    }
 
    static class InputReader {
        public BufferedReader reader;
        public StringTokenizer tokenizer;
 
        public InputReader(InputStream stream) {
            reader = new BufferedReader(new InputStreamReader(stream), 32768);
            tokenizer = null;
        }
 
        public String next() {
            while (tokenizer == null || !tokenizer.hasMoreTokens()) {
                try {
                    tokenizer = new StringTokenizer(reader.readLine());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            return tokenizer.nextToken();
        }
 
        public int nextInt() {
            return Integer.parseInt(next());
        }
 
        public long nextLong() {
            return Long.parseLong(next());
        }
 
        public double nextDouble() {
            return Double.parseDouble(next());
        }
    }
}
 
class Solution {
    public int solve(int[][] a) {
        int w = a[0][0];
        int n = a.length;
        for (int i = 1; i < n; i++) {
            if (a[i][0] >= w && a[i][1] >= a[0][1]) {
                return -1;
            }
        }
        return w;
    }
}
