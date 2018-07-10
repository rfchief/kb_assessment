package com.kakaobank.profile.producer.component.mock;

import com.kakaobank.profile.producer.component.WriteDataToKafkaComponent;
import org.apache.kafka.clients.producer.MockProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.util.concurrent.ExecutionException;

public class MockWriteDataToKafkaComponent implements WriteDataToKafkaComponent {

    private final MockProducer<String, String> producer;
    private final String kafkaTopic;

    public MockWriteDataToKafkaComponent(MockProducer<String, String> producer, String kafkaTopic) {
        this.producer = producer;
        this.kafkaTopic = kafkaTopic;
    }

    public void close() {
        producer.close();
    }

    public void write(String content) throws ExecutionException, InterruptedException {
        ProducerRecord<String, String> record = new ProducerRecord<String, String>(kafkaTopic, content);
        RecordMetadata recordMetadata = producer.send(record).get();
        System.out.println(String.format("Topic : %s, Partition : %d, Offset : %d", kafkaTopic, recordMetadata.partition(), recordMetadata.offset()));
    }
}
