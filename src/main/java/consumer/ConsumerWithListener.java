package consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ConsumerWithListener {

    private Logger LOG = LoggerFactory.getLogger(ConsumerWithListener.class.getName());
    private static Map<Integer, String> products = new HashMap<>();

    @KafkaListener(topics = "test_1", groupId = "Tienda-1")
    public void receive(ConsumerRecord<String, String> record){

        LOG.info("Consuming event: {}", record.value());
        insertDataInMap(record.value());
    }

    public void insertDataInMap(String evento){

        String data []= evento.split(":");
        products.put(Integer.parseInt(data[1]),data[0]);
    }
    @Scheduled(cron = "*/10 * * * * ?")
    public void printProductsById(){
        LOG.info("Imprimiendo productos recibidos en la tienda:");
        Map<Integer, String> productsByKey = products.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue, LinkedHashMap::new));
        productsByKey.forEach((k,v)->System.out.println("Tipo de producto : " + v + " ID : " + k));
    }



}
