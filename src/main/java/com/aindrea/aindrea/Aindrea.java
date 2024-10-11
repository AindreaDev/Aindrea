package com.aindrea.aindrea;

import com.aindrea.aindrea.utils.ProcessUtils;

import java.nio.file.FileSystems;
import java.nio.file.Path;

public class Aindrea {
    public static final String AindreaVersion = Version.getFullVersion();
    public static final Path AindreaWorkPath =  Path.of(System.getProperty("user.dir"));
    public static final String SystemSeparator = FileSystems.getDefault().getSeparator();
    public static final String SystemName = System.getProperty("os.name");
    public static final String SystemArch = System.getProperty("os.arch");
    public static final int AindreaPID = ProcessUtils.GetSelfProcessPid();

}
