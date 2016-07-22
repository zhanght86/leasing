/**
 * 对公众平台发送给公众账号的消息加解密示例代码.
 * 
 * @copyright Copyright (c) 1998-2014 Tencent Inc.
 */

// ------------------------------------------------------------------------

/**
 * 针对org.apache.commons.codec.binary.Base64，
 * 需要导入架包commons-codec-1.9（或commons-codec-1.8等其他版本）
 * 官方下载地址：http://commons.apache.org/proper/commons-codec/download_codec.cgi
 */
package com.pqsoft.weixinfw.utils.weixin;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.StringWriter;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import com.pqsoft.dataDictionary.service.SysDictionaryMemcached;
import com.pqsoft.skyeye.Log;
import com.pqsoft.weixinfw.utils.PQsoftStringUtil;


/**
 * 提供接收和推送给公众平台消息的加解密接口(UTF8编码的字符串).
 * 说明：异常java.security.InvalidKeyException:illegal Key Size的解决方案
 */
public final class WeiXinMsgCryptUtils {
	private Charset CHARSET = Charset.forName("utf-8");
	private Base64 base64 = new Base64();
	private byte[] aesKey = Base64.decodeBase64(encodingAesKey + "=");
	
	
	private static String encodingAesKey = null;
	private static String token = null;
	private static String appId = null;
	private static String appSecret = null;
	private static String callbackuri = null;
	private static boolean  debug;
	static {
		@SuppressWarnings("unchecked")
		List<Map<String,Object>> list = (List<Map<String,Object>>) SysDictionaryMemcached.getList("微信服务号参数");
		for (Map map : list) {
			if ("token".equals(map.get("FLAG")))
				token = map.get("CODE") + "";
			if ("encodingAESKey".equals(map.get("FLAG")))
				encodingAesKey = map.get("CODE") + "";
			if ("fw_appId".equals(map.get("FLAG")))
				appId = map.get("CODE") + "";
			if ("fw_secret".equals(map.get("FLAG")))
				appSecret = map.get("CODE") + "";
			if ("fw_callbackuri".equals(map.get("FLAG")))
				callbackuri = map.get("CODE") + "";
			try {
				if ("debug".equals(map.get("FLAG")))
					debug = Boolean.parseBoolean(map.get("CODE") + "");
			} catch (Exception e) {
			}
		}
	}
	private final static String OAuth2_scope = "snsapi_base"; // snsapi_userinfo
	/**
	 * <pre>
	 * 用户同意授权，获取code。
	 * scope 应用授权作用域 ：snsapi_base （不弹出授权页面，直接跳转，只能获取用户openid），
	 * snsapi_userinfo （弹出授权页面，可通过openid拿到昵称、性别、所在地。并且，即使在未关注的情况下，只要用户授权，也能获取其信息）
	 * </pre>
	 */
	public final static String API_oauth2_authorize = String.format("https://open.weixin.qq.com/connect/oauth2/authorize?appid=%1$s&redirect_uri=%2$s&response_type=code&scope=%3$s&state=%4$s#wechat_redirect", 
			appId , "%1$s" , OAuth2_scope , PQsoftStringUtil.verify_code()); 
	/**
	 * <pre>
	 * 获取access_token：
	 * code作为换取access_token的票据，每次用户授权带上的code将不一样，code只能使用一次，5分钟未被使用自动过期。
	 * </pre>
	 */
	public final static String API_oauth2_access_token = String.format("https://api.weixin.qq.com/sns/oauth2/access_token?appid=%1$s&secret=%2$s&code=%3$s&grant_type=authorization_code", 
			appId, appSecret, "%1$s");
	/**
	 * <pre>
	 * 刷新access_token：
	 * 获取第二步的refresh_token后，请求以下链接获取access_token,
	 * 可以使用refresh_token进行刷新，refresh_token拥有较长的有效期（7天、30天、60天、90天）,
	 * 当refresh_token失效的后，需要用户重新授权。
	 * </pre>
	 */
	public final static String API_oauth2_access_refreshToken = String.format("https://api.weixin.qq.com/sns/oauth2/refresh_token?appid=%1$s&grant_type=refresh_token&refresh_token=%2$s",
			appId, "{{REFRESH_TOKEN}}");
	/**
	 * 拉取用户信息：
	 * 当应用授权作用域是snsapi_userinfo 时可用
	 */
	public final static String API_oauth2_access_userinfo = String.format("https://api.weixin.qq.com/sns/userinfo?access_token=%1$s&openid=%2$s&lang=%3$s",
			appId, "{{OPENID}}", Locale.getDefault());
	// 非网页 获取access_token
	public final static String API_Access_token = String.format("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=%1$s&secret=%2$s", WeiXinMsgCryptUtils.getAppId(),  WeiXinMsgCryptUtils.getAppSecret());
	// 非网页 获取用户信息
	public final static String API_userInfo = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=%1$s&openid=%2$s&lang=" + Locale.getDefault();
	// 非网页 发送客服消息
	public final static String API_send = "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=%1$s";
	// 非网页 生成菜单
	public final static String API_menu_create = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=%1$s";
	// 非网页 删除菜单
	public final static String API_menu_delete = "https://api.weixin.qq.com/cgi-bin/menu/delete?access_token=%1$s";
	
	
	
