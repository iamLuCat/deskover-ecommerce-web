package com.deskover.utils;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystems;
import java.nio.file.Path;

import com.deskover.constant.PathConstant;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

public class QrCodeUtil {
	
	public static String QrCode(String orderCode, String name) {

		    QRCodeWriter qrCodeWriter = new QRCodeWriter();
		    BitMatrix matrix = null;
			try {
				matrix = qrCodeWriter.encode(orderCode, BarcodeFormat.QR_CODE, 200, 200);
			} catch (WriterException e) {
				e.printStackTrace();
			}
		    String outputFile = PathConstant.QRCODE_IMAGE_STATIC+name+".png";
		    Path path = FileSystems.getDefault().getPath(outputFile);
		    try {
				MatrixToImageWriter.writeToPath(matrix, "PNG", path);
				return name+".png";
				
			} catch (IOException e) {
				e.printStackTrace();
				throw new IllegalArgumentException("Tạo không thành công");
			}
		
	}
	 
}
