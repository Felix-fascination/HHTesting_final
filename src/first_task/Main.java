package first_task;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    static ArrayList<Integer> leftRow = new ArrayList<>();
    static ArrayList<Integer> rightRow = new ArrayList<>();

    static int fullSum;

    public static void main (String[] args) throws InterruptedException {
        Scanner sc = new Scanner(System.in);
        String[] nums = sc.nextLine().split(" ");

        int count = 0;
        int countMax = count;
        int sumBuff = 0;
        int leftRowNum = Integer.parseInt(nums[0]);
        int rightRowNum = Integer.parseInt(nums[1]);
        fullSum = Integer.parseInt(nums[2]);

        getNumbers(sc, Math.max(leftRowNum, rightRowNum));
        int left;
        for (int i = 0; i < leftRowNum; i++){
            if ( isItFull(sumBuff, leftRow.get(i)) ){
                sumBuff += leftRow.get(i);
                count++;
            }
        }
        countMax = count;
        left = count;
        int right = 0;
        while(true){

            while ( isItFull(sumBuff, rightRow.get(right)) ){
                sumBuff += rightRow.get(right);
                right++;
                count++;
                if (right > rightRowNum) break;
            }
            countMax = Math.max(count, countMax);
            if (right > rightRowNum) {
                System.out.print(countMax);
                return;
            }

            if (left == -1) {
                System.out.print(countMax);
                return;
            }
            System.out.println(left);
            sumBuff =  sumBuff - leftRow.get(left);
            left--;

        }

/*
        if ( sumBuff <= (fullSum - rightRow.get(i) ) ){
            sumBuff += rightRow.get(i);
            count++;
        }
        else {
            sumBuff -= leftRow.get(j);
        }*/
    }

    private static void getNumbers(Scanner sc, int length){
        String[] nums;
        for(int i = 0; i < length; i++){
            nums = sc.nextLine().split(" ");
            try {
                leftRow.add(Integer.parseInt(nums[0]));
            }
            catch (Exception e){}
            try {
                rightRow.add(Integer.parseInt(nums[1]));
            }
            catch (Exception e){}
        }
    }

    private static boolean isItFull(int sumBuff, int number){
         return sumBuff <= fullSum - number;
    }

    private static int isItBetter (int sumBuff, int sum, int left, int right){
        //Нужно пройти это достаточное количество раз, чтобы полностью пройти на правую стопку
        // Можем пойти не так:
        // 1) Левая стопка закончится, а я до сих пор в правую буду идти (хотя должно быть все в порядке, но стоит сделать проверку
        // 
        if (sumBuff <= sum - rightRow.get(right) ){
            //Добавляем элемент и спрашиваем снова
        }
        else {
            // Убираем последний элемент из левой стопки, в то же время не забывая про подсчет суммы и count
        }
        return 5;

    }
}

