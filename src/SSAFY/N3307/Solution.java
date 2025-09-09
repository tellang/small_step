package SSAFY.N3307;

import java.io.*;
import static java.lang.Integer.parseInt;
import java.util.*;

/**
 * 정석적인 LIS
 */
public class Solution {

    static BufferedReader br;
    static StringTokenizer st;
    static StringBuilder result = new StringBuilder();
    
    static final int NULL = Integer.MAX_VALUE;
    static int T, N, MAX_LENGTH;

    static int[] LIS, originArr;

    static {
        N = MAX_LENGTH = 1000;
        originArr = new int[MAX_LENGTH];
        LIS = new int[MAX_LENGTH];
    }

    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("src/SSAFY/N3307/input.txt"));
        br = new BufferedReader(new InputStreamReader(System.in));

        T = parseInt(br.readLine().trim());

        for (int tc = 1; tc <= T; tc++) {
            result.append('#').append(tc).append(' ');
            init();
            int length = getLISLength();
            result.append(Arrays.toString(Arrays.copyOf(LIS, MAX_LENGTH)));
            result.append(length).append('\n');
        }
        System.out.println(result);
    }

    /**
     * 적절한 idx에 LIS를 채운다
     * @return
     */
    static int getLISLength() {
        int LISIdx = 0, LISLen = 1;

        for (int oriIdx = 0; oriIdx < MAX_LENGTH; oriIdx++) {
            int num = originArr[oriIdx];
            LISIdx = getIdxBy(num);
            LIS[LISIdx] = Math.min(num, LIS[LISIdx]);
            LISLen = Math.max(LISLen, LISIdx + 1);
        }

        return LISLen;
    }

    /**
     * 중복이 있을 수 있으니
     * 음수 양수 처리
     * @param num
     * @return
     */
    static int getIdxBy(int num) {
        int idx = Arrays.binarySearch(LIS, num);
        return idx < 0 ? ~idx : idx;
    }

    static void init() throws NumberFormatException, IOException {
        
        N = MAX_LENGTH = parseInt(br.readLine().trim());
        st = new StringTokenizer(br.readLine().trim());
        for (int arrIdx = 0; arrIdx < MAX_LENGTH; arrIdx++) {
            originArr[arrIdx] = parseInt(st.nextToken());
            LIS[arrIdx] = NULL;
        }
        Arrays.fill(LIS, MAX_LENGTH, 1000, NULL);
    }
}