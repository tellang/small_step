package SSAFY.N5653;

import java.io.*;
import static java.lang.Integer.parseInt;
import java.util.*;

/*
 * 치즈 같은 문제?
 * 어짜피 K 만큼 다 돌려야하네
 * 그럼 애초에 타임라인을 미리 정할 수 도 있겠네?
 * - 비활성 시작 K초마다 비활성 활성 상태 전환
 * 
 * (1 ≤ X ≤ 10)
 * boolean[][] isActive[x][1~k~300] = ((k/K) % 2) == 1 ? false : true // 비활성 부터 시작, 1based
 * - 사실 불변이라 t만 있어도 되겠다
 * 
 * --특이 조건
 * 배양 용기의 크기는 무한하다. valid 필요 없다?
 * 50 x 50 으로 시작한다고 하면 300 초면
 * 400+50+400 -> 850 [400]->[450] OFFSET = 400
 * 
 * 매턴
 * isActive 상태인 것 만 q에 넣고 bfs
 * 아니면 Queue[x] 에서 isActive[x][1~k~300] 인 Queue만 선택해서 bfs하기
 * 
 * 
 * while(1 ≤ K ≤ 300)
 * 
 * 
 */
public class Solution {

    static BufferedReader br;
    static StringTokenizer st;
    static StringBuilder result = new StringBuilder();
    static final int LIMIT_TIME = 300, LIMIT_LIFE = 10, LIMIT_Y = 1000, LIMIT_X = 1000, OFFSET = 400;

    static int T, N, Y, M, X, K, MAX_TIME;

    static Queue<Node>[] multiQ;
    static boolean[][] isActive, visited;
    static int[][] map;
    static int[] mv = { 1, 0, -1, 0, 1 };

    static {
        multiQ = new ArrayDeque[LIMIT_LIFE + 1];
        isActive = new boolean[LIMIT_LIFE + 1][LIMIT_TIME + 1];
        for (int l = 1; l <= LIMIT_LIFE; l++) {
            multiQ[l] = new ArrayDeque<>();
            for (int t = l; t < LIMIT_TIME; t += (l + 1)) { // 비활성부터 시작
                isActive[l][t + 1] = true;
            }
        }
        visited = new boolean[LIMIT_Y][LIMIT_X];
        map = new int[LIMIT_Y][LIMIT_X];
    }

    static int getX(int x) {
        return x + OFFSET;
    }

    static int getY(int y) {
        return y + OFFSET;
    }

    /**
     * 오프셋을 적용한
     * 일종의 방문 처리
     * 
     * @param y
     * @param x
     * @param life
     */
    static void setMap(int y, int x, int life) {
        map[getY(y)][getX(x)] = life;
    }

    static int getMap(int y, int x) {
        return map[getY(y)][getX(x)];
    }

    static boolean getVisitedMap(int y, int x) {
        return visited[getY(y)][getX(x)];
    }

    static void setVisitedMap(int y, int x) {
        visited[getY(y)][getX(x)] = true;
    }

    static class Node {
        int y, x;

        public Node(int y, int x) {
            this.y = y;
            this.x = x;
        }
    }

    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("src/SSAFY/N5653/input.txt"));
        br = new BufferedReader(new InputStreamReader(System.in));

        T = parseInt(br.readLine().trim());

        for (int tc = 1; tc <= T; tc++) {
            result.append('#').append(tc).append(' ');
            init();
            localutil.print.console.ConsolePrinter.ASCII.print(isActive, LIMIT_LIFE + 1, MAX_TIME + 1,
                    (y, x) -> isActive[y][x]);
            int initalCount = makeMap();
            int count = wave() + initalCount;
            result.append(count).append('\n');
        }
        System.out.println(result);
    }

    /**
     * 생명력에 따라 Q에 분리해서 삽입 후 방문처리
     * 
     * @throws IOException
     */
    static int makeMap() throws IOException {
        int initialCount = 0;
        for (int y = 0; y < Y; y++) {
            st = new StringTokenizer(br.readLine().trim());
            for (int x = 0; x < X; x++) {
                int lifePower = parseInt(st.nextToken());
                if (lifePower == 0)
                    continue;

                multiQ[lifePower].offer(new Node(y, x));
                setMap(y, x, lifePower);
                setVisitedMap(y, x);
                initialCount++;
            }
        }
        return initialCount;
    }

    static void init() throws IOException {
        st = new StringTokenizer(br.readLine().trim());
        N = Y = parseInt(st.nextToken());
        M = X = parseInt(st.nextToken());
        K = MAX_TIME = parseInt(st.nextToken());

        for (int y = 0; y < LIMIT_Y; y++) { // 1M
            Arrays.fill(map[y], 0);
            Arrays.fill(visited[y], false);
        }

        for (int l = 1; l <= LIMIT_LIFE; l++) {
            multiQ[l].clear();
        }
    }

    /**
     * 매 턴
     * 선택된 생명력에 따른
     * Q들에서 bfs 진행 후 해당 큐에 다시 삽입
     * - 높은 생명력 먼저 진행
     * 
     * @return
     */
    static int wave() { // 3M
        int count = 0, time = 1, ny, nx;
        Queue<Node> q;

        for (; time <= MAX_TIME; time++) {
            for (int lifePower = LIMIT_LIFE; lifePower >= 1; lifePower--) { // 10

                if (!isActive[lifePower][time])
                    continue;

                q = multiQ[lifePower];
                int max = q.size();
                for (int i = 0; i < max; i++) { // 3000k
                    Node cur = q.poll();

                    for (int dir = 0; dir < 4; dir++) {
                        ny = cur.y + mv[dir];
                        nx = cur.x + mv[dir + 1];

                        if (isWeakOrLate(ny, nx, lifePower))// 이미 죽은 세포를 덮어버림
                            continue;
                        if (isVisited(ny, nx))
                            continue;
                        // localutil.print.console.ConsolePrinter.ASCII.print(map, 1000, 1000, (y,
                        // x)->map[y][x] != 0);
                        count++;

                        q.offer(new Node(ny, nx));
                    }
                }
            }
        }

        return count;
    }

    static boolean isVisited(int ny, int nx) {
        if (getVisitedMap(ny, nx))
            return true;

        setVisitedMap(ny, nx);
        return false;
    }

    /**
     * 늦거나 약할때
     * 
     * @param ny
     * @param nx
     * @param lifePower
     * @return
     */
    static boolean isWeakOrLate(int ny, int nx, int lifePower) {
        if (getMap(ny, nx) >= lifePower)
            return true;
        setMap(ny, nx, lifePower);
        return false;
    }

}