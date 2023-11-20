/**
 * Copyright 2013 ChartLab Co., Ltd.  All Rights Reserved.                              *
 * Copyright 2013   All Rights Reserved.                        						*
 * *
 * Redistribution and use in source and binary forms, with or without modification,     *
 * are prohibited without the written permission of ChartLab Co., Ltd.                  *
 * 000회사는 본 저작물에 대하여 사용권 및 개작권을 가집니다.                       		*
 *
 * @file CompareFragmentApp
 * @brief .
 * @author
 * @date 2013-06-26 오후 2:33:44<br><br>
 * @Section History <b>Modification History :<b><br>
 */
/**
 @file CompareFragmentApp
 @brief .
 @author
 @date 2013-06-26 오후 2:33:44<br><br>
 @Section History <b>Modification History :<b><br>
 */

package chartlab.PowerMChartMain.Main;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcel;
//import android.support.annotation.RequiresApi;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TableLayout.LayoutParams;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import java.io.File;
import java.util.ArrayList;
import java.util.Objects;

import chartlab.PowerMChartApp.Util.FragmentAppMain;
import chartlab.PowerMChartEngine.KernelCore.GlobalDirFile;
import chartlab.PowerMChartEngine.KernelCore.GlobalUtils;
import chartlab.PowerMChartMain.DataWnd.DataHandler;
import chartlab.PowerMChartApp.Config.ConfigObjParam;
import chartlab.PowerMChartApp.Config.ConfigSingleFragmentActivity;
import chartlab.PowerMChartApp.FrameWnd.ChartCtrl;
import chartlab.PowerMChartApp.FrameWnd.ChartView;
import chartlab.PowerMChartApp.Util.BaseApplication;
import chartlab.PowerMChartApp.Dialog.Chart.CompareCodeItemDlg;
import chartlab.PowerMChartApp.Dialog.Chart.FrameLoadDlg;
import chartlab.PowerMChartApp.Dialog.Chart.FrameSaveDlg;
import chartlab.PowerMChartApp.Widget.IconListBox.IconListItem;
import chartlab.PowerMChartApp.Widget.Items.CompareCodeItem;
import chartlab.PowerMChartApp.Widget.UtilControl.MessageBox;
import chartlab.PowerMChartEngine.Util.FrameSkins;
import chartlab.PowerMChartEngine.KernelCore.GlobalDefines;
import chartlab.PowerMChartEngine.KernelCore.GlobalEnums;
import chartlab.PowerMChartEngine.KernelCore.GlobalEnums.ENChartDataEventType;
import chartlab.PowerMChartEngine.KernelCore.GlobalEnums.ENChartStandbyType;
import chartlab.PowerMChartEngine.KernelCore.GlobalEnums.ENConfigProperty;
import chartlab.PowerMChartEngine.KernelCore.GlobalEnums.ENFUNCToolType;
import chartlab.PowerMChartEngine.KernelCore.GlobalEnums.ENFragmentFrameType;
import chartlab.PowerMChartEngine.KernelCore.GlobalEnums.ENObjectKindType;
import chartlab.PowerMChartEngine.KernelCore.GlobalEnums.ENPacketPeriodType;
import chartlab.PowerMChartEngine.KernelCore.GlobalEnums.ENPriceKindType;
import chartlab.PowerMChartEngine.KernelCore.GlobalEnums.ENPropertyAfterType;
import chartlab.PowerMChartEngine.KernelCore.GlobalEnums.ENRequestQueryType;
import chartlab.PowerMChartEngine.KernelCore.GlobalEnums.ENBlockDeleteType;
import chartlab.PowerMChartEngine.KernelCore.GlobalEnums.ENChartOCXEventType;
import chartlab.PowerMChartEngine.KernelCore.GlobalEnums.ENANALToolType;
import chartlab.PowerMChartEngine.KernelCore.GlobalProperty.CProperty_BASE;
import chartlab.PowerMChartEngine.KernelCore.GlobalStructs;
import chartlab.PowerMChartEngine.KernelCore.GlobalStructs.ST_COMM_REQUEST;
import chartlab.PowerMChartEngine.Util.PropertyFileControl;

public class CompareFragmentApp extends FragmentAppMain implements OnClickListener
{
    private enum ENDropCtrlType
    {
        LE_CODE_COMBO_LIST,
        LE_LONG_PRESS_POPUP_DLG,
        LE_LAND_SAVE_TOOL,
        LE_DROP_CTRL_ALL
    }

    private final int GD_CONFIG_RESULT_CODE = 0;
    private final int GD_PERIOD_BUTTON_COUNT = 3;
    private final int GD_PRDVALUE_COUNT = 6;

    public static Context m_contCompareFragment = null;
    private View m_vwCompareFragment = null;

    private final int GD_BTN_PERIOD_DAILY = 0;
    private final int GD_BTN_PERIOD_WEEKLY = 1;
    private final int GD_BTN_PERIOD_MONTHLY = 2;

    // 화면 모드 관련 변수
    private boolean m_bIsLandScape = false;
    private boolean m_bViewFullScreen = false;
    private boolean m_bDataTraceVisible = false;

    private boolean m_bNeedHandlerPopupWnd = false;
    
    private String m_strPathChtSys = "";
    private String m_strPathChtUser = "";
    private String m_strChtTRNumber = "";

    private MessageBox m_cMessageBoxHandler = null;
    private AlertDialog m_dlgCustomAlert = null;

    //코드, 주기 관련 변수
    private Button m_btnCodeItem = null;

    private ArrayList<CompareCodeItem> m_itemCompareCodeList = new ArrayList<CompareCodeItem>();

    //주기 관련 변수
    private ArrayList<IconListItem> m_iconPrdValueItemList = new ArrayList<IconListItem>();
    private ArrayList<Integer> m_nMinPrdValueList = new ArrayList<Integer>();

    private ENPacketPeriodType m_enPacketPeriod = ENPacketPeriodType.GE_PACKET_PERIOD_NONE;
    private ToggleButton[] m_btnPeriodList = null;

    //ButtonUI 변수
    private ImageButton m_btnBack = null;
    private ImageButton m_btnFullScreen = null;
    private ImageButton m_btnInitSys = null;
    private ImageButton m_btnConfig = null;

    private ImageButton m_btnCrossHair = null;
    private ImageButton m_btnSaveLoad = null;
    private ImageButton m_btnLandInitSaveTool = null;

    private ChartCtrl m_cChartCtrl = null;

    //PopWnd 관련 변수
    //private PopupWindow m_dlgPeriodValuePopWnd = null;
    private PopupWindow m_dlgLandBtnsPopWnd = null;
    private PopupWindow m_dlgLongPressPopWnd = null;

    //데이터 관련 변수
    private String m_strLinkCode = "";
    private String m_strLinkCodeName = "";
    private ST_COMM_REQUEST m_stNowCommRqInfo = null;
    private DataHandler m_cDataHandler = null;

    private FrameSaveDlg m_dlgFrameSave = null;
	private FrameLoadDlg m_dlgFrameLoad = null;
	//private Bitmap m_imgSaveImage = null;

	private FragmentAppMain m_cFragmentAppMain;

	private int     m_nMaxOverlapCount = 3;

	public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        //this.overridePendingTransition( R.anim.anim_left_to_center_in, R.anim.anim_center_to_left_out);

        m_contCompareFragment = getActivity();

        //FrameSkins.ReFrameSkin();

        assert m_contCompareFragment != null;
        m_bIsLandScape = BaseApplication.GetOrientationLandScape(m_contCompareFragment);

