package bmw.lbs.demo.dao;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;

import bmw.lbs.demo.Util;
import bmw.lbs.demo.pojo.VehicleSession;

/**
 * The Data manger of Vehicle sessions
 * 
 * @author mingfang
 *
 */
public class VehicleSessionsDB implements VehicleSessionsDao {
	private final Log log = LogFactory.getLog(VehicleSessionsDB.class);
	
	private List<VehicleSession> sessions = null;
	
	public VehicleSessionsDB() {
		// It's better to use Database for this, but for demo, only read all of them into memory.		
		sessions = loadObjectList(VehicleSession.class, "/data_test.csv");
	}
	
	public List<VehicleSession> loadObjectList(Class<VehicleSession> type, String file) {
	    try {
	    	
	    	InputStream rc = getClass().getResourceAsStream(file);
	    	
	        CsvSchema schema = CsvSchema.emptySchema().withHeader().withColumnReordering(true);	        
	        CsvMapper mapper = new CsvMapper();
	        ObjectReader reader = mapper.readerFor(type).with(schema);
	        return reader.<VehicleSession>readValues(rc).readAll();	        
	        
	    } catch (Exception e) {
	    	log.error("Error occurred while loading object list from file " + file, e);
	        return Collections.emptyList();
	    }
	}
	
	class StortByTimestamp implements Comparator<VehicleSession> 
	{ 	    

		@Override
		public int compare(VehicleSession o1, VehicleSession o2) {
			long t1 = Util.ConvertToLong(o1.getTimestamp());
			long t2 = Util.ConvertToLong(o2.getTimestamp());
			
			if(t1 > t2) {
				return 1;
			}
			if(t1 < t2) {
				return -1;
			}
			return 0;
		} 
	} 
	

	@Override
	public List<VehicleSession> getAll(String vid) {
		
		List<VehicleSession> ls = new ArrayList<VehicleSession>();
		
		if(sessions != null && vid != null) {
			for(VehicleSession session : sessions) {
				if(vid.equals(session.getVehicle_id())) {
					ls.add(session);
				}
			}
			
			Collections.sort(ls, new StortByTimestamp());
		}
		return ls;
	}

	@Override
	public VehicleSession getVehicleSessionByTime(String vid, String timestamp) {
		VehicleSession vs = null;		
		if(sessions != null && vid != null) {
			for(VehicleSession session : sessions) {
				if(vid.equals(session.getVehicle_id())) {
					long time = Util.ConvertToLong(session.getTimestamp());
					long time1 = Util.ConvertToLong(timestamp);
					if(time == time1) {					
						vs = session;
						break;
					}
				}
			}			
			
		}
		return vs;		
	}

	@Override
	public VehicleSession getVehicleSessionAtLast(String vid) {
		VehicleSession vs = null;
		long timestamp = -1;
		
		if(sessions != null && vid != null) {
			for(VehicleSession session : sessions) {
				if(vid.equals(session.getVehicle_id()) && timestamp < Util.ConvertToLong(session.getTimestamp())) {
					vs = session;
				}
			}			
		}
		return vs;
	}
}
