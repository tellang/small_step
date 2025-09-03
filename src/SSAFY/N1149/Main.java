package SSAFY.N1149;

/**
 * 모듈러 연산으로 원형 배열 효과
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import static java.lang.Integer.*;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    static BufferedReader br;
    static StringTokenizer st;

    static int N, HOME_IDX, RGB_COUNT;
    static int[][] RGBCosts;

    static {
        br = new BufferedReader(new InputStreamReader(System.in));
        RGB_COUNT = 3;
    }

    public static void main(String[] args) throws IOException {

        init();
        coloring();

        Arrays.sort(RGBCosts[HOME_IDX]);
        System.out.println(RGBCosts[HOME_IDX][0]);
    }

    static void coloring() throws IOException {
        for (int h = 1; h <= HOME_IDX; h++) {
            st = new StringTokenizer(br.readLine().trim());
            for (int rgb = 3; rgb <= 5; rgb++) { // 0, 1, 2
                RGBCosts[h][rgb % 3] = parseInt(st.nextToken());
                RGBCosts[h][rgb % 3] += min(
                        RGBCosts[h - 1][(rgb - 1) % 3],
                        RGBCosts[h - 1][(rgb + 1) % 3]
                );
            }
        }
    }

    static void init() throws IOException {
        HOME_IDX = parseInt(br.readLine().trim());
        RGBCosts = new int[HOME_IDX + 1][RGB_COUNT];
    }

}
