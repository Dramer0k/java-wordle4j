package ru.yandex.practicum;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/*
этот класс содержит в себе всю рутину по работе с файлами словарей и с кодировками
    ему нужны методы по загрузке списка слов из файла по имени файла
    на выходе должен быть класс WordleDictionary
 */
public class WordleDictionaryLoader {

    private final Logger logger;
    private final List<String> sourceDictionary = new ArrayList<>();
    private final List<String> filteredDictionary = new ArrayList<>();

    public WordleDictionaryLoader(Logger logger) {
        this.logger = logger;

    }

    public WordleDictionary LoadDictionary() throws IOException {
        logger.log("Читаем исходный словарь...");
        readSourceDictionary();
        logger.log("Подготавливаем исходный словарь к игре...");
        filterSourceDictionary();
        logger.log("Словарь готов к игре!");

        return new WordleDictionary(filteredDictionary, logger);
    }

    public void readSourceDictionary() throws IOException{
        Path dictionaryFilePath = Paths.get(System.getProperty("user.dir"), GameSetting.DICTIONARY_FILE_NAME);
        try (FileReader fileReader = new FileReader(dictionaryFilePath.toFile(), StandardCharsets.UTF_8)) {
            BufferedReader br = new BufferedReader(fileReader);
            while (fileReader.ready()) {
                String str = br.readLine();
                if (!str.isBlank()) {
                    sourceDictionary.add(str);
                }
            }
        } catch (IOException e) {
            logger.log("[Error] Ошибка при чтении исходного словаря");
            logger.log(Arrays.toString(e.getStackTrace()));
        }

    }

    public void filterSourceDictionary() throws IOException {
        if (!sourceDictionary.isEmpty()) {
            logger.log("Найден исходный словарь! Значений: " + sourceDictionary.size());
            for (String str : sourceDictionary) {
                if (str.length() == GameSetting.WORD_LENGTH) {
                    String[] array = str.toLowerCase().split("");
                    for (int i = 0; i < array.length; i++) {
                        if (array[i].equals("ё")) {
                            array[i] = "е";
                        }
                    }
                    filteredDictionary.add(String.join("", array));

                }
            }
            logger.log("Исходный словарь успешно отфильтрован! Значений: " + filteredDictionary.size());
            return;
        }
        logger.log("[Error] Исходный словарь пуст!");
    }

    public List<String> getSourceDictionary() {
        return sourceDictionary;
    }

    public List<String> getFilteredDictionary() {
        return filteredDictionary;
    }

    public Logger getLogger() {
        return logger;
    }
}
