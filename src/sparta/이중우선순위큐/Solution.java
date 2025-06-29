package sparta.이중우선순위큐;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.StringTokenizer;

class Solution {
	static char INSERT = 'I', OPR = 'D';

	public int[] solution(String[] operations) {
		int[] answer = {0, 0};
		LinkedList<Integer> dq = new LinkedList<>();
		for (String opr : operations) {
			StringTokenizer st = new StringTokenizer(opr);
			char odr = st.nextToken().charAt(0);
			int num = Integer.parseInt(st.nextToken());
			if (odr == INSERT) {
				dq.push(num);
				dq.sort(Comparator.reverseOrder());
			} else {
				if (num == 1) {
					dq.pollFirst();
				} else if (num == -1) {
					dq.pollLast();
				}
			}
		}
		if (!dq.isEmpty()) {
			answer[0] = dq.peekFirst();
			answer[1] = dq.peekLast();
		}
		return answer;
	}
}
