package ru.sfu.mvc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import ru.sfu.mvc.messages.MessageReceiver;

@SpringBootApplication
public class MvcApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(MvcApplication.class, args);
		MessageReceiver messageReceiver = context.getBean(MessageReceiver.class);
		messageReceiver.startReceivingMessages();
	}

}
