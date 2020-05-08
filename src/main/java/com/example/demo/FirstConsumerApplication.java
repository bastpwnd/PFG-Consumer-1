package com.example.demo;

import consumer.Consumer;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.text.MessageFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

@SpringBootApplication(scanBasePackages = { "consumer" })
@EnableScheduling
public class FirstConsumerApplication {

    private static final Log log = LogFactory.getLog(FirstConsumerApplication.class);
    public static Map<String,Integer> productos = new HashMap<>();;
    public static void main(String[] args) {
        SpringApplication.run(FirstConsumerApplication.class, args);
        long startTime = Calendar.getInstance().getTimeInMillis();

        long endTime = Calendar.getInstance().getTimeInMillis();
        double startingAppTimeInSeconds = (endTime - startTime) / 1000.0;

        log.info("--------------------------------------------------------------------");
        log.info(
                MessageFormat.format(
                        "|||| Consumer 1 - Ivan Martin - PFG -  Application started successfully in {0} seconds ||||",
                        startingAppTimeInSeconds));
        log.info("--------------------------------------------------------------------");
        //Activar esta opcion si no se quiere usar el la escucha mediante un listener*/
        /*Consumer tienda = new Consumer();
        tienda.createKafkaConsumer();*/
    }

}
