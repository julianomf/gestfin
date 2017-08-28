package br.com.nsol.gestfin.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;

import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.log4j.Logger;

import br.com.nsol.gestfin.dto.CompanyAccountancyDTO;
import br.com.nsol.gestfin.dto.CompanyAddressDTO;
import br.com.nsol.gestfin.dto.CompanyContactDTO;
import br.com.nsol.gestfin.dto.CompanyDTO;
import br.com.nsol.gestfin.dto.CompanyIssueDTO;
import br.com.nsol.gestfin.dto.CompanyQuestionaireDTO;
import br.com.nsol.gestfin.exceptions.TechnicalException;
import br.com.nsol.gestfin.factory.GenericDAO;
import br.com.nsol.gestfin.factory.MyBatisSessionManager;
import br.com.nsol.gestfin.types.DaoParameterEnum;

/**
 * Camada de acesso para as funcionalidade do usuário
 * 
 * @author 
 */
@Stateless
public class CompanyDAO extends GenericDAO<CompanyDAO> {
	private static final long serialVersionUID = 1L;
	private static final Logger LOG = Logger.getLogger(CompanyDAO.class);

	public CompanyDAO() {
		this(MyBatisSessionManager.getInstance());
	}

	/**
	 * Construtor da classe
	 * 
	 * @param daoType
	 *            Classe
	 * @param sf
	 *            Factory
	 */
	public CompanyDAO(SqlSessionFactory sf) {
		super(CompanyDAO.class, sf);
	}

	/**
	 * Procura a empresa pelo e-mail do seu proprietário
	 * @param email
	 * @return 
	 * @throws TechnicalException
	 */
	public CompanyDTO findCompanyByMail(String email) throws TechnicalException {
		LOG.debug("CompanyDAO.findCompanyByMail: " + email);

		Map<String, Object> keys = new HashMap<String, Object>();
		keys.put(DaoParameterEnum.PARAM_EMAIL.getValue(), email);
		CompanyDTO dto = (CompanyDTO) this.find("findCompanyByMail", keys);
		return dto;
	}

	/**
	 * Procura a empresa pelo seu identificador
	 * @param companyId
	 * @return 
	 * @throws TechnicalException
	 */
	public CompanyDTO findCompanyById(Integer companyId) throws TechnicalException {
		LOG.debug("CompanyDAO.findCompanyById: " + companyId);

		Map<String, Object> keys = new HashMap<String, Object>();
		keys.put(DaoParameterEnum.PARAM_COMPANY_ID.getValue(), companyId);
		CompanyDTO dto = (CompanyDTO) this.find("findCompanyById", keys);
		return dto;
	}

	/**
	 * Procura a empresa pelo seu CPF/CNPJ
	 * @param documentNumber
	 * @return 
	 * @throws TechnicalException
	 */
	public CompanyDTO findCompanyByDocumentNumber(Long documentNumber) throws TechnicalException {
		LOG.debug("CompanyDAO.findCompanyByDocumentNumber: " + documentNumber);

		Map<String, Object> keys = new HashMap<String, Object>();
		keys.put(DaoParameterEnum.PARAM_COMPANY_DOCUMENT_NUMBER.getValue(), documentNumber);
		CompanyDTO dto = (CompanyDTO) this.find("findCompanyByDocumentNumber", keys);
		return dto;
	}

	/**
	 * Insere os dados da empresa
	 * @param company
	 * @throws TechnicalException
	 */
	public void insertCompany(CompanyDTO company) throws TechnicalException {
		LOG.debug("CompanyDAO.insertCompany: " + company);
		
		this.insert("insertCompany", company);
	}
		
	/**
	 * Atualiza os dados da empresa
	 * @param company
	 * @throws TechnicalException
	 */
	public void updateCompany(CompanyDTO company) throws TechnicalException {
		LOG.debug("CompanyDAO.updateCompany: " + company);
		
		this.update("updateCompany", company);
	}

	/**
	 * Lista os endereços da empresa
	 * @param companyId
	 * @return 
	 * @throws TechnicalException
	 */
	@SuppressWarnings("unchecked")
	public List<CompanyAddressDTO> listCompanyAddress(Integer companyId) throws TechnicalException {
		LOG.debug("CompanyDAO.listCompanyAddress: " + companyId);

		Map<String, Object> keys = new HashMap<String, Object>();
		keys.put(DaoParameterEnum.PARAM_COMPANY_ID.getValue(), companyId);
		List<CompanyAddressDTO> list = (List<CompanyAddressDTO>) this.list("listCompanyAddress", keys);
		return list;
	}

