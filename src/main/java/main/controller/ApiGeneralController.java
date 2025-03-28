package main.controller;

import main.api.request.ChangeProfileRequest;
import main.api.request.ModerationRequest;
import main.api.request.SettingsRequest;
import main.api.response.*;
import main.service.*;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping("/api")
public class ApiGeneralController {

    private final SettingsService settingsService;
    private final AuthCheckService authCheckService;
    private final StatistcService statistcService;
    private final ProfileService profileService;
    private final ModerationService moderationService;
    InitResponseDto initResponseDto = new InitResponseDto();

    private final TagService tagService;

    public ApiGeneralController(SettingsService settingsService, AuthCheckService authCheckService,
                                StatistcService statistcService, ProfileService profileService,
                                ModerationService moderationService, TagService tagService) {
        this.settingsService = settingsService;
        this.authCheckService = authCheckService;
        this.statistcService = statistcService;
        this.profileService = profileService;
        this.moderationService = moderationService;
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
    private AuthCheckResponse Check(@CookieValue(value = "auth") String authСookie) {
        return authCheckService.getAuthCheck(authСookie);
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

    @RequestMapping(path = "/profile/my", method = POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    private ChangeProfileResponse profileMy(@CookieValue(value = "auth") String authCoocie,
                                            @RequestPart(value = "photo") MultipartFile photo,
                                            @ModelAttribute ChangeProfileRequest changeProfileRequest) throws IOException {
        return profileService.changeProfile(authCoocie, photo, changeProfileRequest.getEmail(),
                changeProfileRequest.getName(), changeProfileRequest.getPassword(),
                changeProfileRequest.getRemovePhoto());
    }

    @RequestMapping(path = "/profile/my", method = POST)
    private ChangeProfileResponse profileMyNoPhoto(@CookieValue(value = "auth") String authCoocie,
                                            @RequestBody ChangeProfileRequest changeProfileRequest) throws IOException {
        return profileService.changeProfileNoPhoto(authCoocie, changeProfileRequest.getEmail(),
                changeProfileRequest.getName(), changeProfileRequest.getPassword(),
                changeProfileRequest.getRemovePhoto());
    }

    @PostMapping("/moderation")
    public AuthCheckResponse Restore(@CookieValue(value = "auth") String authCoocie,
                                     @RequestBody ModerationRequest moderationRequest) {
        return moderationService.decisionModeration(authCoocie, moderationRequest.getPostId(), moderationRequest.getDecision());
    }

    @PutMapping("/settings")
    public LogoutResponse settings(@CookieValue(value = "auth") String authCoocie,
                                   @RequestBody SettingsRequest settingsRequest) {
        return settingsService.changeSettings(authCoocie, settingsRequest.getMultiUserMode(), settingsRequest.getPostPremoderation()
                , settingsRequest.getStatisticsIsPublic());
    }

}
