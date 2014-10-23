import java.util.Random;

public class Dice{
	int sides;
	int num;
	int[] scores;
	Random r = new Random();
	
	public Dice(int n, int s){
		num = n; sides = s;
		scores = new int[num];
	}
	public int flip(){
		int score = 0;
		for(int i = 1; i<=num; i++){
			score += r.nextInt(sides);
		}
		return score;
	}
	public int roll(){
		for(int i = 0; i<num; i++){
			scores[i] = (r.nextInt(sides)+1);
		}
		int tot = total(scores);
		return tot;
	}
	public int rollMinusLowest(){
		for(int i = 0; i<num; i++){
			scores[i] = (r.nextInt(sides)+1);
		}
		int tot = total(scores) - lowest(scores);
		return tot;
	}
	public static int lowest(int[] nums){
		int min = nums[0];
		for(int num : nums){
			if(num<min) min=num;
		}
		return min;
	}
	public static int total(int[] nums){
		int tot = 0;
		for(int num : nums){
			tot+=num;
		}
		return tot;
	}
}