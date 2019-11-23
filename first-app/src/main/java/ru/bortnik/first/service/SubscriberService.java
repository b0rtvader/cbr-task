package ru.bortnik.first.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import ru.bortnik.first.dto.SubscriberDto;
import ru.bortnik.first.dto.WorkPlaceDto;
import ru.bortnik.first.entity.Subscriber;
import ru.bortnik.first.entity.WorkPlace;
import ru.bortnik.first.repository.SubscriberRepository;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class SubscriberService {

    private final SubscriberRepository repository;
    private final ModelMapper modelMapper;

    public List<SubscriberDto> getAll() {
        return repository.findAll()
                .stream()
                .map(this::toDto)
                .collect(toList());
    }

    private SubscriberDto toDto(Subscriber entity) {
        return modelMapper.map(entity, SubscriberDto.class);
    }
}
