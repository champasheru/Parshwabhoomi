/**
 * parshwabhoomi-server	29-Oct-2017:7:21:10 PM
 * gayatri
 * git: champasheru Gayatri Sirdeshmukh dange.gayatri@gmail.com
 */
package org.cs.parshwabhoomi.server.dao.raw.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.cs.parshwabhoomi.server.dao.AbstractRawDao;
import org.cs.parshwabhoomi.server.dao.raw.BusinessVendorDao;
import org.cs.parshwabhoomi.server.model.Address;
import org.cs.parshwabhoomi.server.model.BusinessCategory;
import org.cs.parshwabhoomi.server.model.BusinessVendor;
import org.cs.parshwabhoomi.server.model.ContactInfo;
import org.cs.parshwabhoomi.server.model.UserCredential;
import org.cs.parshwabhoomi.server.model.UserCredential.Role;

/**
 * @author gayatri
 * git: champasheru Gayatri Sirdeshmukh dange.gayatri@gmail.com
 *
 */
public class BusinessVendorDaoImpl extends AbstractRawDao implements BusinessVendorDao {

	/* (non-Javadoc)
	 * @see org.cs.parshwabhoomi.server.dao.raw.BusinessVendorDao#add(org.cs.parshwabhoomi.server.model.BusinessVendor)
	 */
	@Override
	public int add(BusinessVendor businessVendor) {
		LogManager.getLogger().info("Adding business vendor: "+businessVendor.getName());
    	
        String query = "INSERT INTO business_vendors(user_id, name, "
        		+ "route_or_lane, sublocality, locality, state, pincode, "
        		+ "primary_mobile, secondary_mobile, email, landline, "
        		+ "category_id, offerings, tagline, latitude, longitude) "
                +" VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        PreparedStatement statement = null;
        int status = 0;
        try {
            statement = connection.prepareStatement(query);
            
            statement.setLong(1, businessVendor.getUserCredential().getId());
            statement.setString(2, businessVendor.getName());
            
            statement.setString(3, businessVendor.getAddress().getRouteOrLane());
            statement.setString(4, businessVendor.getAddress().getSublocality());
            statement.setString(5, businessVendor.getAddress().getLocality());
            statement.setString(6, businessVendor.getAddress().getState());
            statement.setString(7, businessVendor.getAddress().getPincode());
            
            statement.setString(8, businessVendor.getContactInfo().getPrimaryMobile());
            statement.setString(9, businessVendor.getContactInfo().getSecondaryMobile());
            statement.setString(10, businessVendor.getContactInfo().getEmail());
            statement.setString(11, businessVendor.getContactInfo().getLandline());
            
            statement.setLong(12, businessVendor.getBusinessCategory().getId());
            statement.setString(13, businessVendor.getOfferings());
            statement.setString(14, businessVendor.getTagLine());
            
            statement.setString(15, businessVendor.getAddress().getLatitude());
            statement.setString(16, businessVendor.getAddress().getLongitude());
            
            status = statement.executeUpdate();
            if (status > 0) {
            	LogManager.getLogger().info("The BusinessVendor added successfully!");
            }
        } catch (SQLException sqle) {
        	LogManager.getLogger().error("Couldn't add vendor info!", sqle);
        } finally {
			try {
				if (statement != null) {
					statement.close();
				}
			} catch (SQLException e) {
				LogManager.getLogger().error("Couldn't add vendor info!", e);
			}
		}
        return status;
	}
	
	