	/**
	 * Insere um endereço para empresa
	 * @param address
	 * @throws TechnicalException
	 */
	public void insertCompanyAddress(CompanyAddressDTO address) throws TechnicalException {
		LOG.debug("CompanyDAO.insertCompanyAddress: " + address);

		this.insert("insertCompanyAddress", address);
	}

	/**
	 * Atualiza um endereço para empresa
	 * @param address
	 * @throws TechnicalException
	 */
	public void updateCompanyAddress(CompanyAddressDTO address) throws TechnicalException {
		LOG.debug("CompanyDAO.updateCompanyAddress: " + address);

		this.insert("updateCompanyAddress", address);
	}

	/**
	 * Apaga um endereço da empresa
	 * @param addressId
	 * @throws TechnicalException
	 */
	public void deleteCompanyAddress(Integer addressId) throws TechnicalException {
		LOG.debug("CompanyDAO.deleteCompanyAddress: " + addressId);

		Map<String, Object> keys = new HashMap<String, Object>();
		keys.put(DaoParameterEnum.PARAM_ID.getValue(), addressId);
		this.insert("deleteCompanyAddress", addressId);
	}

	/**
	 * Lista os contatos da empresa
	 * @param companyId
	 * @return 
	 * @throws TechnicalException
	 */
	@SuppressWarnings("unchecked")
	public List<CompanyContactDTO> listCompanyContact(Integer companyId) throws TechnicalException {
		LOG.debug("CompanyDAO.listCompanyContact: " + companyId);

		Map<String, Object> keys = new HashMap<String, Object>();
		keys.put(DaoParameterEnum.PARAM_COMPANY_ID.getValue(), companyId);
		List<CompanyContactDTO> list = (List<CompanyContactDTO>) this.list("listCompanyContact", keys);
		return list;
	}

	/**
	 * Insere um contato para empresa
	 * @param contact
	 * @throws TechnicalException
	 */
	public void insertCompanyContact(CompanyContactDTO contact) throws TechnicalException {
		LOG.debug("CompanyDAO.insertCompanyContact: " + contact);

		this.insert("insertCompanyContact", contact);
	}

	/**
	 * Atualiza um contato para empresa
	 * @param contact
	 * @throws TechnicalException
	 */
	public void updateCompanyContact(CompanyContactDTO contact) throws TechnicalException {
		LOG.debug("CompanyDAO.updateCompanyContact: " + contact);

		this.insert("updateCompanyContact", contact);
	}

	/**
	 * Apaga um contato da empresa
	 * @param contactId
	 * @throws TechnicalException
	 */
	public void deleteCompanyContact(Integer contactId) throws TechnicalException {
		LOG.debug("CompanyDAO.deleteCompanyContact: " + contactId);

		Map<String, Object> keys = new HashMap<String, Object>();
		keys.put(DaoParameterEnum.PARAM_ID.getValue(), contactId);
		this.insert("deleteCompanyContact", contactId);
	}

	/**
	 * Pesquisa dados da contabilidade da empresa
	 * @param companyId
	 * @return 
	 * @throws TechnicalException
	 */
	public CompanyAccountancyDTO findCompanyAccountancy(Integer companyId) throws TechnicalException {
		LOG.debug("CompanyDAO.findCompanyAccountancy: " + companyId);

		Map<String, Object> keys = new HashMap<String, Object>();
		keys.put(DaoParameterEnum.PARAM_COMPANY_ID.getValue(), companyId);
		CompanyAccountancyDTO dto = (CompanyAccountancyDTO) this.find("findCompanyAccountancy", keys);
		return dto;
	}

	/**
	 * Insere dados da contabilidade da empresa
	 * @param accountancy
	 * @throws TechnicalException
	 */
	public void insertCompanyAccountancy(CompanyAccountancyDTO accountancy) throws TechnicalException {
		LOG.debug("CompanyDAO.insertCompanyAccountancy: " + accountancy);

		this.insert("insertCompanyAccountancy", accountancy);
	}

	/**
	 * Atualiza dados da contabilidade da empresa
	 * @param accountancy
	 * @throws TechnicalException
	 */
	public void updateCompanyAccountancy(CompanyAccountancyDTO accountancy) throws TechnicalException {
		LOG.debug("CompanyDAO.updateCompanyAccountancy: " + accountancy);

		this.insert("updateCompanyAccountancy", accountancy);
	}

