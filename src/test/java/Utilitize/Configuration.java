package Utilitize;

//import com.codeborne.selenide.SelenideConfig;

import java.io.File;

public class Configuration {
    public static String WEB_BROWSER = "";
    public static long  TIME_OUT= 0;
    public static long PAGE_LOAD_TIME= 0;
    public static boolean DEFAULT_MAXIMUM =true;
    public static String PATH_POSTMAN;
    public static boolean IS_HEADLESS;
    public static String[] PREFIX= new String[]{"unique.", "user.","key."};
    public static boolean RUN_UNSAFE_OKHTTP_CLIENT = false;

//    private static final SelenideConfig defaults = new SelenideConfig();

    public static void  ReadConfig(){
        CheckParameter("WEB_BROWSER");
        Configuration.TIME_OUT = Long.parseLong(System.getProperty("timeout")==null ? "300000" : System.getProperty("timeout"));
        Configuration.PAGE_LOAD_TIME = Integer.parseInt(System.getProperty("pageLoadTimeout")==null ? "300000" : System.getProperty("pageLoadTimeout"));
        Configuration.DEFAULT_MAXIMUM = Boolean.parseBoolean(System.getProperty("startMaximized")==null ? "true" : System.getProperty("startMaximized"));
        Configuration.IS_HEADLESS = Boolean.parseBoolean(System.getProperty("headless")==null ? "false" : System.getProperty("headless"));
        Configuration.PATH_POSTMAN = getFilePath(System.getProperty("user.dir")+"/src/test/resources/postman-test");
        Configuration.RUN_UNSAFE_OKHTTP_CLIENT = Boolean.parseBoolean(System.getProperty("run_unsafe_okhttp_client")==null ? "false" : System.getProperty("run_unsafe_okhttp_client"));
//        TIME_OUT = defaults.timeout();
    }
    public static void CheckParameter(String para){
        if(System.getenv(para)!=null){
            Configuration.WEB_BROWSER =   System.getenv("WEB_BROWSER");
        }else
        {
            Configuration.WEB_BROWSER=  "CHROME";
        }


    }
    public static String getFilePath(String filePath){
        return filePath.replace("\\", File.separator).replace("/", File.separator);
    }

}
