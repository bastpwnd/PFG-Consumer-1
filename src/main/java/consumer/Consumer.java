package consumer;


import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Properties;


public class Consumer {

    private static final Logger LOG = LoggerFactory.getLogger(Consumer.class);

    private KafkaConsumer<String, String> kafkaConsumerClient;

    /**
     * Method to create consumer and suscribe a topic
     */
    public void createKafkaConsumer() {

        // Configure the properties and create the Kafka consumer
        Properties props = initProperties();

        // Initialize the kafka consumer instance
        kafkaConsumerClient = new KafkaConsumer<>(props);
        kafkaConsumerClient.subscribe(Arrays.asList("test_1"));

        checkNewStockAvailability();
    }

    /**
     * Properties to Kafka consumer
     * @return props properties with values
     */
    public Properties initProperties(){
        Properties props = new Properties();
        props.put(ConsumerConfig.CLIENT_ID_CONFIG, "Tienda-1-Consumer");
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "New stock");
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "kafka:9092");
        //localhost
        //props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:29092");
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, true);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());

        return props;
    }

    /**
     * Method to consume all events and print data in log
     */
    public void checkNewStockAvailability() {
        ConsumerRecords<String, String> records;
        if (kafkaConsumerClient == null) {
            LOG.warn("Kafka consumer will not be initialized!");
        }
        LOG.info("Lets poll kafka topic");
        while (true) {
            records = kafkaConsumerClient.poll(Duration.of(1000, ChronoUnit.MILLIS));
            for (ConsumerRecord<String, String> record : records) {
                if (record != null && record.value() != null) {
                    final String event = record.value();
                    LOG.info("Consuming event: {}", event);

                }
            }
        }
    }

}