	/* (non-Javadoc)
	 * @see org.cs.parshwabhoomi.server.dao.raw.BusinessVendorDao#update(org.cs.parshwabhoomi.server.model.BusinessVendor)
	 */
	@Override
	public int update(BusinessVendor businessVendor) {
		LogManager.getLogger().info("Updating business vendor: id="+businessVendor.getId()+" name=" +businessVendor.getName());
    	
        String query = "UPDATE business_vendors "
        		+ "SET  name = ?, "
        		+ "route_or_lane = ?, sublocality = ?, locality = ?, state = ?, pincode = ?, "
        		+ "primary_mobile = ?, secondary_mobile = ?, email = ?, landline = ?, "
        		+ "category_id = ?, offerings = ?, tagline = ?, latitude = ?, longitude = ? "
                +" WHERE id = ?";
        
        PreparedStatement statement = null;
        int status = 0;
        try {
            statement = connection.prepareStatement(query);
            
            statement.setString(1, businessVendor.getName());
            
            statement.setString(2, businessVendor.getAddress().getRouteOrLane());
            statement.setString(3, businessVendor.getAddress().getSublocality());
            statement.setString(4, businessVendor.getAddress().getLocality());
            statement.setString(5, businessVendor.getAddress().getState());
            statement.setString(6, businessVendor.getAddress().getPincode());
            
            statement.setString(7, businessVendor.getContactInfo().getPrimaryMobile());
            statement.setString(8, businessVendor.getContactInfo().getSecondaryMobile());
            statement.setString(9, businessVendor.getContactInfo().getEmail());
            statement.setString(10, businessVendor.getContactInfo().getLandline());
            
            statement.setLong(11, businessVendor.getBusinessCategory().getId());
            statement.setString(12, businessVendor.getOfferings());
            statement.setString(13, businessVendor.getTagLine());
            
            statement.setString(14, businessVendor.getAddress().getLatitude());
            statement.setString(15, businessVendor.getAddress().getLongitude());
            
            statement.setLong(16, businessVendor.getId());
            
            status = statement.executeUpdate();
            LogManager.getLogger().info("The BusinessVendor update status ="+status);
            if (status > 0) {
            	LogManager.getLogger().info("The BusinessVendor added successfully!");
            }
        } catch (SQLException sqle) {
        	LogManager.getLogger().error("Couldn't add vendor info!", sqle);
        } finally {
			try {
				if (statement != null) {
					statement.close();
				}
			} catch (SQLException e) {
				LogManager.getLogger().error("Couldn't add vendor info!", e);
			}
		}
        
        return status;
	}


	@Override
	public BusinessVendor getByUsername(String username) {
		LogManager.getLogger().info("Retrieving vendor profile...");
		
		String query = "SELECT business_vendors.id as vid, user_creds.id as ucid, role, "
				+ "category_id, name, route_or_lane, sublocality, locality, state, offerings, "
				+"pincode, latitude, longitude, primary_mobile, secondary_mobile, landline, email, tagline, user_id, "
				+"categories.id as cid, category_name, category_description "
				+ "FROM ((business_vendors INNER JOIN user_creds ON user_creds.id = business_vendors.user_id) "
				+"INNER JOIN categories ON categories.id = business_vendors.category_id) "
				+ "WHERE username = ?";

		PreparedStatement prepStmt = null;
		ResultSet rs = null;
		BusinessVendor vendor = null;
		try {
			prepStmt = connection.prepareStatement(query);
			prepStmt.setString(1, username);
			rs = prepStmt.executeQuery();
			if (rs.next()) {
				vendor = new BusinessVendor();
				
				UserCredential credential = new UserCredential();
				credential.setId(rs.getLong("ucid"));
				credential.setUsername(username);
				credential.setRole(Role.valueOf(rs.getString("role")));
				vendor.setUserCredential(credential);
				
				vendor.setId(rs.getLong("vid"));
				vendor.setName(rs.getString("name"));
				
				Address address = new Address();
				address.setLatitude(rs.getString("latitude"));
				address.setLongitude(rs.getString("longitude"));
				address.setRouteOrLane(rs.getString("route_or_lane"));
				address.setSublocality(rs.getString("sublocality"));
				address.setLocality(rs.getString("locality"));
				address.setState(rs.getString("state"));
				address.setPincode(rs.getString("pincode"));
				vendor.setAddress(address);

				ContactInfo contactInfo = new ContactInfo();
				contactInfo.setEmail(rs.getString("email"));
				contactInfo.setLandline(rs.getString("landline"));
				contactInfo.setPrimaryMobile(rs.getString("primary_mobile"));
				contactInfo.setSecondaryMobile(rs.getString("secondary_mobile"));
				vendor.setContactInfo(contactInfo);

				vendor.setOfferings(rs.getString("offerings"));
				vendor.setTagLine(rs.getString("tagline"));
				
				String catrgoryName = rs.getString("category_name");
				if(catrgoryName != null){
					BusinessCategory bc = BusinessCategory.valueOf(rs.getString("category_name"));
					bc.setId(rs.getLong("cid"));
					vendor.setBusinessCategory(bc);
				}
			}
		} catch (SQLException sqle) {
			LogManager.getLogger().error("Error:retrieving vendor profile",  sqle);
		} finally {
			try {
				if (prepStmt != null) {
					prepStmt.close();
				}
			} catch (SQLException e) {
				LogManager.getLogger().error("Error:retrieving vendor profile",  e);
			}
		}

		return vendor;
	}



