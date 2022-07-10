
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
	

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		int N = Integer.parseInt(st.nextToken());
		int M = Integer.parseInt(st.nextToken());
		int T = Integer.parseInt(st.nextToken());
		int [][] map = new int[N+1][M];
		int dx[] = {1,0,-1,0};
		int dy[] = {0,1,0,-1};
		float avg = 0;
		//입력받기
		for(int i=1; i<N+1;i++) {
			st = new StringTokenizer(br.readLine());
			for(int j=0; j<M;j++) {
				map[i][j] = Integer.parseInt(st.nextToken());
				avg +=map[i][j];
			}
		}
		
		avg = avg/(M*N);
		Queue<int[]> queue =new LinkedList<>();
		
		//회전시키기
		for(int t=0; t<T;t++) {
			st = new StringTokenizer(br.readLine());
			int X= Integer.parseInt(st.nextToken());
			int D = Integer.parseInt(st.nextToken());
			int K = Integer.parseInt(st.nextToken());
			for(int i=X,num=1; i<N+1;i=X*(++num)) { //X배수 원판 선택
				for(int j=0;j<(D==0?1:M-1)*K;j++) { // 회전시키기, 반시계방향 회전 == 시계방향으로 M-1 번  회전 시킨것
					
					int temp = map[i][M-1]; //마지막수 임시저장
					for(int k=M-1; k>0;k--) {
						map[i][k] = map[i][k-1]; //이전 위치수로 현재위치 갱신
					}
					map[i][0] = temp; // 맨처음위치 마지막수로 갱신
					
				}
			}
			
			boolean flag = false; // 인접수 존재 여부 확인용
			for(int i=1; i<N+1;i++) { //원판 숫자 돌면서
				for(int j=0; j<M;j++) {
					if(map[i][j]==0)continue;//지워진 숫자 건너뜀
					queue.offer(new int[] {j,i,map[i][j]}); //현재 숫자 큐에 넣기
					while(!queue.isEmpty()){
						int [] cur = queue.poll(); // 큐에서 꺼내기
						for(int k=0;k<4;k++) {
							int nextX = cur[0] + dx[k]; //같은 원판내 인접위치
							int nextY = cur[1] + dy[k]; //인접한 원판, 인접위치 
							if(nextY<1||nextY>N) continue; //원판 범위 넘어가면 건너뜀
							if(nextX<0) nextX = M-1; //원판내에서 범위 넘어가면 0, M-1로 바꾸기
							else if(nextX>=M) nextX = 0;
							if(map[nextY][nextX]!=cur[2]) continue; //인접한 수 다르면 건너뜀
							queue.offer(new int[] {nextX,nextY,map[nextY][nextX]}); //인접, 같은수 큐에 넣기
							flag = true; //인접한 수 있으니까 true로
							map[nextY][nextX] = 0; //인접한 수 0으로 초기화
						}
					}
				}
			}
			
			//인접한 수가 없다면
			if(!flag) {
				for(int i=1;i<N+1;i++) {
					for(int j=0; j<M;j++) {
						if(map[i][j]==0) continue;
						if(map[i][j]>avg) map[i][j]--; // 평균 초과 -1
						else if(map[i][j]<avg) map[i][j]++; // 평균 미만 +1
					}
				}			
			}
			
			//원판 돌림 처리 후 평균 다시 구하기
			avg = 0;
			int count = 0;
			for(int i=1;i<N+1;i++) {
				for(int j=0; j<M;j++) {
					if(map[i][j]==0) continue;
					avg += map[i][j];
					count++;
				}
			}
			avg/=count;

		}
		
		//원판 내 숫자 합 구하기
		int res = 0;
		for(int i =1;i<N+1;i++) {
			for(int j=0;j<M;j++) {
				res += map[i][j];
			}
		}
		System.out.println(res);
	}
}



