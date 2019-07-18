package com.hangzhou.novaldev.util;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.Base64;


@Controller
public class Api {
    @Value("${API-BQG-SEARCH}")
    private String apiBqgSearch;

    protected Document search = null;

    @Test
    public void search() {
        try {
            search = Jsoup.connect("https://so.biqusoso.com/s.php?ie=utf-8&siteid=biqugexsw.com&q="+ URLEncoder.encode("遮天","utf-8")).get();
            Elements elements = search.select("#search-main > div.search-list > ul>li");
            System.out.println("");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