	/* (non-Javadoc)
	 * @see org.cs.parshwabhoomi.server.dao.raw.BusinessVendorDao#getById(java.lang.long)
	 */
	@Override
	public BusinessVendor getById(long id) {
		LogManager.getLogger().info("Retrieving vendor profile...");
		
		String query = "SELECT business_vendors.*, category_name, category_description "
				+ "FROM business_vendors, categories "
				+ "WHERE user_id = ? "
				+"and business_vendors.category_id = categories.id";

		PreparedStatement prepStmt = null;
		ResultSet rs = null;
		BusinessVendor vendor = null;
		try {
			prepStmt = connection.prepareStatement(query);
			prepStmt.setLong(1, id);
			rs = prepStmt.executeQuery();
			if (rs.next()) {
				vendor = new BusinessVendor();
				
				vendor.setId(id);
				vendor.setName(rs.getString("name"));
				
				Address address = new Address();
				address.setLatitude(rs.getString("latitude"));
				address.setLongitude(rs.getString("longitude"));
				address.setRouteOrLane(rs.getString("route_or_lane"));
				address.setSublocality(rs.getString("sublocality"));
				address.setLocality(rs.getString("locality"));
				address.setState(rs.getString("state"));
				address.setPincode(rs.getString("pincode"));
				vendor.setAddress(address);

				ContactInfo contactInfo = new ContactInfo();
				contactInfo.setEmail(rs.getString("email"));
				contactInfo.setLandline(rs.getString("landline"));
				contactInfo.setPrimaryMobile(rs.getString("primary_mobile"));
				contactInfo.setSecondaryMobile(rs.getString("secondary_mobile"));
				vendor.setContactInfo(contactInfo);

				vendor.setOfferings(rs.getString("offerings"));
				vendor.setTagLine(rs.getString("tagline"));
				
				String catrgoryName = rs.getString("category_name");
				if(catrgoryName != null){
					BusinessCategory bc = BusinessCategory.valueOf(rs.getString("category_name"));
					bc.setId(rs.getLong("category_id"));
					bc.setDescription(rs.getString("category_description"));
					vendor.setBusinessCategory(bc);
				}
			}
		} catch (SQLException sqle) {
			LogManager.getLogger().error("Error:retrieving vendor profile",  sqle);
		} finally {
			try {
				if (prepStmt != null) {
					prepStmt.close();
				}
			} catch (SQLException e) {
				LogManager.getLogger().error("Error:retrieving vendor profile",  e);
			}
		}

		return vendor;
	}



	/* (non-Javadoc)
	 * @see org.cs.parshwabhoomi.server.dao.raw.BusinessVendorDao#getAll()
	 */
	@Override
	public List<BusinessVendor> getAll() {
		LogManager.getLogger().info("Retrieving all vendors...");
		
		List<BusinessVendor> vendors = new ArrayList<>();
		String query = "SELECT business_vendors.*, category_name FROM business_vendors "
				+ "INNER JOIN categories ON business_vendors.category_id = categories.id";
		
		PreparedStatement prepStmt = null;
		ResultSet rs = null;
		try {
			prepStmt = connection.prepareStatement(query);
			rs = prepStmt.executeQuery();
			while(rs.next()) {
				BusinessVendor vendor = new BusinessVendor();
				
				vendor.setId(rs.getLong("id"));
				vendor.setName(rs.getString("name"));
				
				Address address = new Address();
				address.setLatitude(rs.getString("latitude"));
				address.setLongitude(rs.getString("longitude"));
				address.setRouteOrLane(rs.getString("route_or_lane"));
				address.setSublocality(rs.getString("sublocality"));
				address.setLocality(rs.getString("locality"));
				address.setState(rs.getString("state"));
				address.setPincode(rs.getString("pincode"));
				vendor.setAddress(address);

				ContactInfo contactInfo = new ContactInfo();
				contactInfo.setEmail(rs.getString("email"));
				contactInfo.setLandline(rs.getString("landline"));
				contactInfo.setPrimaryMobile(rs.getString("primary_mobile"));
				contactInfo.setSecondaryMobile(rs.getString("secondary_mobile"));
				vendor.setContactInfo(contactInfo);

				vendor.setOfferings(rs.getString("offerings"));
				vendor.setTagLine(rs.getString("tagline"));
				
				String catrgoryName = rs.getString("category_name");
				if(catrgoryName != null){
					BusinessCategory bc = BusinessCategory.valueOf(rs.getString("category_name"));
					bc.setId(rs.getLong("category_id"));
					vendor.setBusinessCategory(bc);
				}
				
				vendors.add(vendor);
			}
		} catch (SQLException sqle) {
			LogManager.getLogger().error("Error:retrieving all vendors",  sqle);
		} finally {
			try {
				if (prepStmt != null) {
					prepStmt.close();
				}
			} catch (SQLException e) {
				LogManager.getLogger().error("Error:retrieving all vendors",  e);
			}
		}
		
		LogManager.getLogger().info("Num vendors found:"+vendors.size());
		
		return vendors;
	}

}
