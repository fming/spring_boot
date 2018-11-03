package bmw.lbs.demo.service;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import bmw.lbs.demo.pojo.User;

/**
 * Redis or Memcached , spring boot
 * 
 * @author mingfang
 *
 */
@CacheConfig()
@Service
public class UserService {
	
	@Cacheable(condition="id<10", unless="#result.status=0")	
	public User getUserById(int id) {
	
		return new User();
	}
	
	@CachePut(key="#u.id", condition="#u.status=0")
	public void updateUser(User u) {
		
	}
	
	@CacheEvict
	public void deleteUserById(int id) {
		
	}
	
	@CacheEvict(allEntries=true)
	public void deleteAll() {
		
	}
	
	
	@Scheduled(cron = "0 0, 30 * * * ?")
	@CacheEvict(allEntries=true)
	public void clearData() {
		
	}

}
