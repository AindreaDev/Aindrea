package com.aindrea.aindrea.logging;

import com.aindrea.aindrea.I18n;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.locks.ReentrantLock;

public class ColorPrint {
    public static final I18n i18n = new I18n();
    private static final String[][] STD_COLOR_LIST = new String[][]{{"0", "#000000"}, {"1", "#0000AA"}, {"2", "#00AA00"}, {"3", "#00AAAA"}, {"4", "#AA0000"}, {"5", "#AA00AA"}, {"6", "#FFAA00"}, {"7", "#AAAAAA"}, {"8", "#555555"}, {"9", "#5555FF"}, {"a", "#55FF55"}, {"b", "#55FFFF"}, {"c", "#FF5555"}, {"d", "#FF55FF"}, {"e", "#FFFF55"}, {"f", "#FFFFFF"}, {"g", "#DDD605"}, {"r", "/"}};
    private static final ReentrantLock lock = new ReentrantLock();

    static {
        // 设置默认语言为中文
        I18n.setLanguage(I18n.Chinese);
    }

    public static String simpleFmt(String[][] kw, String arg) {
        for (String[] kv : kw) {
            arg = arg.replace(kv[0], kv[1]);
        }
        return arg;
    }

    public static String colormodeReplace(String text, int showmode) {
        text = ColorPrint.strike(text);
        return ColorPrint.simpleFmt(new String[][]{{"\u00a70", "\u001b[" + showmode + ";37;90m"}, {"\u00a71", "\u001b[" + showmode + ";37;34m"}, {"\u00a72", "\u001b[" + showmode + ";37;32m"}, {"\u00a73", "\u001b[" + showmode + ";37;36m"}, {"\u00a74", "\u001b[" + showmode + ";37;31m"}, {"\u00a75", "\u001b[" + showmode + ";37;35m"}, {"\u00a76", "\u001b[" + showmode + ";37;33m"}, {"\u00a77", "\u001b[" + showmode + ";37;90m"}, {"\u00a78", "\u001b[" + showmode + ";37;2m"}, {"\u00a79", "\u001b[" + showmode + ";37;94m"}, {"\u00a7a", "\u001b[" + showmode + ";37;92m"}, {"\u00a7b", "\u001b[" + showmode + ";37;96m"}, {"\u00a7c", "\u001b[" + showmode + ";37;91m"}, {"\u00a7d", "\u001b[" + showmode + ";37;95m"}, {"\u00a7e", "\u001b[" + showmode + ";37;93m"}, {"\u00a7f", "\u001b[" + showmode + ";37;1m"}, {"\u00a7r", "\u001b[0m"}, {"\u00a7u", "\u001b[4m"}, {"\u00a7l", "\u001b[1m"}}, text) + "\u001b[0m";
    }

    public static String align(String text, int length) {
        int textLength = text.length();
        for (char c : text.toCharArray()) {
            if (Character.isLetterOrDigit(c)) continue;
            ++textLength;
        }
        return text + " ".repeat(Math.max(0, length - textLength));
    }

    private static String strike(String text) {
        StringBuilder textOk = new StringBuilder();
        boolean strikeMode = false;
        for (int i2 = 0; i2 < text.length(); ++i2) {
            char c = text.charAt(i2);
            if (c == '\u00a7' && i2 + 1 < text.length()) {
                if (text.charAt(i2 + 1) == 's') {
                    strikeMode = true;
                    ++i2;
                    continue;
                }
                if (text.charAt(i2 + 1) == 'r') {
                    strikeMode = false;
                    ++i2;
                    continue;
                }
            }
            if (strikeMode) {
                textOk.append("\u0336").append(c);
                continue;
            }
            textOk.append(c);
        }
        return textOk.toString();
    }

