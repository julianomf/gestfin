/**
 * 
 */
package br.com.nsol.gestfin.services.business;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import br.com.nsol.gestfin.dao.CompanyBusinessDAO;
import br.com.nsol.gestfin.dao.CompanyDAO;
import br.com.nsol.gestfin.dao.CompanyEmployeeDAO;
import br.com.nsol.gestfin.dao.CompanyIssueDAO;
import br.com.nsol.gestfin.dao.CompanySellDAO;
import br.com.nsol.gestfin.dao.CompanySizeDAO;
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
import br.com.nsol.gestfin.enums.CompanyStatusEnum;
import br.com.nsol.gestfin.exceptions.TechnicalException;

/**
 * @author 
 *
 */
@Stateless
public class CompanyServiceImpl implements CompanyService {

	@EJB
	private CompanyBusinessDAO companyBusinessDAO;
	@EJB
	private CompanyEmployeeDAO companyEmployeeDAO;
	@EJB
	private CompanySellDAO companySellDAO;
	@EJB
	private CompanySizeDAO companySizeDAO;
	@EJB
	private CompanyIssueDAO companyIssueDAO;
	@EJB
	private CompanyDAO companyDAO;
	
	@Override
	public List<CompanyBusinessDTO> listBusiness() throws TechnicalException {
		return companyBusinessDAO.listAll();
	}
	@Override
	public List<CompanyEmployeeDTO> listEmployees() throws TechnicalException {
		return companyEmployeeDAO.listAll();
	}
	@Override
	public List<CompanySellDTO> listSells() throws TechnicalException {
		return companySellDAO.listAll();
	}
	@Override
	public List<CompanySizeDTO> listSizes() throws TechnicalException {
		return companySizeDAO.listAll();
	}
	@Override
	public List<CompanyIssueDTO> listIssues() throws TechnicalException {
		return companyIssueDAO.listAll();
	}
	@Override
	public CompanyDTO findCompanyByMail(String email) throws TechnicalException {
		return companyDAO.findCompanyByMail(email);
	}
	@Override
	public CompanyDTO findCompanyById(Integer companyId) throws TechnicalException {
		return companyDAO.findCompanyById(companyId);
	}
	@Override
	public Integer saveCompany(CompanyDTO company) throws TechnicalException {
		if (company.getId() == null) {
			companyDAO.insertCompany(company);
		} else {
			companyDAO.updateCompany(company);
		}
		return company.getId();
	}
	@Override
	public List<CompanyAddressDTO> listCompanyAddress(Integer companyId) throws TechnicalException {
		return companyDAO.listCompanyAddress(companyId);
	}
	@Override
	public List<CompanyContactDTO> listCompanyContact(Integer companyId) throws TechnicalException {
		return companyDAO.listCompanyContact(companyId);
	}
	@Override
	public Integer saveCompanyAddress(CompanyAddressDTO address) throws TechnicalException {
		if (address.getId() == null) {
			companyDAO.insertCompanyAddress(address);
		} else {
			companyDAO.updateCompanyAddress(address);
		}
		return address.getId();
	}
	@Override
	public void deleteCompanyAddress(Integer addressId) throws TechnicalException {
		companyDAO.deleteCompanyAddress(addressId);
	}
	@Override
	public Integer saveCompanyContact(CompanyContactDTO contact) throws TechnicalException {
		if (contact.getId() == null) {
			companyDAO.insertCompanyContact(contact);
		} else {
			companyDAO.updateCompanyContact(contact);
		}
		return contact.getId();
	}
	@Override
	public void deleteCompanyContact(Integer contactId) throws TechnicalException {
		companyDAO.deleteCompanyContact(contactId);
	}
	@Override
	public CompanyAccountancyDTO findCompanyAccountancy(Integer companyId) throws TechnicalException {
		return companyDAO.findCompanyAccountancy(companyId);
	}
	@Override
	public void saveCompanyAccountancy(CompanyAccountancyDTO accountancy) throws TechnicalException {
		if (findCompanyAccountancy(accountancy.getCompanyId()) == null) {
			companyDAO.insertCompanyAccountancy(accountancy);
		} else {
			companyDAO.updateCompanyAccountancy(accountancy);
		}
	}
	@Override
	public CompanyQuestionaireDTO findCompanyQuestionaire(Integer companyId) throws TechnicalException {
		return companyDAO.findCompanyQuestionaire(companyId);
	}
	@Override
	public void saveCompanyQuestionaire(CompanyQuestionaireDTO questionaire) throws TechnicalException {
		if (findCompanyQuestionaire(questionaire.getCompanyId()) == null) {
			companyDAO.insertCompanyQuestionaire(questionaire);
		} else {
			companyDAO.updateCompanyQuestionaire(questionaire);
		}
	}
	@Override
	public List<CompanyIssueDTO> listCompanyQuestionaireIssues(Integer companyId) throws TechnicalException {
		return companyDAO.listCompanyQuestionaireIssues(companyId);
	}
	@Override
	public void saveCompanyQuestionaireIssues(Integer companyId, List<CompanyIssueDTO> issues)
			throws TechnicalException {
		companyDAO.deleteInvalidCompanyQuestionaireIssues(companyId, issues);
		companyDAO.insertCompanyQuestionaireIssues(companyId, issues);
	}
	@Override
	public void finishCompanyMembership(CompanyDTO company) throws TechnicalException {
		company.setStatus(CompanyStatusEnum.REGISTRATION_COMPLETED.getValue());
		companyDAO.finishCompanyMembership(company);
	}
	@Override
	public CompanyDTO findCompanyByDocumentNumber(Long documentNumber) throws TechnicalException {
		return companyDAO.findCompanyByDocumentNumber(documentNumber);
	}
	
}
