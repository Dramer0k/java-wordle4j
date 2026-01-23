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

class WordleDictionaryLoaderTest {

     WordleDictionaryLoader wordleDictionaryLoader = wordleDictionaryLoader = new WordleDictionaryLoader(new Logger("TestLogs.txt"));

    WordleDictionaryLoaderTest() throws IOException {
    }

    @Test
    void readSourceDictionaryTest() throws IOException {
        wordleDictionaryLoader.readSourceDictionary();
        Assertions.assertEquals(67083, wordleDictionaryLoader.getSourceDictionary().size());
    }

    @Test
    void filterSourceDictionaryTest() throws IOException {
        wordleDictionaryLoader.readSourceDictionary();
        wordleDictionaryLoader.filterSourceDictionary();
        Assertions.assertEquals(4108, wordleDictionaryLoader.getFilteredDictionary().size());
    }
}