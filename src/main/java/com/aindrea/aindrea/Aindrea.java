package com.aindrea.aindrea;

import java.nio.file.FileSystems;

public class Aindrea {
    public static final String AindreaVersion = Version.getFullVersion();
    public static final String AindreaWorkPath =  System.getProperty("user.dir");
    public static final String SystemSeparator = FileSystems.getDefault().getSeparator();
    public static final String SystemName = System.getProperty("os.name");
    public static final String SystemArch = System.getProperty("os.arch");


}
