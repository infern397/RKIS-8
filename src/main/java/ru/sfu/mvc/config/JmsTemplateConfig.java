package ru.sfu.mvc.config;

import jakarta.jms.ConnectionFactory;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

/**
 * Класс конфигурации для настройки JmsTemplate.
 */
@Configuration
@Component
public class JmsTemplateConfig {

    /**
     * Предоставляет настроенный ConnectionFactory для JMS.
     *
     * @return Настроенный экземпляр ConnectionFactory.
     */
    @Bean
    public ConnectionFactory connectionFactory() {
        return new ActiveMQConnectionFactory("tcp://localhost:61616");
    }

    /**
     * Создает бин JmsTemplate с областью видимости prototype и настроенным ConnectionFactory.
     *
     * @return Экземпляр JmsTemplate с областью видимости prototype.
     */
    @Bean
    @Scope("prototype")
    public JmsTemplate jmsTemplate() {
        return new JmsTemplate(connectionFactory());
    }
}
