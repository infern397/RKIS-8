package ru.sfu.mvc.messages;

import jakarta.annotation.PreDestroy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Компонент для приема сообщений из JMS очереди.
 */
@Component
public class MessageReceiver {

    private final JmsTemplate jmsTemplate;
    private final String messageQueue;
    private final ScheduledExecutorService scheduledService;

    /**
     * Конструктор для инъекции JmsTemplate, имени очереди сообщений и создания планировщика задач.
     *
     * @param jmsTemplate    JmsTemplate для приема сообщений.
     * @param messageQueue   Имя JMS очереди, полученное из конфигурации.
     */
    @Autowired
    public MessageReceiver(JmsTemplate jmsTemplate, @Value("${queue.name}") String messageQueue) {
        this.jmsTemplate = jmsTemplate;
        this.messageQueue = messageQueue;
        this.scheduledService = Executors.newSingleThreadScheduledExecutor();
    }

    /**
     * Получает сообщение из настроенной JMS очереди.
     *
     * @return Полученное сообщение или null, если нет новых сообщений или возникла ошибка.
     */
    public String receiveMessage() {
        try {
            return (String) jmsTemplate.receiveAndConvert(messageQueue);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Начинает периодический прием сообщений с использованием планировщика задач.
     */
    public void startReceivingMessages() {
        scheduledService.scheduleAtFixedRate(() -> {
            try {
                String receivedMessage = receiveMessage();
                System.out.println(Objects.requireNonNullElse(receivedMessage, "No new messages."));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, 0, 150, TimeUnit.MILLISECONDS);
    }

    /**
     * Выполняет остановку периодического приема сообщений и завершение работы планировщика задач.
     */
    @PreDestroy
    public void stopReceivingMessages() {
        scheduledService.shutdown();
        try {
            scheduledService.awaitTermination(5, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

