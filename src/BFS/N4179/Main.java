package BFS.N4179;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.StringTokenizer;

public class Main {

    final static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    static int R, C, WALL = -1, EMPTY = Integer.MAX_VALUE, MIN = Integer.MAX_VALUE;
    static int[][] map;
    static Node START;
    static ArrayDeque<Node> Q = new ArrayDeque<>();
    static int[] move = {-1, 0, 1, 0, -1};
    public static void main(String[] args) throws IOException {
        st = new StringTokenizer(br.readLine());
        R = Integer.parseInt(st.nextToken());
        C = Integer.parseInt(st.nextToken());
        map = new int[R][C];
        for (int r = 0; r < R; r++) {
            char[] arr = br.readLine().toCharArray();
            for (int c = 0; c < C; c++) {
                if (arr[c] == '#') {
                    map[r][c] = WALL;
                } else if (arr[c] == 'J') {
                    START = new Node(r, c, 0);
                } else if (arr[c] == 'F') {
                    Q.offer(new Node(r, c, 0));
                } else {
                    map[r][c] = EMPTY;
                }
            }
        }
        while (!Q.isEmpty()) { //fire diffusion
            Node cur = Q.poll();

            for (int i = 0; i < 4; i++) {
                int ny = cur.y + move[i];
                int nx = cur.x + move[i+1];
                int nc = cur.c + 1;
                if (isValid(ny, nx) && !isWall(ny, nx)) {
                    if (hasBigCountThanNC(ny, nx, nc)) {
                        map[ny][nx] = nc;
                        Q.offer(new Node(ny, nx, nc));
                    }
                }
            }
        }
        if (isEdge(START.y, START.x)){
            MIN = Math.min(MIN, START.c);
        }else {
            Q.offer(START);
            while (!Q.isEmpty()) {
                Node cur = Q.poll();

                for (int i = 0; i < 4; i++) {
                    int ny = cur.y + move[i];
                    int nx = cur.x + move[i+1];
                    int nc = cur.c + 1;

                    if (isValid(ny, nx) && !isWall(ny, nx)){
                        if (hasBigCountThanNC(ny, nx, nc)) {
                            map[ny][nx] = nc;
                            if (isEdge(ny, nx)){
                                MIN = Math.min(MIN, nc);
                                break;
                            }
                            Q.offer(new Node(ny, nx, nc));
                        }
                    }
                }
            }
        }
        if (MIN == Integer.MAX_VALUE) {
            System.out.println("IMPOSSIBLE");
        } else {
            System.out.println(MIN+1);
        }
    }

    private static boolean isEdge(int nr, int nc) {
        return nr == 0 || nc == 0 || nr == R-1 || nc == C-1;
    }

    private static boolean hasBigCountThanNC(int ny, int nx, int nc) {
        return map[ny][nx] > nc;
    }

    private static boolean isWall(int ny, int nx) {
        return map[ny][nx] == WALL;
    }

    static boolean isValid(int nr, int nc) {
        return nr >= 0 && nr < R && nc >= 0 && nc < C;
    }
}
class Node {
    int y, x, c;
    Node(int y, int x, int c) {
        this.y = y;
        this.x = x;
        this.c = c;
    }
}
