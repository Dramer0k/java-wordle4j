package ru.yandex.practicum;

import exceptions.IncorrectResponse;
import exceptions.WordNotFoundInDictionary;

public class WordleGame {

    private final String answer;
    private int steps;
    private final WordleDictionary dictionary;
    private final Logger logger;
    boolean stopGame = false;

    public WordleGame(WordleDictionary dictionary, Logger logger) {
        this.dictionary = dictionary;
        this.logger = logger;
        this.answer = dictionary.getRandom();
        this.steps = GameSetting.ATTEMPTS_NUMBER;
    }

    public String play(String response)  {
        logger.log("Старт игры...");
        logger.log("Данные: [Загаданное слово: {" + answer + "}" +
                ", Слово пользователя: {" + response + "}]");
        return checkAnswer(response);
    }

    public String checkAnswer(String response) {
        if (response == null || !response.isBlank()) { //если не пустой или null - переводим в нижний регистр, меняем "ё" на "е"
            response = editResponse(response);
            logger.log("Отфильтровали ответ: " + response);
        } else { //если пустой - даем подсказку
            logger.log("Пустой ответ, генерируем подсказку...");
            return checkAnswer(hintWord());
        }

        try {
            if (response.length() != GameSetting.WORD_LENGTH) { //проверяем на корректность длины
                throw new IncorrectResponse("Некорректный ввод. Слово должно состоять из " + GameSetting.WORD_LENGTH +
                        " букв или быть пустым");
            }
            if (!dictionary.checkWordInWords(response)) {
                throw  new WordNotFoundInDictionary("Введенное слово отсутствует в словаре!");
            }
            for (int i = 0; i < response.length(); i++) { //проверка на Кириллицу
                if (!Character.UnicodeBlock.of(response.charAt(i)).equals(Character.UnicodeBlock.CYRILLIC)) {
                    throw new IncorrectResponse("Слово должно состоять только из символов кириллицы или быть пустым");
                }
            }
        } catch (IncorrectResponse | WordNotFoundInDictionary e) {
            logger.log(e.getMessage());
            return e.getMessage();
        }

        steps--;
        logger.log("Использовано попыток: " + steps);
        if (response.equals(answer)) { //Верный ответ
            stopGame = true;
            logger.log("Слово угадано");
            return "Вы угадали! Это слово: " + answer;
        }
        return checkProgress(response, answer);
    }

    public static String editResponse(String response) {
        return response.toLowerCase().trim().replace("ё", "е");
    }

    public String hintWord() {
        String hintWord = dictionary.getRandom();
        logger.log("Подсказка для пользователя: " + hintWord);
        System.out.println(hintWord);
        return hintWord;
    }

    public String checkProgress(String response, String answer) {
        logger.log("Исходное слово {" + answer + "}  " + "Проверяемое {" + response + "}");
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < response.length(); i++) {
            boolean foundLetter = false;
            char symbol = response.charAt(i);

            if (symbol == answer.charAt(i)) {
                logger.log("{ " + i + " : " + symbol + " } - на своем месте");
                sb.append(GameSetting.CORRECT_LETTER);
                dictionary.updateDictionary(i, symbol, GameSetting.CORRECT_LETTER, response);
                continue;
            }

            String[] array = answer.split("");
            for (String s : array) {
                if (Character.toString(symbol).equals(s)) {
                    foundLetter = true;
                    break;
                }
            }
            if (foundLetter) {
                logger.log("В слове обнаружена буква {" + symbol + "}" + " не на своем месте");
                sb.append(GameSetting.DIFFERENT_POSITION);
                dictionary.updateDictionary(i, symbol, GameSetting.DIFFERENT_POSITION, response);
            } else {
                logger.log("В слове не обнаружена буква {" + symbol + "}");
                sb.append(GameSetting.INCORRECT_LETTER);
                dictionary.updateDictionary(i, symbol, GameSetting.INCORRECT_LETTER, response);
            }
        }
        return sb.toString();
    }

    public int getSteps() {
        return steps;
    }
}
