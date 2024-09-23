package com.aindrea.com.aindrea;

public class Version {
    public static final String Version = "V0.0.1.240923_Base"; // 综合版本号
    private static final String prefix = "V"; // 版本前缀
    private static final int major = 0; // 主版本号
    private static final int minor = 0; // 副版本号
    private static final int revision = 1; // 修订版本号
    private static final String date = "240923"; // 日期版本号
    private static final String stage = "Base"; // 希腊字母版本号

    public static String getPrefix() { return prefix; }
    public static int getMajor() { return major; }
    public static int getMinor() { return minor; }
    public static int getRevision() { return revision; }
    public static String getDate() { return date; }
    public static String getStage() { return stage; }

    // 获取完整的版本号
    public static String getFullVersion() {
        return prefix + major + "." + minor + "." + revision + "." + date + "_" + stage;
    }
}

