package SSAFY.N7793;

/**
 * 
 * bfs 2회
 * 
 * 손아귀 bfs 로 int 형 배열에 time을 찍고
 * time 보다 낮은 경로만 갈 수 있게 start 에서 bfs
 * 
 * 입력을 int 형 배열에 돌 위치만 -1 로 못가게
 * Node는 y, x, time
 * 
 * ---
 * 악마가 여럿이네 못된놈들
 */
import java.io.*;
import static java.lang.Integer.parseInt;
import java.util.*;
import java.util.function.Function;

public class Solution {

    static BufferedReader br;
    static StringTokenizer st;
    static StringBuilder result = new StringBuilder();
    static int T, N, Y = 50, M, X = 50, TIME_MAX = Integer.MAX_VALUE, STONE = -10,
            minResult;

    static Queue<Node> q = new ArrayDeque<>();
    static int[][] deadTimes = new int[Y][X];
    static Node START, MEGAMI;

    static char START_SYMBOL = 'S', MEGAMI_SYMBOL = 'D',
            AKUMA_SYMBOL = '*', STONE_SYMBOL = 'X';
    static int[] mv = new int[] { -1, 0, 1, 0, -1 };
    static String GAME_OVER = "GAME OVER";

    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("src/SSAFY/N7793/input.txt"));
        br = new BufferedReader(new InputStreamReader(System.in));

        T = parseInt(br.readLine().trim());

        for (int tc = 1; tc <= T; tc++) {
            result.append('#').append(tc).append(' ');
            init();

            bfsWave(
                    n -> false,
                    (y, x, t) -> aKumaValidCondition(y, x, t));
            q.clear();
            q.offer(START);
            deadTimes[START.y][START.x] = START.t;
            int localMinTime = bfsWave(
                    n -> n.equals(MEGAMI),
                    (y, x, t) -> validCondition(y, x, t));
            result.append(localMinTime == -1 ? GAME_OVER : localMinTime).append('\n');
        }
        System.out.println(result);
    }

    static int bfsWave(
            Function<Node, Boolean> endCondition,
            TriFunction<Integer, Integer, Integer, Boolean> validCondition) {

        while (!q.isEmpty()) {
            Node poll = q.poll();
            if (endCondition.apply(poll)) {
                return poll.t;
            }

            int nt = poll.t + 1;
            for (int dir = 0; dir < 4; dir++) {
                int ny = poll.y + mv[dir];
                int nx = poll.x + mv[dir + 1];

                if (validCondition.apply(ny, nx, nt)) {
                    deadTimes[ny][nx] = nt;
                    q.offer(new Node(ny, nx, nt));
                }
            }
        }
        return -1;
    }

    static boolean validCondition(int y, int x, int t) {
        return isValid(y, x) && isUnderTime(y, x, t);
    }

    static boolean aKumaValidCondition(int y, int x, int t) {
        return validCondition(y, x, t) &&
                !(y == MEGAMI.y && x == MEGAMI.x);
    }

    static boolean isUnderTime(int y, int x, int t) {
        return deadTimes[y][x] > t;
    }

    static boolean isValid(int y, int x) {
        return y >= 0 && y < Y && x >= 0 && x < X;
    }

    static void init() throws IOException {
        st = new StringTokenizer(br.readLine().trim());
        Y = N = parseInt(st.nextToken());
        X = M = parseInt(st.nextToken());

        minResult = TIME_MAX;

        for (int y = 0; y < Y; y++) {
            Arrays.fill(deadTimes[y], TIME_MAX);
        }

        q.clear();

        for (int y = 0; y < Y; y++) {
            char[] row = br.readLine().trim().toCharArray();

            for (int x = 0; x < X; x++) {
                if (row[x] == START_SYMBOL) {
                    START = new Node(y, x, 0);
                } else if (row[x] == MEGAMI_SYMBOL) {
                    MEGAMI = new Node(y, x, -1);
                } else if (row[x] == STONE_SYMBOL) {
                    deadTimes[y][x] = STONE;
                } else if (row[x] == AKUMA_SYMBOL) {
                    q.offer(new Node(y, x, 0));
                    deadTimes[y][x] = 0;
                }
            }
        }

    }

    static class Node {
        int y, x, t;

        public Node(int y, int x, int t) {
            this.y = y;
            this.x = x;
            this.t = t;
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + y;
            result = prime * result + x;
            return result;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            Node other = (Node) obj;
            if (y != other.y)
                return false;
            if (x != other.x)
                return false;
            return true;
        }

    }

    @FunctionalInterface
    static interface TriFunction<T0, T1, T2, R> {

        R apply(T0 t0, T1 t1, T2 t2);
    }
}