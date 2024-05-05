package DFS.N10026;

import static java.lang.Math.abs;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;

public class Main {

    final static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int N, NORMAL = 0, RG_WEAK = 1, RB = 16, GB = 5;
    static char[][] map;
    static boolean[][] isVisited;
    static int[] move = {-1, 0, 1, 0, -1},
    result = new int[2];
    static ArrayDeque<Node> stk = new ArrayDeque<>();
    public static void main(String[] args) throws IOException {
        N = Integer.parseInt(br.readLine());
        map = new char[N][N];
        for (int y = 0; y < N; y++) {
            map[y] = br.readLine().toCharArray();
        }
        isVisited = new boolean[N][N];
        for (int y = 0; y < N; y++) {
            for (int x = 0; x < N; x++) {
                if (isVisited[y][x]) continue;
                char targetColor = getColor(y, x);
                stk.push(new Node(y, x));
                while (!stk.isEmpty()) {
                    Node node = stk.pop();
                    isVisited[node.y][node.x] = true;
                    for (int i = 0; i < 4; i++) {
                        int nextX = node.x + move[i];
                        int nextY = node.y + move[i+1];
                        if (isValid(nextY, nextX) && !isVisited[nextY][nextX]){
                            if (targetColor == getColor(nextY, nextX)){
                                stk.push(new Node(nextY, nextX));
                            }
                        }
                    }
                }
                result[NORMAL]++;
            }
        }

        isVisited = new boolean[N][N];
        for (int y = 0; y < N; y++) {
            for (int x = 0; x < N; x++) {
                if (isVisited[y][x]) continue;
                char targetColor = getColor(y, x);
                stk.push(new Node(y, x));
                while (!stk.isEmpty()) {
                    Node node = stk.pop();
                    isVisited[node.y][node.x] = true;
                    for (int i = 0; i < 4; i++) {
                        int nextX = node.x + move[i];
                        int nextY = node.y + move[i+1];
                        if (isValid(nextY, nextX) && !isVisited[nextY][nextX]){
                            char nextColor = getColor(nextY, nextX);
                            int colorDiff = abs(nextColor - targetColor);
                            if (colorDiff == RB || colorDiff == GB) continue;

                            stk.push(new Node(nextY, nextX));
                        }
                    }
                }
                result[RG_WEAK]++;
            }
        }
        StringBuilder sb = new StringBuilder();
        System.out.println(sb.append(result[NORMAL]).append(' ').append(result[RG_WEAK]));
    }

    private static boolean isValid(int y, int x) {
        return y >= 0 && y < N && x >= 0 && x < N;
    }

    private static char getColor(Node cur) {
        return map[cur.y][cur.x];
    }

    private static char getColor(int y, int x) {
        return map[y][x];
    }
}
class Node {
    int x, y;

    public Node(int y, int x) {
        this.x = x;
        this.y = y;
    }
}
