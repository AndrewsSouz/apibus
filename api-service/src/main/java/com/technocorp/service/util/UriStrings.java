package com.technocorp.service.util;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class UriStrings {

    public static final String ALL_LINES = "http://www.poatransporte.com.br/php/facades/process.php?a=nc&p=%&t=o";
    public static final String LINE = "http://www.poatransporte.com.br/php/facades/process.php?a=il&p=";
    public static final String CITY = URLEncoder.encode(", Porto Alegre, RS, Brasil", StandardCharsets.UTF_8)
            .replace("+", "%20");
    public static final String TOKEN =
            "?access_token=pk.eyJ1Ijoic3Rvcm16ZHJ1aWQiLCJhIjoiY2tta2F5cjM1MHplYjJubWk5cjFrMnJ0diJ9.WPKER-px0O-UIwao0-h53Q";
}
