package com.nemus.newsstand.slack.web;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.nemus.newsstand.slack.integration.SlackMessageAttachment;
import com.nemus.newsstand.slack.integration.SlackNotifier;
import com.nemus.newsstand.slack.integration.SlackNotifier.SlackTarget;

import java.io.IOException;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@Component
@RestController
public class SlackSendController {

	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
	private static List<SlackMessageAttachment> temp = new ArrayList<>();

	@Autowired
	private SlackNotifier slackNotifier;

	@RequestMapping(value = "/", method = RequestMethod.POST)
	public ResponseEntity<Boolean> send(@RequestBody SlackMessageAttachment message) throws IOException {
		
		List<SlackMessageAttachment> articles = new ArrayList<>();

		message.setText("[오늘의 IT 업계 소식] " + getDate());
		articles.add(message);
		articles.addAll(temp);

		temp.clear();

		return ResponseEntity.ok(slackNotifier.notify(SlackTarget.CH_NEWS_STAND, articles));
	}

	@Scheduled(cron="0 30 9,15 ? * MON-FRI")
	public void updateNews() throws IOException {
		
		Document naverNews = Jsoup.connect("http://news.naver.com/main/main.nhn?mode=LSD&mid=shm&sid1=105").get();
		Elements elements = naverNews.select("table.container tbody tr ul.type02_headline li");

		for (Element element : elements) {
			SlackMessageAttachment attachement = new SlackMessageAttachment();

			attachement.setTitle("* " + element.select("a strong").text() + " / " + element.getElementsByTag("span").text());
			attachement.setTitle_link(element.getElementsByTag("a").attr("href"));

			temp.add(attachement);
		}

		for (SlackMessageAttachment a : temp) {
			System.out.println("articles : " + a);
		}

		RestTemplate restTemplate = new RestTemplate();
		String baseUrl = "http://localhost:8080/";

		SlackMessageAttachment a = new SlackMessageAttachment();
		a.setText("");

		restTemplate.postForObject(baseUrl, a, String.class);
	}

	public String getDate() {
		SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss", Locale.KOREA);
		Date currentTime = new Date();
		String mTime = mSimpleDateFormat.format(currentTime);

		return mTime;
	}
}
