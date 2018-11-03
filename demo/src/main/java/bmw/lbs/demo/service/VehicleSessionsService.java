package bmw.lbs.demo.service;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import bmw.lbs.demo.dao.VehicleSessionsDB;
import bmw.lbs.demo.dao.VehicleSessionsDao;
import bmw.lbs.demo.pojo.VehicleSession;

/**
 * Service for Vehicle sessions
 * 
 * @author mingfang
 *
 */

@Service
public class VehicleSessionsService {
	
	private final Log log = LogFactory.getLog(VehicleSessionsService.class);
    
    private VehicleSessionsDao sessionsDao = new VehicleSessionsDB();	
    
    /**
     * Get all sessions of a vehicle in correct ordering
     * 
     * @param vid id of a vehicle
     * @return
     */
	@Transactional(readOnly=true)
    public List<VehicleSession> getAllVehicleSessions(String vid){        
        if(log.isTraceEnabled()) {
            log.trace("Get all sessions of a vehicle, and the vehicle id is: " + vid);
        }
        List<VehicleSession> list = sessionsDao.getAll(vid);
        return list;
    }
	
	/**
	 * Get a session of a vehicle at some time
	 * 
	 * @param vid
	 * @param timestamp
	 * @return
	 */
	@Transactional(readOnly=true)
    public VehicleSession getVehicleSessionByTime(String vid, String timestamp){        
        if(log.isTraceEnabled()) {
            log.trace("getAllTvSeries started   ");
        }
        
        VehicleSession session = sessionsDao.getVehicleSessionByTime(vid, timestamp);
        return session;
    }
	
	/**
	 * Get the last position of a certain vehicle
	 * 
	 * @param vid
	 * @return
	 */
	@Transactional(readOnly=true)
    public VehicleSession getVehicleSessionAtLast(String vid){        
        if(log.isTraceEnabled()) {
            log.trace("getAllTvSeries started   ");
        }
        
        VehicleSession session = sessionsDao.getVehicleSessionAtLast(vid);
        return session;
    }
}
