package br.com.nsol.gestfin.services.business;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.apache.log4j.Logger;

import br.com.nsol.gestfin.dao.TerminalDAO;
import br.com.nsol.gestfin.dto.TerminalDTO;
import br.com.nsol.gestfin.enums.BusinessExceptionType;
import br.com.nsol.gestfin.exceptions.BaseException;
import br.com.nsol.gestfin.exceptions.BusinessException;
import br.com.nsol.gestfin.exceptions.TechnicalException;

/**
 * @author 
 *
 */
@Stateless(name=TerminalService.NAME)
public class TerminalServiceImpl implements TerminalService {
	private static final Logger LOG = Logger.getLogger(TerminalServiceImpl.class);

	public static final String UK_TERMINAL_NUMBER = "UK_TerminalNumber";
	
	@EJB
	private TerminalDAO terminalDAO;
	
	@Override
	public List<TerminalDTO> listTerminals(TerminalDTO filters)
			throws TechnicalException {
		LOG.debug("TerminalService.listTerminals");
		return terminalDAO.listTerminals(filters);
	}

	@Override
	public TerminalDTO findTerminalById(Integer terminalId) throws TechnicalException {
		LOG.debug("TerminalService.findTerminalById: " + terminalId);
		return terminalDAO.findTerminalById(terminalId);
	}
	
	@Override
	public void updateTerminal(TerminalDTO terminal) throws BaseException {
		LOG.debug("TerminalService.updateTerminal");
		try {
			terminalDAO.updateTerminal(terminal);
    	} catch(TechnicalException te) {
    		LOG.error(te, te.getCause());
    		if(te.getCause().getMessage().contains(UK_TERMINAL_NUMBER)){
    			throw new BusinessException(BusinessExceptionType.TERMINAL_NUMBER_EXISTS);
    		}
    		throw te;
    	}
	}

	@Override
	public void insertTerminal(TerminalDTO terminal) throws BaseException {
		LOG.debug("TerminalService.insertTerminal");

		try {
			terminalDAO.insertTerminal(terminal);
		} catch(TechnicalException te) {
			LOG.error(te, te.getCause());
			if(te.getCause().getMessage().contains(UK_TERMINAL_NUMBER)){
				throw new BusinessException(BusinessExceptionType.TERMINAL_NUMBER_EXISTS);
			}
			throw te;
		}
	}
	
	@Override
	public void deleteTerminal(TerminalDTO terminal) throws BaseException {
		LOG.debug("TerminalService.deleteTerminal");
		terminalDAO.deleteTerminal(terminal);
	}

}
