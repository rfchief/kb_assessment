package com.kakaobank.profile.consumer.component;

import com.kakaobank.profile.consumer.service.WriteAccountLogService;
import org.apache.kafka.clients.consumer.CommitFailedException;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

public class ReadStreamFromKafkaComponent {

    private final static Logger logger = LoggerFactory.getLogger(ReadStreamFromKafkaComponent.class);
    private final KafkaConsumer<String, String> kafkaConsumer;
    private final WriteAccountLogService writeAccountLogService;

    public ReadStreamFromKafkaComponent(KafkaConsumer<String, String> kafkaConsumer, WriteAccountLogService writeAccountLogService) {
        this.kafkaConsumer = kafkaConsumer;
        this.writeAccountLogService = writeAccountLogService;
    }

    public void readStream(String topic, long timeout) {
        kafkaConsumer.subscribe(Arrays.asList(topic));
        try {
            while(true) {
                ConsumerRecords<String, String> records = kafkaConsumer.poll(timeout);
                for (ConsumerRecord<String, String> record : records) {
                    logger.debug(String.format("Topic : %s, Partition : %s, Offset : %d, Key : %s", record.topic(), record.partition(), record.offset(), record.key()));

                    writeAccountLogService.write(record.value());
                }

                try {
                    kafkaConsumer.commitSync();
                } catch (CommitFailedException exception){
                    logger.error("Failed to commit. [Message : " + exception.getMessage() + "]");
                }
            }
        } finally {
            logger.info("Consumer is closing....");
            kafkaConsumer.close();
        }
    }
}
