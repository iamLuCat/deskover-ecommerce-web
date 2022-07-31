package com.deskover.other.vnpay;

import com.deskover.other.vnpay.*;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class VnpayQuery extends HttpServlet {
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    String vnp_TxnRef = req.getParameter("order_id");
    String vnp_TransDate = req.getParameter("trans_date");
    String vnp_TmnCode = Config.vnp_TmnCode;
    String vnp_IpAddr = Config.getIpAddress(req);
    Map<String, String> vnp_Params = new HashMap<>();
    vnp_Params.put("vnp_Version", "2.1.0");
    vnp_Params.put("vnp_Command", "querydr");
    vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
    vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
    vnp_Params.put("vnp_OrderInfo", "Kiem tra ket qua GD OrderId:" + vnp_TxnRef);
    vnp_Params.put("vnp_TransDate", vnp_TransDate);
    vnp_Params.put("vnp_IpAddr", vnp_IpAddr);
    Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
    SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
    String vnp_CreateDate = formatter.format(cld.getTime());
    vnp_Params.put("vnp_CreateDate", vnp_CreateDate);
    List<Comparable> fieldNames = new ArrayList(vnp_Params.keySet());
    Collections.sort(fieldNames);
    StringBuilder hashData = new StringBuilder();
    StringBuilder query = new StringBuilder();
    Iterator<Comparable> itr = fieldNames.iterator();
    while (itr.hasNext()) {
      String fieldName = (String)itr.next();
      String fieldValue = vnp_Params.get(fieldName);
      if (fieldValue != null && fieldValue.length() > 0) {
        hashData.append(fieldName);
        hashData.append('=');
        hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
        query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString()));
        query.append('=');
        query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
        if (itr.hasNext()) {
          query.append('&');
          hashData.append('&');
        } 
      } 
    } 
    String queryUrl = query.toString();
    String vnp_SecureHash = Config.hmacSHA512(Config.vnp_HashSecret, hashData.toString());
    queryUrl = queryUrl + "&vnp_SecureHash=" + vnp_SecureHash;
    String paymentUrl = Config.vnp_apiUrl + "?" + queryUrl;
    URL url = new URL(paymentUrl);
    HttpURLConnection connection = (HttpURLConnection)url.openConnection();
    connection.setRequestMethod("GET");
    BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
    StringBuilder response = new StringBuilder();
    String inputLine;
    while ((inputLine = in.readLine()) != null)
      response.append(inputLine); 
    in.close();
    String Rsp = response.toString();
    String respDecode = URLDecoder.decode(Rsp, "UTF-8");
    String[] responseData = respDecode.split("&|\\=");
    JsonObject job = new JsonObject();
    job.addProperty("data", Arrays.toString((Object[])responseData));
    Gson gson = new Gson();
    resp.getWriter().write(gson.toJson((JsonElement)job));
  }
}
