import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Solution {
	static int N;
	static int M;
	static int [][] map;
	static int cost[];
	static int dx [] = {1,1,-1,-1};
	static int dy [] = {-1,1,1,-1}; 
	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int tc = Integer.parseInt(br.readLine());
		for(int t = 1; t<=tc; t++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			 N = Integer.parseInt(st.nextToken());
			 M = Integer.parseInt(st.nextToken());
			 map = new int[N][N];
			 cost = new int[51];
			 int res = 0;
			for(int i=0;i<N;i++) {
				st = new StringTokenizer(br.readLine());
				for(int j=0;j<N;j++) {
					map[i][j] = Integer.parseInt(st.nextToken());
				}
			}
			for(int i=0;i<N;i++) {
				for(int j=0;j<N;j++) {
					fun(i,j);
					res = Math.max(res, cost[50]);
				}
			}
			System.out.printf("#%d %d%n",t,res);
		}
	}

	private static void fun(int y, int x) {
		cost[1] = map[y][x];
		for(int k=2; k<51; k++) {
			if(x==3&&y==3) {
				System.out.println();
			}
			int cnt = 0;
			int nextX = x-(k-1);
			int nextY = y;
			for(int i=0;i<4;i++) {
				for(int j=0;j<(k-1);j++) {
					nextX = nextX+ dx[i];
					nextY = nextY+ dy[i];
					if(nextX>=0&&nextX<N&&nextY>=0&&nextY<N&&map[nextY][nextX]==1) {
						cnt++;
					}
				}
			}
			if(k*k+(k-1)*(k-1)<=cnt*M) {
				cost[k] = cost[k-1]+cnt;
			}
			else {
				cost[k] = cost[k-1];
			}
		}
	}
	
}
