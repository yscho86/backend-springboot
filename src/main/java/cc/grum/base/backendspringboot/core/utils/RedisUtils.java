package cc.grum.base.backendspringboot.core.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Component
public class RedisUtils {

    private static final long DEFAULT_EXPIRE = 60 * 5;
    private static final long NOT_EXPIRE = -1;

    private static RedisTemplate<String, Object> redisTemplate;

    @Autowired
    public void setRedisTemplate(RedisTemplate redisTemplate) {
        RedisUtils.redisTemplate = redisTemplate;
    }

    // ValueOperations
    public static Object get(String key) {
        LogUtils.d("get key [{}]", key);
        try {
            if (key == null) {
                return null;
            } else {
                return redisTemplate.opsForValue().get(key);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static Object set(String key, Object value, long timeout) {
        LogUtils.d("set key [{}]", key);
        try {
            redisTemplate.opsForValue().set(key, value, timeout, TimeUnit.SECONDS);
            return value;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static void delete(String key) {
        LogUtils.d("delete key [{}]", key);
        redisTemplate.delete(key);
    }

    public static long increment(String key, long delta) {
        if (delta < 0) {
            return -1;
        }
        return redisTemplate.opsForValue().increment(key, delta);
    }

    public static long decrement(String key, long delta) {
        if (delta < 0) {
            return -1;
        }
        return redisTemplate.opsForValue().increment(key, -delta);
    }

    public static void setSet(String key, Object value, long time) {
        LogUtils.d("setSet key [{}]", key);

        SetOperations<String, Object> valueOps = redisTemplate.opsForSet();
        valueOps.add(key, value);
        if (time > 0) {
            redisTemplate.expire(key, time, TimeUnit.SECONDS);
        }
    }

    public static void setSet(String key, Object value) {
        setSet(key, value,  NOT_EXPIRE);
    }


    public static void setSet(String key, Set<Object> v, long time) {
        LogUtils.d("setSet key [{}]", key);
        SetOperations<String, Object> setOps = redisTemplate.opsForSet();
        setOps.add(key, (Object[]) v.toArray());
        if (time > 0) {
            redisTemplate.expire(key, time, TimeUnit.SECONDS);
        }
    }

    public static void setSet(String k, Set<Object> v) {
        setSet(k, v,  NOT_EXPIRE);
    }

    public static Set<Object> getSet(String key) {
        LogUtils.d("getSet key [{}]", key);
        try {
            SetOperations<String, Object> setOps = redisTemplate.opsForSet();
            return setOps.members(key);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static long getSetSize(String key) {
        LogUtils.d("getListSize key [{}]", key);
        try {
            SetOperations<String, Object> setOps = redisTemplate.opsForSet();
            return setOps.size(key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    // ListOperrations

    public static void setList(String key, Object v, long time) {
        LogUtils.d("setList key [{}]", key);
        try {
            ListOperations<String, Object> listOps = redisTemplate.opsForList();
            listOps.rightPush(key, v);
            if (time > 0) {
                redisTemplate.expire(key, time, TimeUnit.SECONDS);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void setList(String key, List<Object> v, long time) {
        LogUtils.d("setList key [{}]", key);
        ListOperations<String, Object> listOps = redisTemplate.opsForList();
        listOps.rightPushAll(key, v);
        if (time > 0) {
            redisTemplate.expire(key, time, TimeUnit.SECONDS);
        }
    }

    public static void setList(String k, List<Object> v) {
        setList(k, v,  NOT_EXPIRE);
    }

    public static List<Object> getList(String key, long start, long end) {
        LogUtils.d("getList key [{}]", key);
        try {
            ListOperations<String, Object> listOps = redisTemplate.opsForList();
            return listOps.range(key, start, end);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static long getListSize(String key) {
        LogUtils.d("getListSize key [{}]", key);
        try {
            ListOperations<String, Object> listOps = redisTemplate.opsForList();
            return listOps.size(key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    // HashOperations

    public static void setMap(String key, String mapKey, Object mapValue) {
        HashOperations<String, String, Object> hashOperations = redisTemplate.opsForHash();
        hashOperations.putIfAbsent(key, mapKey, mapValue);
    }

    public static void deleteMap(String key, String mapKey) {
        HashOperations<String, String, Object> hashOperations = redisTemplate.opsForHash();
        hashOperations.delete(key, mapKey);
    }

    public static Object getMap(String key, String mapKey) {
        HashOperations<String, String, Object> hashOperations = redisTemplate.opsForHash();
        return hashOperations.get(key, mapKey);
    }

    public static List<Object> getMapValues(String key) {
        HashOperations<String, String, Object> hashOperations = redisTemplate.opsForHash();
        return hashOperations.values(key);
    }

    public static long getMapSize(String key) {
        List<Object> list = getMapValues(key);
        return list.size();
    }





    // 캐시에 해당 키 값이 있는지 확인
    public boolean existsKey(String key) {
        return redisTemplate.hasKey(key);
    }

    // 키 이름 변경(newKey 가 존재하는 경우, newKey 의 원래 값을 덮어 씀)
    public void renameKey(String oldKey, String newKey) {
        redisTemplate.rename(oldKey, newKey);
    }

    // 새키 이름이 없는 경우만 이름변경
    public boolean renameKeyNotExist(String oldKey, String newKey) {
        return redisTemplate.renameIfAbsent(oldKey, newKey);
    }

    // 키 삭제
    public void deleteKey(String key) {
        redisTemplate.delete(key);
    }

    // 키 삭제(복수)
    public void deleteKey(String... keys) {
        Set<String> kSet = Stream.of(keys).map(k -> k).collect(Collectors.toSet());
        redisTemplate.delete(kSet);
    }

    // 키 컬렉션 삭제
    public void deleteKey(Collection<String> keys) {
        Set<String> kSet = keys.stream().map(k -> k).collect(Collectors.toSet());
        redisTemplate.delete(kSet);
    }

    // 키의 유효시간 설정
    public void expireKey(String key, long time, TimeUnit timeUnit) {
        redisTemplate.expire(key, time, timeUnit);
    }

    // 키의 만료일자 지정
    public void expireKeyAt(String key, Date date) {
        redisTemplate.expireAt(key, date);
    }

    // 키의 유효기간 조회
    public long getKeyExpire(String key, TimeUnit timeUnit) {
        return redisTemplate.getExpire(key, timeUnit);
    }

    // 키를 영구적 설정
    public void persistKey(String key) {
        redisTemplate.persist(key);
    }

}
