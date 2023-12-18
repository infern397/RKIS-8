package ru.sfu.mvc.client;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * Класс, реализующий интерфейс CommandLineRunner для выполнения операций FurnitureRestClient при запуске приложения.
 */
@Component
public class FurnitureClientRunner implements CommandLineRunner {

    private final FurnitureRestClient furnitureRestClient;

    /**
     * Конструктор класса FurnitureClientRunner.
     *
     * @param furnitureRestClient клиент для взаимодействия с REST API мебели.
     */
    public FurnitureClientRunner(FurnitureRestClient furnitureRestClient) {
        this.furnitureRestClient = furnitureRestClient;
    }

    /**
     * Метод, выполняющийся при запуске приложения. Проверяет наличие системного свойства и запускает операции клиента.
     *
     * @param args аргументы командной строки (не используются).
     */
    @Override
    public void run(String... args) {
        boolean start = args.length == 1 && args[0].equals("true");
        if (!start) {
            return;
        }
        System.out.println("Running Furniture Client...");
        furnitureRestClient.performAllOperations();
        System.out.println("Furniture Client Finished.");
    }
}

