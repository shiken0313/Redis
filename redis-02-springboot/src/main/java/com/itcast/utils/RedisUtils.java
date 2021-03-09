package com.itcast.utils;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class RedisUtils {

	private RedisUtils() {
	}

	@Autowired
	private RedisTemplate<String, Object> redisTemplate;

	/**
	 * 設定有效時間
	 *
	 * @param key     Redis鍵
	 * @param timeout 超時時間
	 * @return true=設定成功；false=設定失敗
	 */
	public boolean expire(final String key, final long timeout) {

		return expire(key, timeout, TimeUnit.SECONDS);
	}

	/**
	 * 設定有效時間
	 *
	 * @param key     Redis鍵
	 * @param timeout 超時時間
	 * @param unit    時間單位
	 * @return true=設定成功；false=設定失敗
	 */
	public boolean expire(final String key, final long timeout, final TimeUnit unit) {

		Boolean ret = redisTemplate.expire(key, timeout, unit);
		return ret != null && ret;
	}

	/**
	 * 刪除單個key
	 *
	 * @param key 鍵
	 * @return true=刪除成功；false=刪除失敗
	 */
	public boolean del(final String key) {

		Boolean ret = redisTemplate.delete(key);
		return ret != null && ret;
	}

	/**
	 * 刪除多個key
	 *
	 * @param keys 鍵集合
	 * @return 成功刪除的個數
	 */
	public long del(final Collection<String> keys) {

		Long ret = redisTemplate.delete(keys);
		return ret == null ? 0 : ret;
	}

	/**
	 * 存入普通物件
	 *
	 * @param key   Redis鍵
	 * @param value 值
	 */
	public void set(final String key, final Object value) {

		redisTemplate.opsForValue().set(key, value, 1, TimeUnit.MINUTES);
	}

	// 儲存普通物件操作

	/**
	 * 存入普通物件
	 *
	 * @param key     鍵
	 * @param value   值
	 * @param timeout 有效期，單位秒
	 */
	public void set(final String key, final Object value, final long timeout) {

		redisTemplate.opsForValue().set(key, value, timeout, TimeUnit.SECONDS);
	}

	/**
	 * 獲取普通物件
	 *
	 * @param key 鍵
	 * @return 物件
	 */
	public Object get(final String key) {

		return redisTemplate.opsForValue().get(key);
	}

	// 儲存Hash操作

	/**
	 * 往Hash中存入資料
	 *
	 * @param key   Redis鍵
	 * @param hKey  Hash鍵
	 * @param value 值
	 */
	public void hPut(final String key, final String hKey, final Object value) {

		redisTemplate.opsForHash().put(key, hKey, value);
	}

	/**
	 * 往Hash中存入多個數據
	 *
	 * @param key    Redis鍵
	 * @param values Hash鍵值對
	 */
	public void hPutAll(final String key, final Map<String, Object> values) {

		redisTemplate.opsForHash().putAll(key, values);
	}

	/**
	 * 獲取Hash中的資料
	 *
	 * @param key  Redis鍵
	 * @param hKey Hash鍵
	 * @return Hash中的物件
	 */
	public Object hGet(final String key, final String hKey) {

		return redisTemplate.opsForHash().get(key, hKey);
	}

	/**
	 * 獲取多個Hash中的資料
	 *
	 * @param key   Redis鍵
	 * @param hKeys Hash鍵集合
	 * @return Hash物件集合
	 */
	public List<Object> hMultiGet(final String key, final Collection<Object> hKeys) {

		return redisTemplate.opsForHash().multiGet(key, hKeys);
	}

	// 儲存Set相關操作

	/**
	 * 往Set中存入資料
	 *
	 * @param key    Redis鍵
	 * @param values 值
	 * @return 存入的個數
	 */
	public long sSet(final String key, final Object... values) {
		Long count = redisTemplate.opsForSet().add(key, values);
		return count == null ? 0 : count;
	}

	/**
	 * 刪除Set中的資料
	 *
	 * @param key    Redis鍵
	 * @param values 值
	 * @return 移除的個數
	 */
	public long sDel(final String key, final Object... values) {
		Long count = redisTemplate.opsForSet().remove(key, values);
		return count == null ? 0 : count;
	}

	// 儲存List相關操作

	/**
	 * 往List中存入資料
	 *
	 * @param key   Redis鍵
	 * @param value 資料
	 * @return 存入的個數
	 */
	public long lPush(final String key, final Object value) {
		Long count = redisTemplate.opsForList().rightPush(key, value);
		return count == null ? 0 : count;
	}

	/**
	 * 往List中存入多個數據
	 *
	 * @param key    Redis鍵
	 * @param values 多個數據
	 * @return 存入的個數
	 */
	public long lPushAll(final String key, final Collection<Object> values) {
		Long count = redisTemplate.opsForList().rightPushAll(key, values);
		return count == null ? 0 : count;
	}

	/**
	 * 往List中存入多個數據
	 *
	 * @param key    Redis鍵
	 * @param values 多個數據
	 * @return 存入的個數
	 */
	public long lPushAll(final String key, final Object... values) {
		Long count = redisTemplate.opsForList().rightPushAll(key, values);
		return count == null ? 0 : count;
	}

	/**
	 * 從List中獲取begin到end之間的元素
	 *
	 * @param key   Redis鍵
	 * @param start 開始位置
	 * @param end   結束位置（start=0，end=-1表示獲取全部元素）
	 * @return List物件
	 */
	public List<Object> lGet(final String key, final int start, final int end) {
		return redisTemplate.opsForList().range(key, start, end);
	}
}