	private Map<String,Object> decryptXMLMsg = Collections.emptyMap();

	private  WeiXinMsgCryptUtils() {
		
		if (encodingAesKey.length() != 43) {
			try {
				throw new AesException(AesException.IllegalAesKey);
			} catch (AesException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * @see build(String signature, String timestamp, String nonce, String msg_signature, InputStream is)
	 * @author LUYANFENG @ 2015年8月7日
	 */
//	public static WeiXinMsgCryptUtils getCurrentSession(){ return wxbt;	}

	public static WeiXinMsgCryptUtils build(String signature, String timestamp, String nonce, String msg_signature, InputStream is) throws IOException, AesException {
		WeiXinMsgCryptUtils wxbt = new WeiXinMsgCryptUtils();
		if(StringUtils.isNotBlank(msg_signature)){
			wxbt.decryptXMLMsg(msg_signature, timestamp, nonce, is);
		}
		return wxbt;
	}

	// 生成4个字节的网络字节序
	byte[] getNetworkBytesOrder(int sourceNumber) {
		byte[] orderBytes = new byte[4];
		orderBytes[3] = (byte) (sourceNumber & 0xFF);
		orderBytes[2] = (byte) (sourceNumber >> 8 & 0xFF);
		orderBytes[1] = (byte) (sourceNumber >> 16 & 0xFF);
		orderBytes[0] = (byte) (sourceNumber >> 24 & 0xFF);
		return orderBytes;
	}

	// 还原4个字节的网络字节序
	int recoverNetworkBytesOrder(byte[] orderBytes) {
		int sourceNumber = 0;
		for (int i = 0; i < 4; i++) {
			sourceNumber <<= 8;
			sourceNumber |= orderBytes[i] & 0xff;
		}
		return sourceNumber;
	}

	// 随机生成16位字符串
	public String getRandomStr() {
		String base = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
		Random random = new Random();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < 16; i++) {
			int number = random.nextInt(base.length());
			sb.append(base.charAt(number));
		}
		return sb.toString();
	}

	/**
	 * 对明文进行加密.
	 * 
	 * @param text 需要加密的明文
	 * @return 加密后base64编码的字符串
	 * @throws AesException aes加密失败
	 */
	public String encrypt(String randomStr, String text) throws AesException {
		ByteGroup byteCollector = new ByteGroup();
		byte[] randomStrBytes = randomStr.getBytes(CHARSET);
		byte[] textBytes = text.getBytes(CHARSET);
		byte[] networkBytesOrder = getNetworkBytesOrder(textBytes.length);
		byte[] appidBytes = appId.getBytes(CHARSET);

		// randomStr + networkBytesOrder + text + appid
		byteCollector.addBytes(randomStrBytes);
		byteCollector.addBytes(networkBytesOrder);
		byteCollector.addBytes(textBytes);
		byteCollector.addBytes(appidBytes);

		// ... + pad: 使用自定义的填充方式对明文进行补位填充
		byte[] padBytes = PKCS7Encoder.encode(byteCollector.size());
		byteCollector.addBytes(padBytes);

		// 获得最终的字节流, 未加密
		byte[] unencrypted = byteCollector.toBytes();

		try {
			// 设置加密模式为AES的CBC模式
			Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
			SecretKeySpec keySpec = new SecretKeySpec(aesKey, "AES");
			IvParameterSpec iv = new IvParameterSpec(aesKey, 0, 16);
			cipher.init(Cipher.ENCRYPT_MODE, keySpec, iv);
			// 加密
			byte[] encrypted = cipher.doFinal(unencrypted);
			// 使用BASE64对加密后的字符串进行编码
			String base64Encrypted = base64.encodeToString(encrypted);
			return base64Encrypted;
		} catch (Exception e) {
			e.printStackTrace();
			throw new AesException(AesException.EncryptAESError);
		}
	}

	/**
	 * 对密文进行解密.
	 * 
	 * @param text 需要解密的密文
	 * @return 解密得到的明文
	 * @throws AesException aes解密失败
	 */
	public String decrypt(String text) throws AesException {
		byte[] original;
		try {
			// 设置解密模式为AES的CBC模式
			Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
			SecretKeySpec key_spec = new SecretKeySpec(aesKey, "AES");
			IvParameterSpec iv = new IvParameterSpec(Arrays.copyOfRange(aesKey, 0, 16));
			cipher.init(Cipher.DECRYPT_MODE, key_spec, iv);
			// 使用BASE64对密文进行解码
			byte[] encrypted = Base64.decodeBase64(text);
			// 解密
			original = cipher.doFinal(encrypted);
		} catch (Exception e) {
			e.printStackTrace();
			throw new AesException(AesException.DecryptAESError);
		}

		String xmlContent, from_appid;
		try {
			// 去除补位字符
			byte[] bytes = PKCS7Encoder.decode(original);
			// 分离16位随机字符串,网络字节序和AppId
			byte[] networkOrder = Arrays.copyOfRange(bytes, 16, 20);
			int xmlLength = recoverNetworkBytesOrder(networkOrder);
			xmlContent = new String(Arrays.copyOfRange(bytes, 20, 20 + xmlLength), CHARSET);
			from_appid = new String(Arrays.copyOfRange(bytes, 20 + xmlLength, bytes.length),CHARSET);
		} catch (Exception e) {
			e.printStackTrace();
			throw new AesException(AesException.IllegalBuffer);
		}
		// appid不相同的情况
		if (!from_appid.equals(appId)) {
			throw new AesException(AesException.ValidateAppidError);
		}
		return xmlContent;

	}

	/**
	 * 将公众平台回复用户的消息加密打包.
	 * <ol>
	 * 	<li>对要发送的消息进行AES-CBC加密</li>
	 * 	<li>生成安全签名</li>
	 * 	<li>将消息密文和安全签名打包成xml格式</li>
	 * </ol>
	 * 
	 * @param replyMsg 公众平台待回复用户的消息，xml格式的字符串
	 * @param timeStamp 时间戳，可以自己生成，也可以用URL参数的timestamp
	 * @param nonce 随机串，可以自己生成，也可以用URL参数的nonce
	 * 
	 * @return 加密后的可以直接回复用户的密文，包括msg_signature, timestamp, nonce, encrypt的xml格式的字符串
	 * @throws AesException 执行失败，请查看该异常的错误码和具体的错误信息
	 */
	public String encryptMsg(String replyMsg, String timeStamp, String nonce) throws AesException {
		// 加密
		String encrypt = encrypt(getRandomStr(), replyMsg);

		// 生成安全签名
		if (StringUtils.isBlank(timeStamp)) {
			timeStamp = Long.toString(System.currentTimeMillis());
		}
		if(StringUtils.isBlank(nonce)){
			nonce = UUID.randomUUID().toString().replaceAll("-", "").toUpperCase();
		}

		String signature = SHA1.getSHA1(token, timeStamp, nonce, encrypt);

		// System.out.println("发送给平台的签名是: " + signature[1].toString());
		// 生成发送的xml
		String result = XMLParse.generate(encrypt, signature, timeStamp, nonce);
		return result;
	}

	/**
	 * 检验消息的真实性，并且获取解密后的明文.
	 * <ol>
	 * 	<li>利用收到的密文生成安全签名，进行签名验证</li>
	 * 	<li>若验证通过，则提取xml中的加密消息</li>
	 * 	<li>对消息进行解密</li>
	 * </ol>
	 * 
	 * @param msgSignature 签名串，对应URL参数的msg_signature
	 * @param timeStamp 时间戳，对应URL参数的timestamp
	 * @param nonce 随机串，对应URL参数的nonce
	 * @param postData 密文，对应POST请求的数据
	 * 
	 * @return 解密后的原文
	 * @throws AesException 执行失败，请查看该异常的错误码和具体的错误信息
	 */
	public String decryptMsg(String msgSignature, String timeStamp, String nonce, String postData)
			throws AesException {

		// 密钥，公众账号的app secret
		// 提取密文
		Object[] encrypt = XMLParse.extract(postData);

		// 验证安全签名
		String signature = SHA1.getSHA1(token, timeStamp, nonce, encrypt[1].toString());

		// 和URL中的签名比较是否相等
		// System.out.println("第三方收到URL中的签名：" + msg_sign);
		// System.out.println("第三方校验签名：" + signature);
		if (!signature.equals(msgSignature)) {
			throw new AesException(AesException.ValidateSignatureError);
		}

		// 解密
		String result = decrypt(encrypt[1].toString());
		return result;
	}

	/**
	 * 验证URL
	 * @param msgSignature 签名串，对应URL参数的msg_signature
	 * @param timeStamp 时间戳，对应URL参数的timestamp
	 * @param nonce 随机串，对应URL参数的nonce
	 * @param echoStr 随机串，对应URL参数的echostr
	 * 
	 * @return 解密之后的echostr
	 * @throws AesException 执行失败，请查看该异常的错误码和具体的错误信息
	 */
	public String verifyUrl(String msgSignature, String timeStamp, String nonce, String echoStr)
			throws AesException {
		String signature = SHA1.getSHA1(token, timeStamp, nonce, echoStr);
//		log.debug("加密后 signature   ："+ signature);
//		log.debug("传来的 msgSignature："+msgSignature);
		if (!signature.equals(msgSignature)) {
			throw new AesException(AesException.ValidateSignatureError);
		}

		String result = decrypt(echoStr);
		return result;
	}
	/**
	 * 认证
	 * @param signature
	 * @param timeStamp
	 * @param nonce
	 * @throws AesException
	 * @author : LUYANFENG @ 2015年6月4日
	 */
	public boolean verifyUrl(String signature, String timeStamp, String nonce)
			throws AesException {
		String signature_ = SHA1.getMySHA1(token, timeStamp, nonce);
		return signature_.equals(signature) ;
	}
	
	public Map<String,Object> xmlstr2map(String xml ){
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			StringReader sr = new StringReader(xml );
			InputSource inputSource = new InputSource(sr);
			Document document = db.parse(inputSource );

			Element root = document.getDocumentElement();
			NodeList childNodes = root.getChildNodes();
			int length = childNodes.getLength();
			for(int i = 0 ; i < length ; i++){
				Node item = childNodes.item(i);
				Node firstChild = item.getFirstChild();
				if(firstChild != null){
					String nodeName = item.getNodeName();
					String nodeValue = firstChild.getNodeValue();
					map.put(nodeName, nodeValue);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
	
	
	/**
	 * 解析用户发来的信息
	 * @author : LUYANFENG @ 2015年6月16日
	 */
	public Map<String, Object> decryptXMLMsg(String msg_signature, String timestamp, String nonce, InputStream is)
			throws IOException, AesException {
		if(!this.decryptXMLMsg.isEmpty()){
			return this.decryptXMLMsg;
		}
		Log.debug("--- 解析xml.");
//		WeiXinMsgCryptUtils build = WeiXinMsgCryptUtils.getCurrentSession();
		InputStreamReader in = new InputStreamReader(is) ;
		StringWriter out = new StringWriter();
		int byteCount = 0;
		char[] buffer = new char[4096];
		int bytesRead = -1;
		while ((bytesRead = in.read(buffer)) != -1) {
			out.write(buffer, 0, bytesRead);
			byteCount += bytesRead;
		}
		out.flush();
		String decryptMsg = this.decryptMsg(msg_signature, timestamp, nonce, out.toString() );
		this.decryptXMLMsg = this.xmlstr2map(decryptMsg);
		return this.decryptXMLMsg;
	}
	
	/**
	 * @return 获取被处理成map的 传来的xml信息。
	 */
	public Map<String,Object> decryptXMLMsg(){
		return Collections.unmodifiableMap(this.decryptXMLMsg);
	}
	
	public enum MsgType{
		text
		,image
		,voice 
		,video
		,shortvideo
		,location //地理位置消
		,link //链接消息
		,event 
		;
	}

	/**
	 * @return
	 * @author : LUYANFENG @ 2015年7月7日
	 */
	public static String getAppId() {
		return appId;
	}

	/**
	 * @return
	 * @author : LUYANFENG @ 2015年7月7日
	 */
	public static String getAppSecret() {
		return appSecret;
	}

}