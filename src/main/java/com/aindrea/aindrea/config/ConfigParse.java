package com.aindrea.aindrea.config;

import com.aindrea.aindrea.I18n;
import com.alibaba.fastjson2.JSONObject;
import org.thymeleaf.util.StringUtils;

public class ConfigParse {
    public static String ConfigVersion = null;
    public static boolean ParseState = false;

    public static boolean isJSONString(String content) {
        if (StringUtils.isEmpty(content)) {
            return false;
        } else if (!content.startsWith("{") || !content.endsWith("}")) {
            return false;
        }
        try {
            JSONObject.parse(content);
            return true;
        } catch (Exception error) {
            return false;
        }
    }

    public ConfigParse(String DataStr) {
        if (!isJSONString(DataStr)) {
            ParseState = false;
            return;
        }
        JSONObject ConfigJson = new JSONObject().parseObject(DataStr);
        var cfj = ConfigJson.getString(I18n.bundle.getString("aindrea.config.json.control_version"));
        System.out.println(ConfigJson);
    }

    public static boolean FormatCheck(JSONObject ConfigJson) {
        if (ConfigJson.getString(I18n.bundle.getString("aindrea.config.json.control_version")) == ConfigureControl.ControlVersion) {
            ParseState = true;
            ConfigJson.getString(I18n.bundle.getString(""));
        } else if (ConfigJson.getString(I18n.bundle.getString("aindrea.config.json.control_version")) == null) {
            ParseState = false;
        }
        return false;
    }
}