        m_cFragmentAppMain = this;
        m_cFragmentAppMain.SetFragmentMain(new InterfaceFragmentMainEvent() {

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel parcel, int i) { }

            @Override
            public void OnGetChartLinkEvent(ENChartOCXEventType enOCXEvent, int nID, ST_COMM_REQUEST stCommRequest, int nValue,String strValue)
            {
                int nPrdValue = -1;
                int nRQType = 0;
                int nViewSize = -1;
                int nTotCount = -1;
                boolean bChangeCode = false;

                switch (enOCXEvent)
                {
                    case GE_OCX_EVENT_REQUEST:
                        m_cDataHandler.RequestBaseData(nID, stCommRequest.strCode, stCommRequest.strName, stCommRequest.dStartDT[stCommRequest.nPeriod],
                                stCommRequest.dEndDT[stCommRequest.nPeriod],stCommRequest.nTotCount[stCommRequest.nPeriod],
                                stCommRequest.nMarket, stCommRequest.nPeriod, stCommRequest.nPrdValue[stCommRequest.nPeriod], stCommRequest.nRealInfo,
                                stCommRequest.nMarketGubun, ENRequestQueryType.GE_REQUEST_QUERY_ALL.GetIndexValue());
                        break;
                    case GE_OCX_EVENT_REQUEST_SCR_MORE:
                        ST_COMM_REQUEST stNewCommRequest = m_cChartCtrl.GetChartCommInfoByID(nID);
                        m_cDataHandler.RequestBaseData(nID, stCommRequest.strCode, stCommRequest.strName,
                                stCommRequest.dStartDT[stCommRequest.nPeriod],stCommRequest.dEndDT[stCommRequest.nPeriod],
                                stCommRequest.nTotCount[stCommRequest.nPeriod],stCommRequest.nMarket,
                                stCommRequest.nPeriod, stCommRequest.nPrdValue[stCommRequest.nPeriod], stCommRequest.nRealInfo,
                                stCommRequest.nMarketGubun,ENRequestQueryType.GE_REQUEST_QUERY_MAIN.GetIndexValue());
                        break;
                    case GE_OCX_EVENT_REQUEST_NO_REAL:
                        break;
                    case GE_OCX_EVENT_REQUEST_HEAD:
                        break;
                    case GE_OCX_EVENT_QUERY_COMPLETE:
                        break;
                    case GE_OCX_EVENT_CHANGE_PRICE:
                        break;
                    case GE_OCX_EVENT_CHANGE_COUNT:
                        if (nID == m_cChartCtrl.GetCurrentChartID())
                        {
                            nViewSize = m_cChartCtrl.GetCurrentChartView().GetPacketViewSize();
                            if (m_stNowCommRqInfo.nPeriod >= 0)
                                m_stNowCommRqInfo.nViewCount[m_stNowCommRqInfo.nPeriod] = nViewSize;

                            nTotCount = m_cChartCtrl.GetCurrentChartView().GetPacketTotSize();
                            //m_cMessageBoxHandler.SetMessageBoxStatus("View Size - " + nViewSize, 1000, Gravity.CENTER, 0, 300).ShowMessage();
                            m_cMessageBoxHandler.SetMessageBoxStatus(nViewSize + "/" + nTotCount, 1000, Gravity.CENTER, 0, 300).ShowMessage();

						/*if(nTotCount > 0)
						{
							strViewSize = String.format("%d",nTotCount);
							m_stNowCommRqInfo.nTotCount[m_stNowCommRqInfo.m_nPeriod] = nTotCount;
						}	*/
                        }
                        break;
                    case GE_OCX_EVENT_CHANGE_FOCUS:
                        {
                        stCommRequest.CopyObject(m_cChartCtrl.GetChartCommInfoByID(nID));
                        if (stCommRequest.nPeriod >= 0)
                        {
                            bChangeCode = (m_stNowCommRqInfo.strCode != stCommRequest.strCode);

                            m_stNowCommRqInfo.CopyObject(stCommRequest);
                            //if(bChangeCode == true) RequestBottomQuery();
                        }

                        DoChangeFocus(stCommRequest, bChangeCode);
                        //주의:Position다시 잡아줌(살짝 위치가 바뀜)
                        //int nFirstVisiblePos = m_ctrlListToolBar.getFirstVisiblePosition();
                        //m_ctrlListToolBar.setSelectionFromTop(nFirstVisiblePos, 0);
                    }
                    break;
                    default:
                        break;
                }
            }

            @Override
            public boolean OnDataCrossBtnStatusEvent() {
                return m_bDataTraceVisible;
            }

            @Override
            public void OnChangeBtmBarStatusEvent() { }

            @Override
            public void OnTaskBarHideEvent() { }

            @Override
            public void OnToolBarHideEvent() { }

            @Override
            public void OnToggleToolBarEvent(int nToolType, int nIndex, boolean bCheck) { }

            @Override
            public void OnANALSelPopupDlgEvent(ENANALToolType enANALSelectType, Point ptPos) { }

            @Override
            public CProperty_BASE OnGetChartGlobalPropertyEvent(boolean bReloading, boolean bWorkspace)
            {   return m_cChartCtrl.GetCurrentChartView().OnGetChartGlobalPropertyEvent(bReloading, bWorkspace);   }

            @Override
            public GlobalStructs.ST_ANALPOINT_INFO OnGetSelANALInfoEvent() {
                return null;
            }

            @Override
            public void OnSetSelANALInfoEvent(GlobalStructs.ST_ANALPOINT_INFO stANALToolInfo) { }

            @Override
            public int OnFindXDateIndexEvent(double dXDateTime, int nStart, int nIfNoEqualNearIndex)
            {   return 0;   }

        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        m_vwCompareFragment = inflater.inflate(R.layout.fragment_compare, container, false);
        return m_vwCompareFragment;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        InitCompareFragment();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);

