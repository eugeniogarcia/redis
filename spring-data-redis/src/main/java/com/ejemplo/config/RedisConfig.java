package com.ejemplo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

import com.ejemplo.bean.Person;

import redis.clients.jedis.JedisPoolConfig;

@Configuration
@ComponentScan("com.ejemplo")
public class RedisConfig {
	//Usaremos jedis como cliente para interactuar con Redis
	@Bean
	public RedisConnectionFactory redisConnectionFactory() {
		//Define un pool de conexiones
		final JedisPoolConfig poolConfig = new JedisPoolConfig();
		poolConfig.setMaxTotal(5);
		poolConfig.setTestOnBorrow(true);
		poolConfig.setTestOnReturn(true);

		//Creamos una connection factory que use el pool de conexiones
		final JedisConnectionFactory connectionFactory = new JedisConnectionFactory(poolConfig);
		connectionFactory.setUsePool(true);

		//Especificamos los datos del servidor
		connectionFactory.setHostName("localhost");
		connectionFactory.setPort(6379);

		return connectionFactory;
	}

	//Customiza el redis template
	@Bean
	public RedisTemplate<String, Person> redisTemplate() {
		//Especificamos que el redis template usara un string como key y guardara Person
		final RedisTemplate<String, Person> redisTemplate = new RedisTemplate<>();
		//Indica que connection factory vamos a usar con el redis template...
		redisTemplate.setConnectionFactory(redisConnectionFactory());
		//...y que soporte transacciones
		redisTemplate.setEnableTransactionSupport(true);
		return redisTemplate;
	}

	//Customizamos tambien el string redis template para que soporte transacciones
	@Bean
	public StringRedisTemplate stringRedisTemplate() {
		final StringRedisTemplate stringRedisTemplate = new StringRedisTemplate(redisConnectionFactory());
		stringRedisTemplate.setEnableTransactionSupport(true);
		return stringRedisTemplate;
	}	

	/*
	@Autowired
	LettuceConnectionFactory cf;


	@Primary
	@Bean
	RedisTemplate< String, Object > redisTemplate() {
		final miRedisTemplate< String, Object > template =  new miRedisTemplate< String, Object >();
		template.setConnectionFactory( cf );
		template.setKeySerializer( new StringRedisSerializer() );
		template.setHashValueSerializer( new GenericToStringSerializer< Object >( Object.class ) );
		template.setValueSerializer( new GenericToStringSerializer< Object >( Object.class ) );
		return template;
	}

	@Primary
	@Bean
	RedisTemplate< Object, Object > redisObjectTemplate() {
		final miRedisTemplate< Object, Object > template =  new miRedisTemplate< Object, Object >();
		template.setConnectionFactory( cf );
		template.setKeySerializer( new GenericToStringSerializer< Object >( Object.class ) );
		template.setHashValueSerializer( new GenericToStringSerializer< Object >( Object.class ) );
		template.setValueSerializer( new GenericToStringSerializer< Object >( Object.class ) );
		return template;
	}

	 */

}