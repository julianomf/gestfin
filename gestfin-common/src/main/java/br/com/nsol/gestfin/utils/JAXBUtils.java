package br.com.nsol.gestfin.utils;

import java.util.GregorianCalendar;

import javax.xml.bind.JAXBElement;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;

/**
 * A utility class for converting objects between java.util.Date and
 * XMLGregorianCalendar types
 *
 */
public final class JAXBUtils {

	private JAXBUtils() {
	      //for sonar only
	}

	// DatatypeFactory creates new javax.xml.datatype Objects that map XML
	// to/from Java Objects.
	private static DatatypeFactory df = null;

	static {
		try {
			df = DatatypeFactory.newInstance();
		} catch (DatatypeConfigurationException e) {
			throw new IllegalStateException("Error while trying to obtain a new instance of DatatypeFactory", e);
		}
	}

	// Converts a java.util.Date into an instance of XMLGregorianCalendar
	public static XMLGregorianCalendar asXMLGregorianCalendar(java.util.Date date) {
		if (date == null) {
			return null;
		} else {
			GregorianCalendar gc = new GregorianCalendar();
			gc.setTimeInMillis(date.getTime());
			return df.newXMLGregorianCalendar(gc);
		}
	}

	// Converts an XMLGregorianCalendar to an instance of java.util.Date
	public static java.util.Date asDate(XMLGregorianCalendar xmlGC) {
		if (xmlGC == null) {
			return null;
		} else {
			return xmlGC.toGregorianCalendar().getTime();
		}
	}

	// Wraps data in JAXBElement object
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static <T> JAXBElement<T> wrap( String ns, String tag, T o ){
	    QName qtag = new QName( ns, tag );
	    Class<?> clazz = o.getClass();
	    return new JAXBElement( qtag, clazz, o );
	}
}