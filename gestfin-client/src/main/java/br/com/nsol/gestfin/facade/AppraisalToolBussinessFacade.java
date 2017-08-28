package br.com.nsol.gestfin.facade;

import java.util.List;

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
import br.com.nsol.gestfin.exceptions.BaseException;
import br.com.nsol.gestfin.exceptions.BusinessException;
import br.com.nsol.gestfin.exceptions.TechnicalException;

public interface AppraisalToolBussinessFacade {
	String NAME = "AppraisalToolBussinessFacade";

	/**
	 * 
	 * @param filters
	 * @return
	 * @throws TechnicalException
	 */
	List<UserDTO> listUsers(UserDTO filters) throws TechnicalException;

	/**
	 * 
	 * @param userMail
	 * @return BusinessUserDTO
	 */
	UserDTO findUserByMail(String userMai) throws TechnicalException;
	
	/**
	 * 
	 * @param userId
	 * @return BusinessUserDTO
	 */
	UserDTO findUserById(Integer userId) throws TechnicalException;

	/**
	 * 
	 * @param user
	 * @throws TechnicalException
	 */
	void updateUser(UserDTO user) throws TechnicalException;

	/**
	 * 
	 * @param user
	 * @throws BusinessException
	 * @throws TechnicalException
	 */
	Integer insertUserAndPassword(UserDTO user) throws BusinessException, TechnicalException;
	
	/**
	 * 
	 * @param user
	 * @throws TechnicalException
	 */
	void updateUserAndPassword(UserDTO user) throws TechnicalException;

	/**
	 * 
	 * @param user
	 * @throws TechnicalException
	 */
	void insertUser(UserDTO user) throws BusinessException, TechnicalException;
	
	/**
	 * Valida o usuário e senha
	 * @param email Id do usuário
	 * @param password Senha do usuário
	 * @return True se a senha deve ser recadastrada
	 * @throws InvalidUserPasswordException Usuário ou senha invalidos
	 * @throws TechnicalException Erro do sistema
	 */
	String validadeUserPassword(String email, String password) throws TechnicalException;
	
	/**
	 * Altera a senha do usuário
	 * @param email ID para localização do usuário
	 * @param currentPassword Senha atual
	 * @param password Nova senha
	 * @throws TechnicalException 
	 */
	void updateUserPassword(String email, String currentPassword, String password) throws BusinessException, TechnicalException;
	
	/**
	 * Solicita uma nova senha e envia por email
	 * @param email ID do usuário
	 * @throws TechnicalException
	 */
	void recoverNewPassword(String email) throws BusinessException, TechnicalException;
	
	/**
	 * Busca dados de parametro
	 * @param parameterName
	 * @return
	 * @throws TechnicalException
	 */
	String findParameterValue(String parameterName) throws TechnicalException;

	/**
	 * 
	 * @return
	 * @throws TechnicalException
	 */
	List<ProfileDTO> listProfiles() throws TechnicalException;

	/**
<<<<<<< HEAD
	 * Lista todos os tipos de negócio
	 * @return Lista
	 * @throws TechnicalException
	 */
	List<CompanyBusinessDTO> listMembershipBusiness() throws TechnicalException;
	
	/**
	 * Lista todas as quantidades de funcionários
	 * @return Lista
	 * @throws TechnicalException
	 */
	List<CompanyEmployeeDTO> listMembershipEmployees() throws TechnicalException;

	/**
	 * Lista todas as opções de venda da empresa
	 * @return Lista
	 * @throws TechnicalException
	 */
	List<CompanySellDTO> listMembershipSells() throws TechnicalException;

	/**
	 * Lista todos os tipos de tamanho de empresa
	 * @return Lista
	 * @throws TechnicalException
	 */
	List<CompanySizeDTO> listMembershipSizes() throws TechnicalException;

	/**
	 * Lista todos os tipos de problemas de empresa
	 * @return Lista
	 * @throws TechnicalException
	 */
	List<CompanyIssueDTO> listMembershipIssues() throws TechnicalException;

	/**
	 * Lista todos os tipos de problemas de empresa
	 * @return Lista
	 * @throws TechnicalException
	 */
	List<StateDTO> listStates() throws TechnicalException;

	/**
	 * Lista todos os tipos de regime de tributação
	 * @return Lista
	 * @throws TechnicalException
	 */
	List<TaxRegimeDTO> listTaxRegimess() throws TechnicalException;

	/**
	 * Procura dados relacionados a um CEP
	 * @param postalCode
	 * @return
	 * @throws TechnicalException
	 */
	PostalCodeDTO findPostalCode(Integer postalCode) throws TechnicalException;

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
	 * @param companyId
	 * @throws TechnicalException
	 */
	void saveCompanyQuestionaire(CompanyQuestionaireDTO questionaire) throws TechnicalException;

	/**
	 * Busca todas as operadoras
	 * @return
	 * @throws BaseException
	 */
	List<OperatorDTO> listOperators() throws BaseException;
	
	/**
	 * Busca todos os Estabelecimentos
	 * @return
	 * @throws BaseException
	 */
	List<EstablishmentDTO> listEstablishments() throws BaseException;

	/**
	 * Busca todas as bandeiras de cartão
	 * @return
	 * @throws BaseException
	 */
	List<CardFlagDTO> listCardFlags() throws BaseException;
	
	/**
	 * Lista todos os tipos de transação
	 * @return
	 * @throws BaseException
	 */
	List<TransactionTypeDTO> listTransactionTypes() throws BaseException;
	
	/**
	 * Busca os terminais cadastrados, de acordo com o filtro selecionado
	 * @param filter TerminalDTO com os filtros selecionados na tela de consulta
	 * @return Lista de TerminalDTO encontrados na consulta
	 * @throws BaseException
	 */
	List<TerminalDTO> listTerminals(TerminalDTO filter) throws BaseException;

	void insertTerminal(TerminalDTO terminal) throws BaseException;

	void updateTerminal(TerminalDTO terminal) throws BaseException;

	void deleteTerminal(TerminalDTO terminal) throws BaseException;
	
	/**
	 * Busca as taxas cadastradas, de acordo com o filtro selecionado
	 * @param filter TaxDTO com os filtros selecionados na tela de consulta
	 * @return Lista de TaxDTO encontrados na consulta
	 * @throws BaseException
	 */
	List<TaxDTO> listTaxes(TaxDTO filter) throws BaseException;

	void insertTax(TaxDTO tax) throws BaseException;

	void updateTax(TaxDTO tax) throws BaseException;

	void deleteTax(TaxDTO tax) throws BaseException;

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
