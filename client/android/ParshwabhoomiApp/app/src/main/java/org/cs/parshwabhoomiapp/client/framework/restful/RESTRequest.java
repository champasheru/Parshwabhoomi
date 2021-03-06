/**
 * parshwabhoomi-server	10-Nov-2017:1:01:35 PM
 */
package org.cs.parshwabhoomiapp.client.framework.restful;

import java.util.Map;

/**
 * @author gayatri
 * git: champasheru dange.gayatri@gmail.com
 *
 */
public interface RESTRequest {
	public enum Method{
		GET,
		POST,
		PUT,
		DELETE
	}
	
	public enum ContentType{
		APPLICATION_JSON("application/json"),
		APPLICATION_XML("application/xml"),
		ALL("*/*");
		
		private String value;
		
		private ContentType(String value){
			this.value = value;
		}
		
		public String getValue(){
			return value;
		}
		
	}
	
	public void setUrl(String url);
	
	public String getUrl();
	
	public void setMethod(Method method);
	
	public Method getMethod();
	
	public void setContentType(ContentType contentType);
	
	public ContentType getContentType();
	
	public void setHeader(String key, String value);
	
	void setPayload(byte[] payload);
	
	byte[] getPayload();
	
	Map<String, String> getHeaders();
}
