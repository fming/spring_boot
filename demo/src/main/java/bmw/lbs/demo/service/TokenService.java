package bmw.lbs.demo.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class TokenService {
	private Map<String, UserDetails> tokensMap = new HashMap<String, UserDetails>();
	
	/**
	 * get user by the given token
	 * 
	 * @param token
	 * @return
	 */
	@Cacheable
	public UserDetails getUserFromToken(String token) {
		if(token == null) {
			return null;
		}
		
		return tokensMap.get(token);
		
	}
	
	/**
	 * Login, return the token if successfully or return null.
	 * 
	 * @param name
	 * @param password
	 * @return
	 */
	public String login(String name, String password ) {		
		UserDetails ud = null;
		if("admin".equals(name) && "1234".equals(password)) {
            ud = createUser(name, password, new String[] {"admin"});
		}		
		
		if(ud != null) {
            String token = UUID.randomUUID().toString();
            tokensMap.put(token, ud);
            return token;
        }else {
            return null;
        }
	}
	
	/**
	 * Logout, remove it from the tokens map
	 * 
	 * @param token
	 */
	public void logout(String token) {
		tokensMap.remove(token);
	}
	
	
	/**
	 * Create user by role
	 * 
	 * @param name
	 * @param password
	 * @param roles
	 * @return
	 */
	private UserDetails createUser(String name, String password, String[] roles) {
		return new UserDetails() {
			private static final long serialVersionUID = 1L;

			@Override
            public Collection<? extends GrantedAuthority> getAuthorities() {
                Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
                
                SimpleGrantedAuthority authority = new SimpleGrantedAuthority("query");
                authorities.add(authority);
                
                for(String role : roles) {
                    SimpleGrantedAuthority sga = new SimpleGrantedAuthority("ROLE_" + role);
                    authorities.add(sga);
                }
                
                return authorities;
            }

            @Override
            public String getPassword() {
                return password;
            }

            @Override
            public String getUsername() {
                return name;
            }

            @Override
            public boolean isAccountNonExpired() {
                return true;
            }

            @Override
            public boolean isAccountNonLocked() {
                return true;
            }

            @Override
            public boolean isCredentialsNonExpired() {
                return true;
            }

            @Override
            public boolean isEnabled() {
                return true;
            }
		};
	}
	
	

}
