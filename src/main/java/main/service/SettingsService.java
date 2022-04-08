package main.service;

import main.api.response.LogoutResponse;
import main.api.response.SettingsResponse;
import main.model.GlobalSetting;
import main.repository.GlobalSettingsRepository;
import main.repository.UsersRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SettingsService {

    private final GlobalSettingsRepository globalSettingsRepository;
    private final UsersRepository usersRepository;

    public SettingsService(GlobalSettingsRepository globalSettingsRepository, UsersRepository usersRepository) {
        this.globalSettingsRepository = globalSettingsRepository;
        this.usersRepository = usersRepository;
    }

    public SettingsResponse getGlobalSettings() {

        SettingsResponse settingsResponse = new SettingsResponse();

        settingsResponse.setMultiserMode(valueById(1));
        settingsResponse.setPostPremoderation(valueById(2));
        settingsResponse.setStatisticIsPublic(valueById(3));


        return settingsResponse;
    }

    private boolean valueById(int id) {
        String value = null;
        Iterable<GlobalSetting> globalSettingsIterable = globalSettingsRepository.findAllById(id);
        List<GlobalSetting> globalSettings = new ArrayList<>();
        for (GlobalSetting globalSetting : globalSettingsIterable) {
            globalSettings.add(globalSetting);
            value = globalSetting.getValue();
        }

        return "YES".equals(value);
    }

    public LogoutResponse changeSettings(String authCoocie, boolean multiUserMode, boolean postPremoderation, boolean statisticsIsPublic) {
        LogoutResponse logoutResponse = new LogoutResponse();

        int id = LoginService.sessions.get(authCoocie);
        if (usersRepository.findUserInfo(id).getIs_moderator() == 1) {
            if (multiUserMode==true) {
                globalSettingsRepository.newSettings("YES", 1);
            } else {
                globalSettingsRepository.newSettings("NO", 1);
            }

            if (postPremoderation==true) {
                globalSettingsRepository.newSettings("YES", 2);
            } else {
                globalSettingsRepository.newSettings("NO", 2);
            }

            if (statisticsIsPublic==true) {
                globalSettingsRepository.newSettings("YES", 3);
            } else {
                globalSettingsRepository.newSettings("NO", 3);
            }

            logoutResponse.setResult(true);
        } else {
            logoutResponse.setResult(false);
        }

        return logoutResponse;
    }

}
