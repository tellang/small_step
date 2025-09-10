package SSAFY.N5650;

import java.io.*;
import static java.lang.Integer.parseInt;
import java.util.*;
import static localutil.print.console.ConsolePrinter.ASCII;

/*
 * (5 ≤ N ≤ 100) 정사각형
 * int[][] map[N + 2][N + 2] 1~N
 * int[][][] visited[way][y + 2][x + 2] 1~N
 * int[][] wormHole[mode{0, 1}][11]
 * - nextWormHole -> wormHole[(holeNum/100)^1][holeNum%100];
 * ---
 * 10K의 핀볼을 전부 넣어볼까
 * - 반복문이 이득일듯
 * ---
 * 프루닝 및 종료 조건
 * - 방향 필요
 *   - visited[way][y][x] >= cur.t ) continue
 * - 방향 필요 x
 *   - 블랙홀을 만났을때) maxTime 갱신
 *   - 출발위치를 만났을때) maxTime 갱신
 *     - 그러면 node에 출발위치를 기억해야겠네?
 *     - 아니면 큐에 다 넣고 시작하나 visited 맵을 그대로 들고 포문돌리나 같지않나
 *       - 현재 시간복잡도 및 자료구조 상으로 동일하다
 *       - 그러면 node 에 시작점을 기억할 필요가 없다
 * ---
 * 엣지
 * - 연속 해서 튕길때 
 *   - 매번 검사 ? 이동 시켜서
 *   - 다음 거리를 가지 말고 그냥 넣고 위치에 있는 특수 블록에 따라 판단할까?
 *     - 어짜피 테두리를 5번 블록으로 감쌀예정이니
 *     - 특수 블록일때, 방향에 따라 방향을 바꾸고 nt++; 기본 nt = t;
 *   - 웜홀일때만 다음 웜홀 위치로 이동시킨다
 * - 웜홀 연동 방법 [6<= holeNum%100 <=10]
 *   - ~무식하게 이중 해쉬맵~
 *   - 인덱스 활용하고 싶다 0, 1 인버트써서 6-> 106, 6으로 구분
 *     - map에 num 넣을때 BitSet.get(num) 으로 있으면 100 더해서 넣기
 *       - 근데 숫자 6갠데 그냥 불리언배열쓰지
 *       - int[] 로 만들고 map[y][x] = int[map[y][x]];  int[map[y][x]]+=100 할깐
 *     - 웜홀을 만났을때 map[y][x] = holeNum
 *     - nextWormHole -> wormHole[(holeNum/100)^1][holeNum%100];
 *   -  
 * - 
 * ---
 * 테두리를 5번 블록으로 감싸자
 * map[N + 2][N + 2] 1~N
 * 
 */
public class Solution {

    static BufferedReader br;
    static StringTokenizer st;
    static StringBuilder result = new StringBuilder();

    static final int MAX_HOLE = 11, WORMHOLE_MODULER = 100,
            BLACK_HOLE = -1, SPACE = 0, U = 0, D = 1, L = 2, R = 3;
    static final int[] dx, dy;

    static int T, N, Y, X;

    static int[][] map; // 1~N 0, N+1 : 5
    static Node[][] wormHole; // 1~N 0, N+1 : 5
    static int[][][] visited; // 1~N,
    static boolean[] hasPair;
    // static Deque<Ball> dq;

