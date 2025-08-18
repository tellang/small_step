package IDMK.W1.N1080;

import java.io.*;
import static java.lang.Integer.parseInt;
import java.util.*;

/**
 * 2.5k 한번 뒤집은 포인트는 다시 뒤집을 필요가 없다 둘을 합친 행렬을 0행렬로 만들어야한다
 *
 * 진행 방향을 y, x가 증가하는 방향이라면 좌측은 불변이다 y = 1 -> N - 2
 */
public class Main {

    static BufferedReader br;
    static StringTokenizer st;
    static int N, M, COUNT;

    static int[][] map;
    static char[] arr;

    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("src/IDMK/N1080/sample_input.txt"));
        br = new BufferedReader(new InputStreamReader(System.in));
        st = new StringTokenizer(br.readLine().trim());
        N = parseInt(st.nextToken());
        M = parseInt(st.nextToken());

        map = new int[N][M];

        for (int n = 0; n < N; n++) {
            arr = br.readLine().trim().toCharArray();
            for (int m = 0; m < M; m++) {
                map[n][m] = arr[m] - '0';
            }
        }

        for (int n = 0; n < N; n++) {
            arr = br.readLine().trim().toCharArray();
            for (int m = 0; m < M; m++) {
                map[n][m] ^= arr[m] - '0';
            }
        }

        for (int n = 0; n < N - 2; n++) {
            for (int m = 0; m < M - 2; m++) {
                if (map[n][m] == 0) {
                    continue;
                }
                COUNT++;
                filp3x3(n, m);
            }
            if ((map[n][M - 1] | map[n][M - 2]) == 1) {
                System.out.println(-1);
                return;
            }
        }
        if (is1InLast2Lines()) {
            System.out.println(-1);
        } else {
            System.out.println(COUNT);
        }
    }

    static boolean is1InLast2Lines() {
        for (int n = Math.max(N - 2, 0); n < N; n++) { // N==1 케이스 처리
            for (int m = 0; m < M; m++) {
                if (map[n][m] == 1) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 주어진 시작점에서 3x3 행렬을 뒤집는 메서드
     *
     * @param nStart 뒤집기를 시작할 행 인덱스
     * @param mStart 뒤집기를 시작할 열 인덱스
     */
    static void filp3x3(int nStart, int mStart) {
        for (int n = nStart; n < nStart + 3; n++) {
            for (int m = mStart; m < mStart + 3; m++) {
                map[n][m] ^= 1;
            }
        }
    }
}
