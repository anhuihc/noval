package com.hangzhou.novaldev.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hangzhou.novaldev.constant.RedisKey;
import com.hangzhou.novaldev.model.ChapterListBean;
import com.hangzhou.novaldev.model.ChapterListDeatilBean;
import com.hangzhou.novaldev.model.RankList;
import com.hangzhou.novaldev.model.SearchBean;
import com.hangzhou.novaldev.service.RedisService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
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
	@Value("${bqg}")
	private String BQG;
	@Value("${bqg.search}")
	private String BQG_SEARCH;
	@Value("${bqg.image}")
	private String BQG_IMAGE;
	@Value("${bqg.bookDetail}")
	private String BQG_BOOKDETAIL;

	@Value("${bqgtest}")
	private String TEST;

	@Autowired
	private RedisService redisService;

	public static final String[] UA = {"Mozilla/5.0 (Windows NT 6.1; WOW64; rv:46.0) Gecko/20100101 Firefox/46.0",
			"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/50.0.2661.87 Safari/537.36 OPR/37.0.2178.32",
			"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/534.57.2 (KHTML, like Gecko) Version/5.1.7 Safari/534.57.2",
			"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/45.0.2454.101 Safari/537.36",
			"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2486.0 Safari/537.36 Edge/13.10586",
			"Mozilla/5.0 (Windows NT 10.0; WOW64; Trident/7.0; rv:11.0) like Gecko",
			"Mozilla/5.0 (compatible; MSIE 10.0; Windows NT 6.1; WOW64; Trident/6.0)",
			"Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; WOW64; Trident/5.0)",
			"Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.1; WOW64; Trident/4.0)",
			"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/47.0.2526.106 BIDUBrowser/8.3 Safari/537.36",
			"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/47.0.2526.80 Safari/537.36 Core/1.47.277.400 QQBrowser/9.4.7658.400",
			"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/48.0.2564.116 UBrowser/5.6.12150.8 Safari/537.36",
			"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/38.0.2125.122 Safari/537.36 SE 2.X MetaSr 1.0",
			"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/48.0.2564.116 Safari/537.36 TheWorld 7",
			"Mozilla/5.0 (Windows NT 6.1; W…) Gecko/20100101 Firefox/60.0"};

	/**
	 * 热门关键字
	 * @return
	 */

	/**
	 * 猜你喜欢
	 * @return
	 */

	/**
	 * 获取小说分类
	 *
	 * @param keyword
	 * @return
	 */


