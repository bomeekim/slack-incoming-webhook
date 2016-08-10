package com.nemus.newsstand.slack.integration;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class SlackNotifier {
    final Logger logger = LoggerFactory.getLogger(SlackNotifier.class);

	@Autowired
	private RestTemplate restTemplate;

	public enum SlackTarget {
		
		CH_NEWS_STAND("https://hooks.slack.com/services/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", "article");

		String webHookUrl;
		String channel;

		SlackTarget(String webHookUrl, String channel) {
			this.webHookUrl = webHookUrl;
			this.channel = channel;
		}
	}

	public boolean notify(SlackTarget target, List<SlackMessageAttachment> message) {
		logger.debug("Notify[target: {}, message: {}]", target, message);

		SlackMessage slackMessage = new SlackMessage.SlackMessageBuilder(null, target.channel, message).build();

		try {
            restTemplate.postForEntity(target.webHookUrl, slackMessage, String.class); 
			return true;
		} catch (Exception e) {
			logger.error("Occur Exception: {}", e);
			return false;
		}
	}
}
