package com.github.jacobbishopxy.ubiquitousauth.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

import com.github.jacobbishopxy.ubiquitousauth.config.CasConfig;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.utils.HttpClientUtils;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AuthUtils {

	@Autowired
	CasConfig casConfig;

	private static DateTimeFormatter datetimeFormat = DateTimeFormatter.ofPattern("yyyyMMddHHmmss'Z'");

	private final String appUser;
	private final String appKey;
	private final String appInfo;
	private final String validateUrl;

	@Autowired
	public AuthUtils(CasConfig casConfig) {
		this.appUser = casConfig.getAppUser();
		this.appKey = casConfig.getAppKey();
		this.appInfo = casConfig.getAppInfo();
		this.validateUrl = casConfig.getValidateUrl();
	}

	public String getValidateUrl() {
		return validateUrl;
	}

	public String getResponseFromServer(String url, Map<String, Object> params) throws Exception {
		HttpPost httpPost = new HttpPost(url);
		createSignHeader(httpPost, params);
		JSONObject object = new JSONObject(params);
		// System.out.println("object:" + object);
		httpPost.setEntity(new ByteArrayEntity(object.toString().getBytes(), ContentType.APPLICATION_JSON));
		return execute(httpPost);
	}

	private String execute(HttpUriRequest request) throws Exception {
		CloseableHttpClient client = null;
		CloseableHttpResponse resp = null;
		try {
			client = HttpClientBuilder.create().build();
			resp = client.execute(request);
			// System.out.println("resp:"+resp);
			return EntityUtils.toString(resp.getEntity());
		} finally {
			HttpClientUtils.closeQuietly(resp);
			HttpClientUtils.closeQuietly(client);
		}
	}

	private void createSignHeader(HttpUriRequest request, Map<String, Object> params) throws Exception {
		request.addHeader("appuser", appUser);

		String randomCode = RandomStringUtils.random(16, true, true);
		request.addHeader("randomcode", randomCode);

		String timestamp = LocalDateTime.now().format(datetimeFormat);
		request.addHeader("timestamp", timestamp);
		// System.out.println(timestamp);

		String data = StringUtils.join(appUser, randomCode, timestamp);
		String encodeKey = DigestUtils.sha256Hex(StringUtils.join(data, "{", appKey, "}"));
		request.addHeader("encodekey", encodeKey);

		JSONObject object = new JSONObject(params);
		String paramData = StringUtils.join(object.toString(), "&", appKey);
		String signature = DigestUtils.md5Hex(paramData).toUpperCase();
		request.addHeader("sign", signature);

		request.addHeader("appinfo", appInfo);
		request.addHeader("Content-Type", "application/json");
	}
}