	/**
	 * Pesquisa dados da contabilidade da empresa
	 * @param companyId
	 * @return 
	 * @throws TechnicalException
	 */
	public CompanyQuestionaireDTO findCompanyQuestionaire(Integer companyId) throws TechnicalException {
		LOG.debug("CompanyDAO.findCompanyQuestionaire: " + companyId);

		Map<String, Object> keys = new HashMap<String, Object>();
		keys.put(DaoParameterEnum.PARAM_COMPANY_ID.getValue(), companyId);
		CompanyQuestionaireDTO dto = (CompanyQuestionaireDTO) this.find("findCompanyQuestionaire", keys);
		return dto;
	}
	
	/**
	 * Insere o registro do "questionário" da empresa
	 * @param companyId
	 * @throws TechnicalException
	 */
	public void insertCompanyQuestionaire(CompanyQuestionaireDTO questionaire) throws TechnicalException {
		LOG.debug("CompanyDAO.insertCompanyQuestionaire: " + questionaire);

		this.insert("insertCompanyQuestionaire", questionaire);
	}

	/**
	 * Salva item "Tamanho" do questionário da empresa
	 * @param companyId
	 * @param sizeId
	 * @throws TechnicalException
	 */
	public void updateCompanyQuestionaire(CompanyQuestionaireDTO questionaire) throws TechnicalException {
		LOG.debug("CompanyDAO.updateCompanyQuestionaire: " + questionaire);

		this.insert("updateCompanyQuestionaire", questionaire);
	}

	/**
	 * Lista os problemas da empresa
	 * @param companyId
	 * @return 
	 * @throws TechnicalException
	 */
	@SuppressWarnings("unchecked")
	public List<CompanyIssueDTO> listCompanyQuestionaireIssues(Integer companyId) throws TechnicalException {
		LOG.debug("CompanyDAO.listCompanyQuestionaireIssues: " + companyId);

		Map<String, Object> keys = new HashMap<String, Object>();
		keys.put(DaoParameterEnum.PARAM_COMPANY_ID.getValue(), companyId);
		List<CompanyIssueDTO> list = (List<CompanyIssueDTO>) this.list("listCompanyQuestionaireIssues", keys);
		return list;
	}

	/**
	 * Remove os "problemas" da empresa que não estejam na lista
	 * @param companyId
	 * @param issues
	 * @throws TechnicalException
	 */
	public void deleteInvalidCompanyQuestionaireIssues(Integer companyId, List<CompanyIssueDTO> issues) throws TechnicalException {
		LOG.debug("CompanyDAO.deleteInvalidCompanyQuestionaireIssues: " + companyId + "," + issues);

		if(issues != null && !issues.isEmpty()) {
			Map<String, Object> keys = new HashMap<String, Object>();
			keys.put(DaoParameterEnum.PARAM_COMPANY_ID.getValue(), companyId);
			keys.put(DaoParameterEnum.PARAM_COMPANY_ISSUES.getValue(), issues);
			this.insert("deleteInvalidCompanyQuestionaireIssues", keys);
		}
	}

	/**
	 * Adiciona os "problemas" da empresa 
	 * @param companyId
	 * @param issues
	 * @throws TechnicalException
	 */
	public void insertCompanyQuestionaireIssues(Integer companyId, List<CompanyIssueDTO> issues) throws TechnicalException {
		LOG.debug("CompanyDAO.insertCompanyQuestionaireIssues: " + companyId + "," + issues);

		if(issues != null && !issues.isEmpty()) {
			Map<String, Object> keys = new HashMap<String, Object>();
			keys.put(DaoParameterEnum.PARAM_COMPANY_ID.getValue(), companyId);
			keys.put(DaoParameterEnum.PARAM_COMPANY_ISSUES.getValue(), issues);
			this.insert("insertCompanyQuestionaireIssues", keys);
		}
	}

	/**
	 * Finaliza o processo de adesão da empresa
	 * @param company
	 * @throws TechnicalException
	 */
	public void finishCompanyMembership(CompanyDTO company) throws TechnicalException {
		LOG.debug("CompanyDAO.finishCompanyMembership: " + company);
		
		this.update("finishCompanyMembership", company);
	}
}
