package com.aindrea.aindrea.utils;

import com.aindrea.aindrea.I18n;
import com.aindrea.aindrea.logging.ColorPrint;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;

public class ProcessUtils {
    public static int GetSelfProcessPid() {
        RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();
        final int pid = (int) runtimeMXBean.getPid();
        return pid;
    }

    public static boolean isProcessRunning(String processId) {
        String SystemType = System.getProperty("os.name").toLowerCase();

        ProcessBuilder processBuilder;
        if (SystemType.contains("win")) {
            processBuilder = new ProcessBuilder("tasklist");
        } else {
            processBuilder = new ProcessBuilder("ps", "-e");
        }

        try {
            Process process = processBuilder.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.contains(processId)) {
                    return true;
                }
            }
        } catch (IOException error) {
            ColorPrint.printErr(String.format(I18n.bundle.getString("aindrea.utils.process_utils.is_process_exist.error"), error.getMessage()));
        }
        return false;
    }
}
