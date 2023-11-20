package chartlab.PowerMChartMain.Main;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
//import android.support.v4.app.FragmentActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.fragment.app.FragmentActivity;

import chartlab.PowerMChartApp.ToolBar.TaskBarLinearLayout;
import chartlab.PowerMChartApp.ToolBar.ToolBarLinearLayout;
import chartlab.PowerMChartApp.Dialog.Chart.FrameMultiTypeDlg;

public class BaseFragmentActivity extends FragmentActivity
{
    public static Context m_contBase = null;

    private String m_strFrameType = "";
    private String m_strFrameSubType = "";
    private int m_nMixGraphType = -1;

    private MultiFragmentApp m_fragMulti = null;
    private CompareFragmentApp m_fragCompare = null;
    private FormChartFragmentApp m_fragFormChart = null;
    private FormGraphFragmentApp m_fragFormGraph = null;

    private BackPressCloseHandler m_handlerBackPressClose = null;

    private boolean m_bTaskBarVisible = false;
    private boolean m_bToolBarVisible = false;
    private TaskBarLinearLayout m_llTaskBar = null;
    private ToolBarLinearLayout m_llToolBar = null;
//    private ImageView m_imgVwTaskBar = null;
    private ImageButton m_btnTaskBar = null;
    private ImageView m_imgVwToolBar = null;

    private FrameMultiTypeDlg m_dlgFrameType = null;

    private static boolean m_bActivityAlive = false;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragmentactivity_base);

        m_handlerBackPressClose = new BackPressCloseHandler(this);
        m_contBase = this;

        if (getIntent() != null)
        {
            m_strFrameType = getIntent().getStringExtra(IntroActivity.FrameTypeKey);
            m_strFrameSubType = getIntent().getStringExtra("GraphicsType");
            m_nMixGraphType = getIntent().getIntExtra("MixGraphType",-1);
        }

        if (m_strFrameType.equals(IntroActivity.ChartTypeMulti))
        {
            m_fragMulti = new MultiFragmentApp();
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.Frame_Fragment, m_fragMulti)
                    .commit();
        }
        else if (m_strFrameType.equals(IntroActivity.ChartTypeCompare))
        {
            m_fragCompare = new CompareFragmentApp();
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.Frame_Fragment, m_fragCompare)
                    .commit();
        }
        else if (m_strFrameType.equals(IntroActivity.ChartTypeForm))
        {
            m_fragFormChart = new FormChartFragmentApp();
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.Frame_Fragment, m_fragFormChart)
                    .commit();

            m_fragFormChart.SetFormChartType(m_strFrameSubType);
        }
        else if (m_strFrameType.equals(IntroActivity.GraphTypeForm))
        {
            m_fragFormGraph = new FormGraphFragmentApp();
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.Frame_Fragment, m_fragFormGraph)
                    .commit();

            m_fragFormGraph.SetFormGraphType(m_strFrameSubType);
            m_fragFormGraph.SetMixGraphType(m_nMixGraphType);
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev)
    {
        if(ev.getAction() == MotionEvent.ACTION_DOWN)
        {
            if (m_strFrameType.equals(IntroActivity.ChartTypeMulti) && m_fragMulti != null) {
                if (m_fragMulti.dispatchTouchEvent(ev) == false) return false;
            }
            else if (m_strFrameType.equals(IntroActivity.ChartTypeCompare) && m_fragCompare != null)
            {
                if (m_fragCompare.dispatchTouchEvent(ev) == false) return false;
            }
        }

        return super.dispatchTouchEvent(ev);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (m_strFrameType.equals(IntroActivity.ChartTypeMulti) && m_fragMulti != null)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                m_fragMulti.onActivityResult(requestCode, resultCode, data);
            }
        else if (m_strFrameType.equals(IntroActivity.ChartTypeCompare) && m_fragCompare != null)
            m_fragCompare.onActivityResult(requestCode, resultCode, data);

        //else if (m_strFrameType.equals(IntroActivity.ChartTypeForm) && m_fragFormChart != null) {
        //    m_fragFormChart.onActivityResult(requestCode, resultCode, data);
        //}
        //else if (m_strFrameType.equals(IntroActivity.GraphTypeForm) && m_fragFormGraph != null) {
        //    m_fragFormGraph.onActivityResult(requestCode, resultCode, data);
        //}

        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onBackPressed()
    {
		//super.onBackPressed();
       if (m_strFrameType.equals(IntroActivity.ChartTypeMulti) && m_fragMulti != null)
       {
            m_bTaskBarVisible = m_fragMulti.getLeftTaskBarVisible();
//            m_imgVwTaskBar = m_fragMulti.getImageViewTaskBarLeft();
            m_llTaskBar = m_fragMulti.getPowerMChartLeftTaskBar();
            m_bToolBarVisible = m_fragMulti.getRightTaskBarVisible();
            m_imgVwToolBar = m_fragMulti.getImageViewTaskBarRight();
            m_llToolBar = m_fragMulti.getPowerMChartRightTaskBar();
            m_dlgFrameType = m_fragMulti.GetFrameTypeDlg();
            if (m_bTaskBarVisible)
            {
                m_fragMulti.setLeftTaskBarVisible(false);
                m_llTaskBar.setVisibility(View.GONE);
//                m_imgVwTaskBar.setBackgroundResource(R.drawable.button_default_toggle_off_selector);
            }
            else if (m_bToolBarVisible)
            {
                m_fragMulti.setRightTaskBarVisible(false);
                m_llToolBar.setVisibility(View.GONE);
                m_imgVwToolBar.setBackgroundResource(R.drawable.button_default_toggle_off_selector);
            }
            else if(m_dlgFrameType != null && m_dlgFrameType.isShowing())
            {
                m_dlgFrameType.dismiss();
            }
            else
            {
                m_handlerBackPressClose.onBackPressed();
            }
        }
        else if (m_strFrameType.equals(IntroActivity.ChartTypeCompare) && m_fragCompare != null)
        {
            m_handlerBackPressClose.onBackPressed();            
        }
        else if (m_strFrameType.equals(IntroActivity.ChartTypeForm) && m_fragFormChart != null)
        {
            m_handlerBackPressClose.onBackPressed();
        }
        else if (m_strFrameType.equals(IntroActivity.GraphTypeForm) && m_fragFormGraph != null)
        {
           m_handlerBackPressClose.onBackPressed();
        }
    }

    @Override
    protected void onPause()
    {   super.onPause();    }

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
    protected void onResume()
    {   super.onResume();   }

    public  static boolean IsActivieAlive()
    {   return m_bActivityAlive;    }
}
