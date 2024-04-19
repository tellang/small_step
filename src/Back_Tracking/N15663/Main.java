package Back_Tracking.N15663;

import static java.lang.Integer.MAX_VALUE;
import static java.lang.Integer.MIN_VALUE;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

public class Main {

    final static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    static int N, M;
    static int[] order;
    static int[] arr;
    static boolean[] visited;
    static Set<String> set = new HashSet<>();
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
        combination(0, 0);
        System.out.println(result);
    }

    private static void combination(int idx, int count) {
        if (count == M) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < M; i++) {
                sb.append(order[i]).append(' ');
            }
            if (!set.contains(sb.toString())) {
                result.append(sb).append('\n');
                set.add(sb.toString());
            }
            return;
        }

        for (int i = 1; i <= N; i++) {
            if (!visited[i]) {

                visited[i] = true;
                int before = order[count];
                order[count] = arr[i];
                combination(i + 1, count + 1);

                visited[i] = false;
                order[count] = before;

            }
        }
    }
}
