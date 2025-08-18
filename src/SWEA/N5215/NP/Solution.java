package SWEA.N5215.NP;

import java.io.*;
import static java.lang.Integer.parseInt;
import java.util.*;

/**
 * SWEA 5215번 햄버거 다이어트 문제를 Next Permutation 알고리즘으로 해결하는 솔루션 클래스입니다. 주어진 재료들 중에서
 * 칼로리 제한을 넘지 않으면서 가장 높은 점수를 얻는 조합을 찾습니다.
 */
public class Solution {

    static BufferedReader br;
    static StringTokenizer st;
    static StringBuilder result = new StringBuilder();

    /**
     * 총 테스트 케이스 수 T, 재료의 수 N, 칼로리 제한 L, 얻을 수 있는 최대 점수 MAX_POINT
     */
    static int T, N, L, MAX_POINT, PICK = 1, NO_PICK = 0;

    static Food[] foods; // 모든 음식 재료의 정보를 담는 배열

    static Integer[] selectedFoodIdxList; // 선택된 재료의 인덱스를 나타내는 배열 (1: 선택, 0: 미선택)

    public static void main(String[] args) throws IOException {

        System.setIn(new FileInputStream("src/SWEA/N5215/NP/sample_input.txt"));
        br = new BufferedReader(new InputStreamReader(System.in));

        T = parseInt(br.readLine().trim());
        for (int tc = 1; tc <= T; tc++) {
            result.append('#').append(tc).append(' ');

            st = new StringTokenizer(br.readLine().trim());
            N = parseInt(st.nextToken());
            L = parseInt(st.nextToken());

            foods = new Food[N];
            selectedFoodIdxList = new Integer[N];
            MAX_POINT = 0;

            for (int i = 0; i < N; i++) {
                st = new StringTokenizer(br.readLine().trim());
                int point = parseInt(st.nextToken());
                int cal = parseInt(st.nextToken());
                foods[i] = new Food(point, cal);
            }

            /**
             * Next Permutation을 이용하여 재료의 모든 부분집합을 탐색 1개부터 N개까지 재료를 선택하는 모든 조합을
             * 확인
             */
            for (int rCount = 1; rCount <= N; rCount++) {
                // Next Permutation을 위한 selector 배열 초기화
                // `rCount`개의 1과 `N-rCount`개의 0으로 구성하여 조합의 시작점을 만듭니다.
                Arrays.fill(selectedFoodIdxList, 0, N - rCount, NO_PICK);
                Arrays.fill(selectedFoodIdxList, N - rCount, N, PICK);

                do {
                    int localSumCal = 0, localSumPoint = 0;
                    for (int j = 0; j < N; j++) {
                        if (selectedFoodIdxList[j] == PICK) {
                            localSumCal += foods[j].cal;
                            localSumPoint += foods[j].point;
                        }
                    }

                    // 칼로리 제한을 넘지 않을 경우, 최대 점수 갱신
                    if (localSumCal <= L) {
                        MAX_POINT = Math.max(MAX_POINT, localSumPoint);
                    }
                } while (nextPermutation());
            }
            result.append(MAX_POINT).append('\n');
        }
        System.out.print(result);
    }

    /**
     * Next Permutation 알고리즘을 구현한 메서드 `selectedFoodIdxList` 배열의 다음 순열을 생성
     *
     * @return 다음 순열이 존재하면 true, 아니면 false
     */
    static boolean nextPermutation() {
        int pivotIdx = N - 2;
        int oneCount = 0;
        // 1. 피벗(A) 찾기: 뒤에서부터 오름차순이 깨지는 지점
        while (pivotIdx >= 0 && selectedFoodIdxList[pivotIdx] >= selectedFoodIdxList[pivotIdx + 1]) {
            oneCount += selectedFoodIdxList[pivotIdx + 1];
            pivotIdx--;
        }
        if (pivotIdx < 0) {
            return false;
        }

        // 2. 스왑 대상(B) 찾기: 피벗보다 크면서 가장 뒤에 있는 값
        // int minBigIdx = Arrays.binarySearch(
        //         selectedFoodIdxList, // 이 배열에서
        //         pivotIdx + 1, // 피벗 뒤쪽부터
        //         N, //끝까지
        //         selectedFoodIdxList[pivotIdx], // 피벗값과 같은 수 찾기
        //         Comparator.reverseOrder() //내림차순 이진탐색시 필수
        // );

        // if (minBigIdx < 0) {
        //     minBigIdx = ~minBigIdx;
        // }
        // minBigIdx--;
        // 1 이 반드시 어딘가에 존재하고 위치는 중요하지 않다 

        // 3. 피벗과 스왑 대상 교환
        // swap(pivotIdx, minBigIdx);
        // 선택지는 1, 0 이니 1 넣기
        selectedFoodIdxList[pivotIdx] = 1;

        // 4. 피벗 뒤쪽을 다시 정렬 (오름차순)
        // Arrays.sort(selectedFoodIdxList, pivotIdx + 1, N);
        // 선택지는 1, 0 이니 fill
        Arrays.fill(selectedFoodIdxList, pivotIdx + 1, N - oneCount, NO_PICK);
        Arrays.fill(selectedFoodIdxList, N - oneCount, N, PICK);

        return true;
    }

    static class Food {

        int point, cal;

        Food(int point, int cal) {
            this.point = point;
            this.cal = cal;
        }
    }
}
