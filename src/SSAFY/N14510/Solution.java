package SSAFY.N14510;

/**
 * 어짜피 쉬는날이 있으니
 * 홀수일과 짝수일은 완전한 독립이며
 * 그렇기 때문에 홀수일과 짝수일의 종료일 중 
 * 가장 늦은 것 찾기
 * ---
 * 짝수 홀수간의 평탄화를 해야한다 1짝 -> 2홀
 */
import java.io.*;
import static java.lang.Integer.parseInt;
import java.util.*;

public class Solution {

    static BufferedReader br;
    static StringTokenizer st;
    static StringBuilder result = new StringBuilder();

    static int T, N, MAX_TREE, MAX_HEIGHT;

    static int[] trees;

    static {
        N = MAX_TREE = 100;
        trees = new int[MAX_TREE];
    }

    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("src/SSAFY/N14510/input.txt"));
        br = new BufferedReader(new InputStreamReader(System.in));

        T = parseInt(br.readLine().trim());

        for (int tc = 1; tc <= T; tc++) {
            result.append('#').append(tc).append(' ');
            init();
            long maxDays = getMaxDays();
            result.append(maxDays).append('\n');
        }
        System.out.println(result);
    }

    /**
     * 홀수일과 짝수일을 누적하고 
     * 이를 사용해서 최종 답을 생성
     * 
     * @return
     */
    static long getMaxDays() {
        long evenDays = 0;
        long oddDays = 0;

        for (int tIdx = 0; tIdx < MAX_TREE; tIdx++) {
            int diff = MAX_HEIGHT - trees[tIdx];
            evenDays += diff / 2;
            oddDays += diff % 2;
        }
        long totalOddDays = (oddDays << 1) - 1;
        long totalEvenDays = evenDays << 1;
        long minDay = Math.max(totalOddDays, totalEvenDays);
        long localMin;
        while (true) {

            if (evenDays > oddDays + 1) {
                evenDays--;
                oddDays += 2;
            }

            /*
             * 짝수/홀수 성장 횟수 교환의 비대칭성
             * - 가능: `+2` 성장 1회 -> `+1` 성장 2회 (한 나무를 홀수 날 두 번 물을 주는 것은 가능)
             * - 불가능: `+1` 성장 2회 -> `+2` 성장 1회 (서로 다른 두 나무의 필요량을 합쳐 한 나무에 물을 줄 수는 없음)
             * 결론: `even`을 줄여 `odd`를 늘리는 변환만 가능함
             */
            // else if (evenDays < oddDays + 1) {
            //   evenDays++;
            //   oddDays -= 2;
            // }
            totalOddDays = (oddDays << 1) - 1;
            totalEvenDays = evenDays << 1;
            localMin = Math.max(totalOddDays, totalEvenDays);
            if (localMin >= minDay)
                break;
            minDay = localMin;
        }

        return minDay;
    }

    static void init() throws NumberFormatException, IOException {
        N = MAX_TREE = parseInt(br.readLine().trim());
        MAX_HEIGHT = 0;
        st = new StringTokenizer(br.readLine().trim());
        for (int tIdx = 0; tIdx < MAX_TREE; tIdx++) {
            trees[tIdx] = parseInt(st.nextToken());
            MAX_HEIGHT = Math.max(MAX_HEIGHT, trees[tIdx]);
        }
    }
}