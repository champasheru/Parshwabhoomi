/**
 * parshwabhoomi-server	10-Nov-2017:5:59:27 PM
 */
package org.cs.parshwabhoomi.server.core;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.cs.parshwabhoomi.server.AppContext;
import org.cs.parshwabhoomi.server.dto.adapter.GoogleSearchResultResponseDTOAdapter;
import org.cs.parshwabhoomi.server.dto.impl.SearchResultResponseDTO;
import org.cs.parshwabhoomi.server.restclient.RESTRequest;
import org.cs.parshwabhoomi.server.restclient.RESTRequest.Method;
import org.cs.parshwabhoomi.server.restclient.impl.DefaultRESTClient;
import org.cs.parshwabhoomi.server.restclient.impl.RESTRequestImpl;

/**
 * @author saurabh
 * git: champasheru saurabh.cse2@gmail.com
 *
 */
public class GoogleSearchService {
	private static final String GOOGLE_CUSTOM_SEARCH_API_BASE_URI="https://www.googleapis.com/customsearch/v1?";
    private static final String GOOGLE_MAPS_GEOCODING_API_BASE_URI="https://maps.googleapis.com/maps/api/geocode/json?";
    
    public List<SearchResultResponseDTO> getResults(String query){
    	List<SearchResultResponseDTO> searchResults = new ArrayList<>();
    	
    	try {
			String searchUrl = GOOGLE_CUSTOM_SEARCH_API_BASE_URI+
								"key="+AppContext.getDefaultContext().getProperty(AppContext.GOOGLE_API_KEY)
								+"&cx="+AppContext.getDefaultContext().getProperty(AppContext.GOOGLE_CSE_ID)
								+"&q="+URLEncoder.encode(query,"UTF-8");

//			RESTRequest request = new RESTRequestImpl();
//	    	request.setMethod(Method.GET);
//			request.setUrl(searchUrl);
//			
//			DefaultRESTClient restClient = new DefaultRESTClient();
			//restClient.connect(request);
//			if(restClient.getStatusCode() == 200){
//				InputStream inputStream = restClient.getInputStream();
//				GoogleSearchResultResponseDTOAdapter dtoAdapter = new GoogleSearchResultResponseDTOAdapter();
//				searchResults = dtoAdapter.buildResponse(inputStream);
//				inputStream.close();
//			}
			//InputStream inputStream = new FileInputStream("/Users/saurabh/csrepos/Parshwabhoomi/docs/v1.json");
			InputStream inputStream = new FileInputStream("/Users/saurabh/csrepos/Parshwabhoomi/docs/GoogleCustomSearchResponse_q_ai.json");
			GoogleSearchResultResponseDTOAdapter dtoAdapter = new GoogleSearchResultResponseDTOAdapter();
			searchResults = dtoAdapter.buildResponse(inputStream);
			inputStream.close();
			LogManager.getLogger().info("DTOs for Google Search Results: "+searchResults);
		} catch (IOException e) {
			LogManager.getLogger().info("Error retrieving search results from Google!", e);
		} 
    	
    	return searchResults;
    }
}