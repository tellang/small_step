package SSAFY.N2252;

/**
 * 위상정렬 제한 2초
 *
 * 128Mb = int x 32M N = 32K M = 100K
 *
 * --- preSet 관리해서 바로 잘라주고 counts로 오더링
 */
import java.io.*;
import static java.lang.Integer.parseInt;
import java.util.*;

public class Main {

    static BufferedReader br;
    static StringTokenizer st;
    static StringBuilder result = new StringBuilder();

    static int N, MAX_STUDENT, LINE_COUNT, M, USED = Integer.MAX_VALUE;
    
    static List<Integer>[] preSet; // 간선저장 [pre].posts
    static List<Integer> order = new ArrayList<>();
    static int[] counts;

    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("src/SSAFY/N2252/input"));
        br = new BufferedReader(new InputStreamReader(System.in));

        st = new StringTokenizer(br.readLine().trim());
        N = parseInt(st.nextToken());
        M = parseInt(st.nextToken());

        MAX_STUDENT = N;
        LINE_COUNT = M;
        preSet = new ArrayList[N + 1];
        counts = new int[N + 1];
        counts[0] = USED;
        order.add(0);
        for (int studentNum = 1; studentNum <= MAX_STUDENT; studentNum++) { //n = 32K
            preSet[studentNum] = new ArrayList<>();
            order.add(studentNum);
        }

        for (int lineCount = 1; lineCount <= LINE_COUNT; lineCount++) { //100K
            st = new StringTokenizer(br.readLine().trim());
            int pre = parseInt(st.nextToken());
            int post = parseInt(st.nextToken());
            preSet[pre].add(post);
            counts[post]++;
        }
        for (int cycle = 0; cycle < MAX_STUDENT; cycle++) { //32x32M
            Collections.sort(order, ((o0, o1) -> counts[o0] - counts[o1]));
            int idx = order.get(0);
            result.append(idx).append(' ');
            counts[idx] = USED;
            for (int postIdx : preSet[idx]) {
                counts[postIdx]--;
            }
            order.remove(0); //안하면 대머리가됨
        }

        System.out.println(result);
    }
}
