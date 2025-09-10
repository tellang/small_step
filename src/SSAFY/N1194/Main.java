package SSAFY.N1194;

import java.io.*;
import static java.lang.Integer.parseInt;
import java.util.*;

/**
 * 3차원 배열 bfs
 * 방문 int 필요한가? 굳이
 * visited[key < (1 << MAX_KEY)][Y][X]
 * - key <= N = 1<<6
 */
public class Main {

    static BufferedReader br;
    static StringTokenizer st;
    static StringBuilder result = new StringBuilder();

    static final char START_SYM = '0', DAAL_SYM = '1', KABE_SYM = '#';
    static final int MAX_KEY = 1 << 6;
    static final int[] mv = { 1, 0, -1, 0, 1 };

    static boolean[][][] visited;
    static char[][] map;
    static int N, Y, M, X;
    static Deque<Node> q;

    static {
        q = new ArrayDeque<>();
    }

    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("src/SSAFY/N1194/input.txt"));
        br = new BufferedReader(new InputStreamReader(System.in));
        init();
        int minTime = findOne();
        System.out.println(minTime);
    }

    static int findOne() {

        int y, x, t, key, ny, nx, nt, nkey;
        char symbol;

        while (!q.isEmpty()) {
            Node cur = q.poll();
            y = cur.y;
            x = cur.x;
            t = cur.t;
            key = cur.key;
            for (int dir = 0; dir < 4; dir++) {
                ny = y + mv[dir];
                nx = x + mv[dir + 1];
                nt = t + 1;
                nkey = key;
                if (!valid(ny, nx))
                    continue; // 좌표상 오류

                symbol = map[ny][nx];
                if (isDoor(symbol) && !canOpen(symbol, key))
                    continue; // 문인데 못엶
                else if (isWall(symbol))
                    continue; // 벽임
                else if (isKey(symbol)) { // 열쇠임
                    nkey = getNewKey(nkey, symbol);
                } else if (isDaal(symbol)) { // 달이차오른다가자
                    return nt;
                }
                if (visited[nkey][ny][nx])
                    continue; // 이미 방문함

                visited[nkey][ny][nx] = true;
                q.offer(new Node(ny, nx, nt, nkey));
            }

        }

        return -1; // 못감
    }

    /**
     * 키를 만든다
     * @param nkey
     * @param symbol
     * @return
     */
    static int getNewKey(int nkey, char symbol) {
        nkey |= 1 << (symbol - 'a');
        return nkey;
    }

    /**
     * 달이차오르나?
     * 
     * @param symbol
     * @return
     */
    static boolean isDaal(char symbol) {
        return symbol == DAAL_SYM;
    }

    /**
     * a ~ f
     * 열쇠에요?
     * 
     * @param symbol
     * @return
     */
    static boolean isKey(char symbol) {
        return symbol >= 'a' && symbol <= 'f';
    }

    /**
     * 결계인가?
     * 
     * @param symbol
     * @return
     */
    static boolean isWall(char symbol) {
        return symbol == KABE_SYM;
    }

    /**
     * A ~ F
     * 문이에요?
     * 
     * @return
     */
    static boolean isDoor(char symbol) {
        return symbol >= 'A' && symbol <= 'F';
    }

    /**
     * 문을 만난 경우에만 실행
     * 문 일 경우 열 수 있는지 확인
     * 
     * @param c
     * @param keySet
     * @return
     */
    static boolean canOpen(char lock, int keySet) {
        int targetLock = 1 << (lock - 'A');
        return (keySet & (targetLock)) != 0;
    }

    /**
     * 좌표의 유효성만 검사
     * 
     * @param y
     * @param x
     * @return
     */
    static boolean valid(int y, int x) {
        return y >= 0 && y < Y && x >= 0 && x < X;
    }

    static void init() throws IOException {
        st = new StringTokenizer(br.readLine().trim());
        N = Y = parseInt(st.nextToken());
        M = X = parseInt(st.nextToken());
        map = new char[Y][X];
        visited = new boolean[MAX_KEY][Y][X];

        for (int y = 0; y < Y; y++) {
            map[y] = br.readLine().trim().toCharArray();
            for (int x = 0; x < X; x++) {
                if (map[y][x] == START_SYM) {
                    q.offer(new Node(y, x, 0, 0));
                    visited[0][y][x] = true;
                    map[y][x] = '.';
                }
            }
        }
    }

    static class Node {
        int y, x, t, key;

        public Node(int y, int x, int t, int key) {
            this.y = y;
            this.x = x;
            this.t = t;
            this.key = key;
        }
    }
}