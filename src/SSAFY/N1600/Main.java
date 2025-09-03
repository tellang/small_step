package SSAFY.N1600;
/**
 * 
 * 방문배열을 스킬갯수의 축이 포함된 3차원으로 확장 후 bfs 
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import static java.lang.Integer.*;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {

    static BufferedReader br;
    static StringTokenizer st;

    static final int[] move, horseMove;

    static int W, X, H, Y, K, SKILL_MAX;
    static boolean[][][] map;
    static Queue<Node> q;

    static {
        br = new BufferedReader(new InputStreamReader(System.in));

        move = new int[] { 0, -1, 0, 1, 0 };
        horseMove = new int[] { 1, 2, -1, 2, 1, -2, -1, -2, 1 };

        q = new ArrayDeque<>();
    }

    public static void main(String[] args) throws IOException {

        init();
        int minCost = bfs();
        System.out.println(minCost);
    }

    static int bfs() {
        int minCost = -1;

        while (!q.isEmpty()) {
            Node cur = q.poll();

            if (isLastNode(cur)) {
                if (minCost == -1 || minCost > cur.cost)
                    minCost = cur.cost;
                continue;
            }

            int nc = cur.cost + 1;
            int ny, nx, nhsk;
            for (int dir = 0; dir < 4; dir++) {
                ny = cur.y + move[dir];
                nx = cur.x + move[dir + 1];
                nhsk = cur.horseSkill;
                if (isValid(ny, nx) && !map[ny][nx][0] && !map[ny][nx][nhsk]) {
                    q.offer(new Node(ny, nx, nc, nhsk));
                    map[ny][nx][nhsk] = true;
                }
            }
            if (cur.horseSkill < SKILL_MAX) {
                for (int dir = 0; dir < 8; dir++) {
                    ny = cur.y + horseMove[dir];
                    nx = cur.x + horseMove[dir + 1];
                    nhsk = cur.horseSkill + 1;
                    if (isValid(ny, nx) && !map[ny][nx][0] && !map[ny][nx][nhsk]) {
                        q.offer(new Node(ny, nx, nc, nhsk));
                        map[ny][nx][nhsk] = true;
                    }
                }

            }
        }

        return minCost;
    }

    static boolean isLastNode(Node cur) {
        return cur.y == Y - 1 && cur.x == X - 1;
    }

    static void init() throws IOException {
        K = SKILL_MAX = parseInt(br.readLine());
        st = new StringTokenizer(br.readLine());
        W = X = parseInt(st.nextToken());
        H = Y = parseInt(st.nextToken());

        map = new boolean[Y][X][SKILL_MAX + 1];

        for (int y = 0; y < Y; y++) {
            st = new StringTokenizer(br.readLine().trim());
            for (int x = 0; x < X; x++) {
                int node = Integer.parseInt(st.nextToken());
                if (node == 1) {
                    map[y][x][0] = true;
                }
            }
        }

        q.offer(new Node(0, 0, 0, 0));
        map[0][0][0] = true;
    }

    static boolean isValid(int y, int x) {

        return y >= 0 && y < Y &&
                x >= 0 && x < X;
    }

    static class Node {
        int y, x, cost, horseSkill;

        public Node(int y, int x, int cost, int horseSkill) {
            this.y = y;
            this.x = x;
            this.cost = cost;
            this.horseSkill = horseSkill;
        }
    }
}
