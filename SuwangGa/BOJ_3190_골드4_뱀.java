import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
	
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int N = Integer.parseInt(br.readLine());
		int K = Integer.parseInt(br.readLine());
		int map[][] = new int[N][N];
		final int Apple = 5;
		int[] dx = {1,0,-1,0};
		int[] dy = {0,1,0,-1};
		
		//지도에 사과 표시
		for(int i=0; i<K;i++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			int y = Integer.parseInt(st.nextToken())-1; //y
			int x = Integer.parseInt(st.nextToken())-1; //x
			map[y][x] = Apple;
		}
		

		int L = Integer.parseInt(br.readLine());
		int directionChange[][] = new int[L][2];
		//방향전환 정보입력받기
		for(int i=0;i<L;i++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			directionChange[i][0] = Integer.parseInt(st.nextToken());
			if(st.nextToken().equals("D")) {
				directionChange[i][1] = 0; //오른쪽 이동은 0
			}
			else {
				directionChange[i][1] = 1;	//왼쪽 이동은 1
			}
		}
		int[] head = {0,0}; //머리의 위치
		int[] tail = {0,0}; //꼬리의 위치
		int diIndex = 0;	//머리 방향전환 정보 인덱스
		int diIndex2 = 0;	//꼬리 방향전화 정보 인덱스
		int sec =1;			//시간
		int di = 0;			//머리의 방향
		int diTail = 0;		//꼬리의 방향
		int len = 0;		//머리를 제외한 뱀 길이
		map[0][0] = 1;		//처음 뱀의 위치 저장
		while(true) {
			//머리 위치 갱신
			head[0] += dy[di];
			head[1] += dx[di];
			
			//머리가 지도 밖으로 나가면 부딪힌걸로 간주, 반복문 탈출
			if(head[0]<0||head[0]>=N||head[1]<0||head[1]>=N) break;
			
			//머리가 뱀을 만나면 반복문 탈출
			if(map[head[0]][head[1]]==1) break;
			
			
			//머리가 사과를 만나지 않았을때
			if(map[head[0]] [head[1]]!=Apple) {
				map[tail[0]][tail[1]] = 0; //기존 꼬리 지움
				//꼬리 좌표 갱신
				tail[0] += dy[diTail]; 
				tail[1] += dx[diTail]; 
			}		
			
			
			//사과 만나면 길이 +1
			if(map[head[0]][head[1]]== Apple) {
				len++;
			}
			
			map[head[0]][head[1]] = 1;//지도에 머리 위치 갱신		
			
			//현재 시간과 방향전환 시간이 같으면 방향 전환 실시
			if(directionChange[diIndex][0]==sec) {
				//dx,dy에 시계방향으로 방향 저장  >, v, <, ^
				//오른쪽 방향 전환시 현재 방향의 다음 방향임 dx,dy기준
				if(directionChange[diIndex][1]==0) {
					di = (di+1)%4;
				}
				//왼쪽 방향전환시 현재 방향의 이전 방향임
				else {
					di= di-1<0?3:di-1;
				}
				//방향 전환 완료후 다음 방향전환 정보를 위해 index+1, 단 모든 방향 전환을 마치면 인덱스 증가 X
				diIndex = diIndex+1==L?diIndex:diIndex+1;
			}
			
			//꼬리의 방향 전환 시기는 지정된 방향전환 시간 + len
			if(directionChange[diIndex2][0]+len==sec) {
				if(directionChange[diIndex2][1]==0) {
					diTail = (diTail+1)%4;
				}
				else {
					diTail= diTail-1<0?3:diTail-1;
				}
				diIndex2 = diIndex2+1==L?diIndex2:diIndex2+1;
			}
			//시간 증
			sec++;
		}
		System.out.println(sec);
		
	}
	
}