    public static void printWithInfo(String text, String info, boolean needLog) {
        lock.lock();
        try {
            if (needLog) {
                ColorPrint.cLog(info, text);
            }
            String setNextColor = "\u00a7r";
            if (text.contains("\n")) {
                String[] lines = text.split("\n");
                StringBuilder outputTxts = new StringBuilder();
                for (String line : lines) {
                    int n;
                    if (line.contains("\u00a7") && (n = line.lastIndexOf("\u00a7")) != -1 && n + 1 < line.length()) {
                        setNextColor = line.substring(n, n + 2);
                    }
                    outputTxts.append(new SimpleDateFormat("[HH:mm] ").format(new Date())).append(ColorPrint.colormodeReplace(info, 7)).append(" ").append(ColorPrint.colormodeReplace(setNextColor + line, 0)).append("\n");
                }
                System.out.print(outputTxts.toString());
            } else {
                System.out.println(new SimpleDateFormat("[HH:mm] ").format(new Date()) + ColorPrint.colormodeReplace(info, 7) + " " + ColorPrint.colormodeReplace(text, 0));
            }
        } finally {
            lock.unlock();
        }
    }

    public static void cleanPrint(String text) {
        lock.lock();
        try {
            System.out.println(ColorPrint.colormodeReplace(text, 0));
        } finally {
            lock.unlock();
        }
    }

    public static String cleanFmt(String text) {
        return ColorPrint.colormodeReplace(text, 0);
    }

    public static void print(String ... args) {
        ColorPrint.printWithInfo(String.join((CharSequence)" ", args), i18n.getString("INFO_NORMAL"), true);
    }

    public static void printErr(String text) {
        ColorPrint.printWithInfo("\u00a7c" + text, i18n.getString("INFO_ERROR"), true);
    }

    public static void printInf(String text) {
        ColorPrint.printWithInfo(text, i18n.getString("INFO_NORMAL"), true);
    }

    public static void printSuc(String text) {
        ColorPrint.printWithInfo("\u00a7a" + text, i18n.getString("INFO_SUCC"), true);
    }

    public static void printWar(String text) {
        ColorPrint.printWithInfo("\u00a76" + text, i18n.getString("INFO_WARN"), true);
    }

    public static void printLoad(String text) {
        lock.lock();
        try {
            ColorPrint.printWithInfo("\u00a7d" + text, i18n.getString("INFO_LOAD"), true);
        } finally {
            lock.unlock();
        }
    }

    public static String fmtInfo(String text, String info) {
        lock.lock();
        try {
            String nextcolor = "\u00a7r";
            if (text.contains("\n")) {
                String[] lines = text.split("\n");
                StringBuilder outputTxts = new StringBuilder();
                for (String line : lines) {
                    int n;
                    if (line.contains("\u00a7") && (n = line.lastIndexOf("\u00a7")) != -1 && n + 1 < line.length()) {
                        nextcolor = line.substring(n, n + 2);
                    }
                    outputTxts.append(new SimpleDateFormat("[HH:mm] ").format(new Date())).append(ColorPrint.colormodeReplace(info, 7)).append(" ").append(ColorPrint.colormodeReplace(nextcolor + line, 0)).append("\n");
                }
                String string = outputTxts.toString();
                return string;
            }
            String string = new SimpleDateFormat("[HH:mm] ").format(new Date()) + ColorPrint.colormodeReplace(info, 7) + " " + ColorPrint.colormodeReplace(text, 0);
            return string;
        } finally {
            lock.unlock();
        }
    }

    public static void cLog(String inf, String msg) {
        lock.lock();
        try {
            String colorCode;
            String[] logLevels = new String[]{i18n.getString("INFO_WARN"), "WARN", i18n.getString("INFO_SUCC"), "INFO", i18n.getString("INFO_NORMAL"), "INFO", i18n.getString("INFO_FAIL"), "FAIL", i18n.getString("INFO_ERROR"), "ERROR"};
            for (int i2 = 0; i2 < logLevels.length; i2 += 2) {
                if (!inf.equals(logLevels[i2])) continue;
                inf = logLevels[i2 + 1];
                break;
            }
            for (String[] col : STD_COLOR_LIST) {
                colorCode = "\u00a7" + col[0];
                msg = msg.replace(colorCode, "");
            }
            for (String[] col : STD_COLOR_LIST) {
                colorCode = "\u00a7" + col[0];
                inf = inf.replace(colorCode, "");
            }
            inf = inf.replace(" ", "");
        } finally {
            lock.unlock();
        }
    }
}
