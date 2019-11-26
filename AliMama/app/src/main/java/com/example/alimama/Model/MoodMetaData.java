package com.example.alimama.Model;

import java.util.HashMap;

/**
 * static class which stores color for each emoticon
 * no outstanding issue
 * */
public class MoodMetaData {



    private static final String HAPPY_COLOR = "#20FFFF00";
    private static final String HAPPY_EMOTICON = "\uD83D\uDE0A";
    private static final String SUPER_HAPPY_COLOR = "#20FFA500";
    private static final String SUPER_HAPPY_EMOTICON = "\uD83D\uDE02";
    private static final String LOVE_COLOR = "#20FF0000";
    private static final String LOVE_EMOTICON = "\uD83D\uDE0D";
    private static final String ANGRY_COLOR = "#20B589D6";
    private static final String ANGRY_EMOTICON = "\uD83D\uDE21";
    private static final String TONGUE_COLOR = "#2000ff00";
    private static final String TONGUE_EMOTICON = "\uD83D\uDE1C";
    private static final String CRY_COLOR = "#202A9DF4";
    private static final String CRY_EMOTICON = "\uD83D\uDE22";
    private static final String SMIRK_COLOR = "#2054B948";
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
