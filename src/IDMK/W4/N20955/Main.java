package IDMK.W4.N20955;

/**
 * 사이클은 모두 잘라야 하고
 * 그 상태의 트리들을 합친다고 가정해서
 * 사이클을 자른 횟수에 트리 갯수 -1 만큼 더해준다
 */
import java.io.*;
import static java.lang.Integer.parseInt;
import java.util.*;

public class Main {

    static BufferedReader br;
    static StringTokenizer st;

    static int N, M, NODE_LIMIT, VERTEX_COUNT, surgeryCount = 0;
    static BitSet rootCount;
    static Union uni;

    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("src/IDMK/W4/N20955/input.txt"));
        br = new BufferedReader(new InputStreamReader(System.in));

        init();
        surgery();
    }

    static void surgery() throws IOException {
        for (int vc = 0; vc < VERTEX_COUNT; vc++) {
            st = new StringTokenizer(br.readLine().trim());
            int u = parseInt(st.nextToken());
            int v = parseInt(st.nextToken());

            if (!uni.join(u, v))
                surgeryCount++;
        }
        surgeryCount += (uni.countRoot() - 1);

        System.out.println(surgeryCount);
    }

    static void init() throws IOException {
        st = new StringTokenizer(br.readLine().trim());
        NODE_LIMIT = N = parseInt(st.nextToken());
        NODE_LIMIT++;
        VERTEX_COUNT = M = parseInt(st.nextToken());
        rootCount = new BitSet(NODE_LIMIT);
        uni = new Union();
    }

    static class Union {
        int[] parent, rank;

        public Union() {
            parent = new int[NODE_LIMIT];
            rank = new int[NODE_LIMIT];

            for (int i = 1; i < NODE_LIMIT; i++) {
                parent[i] = i;
            }
        }

        int findRoot(int child) {
            if (parent[child] == child)
                return child;
            return parent[child] = findRoot(parent[child]);
        }

        boolean join(int u, int v) {
            int uRoot = findRoot(u);
            int vRoot = findRoot(v);

            if (uRoot == vRoot)
                return false;

            if (rank[uRoot] == rank[vRoot])
                rank[uRoot]++;

            if (rank[uRoot] > rank[vRoot]) {
                parent[vRoot] = uRoot;
            } else {
                parent[uRoot] = vRoot;
            }
            return true;
        }

        int countRoot() {
            for (int child = 1; child < NODE_LIMIT; child++) {
                rootCount.set(findRoot(child));
            }
            return rootCount.cardinality();
        }
    }
}