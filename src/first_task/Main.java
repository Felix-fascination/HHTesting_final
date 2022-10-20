package first_task;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    static ArrayList<Integer> leftRow = new ArrayList<>();
    static ArrayList<Integer> rightRow = new ArrayList<>();

    static int fullSum;

    public static void main (String[] args) throws FileNotFoundException {
        Scanner sc = new Scanner(new FileReader("input.txt"));
        String[] nums = sc.nextLine().split(" ");

        int count = 0;
        int sumBuff = 0;
        int leftRowNum = Integer.parseInt(nums[0]);
        int rightRowNum = Integer.parseInt(nums[1]);
        fullSum = Integer.parseInt(nums[2]);

        getNumbers(sc, leftRowNum, rightRowNum);
        int left;
        for (int i = 0; i < leftRowNum; i++){
            if ( isItFull(sumBuff, leftRow.get(i)) ){
                sumBuff += leftRow.get(i);
                count++;
            }
        }
        int countMax = count;
        left = count - 1;
        int right = 0;
        while(true){

            while ( isItFull(sumBuff, rightRow.get(right)) ){
                sumBuff += rightRow.get(right);
                right++;
                count++;
                if (right > rightRowNum) break;
            }
            countMax = Math.max(count, countMax);
            if (right > rightRowNum || left == -1) {
                System.out.print(countMax);
                return;
            }
            sumBuff =  sumBuff - leftRow.get(left);
            count--;
            left--;
        }
    }

    private static void getNumbers(Scanner sc, int leftLength, int rightLength){
        String[] nums;
        for(int i = 0; i < Math.max(leftLength, rightLength); i++){
            nums = sc.nextLine().split(" ");
            if (i < leftLength)  leftRow.add(Integer.parseInt(nums[0]));
            if (i < rightLength) rightRow.add(Integer.parseInt(nums[1]));
        }
    }

    private static boolean isItFull(int sumBuff, int number){
         return sumBuff <= fullSum - number;
    }

}

