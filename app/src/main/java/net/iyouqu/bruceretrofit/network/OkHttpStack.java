package net.iyouqu.bruceretrofit.network;

/**
 * Created by q on 2015/11/23.
 */
//public class OkHttpStack implements HttpStack {
//	private final OkHttpClient mOkHttpClient;
//
//	public OkHttpStack(OkHttpClient okHttpClient) {
//		if (okHttpClient == null) {
//			throw new IllegalArgumentException("OkHttpClient can't be null");
//		}
//		mOkHttpClient = okHttpClient;
//	}
//
//	@Override
//	public HttpResponse performRequest(Request<?> request, Map<String, String> additionalHeaders) throws IOException, AuthFailureError {
//		OkHttpClient client = mOkHttpClient;
//		int timeoutMs = request.getTimeoutMs();
//		client.setConnectTimeout(timeoutMs, TimeUnit.MILLISECONDS);
//		client.setReadTimeout(timeoutMs, TimeUnit.MILLISECONDS);
//		client.setWriteTimeout(timeoutMs, TimeUnit.MILLISECONDS);
//
//		com.squareup.okhttp.Request.Builder builder = new com.squareup.okhttp.Request.Builder();
//		builder.url(request.getUrl());
//
//		Map<String, String> headers = request.getHeaders();
//
//		for (String name : headers.keySet()) {
//			builder.addHeader(name, headers.get(name));
//		}
//
//		for (String name : additionalHeaders.keySet()) {
//			builder.addHeader(name, additionalHeaders.get(name));
//		}
//
//		setConnectionParametersForRequest(builder, request);
//		com.squareup.okhttp.Request okRequest = builder.build();
//
//		Call call = client.newCall(okRequest);
//		Response okresponse = call.execute();
//
//		BasicStatusLine responseStatus = new BasicStatusLine(
//				parseProtocol(okresponse.protocol()),
//				okresponse.code(),
//				okresponse.message()
//		);
//
//		BasicHttpResponse response = new BasicHttpResponse(responseStatus);
//		response.setEntity(entityFromOkHttpResponse(okresponse));
//		Headers responseHeaders = okresponse.headers();
//
//		int size = responseHeaders.size();
//		String name = null;
//		String value = null;
//		for (int i = 0; i < size; i++) {
//			name = responseHeaders.name(i);
//			if (name != null) {
//				response.addHeader(new BasicHeader(name, value));
//			}
//		}
//
//		return response;
//	}
//
//	private static HttpEntity entityFromOkHttpResponse(Response r) throws IOException {
//		BasicHttpEntity entity = new BasicHttpEntity();
//		ResponseBody body = r.body();
//
//		entity.setContent(body.byteStream());
//		entity.setContentLength(body.contentLength());
//		entity.setContentEncoding(r.header("Content-Encoding"));
//
//		if (body.contentType() != null) {
//			entity.setContentType(body.contentType().type());
//		}
//		return entity;
//	}
//
//	static void setConnectionParametersForRequest(com.squareup.okhttp.Request.Builder builder, Request<?> request) throws IOException, AuthFailureError {
//		switch (request.getMethod()) {
//			case Request.Method.DEPRECATED_GET_OR_POST:
//				byte[] postBody = request.getPostBody();
//				if (postBody != null) {
//					builder.post(RequestBody.create(MediaType.parse(request.getPostBodyContentType()), postBody));
//				}
//				break;
//			case Request.Method.GET:
//				builder.get();
//				break;
//			case Request.Method.DELETE:
//				builder.delete();
//				break;
//			case Request.Method.POST:
//				builder.post(createRequestBody(request));
//				break;
//			case Request.Method.PUT:
//				builder.put(createRequestBody(request));
//				break;
//			case Request.Method.HEAD:
//				builder.head();
//				break;
//			case Request.Method.OPTIONS:
//				builder.method("OPTIONS", null);
//				break;
//			case Request.Method.TRACE:
//				builder.method("TRACE", null);
//				break;
//			case Request.Method.PATCH:
//				builder.patch(createRequestBody(request));
//				break;
//			default:
//				throw new IllegalStateException("Unknown method type.");
//		}
//	}
//
//	private static ProtocolVersion parseProtocol(final Protocol p) {
//		switch (p) {
//			case HTTP_1_0:
//				return new ProtocolVersion("HTTP", 1, 0);
//			case HTTP_1_1:
//				return new ProtocolVersion("HTTP", 1, 1);
//			case SPDY_3:
//				return new ProtocolVersion("SPDY", 3, 1);
//			case HTTP_2:
//				return new ProtocolVersion("HTTP", 2, 0);
//		}
//
//		throw new IllegalAccessError("Unkwown protocol");
//	}
//
//	private static RequestBody createRequestBody(Request r) throws AuthFailureError {
//		final byte[] body = r.getBody();
//		if (body == null) return null;
//
//		return RequestBody.create(MediaType.parse(r.getBodyContentType()), body);
//	}
//}
