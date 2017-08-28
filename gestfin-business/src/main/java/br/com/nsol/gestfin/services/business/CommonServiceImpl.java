package br.com.nsol.gestfin.services.business;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.apache.log4j.Logger;

import br.com.nsol.gestfin.dao.CommonDAO;
import br.com.nsol.gestfin.dao.TaxRegimeDAO;
import br.com.nsol.gestfin.dto.CardFlagDTO;
import br.com.nsol.gestfin.dto.EstablishmentDTO;
import br.com.nsol.gestfin.dto.OperatorDTO;
import br.com.nsol.gestfin.dto.PostalCodeDTO;
import br.com.nsol.gestfin.dto.StateDTO;
import br.com.nsol.gestfin.dto.TaxRegimeDTO;
import br.com.nsol.gestfin.dto.TransactionTypeDTO;
import br.com.nsol.gestfin.exceptions.BaseException;
import br.com.nsol.gestfin.exceptions.TechnicalException;

/**
 * Servicos comuns do sistema
 * @author 
 */
@Stateless(name=CommonService.NAME)
public class CommonServiceImpl implements CommonService {
	private static final Logger LOG = Logger.getLogger(CommonServiceImpl.class);
	
	@EJB
	private CommonDAO commonDAO;
	@EJB
	private TaxRegimeDAO taxRegimeDAO;

	@Override
	public List<StateDTO> listStates() throws TechnicalException {
		return commonDAO.listStates();
	}

	@Override
	public List<TaxRegimeDTO> listTaxRegimes() throws TechnicalException {
		return taxRegimeDAO.listAll();
	}

	@Override
	public PostalCodeDTO findPostalCode(Integer postalCode) throws TechnicalException {
		return commonDAO.findPostalCode(postalCode);
	}

	@Override
	public List<OperatorDTO> listOperators() throws BaseException {
		LOG.debug("UserService.listOperators");
		return commonDAO.listOperators();
	}

	@Override
	public List<EstablishmentDTO> listEstablishments() throws BaseException {
		LOG.debug("UserService.listEstablishments");
		return commonDAO.listEstablishments();
	}

	@Override
	public List<CardFlagDTO> listCardFlags() throws BaseException {
		LOG.debug("UserService.listCardFlags");
		return commonDAO.listCardFlags();
	}

	@Override
	public List<TransactionTypeDTO> listTransactionTypes() throws BaseException {
		LOG.debug("UserService.listTransactionTypes");
		return commonDAO.listTransactionTypes();
	}
}
