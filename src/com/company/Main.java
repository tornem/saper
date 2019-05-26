package com.company;

import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

enum gameState{
    PLAYING,
    DEFEAT,
    WIN
}

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();

	    char[][] minedField = new char[5][5];
        for (int i = 0; i < minedField.length; i++) {
            Arrays.fill(minedField[i],'O');
        }

	    final int amountOfBombs = 24;
        int plantedBombs = 0;
        end:
        for (int i = 0; i < 5; ++i) {
            for (int j = 0; j < 5; ++j){
//                boolean isBomb = random.nextBoolean();
                boolean isBomb = true;
                if (isBomb){
                    ++plantedBombs;
                    minedField[i][j] = '*';
                }
                if (amountOfBombs == plantedBombs) break end;
            }
        }

        System.out.println("Кол-во бомб на поле: " + plantedBombs);

        //cheat
        for (int i = 0; i < minedField.length; i++) {
            System.out.println(Arrays.toString(minedField[i]));
        }
        gameState currentState = gameState.PLAYING;
        int openedCells = 0;
        while(true) {
            printGameField(minedField);
            if (currentState != gameState.PLAYING) break;
            System.out.println("Введите координату клетки:<строка,столбец>");
            int y = scanner.nextInt() - 1; //1
            int x = scanner.nextInt() - 1; //4
            if (minedField[y][x] == '*') {
                currentState = gameState.DEFEAT;
                minedField[y][x] = '⨂';
            } else {
                minedField[y][x] = 'N';
            }

            ++openedCells;

            if (currentState == gameState.PLAYING &&
                openedCells == 25 - plantedBombs){
                currentState = gameState.WIN;
            }
        }

        System.out.println(currentState == gameState.DEFEAT ?
                "Ты проиграл, братик, но это норма, игра - как жизнь, хуй угадаешь" :
                "Ты ВЫЙГРАЛ! Ну это просто шок, ты везучий как Байгул");

    }

    static void printGameField(char[][] gameField){

        StringBuilder buffer = new StringBuilder();

        for (int i = 0; i < 5; ++i) {
            for (int j = 0; j < 5; ++j) {
                switch (gameField[i][j]){
                    case 'N':
                        buffer.append(" N ");
                        break;
                    case '⨂':
                        buffer.append(" ⨂ ");
                        break;
                    default:
                        buffer.append(" \u086B ");
                        break;
                }
            }
            System.out.println(buffer);
            buffer = new StringBuilder();
        }
    }
}
