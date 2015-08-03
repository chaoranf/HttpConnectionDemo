package com.feng.testhttp.entity;

public class WeatherInfo {
	public String city;
	public String pinyin;
	public String citycode;
	public String date;
	public String time;
	public String postCode;
	public String longitude;
	public String latitude;
	public String altitude;
	public String weather;
	public String temp;
	public String l_tmp;
	public String h_tmp;
	public String WD;
	public String WS;
//	public String sunrise;
//	public String sunset;
	public String oye;
	@Override
	public String toString() {
		return "WeatherInfo [city=" + city + ", pinyin=" + pinyin
				+ ", citycode=" + citycode + ", date=" + date + ", time="
				+ time + ", postCode=" + postCode + ", longitude=" + longitude
				+ ", latitude=" + latitude + ", altitude=" + altitude
				+ ", weather=" + weather + ", temp=" + temp + ", l_tmp="
				+ l_tmp + ", h_tmp=" + h_tmp + ", WD=" + WD + ", WS=" + WS
				+ ", oye=" + oye + "]";
	}
	
	
}
