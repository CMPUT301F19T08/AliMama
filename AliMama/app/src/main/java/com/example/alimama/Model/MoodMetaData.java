package com.example.alimama.Model;

import java.util.HashMap;

/**
 * static class which stores color for each emoticon
 * no outstanding issue
 * */
public class MoodMetaData {



    private static final String HAPPY_COLOR = "#deed9f";
    private static final String HAPPY_EMOTICON = "\uD83D\uDE0A";
    private static final String SUPER_HAPPY_COLOR = "#fe8342";
    private static final String SUPER_HAPPY_EMOTICON = "\uD83D\uDE02";
    private static final String LOVE_COLOR = "#feb1b1";
    private static final String LOVE_EMOTICON = "\uD83D\uDE0D";
    private static final String ANGRY_COLOR = "#c93928";
    private static final String ANGRY_EMOTICON = "\uD83D\uDE21";
    private static final String TONGUE_COLOR = "#e44a6a";
    private static final String TONGUE_EMOTICON = "\uD83D\uDE1C";
    private static final String CRY_COLOR = "#e7c860";
    private static final String CRY_EMOTICON = "\uD83D\uDE22";
    private static final String SMIRK_COLOR = "#ffffff";
    private static final String SMIRK_EMOTICON = "\uD83D\uDE0F";
    public static HashMap<String, String> map = new HashMap<String, String>() {{
        put(HAPPY_EMOTICON, HAPPY_COLOR);
        put(SUPER_HAPPY_EMOTICON, SUPER_HAPPY_COLOR);
        put(LOVE_EMOTICON,LOVE_COLOR);
        put(ANGRY_EMOTICON,ANGRY_COLOR);
        put(TONGUE_EMOTICON, TONGUE_COLOR);
        put(CRY_EMOTICON,CRY_COLOR);
        put(SMIRK_EMOTICON, SMIRK_COLOR);


    }};







}
