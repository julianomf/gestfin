package br.com.nsol.gestfin.converter;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.Locale;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

/**
 * Converter utilizado para tratar campos de taxacom 4 casas decimais
 * 
 * @author
 * 
 */
@FacesConverter("customTaxConverter")
public class CustomTaxConverter implements Converter {
	NumberFormat nf = NumberFormat.getInstance(new Locale("pt", "BR"));
	
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String valorTela) throws ConverterException {
		if (valorTela == null || valorTela.toString().trim().equals("")) {
			return new BigDecimal(0.0d);
		} else {
			try {
				nf.setMinimumFractionDigits(2);
				Number newNumber = nf.parse(valorTela);
				BigDecimal bdValue = new BigDecimal(newNumber.doubleValue());
				return bdValue.setScale(2, RoundingMode.HALF_UP);
			} catch (Exception e) {
				return new BigDecimal(0.0d);
			}
		}
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object valorTela) throws ConverterException {
		if (valorTela == null || valorTela.toString().trim().equals("")) {
			return "0,00";
		} else {
			nf.setMinimumFractionDigits(2);
			return nf.format(new BigDecimal(valorTela.toString()));
		}
	}
}