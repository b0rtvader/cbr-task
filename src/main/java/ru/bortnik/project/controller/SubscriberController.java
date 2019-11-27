package ru.bortnik.project.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.bortnik.project.dto.SubscriberDto;
import ru.bortnik.project.service.SubscriberStorageService;

import javax.validation.Valid;

@Controller
@RequestMapping("/subscriber")
@RequiredArgsConstructor
@Slf4j
public class SubscriberController {

    private static final String DTO_NAME = "subscriberDto";
    private static final String PAGE_NAME = "create-subscriber";

    private final SubscriberStorageService subscriberService;

    @GetMapping
    public String index(SubscriberDto subscriberDto) {
        return PAGE_NAME;
    }

    @PostMapping(params = "action=clear")
    public String clear(Model model) {
        subscriberService.clear();
        model.addAttribute(DTO_NAME, new SubscriberDto());
        return PAGE_NAME;
    }

    @PostMapping(params = "action=add")
    public String add(@Valid SubscriberDto subscriberDto, BindingResult result, Model model) {
        if (result.hasErrors()) {
            log.warn("Input data has errors");
            return PAGE_NAME;
        }

        subscriberService.add(subscriberDto);
        return PAGE_NAME;
    }

    @PostMapping(params = "action=send")
    public String send(Model model) {
        model.addAttribute(DTO_NAME, new SubscriberDto());
        subscriberService.send();
        return PAGE_NAME;
    }
}
