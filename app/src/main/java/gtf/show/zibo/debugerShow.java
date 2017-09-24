package gtf.show.zibo;
import android.app.*;
import android.os.*;
import android.webkit.*;
import java.util.*;
import android.content.*;
import android.net.*;
import android.view.*;
import android.content.pm.*;
import android.widget.*;
import android.text.*;

public class debugerShow extends Activity
{


	boolean debuger = true;
    String title ="showLove Debug("+debuger+") Report";
	//boolean debugAsk = true;
   


	@Override
    protected void onCreate(Bundle savedInstanceState)
    {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.debuger);
		//获取Preferences
		SharedPreferences settingsRead = getSharedPreferences("data", 0);
//取出数据
		final int startTime =Integer.parseInt(settingsRead.getString("startTime", "0")) + 1;
		String startTime1 = startTime + "";
		boolean debugerAsk =Boolean.parseBoolean(settingsRead.getString("debugerAsk", "True"));
//打开数据库
		SharedPreferences settings = getSharedPreferences("data", 0);
//处于编辑状态
		SharedPreferences.Editor editor = settings.edit();
//存放数据
		editor.putString("startTime", startTime1);
		//editor.putString("debugerAsk","true");
//完成提交
		editor.commit();


		if (debuger && debugerAsk)
		{

//显示是否以debuger身份登录
			AlertDialog.Builder debugerAskDialog = new AlertDialog.Builder(this);
			debugerAskDialog.setCancelable(false);
			debugerAskDialog.setTitle("debug");
			debugerAskDialog.setMessage("是否以debuger身份登录?");
			debugerAskDialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which)
					{
						debuger = false;
						//	debug提示
						Toast Toast1 = Toast.makeText(debugerShow.this, "debug:" + debuger, Toast.LENGTH_SHORT);
						Toast1.show();
						//显式intent跳转secret的activity
						Intent intent = new Intent(debugerShow.this, secret.class);
						startActivity(intent);
						String debugContent = "The device info:  "+getHandSetInfo()+"    It's the " + startTime + " time to start."+"    done.";
						userRun(debugContent);
					}
				});
			debugerAskDialog.setPositiveButton("确定",  new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which)
					{
						debuger = true;
						//	debug提示
						String debugContent = "The device info:  "+getHandSetInfo()+"    It's the " + startTime + " time to start."+"    done.";
						Toast Toast1 = Toast.makeText(debugerShow.this, "debug:" + debuger, Toast.LENGTH_SHORT);
						Toast1.show();
						Toast Toast2 = Toast.makeText(debugerShow.this,"这是第" + startTime + "次打开" , Toast.LENGTH_SHORT);
						Toast2.show();
						debugRun(debugContent,debugContent);
						//显式intent跳转secret的activity
						Intent intent = new Intent(debugerShow.this, secret.class);
						startActivity(intent);
					}
				});
			debugerAskDialog.setNeutralButton("始终",  new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which)
					{
						SharedPreferences settings = getSharedPreferences("data", 0);
						SharedPreferences.Editor editor = settings.edit();
						editor.putString("debugerAsk", "false");
						editor.commit();
						debuger = true;
						//	debug提示
						Toast Toast1 = Toast.makeText(debugerShow.this, "debug:" + debuger, Toast.LENGTH_SHORT);
						Toast1.show();
						Toast Toast2 = Toast.makeText(debugerShow.this, "这是第" + startTime + "次打开", Toast.LENGTH_SHORT);
						Toast2.show();
						String debugContent = "The device info:  "+getHandSetInfo()+"    It's the " + startTime + " time to start."+"   done.";
						debugRun(debugContent,debugContent);
						//显式intent跳转secret的activity
						Intent intent = new Intent(debugerShow.this, secret.class);
						startActivity(intent);

					}
				});
			//debugerAskDialog.build();
			debugerAskDialog.show();
		}
		else
		{
			//	debug提示
			Toast Toast1 = Toast.makeText(debugerShow.this, "debug:" + debuger, Toast.LENGTH_SHORT);
			Toast1.show();
		}


	}
	private void userRun(String text){
		sendEMail(text);
	}
	
	
	private void debugRun(String text,String ToastTest){
		
		//	debug提示
		Toast Toast1 = Toast.makeText(debugerShow.this, "debug:"+"Sending Email:"+title+ToastTest, Toast.LENGTH_LONG);
		Toast1.show();
		sendEMail(text);
	}

	private void sendEMail(String text) {
        MailManager.getInstance().sendMail(title, text);
       // String path = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "fade.amr";
      //  MailManager.getInstance().sendMailWithFile("title", "content", path);
    }
	
	//获取设备信息
	private String getHandSetInfo(){
		String handSetInfo=
			"Phone model:" + android.os.Build.MODEL + 
			",SDK Version:" + android.os.Build.VERSION.SDK + 
			",System Version:" + android.os.Build.VERSION.RELEASE+
			",Software Version:"+getAppVersionName(debugerShow.this); 
		return handSetInfo;

	}
	//获取当前版本号
	private  String getAppVersionName(Context context) {
		String versionName = "";
		try {
			PackageManager packageManager = context.getPackageManager();
			PackageInfo packageInfo = packageManager.getPackageInfo("cn.testgethandsetinfo", 0);
			versionName = packageInfo.versionName;
			if (TextUtils.isEmpty(versionName)) {
				return "";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return versionName;
	}
	
	
}
