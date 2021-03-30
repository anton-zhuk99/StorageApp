package com.example.app.storage.service;

import com.example.app.storage.dto.ConsumerDto;
import com.example.app.storage.model.Consumer;

import java.util.List;

public interface ConsumerService extends GenericService<Consumer, ConsumerDto, Long> {

    List<ConsumerDto> getConsumers();

}
