package br.com.nsol.gestfin.integration;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import org.apache.log4j.Logger;

import br.com.nsol.gestfin.dto.CardFlagDTO;
import br.com.nsol.gestfin.dto.CompanyAccountancyDTO;
import br.com.nsol.gestfin.dto.CompanyAddressDTO;
import br.com.nsol.gestfin.dto.CompanyBusinessDTO;
import br.com.nsol.gestfin.dto.CompanyContactDTO;
import br.com.nsol.gestfin.dto.CompanyDTO;
import br.com.nsol.gestfin.dto.CompanyEmployeeDTO;
import br.com.nsol.gestfin.dto.CompanyIssueDTO;
import br.com.nsol.gestfin.dto.CompanyQuestionaireDTO;
import br.com.nsol.gestfin.dto.CompanySellDTO;
import br.com.nsol.gestfin.dto.CompanySizeDTO;
import br.com.nsol.gestfin.dto.EstablishmentDTO;
import br.com.nsol.gestfin.dto.OperatorDTO;
import br.com.nsol.gestfin.dto.PostalCodeDTO;
import br.com.nsol.gestfin.dto.ProfileDTO;
import br.com.nsol.gestfin.dto.StateDTO;
import br.com.nsol.gestfin.dto.TaxDTO;
import br.com.nsol.gestfin.dto.TaxRegimeDTO;
import br.com.nsol.gestfin.dto.TerminalDTO;
import br.com.nsol.gestfin.dto.TransactionTypeDTO;
import br.com.nsol.gestfin.dto.UserDTO;
import br.com.nsol.gestfin.enums.BusinessExceptionType;
import br.com.nsol.gestfin.exceptions.BaseException;
import br.com.nsol.gestfin.exceptions.BusinessException;
import br.com.nsol.gestfin.exceptions.TechnicalException;
import br.com.nsol.gestfin.facade.AppraisalToolBussinessFacade;
import br.com.nsol.gestfin.facade.AppraisalToolBussinessFacadeRemote;
import br.com.nsol.gestfin.services.business.CommonService;
import br.com.nsol.gestfin.services.business.CompanyService;
import br.com.nsol.gestfin.services.business.SystemParameterService;
import br.com.nsol.gestfin.services.business.TaxService;
import br.com.nsol.gestfin.services.business.TerminalService;
import br.com.nsol.gestfin.services.business.UserService;

/**
 * Camada responsavel  por centralizar o acesso a todos os servicos de negocio da ferramenta Appraisal Tool
 * @author 
 */	
@Local(AppraisalToolBussinessFacade.class)
@Remote(AppraisalToolBussinessFacadeRemote.class)
@Stateless(name = AppraisalToolBussinessFacade.NAME)
public class AppraisalToolBussinessFacadeImpl implements AppraisalToolBussinessFacade {
	private static final Logger LOG = Logger.getLogger(AppraisalToolBussinessFacadeImpl.class);
	
	@EJB
	private CommonService commonService;

	@EJB
	private UserService userService;
	
	@EJB
	private SystemParameterService systemParameterService;

	@EJB
	private CompanyService companyService;

	@EJB
	private TerminalService terminalService;
	
	@EJB
	private TaxService taxService;
	
	/* (non-Javadoc)
	 * @see br.com.nsol.gestfin.facade.AppraisalToolBussinessFacade#listUsers(br.com.nsol.gestfin.dto.UserDTO)
	 */
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	@Override
	public List<UserDTO> listUsers(UserDTO filters) throws TechnicalException {
		LOG.debug("AppraisalToolBussinessFacade.listUsers");
		return userService.listUsers(filters);
	}
	
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	@Override
	public UserDTO findUserByMail(String userMail) throws TechnicalException{
		LOG.debug("AppraisalToolBussinessFacade.findUserByMail");
		UserDTO result = null;
		try {
			result = userService.findUserByMail(userMail);
		} catch (TechnicalException e) {
			LOG.debug("AppraisalToolBussinessFacade.findBusinessUser.error", e);
			throw e;
		}
		return result;
	}

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	@Override
	public UserDTO findUserById(Integer userId) throws TechnicalException{
		LOG.debug("AppraisalToolBussinessFacade.findUserById: " + userId);
		UserDTO result = null;
		try {
			result = userService.findUserById(userId);
		} catch (TechnicalException e) {
			LOG.debug("AppraisalToolBussinessFacade.findBusinessUser.error", e);
			throw e;
		}
		return result;
	}

