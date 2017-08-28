package br.com.nsol.gestfin.converter;

import java.math.BigDecimal;
import java.text.DecimalFormat;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

/**
 * Converter utilizado para tratar campos moeda com 2 casas decimais
 * 
 * @author 
 * 
 */
@FacesConverter("customCurrencyConverter")
public class CustomCurrencyConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		if (value == null || value.trim().isEmpty()) {
			return null;
		}

		try {

			String newValue = value.replaceAll("[^\\d]", "");
			return new BigDecimal(newValue).divide(new BigDecimal(100));

		} catch (Exception ex) {
			throw new ConverterException("Não foi possível aplicar conversão de item com valor [" + value + "] no componente ["
					+ component.getId() + "]", ex);
		}
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if (value == null) {
			return "";
		}

		DecimalFormat df = new DecimalFormat("###,###,##0.00");
		return df.format(value);
	}
}