        InitCompareFragment();
    }

    private void InitCompareFragment()
    {
        m_cMessageBoxHandler = new MessageBox(m_contCompareFragment, m_vwCompareFragment);

        InitChartCtrl();
        InitDataHandler();

        InitButton(); //버튼 셋팅

        //기본차트 - 중복차트
        m_cChartCtrl.GetCurrentChartView().ChangePriceKindType(ENPriceKindType.GE_PRICE_KIND_OVERLAP, false);

        //마지막 상태 로딩
        if(SerializeChart("", 1, -1, true) == true)
        {
            MakeCodeListAll();
            ChangeAllCode();
        }

        //LoadXMLPrdValueList(m_enPacketPeriod,true,true);
    }

    /*
     *********************
     **
     ** Activity Controls Init Functions
     **
     *********************/
    private void InitChartCtrl()
    {
        m_cChartCtrl = new ChartCtrl();
        m_cChartCtrl.InitChartCtrl(ENFragmentFrameType.GE_FRAGMENT_FRAME_COMPARE, this, m_contCompareFragment);

        String strPathChtUserRoot = GlobalUtils.GetStorageDirPath() + GlobalDefines.GD_ROOT_DIRECTORY;
        m_strPathChtSys = GlobalUtils.GetStorageDirPath() + GlobalDefines.GD_CHARTINFO_DIRECTORY;
        m_strPathChtUser = GlobalUtils.GetStorageDirPath() + GlobalDefines.GD_CHARTINFO_USER_DIRECTORY;
        m_strChtTRNumber = GlobalDefines.GD_CHART_TR_COMPARE;

        m_cChartCtrl.SetPathChtUserRoot(strPathChtUserRoot);
        m_cChartCtrl.SetPathChtSys(m_strPathChtSys);
        m_cChartCtrl.SetPathChtUser(m_strPathChtUser);
        m_cChartCtrl.SetChartTRNumber(m_strChtTRNumber);

        m_cChartCtrl.CreateDirectory();

        ChartView chartView = (ChartView) m_vwCompareFragment.findViewById(R.id.ChartView_Compare);
        m_cChartCtrl.AddChartView(chartView, true);

        m_stNowCommRqInfo = new ST_COMM_REQUEST();
    }

    private void InitButton()
    {
        m_btnCodeItem = (Button) m_vwCompareFragment.findViewById(R.id.Btn_TopBar_CodeItem);

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
//            m_btnCodeItem.setBackground(FrameSkins.m_gradBtnUnsel26);
//        else
//            m_btnCodeItem.setBackgroundDrawable(FrameSkins.m_gradBtnUnsel26);
        m_btnCodeItem.setBackgroundColor(FrameSkins.m_crBtnSel);
        m_btnCodeItem.setOnClickListener(this);

        m_btnCrossHair = (ImageButton) m_vwCompareFragment.findViewById(R.id.Btn_TopBar_CrossHair);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
            m_btnCrossHair.setBackground(FrameSkins.m_gradBtnUnsel26);
        else
            m_btnCrossHair.setBackgroundDrawable(FrameSkins.m_gradBtnUnsel26);
        m_btnCrossHair.setOnClickListener(this);

        m_btnPeriodList = new ToggleButton[GD_PERIOD_BUTTON_COUNT];
        m_btnPeriodList[0] = (ToggleButton) m_vwCompareFragment.findViewById(R.id.Btn_TopBar_PrdDay);
        m_btnPeriodList[0].setTag(ENPacketPeriodType.GE_PACKET_PERIOD_DAILY.GetIndexValue());
        m_btnPeriodList[1] = (ToggleButton) m_vwCompareFragment.findViewById(R.id.Btn_TopBar_PrdWeek);
        m_btnPeriodList[1].setTag(ENPacketPeriodType.GE_PACKET_PERIOD_WEEKLY.GetIndexValue());
        m_btnPeriodList[2] = (ToggleButton) m_vwCompareFragment.findViewById(R.id.Btn_TopBar_PrdMonth);
        m_btnPeriodList[2].setTag(ENPacketPeriodType.GE_PACKET_PERIOD_MONTHLY.GetIndexValue());

        for (int i = 0; i < GD_PERIOD_BUTTON_COUNT; i++)
        {
            if (m_btnPeriodList[i] != null)
                m_btnPeriodList[i].setOnClickListener(this);
        }
        m_btnPeriodList[GD_BTN_PERIOD_DAILY].setChecked(true);

        ////////////////////////////////////////////////////////////////////////////////////////
        if (m_cChartCtrl.GetChartFrameSize() > 0)
            m_enPacketPeriod = m_cChartCtrl.GetCurrentChartView().GetPacketPeriodType();

        if (m_bIsLandScape == false) //세로 모드
        {
            m_btnBack = (ImageButton) m_vwCompareFragment.findViewById(R.id.Btn_TitleBar_Back);
            m_btnInitSys = (ImageButton) m_vwCompareFragment.findViewById(R.id.Btn_TitleBar_InitSys);
            m_btnConfig = (ImageButton) m_vwCompareFragment.findViewById(R.id.Btn_TitleBar_Config);
            m_btnSaveLoad = (ImageButton) m_vwCompareFragment.findViewById(R.id.Btn_TopBar_SaveLoad);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
                m_btnSaveLoad.setBackground(FrameSkins.m_gradBtnUnsel26);
            else
                m_btnSaveLoad.setBackgroundDrawable(FrameSkins.m_gradBtnUnsel26);

            m_btnBack.setOnClickListener(this);
            m_btnInitSys.setOnClickListener(this);
            m_btnConfig.setOnClickListener(this);
            m_btnSaveLoad.setOnClickListener(this);

            m_btnFullScreen = (ImageButton) m_vwCompareFragment.findViewById(R.id.Btn_TitleBar_FullScreen);
            m_btnFullScreen.setOnClickListener(this);

            ChangeScreenMode();
        }
        else //가로 모드
        {
            m_btnLandInitSaveTool = (ImageButton) m_vwCompareFragment.findViewById(R.id.Btn_TopBar_SaveLoad);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
                m_btnLandInitSaveTool.setBackground(FrameSkins.m_gradBtnUnsel26);
            else
                m_btnLandInitSaveTool.setBackgroundDrawable(FrameSkins.m_gradBtnUnsel26);

            //2020/01/14 - 각자 PopupWnd등을 아이폰 처럼 Toggle처리할려고 했는데 모든 이벤트
            m_btnLandInitSaveTool.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    m_bNeedHandlerPopupWnd = true;
                    if (m_dlgLandBtnsPopWnd == null || m_dlgLandBtnsPopWnd.isShowing() == false) {
                        ShowAllDropCtrls(true, ENDropCtrlType.LE_LAND_SAVE_TOOL,true);
                        m_btnLandInitSaveTool.setBackgroundColor(FrameSkins.m_crBtnSel);
                        ShowDlgPopupEvent("LandInitSaveTool");
                    }
                    else
                    {
                        ShowAllDropCtrls(true, ENDropCtrlType.LE_LAND_SAVE_TOOL);
                    }
                }
            });
        }
    }

    private void InitDataHandler()
    {
        m_cDataHandler = new DataHandler(m_contCompareFragment, m_cChartCtrl, ENFragmentFrameType.GE_FRAGMENT_FRAME_COMPARE);
        //주의: 1000 -> 1초
        String strRealTimeDelay = GlobalUtils.GetPrivateProfileString(m_strPathChtUser,m_strPathChtSys, GlobalDefines.GD_CHARTINFO_MAINPROPERTY, "RealTimeDelay");

        if(strRealTimeDelay.isEmpty() == true) strRealTimeDelay = "200";

        m_cDataHandler.InitDataHandler(GlobalUtils.GetIntValue(strRealTimeDelay));
    }


    private void InitChartRequests(boolean bLinkChart)
    {
        m_stNowCommRqInfo = m_cChartCtrl.GetCurrentCommInfo();

        int nCurrrentID = m_cChartCtrl.GetCurrentChartID();
        MakeCodeListAll();
        if (nCurrrentID != -1)
            m_cChartCtrl.GetCurrentChartView().DeleteBasicBlockAll(ENBlockDeleteType.GE_NORMAL_EXCEPT_PRC_DELETE_ALL);
        ChangeAllCode();
    }

    /*
     *********************
     **
     ** LCW Serialize Functions - Load/Save
     **
     *********************/
    public boolean SerializeChart(String szName, int nLoadSave, int nEventSaveLoad, boolean bExitNoExist)
    {
        if (m_cChartCtrl.SerializeChart(szName, nLoadSave, nEventSaveLoad, bExitNoExist) == false)
        {
            //차트 초기화 후 Last가 저장되어 있지 않으면 그냥 기본 차트로 로딩(오류 메세지 띄우지 않음)
            if(szName.isEmpty() == false)
                GlobalUtils.ToastHandlerMessage(m_contCompareFragment,"[오류]저장 정보 로딩중 오류가 발생했습니다. 기본차트로 로딩됩니다");

            m_cChartCtrl.ChangeFrameSize(-1, 0, false);

            m_cChartCtrl.RedrawCurrentChart();
            ChangePeriodButtonChecked(m_enPacketPeriod);
            return false;
        }

        return true;
    }

    public void DoConfigPropertyEvent(boolean bFromMenuPopup, int nSelPos, String strDBName, ENObjectKindType enSelObjKind, ENFUNCToolType enSelToolType, boolean bSingle) //속성 설정 .Popup(주의:Selpos - 0(Y축),1(X축))
    {
        Intent intent = new Intent(m_contCompareFragment, ConfigSingleFragmentActivity.class);
        ConfigObjParam pConfigInfoObj = new ConfigObjParam(m_strPathChtSys, m_strPathChtUser, m_strChtTRNumber);

        ChartView cChartView = m_cChartCtrl.GetCurrentChartView();
        pConfigInfoObj.m_dPacketMaxValue = cChartView.GetPacketMaxValue(true);
        pConfigInfoObj.m_dPacketMinValue = cChartView.GetPacketMinValue(true);

        if (bSingle == false) //일반 설정화면
        {
            pConfigInfoObj.m_strConfigType = ConfigObjParam.CONFIG_TYPE_COMPARE;

            //View에서 Event(DoubleTab등)을 통해서 속성 설정 Popup
            pConfigInfoObj.m_bPopupType = bFromMenuPopup;
            if (bFromMenuPopup == false)
            {
                pConfigInfoObj.m_nChartSelectPos = nSelPos;
                pConfigInfoObj.m_enChartSeledKind = enSelObjKind;
                pConfigInfoObj.m_strObjSelectName = strDBName;
            }
        }
        else
        {
            pConfigInfoObj.m_strConfigType = ConfigObjParam.CONFIG_TYPE_SINGLE;
            pConfigInfoObj.m_enFuncToolType = enSelToolType;
            pConfigInfoObj.m_strObjSelectName = strDBName;
        }
        intent.putExtra(ConfigObjParam.KEY_CONFIGINFOOBJ, pConfigInfoObj);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        //m_bOverActivityMode = true;
        startActivityForResult(intent, GD_CONFIG_RESULT_CODE);
    }

    public void ShowDlgPopupEvent(String strDialogType)
    {
        if ("Init".equals(strDialogType)) //차트 초기화 Dialog
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(m_contCompareFragment)
                    .setMessage("설정을 초기화 하시겠습니까?")
                    .setPositiveButton("예", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            int nID = m_cChartCtrl.GetCurrentChartID();
                            m_cDataHandler.CancelAllRequest(nID);
                            ChartView chtView = m_cChartCtrl.GetCurrentChartView();
                            chtView.InitChartGlobalProperty(ENChartStandbyType.GE_CHART_STANDBY_SYS_TEMP, chtView.GetBaseCode(),chtView.GetBaseCodeMarket(), false);
                            chtView.ChangePriceKindType(ENPriceKindType.GE_PRICE_KIND_OVERLAP, false);
                            DeleteAllCompareObj();
                            MakeCodeListAll();
                            ChangeAllCode();
                        }
                    })
                    .setNegativeButton("아니요", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });

            if (m_dlgCustomAlert != null && m_dlgCustomAlert.isShowing())
                m_dlgCustomAlert.dismiss();

            m_dlgCustomAlert = builder.create();
            m_dlgCustomAlert.show();
        }
        else if ("CodeEdit".equals(strDialogType)) //종목코드 설정 Dialog
        {
            CompareCodeItemDlg dlgCompareCodeSelector = new CompareCodeItemDlg(m_contCompareFragment, m_itemCompareCodeList);
            dlgCompareCodeSelector.SetOnCompareCodeChangeListener(new CompareCodeItemDlg.OnCompareCodeChangeListener() {
                @Override
                public void OnCompareCodeChange(Dialog dialog, ArrayList<CompareCodeItem> arrListItem) {
                    //2016/07/07 [강민석] 비교지표만 다지우고 BaseCode변경, 새비교지표 추가 방식
                    if (DeleteAllCompareObj() == false)
                    {
                        GlobalUtils.ToastHandlerMessage(m_contCompareFragment, "종목 설정 실패");
                        return;
                    }

                    m_itemCompareCodeList.clear();
                    m_itemCompareCodeList.addAll(arrListItem);

                    ChangeAllCode();

                    dialog.dismiss();
                }
            }).show();
        }
        else if ("ChartSaveLoad".equals(strDialogType)) //차트틀 저장/불러오기 Dialog
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(m_contCompareFragment)
                    .setMessage("차트틀 저장/불러오기")
                    .setPositiveButton("저장", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            ShowFrameSaveDlg();
                        }
                    })
                    .setNeutralButton("불러오기", new DialogInterface.OnClickListener() {
                        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                        public void onClick(DialogInterface dialog, int id) {
                            ShowFrameLoadDlg();
                        }
                    })
                    .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.dismiss();
                        }
                    });

            if (m_dlgCustomAlert != null && m_dlgCustomAlert.isShowing())
                m_dlgCustomAlert.dismiss();

            m_dlgCustomAlert = builder.create();
            m_dlgCustomAlert.show();
        }
        else if ("LandInitSaveTool".equals(strDialogType))
        {
            View vwPopup = LayoutInflater.from(m_contCompareFragment).inflate(R.layout.dialog_landinitsavetool, null);
            LinearLayout llLandInit = (LinearLayout) vwPopup.findViewById(R.id.Linear_Land_Init);
            LinearLayout llLandSave = (LinearLayout) vwPopup.findViewById(R.id.Linear_Land_Save);
            LinearLayout llLandConfig = (LinearLayout) vwPopup.findViewById(R.id.Linear_Land_Config);

            ImageButton btnLandInit = (ImageButton) vwPopup.findViewById(R.id.Btn_Land_Init);
            ImageButton btnLandSave = (ImageButton) vwPopup.findViewById(R.id.Btn_Land_Save);
            ImageButton btnLandConfig = (ImageButton) vwPopup.findViewById(R.id.Btn_Land_Config);

            m_dlgLandBtnsPopWnd = new PopupWindow(vwPopup, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

            m_dlgLandBtnsPopWnd.setAnimationStyle(-1); // 애니메이션 설정(-1:설정안함, 0:설정)
            m_dlgLandBtnsPopWnd.showAsDropDown(m_btnLandInitSaveTool);
            m_dlgLandBtnsPopWnd.setOutsideTouchable(true);
            m_dlgLandBtnsPopWnd.setFocusable(true);

            OnClickListener onClickListenerInit = new OnClickListener() {
                @Override
                public void onClick(View v) {
                    //ToolsPopWndDismiss();
                    ShowAllDropCtrls(true, ENDropCtrlType.LE_LAND_SAVE_TOOL);
                    ShowDlgPopupEvent("Init");
                }
            };
            llLandInit.setOnClickListener(onClickListenerInit);
            btnLandInit.setOnClickListener(onClickListenerInit);

            OnClickListener onClickListenerSave = new OnClickListener() {
                @Override
                public void onClick(View v) {
                    //ToolsPopWndDismiss();
                    ShowAllDropCtrls(true, ENDropCtrlType.LE_LAND_SAVE_TOOL);
                    ShowDlgPopupEvent("ChartSaveLoad");
                }
            };
            llLandSave.setOnClickListener(onClickListenerSave);
            btnLandSave.setOnClickListener(onClickListenerSave);

            OnClickListener onClickListenerConfig = new OnClickListener() {
                @Override
                public void onClick(View v) {
                    //ToolsPopWndDismiss();
                    DoConfigPropertyEvent(true, -1, "", ENObjectKindType.GE_OBJECT_KIND_NONE, ENFUNCToolType.GE_FUNCTOOL_NONE, false);
                }
            };
            llLandConfig.setOnClickListener(onClickListenerConfig);
            btnLandConfig.setOnClickListener(onClickListenerConfig);
        }
    }

    public void ShowFrameSaveDlg()
    {
        ChartView chartView = m_cChartCtrl.GetCurrentChartView();

        String strDefComment = chartView.GetChartDefaultComment();

		/*if(m_imgSaveImage == null)
		{
			m_dlgFrameSave = new FrameSaveDlg(m_contCompareFragment, m_strPathChtUser, m_strChtTRNumber, strDefComment, m_cChartCtrl.GetChartViewBitmap(m_cChartView,null));
			m_imgSaveImage = m_dlgFrameSave.GetImage();
		}
		else
        {*/
        m_dlgFrameSave = new FrameSaveDlg(m_contCompareFragment, m_strPathChtUser, m_strChtTRNumber, strDefComment, m_cChartCtrl.GetChartViewBitmap(chartView, null));
        //	m_imgSaveImage = m_dlgFrameSave.GetImage();
        //}

//		int orientation = getResources().getConfiguration().orientation;
//		if (orientation == Configuration.ORIENTATION_LANDSCAPE)
//			m_dlgFrameSave.getWindow().setLayout(1600,850);
//		else
//			m_dlgFrameSave.getWindow().setLayout(840,1230);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(m_dlgFrameSave.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        m_dlgFrameSave.getWindow().setAttributes(lp);

        m_dlgFrameSave.SetOnChartFrameSaveListener(new FrameSaveDlg.OnChartFrameSaveListener() {
            @Override
            public void OnChartFrameLoad(Dialog dialog, String strLCWFilePath, String strComment) {
                dialog.dismiss();

                m_cChartCtrl.SaveTotChartProperty(strLCWFilePath, strComment, true);
                GlobalUtils.ToastHandlerMessage(m_contCompareFragment,"차트틀 저장완료");
            }
        }).show();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void ShowFrameLoadDlg()
    {
        m_dlgFrameLoad = new FrameLoadDlg(m_contCompareFragment,  m_strPathChtUser, m_strChtTRNumber);
//        int orientation = getResources().getConfiguration().orientation;
//        if (orientation == Configuration.ORIENTATION_LANDSCAPE)
//            m_dlgFrameLoad.getWindow().setLayout(1600,800);
//        else
//            m_dlgFrameLoad.getWindow().setLayout(840,1230);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(Objects.requireNonNull(m_dlgFrameLoad.getWindow()).getAttributes());

        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        Objects.requireNonNull(m_dlgFrameLoad.getWindow()).setAttributes(lp);

        m_dlgFrameLoad.SetOnChartFrameLoadListener(new FrameLoadDlg.OnChartFrameLoadListener()
        {
            @Override
            public void OnChartFrameLoad(Dialog dialog, String strLCWFilePath)
            {
                dialog.dismiss();

                String strLCWPath = strLCWFilePath;
                int nID = m_cChartCtrl.GetCurrentChartID();
                m_cDataHandler.CancelAllRequest(nID);
                if (SerializeChart(strLCWPath, 1, 0, true) == true)
                {
                    MakeCodeListAll();
                    ChangeAllCode();
                }

                //new ChartInfoChangeAsyncTask(m_contCompareFragment).execute("ChartFrameLoading", strLCWFilePath);
            }
        }).show();
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////
    //Handler Functions

    //2020/01/14 - 화면 touchDown시 모든 팝업창 닫는 이벤트때문에 toggle처리가 안되서
    private Handler HandlerFragmentTouchDown = new Handler() {
        public void handleMessage(Message m) {
            switch (m.what) {
                case 0:
                    if(m_bNeedHandlerPopupWnd == false)
                        ShowAllDropCtrls(true, ENDropCtrlType.LE_DROP_CTRL_ALL);

                    HandlerFragmentTouchDown.removeMessages(0);
                    break;
                case 1:

                    break;
            }
        }
    };

    /*
     *********************
     **
     ** CodeChange Functions
     **
     *********************/
    private boolean DeleteAllCompareObj()
    {
        ChartView chartViewCurrent = m_cChartCtrl.GetCurrentChartView();
        if (chartViewCurrent == null) return false;

        chartViewCurrent.DeleteBasicBlockAll(ENBlockDeleteType.GE_NORMAL_EXCEPT_PRC_DELETE_ALL);
        chartViewCurrent.ResizeChartBlock(GlobalEnums.ENBlockResizeType.GE_BLOCK_RESIZE_INIT);
        chartViewCurrent.Redraw();
        return true;
    }

    private void ChangeAllCode()
    {
        String strCodeName = "";
        String strCode = "";
        int nMarket = -1;
        boolean bPriceCode = false;
        CompareCodeItem compareCodeItem;

        int nID = m_cChartCtrl.GetCurrentChartID();

        int nPrdVal = m_stNowCommRqInfo.nPrdValue[m_stNowCommRqInfo.nPeriod];
        int nAllCodeCount = m_itemCompareCodeList.size();
        for (int i = 0; i < nAllCodeCount; i++)
        {
            compareCodeItem = m_itemCompareCodeList.get(i);
            bPriceCode = compareCodeItem.m_bPriceCode;

            strCodeName = compareCodeItem.m_strCodeName;
            strCode = compareCodeItem.m_strCode;
            nMarket = compareCodeItem.m_nMarket;

            m_cChartCtrl.SetQueryEvent(nID, false);

            if (bPriceCode == true)
            {
                m_stNowCommRqInfo.strName = strCodeName;
                m_stNowCommRqInfo.strCode = strCode;
                m_stNowCommRqInfo.nMarket = nMarket;

                if (m_stNowCommRqInfo.nPeriod == -1)
                    m_stNowCommRqInfo.nPeriod = ENPacketPeriodType.GE_PACKET_PERIOD_DAILY.GetIndexValue();

                m_cChartCtrl.SetCommRequestByID(nID, m_stNowCommRqInfo, ENChartDataEventType.GE_DATA_EVENT_CODE_CHANGE);

                m_cDataHandler.RequestBaseData(nID, m_stNowCommRqInfo.strCode, m_stNowCommRqInfo.strName,
                        m_stNowCommRqInfo.dStartDT[m_stNowCommRqInfo.nPeriod],m_stNowCommRqInfo.dEndDT[m_stNowCommRqInfo.nPeriod],
                        m_stNowCommRqInfo.nTotCount[m_stNowCommRqInfo.nPeriod],
                        m_stNowCommRqInfo.nMarket, m_stNowCommRqInfo.nPeriod, nPrdVal, m_stNowCommRqInfo.nRealInfo,
                        m_stNowCommRqInfo.nMarketGubun,ENRequestQueryType.GE_REQUEST_QUERY_ALL.GetIndexValue());
            }
            else
            {
                //2016/07/7 [강민석] BasicBlock AddCompareObject 임시데이터 로딩 방식 변경 필요!
                if(m_cChartCtrl.GetCurrentChartView().IsCompareObjExist(strCode) == false)
                {
                    if (m_cChartCtrl.GetCurrentChartView().AddCompareChartObj(strCode, strCodeName, String.valueOf(nMarket), "-1", "-1", -1, GlobalDefines.GD_OBJECT_PRICE_REGION) == false)
                    {
                        String strErrMsg = String.format("%s (%s) 종목 추가 실패", strCodeName, strCode);
                        Toast.makeText(m_contCompareFragment, strErrMsg, Toast.LENGTH_SHORT).show();
                        return;
                    }
                }

                m_cDataHandler.RequestBaseData(nID, strCode, strCodeName,
                        m_stNowCommRqInfo.dStartDT[m_stNowCommRqInfo.nPeriod],m_stNowCommRqInfo.dEndDT[m_stNowCommRqInfo.nPeriod],
                        m_stNowCommRqInfo.nTotCount[m_stNowCommRqInfo.nPeriod],
                        nMarket, m_stNowCommRqInfo.nPeriod, nPrdVal, m_stNowCommRqInfo.nRealInfo,
                        m_stNowCommRqInfo.nMarketGubun,ENRequestQueryType.GE_REQUEST_QUERY_OVERLAP.GetIndexValue());
            }
        }

        m_cChartCtrl.GetCurrentChartView().InitPacketSize(-1);
        m_cChartCtrl.RedrawCurrentChart();
    }

    private void MakeCodeListAll()
    {
        m_itemCompareCodeList.clear();

        ChartView chartViewCurrent = m_cChartCtrl.GetCurrentChartView();
        if (chartViewCurrent == null) return;

        m_itemCompareCodeList.add(
                new CompareCodeItem(true, chartViewCurrent.GetBaseCodeName(), chartViewCurrent.GetBaseCode(),
                        Integer.parseInt(chartViewCurrent.GetBaseCodeMarket())));

        //2016/07/7 [강민석] 추후 비교지표 이동가능시 bOnlyPrice 변경필요?
        int nCompareObjCount = chartViewCurrent.GetCompareObjCount(true);
        for (int i = 0; i < nCompareObjCount; i++)
        {
            m_itemCompareCodeList.add(
                    new CompareCodeItem(false, chartViewCurrent.GetCompareSubName(i), chartViewCurrent.GetCompareSubCode(i),
                            Integer.parseInt(chartViewCurrent.GetCompareSubMarket(i))));
        }
    }

    /*
     *********************
     **
     ** Button Event Functions
     **
     *********************/
    @Override
    public void onClick(View v)
    {
        boolean bPeriodBtnClick = false;
        switch (v.getId()) {
            case R.id.Btn_TitleBar_FullScreen:
                m_bViewFullScreen = !m_bViewFullScreen;
                ChangeScreenMode();
                break;
            case R.id.Btn_TitleBar_InitSys:
                ShowDlgPopupEvent("Init");
                break;
            case R.id.Btn_TitleBar_Config:
                DoConfigPropertyEvent(true, -1, "", ENObjectKindType.GE_OBJECT_KIND_NONE, ENFUNCToolType.GE_FUNCTOOL_NONE, false);
                break;
            //2018/04/13 조원상 종료버튼 삭제 및 수정
            case R.id.Btn_TitleBar_Back:
                getActivity().finish();
                getActivity().overridePendingTransition(R.anim.fadein, R.anim.fadeout);
                break;
            case R.id.Btn_TopBar_CodeItem:
                //2016/07/07 [강민석] CodeEdit창 오픈전 항상 현재 CodeList 최신화
                MakeCodeListAll();

                ShowDlgPopupEvent("CodeEdit");
                break;
            case R.id.Btn_TopBar_CrossHair:
                if (m_bDataTraceVisible == false)
                {
                    m_btnCrossHair.setBackgroundColor(FrameSkins.m_crBtnSel);
                    m_bDataTraceVisible = true;
                    m_cChartCtrl.GetCurrentChartView().SetCrosshair(m_bDataTraceVisible == true ? 3 : 0);
                }
                else
                {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
                        m_btnCrossHair.setBackground(FrameSkins.m_gradBtnUnsel26);
                    else
                        m_btnCrossHair.setBackgroundDrawable(FrameSkins.m_gradBtnUnsel26);

                    m_bDataTraceVisible = false;
                    m_cChartCtrl.GetCurrentChartView().SetCrosshair(m_bDataTraceVisible == true ? 3 : 0);
                }
                break;
            case R.id.Btn_TopBar_SaveLoad:
                ShowDlgPopupEvent("ChartSaveLoad");
                break;
            /*case R.id.button_period_tick:
				m_enPacketPeriod = ENPacketPeriodType.GE_PACKET_PERIOD_TICK;
				bPeriodBtnClick = true;
				LoadXMLPrdValueList(m_enPacketPeriod, true, true);
				break;
            case R.id.Btn_TopBar_PrdMinute:
                ShowDlgPopupEvent("PeriodValuePopupWnd-Minute");
                break;*/
            case R.id.Btn_TopBar_PrdWeek:
                m_enPacketPeriod = ENPacketPeriodType.GE_PACKET_PERIOD_WEEKLY;
                bPeriodBtnClick = true;
                break;
            case R.id.Btn_TopBar_PrdDay:
                m_enPacketPeriod = ENPacketPeriodType.GE_PACKET_PERIOD_DAILY;
                bPeriodBtnClick = true;
                break;
            case R.id.Btn_TopBar_PrdMonth:
                m_enPacketPeriod = ENPacketPeriodType.GE_PACKET_PERIOD_MONTHLY;
                bPeriodBtnClick = true;
                break;
        }

        if (bPeriodBtnClick == true)
        {
            m_stNowCommRqInfo.nPeriod = m_enPacketPeriod.GetIndexValue();
            ChangePeriodButtonChecked(m_enPacketPeriod);

            int nCurrentChartID = m_cChartCtrl.GetCurrentChartView().GetViewCtrlID();
            m_cChartCtrl.SetCommRequestByID(nCurrentChartID, m_stNowCommRqInfo, ENChartDataEventType.GE_DATA_EVENT_PERIOD);
            m_cChartCtrl.SetPacketPeriodTypeByID(nCurrentChartID, m_enPacketPeriod);

            DeleteAllCompareObj();
            ChangeAllCode();
            //m_cDataHandler.RequestBaseData(nCurrentChartID, m_stNowCommRqInfo.m_strCode, m_stNowCommRqInfo.m_strName, m_stNowCommRqInfo.nTotCount[m_stNowCommRqInfo.m_nPeriod],
            //		m_stNowCommRqInfo.m_nMarket, m_stNowCommRqInfo.m_nPeriod, m_stNowCommRqInfo.m_nPrdValue[m_stNowCommRqInfo.m_nPeriod], m_stNowCommRqInfo.m_nRealInfo,ENRequestQueryType.GE_REQUEST_QUERY_ALL.GetIndexValue());

            //2016/07/08 [강민석] ChartInfoChangeAsyncTask 내에 RequestBaseData 호출로 결론적으로 DataHandler 내부에 AsyncTask로 동작함
            //new ChartInfoChangeAsyncTask(m_contCompareFragment).execute("PeriodChange");
        }
    }

    private void ChangeScreenMode()
    {
        if (m_bViewFullScreen)
        {
            m_btnFullScreen.setBackgroundResource(R.drawable.button_reduce_selector);
            m_vwCompareFragment.findViewById(R.id.Relative_TopBar).setVisibility(View.GONE);
        }
        else
        {
            m_btnFullScreen.setBackgroundResource(R.drawable.button_expand_seletor);
            m_vwCompareFragment.findViewById(R.id.Relative_TopBar).setVisibility(View.VISIBLE);
        }
    }

    //Act->Fragment 일반 함수로 전환
    public boolean dispatchTouchEvent(MotionEvent ev)
    {
        Message messageShow = new Message();
        messageShow.what = 0;
        m_bNeedHandlerPopupWnd = false;
        //2020/02/03 - 분틱 주기 나온후 Fling시 주기값 DropWnd가 남아 있는것 처럼 보여서 Handler 시간을 300 -> 150으로 줄임
        HandlerFragmentTouchDown.sendMessageDelayed(messageShow,150);

        //return super.dispatchTouchEvent(ev);
        return true;
    }

    /*
     *********************
     **
     ** ChartView & Ctrl에서 Event Functions
     **
     *********************/
    public int OnSetChartLinkEvent(ENChartDataEventType enDataEvent, ST_COMM_REQUEST pstCommRqInfo)
    {
		/*int nCodeRotate = 0;	//1:Next,;-1:Prev
		ST_COMM_REQUEST stCommRequest;
		int nID = m_cChartCtrl.GetCurrentChartID();
		//EnChartNavigateType enChartNavigate = m_cChartCtrl.GetChartNavigate();

		//날짜 변경이 아니면 무조건 오늘 날짜 기준으로 되돌린다
		if(enDataEvent != ENChartDataEventType.GE_DATA_EVENT_START_END_DT && enDataEvent != ENChartDataEventType.GE_DATA_EVENT_END_DT)
		{
			m_stNowCommRqInfo.dEndDT[m_stNowCommRqInfo.m_nPeriod] = 99999999.0;
			m_stNowCommRqInfo.dStartDT[m_stNowCommRqInfo.m_nPeriod] = 0.0;

			m_stNowCommRqInfo.m_nRealInfo = 1;
			//m_cChartCtrl.SetRealCommStatus(nID,m_stNowCommRqInfo.m_nRealInfo);
		}

		switch(enDataEvent)
		{
		case GE_DATA_EVENT_CODE_CHANGE:		//시장콤보변경/코드변경 동일 적용
			DoChangeMarketCode(pstCommRqInfo.m_strCode,pstCommRqInfo.m_nMarket);

			//if(m_strPrevInputCode != m_InputBar.m_strCode && m_enChartNavigate != GE_CHART_NAVIGATE_OVERLAP)
			//{
			//	m_cDataHandler.RequestBottomQuery(nID,pstCommRqInfo.m_strCode,pstCommRqInfo.m_nMarket,m_stNowCommRqInfo.m_nMarketGubun);
			//	m_strPrevInputCode = m_InputBar.m_strCode;
			//}
			break;
		case GE_DATA_EVENT_REREQUEST:		//재조회
			m_cChartCtrl.RequestAllChart(nID,enDataEvent,m_stNowCommRqInfo);
			break;
		case GE_DATA_EVENT_PERIOD:			//주기변경시(ex:틱->분)
			DoChangeChartPeriod(pstCommRqInfo.m_nPeriod);
			break;
		case GE_DATA_EVENT_PRDVAL:			//주기값(ex:1분->3분)
			DoChangeChartPrdValue(pstCommRqInfo.m_nPrdValue[pstCommRqInfo.m_nPeriod]);
			break;
		case GE_DATA_EVENT_CHANGE_COUNT:	//데이터 조회요청 갯수 변경(주의:저장필요)
			m_stNowCommRqInfo.nTotCount[m_stNowCommRqInfo.m_nPeriod] = pstCommRqInfo.nTotCount[m_stNowCommRqInfo.m_nPeriod];
			m_stNowCommRqInfo.nTotCountUser[m_stNowCommRqInfo.m_nPeriod] = pstCommRqInfo.nTotCount[m_stNowCommRqInfo.m_nPeriod];
			m_stNowCommRqInfo.dEndDT[m_stNowCommRqInfo.m_nPeriod] = 99999999.0;
			m_stNowCommRqInfo.dStartDT[m_stNowCommRqInfo.m_nPeriod] = 0.0;
			m_cChartCtrl.RequestAllChart(nID,enDataEvent,m_stNowCommRqInfo);
			break;
		case GE_DATA_EVENT_DATA_CONTINUE:
			if (nID == -1) break;

			//m_enChartNavigate = GE_CHART_NAVIGATE_CHANGE;
			//m_cChartCtrl.SetChartNavigate(GE_CHART_NAVIGATE_CHANGE);
			StartRequestData(nID, m_stNowCommRqInfo.m_strCode, m_stNowCommRqInfo.m_strName, m_stNowCommRqInfo.m_nMarket, m_stNowCommRqInfo.m_nMarketGubun,0,1);
			//m_enChartNavigate = enChartNavigate;
			//m_cChartCtrl.SetChartNavigate(m_enChartNavigate);
			break;
		}*/

        return 1;
    }

    public boolean DoChangeFocus(ST_COMM_REQUEST stCommRequest, boolean bChangeCode)
    {
        //해야할일
        //1 - 코드 연동
		/*if(bChangeCode)
		{
			m_ctrlListBoxCode.setText(stCommRequest.m_strName);
			m_ctrlListBoxCode.setValue(stCommRequest.m_strCode);
		}*/

        //2 - 주기(값) 연동, Daily이상이면 주기값콤보 Enable/Disable등처리
        ChangePeriodButtonChecked(ENPacketPeriodType.GetEnumValue(stCommRequest.nPeriod));

        //m_cChartCtrl.GetCurrentChartView().ChangeTaskBarInfo(true);

        //ResetToolBarInfo();
        //DoChangeCurPrice(0);

        return true;
    }

    /*
     *********************
     **
     ** Period & PrdValue Change Functions
     **
     *********************/
    public void LoadXMLPrdValueList(ENPacketPeriodType enPacketPeriod,boolean bListDataChange)
    {
        LoadXMLPrdValueList(enPacketPeriod,bListDataChange,false);
    }

    public void LoadXMLPrdValueList(ENPacketPeriodType enPacketPeriod,boolean bListDataChange,boolean bCheckDiffPrdValue) {
        boolean bChangePrdValue = false;

        int i = 0;
        if (bCheckDiffPrdValue == true)
        {
            int nValue = -1;
            ArrayList<Integer> nMinPrdValueList = new ArrayList<Integer>();
            ArrayList<Integer> nTickPrdValueList = new ArrayList<Integer>();
            String strMinPrdValue = GlobalUtils.GetPrivateProfileString(m_strPathChtUser, m_strPathChtSys, GlobalDefines.GD_CHARTINFO_MTPERIOD, "m");
            String strTickPrdValue = GlobalUtils.GetPrivateProfileString(m_strPathChtUser, m_strPathChtSys, GlobalDefines.GD_CHARTINFO_MTPERIOD, "t");
            if (strMinPrdValue.isEmpty() == false) {
                String strMinPrdValueList[] = GlobalUtils.SplitStringArray(strMinPrdValue, "/");

                for (i = 0; i < strMinPrdValueList.length; i++) {
                    nValue = GlobalUtils.GetIntValue(strMinPrdValueList[i]);
                    nMinPrdValueList.add(nValue);
                }
            }

            if (strTickPrdValue.isEmpty() == false) {
                String strTickPrdValueList[] = GlobalUtils.SplitStringArray(strTickPrdValue, "/");
                for (i = 0; i < strTickPrdValueList.length; i++) {
                    nValue = GlobalUtils.GetIntValue(strTickPrdValueList[i]);
                    nTickPrdValueList.add(nValue);
                }
            }

            if (m_nMinPrdValueList.size() != nMinPrdValueList.size()) bChangePrdValue = true;

            if (bChangePrdValue == false) {
                for (i = 0; i < m_nMinPrdValueList.size(); i++) {

                    if (m_nMinPrdValueList.get(i) != nMinPrdValueList.get(i)) {
                        bChangePrdValue = true;
                        break;
                    }
                }
            }

            if (bChangePrdValue == true) bListDataChange = true;

            if (nMinPrdValueList.isEmpty() == false) {
                m_nMinPrdValueList.clear();
                m_nMinPrdValueList.addAll(nMinPrdValueList);
            }
        }

        if (bListDataChange == true)
        {
            final String strSeparator = "_";
            String strDirPath = GlobalUtils.GetStorageDirPath() + GlobalDefines.GD_CHARTINFO_DIRECTORY + "Temp_Data/files/";
            String strFilePath = "";
            String strCode = m_stNowCommRqInfo.strCode;
            String strCodeName = m_stNowCommRqInfo.strName;

            m_iconPrdValueItemList.clear();
            boolean bEnable = false;
            int nPrdMinValue = -1;
            int nPrdTickValue = -1;
            for (i = 0; i < GD_PRDVALUE_COUNT; i++)
            {
                if (enPacketPeriod == ENPacketPeriodType.GE_PACKET_PERIOD_MINUTE) {
                    nPrdMinValue = m_nMinPrdValueList.get(i);
                    strFilePath = strDirPath + strCodeName + strSeparator + strCode + strSeparator + "분" + nPrdMinValue + ".csv";
                    bEnable = GlobalDirFile.isFileExist(strFilePath);

                    m_iconPrdValueItemList.add(new IconListItem(bEnable, 0, String.valueOf(nPrdMinValue), String.valueOf(i)));
                }
                else //분,틱이 아닌경우는 1값만 입력,셋팅
                {
                    m_iconPrdValueItemList.add(new IconListItem(0, "1", String.valueOf(i)));
                    break;
                }
            }
        }
    }

    public void ChangePeriodButtonChecked(ENPacketPeriodType enPacketPeriod)
    {
        int nPeriodBtnSize = m_btnPeriodList.length;
        //String strMTName = "";
        int nTag = -1;
        for (int i = 0; i < nPeriodBtnSize; i++)
        {
            nTag = (int)m_btnPeriodList[i].getTag();
            if (nTag == enPacketPeriod.GetIndexValue())
            {
                m_btnPeriodList[i].setChecked(true);
                m_btnPeriodList[i].setBackgroundDrawable(FrameSkins.m_gradSegControlSel);
            }
            else
            {
                m_btnPeriodList[i].setChecked(false);
                m_btnPeriodList[i].setBackgroundDrawable(FrameSkins.m_gradSegControlUnsel);
            }

            //비교차트 분,틱 처리 하지 않
            /*if(nTag == ENPacketPeriodType.GE_PACKET_PERIOD_MINUTE.GetIndexValue() ||
                nTag== ENPacketPeriodType.GE_PACKET_PERIOD_TICK.GetIndexValue())
            if (nTag == ENPacketPeriodType.GE_PACKET_PERIOD_MINUTE.GetIndexValue())
            {
                int nPrdValue = m_stNowCommRqInfo.nPrdValue[i];
                if (nPrdValue == -1)
                    strMTName = "분";
                else
                    strMTName = nPrdValue + "분";

                m_btnPeriodList[i].setText(strMTName);
                m_btnPeriodList[i].setTextOn(strMTName);
                m_btnPeriodList[i].setTextOff(strMTName);
            }*/
        }
    }

    /*
     *********************
     **
     ** Property Result Functions
     **
     *********************/
    //Act->Fragment 일반 함수로 전환
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode == GD_CONFIG_RESULT_CODE)
        {
            if (resultCode == getActivity().RESULT_OK)
            {
                m_cChartCtrl.ChangeCurrentChartGlobalProperty(ENConfigProperty.GE_CONFIG_PROPERTY_NONE, ENPropertyAfterType.GE_PROPERTY_AFTER_NONE);

                //new ChartInfoChangeAsyncTask(m_contCompareFragment).execute("ChangeProperty");
            }
        }
        //super.onActivityResult(requestCode, resultCode, data);
    }

    /////////////////////////////////////////////////////////////////////////////////////////
    // 데이터 Handling 함수
    public void SetLinkCode_Name(String strLinkCode, String strLinkCodeName)
    {
        m_strLinkCode = strLinkCode;
        m_strLinkCodeName = strLinkCodeName;
    }

    /*
     *********************
     **
     ** SystemOverrid Functions
     **
     *********************/
    public void onBackPress()
    {
        ShowAllDropCtrls(true, ENDropCtrlType.LE_DROP_CTRL_ALL);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onConfigurationChanged(Configuration newConfig)
    {
        super.onConfigurationChanged(newConfig);

        ShowAllDropCtrls(true, ENDropCtrlType.LE_LONG_PRESS_POPUP_DLG);

        //회전할때 이전 상태를 저장한다
        SerializeChart("", 0, -1, true);

        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE)
        {
            m_bIsLandScape = true;
            // 기기가 가로로 회전할때 할 작업
        }
        else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT)
        {
            m_bIsLandScape = false;
            // 기기가 세로로 회전할때 할 작업
        }

        LayoutInflater inflater2 = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        m_vwCompareFragment = inflater2.inflate(R.layout.fragment_compare, null);
        getActivity().setContentView(m_vwCompareFragment);

        //m_bOverActivityMode = false;
        InitCompareFragment();
        InitChartRequests(false);

		if(m_dlgFrameLoad != null)
		{
			if(m_dlgFrameLoad.isShowing())
			{
				m_dlgFrameLoad.dismiss();
				ShowFrameLoadDlg();
			}
		}
		if(m_dlgFrameSave != null)
		{
			if(m_dlgFrameSave.isShowing())
			{
				m_dlgFrameSave.dismiss();
				m_dlgFrameSave = null;
				ShowFrameSaveDlg();
			}
		}
    }

    @Override
    public void onDestroyView()
    {
        if (m_cMessageBoxHandler != null && m_cMessageBoxHandler.IsShowing())
            m_cMessageBoxHandler.RemoveAllMessage();

        FrameSkins.InitFrameSkin();
        ShowAllDropCtrls(true, ENDropCtrlType.LE_DROP_CTRL_ALL);

        super.onDestroyView();
    }

    public void onDestroy()
    {
        m_cChartCtrl.SerializeChart("", 0, -1, true);

        m_cDataHandler.CloseDataHandler();
        //m_cDataHandler.DoStop();
        super.onDestroy();
    }

    //종목코드,주기값등 목록
    private void ShowAllDropCtrls(boolean bHidden, ENDropCtrlType enShowDropCtrl)
    {
        ShowAllDropCtrls(bHidden,enShowDropCtrl,false);
    }

    private void ShowAllDropCtrls(boolean bHidden, ENDropCtrlType enShowDropCtrl, boolean bExcept) {

        ShowAllDropCtrls(bHidden,enShowDropCtrl,bExcept,false);
    }

    private void ShowAllDropCtrls(boolean bHidden, ENDropCtrlType enShowDropCtrl, boolean bExcept, boolean bTouchDown)
    {
        //주의:자신을 제외하고 모두 닫아라
        if(bExcept == true)
        {
            if (enShowDropCtrl == ENDropCtrlType.LE_DROP_CTRL_ALL) return;

            if (enShowDropCtrl != ENDropCtrlType.LE_LONG_PRESS_POPUP_DLG) {
                if (m_dlgLongPressPopWnd != null && m_dlgLongPressPopWnd.isShowing() == true) {
                    m_dlgLongPressPopWnd.dismiss();
                }
            }

            if (enShowDropCtrl != ENDropCtrlType.LE_LAND_SAVE_TOOL) {
                if (m_dlgLandBtnsPopWnd != null && m_dlgLandBtnsPopWnd.isShowing() == true) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
                        m_btnLandInitSaveTool.setBackground(FrameSkins.m_gradBtnUnsel26);
                    else
                        m_btnLandInitSaveTool.setBackgroundDrawable(FrameSkins.m_gradBtnUnsel26);
                    m_dlgLandBtnsPopWnd.dismiss();
                }
            }
        }
        else
        {
            if (enShowDropCtrl == ENDropCtrlType.LE_DROP_CTRL_ALL || enShowDropCtrl == ENDropCtrlType.LE_LONG_PRESS_POPUP_DLG) {
                if (m_dlgLongPressPopWnd != null && m_dlgLongPressPopWnd.isShowing() == true) {
                    m_dlgLongPressPopWnd.dismiss();
                    if (enShowDropCtrl == ENDropCtrlType.LE_LONG_PRESS_POPUP_DLG) return;
                }
            }

            if (enShowDropCtrl == ENDropCtrlType.LE_DROP_CTRL_ALL || enShowDropCtrl == ENDropCtrlType.LE_LAND_SAVE_TOOL) {
                if (m_dlgLandBtnsPopWnd != null && m_dlgLandBtnsPopWnd.isShowing() == true) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
                        m_btnLandInitSaveTool.setBackground(FrameSkins.m_gradBtnUnsel26);
                    else
                        m_btnLandInitSaveTool.setBackgroundDrawable(FrameSkins.m_gradBtnUnsel26);
                    m_dlgLandBtnsPopWnd.dismiss();
                    if (enShowDropCtrl == ENDropCtrlType.LE_LAND_SAVE_TOOL) return;
                }
            }
        }
    }
}

