package com.cc.admin.controller.jiaowu;

import com.cc.admin.controller.base.BaseController;
import com.cc.admin.entity.*;
import com.cc.admin.util.JiaoWuUtil;
import com.cc.admin.util.PageData;
import org.junit.Test;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.cc.admin.util.JiaoWuUtil.*;

@Controller
@RequestMapping(value = "/jiaowu")
public class JiaowuLoginController extends BaseController{

    public static String level;
    public static String xuehao;

    /**
     * 前往登录页面
     * @return
     */
    @RequestMapping(value = "/toLoginPage")
    public ModelAndView toLoginPage(){
        ModelAndView mv = new ModelAndView();
        mv.setViewName("jiaowu/jwLogin");
        return mv;
    }

    @RequestMapping(value = "/toMyLibPage")
    public ModelAndView toLibPage(Page page) throws Exception {
        ModelAndView mv = new ModelAndView();
        mv = this.getModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();
        page.setPd(pd);
        Map<String, Object> result = libraryLogin();
        ArrayList<BookInfo> bookInfos = (ArrayList<BookInfo>) result.get("bookInfos");
        pd.put("bookInfos",bookInfos);
        mv.addObject("pd",pd);
        mv.setViewName("jiaowu/myLibrary");
        return mv;
    }


    @ResponseBody
    @RequestMapping(value = "/viewBook")
    public Map<String,Object> viewBook() throws IOException {
        Map<String,Object> result = new HashMap<String,Object>();
        PageData pd = new PageData();
        pd = this.getPageData();
        String barCode = pd.getString("BARCODE");
        List<BookInfo> xxBookInfos = getBookDetail(httpClient,barCode);
        String bookNum = String.valueOf(xxBookInfos.size());
        String xxBookInfo = "";
        for(int i = 0;i<xxBookInfos.size();i++){
            xxBookInfo+=xxBookInfos.get(i).xxBookInfo();
        }
        System.out.println(xxBookInfo);
        result.put("xxBookInfo",xxBookInfo);
        result.put("bookNum",bookNum);
        return result;
    }

    @RequestMapping(value = "/jdChaBook")
    public ModelAndView jdChaBook(Page page) throws IOException {
        ModelAndView mv = new ModelAndView();
        mv = this.getModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();
        page.setPd(pd);
        String key = (String)pd.get("keywords");
        List<BookInfo> jdBookInfos = getBookInfo(httpClient,key);
        pd.put("jdBookInfos",jdBookInfos);
        mv.addObject("pd",pd);
        mv.setViewName("jiaowu/library");
        return mv;
    }


