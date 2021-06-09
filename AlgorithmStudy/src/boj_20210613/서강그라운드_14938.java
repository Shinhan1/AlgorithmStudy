package boj_20210613;

/*

서강그라운드 출처분류
시간 제한   메모리 제한   제출   정답   맞은 사람   정답 비율
1 초   128 MB   2399   1256   1003   50.175%
문제
예은이는 요즘 가장 인기가 있는 게임 서강그라운드를 즐기고 있다.
서강그라운드는 여러 지역중 하나의 지역에 낙하산을 타고 낙하하여, 그 지역에 떨어져 있는 아이템들을 이용해 서바이벌을 하는 게임이다.
서강그라운드에서 1등을 하면 보상으로 치킨을 주는데, 예은이는 단 한번도 치킨을 먹을 수가 없었다.
자신이 치킨을 못 먹는 이유는 실력 때문이 아니라 아이템 운이 없어서라고 생각한 예은이는 낙하산에서 떨어질 때
각 지역에 아이템 들이 몇 개 있는지 알려주는 프로그램을 개발을 하였지만 어디로 낙하해야 자신의 수색 범위 내에서 가장 많은 아이템을 얻을 수 있는지 알 수 없었다.

각 지역은 일정한 길이 l (1 ≤ l ≤ 15)의 길로 다른 지역과 연결되어 있고 이 길은 양방향 통행이 가능하다.
예은이는 낙하한 지역을 중심으로 거리가 수색 범위 m (1 ≤ m ≤ 15) 이내의 모든 지역의 아이템을 습득 가능하다고 할 때, 예은이가 얻을 수 있는 아이템의 최대 개수를 알려주자.



주어진 필드가 위의 그림과 같고, 예은이의 수색범위가 4라고 하자. ( 원 밖의 숫자는 지역 번호, 안의 숫자는 아이템 수, 선 위의 숫자는 거리를 의미한다)
예은이가 2번 지역에 떨어지게 되면 1번,2번(자기 지역), 3번, 5번 지역에 도달할 수 있다. (4번 지역의 경우 가는 거리가 3 + 5 = 8 > 4(수색범위) 이므로 4번 지역의 아이템을 얻을 수 없다.)
이렇게 되면 예은이는 23개의 아이템을 얻을 수 있고, 이는 위의 필드에서 예은이가 얻을 수 있는 아이템의 최대 개수이다.

입력
첫째 줄에는 지역의 개수 n (1 ≤ n ≤ 100)과 예은이의 수색범위 m (1 ≤ m ≤ 15), 길의 개수 r (1 ≤ r ≤ 100)이 주어진다.

둘째 줄에는 n개의 숫자가 차례대로  각 구역에 있는 아이템의 수 t (1 ≤ t ≤ 30)를 알려준다.

세 번째 줄부터 r+2번째 줄 까지 길 양 끝에 존재하는 지역의 번호 a, b, 그리고 길의 길이 l (1 ≤ l ≤ 15)가 주어진다.

출력
예은이가 얻을 수 있는 최대 아이템 개수를 출력한다.

예제 입력 1
5 5 4
5 7 8 2 3
1 4 5
5 2 4
3 2 3
1 2 3
예제 출력 1
23

*/

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

// https://www.acmicpc.net/problem/14938
public class 서강그라운드_14938 {
    private static int N;
    private static int M;
    private static int R;
    private static int[] itemCnt;
    private static List<List<Node>> list = new ArrayList<>();
    private static List<Node>[] tree;
    private static int[][] distance;
    private static final int INF = 100000000;

    private static class Node implements Comparable<Node> {
        int index;
        int length;

        public Node(int index, int length) {
            this.index = index;
            this.length = length;
        }
        
        @Override
        public int compareTo(Node o) {
        	// TODO Auto-generated method stub
        	return length - o.length;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        R = Integer.parseInt(st.nextToken());
        itemCnt = new int[N + 1];
        tree = new ArrayList[N + 1];
        distance = new int[N + 1][N + 1];

        st = new StringTokenizer(br.readLine());
        for (int i = 1; i <= N; i++) {
            itemCnt[i] = Integer.parseInt(st.nextToken());
        }

        initList();

        for (int i = 0; i < R; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            int l = Integer.parseInt(st.nextToken());
            
            list.get(a).add(new Node(b, l));
            list.get(b).add(new Node(a, l));
            
            tree[a].add(new Node(b, l));
            tree[b].add(new Node(a, l));
        }
        
        int result = 0;
        for(int i = 1; i <= N; i++) {
        	int cnt = dijkstra(i);
        	result = Math.max(result, cnt);
        }
        
        System.out.println(result);


        br.close();
    }
    
    private static int dijkstra(int start) {
    	PriorityQueue<Node> q = new PriorityQueue<Node>();
    	q.add(new Node(start, 0));
    	boolean visited[] = new boolean[N + 1];
    	int maxItemCnt = 0;
    	distance[start][start] = 0;
    	
    	while(!q.isEmpty()) {
    		Node nowNode = q.poll();
    		int nowIdx = nowNode.index;
    		
    		if (nowNode.length > M) {
    			break;
    		}
    		
    		if (visited[nowIdx]) {
    			continue;
    		}
    		
    		visited[nowIdx] = true;
    		
    		for (Node node : list.get(nowIdx)) {
    			if (distance[start][node.index] > distance[start][nowIdx] + node.length) {
    				distance[start][node.index] = distance[start][nowIdx] + node.length;
    				q.add(new Node(node.index, distance[start][node.index]));
    			}
    		}
    	}
    	
//    	System.out.println("start : " + start);
    	for (int i = 1; i <= N; i++) {
    		if (distance[start][i] <= M) {
    			maxItemCnt += itemCnt[i];
//    			System.out.println("i : " + i + ", itemCnt : " + itemCnt[i]);
    		}
    	}
//    	System.out.println("max : " + maxItemCnt);
    	
//    	print();
    	
    	return maxItemCnt;
    }

    private static void initList() {
        for (int i = 0; i <= N; i++) {
            list.add(new ArrayList<Node>());
            tree[i] = new ArrayList<Node>();
            Arrays.fill(distance[i], INF);
        }
    }
    
    private static void print() {
    	for (int i = 1; i <= N; i++) {
    		for (int j = 1; j <= N; j++) {
    			int a = distance[i][j];
    			if (a == INF) {
    				System.out.print("I ");
    			}else {
    				System.out.print(a + " ");
    			}
    		}
    		System.out.println();
    	}
    	System.out.println();
    }
}