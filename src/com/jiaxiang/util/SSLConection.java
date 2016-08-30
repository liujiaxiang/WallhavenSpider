package com.jiaxiang.util;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

public class SSLConection {

	private static TrustManager[] trustManagers;

    public static class _FakeX509TrustManager implements javax.net.ssl.X509TrustManager {
        private static final X509Certificate[] _AcceptedIssuers = new X509Certificate[]{};

        public void checkClientTrusted(X509Certificate[] arg0, String arg1)
                throws CertificateException {
        }

        public void checkServerTrusted(X509Certificate[] arg0, String arg1)
                throws CertificateException {
        }

        public X509Certificate[] getAcceptedIssuers() {
            return (_AcceptedIssuers);
        }
    }

    public static void allowAllSSL() {

        javax.net.ssl.HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
            @Override
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        });

        javax.net.ssl.SSLContext context;

        if (trustManagers == null) {
            trustManagers = new TrustManager[]{new _FakeX509TrustManager()};
        }

        try {
            context = javax.net.ssl.SSLContext.getInstance("TLS");
            context.init(null, trustManagers, new SecureRandom());
            javax.net.ssl.HttpsURLConnection.setDefaultSSLSocketFactory(context.getSocketFactory());
        } catch (NoSuchAlgorithmException e) {
            System.out.println("allowAllSSL:"+e.toString());
        } catch (KeyManagementException e) {
        	System.out.println("allowAllSSL:"+e.toString());
        }
    }
    
    public static void trustAllHosts() {
    	// Create a trust manager that does not validate certificate chains
    	TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() 
    	{
     
    		public java.security.cert.X509Certificate[] getAcceptedIssuers() {
    			return new java.security.cert.X509Certificate[] {};
    		}
     
    		public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
    			System.out.println("checkServerTrusted");
    		}
     
    		public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
    			System.out.println("checkServerTrusted");
    		}
    	} };
     
    	// Install the all-trusting trust manager
    	try {
    		SSLContext sc = SSLContext.getInstance("TLS");
    		sc.init(null, trustAllCerts, new java.security.SecureRandom());
    		HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    }
	
}
