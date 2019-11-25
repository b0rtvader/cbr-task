package ru.bortnik.camel.processor;


import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.impl.DefaultExchange;
import org.apache.camel.impl.DefaultMessage;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.bortnik.camel.dto.SubscriberDto;

import static org.junit.Assert.*;

public class SetWorkProcessorTest {

    private final String headerName = "HeaderName";
    private final String headerValue = "HeaderValue";

    private Exchange exchange;
    private SubscriberDto subscriberDto;

    @Before
    public void setUp() {
        var context = new DefaultCamelContext();
        subscriberDto = new SubscriberDto();

        var message = new DefaultMessage(context);
        message.setBody(subscriberDto);
        message.setHeader(headerName, headerValue);

        exchange = new DefaultExchange(context);
        exchange.setMessage(message);
    }

    @Test
    public void process_withDto_shouldFillWork() {
        var setWorkProcessor = new SetWorkProcessor(headerName);
        setWorkProcessor.process(exchange);

        assertEquals(headerValue, subscriberDto.getWork());
    }

    @Test
    public void process_withDto_shouldFillBodyAndHeaders() {
        var setWorkProcessor = new SetWorkProcessor(headerName);
        setWorkProcessor.process(exchange);

        assertEquals(exchange.getIn().getBody(), exchange.getOut().getBody());
        assertEquals(exchange.getIn().getHeaders(), exchange.getOut().getHeaders());
    }

    @Test
    public void process_withoutDto_doNothing() {
        exchange.getIn().setBody(null);

        var setWorkProcessor = new SetWorkProcessor(headerName);
        setWorkProcessor.process(exchange);

        assertNull(subscriberDto.getWork());
    }
}
