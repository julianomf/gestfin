package br.com.nsol.gestfin.converter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.ConverterException;
import javax.faces.convert.DateTimeConverter;
import javax.faces.convert.FacesConverter;

/**
 * Converter utilizado para inputs do tipo date (HTML5)
 * 
 * @author 
 * 
 */
@FacesConverter("customDateConverter")
public class CustomDateConverter extends DateTimeConverter {

	public CustomDateConverter() {
		setPattern("dd/MM/yyyy");
	}

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		if (value == null || value.trim().isEmpty()) {
			return null;
		}

		 if (value != null && value.length() != getPattern().length()) {
	        throw new ConverterException("Formato inválido. Obrigatório utilizar " + getPattern());
	     }
		 
		try {

			//-- Input do HTML5 devolve dados sempre no formato yyyy-MM-dd
			DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			return format.parse(value);

		} catch (Exception ex) {
	        throw new ConverterException("Formato inválido. Obrigatório utilizar " + getPattern());
		}
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value == null) {
			return "";
		}

		DateFormat df = new SimpleDateFormat(getPattern());
		return df.format(value);
	}
}