package org.softuni.workman.web.controllers;

import org.modelmapper.ModelMapper;
import org.softuni.workman.domain.models.binding.AddWorkmanBindingModel;
import org.softuni.workman.domain.models.service.CommentServiceModel;
import org.softuni.workman.domain.models.service.WorkmanServiceModel;
import org.softuni.workman.domain.models.view.CommentViewModel;
import org.softuni.workman.domain.models.view.WorkmanAllViewModel;
import org.softuni.workman.domain.models.view.WorkmanProfileViewModel;


import org.softuni.workman.service.CloudinaryService;
import org.softuni.workman.service.WorkmanService;
import org.softuni.workman.web.annotations.PageTitle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;


import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/workman")
public class WorkmanController extends BaseController {

    private final WorkmanService workmanService;
    private final CloudinaryService cloudinaryService;
    private final ModelMapper modelMapper;

    @Autowired
    public WorkmanController(WorkmanService workmanService, CloudinaryService cloudinaryService, ModelMapper modelMapper) {
        this.workmanService = workmanService;
        this.cloudinaryService = cloudinaryService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/add")
    @PreAuthorize("isAuthenticated()")
    @PageTitle("Add Workman")
    public ModelAndView addWorkman() {
        return super.view("workman/add-workman");
    }

    @PostMapping("/add")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView addWorkmanConfirm(@ModelAttribute AddWorkmanBindingModel model) throws IOException {
        WorkmanServiceModel workmanServiceModel = this.modelMapper.map(model, WorkmanServiceModel.class);

        workmanServiceModel.setImageUrl(cloudinaryService.uploadImage(model.getImageUrl()));

        this.workmanService.createWorkman(workmanServiceModel);

        return super.redirect("/workman/list");
    }
    @GetMapping("/list")
    @PreAuthorize("isAuthenticated()")
    @PageTitle("All Workman")
    public ModelAndView listWorkman(ModelAndView modelAndView) {

        List<WorkmanAllViewModel> workman =
        this.workmanService.findAllWorkman()
                .stream()
                .map(w -> {
                    WorkmanAllViewModel workmanW = this.modelMapper.map(w, WorkmanAllViewModel.class);
                            return workmanW;
                })
                .collect(Collectors.toList());

        modelAndView.addObject("workman", workman);

        return super.view("workman/list-workman", modelAndView);
    }
    @GetMapping("/all")
    @PreAuthorize("isAuthenticated()")
    @PageTitle("All Workman")
    public ModelAndView allWorkman(ModelAndView modelAndView) {

        List<WorkmanAllViewModel> workman =
                this.workmanService.findAllWorkman()
                        .stream()
                        .map(w -> {
                            WorkmanAllViewModel workmanW = this.modelMapper.map(w, WorkmanAllViewModel.class);
                            return workmanW;
                        })
                        .collect(Collectors.toList());

        modelAndView.addObject("workman", workman);

        return super.view("workman/all-workman", modelAndView);
    }

    @GetMapping("/details/{id}")
    @PreAuthorize("isAuthenticated()")
    @PageTitle("Workman Details")
    public ModelAndView detailsWorkman(@PathVariable String id, ModelAndView modelAndView) {
        WorkmanProfileViewModel model = this.modelMapper.map(this.workmanService.findWorkmanById(id), WorkmanProfileViewModel.class);

        modelAndView.addObject("workman", model);

        return super.view("workman/details", modelAndView);
    }

    @GetMapping("/fetch")

    @ResponseBody
    public List<WorkmanAllViewModel> fetchWorkman() {
        List<WorkmanAllViewModel> workman = this.workmanService.findAllWorkman()
                .stream()
                .map(c -> this.modelMapper.map(c, WorkmanAllViewModel.class))
                .collect(Collectors.toList());

        return workman;
    }


    @GetMapping("/edit/{id}")
    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    @PageTitle("Edit Workman")
    public ModelAndView editWorkman(@PathVariable String id, ModelAndView modelAndView) {
        WorkmanServiceModel workmanServiceModel = this.workmanService.findWorkmanById(id);
        AddWorkmanBindingModel model = this.modelMapper.map(workmanServiceModel, AddWorkmanBindingModel.class);


        modelAndView.addObject("workman", model);
        modelAndView.addObject("workmanId", id);

        return super.view("workman/edit-workman", modelAndView);
    }

    @PostMapping("/edit/{id}")
    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    public ModelAndView editWorkmanConfirm(@PathVariable String id, @ModelAttribute AddWorkmanBindingModel model) {
        this.workmanService.editWorkman(id, this.modelMapper.map(model, WorkmanServiceModel.class));

        return super.redirect("/workman/details/" + id);
    }

    @GetMapping("/delete/{id}")
    @PreAuthorize("hasRole('ROLE_MODERATOR')")

    public ModelAndView deleteWorkman(@PathVariable String id, ModelAndView modelAndView) {
        WorkmanServiceModel workmanServiceModel = this.workmanService.findWorkmanById(id);
        AddWorkmanBindingModel model = this.modelMapper.map(workmanServiceModel, AddWorkmanBindingModel.class);


        modelAndView.addObject("workman", model);
        modelAndView.addObject("WorkmanId", id);

        return super.view("workman/delete-workman", modelAndView);
    }

    @PostMapping("/delete/{id}")
    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    public ModelAndView deleteWorkmanConfirm(@PathVariable String id) {
        this.workmanService.deleteWorkman(id);

        return super.redirect("/workman/list");
    }
//
//    @GetMapping("/fetch/{comments}")
//    @ResponseBody
//    public List<WorkmanAllViewModel> fetchByWorkman(@PathVariable String comments) {
//        if(comments.equals("all")) {
//            return this.workmanService.findAllWorkman()
//                    .stream()
//                    .map(workman -> this.modelMapper.map(workman, WorkmanAllViewModel.class))
//                    .collect(Collectors.toList());
//        }
//
//        return this.workmanService.fin(comments)
//                .stream()
//                .map(workman -> this.modelMapper.map(workman, ProductAllViewModel.class))
//                .collect(Collectors.toList());
//    }
//
//    @ExceptionHandler({WorkmanNotFoundException.class})
//    public ModelAndView handleProductNotFound(WorkmanNotFoundException e) {
//        ModelAndView modelAndView = new ModelAndView("error");
//        modelAndView.addObject("message", e.getMessage());
//        modelAndView.addObject("statusCode", e.getStatusCode());
//
//        return modelAndView;
//    }
//


//    @ExceptionHandler({WorkmanNameAlreadyExistsException.class})
//    public ModelAndView handleProductNameALreadyExist(WorkmanNameAlreadyExistsException e) {
//        ModelAndView modelAndView = new ModelAndView("error");
//        modelAndView.addObject("message", e.getMessage());
//        modelAndView.addObject("statusCode", e.getStatusCode());
//
//        return modelAndView;
//    }
}