////////////////////////////////////////////////////////////////////////////////////////////////////
//사용하지는 않지만 추후를 고려해서 소스 유지

    /*public void ShowObjectSelPopupDlg(ENObjectKindType enObjectType, Point ptPos, String strDBName)
    {
        Message messageShow = new Message();
        Bundle bundle = new Bundle();
        bundle.putInt("ENObjectKind", enObjectType.GetIndexValue());

        bundle.putInt("PointX", ptPos.x);
        bundle.putInt("PointY", ptPos.y);
        bundle.putString("DBName", strDBName);

        messageShow.setData(bundle);
        messageShow.what = 0;

        HandlerObjectSelPopup.sendMessage(messageShow);
    }*/

    //2020/01/13 - 가격차트,지표,신호,구간등 선택 처리 하지 않음
    /*private Handler HandlerObjectSelPopup = new Handler() {
        public void handleMessage(Message m) {
            switch (m.what)
            {
                case 0:
                    int[] nLocationPosArray = new int[2];
                    m_cChartCtrl.GetCurrentChartView().getLocationInWindow(nLocationPosArray);
                    int nViewTop = nLocationPosArray[1];

                    Bundle bundle = m.getData();
                    final ENObjectKindType enObjectKindType = ENObjectKindType.GetEnumValue(bundle.getInt("ENObjectKind"));

                    int nX = bundle.getInt("PointX");
                    int nY = bundle.getInt("PointY");
                    nY += nViewTop;
                    final String strDBName = bundle.getString("DBName");

                    if (m_dlgLongPressPopWnd != null && m_dlgLongPressPopWnd.isShowing())
                    {
                        HandlerObjectSelPopup.removeMessages(1);
                        m_dlgLongPressPopWnd.dismiss();
                    }

                    View vwPopup = LayoutInflater.from(m_contCompareFragment).inflate(R.layout.dialog_longpress_function, null);
                    TextView tvTitle = (TextView) vwPopup.findViewById(R.id.Tv_LongPress_Title);
                    Button btnFunc1 = (Button) vwPopup.findViewById(R.id.Btn_LongPress_Func1);
                    Button btnFunc2 = (Button) vwPopup.findViewById(R.id.Btn_LongPress_Func2);
                    Button btnFunc3 = (Button) vwPopup.findViewById(R.id.Btn_LongPress_Func3);

                    if (enObjectKindType == ENObjectKindType.GE_OBJECT_KIND_PRICE)
                    {
                        tvTitle.setText("가격차트");
                        btnFunc1.setText("속성");
                        btnFunc2.setText("닫기");
                        btnFunc3.setVisibility(View.GONE);

                        btnFunc1.setOnClickListener(new OnClickListener() {
                            @Override
                            public void onClick(View v) {    // 가격 차트 속성
                                DoConfigPropertyEvent(false, -1, "", enObjectKindType, ENFUNCToolType.GE_FUNCTOOL_CONFIG_SINGLE_PRC, true);
                                LongPressPopWndDismiss();
                            }
                        });

                        btnFunc2.setOnClickListener(new OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                LongPressPopWndDismiss();
                            }
                        });
                    }
                    else if (enObjectKindType == ENObjectKindType.GE_OBJECT_KIND_INDICA ||
                            enObjectKindType == ENObjectKindType.GE_OBJECT_KIND_VOLUME)
                    {
                        if (enObjectKindType == ENObjectKindType.GE_OBJECT_KIND_INDICA)
                            tvTitle.setText("지표");
                        else
                            tvTitle.setText("거래량");

                        btnFunc1.setText("삭제");
                        btnFunc2.setText("속성");
                        btnFunc3.setText("닫기");

                        btnFunc1.setOnClickListener(new OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                m_cChartCtrl.GetCurrentChartView().DelFmIndicaObj(ENObjectYScaleType.GE_OBJECT_YSCALE_LOGIC, strDBName, -1, true);
                                LongPressPopWndDismiss();
                            }
                        });

                        btnFunc2.setOnClickListener(new OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                DoConfigPropertyEvent(false, -1, strDBName, enObjectKindType, ENFUNCToolType.GE_FUNCTOOL_CONFIG_SINGLE_IND, true);
                                LongPressPopWndDismiss();
                            }
                        });

                        btnFunc3.setOnClickListener(new OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                LongPressPopWndDismiss();
                            }
                        });
                    }
                    m_dlgLongPressPopWnd = new PopupWindow(vwPopup, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
                    m_dlgLongPressPopWnd.setAnimationStyle(-1); // 애니메이션 설정(-1:설정안함, 0:설정)

                    m_dlgLongPressPopWnd.showAtLocation(m_cChartCtrl.GetChartView(0), Gravity.TOP | Gravity.LEFT, nX - 200, nY - 100);
                    m_dlgLongPressPopWnd.setOutsideTouchable(true);

                    Message messageDismiss = new Message();
                    messageDismiss.what = 1;
                    HandlerObjectSelPopup.sendMessageDelayed(messageDismiss, 3000);
                    break;
                case 1:
                    LongPressPopWndDismiss();
                    break;
            }
        }
    };*/

