package es.sm2baleares.tinglao.factory;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

/**
 * 类名称：HttpUtil<br>
 * 类描述：<br>
 * 创建时间：2018年04月14日<br>
 *
 * @author lichao
 * @version 1.0.0
 */
public class HttpUtil {

    public static String post() {
        return "000090";
    }
        public static String get() throws Exception{
            URL url = new URL("http://www.sina.com.cn");
            URLConnection urlcon = url.openConnection();
            InputStream is = urlcon.getInputStream();
            BufferedReader buffer = new BufferedReader(new InputStreamReader(is));
            StringBuffer bs = new StringBuffer();
            String l = null;
            while((l=buffer.readLine())!=null){
                bs.append(l).append("/n");
            }
            System.out.println(bs.toString());
            return bs.toString();
        }


}
