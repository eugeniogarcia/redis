package com.euge.cache;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.euge.cache.datasource.heroesDS;

@Configuration
public class Configure {

	@Bean
	public heroesDS service() {
		return new heroesDS();
	}
	/*
	@Bean
	public CacheManager cacheManager() {
		//Podemos definir mas de una cache
		//return new ConcurrentMapCacheManager("misHeroes1","misHeroes2");
		return new ConcurrentMapCacheManager("misHeroes");
	}
	 */

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
}
