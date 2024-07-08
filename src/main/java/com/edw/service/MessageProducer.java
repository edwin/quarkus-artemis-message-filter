package com.edw.service;

import io.quarkus.runtime.ShutdownEvent;
import io.quarkus.runtime.StartupEvent;
import jakarta.jms.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import jakarta.jms.ConnectionFactory;
import jakarta.jms.JMSContext;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * <pre>
 *  com.edw.service.MessageProducer
 * </pre>
 *
 * @author Muhammad Edwin < edwin at redhat dot com >
 * 08 Jul 2024 19:59
 */

public class MessageProducer implements Runnable {
    @Inject
    ConnectionFactory connectionFactory;

    private Logger logger = LoggerFactory.getLogger(MessageProducer.class);

    private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

    void onStart(@Observes StartupEvent ev) {
        scheduler.scheduleWithFixedDelay(this, 0L, 5L, TimeUnit.SECONDS);
    }

    void onStop(@Observes ShutdownEvent ev) {
        scheduler.shutdown();
    }

    @Override
    public void run() {
        try (JMSContext context = connectionFactory.createContext(JMSContext.AUTO_ACKNOWLEDGE)) {
            try {
                String uuid = UUID.randomUUID().toString();
                int key = new Random().nextInt(6) + 1; // between 1 to 6

                String message = key+ "---" + uuid;
                Message jmsMessage = context.createTextMessage(message);
                jmsMessage.setIntProperty("key", key);

                context.createProducer().send(context.createQueue("my-message"), jmsMessage);

                logger.info("sending message >>> {}", message);
            } catch (Exception ex) {
                logger.error(ex.getMessage(), ex);
            }
        }
    }
}
