package utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.webkit.MimeTypeMap;

import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.protocol.HTTP;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.security.cert.X509Certificate;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

/**
 * 网络相关工具类.
 *
 *
 */
public final class NetworkUtil {
	/**
	 * http协议.
	 */
	public static final String SCHEMA_HTTP = "http://";
	private static final String NETWORK_TYPE_CMWAP = "cmwap";
	private static final String NETWORK_TYPE_UNIWAP = "uniwap";
	private static final String NETWORK_TYPE_3GWAP = "3gwap";
	private static final String NETWORK_TYPE_CTWAP = "ctwap";
	private static final String PROXY_CMWAP = "10.0.0.172";
	private static final String PROXY_CTWAP = "10.0.0.200";

	private NetworkUtil() {
	}

    public static boolean isUrlValid(String url) {
        // 防止UXSS漏洞
        Pattern patt =
                Pattern.compile("(http://|ftp://|https://|www){0,1}[^\u4e00-\u9fa5\\s]*?\\.(com|net|cn|me|tw|fr)[^\u4e00-\u9fa5\\s]*");
        Matcher matcher = patt.matcher(url);
        boolean isMatch = matcher.matches();

        if (isMatch == false || url.startsWith("javascript:")) {
            return false;
        }

        return true;
    }

    /**
     * 获取UserAgent
     * @param context if null, use the default format
     *                (Mozilla/5.0 (Linux; U; Android %s) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 %sSafari/534.30).
     * @return
     */
    public static String getUserAgent(Context context) {
        String webUserAgent = null;
        if (context != null) {
            try {
                Class sysResCls = Class.forName("com.android.internal.R$string");
                Field webUserAgentField = sysResCls.getDeclaredField("web_user_agent");
                Integer resId = (Integer) webUserAgentField.get(null);
                webUserAgent = context.getString(resId);
            } catch (Throwable ignored) {
            }
        }
        if (TextUtils.isEmpty(webUserAgent)) {
            webUserAgent = "Mozilla/5.0 (Linux; U; Android %s) AppleWebKit/533.1 (KHTML, like Gecko) Version/4.0 %sSafari/533.1";
        }

        Locale locale = Locale.getDefault();
        StringBuffer buffer = new StringBuffer();
        // Add version
        final String version = Build.VERSION.RELEASE;
        if (version.length() > 0) {
            buffer.append(version);
        } else {
            // default to "1.0"
            buffer.append("1.0");
        }
        buffer.append("; ");
        final String language = locale.getLanguage();
        if (language != null) {
            buffer.append(language.toLowerCase());
            final String country = locale.getCountry();
            if (country != null) {
                buffer.append("-");
                buffer.append(country.toLowerCase());
            }
        } else {
            // default to "en"
            buffer.append("en");
        }
        // add the model for the release build
        if ("REL".equals(Build.VERSION.CODENAME)) {
            final String model = Build.MODEL;
            if (model.length() > 0) {
                buffer.append("; ");
                buffer.append(model);
            }
        }
        final String id = Build.ID;
        if (id.length() > 0) {
            buffer.append(" Build/");
            buffer.append(id);
        }
        return String.format(webUserAgent, buffer, "Mobile ");
    }

    private static SSLSocketFactory sslSocketFactory;

