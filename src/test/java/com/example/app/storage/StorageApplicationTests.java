package com.example.app.storage;

import com.example.app.storage.dto.OrderDto;
import com.example.app.storage.dto.OrderEntryDto;
import com.example.app.storage.model.District;
import com.example.app.storage.rest.ConsumerController;
import com.example.app.storage.service.OrderService;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import java.sql.Date;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantLock;

@RunWith(MockitoJUnitRunner.class)
@WebAppConfiguration
class StorageApplicationTests {

	private static final Logger log = LoggerFactory.getLogger(StorageApplicationTests.class);
	@Mock
	private OrderService orderService;
	private ReentrantLock lock;

	@Before
	public void checkIfAreWaiting() {
		lock = new ReentrantLock();
	}

	@Test
	void contextLoads() {

		ExecutorService executor = Executors.newFixedThreadPool(4);

		executor.submit(this::publishOrderTask);

		executor.shutdown();

	}

	private Runnable publishOrderTask() {
		return () -> {
			OrderDto orderDto = new OrderDto();
			Random rnd = new Random(System.currentTimeMillis());
			orderDto.setDescription("Test generated order");
			orderDto.setConsumerId(5L + rnd.nextInt(4));
			orderDto.setDate(String.format("%d-%d-%d",
					2010 + rnd.nextInt(11),
					1 + rnd.nextInt(11),
					1 + rnd.nextInt(27)));
			List<OrderEntryDto> dtoList = new ArrayList<>();
			int positions = 1 + rnd.nextInt(9);
			for (int i = 0; i < positions; i++) {
				dtoList.add(new OrderEntryDto(1L + rnd.nextInt(12), 1 + rnd.nextInt(49)));
			}
			orderDto.setEntries(dtoList);
			lock.lock();
			try {
				orderService.add(orderDto);
			} catch (Exception e) {
				log.info("EXCEPTION OCCURRED! MESSAGE: " + e.getMessage());
			} finally {
				lock.unlock();
			}
		};
	}

}
