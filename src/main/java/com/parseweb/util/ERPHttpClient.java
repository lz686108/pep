package com.parseweb.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ERPHttpClient {

	private static final Logger logger = LoggerFactory.getLogger(ERPHttpClient.class);

	/**
	 * 设为全局变量是为了内部cookie管理
	 */
	private CloseableHttpClient httpClient = HttpClients.createDefault();
	
	/**
	 * 测试代理IP用
	 */
	private CloseableHttpClient testClient = HttpClients.createDefault();

	private final static String loginUrl = "http://erp.ejianlong.com/sso/login";

	private final String proxyAPI = "http://10.6.1.88:8888/dd/web/data/eng2chn/proxyip";
	private final String proxyIPS = "http://10.6.1.88:8888/dd/web/data/key/proxyips";

	public static final String CONTENT_TYPE_NAME = "Content-Type";

	public static final String CONTENT_TYPE_JSON = "application/json;charset=UTF-8";

	public static final String CONTENT_TYPE_XML = "text/xml;charset=UTF-8";

	public static final String CONTENT_TYPE_FORM = "application/x-www-form-urlencoded;charset=UTF-8";

	public static final String ACCEPT_NAME = "Accept";

	public static final String ACCEPT = "application/json;charset=UTF-8";

	public static final String LOGIN_SUCCESS = "Jianlong SSO Login";

	final static ObjectMapper jackson = new ObjectMapper();

	private static SimpleDateFormat DATE_HH = new SimpleDateFormat("HH");

	boolean isProxy = false;
	//true 代理
	//false 本地
	/**
	 * 2019年9月18日 总部信息化封锁爬虫程序所在电脑IP，不得不采用代理服务器对抗反爬虫 but,
	 * 在20日就被发现使用代理并全面封杀，直到22日下午才发现。
	 * 9月25日 架设高匿代理IP
	 * 
	 * @return
	 */
	public RequestConfig useProxy() {
		// 2019年9月26日 把分时分IP策略改成这个
		String proxyIP = getProxyIPByTest();
		return useProxy(proxyIP);
	}

	public RequestConfig useProxy(String proxyIP) {
		HttpHost proxy = new HttpHost(proxyIP, 8828, "http");
		RequestConfig requestConfig = RequestConfig.custom().setProxy(proxy).setConnectTimeout(1000000000)
				.setSocketTimeout(10000000).setConnectionRequestTimeout(30000000).build();
		return requestConfig;
	}

	public void setProxy(HttpPost httpost) {
		httpost.setProtocolVersion(HttpVersion.HTTP_1_1);
		httpost.addHeader(CONTENT_TYPE_NAME, CONTENT_TYPE_FORM);
		httpost.addHeader(ACCEPT_NAME, ACCEPT);
		httpost.addHeader(HTTP.CONN_DIRECTIVE, HTTP.CONN_CLOSE);
		logger.info("isProxy:{}", isProxy);
		if (isProxy) {
			httpost.setConfig(useProxy());
		} else {
			RequestConfig config = RequestConfig.custom().setConnectTimeout(1000000000) // 设置连接超时时间10秒钟
					.setSocketTimeout(2000000000) // 设置读取超时时间10秒钟
					.setConnectionRequestTimeout(300000000).build();
			httpost.setConfig(config);
		}
	}

	/**
	 * 2019年9月23日 发现需要模拟IE浏览器才能成功登入
	 * 
	 * @param httpost
	 */
	private void setPOSTHeader(HttpPost httpost) {
		httpost.addHeader("Accept", "image/gif, image/jpeg, image/pjpeg, application/x-ms-application, application/xaml+xml, application/x-ms-xbap, */*");
		httpost.addHeader("Host", "erp.ejianlong.com");
		httpost.addHeader("Connection", "Keep-Alive");
		httpost.addHeader("Referer", "http://erp.ejianlong.com/jlerp/ka/do?pageId=kajjGateway");
		// 需要IE浏览器
		httpost.addHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.1; Trident/4.0)");		
	}

	private void setGETHeader(HttpGet httpget) {
		httpget.addHeader("Accept", "image/gif, image/jpeg, image/pjpeg, application/x-ms-application, application/xaml+xml, application/x-ms-xbap, */*");
		httpget.addHeader("Host", "erp.ejianlong.com");
		httpget.addHeader("Connection", "Keep-Alive");
		httpget.addHeader("Referer", "http://erp.ejianlong.com/jlerp/ka/do?pageId=kajjGateway");
		// 需要IE浏览器
		httpget.addHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.1; Trident/4.0)");				
	}

	/**
	 * 登陆总部ERP及新区ERP SSO。
	 * 
	 * @param userNo
	 * @param password
	 * @return
	 */
	public String loginERP(String userNo, String password) {
		String uri = "http://erp.ejianlong.com:80/jlerp/ka/do?pageId=kajjGateway";
		StringBuffer strResult = new StringBuffer();
		HttpPost httpost = new HttpPost(loginUrl);
		setProxy(httpost);
		setPOSTHeader(httpost);
		List<NameValuePair> nvp = new ArrayList<NameValuePair>();
		nvp.add(new BasicNameValuePair("userNo", userNo));
		nvp.add(new BasicNameValuePair("password", password));
		nvp.add(new BasicNameValuePair("uri", uri));
		httpost.setEntity(new UrlEncodedFormEntity(nvp, Charset.forName("UTF-8")));
		int httpCode = 0;
		try {
			CloseableHttpResponse response = httpClient.execute(httpost);
			httpCode = response.getStatusLine().getStatusCode();
			if (httpCode == 200) {
				HttpEntity entity = response.getEntity();
				// 获取网页源码信息
				strResult.append(EntityUtils.toString(entity, "UTF-8"));
				logger.info("{}", strResult.toString());
			} else if (httpCode == 302) {
				// 以下代码是参考网络资源蒙到的。
				// 跳转的目标地址是在 HTTP-HEAD 中的
				Header header = response.getFirstHeader("location");
				// 这就是跳转后的地址，再向这个地址发出新申请，以便得到跳转后的信息。
				String newuri = header.getValue();
				logger.error("httpCode:{}, transfer to new uri:{}", httpCode, newuri);
				httpost = new HttpPost(newuri);
				httpost.addHeader("Accept", "text/plain");
				httpost.addHeader("User-Agent",
						"Mozilla/5.0 (Windows NT 6.1; WOW64; rv:45.0) Gecko/20100101 Firefox/45.0");
				httpost.setEntity(new UrlEncodedFormEntity(nvp, Charset.forName("UTF-8")));
				response = httpClient.execute(httpost);
				httpCode = response.getStatusLine().getStatusCode();
				logger.error("302 redirect result code:{}", httpCode);
			} else {
				logger.error("login failed！httpCode:{}", httpCode);
			}
		} catch (Exception e) {
			logger.error("login failed. httpCode:{}", httpCode, e);
		} finally {
			httpost.abort();
		}
		return strResult.toString();
	}
	
	public String longin(String userNo, String password) {
		StringBuffer strResult = new StringBuffer();
		HttpPost httpost = new HttpPost(loginUrl);
		httpost.addHeader("Accept", "text/plain");
		List<NameValuePair> nvp = new ArrayList<NameValuePair>();
		nvp.add(new BasicNameValuePair("userNo", userNo));
		nvp.add(new BasicNameValuePair("password", password));
		httpost.setEntity(new UrlEncodedFormEntity(nvp, Charset.forName("UTF-8")));
		try {
			HttpResponse response = httpClient.execute(httpost);

			if (response.getStatusLine().getStatusCode() == 200) {
				HttpEntity entity = response.getEntity();
				// 获取网页源码信息
				strResult.append(EntityUtils.toString(entity, "UTF-8"));
			} else {
				logger.error("登录失败！status code:{}", response.getStatusLine().getStatusCode());
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			httpost.abort();
		}
		return strResult.toString();
	}

	public String loginERPTest(String userNo, String password, String proxyIP) {
		String uri = "http://erp.ejianlong.com:80/jlerp/ka/do?pageId=kajjGateway";
		StringBuffer strResult = new StringBuffer();
		HttpPost httpost = new HttpPost(loginUrl);
		httpost.setConfig(useProxy(proxyIP));
		setPOSTHeader(httpost);
		List<NameValuePair> nvp = new ArrayList<NameValuePair>();
		nvp.add(new BasicNameValuePair("userNo", userNo));
		nvp.add(new BasicNameValuePair("password", password));
		nvp.add(new BasicNameValuePair("uri", uri));
		httpost.setEntity(new UrlEncodedFormEntity(nvp, Charset.forName("UTF-8")));
		int httpCode = 0;
		try {
			HttpResponse response = httpClient.execute(httpost);
			httpCode = response.getStatusLine().getStatusCode();
			logger.info("httpCode:{}", httpCode);
			if (httpCode == 200) {
				HttpEntity entity = response.getEntity();
				// 获取网页源码信息
				strResult.append(EntityUtils.toString(entity, "UTF-8"));
				logger.info("login OK! httpCode:{}", httpCode);
				logger.info("login OK! strResult:{}", strResult);
				if (!strResult.toString().contains(LOGIN_SUCCESS)) {
					httpCode = 0;
				}
			} else {
				Header header = response.getFirstHeader("location");
				// 这就是跳转后的地址，再向这个地址发出新申请，以便得到跳转后的信息。
				String newuri = header.getValue();
				System.out.println(newuri);
			}

		} catch (Exception e) {
			logger.error("login failed. httpCode:{}", httpCode, e);
		} finally {
			httpost.abort();
		}
		return strResult.toString();
	}

	public int testIP(String userNo, String password, String proxyIP) {
		String uri = "http://erp.ejianlong.com:80/jlerp/ka/do?pageId=kajjGateway";
		int httpCode = 0;
		StringBuffer strResult = new StringBuffer();
		HttpPost httpost = new HttpPost(loginUrl);
		httpost.setConfig(useProxy(proxyIP));
		setPOSTHeader(httpost);
		List<NameValuePair> nvp = new ArrayList<NameValuePair>();
		nvp.add(new BasicNameValuePair("userNo", userNo));
		nvp.add(new BasicNameValuePair("password", password));
		nvp.add(new BasicNameValuePair("uri", uri));
		httpost.setEntity(new UrlEncodedFormEntity(nvp, Charset.forName("UTF-8")));
		try {
			HttpResponse response = testClient.execute(httpost);
			httpCode = response.getStatusLine().getStatusCode();
			if (httpCode == 200) {
				HttpEntity entity = response.getEntity();
				// 获取网页源码信息
				strResult.append(EntityUtils.toString(entity, "UTF-8"));
				// 必须检查是否有登录成功信息，这是2019年9月18日之后总部增加的反代理IP措施。
				if (!strResult.toString().contains(LOGIN_SUCCESS)) {
					httpCode = 0;
					logger.error("proxyIP:{}, login failed. httpCode:{}", proxyIP, httpCode);
				} else {
					logger.info("test IP:{}, login OK!", proxyIP);
				}
			} else {
				Header header = response.getFirstHeader("location");
				// 用来确认总部是根据正常IP还是代理IP封的。如果是代理IP，则不管是什么IP都被封。
				String newuri = header.getValue();
				logger.error("proxyIP:{}, login failed. newuri:{}", proxyIP, newuri);
			}
		} catch (Exception e) {
			logger.error("login failed. httpCode:{}", httpCode, e);
		} finally {
			httpost.abort();
		}
		return httpCode;
	}

	/**
	 * 登陆总部新质量系统，使用2007年第一版新架构开发的版本。 使用到http code 302 redirect 重定向
	 * 
	 * @return html
	 */
	public String loginQA() {
		String qaLogin = "http://10.6.1.38:8080/xtqc/pages/jsp/loginSuccess.jsp";
		StringBuffer strResult = new StringBuffer();
		HttpPost httpost = new HttpPost(qaLogin);
		// httpost.setConfig(useProxy());
		httpost.addHeader("Accept", "text/plain");
		httpost.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:45.0) Gecko/20100101 Firefox/45.0");
		List<NameValuePair> nvp = new ArrayList<NameValuePair>();
		nvp.add(new BasicNameValuePair("j_username", "217342"));
		nvp.add(new BasicNameValuePair("j_password", "axzj_5419"));
		nvp.add(new BasicNameValuePair("postUri", ""));
		httpost.setEntity(new UrlEncodedFormEntity(nvp, Charset.forName("UTF-8")));
		int httpCode = 0;
		try {
			HttpResponse response = httpClient.execute(httpost);
			httpCode = response.getStatusLine().getStatusCode();
			System.out.println(httpCode);
			if (httpCode == 200) {
				HttpEntity entity = response.getEntity();
				// 获取网页源码信息
				strResult.append(EntityUtils.toString(entity, "UTF-8"));
			} else if (httpCode == 302) {
				// 以下代码是参考网络资源蒙到的。
				// 跳转的目标地址是在 HTTP-HEAD 中的
				Header header = response.getFirstHeader("location");
				// 这就是跳转后的地址，再向这个地址发出新申请，以便得到跳转后的信息。
				String newuri = header.getValue();
				logger.info("httpCode:{}, transfer to new uri:{}", httpCode, newuri);
				httpost = new HttpPost(newuri);
				httpost.setHeader("Content-Type", "application/json;charset=UTF-8");
				httpost.addHeader("User-Agent",
						"Mozilla/5.0 (Windows NT 6.1; WOW64; rv:45.0) Gecko/20100101 Firefox/45.0");
				httpost.setEntity(new UrlEncodedFormEntity(nvp, Charset.forName("UTF-8")));
				response = httpClient.execute(httpost);
				httpCode = response.getStatusLine().getStatusCode();
				logger.info("302 redirect result code:{}", httpCode);
			} else {
				logger.info("login failed！status code:{}:{}", httpCode);
			}
		} catch (Exception e) {
			logger.error("login failed. httpCode:{}", httpCode, e);
		} finally {
			httpost.abort();
		}
		return strResult.toString();
	}

	public String doPost(String path, List<NameValuePair> nvp) {
		String html = "";
		HttpPost httpost = new HttpPost(path);
		try {
			setPOSTHeader(httpost);
			httpost.setEntity(new UrlEncodedFormEntity(nvp, Charset.forName("UTF-8")));
			logger.info("before setProxy(httpost)");
			setProxy(httpost);
			logger.info("before execute post");
			HttpResponse response = httpClient.execute(httpost);
			int httpCode = response.getStatusLine().getStatusCode();
			logger.info("httpCode:{}", httpCode);
			HttpEntity entity = response.getEntity();
			html = EntityUtils.toString(entity, "UTF-8");
		} catch (Exception e) {
			logger.error("doPost failed. path:{}", path, e);
		} finally {
			httpost.abort();
		}
		return html;
	}
	
	/**
	 * 登陆总部新质量系统，使用2007年第一版新架构开发的版本。 使用到http code 302 redirect 重定向
	 * 
	 * @return html
	 */
	public String loginQA01() {
		String qaLogin = "http://10.6.1.38:8080/xtqc/pages/jsp/loginSuccess.jsp";
		StringBuffer strResult = new StringBuffer();
		HttpPost httpost = new HttpPost(qaLogin);
		// httpost.setConfig(useProxy());
		httpost.addHeader("Accept", "text/plain");
		httpost.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:45.0) Gecko/20100101 Firefox/45.0");
		List<NameValuePair> nvp = new ArrayList<NameValuePair>();
		nvp.add(new BasicNameValuePair("j_username", "210377"));
		nvp.add(new BasicNameValuePair("j_password", "19971201"));
		nvp.add(new BasicNameValuePair("postUri", ""));
		httpost.setEntity(new UrlEncodedFormEntity(nvp, Charset.forName("UTF-8")));
		int httpCode = 0;
		try {
			HttpResponse response = httpClient.execute(httpost);
			httpCode = response.getStatusLine().getStatusCode();
			System.out.println(httpCode);
			if (httpCode == 200) {
				HttpEntity entity = response.getEntity();
				// 获取网页源码信息
				strResult.append(EntityUtils.toString(entity, "UTF-8"));
			} else if (httpCode == 302) {
				// 以下代码是参考网络资源蒙到的。
				// 跳转的目标地址是在 HTTP-HEAD 中的
				Header header = response.getFirstHeader("location");
				// 这就是跳转后的地址，再向这个地址发出新申请，以便得到跳转后的信息。
				String newuri = header.getValue();
				logger.info("httpCode:{}, transfer to new uri:{}", httpCode, newuri);
				httpost = new HttpPost(newuri);
				httpost.setHeader("Content-Type", "application/json;charset=UTF-8");
				httpost.addHeader("User-Agent",
						"Mozilla/5.0 (Windows NT 6.1; WOW64; rv:45.0) Gecko/20100101 Firefox/45.0");
				httpost.setEntity(new UrlEncodedFormEntity(nvp, Charset.forName("UTF-8")));
				response = httpClient.execute(httpost);
				httpCode = response.getStatusLine().getStatusCode();
				logger.info("302 redirect result code:{}", httpCode);
			} else {
				logger.info("login failed！status code:{}:{}", httpCode);
			}
		} catch (Exception e) {
			logger.error("login failed. httpCode:{}", httpCode, e);
		} finally {
			httpost.abort();
		}
		return strResult.toString();
	}
	
	/**
	 * 
	 * @param path
	 * @return
	 */
	public String doGet(String path) {
		String html = "";
		HttpGet httpget = new HttpGet(path);
		if (isProxy) {
			httpget.setConfig(useProxy());
		}
		setGETHeader(httpget);
		try {
			// 要使用之前登录过的httpClient实例
			HttpResponse response = httpClient.execute(httpget);
			HttpEntity entity = response.getEntity();
			html = EntityUtils.toString(entity, "UTF-8");
		} catch (Exception e) {
			logger.error("doGet failed. path:{}", path, e);
		} finally {
			httpget.abort();
		}
		return html;
	}

	/**
	 * 用在访问的path返回的是文件时，用byte[]接收。 第一次使用场景时下载质量系统的报表。
	 * 
	 * @param path
	 * @return
	 */
	public byte[] getByteStream(String path) {
		System.out.println(String.format("", path));
		byte[] result = null;
		HttpGet httpget = new HttpGet(path);
		if (isProxy) {
			httpget.setConfig(useProxy());
		}
		setGETHeader(httpget);
		try {
			// 要使用之前登录过的httpClient实例
			HttpResponse response = httpClient.execute(httpget);
			HttpEntity entity = response.getEntity();
			result = EntityUtils.toByteArray(entity);
		} catch (Exception e) {
			logger.error("doGetGetStream failed. path:{}", path, e);
		} finally {
			httpget.abort();
		}
		return result;
	}

	public String doPost(String path, List<NameValuePair> nvp, String unicode) {
		String html = "";
		HttpPost httpost = new HttpPost(path);
		setProxy(httpost);
		try {
			setPOSTHeader(httpost);
			httpost.setEntity(new UrlEncodedFormEntity(nvp, unicode));
			HttpResponse response = httpClient.execute(httpost);
			HttpEntity entity = response.getEntity();
			html = EntityUtils.toString(entity, unicode);
		} catch (Exception e) {
			logger.error("doGetGetStream failed. path:{}", path, e);
		} finally {
			httpost.abort();
		}
		return html;
	}

	public String doSalesPost(String path, List<NameValuePair> nvp, String unicode) {
		String html = "";
		HttpPost httpost = new HttpPost(path);
		setProxy(httpost);
		try {
			setPOSTHeader(httpost);
			httpost.addHeader("Accept",
					"image/gif, image/jpeg, image/pjpeg, application/x-ms-application, application/xaml+xml, application/x-ms-xbap, */*");
			httpost.addHeader("Accept-Language", "zh-CN");
			httpost.addHeader("Referer", "http://erp.ejianlong.com/jlerp/sb/do?pageId=sbjjComplexReportPop");
			httpost.addHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.1; Trident/4.0)");
			httpost.setEntity(new UrlEncodedFormEntity(nvp, Charset.forName(unicode)));
			HttpResponse response = httpClient.execute(httpost);
			HttpEntity entity = response.getEntity();
			html = EntityUtils.toString(entity, unicode);
		} catch (Exception e) {
			logger.error("doGetGetStream failed. path:{}", path, e);
		} finally {
			httpost.abort();
		}
		return html;
	}

	/**
	 * 专门用来调用API的POST方法
	 * 
	 * @param path
	 * @param parameters 需要使用json解析器把json参数转成String
	 * @return
	 */
	public String doAPIPost(String path, String parameters) {
		String result = "";
		HttpPost method = new HttpPost(path);
		// method.setConfig(useProxy());
		try {
			method.addHeader("Content-type", "text/plain; charset=utf-8");
			method.setHeader("Accept", "text/plain");
			method.setEntity(new StringEntity(parameters, Charset.forName("UTF-8")));
			HttpResponse response = httpClient.execute(method);
			int statusCode = response.getStatusLine().getStatusCode();
			logger.info("statusCode:" + statusCode);
			result = EntityUtils.toString(response.getEntity(), "UTF-8");
		} catch (Exception e) {
			logger.error("doAPIPost failed. path:{}, parameters", path, parameters, e);
		} finally {
			method.abort();
		}
		return result;
	}

	public String doAPIGETJSON(String path) {
		String result = "";
		HttpGet method = new HttpGet(path);
		try {
			method.addHeader("Content-type", "application/json; charset=utf-8");
			HttpResponse response = httpClient.execute(method);
			result = EntityUtils.toString(response.getEntity(), "UTF-8");
		} catch (Exception e) {
			logger.error("doAPIPost failed. path:{}", path, e);
		} finally {
			method.abort();
		}
		return result;
	}

	public String doAPIPost2(String path, String parameters) {
		String result = "";
		HttpPost method = new HttpPost(path);
		// method.setConfig(useProxy());
		logger.info("parameters:" + parameters);
		try {
			method.addHeader("Content-type", "application/x-www-form-urlencoded");
			method.addHeader("Accept", "*/*");
			method.setEntity(new StringEntity(parameters, Charset.forName("UTF-8")));
			HttpResponse response = httpClient.execute(method);
			int statusCode = response.getStatusLine().getStatusCode();
			logger.info("statusCode:" + statusCode);
			result = EntityUtils.toString(response.getEntity(), "UTF-8");
		} catch (Exception e) {
			logger.error("doAPIPost failed. path:{}, parameters", path, parameters, e);
		} finally {
			method.abort();
		}
		return result;
	}

	public static String getCompId(Document doc) {
		return doc.select("input[name=compId]").first().attr("value");
	}

	public void doSalesPost2File(String path, List<NameValuePair> nvp, String filePath) {
		HttpPost httpost = new HttpPost(path);
		setProxy(httpost);
		try {
			setPOSTHeader(httpost);
			httpost.addHeader("Accept",
					"image/gif, image/jpeg, image/pjpeg, application/x-ms-application, application/xaml+xml, application/x-ms-xbap, */*");
			httpost.addHeader("Accept-Language", "zh-CN");
			httpost.addHeader("Referer", "http://erp.ejianlong.com/jlerp/sb/do?pageId=sbjjComplexReportPop");
			httpost.addHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.1; Trident/4.0)");
			httpost.setEntity(new UrlEncodedFormEntity(nvp));
			HttpResponse response = httpClient.execute(httpost);
			HttpEntity entity = response.getEntity();
			// 取得文件的流数据格式。
			InputStream is = entity.getContent();
			File file = new File(filePath);
			file.getParentFile().mkdirs();
			FileOutputStream fileout = new FileOutputStream(file);
			byte[] buffer = new byte[1024];
			int ch = 0;
			while ((ch = is.read(buffer)) != -1) {
				fileout.write(buffer, 0, ch);
			}
			// 关闭流处理
			is.close();
			fileout.flush();
			fileout.close();

		} catch (Exception e) {
			logger.error("doSalesPost2File failed. path:{}", path, e);
		} finally {
			httpost.abort();
		}
	}

	/**
	 * @param url       文件下载的路径
	 * @param localPath 保存到本地的目录加档名，注意副档名要正确
	 * @return
	 */
	public int getFile(String url, String localPath) {
		int OK = 0;
		long startTime = System.currentTimeMillis();
		try {
			HttpGet httpget = new HttpGet(url);
			if (isProxy) {
				httpget.setConfig(useProxy());
			}
			setGETHeader(httpget);
			httpget.addHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.1; Trident/4.0)");
			HttpResponse response = httpClient.execute(httpget);
			HttpEntity entity = response.getEntity();
			// 取得文件的流数据格式。
			InputStream is = entity.getContent();
			File file = new File(localPath);
			file.getParentFile().mkdirs();
			FileOutputStream fileout = new FileOutputStream(file);
			byte[] buffer = new byte[1024];
			int ch = 0;
			while ((ch = is.read(buffer)) != -1) {
				fileout.write(buffer, 0, ch);
			}
			// 关闭流处理
			is.close();
			fileout.flush();
			fileout.close();
		} catch (Exception e) {
			logger.error("url:{}, localPath:{}, 下载失败。", url, localPath, e);
			OK = -1;
		}
		logger.info("downloading cost {} seconds for file: {}", (System.currentTimeMillis() - startTime) / 1000,
				localPath);
		return OK;
	}

	@SuppressWarnings("unchecked")
	public String getProxyIP() {
		String ip = "10.6.50.12";
		try {
			String s = doAPIGETJSON(proxyAPI);
			HashMap<String, String> jsonMap = jackson.readValue(s, HashMap.class);
			ip = jsonMap.get("cname");
			System.out.println("proxyIP:" + ip);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ip;
	}

	@SuppressWarnings("unchecked")
	public String getProxyIPByTest() {
		String ip = "127.0.0.1";
		try {
			String s = doAPIGETJSON(proxyIPS);
			HashMap<String, String> jsonMap = jackson.readValue(s, HashMap.class);
			String ips = jsonMap.get("cname");
			String[] ipArray = ips.split(",");
			for (String pp : ipArray) {
				int code = testIP("100360", "87885683", pp);
				if(code == 200) {
					return pp;
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ip;
	}

	
	/**
	 * No Use.分时分IP的策略无效，被群封。
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String getProxyIPByTimeZone() {
		String ip = "";
		try {
			String s = doAPIGETJSON(proxyIPS);
			HashMap<String, String> jsonMap = jackson.readValue(s, HashMap.class);
			String ips = jsonMap.get("cname");
			String[] ipArray = ips.split(",");
			Date d = new Date();
			String hh = DATE_HH.format(d);
			int hd = Integer.parseInt(hh);
			if (hd < 8) {
				ip = ipArray[0];
			} else if (hd >= 8 && hd < 12) {
				ip = ipArray[1];
			} else if (hd >= 12 && hd < 17) {
				ip = ipArray[2];
			} else if (hd >= 17) {
				ip = ipArray[3];
			}
			logger.info("hh:{}, ip:{}", hh, ip);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ip;
	}

	public static void main(String[] args) throws JsonParseException, JsonMappingException, IOException {
		ERPHttpClient client = new ERPHttpClient();
		//client.loginERP("100360", "87885683");
		client.loginERPTest("216323", "216323224", "10.6.49.61");
		// client.loginERPTest("216323", "216323224", "10.6.49.8");
		// loginERPTest("216323", "216323224", "10.6.49.11");
		// client.loginERPTest("216323", "216323224", "10.6.49.10");
		// client.longinQA();
		// System.out.println(client.getProxyIP());
		// String s = client.getProxyIPByTimeZone();
	}

}
