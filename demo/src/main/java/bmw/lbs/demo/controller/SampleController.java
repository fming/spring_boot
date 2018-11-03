package bmw.lbs.demo.controller;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import bmw.lbs.demo.service.SampleService;




@RestController
@RequestMapping("/cache")
public class SampleController {
	
	private static final Log log = LogFactory.getLog(SampleController.class);	
	@Autowired SampleService sampleService;
	
	@Autowired RedisTemplate<?, ?> redisTemplate;
	
	@GetMapping
	public Map<String, Object> getAll() {
		sampleService.doA();
		
		// Can excute it async, but cannot get the return value
		//Integer r = sampleService.doD();
		
		String key ="CACHE-DATE";
		
		@SuppressWarnings("unchecked")
		RedisTemplate<String, Date> rt = (RedisTemplate<String, Date>) redisTemplate;
		
		Date d = rt.opsForValue().get(key);
		Map<String, Object> result = new HashMap<>();
		
		if (d == null) {
			d = new Date();
			rt.opsForValue().set(key, d);
			Calendar c = Calendar.getInstance();
			c.add(Calendar.MINUTE, 1);
			rt.expireAt(key, c.getTime()); // set overdue after 1 minute
			
			if(log.isTraceEnabled()) {
                log.trace("set date " + d + " to cache");
                result.put("result", "from new Date()");
            }			
		}
		else {
			if(log.isTraceEnabled()) {
				log.trace("date from cache is " + d);
			}
			
			result.put("result", "from cache");
		}
		
		
		return result;
		
	}
	
	/**
	 * Cacheable represents the result can be cached
	 * 
	 * @param id
	 * @return
	 */
	@Cacheable(cacheNames="counter")
    @GetMapping("/{id}")
    public Map<String, Object> getOne(@PathVariable int id){
        if(log.isTraceEnabled()) {
            log.trace("getOne " + id);
        }
        Map<String, Object> result = getData(id);
        result.put("message", "data is from getOne(" + id + ")" + new Date());
        return result;
    }
	
	@CacheEvict(cacheNames="counter", key="#id")
    @DeleteMapping("/{id}")
    public Map<String, Object> deleteOne(@PathVariable int id){
        if(log.isTraceEnabled()) {
            log.trace("deleteOne " + id);
        }
        
        HashMap<String, Object> map = new HashMap<>();
        map.put("id", id);
        map.put("message", "counter was cleared");
        return map;
    }
	
	/**
	 * Cache put is update the cache
	 * 
	 * @param id
	 * @return
	 */
	@CachePut(cacheNames="counter", key="#id")
    @PutMapping("/{id}")
    public Map<String, Object> putOne(@PathVariable int id){
        if(log.isTraceEnabled()) {
            log.trace("putOne " + id);
        }
        Map<String, Object> result = getData(id);
        result.put("message", "数据由putOne(" + id + ")方法在" + new Date() + "更新。");
        return result;
    }	
	
	
	private Map<String, Object> getData(int id){
        HashMap<String, Object> map = new HashMap<>();
        map.put("id", id);
        return map;
    }
}
