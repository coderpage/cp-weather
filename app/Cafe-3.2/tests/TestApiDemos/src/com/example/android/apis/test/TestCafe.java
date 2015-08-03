package com.example.android.apis.test;

import android.view.KeyEvent;
import android.widget.Button;

import com.baidu.cafe.CafeTestCase;
import com.baidu.cafe.local.Log;

/**
 * @author luxiaoyu01@baidu.com
 * @date 2012-6-27
 * @version
 * @todo
 */
public class TestCafe extends CafeTestCase {
    private static final String LAUNCHER_ACTIVITY_FULL_CLASSNAME = "com.example.android.apis.ApiDemos";
    private static Class<?>     launcherActivityClass;
    private static final String TARGET_PACKAGE                   = "com.example.android.apis";
    static {
        try {
            launcherActivityClass = Class.forName(LAUNCHER_ACTIVITY_FULL_CLASSNAME);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public TestCafe() {
        super(TARGET_PACKAGE, launcherActivityClass);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * api����չʾ��������ʵ�Ĳ���������  
     * NOTICE: ����caseǰ��Ҫ����cafe_setup.bat��cafe_setup.sh
     */
    public void test_sample() {
        //���HOME��
        remote.pressKey(KeyEvent.KEYCODE_HOME);
        //���������
        remote.pressKey(KeyEvent.KEYCODE_POWER);
        //���������
        remote.pressKey(KeyEvent.KEYCODE_VOLUME_DOWN);
        //����˵���
        remote.pressKey(KeyEvent.KEYCODE_MENU);
        //������ؼ�
        remote.pressKey(KeyEvent.KEYCODE_BACK);
        //���������
        remote.pressKey(KeyEvent.KEYCODE_SEARCH);
        //ж��sd��
        remote.unmount();
        //��װsd��
        remote.mount();
        //ɱ��̨����
        remote.killBackgroundProcesses("com.android.launcher");
        //��������Ӧ��
        remote.launchActivity("com.android.launcher");
        //�����ַ�
        local.enterText(0, "Cafe");//0��ʾ��һ���ı���
        //����
        local.clickLongInList(1);
        local.clickLongOnText("Cafe");
        local.clickLongOnScreen(100, 100);
        //������֣��κοؼ��ϵ����֣�
        local.clickOnText("Cafe");
        //��ȡ�ؼ�
        local.getCurrentViews(Button.class);
        //��ȡlogcat
        String[] logs = remote.getLog();
        //��ȡ����
        remote.getBatteryLevel();
        //��ȡ����
        remote.getScreenBrightness();
        //��(0,0)�϶���(100,100)
        local.drag(0, 100, 0, 100, 10);
        //�Ŵ���С
        /**
         * zoom screen
         * 
         * @param start
         *            the start position e.g. new int[]{0,0,1,2}; means two
         *            pointers start at {0,0} and {1,2}
         * @param end
         *            the end position e.g. new int[]{100,110,200,220}; means
         *            two pointers end at {100,110} and {200,220}
         */
        local.zoom(new int[] { 0, 0, 1, 2 }, new int[] { 100, 110, 200, 220 });
        //����̵�������ҽ���ro.debuggable=1 and ro.secure=0ʱ�����ܿ���ʹ�á�
        remote.clickViewByText("ͨѶ¼");
        if (remote.waitForActivity("com.android.contacts.DialtactsContactsEntryActivity", 5000)) {
            Log.i("at com.android.contacts.DialtactsContactsEntryActivity");
        }
    }
}
