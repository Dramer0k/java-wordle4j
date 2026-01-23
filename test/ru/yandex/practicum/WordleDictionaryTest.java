package ru.yandex.practicum;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static java.lang.System.getProperty;
import static org.junit.jupiter.api.Assertions.*;

class WordleDictionaryTest {

    WordleDictionaryLoader wordleDictionaryLoader = new WordleDictionaryLoader(new Logger("TestLogs.txt"));;
    WordleDictionary wordleDictionary = wordleDictionary = wordleDictionaryLoader.loadDictionary();;

    WordleDictionaryTest() throws IOException {
    }

    @Test
    void testUpdateDictionary() throws IOException {
        int oldWordSize = wordleDictionary.getWords().size();
        wordleDictionary.updateDictionary(1, 'a', "-", "горец");
        Assertions.assertTrue(oldWordSize > wordleDictionary.getWords().size());
    }

    @Test
    void testCheckWordInWords() {
        Assertions.assertTrue(wordleDictionary.checkWordInWords("горец"));
    }

    @Test
    void testCheckWordOutWords() {
        Assertions.assertFalse(wordleDictionary.checkWordInWords("мысли"));
    }
}