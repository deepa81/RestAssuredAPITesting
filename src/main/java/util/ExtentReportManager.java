package util;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import freemarker.core.ReturnInstruction;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Stack;

public class ExtentReportManager {

    public static  ExtentReports extentReports;
    public static ExtentReports createReport(String filename, String reportname){
        ExtentSparkReporter reporter = new ExtentSparkReporter(filename);
        reporter.config().setReportName(reportname);
        reporter.config().setDocumentTitle("API Automation Report 123");
        reporter.config().setTheme(Theme.STANDARD);
        reporter.config().setEncoding("UTF-8");
         extentReports=new ExtentReports();
        extentReports.attachReporter(reporter);
        return extentReports;
    }

    public static String getReportNameWithTimeStamp(){
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy_MM_dd_hh_mm_ss");
        LocalDateTime localDateTime=LocalDateTime.now();
        String formatedTime = dateTimeFormatter.format(localDateTime);
        String fileName ="API_Report"+formatedTime+".html";
        return fileName;
    }
    public static void logResultDetails(boolean status , String log){
        if(status)
             ReportSetup.threadLocal.get().pass(MarkupHelper.createLabel(log, ExtentColor.GREEN));
        else
            ReportSetup.threadLocal.get().fail(MarkupHelper.createLabel(log, ExtentColor.RED));
    }

    public static void logInfoDetails(String log){
        ReportSetup.threadLocal.get().info(MarkupHelper.createLabel(log, ExtentColor.GREY));
    }
    public static void logWarningDetails(String log){
        ReportSetup.threadLocal.get().warning(MarkupHelper.createLabel(log, ExtentColor.YELLOW));
    }

}
