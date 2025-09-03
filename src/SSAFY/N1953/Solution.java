package SSAFY.N1953;

/**
 * 
 * int[][] map
 * boolean[][] isVisited ? BitSet?
 * 
 */
import java.io.*;
import static java.lang.Integer.parseInt;
import java.util.*;

public class Solution {

    static BufferedReader br;
    static StringTokenizer st;
    static StringBuilder result = new StringBuilder();
    static final int MV_Y = 0, MV_X = 1, UP = 0, DOWN = 1, LEFT = 2, RIGHT = 3;
    static int T, N, Y = 50, M, X = 50, R, START_Y, C, START_X, L, LIMIT_TIME;

    static int[][] map;
    static boolean[][] visited;
    static Queue<Node> q;
    static int[][][] mvOffset;
    static int[][] matchPipe;
    static boolean[][] matchType;

    static {
        map = new int[Y][X];
        visited = new boolean[Y][X];
        q = new ArrayDeque<>();

        mvOffset = new int[][][] { // [type][MV_X/Y][DIR]
                {
                        {}, // 1based 0
                        {},
                },
                {
                        { -1, 0, 1, 0 }, // ┼ 1
                        { 0, 1, 0, -1 },
                },
                {
                        { -1, 1 }, // │ 2
                        { 0, 0 },
                },
                {
                        { 0, 0 }, // ─ 3
                        { -1, 1 },
                },
                {
                        { -1, 0 }, // └ 4
                        { 0, 1 },
                },
                {
                        { 1, 0 }, // ┌ 5
                        { 0, 1 },
                },
                {
                        { 1, 0 }, // ┐ 6
                        { 0, -1 },
                },
                {
                        { -1, 0 }, // ┘ 7
                        { 0, -1 }
                }
        };
        matchType = new boolean[][] { // [UDLR][matchType]
                { false, true, true, false, false, true, true, false }, // U 1, 2, 5, 6
                { false, true, true, false, true, false, false, true }, // D, 1, 2, 4, 7
                { false, true, false, true, true, true, false, false }, // L 1, 3, 4, 5
                { false, true, false, true, false, false, true, true } // R // 1, 3, 6, 7
        };
        matchPipe = new int[][] { // [pipeType][4~2]
                {},
                { // ┼ 1 URDL
                        UP, RIGHT, DOWN, LEFT
                },
                { // │ 2 UD
                        UP, DOWN
                },
                { // ─ 3 LR
                        LEFT, RIGHT
                },
                { // └ 4 UR
                        UP, RIGHT
                },
                { // ┌ 5 DR
                        DOWN, RIGHT
                },
                { // ┐ 6 DL
                        DOWN, LEFT
                },
                { // ┘ 7 UL
                        UP, LEFT
                }
        };
                
    }
    static char pConver(int num){
        char[] pipe = {' ', '┼', '│', '─', '└', '┌', '┐', '┘'};
        return pipe[num];
    }
    static void init() throws IOException {
        st = new StringTokenizer(br.readLine().trim());
        N = Y = parseInt(st.nextToken());
        M = X = parseInt(st.nextToken());
        R = START_Y = parseInt(st.nextToken());
        C = START_X = parseInt(st.nextToken());
        L = LIMIT_TIME = parseInt(st.nextToken());

        for (int y = 0; y < Y; y++) {
            st = new StringTokenizer(br.readLine().trim());
            for (int x = 0; x < X; x++) {
                map[y][x] = parseInt(st.nextToken());
                visited[y][x] = false;
            }
        }
        q.clear();
        q.offer(new Node(START_Y, START_X, 1));
        visited[START_Y][START_X] = true;
    }

    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("src/SSAFY/N1953/input.txt"));
        br = new BufferedReader(new InputStreamReader(System.in));

        T = parseInt(br.readLine().trim());

        for (int tc = 1; tc <= T; tc++) {
            result.append('#').append(tc).append(' ');
            init();
            bfs();
            int count = calculate();

            result.append(count).append('\n');
        }
        System.out.println(result);
    }

    static void bfs() {
        while (!q.isEmpty()) {
            Node cur = q.poll();
            if (cur.time == LIMIT_TIME)
                return;

            int pipeType = map[cur.y][cur.x];
            int[][] mv = mvOffset[pipeType];
            int nt = cur.time + 1;
            for (int dir = 0; dir < mv[MV_Y].length; dir++) {

                int ny = mv[MV_Y][dir] + cur.y;
                int nx = mv[MV_X][dir] + cur.x;

                if (!valid(ny, nx) ||
                        visited[ny][nx] ||
                        !isMatchPipe(matchPipe[map[cur.y][cur.x]][dir], map[ny][nx]))
                    continue;

                visited[ny][nx] = true;
                q.offer(new Node(ny, nx, nt));
            }
        }
    }

    /**
     * 
     * 진입 방향으로
     * 해당 위치의 파이프와 연결할 수 있는지 파악
     * 
     * @param dir
     * @param y
     * @param x
     * @return
     */
    static boolean isMatchPipe(int insertDir, int nextPipeType) {
        if (nextPipeType == 0)
            return false;
            
        int reverseDir = getReverseDir(insertDir);

        for (int matchPipeType : matchPipe[nextPipeType]) { // 사용가능한 UDLR
            if (matchPipeType == reverseDir)
                return true;
        }
        return false;
    }

    /**
     * 0 <-> 1 UD
     * 2 <-> 3 LR
     * 
     * @param insertDir
     * @return
     */
    static int getReverseDir(int insertDir) {
        return (5 - insertDir) % 4;
    }

    static boolean valid(int y, int x) {
        return y >= 0 && y < Y && x >= 0 && x < X;
    }

    static int calculate() {
        int count = 0;

        for (int y = 0; y < Y; y++) {
            for (int x = 0; x < X; x++) {
                if (visited[y][x])
                    count++;
            }
        }

        return count;
    }

    static class Node {
        int y, x, time;

        public Node(int y, int x, int time) {
            this.y = y;
            this.x = x;
            this.time = time;
        }
    }
}