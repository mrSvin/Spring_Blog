package main.service;

import main.api.response.SettingsResponse;
import main.model.GlobalSetting;
import main.repository.GlobalSettingsRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SettingsService {

    private final GlobalSettingsRepository globalSettingsRepository;

    public SettingsService(GlobalSettingsRepository globalSettingsRepository) {
        this.globalSettingsRepository = globalSettingsRepository;
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
