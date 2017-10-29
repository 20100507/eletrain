package cn.edu.qqhru.train.utils;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class SyllabusUtils{
	@SuppressWarnings({ "unused", "deprecation" })
	public static List<String> getAfterDay(String startString,String endString) throws ParseException {  
      
		 Calendar calendar = Calendar.getInstance();  
	      SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");  
	      Date dateStart = dateFormat.parse(startString); 
	      Date dateEnd = dateFormat.parse(endString); 
	      if(dateStart.getTime()<new Date().getTime()&&new Date().getTime()<=dateEnd.getTime()){
	    	  dateStart=dateFormat.parse(new Date().toLocaleString());
	      }
	      calendar.setTime(dateStart); 
	      List<String> dates = new ArrayList<>();
	      for(int i=0;i<14;i++){
	    	  int day = calendar.get(Calendar.DATE);  
	    	  if(i==0){
	    		  calendar.set(Calendar.DATE, day);
	    	  }else{
	    		  calendar.set(Calendar.DATE, day+1);
	    	  }
	    	  Date time = calendar.getTime();
	    	  if(new Date().getTime()>dateEnd.getTime()){
	    		  dates.add("日");
	    	  }
	    	  else{
	    		  dates.add(time.getDate()+"日");
	    	  }
	      }
		return dates;
   }  
}