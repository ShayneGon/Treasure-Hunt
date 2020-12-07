// there are two types of bots, the easy bot guess randomly to find treasure
// the hard bot rembers what spots have already been looked at and make sures to not pick that one
package com.mycompany.treasurehunt;
import java.util.*;
public class main {
    static Random rand = new Random();
    static Scanner input = new Scanner(System.in);
    
    public static void main(String[] args){
        int[][] board = new int[10][11];
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                board[i][j] = 0;
            }
        }
        int[][] botMemory = new int[10][10];
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                botMemory[i][j] = 1;
            }
        }
        System.out.println("Welcome to treasure hunt, in this game you dig for treasure by guessing where on the board it is.");
        System.out.println("You can play by yourself[1], with someone else[2] or with a bot[3] - please pick the number corrosponding to your choice.");
        int choice = input.nextInt();
        System.out.println("Fair enough.");
        for (int i = 0; i < 3; i++) {
            System.out.println(" ");
        }
        System.out.println("There are 15 spots filled with treasure on the board");
        fillBoard(board);
        System.out.println(Arrays.deepToString(board));
        
        boolean gameOver = false;
        int points = 0;
        int points2 = 0; 
        int winPoints = 0;
        if (board[9][10]%2==1){
            winPoints = Math.round(board[9][10]/2);
        }else{
            winPoints = (board[9][10]/2)+1;
        }
        if (choice == 1){
            while (gameOver == false){
                System.out.println("You need "+board[9][10]+" coins to win,");
                String name = ("You");
                points = points+player(board,name)[0];
                System.out.println("Your coins are: "+points);
                if (board[9][10] == points){
                    gameOver = true;
                }
            }
        }else if (choice == 2){
            while (gameOver == false){
                System.out.println("You need "+winPoints+" coins to win,");
                String name1 = "Player 1";
                String name2 = "Player 2";
                points = points + player(board, name1)[0];
                points2 = points2 + player(board, name2)[0];
                System.out.println("Player 1's coins are: "+points);
                System.out.println("Player 2's coins are: "+points2);
                if (winPoints <= points){
                    System.out.println("P1 wins");
                    gameOver = true;
                }else if (winPoints <= points2){
                    System.out.println("P2 wins");
                    gameOver = true;
                }
            }
        }else if (choice == 3) {
            System.out.println("Would you like the bot to be easy[1] or hard[2]");
            int choice2 = input.nextInt();
            System.out.println("You need "+winPoints+" coins to win,");
            while (gameOver == false){
                if (choice2 == 1) { 
                    String name = "You";
                    points = points + player(board, name)[0];
                    points2 = points2 + botEasy(board);
                    System.out.println("Your coins are: "+points);
                    System.out.println("The bots coins are: "+points2);
                    if (winPoints <= points){
                        System.out.println("You win");
                        gameOver = true;
                    }else if (winPoints <= points2){
                        System.out.println("You lose");
                        gameOver = true;
                    }
                }else{
                    String name = "You";
                    int[] Arr = player(board, name);
                    points = points + Arr[0];
                    points2 = points2 + botHard(board, Arr, botMemory);
                    System.out.println("Your coins are: "+points);
                    System.out.println("The bots coins are: "+points2);
                    if (winPoints <= points){
                        System.out.println("You win");
                        gameOver = true;
                    }else if (winPoints <= points2){
                        System.out.println("You lose");
                        gameOver = true;
                    }
                }
            }
        }        
    }
    
    public static int[][] fillBoard(int[][] board){
        int points = 0;
        for (int i = 0; i < 30; i++) {
            int p1 = rand.nextInt(9-0)+0;
            int p2 = rand.nextInt(9-0)+0;
            if (board[p1][p2]==0){
                int treasure = rand.nextInt(10-1)+1;
                points = points + treasure;
                board[p1][p2] = treasure;
            }else {
                i--;
            }
        }
        board[9][10] = points;
        return board;
    }
    
    public static int botEasy(int[][] board){
        int[] position = new int[2];
        int botPoints = 0;
        position[0] = rand.nextInt(9-0)-0;
        position[1] = rand.nextInt(9-0)-0;
        botPoints = board[position[0]][position[1]];
        board[position[0]][position[1]] = 0;
        int row = position[0]+1;
        int column = position[1]+1;
        System.out.println("The bot chose row: "+row+" and column: "+column);
        System.out.println("The bot got "+botPoints+" coins");
        return botPoints;
    }
    public static int[] player(int[][]board, String name){
        int[] points = new int[3]; //Needed to return the variable row and colum so doing it to an array so I can return points as well
        System.out.println("Choose the row you want to dig for treasure at?");
        int row = (input.nextInt())-1;
        System.out.println("Choose the column you want to dig for treasure at?");
        int column = (input.nextInt()-1);
        points[0] = board[row][column];
        board[row][column] = 0;
        points[1] = row;
        points[2] = column;
        System.out.println(name+ " got "+points[0]+" coins");
        return points;
    }
    
    public static int botHard(int[][] board, int[] Arr, int[][] botMemory){
        int[] position = new int[2];
        int botPoints = 0;
        boolean repeat = false;
        botMemory[Arr[1]][Arr[2]] = 0;
        do{
            position[0] = rand.nextInt(9-0)-0;
            position[1] = rand.nextInt(9-0)-0;
            if ((botMemory[position[0]][position[1]])!=0){
                botPoints = board[position[0]][position[1]];
                int row = position[0]+1;
                int column = position[1]+1;
                System.out.println("The bot chose row: "+row+" and column: "+column);
                System.out.println("The bot got "+botPoints+" coins");
                board[position[0]][position[1]] = 0;
                botMemory[position[0]][position[1]] = 0;
                System.out.println(Arrays.deepToString(botMemory));
                repeat = false;
            }else{
                repeat = true;
            }
        }while (repeat == true);
        return botPoints;
    }
}

