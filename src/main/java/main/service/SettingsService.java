package main.service;

import main.api.response.SettingsResponse;
import main.model.GlobalSetting;
import main.repository.GlobalSettingsRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SettingsService {

    private final GlobalSettingsRepository global_settingsRepository;

    public SettingsService(GlobalSettingsRepository global_settingsRepository) {
        this.global_settingsRepository = global_settingsRepository;
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
        Iterable<GlobalSetting> global_settingsIterable = global_settingsRepository.findAllById(id);
        List<GlobalSetting> global_settings = new ArrayList<>();
        for (GlobalSetting global_setting : global_settingsIterable) {
            global_settings.add(global_setting);
            value = global_setting.getValue();
        }

        if (value.equals("YES")) {
            return true;
        } else {
            return false;
        }
    }


//    public List<Global_settings> getGlobal_settings(int id) {
//        Iterable<Global_settings> global_settingsIterable = global_settingsRepository.findAllById(id);
//
//        List<Global_settings> global_settings = new ArrayList<>();
//        for (Global_settings global_setting : global_settingsIterable) {
//            global_settings.add(global_setting);
//        }
//        return global_settings;
//    }
}
