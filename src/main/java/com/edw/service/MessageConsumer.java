package com.edw.service;

import io.quarkus.runtime.ShutdownEvent;
import io.quarkus.runtime.StartupEvent;
import io.smallrye.common.annotation.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import jakarta.jms.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * <pre>
 *  com.edw.service.MessageConsumer
 * </pre>
 *
 * @author Muhammad Edwin < edwin at redhat dot com >
 * 08 Jul 2024 20:07
 */

public class MessageConsumer implements Runnable {

    @Inject
    ConnectionFactory connectionFactory;

    private Logger logger = LoggerFactory.getLogger(MessageConsumer.class);

    public MessageConsumer() {
    }

    private final ExecutorService scheduler = Executors.newSingleThreadExecutor();

    void onStart(@Observes StartupEvent ev) {
        scheduler.submit(this);
    }

    void onStop(@Observes ShutdownEvent ev) {
        scheduler.shutdown();
    }

    @Override
    public void run() {
        new Thread(() -> {
            try (JMSContext context = connectionFactory.createContext(JMSContext.AUTO_ACKNOWLEDGE)) {
                JMSConsumer consumer = context.createConsumer(context.createQueue("my-message"), "key <= 3");
                while (true) {
                    Message message = consumer.receive();
                    if (message == null) return;
                    String jmsMessage = message.getBody(String.class);

                    logger.info("---- msg from listener with key less or equals to 3 ----  {}", jmsMessage);
                }
            } catch (JMSException e) {
                throw new RuntimeException(e);
            }
        }).start();

        new Thread(() -> {
            try (JMSContext context = connectionFactory.createContext(JMSContext.AUTO_ACKNOWLEDGE)) {
                JMSConsumer consumer = context.createConsumer(context.createQueue("my-message"), "key > 3 ");
                while (true) {
                    Message message = consumer.receive();
                    if (message == null) return;
                    String jmsMessage = message.getBody(String.class);

                    logger.info("++++ msg from listener with key more than 3 ++++  {}", jmsMessage);
                }
            } catch (JMSException e) {
                throw new RuntimeException(e);
            }
        }).start();
    }
}
