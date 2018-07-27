package com.cc.admin.util;

import com.cc.admin.entity.*;
import net.sf.json.JSONObject;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.CookieStore;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.poi.ss.formula.functions.T;
import org.junit.Test;

import javax.sound.midi.Soundbank;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.cc.admin.controller.jiaowu.JiaowuLoginController.xuehao;

public class JiaoWuUtil {

    //String username, String password
    public static CloseableHttpClient httpClient = HttpClients.createDefault();;
    public static String cookie = getCookie("http://jwxt.xtu.edu.cn/jsxsd",httpClient);
    public static String libraryCookie = getCookie("http://202.197.232.4:8081/opac_two/reader/infoList.jsp",httpClient);



    /**
     * 登录湘潭大学图书管理系统个人的
     * @return
     * @throws Exception
     */
    public static Map<String, Object> libraryLogin() throws Exception{
        Map<String,Object> result = new HashMap<String, Object>();
        ArrayList<BookInfo> bookInfos = new ArrayList<BookInfo>();
        String username = xuehao;
        String password = username.substring(4,10);
        String url = "http://202.197.232.4:8081/opac_two/include/login_app.jsp?login_type=&barcode="+ username+"&password="+password+"&_=";
        HttpPost httpPost = new HttpPost(url);
        httpPost.addHeader(new BasicHeader("Cookie",libraryCookie));
        httpClient.execute(httpPost);
        HttpGet httpGet = new HttpGet("http://202.197.232.4:8081/opac_two/reader/jieshuxinxi.jsp");
        httpGet.addHeader(new BasicHeader("Cookie",libraryCookie));
        HttpResponse response = httpClient.execute(httpGet);
        String resultStr = EntityUtils.toString(response.getEntity(),"ISO-8859-1");
        String res = new String(resultStr.getBytes("ISO-8859-1"),"GB2312");
        String p1 = "<td width=7></td><td align=center>.*?&nbsp;</td><td>.*?&nbsp;</td><td>.*?&nbsp;</td><td>.*?&nbsp;</td><td>.*?&nbsp;</td><td>.*?&nbsp;</td>";
        String p2 = "<td width=7></td><td align=center>(.*?)&nbsp;</td><td>(.*?)&nbsp;</td><td>(.*?)&nbsp;</td><td>(.*?)&nbsp;</td><td>(.*?)&nbsp;</td><td>(.*?)&nbsp;</td>";
        bookInfos = getBookInfos(bookInfos,res,p1,p2,6,"jieShuInfo");
        result.put("bookInfos" ,bookInfos);
        return result;
    }



