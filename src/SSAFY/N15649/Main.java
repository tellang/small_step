package SSAFY.N15649;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import static java.lang.Integer.parseInt;
import java.util.StringTokenizer;

public class Main {
    // 1부터 'maxNumber'까지의 숫자 중에서 'selectCount'개의 숫자를 선택
    static int maxNumber; 
    static int selectCount;
    
    // 각 숫자의 사용 여부를 확인하는 배열
    // isUsed[i]가 true면 숫자 i가 이미 선택되었음을 의미
    static boolean[] isUsed;
    
    // 현재까지 선택된 숫자들을 저장하는 배열
    static int[] selectedNumbers;
    
    // 최종 결과를 저장할 StringBuilder
    static StringBuilder result;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        
        maxNumber = parseInt(st.nextToken());
        selectCount = parseInt(st.nextToken());

        isUsed = new boolean[maxNumber + 1];
        selectedNumbers = new int[selectCount];
        result = new StringBuilder();

        // 백트래킹 시작
        // 현재까지 선택된 숫자의 개수는 0개
        findPermutations(0);
        
        System.out.println(result);
    }

    /**
     * 백트래킹을 사용하여 순열을 찾는 재귀 함수
     * @param count 현재까지 선택된 숫자의 개수
     */
    public static void findPermutations(int count) {
        // 종료 조건: 'selectCount'개 만큼의 숫자를 모두 선택했을 때
        if (count == selectCount) {
            // 선택된 숫자들을 결과 문자열에 추가
            for (int number : selectedNumbers) {
                result.append(number).append(' ');
            }
            result.append('\n');
            return;
        }

        // 1부터 'maxNumber'까지의 숫자들을 순회
        for (int i = 1; i <= maxNumber; i++) {
            // 해당 숫자가 아직 사용되지 않았다면
            if (!isUsed[i]) {
                // 1. 현재 숫자를 사용했다고 표시
                isUsed[i] = true;
                
                // 2. 현재 숫자를 'selectedNumbers' 배열에 추가
                selectedNumbers[count] = i;
                
                // 3. 다음 숫자를 선택하기 위해 재귀 호출
                findPermutations(count + 1);
                
                // 4. 백트래킹: 다음 경우를 위해 현재 숫자의 사용 상태를 되돌림
                isUsed[i] = false;
            }
        }
    }
}