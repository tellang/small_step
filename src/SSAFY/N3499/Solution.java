package SSAFY.N3499;

import java.io.*;
import static java.lang.Integer.parseInt;
import java.util.*;

/**
 * 그냥 반으로 갈라서 번갈아서 출력?
 */
public class Solution {

    static BufferedReader br;
    static StringTokenizer st;
    static StringBuilder result = new StringBuilder();

    static final int MAX_LEN = 80;

    static int T, N, MAX_CARD;
    static char[][] cards;

    static {
        N = MAX_CARD = 1000;
        cards = new char[MAX_CARD + 1][MAX_LEN];
    }

    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("src/SSAFY/N3499/input.txt"));
        br = new BufferedReader(new InputStreamReader(System.in));

        T = parseInt(br.readLine().trim());

        for (int tc = 1; tc <= T; tc++) {
            result.append('#').append(tc).append(' ');
            init();
            StringBuilder suffled = suffle();
            result.append(suffled).append('\n').trimToSize();
        }
        System.out.println(result);
    }

    static StringBuilder suffle() {
        StringBuilder localResult = new StringBuilder();
        int halfCard = (MAX_CARD >> 1) + (MAX_CARD & 1);

        for (int cIdx = 0; cIdx < halfCard; cIdx++) {

            localResult.append(cards[cIdx])
                    .append(' ')
                    .append(cards[cIdx + halfCard])
                    .append(' ');
        }

        return localResult;
    }

    static void init() throws NumberFormatException, IOException {
        N = MAX_CARD = parseInt(br.readLine().trim());

        for (int cd = 0; cd <= MAX_CARD; cd++) {
            Arrays.fill(cards[cd], '\0');
        }
        st = new StringTokenizer(br.readLine().trim());
        for (int n = 0; n < MAX_CARD; n++) {
            cards[n] = st.nextToken().toCharArray();
        }
    }
}