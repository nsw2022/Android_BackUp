package chartlab.PowerMChartMain.Main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
//import android.support.v4.app.FragmentActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;

import androidx.fragment.app.FragmentActivity;

import chartlab.PowerMChartApp.Config.ConfigFormGraphActivity;
//import chartlab.PowerMChart.Config.R;
import chartlab.PowerMChartEngine.KernelCore.GlobalEnums;
import chartlab.PowerMChartEngine.KernelCore.GlobalProperty.CProperty_BASE;

public class BaseSample1FragmentActivity extends FragmentActivity implements View.OnClickListener
{
    final  static  int DELAY                        = 5000;
    final  static  int PLAY                         = 1;
    final  static  int STOP                         = 0;

    private int m_nHandlerStatus = -1;

    public static Context m_contBaseSample1 = null;

    private String m_strFrameType = "";
    private int m_nMixGraphType = -1;
    private int m_nGraphIndex = 0;

    private ImageButton m_btnBack = null;
    private ImageButton m_btnConfig = null;

    private FormChartFragmentApp m_fragFormChart = null;
    private FormGraphFragmentApp m_fragFormGraph = null;

    private BackPressCloseHandler m_handlerBackPressClose = null;

    private static boolean m_bActivityAlive = false;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragmentactivity_powergraphics_sample1);

        InitControls();
        InitBaseSample1();
    }

    private void InitBaseSample1()
    {
        m_nHandlerStatus = STOP;

        m_handlerBackPressClose = new BackPressCloseHandler(this);
        m_contBaseSample1 = this;

        if (getIntent() != null)
        {
            m_strFrameType = getIntent().getStringExtra(IntroActivity.FrameTypeKey);
            m_nMixGraphType = getIntent().getIntExtra("MixGraphType",-1);
        }

        if(m_nMixGraphType == 0) //- Sample1
        {
            m_fragFormChart = new FormChartFragmentApp();
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.Frame_Fragment_Chart, m_fragFormChart)
                    .commit();

            m_fragFormChart.SetAttachFormChart(true);
            m_fragFormChart.SetFormChartType("0");

            m_fragFormGraph = new FormGraphFragmentApp();
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.Frame_Fragment_Graph, m_fragFormGraph)
                    .commit();

            m_fragFormGraph.SetAttachFormGraph(true);
            m_fragFormGraph.SetFormGraphType("4");
            m_fragFormGraph.SetMixGraphType(-1);

            //3초 대기 핸들러
            if(m_nHandlerStatus == STOP)
            {
                m_nHandlerStatus = PLAY;
                handler.sendEmptyMessageDelayed(0, DELAY);
            }
        }
    }

    @Override
    public void onDestroy()
    {
        if(m_nHandlerStatus != STOP)
        {
            m_nHandlerStatus = STOP;
            handler.removeMessages(0);
        }

        super.onDestroy();
    }

    private void InitControls()
    {
        m_btnBack = (ImageButton) findViewById(R.id.Btn_TitleBar_Back);
        m_btnBack.setOnClickListener(this);

        m_btnConfig = (ImageButton) findViewById(R.id.Btn_TitleBar_Config);
        m_btnConfig.setOnClickListener(this);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev)
    {   return super.dispatchTouchEvent(ev);    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {   super.onActivityResult(requestCode, resultCode, data);  }

    public CProperty_BASE OnGetChartGlobalPropertyEvent(boolean bReloading, boolean bWorkspace)
    {   return null;    }

    @Override
    public void onBackPressed()
    {   m_handlerBackPressClose.onBackPressed();    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop()
    {
        m_bActivityAlive = false;
        super.onStop();
    }

    @Override
    protected void onStart()
    {
        m_bActivityAlive = true;
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public  static boolean IsActivieAlive()
    {   return m_bActivityAlive;    }

    @Override
    public void onClick(View v)
    {
        int i = v.getId();
        if (i == R.id.Btn_TitleBar_Back)
        {
            finish();
        }
        else if (i == R.id.Btn_TitleBar_Config)
        {
            Intent intent = new Intent(m_contBaseSample1, ConfigFormGraphActivity.class);
            startActivity(intent);
        }
    }

    Handler handler = new Handler()
    {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what == 0)
            {
                m_nGraphIndex++;
                if(m_nGraphIndex <= 6)
                {
                    m_fragFormGraph.SetMixGraphType(-1);
                    m_fragFormGraph.SetFormGraphType(m_nGraphIndex + 4 + "");
                }
                else if(m_nGraphIndex <= 23)
                {
                    m_fragFormGraph.SetMixGraphType(-1);
                    String strPriceName = "Graph-" + GlobalEnums.GE_OBJPRICETYPENAMES[16+m_nGraphIndex-7];
                    m_fragFormGraph.SetFormGraphType(strPriceName);
                }
                else if(m_nGraphIndex <= 29)
                {
                    m_fragFormGraph.SetMixGraphType(m_nGraphIndex-24);
                    m_fragFormGraph.SetFormGraphType("");
                }
                else
                {
                    //중지할려면
                    //m_nHandlerStatus = STOP;
                    //handler.removeMessages(0);
                    //return;

                    //계속 돌릴려면
                    m_nGraphIndex = -1;
                }

                m_fragFormGraph.InitDataGraphCtrl();

                if(m_nHandlerStatus == PLAY)
                    handler.sendEmptyMessageDelayed(0, DELAY);
            }
        }
    };
}
