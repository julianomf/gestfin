package br.com.nsol.gestfin.utils;

import java.util.List;

public final class CsvUtil {

	private CsvUtil() {
	      //for sonar only
	}

	/**
     * Converte uma lista de Long para uma string CSV
     * @param list
     * @return
     */
    public static String longList2Csv(List<Long> list) {
      String csv = "";
      if (list != null && !list.isEmpty()) {
          for (Long id : list) {
            if (csv.length() > 0) {
                csv += ",";
            }
            csv += id;
          }
      }
      if (csv.isEmpty()) {
          return null;
      } else {
          return csv;
      }
    }

	/**
     * Converte uma lista de Integer para uma string CSV
     * @param list
     * @return
     */
    public static String integerList2Csv(List<Integer> list) {
      String csv = "";
      if (list != null && !list.isEmpty()) {
          for (Integer id : list) {
            if (csv.length() > 0) {
                csv += ",";
            }
            csv += id;
          }
      }
      if (csv.isEmpty()) {
          return null;
      } else {
          return csv;
      }
    }

    
	/**
     * Converte uma lista de String para uma string CSV
     * @param list
     * @return
     */
    public static String stringList2Csv(List<String> list) {
        String csv = "";
        if (list != null && !list.isEmpty()) {
            for (String id : list) {
              if (csv.length() > 0) {
                  csv += ",";
              }
              csv +=  "'" + id + "'";
            }
        }
        if (csv.isEmpty()) {
            return null;
        } else {
            return csv;
        }
      }
}