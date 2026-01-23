package ru.yandex.practicum;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static ru.yandex.practicum.GameSetting.*;

/*
этот класс содержит в себе список слов List<String>
    его методы похожи на методы списка, но учитывают особенности игры
    также этот класс может содержать рутинные функции по-сравнению слов, букв и т.д.
 */
public class WordleDictionary {

    private final List<String> words;
    Logger logger;

    public WordleDictionary(List<String> words, Logger logger) {
        this.words = words;
        this.logger = logger;
    }

    public String getRandom() {
        Random random = new Random();
        int randomNumber = random.nextInt(words.size());

        return words.get(randomNumber);
    }

    public void updateDictionary(int position, char symbol, String verdict, String response) throws IOException {
        List<String> toRemove = new ArrayList<>();
        toRemove.add(response);
        for (String str : words) {
            char currentSymbol = str.charAt(position);
            if (verdict.equals(CORRECT_LETTER) && !Character.toString(currentSymbol).equals(Character.toString(symbol))) {
                toRemove.add(str);
            } else if (verdict.equals(INCORRECT_LETTER) || verdict.equals(DIFFERENT_POSITION)) {
                boolean foundLetter = false;
                for (int i = 0; i < str.length(); i++) {
                    String letter = String.valueOf(str.charAt(i));
                    if (letter.equals(Character.toString(symbol))) {
                        foundLetter = true;
                    }
                }
                if (foundLetter && verdict.equals(INCORRECT_LETTER) || !foundLetter && verdict.equals(DIFFERENT_POSITION)) {
                    toRemove.add(str);
                }
            }
        }
        words.removeAll(toRemove);
        logger.log("После фильтрации в словаре осталось значений: " + words.size());
    }

    public boolean checkWordInWords(String response) {
        return words.contains(response);
    }

    public List<String> getWords() {
        return words;
    }
}

