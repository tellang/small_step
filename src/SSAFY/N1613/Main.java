package SSAFY.N1613;

import java.io.*;
import static java.lang.Integer.parseInt;
import java.util.*;

/*
 * 우선순위 파악
 * 전이 폐쇄 문제
 * ---
 * BitSet[] nextEvents : nextEvents[f].get(l)
 *  nextEvents.cardinality() => 다음 사건의 수
 *  for n for b nextEvents[b].get(n) => 과거 사건의 수
 * 갯수 측정 후 N-1인지 확인
 *  
 */
public class Main {

    static BufferedReader br;
    static StringTokenizer st;
    static StringBuilder result = new StringBuilder();

    static BitSet[] postEventsOf;
    static int N, MAX_EVENT, K, MAX_EDGE, S, MAX_QUERY;

    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("src/SSAFY/N1613/input.txt"));
        br = new BufferedReader(new InputStreamReader(System.in));
        init();
        floyd();
        query();
        System.out.println(result);
    }

    static void query() throws NumberFormatException, IOException {
        MAX_QUERY = parseInt(br.readLine().trim());

        for (int q = 0; q < MAX_QUERY; q++) {
            st = new StringTokenizer(br.readLine().trim());
            int pre = parseInt(st.nextToken());
            int post = parseInt(st.nextToken());

            if(postEventsOf[pre].get(post))
                result.append(-1);
            else if(postEventsOf[post].get(pre))
                result.append(1);
            else
                result.append(0);
            result.append('\n');
        }
    }

    static void floyd() {
        for (int child = 1; child <= MAX_EVENT; child++) {
            for (int parent = 1; parent <= MAX_EVENT; parent++) {
                if (child == parent)
                    continue;
                if (postEventsOf[parent].get(child))
                    postEventsOf[parent].or(postEventsOf[child]);
            }
        }
        for (int e = 1; e <= MAX_EVENT; e++) {
            postEventsOf[e].clear(e);
        }
    }

    static void init() throws IOException {
        st = new StringTokenizer(br.readLine().trim());
        N = MAX_EVENT = parseInt(st.nextToken());
        K = MAX_EDGE = parseInt(st.nextToken());

        postEventsOf = new BitSet[MAX_EVENT + 1];
        for (int n = 1; n <= MAX_EVENT; n++) {
            postEventsOf[n] = new BitSet(MAX_EVENT + 1);
        }

        for (int k = 0; k < MAX_EDGE; k++) {
            st = new StringTokenizer(br.readLine().trim());
            int pre = parseInt(st.nextToken());
            int post = parseInt(st.nextToken());

            postEventsOf[pre].set(post);
        }
    }

}