package com.alkemy.java.config;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sendgrid.Personalization;

public class DynamicTemplatePersonalization extends Personalization {

		@JsonProperty(value = "dynamic_template_data")
		private Map<String, String> dynamicTemplateData;

		@JsonProperty("dynamic_template_data")
		public Map<String, String> getDynamicTemplateData() {
			if (dynamicTemplateData == null) {
				return Collections.<String, String>emptyMap();
			}
			return dynamicTemplateData;
		}

		public void addDynamicTemplateData(String key, String value) {
			if (dynamicTemplateData == null) {
				dynamicTemplateData = new HashMap<String, String>();
				dynamicTemplateData.put(key, value);
			} else {
				dynamicTemplateData.put(key, value);
			}
		}

	}
