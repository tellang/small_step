package SSAFY.N3289;

/**
 * n = 1M, m = 100K 일반적인 서로소 집합문제 mst 생성 후 parent 비교
 */
import java.io.*;
import static java.lang.Integer.parseInt;
import java.util.*;

public class Solution {

    static BufferedReader br;
    static StringTokenizer st;
    static StringBuilder result = new StringBuilder();
    static int T, UNION = 0, CHECK = 1, N, M;

    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("src/SSAFY/N3289/input.txt"));
        br = new BufferedReader(new InputStreamReader(System.in));

        T = parseInt(br.readLine().trim());

        for (int tc = 1; tc <= T; tc++) {
            result.append('#').append(tc).append(' ');
            // init();
            query();
            result.append('\n');
        }
        System.out.println(result);
    }

    /**
     * 입력된 쿼리를 처리하는 메서드
     *
     * @throws IOException 입출력 오류 발생 시
     */
    static void query() throws IOException {

        st = new StringTokenizer(br.readLine().trim());
        int maxNum = N = parseInt(st.nextToken());
        int queryCount = M = parseInt(st.nextToken());
        Union union = new Union(maxNum);
        for (int m = 0; m < queryCount; m++) {
            st = new StringTokenizer(br.readLine().trim());
            int queryType = parseInt(st.nextToken());
            int u = parseInt(st.nextToken());
            int v = parseInt(st.nextToken());
            if (queryType == UNION) {
                union.union(u, v);
            } else {
                result.append(union.isUnion(u, v));
            }
        }
    }

    /**
     * Union-Find 알고리즘을 구현한 클래스
     */
    static class Union {

        int[] parent, rank;

        /**
         * Union 클래스의 생성자
         *
         * @param N 원소의 최대 개수
         */
        public Union(int N) {
            parent = new int[N + 1];
            rank = new int[N + 1];
            for (int i = 1; i < parent.length; i++) {
                parent[i] = i;
            }
        }

        /**
         * 특정 원소의 루트 노드를 찾는 메서드 (경로 압축 최적화)
         *
         * @param child 루트를 찾을 원소
         * @return 루트 노드
         */
        int find(int child) {
            if (child == parent[child]) {
                return child;
            }
            return parent[child] = find(parent[child]);
        }

        /**
         * 두 원소를 하나의 집합으로 합치는 메서드 (랭크 최적화)
         *
         * @param u 합칠 첫 번째 원소
         * @param v 합칠 두 번째 원소
         * @return 합치기 성공 여부
         */
        boolean union(int u, int v) {
            int uRoot = find(u);
            int vRoot = find(v);

            if (uRoot == vRoot) {
                return false;
            }

            if (rank[uRoot] == rank[vRoot]) {
                rank[uRoot]++;
            }

            if (rank[uRoot] > rank[vRoot]) {
                parent[vRoot] = uRoot;
                return true;
            }

            parent[uRoot] = vRoot;
            return true;
        }

        /**
         * 두 원소가 같은 집합에 속해 있는지 확인하는 메서드
         *
         * @param u 첫 번째 원소
         * @param v 두 번째 원소
         * @return 같은 집합에 속하면 1, 아니면 0
         */
        int isUnion(int u, int v) {
            return u == v || find(u) == find(v) ? 1 : 0;
        }

        /**
         * 모든 원소의 루트를 재설정하여 경로 압축을 수행
         */
        void refrash() {
            for (int child = 1; child < parent.length; child++) {
                find(child);
            }
        }
    }
}
