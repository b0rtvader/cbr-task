package ru.bortnik.first.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import ru.bortnik.first.dto.SubscriberDto;
import ru.bortnik.first.entity.Subscriber;
import ru.bortnik.first.repository.SubscriberRepository;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class SubscriberServiceTest {

    @Autowired
    private SubscriberService subscriberService;

    @MockBean
    private SubscriberRepository subscriberRepository;

    private List<Subscriber> entities = Arrays.asList(
            new Subscriber("Ivanov",
                    "Ivan",
                    "111-222-33-44",
                    "111-555-66-44",
                    "test@mail.ru",
                    LocalDate.of(1995, 4, 6),
                    "Intel"),
            new Subscriber("Petrov",
                    "Petr",
                    "111-888-33-44",
                    "111-666-66-99",
                    "test2@mail.ru",
                    LocalDate.of(1996, 4, 6),
                    "Microsoft")
    );

    private List<SubscriberDto> dtos = Arrays.asList(
            new SubscriberDto("Ivanov",
                    "Ivan",
                    "111-222-33-44",
                    "111-555-66-44",
                    "test@mail.ru",
                    LocalDate.of(1995, 4, 6),
                    "Intel"),
            new SubscriberDto("Petrov",
                    "Petr",
                    "111-888-33-44",
                    "111-666-66-99",
                    "test2@mail.ru",
                    LocalDate.of(1996, 4, 6),
                    "Microsoft")
    );

    @Before
    public void setUp() {
        when(subscriberRepository.findAll()).thenReturn(entities);
    }

    @Test
    public void getAll_should_returnExpected() {
        List<SubscriberDto> returnedFromService = subscriberService.getAll();

        assertEquals(dtos, returnedFromService);
    }


}
