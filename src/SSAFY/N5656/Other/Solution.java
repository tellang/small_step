package SSAFY.N5656.Other;

import java.io.*;
import static java.lang.Integer.parseInt;
import java.util.*;

/*
 * 벽돌 깨기
 *
 * 핵심 로직
 * - DFS(중복 순열) + 시뮬레이션
 *
 * 최적화
 * - GC 부담을 줄이기 위해 모든 자료구조를 최대 크기로 미리 생성
 * - 재귀 깊이를 이용한 상태 관리 (3차원 배열)
 *
 * 상태 관리
 * - mapStates[0]: 원본 맵
 * - mapStates[stage]: 각 구슬을 떨어뜨린 후의 맵 상태
 */
public class Solution {

    static BufferedReader br;
    static StringTokenizer st;
    static StringBuilder result = new StringBuilder();

    static final int MAX_BALL_COUNT = 4, MAX_WIDTH = 12, MAX_HEIGHT = 15;
    static int T, N, W, H, BALL_COUNT, WIDTH, HEIGHT, minRemainBricks;
    static int[][][] map = new int[MAX_BALL_COUNT + 1][MAX_HEIGHT][MAX_WIDTH];
    static int[] dropX = new int[MAX_BALL_COUNT];
    static Queue<Node> searchQ = new ArrayDeque<>();
    static ArrayDeque<Integer> trimStk = new ArrayDeque<>();
    static int[] mv = { -1, 0, 1, 0, -1 };

    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("src/SSAFY/N5656/input"));
        br = new BufferedReader(new InputStreamReader(System.in));

        T = parseInt(br.readLine().trim());

        for (int tc = 1; tc <= T; tc++) {
            result.append('#').append(tc).append(' ');
            initAndInput();
            duplicatedCombination(0);
            result.append(minRemainBricks).append('\n');
        }
        System.out.print(result);
    }

    static void initAndInput() throws IOException {
        st = new StringTokenizer(br.readLine().trim());
        BALL_COUNT = N = parseInt(st.nextToken());
        WIDTH = W = parseInt(st.nextToken());
        HEIGHT = H = parseInt(st.nextToken());

        int totalBricks = 0;
        for (int h = 0; h < HEIGHT; h++) {
            st = new StringTokenizer(br.readLine().trim());
            for (int w = 0; w < WIDTH; w++) {
                map[0][h][w] = parseInt(st.nextToken());
                if (map[0][h][w] != 0) {
                    totalBricks++;
                }
            }
        }
        minRemainBricks = totalBricks;
    }

    static void duplicatedCombination(int bc) {
        if (minRemainBricks == 0)
            return;

        if (bc == BALL_COUNT) {
            minRemainBricks = Math.min(minRemainBricks, simulate());
            return;
        }

        for (int w = 0; w < WIDTH; w++) {
            dropX[bc] = w;
            duplicatedCombination(bc + 1);
        }
    }

    static int simulate() {
        for (int i = 0; i < BALL_COUNT; i++) {
            int stage = i + 1;
            reset(stage);

            int[][] localMap = map[stage];
            int x = dropX[i];
            int y = -1;

            for (int h = 0; h < HEIGHT; h++) {
                if (localMap[h][x] != 0) {
                    y = h;
                    break;
                }
            }

            if (y != -1) {
                breakBricks(y, x, localMap);
                trimZero(localMap);
            }
        }

        int[][] resultMap = map[BALL_COUNT];
        int remain = 0;
        for (int h = 0; h < HEIGHT; h++) {
            for (int w = 0; w < WIDTH; w++) {
                if (resultMap[h][w] != 0) {
                    remain++;
                }
            }
        }
        return remain;
    }

    static void reset(int stage) {
        int[][] originMap = map[stage - 1];
        int[][] localMap = map[stage];
        for (int h = 0; h < HEIGHT; h++) {
            System.arraycopy(originMap[h], 0, localMap[h], 0, WIDTH);
        }
    }

    static void breakBricks(int y, int x, int[][] localMap) {
        searchQ.clear();
        searchQ.offer(new Node(y, x, localMap[y][x]));
        localMap[y][x] = 0;

        while (!searchQ.isEmpty()) {
            Node poll = searchQ.poll();

            for (int d = 0; d < 4; d++) {
                for (int r = 1; r < poll.power; r++) {
                    int ny = poll.y + mv[d] * r;
                    int nx = poll.x + mv[d + 1] * r;

                    if (isValid(ny, nx)) {
                        if (localMap[ny][nx] > 0) {
                            searchQ.offer(new Node(ny, nx, localMap[ny][nx]));
                        }
                        localMap[ny][nx] = 0;
                    }
                }
            }
        }
    }

    /**
     * 중력 적용
     */
    static void trimZero(int[][] localMap) {
        for (int w = 0; w < WIDTH; w++) {
            trimStk.clear();

            // [수정] 위에서부터(h=0) 아래로 내려오면서 스택에 push
            for (int h = 0; h < HEIGHT; h++) {
                if (localMap[h][w] != 0) {
                    trimStk.push(localMap[h][w]);
                }
            }

            // 밑에서부터(h=H-1) 위로 올라오면서 스택에서 pop하여 채움
            for (int h = HEIGHT - 1; h >= 0; h--) {
                if (!trimStk.isEmpty()) {
                    localMap[h][w] = trimStk.pop();
                } else {
                    localMap[h][w] = 0;
                }
            }
        }
    }

    static boolean isValid(int y, int x) {
        return y >= 0 && y < HEIGHT && x >= 0 && x < WIDTH;
    }

    static class Node {
        int y, x, power;

        public Node(int y, int x, int power) {
            this.y = y;
            this.x = x;
            this.power = power;
        }
    }
}