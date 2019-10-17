package com.htmitech.commonx.base;

import android.text.TextUtils;

import com.htmitech.commonx.base.exception.HttpException;
import com.htmitech.commonx.base.http.HttpCache;
import com.htmitech.commonx.base.http.HttpHandler;
import com.htmitech.commonx.base.http.RequestParams;
import com.htmitech.commonx.base.http.ResponseStream;
import com.htmitech.commonx.base.http.SyncHttpHandler;
import com.htmitech.commonx.base.http.callback.HttpRedirectHandler;
import com.htmitech.commonx.base.http.callback.RequestCallBack;
import com.htmitech.commonx.base.http.client.DefaultSSLSocketFactory;
import com.htmitech.commonx.base.http.client.HttpRequest;
import com.htmitech.commonx.base.http.client.RetryHandler;
import com.htmitech.commonx.base.http.client.entity.GZipDecompressingEntity;
import com.htmitech.commonx.base.task.PriorityExecutor;
import com.htmitech.commonx.util.NetworkUtil;
import com.htmitech.myEnum.LogManagerEnum;


import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.HttpEntity;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.HttpResponse;
import org.apache.http.HttpResponseInterceptor;
import org.apache.http.HttpVersion;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.conn.params.ConnPerRouteBean;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;

import java.io.File;
import java.io.IOException;

import htmitech.com.componentlibrary.unit.PreferenceUtils;

/**
 * 网络接口，包括网络请求，文件下载
 */
public class HttpUtils {

    public final static HttpCache sHttpCache = new HttpCache();

    private final DefaultHttpClient httpClient;
    private final HttpContext httpContext = new BasicHttpContext();

    private HttpRedirectHandler httpRedirectHandler;

    /**
     * 构造函数
     */
    public HttpUtils() {
        this(HttpUtils.DEFAULT_CONN_TIMEOUT, null, "");
    }

    public HttpUtils(String logfunctionCode, int index) {
        this(HttpUtils.DEFAULT_CONN_TIMEOUT, null, logfunctionCode);
    }

    /**
     * 构造函数
     *
     * @param connTimeout 超时时间，默认15秒
     */
    public HttpUtils(int connTimeout) {
        this(connTimeout, null, "");
    }

    /**
     * 构造函数
     *
     * @param userAgent 用户代理，默认空
     */
    public HttpUtils(String userAgent) {
        this(HttpUtils.DEFAULT_CONN_TIMEOUT, userAgent, "");
    }

    /**
     * 构造函数
     *
     * @param connTimeout 超时时间，默认15秒
     * @param userAgent   用户代理，默认空
     */
    public HttpUtils(int connTimeout, String userAgent, String logfunctionCode) {
        HttpParams params = new BasicHttpParams();
        this.logfunctionCode = logfunctionCode;
        ConnManagerParams.setTimeout(params, connTimeout);
        HttpConnectionParams.setSoTimeout(params, connTimeout);
        HttpConnectionParams.setConnectionTimeout(params, connTimeout);

        if (TextUtils.isEmpty(userAgent)) {
            userAgent = NetworkUtil.getUserAgent(null);
        }
        HttpProtocolParams.setUserAgent(params, userAgent);

        ConnManagerParams.setMaxConnectionsPerRoute(params,
                new ConnPerRouteBean(10));
        ConnManagerParams.setMaxTotalConnections(params, 10);

        HttpConnectionParams.setTcpNoDelay(params, true);
        HttpConnectionParams.setSocketBufferSize(params, 1024 * 8);
        HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);

        SchemeRegistry schemeRegistry = new SchemeRegistry();
        schemeRegistry.register(new Scheme("http", PlainSocketFactory
                .getSocketFactory(), 80));
        schemeRegistry.register(new Scheme("https", DefaultSSLSocketFactory
                .getSocketFactory(), 443));

        httpClient = new DefaultHttpClient(new ThreadSafeClientConnManager(
                params, schemeRegistry), params);

        httpClient.setHttpRequestRetryHandler(new RetryHandler(
                DEFAULT_RETRY_TIMES));


