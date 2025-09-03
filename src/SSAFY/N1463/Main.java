package SSAFY.N1463;
/**
 * 문제와 반대로 1 부터 쌓아 올라가자
 * 
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static int target;
    static int[] arr;

    public static void main(String[] args) throws IOException {

        target = Integer.parseInt(br.readLine().trim()); // 1000000
        arr = new int[target + 1];

        if (target < 4) {
            System.out.println(target == 1 ? 0 : 1);
        } else {
            arr[1] = 0;
            arr[2] = 1;
            arr[3] = 1;
            for (int i = 4; i < target + 1; i++) {
                int preVal = arr[i - 1];
                if (i % 2 == 0)
                    if (preVal > arr[i >> 1])
                        preVal = arr[i >> 1];
                if (i % 3 == 0)
                    if (preVal > arr[i / 3])
                        preVal = arr[i / 3];
                arr[i] = preVal + 1;
            }
            System.out.println(arr[target]);
        }
    }
}
