package com.nemus.newsstand.slack.integration;

import java.util.List;

public class SlackMessage {
	
	private String text;
	private String channel;
	private List<SlackMessageAttachment> attachments;
	
	private SlackMessage(SlackMessageBuilder builder) {
		this.text = builder.text;
		this.channel = builder.channel;
		this.attachments = builder.attachments;
	}

	public String getText() {
		return this.text;
	}

	public String getChannel() {
		return this.channel;
	}

	public List<SlackMessageAttachment> getAttachments() {
		return this.attachments;
	}
	
	public void setText(String text) {
		this.text = text;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public void setAttachments(List<SlackMessageAttachment> attachments) {
		this.attachments = attachments;
	}

	public static class SlackMessageBuilder {
		private String text;
		private String channel;
		private List<SlackMessageAttachment> attachments;

		public SlackMessageBuilder (String text, String channel, List<SlackMessageAttachment> attachments) {
			this.text = text;
			this.channel = channel;
			this.attachments = attachments;
		}
		
		public SlackMessage build() {
			return new SlackMessage(this);
		}
	}	
}
