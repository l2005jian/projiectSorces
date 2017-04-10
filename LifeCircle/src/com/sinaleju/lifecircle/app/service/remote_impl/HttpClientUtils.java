package com.sinaleju.lifecircle.app.service.remote_impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.protocol.HTTP;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.sinaleju.lifecircle.app.exception.ADRemoteException;

public class HttpClientUtils {

	private static final int CMNET = 3;
	private static final int CMWAP = 2;
	private static final int WIFI = 1;

	// /**
	// * @author sunny.dai
	// * @param params
	// * @param baseUrl
	// * @param time
	// * @return
	// * @throws ClientProtocolException
	// * @throws IOException
	// */
	// public static String getStringByHttpClientGet(Map<String, String> params,
	// String baseUrl, int time) throws ClientProtocolException,
	// IOException {
	// // 取得取得默认的HttpClient实例
	// DefaultHttpClient httpClient = new DefaultHttpClient();
	// // 创建HttpGet实例
	// HttpGet request = new HttpGet(
	// "http://www.baidu.com?url=http://wap.sohu.com/");
	// // 连接服务器
	// HttpResponse response = httpClient.execute(request);
	// // 读取所有头数据
	// Header[] header = response.getAllHeaders();
	//
	// HashMap<String, String> hm = new HashMap<String, String>();
	// for (int i = 0; i < header.length; i++) {
	// hm.put(header[i].getName(), header[i].getValue());
	// }
	//
	// // 取得数据记录
	// HttpEntity entity = response.getEntity();
	// // 取得数据记录内容
	// InputStream is = entity.getContent();
	// // 显示数据记录内容
	// BufferedReader in = new BufferedReader(new InputStreamReader(is));
	// String str = "";// in.readLine();
	// StringBuffer s = new StringBuffer("");
	// while ((str = in.readLine()) != null) {
	// s.append(str);
	// }
	// // 释放连接
	// httpClient.getConnectionManager().shutdown();
	//
	// return s.toString();
	//
	// }

	/**
	 * @author sunny.dai
	 * @param params
	 * @param baseUrl
	 * @param time
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public static String getStringByHttpUrlConnection(
			Map<String, String> params, String baseUrl, int time)
			throws ClientProtocolException, IOException {

		System.out.print(baseUrl + "?");

		// HttpUrlConnection url

		// remake params
		List<BasicNameValuePair> basicList = new ArrayList<BasicNameValuePair>();
		for (String name : params.keySet()) {
			String value = params.get(name);
			System.out.print(name + "=" + value + "&");
			BasicNameValuePair pair = new BasicNameValuePair(name, value);
			basicList.add(pair);
		}

		System.out.println("");
		// //generate a HttpHost
		HttpPost httpPost = new HttpPost(baseUrl);
		httpPost.setEntity(new UrlEncodedFormEntity(basicList, HTTP.UTF_8));

		// generate a BasicHttpParams
		BasicHttpParams httpParams = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(httpParams, time);
		HttpConnectionParams.setSoTimeout(httpParams, time);

		// generate a HttpClient
		HttpClient httpClient = new DefaultHttpClient(httpParams);

		// request with HttpClient
		HttpResponse response = httpClient.execute(httpPost);

		// read
		StringBuilder builder = new StringBuilder();
		if (response.getStatusLine().getStatusCode() == 200) {
			BufferedReader bufferedReader2 = new BufferedReader(
					new InputStreamReader(response.getEntity().getContent()));
			for (String s = bufferedReader2.readLine(); s != null; s = bufferedReader2
					.readLine()) {
				builder.append(s);
			}
		}

		return builder.toString();
	}

	/**
	 * @author sunny.dai
	 * @param params
	 * @param baseUrl
	 * @param time
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 * @throws ADRemoteException 
	 */
	public static String getStringByHttpClientPost(Map<String, String> params,
			String baseUrl, int time) throws ClientProtocolException,
			IOException, ADRemoteException {

		System.out.print(baseUrl + "?");
		// remake params
		List<BasicNameValuePair> basicList = new ArrayList<BasicNameValuePair>();
		for (String name : params.keySet()) {
			String value = params.get(name);
			System.out.print(name + "=" + value + "&");
			BasicNameValuePair pair = new BasicNameValuePair(name, value);
			basicList.add(pair);
		}
		System.out.println("");
		// generate a HttpHost
		HttpPost httpPost = new HttpPost(baseUrl);

		httpPost.setEntity(new UrlEncodedFormEntity(basicList, HTTP.UTF_8));

		// generate a BasicHttpParams
		BasicHttpParams httpParams = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(httpParams, time);
		HttpConnectionParams.setSoTimeout(httpParams, time);

		// generate a HttpClient
		HttpClient httpClient = new DefaultHttpClient(httpParams);

		// request with HttpClient
		HttpResponse response = httpClient.execute(httpPost);

		// read
		StringBuilder builder = new StringBuilder();
		int resposeCode = response.getStatusLine().getStatusCode();
		if (resposeCode == 200) {
			BufferedReader bufferedReader2 = new BufferedReader(
					new InputStreamReader(response.getEntity().getContent()));
			for (String s = bufferedReader2.readLine(); s != null; s = bufferedReader2
					.readLine()) {
				builder.append(s);
			}
			bufferedReader2.close();
			bufferedReader2 = null;
		}else{
			throw new ADRemoteException("response code is : "+resposeCode+";\n msg: "+response.getStatusLine().getReasonPhrase());
		}

		return builder.toString();
	}

