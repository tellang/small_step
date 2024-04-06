package Queue.N1966;

import static java.lang.Integer.parseInt;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Comparator;
import java.util.Objects;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main {

    static int T, N, M;
    static StringBuilder result = new StringBuilder();
    static Pair TANGO;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        T = parseInt(br.readLine());

        for (int t = 0; t < T; t++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            N = parseInt(st.nextToken());
            M = parseInt(st.nextToken());
            ArrayDeque<Pair> q = new ArrayDeque<>();
            PriorityQueue<Integer> pq = new PriorityQueue<>(Comparator.reverseOrder());

            st = new StringTokenizer(br.readLine());
            for (int n = 0; n < N; n++) {
                int num = parseInt(st.nextToken());
                Pair pair = new Pair(n, num);
                if (n == M)
                    TANGO = pair;
                q.offer(pair);
                pq.offer(num);
            }

            Pair presetPair = q.poll();
            Integer prio = pq.peek();


            int i = 0;
            while (prio > presetPair.prio||(!presetPair.equals(TANGO) && i < N)) {
                if (presetPair.prio == prio) {
                    pq.poll();
                    prio = pq.peek();
                    i++;
                } else
                    q.offer(presetPair);
                presetPair = q.poll();
            }
            result.append(i + 1).append('\n');
        }
        System.out.println(result);
    }


}
class Pair {
    int order, prio;

    public Pair(int order, int prio) {
        this.order = order;
        this.prio = prio;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Pair pair = (Pair) o;
        return order == pair.order && prio == pair.prio;
    }

    @Override
    public int hashCode() {
        return Objects.hash(order, prio);
    }
}