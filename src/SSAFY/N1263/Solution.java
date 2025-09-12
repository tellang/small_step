package SSAFY.N1263;

import java.io.*;
import static java.lang.Integer.parseInt;
import java.util.*;
/*
 * 다중 BFS 노가다  
 */ 
public class Solution {

    static BufferedReader br;
    static StringTokenizer st;
    static StringBuilder result = new StringBuilder();
    static final int LIMIT_NODE = 1001;

    static int T, N, MAX_NODE;

    static BitSet visited, frontier, next;
    static BitSet[] nextNodes; // 1based

    static {
        visited = new BitSet(LIMIT_NODE + 1);
        frontier = new BitSet(LIMIT_NODE + 1);
        next = new BitSet(LIMIT_NODE + 1);

        nextNodes = new BitSet[LIMIT_NODE];
        for (int n = 1; n < LIMIT_NODE; n++) {
            nextNodes[n] = new BitSet(LIMIT_NODE + 1);
        }
    }

    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("src/SSAFY/N1263/input.txt"));
        br = new BufferedReader(new InputStreamReader(System.in));

        T = parseInt(br.readLine().trim());

        for (int tc = 1; tc <= T; tc++) {
            result.append('#').append(tc).append(' ');
            init();
            int minDist = allIdxBfs();
            result.append(minDist).append('\n');
        }
        System.out.println(result);
    }

    static int allIdxBfs() {
        int minDist = Integer.MAX_VALUE;
        for (int idx = 1; idx <= MAX_NODE; idx++) {
            minDist = Math.min(bfs(idx), minDist);
        }
        return minDist;
    }

    static void init() throws IOException {
        st = new StringTokenizer(br.readLine().trim());
        N = MAX_NODE = parseInt(st.nextToken());
        for (int y = 1; y <= MAX_NODE; y++) {
            nextNodes[y].clear();
            for (int x = 1; x <= MAX_NODE; x++) {
                if (parseInt(st.nextToken()) == 0)
                    continue;
                nextNodes[y].set(x);
            }
        }
    }

    /**
     * 배열을 BitSet으로 관리
     * 3
     * 0 1 0 nextNodes[1] = {2} 
     * 1 0 1 nextNodes[2] = {1, 3}
     * 0 1 0 nextNodes[3] = {2}
     * 
     * 1. 프론티어집합들 합집합
     * 2. 방문집합 차집합
     * 3. 카디널리티 계산
     * 시작 idx 부터 nextBit를 확인해가며 for idx
     * next에 연결된 다음 Bit 의 row인 nextNodes[idx] 를 합집합
     * next.andNot(visited); 로 방문 집합과 next 집합을 차집합
     * 
     * @param startIdx
     * @return
     */
    static int bfs(int startIdx) {
        int totalDist = 0;
        int level = 1;

        visited.clear();
        frontier.clear();

        visited.set(startIdx);
        frontier.set(startIdx);

        while (!frontier.isEmpty()) {
            next.clear();
            for (int idx = frontier.nextSetBit(0); idx >= 0; idx = frontier.nextSetBit(idx + 1)) {
                next.or(nextNodes[idx]); //프론티어집합들 합집합
            }
            next.andNot(visited); //방문집합 차집합
            totalDist += (level * next.cardinality());
            visited.or(next); //방문처리

            frontier.clear();
            frontier.or(next);
            level++;
        }

        return totalDist;
    }
}