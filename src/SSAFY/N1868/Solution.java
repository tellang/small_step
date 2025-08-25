package SSAFY.N1868;

/**
 * N 300 x 300
 *
 *
 * 0 클러스터에서 확장한 zeroCount를 세고 
 * 진입 시점의 bfsCount 를 세고 mineCount를 세고 
 * N*N - zeroCount - mineCount + bfsCount
 */
import java.io.*;
import static java.lang.Integer.parseInt;
import java.util.*;

public class Solution {

    static BufferedReader br;
    static StringTokenizer st;
    static StringBuilder result = new StringBuilder();
    static int T, N, Y, X, zeroCount, mineCount, bfsCount; // 1based y, x with border 1

    static char[][] map;
    static char NULL = '.', MINE = '*', CHEKED = 'K', ZERO = '0';
    // 주변 지뢰가 0개인 칸을 담는 큐, BFS 탐색을 위한 큐
    static Queue<Node> zeroQ = new ArrayDeque<>(), searchQ = new ArrayDeque<>();
    // 8방향 탐색을 위한 배열
    static int[] dy = {-1, -1, -1, 0, 1, 1, 1, 0},
            dx = {-1, 0, 1, 1, 1, 0, -1, -1};
    static boolean[][] isVisited;

    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("src/SSAFY/N1868/input"));
        br = new BufferedReader(new InputStreamReader(System.in));

        T = parseInt(br.readLine().trim());

        for (int tc = 1; tc <= T; tc++) {
            result.append('#').append(tc).append(' ');

            init();
            countBorder();
            result.append((N * N - mineCount - zeroCount + bfsCount)).append('\n');
        }
        System.out.println(result);
    }

    /**
     * 주변 지뢰가 0개인 칸을 시작으로 BFS를 수행하여 클러스터 확장
     */
    static void countBorder() {
        while (!zeroQ.isEmpty()) {
            Node start = zeroQ.poll();
            if (map[start.y][start.x] == CHEKED) {
                continue;
            }

            bfsCount++;
            zeroCount++;
            map[start.y][start.x] = CHEKED;
            searchQ.clear();
            searchQ.offer(start);

            while (!searchQ.isEmpty()) {
                Node poll = searchQ.poll();

                int y = poll.y, x = poll.x;
                int ny, nx;
                for (int dir = 0; dir < 8; dir++) {
                    ny = y + dy[dir];
                    nx = x + dx[dir];
                    if (map[ny][nx] == NULL) {
                        map[ny][nx] = CHEKED;
                        zeroCount++;
                    } else if (map[ny][nx] == ZERO) {
                        zeroCount++;
                        map[ny][nx] = CHEKED;
                        searchQ.offer(new Node(ny, nx));
                    }
                }
            }
        }
    }

    static void init() throws IOException {
        Y = X = N = parseInt(br.readLine().trim());

        zeroCount = 0;
        mineCount = 0;
        bfsCount = 0;

        map = new char[Y + 2][X + 2];
        isVisited = new boolean[Y + 2][X + 2];
        zeroQ.clear();
        searchQ.clear();
        for (int y = 1; y <= Y; y++) {
            char[] row = br.readLine().trim().toCharArray();
            for (int x = 1; x <= X; x++) {
                map[y][x] = row[x - 1];
            }
        }

        /**
         * 90K x 8 = 720K
         */
        for (int y = 1; y <= Y; y++) {
            for (int x = 1; x <= X; x++) {
                if (map[y][x] == MINE) {
                    mineCount++;
                    continue;
                }
                int localMineCount = get8WayCount(y, x);
                if (localMineCount == 0) {
                    map[y][x] = ZERO;
                    // zeroCount++;
                    zeroQ.offer(new Node(y, x));
                }
            }
        }
    }

    /**
     * 특정 좌표의 주변 8방향 지뢰 개수를 계산
     *
     * @param y 행 인덱스
     * @param x 열 인덱스
     * @return 주변 지뢰 개수
     */
    static int get8WayCount(int y, int x) {
        int count = 0;
        int ny, nx;
        for (int dir = 0; dir < 8; dir++) {
            ny = y + dy[dir];
            nx = x + dx[dir];
            if (map[ny][nx] == MINE) {
                count++;
            }
        }
        return count;
    }

    static class Node {

        int y, x;

        public Node(int y, int x) {
            this.y = y;
            this.x = x;
        }
    }
}
