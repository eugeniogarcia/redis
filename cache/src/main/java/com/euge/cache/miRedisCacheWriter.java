package com.euge.cache;

import java.time.Duration;

import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

public class miRedisCacheWriter implements RedisCacheWriter {

	private final RedisCacheWriter defaultRedisCacheWriter;

	miRedisCacheWriter(RedisConnectionFactory connectionFactory) {

		Assert.notNull(connectionFactory, "ConnectionFactory must not be null!");

		//Usamos el default
		defaultRedisCacheWriter=RedisCacheWriter.lockingRedisCacheWriter(connectionFactory);

	}

	//Añade a la cache
	@Override
	public void put(String name, byte[] key, byte[] value, @Nullable Duration ttl) {
		try {
			defaultRedisCacheWriter.put(name,key,value,ttl);
		}
		catch(final Exception ex) {
			//Eso si, si hay un fallo de disponibilidad, el tiempo para hacer la operacion se incrementa
			//time-out del get, time-out del put, y duración de la operación
			//Podriamos implementar un fast fail para que si nos falla al hacer el get, ya ni se intente
			//hacer el put
			System.out.println("No pasa nada, seguimos como si no huviera cache");
		}
	}

	//Recupera de la cache
	@Override
	public byte[] get(String name, byte[] key) {
		try {	
			return defaultRedisCacheWriter.get(name,key);
		}
		catch(final Exception ex) {
			System.out.println("No pasa nada, seguimos como si no huviera cache");
			return null;
		}
	}


	@Override
	public byte[] putIfAbsent(String name, byte[] key, byte[] value, @Nullable Duration ttl) {
		try{
			return defaultRedisCacheWriter.putIfAbsent(name,key,value,ttl);
		}
		catch(final Exception ex) {
			System.out.println("No pasa nada, seguimos como si no huviera cache");
			return null;
		}
	}

	//Borra una entrada de la cache
	@Override
	public void remove(String name, byte[] key) {
		try {
			defaultRedisCacheWriter.remove(name, key);
		}
		catch(final Exception ex) {
			System.out.println("No pasa nada, seguimos como si no huviera cache");
		}
	}

	//Limpia toda la cache
	@Override
	public void clean(String name, byte[] pattern) {
		try {
			defaultRedisCacheWriter.clean(name, pattern);
		}
		catch(final Exception ex) {
			System.out.println("No pasa nada, seguimos como si no huviera cache");
		}
	}

}

