package boj_20210504_구현;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.StringTokenizer;

/*

연구소 3 분류
시간 제한	메모리 제한	제출	정답	맞은 사람	정답 비율
0.25 초 (하단 참고)	512 MB	23594	6764	3778	25.308%
문제
인체에 치명적인 바이러스를 연구하던 연구소에 승원이가 침입했고, 바이러스를 유출하려고 한다. 
바이러스는 활성 상태와 비활성 상태가 있다. 
가장 처음에 모든 바이러스는 비활성 상태이고, 활성 상태인 바이러스는 상하좌우로 인접한 모든 빈 칸으로 동시에 복제되며, 1초가 걸린다. 
승원이는 연구소의 바이러스 M개를 활성 상태로 변경하려고 한다.

연구소는 크기가 N×N인 정사각형으로 나타낼 수 있으며, 정사각형은 1×1 크기의 정사각형으로 나누어져 있다. 
연구소는 빈 칸, 벽, 바이러스로 이루어져 있으며, 벽은 칸 하나를 가득 차지한다. 
활성 바이러스가 비활성 바이러스가 있는 칸으로 가면 비활성 바이러스가 활성으로 변한다.

예를 들어, 아래와 같이 연구소가 생긴 경우를 살펴보자. 
0은 빈 칸, 1은 벽, 2는 바이러스의 위치이다.

2 0 0 0 1 1 0
0 0 1 0 1 2 0
0 1 1 0 1 0 0
0 1 0 0 0 0 0
0 0 0 2 0 1 1
0 1 0 0 0 0 0
2 1 0 0 0 0 2
M = 3이고, 바이러스를 아래와 같이 활성 상태로 변경한 경우 6초면 모든 칸에 바이러스를 퍼뜨릴 수 있다. 
벽은 -, 비활성 바이러스는 *, 활성 바이러스는 0, 빈 칸은 바이러스가 퍼지는 시간으로 표시했다.

* 6 5 4 - - 2
5 6 - 3 - 0 1
4 - - 2 - 1 2
3 - 2 1 2 2 3
2 2 1 0 1 - -
1 - 2 1 2 3 4
0 - 3 2 3 4 *
시간이 최소가 되는 방법은 아래와 같고, 4초만에 모든 칸에 바이러스를 퍼뜨릴 수 있다.

0 1 2 3 - - 2
1 2 - 3 - 0 1
2 - - 2 - 1 2
3 - 2 1 2 2 3
3 2 1 0 1 - -
4 - 2 1 2 3 4
* - 3 2 3 4 *
연구소의 상태가 주어졌을 때, 모든 빈 칸에 바이러스를 퍼뜨리는 최소 시간을 구해보자.

입력
첫째 줄에 연구소의 크기 N(4 ≤ N ≤ 50), 놓을 수 있는 바이러스의 개수 M(1 ≤ M ≤ 10)이 주어진다.

둘째 줄부터 N개의 줄에 연구소의 상태가 주어진다. 
0은 빈 칸, 1은 벽, 2는 바이러스를 놓을 수 있는 위치이다. 
2의 개수는 M보다 크거나 같고, 10보다 작거나 같은 자연수이다.

출력
연구소의 모든 빈 칸에 바이러스가 있게 되는 최소 시간을 출력한다.
바이러스를 어떻게 놓아도 모든 빈 칸에 바이러스를 퍼뜨릴 수 없는 경우에는 -1을 출력한다.

예제 입력 1 
7 3
2 0 0 0 1 1 0
0 0 1 0 1 2 0
0 1 1 0 1 0 0
0 1 0 0 0 0 0
0 0 0 2 0 1 1
0 1 0 0 0 0 0
2 1 0 0 0 0 2
예제 출력 1 
4
예제 입력 2 
7 3
2 0 2 0 1 1 0
0 0 1 0 1 2 0
0 1 1 2 1 0 0
2 1 0 0 0 0 2
0 0 0 2 0 1 1
0 1 0 0 0 0 0
2 1 0 0 2 0 2
예제 출력 2 
4
예제 입력 3 
7 4
2 0 2 0 1 1 0
0 0 1 0 1 2 0
0 1 1 2 1 0 0
2 1 0 0 0 0 2
0 0 0 2 0 1 1
0 1 0 0 0 0 0
2 1 0 0 2 0 2
예제 출력 3 
4
예제 입력 4 
7 5
2 0 2 0 1 1 0
0 0 1 0 1 2 0
0 1 1 2 1 0 0
2 1 0 0 0 0 2
0 0 0 2 0 1 1
0 1 0 0 0 0 0
2 1 0 0 2 0 2
예제 출력 4 
3
예제 입력 5 
7 3
2 0 2 0 1 1 0
0 0 1 0 1 0 0
0 1 1 1 1 0 0
2 1 0 0 0 0 2
1 0 0 0 0 1 1
0 1 0 0 0 0 0
2 1 0 0 2 0 2
예제 출력 5 
7
예제 입력 6 
7 2
2 0 2 0 1 1 0
0 0 1 0 1 0 0
0 1 1 1 1 0 0
2 1 0 0 0 0 2
1 0 0 0 0 1 1
0 1 0 0 0 0 0
2 1 0 0 2 0 2
예제 출력 6 
-1
예제 입력 7 
5 1
2 2 2 1 1
2 1 1 1 1
2 1 1 1 1
2 1 1 1 1
2 2 2 1 1
예제 출력 7 
0

*/

