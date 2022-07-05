import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;

class FireBall{
	int m, s, count;
	ArrayList<Integer> d; //방향 기록
	
	FireBall(){
		this.m = 0;
		this.s = 0;
		this.count = 0;
		this.d = new ArrayList<>();
	}
	
	void plusM(int m) {
		this.m += m;
	}
	void plusS(int s) {
		this.s += s;
	}
	void plusD(int d) {
		this.d.add(d);
	}
	void plusCount() {
		this.count += 1;
	}
	void toStr() {
		System.out.println(this.m+" "+" "+this.s+" "+this.count);
	}
}


public class Main {
	static int N;
	static int[][] d = {
			{-1,0}, //0
			{-1,1}, //1
			{0,1}, //2
			{1,1}, //3
			{1,0}, //4
			{1,-1}, //5
			{0,-1}, //6
			{-1,-1} //7
	};
	
	public static void moveAndSplit(ArrayList<int[]> arr) {
		FireBall[][] map = new FireBall[N][N]; //임시 map 생성
		
		//arr에 기록된 파이어볼 이동 후 map에 기록
		for(int i=0; i<arr.size(); i++) {
			int[] now = arr.get(i); //r, c, m, s, d
			move(now);
			if(map[now[0]][now[1]] == null) {
				map[now[0]][now[1]] = new FireBall();
			}
			FireBall fireBall = map[now[0]][now[1]];
			fireBall.plusCount();
			fireBall.plusM(now[2]);
			fireBall.plusS(now[3]);
			fireBall.plusD(now[4]);
			//System.out.println("이동중: "+Arrays.toString(now));
		}
		
		//map에 기록된 fireBall들을 쪼개서 재기록
		arr.clear();
		
		for(int i=0; i<N; i++) {
			for(int j=0; j<N; j++) {
				if(map[i][j] != null) {
					FireBall fireBall = map[i][j];
					if(fireBall.count>=2) {
						int m = fireBall.m/5;
						if(m==0) {
							continue;
						}
						
						int s = fireBall.s/fireBall.count;
						
						boolean judge = true; // 이후 방향을 정해줄 flag 변수
						int start = fireBall.d.get(0)%2;
						for(int k=1; k<fireBall.d.size(); k++) {
							if(fireBall.d.get(k)%2 != start) {
								judge = false;
								k = fireBall.d.size();
							}
						}
						
						if(judge) { //모두 짝수거나 홀수인 방향
							for(int k=0; k<8; k+=2) {
								arr.add(new int[] {i,j,m,s,k});
							}
						}else {
							for(int k=1; k<8; k+=2) {
								arr.add(new int[] {i,j,m,s,k});
							}
						}
					}else {
						arr.add(new int[] {i,j,fireBall.m, fireBall.s, fireBall.d.get(0)});
					}
				}
			}
		}
		
//		for(int i=0; i<arr.size(); i++) {
//			System.out.println(Arrays.toString(arr.get(i)));
//		}
	}
	public static void move(int[] now) {
		// 파이어볼을 속도만큼 이동 시킨 후 위치 재 반환
		int row = (now[0] + now[3]*(d[now[4]][0]))%N;
		int col = (now[1] + now[3]*(d[now[4]][1]))%N;
		
		//음수라면 양수로 치환
		now[0] = row<0? row+N : row;
		now[1] = col<0? col+N : col;
	}
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		int M = Integer.parseInt(st.nextToken());
		int K = Integer.parseInt(st.nextToken());
		
		ArrayList<int[]> arr = new ArrayList<>();
		
		while(M-- >0) {
			st = new StringTokenizer(br.readLine());
			int r = Integer.parseInt(st.nextToken())-1;
			int c = Integer.parseInt(st.nextToken())-1;
			int m = Integer.parseInt(st.nextToken());
			int s = Integer.parseInt(st.nextToken());
			int d = Integer.parseInt(st.nextToken());
			arr.add(new int[] {r,c,m,s,d});
		}
		
		while(K-- >0) {
			//이동 부분
			moveAndSplit(arr);
		}
		
		int res = 0;
		for(int i=0; i<arr.size(); i++) {
			res += arr.get(i)[2];
		}
		System.out.println(res);
	}

}
