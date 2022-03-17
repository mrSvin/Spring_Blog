package main.controller;

import main.api.response.*;
import main.service.AuthCheckService;
import main.service.PostService;
import main.service.SettingsService;
import main.service.TagService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ApiGeneralController {

    private final SettingsService settingsService;
    InitResponseDto initResponseDto = new InitResponseDto();
    private final AuthCheckService authCheckService;

    private final TagService tagService;

    public ApiGeneralController(SettingsService settingsService, AuthCheckService authCheckService, TagService tagService) {
        this.settingsService = settingsService;
        this.authCheckService = authCheckService;
        this.tagService = tagService;
    }

    @GetMapping("/settings")
    private SettingsResponse Settings() {

        return settingsService.getGlobalSettings();
    }

    @GetMapping("/init")
    private InitResponseDto Init() {
        return initResponseDto;
    }

    @GetMapping("/auth/check")
    private AuthCheckResponse Check() {
        return authCheckService.getAuthCheck();
    }

    @GetMapping("/tag")
    private TagResponse Tag(
            @RequestParam(value = "query", required = false) String query) {
        if (query == null) {
            return tagService.getTagsAll();
        } else {
            return tagService.getTags(query);
        }

    }

}
