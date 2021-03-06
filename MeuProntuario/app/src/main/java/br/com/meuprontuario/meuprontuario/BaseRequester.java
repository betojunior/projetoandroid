package br.com.meuprontuario.meuprontuario;


import android.content.Context;
import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;

/**
 * Created by aluno on 14/06/2017.
 */

public class BaseRequester extends AsyncTask<BaseRequester, Object, String> {

    private String url;
    private JSONObject jsonObject;
    private Context context;
    private String strReturn;
    private String jsonString;
    private String authorization;
    private Method method;

    public BaseRequester() {

    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public JSONObject getJsonObject() {
        return jsonObject;
    }

    public void setJsonObject(JSONObject jsonObject) {
        this.jsonObject = jsonObject;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public String getStrReturn() {
        return strReturn;
    }

    public void setStrReturn(String strReturn) {
        this.strReturn = strReturn;
    }

    public String getJsonString() {
        return jsonString;
    }

    public void setJsonString(String gsonString) {
        this.jsonString = gsonString;
    }

    public String getAuthorization() {
        return authorization;
    }

    public void setAuthorization(String authorization) {
        this.authorization = authorization;
    }

    @Override
    protected String doInBackground(BaseRequester... params) {
        try {
            return sendGson(this.url, this.method, this.jsonString, this.context);
        } catch (JSONException e) {
            return null;
        } catch (IOException e) {
            return null;
        }
    }

    private static String sendGson(String apiUrl, Method method, String gsonString, Context context) throws JSONException, IOException {

        URL url;
        String returnStr = "";

        try {
            url = new URL(apiUrl);
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException("invalid url: " + apiUrl);
        }

        try {
            if (1 == 1) {


                /*
                HTTP
                 */
                HttpURLConnection conn = null;
                conn = (HttpURLConnection) url.openConnection();

                /*
                HTTPS
                 */
                //HttpsURLConnection conn = null;
                //conn = (HttpsURLConnection) url.openConnection();
                //conn.setSSLSocketFactory(generateCertificate(context).getSocketFactory());

                byte[] bytes = null;


                String body = "";
                if (gsonString != null) {
                    body = gsonString;
                }

                bytes = body.getBytes();
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

                conn.setReadTimeout(10000);
                conn.setConnectTimeout(15000);
                conn.setRequestMethod(String.valueOf("POST"));
                conn.setDoInput(true);
                conn.setDoOutput(true);

                OutputStream out = conn.getOutputStream();
                out.write(bytes);
                out.close();

                int status = conn.getResponseCode();

                InputStream is;
                String convertStreamToString = "";

                if (status == 400 || status == 500) {
                    //return MessageText.ERROR_SERVER.toString();
                    convertStreamToString = convertStreamToString(conn.getErrorStream(), "UTF-8");
                } else {
                    convertStreamToString = convertStreamToString(conn.getInputStream(), /*HTTP.UTF_8*/"UTF-8");
                    conn.disconnect();
                }
                //returnStr = convertStreamToString;
                return convertStreamToString;

            }
        } catch (Exception e) {
            return e.getMessage();
        }
        return returnStr;
    }


    private static String convertStreamToString(InputStream is, String enc) throws UnsupportedEncodingException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is, enc));
        StringBuilder sb = new StringBuilder();
        String line = null;

        try
        {
            while ((line = reader.readLine()) != null)
            {
                sb.append(line + "\n");
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                is.close();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }

        return sb.toString();
    }

    private static SSLContext generateCertificate(Context context) {

        // Load CAs from an InputStream
        // (could be from a resource or ByteArrayInputStream or ...)
        CertificateFactory cf = null;
        try {
            cf = CertificateFactory.getInstance("X.509");
        } catch (CertificateException e) {
            e.printStackTrace();
        }

        InputStream caInput = null;
        try {

            caInput = context.getAssets().open("certificado.crt");
            //caInput = new BufferedInputStream(new FileInputStream(context.getAssets().open("47b83fbefd5092a.crt")));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Certificate ca = null;
        try {
            ca = cf.generateCertificate(caInput);
            System.out.println("ca=" + ((X509Certificate) ca).getSubjectDN());
        } catch (CertificateException e) {
            e.printStackTrace();
        } finally {
            try {
                caInput.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // Create a KeyStore containing our trusted CAs
        String keyStoreType = KeyStore.getDefaultType();
        KeyStore keyStore = null;
        try {
            keyStore = KeyStore.getInstance(keyStoreType);
            keyStore.load(null, null);
            keyStore.setCertificateEntry("ca", ca);
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Create a TrustManager that trusts the CAs in our KeyStore
        String tmfAlgorithm = TrustManagerFactory.getDefaultAlgorithm();
        TrustManagerFactory tmf = null;
        try {
            tmf = TrustManagerFactory.getInstance(tmfAlgorithm);
            tmf.init(keyStore);
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        // Create an SSLContext that uses our TrustManager
        SSLContext SSLcontext = null;
        try {
            SSLcontext = SSLContext.getInstance("TLS");
            SSLcontext.init(null, tmf.getTrustManagers(), null);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }

        return SSLcontext;
    }

}
