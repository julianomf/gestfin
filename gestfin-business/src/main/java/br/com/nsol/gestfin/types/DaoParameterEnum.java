package br.com.nsol.gestfin.types;

public enum DaoParameterEnum {

	PARAM_ID("PARAM_ID"),
	PARAM_USERID("PARAM_USERID"),
	PARAM_NAME("PARAM_NAME"),
	PARAM_EMAIL("PARAM_EMAIL"),
	PARAM_PASSWORD("PARAM_PASSWORD"),
	PARAM_RESET("PARAM_RESET"),
	PARAM_PROFILEID("PARAM_PROFILEID"),

	PARAM_POSTALCODE("PARAM_POSTALCODE"),
	
	PARAM_COMPANY_ID("PARAM_COMPANY_ID"),
	PARAM_COMPANY_DOCUMENT_NUMBER("PARAM_COMPANY_DOCUMENT_NUMBER"),
	PARAM_COMPANY_SIZE_ID("PARAM_COMPANY_SIZE_ID"),
	PARAM_COMPANY_EMPLOYEE_ID("PARAM_COMPANY_EMPLOYEE_ID"),
	PARAM_COMPANY_SELL_ID("PARAM_COMPANY_SELL_ID"),
	PARAM_COMPANY_BUSINESS_ID("PARAM_COMPANY_BUSINESS_ID"),
	PARAM_COMPANY_HAS_ISSUED_INVOICES("PARAM_COMPANY_HAS_ISSUED_INVOICES"),
	PARAM_COMPANY_ISSUES("PARAM_COMPANY_ISSUES"),
	
	
	PARAM_AA_BUY_PRICE("PARAM_AA_BUY_PRICE"),
	PARAM_AA_SELL_PRICE("PARAM_AA_SELL_PRICE"),
	PARAM_ADMIN_COSTS("PARAM_ADMIN_COSTS"),
	PARAM_ALLOWED_PROFILES("PARAM_ALLOWED_PROFILES"),
	PARAM_AOV_ID("PARAM_AOV_ID"),
	PARAM_AOV_PRICE("PARAM_AOV_PRICE"),
	PARAM_APPRAISAL_AREA_ID("PARAM_APPRAISAL_AREA_ID"),
	PARAM_APPRAISAL_CODE("PARAM_APPRAISAL_CODE"),
	PARAM_APPRAISAL_ID("PARAM_APPRAISAL_ID"), 
	PARAM_APPRAISAL_IDS("PARAM_APPRAISAL_IDS"),
	PARAM_APPRAISAL_ITEM_ID("PARAM_APPRAISAL_ITEM_ID"),
	PARAM_APPRAISAL_ITEM_IDS("PARAM_APPRAISAL_ITEM_IDS"),
	PARAM_APPRAISAL_PHOTO_ID("PARAM_APPRAISAL_PHOTO_ID"),
	PARAM_APPRAISAL_TEMPLATE_AREA_ID("PARAM_APPRAISAL_TEMPLATE_AREA_ID"),
	PARAM_AVERAGE_STOCK_TIME("PARAM_AVERAGE_STOCK_TIME"),


	PARAM_DEALER_GROUP_ID("PARAM_DEALER_GROUP_ID"),
	PARAM_DEALER_ID("PARAM_DEALER_ID"),
	PARAM_DEALER_PRICE("PARAM_DEALER_PRICE"), 
	PARAM_DIAS_RETENCAO("PARAM_DIAS_RETENCAO"),

	PARAM_EMAIL_MESSAGE_ID("PARAM_EMAIL_MESSAGE_ID"),
	PARAM_EQUIPMENT_IDS("PARAM_EQUIPMENT_IDS"),
	PARAM_EQUIPMENT_NAMES("PARAM_EQUIPMENT_NAMES"),

	PARAM_FILENAME("PARAM_FILENAME"),
	PARAM_FIPE_PRICE("PARAM_FIPE_PRICE"), 

	PARAM_LAST_APPRAISAL_STEP_EXEC("PARAM_LAST_APPRAISAL_STEP_EXEC"),
	PARAM_LAST_INSPECTION_STEP_EXEC("PARAM_LAST_INSPECTION_STEP_EXEC"),

	PARAM_MAX_ATTEMPTS("PARAM_MAX_ATTEMPTS"),
	PARAM_MODEL_VERSION_ID("PARAM_MODEL_VERSION_ID"),
	PARAM_MODEL_YEAR("PARAM_MODEL_YEAR"),
	PARAM_MODEL_YEAR_ID("PARAM_MODEL_YEAR_ID"),

	PARAM_NEW_APPRAISAL_ID("PARAM_NEW_APPRAISAL_ID"),
	PARAM_NEW_INSPECTION_STEP_EXEC("PARAM_NEW_INSPECTION_STEP_EXEC"),

	PARAM_OLD_APPRAISAL_ID("PARAM_OLD_APPRAISAL_ID"),

	PARAM_PHOTO_FILENAMES("PARAM_PHOTO_FILENAMES"),
	PARAM_PHOTO_TYPE("PARAM_PHOTO_TYPE"),

	PARAM_RATING_ID("PARAM_RATING_ID"), 
	PARAM_REGION_ID("PARAM_REGION_ID"),
	PARAM_REGION_PRICE("PARAM_REGION_PRICE"), 
	PARAM_REPAIR_COSTS("PARAM_REPAIR_COSTS"), 

	PARAM_SELECTED_OPTIONS_IDS("PARAM_SELECTED_OPTIONS_IDS"),
	PARAM_SELECTED_SUB_OPTIONS_IDS("PARAM_SELECTED_SUB_OPTIONS_IDS"),
	PARAM_SEND_ATTEMPTS("PARAM_SEND_ATTEMPTS"),
	PARAM_STATUS("PARAM_STATUS"),
	PARAM_STOCK_QUANTITY("PARAM_STOCK_QUANTITY"), 

	PARAM_TAX_COSTS("PARAM_TAX_COSTS"), 
	PARAM_TOKEN("PARAM_TOKEN"), 

	PARAM_USER_TYPE("PARAM_USER_TYPE"),
	PARAM_USER_EMAIL("PARAM_USER_EMAIL"),

	PARAM_VALUE("PARAM_VALUE"),
	PARAM_VEHICLE_BRAND_ID("PARAM_VEHICLE_BRAND_ID"),
	PARAM_VEHICLE_MODEL_ID("PARAM_VEHICLE_MODEL_ID"),
	PARAM_VEHICLE_PLATE("PARAM_VEHICLE_PLATE"),

	PARAM_WEBMOTORS_PRICE("PARAM_WEBMOTORS_PRICE"), 
	
	PARAM_TERMINAL_NUMBER("PARAM_TERMINAL_NUMBER"),
	PARAM_DESCRIPTION("PARAM_DESCRIPTION"),
	PARAM_OPERATOR_ID("PARAM_OPERATOR_ID"),
	PARAM_ESTABLISHMENT_ID("PARAM_ESTABLISHMENT_ID"),
	PARAM_CARD_FLAG_ID("PARAM_CARD_FLAG_ID"),
	PARAM_TRANSACTION_TYPE_ID("PARAM_TRANSACTION_TYPE_ID"),

	XXXX("XXXX");


	
	private String value;
	
	DaoParameterEnum(String value){
		this.value = value;
	}
	
	public String getValue(){
		return value;
	}
}
