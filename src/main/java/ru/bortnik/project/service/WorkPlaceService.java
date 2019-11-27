package ru.bortnik.project.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import ru.bortnik.project.dto.WorkPlaceDto;
import ru.bortnik.project.entity.WorkPlace;
import ru.bortnik.project.repository.WorkPlaceRepository;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class WorkPlaceService {

    private final WorkPlaceRepository repository;
    private final ModelMapper modelMapper;

    public WorkPlaceDto save(WorkPlaceDto workPlaceDto) {
        var savedWorkPlace = repository.save(toEntity(workPlaceDto));
        return toDto(savedWorkPlace);
    }

    public List<WorkPlaceDto> getAll() {
        return repository.findAll()
                .stream()
                .map(this::toDto)
                .collect(toList());
    }

    private WorkPlace toEntity(WorkPlaceDto dto) {
        return modelMapper.map(dto, WorkPlace.class);
    }

    private WorkPlaceDto toDto(WorkPlace entity) {
        return modelMapper.map(entity, WorkPlaceDto.class);
    }
}
