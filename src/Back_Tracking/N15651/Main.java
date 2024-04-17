package Back_Tracking.N15651;

import static java.lang.Integer.parseInt;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

    final static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringBuilder result = new StringBuilder();
    static int N, M;

    public static void main(String[] args) throws IOException {
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = parseInt(st.nextToken());
        M = parseInt(st.nextToken());

        maker(0 ,new int[M]);
        System.out.println(result);
    }

    public static void maker(int len, int[] arr) {
        if (len == M) {
            for (int m = 0; m < M; m++) {
                int i = arr[m];
                result.append(i).append(' ');
            }
            result.append('\n');
            return;
        }
        for (int n = 1; n <= N; n++) {

            int[] clone = arr.clone();
            clone[len] = n;
            maker(len + 1, clone);
        }
    }
}
