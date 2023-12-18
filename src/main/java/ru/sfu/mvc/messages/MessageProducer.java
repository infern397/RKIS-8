package ru.sfu.mvc.messages;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

/**
 * Компонент для отправки сообщений в JMS очередь.
 */
@Component
public class MessageProducer {

    private final JmsTemplate jmsTemplate;
    private final String messageQueue;

    /**
     * Конструктор для инъекции JmsTemplate и имени очереди сообщений.
     *
     * @param jmsTemplate   JmsTemplate для отправки сообщений.
     * @param messageQueue  Имя JMS очереди, полученное из конфигурации.
     */
    @Autowired
    public MessageProducer(JmsTemplate jmsTemplate,
                           @Value("${queue.name}") String messageQueue) {
        this.jmsTemplate = jmsTemplate;
        this.messageQueue = messageQueue;
    }

    /**
     * Отправляет сообщение в настроенную JMS очередь.
     *
     * @param message Сообщение, которое нужно отправить.
     */
    public void sendMessage(String message) {
        jmsTemplate.convertAndSend(messageQueue, message);
    }
}

