package com.aindrea.com.aindrea.utils;

import com.aindrea.com.aindrea.I18n;
import com.aindrea.com.aindrea.logging.ColorPrint;

import java.util.Scanner;

public class LanguageConfig {
    public LanguageConfig() {
        ColorPrint.printWithInfo("§d" + "Since Aindrea is adapted for i18n (internationalization), Aindrea requires you to select the language you are most familiar with.\n[ 0 ] 简体中文\n[ 1 ] English", "§d i18n ", true);
        System.out.print(ColorPrint.fmtInfo("§d" + "Please select the language you use most often: ", "§3 i18n "));
        Scanner scanner = new Scanner(System.in);
        switch (scanner.nextLine()) {
            case "0":
                I18n.setLanguage(I18n.Chinese);
                ColorPrint.i18n.setLanguage(I18n.Chinese);
                ColorPrint.printSuc(I18n.bundle.getString("aindrea.language.set_success"));
                break;
            case "1":
                I18n.setLanguage(I18n.English);
                ColorPrint.i18n.setLanguage(I18n.English);
                ColorPrint.printSuc(I18n.bundle.getString("aindrea.language.set_success"));
                break;
            default:
                I18n.setLanguage(I18n.Default);
                ColorPrint.i18n.setLanguage(I18n.Default);
                ColorPrint.printWar("未识别到正确的选项，将自动选择语言: 简体中文(Chinese)");
                break;
        }
    }
}