	/**
	 * @author sunny.dai
	 * @param params
	 * @param baseUrl
	 * @param time
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public static String getStringByHttpClientGet(Map<String, String> params,
			String baseUrl, int time) throws ClientProtocolException,
			IOException {
		if(params != null){
			String paramStr = "";
			Iterator iter = params.entrySet().iterator();
			while (iter.hasNext()) {
				Map.Entry entry = (Map.Entry) iter.next();
				Object key = entry.getKey();
				Object val = entry.getValue();
				paramStr += paramStr = "&" + key + "=" + val;
			}
			
			if (!paramStr.equals("")) {
				paramStr = paramStr.replaceFirst("&", "?");
				baseUrl += paramStr;
			}
		}
		System.out.println("baseUrl : " + baseUrl);
		HttpGet httpGet = new HttpGet(baseUrl);

		// generate a BasicHttpParams
		BasicHttpParams httpParams = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(httpParams, time);
		HttpConnectionParams.setSoTimeout(httpParams, time);

		// generate a HttpClient
		HttpClient httpClient = new DefaultHttpClient(httpParams);

		// request with HttpClient
		HttpResponse response = httpClient.execute(httpGet);

		// read
		StringBuilder builder = new StringBuilder();
		if (response.getStatusLine().getStatusCode() == 200) {
			BufferedReader bufferedReader2 = new BufferedReader(
					new InputStreamReader(response.getEntity().getContent()));
			for (String s = bufferedReader2.readLine(); s != null; s = bufferedReader2
					.readLine()) {
				builder.append(s);
			}
		}

		return builder.toString();
	}

	public static InputStream getInputStreamByUrlConn(Map<String, String> params,String baseUrl)
			throws IOException {
		if(params != null){
			String paramStr = "";
			Iterator iter = params.entrySet().iterator();
			while (iter.hasNext()) {
				Map.Entry entry = (Map.Entry) iter.next();
				Object key = entry.getKey();
				Object val = entry.getValue();
				paramStr += paramStr = "&" + key + "=" + val;
			}
			
			if (!paramStr.equals("")) {
				paramStr = paramStr.replaceFirst("&", "?");
				baseUrl += paramStr;
			}
		}
		URL requestUrl = new URL(baseUrl);
		HttpURLConnection conn = (HttpURLConnection) requestUrl
				.openConnection();
		// conn.setRequestMethod("GET");
		conn.setConnectTimeout(10 * 1000);
		System.out.println("Url : " + baseUrl);
		if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
			InputStream is = conn.getInputStream();
			return is;
		}
		return null;
	}

	public static Bitmap getBitmapByUrlConn(Map<String, String> params,String baseUrl)
			throws IOException {
		if(params != null){
			String paramStr = "";
			Iterator iter = params.entrySet().iterator();
			while (iter.hasNext()) {
				Map.Entry entry = (Map.Entry) iter.next();
				Object key = entry.getKey();
				Object val = entry.getValue();
				paramStr += paramStr = "&" + key + "=" + val;
			}
			
			if (!paramStr.equals("")) {
				paramStr = paramStr.replaceFirst("&", "?");
				baseUrl += paramStr;
			}
		}
		URL requestUrl = new URL(baseUrl);
		HttpURLConnection conn = (HttpURLConnection) requestUrl
				.openConnection();
		// conn.setRequestMethod("GET");
		conn.setConnectTimeout(10 * 1000);
		System.out.println("Url : " + baseUrl);
		if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
			InputStream is = conn.getInputStream();
			Bitmap bmp = BitmapFactory.decodeStream(is);
			is.close();
			return bmp;
		}
		return null;
	}
}