package util;


import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.File;
import java.io.IOException;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class ReportSetup implements ITestListener {
    ExtentReports extentReports = new ExtentReports();
    public static ThreadLocal<ExtentTest> threadLocal = new ThreadLocal<>();


    /**
     * Invoked each time before a test will be invoked. The <code>ITestResult</code> is only partially
     * filled with the references to class, method, start millis and status.
     *
     * @param result the partially filled <code>ITestResult</code>
     * @see ITestResult#STARTED
     */
    public void onTestStart(ITestResult result) {
        ExtentTest extentTest= extentReports.createTest("Test name : "+ result.getMethod().getMethodName());
        threadLocal.set(extentTest);

        // not implemented
    }



    public void onStart(ITestContext context) {

        String source = System.getProperty("user.dir")+"/reports/current";
        File srcDir = new File(source);

        String destination = System.getProperty("user.dir")+"/reports/backup";
        File destDir = new File(destination);
        try {
            FileUtils.copyDirectory(srcDir, destDir, true);
           FileUtils.deleteDirectory(srcDir);

        } catch (IOException e) {
            e.printStackTrace();
        }
      String filename = ExtentReportManager.getReportNameWithTimeStamp();
      String fullpath = System.getProperty("user.dir")+"/reports/current/"+filename;
      System.out.println(fullpath);
       extentReports = ExtentReportManager.createReport(fullpath,"API_Report");
    }

    /**
     * Invoked after all the test methods belonging to the classes inside the &lt;test&gt; tag have
     * run and all their Configuration methods have been called.
     *
     * @param context The test context
     */
    public void onFinish(ITestContext context) {
        if(extentReports!=null)
            extentReports.flush();
        // not implemented
    }

}