public class 연구소3_17142 {
	private static int N, M;
	private static int[][] map;
	private static List<Pair> virusList = new ArrayList<Pair>();
	private static boolean[] virusVisited;
	private static int[] virusCombi;
	private static int min = Integer.MAX_VALUE;

	// 이 maxCount가 KeyPoint!!
	// 이걸 몰라서 진짜 한참 걸림
	private static int maxCount = 0;

	private static int dx[] = { 1, -1, 0, 0 };
	private static int dy[] = { 0, 0, 1, -1 };

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		map = new int[N][N];
		virusCombi = new int[M];

		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 0; j < N; j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
				if (map[i][j] == 0) {
					maxCount++;
				} else if (map[i][j] == 2) {
					virusList.add(new Pair(i, j, 0));
				}
			}
		}

		virusVisited = new boolean[virusList.size()];

		if (maxCount == 0) {
			System.out.println(0);
		} else {
			selectVirus(0, 0);
			if (min == Integer.MAX_VALUE) {
				min = -1;
			}
			System.out.println(min);
		}

		br.close();
	}

	private static void spreadVirus(int maxCnt) {
		Queue<Pair> q = new LinkedList<Pair>();
		boolean[][] visited = new boolean[N][N];
		for (int i = 0; i < M; i++) {
			q.add(virusList.get(virusCombi[i]));
			int x = virusList.get(virusCombi[i]).x;
			int y = virusList.get(virusCombi[i]).y;
			visited[x][y] = true;
		}

		while (!q.isEmpty()) {
			Pair p = q.poll();

			for (int i = 0; i < 4; i++) {
				int X = p.x + dx[i];
				int Y = p.y + dy[i];

				if (!isInvalid(X, Y) && !visited[X][Y]) {
					if (map[X][Y] == 1) {
						continue;
					}
					if (map[X][Y] == 0) {
						maxCnt--;
					}

					if (maxCnt == 0) {
						min = Math.min(min, p.time + 1);
						return;
					}
					visited[X][Y] = true;
					q.add(new Pair(X, Y, p.time + 1));
				}
			}
		}
	}

	private static boolean isInvalid(int X, int Y) {
		if (X < 0 || X >= N || Y < 0 || Y >= N) {
			return true;
		}

		return false;
	}

	private static void selectVirus(int index, int depth) {
		if (depth == M) {
			spreadVirus(maxCount);

			return;
		}

		for (int i = index; i < virusList.size(); i++) {
			if (!virusVisited[i]) {
				virusVisited[i] = true;
				virusCombi[depth] = i;
				selectVirus(i + 1, depth + 1);
				virusVisited[i] = false;
			}
		}
	}

	private static class Pair {
		int x;
		int y;
		int time;

		public Pair(int x, int y, int time) {
			this.x = x;
			this.y = y;
			this.time = time;
		}
	}
}
