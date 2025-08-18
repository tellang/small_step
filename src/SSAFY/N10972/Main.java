package SSAFY.N10972;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import static java.lang.Integer.valueOf;
import java.util.Arrays;
import java.util.Comparator;
import java.util.StringTokenizer;

public class Main {

    static int N;
    static Integer[] arr;
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;

    static StringBuilder result = new StringBuilder();

    public static void main(String[] args) throws IOException {
        N = Integer.parseInt(br.readLine());
        arr = new Integer[N];

        st = new StringTokenizer(br.readLine());

        for (int i = 0; i < N; i++) {
            arr[i] = valueOf(st.nextToken());
        }

        if (hasNextPermutation()) {
            for (int num : arr) {
                result.append(num).append(' ');
            }
            System.out.println(result);
        } else {
            System.out.println(-1);
        }
    }

    public static boolean hasNextPermutation() {
        int pivotIdx = N - 2;

        // 1. 피벗(A) 찾기
        while (pivotIdx >= 0 && arr[pivotIdx] >= arr[pivotIdx + 1]) {
            pivotIdx--;
        }
        if (pivotIdx < 0) {
            return false;
        }

        // 2. 스왑 대상(B) 찾기: 이진 탐색으로 최적화
        // 피벗 뒤의 부분은 내림차순이므로, 내림차순용 Comparator를 사용
        Integer minBigIdx = Arrays.binarySearch(
                arr, pivotIdx + 1, N, arr[pivotIdx], Comparator.reverseOrder()
        );

        // binarySearch는 찾지 못하면 음수를 반환 -> 삽입 지점으로 변환
        if (minBigIdx < 0) {
            minBigIdx = ~minBigIdx;
        }

        // binarySearch는 피벗과 같거나 큰 원소를 찾으므로,
        // 피벗보다 큰 원소를 찾기 위해 한 칸 더 왼쪽으로 이동
        minBigIdx--;

        swap(pivotIdx, minBigIdx);

        Arrays.sort(arr, pivotIdx + 1, N);

        return true;
    }

    public static void swap(int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

}
