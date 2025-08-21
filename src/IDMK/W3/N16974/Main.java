package IDMK.W3.N16974;

import java.io.*;
import java.util.*;

public class Main {

    static BufferedReader br;
    static StringTokenizer st;

    static int N;
    static long X;
    static long[] burgerSize, pattySize;

    public static void main(String[] args) throws Exception {
        System.setIn(new FileInputStream("src/IDMK/W3/N16974/input"));
        br = new BufferedReader(new InputStreamReader(System.in));
        st = new StringTokenizer(br.readLine().trim());
        N = Integer.parseInt(st.nextToken());
        X = Long.parseLong(st.nextToken());

        burgerSize = new long[N + 1];
        pattySize = new long[N + 1];

        burgerSize[0] = 1;
        pattySize[0] = 1;

        // 버거 크기 및 패티 수
        for (int i = 1; i <= N; i++) {
            burgerSize[i] = (burgerSize[i - 1] << 1) + 3; // 2 * B(n-1) + 1 + 2
            pattySize[i] = (pattySize[i - 1] << 1) + 1; // 2 * P(n-1) + 1
        }

        System.out.println(countPatty(N, X));
    }

    /**
     * n레벨 버거의 x번째까지 패티 수를 계산
     *
     * @param nLevel 현재 버거 레벨
     * @param xEat 먹는 양
     * @return 먹은 패티 수
     */
    static long countPatty(int nLevel, long xEat) {
        long result = 0L;

        while (true) {
            if (xEat <= 0) {
                return result;
            }
            if (nLevel == 0) {
                return result + (xEat >= 1 ? 1 : 0);
            }

            long leftSize = burgerSize[nLevel - 1]; // 왼쪽 B(n-1) 크기

            // B(n) = B B(n-1) P B(n-1) B
            if (xEat == 1) { // B [x] B(n-1) P B(n-1) B
                return result;
            } else if (xEat <= 1 + leftSize) { // B B(n-1, [x]) P B(n-1) B
                xEat--;
                nLevel--;
            } else if (xEat == 1 + leftSize + 1) { // B B(n-1) P [x] B(n-1) B
                return result + pattySize[nLevel - 1] + 1;
            } else if (xEat <= 1 + leftSize + 1 + leftSize) { // B B(n-1) P B(n-1, [x]) B
                result += pattySize[nLevel - 1] + 1;
                xEat -= (1 + leftSize + 1);
                nLevel--;
            } else { // B B(n-1) P B(n-1) B [x]
                return result + pattySize[nLevel];
            }
        }
    }
}
