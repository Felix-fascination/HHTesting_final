package second_task;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.LinkedList;
import java.util.Scanner;

public class Main {

    static int width;
    static int height;
    static boolean [][] fieldNetwork;

    public static void main(String[] args) throws FileNotFoundException {
        Scanner sc = new Scanner(new FileReader("input.txt"));
        width = sc.nextInt(); height = sc.nextInt();
        int [][] fields = new int[height][width];

        LinkedList<Rectangle> rectangles = new LinkedList<>();
        fieldNetwork = new boolean[height][width];

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                fields[i][j] = sc.nextInt();
                fieldNetwork[i][j] = false;
            }
        }
        // Через рекурсию  с жадным алгоритмом - пожираю прямоугольник до конца, пока он не закончится
        // То есть дохожу строку до конца, только в начале ища снизу самую левую точку, а дальше на этой строке справа
        // Как только заканчивается строка, вызываю метод, который делает то же самое с нижней строкой и так, пока
        // Не заканчивается прямоугольник, в то же время заполняя сетку и идя по ней
        for (int i = 0; i < height; i++){
            for (int j = 0; j < width; j++){
                if (!fieldNetwork[i][j] ) {
                    if(fields[i][j] == 1) {
                        rectangles.addFirst(new Rectangle(j, i));
                        fieldNetwork[i][j] = true;
                        rectangleSearch(fields, rectangles.peek());
                    }
                    else fieldNetwork[i][j] = true;
                }
            }
        }

        int bestFields = 0;
        float bestFertileRate = -1;
        for(Rectangle rectangle: rectangles){
            if (rectangle.getFertileRate() > bestFertileRate) {
                bestFertileRate = rectangle.getFertileRate();
                bestFields = rectangle.getSize() > 1 ? rectangle.getSize() : bestFields;
            }
        }
        System.out.println(bestFields);
    }

    private static void rectangleSearch(int [][] fields, Rectangle rectangle){
        while (!rectangle.isEmpty()){
            Coordinate coor =  rectangle.getCoordinate();
            int x = coor.getX(); int y = coor.getY();
            isAround(fields, rectangle, x, y);
            rectangle.expandRectangle(x, y);
        }
    }
    // Не забыть в самом верхней строке пропускать через раз, чтобы ускорить алгоритм
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
class Rectangle {
    private int[] upperLeft;
    private int[] downRight;

    private int fertileAmount = 1;
    private LinkedList<Coordinate> coordinates = new LinkedList<>();

    Rectangle(int x, int y){
        this.coordinates.addFirst(new Coordinate(x, y));
        this.upperLeft = new int[]{x, y};
        this.downRight = new int[]{x, y};
    }

    @Override
    public String toString() {
        return "Rectangle:" + upperLeft[0] + ":" + upperLeft[1] + " " + downRight[0] + ":" +downRight[1]
                + "\n Size: " + getSize() + "; Fertile: " + getFertileRate();
    }
    public void expandRectangle(int x, int y){
        if (x < upperLeft[0]) upperLeft[0] = x;
        else if (x > downRight[0]) downRight[0] = x;

        if (y > downRight[1]) downRight[1] = y;
        else if (y < upperLeft[1]) upperLeft[1] = y;

    }

    public Coordinate getCoordinate(){
        return coordinates.poll();
    }
    public boolean isEmpty(){
        return coordinates.isEmpty();
    }
    public void newCoordinate(int x, int y){
        this.coordinates.addFirst(new Coordinate(x, y));
        fertileUp();
    }

    private void fertileUp(){
        this.fertileAmount++;
    }
    public int getSize(){
        int width = Math.abs(upperLeft[0] - downRight[0])+ 1;
        int height = Math.abs(upperLeft[1] - downRight[1])  + 1;
        return width * height;
    }
    public float getFertileRate(){
        return (float)fertileAmount/getSize();
    }

}
