import java.io.*;
import java.util.Arrays;
import java.util.StringTokenizer;

public class SW2155 {
	static int MAX, N,M,C;
	static int[][] map;
	static int select_MAX;
	static int[] temp;
	static void dfs(int depth, int row, int col, int SUM) {
		if(depth == 2) {
			MAX = Math.max(SUM, MAX);
			return;
		}
		
		if(col+M-1>=N) {
			col = 0;
			row ++;
		}
		
		if(row>=N) return;
		
		for(int i=0; i<M; i++) {
			//System.out.println(i+" "+row+" "+(col+i));
			temp[i] = map[row][col+i];
		}
		select_MAX = 0;
		dfs2(0,0,0);
		dfs(depth+1, row, col+M, SUM + select_MAX);
		
		dfs(depth, row, col+1, SUM);
	}
	
	static void dfs2(int now, int honey, int sum) {
		if(now>=M) {
			select_MAX = Math.max(select_MAX, sum);
			return;
		}
		
		dfs2(now+1, honey, sum); // 그냥 넘어가기
		
		if(honey+temp[now]<=C) {
			dfs2(now+1, honey+temp[now], sum+temp[now]*temp[now]);
		}
	}
	public static void main(String[] args) throws NumberFormatException, IOException {
		// 9*9 + 1*1 = 82
		// 8*8 + 2*2 = 68
		// 7*7 + 3*3 = 58
		// 6*6 + 4*4 = 52
		// 5*5 + 5*5 = 50
        // 결론: 벌꿀 제곱합의 최대값을 구할 때 규칙성이 없으므로 DFS 처리 등 탐색으로 진행
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		int T = Integer.parseInt(br.readLine());
		for(int t=1; t<=T; t++) {
			st = new StringTokenizer(br.readLine());
			MAX = 0;
			N = Integer.parseInt(st.nextToken());
			M = Integer.parseInt(st.nextToken());
			C = Integer.parseInt(st.nextToken());
			temp = new int[M];
			map = new int[N][N];
			for(int i=0; i<N; i++) {
				st = new StringTokenizer(br.readLine());
				for(int j=0; j<N; j++) {
					map[i][j] = Integer.parseInt(st.nextToken());
				}
			}

			dfs(0,0,0,0);
			System.out.println("#"+t+" "+MAX);
		}
	}

}
