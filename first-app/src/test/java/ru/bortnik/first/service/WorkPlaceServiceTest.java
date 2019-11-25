package ru.bortnik.first.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ru.bortnik.first.dto.WorkPlaceDto;
import ru.bortnik.first.repository.WorkPlaceRepository;

import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class WorkPlaceServiceTest {

    @Autowired
    private WorkPlaceRepository workPlaceRepository;

    @Autowired
    private WorkPlaceService workPlaceService;

    @Before
    public void setUp() {
        workPlaceRepository.deleteAll();
    }

    @Test
    public void save_dto_returnTheSame() {
        var dto = new WorkPlaceDto("Ivanov",
                "Ivan",
                "Microsoft",
                "USA");

        var saved = workPlaceService.save(dto);
        assertEquals(dto, saved);
    }

    @Test
    public void getAll_afterSave_returnExpected() {
        var dto = new WorkPlaceDto("Ivanov",
                "Ivan",
                "Microsoft",
                "USA");

        workPlaceService.save(dto);
        var workplaces = workPlaceService.getAll();
        assertEquals(Collections.singletonList(dto), workplaces);
    }
}
