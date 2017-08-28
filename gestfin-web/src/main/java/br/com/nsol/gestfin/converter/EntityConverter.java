package br.com.nsol.gestfin.converter;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;

import javax.faces.component.UIComponent;
import javax.faces.component.UISelectItems;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import br.com.nsol.gestfin.dto.BaseDTO;

/**
 * Converter utilizado para todos os objetos que implementam {@link BaseDTO},
 * ou que o método toString() esteja implementado e retornando um valor único
 * para cada instância
 * 
 * @author 
 * 
 */
@FacesConverter("entityConverter")
public class EntityConverter implements Converter {

    @SuppressWarnings("unchecked")
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
	if (value == null || value.trim().isEmpty()) {
	    return null;
	}

	try {
	    String id = String.valueOf(value);
	    Collection<Object> items = new ArrayList<Object>();
	    for (UIComponent child : component.getChildren()) {
		if (child instanceof UISelectItems) {
		    items.addAll((Collection<BaseDTO>) child.getAttributes().get("value"));
		}
	    }
	    return findById(items, id);
	} catch (Exception ex) {
	    String error = "Não foi possível aplicar conversão de item com valor [" + value + "] no componente [" + component.getId() + "]";
	    throw new ConverterException(error, ex);
	}
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
	if (value == null) {
	    return "";
	}

	return this.getIdByReflection(value).toString();
    }

    private Object findById(Collection<Object> collection, String idToFind) {
	for (Object obj : collection) {
	    String id = this.getIdByReflection(obj);
	    if (id.equals(idToFind)) {
		return obj;
	    }
	}

	return null;
    }

    private String getIdByReflection(Object bean) {
	try {
	    if (bean instanceof String) {
		return "0";
	    }
	    Field idField = bean.getClass().getDeclaredField("id");
	    idField.setAccessible(true);
	    return String.valueOf(idField.get(bean));
	} catch (NoSuchFieldException ex) {
	    return bean.toString();
	} catch (Exception ex) {
	    throw new ConverterException("Não foi possível obter a propriedade 'id' do item", ex);
	}
    }
}