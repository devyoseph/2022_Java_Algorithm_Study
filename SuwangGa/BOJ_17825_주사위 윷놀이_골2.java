
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
	static class Map{
		int score;
		int count;
		public Map(int score, int count) {
			super();
			this.score = score;
			this.count = count;
		}
		@Override
		public String toString() {
			return "Map [score=" + score + ", count=" + count + "]";
		}
		
	}
	
	static class Target{
		int cur;
		boolean canMove;
		public Target(int cur) {
			super();
			this.cur = cur;
			this.canMove = true;
		}
		@Override
		public String toString() {
			return "Target [cur=" + cur + ", canMove=" + canMove + "]";
		}
		
	}
	
	
	static int dice[];
	static ArrayList<Map> map;
	static int res;
	static Target [] arr;
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		dice = new int[10];
		for(int i=0; i< 10; i++) {
			dice[i] = Integer.parseInt(st.nextToken());
		}
		map = new ArrayList<Map>();
		arr = new Target[4];
		for(int i=0; i<4;i++) {
			arr[i] = new Target(-1);
		}
		int temp=0;
		for(int i=0;i<20;i++) {
			temp +=2;
			map.add(new Map(temp,0));
		}
		temp = 10;
		for(int i=0; i<3; i++) {
			temp+=3;
			map.add(new Map(temp,0));
		}
		map.add(new Map(22,0));
		map.add(new Map(24,0));
		temp=28;
		for(int i=0; i<3; i++) {
			map.add(new Map(temp,0));
			temp --;
		}
		temp = 25;
		for(int i=0; i<3; i++) {
			map.add(new Map(temp,0));
			temp +=5;
		}
		
		res = 0;
		dfs(0,0);
		System.out.println(res);
	}

	private static void dfs(int count, int score) {
		if(count == 10) {
			res = Math.max(res, score);
			return;
		}
		for(int i=0; i<4; i++) {
			if(!arr[i].canMove) continue;
			int next = arr[i].cur;
			int temp = dice[count];
			
			if(next ==4) {
				next = 20;
				temp--;
			}
			else if(next==9) {
				next = 23;
				temp--;
			}else if(next==14) {
				next = 25;
				temp--;
			}
			
			while(temp !=0) {		
				if(next==22||next==24||next==27) {
					next = 28;
				}else if(next==30) {
					next = 19;
				}else if(next==19) {
					next = 100;
					break;
				}
				else {
					next ++;
				}
				temp--;
			}
			
			boolean saveCan = arr[i].canMove;
			int cur = arr[i].cur;
			if(next<100) {
				if(map.get(next).count == 1) {
					continue;
				}
				
				if(cur != -1) {
					map.get(cur).count=0;
				}
				arr[i].cur = next;
				map.get(next).count = 1;
				dfs(count+1, score+map.get(next).score);
				map.get(next).count = 0;
				if(cur != -1) {
					map.get(cur).count=1;
				}
			}
			else {
				map.get(cur).count = 0;
				arr[i].cur = next;
				arr[i].canMove = false;
				dfs(count+1,score);
				map.get(cur).count = 1;
			}
			arr[i].cur = cur;
			arr[i].canMove = saveCan;
			
		}
		res = Math.max(res, score);
		return;
		
	}
}



