package com.aindrea.aindrea.config;

import com.aindrea.aindrea.Aindrea;
import com.aindrea.aindrea.I18n;
import com.aindrea.aindrea.logging.ColorPrint;
import com.aindrea.aindrea.utils.BasicWorkPath;
import com.aindrea.aindrea.utils.ProcessUtils;

import java.io.*;
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
            if (lockFile.exists()) {
                String existingPid = readLockFile();
                if (existingPid != null && ProcessUtils.isProcessRunning(existingPid)) {
                    ColorPrint.printErr(String.format(I18n.bundle.getString("aindrea.config.control.lock_file.exists"), lockFile.getName(), lockFile.toPath()));
                    return false;
                } else {
                    if (!lockFile.delete()) {
                        ColorPrint.printErr(String.format(I18n.bundle.getString("aindrea.config.control.lock_file.delete_error"), lockFile.getName()));
                        return false;
                    }
                }
            }

            if (!lockFile.createNewFile()) {
                ColorPrint.printErr(String.format(I18n.bundle.getString("aindrea.config.control.lock_file.exists"), lockFile.getName(), lockFile.toPath()));
                return false;
            }

            writePidToLockFile(String.valueOf(Aindrea.AindreaPID));

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

    private static String readLockFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(lockFile))) {
            return reader.readLine();
        } catch (IOException e) {
            ColorPrint.printErr(String.format(I18n.bundle.getString("aindrea.config.control.lock_file.read_error"), lockFile.getName(), e.getMessage()));
            return null;
        }
    }

    private static void writePidToLockFile(String pid) {
        try (FileWriter writer = new FileWriter(lockFile)) {
            writer.write(pid);
        } catch (IOException e) {
            ColorPrint.printErr(String.format(I18n.bundle.getString("aindrea.config.control.lock_file.write_error"), lockFile.getName(), e.getMessage()));
        }
    }

    public static boolean ReadConfigurationFile() {
        if (!WorksNormally) {
            ColorPrint.printErr(String.format(I18n.bundle.getString("aindrea.config.control.read_config.error"), ConfigureFile.getName()));
            return false;
        }
        while (ConfigureReader.hasNextLine()) {
            String data = ConfigureReader.nextLine();
            new ConfigParse(data);
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
