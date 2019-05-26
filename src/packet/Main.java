package packet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        MinedField minedField = new MinedField(10);

        System.out.println("Кол-во бомб на поле: " + minedField.getPlantedBombs());

        //cheat
        minedField.printOpenField();

        int y;
        int x;
        while(true) {
            minedField.printHiddenField();
            if (!minedField.isContinueToPlay()) break;
            System.out.print("Введите координату клетки:\n<строка> : ");
            y = Integer.parseInt(reader.readLine()) - 1;
            System.out.print("Введите координату клетки:\n<столбец> : ");
            x = Integer.parseInt(reader.readLine()) - 1;
            if (minedField.getCell(y,x) == '*') {
                minedField.setStatusDefeat();
                minedField.setBoom(y, x);
            } else {
                minedField.setOpenCell(y, x);
            }

            if (minedField.isWin()){
                break;
            }
        }

        System.out.println(minedField.isDefeat() ?
                "Ты проиграл, братик, но это норма, игра - как жизнь, хуй угадаешь" :
                "Ты ВЫЙГРАЛ! Ну это просто шок, ты везучий как Байгул");

    }
}
