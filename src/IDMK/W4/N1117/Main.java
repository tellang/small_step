package IDMK.W4.N1117;

/**
 * 수학
 * 100Mx100M long
 * 하단 메소드 참조
 */
import java.io.*;
import java.util.*;

public class Main {

    static BufferedReader br;
    static StringTokenizer st;

    static int W = 0, H = 1, f = 2, c = 3, x1 = 4, y1 = 5, x2 = 6, y2 = 7;
    static long[] input = new long[8];

    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("src/IDMK/W4/N1117/input.txt"));
        br = new BufferedReader(new InputStreamReader(System.in));
        init();
        System.out.println(paint());
    }

    static void init() throws IOException {
        st = new StringTokenizer(br.readLine().trim());

        for (int inputIdx = 0; inputIdx < 8; inputIdx++) {
            input[inputIdx] = Long.parseLong(st.nextToken());
        }

    }

    /**
     * 잘려나가는 영역의 넓이를 계산하여 남은 종이의 넓이를 반환
     *
     * 전체 넓이: W * H 잘려나가는 영역(한 묶음): height * (width + mirrorWidth) 총 잘려나가는 영역:
     * rowCount * (height * (width + mirrorWidth))
     *
     * - width: 잘라낼 영역의 실제 너비 (x2 - x1) - height: 잘라낼 영역의 실제 높이 (y2 - y1) -
     * mirrorWidth: 접었을 때 겹치는 부분의 너비
     *
     * 주어진 로직에 따른 mirrorWidth 계산: - `input[x1] >= input[f]`: - 잘라낼 영역의 왼쪽 경계(x1)가 접는
     * 선(f)보다 오른쪽에 위치 - 이 경우 겹치는 부분이 없으므로 `mirrorWidth = 0` - `input[x1] <
     * input[f]`: - 잘라낼 영역의 왼쪽 경계(x1)가 접는 선(f)보다 왼쪽에 위치 - `Math.min(input[x2],
     * Math.min(input[f], input[W] - input[f]))` - `input[f]`와 `input[W] -
     * input[f]`는 각각 왼쪽과 오른쪽 접힌 부분의 너비 - `Math.min(input[f], input[W] - input[f])`는
     * 더 짧은 접힌 부분의 너비 - `input[x2]`는 잘라낼 영역의 오른쪽 경계 - 이 셋 중 가장 작은 값을 기준으로 x1을 빼서(겹치는
     * 부분을 제외한 부분 바로 연산) mirrorWidth를 계산 -
     * 
     * @return 남은 종이의 넓이
     */
    static long paint() {
        long height = (input[y2] - input[y1]);
        long width = (input[x2] - input[x1]);
        long mirrorWidth = Math.max(
                Math.min(input[x2], Math.min(input[f], input[W] - input[f])) - input[x1], 0);

        long totalSize = input[H] * input[W];
        long rowSize = ((height) * (width + mirrorWidth));
        long rowCount = (input[c] + 1);
        return totalSize - (rowCount * rowSize);
    }
}