package br.com.nsol.gestfin.services.business;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.apache.log4j.Logger;

import br.com.nsol.gestfin.dao.TaxDAO;
import br.com.nsol.gestfin.dto.TaxDTO;
import br.com.nsol.gestfin.enums.BusinessExceptionType;
import br.com.nsol.gestfin.exceptions.BaseException;
import br.com.nsol.gestfin.exceptions.BusinessException;
import br.com.nsol.gestfin.exceptions.TechnicalException;

@Stateless(name=TaxService.NAME)
public class TaxServiceImpl implements TaxService {
	private static final Logger LOG = Logger.getLogger(TaxServiceImpl.class);

	public static final String UK_TAX_NUMBER = "UK_TaxNumber";
	
	@EJB
	private TaxDAO taxDAO;
	
	@Override
	public List<TaxDTO> listTaxes(TaxDTO filter)
			throws TechnicalException {
		LOG.debug("TaxService.listTaxes");
		return taxDAO.listTaxes(filter);
	}

	@Override
	public TaxDTO findTaxById(Integer taxId) throws TechnicalException {
		LOG.debug("TaxService.findTaxById: " + taxId);
		return taxDAO.findTaxById(taxId);
	}
	
	@Override
	public void updateTax(TaxDTO tax) throws BaseException {
		LOG.debug("TaxService.updateTax");
		try {
			taxDAO.updateTax(tax);
    	} catch(TechnicalException te) {
    		LOG.error(te, te.getCause());
    		if(te.getCause().getMessage().contains(UK_TAX_NUMBER)){
    			throw new BusinessException(BusinessExceptionType.TERMINAL_NUMBER_EXISTS);
    		}
    		throw te;
    	}
	}

	@Override
	public void insertTax(TaxDTO tax) throws BaseException {
		LOG.debug("TaxService.insertTax");

		try {
			taxDAO.insertTax(tax);
		} catch(TechnicalException te) {
			LOG.error(te, te.getCause());
			if(te.getCause().getMessage().contains(UK_TAX_NUMBER)){
				throw new BusinessException(BusinessExceptionType.TERMINAL_NUMBER_EXISTS);
			}
			throw te;
		}
	}
	
	@Override
	public void deleteTax(TaxDTO tax) throws BaseException {
		LOG.debug("TaxService.deleteTax");
		taxDAO.deleteTax(tax);
	}

}
