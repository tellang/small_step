package IDMK.W4.N9205;

/**
 * x, y 0based
 */
import java.io.*;
import static java.lang.Integer.parseInt;
import java.util.*;

public class Main {

    static BufferedReader br;
    static StringTokenizer st;
    static StringBuilder result = new StringBuilder();

    static int T, N, STORE_COUNT, HOME = 0, HELL, STAMINA_LIMIT = 1001;
    static Node[] nodes;
    static boolean[][] floyd;
    static String HAPPY = "happy", SAD = "sad";

    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("src/IDMK/W4/N9205/input.txt"));
        br = new BufferedReader(new InputStreamReader(System.in));

        T = parseInt(br.readLine().trim());

        for (int tc = 1; tc <= T; tc++) {
            init();
            floyd();
            result.append(floyd[HOME][HELL] == true ? HAPPY : SAD).append('\n');
        }
        System.out.println(result);
    }

    /**
     * start -> mid -> end 를 갈 수 있는가
     */
    static void floyd() {
        for (int mid = HOME + 1; mid < HELL; mid++) {
            for (int start = HOME; start < HELL; start++) {
                if (start == mid)
                    continue;
                for (int end = HOME + 1; end <= HELL; end++) {
                    if (start == end || mid == end || floyd[start][end])
                        continue;
                    floyd[start][end] = floyd[start][mid] && floyd[mid][end];
                }
            }
        }
    }

    static void init() throws IOException {
        STORE_COUNT = N = parseInt(br.readLine().trim());
        HELL = STORE_COUNT + 1;
        nodes = new Node[HELL + 1];
        floyd = new boolean[HELL + 1][HELL + 1];
        for (int nIdx = HOME; nIdx <= HELL; nIdx++) {
            st = new StringTokenizer(br.readLine().trim());
            int x = parseInt(st.nextToken());
            int y = parseInt(st.nextToken());

            nodes[nIdx] = new Node(y, x);
        }
        /**
         * 맥주 다 쓰고 갈 수 있으면 true 플로이드 초기 간선
         */
        for (int start = HOME; start < HELL; start++) {
            for (int end = HOME + 1; end <= HELL; end++) {
                if (start == end)
                    continue;
                floyd[start][end] = Node.isHappyRoute(start, end);
            }
        }
    }

    static class Node {
        int y, x;

        public Node(int y, int x) {
            this.y = y;
            this.x = x;
        }

        static boolean isHappyRoute(Node u, Node v) {
            return (Math.abs(u.x - v.x) + Math.abs(u.y - v.y)) < STAMINA_LIMIT;
        }

        static boolean isHappyRoute(int uIdx, int vIdx) {
            return isHappyRoute(nodes[uIdx], nodes[vIdx]);
        }
    }
}