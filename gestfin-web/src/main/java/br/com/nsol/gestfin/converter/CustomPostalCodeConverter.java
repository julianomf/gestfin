package br.com.nsol.gestfin.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

/**
 * Converter utilizado para tratamento de CEP
 * 
 * @author 
 * 
 */
@FacesConverter("customPostalCodeConverter")
public class CustomPostalCodeConverter implements Converter {

	/**
	 * Get as object
	 */
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		if (value == null || value.trim().isEmpty()) {
			return null;
		}

		try {

			return Integer.parseInt(value.replaceAll("[^\\d]", ""));

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
		StringBuilder builder = new StringBuilder();
		newValue = String.format("%08d", value);
		builder.append(newValue.substring(0, 5))
				.append("-").append(newValue.substring(5, 8));
		newValue = builder.toString();
		
		return newValue;
	}
}