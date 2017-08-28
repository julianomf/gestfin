package br.com.nsol.gestfin.enums;

public enum BusinessExceptionType {
	CUSTOM_MESSAGE_EXCEPTION("custom.message.exception", "Custom message exception."),
	EMAIL_ALREADY_EXISTS("message.user.already.exists", "E-mail já cadastrado."),
	EMAIL_NOT_FOUND("message.email.not.found", "E-mail não cadastrado."),
	INVALID_TEMP_PASSWORD("message.temp_password.invalid", "Senha provisória inválida."),
	APPRAISAL_NOT_FOUND("appraisal.message.not_found", "Avaliação não encontrada."),
	INVALID_APPRAISAL_TEMPLATE("appraisal.message.invalid_template", "Template da avaliação não é válido."),
	APPRAISAL_PDF_TEMPLATE_EMPTY("appraisal.summary.pdf.template.empty", "Arquivo de template da impressão não parametrizado."),
	APPRAISAL_PDF_GENERATION_ERROR("appraisal.summary.pdf.generate.fail", "Falha ao gerar o arquivo PDF da avaliação."),
	MESSAGE_APPRAISAL_NOT_FOUND("message.appraisal.not.found", "Não foi possível encontrar a avaliação."),
	MESSAGE_APPRAISAL_ACCESS_DENIED("message.appraisal.access_denied", "Acesso negado à avaliação solicitada."),
	ERROR_SENDING_VEHICLE_TO_AUTOAVALIAR("error.sending.vehicle.to.autoavaliar", "Falha no envio do veículo para AutoAvaliar."),
	APPRAISAL_TOKEN_NOT_FOUND("error.appraisal.token.not_found", "Token de acesso não localizado."),
	APPRAISAL_TOKEN_EXPIRED("error.appraisal.token.expired", "Token de acesso expirado."),
	SVT_CARD_NOT_FOUND("error.svt_card.not_found", "Ficha SVT não localizada."),
	ABRADIT_NOT_FOUND("error.abradit.not_found", "Dados não localizados na Abradit."),
	ABRADIT_TECHNICAL_ERROR("error.abradit.technical_error", "Erro técnico na chamada Abradit."),
	TERMINAL_NUMBER_EXISTS("message.terminal.number.already.exists", "Número de Terminal já cadastrado.");

	private final String bundleKey;
	private final String message;

	public String getBundleKey() {
		return bundleKey;
	}

	public String getMessage() {
		return message;
	}

	/**
	 * @param text
	 */
	private BusinessExceptionType(final String bundleKey, final String message) {
		this.bundleKey = bundleKey;
		this.message = message;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Enum#toString()
	 */
	@Override
	public String toString() {
		return bundleKey;
	}
}