/*private boolean m_bOverActivityMode = false;

    @Override
    public void onStart()
    {
        super.onStart();
        //2016/07/11 [강민석] 속성설정 Activty Over하는경우 데이터 처리
        if (m_bOverActivityMode == false)
        {
            InitChartRequests(false);
            m_cDataHandler.DoStart();
        }

        //2016/07/11 onActivityResult 이후에 onResume 호출됨
        if (m_bOverActivityMode)
            m_bOverActivityMode = false;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause()
    {
        super.onPause();
        if (m_bOverActivityMode == false)
        {
            m_cDataHandler.CloseDataHandler();
            m_cDataHandler.DoStop();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
    }
*/

/*
 *********************
 **
 ** Dialog Event & Progress Functions
 **
 *********************/
/*private class ChartInfoChangeAsyncTask extends AsyncTask<String, String, String>
{
    private Context contProgress = null;
    private ProgressDlg dlgProgress = null;
    private ChartView chartViewCurrent = null;
    private int nCurrentChartID = -1;

    public ChartInfoChangeAsyncTask(Context contProgress)
    {
        this.contProgress = contProgress;
        dlgProgress = new ProgressDlg(contProgress);
        dlgProgress.SetBackColorDrawable(Color.TRANSPARENT);

        chartViewCurrent = m_cChartCtrl.GetCurrentChartView();
        nCurrentChartID = chartViewCurrent.GetViewCtrlID();
    }

    @Override
    protected void onCancelled()
    {
        if (dlgProgress != null) dlgProgress.dismiss();
        super.onCancelled();
    }

    @Override
    protected void onPreExecute()
    {
        if (dlgProgress != null || dlgProgress.isShowing() == false)
        {
            dlgProgress.SetMessageTextColor(Color.WHITE);
            dlgProgress.SetMessage("");
            dlgProgress.show();
        }
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... params)
    {
        if (this.isCancelled()) return null;

        if (params[0].equals("ChartFrameLoading"))
        {
            dlgProgress.SetMessage("차트 로딩중...");
            publishProgress(params[0], params[1]);
        }
        else if (params[0].equals("ChangeProperty"))
        {
            dlgProgress.SetMessage("설정 적용중...");
            m_cChartCtrl.ChangeCurrentChartGlobalProperty(ENConfigProperty.GE_CONFIG_PROPERTY_NONE, ENPropertyAfterType.GE_PROPERTY_AFTER_REDRAW);
            //chartViewCurrent.ChangeChartGlobalProperty(ENConfigProperty.GE_CONFIG_PROPERTY_NONE, ENPropertyAfterType.GE_PROPERTY_AFTER_NONE);
            publishProgress(params[0]);
            return params[0];
        }
        else if (params[0].equals("PeriodChange"))
        {
            dlgProgress.SetMessage("데이터 수신중...");
            m_cChartCtrl.SetCommRequestByID(nCurrentChartID, m_stNowCommRqInfo, ENChartDataEventType.GE_DATA_EVENT_PERIOD_VAL);
            m_cChartCtrl.SetPacketPeriodTypeByID(nCurrentChartID, m_enPacketPeriod);
            publishProgress(params[0]);
        }

        return null;
    }

    @Override
    protected void onProgressUpdate(String... values)
    {
        if (values[0].equals("ChartFrameLoading"))
        {
            String strLCWPath = values[1];
            int nID = m_cChartCtrl.GetCurrentChartID();
            m_cDataHandler.CancelAllRequest(nID);
            if (SerializeChart(strLCWPath, 1, 0, true) == true)
            {
                MakeCodeListAll();
                ChangeAllCode();
            }
        }
        else if (values[0].equals("ChangeProperty"))
        {
            //PGLog.e("ChangeProperty");
            m_cChartCtrl.RedrawCurrentChart();
        }
        else if (values[0].equals("PeriodChange"))
        {
            m_cDataHandler.RequestBaseData(chartViewCurrent.GetViewCtrlID(), m_stNowCommRqInfo.strCode, m_stNowCommRqInfo.strName,
                    m_stNowCommRqInfo.dStartDT[m_stNowCommRqInfo.nPeriod],m_stNowCommRqInfo.dEndDT[m_stNowCommRqInfo.nPeriod],
                    m_stNowCommRqInfo.nTotCount[m_stNowCommRqInfo.nPeriod],
                    m_stNowCommRqInfo.nMarket, m_stNowCommRqInfo.nPeriod, m_stNowCommRqInfo.nPrdValue[m_stNowCommRqInfo.nPeriod], m_stNowCommRqInfo.nRealInfo,
                    m_stNowCommRqInfo.nMarketGubun,ENRequestQueryType.GE_REQUEST_QUERY_ALL.GetIndexValue());
        }
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(String result)
    {
        if (result != null && result.equals("ChangeProperty"))
            LoadXMLPrdValueList(m_enPacketPeriod, true, true);

        if (dlgProgress != null) dlgProgress.dismiss();

        super.onPostExecute(result);
    }
}*/