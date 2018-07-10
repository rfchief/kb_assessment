package com.kakaobank.profile.producer.component.impl;

import com.kakaobank.profile.producer.component.WriteDataToKafkaComponent;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutionException;

public class WriteDataToKafkaComponentImpl implements WriteDataToKafkaComponent {
    private final Logger logger = LoggerFactory.getLogger(WriteDataToKafkaComponentImpl.class);

    private final String kafkaTopic;
    private final KafkaProducer<String, String> producer;

    public WriteDataToKafkaComponentImpl(KafkaProducer<String, String> producer, String kafkaTopic) {
        this.producer = producer;
        this.kafkaTopic = kafkaTopic;
    }

    public void close() {
        this.producer.close();
    }

    public void write(String content) throws ExecutionException, InterruptedException {
        if(content == null || content == "")
            throw new IllegalArgumentException("Content is empty!");

        ProducerRecord<String, String> record = new ProducerRecord<String, String>(kafkaTopic, content);
        RecordMetadata recordMetadata = producer.send(record).get();
        logger.debug(String.format("Topic : %s, Partition : %d, Offset : %d", kafkaTopic, recordMetadata.partition(), recordMetadata.offset()));
    }


}
