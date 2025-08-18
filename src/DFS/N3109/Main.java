package DFS.N3109;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/*
 * 전깃줄 문제와 같은느낌으로
 * 위에서 내려오면 그리디하게 가능할듯
 * - 위에서 내려오기 때문에 위를 먼저 본다
 * 첫줄과 끝줄은 .
 * 
 * 로직이 틀림
 *  - .x
 *    x. 가 파이프가 아닌 벽이면 가능
 *      이전 r 값내기전에 r == 0일때 예외처리
 */
public class Main {

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;

    // 맵의 정보
    static char[][] map;
    // 행R, 열C, 최대 파이프라인 수 MAX
    static int R, C, MAX = 0;
    // 탐색 방향(상, 중, 하)
    static int[] dr = {-1, 0, 1};
    // 파이프 설치 성공 여부
    static boolean isCleared;

    public static void main(String[] args) throws IOException {
        st = new StringTokenizer(br.readLine());
        R = Integer.parseInt(st.nextToken());
        C = Integer.parseInt(st.nextToken());
        map = new char[R][C];
        for (int r = 0; r < R; r++) {
            st = new StringTokenizer(br.readLine());
            map[r] = st.nextToken().toCharArray();
        }

        for (int r = 0; r < R; r++) {
            isCleared = false;
            map[r][0] = 'x'; // 파이프라인 시작점 설치
            dfs(r, 0);

        }
        System.out.println(MAX);
    }

    /**
     * 현재 위치가 유효한 맵 범위 내에 있는지 확인
     *
     * @param r 현재 행
     * @param c 현재 열
     * @return 유효 범위 여부
     */
    private static boolean isValid(int r, int c) {
        return r >= 0 && r < R && c >= 0 && c < C;
    }

    /**
     * 깊이 우선 탐색(DFS)을 이용한 파이프라인 설치
     *
     * @param r 현재 행
     * @param c 현재 열
     */
    private static void dfs(int r, int c) {
        if (isCleared) {
            return;
        }

        if (c == C - 1) {
            MAX++;
            isCleared = true;
            return;
        }

        for (int i = 0; i < 3; i++) {
            int nr = r + dr[i];
            int nc = c + 1;

            if (isValid(nr, nc) && map[nr][nc] == '.') {
                map[nr][nc] = 'x';
                dfs(nr, nc);
                if (isCleared) {
                    return;
                }
            }
        }
    }
}
