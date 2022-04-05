package main.controller;

import main.api.request.ChangeProfileRequest;
import main.api.response.*;
import main.service.*;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping("/api")
public class ApiGeneralController {

    private final SettingsService settingsService;
    private final AuthCheckService authCheckService;
    private final StatistcService statistcService;
    private final ProfileService profileService;
    InitResponseDto initResponseDto = new InitResponseDto();

    private final TagService tagService;

    public ApiGeneralController(SettingsService settingsService, AuthCheckService authCheckService, StatistcService statistcService, ProfileService profileService, TagService tagService) {
        this.settingsService = settingsService;
        this.authCheckService = authCheckService;
        this.statistcService = statistcService;
        this.profileService = profileService;
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

    @GetMapping("/statistics/my")
    private StatisticResponse statisticMy(@CookieValue(value = "auth") String authCoocie) {
        return statistcService.myStatistic(authCoocie);
    }

    @GetMapping("/statistics/all")
    private StatisticResponse statisticAll() {
        return statistcService.allStatistic();
    }

    //@PostMapping("/profile/my", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    @RequestMapping(path = "/profile/my", method = POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    private ChangeProfileResponse profileMy(@CookieValue(value = "auth") String authCoocie,
                                            @RequestBody ChangeProfileRequest changeProfileRequest) {
        return profileService.changeProfile(authCoocie, changeProfileRequest.getPhoto(), changeProfileRequest.getEmail(),
                changeProfileRequest.getName(), changeProfileRequest.getPassword(),
                changeProfileRequest.getRemovePhoto());

    }

}
