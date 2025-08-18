package SWEA.N2001;

/*
 * 255x255x10
 * 2차원 누적합(Prefix Sum)을 이용
 * */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import static java.lang.Integer.*;
import java.util.StringTokenizer;

public class Solution {

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    static int T, N, M, Y, X, FLY_POINT;
    static StringBuilder result = new StringBuilder();
    /** 입력된 파리 수를 저장하는 원본 배열 */
    static int[][] map;
    /** 2차원 누적합을 저장하는 배열 */
    static int[][] sumMap;

    public static void main(String[] args) throws IOException {
        T = parseInt(br.readLine());

        for (int testCase = 1; testCase <= T; testCase++) {
            result.append('#').append(testCase).append(' ');
            st = new StringTokenizer(br.readLine());
            N = parseInt(st.nextToken());
            M = parseInt(st.nextToken());
            Y = N;
            X = N;

            FLY_POINT = 0;
            map = new int[Y][X];
            sumMap = new int[Y + 1][X + 1];

            for (int y = 0; y < Y; y++) {
                st = new StringTokenizer(br.readLine());
                for (int x = 0; x < X; x++) {
                    map[y][x] = parseInt(st.nextToken());
                }
            }

            // 2차원 누적합
            for (int y = 1; y <= N; y++) {
                for (int x = 1; x <= N; x++) {
                    sumMap[y][x] = map[y - 1][x - 1]
                            + sumMap[y - 1][x]
                            + sumMap[y][x - 1]
                            - sumMap[y - 1][x - 1];
                }
            }

            // 모든 가능한 파리채 위치를 탐색
            for (int y = 0; y <= Y - M; y++) {
                for (int x = 0; x <= X - M; x++) {
                    FLY_POINT = Math.max(FLY_POINT, getFlyPoint(y, x));
                }
            }

            result.append(FLY_POINT).append('\n');
        }
        System.out.println(result);
    }

    /**
     * 주어진 시작점 (y, x)를 기준으로 M x M 크기 영역의 파리 수를 반환
     */
    public static int getFlyPoint(int startY, int startX) {
        int endY = startY + M;
        int endX = startX + M;

        return sumMap[endY][endX]
                - sumMap[endY][startX]
                - sumMap[startY][endX]
                + sumMap[startY][startX];
    }
}
