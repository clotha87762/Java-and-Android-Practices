package netdb.courses.softwarestudio.lab.main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import netdb.courses.softwarestudio.lab.http.HttpRetriever;
import netdb.courses.softwarestudio.lab.model.User;

import com.alibaba.fastjson.JSON;

public class App {

	public static void main(String[] args) throws IOException {

		BufferedReader reader = new BufferedReader(new InputStreamReader(
				System.in));
		System.out.print("Enter your Facebook ID: ");
		String fbId = reader.readLine();

		HttpRetriever retriever = new HttpRetriever();
		Map<String, String> params = new HashMap<String, String>();
		params.put("locale", "zh_TW");

		String jsonString = retriever.getContent("https://graph.facebook.com/"
				+ fbId, params);

		User user = JSON.parseObject(jsonString, User.class);

		System.out.println(user.getUserName() + "/" + user.getName() + "/"
				+ user.getGender());
	}
}
