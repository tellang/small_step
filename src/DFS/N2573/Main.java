package DFS.N2573;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {

    final static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    static int N, M, YEAR = 0, WATER = 0, REMAIN_ICE;
    static int[][] map;
    static boolean[][] isVisited;
    static int[] move = {-1, 0, 1, 0, -1};
    static Queue<Node> iceQ = new ArrayDeque<>();
    static Queue<Node> waterQ = new ArrayDeque<>();
    public static void main(String[] args) throws IOException {
        st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        map = new int[N][M];
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < M; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
                if (map[i][j] > WATER) {
                    REMAIN_ICE++;
                    iceQ.offer(new Node(i, j));
                }
            }
        }
        while (!iceQ.isEmpty()) {
            Node ice = iceQ.poll();
            if (map[ice.y][ice.x] <= WATER) {
                continue;
            }

            int iceCount = 0;
            isVisited = new boolean[N][M];
            ArrayDeque<Node> stk = new ArrayDeque<>();
            stk.offer(ice);
            isVisited[ice.y][ice.x] = true;

            while (!stk.isEmpty()) {
                Node cur = stk.pop();

                for (int i = 0; i < 4; i++) {
                    int nx = cur.x + move[i];
                    int ny = cur.y + move[i+1];
                    if (isValid(ny, nx) && !isVisited[ny][nx]) {
                        if (map[ny][nx] > WATER) {
                            stk.push(new Node(ny, nx));
                            isVisited[ny][nx] = true;
                        } else if (map[ny][nx] <= WATER) {
                            waterQ.offer(cur);
                        }
                    }
                }
            }
            while (!waterQ.isEmpty()) {
                Node cur = waterQ.poll();
                if (map[cur.y][cur.x] > WATER) {
                    map[cur.y][cur.x]--;
                    if (map[cur.y][cur.x] == WATER) {
                        REMAIN_ICE--;
                    }
                }
            }
            if (!isWholeIce()) {
                System.out.println(YEAR+1);
                System.exit(0);
            }
            YEAR++;
            if (map[ice.y][ice.x] > WATER)
                iceQ.offer(ice);
        }

        System.out.println(0);
    }

    private static boolean isWholeIce() {
        int iceCount = 0;
        isVisited = new boolean[N][M];
        ArrayDeque<Node> stk = new ArrayDeque<>();
        for (int y = 1; y < N; y++) {
            for (int x = 1; x < M; x++) {
                if (map[y][x] > WATER) {
                    stk.push(new Node(y, x));
                    isVisited[y][x] = true;
                    iceCount = getIceCount(stk, iceCount);
                    return iceCount == REMAIN_ICE;
                }
            }
        }
        return true;
    }

    private static int getIceCount(ArrayDeque<Node> stk, int iceCount) {
        while (!stk.isEmpty()) {
            Node cur = stk.pop();


            iceCount++;

            for (int i = 0; i < 4; i++) {
                int nx = cur.x + move[i];
                int ny = cur.y + move[i+1];
                if (isValid(ny, nx) && !isVisited[ny][nx]) {
                    if (map[ny][nx] > WATER) {
                        isVisited[ny][nx] = true;
                        stk.push(new Node(ny, nx));
                    }
                }
            }
        }
        return iceCount;
    }

    static boolean isValid(int y, int x) {
        return y >= 0 && y < N && x >= 0 && x < M;
    }
}
class Node {
    int x, y;
    Node(int y, int x) {
        this.x = x;
        this.y = y;
    }
}
