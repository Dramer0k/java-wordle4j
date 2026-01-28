package ru.yandex.practicum;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static java.lang.System.getProperty;
import static org.junit.jupiter.api.Assertions.*;

class LoggerTest {

    static Path path = Paths.get(getProperty("user.dir"), "logs", "TestLogs");
    Logger logger = new Logger("TestLogs");

    LoggerTest() throws IOException {
    }

    @Test
    void addLoggerFile() throws IOException {
        Assertions.assertTrue(Files.exists(path));
        logger.close();
    }

    @AfterAll
    static void afterAll() throws IOException {
        if (Files.exists(path)) {
            Files.delete(path);
        }
    }
}