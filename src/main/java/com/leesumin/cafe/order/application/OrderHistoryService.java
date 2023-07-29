package com.leesumin.cafe.order.application;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.leesumin.cafe.kafka.producer.KafkaProducer;
import com.leesumin.cafe.order.exception.OrderHistoryMessageSendException;
import com.leesumin.cafe.order.interfaces.OrderDto;
import com.leesumin.cafe.order.message.OrderHistoryMessage;
import com.leesumin.cafe.util.JsonUtil;
import org.apache.kafka.clients.producer.MockProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.Future;

@Service
public class OrderHistoryService {

    public List<ProducerRecord<String, String>> send(OrderDto order) throws JsonProcessingException {
        OrderHistoryMessage message = OrderHistoryMessage.builder()
                .customerId(order.getCustomerId())
                .orderItemDtos(order.getOrderItemDtos())
                .totalPrice(order.getTotalPrice())
                .orderDateTime(order.getOrderDateTime())
                .build();
        String value = JsonUtil.objectToJsonMapper(message);

        MockProducer<String, String> mockProducer = new MockProducer<>(true, new StringSerializer(), new StringSerializer());
        KafkaProducer kafkaProducer = new KafkaProducer(mockProducer);
        Future<RecordMetadata> recordMetadataFuture = kafkaProducer.send("OrderHistoryMessage", value);

        if (mockProducer.history().size() < 1) {
            throw new OrderHistoryMessageSendException();
        }

        return mockProducer.history();
    }
}
