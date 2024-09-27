package com.aindrea;

import com.aindrea.aindrea.Aindrea;
import com.aindrea.aindrea.config.Config;
import com.aindrea.aindrea.utils.BasicWorkPath;
import com.aindrea.aindrea.utils.LanguageConfig;

/**
 * author: xingchen
 * date: 2024/9/22
 */

public class Main {
    public static void main(String[] args) {
        System.setProperty("file.encoding","UTF-8");
        LanguageConfig Language = new LanguageConfig();
        Config.RegisterDefaultConfiguration();
        new BasicWorkPath(Aindrea.AindreaWorkPath);
    }
}