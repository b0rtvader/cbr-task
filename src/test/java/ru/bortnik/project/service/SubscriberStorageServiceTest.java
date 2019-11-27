package ru.bortnik.project.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import ru.bortnik.project.dto.SubscriberDto;
import ru.bortnik.project.exception.SubscriberException;
import ru.bortnik.project.storage.SubscriberStorage;

import java.time.LocalDate;

import static org.mockito.Mockito.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class SubscriberStorageServiceTest {

    @MockBean
    private SubscriberStorage subscriberStorage;

    @Autowired
    private SubscriberStorageService subscriberService;

    private SubscriberDto dto = new SubscriberDto("Ivanov",
            "Ivan",
            "111-123-45-66",
            "111-234-56-78",
            "example@mail.ru",
            LocalDate.now(),
            null);

    @Before
    public void setUp() {
        doNothing().when(subscriberStorage).add(any(SubscriberDto.class));
        doNothing().when(subscriberStorage).clear();
        when(subscriberStorage.commit()).thenReturn(1);

        subscriberService.clear();
    }

    @Test(expected = SubscriberException.class)
    public void add_twoTimesInARow_throwAnException() {
        subscriberService.add(dto);
        subscriberService.add(dto);
    }

    @Test
    public void add_twoTimesWithSend_ok() {
        subscriberService.add(dto);
        subscriberService.send();
        subscriberService.add(dto);
    }

    @Test(expected = SubscriberException.class)
    public void send_beforeAdd_throwAnException() {
        subscriberService.send();
    }

    @Test
    public void send_afterAdd_ok() {
        subscriberService.add(dto);
        subscriberService.send();
    }
}
