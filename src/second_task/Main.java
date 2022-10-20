package second_task;

import org.jetbrains.annotations.NotNull;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.LinkedList;
import java.util.Scanner;

public class Main {

    static int width;
    static int height;
    static boolean [][] fieldNetwork;
    static int [][] fields;

    public static void main(String[] args) throws FileNotFoundException {
        Scanner sc = new Scanner(new FileReader("input.txt"));

        width = sc.nextInt(); height = sc.nextInt();
        fields = new int[height][width];
        fieldNetwork = new boolean[height][width];
        LinkedList<Rectangle> rectangles = new LinkedList<>();

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                fields[i][j] = sc.nextInt();
                fieldNetwork[i][j] = false;
            }
        }
         /*Храню матрицу просмотренных полей, в итоге дохожу до первого незнакомого плодородного поля и отправляюсь
         в метод, суть которого заключается в поиске вокруг этого поля других плодородных, при нахождении
         отправляю их в очередь. После обрабатываю найденное изначально поле и провожу ту же итерацию со всеми найденными
         полями
                */
        for (int y = 0; y < height; y++){
            for (int x = 0; x < width; x++){
                if (!fieldNetwork[y][x] ) {
                    if(fields[y][x] == 1) {
                        rectangles.addFirst(new Rectangle(x, y));
                        fieldNetwork[y][x] = true;
                        rectangleSearch(fields, rectangles.peek());
                    }
                    else fieldNetwork[y][x] = true;
                }
            }
        }

        int bestFieldsSize = 0;
        float bestFertileRate = -1;
        for(Rectangle rectangle: rectangles){
            rectangle.setFertileAmount(fields);
            if (rectangle.getFertileAmount() > 1) {
                if (rectangle.getFertileRate() > bestFertileRate){
                    bestFertileRate = rectangle.getFertileRate();
                    bestFieldsSize = rectangle.getSize();
                }

                else if (rectangle.getFertileRate() == bestFertileRate && rectangle.getSize() > bestFieldsSize) {
                    bestFertileRate = rectangle.getFertileRate();
                    bestFieldsSize = rectangle.getSize();
                }
            }
        }
        System.out.println(bestFieldsSize);
    }

    // Метод, обрабатывающий и ищущий полный прямоугольник
    private static void rectangleSearch(int [][] fields, @NotNull Rectangle rectangle){
        while (!rectangle.isEmpty()){
            Coordinate coor =  rectangle.getCoordinate();
            int x = coor.getX(); int y = coor.getY();
            isAround(fields, rectangle, x, y);
            rectangle.expandRectangle(x, y);
        }
    }
    // Метод, ищущий новые плодородные поля вокрук
    private static void isAround(int [][] fields, Rectangle rectangle, int x, int y){
        for (int yAround = y - 1; yAround < y + 2; yAround++){
            for( int xAround = x - 1; xAround < x + 2; xAround++){
                if (isInRange(xAround, yAround) && !fieldNetwork[yAround][xAround]){
                    if (fields[yAround][xAround] == 1) rectangle.newCoordinate(xAround, yAround);
                    fieldNetwork[yAround][xAround] = true;
                }
            }
        }
    }
    // Проверка координат при поиске плодородного тела вокруг
    private static boolean isInRange(int x, int y){
        if (x == -1 || y == -1 ) return false;
        else return x != width && y != height;
    }
}

//Класс, хранящий двумерное представление поля
//Был создан для возвращение из методов двумерных значений
class Coordinate {
    private final int x;
    private final int y;

    Coordinate(int x, int y){
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
//Класс прямоугольников, хранящий всю логику, связанную с ними, внутри
class Rectangle {
    private int up;
    private int left;
    private int down;
    private int right;

    private int fertileAmount = 0;
    private LinkedList<Coordinate> coordinates = new LinkedList<>();

    Rectangle(int x, int y){
        this.coordinates.addFirst(new Coordinate(x, y));
        this.left = x;
        this.up = y;
        this.right = x;
        this.down = y;
    }

    @Override
    public String toString() {
        return "\tRectangle:" + left + ":" + up
                + " " + down + ":" + right
                + "\tSize: " + getSize() + "; Fertile: " + getFertileRate()
                + ";\t fertile: "+ fertileAmount +"\n";
    }
    public void expandRectangle(int x, int y){
        if (x < this.left) this.left = x;
        else if (x > this.right) this.right = x;

        if (y > this.down) this.down = y;
        else if (y < this.up) this.up = y;

    }

    public void setFertileAmount(int[][] fields){
        for (int x = left; x <= right; x++){
            for (int y = up; y <= down; y++){
                if (fields[y][x] == 1) fertileUp();
            }
        }
    }
    public int getFertileAmount(){
        return fertileAmount;
    }

    private void fertileUp(){
        this.fertileAmount++;
    }
    public Coordinate getCoordinate(){
        return coordinates.poll();
    }
    public boolean isEmpty(){
        return coordinates.isEmpty();
    }
    public void newCoordinate(int x, int y){
        this.coordinates.addFirst(new Coordinate(x, y));
    }

    public int getSize(){
        int width = Math.abs(right - left)+ 1;
        int height = Math.abs(down - up)  + 1;
        return width * height;
    }
    public float getFertileRate(){
        return (float)fertileAmount/getSize();
    }

}

