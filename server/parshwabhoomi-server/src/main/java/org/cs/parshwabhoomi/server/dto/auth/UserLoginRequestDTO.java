/**
 * parshwabhoomi-server	29-Oct-2017:7:39:52 PM
 * gayatri
 * git: champasheru Gayatri Sirdeshmukh dange.gayatri@gmail.com
 */
package org.cs.parshwabhoomi.server.dto.auth;

import org.cs.parshwabhoomi.server.dto.AbstractRequestDTO;

/**
 * @author gayatri
 * git: champasheru Gayatri Sirdeshmukh dange.gayatri@gmail.com
 *
 */
public class UserLoginRequestDTO extends AbstractRequestDTO {
	private String username;
	private String password;
	
	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}
	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}
	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}
}
