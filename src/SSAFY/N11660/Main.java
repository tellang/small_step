package SSAFY.N11660;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import static java.lang.Integer.parseInt;
import java.util.StringTokenizer;

public class Main {

    // System.setIn(new FileInputStream("sample_input.txt"));

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    static StringBuilder sb = new StringBuilder();

    static int N, M; // N: 배열의 크기, M: 질의의 수
    static int[][] matrix; // 입력 배열 및 누적합 저장

    public static void main(String[] args) throws IOException {
        st = new StringTokenizer(br.readLine());
        N = parseInt(st.nextToken());
        M = parseInt(st.nextToken());
        matrix = new int[N + 1][N + 1];

        // 각 행에 대한 1차원 누적합 계산
        for (int y = 1; y <= N; y++) {
            st = new StringTokenizer(br.readLine());
            for (int x = 1; x <= N; x++) {
                matrix[y][x] = matrix[y][x - 1] + parseInt(st.nextToken());
            }
        }

        // M개의 질의를 처리하며 직사각형 영역의 합 계산
        for (int m = 0; m < M; m++) {
            st = new StringTokenizer(br.readLine());
            int result = 0;
            int[] query = new int[4]; // 쿼리 좌표를 담는 배열
            for (int i = 0; i < 4; i++) {
                query[i] = parseInt(st.nextToken());
            }

            int y1 = query[0];
            int x1 = query[1];
            int y2 = query[2];
            int x2 = query[3];

            // 1차원 누적합을 이용하여 직사각형 영역의 합계산
            for (int y = y1; y <= y2; y++) {
                result += (matrix[y][x2] - matrix[y][x1 - 1]);
            }
            sb.append(result).append('\n');
        }
        System.out.println(sb);
    }
}
