package boj_20210316_플로이드와샬;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

/*

저울 출처분류
시간 제한	메모리 제한	제출	정답	맞은 사람	정답 비율
1 초	256 MB	4663	2845	2307	63.536%
문제
무게가 서로 다른 N 개의 물건이 있다. 각 물건은 1부터 N 까지 번호가 매겨져 있다. 
우리는 일부 물건 쌍에 대해서 양팔 저울로 어떤 것이 무거운 것인지를 측정한 결과표를 가지고 있다. 
이 결과표로부터 직접 측정하지 않은 물건 쌍의 비교 결과를 알아낼 수도 있고 알아내지 못할 수도 있다. 
예를 들어, 총 6개의 물건이 있고, 다음 5개의 비교 결과가 주어졌다고 가정하자. ([1]은 1번 물건의 무게를 의미한다.)

[1]>[2], [2]>[3], [3]>[4], [5]>[4], [6]>[5]

우리는 [2]>[3], [3]>[4]로부터 [2]>[4]라는 것을 알 수 있다. 
하지만, 물건 2와 물건 6을 비교하는 경우, 앞서의 결과만으로는 어느 것이 무거운지 알 수 없다. 
이와 같이, 물건 2는 물건 1, 3, 4와의 비교 결과는 알 수 있지만, 물건 5, 6과의 비교 결과는 알 수 없다. 
물건 4는 모든 다른 물건과의 비교 결과를 알 수 있다. 

비교 결과가 모순되는 입력은 없다고 가정한다. 
위 예제의 기존 측정 결과에 [3]>[1]이 추가되었다고 가정하자. 
이 경우 [1]>[2], [2]>[3]이므로 우리는 [1]>[3]이라는 것을 예측할 수 있는데, 
이는 기존에 측정된 결과 [3]>[1]과 서로 모순이므로 이러한 입력은 가능하지 않다. 

물건의 개수 N 과 일부 물건 쌍의 비교 결과가 주어졌을 때, 
각 물건에 대해서 그 물건과의 비교 결과를 알 수 없는 물건의 개수를 출력하는 프로그램을 작성하시오. 

입력
첫 줄에는 물건의 개수 N 이 주어지고, 둘째 줄에는 미리 측정된 물건 쌍의 개수 M이 주어진다. 
단, 5 ≤ N ≤ 100 이고, 0 ≤ M ≤ 2,000이다. 
다음 M개의 줄에 미리 측정된 비교 결과가 한 줄에 하나씩 주어진다. 
각 줄에는 측정된 물건 번호를 나타내는 두 개의 정수가 공백을 사이에 두고 주어지며, 앞의 물건이 뒤의 물건보다 더 무겁다.

출력
여러분은 N개의 줄에 결과를 출력해야 한다. i 번째 줄에는 물건 i 와 비교 결과를 알 수 없는 물건의 개수를 출력한다.

예제 입력 1 
6
5
1 2
2 3
3 4
5 4
6 5
예제 출력 1 
2
2
2
0
3
3

*/

public class 저울_10159 {
	private static int N, M;
	private static int fw[][];
	private static int INF = 10000000;
	private static StringBuilder sb = new StringBuilder();
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		N = Integer.parseInt(br.readLine());
		M = Integer.parseInt(br.readLine());
		
		fw = new int[N+1][N+1];
		
		for(int i = 1; i <= N; i++) {
			Arrays.fill(fw[i], INF);
			fw[i][i] = 0;
		}
		
		for(int i = 1; i <= M; i++) {
			st = new StringTokenizer(br.readLine());
			int a = Integer.parseInt(st.nextToken());
			int b = Integer.parseInt(st.nextToken());
			
			fw[a][b] = 1;
		}
		
		floydWarshall();
		
		
		br.close();
	}
	
	private static void floydWarshall() {
		for(int k = 1; k <= N; k++) {
			for(int i = 1; i <= N; i++) {
				for(int j = 1; j <= N; j++) {
					if(i == j || i == k || k == j) continue;
					if(fw[i][k] == 1 && fw[k][j] == 1) {
						fw[i][j] = 1;
					}
				}
			}
		}
		
//		symmetry();
		solve();
	}
	
	private static void solve() {
		for(int i = 1; i <= N; i++) {
			int count = 0;
			for(int j = 1; j <= N; j++) {
				if(fw[i][j] == INF && fw[j][i] == INF) count++;
			}
			sb.append(count + "\n");
		}
		
		System.out.println(sb.toString());
	}
	
}
