package Util;

import com.codeborne.selenide.SelenideConfig;

import java.sql.Time;

public class Configuration {
    public static String WEB_BROWSER = "";
    public static long  TIME_OUT= 0;
    public static int PAGE_LOAD_TIME= 0;
    public static boolean DEFAULT_MAXIMUM =true;
//    private static final SelenideConfig defaults = new SelenideConfig();

    public static void  ReadConfig(){
        if(CheckParameter("WEB_BROWSER")){
            Configuration.WEB_BROWSER = System.getenv("WEB_BROWSER");
        }
        Configuration.TIME_OUT = Long.parseLong(System.getProperty("timeout"));
        Configuration.PAGE_LOAD_TIME = Integer.parseInt(System.getProperty("pageLoadTimeout"));
        Configuration.DEFAULT_MAXIMUM = Boolean.parseBoolean(System.getProperty("startMaximized"));
//        TIME_OUT = defaults.timeout();
    }
    public static boolean CheckParameter(String para){
        if(System.getenv(para)!=null){
            return true;
        }
        return false;
    }

}
