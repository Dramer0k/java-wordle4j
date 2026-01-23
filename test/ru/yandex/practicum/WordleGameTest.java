package ru.yandex.practicum;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class WordleGameTest {

    static WordleDictionaryLoader wordleDictionaryLoader;
    static WordleDictionary wordleDictionar;
    static WordleGame wordleGame;

    @BeforeAll
    static void beforeAll() throws IOException {
        wordleDictionaryLoader = new WordleDictionaryLoader(new Logger("TestLogs"));
        wordleDictionar = wordleDictionaryLoader.loadDictionary();
        wordleGame = new WordleGame(wordleDictionar, new Logger("TestLogs.txt"));

    }

    @Test
    void testCheckInputFilter() {
        String testStr = wordleGame.inputFilter("Ёжик");
        Assertions.assertEquals("ежик", testStr);
    }

    @Test
    void testGiveHintWord() throws IOException {
        Assertions.assertTrue(wordleDictionar.getWords().contains(wordleGame.hintWord()));
    }

    @Test
    void checkProgressTest() throws IOException {
        String response = "акула";
        String answer = "арбуз";
        String progress = "+-^-^";
        Assertions.assertEquals(progress, wordleGame.checkProgress(response, answer));
    }

}