//    @GetMapping("/search")
//    public Map search(@RequestParam("keyword") String keyword) {
//        try {
//            Random r = new Random();
//            int k = r.nextInt(14);
//            Document search  = Jsoup.connect("https://so.biqusoso.com/s.php?ie=utf-8&siteid=biqugexsw.com&q="+ keyword)
//                    .userAgent(UA[k])
//                    .get();
//            Elements elements = search.select("#search-main > div.search-list > ul>li");
//            Map<String,Object> map=new HashMap<>();
//            List<SearchBean> list =new ArrayList<>();
//            String img="https://www.biqugexsw.com";
//            for(int i=1;i<(elements.size()>6?6:elements.size());i++){
//                SearchBean sb=new SearchBean();
//                Document detail=Jsoup.connect(elements.get(i).select("a").attr("href")).get();
//                sb.setTitle(elements.get(i).getElementsByClass("s2").text());
//                sb.setAuthor(elements.get(i).getElementsByClass("s4").text());
//                //解析封面
//                sb.setCover(img+detail.select("body > div.book > div.info > div.cover > img").attr("src"));
//                //解析简介
//                sb.setShortIntro(detail.select("body > div.book > div.info > div.intro").text().substring(0,detail.select("body > div.book > div.info > div.intro").text().indexOf("作者")));
//                //解析分类
//                sb.setCat(detail.select("body > div.book > div.info > div.small > span:nth-child(2)").text().substring(detail.select("body > div.book > div.info > div.small > span:nth-child(2)").text().indexOf("：")+1));
//                //解析字数
//                sb.setWordTotal(detail.select("body > div.book > div.info > div.small > span:nth-child(4)").text().substring(detail.select("body > div.book > div.info > div.small > span:nth-child(4)").text().indexOf("：")+1));
//                //解析状态
//                sb.setUpdateStatus(detail.select("body > div.book > div.info > div.small > span:nth-child(3)").text().substring(detail.select("body > div.book > div.info > div.small > span:nth-child(3)").text().indexOf("：")+1));
//                //解析最后更新时间
//                sb.setUpdateDate(detail.select("body > div.book > div.info > div.small > span:nth-child(5)").text().substring(detail.select("body > div.book > div.info > div.small > span:nth-child(5)").text().indexOf("：")+1));
//                //解析最新章节
//                sb.setLastChapter(detail.select("body > div.book > div.info > div.small > span:nth-child(6)").text().substring(detail.select("body > div.book > div.info > div.small > span:nth-child(6)").text().indexOf("：")+1));
//                //解析书籍id
//                sb.setId(elements.get(i).select("a").attr("href").substring(elements.get(i).select("a").attr("href").lastIndexOf("/")+1));
//                list.add(sb);
//            }
//            map.put("books",list);
//            System.out.println(JSON.toJSONString(map));
//            return map;
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return new HashMap<>();
//    }
	@GetMapping("/search")
	public Map search1(@RequestParam("keyword") String keyword) {

		String searchJson = redisService.get(RedisKey.BQGSEARCH + "-" + keyword);
		if (searchJson != null) {
			return JSONObject.parseObject(searchJson);
		} else {
			Document search=null;
			Random r = new Random();
			int k = r.nextInt(14);
			JSONArray jsonArray=JSON.parseArray(TEST);
			for(int i=0;i<jsonArray.size();i++){
				try {
					search = Jsoup.connect(((JSONObject) jsonArray.get(i)).get("search") + keyword)
							.userAgent(UA[k]).timeout(5000)
							.get();
				} catch (Exception e) {
					continue;
				}
			}
			Elements elements = search.select("#search-main > div.search-list > ul>li");
			Map<String, Object> map = new HashMap<>();
			List<SearchBean> list = new ArrayList<>();
			String img = BQG_IMAGE;
			for (int i = 1; i < elements.size(); i++) {
				SearchBean sb = new SearchBean();
				String bookId = elements.get(i).select("a").attr("href").substring(elements.get(i).select("a").attr("href").lastIndexOf("/") + 1);
				sb.setTitle(elements.get(i).getElementsByClass("s2").text());
				sb.setAuthor(elements.get(i).getElementsByClass("s4").text());
				//解析封面
				sb.setCover(img + bookId.substring(0, 2) + "/" + bookId + "/" + bookId + "s.jpg");
				//解析书籍id
				sb.setId(bookId);
				list.add(sb);
			}
			map.put("books", list);
			redisService.set(RedisKey.BQGSEARCH + "-" + keyword, JSON.toJSONString(map));
			redisService.expire(RedisKey.BQGSEARCH + "-" + keyword, 24 * 60 * 60);
			System.out.println(JSON.toJSONString(map));
			return map;
		}
	}

	/**
	 * 书籍是否更新接口
	 */
	@GetMapping("/bookUpdate")
	public List bookUpdate(@RequestParam("bookId") String bookId) {
		String[] ids = bookId.split(",");
		List list = new ArrayList();
		for (String id : ids) {
			Map map = bookDetail(id);
			list.add(map);
		}
		return list;
	}


	/**
	 * 获取书籍详情
	 *
	 * @param bookId
	 * @return
	 */
	@GetMapping("/bookDetail")
	public Map bookDetail(@RequestParam("bookId") String bookId) {
		try {
			String img = BQG;
			SearchBean sb = new SearchBean();
			Document detail = Jsoup.connect(BQG_BOOKDETAIL + bookId).get();
			sb.setTitle(detail.select("body > div.book > div.info > h2").text());
			sb.setAuthor(detail.select("body > div.book > div.info > div.small > span:nth-child(1)").text().substring(detail.select("body > div.book > div.info > div.small > span:nth-child(1)").text().indexOf("：") + 1));
			//解析封面
			sb.setCover(img + detail.select("body > div.book > div.info > div.cover > img").attr("src"));
			//解析简介
			sb.setShortIntro(detail.select("body > div.book > div.info > div.intro").text().substring(detail.select("body > div.book > div.info > div.intro").text().indexOf("：") + 1, detail.select("body > div.book > div.info > div.intro").text().indexOf("作者")));
			//解析分类
			sb.setCat(detail.select("body > div.book > div.info > div.small > span:nth-child(2)").text().substring(detail.select("body > div.book > div.info > div.small > span:nth-child(2)").text().indexOf("：") + 1));
			//解析字数
			sb.setWordTotal(detail.select("body > div.book > div.info > div.small > span:nth-child(4)").text().substring(detail.select("body > div.book > div.info > div.small > span:nth-child(4)").text().indexOf("：") + 1));
			//解析状态
			sb.setUpdateStatus(detail.select("body > div.book > div.info > div.small > span:nth-child(3)").text().substring(detail.select("body > div.book > div.info > div.small > span:nth-child(3)").text().indexOf("：") + 1));
			//解析最后更新时间
			sb.setUpdateDate(detail.select("body > div.book > div.info > div.small > span:nth-child(5)").text().substring(detail.select("body > div.book > div.info > div.small > span:nth-child(5)").text().indexOf("：") + 1));
			//解析最新章节
			sb.setLastChapter(detail.select("body > div.book > div.info > div.small > span:nth-child(6)").text().substring(detail.select("body > div.book > div.info > div.small > span:nth-child(6)").text().indexOf("：") + 1));
			//解析书籍id
			sb.setId(bookId);
			System.out.println(JSON.toJSONString(sb));
			return (Map<String, Object>) JSON.toJSON(sb);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new HashMap();
	}

	//首先使用书籍id获取书源列表，然后选择书源获取章节列表，最后获取章节列表中的link字段url进行编码，作为link传入。

	//获取书籍章节列表
	@GetMapping("/chapterList")
	public Map chapterList(@RequestParam("bookId") String bookId) {
		try {
			Map<String, Object> map = new HashMap<>();
			List<ChapterListBean> list = new ArrayList<>();
			map.put("_id", bookId);
			map.put("name", "笔趣阁");
			Document listUrl = Jsoup.connect(BQG_BOOKDETAIL + bookId).get();
			Elements elements = listUrl.select("body > div.listmain > dl > dt:nth-child(11)").nextAll();
			for (Element element : elements) {
				ChapterListBean clb = new ChapterListBean();
				clb.setTitle(element.text());
				clb.setLink("http://www.biqugexsw.com" + element.select("a").attr("href"));
				clb.setId(bookId);
				list.add(clb);
			}
			map.put("chapters", list);
			System.out.println(JSON.toJSONString(map));
			return map;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new HashMap<>();
	}


	//获取书籍章节内容
	@GetMapping("/chapterDetail")
	public Map chapterDetail(@RequestParam("link") String link) {
		try {
			Document chapterDetailUrl = Jsoup.connect(link).get();
			Elements elements = chapterDetailUrl.select("#content");
			Map<String, Object> map = new HashMap<>();
			ChapterListDeatilBean cldb = new ChapterListDeatilBean();
			cldb.setId("");
			cldb.setTital(elements.get(0).select("#wrapper > div.book.reader > div.content > h1").text());
			cldb.setCpContent(elements.get(0).select("#content").html().substring(0, elements.get(0).select("#content").html().indexOf("https")));
			map.put("ok", true);
			map.put("chapter", cldb);
			System.out.println(JSON.toJSONString(map));
			return map;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new HashMap<>();
	}

	//获取小说排行榜
//    @Test
//    public void rankCategory(){
//        try{
//            Map<String,Object> map=new HashMap<>();
//
//            Document rankCategoryUrl=Jsoup.connect("https://www.biqugexsw.com/paihangbang").get();
//            List<Element> wrap_rank = rankCategoryUrl.getElementsByClass("wrap rank").get(0).children();
//            List listOne=new ArrayList<>();
//            for(Element element:wrap_rank){
//                Elements elements=element.children();
//                RankList rl=new RankList();
//                Map<String,Object> mapList=new HashMap<>();
//                List<RankList> list=new ArrayList<>();
////                rl.setTitle(elements.get(i).select("h2").text());
//                mapList.put("cat","1");
//                for(int i=0;i<elements.size();i++){
//                    rl.setTitle(elements.get(i).select("ul>li").get(i).text());
//                    rl.setLink(elements.get(i).select("ul>li").get(i).select("a").attr("href"));
//                    list.add(rl);
//                }++
//                mapList.put("list",list);
//            }
////            map.put("1",mapList);
//            System.out.println(JSON.toJSONString(map));
//        }catch(Exception e){
//            e.printStackTrace();
//        }
//
//    }
}
