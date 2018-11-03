package bmw.lbs.demo.security;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, jsr250Enabled = false)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	private final static Log log = LogFactory.getLog(WebSecurityConfig.class);
	
	 @Override
     protected void configure(HttpSecurity httpSecurity) throws Exception {
        if (log.isTraceEnabled()) {
            log.trace("diable the default configure");
        }
        
        // Disable the default behavior which will need login for all API accessing.
        httpSecurity.csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .authorizeRequests().anyRequest().permitAll();
     }
}
