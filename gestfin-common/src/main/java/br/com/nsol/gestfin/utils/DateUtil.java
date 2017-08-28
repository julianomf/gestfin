package br.com.nsol.gestfin.utils;

import java.util.Calendar;
import java.util.Date;

public final class DateUtil {

	private DateUtil() {
	      //for sonar only
	}

	/**
     * Adiciona dias a uma data qualquer
     * @param list
     * @return
     */
    public static Date addDays(Date start, Integer days) {
		Calendar newDate = Calendar.getInstance();
		newDate.setTime(start);
		if (days != null) {
			newDate.add(Calendar.DATE, days);
		}
		return (Date) newDate.getTime().clone();
    }

	/**
     * Adiciona minutos a uma data qualquer
     * @param list
     * @return
     */
    public static Date addMinutes(Date start, Integer minutes) {
		Calendar newDate = Calendar.getInstance();
		newDate.setTime(start);
		if (minutes != null) {
			newDate.add(Calendar.MINUTE, minutes);
		}
		return (Date) newDate.getTime().clone();
    }

    /**
     * Obtém o primeiro horário válido em uma data qualquer
     * @param date
     * @return
     */
    public static Date firstHour(Date date) {
    	if(date == null) {
    		return null;
    	}
    	
		Calendar newDate = Calendar.getInstance();
		newDate.setTime(date);
		newDate.set(Calendar.AM_PM, Calendar.AM);
		newDate.set(Calendar.HOUR, 0);
		newDate.set(Calendar.MINUTE, 0);
		newDate.set(Calendar.SECOND, 0);
		newDate.set(Calendar.MILLISECOND, 0);
		
		return (Date) newDate.getTime().clone();
    }
    
    /**
     * Retorna o primeiro horário da data atual
     * @return
     */
    public static Date nowFirstHour() {
    	return firstHour(new Date());
    }
    
    /**
     * Obtém o último horário válido em uma data qualquer
     * @param date
     * @return
     */
    public static Date lastHour(Date date) {
    	if(date == null) { 
    		return null;
    	}
    	
		Calendar newDate = Calendar.getInstance();
		newDate.setTime(date);
		newDate.set(Calendar.AM_PM, Calendar.PM);
		newDate.set(Calendar.HOUR, 11);
		newDate.set(Calendar.MINUTE, 59);
		newDate.set(Calendar.SECOND, 59);
		newDate.set(Calendar.MILLISECOND, 0);
		
		return (Date) newDate.getTime().clone();
    }

    /**
     * Retorna o último horário da data atual
     * @return
     */
    public static Date nowLastHour() {
    	return lastHour(new Date());
    }
    
}