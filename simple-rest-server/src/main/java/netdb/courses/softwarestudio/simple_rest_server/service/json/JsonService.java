package netdb.courses.softwarestudio.simple_rest_server.service.json;

import com.alibaba.fastjson.JSON;

public class JsonService {
	public static String serialize(Object obj) {
		try {
			return JSON.toJSONString(obj);
		} catch(Exception e) {
			throw new JsonServiceException(e);
		}
	}
	
	public static <T> T deserialize(String str, Class<T> cls) {
		try {
			return JSON.parseObject(str, cls);
		} catch(Exception e) {
			throw new JsonServiceException(e);
		}
	}
	
}
