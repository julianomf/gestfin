package br.com.nsol.gestfin.services.business;

import java.util.List;

import javax.ejb.Local;

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
import br.com.nsol.gestfin.exceptions.TechnicalException;

@Local
public interface CompanyService {
	
	/**
	 * Lista todos os tipos de negócio
	 * @return Lista
	 * @throws TechnicalException
	 */
	List<CompanyBusinessDTO> listBusiness() throws TechnicalException;
	
	/**
	 * Lista todas as quantidades de funcionários
	 * @return Lista
	 * @throws TechnicalException
	 */
	List<CompanyEmployeeDTO> listEmployees() throws TechnicalException;

	/**
	 * Lista todas as opções de venda da empresa
	 * @return Lista
	 * @throws TechnicalException
	 */
	List<CompanySellDTO> listSells() throws TechnicalException;

	/**
	 * Lista todos os tipos de tamanho de empresa
	 * @return Lista
	 * @throws TechnicalException
	 */
	List<CompanySizeDTO> listSizes() throws TechnicalException;

	/**
	 * Lista todos os tipos de problemas de empresa
	 * @return Lista
	 * @throws TechnicalException
	 */
	List<CompanyIssueDTO> listIssues() throws TechnicalException;
	
	/**
	 * Procura a empresa pelo e-mail do seu proprietário
	 * @param email
	 * @return 
	 * @throws TechnicalException
	 */
	CompanyDTO findCompanyByMail(String email) throws TechnicalException;

	/**
	 * Procura a empresa pelo seu identificador
	 * @param companyId
	 * @return 
	 * @throws TechnicalException
	 */
	CompanyDTO findCompanyById(Integer companyId) throws TechnicalException;

	/**
	 * Procura a empresa pelo seu CPF/CNPJ
	 * @param documentNumber
	 * @return 
	 * @throws TechnicalException
	 */
	CompanyDTO findCompanyByDocumentNumber(Long documentNumber) throws TechnicalException;
	
	/**
	 * Salva os dados da empresa
	 * @param company
	 * @throws TechnicalException
	 */
	Integer saveCompany(CompanyDTO company) throws TechnicalException;
		
	/**
	 * Lista todos os endereços da empresa
	 * @return Lista
	 * @throws TechnicalException
	 */
	List<CompanyAddressDTO> listCompanyAddress(Integer companyId) throws TechnicalException;

	/**
	 * Salva os dados de um endereço da empresa
	 * @param address
	 * @throws TechnicalException
	 */
	Integer saveCompanyAddress(CompanyAddressDTO address) throws TechnicalException;
		
    /**
     * Exclui um endereço da empresa
     * @param addressId
     * @throws TechnicalException
     */
    void deleteCompanyAddress(Integer addressId) throws TechnicalException;

	/**
	 * Lista todos os contatos da empresa
	 * @return Lista
	 * @throws TechnicalException
	 */
	List<CompanyContactDTO> listCompanyContact(Integer companyId) throws TechnicalException;

	/**
	 * Salva os dados de um contato da empresa
	 * @param contact
	 * @throws TechnicalException
	 */
	Integer saveCompanyContact(CompanyContactDTO contact) throws TechnicalException;
		
    /**
     * Exclui um contato da empresa
     * @param contactId
     * @throws TechnicalException
     */
    void deleteCompanyContact(Integer contactId) throws TechnicalException;

	/**
	 * Procura os dados da contabilidade da empresa
	 * @param companyId
	 * @return 
	 * @throws TechnicalException
	 */
	CompanyAccountancyDTO findCompanyAccountancy(Integer companyId) throws TechnicalException;
	
	/**
	 * Salva os dados da contabilidade da empresa
	 * @param accountancy
	 * @throws TechnicalException
	 */
	void saveCompanyAccountancy(CompanyAccountancyDTO accountancy) throws TechnicalException;

	/**
	 * Pesquisa dados da contabilidade da empresa
	 * @param companyId
	 * @return 
	 * @throws TechnicalException
	 */
	CompanyQuestionaireDTO findCompanyQuestionaire(Integer companyId) throws TechnicalException;
	
	/**
	 * Salva o "questionário" da empresa
	 * @param questionaire
	 * @throws TechnicalException
	 */
	void saveCompanyQuestionaire(CompanyQuestionaireDTO questionaire) throws TechnicalException;

	/**
	 * Lista os problemas da empresa
	 * @param companyId
	 * @return 
	 * @throws TechnicalException
	 */
	List<CompanyIssueDTO> listCompanyQuestionaireIssues(Integer companyId) throws TechnicalException;
	
	/**
	 * Salva os problemas da empresa
	 * @param companyId
	 * @param issues
	 * @throws TechnicalException
	 */
	void saveCompanyQuestionaireIssues(Integer companyId, List<CompanyIssueDTO> issues) throws TechnicalException;

	/**
	 * Finaliza o processo de adesão da empresa
	 * @param company
	 * @throws TechnicalException
	 */
	void finishCompanyMembership(CompanyDTO company) throws TechnicalException;
}
