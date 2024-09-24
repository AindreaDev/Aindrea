package com.aindrea;

import com.aindrea.aindrea.Aindrea;
import com.aindrea.aindrea.utils.BasicWorkPath;
import com.aindrea.aindrea.utils.LanguageConfig;

import java.nio.file.Path;

/**
 * author: xingchen
 * date: 2024/9/22
 */

public class Main {
    public static void main(String[] args) {
        System.setProperty("file.encoding","UTF-8");
        LanguageConfig Language = new LanguageConfig();
        new BasicWorkPath(Path.of(Aindrea.AindreaWorkPath));
    }
}