	/* (non-Javadoc)
	 * @see br.com.nsol.gestfin.facade.AppraisalToolBussinessFacade#updateUser(br.com.nsol.gestfin.dto.UserRegisterDTO)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	@Override
	public void updateUser(UserDTO user) throws TechnicalException {
		LOG.debug("AppraisalToolBussinessFacade.updateUser");
		userService.updateUser(user);
	}

	/* (non-Javadoc)
	 * @see br.com.nsol.gestfin.facade.AppraisalToolBussinessFacade#updateUserAndPassword(br.com.nsol.gestfin.dto.UserRegisterDTO)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	@Override
	public void updateUserAndPassword(UserDTO user) throws TechnicalException {
		LOG.debug("AppraisalToolBussinessFacade.updateUserAndPassword");
		userService.updateUserAndPassword(user);
	}

	/* (non-Javadoc)
	 * @see br.com.nsol.gestfin.facade.AppraisalToolBussinessFacade#insertUser(br.com.nsol.gestfin.dto.UserRegisterDTO)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	@Override
	public Integer insertUserAndPassword(UserDTO user) throws BusinessException, TechnicalException {
		LOG.debug("AppraisalToolBussinessFacade.insertUserAndPassword");
		return userService.insertUserAndPassword(user);
	}

	/* (non-Javadoc)
	 * @see br.com.nsol.gestfin.facade.AppraisalToolBussinessFacade#insertUser(br.com.nsol.gestfin.dto.UserRegisterDTO)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	@Override
	public void insertUser(UserDTO user) throws BusinessException, TechnicalException {
		LOG.debug("AppraisalToolBussinessFacade.insertUser");
		userService.insertUser(user);
	}
		
	/* (non-Javadoc)
	 * @see br.com.nsol.gestfin.facade.AppraisalToolBussinessFacade#validadeUserPassword(java.lang.String, java.lang.String)
	 */
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	@Override
	public String validadeUserPassword(String email, String password) throws TechnicalException {
		LOG.debug("AppraisalToolBussinessFacade.validadeUserPassword");
		String result = null;
		try {
			result = userService.validadeUserPassword(email, password);
		} catch (TechnicalException e) {
			LOG.debug("AppraisalToolBussinessFacade.validadeUserPassword.error", e);
			throw e;
		}
		return result;
	}

