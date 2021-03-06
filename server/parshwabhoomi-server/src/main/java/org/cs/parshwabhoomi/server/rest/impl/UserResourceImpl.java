/**
 * parshwabhoomi-server	29-Oct-2017:7:47:00 PM
 * gayatri
 * git: champasheru Gayatri Sirdeshmukh dange.gayatri@gmail.com
 */
package org.cs.parshwabhoomi.server.rest.impl;

import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.logging.log4j.LogManager;
import org.cs.parshwabhoomi.server.AppContext;
import org.cs.parshwabhoomi.server.dao.raw.impl.UserCredentialDaoImpl;
import org.cs.parshwabhoomi.server.dto.ErrorResponseDTO;
import org.cs.parshwabhoomi.server.dto.auth.UserLoginRequestDTO;
import org.cs.parshwabhoomi.server.rest.AbstractResource;
import org.cs.parshwabhoomi.server.rest.UserResource;
import org.cs.parshwabhoomi.server.utils.RestUtils;

/**
 * @author gayatri
 * git: champasheru Gayatri Sirdeshmukh dange.gayatri@gmail.com
 *
 */
@Path("/user")
public class UserResourceImpl extends AbstractResource implements UserResource {

	/* (non-Javadoc)
	 * @see org.cs.parshwabhoomi.server.rest.UserResource#login(org.cs.parshwabhoomi.server.dto.impl.UserLoginRequestDTO)
	 */
	@Override
	public Response login(UserLoginRequestDTO loginRequestDTO) {
		LogManager.getLogger().info("Validating UserCredential Login: "+loginRequestDTO.getUsername());
		
		Response response = null;
		UserCredentialDaoImpl userCredentialDaoImpl = null;
		try{
			userCredentialDaoImpl = (UserCredentialDaoImpl)AppContext.getDefaultContext().getDaoProvider().getDAO("UserCredentialDaoImpl");
			boolean isValid = userCredentialDaoImpl.isValidUser(loginRequestDTO.getUsername(), loginRequestDTO.getPassword());
			LogManager.getLogger().info("isValid: "+isValid);
			
			if(isValid){
				response = Response.ok().build();
			}else{
				ErrorResponseDTO dto = RestUtils.createUnauthorizedResponseDTO();
				response = Response.status(Status.UNAUTHORIZED).entity(dto).build();
			}
		}catch(Exception e){														
			LogManager.getLogger().error("Couldn't login: ", e);
			ErrorResponseDTO dto = RestUtils.createInternalServerErrorResponseDTO();
			response = Response.status(Status.INTERNAL_SERVER_ERROR).entity(dto).build();
		}finally {
			if(userCredentialDaoImpl != null){
				userCredentialDaoImpl.close();
			}
		}
		
		return response;
	}
}
