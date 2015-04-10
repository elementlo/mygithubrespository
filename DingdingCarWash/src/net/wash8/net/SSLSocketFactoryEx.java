package net.wash8.net;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;

import org.apache.http.conn.ssl.SSLSocketFactory;


public class SSLSocketFactoryEx extends SSLSocketFactory {

	SSLContext sslContext = SSLContext.getInstance("TLS");

	public SSLSocketFactoryEx(KeyStore truststore)
			throws NoSuchAlgorithmException, KeyManagementException,
			KeyStoreException, UnrecoverableKeyException {
		super(truststore);
		
		TrustManager tm = null;
//		if (!AnydoorConfig.getConfig("CONFIG_TAG").equals("prd")) {
//
//				tm = new X509TrustManager() {
//
//				public java.security.cert.X509Certificate[] getAcceptedIssuers() {
//					return null;
//				}
//
//				@Override
//				public void checkClientTrusted(
//						java.security.cert.X509Certificate[] chain, String authType)
//						throws java.security.cert.CertificateException {
//
//				}
//
//				@Override
//				public void checkServerTrusted(
//						java.security.cert.X509Certificate[] chain, String authType)
//						throws java.security.cert.CertificateException {}
//			};
//		}if (!AnydoorConfig.getConfig("CONFIG_TAG").equals("prd")) { tm = new X509TrustManager() { public java.security.cert.X509Certificate[] getAcceptedIssuers() { return null; } @Override public void checkClientTrusted( java.security.cert.X509Certificate[] chain, String authType) throws java.security.cert.CertificateException { } @Override public void checkServerTrusted( java.security.cert.X509Certificate[] chain, String authType) throws java.security.cert.CertificateException {} }; }
		
		if(tm==null)
			sslContext.init(null, null, null);
		else
		sslContext.init(null, new TrustManager[]{tm}, null);
		
	
	}

	
	
	@Override
	public Socket createSocket(Socket socket, String host, int port,
			boolean autoClose) throws IOException, UnknownHostException {
		return sslContext.getSocketFactory().createSocket(socket, host, port,
				autoClose);
	}

	@Override
	public Socket createSocket() throws IOException {
		return sslContext.getSocketFactory().createSocket();
	}
}