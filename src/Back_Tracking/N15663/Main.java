package Back_Tracking.N15663;

import static java.lang.Integer.MIN_VALUE;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {

    final static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    static int N, M;
    static int[] order;
    static int[] arr;
    static boolean[] visited;
    static StringBuilder result = new StringBuilder();

    public static void main(String[] args) throws IOException {
        st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        arr = new int[N + 1];
        arr[N] = MIN_VALUE;
        order = new int[M];
        visited = new boolean[N + 1];
        st = new StringTokenizer(br.readLine());
        for (int i = 1; i <= N; i++) {
            arr[i] = Integer.parseInt(st.nextToken());
        }
        Arrays.sort(arr);
        combination(0);
        System.out.println(result);
    }

    private static void combination(int count) {
        if (count == M) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < M; i++) {
                sb.append(order[i]).append(' ');
            }
            result.append(sb).append('\n');
            return;
        }

        int before = -1;
        for (int i = 1; i <= N; i++) {
            if (!visited[i] && (before != arr[i])) {

                visited[i] = true;
                order[count] = arr[i];
                before = order[count];
                combination(count + 1);

                visited[i] = false;

            }
        }
    }
}
