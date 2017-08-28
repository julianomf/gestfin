package br.com.nsol.gestfin.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

/**
 * Converter utilizado para tratamento de CNPJ apenas
 * 
 * @author 
 * 
 */
@FacesConverter("customCnpjConverter")
public class CustomCnpjConverter implements Converter {

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
		
		String newValue = String.format("%014d", value);
		StringBuilder builder = new StringBuilder();
		builder.append(newValue.substring(0, 2))
				.append(".").append(newValue.substring(2, 5))
				.append(".").append(newValue.substring(5, 8))
				.append("/").append(newValue.substring(8, 12))
				.append("-").append(newValue.substring(12, 14));
		newValue = builder.toString();
		
		return newValue;
	}
}