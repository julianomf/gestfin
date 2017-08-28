package br.com.nsol.gestfin.utils;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;
import javax.mail.util.ByteArrayDataSource;

public class EmailUtil {

	private String host;
	private String port;
	private String userName;
	private String password;
	private Properties properties;
	private Session session;
	private String startTlsEnable;
	private String auth;

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the startTlsEnable
	 */
	public String getStartTlsEnable() {
		return startTlsEnable;
	}

	/**
	 * @param startTlsEnable the startTlsEnable to set
	 */
	public void setStartTlsEnable(String startTlsEnable) {
		this.startTlsEnable = startTlsEnable;
	}

	/**
	 * @return the auth
	 */
	public String getAuth() {
		return auth;
	}

	/**
	 * @param auth the auth to set
	 */
	public void setAuth(String auth) {
		this.auth = auth;
	}

	private void createSession() {
		if (session == null) {

			// sets SMTP server properties
			properties = new Properties();

			properties.put("mail.smtp.auth", this.auth);
			properties.put("mail.smtp.host", this.host);
			properties.put("mail.smtp.port", this.port);
			properties.put("mail.smtp.starttls.enable", this.startTlsEnable);
			properties.put("mail.mime.charset", "ISO-8859-1");
			
			//-- Usuário e senha só passa se o servidor requer autenticação
			if ("true".equalsIgnoreCase(this.auth)) {
				
				properties.put("mail.user", this.userName);
				properties.put("mail.password", this.password);

				// creates a new session with an authenticator
				Authenticator authenticator = new Authenticator() {
					public PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(userName, password);
					}
				};
				session = Session.getInstance(properties, authenticator);
				
			}else {
				session = Session.getInstance(properties);
			}

		}
	}

	public void sendEmail(String fromAddress, String toAddress, String subject, String body,
			List<Attachment> attachments) throws MessagingException {

		createSession();

		// creates a new e-mail message
		Message msg = new MimeMessage(this.session);

		msg.setFrom(new InternetAddress(fromAddress));
		InternetAddress[] toAddresses = { new InternetAddress(toAddress) };
		msg.setRecipients(Message.RecipientType.TO, toAddresses);
		try {
			msg.setSubject(MimeUtility.encodeText(subject, "ISO-8859-1", null));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		msg.setSentDate(new Date());

		// creates message part
		MimeBodyPart messageBodyPart = new MimeBodyPart();
		messageBodyPart.setContent(body, "text/html");

		// creates multi-part
		Multipart multipart = new MimeMultipart();
		multipart.addBodyPart(messageBodyPart);

		// adds attachments
		if (attachments != null && attachments.size() > 0) {
			for (Attachment attach : attachments) {

				DataSource dataSource = new ByteArrayDataSource(attach.getContents(), "application/octet-stream");
				MimeBodyPart attachPart = new MimeBodyPart();
				attachPart.setDataHandler(new DataHandler(dataSource));
				attachPart.setFileName(attach.getName());

				multipart.addBodyPart(attachPart);
			}
		}

		// sets the multi-part as e-mail's content
		msg.setContent(multipart);

		// sends the e-mail
		Transport.send(msg);

	}

	public class Attachment {
		private String name;
		private byte[] contents;

		public Attachment(String name, byte[] contents) {
			this.name = name;
			if (contents == null) {
				this.contents = new byte[0];
			} else {
				this.contents = Arrays.copyOf(contents, contents.length);
			}
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public byte[] getContents() {
			return contents;
		}

		public void setContents(byte[] contents) {
			if (contents == null) {
				this.contents = new byte[0];
			} else {
				this.contents = Arrays.copyOf(contents, contents.length);
			}
		}
	}
	
	public String buildBody(String originalText, Object sourceObject) {
		final Set<String> list = getPlaceHolders(originalText);

		String newText = originalText;
		for (String string : list) {
			newText = replacePlaceHolder(newText, string, sourceObject);
		}
		return newText;
	}

	private Set<String> getPlaceHolders(String text){
		final Pattern pattern = Pattern.compile("\\#\\{(.+?)\\}");
		final Matcher matcher = pattern.matcher(text);
		final Set<String> list = new HashSet<String>();
		 
		while (matcher.find()) {
		  list.add(matcher.group(1));
		}			

		return list;
	}
	
	private String replacePlaceHolder(String text, String placeHolder, Object sourceObject){
		if(text == null || text.isEmpty()){
			return "";
		}
		if (placeHolder == null || placeHolder.isEmpty()) {
			return text;
		}
		
		String result = text;
		try {
			PropertyInfo info = splitPlaceHolder(placeHolder);
			Class<? extends Object> value = sourceObject.getClass();
			List<String> listMethodsName = getClassMethods(value);
			if (listMethodsName.contains(info.getMethodName())) {
				Method property = value.getMethod(info.getMethodName());
				String propertyValue = String.valueOf(property.invoke(sourceObject));
				if (info.getPropertyFormatPattern() != null) {
					if ("number".equalsIgnoreCase(info.getPropertyType())) {
						DecimalFormat df = new DecimalFormat(info.getPropertyFormatPattern());
						propertyValue = df.format(property.invoke(sourceObject));
					} else if ("date".equalsIgnoreCase(info.getPropertyType())) {
						SimpleDateFormat sdf = new SimpleDateFormat(info.getPropertyFormatPattern());
						propertyValue = sdf.format(property.invoke(sourceObject));
					}
				}
				String s = "#{" + placeHolder + "}";
				result = text.replaceAll(Pattern.quote(s), propertyValue);
			}
		} catch (Exception e) {
			
		}
		return result;
	}

	private PropertyInfo splitPlaceHolder(String placeHolder) {
		String[] placeHolderParts = placeHolder.split(Pattern.quote("|"));
		
		PropertyInfo info = new PropertyInfo();
		info.setPropertyName("");
		info.setPropertyType("string");
		info.setPropertyFormatPattern(null);

		if (placeHolderParts.length >= 1) {
			info.setPropertyName(placeHolderParts[0]);
		}
		if (placeHolderParts.length >= 2) {
			info.setPropertyType(placeHolderParts[1]);
		}
		if (placeHolderParts.length >= 3) {
			info.setPropertyFormatPattern(placeHolderParts[2]);
		}

		return info;
	}
	
	private List<String> getClassMethods(Class<? extends Object> value) {
		List<String> listMethodsName = new ArrayList<String>();
		for (Method methodName : value.getDeclaredMethods()) {
			listMethodsName.add(methodName.getName());
		}
		return listMethodsName;
	}
	
	class PropertyInfo {
		
		private String propertyName;
		private String propertyType;
		private String propertyFormatPattern;

		/**
		 * Retorna o nome do método associado à property
		 * @return
		 */
		public String getMethodName() {
			return "get" + propertyName.substring(0, 1).toUpperCase() + propertyName.substring(1);
		}
		
		/**
		 * @return the propertyName
		 */
		public String getPropertyName() {
			return propertyName;
		}
		/**
		 * @param propertyName the propertyName to set
		 */
		public void setPropertyName(String propertyName) {
			this.propertyName = propertyName;
		}
		/**
		 * @return the propertyType
		 */
		public String getPropertyType() {
			return propertyType;
		}
		/**
		 * @param propertyType the propertyType to set
		 */
		public void setPropertyType(String propertyType) {
			this.propertyType = propertyType;
		}
		/**
		 * @return the propertyFormatPattern
		 */
		public String getPropertyFormatPattern() {
			return propertyFormatPattern;
		}
		/**
		 * @param propertyFormatPattern the propertyFormatPattern to set
		 */
		public void setPropertyFormatPattern(String propertyFormatPattern) {
			this.propertyFormatPattern = propertyFormatPattern;
		}
		
	}
	
}