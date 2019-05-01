package PracticeSim.background;

import java.awt.Color;
import java.awt.Graphics;

public class Time {
	private int seconds;
	private int minutes;
	private int hour;
	private int AmOrPm;
	
	private boolean changed1 = false;
	private boolean changed2 = false;
	private boolean changed3 = false;
	
	public Time() {
		seconds = 0;
		minutes = 56;
		hour =11;
		AmOrPm = 1;
	}
	public void addMin(int sec) {
		seconds += sec;
		if(seconds % 60 == 0) {
			minutes++;
			seconds = 0;
		}
		if(minutes % 60 == 0 && !changed1) {
			minutes = 0;
			hour++;
			changed1 = true;
		}
		if(hour % 13 == 0 && !changed2) {
			hour = 1;
			changed2 = true;
		}
		
		if(hour == 12 && minutes == 0 && !changed3) {
			changed3 = true;
			AmOrPm += 1;
		}
		
	}
	public String getTimer() {
		String ampm = "";
		if(AmOrPm % 2 == 0) {
			ampm= "PM";
		}else {
			ampm = "AM";
		}
		String format2 = String.format("%02d", minutes);
		String format = String.format("%02d", hour);
		String time = "Time: " + format + ":" + format2 +" "+ ampm;
		return time;
	}
	public void render(Graphics g) {
		g.setColor(Color.white);
		g.drawString(getTimer(), 10, 25);
		
	}
	public int getSeconds() {
		return seconds;
	}
	public void setSeconds(int seconds) {
		this.seconds = seconds;
	}
	public int getMinutes() {
		return minutes;
	}
	public void setMinutes(int minutes) {
		this.minutes = minutes;
	}
	public int getHour() {
		return hour;
	}
	public void setHour(int hour) {
		this.hour = hour;
	}	 
	
}
