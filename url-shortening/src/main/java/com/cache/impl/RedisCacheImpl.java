package com.cache.impl;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Component;

import redis.clients.jedis.Jedis;

import com.cache.RedisCache;
import com.utils.JedisConnectionManager;

/**
 * @author Sumeet.Tripathi
 *
 */
@Component
public class RedisCacheImpl implements RedisCache {
	private static final String URL_BUCKET = "URLBUCKET";
	private AtomicLong hits;

	public RedisCacheImpl() {
		hits = new AtomicLong(0l);
	}

	public String addUrl(String key, String value, Integer minutes) {
		if (key != null && key.trim().length() > 0) {
			// do-nothing
		} else {
			key = UUID.randomUUID().toString().split("-")[0];
			while (!validateUrl(key)) {
				key = UUID.randomUUID().toString().split("-")[0];
			}
		}

		Jedis conn = JedisConnectionManager.getConnection();
		if (!conn.isConnected()) {
			conn.connect();
		}

		conn.set((URL_BUCKET + key).getBytes(StandardCharsets.UTF_8),
				value.getBytes());
		if (minutes != null && minutes > 0) {
			conn.expire((URL_BUCKET + key).getBytes(StandardCharsets.UTF_8),
					minutes * 60);
		}
		conn.set((key).getBytes(StandardCharsets.UTF_8), "0".getBytes());

		if (conn != null) {
			JedisConnectionManager.release(conn);
		}

		return key;
	}

	@SuppressWarnings("deprecation")
	public String getUrl(String key) {
		String url = null;

		Jedis conn = JedisConnectionManager.getConnection();
		if (!conn.isConnected()) {
			conn.connect();
		}
		byte[] bytes = conn.get((URL_BUCKET + key).getBytes());
		try {
			url = IOUtils.toString(bytes, "UTF-8");
		} catch (IOException e) {
			// do-nothing
		}
		if (conn != null) {
			JedisConnectionManager.release(conn);
		}

		if (url != null && url.trim().length() > 0) {
			saveSet(key, hits.incrementAndGet() + "");
		}
		return url;
	}

	public void saveSet(String hash, String key) {
		Jedis conn = null;

		try {
			conn = JedisConnectionManager.getConnection();
			conn.incr(hash.getBytes());
		} catch (Throwable e) {
			System.out.println("Error in adding data to set in redis :" + e);
		} finally {
			if (conn != null) {
				JedisConnectionManager.release(conn);
			}
		}
	}

	public String getHits(String hash) throws Throwable {
		Jedis conn = null;
		String set = "0";
		try {
			conn = JedisConnectionManager.getConnection();
			set = conn.get(hash);
		} catch (Throwable e) {
			System.out.println("Error in adding data to set in redis :" + e);
		} finally {
			if (conn != null) {
				JedisConnectionManager.release(conn);
			}
			if (set == null) {
				set = "0";
			}
		}

		return set;
	}

	@SuppressWarnings({ "deprecation", "finally" })
	public Boolean validateUrl(String key) {
		String url = null;

		Jedis conn = JedisConnectionManager.getConnection();
		if (!conn.isConnected()) {
			conn.connect();
		}
		byte[] bytes = conn.get((URL_BUCKET + key).getBytes());
		try {
			url = IOUtils.toString(bytes, "UTF-8");
		} catch (IOException e) {
			// do-nothing
		} finally {
			if (conn != null) {
				JedisConnectionManager.release(conn);
			}

			if (url != null && url.trim().length() > 0) {
				return Boolean.FALSE;
			}
			return Boolean.TRUE;
		}
	}

}
