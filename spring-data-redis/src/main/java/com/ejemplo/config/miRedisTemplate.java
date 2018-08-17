package com.ejemplo.config;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.data.redis.serializer.RedisSerializer;


public class miRedisTemplate<K, V> extends RedisTemplate<K, V> {
	private static final Logger logger = LoggerFactory.getLogger(miRedisTemplate.class);

	@Override
	public <T> T execute(RedisCallback<T> action) {
		try {
			return super.execute(action);
		}
		catch(final Throwable t) {
			logger.warn("Error executing cache operation: {}", t.getMessage());
			return null;
		}
	}

	@Override
	public <T> T execute(RedisCallback<T> action, boolean exposeConnection) {
		try {
			return super.execute(action, exposeConnection);
		}
		catch(final Throwable t) {
			logger.warn("Error executing cache operation: {}", t.getMessage());
			return null;
		}
	}


	@Override
	public <T> T execute(final RedisCallback<T> action, final boolean exposeConnection, final boolean pipeline) {
		try {
			return super.execute(action, exposeConnection, pipeline);
		}
		catch(final Throwable t) {
			logger.warn("Error executing cache operation: {}", t.getMessage());
			return null;
		}
	}


	@Override
	public <T> T execute(final RedisScript<T> script, final List<K> keys, final Object... args) {
		try {
			return super.execute(script, keys, args);
		}
		catch(final Throwable t) {
			logger.warn("Error executing cache operation: {}", t.getMessage());
			return null;
		}
	}


	@Override
	public <T> T execute(final RedisScript<T> script, final RedisSerializer<?> argsSerializer, final RedisSerializer<T> resultSerializer, final List<K> keys, final Object... args) {
		try {
			return super.execute(script, argsSerializer, resultSerializer, keys, args);
		}
		catch(final Throwable t) {
			logger.warn("Error executing cache operation: {}", t.getMessage());
			return null;
		}
	}


	@Override
	public <T> T execute(final SessionCallback<T> session) {
		try {
			return super.execute(session);
		}
		catch(final Throwable t) {
			logger.warn("Error executing cache operation: {}", t.getMessage());
			return null;
		}
	}
}
