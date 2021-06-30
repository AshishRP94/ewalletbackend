package ewalletbackend.redisconfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
public class Rediconfig
{
	
	@Bean
	RedisConnectionFactory redisConnectionFactory()
	{
		RedisStandaloneConfiguration redis=new RedisStandaloneConfiguration();
		redis.setHostName("127.0.0.1");
		redis.setPort(6379);
		redis.setDatabase(0);
		return new JedisConnectionFactory( redis);
	}

//	@Bean
//	JedisConnectionFactory jedisConnectionFactory() {
//	    return new JedisConnectionFactory();
//	}
	
	@Bean
	public RedisTemplate<String, Object> redisTemplate() {
	    RedisTemplate<String, Object> template = new RedisTemplate<>();
	    template.setConnectionFactory(redisConnectionFactory());
	    return template;
	}
	
	
	

}
