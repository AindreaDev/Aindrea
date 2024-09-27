package com.aindrea.aindrea.config;

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

    public void ConfigState(String DataStr) {
        if (!isJSONString(DataStr)) {
            ParseState = false;
            return;
        }

    }
}
