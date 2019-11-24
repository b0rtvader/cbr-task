package ru.bortnik.camel.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import ru.bortnik.camel.dto.SubscriberDto;
import ru.bortnik.camel.entity.Subscriber;
import ru.bortnik.camel.repository.SubscriberRepository;

@Service
@RequiredArgsConstructor
public class SubscriberService {

    private final SubscriberRepository subscriberRepository;
    private final ModelMapper modelMapper;

    public SubscriberDto save(SubscriberDto subscriberDto) {
        var subscriber = toEntity(subscriberDto);
        return toDto(subscriberRepository.save(subscriber));
    }

    private SubscriberDto toDto(Subscriber entity) {
        return modelMapper.map(entity, SubscriberDto.class);
    }

    private Subscriber toEntity(SubscriberDto entity) {
        return modelMapper.map(entity, Subscriber.class);
    }
}
