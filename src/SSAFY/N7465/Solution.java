package SSAFY.N7465;

import java.io.*;
import static java.lang.Integer.parseInt;
import java.util.*;

public class Solution {

    static BufferedReader br;
    static StringTokenizer st;
    static StringBuilder result = new StringBuilder();
    static int T, N, M, MAX_N, VERTEX_COUNT;

    static BitSet mask;
    static Union union;

    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("src/SSAFY/N7465/input.txt"));
        br = new BufferedReader(new InputStreamReader(System.in));

        T = parseInt(br.readLine().trim());

        for (int tc = 1; tc <= T; tc++) {
            result.append('#').append(tc).append(' ');

            init();
            union.joinQuery();
            result.append(mask.cardinality()).append('\n');
        }
        System.out.println(result);
    }

    static void init() throws IOException {
        st = new StringTokenizer(br.readLine().trim());
        MAX_N = N = parseInt(st.nextToken());
        VERTEX_COUNT = M = parseInt(st.nextToken());

        mask = new BitSet(++MAX_N);

        union = new Union();
        for (int vc = 0; vc < VERTEX_COUNT; vc++) {
            st = new StringTokenizer(br.readLine().trim());
            int u = parseInt(st.nextToken());
            int v = parseInt(st.nextToken());

            union.join(u, v);
        }
    }

    static class Union {

        int[] parent, rank;

        public Union() {
            parent = new int[MAX_N];
            rank = new int[MAX_N];

            for (int child = 1; child < parent.length; child++) {
                parent[child] = child;
            }
        }

        public int find(int child) {
            if (parent[child] == child) {
                return child;
            }
            return parent[child] = find(parent[child]);
        }

        public boolean join(int u, int v){
            int uRoot = find(u);
            int vRoot = find(v);

            if(uRoot == vRoot) return false;

            if(rank[uRoot] == rank[vRoot])
                rank[uRoot]++;

            if(rank[uRoot] > rank[vRoot]){
                parent[vRoot] = uRoot;
            } else {
                parent[uRoot] = vRoot;
            }

            return true;
        }

        public void joinQuery(){
            for (int i = 1; i < parent.length; i++) {
                mask.set(find(i));
            }
        }
    }
}
