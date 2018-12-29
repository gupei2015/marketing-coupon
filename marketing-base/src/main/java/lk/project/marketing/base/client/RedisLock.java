package lk.project.marketing.base.client;


import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisConnectionUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;


/**
 * Created on 2018/10/25.
 *
 * @author gu pei
 */
@Component
@Slf4j
public class RedisLock {

    @Autowired
    private RedisTemplate redisTemplate;

	/**
	 * 默认锁超时时间（毫秒）
	 */
	private static final int DEFAULT_EXPIRE = 5*1000;

	/**
	 * 锁名称前缀
	 */
	private static final String LOCK_PREFIX = "Coupon:";

	private RedisLock() {
	}

	/**
	 * 获取锁
	 * @param lockName	锁名称
	 * @param acquireTimeout	等待获取锁的超时时间（毫秒）
	 * @return	加锁成功后返回锁的唯一标识，未获取成功则返回null
	 */
	public String lock(String lockName, long acquireTimeout) {
		return lockWithTimeout(lockName, acquireTimeout, DEFAULT_EXPIRE);
	}



	/**
	 * 获取锁
	 * @param lockName	锁名称
	 * @param acquireTimeout	等待获取锁的超时时间（毫秒）
	 * @param timeout	锁超时时间，上锁后超过此时间则自动释放锁
	 * @return	加锁成功后返回锁的唯一标识，未获取成功则返回null
	 */
	public String lockWithTimeout(String lockName, long acquireTimeout, long timeout){

		RedisConnectionFactory connectionFactory = redisTemplate.getConnectionFactory();
		RedisConnection redisConnection = connectionFactory.getConnection();

		/** 随机生成一个value */
		String identifier = UUID.randomUUID().toString();
		String lockKey = LOCK_PREFIX + lockName;
		int lockExpire = (int)(timeout / 1000);

		long end = System.currentTimeMillis() + acquireTimeout;	/** 获取锁的超时时间，超过这个时间则放弃获取锁 */
		while (System.currentTimeMillis() < end) {
			if (redisConnection.setNX(lockKey.getBytes(), identifier.getBytes())) {
				redisConnection.expire(lockKey.getBytes(), lockExpire);
				/** 获取锁成功，返回标识锁的value值，用于释放锁确认 */
				RedisConnectionUtils.releaseConnection(redisConnection, connectionFactory);
				return identifier;
			}
			/** 返回-1代表key没有设置超时时间，为key设置一个超时时间 */
			if (redisConnection.ttl(lockKey.getBytes()) == -1) {
				redisConnection.expire(lockKey.getBytes(), lockExpire);
			}
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				log.warn("获取分布式锁：线程中断！");
				Thread.currentThread().interrupt();
			}
		}
		RedisConnectionUtils.releaseConnection(redisConnection, connectionFactory);
		return null;
	}

	/**
	 * 释放锁
	 * @param lockName 锁名称
	 * @param identifier 锁标识
	 * @return
	 */
	public boolean releaseLock(String lockName, String identifier) {

		if (StringUtils.isEmpty(identifier)) return false;

		RedisConnectionFactory connectionFactory = redisTemplate.getConnectionFactory();
		RedisConnection redisConnection = connectionFactory.getConnection();
		String lockKey = LOCK_PREFIX + lockName;
		boolean releaseFlag = false;
		while (true) {
			try{
				/** 监视lock，准备开始事务 */
				//redisConnection.watch(lockKey.getBytes());
				byte[] valueBytes = redisConnection.get(lockKey.getBytes());

				/**  value为空表示锁不存在或已经被释放*/
				if(valueBytes == null){
					//redisConnection.unwatch();
					releaseFlag = false;
					break;
				}

				/** 通过前面返回的value值判断是不是该锁，若是该锁，则删除，释放锁 */
				String identifierValue = new String(valueBytes);
				if (identifier.equals(identifierValue)) {
					//redisConnection.multi();
					redisConnection.del(lockKey.getBytes());
//					List<Object> results = redisConnection.exec();
//					if (results == null) {
//						continue;
//					}
					releaseFlag = true;
				}
//				redisConnection.unwatch();
				break;
			}
			catch(Exception e){
				log.warn("释放锁异常", e);
			}
		}
		RedisConnectionUtils.releaseConnection(redisConnection, connectionFactory);
		return releaseFlag;
	}


}
