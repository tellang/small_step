package IDMK.W4.N25542;

/**
 * 메모리 1Gb를 이용한 바보처럼 풀기
 * 최대길이 L<=20, 알파벳 아마 26?
 * 20x20x26 다 찾기
 */
import java.io.*;
import static java.lang.Integer.parseInt;
import java.util.*;

public class Main {

    static final String CALL_FRIEND = "CALL FRIEND";
    static BufferedReader br;
    static StringTokenizer st;

    static int N, STORE_COUNT, L, LENGTH;
    static Set<String> tangoLikes = new HashSet<>();
    static String tango;

    public static void main(String[] args) throws IOException {
        System.setIn(new FileInputStream("src/IDMK/W4/N25542/input.txt"));
        br = new BufferedReader(new InputStreamReader(System.in));

        st = new StringTokenizer(br.readLine().trim());
        STORE_COUNT = N = parseInt(st.nextToken());
        LENGTH = L = parseInt(st.nextToken().trim());

        tango = br.readLine().trim();
        if (STORE_COUNT == 1) {
            System.out.println(tango);
            return;
        }

        tangoLikes.add(tango);
        char[] tangoLikeMakeArr = tango.toCharArray();
        for (int cIdx = 0; cIdx < LENGTH; cIdx++) { // 20
            char defaultChar = tangoLikeMakeArr[cIdx];
            for (char like = 'A'; like <= 'Z'; like++) { // 20x26
                tangoLikeMakeArr[cIdx] = like;
                tangoLikes.add(String.valueOf(tangoLikeMakeArr));
            }
            tangoLikeMakeArr[cIdx] = defaultChar;
        }

        for (int sc = 1; sc < STORE_COUNT; sc++) { // 20
            String isTango = br.readLine().trim();
            char[] isTangoArr = isTango.toCharArray();

            Iterator<String> tangoLikeIter = tangoLikes.iterator(); // 사이클 도중 삭제를 위한 이터레이터

            while (tangoLikeIter.hasNext()) {
                String tengoLike = tangoLikeIter.next();
                char[] tengoLikeArr = tengoLike.toCharArray();
                int diffCount = 0;

                for (int cIdx = 0; cIdx < LENGTH; cIdx++) { // 다른 갯수 계산
                    if (tengoLikeArr[cIdx] != isTangoArr[cIdx]) {

                        diffCount++;
                        if (diffCount >= 2) { // 삔또 두번나가면 제거
                            tangoLikeIter.remove();
                            break;
                        }
                    }
                }
            }

            if (tangoLikes.isEmpty()) { // 타겟 확장팩이 다털렸다
                System.out.println(CALL_FRIEND);
                return;
            }
        }
        for (String result : tangoLikes) {

            System.out.println(result);
            return;
        }
    }
}