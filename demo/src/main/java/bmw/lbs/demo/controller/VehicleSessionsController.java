package bmw.lbs.demo.controller;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import bmw.lbs.demo.pojo.VehicleSession;
import bmw.lbs.demo.service.VehicleSessionsService;


/**
 * The Controller of Sessions
 * 
 * @author mingfang
 *
 */
@RestController
@RequestMapping("/sessions")
public class VehicleSessionsController {
	
	private final Log log = LogFactory.getLog(VehicleSessionsController.class);
	@Autowired VehicleSessionsService vehicleSessionsService;
	
	
	@GetMapping
	@PreAuthorize("hasRole('admin')")
    public String getAll() {
        if(log.isTraceEnabled()) {
            log.trace("getAll() ");
        }        
        return "Please specify a concrete id of vehcile!";
    }
		
	@GetMapping("/{vid}")
	@PreAuthorize("hasRole('admin')")
    public List<VehicleSession> getVehicleSessions(@PathVariable String vid) {
        if(log.isTraceEnabled()) {
            log.trace(String.format("getVehicleSessions(vid = %s)", vid));
        }
        
        List<VehicleSession> vs = vehicleSessionsService.getAllVehicleSessions(vid);
        if(vs == null) {
            throw new ResourceNotFoundException();
        }
        return vs;        
    }
	
	@GetMapping("/{vid}/date")
	@PreAuthorize("hasRole('admin')")
    public VehicleSession getVehicleSessionsByTime(@PathVariable String vid, @RequestParam("timestamp") String timestamp) {
        if(log.isTraceEnabled()) {
            log.trace(String.format("getVehicleSessionsByTime(vid = %s, timestamp = %s)", vid, timestamp));
        }
        
        VehicleSession vs = vehicleSessionsService.getVehicleSessionByTime(vid, timestamp);
        if(vs == null) {
            throw new ResourceNotFoundException();
        }
        return vs;        
    }
	
	@GetMapping("/{vid}/last")
	@PreAuthorize("hasRole('admin')")
    public VehicleSession getVehicleSessionAtLast(@PathVariable String vid) {
        if(log.isTraceEnabled()) {
            log.trace(String.format("getVehicleSessionAtLast(vid = %s)", vid));
        }
        
        VehicleSession vs = vehicleSessionsService.getVehicleSessionAtLast(vid);
        if(vs == null) {
            throw new ResourceNotFoundException();
        }
        return vs;        
    }

}