    /**
     * 登录教务管理系统
     * @param pd
     * @return
     * @throws Exception
     */
    public static Map<String, Object>  loginJWXT(PageData pd) throws Exception {
        String username = pd.getString("USERNAME");
        String password = pd.getString("PASSWORD");
        Map<String,Object> result = new HashMap<String, Object>();
        String data = null;
        HttpPost httpPost = new HttpPost("http://jwxt.xtu.edu.cn/jsxsd/xk/LoginToXk?flag=sess");
        httpPost.addHeader(new BasicHeader("Cookie",cookie));
        try {
            HttpResponse response = httpClient.execute(httpPost);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != HttpStatus.SC_OK) {
                System.err.println("Method failed:" + response.getStatusLine());
            } else {
                String resultStr = EntityUtils.toString(response.getEntity());
                JSONObject resultJSON = JSONObject.fromObject(resultStr);
                data = resultJSON.getString("data");
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
        //下载验证码图片
        getYZCode(httpClient,cookie);
        //模拟登陆
        //验证码识别
        String yzmcode = MyCodeUtil.getCode( "D:\\img\\code.jsp");
        String encoded = getEncoded(data,username,password);
        HttpPost httpPost2 = new HttpPost("http://jwxt.xtu.edu.cn/jsxsd/xk/LoginToXk" );
        httpPost2.addHeader(new BasicHeader("Cookie",cookie));
        httpPost2.addHeader("Cache-Control", "no-cache");
        ArrayList<NameValuePair> par = new ArrayList<NameValuePair>();
        par.add(new BasicNameValuePair("USERNAME",pd.getString("USERNAME")));//pd.getString("USERNAME")
        par.add(new BasicNameValuePair("PASSWORD",pd.getString("PASSWORD")));//pd.getString("PASSWORD")
        par.add(new BasicNameValuePair("encoded",encoded));
        par.add(new BasicNameValuePair("RANDOMCODE",yzmcode));
        httpPost2.setEntity(new UrlEncodedFormEntity(par));
        String resultStr = "";
        try {
            HttpResponse response = httpClient.execute(httpPost2);
            resultStr = EntityUtils.toString(response.getEntity());
        }catch (IOException e) {
            e.printStackTrace();
        }
        String errorPattern = "<font color=\"red\">(.*?)</font>";
        Pattern errorP = Pattern.compile(errorPattern);
        Matcher errorM = errorP.matcher(resultStr);
        if(errorM.find()){
            result.put("error",errorM.group(1));
            System.out.println(errorM.group(1));
        }else {
            result.put("error","success");
        }
        DelAllFile.delFolder("D:\\img");
        String name =  getIndexInfo(httpClient,cookie).get(0);
        result.put("Cookie",cookie);
        result.put("httpClient",httpClient);
        result.put("name",name);
        return result;
    }



    /**
     * 得到绩点和班级排名
     * @param httpClient
     * @param cookie
     * @param url
     * @return
     * @throws UnsupportedEncodingException
     */
    public static List<JiDian> getJD(CloseableHttpClient httpClient, String cookie, String url) throws UnsupportedEncodingException {
        ArrayList<JiDian> jDInfo = new ArrayList<JiDian>();
        //                                     http://jwxt.xtu.edu.cn/jsxsd/kscj/cjjd_list
        //http://jwxt.xtu.edu.cn/jsxsd/kscj/cjjd_list?kksj=2015-2016-2&kksj=2015-2016-1&kksj=2016-2017-2&kksj=2016-2017-1&kksj=2017-2018-1&kksj=2017-2018-2&kclb=1&zsb=0
        //http://jwxt.xtu.edu.cn/jsxsd/kscj/cjjd_list?kksj=2016-2017-2&kclb=1&zsb=0
        HttpPost httpPost2 = new HttpPost(url);
        httpPost2.addHeader(new BasicHeader("Cookie",cookie));
        httpPost2.addHeader("Cache-Control", "no-cache");
        ArrayList<NameValuePair> par = new ArrayList<NameValuePair>();
        httpPost2.setEntity(new UrlEncodedFormEntity(par));
        String resultStr = "";
        try {
            HttpResponse response = httpClient.execute(httpPost2);
            resultStr = EntityUtils.toString(response.getEntity());
        }catch (IOException e) {
            e.printStackTrace();
        }
        String pattern = "<td>(.*?)</td>\\s+<td >(.*?)</td>\\s+<td >(.*?)</td>\\s+<td >(.*?)</td>";
        Pattern P = Pattern.compile(pattern);
        Matcher M = P.matcher(resultStr);
        M.find();
        JiDian jiDian = new JiDian(M.group(1),M.group(2),M.group(3),M.group(4));
        jDInfo.add(jiDian);
        return jDInfo;
    }





    /**
     * 得到姓名和学号
     * @param httpClient
     * @param cookie
     * @return
     * @throws IOException
     */
    public static List<String> getIndexInfo(CloseableHttpClient httpClient,String cookie) throws IOException {
        //姓名
        HttpGet acchttpGet = new HttpGet("http://jwxt.xtu.edu.cn/jsxsd/framework/main.jsp");
        acchttpGet.addHeader(new BasicHeader("Cookie",cookie));
        HttpResponse accresponse = httpClient.execute(acchttpGet);
        String resultStr = EntityUtils.toString(accresponse.getEntity());
        String pattern = "<div id=\"Top1_divLoginName\" class=\"Nsb_top_menu_nc\" style=\"color: #000000;\">(.*?)\\((.*?)\\)</div>";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(resultStr);
        List<String> result = new ArrayList<String>();
        m.find();
        result.add(m.group(1));
        result.add(m.group(2));
        return result;
    }


    /**
     * 得到课表信息
     * @param httpClient
     * @param cookie
     * @return
     * @throws IOException
     */
    public static List<KeBiao> getKebiaoInfo(CloseableHttpClient httpClient,String cookie) throws IOException {
        ArrayList<KeBiao> keBiaoInfo = new ArrayList<KeBiao>();
        String url = "http://jwxt.xtu.edu.cn/jsxsd/xskb/xskb_list.do";
        HttpGet httpGet = new HttpGet(url);
        httpGet.addHeader(new BasicHeader("Cookie",cookie));
        HttpResponse accresponse = httpClient.execute(httpGet);
        String resultStr = EntityUtils.toString(accresponse.getEntity());
        //String p = "<div id=\".*?\"\\s+style=\"display: none;\" class=\"kbcontent\" >.*?<br/><.*?>.*?</font><br/><.*?>.*?</font><br/><.*?>.*?</font><br/></div>";
        String p = "<div.*?(\\s+)?.*?老师.*?</div>";
        Pattern r = Pattern.compile(p);
        Matcher m = r.matcher(resultStr);
        List<String> result = new ArrayList<String>();
        while(m.find()){
            result.add(m.group(0));
        }
        String resultpart = "";
        //<font title='老师'>(.*?)</font><br/><font title='周次(节次)'>(.*?)</font><br/><font title='教室'>(.*?)</font><br/></div>
        String p2 =  "id=\"(.*?)\".*?\\s+?.*?>(.*?)<br/><font title='老师'>(.*?)</font><br/><font title=.*?>(.*?)</font><br/><font title='教室'>(.*?)</font><br/></div>";
        for(int i = 0;i < result.size();i++) {
            resultpart = result.get(i);
            Pattern r1 = Pattern.compile(p2,Pattern.MULTILINE);
            Matcher m1 = r1.matcher(resultpart);
            List<String> result1 = new ArrayList<String>();
            m1.find();
            for(int j = 1;j <= 5 ;j++){
                result1.add(m1.group(j));
            }
            KeBiao keBiao = new KeBiao(getKeBiaoTime(result1.get(0)),result1.get(1),result1.get(2),result1.get(3),result1.get(4));
            keBiaoInfo.add(keBiao);
        }
        return keBiaoInfo;
    }


    //Java Web程序设计
    public static List<BookInfo> getBookInfo(CloseableHttpClient httpClient,String key1) throws IOException {
        ArrayList<BookInfo> bookInfo = new ArrayList<BookInfo>();
        String url = "http://202.197.232.4:8081/opac_two/search2/searchout.jsp";
        HttpPost httpPost2 = new HttpPost(url);
        ArrayList<NameValuePair> par = new ArrayList<NameValuePair>();
        if(key1!=null) {
            String key = new String(key1.getBytes("GBK"), "ISO8859-1");
            par.add(new BasicNameValuePair("search_no_type", "N"));//pd.getString("USERNAME")
            par.add(new BasicNameValuePair("snumber_type", "N"));//pd.getString("PASSWORD")
            par.add(new BasicNameValuePair("suchen_type", "1"));
            par.add(new BasicNameValuePair("suchen_word", key));
            par.add(new BasicNameValuePair("size", "100000"));
            par.add(new BasicNameValuePair("suchen_match", "qx"));
            par.add(new BasicNameValuePair("recordtype", "all"));
            par.add(new BasicNameValuePair("library_id", "all"));
            par.add(new BasicNameValuePair("show_type", "wenzi"));
            httpPost2.setEntity(new UrlEncodedFormEntity(par));
            HttpResponse response = httpClient.execute(httpPost2);
            String resultStr = EntityUtils.toString(response.getEntity(), "ISO8859-1");
            String res = new String(resultStr.getBytes("ISO8859-1"), "GBK");
            String p1 = "<td align=center><br>.*?&nbsp;<br></td>\\s+<td><br><a href=\".*?\" target = \"_blank\">.*?&nbsp;</a><br></td>\\s+<td><br>.*?&nbsp;<br></td>\\s+<td><br>.*?&nbsp;<br></td>";
            String p2 = "<td align=center><br>(.*?)&nbsp;<br></td>\\s+<td><br><a href=\"s_detail.jsp\\?sid=(.*?)\" target = \"_blank\">(.*?)&nbsp;</a><br></td>\\s+<td><br>(.*?)&nbsp;<br></td>\\s+<td><br>(.*?)&nbsp;<br></td>";
            bookInfo = getBookInfos(bookInfo, res, p1, p2, 5, "jdChaInfo");
            System.out.println(bookInfo.get(5).toString());
        }
        return bookInfo;
    }

    //"<td align=center><br>(.*?)&nbsp;<br></td>\\s+<td><br><a href=\"s_detail.jsp?sid=(.*?)\" target = \"_blank\">(.*?)&nbsp;</a><br></td>\\s+<td><br>(.*?)&nbsp;<br></td>\\s+<td><br>(.*?)&nbsp;<br></td\\s+<td><br>(.*?)&nbsp;<br></td>\\s+<td><br>.*?&nbsp;<br></td>"

    public static List<BookInfo> getBookDetail(CloseableHttpClient httpClient,String barCode) throws IOException {
        ArrayList<BookInfo> bookInfos = new ArrayList<BookInfo>();
        String url = "http://202.197.232.4:8081/opac_two/search2/s_detail.jsp?sid="+barCode;
        HttpGet httpGet = new HttpGet(url);
        HttpResponse accresponse = httpClient.execute(httpGet);
        String resultStr = EntityUtils.toString(accresponse.getEntity(), "ISO8859-1");
        String res = new String(resultStr.getBytes("ISO8859-1"), "GBK");
        String p1 = "<td align=center>.*?</td>\\s+<td align=center>.*?</td>\\s+<td align=center>.*?</td>\\s+<td align=center>.*?</td>\\s+<td align=center></td>\\s+<td align=center></td>";
        String p2 = "<td align=center>(.*?)</td>\\s+<td align=center>.*?</td>\\s+<td align=center>(.*?)</td>\\s+<td align=center>(.*?)</td>\\s+<td align=center></td>\\s+<td align=center></td>";
        bookInfos =  getBookInfos(bookInfos,res,p1,p2,3,"xxChaInfo");
        return bookInfos;
    }
    /**
     * 查成绩
     * @param httpClient
     * @param cookie
     * @return
     * @throws IOException
     */
    public static List<ScorePage> getScoreInfo(CloseableHttpClient httpClient,String cookie,String url) throws IOException {
        ArrayList<ScorePage> socreInfo = new ArrayList<ScorePage>();
        HttpGet acchttpGet = new HttpGet(url);
        acchttpGet.addHeader(new BasicHeader("Cookie",cookie));
        HttpResponse accresponse = httpClient.execute(acchttpGet);
        String resultStr = EntityUtils.toString(accresponse.getEntity());
        String pattern = "(<tr>\\s+<td>.*?</td>\\s+<td>.*?</td>\\s+<td align=\"left\">.*?</td>\\s+<td style=\" \"><a href=\"javascript:JsMod.*?\">.*?</a></td>\\s+<td>.*?</td>\\s+<td>.*?</td>\\s+<td>.*?</td>\\s+<td>.*?</td>\\s+<td>.*?</td>\\s+</tr>)";
        //1:序号 2:学期 3:课程名字 4：成绩 5:学分 6：总学时 7:考核方式 8:课程属性 9:课程性质
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(resultStr);
        List<String> result = new ArrayList<String>();
        while(m.find()){
            result.add(m.group());
        }
        String resultpart = "";  //得到某个科目的数据
        String pattern2 =  "<tr>\\s+<td>(.*?)</td>\\s+<td>(.*?)</td>\\s+<td align=\"left\">(.*?)</td>\\s+<td style=\" \"><a href=\"javascript:JsMod.*?\">(.*?)</a></td>\\s+<td>(.*?)</td>\\s+<td>(.*?)</td>\\s+<td>(.*?)</td>\\s+<td>(.*?)</td>\\s+<td>(.*?)</td>\\s+</tr>";
        for(int i = 0;i < result.size();i++) {
            resultpart = result.get(i);
            Pattern r1 = Pattern.compile(pattern2);
            Matcher m1 = r1.matcher(resultpart);
            List<String> result1 = new ArrayList<String>();
            m1.find();
            for(int j = 1;j <= 9 ;j++){
                result1.add(m1.group(j));
            }
            ScorePage scorePage = new ScorePage(result1.get(0),result1.get(1),result1.get(2),result1.get(3),result1.get(4),result1.get(5),result1.get(6),result1.get(7),result1.get(8));
            socreInfo.add(scorePage);
        }
        return socreInfo;
    }

    /**
     * 下载验证码图片
     * @param httpClient
     * @param cookie
     * @throws IOException
     */
    public  static void getYZCode(CloseableHttpClient httpClient,String cookie) throws IOException {
        final HttpGet httpGet = new HttpGet("http://jwxt.xtu.edu.cn/jsxsd/verifycode.servlet");
        httpGet.addHeader(new BasicHeader("Cookie",cookie));
        httpGet.setHeader("User-Agent",
                "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_10_4) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/43.0.2357.132 Safari/537.36");
        // 请求http
        final HttpResponse codeResponse = httpClient.execute(httpGet);
        if (codeResponse.getStatusLine().getStatusCode() != org.apache.http.HttpStatus.SC_OK) {
            System.err.println("Method failed: " + codeResponse.getStatusLine());
        }
        String picName = "D:\\img";
        final File f = new File(picName);
        f.mkdirs();
        picName += "/code.jpg";
        final InputStream inputStream = codeResponse.getEntity().getContent();
        final OutputStream outStream = new FileOutputStream("D:\\img\\code.jpg");
        IOUtils.copy(inputStream, outStream);
        outStream.close();
        httpGet.releaseConnection();
    }

    /**
     * 得到cookie
     * @param url
     * @param httpClient
     * @return
     */
    public static String getCookie(String url, CloseableHttpClient httpClient){
        //HttpPost httpPost = null;
        HttpGet httpGet = null;
        String result = null;
        try {
            CookieStore cookieStore = new BasicCookieStore();
            httpClient = HttpClients.custom().setDefaultCookieStore(cookieStore).build();
            httpGet = new HttpGet(url);
            List<NameValuePair> list = new ArrayList<NameValuePair>();
            httpClient.execute(httpGet);
            List<Cookie> cookies = cookieStore.getCookies();
            result = cookies.get(0).getValue().toString();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return result;
    }

    /**
     * 得到encode
     * @param data
     * @param username
     * @param password
     * @return
     */
    public static String getEncoded(String data,String username,String password){
        String encoded = "";
        String scode = data.split("#")[0];
        String sxh = data.split("#")[1];
        String code=username + "%%%" + password;

        for(int i=0;i<code.length();i++){
            if(i<20){
                encoded = encoded+code.substring(i,i+1)+scode.substring(0,Integer.parseInt(sxh.substring(i,i+1)));
                scode = scode.substring(Integer.parseInt(sxh.substring(i,i+1)),scode.length());
            }else{
                encoded=encoded+code.substring(i,code.length());
                i=code.length();
            }
        }
        System.out.println("encoded :" + encoded);
        return encoded;
    }


    /**
     * 得到查成绩的url
     * @param firstXQ
     * @param xueqi
     * @return
     */
    public static String getChaURL(String firstXQ,String xueqi){
        int xq = Integer.parseInt(xueqi);
        int fYear = Integer.parseInt(firstXQ);
        int sYear = fYear+1;
        int udXQ = (xq%2)==1?1:2;
        if(xq == 3){ fYear++;sYear++;}
        if(xq == 4){ fYear++;sYear++;}
        if(xq == 5){ fYear+=2;sYear+=2;}
        if(xq == 6){ fYear+=2;sYear+=2;}
        if(xq == 7){ fYear+=3;sYear+=3;}
        if(xq == 8){ fYear+=3;sYear+=3;}
        String url = fYear+"-"+sYear+"-"+udXQ;
        return  url;
    }


    /**
     * 查绩点的url
     * @param firstXQ
     * @param xueqi
     */
    public static String getJDURL(String firstXQ,String xueqi){
        //http://jwxt.xtu.edu.cn/jsxsd/kscj/cjjd_list?kksj=2015-2016-2&kksj=2015-2016-1&kksj=2016-2017-2&kksj=2016-2017-1&kksj=2017-2018-1&kksj=2017-2018-2&kclb=1&zsb=0
        //http://jwxt.xtu.edu.cn/jsxsd/kscj/cjjd_list?kksj=2016-2017-2&kclb=1&zsb=0
        //http://jwxt.xtu.edu.cn/jsxsd/kscj/cjjd_list?
        String  kksj =  getChaURL(firstXQ,xueqi);
        String url = "kksj="+kksj+"&kclb=1&zsb=0";
        return url;
    }

    //得到课程的上课时间坐标
    public  static String getKeBiaoTime(String id){
        String a[] = id.split("-");
        String result = "";
        if(a[0].equals(Const.KB12)) {result+="1-";}
        if(a[0].equals(Const.KB34)) {result+="2-";}
        if(a[0].equals(Const.KB56)) {result+="3-";}
        if(a[0].equals(Const.KB78)) {result+="4-";}
        if(a[0].equals(Const.KB910)) {result+="5-";}
        result+=a[1];
        return result;
    }

    public static ArrayList<BookInfo> getBookInfos(ArrayList<BookInfo> result,String resultStr,String p1,String p2,int size,String type){
        Pattern r = Pattern.compile(p1);
        Matcher m = r.matcher(resultStr);
        ArrayList<String> rsss = new ArrayList<String>();
        while(m.find()){
            rsss.add(m.group());
        }
        String resultpart = "";
        for(int i = 0;i < rsss.size();i++) {
            resultpart = rsss.get(i);
            Pattern r1 = Pattern.compile(p2);
            Matcher m1 = r1.matcher(resultpart);
            List<String> result1 = new ArrayList<String>();
            m1.find();
            for(int j = 1;j <= size ;j++){
                result1.add(m1.group(j));
            }
            //ScorePage scorePage = new ScorePage(result1.get(0),result1.get(1),result1.get(2),result1.get(3),result1.get(4),result1.get(5),result1.get(6),result1.get(7),result1.get(8));

            if(type == "jieShuInfo") {BookInfo bookInfo = new BookInfo(result1.get(0), result1.get(1),"","", result1.get(2),"", result1.get(3), result1.get(4),result1.get(5)); result.add(bookInfo);}
            if(type == "jdChaInfo") { BookInfo bookInfo = new BookInfo(result1.get(0), result1.get(2), result1.get(3), result1.get(4), result1.get(1), "","","",""); result.add(bookInfo);}
            if(type == "xxChaInfo") { BookInfo bookInfo = new BookInfo("", "","","","", result1.get(0),result1.get(1),result1.get(2),""); result.add(bookInfo);}

        }
        return result;
    }

    /**
     * 测试用的
     * @throws Exception
     */
    @Test
    public void test() throws Exception {
        ArrayList<BookInfo> bookInfo = new ArrayList<BookInfo>();
        String url = "http://202.197.232.4:8081/opac_two/search2/searchout.jsp";
        HttpPost httpPost2 = new HttpPost(url);
        ArrayList<NameValuePair> par = new ArrayList<NameValuePair>();
        String key1 = "Java Web程序设计";
        String key = new String(key1.getBytes("GBK"),"ISO8859-1");
        par.add(new BasicNameValuePair("search_no_type","N"));//pd.getString("USERNAME")
        par.add(new BasicNameValuePair("snumber_type","N"));//pd.getString("PASSWORD")
        par.add(new BasicNameValuePair("suchen_type","1"));
        par.add(new BasicNameValuePair("suchen_word",key));
        par.add(new BasicNameValuePair("suchen_match","qx"));
        par.add(new BasicNameValuePair("recordtype","all"));
        par.add(new BasicNameValuePair("library_id","all"));
        par.add(new BasicNameValuePair("show_type","wenzi"));
        httpPost2.setEntity(new UrlEncodedFormEntity(par));
        HttpResponse response = httpClient.execute(httpPost2);
        String resultStr = EntityUtils.toString(response.getEntity(),"ISO8859-1");
        String res = new String(resultStr.getBytes("ISO8859-1"),"GBK");
        String p1 = "<td align=center><br>.*?&nbsp;<br></td>\\s+<td><br><a href=\".*?\" target = \"_blank\">.*?&nbsp;</a><br></td>\\s+<td><br>.*?&nbsp;<br></td>\\s+<td><br>.*?&nbsp;<br></td>";
        String p2 = "<td align=center><br>(.*?)&nbsp;<br></td>\\s+<td><br><a href=\"s_detail.jsp\\?sid=(.*?)\" target = \"_blank\">(.*?)&nbsp;</a><br></td>\\s+<td><br>(.*?)&nbsp;<br></td>\\s+<td><br>(.*?)&nbsp;<br></td>";
        Pattern r = Pattern.compile(p1);
        Matcher m = r.matcher(res);
        ArrayList<String> rsss = new ArrayList<String>();
        while(m.find()){
            rsss.add(m.group());
            System.out.println(m.group());
        }
        String resultpart = "";
        for(int i = 0;i < rsss.size();i++) {
            resultpart = rsss.get(i);
            Pattern r1 = Pattern.compile(p2);
            Matcher m1 = r1.matcher(resultpart);
            List<String> result1 = new ArrayList<String>();
            m1.find();
            for (int j = 1; j <= 5; j++) {
                result1.add(m1.group(j));
            }
            BookInfo bookInfotest = new BookInfo(result1.get(0), result1.get(2), result1.get(3), result1.get(4), result1.get(1), "","","","");
            System.out.println(bookInfotest.toString());
        }
        //getBookInfos(bookInfo,res,p1,p2,5,"jdChaInfo");
    }


    @Test
    public void tes2() throws IOException {
        String url = "http://202.197.232.4:8081/opac_two/search2/s_detail.jsp?sid=0100970347";
        HttpGet httpGet = new HttpGet(url);
        HttpResponse accresponse = httpClient.execute(httpGet);
        String resultStr = EntityUtils.toString(accresponse.getEntity(), "ISO8859-1");
        String res = new String(resultStr.getBytes("ISO8859-1"), "GBK");
        String p1 = "<td align=center>.*?</td>\\s+<td align=center>.*?</td>\\s+<td align=center>.*?</td>\\s+<td align=center>.*?</td>\\s+<td align=center></td>\\s+<td align=center></td>";
        String p2 = "<td align=center>(.*?)</td>\\s+<td align=center>.*?</td>\\s+<td align=center>(.*?)</td>\\s+<td align=center>(.*?)</td>\\s+<td align=center></td>\\s+<td align=center></td>";
        Pattern r = Pattern.compile(p1);
        Matcher m = r.matcher(res);
        ArrayList<String> rsss = new ArrayList<String>();
        while(m.find()){
            rsss.add(m.group());
            System.out.println(m.group());
        }
        String resultpart = "";

    }

    @Test
    public void test3() throws IOException {
        ArrayList<BookInfo> bookInfos = new ArrayList<BookInfo>();
        String url = "http://202.197.232.4:8081/opac_two/search2/s_detail.jsp?sid=0100970347";
        HttpGet httpGet = new HttpGet(url);
        HttpResponse accresponse = httpClient.execute(httpGet);
        String resultStr = EntityUtils.toString(accresponse.getEntity(), "ISO8859-1");
        String res = new String(resultStr.getBytes("ISO8859-1"), "GBK");
        String p1 = "<td align=center>.*?</td>\\s+<td align=center>.*?</td>\\s+<td align=center>.*?</td>\\s+<td align=center>.*?</td>\\s+<td align=center></td>\\s+<td align=center></td>";
        String p2 = "<td align=center>(.*?)</td>\\s+<td align=center>.*?</td>\\s+<td align=center>(.*?)</td>\\s+<td align=center>(.*?)</td>\\s+<td align=center></td>\\s+<td align=center></td>";
        bookInfos =  getBookInfos(bookInfos,res,p1,p2,3,"xxChaInfo");
        System.out.println(bookInfos.get(0).toString());
    }

}
