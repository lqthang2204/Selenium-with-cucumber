package Util;

public class Configuration {
    public static String WEB_BROWSER = "";
    public static int TIME_OUT= 0;
    public static int PAGE_LOAD_TIME= 0;
    public static boolean DEFAULT_MAXIMUM =true;

    public static void  ReadConfig(){
        if(CheckParameter("WEB_BROWSER")){
            Configuration.WEB_BROWSER = System.getenv("WEB_BROWSER");
        }
        Configuration.TIME_OUT = Integer.parseInt(System.getProperty("timeout"));
        Configuration.PAGE_LOAD_TIME = Integer.parseInt(System.getProperty("pageLoadTimeout"));
        Configuration.DEFAULT_MAXIMUM = Boolean.parseBoolean(System.getProperty("startMaximized"));
    }
    public static boolean CheckParameter(String para){
        if(System.getenv(para)!=null){
            return true;
        }
        return false;
    }
}
