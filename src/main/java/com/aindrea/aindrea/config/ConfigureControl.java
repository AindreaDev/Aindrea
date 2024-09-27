package com.aindrea.aindrea.config;

import com.aindrea.aindrea.I18n;
import com.aindrea.aindrea.logging.ColorPrint;
import com.aindrea.aindrea.utils.BasicWorkPath;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Scanner;

public class ConfigureControl {
    public static final String ControlVersion = "0.0.1";
    public static File ConfigureFile = null;
    public static boolean WorksNormally = false;
    public static Scanner ConfigureReader = null;
    public static int State = ConfigState.not_opened;
    private static File lockFile = null;

    public ConfigureControl(Path ConfigurePath) {
        ConfigureFile = ConfigurePath.toFile();
        lockFile = new File(ConfigureFile.getParent(), ConfigureFile.getName() + ".lock");
        if (!initializeAndReadConfigurationFile()) {
            State = ConfigState.abnormal;
        } else {
            State = ConfigState.normal;
            WorksNormally = true;
        }
    }

    private static boolean initializeAndReadConfigurationFile() {
        if (!BasicWorkPath.exists(ConfigureFile.toPath()) || ConfigureFile.isDirectory()) {
            try {
                if (ConfigureFile.createNewFile() && !BasicWorkPath.exists(ConfigureFile.toPath())) {
                    ColorPrint.printErr(String.format(I18n.bundle.getString("aindrea.config.control.create_file.failure"), ConfigureFile.getName()));
                    return false;
                }
            } catch (IOException error) {
                ColorPrint.printErr(String.format(I18n.bundle.getString("aindrea.config.control.create_file.error"), ConfigureFile.getName(), error.getMessage()));
                return false;
            }
        }

        try {
            if (!lockFile.createNewFile()) {
                ColorPrint.printErr(String.format(I18n.bundle.getString("aindrea.config.control.lock_file.exists"), lockFile.getName(), lockFile.toPath()));
                return false;
            }
        } catch (IOException error) {
            ColorPrint.printErr(String.format(I18n.bundle.getString("aindrea.config.control.lock_file.error"), lockFile.getName(), error.getMessage()));
            return false;
        }

        try {
            ConfigureReader = new Scanner(ConfigureFile);
        } catch (FileNotFoundException error) {
            ColorPrint.printErr(String.format(I18n.bundle.getString("aindrea.config.control.openScanner.error"), ConfigureFile.getName(), error.getMessage()));
            return false;
        }
        return true;
    }

    public static boolean ReadConfigurationFile() {
        if (!WorksNormally) {
            ColorPrint.printErr(String.format(I18n.bundle.getString("aindrea.config.control.read_config.error"), ConfigureFile.getName()));
            return false;
        }
        while (ConfigureReader.hasNextLine()) {
            String data = ConfigureReader.nextLine();
        }
        return true;
    }

    public static void releaseLock() {
        if (lockFile != null && lockFile.exists()) {
            if (!lockFile.delete()) {
                ColorPrint.printErr(String.format(I18n.bundle.getString("aindrea.config.control.lock_file.delete_error"), lockFile.getName()));
            }
        }
    }

}
