package br.com.nsol.gestfin.services.business;

import java.util.List;

import javax.ejb.Local;

import br.com.nsol.gestfin.dto.ProfileDTO;
import br.com.nsol.gestfin.dto.UserDTO;
import br.com.nsol.gestfin.exceptions.BusinessException;
import br.com.nsol.gestfin.exceptions.TechnicalException;

@Local
public interface UserService {
	/**
	 * Nome da classe
	 */
	String NAME = "UserService";
	
	/**
	 * Lista usuários registrados no sistema para exibição na manutenção do cadastro
	 * @param filters
	 * @param allowedProfiles
	 * @return
	 * @throws TechnicalException
	 */
	List<UserDTO> listUsers(UserDTO filters) throws TechnicalException;

	/**
	 * Procura os dados do usuário a partir do email fornecido
	 * @param email email gravado
	 * @return BusinessUserDTO
	 * @throws TechnicalException
	 */
	UserDTO findUserByMail(String email) throws TechnicalException;

	/**
	 * Procura os dados do usuário a partir do ID fornecido
	 * @param businessUserId id do usuário
	 * @return BusinessUserDTO
	 * @throws TechnicalException
	 */
	UserDTO findUserById(Integer userId) throws TechnicalException;

	/**
	 * Atualiza os dados de um usuário
	 * @param user
	 * @throws TechnicalException
	 */
	void updateUser(UserDTO user) throws TechnicalException;

	/**
	 * Atualiza os dados de um usuário incluindo sua senha
	 * @param user
	 * @throws TechnicalException
	 */
	void updateUserAndPassword(UserDTO user) throws TechnicalException;

	/**
	 * Insere um usuário
	 * @param user
	 * @throws TechnicalException
	 */
	void insertUser(UserDTO user) throws BusinessException, TechnicalException ;

	/**
	 * Insere um usuário e senha (sem envio de e-mail de senha)
	 * @param user
	 * @throws TechnicalException
	 */
	Integer insertUserAndPassword(UserDTO user) throws BusinessException, TechnicalException ;

	/**
	 * Valida o usuário e senha
	 * @param email Id do usuário
	 * @param password Senha do usuário
	 * @return Role do usuário
	 * @throws InvalidUserPasswordException Usuário ou senha invalidos
	 * @throws TechnicalException Erro do sistema
	 */
	String validadeUserPassword(String email, String password) throws TechnicalException;
	
	/**
	 * Altera a senha do usuário
	 * @param email ID para localização do usuário
	 * @param password Nova senha
	 * @throws TechnicalException 
	 */
	void updateUserPassword(String email, String password) throws TechnicalException;
	
	/**
	 * Solicita uma nova senha e envia por email
	 * @param email Id do usuario
	 * @throws TechnicalException
	 */
	void recoverNewPassword(String email) throws BusinessException, TechnicalException;

	/**
	 * 
	 * @return
	 * @throws TechnicalException
	 */
	List<ProfileDTO> listProfiles() throws TechnicalException;

}