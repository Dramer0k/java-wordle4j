package ru.yandex.practicum;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

public class Logger {

    private final BufferedWriter bufferedWriter;

    public Logger(String str) throws IOException {
        this.bufferedWriter = new BufferedWriter(new FileWriter(createFile(str), StandardCharsets.UTF_8));
    }

    public File createFile(String name) {
        Path path = Paths.get(System.getProperty("user.dir"), "logs", name);
        try {
            if (!Files.exists(path.getParent())) {
                Files.createDirectories(path.getParent());
            }
            if (!Files.exists(path)) {
                Files.createFile(path);
            }
        } catch (IOException e) {
            System.out.println("Что-то пошло не так при создании файла логов");
            System.out.println(Arrays.toString(e.getStackTrace()));
        }

        return path.toFile();
    }

    public void log(String message) throws IOException {
        try {
            bufferedWriter.write(message);
            bufferedWriter.write("\n");
        } catch (IOException e) {
            bufferedWriter.write("[Error] Ошибка при записи в лог");
        }
    }

    public void close() throws IOException {
        bufferedWriter.close();
    }

}
