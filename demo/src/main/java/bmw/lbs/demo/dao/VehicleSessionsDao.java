package bmw.lbs.demo.dao;

import java.util.List;

import bmw.lbs.demo.pojo.VehicleSession;

public interface VehicleSessionsDao  {	

    public List<VehicleSession> getAll(String vid);
    
    public VehicleSession getVehicleSessionByTime(String vid, String timestamp);
    
    public VehicleSession getVehicleSessionAtLast(String vid);

}
