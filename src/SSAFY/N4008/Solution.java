package SSAFY.N4008;

import java.io.*;
import static java.lang.Integer.parseInt;
import java.util.*;

/**
 * 1초 기준 T=10, N <= 12 단순 순열 - 이지만 idx 늘리지 말고 연산자 있으면 있는만큼 줘야한다 - 사칙연산 배열 써서 갯수
 * 증감 하여 사용 - 연산자 총 갯수만큼 순열 진행
 *
 * ---
 *
 * 연산자 우선순위 없음 나눗셈 소수 버림 int 범위 연산
 */
public class Solution {

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;

    static StringBuilder result = new StringBuilder();
    static int T, N,
            PLUS = 0, MINUS = 1, MULTI = 2, DIV = 3,
            MAX_VALUE, MIN_VALUE;

    static int[] operators = new int[4], nums, selectedOpr;

    public static void main(String[] args) throws IOException {
        T = parseInt(br.readLine().trim());

        for (int tc = 1; tc <= T; tc++) {
            result.append('#').append(tc).append(' ');

            N = parseInt(br.readLine().trim());
            nums = new int[N];
            selectedOpr = new int[N - 1]; // 연산자는 N-1개
            MAX_VALUE = Integer.MIN_VALUE;
            MIN_VALUE = Integer.MAX_VALUE;

            st = new StringTokenizer(br.readLine());
            for (int opIdx = PLUS; opIdx <= DIV; opIdx++) {
                operators[opIdx] = parseInt(st.nextToken());
            }

            st = new StringTokenizer(br.readLine());
            for (int n = 0; n < N; n++) {
                nums[n] = parseInt(st.nextToken());
            }

            permutaion(0);

            result.append(MAX_VALUE - MIN_VALUE).append('\n');
        }

        System.out.print(result);
    }

    static void permutaion(int count) {
        if (count == N - 1) {
            calculation();
            return;
        }

        for (int opr = PLUS; opr <= DIV; opr++) {
            if (operators[opr] > 0) { // 사용할 연산자가 남아있다면
                operators[opr]--;             // 사용
                selectedOpr[count] = opr;     // 현재 자리에 기록
                permutaion(count + 1);      // 다음 자리로 이동
                operators[opr]++;             // 백트래킹 (반납)
            }
        }
    }

    /**
     * 완성된 순열로 계산
     */
    static void calculation() {
        int localResult = nums[0];
        // 2. 계산 로직 인덱스 수정
        for (int i = 0; i < N - 1; i++) {
            int operator = selectedOpr[i];
            int nextNum = nums[i + 1];

            switch (operator) {
                case 0:// + 
                    localResult += nextNum;
                    break;
                case 1: // -
                    localResult -= nextNum;
                    break;
                case 2: // *
                    localResult *= nextNum;
                    break;
                case 3: // /
                    localResult /= nextNum;
                    break;
            }
        }

        MAX_VALUE = Math.max(MAX_VALUE, localResult);
        MIN_VALUE = Math.min(MIN_VALUE, localResult);
    }
}
