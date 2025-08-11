package BFS.N23352;

import java.io.*;
import static java.lang.Integer.*;
import java.util.*;

/*
 * 최단 경로 중
 * 특정 벨류값이 높은 경로
 * 2중 정렬하면 되는게 아닐까?? 라기엔 별로네
 * 최단루트 찾아서 방문 한 뒤
 * 계속 방문하면서 최단 아닐때 까지만 본다
 * 그러기 위해선 pq로 최단루트를 만들어야한다'
 * 
 * 어 근데 시작이 랜덤이네??
 * 50x50, 250에
 * 2.5k x O(2500) = 6250K = 6M ㄱㅊ
 * 
 * --- 멍청이 컷 ---
 *
 */
 /*
 * 모든 지점을 시작점으로 간주하여 각각 BFS 수행
 *
 * 단일 BFS 로직
 * - 시작점에서 가장 멀리 갈 수 있는 경로의 길이와 해당 지점의 값을 찾기
 * - BFS의 마지막 레벨에 도달한 노드들이 최장 경로의 후보
 *
 * 정답 갱신 규칙
 * 1. 최장 경로 길이를 1순위로 비교
 * - 1-1. 경로 길이가 같을 경우, 의 합을 2순위로 비교
 */
public class Main {

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;

    static int N, M;
    static int[][] map;
    static boolean[][] visited;

    static int MAX_LEN = 0, MAX_SUM = 0, WALL = 0;

    static Queue<Node> q = new ArrayDeque<>();
    static int[] mv = {-1, 0, 1, 0, -1};

    public static void main(String[] args) throws IOException {
        st = new StringTokenizer(br.readLine());
        N = parseInt(st.nextToken());
        M = parseInt(st.nextToken());
        map = new int[N][M];

        for (int y = 0; y < N; y++) {
            st = new StringTokenizer(br.readLine());
            for (int x = 0; x < M; x++) {
                map[y][x] = parseInt(st.nextToken());
            }
        }

        for (int y = 0; y < N; y++) {
            for (int x = 0; x < M; x++) {
                if (map[y][x] != 0) {
                    localBfs(y, x);
                }
            }
        }

        System.out.println(MAX_SUM);
    }

    static void localBfs(int startY, int startX) {

        visited = new boolean[N][M];

        int startVal = map[startY][startX];
        int localMaxLen = 0;
        int localMaxEndVal = 0;

        q.offer(new Node(startY, startX, 1));
        visited[startY][startX] = true;

        while (!q.isEmpty()) {
            Node poll = q.poll();

            if (poll.length > localMaxLen) {
                localMaxLen = poll.length;
                localMaxEndVal = map[poll.y][poll.x];
            } else if (poll.length == localMaxLen) {
                localMaxEndVal = max(localMaxEndVal, map[poll.y][poll.x]);
            }

            for (int i = 0; i < 4; i++) {
                int ny = poll.y + mv[i];
                int nx = poll.x + mv[i + 1];

                if (isValid(ny, nx)
                        && !visited[ny][nx]
                        && map[ny][nx] != WALL) {

                    visited[ny][nx] = true;
                    int nl = poll.length + 1;
                    q.offer(new Node(ny, nx, nl));
                }
            }
        }

        int localSum = startVal + localMaxEndVal;
        if (localMaxLen > MAX_LEN) {
            MAX_LEN = localMaxLen;
            MAX_SUM = localSum;
        } else if (localMaxLen == MAX_LEN) {
            MAX_SUM = max(MAX_SUM, localSum);
        }
    }

    static boolean isValid(int y, int x) {
        return y >= 0 && y < N && x >= 0 && x < M;
    }

    static class Node {

        int y, x, length;

        Node(int y, int x, int len) {
            this.y = y;
            this.x = x;
            this.length = len;
        }
    }
}
