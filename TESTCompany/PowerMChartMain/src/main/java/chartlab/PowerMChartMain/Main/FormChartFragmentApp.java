package chartlab.PowerMChartMain.Main;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcel;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.IOException;
import java.util.ArrayList;

import chartlab.PowerMChartApp.Util.FragmentAppMain;
import chartlab.PowerMChartMain.DataWnd.DataHandler;
import chartlab.PowerMChartApp.Config.ConfigObjParam;
import chartlab.PowerMChartApp.Config.ConfigSingleFragmentActivity;
import chartlab.PowerMChartApp.Util.BaseApplication;
import chartlab.PowerMChartApp.FrameWnd.ChartView;
import chartlab.PowerMChartApp.FrameWnd.ChartCtrl;
import chartlab.PowerMChartApp.Widget.UtilControl.MessageBox;
import chartlab.PowerMChartApp.Dialog.Chart.IndicateAddDlg;
import chartlab.PowerMChartEngine.Util.FrameSkins;
import chartlab.PowerMChartEngine.KernelCore.GlobalDefines;
import chartlab.PowerMChartEngine.KernelCore.GlobalEnums.*;
import chartlab.PowerMChartEngine.KernelCore.GlobalProperty;
import chartlab.PowerMChartEngine.KernelCore.GlobalStructs;
import chartlab.PowerMChartEngine.KernelCore.GlobalStructs.ST_PACKET_COREDATA;
import chartlab.PowerMChartEngine.KernelCore.GlobalStructs.ST_COMM_REQUEST;
import chartlab.PowerMChartEngine.KernelCore.GlobalUtils;
import chartlab.PowerMChartEngine.Util.ColorNames;

public class FormChartFragmentApp extends FragmentAppMain implements OnClickListener
{
    private final int GD_CONFIG_RESULT_CODE = 0;
    final  static  int DELAY                        = 3000;
    final  static  int MID_DELAY                   = 1500;
    final  static  int SPEED_DELAY1                = 500;
    final  static  int SPEED_DELAY2                = 100;
    final  static  int PLAY                         = 1;
    final  static  int MID_PLAY                     = 2;
    final  static  int SPEED_PLAY1                 = 3;
    final  static  int SPEED_PLAY2                 = 4;
    final  static  int STOP                         = 0;

    private int m_nHandlerStatus = 0;
    private int m_nTickPosIndex;
    private double m_dLoginToday;

    private ImageButton m_btnBack;
    private ImageButton m_btnConfig;
    private ImageButton m_btnIndicateAdd;

    //파일경로 관련 변수
    private String m_strPathChtSys = "";
    private String m_strPathChtUser = "";
    private String m_strChtTRNumber = "";

    private ChartCtrl m_cChartCtrl = null;

    //데이터 관련 변수
    private String m_strLinkCode = "";
    private String m_strLinkCodeName = "";
    private ST_COMM_REQUEST m_stNowCommRqInfo = null;
    private DataHandler m_cDataHandler = null;

    private MessageBox m_cMessageBoxHandler = null;

    //ChartView
    private ChartView m_cChartView = null;

    // 화면 모드 관련 변수
    private boolean m_bIsLandScape = false;

    private boolean m_bIsAttachFormChart = false;
    private String m_strChartType = "";
    private int m_nMixFormChartType = -1;

    public Context m_contFormChart = null;
    private View m_vwFormChart = null;

    private int m_nDateTimeIndex = -1;
    private int m_nOpenIndex = -1;
    private int m_nHighIndex = -1;
    private int m_nLowIndex = -1;
    private int m_nCloseIndex = -1;
    private int m_nVolumeIndex = -1;

    //private TextView m_tvTitle = null;
    private IndicateAddDlg m_indicateAddDlg;

    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        m_contFormChart = getActivity();

