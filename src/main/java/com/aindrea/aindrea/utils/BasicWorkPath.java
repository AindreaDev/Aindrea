package com.aindrea.aindrea.utils;

import com.aindrea.aindrea.I18n;
import com.aindrea.aindrea.logging.ColorPrint;

import java.io.File;
import java.nio.file.Path;

public class BasicWorkPath {
    public BasicWorkPath(Path workPath) {
        mkdirs(workPath.resolve(I18n.bundle.getString("aindrea.work.path.log.name")));
        mkdirs(workPath.resolve(I18n.bundle.getString("aindrea.work.path.database.name")));
        mkdirs(workPath.resolve(I18n.bundle.getString("aindrea.work.path.config.name")));
    }

    public static boolean mkdirs(Path makePath) {
        File file = makePath.toFile();
        try {
            if (!file.exists() && !file.isDirectory()) {
                file.mkdirs();
                return true;
            }
        } catch (Exception error) {
            ColorPrint.printErr(String.format(I18n.bundle.getString("aindrea.basic_work_path.error"), error.getMessage()));
            return false;
        }
        return true;
    }

    public static boolean exists(Path FileOrPath) {
        File file = FileOrPath.toFile();
        return file.exists();
    }
}
