package DFS.N16946;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

/*
 * visited는 int 형으로 idx 증가형으로 체크
 * 
 * 매번 안찾을 수 없을까?
 * 
 * 한번에 찾고
 * 
 * 최초에 0칸으로 각 섹션으로 나눠서
 * 
 * 
 * 벽을 부셨을때 4방향의 섹션 중 중복된 값을 뺸 합을 구한다
 */
public class Main {
    static int N, M, visitedIdx = 0;
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    static int[][] visited, resultMap;
    static char[][] map;
    static int[] mv = { -1, 0, 1, 0, -1 };
    static char WALL = '1', EMPTY = '0';
    static ArrayDeque<Node> q = new ArrayDeque<>(), wallList = new ArrayDeque<>();
    static StringBuilder sb = new StringBuilder();
    static Map<Integer, Integer> routeMap = new HashMap<>();

    public static void main(String[] args) throws IOException {
        st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        visited = new int[N][M];
        resultMap = new int[N][M];
        map = new char[N][M];

        for (int n = 0; n < N; n++) {
            map[n] = br.readLine().toCharArray();
            for (int m = 0; m < M; m++) {
                if (map[n][m] == WALL)
                    wallList.offer(new Node(n, m));
            }
        }

        for (int y = 0; y < N; y++) {
            for (int x = 0; x < M; x++) {

                if (isEmpty(y, x) && visited[y][x] == 0) {
                    visitedIdx++;
                    q.offer(new Node(y, x));
                    visited[y][x] = visitedIdx;
                    int routeCount = 1; // 현재위치도 추가
                    while (!q.isEmpty()) {
                        Node poll = q.poll();

                        for (int i = 0; i < 4; i++) {
                            int ny = poll.y + mv[i];
                            int nx = poll.x + mv[i + 1];

                            if (isValid(ny, nx) && isEmpty(ny, nx) && !isVitied(ny, nx)) {
                                visited[ny][nx] = visitedIdx;
                                q.offer(new Node(ny, nx));
                                routeCount++;
                            }
                        }
                    }
                    routeMap.put(visitedIdx, routeCount);
                }
            }
        }

        /*
         * 4방향 체크 하고
         */
        while (!wallList.isEmpty()) {
            Node wall = wallList.poll();
            int routeCount = 1;
            Set<Integer> visitedSet = new HashSet<>();
            visitedSet.clear();
            for (int i = 0; i < 4; i++) {
                int ny = wall.y + mv[i];
                int nx = wall.x + mv[i + 1];

                if (isValid(ny, nx) && isEmpty(ny, nx)) {
                    int sectionIdx = visited[ny][nx];
                    if (!visitedSet.contains(sectionIdx)) {
                        visitedSet.add(sectionIdx);
                        routeCount += routeMap.get(sectionIdx);
                    }
                }

            }
            resultMap[wall.y][wall.x] = routeCount % 10;
        }

        for (int[] resultArr : resultMap) {
            for (int num : resultArr) {
                sb.append(num);
            }
            sb.append('\n');
        }
        System.out.println(sb);
    }

    static boolean isValid(int y, int x) {
        return y >= 0 && y < N && x >= 0 && x < M;
    }

    static boolean isVitied(int y, int x) {
        return visited[y][x] != 0;
    }

    static boolean isEmpty(int y, int x) {
        return map[y][x] == EMPTY;
    }
}

class Node {
    int y, x;

    public Node(int y, int x) {
        this.y = y;
        this.x = x;
    }

}
