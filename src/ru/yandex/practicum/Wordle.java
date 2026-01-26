package ru.yandex.practicum;

import java.util.Scanner;

/*
в главном классе нам нужно:
    создать лог-файл (он должен передаваться во все классы)
    создать загрузчик словарей WordleDictionaryLoader
    загрузить словарь WordleDictionary с помощью класса WordleDictionaryLoader
    затем создать игру WordleGame и передать ей словарь
    вызвать игровой метод в котором в цикле опрашивать пользователя и передавать информацию в игру
    вывести состояние игры и конечный результат
 */
public class Wordle {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        try (Logger logger = new Logger(GameSetting.LOGGER_FILE_NAME)) {
            WordleDictionaryLoader loader = new WordleDictionaryLoader(logger); //создаем загрузчик словаря
            WordleDictionary wordleDictionary = loader.loadDictionary(); // загружаем словарь
            WordleGame wordleGame = new WordleGame(wordleDictionary, logger); //создаем игру
            preview();
            while (true) {
                if (wordleGame.stopGame) {
                    break;
                } else if (wordleGame.getSteps() < GameSetting.ATTEMPTS_NUMBER && wordleGame.getSteps() > 0) {
                    System.out.println("Осталось попыток: " + wordleGame.getSteps());
                } else if (wordleGame.getSteps() == 0) {
                    System.out.println("Лимит попыток исчерпан :(");
                    logger.log("Лимит попыток исчерпан");
                    break;
                }
                String input = scanner.nextLine();
                System.out.println(wordleGame.play(input));
            }
        } catch (Throwable e) {
            System.out.println("Что-то пошло не так");
        }
    }

    public static void preview() {
        System.out.println("Давайте сыграем в игру!");
        System.out.println("Я загадал слово из " + GameSetting.WORD_LENGTH + " букв. Попробуйте угадать с " +
                GameSetting.ATTEMPTS_NUMBER + " попыток!");
        System.out.println("Во время игры я буду давать вам подсказки:");
        System.out.println(GameSetting.CORRECT_LETTER + " - такая буква есть в слове и она на своем месте");
        System.out.println(GameSetting.DIFFERENT_POSITION + " - такая буква есть в слове, но она не на своем месте");
        System.out.println(GameSetting.INCORRECT_LETTER + " - такой буквы в слове нет");
        System.out.println("Введите предполагаемое слово:");
    }
}
