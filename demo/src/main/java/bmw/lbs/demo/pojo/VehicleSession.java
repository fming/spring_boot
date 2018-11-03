package bmw.lbs.demo.pojo;

/**
 * 
 * Represent a Vehicle Session
 * 
 * @author mingfang
 *
 */
public class VehicleSession {
	
	private String session_id;
	
	private String vehicle_id;

	private String timestamp;
	
	private double lat;
	
	private double lon;
	
	private Integer heading;
	
	public String getSession_id() {
		return session_id;
	}
	public void setSession_id(String session_id) {
		this.session_id = session_id;
	}
	public String getVehicle_id() {
		return vehicle_id;
	}
	public void setVehicle_id(String vehicle_id) {
		this.vehicle_id = vehicle_id;
	}
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	public double getLat() {
		return lat;
	}
	public void setLat(double lat) {
		this.lat = lat;
	}
	public double getLon() {
		return lon;
	}
	public void setLon(double lon) {
		this.lon = lon;
	}
	public Integer getHeading() {
		return heading;
	}
	public void setHeading(Integer heading) {
		this.heading = heading;
	}	
}
