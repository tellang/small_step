package SSAFY.N1247;

import java.io.*;
import static java.lang.Integer.parseInt;
import java.util.*;

/**
 * SSAFY 1247번 최적 경로 문제를 백트래킹으로 해결하는 솔루션 클래스 회사에서 출발하여 모든 고객의 집을 방문한 뒤 자신의 집으로
 * 돌아오는 최단 경로를 탐색
 */
public class Solution {

    static BufferedReader br;
    static StringTokenizer st;
    static StringBuilder result = new StringBuilder();

    static int T, N, NODE_LIMIT, SAMSUNG = 0, HOME, minCost;
    static Node[] nodes; // 회사, 고객 집, 자신의 집의 좌표를 저장하는 배열
    static BitSet mask; // 방문한 노드를 체크하는 BitSet

    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("src/SSAFY/N1247/input.txt"));
        br = new BufferedReader(new InputStreamReader(System.in));

        T = parseInt(br.readLine().trim());

        for (int tc = 1; tc <= T; tc++) {
            result.append('#').append(tc).append(' ');
            init();
            permutate(SAMSUNG, 0);
            result.append(minCost).append('\n');
        }
        System.out.println(result);
    }

    /**
     * 고객의 집을 방문하는 모든 순열을 탐색
     * 
     * @param lastNode 마지막으로 방문한 노드의 인덱스
     * @param cost     현재까지의 누적 이동 거리
     */
    static void permutate(int lastNode, int cost) {
        if (mask.cardinality() == N) {
            // 모든 고객의 집을 방문한 경우, 마지막 고객 집에서 회사까지의 거리를 추가
            cost += Node.getDistance(nodes[lastNode], nodes[HOME]);

            minCost = Math.min(minCost, cost);
            return;
        }

        // 가지치기: 현재 누적 비용이 이미 최소 비용을 초과하면 더 이상 탐색할 필요 없음
        if (cost >= minCost) {
            return;
        }

        // 방문하지 않은 고객의 집을 다음 방문지로 선택
        for (int midNode = SAMSUNG + 1; midNode < HOME; midNode++) {
            if (!mask.get(midNode)) {
                mask.set(midNode);
                permutate(midNode, cost + Node.getDistance(nodes[lastNode], nodes[midNode]));
                mask.clear(midNode); // 백트래킹
            }
        }
    }

    /**
     * 테스트 케이스 초기화
     * 
     * @throws IOException 입출력 오류 발생 시
     */
    static void init() throws IOException {
        N = parseInt(br.readLine().trim());
        NODE_LIMIT = N + 2;
        nodes = new Node[NODE_LIMIT];

        mask = new BitSet(NODE_LIMIT);

        HOME = NODE_LIMIT - 1;
        minCost = Integer.MAX_VALUE;
        st = new StringTokenizer(br.readLine().trim());

        // 회사와 자신의 집 좌표를 먼저 초기화
        for (int nodeIdx : new int[] { SAMSUNG, HOME }) {
            int x = parseInt(st.nextToken());
            int y = parseInt(st.nextToken());

            nodes[nodeIdx] = new Node(y, x);
        }

        // 고객들의 집 좌표를 초기화
        for (int nodeIdx = 1; nodeIdx < HOME; nodeIdx++) {
            int x = parseInt(st.nextToken());
            int y = parseInt(st.nextToken());

            nodes[nodeIdx] = new Node(y, x);
        }
    }

    /**
     * 좌표를 저장하는 클래스
     */
    static class Node {
        int y, x;

        public Node(int y, int x) {
            this.y = y;
            this.x = x;
        }

        /**
         * 두 노드 사이의 맨해튼 거리를 계산
         * 
         * @param start 시작 노드
         * @param dest  도착 노드
         * @return 맨해튼 거리
         */
        public static int getDistance(Node start, Node dest) {
            return Math.abs(start.y - dest.y) + Math.abs(start.x - dest.x);
        }
    }
}
