package IDMK.W3.N21738;

/**
 * N 330K?
 *
 * 단순 bfs?
 */
import java.io.*;
import static java.lang.Integer.parseInt;
import java.util.*;

public class Main {

    static BufferedReader br;
    static StringTokenizer st;

    static int N, S, P;
    static List<Integer>[] iceberg; //1based
    static Queue<Node> q = new ArrayDeque<>();
    static boolean[] isVitied; //1based
    static int[] implants; //1based

    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("src/IDMK/W3/N21738/input"));
        br = new BufferedReader(new InputStreamReader(System.in));

        st = new StringTokenizer(br.readLine().trim());
        N = parseInt(st.nextToken());
        S = parseInt(st.nextToken());
        P = parseInt(st.nextToken());

        isVitied = new boolean[N + 1];
        iceberg = new ArrayList[N + 1];
        implants = new int[S + 1];
        implants[0] = Integer.MAX_VALUE; // 정렬시 사용안하기 위함
        for (int n = 1; n <= N; n++) {
            iceberg[n] = new ArrayList<>();
        }

        for (int n = 1; n < N; n++) {
            st = new StringTokenizer(br.readLine().trim());

            int iceA = parseInt(st.nextToken());
            int iceB = parseInt(st.nextToken());
            iceberg[iceA].add(iceB);
            iceberg[iceB].add(iceA);
        }

        q.offer(new Node(P, 0));
        isVitied[P] = true;

        while (!q.isEmpty()) {
            Node poll = q.poll();
            if(poll.idx <= S){
                implants[poll.idx] = poll.count;
                continue;
            }

            int nc = poll.count + 1;

            for (int child : iceberg[poll.idx]) {
                if(!isVitied[child]){
                    isVitied[child] = true;
                    q.offer(new Node(child, nc));
                }
            }
        }

        Arrays.sort(implants);
        System.out.println(N - (implants[0] + implants[1] + 1)); //펭귄 자리 + 1
    }

    static class Node {

        int idx, count;

        public Node(int idx, int count) {
            this.idx = idx;
            this.count = count;
        }
    }
}
