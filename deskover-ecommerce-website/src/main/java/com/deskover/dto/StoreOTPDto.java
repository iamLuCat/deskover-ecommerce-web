package com.deskover.dto;

public class StoreOTPDto {
	private static int otp;

	public static int getOtp() {
		return otp;
	}

	public static void setOtp(int otp) {
		StoreOTPDto.otp = otp;
	}
}
