package abc.kraitax;

import org.apache.log4j.Logger;

import javax.net.ssl.*;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

public class HTTPSClient2 {

	private final static Logger logger = Logger.getLogger(HTTPSClient2.class);
	
	public static void main(String[] args) {
		String data = "<soapenv:Envelope\n" + 
				"	xmlns:q0=\"http://www.abcthebank.com/itaxws/\"\n" + 
				"	xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\"\n" + 
				"	xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\"\n" + 
				"	xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">\n" + 
				"	<soapenv:Header>\n" + 
				"  </soapenv:Header>\n" + 
				"  <soapenv:Body>\n" + 
				"    <q0:ValidateCustomerDetail>\n" + 
				"      <strUtilityCode>32</strUtilityCode>\n" + 
				"      <strCustomerRef>0003333</strCustomerRef>\n" + 
				"      <strCustRefAdd1>22222</strCustRefAdd1>\n" + 
				"      <strCustRefAdd2>44444</strCustRefAdd2>\n" + 
				"      <strCustRefAdd3/>\n" + 
				"      <strCustRefAdd4/>\n" + 
				"      <strCustRefAdd5/>\n" + 
				"      <strCustRefPathFile1/>\n" + 
				"      <strCustRefPathFile2/>\n" + 
				"      <strCustRefPathFile3/>\n" + 
				"      <strVendorCode/>\n" + 
				"      <strVendorPassword/>\n" + 
				"    </q0:ValidateCustomerDetail>\n" + 
				"  </soapenv:Body>\n" + 
				"</soapenv:Envelope>";
		String urlString = "https://172.16.2.66/itax/services/itaxws";
		System.out.println(urlString);
		String res = new HTTPSClient2().sendHttpsRequest(data, urlString);
		System.out.println(res);
	}
	
	public String sendHttpsRequest(String xml, String path) {
		logger.info("URL: " + path + " xml: " + xml);
		try {
			SSLContext sContext = SSLContext.getInstance("TLS");
			////new SSLUtilities();
			sContext.init(null, new TrustManager[] { new BlindTrustManager() }, new SecureRandom());
			SSLContext.setDefault(sContext);

			URL url = new URL(path);
			HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
			HttpsURLConnection.setDefaultSSLSocketFactory(sContext.getSocketFactory());
			con.setHostnameVerifier(new HostnameVerifier() {
				public boolean verify(String arg0, SSLSession arg1) {
					return true;
				}
			});

			con.setDoOutput(true);
			DataOutputStream wr = new DataOutputStream(con.getOutputStream());
			wr.writeBytes(xml);
			wr.flush();
			wr.close();
			String responseStatus = con.getResponseMessage();
			logger.info("responseStatus ::: " + responseStatus);
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();
			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
			return response.toString();
		} catch (Exception ex) {
			logger.error("Error  :::", ex);
			return null;
		}
	}

	class BlindTrustManager implements X509TrustManager {
		public X509Certificate[] getAcceptedIssuers() {
			return null;
		}

		public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
		}

		public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
		}
	}
}