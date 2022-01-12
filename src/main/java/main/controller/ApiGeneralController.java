package main.controller;

import main.api.response.*;
import main.service.AuthCheckService;
import main.service.PostService;
import main.service.SettingsService;
import main.service.TagService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ApiGeneralController {

    private final SettingsService settingsService;
    private final InitResponse initResponse;
    private final AuthCheckService authCheckService;
    private final PostService postService;
    private final TagService tagService;

    public ApiGeneralController(SettingsService settingsService, InitResponse initResponse, AuthCheckService authCheckService, PostService postService, TagService tagService) {
        this.settingsService = settingsService;
        this.initResponse = initResponse;
        this.authCheckService = authCheckService;
        this.postService = postService;
        this.tagService = tagService;
    }

    @GetMapping("/settings")
    private SettingsResponse Settings() {
        return settingsService.getGlobalSettings();
    }

    @GetMapping("/init")
    private InitResponse Init() {
        return initResponse;
    }

    @GetMapping("/auth/check")
    private AuthCheckResponse Check() {
        return authCheckService.getAuthCheck();
    }

    @GetMapping("/post")
    private PostsResponse Post() {
        return postService.getPosts();
    }

    @GetMapping("/tag")
    private TagResponse Tag() {
        return tagService.getTags();
    }

}
