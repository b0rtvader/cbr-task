package ru.bortnik.camel.processor;

import lombok.RequiredArgsConstructor;
import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import ru.bortnik.camel.dto.SubscriberDto;

@RequiredArgsConstructor
public class SetWorkProcessor implements Processor {

    private final String headerName;

    @Override
    public void process(Exchange exchange) {
        Message in = exchange.getIn();
        SubscriberDto subscriberDto = in.getBody(SubscriberDto.class);
        if (subscriberDto == null) {
            return;
        }

        subscriberDto.setWork(in.getHeader(headerName, String.class));

        exchange.getOut().setBody(subscriberDto);
        exchange.getOut().setHeaders(in.getHeaders());
    }
}
