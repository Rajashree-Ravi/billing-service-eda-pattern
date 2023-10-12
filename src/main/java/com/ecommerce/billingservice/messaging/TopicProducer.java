package com.ecommerce.billingservice.messaging;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.ecommerce.billingservice.model.BillDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class TopicProducer {

	@Value("${producer.config.topic.name}")
	private String topicName;

	private final KafkaTemplate<String, BillDto> kafkaTemplate;

	public void send(BillDto bill) {
		log.info("Payload : {}", bill.toString());
		kafkaTemplate.send(topicName, bill);
	}

}