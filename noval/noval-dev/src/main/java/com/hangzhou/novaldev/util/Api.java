package com.hangzhou.novaldev.util;

import com.hangzhou.novaldev.model.ChapterListBean;
import com.hangzhou.novaldev.model.ChapterListDeatilBean;
import com.hangzhou.novaldev.model.SearchBean;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.*;
import com.alibaba.fastjson.JSON;

@RestController
@RequestMapping("api")
public class Api {
//    @Value("${API-BQG-SEARCH}")
//    private String apiBqgSearch;

    @GetMapping("/search")
    public Map search(@RequestParam("keyword") String keyword) {
        try {
            Document search  = Jsoup.connect("https://so.biqusoso.com/s.php?ie=utf-8&siteid=biqugexsw.com&q="+ keyword).get();
            Elements elements = search.select("#search-main > div.search-list > ul>li");
            Map<String,Object> map=new HashMap<>();
            List<SearchBean> list =new ArrayList<>();
            String img="https://www.biqugexsw.com";
            for(int i=1;i<(elements.size()>6?6:elements.size());i++){
                SearchBean sb=new SearchBean();
                Document detail=Jsoup.connect(elements.get(i).select("a").attr("href")).get();
                sb.setTitle(elements.get(i).getElementsByClass("s2").text());
                sb.setAuthor(elements.get(i).getElementsByClass("s4").text());
                //解析封面
                sb.setCover(img+detail.select("body > div.book > div.info > div.cover > img").attr("src"));
                //解析简介
                sb.setShortIntro(detail.select("body > div.book > div.info > div.intro").text().substring(0,detail.select("body > div.book > div.info > div.intro").text().indexOf("作者")));
                //解析分类
                sb.setCat(detail.select("body > div.book > div.info > div.small > span:nth-child(2)").text().substring(detail.select("body > div.book > div.info > div.small > span:nth-child(2)").text().indexOf("：")+1));
                //解析字数
                sb.setWordTotal(detail.select("body > div.book > div.info > div.small > span:nth-child(4)").text().substring(detail.select("body > div.book > div.info > div.small > span:nth-child(4)").text().indexOf("：")+1));
                //解析状态
                sb.setUpdateStatus(detail.select("body > div.book > div.info > div.small > span:nth-child(3)").text().substring(detail.select("body > div.book > div.info > div.small > span:nth-child(3)").text().indexOf("：")+1));
                //解析最后更新时间
                sb.setUpdateDate(detail.select("body > div.book > div.info > div.small > span:nth-child(5)").text().substring(detail.select("body > div.book > div.info > div.small > span:nth-child(5)").text().indexOf("：")+1));
                //解析最新章节
                sb.setLastChapter(detail.select("body > div.book > div.info > div.small > span:nth-child(6)").text().substring(detail.select("body > div.book > div.info > div.small > span:nth-child(6)").text().indexOf("：")+1));
                //解析书籍id
                sb.setId(elements.get(i).select("a").attr("href").substring(elements.get(i).select("a").attr("href").lastIndexOf("/")+1));
                list.add(sb);
            }
            map.put("books",list);
            System.out.println(JSON.toJSONString(map));
            return map;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new HashMap<>();
    }

    //首先使用书籍id获取书源列表，然后选择书源获取章节列表，最后获取章节列表中的link字段url进行编码，作为link传入。

    //获取书籍章节列表
    @GetMapping("/chapterList")
    public void chapterList(){
        //body > div.listmain > dl > dd:nth-child(12)
        try{
            Map<String,Object> map=new HashMap<>();
            List<ChapterListBean> list=new ArrayList<>();
            map.put("_id","41047");
            map.put("name","笔趣阁");
            Document listUrl=Jsoup.connect("http://www.biqugexsw.com/book/goto/id/41047").get();
            Elements elements = listUrl.select("body > div.listmain > dl > dt:nth-child(11)").nextAll();
            for(Element element:elements){
                ChapterListBean clb=new ChapterListBean();
                clb.setTitle(element.text());
                clb.setLink("http://www.biqugexsw.com"+element.select("a").attr("href"));
                clb.setId("41047");
                list.add(clb);
            }
            map.put("chapters",list);
            System.out.println(JSON.toJSONString(map));
        }catch(Exception e){
            e.printStackTrace();
        }
    }


    //获取书籍章节内容
    @GetMapping("/chapterDetail")
    public void chapterDetail(String url){
        try{
            Document chapterDetailUrl=Jsoup.connect("https://www.biqugexsw.com/41_41047/7474539.html").get();
            Elements elements=chapterDetailUrl.select("#content");
            Map<String,Object> map=new HashMap<>();
            List<ChapterListDeatilBean> list=new ArrayList<>();
            ChapterListDeatilBean cldb=new ChapterListDeatilBean();
            cldb.setId("");
            cldb.setTital(elements.get(0).select("#wrapper > div.book.reader > div.content > h1").text());
            cldb.setCpContent(elements.get(0).select("#content").html().substring(0,elements.get(0).select("#content").html().indexOf("https")));
            list.add(cldb);
            map.put("ok",true);
            map.put("chapter",list);
            System.out.println(JSON.toJSONString(map));
        }catch(Exception e){
            e.printStackTrace();
        }

    }
}
