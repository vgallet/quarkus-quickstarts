package org.acme.kafka;

import org.eclipse.microprofile.reactive.messaging.Message;
import org.eclipse.microprofile.reactive.messaging.Outgoing;

import javax.enterprise.context.ApplicationScoped;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.function.IntFunction;
import java.util.function.IntSupplier;
import java.util.stream.IntStream;

/**
 * A bean producing random prices every 5 seconds.
 * The prices are written to a Kafka topic (prices). The Kafka configuration is specified in the application configuration.
 */
@ApplicationScoped
public class PriceGenerator {

    private Random random = new Random();

    @Outgoing("generated-price")
    public Message<Integer> generate() throws InterruptedException {
        Thread.sleep(5000);

        return Message.of(random.nextInt(100), () -> {
            System.out.println("Everything is fine!");
            return CompletableFuture.completedStage(null);
        }, throwable -> {
            System.out.println("Never call!");
            return CompletableFuture.failedStage(throwable);
        });
    }

}
