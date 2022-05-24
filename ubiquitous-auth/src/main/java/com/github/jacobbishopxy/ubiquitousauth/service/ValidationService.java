package com.github.jacobbishopxy.ubiquitousauth.service;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ValidationService {

  @Autowired
  AuthUtils util;

  public String validate(String ticketValue) throws Exception {
    Map<String, Object> params = new HashMap<String, Object>();
    params.put("ticketValue", ticketValue);
    params.put("ticketName", "SIAMTGT");
    params.put("timingVerifyTime", "0");
    params.put("autoTimeout", "0");
    String validateResult = util.getResponseFromServer(util.getValidateUrl(), params);

    return validateResult;
  }

  public String getUsername(String ticketValue) throws Exception {
    String validateResult = validate(ticketValue);
    JSONObject obj = new JSONObject(validateResult);

    try {
      JSONObject user = obj.getJSONObject("user");
      return user.getString("uid");
    } catch (Exception e) {
      return e.getMessage();
    }
  }

  public String getEmail(String ticketValue) throws Exception {
    String validateResult = validate(ticketValue);
    JSONObject obj = new JSONObject(validateResult);

    try {
      JSONObject user = obj.getJSONObject("user");
      return user.getString("uid");
    } catch (Exception e) {
      return e.getMessage();
    }
  }

}