    /**
     * 查课表
     * score: KeBiao{time='4-1', className='软件工程Ⅰ', teacher='文获和高级工程师', week='1-12(周)', place='一教楼-102'}
     * @param page
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/toKeBiaoPage")
    public ModelAndView toKeBiaoPage(Page page) throws IOException {
        ModelAndView mv = new ModelAndView();
        mv = this.getModelAndView();
        PageData pd = new PageData();
        pd = this.getPageData();
        List<KeBiao> keBiaoInfo = new ArrayList<KeBiao>();
        keBiaoInfo = getKebiaoInfo(httpClient,cookie);
        page.setPd(pd);
        int size = keBiaoInfo.size();
        pd.put("keBiaoInfo",keBiaoInfo);
        String kbS = "";
        for(int i =0;i<keBiaoInfo.size();i++){
            kbS+=keBiaoInfo.get(i).toString();
        }
        pd.put("kbS",kbS);
        pd.put("size",size);
        mv.addObject("pd",pd);
        mv.setViewName("jiaowu/keBiao");
        return mv;
    }

    @ResponseBody
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public Map<String,String> login() throws Exception {
        Map<String,String> map = new HashMap<String, String>();
        PageData pd = new PageData();
        pd = this.getPageData();
        String result = "";
        String KEYDAYA[] = pd.getString("KEYDATA").split(",");
        Map<String,Object> resultMap = new HashMap<String,Object>();
        if(null != KEYDAYA ) {
            //判断用户名和密码
            String USERNAME = KEYDAYA[0];
            String PASSWORD = KEYDAYA[1];
            level = USERNAME.substring(0,4);
            xuehao = USERNAME;
            pd.put("USERNAME", USERNAME);
            pd.put("PASSWORD", PASSWORD);
            resultMap =  JiaoWuUtil.loginJWXT(pd);
            //图书馆简单查询
            getBookInfo(httpClient,"Java Web程序设计");
            //图书馆登录得到个人借书信息
            //libraryLogin();
            //课程表
            //getKebiaoInfo(httpClient,cookie);
            //绩点
            //System.out.println(getJD(httpClient,cookie));
            //获取成绩
            //List<ScorePage> scoreInfo =  getScoreInfo((CloseableHttpClient)resultMap.get("httpClient"),(String) resultMap.get("Cookie"));
        }
        if("用户名或密码错误,请联系本院教务老师!" == resultMap.get("error")){
            result = "usererror";
        }else if("验证码错误!!" == resultMap.get("error")){
            result = "codeerror";
        }else {
            result = "success";
        }
        map.put("result",result);
        return map;
    }

    @RequestMapping(value = "/index")
    public ModelAndView toIndexPage() throws IOException {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("jiaowu/main");
        PageData pd = new PageData();
        pd = this.getPageData();
        List<String> info = getIndexInfo(httpClient,cookie);
        String sName = info.get(0);
        String xueHao = info.get(1);
        pd.put("sName",sName);
        pd.put("xueHao",xueHao);
        mv.addObject("pd",pd);
        return mv;
    }



    @RequestMapping(value = "/chaAllScore")
    public ModelAndView chaAllScore(Page page) throws IOException {
        ModelAndView mv = new ModelAndView();
        mv = this.getModelAndView();
        PageData pd  = new PageData();
        pd = this.getPageData();
        page.setPd(pd);
        String JDUrl = "http://jwxt.xtu.edu.cn/jsxsd/kscj/cjjd_list?kksj=2015-2016-2&kksj=2015-2016-1&kksj=2016-2017-2&kksj=2016-2017-1&kksj=2017-2018-1&kksj=2017-2018-2&kclb=1&zsb=0";
        String chaAll = "http://jwxt.xtu.edu.cn/jsxsd/kscj/cjcx_list?xq=null";
        List<ScorePage> scoreList =getScoreInfo(httpClient,cookie,chaAll);
        List<JiDian> jiDianList = getJD(httpClient,cookie,JDUrl);
        pd.put("jiDianList",jiDianList);
        pd.put("scoreList",scoreList);
        mv.addObject("pd",pd);
        mv.setViewName("jiaowu/score_list");
        return mv;
    }

    @RequestMapping(value = "/chaScore")
    public ModelAndView chaScore(Page page) throws IOException {
        ModelAndView mv = new ModelAndView();
        mv = this.getModelAndView();
        PageData pd  = new PageData();
        pd = this.getPageData();
        String xueqi =  pd.getString("xueqi");     //1
        page.setPd(pd);
        String url = "http://jwxt.xtu.edu.cn/jsxsd/kscj/cjcx_list?xq="+getChaURL(level,xueqi);
        String JDUrl = "http://jwxt.xtu.edu.cn/jsxsd/kscj/cjjd_list?"+getJDURL(level,xueqi);
        System.out.println("url "+url);
        System.out.println("JDUrl "+JDUrl);
        List<ScorePage> scoreList =getScoreInfo(httpClient,cookie,url);
        List<JiDian> jiDianList = getJD(httpClient,cookie,JDUrl);
        pd.put("scoreList",scoreList);
        pd.put("jiDianList",jiDianList);
        mv.addObject("pd",pd);
        mv.setViewName("jiaowu/score_list");
        return mv;
    }


    @RequestMapping(value = "/login_default")
    public ModelAndView defaultPage(){
        ModelAndView mv = new ModelAndView();
        mv= this.getModelAndView();
        PageData pd = new PageData();
        pd =this.getPageData();
        //名字，一卡通余额，图书馆欠费，图书借阅，今日课程

        mv.setViewName("jiaowu/default");
        return mv;
    }



    @Test
    public void test() throws UnsupportedEncodingException {

    }

}
