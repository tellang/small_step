package SSAFY.N7733;

/**
 * 100x100x100 완탐 * 1부터 100까지의 모든 날짜에 대해 치즈를 먹고, 남은 덩어리 개수를 계산하는 완전 탐색 문제 매일
 * BFS를 실행하여 연결된 치즈 덩어리 수를 세고, 그 중 최댓값을 찾습니다.
 */
import java.io.*;
import static java.lang.Integer.parseInt;
import java.util.*;

public class Solution {

    static BufferedReader br;
    static StringTokenizer st;
    static StringBuilder result = new StringBuilder();
    static int T, N, maxChunk, ATE = -1;

    static int[][] cheese, visited;
    static Queue<Node> q = new ArrayDeque<>();
    static int[] mv = {-1, 0, 1, 0, -1};

    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("src/SSAFY/N7733/input"));
        br = new BufferedReader(new InputStreamReader(System.in));

        T = parseInt(br.readLine().trim());

        for (int tc = 1; tc <= T; tc++) {
            result.append('#').append(tc).append(' ');

            initialize();
            for (int day = 1; day <= 100; day++) {
                eat(day);
                countChunk(day);
            }
            result.append(maxChunk).append('\n');
        }
        System.out.println(result);
    }

    /**
     * 특정 날짜에 해당하는 치즈를 먹는 메서드
     *
     * @param day 먹는 날짜
     */
    static void eat(int day) {
        for (int y = 0; y < N; y++) {
            for (int x = 0; x < N; x++) {
                if (cheese[y][x] == day) {
                    cheese[y][x] = ATE;
                }
            }
        }
    }

    /**
     * 남은 치즈 덩어리 개수를 세는 메서드 BFS를 사용하여 연결된 덩어리를 탐색
     *
     * @param day 현재 날짜 (visited 배열 초기화에 사용)
     */
    static void countChunk(int day) {
        int localChunk = 0;
        for (int y = 0; y < N; y++) {
            for (int x = 0; x < N; x++) {
                if (cheese[y][x] == ATE || visited[y][x] == day) {
                    continue;
                }

                localChunk++;

                q.clear();
                q.offer(new Node(y, x));

                while (!q.isEmpty()) {
                    Node poll = q.poll();

                    for (int i = 0; i < 4; i++) {
                        int ny = poll.y + mv[i];
                        int nx = poll.x + mv[i + 1];

                        if (isValid(ny, nx)
                                && cheese[ny][nx] != ATE
                                && visited[ny][nx] != day) {

                            visited[ny][nx] = day;
                            q.offer(new Node(ny, nx));

                        }
                    }
                }

            }
        }
        maxChunk = Math.max(maxChunk, localChunk);
    }

    /**
     * 주어진 좌표가 맵의 유효 범위 내에 있는지 확인
     *
     * @param y 확인할 행 인덱스
     * @param x 확인할 열 인덱스
     * @return 유효 범위 여부
     */
    static boolean isValid(int y, int x) {
        return y >= 0 && y < N && x >= 0 && x < N;
    }

    /**
     * 테스트 케이스 초기화
     *
     * @throws IOException 입출력 오류 발생 시
     */
    static void initialize() throws IOException {
        N = parseInt(br.readLine().trim());
        cheese = new int[N][N];
        visited = new int[N][N];

        maxChunk = 1;
        for (int y = 0; y < N; y++) {
            st = new StringTokenizer(br.readLine().trim());
            for (int x = 0; x < N; x++) {
                cheese[y][x] = parseInt(st.nextToken());
            }
        }
    }

    /**
     * 좌표를 저장하는 Node 클래스
     */
    static class Node {

        int y, x;

        public Node(int y, int x) {
            this.y = y;
            this.x = x;
        }

    }
}
