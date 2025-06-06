package Data_structures.N7432;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.TreeMap;

/*
이진트리 명시 없음
같은 층에 같은 이름이 없음
 */
public class Main {
	static int N;
	static Node root = new Node(null, "root");
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer st;
	static StringBuilder result = new StringBuilder();

	public static void main(String[] args) throws IOException {
		N = Integer.parseInt(br.readLine());
		for (int n = 0; n < N; n++) {
			st = new StringTokenizer(br.readLine());
			Node head = root;
			Node child = null;
			while (st.hasMoreTokens()) {
				String name = st.nextToken("\\");
				if (head.children.containsKey(name)) {
					child = head.children.get(name);
				} else
					child = new Node(head, name);
				head = child;
			}
		}

		dfs(root);
		System.out.println(result);
	}

	static void dfs(Node root) {
		if (!root.children.isEmpty()) {
			for (String childName : root.children.keySet()) {
				result.append(" "
						.repeat(Math.max(0, root.children.get(childName).level))
					)
					.append(childName)
					.append('\n');
				dfs(root.children.get(childName));
			}
		}
	}
}

class Node {
	Node root;
	int level;
	Map<String, Node> children = new TreeMap<>();

	public Node(Node root, String name) {
		this.root = root;
		if (root != null) {
			root.children.put(name, this);
			this.level = root.level + 1;
		} else {
			this.level = -1;
		}
	}
}