package org.softuni.workman.web.controllers;

import org.modelmapper.ModelMapper;
import org.softuni.workman.domain.models.binding.AddCommentBindingModel;
import org.softuni.workman.domain.models.service.CommentServiceModel;
import org.softuni.workman.domain.models.view.CommentAllViewModel;
import org.softuni.workman.domain.models.view.CommentViewModel;
import org.softuni.workman.domain.models.view.WorkmanAllViewModel;
import org.softuni.workman.service.CloudinaryService;
import org.softuni.workman.service.CommentService;
import org.softuni.workman.service.UserService;
import org.softuni.workman.service.WorkmanService;
import org.softuni.workman.web.annotations.PageTitle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/comments")
public class CommentController extends BaseController {

    private final CommentService commentService;
    private final ModelMapper modelMapper;
    private final CloudinaryService cloudinaryService;
    private final WorkmanService workmanService;
    private final UserService userService;

    @Autowired
    public CommentController(CommentService commentService, ModelMapper modelMapper, CloudinaryService cloudinaryService, WorkmanService workmanService, UserService userService) {
        this.commentService = commentService;
        this.modelMapper = modelMapper;
        this.cloudinaryService = cloudinaryService;
        this.workmanService = workmanService;
        this.userService = userService;
    }
    @GetMapping("/create")
    @PreAuthorize("isAuthenticated()")
    @PageTitle("Create Comment")
    public ModelAndView createComment (){

        return super.view("comments/create-comment");
    }

    @PostMapping("/create")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView createConfirm (@ModelAttribute AddCommentBindingModel model, Principal principal) throws IOException {

            CommentServiceModel commentServiceModel = this.modelMapper
                    .map(model, CommentServiceModel.class);

            commentServiceModel.setWorkman(this.workmanService.findWorkmanById(model.getWorkman()));


            commentServiceModel.setUser( this.userService.findUserByUserName(principal.getName()));
            commentServiceModel.setImageUrl(this.cloudinaryService.uploadImage(model.getImageUrl()));
//            commentServiceModel.setWorkman(this.workmanService.findAllWorkman()
//            .stream()
//            .filter(w->model.getWorkman().contains(w.getId()))
//            .collect(Collectors.toList()));

            this.commentService.createComment(commentServiceModel);

            return super.redirect("/comments/all" );

//        modelAndView.addObject("bindingModel", bindingModel);

//        return super.view("comments/create-comment", modelAndView);
    }

    @GetMapping("/all")
    @PreAuthorize("isAuthenticated()")
    @PageTitle("All Comments")
    public ModelAndView showAll(ModelAndView modelAndView){
        List<CommentServiceModel> comments = this.commentService.findAllComments()
                .stream()
                .map(c -> this.modelMapper.map(c, CommentServiceModel.class))
                .collect(Collectors.toList());

        modelAndView.addObject("comments", comments);
        return super.view("comments/all-comments", modelAndView);
    }

//    @GetMapping("/details/{id}")
//    @PreAuthorize("isAuthenticated()")
//    @PageTitle("Comment Details")
//    public ModelAndView detailsComment(@PathVariable String id, ModelAndView modelAndView) {
//        modelAndView.addObject("comment", this.modelMapper.map(this.commentService.findCommentById(id), CommentViewModel.class));
//
//        return super.view("comments/details-comments", modelAndView);
//    }


    @GetMapping("/my")
    @PreAuthorize("isAuthenticated()")
    @PageTitle("My Comments")
    public ModelAndView getMyComments(ModelAndView modelAndView, Principal principal) {
        List<CommentViewModel> commentViewModels = commentService.findAllByUser(principal.getName())
                .stream()
                .map(c -> modelMapper.map(c, CommentViewModel.class))
                .collect(Collectors.toList());

        modelAndView.addObject("comments", commentViewModels);

        return view("comments/all-comments", modelAndView);
    }

    @GetMapping("/details/{id}")
    @PreAuthorize("isAuthenticated()")
    @PageTitle("Comments Details")
    public ModelAndView CommentsDetails(@PathVariable String id, ModelAndView modelAndView) {
        CommentViewModel commentViewModel = this.modelMapper.map(this.commentService.findCommentById(id), CommentViewModel.class);
        modelAndView.addObject("comments", commentViewModel);

        return super.view("comments/details-comment", modelAndView);
    }
    @GetMapping("/fetch")
    @ResponseBody
    public List<CommentAllViewModel> fetchComments() {
        List<CommentAllViewModel> comments = this.commentService.findAllComments()
                .stream()
                .map(c -> this.modelMapper.map(c, CommentAllViewModel.class))
                .collect(Collectors.toList());

        return comments;
    }
    @GetMapping("/list")
    @PreAuthorize("isAuthenticated()")
    @PageTitle("Comments List")
    public ModelAndView commentsList(ModelAndView modelAndView) {

        List<CommentAllViewModel> commentAllViewModels =
                this.commentService.findAllComments()
                        .stream()
                        .map(w -> {
                            CommentAllViewModel comment = this.modelMapper.map(w, CommentAllViewModel.class);
                            return comment;
                        })
                        .collect(Collectors.toList());

        modelAndView.addObject("comments", commentAllViewModels);

        return super.view("comments/list-comments", modelAndView);
    }
    @GetMapping("/edit/{id}")
    @PreAuthorize("isAuthenticated()")
    @PageTitle("Edit Comments")
    public ModelAndView editComment(@PathVariable String id, ModelAndView modelAndView) throws IOException {
        CommentServiceModel commentServiceModel = this.commentService.findCommentById(id);

        AddCommentBindingModel model = this.modelMapper.map(commentServiceModel, AddCommentBindingModel.class);

//        model.setImageUrl(this.cloudinaryService.uploadImage(commentServiceModel.getImageUrl()));


        modelAndView.addObject("comment", model);
        modelAndView.addObject("commentId", id);

        return super.view("comments/edit-comment", modelAndView);
    }

    @PostMapping("/edit/{id}")
    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    public ModelAndView editCommentConfirm(@PathVariable String id, @ModelAttribute AddCommentBindingModel model) {
        this.commentService.editComment(id, this.modelMapper.map(model, CommentServiceModel.class));

        return super.redirect("/comments/details/" + id);
    }

    @GetMapping("/delete/{id}")
    @PreAuthorize("isAuthenticated()")

    public ModelAndView deleteComment(@PathVariable String id, ModelAndView modelAndView) {
        CommentServiceModel commentServiceModel = this.commentService.findCommentById(id);
        AddCommentBindingModel model = this.modelMapper.map(commentServiceModel, AddCommentBindingModel.class);


        modelAndView.addObject("comment", model);
        modelAndView.addObject("CommentId", id);

        return super.view("comments/delete-comment", modelAndView);
    }

    @PostMapping("/delete/{id}")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView deleteCommentConfirm(@PathVariable String id) {
        this.commentService.deleteComment(id);

        return super.redirect("/comments/list");
    }
    @GetMapping("/workman/{id}")
    @PreAuthorize("isAuthenticated()")
    @PageTitle("Workman Comments")
    public ModelAndView getWorkmanComments(ModelAndView modelAndView,@PathVariable String id) {
        List<CommentViewModel> commentViewModels = commentService.findAllByWorkman(id)
                .stream()
                .map(c -> modelMapper.map(c, CommentViewModel.class))
                .collect(Collectors.toList());

        modelAndView.addObject("comments", commentViewModels);

        return view("comments/workman-comments", modelAndView);
    }


}
