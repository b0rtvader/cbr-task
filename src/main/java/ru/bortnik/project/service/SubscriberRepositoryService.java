package ru.bortnik.project.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import ru.bortnik.project.dto.SubscriberDto;
import ru.bortnik.project.entity.Subscriber;
import ru.bortnik.project.repository.SubscriberRepository;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class SubscriberRepositoryService {

    private final SubscriberRepository subscriberRepository;
    private final ModelMapper modelMapper;

    public SubscriberDto save(SubscriberDto subscriberDto) {
        var subscriber = toEntity(subscriberDto);
        return toDto(subscriberRepository.save(subscriber));
    }

    public List<SubscriberDto> getAll() {
        return subscriberRepository.findAll()
                .stream()
                .map(this::toDto)
                .collect(toList());
    }

    private SubscriberDto toDto(Subscriber entity) {
        return modelMapper.map(entity, SubscriberDto.class);
    }

    private Subscriber toEntity(SubscriberDto entity) {
        return modelMapper.map(entity, Subscriber.class);
    }
}
