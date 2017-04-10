package test;

import java.util.HashSet;
import java.util.Set;

import org.apache.shiro.cache.CacheException;
import org.apache.shiro.subject.SimplePrincipalCollection;

import com.seaky.centralconf.manager.entry.po.User;
import com.seaky.centralconf.manager.shiro.SerializeUtil;

public class RedisData {

	public static void main(String[] args) {
		RedisManagerStandOlone r = new RedisManagerStandOlone();
		r.init();
		try {
			Set<byte[]> hKeys = r.keys("authorizationCache".getBytes());
			Set<Object> keys = new HashSet<Object>();
			for (byte[] bs : hKeys) {
				keys.add(SerializeUtil.unserialize(bs));
			}
			for (Object b : keys) {
				System.out.println(b);
			}
			System.out.println("------------------------------------------------");
			
//			User user = new User();
//			user.setId(836027167769886720l);
//			user.setMobile(1111l);
//			user.setDepartment("研发");
//			user.setPassword("0c90e41f7bb59492cb2e9837250b5814");
//			user.setSalt("aa");
//			user.setSalt("0");
//			user.setType(1);
//			user.setUserName("huangfan");
//			user.setRealName("黄");
//			SimplePrincipalCollection principals = new SimplePrincipalCollection(user, "myShiroRealmName");
//			r.del("authorizationCache".getBytes(), SerializeUtil.serialize(principals));
		} catch (Throwable t) {
			throw new CacheException(t);
		}
	}

}
