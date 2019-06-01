package packet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        MinedField minedField = new MinedField(10);

        //cheat
        minedField.getOpenField();

        int y;
        int x;
        while(minedField.isPlaying()) {
            System.out.println(minedField.getView());
            System.out.print("Введите координату клетки:\n<строка> : ");
            y = Integer.parseInt(reader.readLine()) - 1;
            System.out.print("Введите координату клетки:\n<столбец> : ");
            x = Integer.parseInt(reader.readLine()) - 1;
            minedField.openCell(y,x);
        }
        minedField.getOpenField();

        System.out.println(minedField.isDefeat() ?
                "Ты проиграл, братик, но это норма, игра - как жизнь, хуй угадаешь" :
                "Ты ВЫЙГРАЛ! Ну это просто шок, ты везучий как Байгул");

    }
}
