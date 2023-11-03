package in.gov.abdm.hip.domain.redis;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

import static in.gov.abdm.hip.constant.HIPConstant.REDIS_VALIDITY_IN_MINUTES;
@Service
@Slf4j
public class RedisAuthService {

	@Autowired
	private RedisTemplate<String, Object> redisTemplate;
	@Value(REDIS_VALIDITY_IN_MINUTES) Long redisValidityInMinutes;

	public void saveToRedis(String redisKey, Object o) {
		redisTemplate.opsForValue().set(redisKey, o);
		redisTemplate.expire(redisKey, Duration.ofMinutes(redisValidityInMinutes));
	}

	public Object getFromRedis(String redisKey) {
		return  redisTemplate.opsForValue().get(redisKey);
	}

}
