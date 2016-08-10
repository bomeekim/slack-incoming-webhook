package com.nemus.newsstand.slack.integration;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SlackMessageAttachment {
	private String color;
	private String pretext;
	private String title;
	private String title_link;
	private String text;
	
	public String getColor() {
		return color;
	}

	public String getPretext() {
		return pretext;
	}

	public String getTitle() {
		return title;
	}

	public String getTitle_link() {
		return title_link;
	}

	public String getText() {
		return text;
	}

	public void setPretext(String pretext) {
		this.pretext = pretext;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setTitle_link(String title_link) {
		this.title_link = title_link;
	}

	public void setText(String text) {
		this.text = text;
	}
}