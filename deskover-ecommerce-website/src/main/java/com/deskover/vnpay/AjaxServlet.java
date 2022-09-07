package com.deskover.vnpay;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = "/vnpayajax/*", loadOnStartup = 1)
public class AjaxServlet extends HttpServlet {
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    String vnp_Version = "2.1.0";
    String vnp_Command = "pay";
    String vnp_OrderInfo = req.getParameter("vnp_OrderInfo");
    String orderType = req.getParameter("ordertype");
    String vnp_TxnRef = Config.getRandomNumber(8);
    int mcId = Integer.parseInt(Config.getRandomNumber(8));
    int amountmc = Integer.parseInt(req.getParameter("amount"));
    String vnp_IpAddr = Config.getIpAddress(req);
    String vnp_TmnCode = Config.vnp_TmnCode;
    int amount = Integer.parseInt(req.getParameter("amount")) * 100;
    Map<String, String> vnp_Params = new HashMap<>();
    vnp_Params.put("vnp_Version", vnp_Version);
    vnp_Params.put("vnp_Command", vnp_Command);
    vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
    vnp_Params.put("vnp_Amount", String.valueOf(amount));
    vnp_Params.put("vnp_CurrCode", "VND");
    String bank_code = req.getParameter("bankcode");
    if (bank_code != null && !bank_code.isEmpty())
      vnp_Params.put("vnp_BankCode", bank_code); 
    vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
    vnp_Params.put("vnp_OrderInfo", vnp_OrderInfo);
    vnp_Params.put("vnp_OrderType", orderType);
    String locate = req.getParameter("language");
    if (locate != null && !locate.isEmpty()) {
      vnp_Params.put("vnp_Locale", locate);
    } else {
      vnp_Params.put("vnp_Locale", "vn");
    } 
    vnp_Params.put("vnp_ReturnUrl", Config.vnp_Returnurl);
    vnp_Params.put("vnp_IpAddr", vnp_IpAddr);
    Date dt = new Date();
    SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
    String vnp_CreateDate = formatter.format(dt);
    vnp_Params.put("vnp_CreateDate", vnp_CreateDate);
    Calendar cldvnp_ExpireDate = Calendar.getInstance();
    cldvnp_ExpireDate.add(13, 30);
    Date vnp_ExpireDateD = cldvnp_ExpireDate.getTime();
    String vnp_ExpireDate = formatter.format(vnp_ExpireDateD);
    vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);
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
    String paymentUrl = Config.vnp_PayUrl + "?" + queryUrl;
    JsonObject job = new JsonObject();
    job.addProperty("code", "00");
    job.addProperty("message", "success");
    job.addProperty("data", paymentUrl);
    Gson gson = new Gson();
    resp.getWriter().write(gson.toJson((JsonElement)job));
  }
}
