package SSAFY.N14502;

import java.io.*;
import java.util.*;

/**
 * 조합 + BFS
 *
 * 1. 빈 칸 좌표를 수집해 3곳을 조합으로 벽 설치
 * 2. 바이러스 시작점을 큐에 넣고 BFS 확산 시뮬레이션
 * 3. 남은 안전 영역 최대값 갱신
 *
 * 시간복잡도
 * O(C(S,3) x N x M) <= C(64,3) x 64 = 2.7M
 */
public class Main {

    static BufferedReader br;
    static StringTokenizer st;

    static int N, M, X = 8, Y = 8,
    SPACE = 0, WALL = 1, VIRUS = 2, spaceCnt, virusCnt;

    static int[][] originMap, copyMap;
    static Node[] spaces, viruses;
    static int[] mv = {-1, 0, 1, 0, -1};
    static Queue<Node> q = new ArrayDeque<>();

    static {
        originMap = new int[Y][X];
        copyMap = new int[Y][X];

        spaces = new Node[Y * X];   // < 64
        viruses = new Node[Y * X];  // < 64
    }

    public static void main(String[] args) throws IOException {
        br = new BufferedReader(new InputStreamReader(System.in));
        init();
        int safeRemain = bfs();
        System.out.print(safeRemain);
    }

    static void init() throws IOException {
        st = new StringTokenizer(br.readLine());
        Y = N = Integer.parseInt(st.nextToken());
        X = M = Integer.parseInt(st.nextToken());

        virusCnt = spaceCnt = 0;

        for (int y = 0; y < Y; y++) {
            st = new StringTokenizer(br.readLine());
            for (int x = 0; x < X; x++) {
                int cell = Integer.parseInt(st.nextToken());
                originMap[y][x] = cell;

                if (cell == SPACE) {
                    spaces[spaceCnt++] = new Node(y, x);
                } else if (cell == VIRUS) {
                    viruses[virusCnt++] = new Node(y, x);
                }
            }
        }
    }

    /**
     * 3개 벽 조합 + BFS 시뮬레이션
     */
    static int bfs() {
        int maxSafeRemain = 0;
        
        for (int spIdx = 0; spIdx < spaceCnt; spIdx++) {
            for (int spJdx = spIdx + 1; spJdx < spaceCnt; spJdx++) {
                for (int spKdx = spJdx + 1; spKdx < spaceCnt; spKdx++) {
                    
                    // 복사 후 벽 3개 세우기
                    getCopyMap();
                    setWall(spIdx);
                    setWall(spJdx);
                    setWall(spKdx);

                    int safeRemain = spaceCnt - 3; // 현재 남은 빈 칸 수

                    // 바이러스 BFS
                    q.clear();

                    for (int v = 0; v < virusCnt; v++) 
                        q.offer(viruses[v]);

                    while (!q.isEmpty() && safeRemain > maxSafeRemain) {
                        Node poll = q.poll();

                        for (int d = 0; d < 4; d++) {
                            int ny = poll.y + mv[d];
                            int nx = poll.x + mv[d + 1];

                            if (!valid(ny, nx)) continue;
                            if (copyMap[ny][nx] != SPACE) continue;

                            // 감염 확산
                            copyMap[ny][nx] = VIRUS;
                            safeRemain--;
                            q.offer(new Node(ny, nx));

                            if (safeRemain <= maxSafeRemain) 
                                break;
                        }
                    }
                    
                    maxSafeRemain = Math.max(maxSafeRemain, safeRemain);
                }
            }
        }
        return maxSafeRemain;
    }

    static boolean valid(int y, int x) {
        return y >= 0 && y < Y && x >= 0 && x < X;
    }

    /**
     * 벽 설치
     */
    static void setWall(int spacesIdx) {
        copyMap[spaces[spacesIdx].y][spaces[spacesIdx].x] = WALL;
    }

    /**
     * 2차원 배열 복사
     */
    static void getCopyMap() {
        for (int y = 0; y < Y; y++) {
            System.arraycopy(originMap[y], 0, copyMap[y], 0, X);
        }
    }

    static class Node {
        int y, x;
        Node(int y, int x) { this.y = y; this.x = x; }
    }
}
