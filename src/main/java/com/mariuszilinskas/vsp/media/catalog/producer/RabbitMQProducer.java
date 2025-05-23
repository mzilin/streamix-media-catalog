package com.mariuszilinskas.vsp.contentservice.producer;

import com.mariuszilinskas.vsp.contentservice.dto.MediaESRequest;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RabbitMQProducer {

    private static final Logger logger = LoggerFactory.getLogger(RabbitMQProducer.class);
    private final RabbitTemplate rabbitTemplate;

    @Value("${rabbitmq.exchange}")
    private String exchange;

    @Value("${rabbitmq.routing-keys.add-update-content}")
    private String addUpdateContentRoutingKey;

    @Value("${rabbitmq.routing-keys.delete-content}")
    private String deleteContentRoutingKey;

    public void sendAddUpdateContentMessage(MediaESRequest request) {
        logger.info("Sending message to add/update media content data [title: {}]", request.title());
        rabbitTemplate.convertAndSend(exchange, addUpdateContentRoutingKey, request);
    }

    public void sendDeleteContentMessage(String contentId) {
        logger.info("Sending message to delete content data [contentId: {}]", contentId);
        rabbitTemplate.convertAndSend(exchange, deleteContentRoutingKey, contentId);
    }

}
