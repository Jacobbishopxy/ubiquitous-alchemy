package com.github.jacobbishopxy.ubiquitousauth;

import java.util.HashMap;
import java.util.Map;

import com.github.jacobbishopxy.ubiquitousauth.config.CasConfig;
import com.github.jacobbishopxy.ubiquitousauth.service.AuthUtils;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@Import(WebClientTestConfiguration.class)
@TestPropertySource(locations = "classpath:cas.properties")
public class ValidationTest {

  @Autowired
  private CasConfig casConfig;

  @Autowired
  AuthUtils util;

  @Test
  public void testValidation() throws Exception {

    AuthUtils util = new AuthUtils(casConfig);
    Map<String, Object> params = new HashMap<String, Object>();

    params.put("username", casConfig.getTestUser());
    params.put("password", casConfig.getTestPass());

    String authUrl = casConfig.getAuthenticateUrl();
    String retStr = util.getResponseFromServer(authUrl, params);

    System.err.println(retStr);

    JSONObject myJsonObject = new JSONObject(retStr);
    JSONObject te = myJsonObject.getJSONObject("ticketEntry");
    String st = te.getString("ticketValue");
    String stname = te.getString("ticketName");
    Integer timingVerifyTime = te.getInt("timingVerifyTime");
    Integer autoTimeout = te.getInt("autoTimeout");
    System.out.println("ticketValue is " + st);
    System.out.println("ticketName is:" + stname);
    System.out.println("timingVerifyTime is:" + timingVerifyTime);
    System.out.println("autoTimeout is:" + autoTimeout);

    String validateUrl = casConfig.getValidateUrl();

    Map<String, Object> params2 = new HashMap<String, Object>();
    params2.put("ticketValue", st);
    params2.put("ticketName", "SIAMTGT");
    params2.put("timingVerifyTime", "0");
    params2.put("autoTimeout", "0");
    String res2 = util.getResponseFromServer(validateUrl, params2);

    System.err.println(res2);

    JSONObject obj = new JSONObject(res2);
    JSONObject user = obj.getJSONObject("user");
    String uid = user.getString("uid");

    System.err.println(uid);
  }

}
