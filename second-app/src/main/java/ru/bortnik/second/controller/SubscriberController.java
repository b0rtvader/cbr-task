package ru.bortnik.second.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.bortnik.second.dto.SubscriberDto;
import ru.bortnik.second.service.SubscriberService;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
@Slf4j
public class SubscriberController {

    private static final String DTO_NAME = "subscriberDto";
    private static final String PAGE_NAME = "main-form";

    private final SubscriberService subscriberService;

    @GetMapping
    public String index(SubscriberDto subscriberDto) {
        log.debug("index");
        return PAGE_NAME;
    }

    @PostMapping(params = "action=clear")
    public String clear(Model model) {
        log.debug("clear");

        subscriberService.clear();
        model.addAttribute(DTO_NAME, new SubscriberDto());
        return PAGE_NAME;
    }

    @PostMapping(params = "action=add")
    public String add(@Valid SubscriberDto subscriberDto, BindingResult result, Model model) {
        log.debug("add");

        if (result.hasErrors()) {
            log.warn("Input data has errors");
            return PAGE_NAME;
        }

        subscriberService.add(subscriberDto);
        return PAGE_NAME;
    }

    @PostMapping(params = "action=send")
    public String send(Model model) {
        log.debug("send");

        model.addAttribute(DTO_NAME, new SubscriberDto());
        subscriberService.send();
        return PAGE_NAME;
    }
}