    static {
        X = Y = N = 100;
        map = new int[Y + 2][X + 2];
        visited = new int[4][Y + 2][X + 2];
        wormHole = new Node[2][MAX_HOLE];

        dy = new int[] { -1, 1, 0, 0 };
        dx = new int[] { 0, 0, -1, 1 };

        hasPair = new boolean[MAX_HOLE];
        // dq = new ArrayDeque<>();
    }

    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("src/SSAFY/N5650/input.txt"));
        br = new BufferedReader(new InputStreamReader(System.in));

        T = parseInt(br.readLine().trim());

        for (int tc = 1; tc <= T; tc++) {
            result.append('#').append(tc).append(' ');
            init();
            int minTime = playPinball();
            for (int dir = 0; dir < 4; dir++) {
                ASCII.print(visited[dir], Y + 2, X + 2);
            }
            result.append(minTime).append('\n');
        }
        System.out.println(result);
    }

    static void init() throws NumberFormatException, IOException {
        X = Y = N = parseInt(br.readLine().trim());
        Arrays.fill(hasPair, false);
        // dq.clear();
        mapInit();
        visitedInit();
    }

    static void visitedInit() {
        for (int dir = 0; dir < 4; dir++) {
            for (int y = 0; y <= Y + 1; y++) {
                Arrays.fill(visited[dir][y], 0, X + 2, -100);
            }
        }
    }

    static void mapInit() throws IOException {
        Arrays.fill(map[0], 0, X + 2, 5);
        for (int y = 1; y <= Y; y++) {
            st = new StringTokenizer(br.readLine().trim());
            map[y][0] = 5;
            for (int x = 1; x <= X; x++) {
                map[y][x] = parseInt(st.nextToken());
                if (isWormHole(map[y][x])) {
                    setWormHole(y, x);
                }
            }
            map[y][X + 1] = 5;
        }
        Arrays.fill(map[Y + 1], 0, X + 2, 5);
    }

    /**
     * 웜홀을 WORMHOLE_MODULER 값 을 통해
     * 짝을 지어준다
     * 
     * @param y
     * @param x
     */
    static void setWormHole(int y, int x) {
        if (hasPair[map[y][x]]) {
            wormHole[1][map[y][x]] = new Node(y, x);
            map[y][x] += WORMHOLE_MODULER;
        } else {
            wormHole[0][map[y][x]] = new Node(y, x);
            hasPair[map[y][x]] = true;
        }
    }

    /**
     * 웜홀임?
     * 
     * @param symbol
     * @return
     */
    static boolean isWormHole(int symbol) {
        symbol %= WORMHOLE_MODULER;
        return symbol >= 6 && symbol <= 10;
    }

    /**
     * 맵을 전체 순회하며
     * 빈 공간일때 방문 안했더라면
     * 4방향으로 핀볼 굴려보기
     * 
     * @return
     */
    static int playPinball() {
        int maxScore = 0;
        Ball cur = new Ball(-1, -1, -1, 0);
        for (int y = 1; y <= Y; y++) {
            for (int x = 1; x <= X; x++) {
                int symbol = map[y][x];
                for (int dIdx = 0; dIdx < 4; dIdx++) {
                    if (isSpace(symbol) && !isVisited(dIdx, y, x)) {
                        cur.y = y;
                        cur.x = x;
                        cur.dir = dIdx;
                        maxScore = Math.max(playPinball(cur), maxScore);
                    }
                }
            }
        }
        return maxScore;
    }

    /**
     * 빈 공간임?
     * 
     * @param symbol
     * @return
     */
    static boolean isSpace(int symbol) {
        return symbol == SPACE;
    }

    /**
     * 방문함?
     * 기본값은 -1
     * 
     * @param y
     * @param x
     * @param dIdx
     * @return
     */
    static boolean isVisited(int dIdx, int y, int x) {
        return visited[dIdx][y][x] > -1;
    }

    /**
     * 판단은 뽑고 나서
     * 블럭 현재 위치로 판단
     * - 블럭 위치를 넣고
     * - 현재 위치가 블럭이면 튕긴 위치를 넣기
     * 웜홀은 다음위치로 판단
     * - 다음 웜홀 위치로 q에 넣기
     * 
     * @return
     */
    static int playPinball(Ball cur) {
        int maxScore = 0,
                y, x, score, dir,
                startY = cur.y, startX = cur.x;

        while (true) {

            y = cur.y;
            x = cur.x;
            dir = cur.dir;
            score = cur.score;

            if (isLowScore(cur))
                break;

            visited[dir][y][x] = score;

            y += dy[dir];
            x += dx[dir];

            /*
             * 블록, 바운더리 후처리
             * ndir, nscore 바뀜,
             * 바운더리도 5번블록 처리해서 블록취급
             */
            while (isBlock(map[y][x])) {
                // System.out.println("===============" + symbol + "===============");
                // ASCII.print(map, Y + 2, X + 2);
                // ASCII.print(visited[dir], Y + 2, X + 2);
                score++;
                dir = getNextDirBy(map[y][x], dir);

                y += dy[dir];
                x += dx[dir];
                if (visited[dir][y][x] >= score)
                    return score;
            }

            /*
             * 화이트홀 전처리
             * 이미 나온 값을 대입
             */
            if (isWormHole(map[y][x])) {
                visited[dir][y][x] = score;
                Node otherSide = getWormHole(map[y][x]);
                y = otherSide.y;
                x = otherSide.x;
            }
            cur.dir = dir;
            cur.y = y;
            cur.x = x;
            cur.score = score;

            // ASCII.print(map, Y + 2, X + 2);
            ASCII.print(visited[dir], Y + 2, X + 2);

            if (isBlackHole(cur) || isStart(startY, startX, cur)) {
                maxScore = Math.max(maxScore, cur.score);
                break;
            }

        }

        return maxScore;
    }

    /**
     * 현재 밟고 있는 블럭에 따라 진행해야할 반사 방향을 반환
     * 
     * 
     * @param symbol    현재 블록 [1, 5] └, ┌, ┐, ┘, ■
     * @param insertDir 진입각 [0, 3] 상하좌우
     * @return
     */
    static int getNextDirBy(int symbol, int insertDir) {
        switch (symbol) {
            case 1: // └
                if (insertDir == U || insertDir == R)
                    break;
                return Lcurve(insertDir);
            case 2: // ┌
                if (insertDir == D || insertDir == R)
                    break;
                return reverseLcurve(insertDir);
            case 3: // ┐
                if (insertDir == D || insertDir == L)
                    break;
                return Lcurve(insertDir);
            case 4:
                if (insertDir == U || insertDir == L)
                    break;
                return reverseLcurve(insertDir);
            default:
                break;
        }
        return reverseDir(insertDir);
    }

    /**
     * UDLR
     * U<->L
     * D<->R
     * 
     * @param insertDir
     * @return
     */
    static int Lcurve(int insertDir) {
        return (insertDir + 2) % 4;
    }

    /**
     * U<->R
     * D<->L
     * 
     * @param insertDir
     * @return
     */
    static int reverseLcurve(int insertDir) {
        return reverseDir(Lcurve(insertDir));
    }

    /**
     * 정반사
     * 
     * @param insertDir
     * @return
     */
    static int reverseDir(int insertDir) {
        return insertDir / 2 + insertDir % 2 ^ 1;
    }

    static boolean isBlock(int symbol) {
        return symbol >= 1 && symbol <= 5;
    }

    static boolean isLowScore(Ball cur) {
        return visited[cur.dir][cur.y][cur.x] >= cur.score;
    }


    /**
     * 시작점임?
     * 
     * @param startY
     * @param startX
     * @param cur
     * @return
     */
    static boolean isStart(int startY, int startX, Ball cur) {
        return cur.y == startY && cur.x == startX;
    }

    /**
     * 블랙홀임?
     * 
     * @param cur
     * @return
     */
    static boolean isBlackHole(Ball cur) {
        return map[cur.y][cur.x] == BLACK_HOLE;
    }

    /**
     * 주어진 심볼에 해당하는 다른쪽 웜홀 좌표 반환
     * 
     * @param symbol
     * @return
     */
    static Node getWormHole(int symbol) {
        return wormHole[symbol / WORMHOLE_MODULER][symbol % WORMHOLE_MODULER];
    }

    static class Node {
        int y, x;

        public Node(int y, int x) {
            this.y = y;
            this.x = x;
        }
    }

    static class Ball extends Node {
        int dir, score;

        public Ball(int y, int x, int dir, int score) {
            super(y, x);
            this.dir = dir;
            this.score = score;
        }
    }
}