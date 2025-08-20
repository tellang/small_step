package SSAFY.N1238;

/**
 * bfs Node(idx, time)
 */
import java.io.*;
import static java.lang.Integer.parseInt;
import java.util.*;

public class Solution {

    static BufferedReader br;
    static StringTokenizer st;
    static StringBuilder result = new StringBuilder();
    static int T, VERTEX_COUNT, START, MAX_NODE = 101, MAX_IDX, MAX_TIME;

    static Map<Integer, Set<Integer>> graph = new HashMap<>();
    static Queue<Node> q = new ArrayDeque<>();
    static boolean[] isVisited;

    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("src/SSAFY/N1238/input"));
        br = new BufferedReader(new InputStreamReader(System.in));

        T = 10;
        for (int tc = 1; tc <= T; tc++) {
            result.append('#').append(tc).append(' ');
            initalize();
            bfs();
            result.append(MAX_IDX).append('\n');
        }
        System.out.println(result);
    }

    /**
     * BFS 탐색을 통해 가장 늦게 연락받는 사람을 찾습니다.
     */
    static void bfs() {
        while (!q.isEmpty()) {
            Node poll = q.poll();

            // 현재 노드의 시간이 최대 시간보다 크면 갱신
            if (poll.time > MAX_TIME) {
                MAX_TIME = poll.time;
                MAX_IDX = poll.idx;
            } else if (poll.time == MAX_TIME) { // 현재 노드의 시간이 최대 시간과 같으면 더 큰 번호로 갱신
                MAX_IDX = Math.max(MAX_IDX, poll.idx);
            }

            int nt = poll.time + 1;
            for (int child : graph.get(poll.idx)) {
                if (!isVisited[child]) {
                    isVisited[child] = true;
                    q.offer(new Node(child, nt));
                }
            }
        }
    }

    /**
     * 테스트 케이스 초기화
     *
     * @throws IOException 입출력 오류 발생 시
     */
    static void initalize() throws IOException {
        st = new StringTokenizer(br.readLine().trim());

        VERTEX_COUNT = parseInt(st.nextToken()) >> 1; // 한번에 두개씩 묶어서 받을 예정
        START = parseInt(st.nextToken());
        q.clear();
        graph.clear();
        for (int nodeIdx = 1; nodeIdx < MAX_NODE; nodeIdx++) {
            graph.put(nodeIdx, new HashSet<>());
        }
        isVisited = new boolean[MAX_NODE];

        st = new StringTokenizer(br.readLine().trim());
        for (int vc = 0; vc < VERTEX_COUNT; vc++) { // 자식 노드 추가
            int from = parseInt(st.nextToken());
            int to = parseInt(st.nextToken());

            Set<Integer> children = graph.get(from);
            children.add(to);
            graph.put(from, children);
        }

        q.offer(new Node(START, 1));
        isVisited[START] = true;
        MAX_IDX = START;
        MAX_TIME = 1;
    }

    /**
     * BFS 탐색을 위한 노드 클래스
     */
    static class Node {

        int idx, time;

        public Node(int idx, int time) {
            this.idx = idx;
            this.time = time;
        }
    }
}