	/* (non-Javadoc)
	 * @see br.com.nsol.gestfin.facade.AppraisalToolBussinessFacade#updateUserPassword(java.lang.String, java.lang.String)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	@Override
	public void updateUserPassword(String email, String currentPassword, String password) throws BusinessException, TechnicalException {
		LOG.debug("AppraisalToolBussinessFacade.updateUserPassword");
		if (validadeUserPassword(email, currentPassword) == null) {
			throw new BusinessException(BusinessExceptionType.INVALID_TEMP_PASSWORD);
		}
		userService.updateUserPassword(email, password);
	}

	/* (non-Javadoc)
	 * @see br.com.nsol.gestfin.facade.AppraisalToolBussinessFacade#recoverNewPassword(java.lang.String)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	@Override
	public void recoverNewPassword(String email) throws BusinessException, TechnicalException {
		LOG.debug("AppraisalToolBussinessFacade.recoverNewPassword");
		userService.recoverNewPassword(email);
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<ProfileDTO> listProfiles() throws TechnicalException {
		LOG.debug("AppraisalToolBussinessFacade.listProfiles");
		return userService.listProfiles();
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<OperatorDTO> listOperators() throws BaseException {
		LOG.debug("AppraisalToolBussinessFacade.listOperators");
		return commonService.listOperators();
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<EstablishmentDTO> listEstablishments() throws BaseException {
		LOG.debug("AppraisalToolBussinessFacade.listEstablishments");
		return commonService.listEstablishments();
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<CardFlagDTO> listCardFlags() throws BaseException {
		LOG.debug("AppraisalToolBussinessFacade.listCardFlags");
		return commonService.listCardFlags();
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<TransactionTypeDTO> listTransactionTypes() throws BaseException {
		LOG.debug("AppraisalToolBussinessFacade.listTransactionTypes");
		return commonService.listTransactionTypes();
	}
	
	/* (non-Javadoc)
	 * @see br.com.nsol.gestfin.facade.AppraisalToolBussinessFacade#findParameterValue(java.lang.String)
	 */
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	@Override
	public String findParameterValue(String parameterName) throws TechnicalException {
		LOG.debug("AppraisalToolBussinessFacade.findParameterValue");
		return systemParameterService.findParameterValue(parameterName);
	}

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	@Override
	public List<CompanyBusinessDTO> listMembershipBusiness() throws TechnicalException {
		return companyService.listBusiness();
	}

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	@Override
	public List<CompanyEmployeeDTO> listMembershipEmployees() throws TechnicalException {
		return companyService.listEmployees();
	}

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	@Override
	public List<CompanySellDTO> listMembershipSells() throws TechnicalException {
		return companyService.listSells();
	}

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	@Override
	public List<CompanySizeDTO> listMembershipSizes() throws TechnicalException {
		return companyService.listSizes();
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<CompanyIssueDTO> listMembershipIssues() throws TechnicalException {
		return companyService.listIssues();
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<StateDTO> listStates() throws TechnicalException {
		return commonService.listStates();
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<TaxRegimeDTO> listTaxRegimess() throws TechnicalException {
		return commonService.listTaxRegimes();
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public PostalCodeDTO findPostalCode(Integer postalCode) throws TechnicalException {
		return commonService.findPostalCode(postalCode);
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public CompanyDTO findCompanyByMail(String email) throws TechnicalException {
		return companyService.findCompanyByMail(email);
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public CompanyDTO findCompanyById(Integer companyId) throws TechnicalException {
		return companyService.findCompanyById(companyId);
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Integer saveCompany(CompanyDTO company) throws TechnicalException {
		return companyService.saveCompany(company);
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<CompanyAddressDTO> listCompanyAddress(Integer companyId) throws TechnicalException {
		return companyService.listCompanyAddress(companyId);
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Integer saveCompanyAddress(CompanyAddressDTO address) throws TechnicalException {
		return companyService.saveCompanyAddress(address);
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void deleteCompanyAddress(Integer addressId) throws TechnicalException {
		companyService.deleteCompanyAddress(addressId);		
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<CompanyContactDTO> listCompanyContact(Integer companyId) throws TechnicalException {
		return companyService.listCompanyContact(companyId);
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Integer saveCompanyContact(CompanyContactDTO contact) throws TechnicalException {
		return companyService.saveCompanyContact(contact);
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void deleteCompanyContact(Integer contactId) throws TechnicalException {
		companyService.deleteCompanyContact(contactId);		
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public CompanyAccountancyDTO findCompanyAccountancy(Integer companyId) throws TechnicalException {
		return companyService.findCompanyAccountancy(companyId);
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void saveCompanyAccountancy(CompanyAccountancyDTO accountancy) throws TechnicalException {
		companyService.saveCompanyAccountancy(accountancy);
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public CompanyQuestionaireDTO findCompanyQuestionaire(Integer companyId) throws TechnicalException {
		return companyService.findCompanyQuestionaire(companyId);
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void saveCompanyQuestionaire(CompanyQuestionaireDTO questionaire) throws TechnicalException {
		companyService.saveCompanyQuestionaire(questionaire);
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<TerminalDTO> listTerminals(TerminalDTO filter) throws BaseException {
		LOG.debug("AppraisalToolBussinessFacade.listTerminals");
		return terminalService.listTerminals(filter);
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void insertTerminal(TerminalDTO terminal) throws BaseException {
		LOG.debug("AppraisalToolBussinessFacade.insertTerminal");
		terminalService.insertTerminal(terminal);
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void updateTerminal(TerminalDTO terminal) throws BaseException {
		LOG.debug("AppraisalToolBussinessFacade.updateTerminal");
		terminalService.updateTerminal(terminal);
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void deleteTerminal(TerminalDTO terminal) throws BaseException {
		LOG.debug("AppraisalToolBussinessFacade.deleteTerminal");
		terminalService.deleteTerminal(terminal);
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<TaxDTO> listTaxes(TaxDTO filter) throws BaseException {
		LOG.debug("AppraisalToolBussinessFacade.listTaxes");
		return taxService.listTaxes(filter);
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void insertTax(TaxDTO tax) throws BaseException {
		LOG.debug("AppraisalToolBussinessFacade.insertTax");
		taxService.insertTax(tax);
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void updateTax(TaxDTO tax) throws BaseException {
		LOG.debug("AppraisalToolBussinessFacade.updateTax");
		taxService.updateTax(tax);
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void deleteTax(TaxDTO tax) throws BaseException {
		LOG.debug("AppraisalToolBussinessFacade.deleteTax");
		taxService.deleteTax(tax);
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public List<CompanyIssueDTO> listCompanyQuestionaireIssues(Integer companyId) throws TechnicalException {
		return companyService.listCompanyQuestionaireIssues(companyId);
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void saveCompanyQuestionaireIssues(Integer companyId, List<CompanyIssueDTO> issues)
			throws TechnicalException {
		companyService.saveCompanyQuestionaireIssues(companyId, issues);
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void finishCompanyMembership(CompanyDTO company) throws TechnicalException {
		companyService.finishCompanyMembership(company);
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public CompanyDTO findCompanyByDocumentNumber(Long documentNumber) throws TechnicalException {
		return companyService.findCompanyByDocumentNumber(documentNumber);
	}
}