        assert m_contFormChart != null;
        m_bIsLandScape = BaseApplication.GetOrientationLandScape(m_contFormChart);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        m_vwFormChart = inflater.inflate(R.layout.fragment_formchart, container, false);
        return m_vwFormChart;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        InitFormChartFragment();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);

        InitFormChartFragment();
    }

    private void InitFormChartFragment()
    {
        m_nDateTimeIndex = ENPacketDataType.GE_PACKET_CORE_DATETIME.GetIndexValue();
        m_nOpenIndex = ENPacketDataType.GE_PACKET_CORE_OPEN.GetIndexValue();
        m_nHighIndex = ENPacketDataType.GE_PACKET_CORE_HIGH.GetIndexValue();
        m_nLowIndex = ENPacketDataType.GE_PACKET_CORE_LOW.GetIndexValue();
        m_nCloseIndex = ENPacketDataType.GE_PACKET_CORE_CLOSE.GetIndexValue();
        m_nVolumeIndex = ENPacketDataType.GE_PACKET_CORE_VOLUME.GetIndexValue();

        InitFormChartCtrl();
        InitDataHandler();
        InitControls(); //버튼 셋팅

        m_indicateAddDlg = new IndicateAddDlg(m_contFormChart, m_cChartCtrl);

        this.SetFragmentMain(new InterfaceFragmentMainEvent() {

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
            }

            @Override
            public void OnGetChartLinkEvent(ENChartOCXEventType enOCXEvent, int nID, GlobalStructs.ST_COMM_REQUEST stCommRequest, int nValue,String strValue)
            {
                int nRQType = 0;
                int nViewSize = -1;
                int	nTotCount = -1;

                switch(enOCXEvent)
                {
                    case GE_OCX_EVENT_REQUEST:
                        m_cDataHandler.RequestBaseData(nID, stCommRequest.strCode, stCommRequest.strName,
                                stCommRequest.dStartDT[stCommRequest.nPeriod],stCommRequest.dEndDT[stCommRequest.nPeriod],
                                stCommRequest.nTotCount[stCommRequest.nPeriod],stCommRequest.nMarket, stCommRequest.nPeriod,
                                stCommRequest.nPrdValue[stCommRequest.nPeriod], stCommRequest.nRealInfo,
                                stCommRequest.nMarketGubun,ENRequestQueryType.GE_REQUEST_QUERY_ALL.GetIndexValue());
                        break;
                    case GE_OCX_EVENT_REQUEST_CODE:
                        break;
                    case GE_OCX_EVENT_CHANGE_CODEPERIOD:
                        break;
                    case GE_OCX_EVENT_REQUEST_NO_REAL:
                        break;
                    case GE_OCX_EVENT_SELECT_FRAME:
                        break;
                    case GE_OCX_EVENT_REQUEST_HEAD:
                        break;
                    case GE_OCX_EVENT_CHART_INIT:
                        break;
                    case GE_OCX_EVENT_REQUEST_CANCEL:
                        //nValue 값이 100 이면 "All"
                        //if(nValue == 100) m_cDataHandler.CancelRequest(nID,"ALL");
                        //else m_cDataHandler.CancelRequest(nID,nValue + "");
                        break;
                    case GE_OCX_EVENT_OVERLAP_DELETE:
                        break;
                    case GE_OCX_EVENT_QUERY_RECEIVE:
                        break;
                    case GE_OCX_EVENT_QUERY_COMPLETE:
                        break;
                    case GE_OCX_EVENT_CHANGE_COUNT:
                        if(nID == m_cChartCtrl.GetCurrentChartID())
                        {
                            nViewSize = m_cChartCtrl.GetCurrentChartView().GetPacketViewSize();
                            if(m_stNowCommRqInfo.nPeriod >= 0)
                                m_stNowCommRqInfo.nViewCount[m_stNowCommRqInfo.nPeriod] = nViewSize;

                            nTotCount = m_cChartCtrl.GetCurrentChartView().GetPacketTotSize();
                            //m_cMessageBoxHandler.SetMessageBoxStatus("View Size - " + nViewSize, 1000, Gravity.CENTER, 0, 300).ShowMessage();
                            m_cMessageBoxHandler.SetMessageBoxStatus( nViewSize+ "/" + nTotCount, 500, Gravity.CENTER, 0, 300).ShowMessage();

						/*if(nTotCount > 0)
						{
							strViewSize = String.format("%d",nTotCount);
							m_stNowCommRqInfo.nTotCount[m_stNowCommRqInfo.m_nPeriod] = nTotCount;
						}	*/
                        }
                        break;
                    default:break;
                }
            }

            @Override
            public boolean OnDataCrossBtnStatusEvent() {
                return false;
            }

            @Override
            public void OnChangeBtmBarStatusEvent() {
            }

            @Override
            public void OnTaskBarHideEvent() { }

            @Override
            public void OnToolBarHideEvent() { }


            @Override
            public void OnToggleToolBarEvent(int nToolType, int nIndex, boolean bCheck) { }

            @Override
            public void OnANALSelPopupDlgEvent(ENANALToolType enANALToolType, Point ptPos) { }

            @Override
            public GlobalProperty.CProperty_BASE OnGetChartGlobalPropertyEvent(boolean bReloading, boolean bWorkspace) {
                return m_cChartCtrl.GetCurrentChartView().OnGetChartGlobalPropertyEvent(bReloading, bWorkspace);
            }

            @Override
            public GlobalStructs.ST_ANALPOINT_INFO OnGetSelANALInfoEvent() {
                return null;
            }

            @Override
            public void OnSetSelANALInfoEvent(GlobalStructs.ST_ANALPOINT_INFO stANALToolInfo) {  }

            @Override
            public int OnFindXDateIndexEvent(double dXDateTime, int nStart, int nIfNoEqualNearIndex) {
                return 0;
            }
        });

        m_vwFormChart.findViewById(R.id.Relative_TitleBar).setBackgroundColor(FrameSkins.m_crNAVIBack);
        m_cMessageBoxHandler = new MessageBox(m_contFormChart, m_vwFormChart);

        m_dLoginToday = Double.parseDouble(GlobalUtils.GetToday(false));

        try {
            InitDataChartCtrl();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /*
     *********************
     **
     ** Init Functions
     **
     *********************/
    private void InitFormChartCtrl()
    {
        m_cChartCtrl = new ChartCtrl();
        m_cChartCtrl.InitChartCtrl(ENFragmentFrameType.GE_FRAGMENT_FRAME_FORM_CHART,this, m_contFormChart);

        String strPathChtUserRoot = GlobalUtils.GetStorageDirPath() + GlobalDefines.GD_ROOT_DIRECTORY;
        m_strPathChtSys = GlobalUtils.GetStorageDirPath()+ GlobalDefines.GD_CHARTINFO_DIRECTORY;
        m_strPathChtUser = GlobalUtils.GetStorageDirPath() + GlobalDefines.GD_CHARTINFO_USER_DIRECTORY;
        m_strChtTRNumber = GlobalDefines.GD_CHART_TR_FORM;

        m_cChartCtrl.SetPathChtUserRoot(strPathChtUserRoot);
        m_cChartCtrl.SetPathChtSys(m_strPathChtSys);
        m_cChartCtrl.SetPathChtUser(m_strPathChtUser);
        m_cChartCtrl.SetChartTRNumber(m_strChtTRNumber);

        m_cChartCtrl.CreateDirectory();

        m_cChartView = (ChartView) m_vwFormChart.findViewById(R.id.ChartView_FormChart);
        m_cChartCtrl.AddChartView(m_cChartView, true);

        m_nHandlerStatus = STOP;
        m_nTickPosIndex = 0;
        m_dLoginToday = GlobalDefines.GD_NAVALUE_DOUBLE;
        m_nMixFormChartType = -1;

        m_stNowCommRqInfo = new GlobalStructs.ST_COMM_REQUEST();
    }

    private void InitControls()
    {
        m_btnBack = (ImageButton) m_vwFormChart.findViewById(R.id.Btn_TitleBar_Back);
        m_btnBack.setOnClickListener(this);

        m_btnConfig = (ImageButton) m_vwFormChart.findViewById(R.id.Btn_TitleBar_Config);
        m_btnConfig.setOnClickListener(this);

        m_btnIndicateAdd = m_vwFormChart.findViewById(R.id.Btn_TitleBar_FullScreen);
        m_btnIndicateAdd.setOnClickListener(this);

        if (m_bIsAttachFormChart == true) {
            RelativeLayout relativeLayoutTitleBar = (RelativeLayout) m_vwFormChart.findViewById(R.id.Relative_TitleBar);
            relativeLayoutTitleBar.setVisibility(View.GONE);

            TextView textView = (TextView) m_vwFormChart.findViewById(R.id.Tv_OtherCtrl);
            textView.setVisibility(View.GONE);
        }
    }

    public void SetFormChartType(String strChartType)
    {       m_strChartType = strChartType;      }

    public void SetAttachFormChart(boolean bIsAttachFormChart)
    {       m_bIsAttachFormChart = bIsAttachFormChart;      }

    private void InitDataChartCtrl() throws IOException
    {
        m_nMixFormChartType = -1;
        int nGraphKind = Integer.parseInt(m_strChartType);
        m_nMixFormChartType = nGraphKind;
        switch (nGraphKind) {
            case 0:
                InitFormChartCandle();
                break;
            case 1:
                InitFormChartUSA_FM();
                break;
            case 2:
                InitFormChartOverlap();
                break;
            case 3:
                InitFormChartLine();
                break;
        }
    }

    private void InitDataHandler()
    {
        m_cDataHandler = new DataHandler(m_contFormChart, m_cChartCtrl, ENFragmentFrameType.GE_FRAGMENT_FRAME_FORM_CHART);
        //주의: 1000 -> 1초
		String strRealTimeDelay = GlobalUtils.GetPrivateProfileString(m_strPathChtUser,m_strPathChtSys, GlobalDefines.GD_CHARTINFO_MAINPROPERTY, "RealTimeDelay");

		m_cDataHandler.InitDataHandler(GlobalUtils.GetIntValue(strRealTimeDelay));
	}

    /*
     *********************
     **
     ** Button Event Functions
     **
     *********************/
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.Btn_TitleBar_FullScreen:
                String strObjName = "";
                int nObjTotCount = m_cChartView.GetChartKindObjCount(ENObjectKindType.GE_OBJECT_KIND_INDICA, false);
                for(int i = 0;i<nObjTotCount;i++)
                {
                    strObjName = m_cChartView.GetChartKindObjName(ENObjectKindType.GE_OBJECT_KIND_INDICA, i);
                    m_indicateAddDlg.ChangeIndicaCheck(strObjName, true);
                }

                if(m_cChartView.GetChartKindObjCount(ENObjectKindType.GE_OBJECT_KIND_VOLUME, false) == 1)
                    m_indicateAddDlg.ChangeIndicaCheck(GlobalDefines.GD_INDICA_VOLUME, true);

                m_indicateAddDlg.show();
                break;
            case R.id.Btn_TitleBar_Back:
                getActivity().finish();
                getActivity().overridePendingTransition(R.anim.fadein, R.anim.fadeout);
                break;
            case R.id.Btn_TitleBar_Config:
                DoConfigPropertyEvent(-1, "기본설정", ENObjectKindType.GE_OBJECT_KIND_NONE, ENFUNCToolType.GE_FUNCTOOL_CONFIG_FORM_CHART);

//                Intent intent = new Intent(m_contFormChart.getApplicationContext(), ConfigFormChartFragment.class);
//                startActivity(intent);
                break;
        }
    }

    public void DoConfigPropertyEvent(int nSelPos, String strDBName, ENObjectKindType enSelObjKind, ENFUNCToolType enSelToolType) //속성 설정 .Popup(주의:Selpos - 0(Y축),1(X축))
    {
        Intent intent = null;
        ConfigObjParam pConfigInfoObj = new ConfigObjParam(m_strPathChtSys, m_strPathChtUser, m_strChtTRNumber);

        //2016/06/02 - 최대/최소값
        ChartView cChartView = m_cChartCtrl.GetCurrentChartView();
        pConfigInfoObj.m_dPacketMaxValue = cChartView.GetPacketMaxValue(true);
        pConfigInfoObj.m_dPacketMinValue = cChartView.GetPacketMinValue(true);

        intent = new Intent(m_contFormChart, ConfigSingleFragmentActivity.class);
        pConfigInfoObj.m_strConfigType = ConfigObjParam.CONFIG_TYPE_SINGLE;
        pConfigInfoObj.m_enFuncToolType = enSelToolType;
        pConfigInfoObj.m_enChartSeledKind = enSelObjKind;
        pConfigInfoObj.m_strObjSelectName = strDBName;
        pConfigInfoObj.m_nChartSelectPos = nSelPos;

        intent.putExtra(ConfigObjParam.KEY_CONFIGINFOOBJ, pConfigInfoObj);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        //취소하고 돌아오는경우 Draw안됨
        //m_cChartCtrl.GetCurrentChartView().SetChartLoadingNum(1);
        startActivityForResult(intent, GD_CONFIG_RESULT_CODE);
    }

    /*
     *********************
     **
     ** SystemOverrid Functions
     **
     *********************/
    public void onBackPress() { }

    @Override
    public void onStart() {
        super.onStart();
   }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroyView()
    {
        if(m_cMessageBoxHandler != null && m_cMessageBoxHandler.IsShowing() == true)
            m_cMessageBoxHandler.RemoveAllMessage();

        if(m_nHandlerStatus != STOP)
        {
            m_nHandlerStatus = STOP;
            handler.removeMessages(0);
        }

        super.onDestroyView();
    }

    @Override
    public void onDestroy()
    {
        /*SerializeChart("", 0,-1,true);

        m_cDataHandler.CloseDataHandler();
        m_cDataHandler.DoStop();*/

        super.onDestroy();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig)
    {
        super.onConfigurationChanged(newConfig);

        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE)
        {
            // 기기가 가로로 회전할때 할 작업
        }
        else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT)
        {
            // 기기가 세로로 회전할때 할 작업
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Form Chart
    public void InitFormChartCandle() {
        if (m_cChartCtrl == null) return;

        m_btnIndicateAdd.setVisibility(View.VISIBLE);

        int nID = m_cChartCtrl.GetCurrentChartID();
        //프레임 유형 변경
        m_cChartCtrl.SetChartFrameType(ENChartFrameType.GE_CHART_FRAME_MINI_BASIC);
        /////////////////////////////////////////////////////////////////////////////////////////////////////////
        //데이터 직접 처리
        int nDataCount = TempData.GE_FORMCHART_005930_DAILY.length;
        ArrayList<ST_PACKET_COREDATA> cPacketList = new ArrayList<ST_PACKET_COREDATA>();

        ST_PACKET_COREDATA stPacketBasicDataP = null;

        int nFind = 0;
        int nNext = 0;
        int nIndex = 0;
        String strLine = "";
        String strSub = "";
        for(int i=nDataCount-1;i>=0;i--)
        {
            stPacketBasicDataP = ST_PACKET_COREDATA.AllocMemory();

            nNext = 0;
            nIndex = 0;

            strLine = TempData.GE_FORMCHART_005930_DAILY[i];
            nFind = strLine.indexOf(" ");
            while(nFind >= 0)
            {
                if(nIndex == 0)
                {
                    strSub = strLine.substring(0,nFind);
                }
                else
                {
                    nNext = strLine.indexOf(" ", nFind + 1);
                    if (nNext >= 0)
                        strSub = strLine.substring(nFind, nNext);
                    else
                        //strSub = strLine.substring(nFind + 1);
                        strSub = strLine.substring(nFind);
                    nFind = nNext + 1;
                }

                stPacketBasicDataP.dValueList[nIndex] = Double.parseDouble(strSub);
                nIndex++;

                if(nNext < 0) break;
            }

            cPacketList.add(stPacketBasicDataP);
        }

        m_cChartCtrl.ReceiveQuery(nID,"Candle","", 2, 0,cPacketList);

        //초기차트
        m_cChartCtrl.ChangeFUNCToolType(ENFUNCToolType.GE_FUNCTOOL_INIT_CREATE,1);

        m_cChartCtrl.SetMainFloatingPoint(nID,0);

        //가격차트 그라데이션 설정
        m_cChartCtrl.SetPriceGradation(nID, true);
        m_cChartCtrl.SetGradientDirectType(nID,ENGradationDirectType.GE_GRADATION_HCENTER_OUT);
        m_cChartCtrl.SetGradientPosType(nID, ENGradationPosType.GE_GRADATION_POS_END);
        //m_cChartCtrl.SetGradientPosColor(nID,0, Color.rgb(206,148,148));
        m_cChartCtrl.SetGradientPosColor(nID,1, Color.rgb(169,206,220));

        m_cChartCtrl.SetTitleDspType(nID, ENTitleDspType.GE_TITLE_DISPLAY_NAME_VALUE);
        m_cChartCtrl.SetTitleChgType(nID, ENTitleChgType.GE_TITLE_CHANGE_DATA);
        //Touch & Drag 수치조회
        //m_cChartCtrl.SetCrosshair(nID,3);

        m_cChartCtrl.RedrawCurrentChart();

        //3초 대기 핸들러
        if(m_nHandlerStatus == STOP)
        {
            m_nHandlerStatus = PLAY;
            handler.sendEmptyMessageDelayed(0, DELAY);
        }
    }

    public void InitFormChartUSA_FM() throws IOException {
        if (m_cChartCtrl == null) return;

        m_btnIndicateAdd.setVisibility(View.VISIBLE);

        int nID = m_cChartCtrl.GetCurrentChartID();
        //프레임 유형 변경
        m_cChartCtrl.SetChartFrameType(ENChartFrameType.GE_CHART_FRAME_MINI_BASIC);

        m_cChartCtrl.SetMainFloatingPoint(nID,2);

        m_cChartCtrl.SetMainPriceType(nID, ENPriceKindType.GE_PRICE_KIND_USABAR);

        m_cChartCtrl.SetBlockBGColor(nID, ColorNames.GC_Black);
        //차트 텍스트색
        m_cChartCtrl.SetBlockTextColor(nID, ColorNames.GC_White);
        //X축 텍스트색상
        m_cChartCtrl.SetXLayer_PropertyInfo(nID,4,ColorNames.GC_White);
        //Y축 텍스트색상
        m_cChartCtrl.SetYLayer_PropertyInfo(nID,-1,4,(double)ColorNames.GC_White);

        //X축 Grid격자 색상
        m_cChartCtrl.SetXLayer_PropertyInfo(nID,1,ColorNames.GC_DarkSlateGray);

        //새로운 영역에 CCI지표 추가
        m_cChartCtrl.AddFMChartObject(nID,ENObjectKindType.GE_OBJECT_KIND_INDICA, ENObjectYScaleType.GE_OBJECT_YSCALE_LOGIC,"CCI");

        //가격영역에 구간 표시
        m_cChartCtrl.AddFMChartObject(nID,ENObjectKindType.GE_OBJECT_KIND_REGION, ENObjectYScaleType.GE_OBJECT_YSCALE_NONE,"PSar가격강세구간");

        //가격영역에 매수/매도 신호 표시
        m_cChartCtrl.AddFMChartObject(nID,ENObjectKindType.GE_OBJECT_KIND_SIGNAL, ENObjectYScaleType.GE_OBJECT_YSCALE_NONE,"Aroon골든크로스");

        //Y축 Grid격자 색상(-1로 주면 모든 영역 적용)
        m_cChartCtrl.SetYLayer_PropertyInfo(nID,-1,1,(double) ColorNames.GC_DarkSlateGray);

        //CCI 선색상 변경
        m_cChartCtrl.SetObject_PropertyInfo(nID,"CCI",0,0,ColorNames.GC_Orange);

        //상승색 변경
        m_cChartCtrl.SetPrice_PropertyInfo(nID,1,ColorNames.GC_IndianRed);
        //하락색 변경
        m_cChartCtrl.SetPrice_PropertyInfo(nID,16,ColorNames.GC_LightBlue);

        //굵기
        m_cChartCtrl.SetPrice_PropertyInfo(nID,12,3);

        //거래량 영역 Y축 수치표현 숨기기
        m_cChartCtrl.SetVolume_PropertyInfo(nID,19,0);

        //거래량 타입 - Simple Bar
        m_cChartCtrl.SetVolume_PropertyInfo(nID,1,0);

        /////////////////////////////////////////////////////////////////////////////////////////////////////////
        //데이터 직접 처리
        int nDataCount = TempData.GE_FORMCHART_Old_Ind_DAILY.length;
        ArrayList<ST_PACKET_COREDATA> cPacketList = new ArrayList<ST_PACKET_COREDATA>();

        ST_PACKET_COREDATA stPacketBasicDataP = null;

        int nFind = 0;
        int nNext = 0;
        int nIndex = 0;
        String strLine = "";
        String strSub = "";
        for(int i=nDataCount-1;i>=0;i--)
        {
            stPacketBasicDataP = ST_PACKET_COREDATA.AllocMemory();

            nNext = 0;
            nIndex = 0;

            strLine = TempData.GE_FORMCHART_Old_Ind_DAILY[i];
            nFind = strLine.indexOf(" ");
            while(nFind >= 0)
            {
                if(nIndex == 0)
                {
                    strSub = strLine.substring(0,nFind);
                }
                else
                {
                    nNext = strLine.indexOf(" ", nFind + 1);
                    if (nNext >= 0)
                        strSub = strLine.substring(nFind, nNext);
                    else
                        //strSub = strLine.substring(nFind + 1);
                        strSub = strLine.substring(nFind);
                    nFind = nNext + 1;
                }

                stPacketBasicDataP.dValueList[nIndex] = Double.parseDouble(strSub);
                nIndex++;

                if(nNext < 0) break;
            }

            cPacketList.add(stPacketBasicDataP);
        }
        m_cChartCtrl.ReceiveQuery(nID, "USABar", "", 2, 0, cPacketList);

        //View갯수 변경
        m_cChartCtrl.ChangeViewSize(nID,30);

        //3초 대기 핸들러
        if(m_nHandlerStatus == STOP)
        {
            m_nHandlerStatus = PLAY;
            handler.sendEmptyMessageDelayed(0, DELAY);
        }
    }

    public void InitFormChartOverlap() {
        if (m_cChartCtrl == null) return;

        m_btnIndicateAdd.setVisibility(View.GONE);

        int nID = m_cChartCtrl.GetCurrentChartID();
        //프레임 유형 변경
        m_cChartCtrl.SetChartFrameType(ENChartFrameType.GE_CHART_FRAME_MINI_COMPARE);

        m_cChartCtrl.SetMainFloatingPoint(nID,0);

        m_cChartCtrl.SetMainPriceType(nID, ENPriceKindType.GE_PRICE_KIND_OVERLAP);

        m_cChartCtrl.SetPrice_PropertyInfo(nID,10,ColorNames.GC_Red);
        //선굵기
        m_cChartCtrl.SetPrice_PropertyInfo(nID,12,3);

        //거래량 삭제
        m_cChartCtrl.SetVolume_PropertyInfo(nID,20,0);

        //상대비교 스케일로 변경
        m_cChartCtrl.SetCompareScaleType(nID, ENCompareScaleType.GE_COMPARE_SCALE_RELATIVE_RATIO);

        //0선 표시
        m_cChartCtrl.SetCompareZeroLine(nID, true);

        //비율일때 0값을 최초 시작 위치로
        m_cChartCtrl.SetComparePosition(nID, ENComparePosType.GE_COMPARE_BASE_POS_START_ALL);

        //가격영영에 중복지표 추가
        m_cChartCtrl.AddCompareChartObj(nID,"000660","SK하이닉스", ENMarketCategoryType.PNX_CODE_TYPE_KOSPI_CODE.GetIndexValue()+"",
                "", "",-1, GlobalDefines.GD_OBJECT_PRICE_REGION);
        //선굵기 - 3
        m_cChartCtrl.SetObject_PropertyInfo(nID,"SK하이닉스",0,2,3);
        /////////////////////////////////////////////////////////////////////////////////////////////////////////
        //데이터 직접 처리
        int nDataCount = TempData.GE_FORMCHART_005930_DAILY.length;
        ArrayList<ST_PACKET_COREDATA> cPacketList = new ArrayList<ST_PACKET_COREDATA>();

        ST_PACKET_COREDATA stPacketBasicDataP = null;

        int nFind = 0;
        int nNext = 0;
        int nIndex = 0;
        String strLine = "";
        String strSub = "";
        int i=nDataCount-1;
        for(i=nDataCount-1;i>=0;i--)
        {
            stPacketBasicDataP = GlobalStructs.AllocPacketCore();

            nNext = 0;
            nIndex = 0;

            strLine = TempData.GE_FORMCHART_005930_DAILY[i];
            nFind = strLine.indexOf(" ");
            while(nFind >= 0)
            {
                if(nIndex == 0)
                {
                    strSub = strLine.substring(0,nFind);
                }
                else
                {
                    nNext = strLine.indexOf(" ", nFind + 1);
                    if (nNext >= 0)
                        strSub = strLine.substring(nFind, nNext);
                    else
                        //strSub = strLine.substring(nFind + 1);
                        strSub = strLine.substring(nFind);
                    nFind = nNext + 1;
                }

                stPacketBasicDataP.dValueList[nIndex] = Double.parseDouble(strSub);
                nIndex++;

                if(nNext < 0) break;
            }

            cPacketList.add(stPacketBasicDataP);
        }

        m_cChartCtrl.ReceiveQuery(nID,"005930","삼성전자", 0, ENMarketCategoryType.PNX_CODE_TYPE_KOSPI_CODE.GetIndexValue(),cPacketList);

        ////////////////////////////////////////////////////////////////////////////////////////////////
        //중복지표 데이터 추가

        cPacketList.clear();

        nFind = 0;
        nNext = 0;
        nIndex = 0;
        strLine = "";
        strSub = "";
        nDataCount = TempData.GE_FORMCHART_000660_DAILY.length;
        i=nDataCount-1;
        for(i=nDataCount-1;i>=0;i--)
        {
            stPacketBasicDataP = ST_PACKET_COREDATA.AllocMemory();

            nNext = 0;
            nIndex = 0;

            strLine = TempData.GE_FORMCHART_000660_DAILY[i];
            nFind = strLine.indexOf(" ");
            while(nFind >= 0)
            {
                if(nIndex == 0)
                {
                    strSub = strLine.substring(0,nFind);
                }
                else
                {
                    nNext = strLine.indexOf(" ", nFind + 1);
                    if (nNext >= 0)
                        strSub = strLine.substring(nFind, nNext);
                    else
                        //strSub = strLine.substring(nFind + 1);
                        strSub = strLine.substring(nFind);
                    nFind = nNext + 1;
                }

                stPacketBasicDataP.dValueList[nIndex] = Double.parseDouble(strSub);
                nIndex++;

                if(nNext < 0) break;
            }

            cPacketList.add(stPacketBasicDataP);
        }

        m_cChartCtrl.ReceiveQuery(nID, "000660", "SK하이닉스", 0, ENMarketCategoryType.PNX_CODE_TYPE_KOSPI_CODE.GetIndexValue(),
                cPacketList,null,null,null,null,ENRequestQueryType.GE_REQUEST_QUERY_OVERLAP.GetIndexValue(), 0);
        //3초 대기 핸들러
        if(m_nHandlerStatus == STOP)
        {
            m_nHandlerStatus = MID_PLAY;
            handler.sendEmptyMessageDelayed(0, MID_DELAY);
        }
    }

    public void InitFormChartLine() {
        if (m_cChartCtrl == null) return;

        m_btnIndicateAdd.setVisibility(View.VISIBLE);

        int nID = m_cChartCtrl.GetCurrentChartID();
        //프레임 유형 변경
        m_cChartCtrl.SetChartFrameType(ENChartFrameType.GE_CHART_FRAME_MINI_BASIC);

        m_cChartCtrl.SetMainFloatingPoint(nID,0);

        m_cChartCtrl.SetPacketPeriodTypeByID(nID, ENPacketPeriodType.GE_PACKET_PERIOD_TICK);

        //차트 기본 정보 바꿈
        GlobalStructs.ST_COMM_REQUEST  stNowCommRqInfo = m_cChartCtrl.GetChartCommInfoByID(nID);
        stNowCommRqInfo.strCode = "000660";
        stNowCommRqInfo.strName = "SK하이닉스";
        m_cChartCtrl.SetCommRequestByID(nID,stNowCommRqInfo, ENChartDataEventType.GE_DATA_EVENT_CODE_CHANGE);
        m_cChartCtrl.SetMainPriceType(nID, ENPriceKindType.GE_PRICE_KIND_LINE);

        m_cChartCtrl.SetBaseLineValue(nID, ENBaseLineType.GE_BASELINE_PREV_CLOSE,223500);

        //새로운 영역에 CCI지표 추가
        //m_cChartCtrl.AddFMChartObject(nID,ENObjectKindType.GE_OBJECT_KIND_INDICA, ENObjectYScaleType.GE_OBJECT_YSCALE_LOGIC,"CCI");

        //Y축 Grid격자 색상(-1로 주면 모든 영역 적용)
        m_cChartCtrl.SetYLayer_PropertyInfo(nID,-1,1,(double) ColorNames.GC_DarkSlateGray);

        //상승색 변경
        m_cChartCtrl.SetPrice_PropertyInfo(nID,1,ColorNames.GC_IndianRed);
        //하락색 변경
        m_cChartCtrl.SetPrice_PropertyInfo(nID,16,ColorNames.GC_LightBlue);

        //굵기
        m_cChartCtrl.SetPrice_PropertyInfo(nID,12,3);

        //거래량 영역 Y축 수치표현 숨기기
        m_cChartCtrl.SetVolume_PropertyInfo(nID,19,0);

        //0번째 영역 상하 여백 10%씩
        m_cChartCtrl.SetChartYScaleRatio(nID,0,0.8);

        //거래량 타입 - Simple Bar
        m_cChartCtrl.SetVolume_PropertyInfo(nID,1,0);

        m_cChartCtrl.SetTitleDspType(nID, ENTitleDspType.GE_TITLE_DISPLAY_NAME_VALUE);
        m_cChartCtrl.SetTitleChgType(nID, ENTitleChgType.GE_TITLE_CHANGE_DATA);

        /////////////////////////////////////////////////////////////////////////////////////////////////////////
        //데이터 직접 처리
        int nDataCount = TempData.GE_FORMCHART_017670_TICK.length;
        ArrayList<ST_PACKET_COREDATA> cPacketList = new ArrayList<ST_PACKET_COREDATA>();

        ST_PACKET_COREDATA stPacketBasicDataP = null;

        int nFind = 0;
        int nNext = 0;
        int nIndex = 0;
        String strLine = "";
        String strSub = "";
        nDataCount = 300;
        double dDate = GlobalDefines.GD_NAVALUE_DOUBLE;
        double dToday = Double.parseDouble(GlobalUtils.GetToday(false));
        for(int i=nDataCount-1;i>=0;i--)
        {
            stPacketBasicDataP = ST_PACKET_COREDATA.AllocMemory();

            nNext = 0;
            nIndex = 0;

            dDate = GlobalDefines.GD_NAVALUE_DOUBLE;
            strLine = TempData.GE_FORMCHART_017670_TICK[i];
            nFind = strLine.indexOf(" ");
            while(nFind >= 0)
            {
                if(nIndex == 0)
                {
                    strSub = strLine.substring(0,nFind);
                    dDate = Double.parseDouble(strSub);
                    nIndex++;
                    continue;
                }

                nNext = strLine.indexOf(" ", nFind + 1);
                if (nNext >= 0)
                    strSub = strLine.substring(nFind, nNext);
                else
                    //strSub = strLine.substring(nFind + 1);
                    strSub = strLine.substring(nFind);
                nFind = nNext + 1;

                if(nIndex == 1)
                {
                    if(dDate != GlobalDefines.GD_NAVALUE_DOUBLE)
                        stPacketBasicDataP.dValueList[m_nDateTimeIndex] = dDate*100000000.0 + Double.parseDouble(strSub);
                    else
                        stPacketBasicDataP.dValueList[m_nDateTimeIndex] = dToday*100000000.0 + Double.parseDouble(strSub);
                }
                else if(nIndex == 2)    //종가
                {
                    stPacketBasicDataP.dValueList[m_nCloseIndex] = Double.parseDouble(strSub);
                    stPacketBasicDataP.dValueList[m_nOpenIndex] = stPacketBasicDataP.dValueList[m_nCloseIndex];
                    stPacketBasicDataP.dValueList[m_nHighIndex] = stPacketBasicDataP.dValueList[m_nCloseIndex];
                    stPacketBasicDataP.dValueList[m_nLowIndex] = stPacketBasicDataP.dValueList[m_nCloseIndex];
                }
                else if(nIndex == 3)    //거래량
                {
                    stPacketBasicDataP.dValueList[m_nVolumeIndex] = Double.parseDouble(strSub);
                }

                nIndex++;

                if(nNext < 0) break;
            }

            cPacketList.add(stPacketBasicDataP);
        }

        m_cChartCtrl.ReceiveQuery(nID, "017670", "SK텔레콤", 0, 0, cPacketList);
        //View갯수 변경
        m_cChartCtrl.ChangeViewSize(nID,30);

        //3초 대기 핸들러
        if(m_nHandlerStatus == STOP) {
            //m_nHandlerStatus = SPEED_PLAY1;
            //handler.sendEmptyMessageDelayed(0, SPEED_DELAY1);
            m_nHandlerStatus = SPEED_PLAY2;
            handler.sendEmptyMessageDelayed(0, SPEED_DELAY2);
        }
    }

    private double GetRandomValue(double dMax,double dMin)
    {
        if(dMax < dMin)
        {
            double dTemp = dMax;
            dMax = dMin;
            dMin = dTemp;
        }

        double dRandomValue = dMin + (Math.random() * (dMax - dMin));
        return dRandomValue;
    }

    @SuppressLint("HandlerLeak")
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what == 0)
            {
                ChangeChartData();

                if(m_nHandlerStatus == PLAY)
                    handler.sendEmptyMessageDelayed(0, DELAY);
                else if(m_nHandlerStatus == MID_PLAY)
                    handler.sendEmptyMessageDelayed(0, MID_DELAY);
                else if(m_nHandlerStatus == SPEED_PLAY1)
                    handler.sendEmptyMessageDelayed(0, SPEED_DELAY1);
                else if(m_nHandlerStatus == SPEED_PLAY2)
                    handler.sendEmptyMessageDelayed(0, SPEED_DELAY2);
            }
        }
    };

    public void ChangeChartData()
    {
        int nID = m_cChartCtrl.GetCurrentChartID();
        if (nID == -1) return;

        ArrayList<GlobalStructs.ST_PACKET_COREDATA> cPacketList = new ArrayList<GlobalStructs.ST_PACKET_COREDATA>();

        GlobalStructs gStruct = new GlobalStructs();
        GlobalStructs.ST_PACKET_COREDATA stPacketBasicDataP = null;

        int i = 0;
        switch (m_nMixFormChartType) {

            case 0:
                //마지막 값을 Update하면서 고가,저가와 같이 계산한다
                m_cChartCtrl.UpdateCalcLastPrcClose(nID, GetRandomValue(51000.0, 49000.00));
                m_cChartCtrl.UpdateSumLastPacket(nID,GetRandomValue(100.0, 10.0),ENPacketDataType.GE_PACKET_CORE_VOLUME);

                m_cChartCtrl.RedrawCurrentChart();
                break;
            case 1:
                //마지막 값을 Update하면서 고가,저가와 같이 계산한다
                m_cChartCtrl.UpdateCalcLastPrcClose(nID, GetRandomValue(2015.55, 2001.25));
                m_cChartCtrl.UpdateSumLastPacket(nID,GetRandomValue(50.0, 5.0),ENPacketDataType.GE_PACKET_CORE_VOLUME);

                m_cChartCtrl.CalculateChart(true);
                break;
            case 2:
                //마지막 값을 Update하면서 고가,저가와 같이 계산한다
                double dCodeType = GetRandomValue(2.0, 1.00);
                if(dCodeType >= 1.5)
                {
                    m_cChartCtrl.UpdateCalcLastPrcClose(nID, GetRandomValue(51000.0, 49000.00));
                    m_cChartCtrl.UpdateSumLastPacket(nID, GetRandomValue(100.0, 10.0), ENPacketDataType.GE_PACKET_CORE_VOLUME);
                }
                else
                {
                    m_cChartCtrl.UpdateUserDataValue(nID,"SK하이닉스",0,GetRandomValue(89000.0, 85000.0), ENPacketDataType.GE_PACKET_CORE_CLOSE,-1);
                }

                m_cChartCtrl.RedrawCurrentChart();
                break;
            case 3:
                m_nTickPosIndex++;

                /////////////////////////////////////////////////////////////////////////////////////////////////////////
                //데이터 직접 처리
                stPacketBasicDataP = ST_PACKET_COREDATA.AllocMemory();

                int nNext = 0;
                int nIndex = 0;
                String strSub = "";
                boolean bDataLoadingFail = false;
                double dDate = GlobalDefines.GD_NAVALUE_DOUBLE;
                String strLine = TempData.GE_FORMCHART_017670_TICK[m_nTickPosIndex];
                int nFind = strLine.indexOf(" ");
                if(nFind >= 0)
                {
                    while (nFind >= 0)
                    {
                        if (nIndex == 0)
                        {
                            strSub = strLine.substring(0, nFind);
                            dDate = Double.parseDouble(strSub);
                            nIndex++;
                            continue;
                        }

                        nNext = strLine.indexOf(" ", nFind + 1);
                        if (nNext >= 0)
                            strSub = strLine.substring(nFind, nNext);
                        else
                            //strSub = strLine.substring(nFind + 1);
                            strSub = strLine.substring(nFind);
                        nFind = nNext + 1;

                        if (nIndex == 1)
                        {
                            if (dDate != GlobalDefines.GD_NAVALUE_DOUBLE)
                                stPacketBasicDataP.dValueList[m_nDateTimeIndex] = dDate * 100000000.0 + Double.parseDouble(strSub);
                            else
                                stPacketBasicDataP.dValueList[m_nDateTimeIndex] = m_dLoginToday * 100000000.0 + Double.parseDouble(strSub);
                        }
                        else if (nIndex == 2)    //종가
                        {
                            stPacketBasicDataP.dValueList[m_nCloseIndex] = Double.parseDouble(strSub);
                            stPacketBasicDataP.dValueList[m_nOpenIndex] = stPacketBasicDataP.dValueList[m_nCloseIndex];
                            stPacketBasicDataP.dValueList[m_nHighIndex] = stPacketBasicDataP.dValueList[m_nCloseIndex];
                            stPacketBasicDataP.dValueList[m_nLowIndex] = stPacketBasicDataP.dValueList[m_nCloseIndex];
                        }
                        else if (nIndex == 3)    //거래량
                        {
                            stPacketBasicDataP.dValueList[m_nVolumeIndex] = Double.parseDouble(strSub);
                        }

                        nIndex++;

                        if (nNext < 0) break;
                    }

                    m_cChartCtrl.ReceiveReal("017670",stPacketBasicDataP, ENMarketSatusType.GE_MKTIME_DUR.GetIndexValue());
                }
                else
                {
                    bDataLoadingFail = true;
                }

                if(bDataLoadingFail == true || m_nTickPosIndex >= (TempData.GE_FORMCHART_017670_TICK.length-1))
                {
                    m_nHandlerStatus = STOP;
                    m_nTickPosIndex = 0;
                    handler.removeMessages(0);
                    Toast.makeText(m_contFormChart,"Tick Data Finish!",Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == GD_CONFIG_RESULT_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                m_cChartCtrl.ChangeCurrentChartGlobalProperty(ENConfigProperty.GE_CONFIG_PROPERTY_NONE, ENPropertyAfterType.GE_PROPERTY_AFTER_NONE);
                int i = 0;
                int nNowChartIndex = m_cChartCtrl.GetFocusFrameIndex();
                boolean bScrollBarShow = m_cChartCtrl.GetCurrentChartView().IsShowScrollBar();
                for (i = 0; i < m_cChartCtrl.GetChartFrameSize(); i++) {
                    if (i == nNowChartIndex) continue;
                    m_cChartCtrl.GetChartView(i).SetShowScrollBar(bScrollBarShow);
                    m_cChartCtrl.GetChartView(i).DoSizeChange();
                    m_cChartCtrl.GetChartView(i).Redraw();
                }

                //ChangeChartGlobalProperty 에서 제외한 DrawBitmap 여기서 호출
                m_cChartCtrl.RedrawCurrentChart();
            }
        }
    }


}
