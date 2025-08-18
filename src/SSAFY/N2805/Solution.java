package SSAFY.N2805;

import java.io.*;
import static java.lang.Integer.parseInt;

/**
 * 기본적으로 분할정복일텐데 - 분할정복이라면 n = 1 이면 return
 *
 * 네 귀퉁이 슥 슥슥 슥 하고 가운데 3번 빼는 구조일텐데
 *
 * 물론 인덱스로 풀 수 있지않을까?
 *
 * N 이 홀수라면 N>>1 은 중간값 idx일테고 0-basedIdx 그럼 for문 굴려
 *
 */
public class Solution {

    static BufferedReader br;
    static StringBuilder result = new StringBuilder();

    static int T, N, Y, X, midIdx, totalSum;
    static char[] row;

    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("src/SSAFY/N2805/sample_input.txt"));
        br = new BufferedReader(new InputStreamReader(System.in));

        T = parseInt(br.readLine().trim());

        for (int tc = 1; tc <= T; tc++) {
            result.append('#').append(tc).append(' ');

            N = parseInt(br.readLine().trim());
            Y = N;
            X = N;
            midIdx = N >> 1;
            totalSum = 0;
            for (int y = 0; y < Y; y++) {
                row = br.readLine().trim().toCharArray();

                int xOffset = Math.abs(y - midIdx);

                for (int x = xOffset; x < X - xOffset; x++) {
                    totalSum += row[x] - '0';
                }
            }
            result.append(totalSum).append('\n');
        }
        System.out.println(result);
    }

}
