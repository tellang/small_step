package DFS.N3109;

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
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    static char[][] map;
    static int R, C, MAX = 0;
    static int[] dr = { -1, 0, 1 };
    static boolean[] isCross = { true, false, true };
    static char NULL = '.', PIPE = 'p';
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
            map[r][0] = PIPE;
            dfs(r, 0);
            
        }
        System.out.println(MAX);
    }

    private static boolean isValid(int r, int c) {
        return r >= 0 && r < R && c >= 0 && c < C;
    }

    private static void dfs(int r, int c) {
        if(isCleared)
            return;

        if (c == C - 1) {

            MAX++;
            isCleared = true;
            return;
        }

        for (int i = 0; i < 3; i++) {
            int nr = r + dr[i];
            int nc = c + 1;

            if (isValid(nr, nc) && map[nr][nc] == NULL) {

                if (isCross[i]){
                    int br = r + dr[i];
                    if(isValid(br, c)){
                        if(map[br][c] == PIPE && map[r][nc] == PIPE) continue;
                    }

                }                
                map[nr][nc] = PIPE;
                printMap();
                dfs(nr, nc);
                if(isCleared) return;
            }
        }


    }
    static void printMap(){
        StringBuilder sb = new StringBuilder();
        for (var i : map) {
            for (char c : i) {
                sb.append(c);
            }
            sb.append('\n');
        }

        System.out.println(sb);
    }
}