        httpClient.addRequestInterceptor(new HttpRequestInterceptor() {
            @Override
            public void process(org.apache.http.HttpRequest httpRequest,
                                HttpContext httpContext)
                    throws org.apache.http.HttpException, IOException {
                if (!httpRequest.containsHeader(HEADER_ACCEPT_ENCODING)) {
                    httpRequest
                            .addHeader(HEADER_ACCEPT_ENCODING, ENCODING_GZIP);
                }
                if (!httpRequest.containsHeader(HEADER_ACCEPT_FORMAT)) {
                    httpRequest.addHeader(HEADER_ACCEPT_FORMAT, FORMAT_PARAM);
                }

                if (!httpRequest.containsHeader(LOGFUNCTIONID)) {
                    if (HttpUtils.this.logfunctionCode != null && !HttpUtils.this.logfunctionCode.equals(""))
                        httpRequest.addHeader(LOGFUNCTIONID, LogManagerEnum.getLogManagerEnum(HttpUtils.this.logfunctionCode).getFunctionId());
                }

                String refreshToken = PreferenceUtils.getRefreshToken();
                String accessToken = PreferenceUtils.getAccessToken();
                httpRequest.addHeader("accessToken", accessToken);
                httpRequest.addHeader("refreshToken", refreshToken);
            }
        });

