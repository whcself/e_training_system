package com.csu.etrainingsystem.redis.dao.impl;

import com.csu.etrainingsystem.redis.dao.JedisDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;

import javax.annotation.Resource;

@Repository
public class JedisDaoImpl implements JedisDao {

	@Autowired
	private Jedis jedis;
	@Override
	public Boolean exists(String key) {
		return jedis.exists(key);
	}

	@Override
	public Long del(String key) {
		return jedis.del(key);
	}

	@Override
	public String set(String key, String value) {
		return jedis.set(key, value);
	}

	@Override
	public String get(String key) {
		return jedis.get(key);
	}

	@Override
	public Long expire(String key, int seconds) {
		return jedis.expire(key, seconds);
	}


}
