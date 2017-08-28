package br.com.nsol.gestfin.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

/**
 * Converter utilizado para tratamento de telefone
 * 
 * @author 
 * 
 */
@FacesConverter("customPhoneConverter")
public class CustomPhoneConverter implements Converter {

	/**
	 * Get as object
	 */
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		if (value == null || value.trim().isEmpty()) {
			return null;
		}

		try {

			return Long.parseLong(value.replaceAll("[^\\d]", ""));

		} catch (Exception ex) {
			throw new ConverterException("Não foi possível aplicar conversão de item com valor [" + value + "] no componente ["
					+ component.getId() + "]", ex);
		}
	}

	/**
	 * get as string
	 */
	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value == null) {
			return "";
		}
		
		String newValue = String.valueOf(value);
		if (newValue.length() <= 10) {
			StringBuilder builder = new StringBuilder();
			newValue = String.format("%010d", value);
			//(00) 0000-0000
			builder.append("(").append(newValue.substring(0, 2)).append(")")
        			.append(" ")
        			.append(newValue.substring(2, 6))
        			.append("-")
        			.append(newValue.substring(6, 10));
			newValue = builder.toString();
		} else {
			newValue = String.format("%011d", value);
			StringBuilder builder = new StringBuilder();
			//(00) 90000-0000
			builder.append("(").append(newValue.substring(0, 2)).append(")")
					.append(" ")
					.append(newValue.substring(2, 7))
					.append("-")
					.append(newValue.substring(7, 11));
			newValue = builder.toString();
		}
		
		return newValue;
	}
}