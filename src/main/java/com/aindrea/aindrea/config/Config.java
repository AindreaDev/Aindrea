package com.aindrea.aindrea.config;

import com.aindrea.aindrea.Aindrea;
import com.aindrea.aindrea.I18n;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;

public class Config {
    public static final Path WorkPath = Aindrea.AindreaWorkPath;
    private static final ArrayList<Object> ConfigurePool = new ArrayList<>();

    public static boolean RegisterConfiguration(String name, Path path, HashMap default_type, HashMap default_data) {
        ConfigurePool.add(new HashMap<String, Object>() {{
            put("name", name);
            put("path", Paths.get(String.valueOf(WorkPath), String.valueOf(path)));
            put("control", new ConfigureControl(Paths.get(String.valueOf(WorkPath), String.valueOf(path))));
            put("default_type",default_type);
            put("default_data",default_data);
            put("version", ConfigureControl.ControlVersion);
            put("enabled", true);
        }});
        return false;
    }

    public static boolean RegisterDefaultConfiguration() {
        ConfigurePool.add(new HashMap<String, Object>() {{
            put("name", "LanguageSettings");
            put("path", Paths.get(String.valueOf(WorkPath), I18n.bundle.getString("aindrea.work.path.config.name"), "lang.cfg"));
            put("control", new ConfigureControl(Paths.get(String.valueOf(WorkPath), I18n.bundle.getString("aindrea.work.path.config.name"), "lang.cfg")));
            put("default_type", new HashMap<String, Object>().put("language", String.class));
            put("default_data", new HashMap<String, String>().put("language", I18n.Chinese));
            put("version", ConfigureControl.ControlVersion);
            put("enabled", true);
        }});
        return true;
    }

    public static void LoadAllConfigurations() {
        for (Object config : ConfigurePool) {
            if (config instanceof HashMap) {
                HashMap<String, Object> configMap = (HashMap<String, Object>) config;
                ConfigureControl control = (ConfigureControl) configMap.get("control");
                control.ReadConfigurationFile();
            }
        }
    }
}