        httpClient.addResponseInterceptor(new HttpResponseInterceptor() {
            @Override
            public void process(HttpResponse response, HttpContext httpContext)
                    throws org.apache.http.HttpException, IOException {
                final HttpEntity entity = response.getEntity();
                if (entity == null) {
                    return;
                }
                final Header encoding = entity.getContentEncoding();
                if (encoding != null) {
                    for (HeaderElement element : encoding.getElements()) {
                        if (element.getName().equalsIgnoreCase("gzip")) {
                            response.setEntity(new GZipDecompressingEntity(response.getEntity()));
                            return;
                        }
                    }
                }
                String refreshToken = PreferenceUtils.getRefreshToken();
                String accessToken = PreferenceUtils.getAccessToken();
                response.addHeader("accessToken", accessToken);
                response.addHeader("refreshToken", refreshToken);
            }
        });
    }

    // ************************************ default settings & fields
    // ****************************

    private String responseTextCharset = HTTP.UTF_8;

    private long currentRequestExpiry = HttpCache.getDefaultExpiryTime();

    private final static int DEFAULT_CONN_TIMEOUT = 1000 * 15; // 15s

    private final static int DEFAULT_RETRY_TIMES = 3;

    private static final String HEADER_ACCEPT_ENCODING = "Accept-Encoding";
    private static final String HEADER_ACCEPT_FORMAT = "content-type";
    private static final String LOGFUNCTIONID = "functionLogId";
    private static final String ENCODING_GZIP = "gzip";
    private static final String FORMAT_PARAM = "application/json";

    public String logfunctionCode = "";
    private final static int DEFAULT_POOL_SIZE = 3;
    private final static PriorityExecutor EXECUTOR = new PriorityExecutor(
            DEFAULT_POOL_SIZE);

    public HttpClient getHttpClient() {
        return this.httpClient;
    }

    // ***************************************** config
    // *******************************************

    /**
     * 配置数据接收字符集，默认为UTF-8
     *
     * @param charSet 字符集
     * @return 返回已设置完成的HttpUtils对象
     */
    public HttpUtils configResponseTextCharset(String charSet) {
        if (!TextUtils.isEmpty(charSet)) {
            this.responseTextCharset = charSet;
        }
        return this;
    }

    /**
     * 配置请求头，默认配置Location和cookie
     *
     * @param httpRedirectHandler HttpRedirectHandler对象
     * @return 返回已设置完成的HttpUtils对象
     */
    public HttpUtils configHttpRedirectHandler(
            HttpRedirectHandler httpRedirectHandler) {
        this.httpRedirectHandler = httpRedirectHandler;
        return this;
    }

    /**
     * 配置请求缓存大小
     *
     * @param httpCacheSize 缓存大小，默认DEFAULT_CACHE_SIZE：1024 * 100
     * @return 返回已设置完成的HttpUtils对象
     */
    public HttpUtils configHttpCacheSize(int httpCacheSize) {
        sHttpCache.setCacheSize(httpCacheSize);
        return this;
    }

    /**
     * 配置缓存失效日期
     *
     * @param defaultExpiry 失效日期，默认60秒
     * @return 返回已设置完成的HttpUtils对象
     */
    public HttpUtils configDefaultHttpCacheExpiry(long defaultExpiry) {
        HttpCache.setDefaultExpiryTime(defaultExpiry);
        currentRequestExpiry = HttpCache.getDefaultExpiryTime();
        return this;
    }

    /**
     * 配置当前失效日期
     *
     * @param currRequestExpiry 失效日期, 默认60秒
     * @return 返回已设置完成的HttpUtils对象
     */
    public HttpUtils configCurrentHttpCacheExpiry(long currRequestExpiry) {
        this.currentRequestExpiry = currRequestExpiry;
        return this;
    }

    /**
     * 配置CookieStore
     *
     * @param cookieStore cookieStroe
     * @return 返回已设置完成的HttpUtils对象
     */
    public HttpUtils configCookieStore(CookieStore cookieStore) {
        httpContext.setAttribute(ClientContext.COOKIE_STORE, cookieStore);
        return this;
    }

    /**
     * 设置用户代理
     *
     * @param userAgent 用户代理
     * @return 返回已设置完成的HttpUtils对象
     */
    public HttpUtils configUserAgent(String userAgent) {
        HttpProtocolParams.setUserAgent(this.httpClient.getParams(), userAgent);
        return this;
    }

    /**
     * 配置连接超时时间，默认超时时间15秒
     *
     * @param timeout 超时时间
     * @return 返回已设置完成的HttpUtils对象
     */
    public HttpUtils configTimeout(int timeout) {
        final HttpParams httpParams = this.httpClient.getParams();
        ConnManagerParams.setTimeout(httpParams, timeout);
        HttpConnectionParams.setConnectionTimeout(httpParams, timeout);
        return this;
    }

    /**
     * 配置SOTime时间，指当请求在指定超时时间内，多长时间没有得到accept方法返回的套接字实例，则抛出IOException的异常
     *
     * @param timeout 超时时间
     * @return 返回已设置完成的HttpUtils对象
     */
    public HttpUtils configSoTimeout(int timeout) {
        final HttpParams httpParams = this.httpClient.getParams();
        HttpConnectionParams.setSoTimeout(httpParams, timeout);
        return this;
    }

    /**
     * 注册Scheme
     *
     * @param scheme scheme属性
     * @return 返回已设置完成的HttpUtils对象
     */
    public HttpUtils configRegisterScheme(Scheme scheme) {
        this.httpClient.getConnectionManager().getSchemeRegistry()
                .register(scheme);
        return this;
    }

    /**
     * 配置SSL证书对象
     *
     * @param sslSocketFactory SSL对象
     * @return 返回已设置完成的HttpUtils对象
     */
    public HttpUtils configSSLSocketFactory(SSLSocketFactory sslSocketFactory) {
        Scheme scheme = new Scheme("https", sslSocketFactory, 443);
        this.httpClient.getConnectionManager().getSchemeRegistry()
                .register(scheme);
        return this;
    }

    /**
     * 配置请求重试次数，默认3次
     *
     * @param count 重试次数
     * @return 返回已设置完成的HttpUtils对象
     */
    public HttpUtils configRequestRetryCount(int count) {
        this.httpClient.setHttpRequestRetryHandler(new RetryHandler(count));
        return this;
    }

    /**
     * 配置线程池中线程最大数量，默认3个
     *
     * @param threadPoolSize 线程最大数量
     * @return 返回已设置完成的HttpUtils对象
     */
    public HttpUtils configRequestThreadPoolSize(int threadPoolSize) {
        HttpUtils.EXECUTOR.setPoolSize(threadPoolSize);
        return this;
    }

    // ***************************************** send request
    // *******************************************

    /**
     * 异步发送Http请求
     *
     * @param method   请求方式：GET,POST,PUT,HEAD,MOVE,COPY,DELETE,OPTIONS,TRACE,CONNECT
     * @param url      请求地址
     * @param callBack 异步结果回调
     * @return 当前请求Handler句柄
     */
    public <T> HttpHandler<T> send(HttpRequest.HttpMethod method, String url,
                                   RequestCallBack<T> callBack) {
        return send(method, url, null, callBack);
    }

    /**
     * 异步发送Http请求
     *
     * @param method   请求方式：GET,POST,PUT,HEAD,MOVE,COPY,DELETE,OPTIONS,TRACE,CONNECT
     * @param url      请求地址
     * @param params   Http参数
     * @param callBack 请求结果回调
     * @return 当前请求Handler句柄
     */
    public <T> HttpHandler<T> send(HttpRequest.HttpMethod method, String url,
                                   RequestParams params, RequestCallBack<T> callBack) {
        if (url == null)
            throw new IllegalArgumentException("url may not be null");

        HttpRequest request = new HttpRequest(method, url);
        return sendRequest(request, params, callBack);
    }

    /**
     * 同步发送Http请求
     *
     * @param method 请求方式：GET,POST,PUT,HEAD,MOVE,COPY,DELETE,OPTIONS,TRACE,CONNECT
     * @param url    请求地址
     * @return 返回ResponseStream流
     * @throws HttpException
     */
    public ResponseStream sendSync(HttpRequest.HttpMethod method, String url)
            throws HttpException {
        return sendSync(method, url, null);
    }

    /**
     * 同步发送Http请求
     *
     * @param method 请求方式：GET,POST,PUT,HEAD,MOVE,COPY,DELETE,OPTIONS,TRACE,CONNECT
     * @param url    请求地址
     * @param params 请求参数
     * @return 返回ResponseStream流
     * @throws HttpException
     */
    public ResponseStream sendSync(HttpRequest.HttpMethod method, String url,
                                   RequestParams params) throws HttpException {
        if (url == null)
            throw new IllegalArgumentException("url may not be null");

        HttpRequest request = new HttpRequest(method, url);
        return sendSyncRequest(request, params);
    }

    // ***************************************** download
    // *******************************************

    /**
     * 下载文件
     *
     * @param url      下载地址
     * @param target   本地目标文件存放地址
     * @param callback 异步结果回调
     * @return 当前请求Handler句柄
     */
    public HttpHandler<File> download(String url, String target,
                                      RequestCallBack<File> callback) {
        return download(HttpRequest.HttpMethod.GET, url, target, null, false,
                false, callback);
    }

    /**
     * 下载文件
     *
     * @param url        下载地址
     * @param target     本地目标文件存放地址
     * @param autoResume 当服务器支持Range时，是否自动断点续传
     * @param callback   异步结果回调
     * @return 当前请求Handler句柄
     */
    public HttpHandler<File> download(String url, String target,
                                      boolean autoResume, RequestCallBack<File> callback) {
        return download(HttpRequest.HttpMethod.GET, url, target, null,
                autoResume, false, callback);
    }

    /**
     * 下载文件
     *
     * @param url        下载地址
     * @param target     本地目标文件存放地址
     * @param autoResume 当服务器支持Range时，是否自动断点续传
     * @param autoRename 当服务器存在Content-Disposition-->filename属性时，是否自动更名
     * @param callback   异步结果回调
     * @return 当前请求Handler句柄
     */
    public HttpHandler<File> download(String url, String target,
                                      boolean autoResume, boolean autoRename,
                                      RequestCallBack<File> callback) {
        return download(HttpRequest.HttpMethod.GET, url, target, null,
                autoResume, autoRename, callback);
    }

    /**
     * 下载文件
     *
     * @param url      下载地址
     * @param target   本地目标文件存放地址
     * @param params   请求参数
     * @param callback 异步结果回调
     * @return 当前请求Handler句柄
     */
    public HttpHandler<File> download(String url, String target,
                                      RequestParams params, RequestCallBack<File> callback) {
        return download(HttpRequest.HttpMethod.GET, url, target, params, false,
                false, callback);
    }

    /**
     * 下载文件
     *
     * @param url        下载地址
     * @param target     本地目标文件存放地址
     * @param params     请求参数
     * @param autoResume 当服务器支持Range时，是否自动断点续传
     * @param callback   异步结果回调
     * @return 当前请求Handler句柄
     */
    public HttpHandler<File> download(String url, String target,
                                      RequestParams params, boolean autoResume,
                                      RequestCallBack<File> callback) {
        return download(HttpRequest.HttpMethod.GET, url, target, params,
                autoResume, false, callback);
    }

    /**
     * 下载文件
     *
     * @param url        下载地址
     * @param target     本地目标文件存放地址
     * @param params     请求参数
     * @param autoResume 当服务器支持Range时，是否自动断点续传
     * @param autoRename 当服务器存在Content-Disposition-->filename属性时，是否自动更名
     * @param callback   异步结果回调
     * @return 当前请求Handler句柄
     */
    public HttpHandler<File> download(String url, String target,
                                      RequestParams params, boolean autoResume, boolean autoRename,
                                      RequestCallBack<File> callback) {
        return download(HttpRequest.HttpMethod.GET, url, target, params,
                autoResume, autoRename, callback);
    }

    /**
     * 下载文件
     *
     * @param method   请求方式：GET,POST,PUT,HEAD,MOVE,COPY,DELETE,OPTIONS,TRACE,CONNECT
     * @param url      下载地址
     * @param target   本地目标文件存放地址
     * @param params   请求参数
     * @param callback 异步结果回调
     * @return 当前请求Handler句柄
     */
    public HttpHandler<File> download(HttpRequest.HttpMethod method,
                                      String url, String target, RequestParams params,
                                      RequestCallBack<File> callback) {
        return download(method, url, target, params, false, false, callback);
    }

    /**
     * 下载文件
     *
     * @param method     请求方式：GET,POST,PUT,HEAD,MOVE,COPY,DELETE,OPTIONS,TRACE,CONNECT
     * @param url        下载地址
     * @param target     本地目标文件存放地址
     * @param params     请求参数
     * @param autoResume 当服务器支持Range时，是否自动断点续传
     * @param callback   异步结果回调
     * @return 当前请求Handler句柄
     */
    public HttpHandler<File> download(HttpRequest.HttpMethod method,
                                      String url, String target, RequestParams params,
                                      boolean autoResume, RequestCallBack<File> callback) {
        return download(method, url, target, params, autoResume, false,
                callback);
    }

    /**
     * 下载文件
     *
     * @param method     请求方式：GET,POST,PUT,HEAD,MOVE,COPY,DELETE,OPTIONS,TRACE,CONNECT
     * @param url        下载地址
     * @param target     本地目标文件存放地址
     * @param params     请求参数
     * @param autoResume 当服务器支持Range时，是否自动断点续传
     * @param autoRename 当服务器存在Content-Disposition-->filename属性时，是否自动更名
     * @param callback   异步结果回调
     * @return 当前请求Handler句柄
     */
    public HttpHandler<File> download(HttpRequest.HttpMethod method,
                                      String url, String target, RequestParams params,
                                      boolean autoResume, boolean autoRename,
                                      RequestCallBack<File> callback) {

        if (url == null)
            throw new IllegalArgumentException("url may not be null");
        if (target == null)
            throw new IllegalArgumentException("target may not be null");

        HttpRequest request = new HttpRequest(method, url);

        HttpHandler<File> handler = new HttpHandler<File>(httpClient,
                httpContext, responseTextCharset, callback);

        handler.setExpiry(currentRequestExpiry);
        handler.setHttpRedirectHandler(httpRedirectHandler);

        if (params != null) {
            request.setRequestParams(params, handler);
            handler.setPriority(params.getPriority());
        }
        handler.executeOnExecutor(EXECUTOR, request, target, autoResume,
                autoRename);
        return handler;
    }

    // //////////////////////////////////////////////////////////////////////////////////////////////
    public <T> HttpHandler<T> sendRequest(HttpRequest request,
                                          RequestParams params, RequestCallBack<T> callBack) {

        HttpHandler<T> handler = new HttpHandler<T>(httpClient, httpContext,
                responseTextCharset, callBack);

        handler.setExpiry(currentRequestExpiry);
        handler.setHttpRedirectHandler(httpRedirectHandler);
        request.setRequestParams(params, handler);

        if (params != null) {
            handler.setPriority(params.getPriority());
        }
        handler.executeOnExecutor(EXECUTOR, request);
        return handler;
    }

    public ResponseStream sendSyncRequest(HttpRequest request,
                                          RequestParams params) throws HttpException {

        SyncHttpHandler handler = new SyncHttpHandler(httpClient, httpContext,
                responseTextCharset);

        handler.setExpiry(currentRequestExpiry);
        handler.setHttpRedirectHandler(httpRedirectHandler);
        request.setRequestParams(params);

        return handler.sendRequest(request);
    }
}
