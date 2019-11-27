package ru.bortnik.project.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.bortnik.project.service.SubscriberRepositoryService;

@Controller
@RequestMapping("/phonebook")
@RequiredArgsConstructor
@Slf4j
public class PhonebookController {

    private final SubscriberRepositoryService subscriberService;

    @GetMapping
    public String getAllSubscribers(Model model) {
        model.addAttribute("subscribers", subscriberService.getAll());
        return "subscribers";
    }
}
