package com.euge.cache;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;

import com.euge.cache.datasource.heroesDS;

@Configuration
public class Configure {

	@Bean
	public heroesDS service() {
		return new heroesDS();
	}

	/*
	//Esta es una cache para ander por casa, podemos usarla cuando no tengamos otra
	@Bean
	public CacheManager cacheManager() {
		//Podemos definir mas de una cache
		//return new ConcurrentMapCacheManager("misHeroes1","misHeroes2");
		return new ConcurrentMapCacheManager("misHeroes");
	}
	 */

	//Podriamos definir dos cache managers si quisieras dotarles de propiedades distintas, como por ejemplo el ttl
	/*
	@Primary
	@Bean
	public RedisCacheManager cacheManager(final RedisConnectionFactory connectionFactory) {
		final RedisCacheWriter redisCacheWriter = RedisCacheWriter.lockingRedisCacheWriter(connectionFactory);
		RedisCacheConfiguration cacheConfiguration = RedisCacheConfiguration.defaultCacheConfig();
		cacheConfiguration = cacheConfiguration.entryTtl(Duration.ofSeconds(60));
		final RedisCacheManager redisCacheManager = new RedisCacheManager(redisCacheWriter, cacheConfiguration);
		return redisCacheManager;
	}

	@Bean(name = "largaDuracion")
	public RedisCacheManager pickleCacheManager(final RedisConnectionFactory connectionFactory) {
		final RedisCacheWriter redisCacheWriter = RedisCacheWriter.lockingRedisCacheWriter(connectionFactory);
		RedisCacheConfiguration cacheConfiguration = RedisCacheConfiguration.defaultCacheConfig();
		cacheConfiguration = cacheConfiguration.entryTtl(Duration.ofSeconds(120));
		final RedisCacheManager redisCacheManager = new RedisCacheManager(redisCacheWriter, cacheConfiguration);
		return redisCacheManager;
	}
	 */

	@Autowired
	private RedisConnectionFactory connectionFactory;

	@Primary
	@Bean
	public RedisCacheManager cacheManager() {

		//Usa este CacheWriter, que tiene la particularidad de que me permite interceptar las 
		//operaciones con la cache
		final RedisCacheWriter redisCacheWriter = new miRedisCacheWriter(connectionFactory);

		RedisCacheConfiguration cacheConfiguration = RedisCacheConfiguration.defaultCacheConfig();
		cacheConfiguration = cacheConfiguration.entryTtl(Duration.ofSeconds(60));
		final RedisCacheManager redisCacheManager = new RedisCacheManager(redisCacheWriter, cacheConfiguration);
		return redisCacheManager;
	}

}
