package com.deskover.vnpay;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;


public class Config {

  public static String vnp_PayUrl = "https://sandbox.vnpayment.vn/paymentv2/vpcpay.html";
  
  public static String vnp_Returnurl = "http://localhost:8080/vnpay_return.jsp";
  
  public static String vnp_TmnCode = "XJ9TEL5Q";
  
  public static String vnp_HashSecret = "KHPQXFCHYZICTXHZHSMGAEBYBVIUBIKQ";
  
  public static String vnp_apiUrl = "https://sandbox.vnpayment.vn/merchant_webapi/merchant.html";
  
  public static String md5(String message) {
    String digest = null;
    try {
      MessageDigest md = MessageDigest.getInstance("MD5");
      byte[] hash = md.digest(message.getBytes("UTF-8"));
      StringBuilder sb = new StringBuilder(2 * hash.length);
      for (byte b : hash) {
        sb.append(String.format("%02x", new Object[] { Integer.valueOf(b & 0xFF) }));
      } 
      digest = sb.toString();
    } catch (UnsupportedEncodingException ex) {
      digest = "";
    } catch (NoSuchAlgorithmException ex) {
      digest = "";
    } 
    return digest;
  }
  
  public static String Sha256(String message) {
    String digest = null;
    try {
      MessageDigest md = MessageDigest.getInstance("SHA-256");
      byte[] hash = md.digest(message.getBytes("UTF-8"));
      StringBuilder sb = new StringBuilder(2 * hash.length);
      for (byte b : hash) {
        sb.append(String.format("%02x", new Object[] { Integer.valueOf(b & 0xFF) }));
      } 
      digest = sb.toString();
    } catch (UnsupportedEncodingException ex) {
      digest = "";
    } catch (NoSuchAlgorithmException ex) {
      digest = "";
    } 
    return digest;
  }
  
  public static String hashAllFields(Map fields) {
    List<Comparable> fieldNames = new ArrayList(fields.keySet());
    Collections.sort(fieldNames);
    StringBuilder sb = new StringBuilder();
    Iterator<Comparable> itr = fieldNames.iterator();
    while (itr.hasNext()) {
      String fieldName = (String)itr.next();
      String fieldValue = (String)fields.get(fieldName);
      if (fieldValue != null && fieldValue.length() > 0) {
        sb.append(fieldName);
        sb.append("=");
        sb.append(fieldValue);
      } 
      if (itr.hasNext())
        sb.append("&"); 
    } 
    return hmacSHA512(vnp_HashSecret, sb.toString());
  }
  
  public static String hmacSHA512(String key, String data) {
    try {
      if (key == null || data == null)
        throw new NullPointerException(); 
      Mac hmac512 = Mac.getInstance("HmacSHA512");
      byte[] hmacKeyBytes = key.getBytes();
      SecretKeySpec secretKey = new SecretKeySpec(hmacKeyBytes, "HmacSHA512");
      hmac512.init(secretKey);
      byte[] dataBytes = data.getBytes(StandardCharsets.UTF_8);
      byte[] result = hmac512.doFinal(dataBytes);
      StringBuilder sb = new StringBuilder(2 * result.length);
      for (byte b : result) {
        sb.append(String.format("%02x", new Object[] { Integer.valueOf(b & 0xFF) }));
      } 
      return sb.toString();
    } catch (Exception ex) {
      return "";
    } 
  }
  
  public static String getIpAddress(HttpServletRequest request) {
    String ipAdress;
    try {
      ipAdress = request.getHeader("X-FORWARDED-FOR");
      if (ipAdress == null)
        ipAdress = request.getRemoteAddr(); 
    } catch (Exception e) {
      ipAdress = "Invalid IP:" + e.getMessage();
    } 
    return ipAdress;
  }
  
  public static String getRandomNumber(int len) {
    Random rnd = new Random();
    String chars = "0123456789";
    StringBuilder sb = new StringBuilder(len);
    for (int i = 0; i < len; i++)
      sb.append(chars.charAt(rnd.nextInt(chars.length()))); 
    return sb.toString();
  }
}
