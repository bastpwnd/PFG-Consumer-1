package consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class ConsumerWithListener {

    private Logger LOG = LoggerFactory.getLogger(ConsumerWithListener.class.getName());
    private static Map<Integer, String> products = new HashMap<>();
    private static ArrayList<String> eventos = new ArrayList<String>();;

    @KafkaListener(topics = "test_1", groupId = "Tienda-1")
    public void receive(ConsumerRecord<String, String> record){

        LOG.info("Consuming event: {}", record.value());
        insertDataInMap(record);
    }

    public void insertDataInMap(ConsumerRecord<String, String> evento){

        String data []= evento.value().split(":");

        products.put(Integer.parseInt(data[1]),data[0]);

        LocalDateTime date =
                LocalDateTime.ofInstant(Instant.ofEpochMilli(evento.timestamp() ), TimeZone
                        .getDefault().toZoneId());

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        eventos.add(data[0]+" "+data[1]+" creado: "+date+" consumido: "+timestamp.toLocalDateTime());
    }

    @Scheduled(cron = "*/20 * * * * ?")
    public void printProductsById(){
        LOG.info("Imprimiendo productos recibidos en la tienda:");
        Map<Integer, String> productsByKey = products.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue, LinkedHashMap::new));
        productsByKey.forEach((k,v)->System.out.println("Tipo de producto : " + v + " ID : " + k));
    }

    public static List<String> getEvents(){

        return eventos;
    }


}
