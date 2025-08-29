package SSAFY.N5656;

/**
 * 12^4 < 16^4 < 66k
 * 66k x 720byte < 66M
 * 66k x dfs(180) 1400k = 1.4M
 * 
 * 좌상단 0
 * 
 * ---
 * 중복 순열로 드롭위치 결정후
 * bfs로 드롭
 */
import java.io.*;
import static java.lang.Integer.parseInt;
import java.util.*;

public class Solution {

    static BufferedReader br;
    static StringTokenizer st;
    static StringBuilder result = new StringBuilder();

    static int T, N, BALL_MAX, X = 12, Y = 15, minRemainBricks;
    static int[][] originMap = new int[Y][X];

    static Queue<Node> searchQ = new ArrayDeque<>();
    static ArrayDeque<Integer> trimStk = new ArrayDeque<>();
    static int[] dropX = new int[4],
            mv = { -1, 0, 1, 0, -1 },
            originYHeightOf = new int[X]; // 맨위 y
    static boolean[][] isVisited = new boolean[Y][X];

    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("src/SSAFY/N5656/input"));
        br = new BufferedReader(new InputStreamReader(System.in));

        T = parseInt(br.readLine().trim());

        for (int tc = 1; tc <= T; tc++) {
            result.append('#').append(tc).append(' ');
            init();
            inputMap();
            duplicatedCombination(0);
            result.append(minRemainBricks).append('\n');
        }
        System.out.println(result);
    }

    static void inputMap() throws IOException {
        minRemainBricks = 0;
        for (int y = 0; y < Y; y++) {
            st = new StringTokenizer(br.readLine().trim());
            for (int x = 0; x < X; x++) { // 12
                originMap[y][x] = parseInt(st.nextToken());
                if (originMap[y][x] != 0) {
                    minRemainBricks++;
                    if (originYHeightOf[x] > y) // 가장 위에 있는 블록
                        originYHeightOf[x] = y;
                }
            }
        }
    }

    static void duplicatedCombination(int selectedCount) {
        if (selectedCount == BALL_MAX) {
            int[] localYHeight = originYHeightOf.clone();
            int[][] copyMap = mapCopy();
            minRemainBricks = Math.min(minRemainBricks, drop(copyMap, localYHeight));
            return;
        }

        for (int x = 0; x < X; x++) {

            if (originYHeightOf[x] >= Y)
                continue;
            dropX[selectedCount] = x;
            duplicatedCombination(selectedCount + 1);
        }
    }

    /**
     * 백트래킹 형식의 dfs 좌하단을 0, 0 으로 하는 수직형 LinkedList[] deleteQ에 넣고 지연삭제
     * 
     * @param y, x 드롭 위치
     */
    static void drop(int y, int x, int[][] map) {
        Node startNode = new Node(y, x);
        searchQ.offer(startNode);

        for (int vy = 0; vy < Y; vy++) {
            Arrays.fill(isVisited[vy], false);
        }
        isVisited[y][x] = true;

        while (!searchQ.isEmpty()) {
            Node poll = searchQ.poll();

            int repeatLimit = map[poll.y][poll.x];
            map[poll.y][poll.x] = 0;

            for (int mvIdx = 0; mvIdx < 4; mvIdx++) {
                for (int repeat = 1; repeat < repeatLimit; repeat++) {
                    int ny = poll.y + mv[mvIdx] * repeat;
                    int nx = poll.x + mv[mvIdx + 1] * repeat;
                    if (!isValid(ny, nx))
                        break;
                    if (isVisited[ny][nx])
                        continue;

                    searchQ.offer(new Node(ny, nx));
                    isVisited[ny][nx] = true;
                }
            }
        }

    }

    static boolean isValid(int y, int x) {
        return y >= 0 && y < Y && x >= 0 && x < X;
    }

    /**
     * X 위치에 드롭
     */
    static int drop(int[][] map, int[] yHeightOf) {
        int loclaRemainBricks = 0;
        for (int dropIdx = 0; dropIdx < BALL_MAX; dropIdx++) {
            int x = dropX[dropIdx];
            int y = yHeightOf[x];
            if (y >= Y)
                continue;
            // printMap(map);
            drop(y, x, map);
            // printMap(map);
            loclaRemainBricks = trimZero(map, yHeightOf);
            // printMap(map);
        }
        return loclaRemainBricks;
    }

    static void printMap(int[][] map) {
        StringBuilder print = new StringBuilder();
        print.append(" \\  X").append('\n').append(" Y").append('\n');
        for (int y = 0; y < Y; y++) {
            print.append("[").append(y).append("] ");
            for (int x = 0; x < X; x++) {
                print.append(map[y][x]).append(' ');
            }
            print.append('\n');
        }
        System.out.println(print);
    }

    static int[][] mapCopy() {
        int[][] copy = new int[Y][];
        for (int y = 0; y < Y; y++) {
            copy[y] = originMap[y].clone();
        }
        return copy;
    }

    /**
     * 좌하단이 0, 0 매 열마다 밑에서 위로 올라가면서 0 밀어내기 우하단 기준으로 수정
     */
    static int trimZero(int[][] map, int[] yHeightOf) {

        int loclaRemainBricks = 0;
        for (int x = 0; x < X; x++) {
            trimStk.clear();
            int y = 0;
            for (; y < Y; y++) {
                if (map[y][x] != 0) { // 0 아닌 것 stack에 넣기
                    trimStk.push(map[y][x]);
                    map[y][x] = 0;
                }
            }
            loclaRemainBricks += trimStk.size();
            yHeightOf[x] = Y - trimStk.size();
            while (!trimStk.isEmpty()) {
                map[--y][x] = trimStk.pop();
            }
        }
        return loclaRemainBricks;
    }

    static void init() throws IOException {
        st = new StringTokenizer(br.readLine().trim());
        BALL_MAX = N = parseInt(st.nextToken());
        X = parseInt(st.nextToken());
        Y = parseInt(st.nextToken());

        Arrays.fill(originYHeightOf, Y);

        searchQ.clear();
    }

    static class Node {
        int y, x;

        public Node(int y, int x) {
            this.y = y;
            this.x = x;
        }

    }
}