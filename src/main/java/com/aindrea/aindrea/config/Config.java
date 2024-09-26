package com.aindrea.aindrea.config;

import com.aindrea.aindrea.Aindrea;
import com.aindrea.aindrea.I18n;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;

public class Config {
    public static final Path WorkPath = Aindrea.AindreaWorkPath;
    public static final ArrayList<Object> ConfigurePool = new ArrayList<>();

    public static boolean LoadDefaultConfiguration() {
        ConfigurePool.add(new HashMap<String, Object>() {{
            put("name", "LanguageSettings");
            put("path", Paths.get(String.valueOf(WorkPath), I18n.bundle.getString("aindrea.work.path.config.name"), "lang.cfg"));
            put("control", new ConfigureControl(Paths.get(String.valueOf(WorkPath), I18n.bundle.getString("aindrea.work.path.config.name"), "lang.cfg")));
            put("version", ConfigureControl.ControlVersion);
            put("enabled", true);
        }});
        loadAllConfigurations();
        return true;
    }
    public static void loadAllConfigurations() {
        for (Object config : ConfigurePool) {
            if (config instanceof HashMap) {
                HashMap<String, Object> configMap = (HashMap<String, Object>) config;
                ConfigureControl control = (ConfigureControl) configMap.get("control");
                control.toString();
            }
        }
    }
}
