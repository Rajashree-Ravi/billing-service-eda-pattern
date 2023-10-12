package com.ecommerce.billingservice.messaging;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.ecommerce.billingservice.model.BillDto;
import com.ecommerce.billingservice.model.OrderDto;
import com.ecommerce.billingservice.service.BillingService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class TopicListener {

	private final TopicProducer topicProducer;

	@Value("${consumer.config.topic.name")
	private String topicName;

	@Autowired
	BillingService billingService;

	@KafkaListener(topics = "${consumer.config.topic.name}", groupId = "${consumer.config.group-id}")
	public void consume(ConsumerRecord<String, OrderDto> payload) {
		log.info("Topic : {}", topicName);
		log.info("Key : {}", payload.key());
		log.info("Headers : {}", payload.headers());
		log.info("Partion : {}", payload.partition());
		log.info("Order : {}", payload.value());

		OrderDto order = payload.value();
		BillDto bill = new BillDto("2345", order.getId(), order.getTotal(), "NOT PAID");

		BillDto savedBill = billingService.createBill(bill);
		if (savedBill != null)
			topicProducer.send(savedBill);
	}

}