package chartlab.PowerMChartMain.Main;

import android.app.Activity;
import android.widget.Toast;

public class BackPressCloseHandler
{
    private long m_lBackKeyPressedTime = 0;
    private Toast m_cToast;
    private Activity m_activityMain;

    public BackPressCloseHandler(Activity context) {
        this.m_activityMain = context;
    }

    public void onBackPressed()
    {
        if (System.currentTimeMillis() > m_lBackKeyPressedTime + 2000)
        {
            m_lBackKeyPressedTime = System.currentTimeMillis();
            m_cToast = Toast.makeText(m_activityMain, "\'뒤로\'버튼을 한번 더 누르시면 종료됩니다.", Toast.LENGTH_SHORT);
            m_cToast.show();
            return;
        }
        if (System.currentTimeMillis() <= m_lBackKeyPressedTime + 2000)
        {
            m_activityMain.finish();
            m_cToast.cancel();
        }
    }
}
