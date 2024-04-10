package Two_Pointer.N9024;

import static java.lang.Integer.MAX_VALUE;
import static java.lang.Integer.parseInt;
import static java.lang.Math.abs;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {

    static int N;
    static int K;
    static int T;
    static int K_NEAREST;
    static int K_COUNT;
    static StringBuilder result = new StringBuilder();

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        T = parseInt(br.readLine());
        for (int t = 0; t < T; t++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            N = parseInt(st.nextToken());
            K = parseInt(st.nextToken());
            int[] nums = new int[N];

            st = new StringTokenizer(br.readLine());
            for (int n = 0; n < N; n++) {
                nums[n] = parseInt(st.nextToken());
            }
            Arrays.sort(nums);

            K_NEAREST = MAX_VALUE;
            K_COUNT = 0;

            int i = 0;
            int j = N - 1;
            while (j > i) {
                int sum = nums[i] + nums[j];
                int searchSize = abs(K - sum);

                if (searchSize == K_NEAREST) {
                    K_COUNT++;
                } else if (searchSize < K_NEAREST) {
                    K_COUNT = 1;
                    K_NEAREST = searchSize;
                }
                if (sum < K) {
                    i++;
                } else if (sum > K) {
                    j--;
                } else {
                    i++;
                    j--;
                }

            }
            result.append(K_COUNT).append('\n');
        }
        System.out.println(result);
    }
}
