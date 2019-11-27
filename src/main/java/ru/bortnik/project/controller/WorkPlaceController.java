package ru.bortnik.project.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.bortnik.project.dto.WorkPlaceDto;
import ru.bortnik.project.service.WorkPlaceService;

@Controller
@RequestMapping("/workplace")
@RequiredArgsConstructor
@Slf4j
public class WorkPlaceController {

    private final WorkPlaceService workPlaceService;

    @GetMapping
    public String showCreateWorkplaceForm(WorkPlaceDto workPlaceDto) {
        return "create-workplace";
    }

    @PutMapping
    public String putWorkplace(WorkPlaceDto workPlaceDto, Model model) {
        WorkPlaceDto saved = workPlaceService.save(workPlaceDto);
        model.addAttribute("workPlaceDto", saved);

        return "index";
    }

    @GetMapping("/all")
    public String getAllWorkplaces(Model model) {
        model.addAttribute("workplaceList", workPlaceService.getAll());
        return "workplaces";
    }
}
