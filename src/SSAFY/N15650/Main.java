package SSAFY.N15650;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import static java.lang.Integer.parseInt;
import java.util.StringTokenizer;

public class Main {

    static int N, M;
    static int[] arr;
    static StringBuilder sb = new StringBuilder();

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = parseInt(st.nextToken());
        M = parseInt(st.nextToken());

        arr = new int[M];

        // 백트래킹 시작
        backTracking(1, 0);

        System.out.println(sb);
    }

    static void backTracking(int s, int i) {
        // 종료 조건: M개의 숫자를 모두 선택했을 때
        if (i == M) {
            // 선택된 숫자들을 StringBuilder에 추가
            for (int num : arr) {
                sb.append(num).append(' ');
            }
            sb.append('\n');
            return;
        }

        // s부터 N까지의 숫자를 탐색
        for (int j = s; j <= N; j++) {
            arr[i] = j;
            // 다음 숫자는 현재 선택한 숫자(j)보다 큰 값에서 탐색
            backTracking(j + 1, i + 1);
        }
    }
}