    /**
     * 信任所有SSL连接
     */
    public static void trustAllHttpsURLConnection() {
        // Create a trust manager that does not validate certificate chains
        if (sslSocketFactory == null) {
            TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
                @Override
                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }

                @Override
                public void checkClientTrusted(X509Certificate[] certs,
                                               String authType) {
                }

                @Override
                public void checkServerTrusted(X509Certificate[] certs,
                                               String authType) {
                }
            }};
            try {
                SSLContext sslContext = SSLContext.getInstance("TLS");
                sslContext.init(null, trustAllCerts, null);
                sslSocketFactory = sslContext.getSocketFactory();
            } catch (Throwable e) {
                LogUtil.e(e.getMessage(), e);
            }
        }

        if (sslSocketFactory != null) {
            HttpsURLConnection.setDefaultSSLSocketFactory(sslSocketFactory);
            HttpsURLConnection.setDefaultHostnameVerifier(org.apache.http.conn.ssl.SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
        }
    }

	/**
	 * 获取代理host地址.目前仅支持{@code #NETWORK_TYPE_CMWAP},{@code #NETWORK_TYPE_UNIWAP},
	 * {@code #NETWORK_TYPE_3GWAP},{@code #NETWORK_TYPE_CTWAP}.四种.其余不进行代理.
	 *
	 * @param context 上下文
	 * @return 代理地址.
	 */
	public static String getProxy(Context context) {
		ConnectivityManager connectivityManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		final NetworkInfo networkInfo = connectivityManager
				.getActiveNetworkInfo();
		if (networkInfo == null || networkInfo.getExtraInfo() == null) {
			return null;
		}

		String info = networkInfo.getExtraInfo().toLowerCase(
				Locale.getDefault());
		if (info != null) {
			if (info.startsWith(NETWORK_TYPE_CMWAP)
					|| info.startsWith(NETWORK_TYPE_UNIWAP)
					|| info.startsWith(NETWORK_TYPE_3GWAP)) {
				return PROXY_CMWAP;
			} else if (info.startsWith(NETWORK_TYPE_CTWAP)) {
				return PROXY_CTWAP;
			}
		}
		return null;
	}

	/**
	 * 获取url的host.
	 *
	 * @param url url地址.
	 * @return host地址
	 */
	public static String getUrlHost(String url) {
		if (TextUtils.isEmpty(url)) {
			return null;
		}
		String host = null;
		int hostIndex = SCHEMA_HTTP.length();
		int pathIndex = url.indexOf('/', hostIndex);
		if (pathIndex < 0) {
			host = url.substring(hostIndex);
		} else {
			host = url.substring(hostIndex, pathIndex);
		}
		return host;
	}

	/**
	 * 检测网络是否可用.wifi和手機有一種可用即爲可用.
	 *
	 * @param context 上下文对象.此处请使用application的context.
	 * @return true可用.false不可用.
	 */
	public static boolean isNetworkAvailable(Context context) {
		ConnectivityManager connManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo info = connManager.getActiveNetworkInfo();
		return isNetworkActive(info);
	}

	/**
	 * 移动数据网络是否可用.
	 *
	 * @param context context.此处请使用application的context.
	 * @return 是否可用.true可用.false不可用.
	 */
	public static boolean isMobileNetAvailable(Context context) {
		ConnectivityManager connManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo info = connManager
				.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
		return isNetworkActive(info);
	}

	/**
	 * WIFI连接是否可用.
	 *
	 * @param context 上下文對象.此处请使用application的context.
	 * @return wifi連接是否可用.true可用.false不可用.
	 */
	public static boolean isWifiAvailable(Context context) {
		ConnectivityManager connManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo info = connManager
				.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		return isNetworkActive(info);
	}

	/**
	 * 指定类型的连接是否可用.
	 *
	 * @param info networkinfo.
	 * @return 是否可用.true可用,false不可用.
	 */
	private static boolean isNetworkActive(NetworkInfo info) {
		if ((info != null) && info.isConnected() && info.isAvailable()) {
			return true;
		}
		return false;
	}

	/**
	 * url encode.
	 *
	 * @param url url
	 * @return encode后的值.
	 */
	public static String urlEncode(String url) {
		try {
			return URLEncoder.encode(url, "utf-8");
		} catch (UnsupportedEncodingException e) {
			LogUtil.e(e.getMessage(), e);
		} catch (Exception e) {
			LogUtil.e(e.getMessage(), e);
		}
		return "";
	}

	/**
	 * url encode.
	 *
	 * @param url url
	 * @param charset 编码.
	 * @return encode后的值.
	 */
	public static String urlEncode(String url, String charset) {
		try {
			return URLEncoder.encode(url, charset);
		} catch (UnsupportedEncodingException e) {
			LogUtil.e(e.getMessage(), e);
		}
		return "";
	}

	/**
	 * url decode.
	 *
	 * @param url url
	 * @return decode后的值.
	 */
	public static String urlDecode(String url) {
		try {
			return URLDecoder.decode(url, "utf-8");
		} catch (UnsupportedEncodingException e) {
			LogUtil.e(e.getMessage(), e);
		} catch (Exception e) {
			LogUtil.e(e.getMessage(), e);
		}
		return "";
	}

	/**
	 * url decode.
	 *
	 * @param url url
	 * @param charset 编码.
	 * @return decode后的值.
	 */
	public static String urlDecode(String url, String charset) {
		try {
			return URLDecoder.decode(url, charset);
		} catch (UnsupportedEncodingException e) {
			LogUtil.e(e.getMessage(), e);
		}
		return "";
	}

	/**
	 * 获取网络类型，GSM等
	 *
	 * @param context 上下文
	 * @return
	 */
	public static String getNetworkTypeStr(Context context) {
		String type = "";

		ConnectivityManager connectivityManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
		TelephonyManager tm = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);

		int networkType = tm.getNetworkType();

		if (activeNetInfo == null) {
			type = "NO_NETWORK";
		} else if (activeNetInfo.getType() == ConnectivityManager.TYPE_WIFI) {
			type = "WIFI";
		} else {
			switch (networkType) {
			case TelephonyManager.NETWORK_TYPE_1xRTT:
				type = "1xRTT";
				break;
			case TelephonyManager.NETWORK_TYPE_CDMA:
				type = "CDMA";
				break;
			case TelephonyManager.NETWORK_TYPE_EDGE:
				type = "EDGE";
				break;
			case TelephonyManager.NETWORK_TYPE_EVDO_0:
				type = "EVDO_0";
				break;
			case TelephonyManager.NETWORK_TYPE_EVDO_A:
				type = "EVDO_A";
				break;
			case TelephonyManager.NETWORK_TYPE_GPRS:
				type = "GPRS";
				break;
			case TelephonyManager.NETWORK_TYPE_HSDPA:
				type = "HSDPA";
				break;
			case TelephonyManager.NETWORK_TYPE_HSPA:
				type = "HSPA";
				break;
			case TelephonyManager.NETWORK_TYPE_HSUPA:
				type = "HSUPA";
				break;
			case TelephonyManager.NETWORK_TYPE_UMTS:
				type = "UMTS";
				break;
			default:
				type = "UNKNOWN";
				break;
			}
		}

		return type;
	}

	/**
	 * 判断连接是否支持断点续传
	 * @param response
	 * @return
	 */
    public static boolean isSupportRange(final HttpResponse response) {
        if (response == null) return false;
        Header header = response.getFirstHeader("Accept-Ranges");
        if (header != null) {
            return "bytes".equals(header.getValue());
        }
        header = response.getFirstHeader("Content-Range");
        if (header != null) {
            String value = header.getValue();
            return value != null && value.startsWith("bytes");
        }
        return false;
    }

    /**
     * 获取Server重命名文件名
     * @param response
     * @return
     */
    public static String getFileNameFromHttpResponse(final HttpResponse response) {
        if (response == null) return null;
        String result = null;
        Header header = response.getFirstHeader("Content-Disposition");
        if (header != null) {
            for (HeaderElement element : header.getElements()) {
                NameValuePair fileNamePair = element.getParameterByName("filename");
                if (fileNamePair != null) {
                    result = fileNamePair.getValue();
                    // try to get correct encoding str
                    result = CharsetUtils.toCharset(result, HTTP.UTF_8, result.length());
                    break;
                }
            }
        }
        return result;
    }

    /**
     * 获取请求编码规范
     * @param request
     * @return
     */
    public static Charset getCharsetFromHttpRequest(final HttpRequestBase request) {
        if (request == null) return null;
        String charsetName = null;
        Header header = request.getFirstHeader("Content-Type");
        if (header != null) {
            for (HeaderElement element : header.getElements()) {
                NameValuePair charsetPair = element.getParameterByName("charset");
                if (charsetPair != null) {
                    charsetName = charsetPair.getValue();
                    break;
                }
            }
        }

        boolean isSupportedCharset = false;
        if (!TextUtils.isEmpty(charsetName)) {
            try {
                isSupportedCharset = Charset.isSupported(charsetName);
            } catch (Throwable e) {
            }
        }

        return isSupportedCharset ? Charset.forName(charsetName) : null;
    }

    /**
     * 获取当前请求的MimeType
     * @param fileName
     * @return
     */
    public static String getMimeType(final String fileName) {
        String result = "application/octet-stream";
        int extPos = fileName.lastIndexOf(".");
        if (extPos != -1) {
            String ext = fileName.substring(extPos + 1);
            result = MimeTypeMap.getSingleton().getMimeTypeFromExtension(ext);
        }
        return result;
    }
}
