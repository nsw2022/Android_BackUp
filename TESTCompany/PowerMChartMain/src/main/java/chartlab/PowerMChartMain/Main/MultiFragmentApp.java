/**
 * Copyright 2013 ChartLab Co., Ltd.  All Rights Reserved.                              *
 * Copyright 2013   All Rights Reserved.                        						*
 * *
 * Redistribution and use in source and binary forms, with or without modification,     *
 * are prohibited without the written permission of ChartLab Co., Ltd.                  *
 * 000회사는 본 저작물에 대하여 사용권 및 개작권을 가집니다.                       		*
 *
 * @file MultiFragmentApp
 * @brief .
 * @author
 * @date 2013-06-26 오후 2:33:44<br><br>
 * @Section History <b>Modification History :<b><br>
 */
/**
 @file MultiFragmentApp
 @brief .
 @author
 @date 2013-06-26 오후 2:33:44<br><br>
 @Section History <b>Modification History :<b><br>
 */

package chartlab.PowerMChartMain.Main;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcel;
//import android.support.annotation.RequiresApi;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TableLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.Objects;

import chartlab.PowerMChartApp.Util.FragmentAppMain;
import chartlab.PowerMChartEngine.BuildConfig;
import chartlab.PowerMChartMain.DataWnd.DataHandler;
import chartlab.PowerMChartApp.Config.ConfigObjParam;
import chartlab.PowerMChartApp.Config.ConfigMultiFragmentActivity;
import chartlab.PowerMChartApp.Config.ConfigMainPropertyActivity;
import chartlab.PowerMChartApp.Config.ConfigSingleFragmentActivity;
import chartlab.PowerMChartApp.Util.BaseApplication;
import chartlab.PowerMChartApp.FrameWnd.ChartCtrl;
import chartlab.PowerMChartApp.FrameWnd.ChartView;
import chartlab.PowerMChartApp.ToolBar.TaskBarLinearLayout;
import chartlab.PowerMChartApp.ToolBar.ToolBarLinearLayout;
import chartlab.PowerMChartApp.Widget.UtilControl.MessageBox;
import chartlab.PowerMChartApp.Dialog.Chart.DataProgressDlg;
import chartlab.PowerMChartApp.Dialog.Chart.FrameLoadDlg;
import chartlab.PowerMChartApp.Dialog.Chart.FrameSaveDlg;
import chartlab.PowerMChartApp.Dialog.Chart.InitChartInfoDlg;
import chartlab.PowerMChartApp.Dialog.Chart.FrameMultiTypeDlg;
import chartlab.PowerMChartApp.Widget.IconListBox.IconListAdapter;
import chartlab.PowerMChartApp.Widget.IconListBox.IconListAdapter.ListIconClickHandler;
import chartlab.PowerMChartApp.Widget.IconListBox.IconListBox;
import chartlab.PowerMChartApp.Widget.IconListBox.IconListItem;
import chartlab.PowerMChartApp.Widget.Items.TaskBarItem;
import chartlab.PowerMChartApp.Widget.ListAdapt.ChartTypeItemAdapter;
import chartlab.PowerMChartApp.Widget.ListAdapt.PeriodValueItemAdapter;
import chartlab.PowerMChartApp.Widget.TreeView.TreeNode;
import chartlab.PowerMChartEngine.KernelCore.GlobalDefines;
import chartlab.PowerMChartEngine.KernelCore.GlobalDirFile;
import chartlab.PowerMChartEngine.KernelCore.GlobalEnums;
import chartlab.PowerMChartEngine.KernelCore.GlobalEnums.ENANALToolType;
import chartlab.PowerMChartEngine.KernelCore.GlobalEnums.ENChartDataEventType;
import chartlab.PowerMChartEngine.KernelCore.GlobalEnums.ENChartOCXEventType;
import chartlab.PowerMChartEngine.KernelCore.GlobalEnums.ENChartStandbyType;
import chartlab.PowerMChartEngine.KernelCore.GlobalEnums.ENConfigProperty;
import chartlab.PowerMChartEngine.KernelCore.GlobalEnums.ENFUNCToolType;
import chartlab.PowerMChartEngine.KernelCore.GlobalEnums.ENFragmentFrameType;
import chartlab.PowerMChartEngine.KernelCore.GlobalEnums.ENMarketCategoryType;
import chartlab.PowerMChartEngine.KernelCore.GlobalEnums.ENObjectKindType;
import chartlab.PowerMChartEngine.KernelCore.GlobalEnums.ENObjectYScaleType;
import chartlab.PowerMChartEngine.KernelCore.GlobalEnums.ENPacketPeriodType;
import chartlab.PowerMChartEngine.KernelCore.GlobalEnums.ENPriceKindType;
import chartlab.PowerMChartEngine.KernelCore.GlobalEnums.ENResizeObjectList;
import chartlab.PowerMChartEngine.KernelCore.GlobalEnums.ENPropertyAfterType;
import chartlab.PowerMChartEngine.KernelCore.GlobalEnums.ENRequestQueryType;
import chartlab.PowerMChartEngine.KernelCore.GlobalEnums.ENTitleDspType;
import chartlab.PowerMChartEngine.KernelCore.GlobalEnums.ENPacketDataType;
import chartlab.PowerMChartEngine.KernelCore.GlobalProperty.CProperty_BASE;
import chartlab.PowerMChartEngine.KernelCore.GlobalStructs.ST_ANALPOINT_INFO;
import chartlab.PowerMChartEngine.KernelCore.GlobalStructs.ST_COMM_REQUEST;
import chartlab.PowerMChartEngine.KernelCore.GlobalUtils;
import chartlab.PowerMChartEngine.Util.ColorNames;
import chartlab.PowerMChartEngine.Util.FrameSkins;

public class MultiFragmentApp extends FragmentAppMain implements OnClickListener
{
    private enum ENDropCtrlType
    {
        LE_CODE_COMBO_LIST,
        LE_PERIOD_VALUE_MIN,
        LE_PERIOD_VALUE_TICK,
        LE_TASK_BAR,
        LE_TOOL_BAR,
        LE_TAB_BAR,
        LE_CHART_SYNC,
        LE_MULTI_FRAME_DLG,
        LE_LONG_PRESS_POPUP_DLG,
        LE_LAND_SAVE_TOOL,
        LE_DROP_CTRL_ALL
    }

    private final int GD_FRAME_LIST_COUNT = 24;
    private final int GD_CONFIG_RESULT_CODE = 0;
    private final int GD_ANALYSIS_CONFIG_RESULT_CODE = 1;
    private final int GD_CONFIG_MAINPROPERTY = 2;
    private final int GD_PERIOD_BUTTON_COUNT = 5;
    private final int GD_PRDVALUE_COUNT = 6;

    private final int GD_HANDLER_ANAL_POPUP_SHOW = 0;
    private final int GD_HANDLER_ANAL_POPUP_HIDE = 1;

    private final int GD_BTN_PERIOD_DAILY = 0;
    private final int GD_BTN_PERIOD_WEEKLY = 1;
    private final int GD_BTN_PERIOD_MONTHLY = 2;
    private final int GD_BTN_PERIOD_MINUTE = 3;
    private final int GD_BTN_PERIOD_TICK = 4;

    public static Context m_contMultiFragment = null;
    private View m_vwMultiFragment = null;
    private FrameLoadDlg m_dlgFrameLoad = null;
    private FrameSaveDlg m_dlgFrameSave = null;

    // 화면 모드 관련 변수
    private boolean m_bIsLandScape = false;
    private boolean m_bViewFullScreen = false;
    private boolean m_bDataTraceVisible = false;

    private boolean m_bNeedHandlerPopupWnd = false;

    private String m_strPathChtSys = "";
    private String m_strPathChtUser = "";
    private String m_strChtTRNumber = "";

    //2020/01/16 - 현재는 별 필요 없으나 추후 Progress를 사용할때 텍스트 변경시 필요할듯해서 놔둠
    //private TextView m_tvProgressMsg = null;
    private MessageBox m_cMessageBoxHandler = null;
    private AlertDialog m_dlgCustomAlert = null;

    private LinearLayout m_llChartBase = null;
    private LinearLayout m_llChartSub1 = null;
    private LinearLayout m_llChartSub2 = null;

    //현재가 표시 변수
    private TextView m_tvTopBarCurPrice = null;
    private TextView m_tvTopBarDiff = null;
    private TextView m_tvTopBarVol = null;
    private TextView m_tvTopBarRatio = null;

    //코드 관련 변수
    private IconListBox m_iconCodeCtrl = null;
    private IconListAdapter m_adapCodeCtrl = null;

    //Frame 관련 변수
    private IconListBox m_iconFrameType = null;

    //ButtonUI 변수
    private ImageButton m_btnBack = null;
    private ImageButton m_btnFullScreen = null;
    private ImageButton m_btnInitSys = null;
    private ImageButton m_btnConfig = null;
    private ImageButton m_btnLandConfig = null;

    private ImageButton m_btnCrossHair = null;
    private ImageButton m_btnChartSync = null;
    private ImageButton m_btnSaveLoad = null;
    private ImageButton m_btnLandInitSaveTool = null;

    //주기 관련 변수
    private ArrayList<IconListItem> m_iconPrdValueItemList = null;

    private ArrayList<Integer> m_nMinPrdValueList = new ArrayList<Integer>();
    private ArrayList<Integer> m_nTickPrdValueList = new ArrayList<Integer>();

    private ENPacketPeriodType m_enPacketPeriod = ENPacketPeriodType.GE_PACKET_PERIOD_DAILY;
    private ToggleButton[] m_btnPeriodList = null;

    //하단 탭바 관련 변수
    private LinearLayout m_llTabBar = null;
    private ToggleButton m_btnTabBarList[] = new ToggleButton[6];
    private float m_fTouchDnTabBtnPos[] = new float[2];

    //좌/우 팝업 관련 변수
    private boolean m_bTaskBarVisible = false;
    private boolean m_bToolBarVisible = false;

    private ImageView m_imgVwTaskBar = null;
    private ImageView m_imgVwToolBar = null;

    private TaskBarLinearLayout m_llTaskBar = null;
    private ToolBarLinearLayout m_llToolBar = null;

    //PopWnd 관련 변수
    private PopupWindow m_dlgPrdValueMinPopWnd = null;
    private PopupWindow m_dlgPrdValueTickPopWnd = null;
    private PopupWindow m_dlgSyncPopWnd = null;
    private PopupWindow m_dlgLandBtnsPopWnd = null;
    private PopupWindow m_dlgLongPressPopWnd = null;
    private FrameMultiTypeDlg m_dlgFrameType = null;
    private PopupWindow m_dlgTabBarBtnPopWnd = null;
    private DataProgressDlg m_dlgDataProgress = null;

    //데이터 관련 변수
    private String m_strLinkCode = "";
    private String m_strLinkCodeName = "";
    private String m_strPrevPrice = "";
    private ST_COMM_REQUEST m_stNowCommRqInfo = null;
    private DataHandler m_cDataHandler = null;

    private ChartCtrl m_cChartCtrl = null;

    private FragmentAppMain m_cFragmentAppMain = null;

    private ENANALToolType m_enANALToolType = ENANALToolType.GE_ANALTOOL_NONE;

    private boolean m_bTitleLongPress = false;

    private boolean m_bIsSimpleMain = false;
    private boolean m_bIsAnalContinue = false;
    private int     m_nMainConfigType = 0;

    ConfigObjParam  m_pConfigInfoObj = null;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //this.overridePendingTransition(R.anim.anim_right_to_center_in, R.anim.anim_center_to_right_out);

        m_contMultiFragment = getActivity();

        m_pConfigInfoObj = new ConfigObjParam("","","");

        assert m_contMultiFragment != null;
        m_bIsLandScape = BaseApplication.GetOrientationLandScape(m_contMultiFragment);

        m_cFragmentAppMain = this;
        m_cFragmentAppMain.SetFragmentMain(new InterfaceFragmentMainEvent()
        {
            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel parcel, int i) {
            }

            ////////////////////////////////////////////////////////////////////////////////////
            //OCX Event
            @Override
            public void OnGetChartLinkEvent(ENChartOCXEventType enOCXEvent, int nID, ST_COMM_REQUEST stCommRequest, int nValue,String strValue) {
                int nPrdValue = -1;
                int nRQType = 0;
                int nViewSize = -1;
                int nTotCount = -1;
                boolean bChangeCode = false;

                switch (enOCXEvent) {
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
                    case GE_OCX_EVENT_REQUEST_SCR_MORE:
                        ST_COMM_REQUEST stNewCommRequest = m_cChartCtrl.GetChartCommInfoByID(nID);
                        m_cDataHandler.RequestBaseData(nID, stNewCommRequest.strCode, stNewCommRequest.strName,
                            stNewCommRequest.dStartDT[stNewCommRequest.nPeriod],stNewCommRequest.dEndDT[stNewCommRequest.nPeriod],
                            stNewCommRequest.nTotCount[stNewCommRequest.nPeriod],stNewCommRequest.nMarket,
                            stNewCommRequest.nPeriod, stNewCommRequest.nPrdValue[stNewCommRequest.nPeriod], stNewCommRequest.nRealInfo,
                            stNewCommRequest.nMarketGubun,ENRequestQueryType.GE_REQUEST_QUERY_MAIN.GetIndexValue());
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
                        m_cDataHandler.CancelRequest(nID,strValue);
                        break;
                    case GE_OCX_EVENT_OVERLAP_DELETE:
                        break;
                    case GE_OCX_EVENT_QUERY_RECEIVE:
                        break;
                    case GE_OCX_EVENT_QUERY_COMPLETE:
                        break;
                    case GE_OCX_EVENT_CHANGE_PRICE:
                        if(nValue < ENPriceKindType.GE_PRICE_KIND_THREEBREAK.GetIndexValue())
                            SetToolBarBtnStatus(true);
                        else
                            SetToolBarBtnStatus(false);

                        boolean bChangeNormalANALPrice = strValue.equals("1");
                        if(bChangeNormalANALPrice == true) ResetTaskBarInfo(true);
                        break;
                    case GE_OCX_EVENT_CHART_DELETE:
                        //m_cDataHandler.CancelRequest(nID,"ALL");
//                        DeleteChartFrame(nID);
                        //주의:	먼저 조회 정보 다 정리하고 Frame변화한다
                        break;
                    case GE_OCX_EVENT_CHANGE_FRAME:
                        break;
                    case GE_OCX_EVENT_CHANGE_COUNT:
                        if (nID == m_cChartCtrl.GetCurrentChartID()) {
                            nViewSize = m_cChartCtrl.GetCurrentChartView().GetPacketViewSize();
                            if (m_stNowCommRqInfo.nPeriod >= 0)
                                m_stNowCommRqInfo.nViewCount[m_stNowCommRqInfo.nPeriod] = nViewSize;

                            nTotCount = m_cChartCtrl.GetCurrentChartView().GetPacketTotSize();
                            //m_cMessageBoxHandler.SetMessageBoxStatus("View Size - " + nViewSize, 1000, Gravity.CENTER, 0, 300).ShowMessage();
                            m_cMessageBoxHandler.SetMessageBoxStatus(nViewSize + "/" + nTotCount, 1000, Gravity.CENTER, 0, 300).ShowMessage();

						/*
						if(nTotCount > 0)
						{
							strViewSize = String.format("%d",nTotCount);
							m_stNowCommRqInfo.nTotCount[m_stNowCommRqInfo.m_nPeriod] = nTotCount;
						}
						*/
                        }
                        break;
                    case GE_OCX_EVENT_CHANGE_FOCUS: {
                        stCommRequest.CopyObject(m_cChartCtrl.GetChartCommInfoByID(nID));
                        if (stCommRequest.nPeriod >= 0) {
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
                    case GE_OCX_EVENT_FRAME_SYNC:
                        break;
                    //2018/05/29 - 추세선 초기화
                    case GE_OCX_EVENT_ANAL_INIT:
                        if(m_enANALToolType != ENANALToolType.GE_ANALTOOL_NONE)
                        {
                            m_llToolBar.InitAnalysisPress(true);
                            m_cMessageBoxHandler.SetMessageBoxStatus("추세선 선택 해제", 1500, Gravity.CENTER, 0, 300).ShowMessage();
                            m_enANALToolType = ENANALToolType.GE_ANALTOOL_NONE;
                        }

                        if(m_bIsAnalContinue == false) OnToolBarHideEvent();
                        break;
                    case GE_OCX_EVENT_CURPRICE:
                        DoChangeCurPrice(nValue);
                        break;
                    case GE_OCX_EVENT_REQUEST_MARKET:

                        /*if(stMarketInfo == null) { return; }

                        ST_COMM_REQUEST stNewCommRequest = m_cChartCtrl.GetChartCommInfoByID(nID);

                        int nYearDays = 0;
                        String strBaseCode = stNewCommRequest.strCode;
                        if(stNewCommRequest != null)
                        { strBaseCode = m_cDataHandler.GetMarketBaseCode(stMarketInfo.strName, stNewCommRequest.strCode); }

                        //2014/06/13 - 투자자 누적 같은 경우 현재 데이터 갯수대로 요청해야 한다
                        stNewCommRequest.nTotCount[stNewCommRequest.nPeriod] = m_cChartCtrl.GetPacketTotSize(nID);
                        int nPrdValue = stNewCommRequest.nPrdValue[stNewCommRequest.nPeriod];
                        m_cDataHandler.RequestMarketData(nID, stMarketInfo.strName, stMarketInfo.strQueryTR, stMarketInfo.strRealTR, strBaseCode,
                            stNewCommRequest.dStartDT[stNewCommRequest.nPeriod], stNewCommRequest.dEndDT[stNewCommRequest.nPeriod],
                        stNewCommRequest.nTotCount[stNewCommRequest.nPeriod], stNewCommRequest.nPeriod, nPrdValue, nYearDays,
                            stNewCommRequest.nMarket, stMarketInfo.strETCInfo, stMarketInfo.nETCInfo, stMarketInfo.nUseGubun,
                            stMarketInfo.nDataGubun, stMarketInfo.nCalc);
                        break;*/
                    case GE_OCX_EVENT_MENUBAR_REFRESH:
                        ResetTaskBarInfo(false);
                        break;
                    default:
                        break;
                }
            }

            @Override
            //속성 창 Main에서 현재 차트의 속성을 Get 하는 함수
            public CProperty_BASE OnGetChartGlobalPropertyEvent(boolean bReloading, boolean bWorkspace) {
                return m_cChartCtrl.GetCurrentChartView().OnGetChartGlobalPropertyEvent(bReloading, bWorkspace);
            }
            ////////////////////////////////////////////////////////////////////////////////////
            //DataTraceDlg
            @Override
            public boolean OnDataCrossBtnStatusEvent() {
                return m_bDataTraceVisible;
            }

            ////////////////////////////////////////////////////////////////////////////////////
            //BottomBar
            @Override
            public void OnChangeBtmBarStatusEvent() {
                int nFrameCount = m_cChartCtrl.GetChartFrameSize();
                if (nFrameCount <= 1) return;

                boolean bFrameMaxStatus = m_cChartCtrl.IsFrameMaxStatus();

                if (bFrameMaxStatus == true)
                    m_cChartCtrl.SetFrameMaxStatus(false);
                else
                    m_cChartCtrl.SetFrameMaxStatus(true);

                if (bFrameMaxStatus == true)
                    m_llTabBar.setVisibility(View.GONE);
                else
                    m_llTabBar.setVisibility(View.VISIBLE);

                int nNowChartIndex = m_cChartCtrl.GetFocusFrameIndex();
                for (int i = 0; i < nFrameCount; i++) {
                    if (bFrameMaxStatus == false) {
                        if (i != nNowChartIndex)
                            m_cChartCtrl.GetChartView(i).setVisibility(View.GONE);
                        else
                            m_cChartCtrl.GetChartView(i).setVisibility(View.VISIBLE);
                    } else {
                        m_cChartCtrl.GetChartView(i).setVisibility(View.VISIBLE);
                    }
                }

                if (bFrameMaxStatus == false) ResetTabBarSubButton(nFrameCount);

                ResetChartFrame();
            }

            ////////////////////////////////////////////////////////////////////////////////////
            //TaskBar
            @Override
            public void OnTaskBarHideEvent() {
                if (m_bTaskBarVisible == false) return;

                m_bTaskBarVisible = false;
                m_llTaskBar.setVisibility(View.GONE);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
                    m_imgVwTaskBar.setBackground(FrameSkins.m_gradBtnUnsel30);
                else
                    m_imgVwTaskBar.setBackgroundDrawable(FrameSkins.m_gradBtnUnsel30);
            }

            ////////////////////////////////////////////////////////////////////////////////////
            //ToolBar
            @Override
            public void OnToolBarHideEvent() {
                if (m_bToolBarVisible == false) return;

                if(m_enANALToolType == ENANALToolType.GE_ANALTOOL_NONE || m_bIsAnalContinue == false)
                {
                    m_bToolBarVisible = false;
                    m_llToolBar.setVisibility(View.GONE);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
                        m_imgVwToolBar.setBackground(FrameSkins.m_gradBtnUnsel26);
                    else
                        m_imgVwToolBar.setBackgroundDrawable(FrameSkins.m_gradBtnUnsel26);
                }
            }

            @Override
            public void OnToggleToolBarEvent(int nToolType, int nIndex, boolean bCheck) {
                //기능툴바
                if(nToolType == 0)
                {

                }
                //추세선툴바
                else if(nToolType == 1)
                {
                    if (m_bIsSimpleMain == true)
                        m_llToolBar.ToggleAnalysisPress(nToolType + 1, bCheck, true);
                    else
                        m_llToolBar.ToggleAnalysisPress(nToolType, bCheck, true);
                }
            }
            ////////////////////////////////////////////////////////////////////////////////////
            //ANAL Tool
            @Override
            public void OnANALSelPopupDlgEvent(ENANALToolType enANALToolType, Point ptPos) {
                Message messageShow = new Message();
                Bundle bundle = new Bundle();
                bundle.putInt("ENANALToolType", enANALToolType.GetIndexValue());

                bundle.putInt("PointX", ptPos.x);
                bundle.putInt("PointY", ptPos.y);

                messageShow.setData(bundle);
                messageShow.what = GD_HANDLER_ANAL_POPUP_SHOW;

                HandlerANALSelPopUp.sendMessage(messageShow);
            }

            @Override
            public ST_ANALPOINT_INFO OnGetSelANALInfoEvent() {
                return m_cChartCtrl.GetCurrentChartView().GetCurrentANALProperty();
            }

            @Override
            public void OnSetSelANALInfoEvent(ST_ANALPOINT_INFO stANALToolInfo) {
                m_cChartCtrl.GetCurrentChartView().ChangeCurrentANALProperty(stANALToolInfo);
            }

            ////////////////////////////////////////////////////////////////////////////////////
            //기타 함수

            @Override
            public int OnFindXDateIndexEvent(double dXDateTime, int nStart, int nIfNoEqualNearIndex) {
                return m_cChartCtrl.GetCurrentChartView().FindDateTimeIndex(dXDateTime, nStart, nIfNoEqualNearIndex);
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        m_vwMultiFragment = inflater.inflate(R.layout.fragment_multi, container, false);
        return m_vwMultiFragment;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        InitMultiFragment();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        InitMultiFragment();
    }

    private View.OnLongClickListener LongClickMainTitleListener = new View.OnLongClickListener() {

        @Override
        public boolean onLongClick(View pView) {
            // Do something when your hold starts here.

            GetFragmentMain().OnTaskBarHideEvent();
            GetFragmentMain().OnToolBarHideEvent();

            m_bTitleLongPress = true;
            return true;
        }
    };

    private View.OnTouchListener ClickMainTitleListener = new View.OnTouchListener() {

        @SuppressLint("ClickableViewAccessibility")
        @Override
        public boolean onTouch(View pView, MotionEvent pEvent) {
            pView.onTouchEvent(pEvent);
            // We're only interested in when the button is released.
            if (pEvent.getAction() == MotionEvent.ACTION_UP) {
                // We're only interested in anything if our speak button is currently pressed.
                if (m_bTitleLongPress == true) {

                    // Do something when the button is released.
                    Intent intent = new Intent(m_contMultiFragment, ConfigMainPropertyActivity.class);
                    intent.putExtra("UserPath", m_strPathChtUser);
                    intent.putExtra("SysPath", m_strPathChtSys);
                    intent.putExtra("TRNumber", m_strChtTRNumber);
                    startActivityForResult(intent,GD_CONFIG_MAINPROPERTY);

                    m_bTitleLongPress = false;
                }
            }
            return false;
        }
    };

    private void InitMultiFragment() {
        m_llChartBase = m_vwMultiFragment.findViewById(R.id.Linear_Chart);
        m_llChartSub1 = new LinearLayout(m_contMultiFragment);
        m_llChartSub2 = new LinearLayout(m_contMultiFragment);

        //m_tvProgressMsg = m_vwMultiFragment.findViewById(R.id.Tv_ProgressMSG);

        m_cMessageBoxHandler = new MessageBox(m_contMultiFragment, m_vwMultiFragment);

        InitChartCtrl();
        InitDataHandler();

        InitTextView(); //현재가 셋팅
        InitButton(); //버튼 셋팅
        InitListBox(); //리스트 박스 셋팅
        InitTopBar();
        //화면 초기에 띄우지 않고 클릭하면 띄움
        //InitTaskBar(); //좌측 팝업 셋팅
        //InitToolBar(); //우측 팝업 셋팅

        if (SerializeChart("", 1, -1, true) == false) {
            //주의:Serialize로딩에서 실패(File NotFound등)일경우 기본차트 로딩
            //m_cChartCtrl.ChangeFrameSize(-1, 0,false);
        }

        m_stNowCommRqInfo = m_cChartCtrl.GetCurrentCommInfo();

        if(m_bIsSimpleMain == true)
        {
            m_iconFrameType.setVisibility(View.GONE);
            m_btnChartSync.setVisibility(View.GONE);
        }

        LoadXMLPrdValueList(m_enPacketPeriod,true,true);

        InitChartRequests(false);

		/*SetCurCodeName(m_stNowCommRqInfo.m_strName);
		m_nMarketType = m_stNowCommRqInfo.m_nMarket;
		if(ENMarketCategoryType.GE_MARKET_CATE_STOCK.Compare(m_nMarketType))
			m_ctrlBtnMarketType.setText("주식");
		else if(ENMarketCategoryType.GE_MARKET_CATE_INDUSTRY.Compare(m_nMarketType))
			m_ctrlBtnMarketType.setText("업종");

		if(m_stNowCommRqInfo.m_nPeriod != -1)
		{
			int m_nPrdValue = -1;
			if(m_stNowCommRqInfo.m_nPeriod <= ENPacketPeriodType.GE_PACKET_PERIOD_MINUTE.GetIndexValue())
			{
				m_nPrdValue = m_stNowCommRqInfo.m_nPrdValue[m_stNowCommRqInfo.m_nPeriod];
				for(int i=0; i<m_nArrMinPrdValues.length; i++)
				{
					if(m_nArrMinPrdValues[i] == m_nPrdValue)
					{
						m_nLastPrdValue = i;
						break;
					}
				}
			}
			ChangePeriodButton(m_stNowCommRqInfo.m_nPeriod, -1, false);
		}*/
    }

    /*
     *********************
     **
     ** Activity Controls Init Functions
     **
     *********************/

    private void InitChartCtrl() {
        m_cChartCtrl = new ChartCtrl();
        m_cChartCtrl.InitChartCtrl(ENFragmentFrameType.GE_FRAGMENT_FRAME_MULTI, this, m_contMultiFragment);

        String m_strPathChtUserRoot = GlobalUtils.GetStorageDirPath() + GlobalDefines.GD_ROOT_DIRECTORY;
        m_strPathChtSys = GlobalUtils.GetStorageDirPath() + GlobalDefines.GD_CHARTINFO_DIRECTORY;
        m_strPathChtUser = GlobalUtils.GetStorageDirPath() + GlobalDefines.GD_CHARTINFO_USER_DIRECTORY;
        m_strChtTRNumber = GlobalDefines.GD_CHART_TR_MULTI;

        m_cChartCtrl.SetPathChtUserRoot(m_strPathChtUserRoot);
        m_cChartCtrl.SetPathChtSys(m_strPathChtSys);
        m_cChartCtrl.SetPathChtUser(m_strPathChtUser);
        m_cChartCtrl.SetChartTRNumber(m_strChtTRNumber);

        m_cChartCtrl.CreateDirectory();

        m_stNowCommRqInfo = new ST_COMM_REQUEST();

        m_cChartCtrl.SetLandScapeMode(m_bIsLandScape);

        m_dlgDataProgress = new DataProgressDlg(m_contMultiFragment);

        m_bIsAnalContinue = GlobalUtils.GetPrivateProfileInt(m_strPathChtUser,m_strPathChtSys,GlobalDefines.GD_CHARTINFO_MAINPROPERTY,"AnalContinue") == 1;
        m_bIsSimpleMain = GlobalUtils.GetPrivateProfileInt(m_strPathChtUser,m_strPathChtSys,GlobalDefines.GD_CHARTINFO_MAINPROPERTY,"SimpleMain",0) == 1;
        m_nMainConfigType = GlobalUtils.GetPrivateProfileInt(m_strPathChtUser,m_strPathChtSys,GlobalDefines.GD_CHARTINFO_MAINPROPERTY,"ConfigType",0);
    }

    private void InitTextView() {
        m_tvTopBarCurPrice = m_vwMultiFragment.findViewById(R.id.Tv_TopBar_CurPrice);
        m_tvTopBarDiff = m_vwMultiFragment.findViewById(R.id.Tv_TopBar_Diff);
        m_tvTopBarVol = m_vwMultiFragment.findViewById(R.id.Tv_TopBar_Vol);
        m_tvTopBarRatio = m_vwMultiFragment.findViewById(R.id.Tv_TopBar_Ratio);
    }

    private void InitButton()
    {
        m_btnChartSync = m_vwMultiFragment.findViewById(R.id.Btn_TopBar_ChartSync);
        m_btnCrossHair = m_vwMultiFragment.findViewById(R.id.Btn_TopBar_CrossHair);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
            m_btnChartSync.setBackground(FrameSkins.m_gradBtnUnsel26);
        else
            m_btnChartSync.setBackgroundDrawable(FrameSkins.m_gradBtnUnsel26);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
            m_btnCrossHair.setBackground(FrameSkins.m_gradBtnUnsel26);
        else
            m_btnCrossHair.setBackgroundDrawable(FrameSkins.m_gradBtnUnsel26);

        m_btnChartSync.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v)
            {
                m_bNeedHandlerPopupWnd = true;
                if (m_dlgSyncPopWnd == null || m_dlgSyncPopWnd.isShowing() == false) {
                    ShowAllDropCtrls(true,ENDropCtrlType.LE_CHART_SYNC,true);
                    m_btnChartSync.setBackgroundColor(FrameSkins.m_crBtnSel);
                    ShowDlgPopupEvent("SyncPopupWnd");
                }
                else {
                    ShowAllDropCtrls(true, ENDropCtrlType.LE_CHART_SYNC);
                }
            }
        });

        //m_btnChartSync.setOnClickListener(this);
        m_btnCrossHair.setOnClickListener(this);

        ////////////////////////////////////////////////////////////////////////////////////////
        m_btnPeriodList = new ToggleButton[GD_PERIOD_BUTTON_COUNT];
        m_btnPeriodList[0] = m_vwMultiFragment.findViewById(R.id.Btn_TopBar_PrdDay);
        m_btnPeriodList[0].setTag(ENPacketPeriodType.GE_PACKET_PERIOD_DAILY.GetIndexValue());
        m_btnPeriodList[1] = m_vwMultiFragment.findViewById(R.id.Btn_TopBar_PrdWeek);
        m_btnPeriodList[1].setTag(ENPacketPeriodType.GE_PACKET_PERIOD_WEEKLY.GetIndexValue());
        m_btnPeriodList[2] = m_vwMultiFragment.findViewById(R.id.Btn_TopBar_PrdMonth);
        m_btnPeriodList[2].setTag(ENPacketPeriodType.GE_PACKET_PERIOD_MONTHLY.GetIndexValue());
        m_btnPeriodList[3] = m_vwMultiFragment.findViewById(R.id.Btn_TopBar_PrdMinute);
        m_btnPeriodList[3].setTag(ENPacketPeriodType.GE_PACKET_PERIOD_MINUTE.GetIndexValue());
        m_btnPeriodList[4] = m_vwMultiFragment.findViewById(R.id.Btn_TopBar_PrdTick);
        m_btnPeriodList[4].setTag(ENPacketPeriodType.GE_PACKET_PERIOD_TICK.GetIndexValue());

        int i = 0;
        for (i = 0; i < GD_PERIOD_BUTTON_COUNT; i++)
        {
            m_btnPeriodList[i].setOnClickListener(this);

            //if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
            //    m_btnPeriodList[i].setBackground(FrameSkins.m_gradSegControlSel);
            //else
            //    m_btnPeriodList[i].setBackgroundDrawable(FrameSkins.m_gradSegControlSel);

            m_btnPeriodList[i].setBackgroundColor(FrameSkins.m_crBtnSel);
        }
        m_btnPeriodList[GD_BTN_PERIOD_DAILY].setChecked(true);

        ////////////////////////////////////////////////////////////////////////////////////////
        m_llTabBar = m_vwMultiFragment.findViewById(R.id.Linear_TabBar);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
            m_llTabBar.setBackground(FrameSkins.m_gradSegControlUnsel);
        else
            m_llTabBar.setBackgroundDrawable(FrameSkins.m_gradSegControlUnsel);
        m_llTabBar.setVisibility(View.GONE);

        m_btnTabBarList[0] = m_vwMultiFragment.findViewById(R.id.Btn_TabBar_1);
        m_btnTabBarList[1] = m_vwMultiFragment.findViewById(R.id.Btn_TabBar_2);
        m_btnTabBarList[2] = m_vwMultiFragment.findViewById(R.id.Btn_TabBar_3);
        m_btnTabBarList[3] = m_vwMultiFragment.findViewById(R.id.Btn_TabBar_4);
        m_btnTabBarList[4] = m_vwMultiFragment.findViewById(R.id.Btn_TabBar_5);
        m_btnTabBarList[5] = m_vwMultiFragment.findViewById(R.id.Btn_TabBar_6);

        // add both a touch listener and a long click listener
        m_btnTabBarList[0].setOnTouchListener(OnTouchTabBarBtns);
        m_btnTabBarList[1].setOnTouchListener(OnTouchTabBarBtns);
        m_btnTabBarList[2].setOnTouchListener(OnTouchTabBarBtns);
        m_btnTabBarList[3].setOnTouchListener(OnTouchTabBarBtns);
        m_btnTabBarList[4].setOnTouchListener(OnTouchTabBarBtns);
        m_btnTabBarList[5].setOnTouchListener(OnTouchTabBarBtns);

        for (i = 0;i<m_btnTabBarList.length;i++) {
            //m_btnTabBarList[t].setTextSize(8);
            m_btnTabBarList[i].setTextSize(10);
            //m_btnTabBarList[i].setOnClickListener(this);

            final int finalI = i;
            m_btnTabBarList[i].setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    m_bNeedHandlerPopupWnd = true;
                    if (m_dlgTabBarBtnPopWnd == null || m_dlgTabBarBtnPopWnd.isShowing() == false) {
                        ShowAllDropCtrls(true,ENDropCtrlType.LE_TAB_BAR,true);

                        Message messageShow = new Message();
                        Bundle bundle = new Bundle();
                        bundle.putInt("BtnIndex", finalI);

                        bundle.putInt("PointX", (int)m_fTouchDnTabBtnPos[0]);
                        bundle.putInt("PointY", (int)m_fTouchDnTabBtnPos[1]);

                        messageShow.setData(bundle);
                        messageShow.what = 0;

                        HandlerTabBarPopUp.sendMessage(messageShow);
                        //ShowDlgPopupEvent("TabBarPopupWnd", finalI);
                    }
                    else {
                        ShowAllDropCtrls(true, ENDropCtrlType.LE_TAB_BAR);
                    }

                    return true;
                }
            });

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
                m_btnTabBarList[i].setBackground(FrameSkins.m_gradSegControlSel);
            else
                m_btnTabBarList[i].setBackgroundDrawable(FrameSkins.m_gradSegControlSel);
        }

        ////////////////////////////////////////////////////////////////////////////////////////
        if (m_cChartCtrl.GetChartFrameSize() > 0)
            m_enPacketPeriod = m_cChartCtrl.GetCurrentChartView().GetPacketPeriodType();

        if (m_bIsLandScape == false) //세로모드 인 경우
        {
            m_btnBack = m_vwMultiFragment.findViewById(R.id.Btn_TitleBar_Back);
            m_btnInitSys = m_vwMultiFragment.findViewById(R.id.Btn_TitleBar_InitSys);
            m_btnConfig = m_vwMultiFragment.findViewById(R.id.Btn_TitleBar_Config);
            m_btnSaveLoad = m_vwMultiFragment.findViewById(R.id.Btn_TopBar_SaveLoad);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
                m_btnSaveLoad.setBackground(FrameSkins.m_gradBtnUnsel26);
            else
                m_btnSaveLoad.setBackgroundDrawable(FrameSkins.m_gradBtnUnsel26);

            m_btnBack.setOnClickListener(this);
            m_btnInitSys.setOnClickListener(this);
            //m_btnConfig.setOnClickListener(this);
            m_btnSaveLoad.setOnClickListener(this);

            m_btnConfig.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    m_bNeedHandlerPopupWnd = true;
                    if(m_nMainConfigType != 1) {
                        ShowAllDropCtrls(true, ENDropCtrlType.LE_DROP_CTRL_ALL);
                        GetFragmentMain().OnTaskBarHideEvent();
                        GetFragmentMain().OnToolBarHideEvent();
                    }

                    //SingleMode
                    if(m_nMainConfigType == 1)
                    {
                        if (m_dlgLongPressPopWnd == null || m_dlgLongPressPopWnd.isShowing() == false)
                        {
                            ShowAllDropCtrls(true, ENDropCtrlType.LE_DROP_CTRL_ALL);
                            GetFragmentMain().OnTaskBarHideEvent();
                            GetFragmentMain().OnToolBarHideEvent();
                            ShowSingleMenuList();
                        }
                        else {
                            ShowAllDropCtrls(true, ENDropCtrlType.LE_LONG_PRESS_POPUP_DLG);
                        }
                    }
                    //SimpleMode
                    else if(m_nMainConfigType == 2) {
                        DoConfigPropertyEvent(true, -1, "기본설정", ENObjectKindType.GE_OBJECT_KIND_NONE, ENFUNCToolType.GE_FUNCTOOL_CONFIG_SIMPLE_VER, true);
                    }
                    else {
                    //MultMode
                        DoConfigPropertyEvent(true, -1, "", ENObjectKindType.GE_OBJECT_KIND_NONE, ENFUNCToolType.GE_FUNCTOOL_NONE, false);
                    }
                }
            });

            m_btnFullScreen = m_vwMultiFragment.findViewById(R.id.Btn_TitleBar_FullScreen);
            m_btnFullScreen.setOnClickListener(this);

            ChangeScreenMode();
        }
        else // 가로 모드 인 경우
        {
            m_btnLandInitSaveTool = m_vwMultiFragment.findViewById(R.id.Btn_TopBar_LandInitSaveTool);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
                m_btnLandInitSaveTool.setBackground(FrameSkins.m_gradBtnUnsel26);
            else
                m_btnLandInitSaveTool.setBackgroundDrawable(FrameSkins.m_gradBtnUnsel26);
            //m_btnLandInitSaveTool.setOnClickListener(this);

            //2020/01/14 - 각자 PopupWnd등을 아이폰 처럼 Toggle처리할려고 했는데 모든 이벤트
            m_btnLandInitSaveTool.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    m_bNeedHandlerPopupWnd = true;
                    //Log.d("Event","m_btnLandInitSaveTool - touch Down");
                    if (m_dlgLandBtnsPopWnd == null || m_dlgLandBtnsPopWnd.isShowing() == false) {
                        ShowAllDropCtrls(true,ENDropCtrlType.LE_LAND_SAVE_TOOL,true);
                        m_btnLandInitSaveTool.setBackgroundColor(FrameSkins.m_crBtnSel);
                        ShowDlgPopupEvent("LandInitSaveTool");
                    }
                    else
                    {
                        ShowAllDropCtrls(true,ENDropCtrlType.LE_LAND_SAVE_TOOL);
                    }
                }
            });
        }
    }

    View.OnTouchListener OnTouchTabBarBtns = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {

            // save the X,Y coordinates
            if (event.getActionMasked() == MotionEvent.ACTION_DOWN)
            {
                m_fTouchDnTabBtnPos[0] = event.getX();
                m_fTouchDnTabBtnPos[1] = event.getY();

                switch (v.getId())
                {
                    case R.id.Btn_TabBar_1: ChangeTabBarSubItem(0);  break;
                    case R.id.Btn_TabBar_2: ChangeTabBarSubItem(1);  break;
                    case R.id.Btn_TabBar_3: ChangeTabBarSubItem(2);  break;
                    case R.id.Btn_TabBar_4: ChangeTabBarSubItem(3);  break;
                    case R.id.Btn_TabBar_5: ChangeTabBarSubItem(4);  break;
                    case R.id.Btn_TabBar_6: ChangeTabBarSubItem(5);  break;
                }
            }

            // let the touch event pass on to whoever needs it
            return false;
        }
    };

    private void InitTopBar() {

        if (m_bIsSimpleMain == true) {
            m_iconFrameType.setVisibility(View.GONE);
            m_btnChartSync.setVisibility(View.GONE);
        }

        if (m_bIsLandScape == false) {
            RelativeLayout rlTitleBar = m_vwMultiFragment.findViewById(R.id.Relative_TitleBar);
            rlTitleBar.setBackgroundColor(FrameSkins.m_crNAVIBack);
            //타이틀바 변수
            TextView tvTitle = m_vwMultiFragment.findViewById(R.id.Tv_TitleBar_Title);
            tvTitle.setTextColor(FrameSkins.m_crNAVIText);
            tvTitle.setOnLongClickListener(LongClickMainTitleListener);
            tvTitle.setOnTouchListener(ClickMainTitleListener);
        }

        int [] nDefaultPrdValue = { 1, 3, 5, 10,15,30 };
        m_nMinPrdValueList.clear();
        m_nTickPrdValueList.clear();
        for(int i=0;i<nDefaultPrdValue.length;i++)
        {
            m_nMinPrdValueList.add(nDefaultPrdValue[i]);
            m_nTickPrdValueList.add(nDefaultPrdValue[i]);
        }

        m_llTaskBar = null;
        m_imgVwTaskBar = m_vwMultiFragment.findViewById(R.id.ImgVw_TopBar_TaskBar);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
            m_imgVwTaskBar.setBackground(FrameSkins.m_gradBtnUnsel30);
        else
            m_imgVwTaskBar.setBackgroundDrawable(FrameSkins.m_gradBtnUnsel30);

        m_imgVwTaskBar.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                m_bNeedHandlerPopupWnd = true;
                if (m_bTaskBarVisible == false) {
                    m_bTaskBarVisible = true;
                    if(InitTaskBar() == true) ResetTaskBarInfo();

                    m_llTaskBar.setVisibility(View.VISIBLE);
                    m_imgVwTaskBar.setBackgroundColor(FrameSkins.m_crBtnSel);

                    ShowAllDropCtrls(true,ENDropCtrlType.LE_TASK_BAR,true);
                } else if (m_bTaskBarVisible == true) {
                    m_bTaskBarVisible = false;
                    m_llTaskBar.setVisibility(View.GONE);

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
                        m_imgVwTaskBar.setBackground(FrameSkins.m_gradBtnUnsel30);
                    else
                        m_imgVwTaskBar.setBackgroundDrawable(FrameSkins.m_gradBtnUnsel30);
                }
            }
        });

        m_llToolBar = null;
        m_imgVwToolBar = m_vwMultiFragment.findViewById(R.id.ImgVw_TopBar_ToolBar);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
            m_imgVwToolBar.setBackground(FrameSkins.m_gradBtnUnsel26);
        else
            m_imgVwToolBar.setBackgroundDrawable(FrameSkins.m_gradBtnUnsel26);
        m_imgVwToolBar.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                m_bNeedHandlerPopupWnd = true;

                if (m_bToolBarVisible == false)
                {
                    m_bToolBarVisible = true;
                    if(InitToolBar() == true) ResetToolBarInfo();

                    m_llToolBar.setVisibility(View.VISIBLE);
                    m_imgVwToolBar.setBackgroundColor(FrameSkins.m_crBtnSel);
                    ShowAllDropCtrls(true,ENDropCtrlType.LE_TOOL_BAR,true);
                } else if (m_bToolBarVisible == true) {
                    m_bToolBarVisible = false;

                    if(m_enANALToolType != ENANALToolType.GE_ANALTOOL_NONE)
                    {
                        m_cChartCtrl.ChangeANALToolType(ENANALToolType.GE_ANALTOOL_CONTINUE, false, true);
                        m_llToolBar.InitAnalysisPress(true);
                        m_enANALToolType = ENANALToolType.GE_ANALTOOL_NONE;
                    }

                    m_llToolBar.setVisibility(View.GONE);

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
                        m_imgVwToolBar.setBackground(FrameSkins.m_gradBtnUnsel26);
                    else
                        m_imgVwToolBar.setBackgroundDrawable(FrameSkins.m_gradBtnUnsel26);
                }
            }
        });
    }

    private boolean InitTaskBar() {
        if(m_llTaskBar != null) return false;

//        m_imgVwTaskBar = m_vwMultiFragment.findViewById(R.id.ImgVw_TopBar_TaskBar);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
//            m_imgVwTaskBar.setBackground(FrameSkins.m_gradBtnUnsel30);
//        else
//            m_imgVwTaskBar.setBackgroundDrawable(FrameSkins.m_gradBtnUnsel30);
//
//        m_imgVwTaskBar.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                m_bNeedHandlerPopupWnd = true;
//                if (m_bTaskBarVisible == false) {
//                    m_bTaskBarVisible = true;
//                    m_llTaskBar.setVisibility(View.VISIBLE);
//                    m_imgVwTaskBar.setBackgroundColor(FrameSkins.m_crBtnSel);
//
//                    ShowAllDropCtrls(true,ENDropCtrlType.LE_TASK_BAR,true);
//                } else if (m_bTaskBarVisible == true) {
//                    m_bTaskBarVisible = false;
//                    m_llTaskBar.setVisibility(View.GONE);
//
//                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
//                        m_imgVwTaskBar.setBackground(FrameSkins.m_gradBtnUnsel30);
//                    else
//                        m_imgVwTaskBar.setBackgroundDrawable(FrameSkins.m_gradBtnUnsel30);
//                }
//            }
//        });

        m_llTaskBar = m_vwMultiFragment.findViewById(R.id.Linear_TaskBar);

        WindowManager wm = (WindowManager) m_contMultiFragment.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        if(m_bIsSimpleMain == false)
        {
            if (size.x < size.y)
                m_llTaskBar.getLayoutParams().width = (int) ((double) size.x * 0.533333);
            else
                m_llTaskBar.getLayoutParams().width = (int) ((double) size.y * 0.533333);
        } else {
            m_llTaskBar.SetVisibleChartSignalRegion(false);

            if (size.x < size.y)
                m_llTaskBar.getLayoutParams().width = (int) ((double) size.x * 0.6);
            else
                m_llTaskBar.getLayoutParams().width = (int) ((double) size.y * 0.6);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
            m_llTaskBar.setBackground(FrameSkins.m_gradTASKBack);
        else
            m_llTaskBar.setBackgroundDrawable(FrameSkins.m_gradTASKBack);
        m_llTaskBar.setVisibility(View.GONE);

        ENPriceKindType enPriceKindType = ENPriceKindType.GE_PRICE_KIND_CANDLE;
        if (m_cChartCtrl.GetChartFrameSize() > 0)
            enPriceKindType = m_cChartCtrl.GetCurrentChartView().GetPriceKindType();

        m_llTaskBar.SetPathChartSys(m_strPathChtSys);
        m_llTaskBar.ChangePriceKind(enPriceKindType,true);
        m_llTaskBar.BuildLeftTaskBar();
        m_llTaskBar.SetMainConfigType(m_nMainConfigType);

        int nMaxObjectCount = GlobalUtils.GetPrivateProfileInt(m_strPathChtUser,m_strPathChtSys,GlobalDefines.GD_CHARTINFO_MAINPROPERTY,"MaxObjectCount");
        m_llTaskBar.SetMaxObjectCount(nMaxObjectCount);

        m_llTaskBar.SetOnChartTypeChangeListener(new ChartTypeItemAdapter.OnChartTypeChangeListener() {
            @Override
            public void OnChartTypeChange(TaskBarItem taskBarItem, View view) {
                //String strPriceName = taskBarItem.getName();
                //ENPriceKindType enPriceKind = GlobalEnums.GetPriceKindType(priceName);

                if (view.getId() == R.id.ChartTypeAdapt_Btn_Config)
                    DoConfigPropertyEvent(true, -1, taskBarItem.getName(), ENObjectKindType.GE_OBJECT_KIND_NONE, ENFUNCToolType.GE_FUNCTOOL_CONFIG_SINGLE_PRC, true);

                ENPriceKindType enPriceKind = ENPriceKindType.GetEnumValue(taskBarItem.getIndex());
                if (enPriceKind != ENPriceKindType.GE_PRICE_KIND_NONE) {
                    m_cChartCtrl.GetCurrentChartView().ChangePriceKindType(enPriceKind, false);

                    m_cChartCtrl.RedrawCurrentChart();
                }
            }
        });

        m_llTaskBar.SetOnItemChangeListener(new TaskBarLinearLayout.OnItemChangeListener() {
            @Override
            public void OnItemChange(TreeNode.IndicaTreeItem indicaTreeItem, boolean bCheck, View v) {
                m_bNeedHandlerPopupWnd = true;
                if (v.getId() == chartlab.PowerMChartMain.Config.R.id.Btn_Node_Indica) {
                    DoConfigPropertyEvent(true, -1, indicaTreeItem.strName, ENObjectKindType.GetEnumValue(indicaTreeItem.enObjectKindType), ENFUNCToolType.GE_FUNCTOOL_CONFIG_SINGLE_IND, true);
                } else {
                    /*ENPriceKindType enPriceKind = m_cChartCtrl.GetCurrentChartView().GetPriceKindType();
                    if (enPriceKind.GetIndexValue() >= ENPriceKindType.GE_PRICE_KIND_THREEBREAK.GetIndexValue()) {
                        m_cMessageBoxHandler.SetMessageBoxStatus("분석차트에서는 지표를 추가/삭제 할수 없습니다", 1500, Gravity.CENTER, 0, 300).ShowMessage();
                        //Toast.makeText(m_contMultiFragment, "분석차트에서는 지표를 추가/삭제 할수 없습니다", Toast.LENGTH_SHORT).show();
                        //m_llTaskBar.ChangeIndicaCheck(indicaTreeItem.m_strName, false, true);
                        return;
                    }*/

                    try {
                        ENObjectYScaleType enObjectYScaleType = ENObjectYScaleType.GetEnumValue(indicaTreeItem.nYScale);
                        ENObjectKindType enObjectKindType = ENObjectKindType.GetEnumValue(indicaTreeItem.enObjectKindType);

                        if (bCheck)
                            m_cChartCtrl.GetCurrentChartView().AddFMChartObj(enObjectKindType, enObjectYScaleType, indicaTreeItem.strName, true);
                        else
                            m_cChartCtrl.GetCurrentChartView().DelFmIndicaObj(enObjectYScaleType, indicaTreeItem.strName, -1, true);
                    } catch (Exception e) {
                        if (bCheck)
                            m_cMessageBoxHandler.SetMessageBoxStatus("지표 추가에 실패 하였습니다", 1500, Gravity.CENTER, 0, 300).ShowMessage();
                        else
                            m_cMessageBoxHandler.SetMessageBoxStatus("지표 삭제에 실패 하였습니다", 1500, Gravity.CENTER, 0, 300).ShowMessage();
                        e.printStackTrace();
                    }
                }
            }
        });

        return true;
    }

    private boolean InitToolBar() {

        if(m_llToolBar != null) return false;

//        m_imgVwToolBar = m_vwMultiFragment.findViewById(R.id.ImgVw_TopBar_ToolBar);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
//            m_imgVwToolBar.setBackground(FrameSkins.m_gradBtnUnsel26);
//        else
//            m_imgVwToolBar.setBackgroundDrawable(FrameSkins.m_gradBtnUnsel26);
//        m_imgVwToolBar.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                m_bNeedHandlerPopupWnd = true;
//
//                if (m_bToolBarVisible == false) {
//                    m_bToolBarVisible = true;
//                    m_llToolBar.setVisibility(View.VISIBLE);
//                    m_imgVwToolBar.setBackgroundColor(FrameSkins.m_crBtnSel);
//                    ShowAllDropCtrls(true,ENDropCtrlType.LE_TOOL_BAR,true);
//                } else if (m_bToolBarVisible == true) {
//                    m_bToolBarVisible = false;
//
//                    if(m_enANALToolType != ENANALToolType.GE_ANALTOOL_NONE)
//                    {
//                        m_cChartCtrl.ChangeANALToolType(ENANALToolType.GE_ANALTOOL_CONTINUE, false, true);
//                        m_llToolBar.InitAnalysisPress(true);
//                        m_enANALToolType = ENANALToolType.GE_ANALTOOL_NONE;
//                    }
//
//                    m_llToolBar.setVisibility(View.GONE);
//
//                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
//                        m_imgVwToolBar.setBackground(FrameSkins.m_gradBtnUnsel26);
//                    else
//                        m_imgVwToolBar.setBackgroundDrawable(FrameSkins.m_gradBtnUnsel26);
//                }
//            }
//        });

        m_llToolBar = m_vwMultiFragment.findViewById(R.id.Linear_ToolBar);
        m_llToolBar.SetBackground(FrameSkins.m_crTOOLBackList, FrameSkins.m_crTOOLSelBackList);
        m_llToolBar.setVisibility(View.GONE);

        m_llToolBar.BuildRightToolBar();
        m_llToolBar.ChangeVisibleType(ToolBarLinearLayout.VISIBLETYPE_ANALYSIS);
        m_llToolBar.SetOnAnalysisIconPressListener(new IconListAdapter.OnIconPressListener() {
            @Override
            public void OnIconPress(int nIcon, String strName, String strValue, boolean isPressed, View viewIconListbox, View viewSelectItem) {
                m_bNeedHandlerPopupWnd = true;
                //int nSimpleMain = 0;
                int nPosition = Integer.parseInt(strValue);
                ENANALToolType enAnalToolType = ENANALToolType.GetEnumValue(nPosition);
                if (strName.equals("추세선툴바") || nPosition == ENANALToolType.GE_ANALTOOL_NONE.GetIndexValue())
                {
                    m_llToolBar.ChangeVisibleType(ToolBarLinearLayout.VISIBLETYPE_FUNCTION);
                    m_cMessageBoxHandler.SetMessageBoxStatus("기능 툴바", 1500, Gravity.CENTER, 0, 300).ShowMessage();
                    if(m_enANALToolType != ENANALToolType.GE_ANALTOOL_NONE)
                    {
                        m_cChartCtrl.ChangeANALToolType(ENANALToolType.GE_ANALTOOL_CONTINUE, false, true);
                        m_llToolBar.InitAnalysisPress(true);
                        m_enANALToolType = ENANALToolType.GE_ANALTOOL_NONE;
                    }
                }
                else
                {
                    ENANALToolType enPrevANALToolType = m_enANALToolType;
                    m_enANALToolType = enAnalToolType;
                    if (enPrevANALToolType != ENANALToolType.GE_ANALTOOL_NONE && enAnalToolType != ENANALToolType.GE_ANALTOOL_DELETE_ALL && enAnalToolType != ENANALToolType.GE_ANALTOOL_DELETE)
                    {
                        m_llToolBar.InitAnalysisPress(true);
                        if(m_enANALToolType == enPrevANALToolType)
                        {
                            m_cChartCtrl.ChangeANALToolType(ENANALToolType.GE_ANALTOOL_CONTINUE, false, true);
                            m_enANALToolType = ENANALToolType.GE_ANALTOOL_NONE;
                            return;
                        }
                    }

                    if (enAnalToolType != ENANALToolType.GE_ANALTOOL_DELETE_ALL && enAnalToolType != ENANALToolType.GE_ANALTOOL_DELETE)
                    {
                        //if (m_bIsSimpleMain == true)
                            m_llToolBar.ChangeAnalysisPress(nPosition + 1, isPressed, true);
                        //else
                        //    m_llToolBar.ChangeAnalysisPress(nPosition, isPressed, true);
                    }
                    else
                    {
                        m_enANALToolType = ENANALToolType.GE_ANALTOOL_NONE;
                    }

                    if (isPressed == true) {
                        String strAnaltoolName = GlobalEnums.GE_ANALTOOLTYPENAMES[enAnalToolType.GetIndexValue()];
                        if (enAnalToolType == ENANALToolType.GE_ANALTOOL_SHAPE_TRIANGLE ||
                                enAnalToolType == ENANALToolType.GE_ANALTOOL_ANDREW_PITCH ||
                                enAnalToolType == ENANALToolType.GE_ANALTOOL_STAT_ELLIOT_EX ||
                                enAnalToolType == ENANALToolType.GE_ANALTOOL_FIBO_TARGET ||
                                enAnalToolType == ENANALToolType.GE_ANALTOOL_CORE_TARGET_ENV ||
                                enAnalToolType == ENANALToolType.GE_ANALTOOL_CORE_TARGET_NT)
                            m_cMessageBoxHandler.SetMessageBoxStatus(strAnaltoolName + " - 세점을 찍으세요", 1500, Gravity.CENTER, 0, 300).ShowMessage();
                        else
                            m_cMessageBoxHandler.SetMessageBoxStatus(strAnaltoolName, 1500, Gravity.CENTER, 0, 300).ShowMessage();
                    }

                    //수치조회창 선택이 되고 추세선이 2(Trend)이상
                    if (m_bDataTraceVisible == true && nPosition >= 0 &&
                            m_enANALToolType != ENANALToolType.GE_ANALTOOL_DELETE_ALL && m_enANALToolType != ENANALToolType.GE_ANALTOOL_DELETE) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
                            m_btnCrossHair.setBackground(FrameSkins.m_gradBtnUnsel26);
                        else
                            m_btnCrossHair.setBackgroundDrawable(FrameSkins.m_gradBtnUnsel26);
                        m_bDataTraceVisible = false;
                        m_cChartCtrl.SetCrosshair(-1, 0);
                    }

                    if(isPressed == true || m_enANALToolType == ENANALToolType.GE_ANALTOOL_DELETE_ALL || m_enANALToolType == ENANALToolType.GE_ANALTOOL_DELETE)
                    {
                        m_cChartCtrl.ChangeANALToolType(enAnalToolType, true, true);
                        if(m_enANALToolType == ENANALToolType.GE_ANALTOOL_SYMBOL_LIST)
                        {
                            //확인 필요
                        }
                    }
                    else
                    {
                        m_cChartCtrl.ChangeANALToolType(enAnalToolType, false, true);
                    }
//                    if(enPrevANALToolType == m_enANALToolType)
//                        m_cChartCtrl.ChangeANALToolType(enAnalToolType, false, true);
//                    else
//                        m_cChartCtrl.ChangeANALToolType(enAnalToolType, isPressed, true);

                    if(m_enANALToolType == ENANALToolType.GE_ANALTOOL_DELETE_ALL || m_enANALToolType == ENANALToolType.GE_ANALTOOL_DELETE || isPressed == false)
                    {
                        if(m_enANALToolType == ENANALToolType.GE_ANALTOOL_DELETE_ALL || m_enANALToolType == ENANALToolType.GE_ANALTOOL_DELETE)
                        {
                            m_llToolBar.InitAnalysisPress(true);
                            m_cChartCtrl.ChangeANALToolType(ENANALToolType.GE_ANALTOOL_CONTINUE, false, true);
                        }

                        m_enANALToolType = ENANALToolType.GE_ANALTOOL_NONE;
                    }
                }
            }
        });

        m_llToolBar.SetOnFunctionIconPressListener(new IconListAdapter.OnIconPressListener() {
            @Override
            public void OnIconPress(int nIcon, String strName, String strValue, boolean isPressed, View viewIconListbox, View viewSelectItem) {
                m_bNeedHandlerPopupWnd = true;
                int nPosition = Integer.parseInt(strValue);
                if (strName.equals("기능툴바") || nPosition == ENANALToolType.GE_ANALTOOL_NONE.GetIndexValue()) {
                    m_llToolBar.ChangeVisibleType(ToolBarLinearLayout.VISIBLETYPE_ANALYSIS);
                    m_cMessageBoxHandler.SetMessageBoxStatus("추세선 툴바", 1500, Gravity.CENTER, 0, 300).ShowMessage();
                }
                else
                {
                    boolean bCheck = !isPressed;
                    int nFunctionToolTypeIndex = ENFUNCToolType.GE_FUNCTOOL_INIT_CREATE.GetIndexValue() + nPosition;
                    ENFUNCToolType enFuncTool = ENFUNCToolType.GetEnumValue(nFunctionToolTypeIndex);

                    if (enFuncTool == ENFUNCToolType.GE_FUNCTOOL_CONFIG_MULTI) {
                        DoConfigPropertyEvent(true, -1, "", ENObjectKindType.GE_OBJECT_KIND_NONE, ENFUNCToolType.GE_FUNCTOOL_NONE, false);
                    } else if (enFuncTool == ENFUNCToolType.GE_FUNCTOOL_CONFIG_SINGLE_IND ||
                            enFuncTool == ENFUNCToolType.GE_FUNCTOOL_CONFIG_SINGLE_GUIDE ||
                            enFuncTool == ENFUNCToolType.GE_FUNCTOOL_CONFIG_SINGLE_PRC) {
                        DoConfigPropertyEvent(false, -1, "", ENObjectKindType.GE_OBJECT_KIND_NONE, enFuncTool, true);
                    } else {
                        m_cChartCtrl.ChangeFUNCToolType(enFuncTool, bCheck ? 1 : 0);

                        //초기화등 On/Off 표시가 필요없으면 아래 Select False처리
                        if (enFuncTool != ENFUNCToolType.GE_FUNCTOOL_INIT_CREATE) {
                            m_llToolBar.ChangeFunctionPress(nPosition + 1, bCheck, true);

                            String strMsg = strName;
                            if (bCheck) strMsg += " On";
                            else strMsg += " Off";
                            m_cMessageBoxHandler.SetMessageBoxStatus(strMsg, 1500, Gravity.CENTER, 0, 300).ShowMessage();
                        } else if (enFuncTool == ENFUNCToolType.GE_FUNCTOOL_INIT_CREATE) {
                            m_cMessageBoxHandler.SetMessageBoxStatus(strName, 1500, Gravity.CENTER, 0, 300).ShowMessage();
                        }
                    }
                }
            }
        });

        return true;
    }

    private void InitListBox() {
        // CodeBox 설정
        m_iconCodeCtrl = m_vwMultiFragment.findViewById(R.id.Icon_TopBar_CodeCtrl);
        //2020/01/1 7 - 주의:아이폰과 코드콤보 선택 화살표가 다른데 현재 이미지가 없음(추후 이미지 교체하려면 여기서 작업)
        //m_iconCodeCtrl.SetIconBtnImageID(R.drawable.img_ico_expandablelist_group_down);
        m_iconCodeCtrl.SetBackground(FrameSkins.m_crBtnUnselList);
        //코드 관련 변수
        ArrayList<IconListItem> itemCodeCtrlList = new ArrayList<IconListItem>();

        itemCodeCtrlList.add(new IconListItem(0, "삼성전자", "005930", ENMarketCategoryType.PNX_CODE_TYPE_KOSPI_CODE.GetIndexValue()));
        itemCodeCtrlList.add(new IconListItem(0, "POSCO", "005490", ENMarketCategoryType.PNX_CODE_TYPE_KOSPI_CODE.GetIndexValue()));
        itemCodeCtrlList.add(new IconListItem(0, "현대차", "005380", ENMarketCategoryType.PNX_CODE_TYPE_KOSPI_CODE.GetIndexValue()));
        itemCodeCtrlList.add(new IconListItem(0, "셀트리온", "068270", ENMarketCategoryType.PNX_CODE_TYPE_KOSPI_CODE.GetIndexValue()));
        itemCodeCtrlList.add(new IconListItem(0, "안랩", "053800", ENMarketCategoryType.PNX_CODE_TYPE_KOSDAQ_CODE.GetIndexValue()));

        m_adapCodeCtrl = new IconListAdapter(m_contMultiFragment, itemCodeCtrlList, IconListAdapter.VISIBLE_TYPE_TEXT, m_iconCodeCtrl);

        m_iconCodeCtrl.setOnIconListBoxClickListener(new IconListBox.IconListBoxClickListener() {
            @Override
            public void setOnIcoListBoxClick(View ctrlIconListbox, View ctrlButton) {
                m_bNeedHandlerPopupWnd = true;
                if (m_iconCodeCtrl.isShown() == true)
                    ShowAllDropCtrls(true,ENDropCtrlType.LE_CODE_COMBO_LIST,true);
                else
                    m_iconCodeCtrl.dismiss();
            }
        });

        m_adapCodeCtrl.setItemClickHandler(new ListIconClickHandler() {
            public void setOnClick(int nIcon, String strName, String strValue, int nMarket, View selectView) {
                ChangeJongMokCode(strName, strValue, nMarket);
                ResetTabBarSubButtonTitle(-1);
                m_iconCodeCtrl.setText(strName);
                m_iconCodeCtrl.dismiss();
            }
        });

        m_iconCodeCtrl.setItemSize(itemCodeCtrlList.size());
        m_iconCodeCtrl.setDropDownAdapter(m_adapCodeCtrl);
        if (m_cChartCtrl.GetChartFrameSize() > 0) {
            m_iconCodeCtrl.setText(m_cChartCtrl.GetCurrentChartView().GetBaseCodeName());
            m_iconCodeCtrl.setValue(m_cChartCtrl.GetCurrentChartView().GetBaseCode());
        }
        m_iconCodeCtrl.setEnabled(true);

        // MultiBox 설정
        String[] arMultiValues = new String[GD_FRAME_LIST_COUNT];
        int nIndex = -1;
        for (int i = 0; i < GD_FRAME_LIST_COUNT; i++) {
            if (i == 3) nIndex = 10;
            else if (i == 9) nIndex = 20;
            else if (i == 16) nIndex = 30;
            else if (i == 20) nIndex = 40;
            arMultiValues[i] = String.valueOf(nIndex);

            nIndex++;
        }

        m_iconFrameType = m_vwMultiFragment.findViewById(R.id.Icon_TopBar_FrameType);
        m_iconFrameType.SetIsImageButton(false);
        m_iconFrameType.SetBackground(FrameSkins.m_crBtnUnselList);
        m_iconPrdValueItemList = new ArrayList<IconListItem>();

        m_iconFrameType.setItemSize(5);
        //m_iconFrameType.setText("1");
        //m_iconFrameType.setIcon(R.drawable.multi01);
        m_iconFrameType.ChangeOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v)
            {
                m_bNeedHandlerPopupWnd = true;
                ShowAllDropCtrls(true,ENDropCtrlType.LE_MULTI_FRAME_DLG,true);
                ShowDlgPopupEvent("MultiPopupWnd");
            }
        });
    }

    private void InitDataHandler() {
        m_cDataHandler = new DataHandler(m_contMultiFragment, m_cChartCtrl, ENFragmentFrameType.GE_FRAGMENT_FRAME_MULTI);
        //주의: 1000 -> 1초
		String strRealTimeDelay = GlobalUtils.GetPrivateProfileString(m_strPathChtUser,m_strPathChtSys, GlobalDefines.GD_CHARTINFO_MAINPROPERTY, "RealTimeDelay");

		m_cDataHandler.InitDataHandler(GlobalUtils.GetIntValue(strRealTimeDelay));
    }

    /*
     *********************
     **
     ** MultiChart BottomBar Event Functions
     **
     ********************
     * */

    public void DelChartFromTabBar(int nFrameIndex)
    {
        if(m_cChartCtrl.DeleteChartFrame(nFrameIndex) == false) return;

        int nID = m_cChartCtrl.GetChartID(nFrameIndex);
        m_cDataHandler.CancelRequest(nID,"ALL");

        int nChartFrameStatus = m_cChartCtrl.GetChartFrameStatus();
        int nFrameCount = m_cChartCtrl.GetChartFrameSize();
        m_cChartCtrl.ChangeFrameFocus(true);
        if(nFrameCount == 1) {
            ShowTabBarStatus(false);
            //아이폰과 소스 통일하기 위해서 막
            //m_cChartCtrl.SetFrameMaxStatus(false);
        }
        else
        {
            ResetTabBarSubButton(nFrameCount);
        }

        int nFrameStatusImgIndex = GetFrameStatusImgIndex(nChartFrameStatus);
        int nIconID = R.drawable.multi01 + nFrameStatusImgIndex;
        //m_iconFrameType.setText(" " + String.valueOf(nChartFrameSize));
        m_iconFrameType.setIcon(nIconID);

        //if(nFrameCount > 1)
        //    m_cChartCtrl.GetCurrentChartView().setVisibility(View.VISIBLE);
        //else
        //   ResetChartFrame();

        int nNowChartIndex = m_cChartCtrl.GetFocusFrameIndex();
        for(int i=0;i<nFrameCount;i++)
        {
            if (i != nNowChartIndex)
            { m_cChartCtrl.GetChartView(i).setVisibility(View.GONE); }
            else
            { m_cChartCtrl.GetChartView(i).setVisibility(View.VISIBLE); }
        }

        ResetChartFrame();
    }

    public void ChangeTabBarMaxMinStatus(boolean bFrameMaxStatus) {
        int nFrameCount = m_cChartCtrl.GetChartFrameSize();
        if (nFrameCount <= 1) return;

        if (bFrameMaxStatus == true && nFrameCount > 1)
        {
            //TabBarShow
            ShowTabBarStatus(true);
            ResetTabBarSubButton(m_cChartCtrl.GetChartFrameSize());
        }
        else
        {
            //TabBar - Hide
            ShowTabBarStatus(false);
        }

        int nNowChartIndex = m_cChartCtrl.GetFocusFrameIndex();
        for (int i = 0; i < nFrameCount; i++) {
            if (bFrameMaxStatus == true) {
                if (i != nNowChartIndex)
                    m_cChartCtrl.GetChartView(i).setVisibility(View.GONE);
                else
                    m_cChartCtrl.GetChartView(i).setVisibility(View.VISIBLE);
            } else {
                m_cChartCtrl.GetChartView(i).setVisibility(View.VISIBLE);
            }
        }

        ResetChartFrame();
    }

    //1 : Visible ,0 : InVisible
//    public void ShowTabBarStatus(int nVisibleStatus) {
//        if (m_llTabBar == null) return;
//
//        int nFrameCount = m_cChartCtrl.GetChartFrameSize();
//        if (nFrameCount > 1) {
//            m_llTabBar.setVisibility(nVisibleStatus);
//            ResetTabBarSubButton(nFrameCount);
//        } else {
//            m_llTabBar.setVisibility(nVisibleStatus);
//            //2020/01/15 - 분할프레임 아닐때 TabBar처리 안함(그냥 Gone등만 처리)
//            //ResetTabBarSubButton(nFrameCount);
//        }
//    }

    public void ShowTabBarStatus(boolean bVisibleStatus) {
        if (m_llTabBar == null) return;

        //int nFrameCount = m_cChartCtrl.GetChartFrameSize();
        //if (nFrameCount > 1) {
        if(bVisibleStatus == true)
        {
            if(m_llTabBar.isShown() == false)
            {
                m_llTabBar.setVisibility(View.VISIBLE);
                //이 함수 아래에 다시 호출한
                //ResetTabBarSubButton(nFrameCount);

                //주의:아이폰은 ResizeChart로 해당 Bound를 다시 설정해야 하지만 안드로이드는 그냥 둔다
            }
        }
        else
        {
            if(m_llTabBar.isShown() == true)
            {
                m_llTabBar.setVisibility(View.GONE);
                //2020/01/15 - 분할프레임 아닐때 TabBar처리 안함(그냥 Gone등만 처리)
                //ResetTabBarSubButton(nFrameCount);

                //주의:아이폰은 ResizeChart로 해당 Bound를 다시 설정해야 하지만 안드로이드는 그냥 둔다
            }
        }
    }

    public void ResetTabBarSubButton(int nFrameSize)
    {
        if(m_cChartCtrl.IsFrameMaxStatus() == false) return;

        String strViewTitle = "";
        int nViewBtn = nFrameSize - 1;
        for (int i = 0; i < m_btnTabBarList.length; i++) {
            if (nViewBtn >= i) {
                m_btnTabBarList[i].setVisibility(View.VISIBLE);
                strViewTitle = m_cChartCtrl.GetChartView(i).GetChartTitle();
                if (nFrameSize == 2) m_btnTabBarList[i].setTextSize(14);
                else if (nFrameSize == 3) m_btnTabBarList[i].setTextSize(12);
                else if (nFrameSize == 4) m_btnTabBarList[i].setTextSize(10);
                else if (nFrameSize == 5) m_btnTabBarList[i].setTextSize(9);
                else if (nFrameSize == 6) m_btnTabBarList[i].setTextSize(8);

                m_btnTabBarList[i].setText(strViewTitle);
                m_btnTabBarList[i].setTextOn(strViewTitle);
                m_btnTabBarList[i].setTextOff(strViewTitle);

                if (i == m_cChartCtrl.GetFocusFrameIndex())
                    m_btnTabBarList[i].setChecked(true);
                else
                    m_btnTabBarList[i].setChecked(false);

                if (m_btnTabBarList[i].isChecked())
                    m_btnTabBarList[i].setBackgroundColor(FrameSkins.m_crBtnSel);
                else
                    m_btnTabBarList[i].setBackgroundColor(FrameSkins.m_crBtnUnsel);
            } else {
                m_btnTabBarList[i].setVisibility(View.GONE);
            }
        }
    }

    public void ResetTabBarSubButtonTitle(int nFrameIndex)
    {
        String strViewTitle = "";
        int nChartFrameSize = m_cChartCtrl.GetChartFrameSize();

        for (int i = 0; i < nChartFrameSize; i++) {
            if (nFrameIndex < 0 || nFrameIndex == i) {
                strViewTitle = m_cChartCtrl.GetChartView(i).GetChartTitle();
                m_btnTabBarList[i].setText(strViewTitle);
                m_btnTabBarList[i].setTextOn(strViewTitle);
                m_btnTabBarList[i].setTextOff(strViewTitle);
            }
        }
    }

    private void ResetChartFrame() {
		/* 2016/07/06 [강민석]
		    기존 TableLayout, TableRow를 이용하여 동적으로 구성한 ChartFrame 변경방식에서
		    Linearlayout Orientation 변경방식으로 변경으로 레이아웃 감소, Add, remove 횟수 감소, 로직 감소 로 속도 증가
		    추후 Relative 혹은 LayoutInflater 방식을 이용한 방법도 고려
		*/
        m_llChartSub1.removeAllViews();
        m_llChartSub2.removeAllViews();
        m_llChartBase.removeAllViews();

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, 1);
        //분할차트 최대화 되었을때
        int nChartFrameSize = m_cChartCtrl.GetChartFrameSize();
        int nChartFrameStatus = m_cChartCtrl.GetChartFrameStatus();
        //if(nChartFrameSize > 1 && m_cChartCtrl.IsFrameMaxStatus() == true && m_llTabBar.getVisibility() == View.VISIBLE)
        if (nChartFrameSize > 1 && m_cChartCtrl.IsFrameMaxStatus() == true) {
            if (m_cChartCtrl.GetCurrentChartView().getVisibility() != View.VISIBLE) return;

            m_llChartBase.addView(m_cChartCtrl.GetCurrentChartView(), layoutParams);
            return;
        }

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        if (nChartFrameSize == 1) {
            m_llChartBase.addView(m_cChartCtrl.GetChartView(0), layoutParams);
        }
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        else if (nChartFrameSize == 2) {
            if (nChartFrameStatus == 0) {
                m_llChartBase.setOrientation(LinearLayout.HORIZONTAL);
                m_llChartBase.addView(m_cChartCtrl.GetChartView(0), layoutParams);
                m_llChartBase.addView(m_cChartCtrl.GetChartView(1), layoutParams);
            } else if (nChartFrameStatus == 1) {
                m_llChartBase.setOrientation(LinearLayout.VERTICAL);
                m_llChartBase.addView(m_cChartCtrl.GetChartView(0), layoutParams);
                m_llChartBase.addView(m_cChartCtrl.GetChartView(1), layoutParams);
            }
        }
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        else if (nChartFrameSize == 3) {
            if (nChartFrameStatus == 10) {
                m_llChartBase.setOrientation(LinearLayout.HORIZONTAL);
                m_llChartBase.addView(m_cChartCtrl.GetChartView(0), layoutParams);
                m_llChartBase.addView(m_cChartCtrl.GetChartView(1), layoutParams);
                m_llChartBase.addView(m_cChartCtrl.GetChartView(2), layoutParams);
            } else if (nChartFrameStatus == 11) {
                m_llChartBase.setOrientation(LinearLayout.VERTICAL);
                m_llChartBase.addView(m_cChartCtrl.GetChartView(0), layoutParams);
                m_llChartBase.addView(m_cChartCtrl.GetChartView(1), layoutParams);
                m_llChartBase.addView(m_cChartCtrl.GetChartView(2), layoutParams);
            } else if (nChartFrameStatus == 12) {
                m_llChartBase.setOrientation(LinearLayout.HORIZONTAL);

                m_llChartBase.addView(m_cChartCtrl.GetChartView(0), layoutParams);

                m_llChartSub1.setOrientation(LinearLayout.VERTICAL);
                m_llChartSub1.addView(m_cChartCtrl.GetChartView(1), layoutParams);
                m_llChartSub1.addView(m_cChartCtrl.GetChartView(2), layoutParams);
                m_llChartBase.addView(m_llChartSub1, layoutParams);
            } else if (nChartFrameStatus == 13) {
                m_llChartBase.setOrientation(LinearLayout.HORIZONTAL);

                m_llChartSub1.setOrientation(LinearLayout.VERTICAL);
                m_llChartSub1.addView(m_cChartCtrl.GetChartView(0), layoutParams);
                m_llChartSub1.addView(m_cChartCtrl.GetChartView(1), layoutParams);
                m_llChartBase.addView(m_llChartSub1, layoutParams);

                m_llChartBase.addView(m_cChartCtrl.GetChartView(2), layoutParams);
            } else if (nChartFrameStatus == 14) {
                m_llChartBase.setOrientation(LinearLayout.VERTICAL);

                m_llChartSub1.setOrientation(LinearLayout.HORIZONTAL);
                m_llChartSub1.addView(m_cChartCtrl.GetChartView(0), layoutParams);
                m_llChartSub1.addView(m_cChartCtrl.GetChartView(1), layoutParams);
                m_llChartBase.addView(m_llChartSub1, layoutParams);

                m_llChartBase.addView(m_cChartCtrl.GetChartView(2), layoutParams);
            } else if (nChartFrameStatus == 15) {
                m_llChartBase.setOrientation(LinearLayout.VERTICAL);

                m_llChartBase.addView(m_cChartCtrl.GetChartView(0), layoutParams);

                m_llChartSub1.setOrientation(LinearLayout.HORIZONTAL);
                m_llChartSub1.addView(m_cChartCtrl.GetChartView(1), layoutParams);
                m_llChartSub1.addView(m_cChartCtrl.GetChartView(2), layoutParams);
                m_llChartBase.addView(m_llChartSub1, layoutParams);
            }
        }
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        else if (nChartFrameSize == 4) {
            if (nChartFrameStatus == 20) {
                m_llChartBase.setOrientation(LinearLayout.HORIZONTAL);

                m_llChartBase.addView(m_cChartCtrl.GetChartView(0), layoutParams);
                m_llChartBase.addView(m_cChartCtrl.GetChartView(1), layoutParams);
                m_llChartBase.addView(m_cChartCtrl.GetChartView(2), layoutParams);
                m_llChartBase.addView(m_cChartCtrl.GetChartView(3), layoutParams);
            } else if (nChartFrameStatus == 21) {
                m_llChartBase.setOrientation(LinearLayout.VERTICAL);

                m_llChartBase.addView(m_cChartCtrl.GetChartView(0), layoutParams);
                m_llChartBase.addView(m_cChartCtrl.GetChartView(1), layoutParams);
                m_llChartBase.addView(m_cChartCtrl.GetChartView(2), layoutParams);
                m_llChartBase.addView(m_cChartCtrl.GetChartView(3), layoutParams);
            } else if (nChartFrameStatus == 22) {
                m_llChartBase.setOrientation(LinearLayout.HORIZONTAL);

                m_llChartBase.addView(m_cChartCtrl.GetChartView(0), layoutParams);

                m_llChartSub1.setOrientation(LinearLayout.VERTICAL);
                m_llChartSub1.addView(m_cChartCtrl.GetChartView(1), layoutParams);
                m_llChartSub1.addView(m_cChartCtrl.GetChartView(2), layoutParams);
                m_llChartSub1.addView(m_cChartCtrl.GetChartView(3), layoutParams);
                m_llChartBase.addView(m_llChartSub1, layoutParams);
            } else if (nChartFrameStatus == 23) {
                m_llChartBase.setOrientation(LinearLayout.HORIZONTAL);

                m_llChartSub1.setOrientation(LinearLayout.VERTICAL);
                m_llChartSub1.addView(m_cChartCtrl.GetChartView(0), layoutParams);
                m_llChartSub1.addView(m_cChartCtrl.GetChartView(1), layoutParams);
                m_llChartSub1.addView(m_cChartCtrl.GetChartView(2), layoutParams);
                m_llChartBase.addView(m_llChartSub1, layoutParams);

                m_llChartBase.addView(m_cChartCtrl.GetChartView(3), layoutParams);
            } else if (nChartFrameStatus == 24) {
                m_llChartBase.setOrientation(LinearLayout.VERTICAL);

                m_llChartSub1.setOrientation(LinearLayout.HORIZONTAL);
                m_llChartSub1.addView(m_cChartCtrl.GetChartView(0), layoutParams);
                m_llChartSub1.addView(m_cChartCtrl.GetChartView(1), layoutParams);
                m_llChartSub1.addView(m_cChartCtrl.GetChartView(2), layoutParams);
                m_llChartBase.addView(m_llChartSub1, layoutParams);

                m_llChartBase.addView(m_cChartCtrl.GetChartView(3), layoutParams);
            } else if (nChartFrameStatus == 25) {
                m_llChartBase.setOrientation(LinearLayout.VERTICAL);

                m_llChartBase.addView(m_cChartCtrl.GetChartView(0), layoutParams);

                m_llChartSub1.setOrientation(LinearLayout.HORIZONTAL);
                m_llChartSub1.addView(m_cChartCtrl.GetChartView(1), layoutParams);
                m_llChartSub1.addView(m_cChartCtrl.GetChartView(2), layoutParams);
                m_llChartSub1.addView(m_cChartCtrl.GetChartView(3), layoutParams);
                m_llChartBase.addView(m_llChartSub1, layoutParams);
            } else if (nChartFrameStatus == 26) {
                m_llChartBase.setOrientation(LinearLayout.VERTICAL);

                m_llChartSub1.setOrientation(LinearLayout.HORIZONTAL);
                m_llChartSub1.addView(m_cChartCtrl.GetChartView(0), layoutParams);
                m_llChartSub1.addView(m_cChartCtrl.GetChartView(1), layoutParams);
                m_llChartBase.addView(m_llChartSub1, layoutParams);

                m_llChartSub2.setOrientation(LinearLayout.HORIZONTAL);
                m_llChartSub2.addView(m_cChartCtrl.GetChartView(2), layoutParams);
                m_llChartSub2.addView(m_cChartCtrl.GetChartView(3), layoutParams);
                m_llChartBase.addView(m_llChartSub2, layoutParams);
            }
        }
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        else if (nChartFrameSize == 5) {
            if (nChartFrameStatus == 30) {
                m_llChartBase.setOrientation(LinearLayout.HORIZONTAL);

                m_llChartBase.addView(m_cChartCtrl.GetChartView(0), layoutParams);

                m_llChartSub1.setOrientation(LinearLayout.VERTICAL);
                m_llChartSub1.addView(m_cChartCtrl.GetChartView(1), layoutParams);
                m_llChartSub1.addView(m_cChartCtrl.GetChartView(3), layoutParams);
                m_llChartBase.addView(m_llChartSub1, layoutParams);

                m_llChartSub2.setOrientation(LinearLayout.VERTICAL);
                m_llChartSub2.addView(m_cChartCtrl.GetChartView(2), layoutParams);
                m_llChartSub2.addView(m_cChartCtrl.GetChartView(4), layoutParams);
                m_llChartBase.addView(m_llChartSub2, layoutParams);
            } else if (nChartFrameStatus == 31) {
                m_llChartBase.setOrientation(LinearLayout.VERTICAL);

                m_llChartSub1.setOrientation(LinearLayout.HORIZONTAL);
                m_llChartSub1.addView(m_cChartCtrl.GetChartView(0), layoutParams);
                m_llChartSub1.addView(m_cChartCtrl.GetChartView(1), layoutParams);
                m_llChartBase.addView(m_llChartSub1, layoutParams);

                m_llChartSub2.setOrientation(LinearLayout.HORIZONTAL);
                m_llChartSub2.addView(m_cChartCtrl.GetChartView(2), layoutParams);
                m_llChartSub2.addView(m_cChartCtrl.GetChartView(3), layoutParams);
                m_llChartBase.addView(m_llChartSub2, layoutParams);

                m_llChartBase.addView(m_cChartCtrl.GetChartView(4), layoutParams);
            } else if (nChartFrameStatus == 32) {
                m_llChartBase.setOrientation(LinearLayout.HORIZONTAL);

                m_llChartSub1.setOrientation(LinearLayout.VERTICAL);
                m_llChartSub1.addView(m_cChartCtrl.GetChartView(0), layoutParams);
                m_llChartSub1.addView(m_cChartCtrl.GetChartView(3), layoutParams);
                m_llChartBase.addView(m_llChartSub1, layoutParams);

                m_llChartBase.addView(m_cChartCtrl.GetChartView(1), layoutParams);

                m_llChartSub2.setOrientation(LinearLayout.VERTICAL);
                m_llChartSub2.addView(m_cChartCtrl.GetChartView(2), layoutParams);
                m_llChartSub2.addView(m_cChartCtrl.GetChartView(4), layoutParams);
                m_llChartBase.addView(m_llChartSub2, layoutParams);
            } else if (nChartFrameStatus == 33) {
                m_llChartBase.setOrientation(LinearLayout.VERTICAL);

                m_llChartSub1.setOrientation(LinearLayout.HORIZONTAL);
                m_llChartSub1.addView(m_cChartCtrl.GetChartView(0), layoutParams);
                m_llChartSub1.addView(m_cChartCtrl.GetChartView(1), layoutParams);
                m_llChartBase.addView(m_llChartSub1, layoutParams);

                m_llChartBase.addView(m_cChartCtrl.GetChartView(2), layoutParams);

                m_llChartSub2.setOrientation(LinearLayout.HORIZONTAL);
                m_llChartSub2.addView(m_cChartCtrl.GetChartView(3), layoutParams);
                m_llChartSub2.addView(m_cChartCtrl.GetChartView(4), layoutParams);
                m_llChartBase.addView(m_llChartSub2, layoutParams);
            }
        }
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        else if (nChartFrameSize == 6) {
            if (nChartFrameStatus == 40) {
                m_llChartBase.setOrientation(LinearLayout.VERTICAL);

                m_llChartSub1.setOrientation(LinearLayout.HORIZONTAL);
                m_llChartSub1.addView(m_cChartCtrl.GetChartView(0), layoutParams);
                m_llChartSub1.addView(m_cChartCtrl.GetChartView(1), layoutParams);
                m_llChartSub1.addView(m_cChartCtrl.GetChartView(2), layoutParams);
                m_llChartBase.addView(m_llChartSub1, layoutParams);

                m_llChartSub2.setOrientation(LinearLayout.HORIZONTAL);
                m_llChartSub2.addView(m_cChartCtrl.GetChartView(3), layoutParams);
                m_llChartSub2.addView(m_cChartCtrl.GetChartView(4), layoutParams);
                m_llChartSub2.addView(m_cChartCtrl.GetChartView(5), layoutParams);
                m_llChartBase.addView(m_llChartSub2, layoutParams);
            } else if (nChartFrameStatus == 41) {
                m_llChartBase.setOrientation(LinearLayout.HORIZONTAL);

                m_llChartSub1.setOrientation(LinearLayout.VERTICAL);
                m_llChartSub1.addView(m_cChartCtrl.GetChartView(0), layoutParams);
                m_llChartSub1.addView(m_cChartCtrl.GetChartView(2), layoutParams);
                m_llChartSub1.addView(m_cChartCtrl.GetChartView(4), layoutParams);
                m_llChartBase.addView(m_llChartSub1, layoutParams);

                m_llChartSub2.setOrientation(LinearLayout.VERTICAL);
                m_llChartSub2.addView(m_cChartCtrl.GetChartView(1), layoutParams);
                m_llChartSub2.addView(m_cChartCtrl.GetChartView(3), layoutParams);
                m_llChartSub2.addView(m_cChartCtrl.GetChartView(5), layoutParams);
                m_llChartBase.addView(m_llChartSub2, layoutParams);
            } else if (nChartFrameStatus == 42) {
                m_llChartBase.setOrientation(LinearLayout.HORIZONTAL);

                m_llChartBase.addView(m_cChartCtrl.GetChartView(0), layoutParams);

                m_llChartSub1.setOrientation(LinearLayout.VERTICAL);
                m_llChartSub1.addView(m_cChartCtrl.GetChartView(1), layoutParams);
                m_llChartSub1.addView(m_cChartCtrl.GetChartView(3), layoutParams);
                m_llChartBase.addView(m_llChartSub1, layoutParams);

                m_llChartSub2.setOrientation(LinearLayout.VERTICAL);
                m_llChartSub2.addView(m_cChartCtrl.GetChartView(2), layoutParams);
                m_llChartSub2.addView(m_cChartCtrl.GetChartView(4), layoutParams);
                m_llChartBase.addView(m_llChartSub2, layoutParams);

                m_llChartBase.addView(m_cChartCtrl.GetChartView(5), layoutParams);
            } else if (nChartFrameStatus == 43) {
                m_llChartBase.setOrientation(LinearLayout.VERTICAL);

                m_llChartBase.addView(m_cChartCtrl.GetChartView(0), layoutParams);

                m_llChartSub1.setOrientation(LinearLayout.HORIZONTAL);
                m_llChartSub1.addView(m_cChartCtrl.GetChartView(1), layoutParams);
                m_llChartSub1.addView(m_cChartCtrl.GetChartView(2), layoutParams);
                m_llChartBase.addView(m_llChartSub1, layoutParams);

                m_llChartSub2.setOrientation(LinearLayout.VERTICAL);
                m_llChartSub2.addView(m_cChartCtrl.GetChartView(3), layoutParams);
                m_llChartSub2.addView(m_cChartCtrl.GetChartView(4), layoutParams);
                m_llChartBase.addView(m_llChartSub2, layoutParams);

                m_llChartBase.addView(m_cChartCtrl.GetChartView(5), layoutParams);
            }
        }
    }

    private void InitChartRequests(boolean bLinkChart)
    {
        int nID = -1;
        int nCurrrentID = m_cChartCtrl.GetCurrentChartID();
        int nPrdVal = -1;
        String strCode = "";
        String strCodeName = "";
        ST_COMM_REQUEST stCommRequest = null;
        int nChartFrameSize = m_cChartCtrl.GetChartFrameSize();
        for (int i = 0; i < nChartFrameSize; i++) {
            nID = m_cChartCtrl.GetChartID(i);
            if (nID < 0) continue;

            stCommRequest = m_cChartCtrl.GetChartCommInfoByIndex(i);

            //m_nMarketType = stCommRequest.m_nMarket;
            nPrdVal = stCommRequest.nPrdValue[stCommRequest.nPeriod];
            if (nCurrrentID == nID && m_strLinkCode.isEmpty() == false && m_strLinkCodeName.isEmpty() == false) {
                strCode = m_strLinkCode;
                strCodeName = m_strLinkCodeName;
                m_stNowCommRqInfo.strCode = strCode;
                m_stNowCommRqInfo.strName = strCodeName;

                //종목명을 TextView로 제공하는 경우
                //SetCurCodeName(m_stNowCommRqInfo.m_strName);

                m_cChartCtrl.SetCommRequestByID(nID, m_stNowCommRqInfo, ENChartDataEventType.GE_DATA_EVENT_CODE_CHANGE);
            } else {
                strCode = stCommRequest.strCode;
                strCodeName = stCommRequest.strName;
            }

            m_cDataHandler.RequestBaseData(nID, strCode, strCodeName,
                    stCommRequest.dStartDT[stCommRequest.nPeriod],stCommRequest.dEndDT[stCommRequest.nPeriod],
                    stCommRequest.nTotCount[stCommRequest.nPeriod],stCommRequest.nMarket, stCommRequest.nPeriod,nPrdVal,
                    stCommRequest.nRealInfo,stCommRequest.nMarketGubun,ENRequestQueryType.GE_REQUEST_QUERY_ALL.GetIndexValue());
        }
    }

    /*
     *********************
     **
     ** LCW Serialize Functions - Load/Save
     **
     *********************/
    public boolean SerializeChart(String szName, int nLoadSave, int nEventSaveLoad, boolean bExitNoExist) {
        boolean bOldTaskBarVisibled = m_bTaskBarVisible;
        boolean bOldToolBarVisibled = m_bToolBarVisible;

        int nPrevChartFrameSize = m_cChartCtrl.GetChartFrameSize();
        if (m_cChartCtrl.SerializeChart(szName, nLoadSave, nEventSaveLoad, bExitNoExist) == false)
        {
            //차트 초기화 후 Last가 저장되어 있지 않으면 그냥 기본 차트로 로딩(오류 메세지 띄우지 않음)
            if(szName.isEmpty() == false)
                GlobalUtils.ToastHandlerMessage(m_contMultiFragment, "[오류]저장 정보 로딩중 오류가 발생했습니다. 기본차트로 로딩됩니다");

            m_cChartCtrl.ChangeFrameSize(-1, 0, false);
            //ShowTabBarStatus(View.GONE);
            ShowTabBarStatus(false);
            ResetChartFrame();

            m_cChartCtrl.RedrawCurrentChart();
            m_iconCodeCtrl.setText(m_cChartCtrl.GetCurrentChartView().GetBaseCodeName());

            ChangePeriodButtonChecked(m_enPacketPeriod, false);

            return false;
        }

        //Load
        if (nLoadSave == 1)
        {
            m_bTaskBarVisible = (m_cChartCtrl.GetTaskBarStatus() == 0 || m_cChartCtrl.GetTaskBarStatus() == 1);
            m_bToolBarVisible = (m_cChartCtrl.GetToolBarStatus() == 0 || m_cChartCtrl.GetToolBarStatus() == 1);

            //주의:이미 메뉴바,툴바가 나와있디면
            if (bOldTaskBarVisibled != m_bTaskBarVisible) {
                if (m_bTaskBarVisible) {
                    m_cChartCtrl.SetTaskBarStatus(0);    //Left
                    m_llTaskBar.setVisibility(View.VISIBLE);
                    m_imgVwTaskBar.setBackgroundColor(FrameSkins.m_crBtnSel);
                } else {
                    m_cChartCtrl.SetTaskBarStatus(2);    //Hide
                    m_llTaskBar.setVisibility(View.GONE);

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
                        m_imgVwTaskBar.setBackground(FrameSkins.m_gradBtnUnsel30);
                    else
                        m_imgVwTaskBar.setBackgroundDrawable(FrameSkins.m_gradBtnUnsel30);
                }
            }

            if (m_bToolBarVisible != bOldToolBarVisibled) {
                if (m_bToolBarVisible == true) {
                    m_cChartCtrl.SetToolBarStatus(1);    //right
                    m_llToolBar.setVisibility(View.VISIBLE);
                    m_imgVwToolBar.setBackgroundColor(FrameSkins.m_crBtnSel);
                } else {
                    m_cChartCtrl.SetToolBarStatus(2);    //Hide
                    m_llToolBar.setVisibility(View.GONE);

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
                        m_imgVwToolBar.setBackground(FrameSkins.m_gradBtnUnsel26);
                    else
                        m_imgVwToolBar.setBackgroundDrawable(FrameSkins.m_gradBtnUnsel26);
                }
            }

            //주의:Serialize후에 정보 다시 설정한다
            ResetTaskBarInfo();
            ResetToolBarInfo();

            int nChartFrameStatus = m_cChartCtrl.GetChartFrameStatus();
            int nFrameStatusImgIndex = GetFrameStatusImgIndex(nChartFrameStatus);
            int nIconID = R.drawable.multi01 + nFrameStatusImgIndex;
            //m_iconFrameType.setText(" " + String.valueOf(nChartFrameSize));
            m_iconFrameType.setIcon(nIconID);

            ResetChartFrame();

            int nChartFrameSize = m_cChartCtrl.GetChartFrameSize();
            boolean bChangeMaxStatus = m_cChartCtrl.IsFrameMaxStatus();

//            int nWorkSpace = 1;
//            if (nWorkSpace == 0 || (nWorkSpace == 1 && bChangeMaxStatus == false && nPrevChartFrameSize != nChartFrameSize)) {
//                ShowTabBarStatus(View.GONE);
//            } else if (nWorkSpace == 1 && bChangeMaxStatus == true) {
//                ChangeTabBarMaxMinStatus(bChangeMaxStatus);
//            }

            if (bChangeMaxStatus == false && nPrevChartFrameSize != nChartFrameSize) {
                ShowTabBarStatus(false);
            } else if (bChangeMaxStatus == true && nChartFrameSize > 1) {
                //ChangeTabBarMaxMinStatus(bChangeMaxStatus);
                ShowTabBarStatus(true);
                ResetTabBarSubButton(nChartFrameSize);
            }
        }

        return true;
    }

    public void DoConfigPropertyEvent(boolean bFromMenuPopup, int nSelPos, String strDBName, ENObjectKindType enSelObjKind, ENFUNCToolType enSelToolType, boolean bSingle) //속성 설정 .Popup(주의:Selpos - 0(Y축),1(X축))
    {
        Intent intent = null;
        if (bSingle == false) //일반 설정화면
        {
            intent = new Intent(m_contMultiFragment, ConfigMultiFragmentActivity.class);

            SetUpConfigInfoObj(ConfigObjParam.CONFIG_TYPE_MULTI,bFromMenuPopup,nSelPos,enSelObjKind,strDBName);
        } else //싱글모드
        {
            intent = new Intent(m_contMultiFragment, ConfigSingleFragmentActivity.class);

            SetUpConfigInfoObj(ConfigObjParam.CONFIG_TYPE_SINGLE,bFromMenuPopup,nSelPos,enSelObjKind,strDBName);
            m_pConfigInfoObj.m_enFuncToolType = enSelToolType;
        }
        intent.putExtra(ConfigObjParam.KEY_CONFIGINFOOBJ, m_pConfigInfoObj);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        //m_bOverActivityMode = true;
        //취소하고 돌아오는경우 Draw안됨
        //m_cChartCtrl.GetCurrentChartView().SetChartLoadingNum(1);
        startActivityForResult(intent, GD_CONFIG_RESULT_CODE);
    }

    public void ShowDlgPopupEvent(String strDialogType)
    {
        if ("Init".equals(strDialogType)) //차트 초기화 Dialog
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(m_contMultiFragment)
                    .setMessage("설정을 초기화 하시겠습니까?")
                    .setPositiveButton("예", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            ChartView chtView = m_cChartCtrl.GetCurrentChartView();
                            chtView.InitChartGlobalProperty(ENChartStandbyType.GE_CHART_STANDBY_SYS_TEMP, chtView.GetBaseCode(), chtView.GetBaseCodeMarket(), false);
                            DoChangeFocus(m_stNowCommRqInfo, false);
                        }
                    })
                    .setNeutralButton("자세히", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            InitChartInfoDlg dlgInitChartInfo = new InitChartInfoDlg(m_contMultiFragment, m_strPathChtUser,m_strChtTRNumber);
                            dlgInitChartInfo.show();
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
        else if ("ChartSaveLoad".equals(strDialogType)) //차트틀 저장/불러오기 Dialog
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(m_contMultiFragment)
                    .setMessage("차트틀 저장/불러오기")
                    .setPositiveButton("저장", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            ShowFrameSaveDlg();
                        }
                    })
                    .setNeutralButton("불러오기", new DialogInterface.OnClickListener() {
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
        else if ("SyncPopupWnd".equals(strDialogType))
        {
            @SuppressLint("InflateParams")
            View popupView = LayoutInflater.from(m_contMultiFragment).inflate(R.layout.dialog_sync, null);
            CheckBox chkCodeSync = popupView.findViewById(R.id.Chk_CodeSync);
            CheckBox chkPeriodSync = popupView.findViewById(R.id.Chk_PeriodSync);
            CheckBox chkIndicaSync = popupView.findViewById(R.id.Chk_IndicaSync);

            chkCodeSync.setChecked(m_cChartCtrl.GetCodeSync());
            chkPeriodSync.setChecked(m_cChartCtrl.GetPeriodSync());

            m_dlgSyncPopWnd = new PopupWindow(popupView, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

            m_dlgSyncPopWnd.setAnimationStyle(-1); // 애니메이션 설정(-1:설정안함, 0:설정)
            m_dlgSyncPopWnd.showAsDropDown(m_btnChartSync);
            m_dlgSyncPopWnd.setOutsideTouchable(true);
            m_dlgSyncPopWnd.setFocusable(true);

            chkCodeSync.setOnCheckedChangeListener(new OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    m_cChartCtrl.SetCodeSync(isChecked);
                    if (isChecked == true)
                    {
                        if (m_cChartCtrl.GetChartFrameSize() > 1 && m_cChartCtrl.GetCodeSync() == true)
                            m_cChartCtrl.MakeSameAllConfig(ENChartDataEventType.GE_DATA_EVENT_SAME_ITEM);
                        //new ChartInfoChangeAsyncTask(m_contMultiFragment).execute("CodeSync");
                    }

                    //그냥 너무 빨리 사라지는 것 막을려고
                    //for(int i = 0;i<20000;i++){}
                    ShowAllDropCtrls(true,ENDropCtrlType.LE_CHART_SYNC);
                }
            });

            chkPeriodSync.setOnCheckedChangeListener(new OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    m_cChartCtrl.SetPeriodSync(isChecked);
                    if (isChecked == true)
                    {
                        if (m_cChartCtrl.GetChartFrameSize() > 1 && m_cChartCtrl.GetPeriodSync() == true)
                            m_cChartCtrl.MakeSameAllConfig(ENChartDataEventType.GE_DATA_EVENT_SAME_PERIOD);
                        //new ChartInfoChangeAsyncTask(m_contMultiFragment).execute("PeriodSync");
                    }

                    //그냥 너무 빨리 사라지는 것 막을려고
                    //for(int i = 0;i<20000;i++){}
                    ShowAllDropCtrls(true,ENDropCtrlType.LE_CHART_SYNC);
                }
            });

            chkIndicaSync.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    ShowAllDropCtrls(true,ENDropCtrlType.LE_CHART_SYNC);
                    m_cChartCtrl.MakeSameAllConfig(ENChartDataEventType.GE_DATA_EVENT_SAME_CONFIG);
                    //new ChartInfoChangeAsyncTask(m_contMultiFragment).execute("ConfigSync");
                }
            });
        }
        else if ("LandInitSaveTool".equals(strDialogType))
        {
            @SuppressLint("InflateParams")
            View vwPopup = LayoutInflater.from(m_contMultiFragment).inflate(R.layout.dialog_landinitsavetool, null);
            LinearLayout llLandInit = vwPopup.findViewById(R.id.Linear_Land_Init);
            LinearLayout llLandSave = vwPopup.findViewById(R.id.Linear_Land_Save);
            LinearLayout llLandConfig = vwPopup.findViewById(R.id.Linear_Land_Config);

            ImageButton btnLandInit = vwPopup.findViewById(R.id.Btn_Land_Init);
            ImageButton btnLandSave = vwPopup.findViewById(R.id.Btn_Land_Save);
            ImageButton btnLandConfig = vwPopup.findViewById(R.id.Btn_Land_Config);

            m_btnLandConfig = btnLandConfig;

            m_dlgLandBtnsPopWnd = new PopupWindow(vwPopup, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

            m_dlgLandBtnsPopWnd.setAnimationStyle(-1); // 애니메이션 설정(-1:설정안함, 0:설정)
            m_dlgLandBtnsPopWnd.showAsDropDown(m_btnLandInitSaveTool);
            m_dlgLandBtnsPopWnd.setOutsideTouchable(true);
            m_dlgLandBtnsPopWnd.setFocusable(true);

            OnClickListener onClickListenerInit = new OnClickListener() {
                @Override
                public void onClick(View v) {
                    ShowAllDropCtrls(true,ENDropCtrlType.LE_LAND_SAVE_TOOL);
                    ShowDlgPopupEvent("Init");
                }
            };
            llLandInit.setOnClickListener(onClickListenerInit);
            btnLandInit.setOnClickListener(onClickListenerInit);

            OnClickListener onClickListenerSave = new OnClickListener() {
                @Override
                public void onClick(View v) {
                    ShowAllDropCtrls(true,ENDropCtrlType.LE_LAND_SAVE_TOOL);
                    ShowDlgPopupEvent("ChartSaveLoad");
                }
            };
            llLandSave.setOnClickListener(onClickListenerSave);
            btnLandSave.setOnClickListener(onClickListenerSave);

            OnClickListener onClickListenerConfig = new OnClickListener() {
                @Override
                public void onClick(View v) {
                //SingleMode
                if(m_nMainConfigType == GlobalDefines.GD_CONFIG_DIALOG_TYPE_SINGLE)
                {
                    if (m_dlgLongPressPopWnd == null || m_dlgLongPressPopWnd.isShowing() == false)
                        ShowSingleMenuList();
                    else
                        ShowAllDropCtrls(true,ENDropCtrlType.LE_LONG_PRESS_POPUP_DLG);
                } else if (m_nMainConfigType == GlobalDefines.GD_CONFIG_DIALOG_TYPE_SIMPLE) {
                    //SimpleMode
                    DoConfigPropertyEvent(true, -1, "", ENObjectKindType.GE_OBJECT_KIND_NONE, ENFUNCToolType.GE_FUNCTOOL_CONFIG_SIMPLE_VER, true);
                } else {
                    //MultMode
                    DoConfigPropertyEvent(true, -1, "", ENObjectKindType.GE_OBJECT_KIND_NONE, ENFUNCToolType.GE_FUNCTOOL_NONE, false);
                }
                }
            };
            llLandConfig.setOnClickListener(onClickListenerConfig);
            btnLandConfig.setOnClickListener(onClickListenerConfig);
        }
        else if ("PrdValueMinuteList".equals(strDialogType))
        {
            View popupView = LayoutInflater.from(m_contMultiFragment).inflate(R.layout.dialog_prdvaluemin, null);
            ListView lvPeriodList = popupView.findViewById(R.id.PeriodValue_ListView_Items);
            m_dlgPrdValueMinPopWnd = new PopupWindow(popupView, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

            m_dlgPrdValueMinPopWnd.setAnimationStyle(-1); // 애니메이션 설정(-1:설정안함, 0:설정)
            m_dlgPrdValueMinPopWnd.showAsDropDown(m_btnPeriodList[GD_BTN_PERIOD_MINUTE]);
            m_dlgPrdValueMinPopWnd.setOutsideTouchable(true);

            LoadXMLPrdValueList(ENPacketPeriodType.GE_PACKET_PERIOD_MINUTE,true,false);
            PeriodValueItemAdapter periodValueItemAdapter = new PeriodValueItemAdapter(m_contMultiFragment, m_iconPrdValueItemList, ENPacketPeriodType.GE_PACKET_PERIOD_MINUTE);
            
            lvPeriodList.setAdapter(periodValueItemAdapter);

            periodValueItemAdapter.AddOnItemClickListener(new PeriodValueItemAdapter.AddOnItemClickListener() {
                @Override
                public void AddOnItemClick(String strName, String strValue, ENPacketPeriodType enPacketPeriodType) {

                    int nMinPrdValue = Integer.parseInt(strName);
                    boolean bPrdValueChange = false;
                    if(m_enPacketPeriod != ENPacketPeriodType.GE_PACKET_PERIOD_MINUTE || m_stNowCommRqInfo.nPrdValue[m_stNowCommRqInfo.nPeriod] != nMinPrdValue)
                        bPrdValueChange = true;

                    boolean bPeriodChange = false;
                    if(m_enPacketPeriod != ENPacketPeriodType.GE_PACKET_PERIOD_MINUTE)
                        bPeriodChange = true;

                    m_enPacketPeriod = enPacketPeriodType;
                    m_stNowCommRqInfo.nPeriod = enPacketPeriodType.GetIndexValue();

                    int nID = m_cChartCtrl.GetCurrentChartView().GetViewCtrlID();
                    if(bPrdValueChange == false)
                    {
                        //재조회
                    }
                    else {
                        m_stNowCommRqInfo.nPrdValue[m_stNowCommRqInfo.nPeriod] = nMinPrdValue;
                        ChangePeriodButtonChecked(ENPacketPeriodType.GE_PACKET_PERIOD_MINUTE, false);

                        //m_cChartCtrl.SetCommRequestByID(nID, m_stNowCommRqInfo, ENChartDataEventType.GE_DATA_EVENT_PERIOD_VAL);

                        if (m_cChartCtrl.GetChartFrameSize() > 1 && m_cChartCtrl.GetPeriodSync() == true) {
                            //new ChartInfoChangeAsyncTask(m_contMultiFragment).execute("PeriodSyncAll");
                            m_cChartCtrl.MakeSameAllConfig(ENChartDataEventType.GE_DATA_EVENT_SAME_PERIOD_ALL);
                        }
                        else
                        {
                            int nPrdValue = m_stNowCommRqInfo.nPrdValue[m_stNowCommRqInfo.nPeriod];
                            try {
                                m_cChartCtrl.ChangeDataPeriodInfo(nID, m_stNowCommRqInfo.nPeriod, nPrdValue, true);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                            if(m_cChartCtrl.IsEachPeriodProperty() == true && bPeriodChange == true) {
                                ResetTaskBarInfo();
                                //m_cChartCtrl.GetCurrentChartView().ChangeTaskBarInfo(true);

                                ResetTabBarSubButtonTitle(-1);
                            }

                            m_cDataHandler.RequestBaseData(nID, m_stNowCommRqInfo.strCode, m_stNowCommRqInfo.strName,
                                    m_stNowCommRqInfo.dStartDT[m_stNowCommRqInfo.nPeriod], m_stNowCommRqInfo.dEndDT[m_stNowCommRqInfo.nPeriod],
                                    m_stNowCommRqInfo.nTotCount[m_stNowCommRqInfo.nPeriod], m_stNowCommRqInfo.nMarket, m_stNowCommRqInfo.nPeriod,
                                    m_stNowCommRqInfo.nPrdValue[m_stNowCommRqInfo.nPeriod], m_stNowCommRqInfo.nRealInfo,
                                    m_stNowCommRqInfo.nMarketGubun, ENRequestQueryType.GE_REQUEST_QUERY_ALL.GetIndexValue());
                        }
                    }

                    ShowAllDropCtrls(true,ENDropCtrlType.LE_PERIOD_VALUE_MIN);
                }
            });
        }
        else if ("PrdValueTickList".equals(strDialogType))
        {
            View popupView = LayoutInflater.from(m_contMultiFragment).inflate(R.layout.dialog_prdvaluetick, null);
            ListView lvPeriodList = popupView.findViewById(R.id.PeriodValue_ListView_Items);
            m_dlgPrdValueTickPopWnd = new PopupWindow(popupView, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

            m_dlgPrdValueTickPopWnd.setAnimationStyle(-1); // 애니메이션 설정(-1:설정안함, 0:설정)
            m_dlgPrdValueTickPopWnd.showAsDropDown(m_btnPeriodList[GD_BTN_PERIOD_TICK]);
            m_dlgPrdValueTickPopWnd.setOutsideTouchable(true);

            LoadXMLPrdValueList(ENPacketPeriodType.GE_PACKET_PERIOD_TICK,true,false);
            PeriodValueItemAdapter periodValueItemAdapter = new PeriodValueItemAdapter(m_contMultiFragment, m_iconPrdValueItemList, ENPacketPeriodType.GE_PACKET_PERIOD_TICK);

            lvPeriodList.setAdapter(periodValueItemAdapter);

            periodValueItemAdapter.AddOnItemClickListener(new PeriodValueItemAdapter.AddOnItemClickListener() {
                @Override
                public void AddOnItemClick(String strName, String strValue, ENPacketPeriodType enPacketPeriodType) {

                    int nTickPrdValue = Integer.parseInt(strName);
                    boolean bPrdValueChange = false;
                    if(m_enPacketPeriod != ENPacketPeriodType.GE_PACKET_PERIOD_TICK || m_stNowCommRqInfo.nPrdValue[m_stNowCommRqInfo.nPeriod] != nTickPrdValue)
                        bPrdValueChange = true;

                    boolean bPeriodChange = false;
                    if(m_enPacketPeriod != ENPacketPeriodType.GE_PACKET_PERIOD_TICK)
                        bPeriodChange = true;

                    m_enPacketPeriod = enPacketPeriodType;
                    m_stNowCommRqInfo.nPeriod = enPacketPeriodType.GetIndexValue();

                    int nID = m_cChartCtrl.GetCurrentChartView().GetViewCtrlID();
                    if(bPrdValueChange == false)
                    {
                        //재조회
                    }
                    else {
                        m_stNowCommRqInfo.nPrdValue[m_stNowCommRqInfo.nPeriod] = nTickPrdValue;
                        ChangePeriodButtonChecked(ENPacketPeriodType.GE_PACKET_PERIOD_TICK, false);

                        //m_cChartCtrl.SetCommRequestByID(nID, m_stNowCommRqInfo, ENChartDataEventType.GE_DATA_EVENT_PERIOD_VAL);

                        if (m_cChartCtrl.GetChartFrameSize() > 1 && m_cChartCtrl.GetPeriodSync() == true) {
                            //new ChartInfoChangeAsyncTask(m_contMultiFragment).execute("PeriodSyncAll");
                            m_cChartCtrl.MakeSameAllConfig(ENChartDataEventType.GE_DATA_EVENT_SAME_PERIOD_ALL);
                        } else {
                            int nPrdValue = m_stNowCommRqInfo.nPrdValue[m_stNowCommRqInfo.nPeriod];
                            try {
                                m_cChartCtrl.ChangeDataPeriodInfo(nID, m_stNowCommRqInfo.nPeriod, nPrdValue, true);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                            if(m_cChartCtrl.IsEachPeriodProperty() == true && bPeriodChange == true)
                            {
                                ResetTaskBarInfo();
                                //m_cChartCtrl.GetCurrentChartView().ChangeTaskBarInfo(true);

                                ResetTabBarSubButtonTitle(-1);
                            }

                            m_cDataHandler.RequestBaseData(nID, m_stNowCommRqInfo.strCode, m_stNowCommRqInfo.strName,
                                    m_stNowCommRqInfo.dStartDT[m_stNowCommRqInfo.nPeriod], m_stNowCommRqInfo.dEndDT[m_stNowCommRqInfo.nPeriod],
                                    m_stNowCommRqInfo.nTotCount[m_stNowCommRqInfo.nPeriod], m_stNowCommRqInfo.nMarket, m_stNowCommRqInfo.nPeriod,
                                    m_stNowCommRqInfo.nPrdValue[m_stNowCommRqInfo.nPeriod], m_stNowCommRqInfo.nRealInfo,
                                    m_stNowCommRqInfo.nMarketGubun, ENRequestQueryType.GE_REQUEST_QUERY_ALL.GetIndexValue());
                        }
                    }

                    ShowAllDropCtrls(true, ENDropCtrlType.LE_PERIOD_VALUE_TICK);
                }
            });
        }
        else if ("MultiPopupWnd".equals(strDialogType)) {
            ShowDialogMultiPopWnd();
        }
    }

    public void ShowDialogMultiPopWnd() {
        int nFindIndex = -1;
        int nIndex = -1;
        int nChartFrameStatus = m_cChartCtrl.GetChartFrameStatus();

        for (int i = 0; i < GD_FRAME_LIST_COUNT; i++) {
            if (i == 3) nIndex = 10;
            else if (i == 9) nIndex = 20;
            else if (i == 16) nIndex = 30;
            else if (i == 20) nIndex = 40;
            if (nChartFrameStatus == nIndex) {
                nFindIndex = i;
                break;
            }
            nIndex++;
        }

        int nMaxFrameCount = GlobalUtils.GetPrivateProfileInt(m_strPathChtUser,m_strPathChtSys,GlobalDefines.GD_CHARTINFO_MAINPROPERTY,"MaxFrameCount",4);

        m_dlgFrameType = new FrameMultiTypeDlg(m_contMultiFragment, nFindIndex, nMaxFrameCount);
        m_dlgFrameType.showAtLocation(m_vwMultiFragment, Gravity.CENTER, 0, 0);
        m_dlgFrameType.SetFrameMaxStatus(m_cChartCtrl.IsFrameMaxStatus());
        m_dlgFrameType.AddOnMultiItemClickListener(new FrameMultiTypeDlg.AddOnMultiItemClickListener() {
            @Override
            public void AddOnMultiItemClick(int nRes, int nFrameSize, int nFrameStatus)
            {
                //m_tvProgressMsg.setText("차트 로딩중...");
                m_dlgDataProgress.setMessage("차트 로딩중...");

                int nPrevID = m_cChartCtrl.GetCurrentChartID();
                boolean bFrameMaxStatus = m_cChartCtrl.IsFrameMaxStatus();
                if (bFrameMaxStatus == true) {
                    m_llTabBar.setVisibility(View.GONE);
                    m_cChartCtrl.SetFrameMaxStatus(false);
                }

                int nID = -1;
                try {
                    nID = m_cChartCtrl.OnImpFrameResize(ENResizeObjectList.GE_RESIZE_FRAME_COUNT, nFrameStatus);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                if (nID < 0)
                {
                    m_dlgDataProgress.dismiss();
                    return;
                }

                if (nPrevID != nID)
                    m_stNowCommRqInfo = m_cChartCtrl.GetCurrentCommInfo();

                if (m_cChartCtrl.GetChartFrameSize() > 1 && bFrameMaxStatus == true)
                    ChangeTabBarMaxMinStatus(false);
                else
                    ResetChartFrame();

                m_dlgDataProgress.dismiss();

                //new ChartFrameChangeAsyncTask(m_contMultiFragment).execute("ChangeFrameSize", nFrameStatus + "");

                //m_iconFrameType.setText(" " + nFrameSize);
                m_iconFrameType.setIcon(nRes);
            }
        });

        m_dlgFrameType.ChangeOnMaxMinClickListener(new FrameMultiTypeDlg.ChangeOnMaxMinClickListener() {

            @Override
            public void ChangeOnMaxMinClick(boolean bMaxMinStatus) {
                if (m_cChartCtrl.GetChartFrameSize() > 1) {
                    m_cChartCtrl.SetFrameMaxStatus(bMaxMinStatus);
                    ChangeTabBarMaxMinStatus(bMaxMinStatus);
                }
            }
        });
    }

    public void ShowFrameSaveDlg() {
        ChartView cChartView = m_cChartCtrl.GetCurrentChartView();

        String strDefComment = cChartView.GetChartDefaultComment();

        if (m_cChartCtrl.GetChartFrameSize() > 1 && m_cChartCtrl.IsFrameMaxStatus() == true) {
            LinearLayout llChartBase = m_vwMultiFragment.findViewById(R.id.Linear_Chart);
            llChartBase.removeAllViews();

            LinearLayout.LayoutParams lparam = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, 1);
            llChartBase.setOrientation(LinearLayout.VERTICAL);
            llChartBase.addView(m_cChartCtrl.GetCurrentChartView(), lparam);

            m_llTabBar.setDrawingCacheEnabled(true);
            m_llTabBar.buildDrawingCache();
            Bitmap bmpBottomBar = m_llTabBar.getDrawingCache();

            m_dlgFrameSave = new FrameSaveDlg(m_contMultiFragment, m_strPathChtUser, m_strChtTRNumber, strDefComment, m_cChartCtrl.GetChartViewBitmap(llChartBase, bmpBottomBar));
        } else {
            m_dlgFrameSave = new FrameSaveDlg(m_contMultiFragment, m_strPathChtUser, m_strChtTRNumber, strDefComment, m_cChartCtrl.GetChartViewBitmap(m_llChartBase, null));
        }

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
                GlobalUtils.ToastHandlerMessage(m_contMultiFragment,"차트틀 저장완료");
            }
        }).show();
    }

    @SuppressLint("NewApi")
    public void ShowFrameLoadDlg() {
        m_dlgFrameLoad = new FrameLoadDlg(m_contMultiFragment, m_strPathChtUser, m_strChtTRNumber);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(Objects.requireNonNull(m_dlgFrameLoad.getWindow()).getAttributes());

        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        Objects.requireNonNull(m_dlgFrameLoad.getWindow()).setAttributes(lp);

        m_dlgFrameLoad.SetOnChartFrameLoadListener(new FrameLoadDlg.OnChartFrameLoadListener() {
            @Override
            public void OnChartFrameLoad(Dialog dialog, String strLCWFilePath) {
                dialog.dismiss();

                String strLCWPath = strLCWFilePath;
                SerializeChart(strLCWPath, 1, 0, true);

                m_stNowCommRqInfo.CopyObject(m_cChartCtrl.GetCurrentCommInfo());

                //주의: 원래 InitTopBarCtrl에 있어야 하나 마지막 코드등 받아야해서 위치변경
                //저장된 XML정보를 분,틱주기값 셋팅
                //LoadXMLPrdValueList(true)

                String strExt = GlobalUtils.RightCountString(strLCWFilePath, 4);
                if(strExt.equals(".mcw")) m_cChartCtrl.ResetChartResolution();

                InitChartRequests(false);
                //m_cChartCtrl.RequestAllChart(-1);

            }
        }).show();
    }

    public FrameMultiTypeDlg GetFrameTypeDlg() {
        return m_dlgFrameType;
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////
    private void ShowSingleMenuList()
    {
        View vwPopup = LayoutInflater.from(m_contMultiFragment).inflate(R.layout.dialog_longpress_function, null);
        TextView tvTitle = vwPopup.findViewById(R.id.Tv_LongPress_Title);
        Button btnFunc1 = vwPopup.findViewById(R.id.Btn_LongPress_Func1);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
            btnFunc1.setBackground(FrameSkins.m_gradBtnUnsel26);
        else
            btnFunc1.setBackgroundDrawable(FrameSkins.m_gradBtnUnsel26);
        Button btnFunc2 = vwPopup.findViewById(R.id.Btn_LongPress_Func2);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
            btnFunc2.setBackground(FrameSkins.m_gradBtnUnsel26);
        else
            btnFunc2.setBackgroundDrawable(FrameSkins.m_gradBtnUnsel26);
        Button btnFunc3 = vwPopup.findViewById(R.id.Btn_LongPress_Func3);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
            btnFunc3.setBackground(FrameSkins.m_gradBtnUnsel26);
        else
            btnFunc3.setBackgroundDrawable(FrameSkins.m_gradBtnUnsel26);
        Button btnFunc4 = vwPopup.findViewById(R.id.Btn_LongPress_Func4);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
            btnFunc4.setBackground(FrameSkins.m_gradBtnUnsel26);
        else
            btnFunc4.setBackgroundDrawable(FrameSkins.m_gradBtnUnsel26);

        tvTitle.setVisibility(View.GONE);
        btnFunc1.setText("기본설정");
        btnFunc2.setText("차트유형설정");
        btnFunc3.setText("지표설정");
        btnFunc4.setText("기준선설정");

        btnFunc1.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {    // 기본속성
                DoConfigPropertyEvent(true, -1, "기본설정", ENObjectKindType.GE_OBJECT_KIND_NONE, ENFUNCToolType.GE_FUNCTOOL_CONFIG_SINGLE_LIST, true);
                m_dlgLongPressPopWnd.dismiss();
            }
        });

        btnFunc2.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) { // 차트유형속성
                DoConfigPropertyEvent(true, -1, "차트유형설정", ENObjectKindType.GE_OBJECT_KIND_NONE, ENFUNCToolType.GE_FUNCTOOL_CONFIG_SINGLE_PRC, true);
                m_dlgLongPressPopWnd.dismiss();
            }
        });

        btnFunc3.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {    // 지표설정
                DoConfigPropertyEvent(true, -1, "지표설정", ENObjectKindType.GE_OBJECT_KIND_NONE, ENFUNCToolType.GE_FUNCTOOL_CONFIG_SINGLE_IND, true);
                m_dlgLongPressPopWnd.dismiss();
            }
        });

        btnFunc4.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) { // 기준선설정
                DoConfigPropertyEvent(true, -1, "기준선설정", ENObjectKindType.GE_OBJECT_KIND_NONE, ENFUNCToolType.GE_FUNCTOOL_CONFIG_SINGLE_GUIDE, true);
                m_dlgLongPressPopWnd.dismiss();
            }
        });

        m_dlgLongPressPopWnd = new PopupWindow(vwPopup, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        m_dlgLongPressPopWnd.setAnimationStyle(-1); // 애니메이션 설정(-1:설정안함, 0:설정)
        m_dlgLongPressPopWnd.setOutsideTouchable(true);
        m_dlgLongPressPopWnd.setFocusable(true);

        Resources resources = m_contMultiFragment.getResources();
        int resId = resources.getIdentifier("status_bar_height", "dimen", "android");
        int nStatusBarHeight = -1;
        if (resId > 0)
            nStatusBarHeight = resources.getDimensionPixelSize(resId);
        else
            nStatusBarHeight = (int) Math.ceil((Build.VERSION.SDK_INT >= Build.VERSION_CODES.M ? 24 : 25) * resources.getDisplayMetrics().density);

        RelativeLayout rlTopBar1 = m_vwMultiFragment.findViewById(R.id.Relative_TopBar1);

        if(m_bIsLandScape == true) {
            m_dlgLongPressPopWnd.showAsDropDown(m_btnLandConfig);
        }

        m_dlgLongPressPopWnd.showAtLocation(m_vwMultiFragment, Gravity.TOP | Gravity.RIGHT, 0, rlTopBar1.getTop() + nStatusBarHeight);
        m_cChartCtrl.GetCurrentChartView().SetPopWin(m_dlgLongPressPopWnd);
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

    @SuppressLint("HandlerLeak")
    private Handler HandlerTabBarPopUp = new Handler() {
        public void handleMessage(Message m) {
            switch (m.what) {
                case 0:
                    Bundle bundle = m.getData();
                    final int nBtnIndex = bundle.getInt("BtnIndex");

                    int nX = bundle.getInt("PointX");
                    int nY = bundle.getInt("PointY");

                    View popupView = LayoutInflater.from(m_contMultiFragment).inflate(R.layout.dialog_longpress_tabbtn, null);
                    Button btnTabDel = popupView.findViewById(R.id.Btn_LongPress_Tab_Del);
                    Button btnTabMin = popupView.findViewById(R.id.Btn_LongPress_Tab_Min);

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
                        btnTabDel.setBackground(FrameSkins.m_gradBtnUnsel26);
                    else
                        btnTabDel.setBackgroundDrawable(FrameSkins.m_gradBtnUnsel26);

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
                        btnTabMin.setBackground(FrameSkins.m_gradBtnUnsel26);
                    else
                        btnTabMin.setBackgroundDrawable(FrameSkins.m_gradBtnUnsel26);

                    btnTabDel.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            DelChartFromTabBar(nBtnIndex);
                            m_dlgTabBarBtnPopWnd.dismiss();
                        }
                    });

                    btnTabMin.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //주의: 현재차트만 Redrawing하면 안됨
                            boolean bFrameMaxStatus = !m_cChartCtrl.IsFrameMaxStatus();
                            m_cChartCtrl.SetFrameMaxStatus(bFrameMaxStatus);
                            ChangeTabBarMaxMinStatus(bFrameMaxStatus);

                            m_cChartCtrl.RedrawAllChart();

                            m_dlgTabBarBtnPopWnd.dismiss();
                        }
                    });

                    int nXYLocation[] = new int[2];
                    //m_btnTabBarList[nBtnIndex].getLocationOnScreen(nXYLocation);
                    m_btnTabBarList[nBtnIndex].getLocationInWindow(nXYLocation);

                    int nBtnHeight = m_btnTabBarList[nBtnIndex].getHeight();

                    m_dlgTabBarBtnPopWnd = new PopupWindow(popupView, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
                    m_dlgTabBarBtnPopWnd.setAnimationStyle(-1); // 애니메이션 설정(-1:설정안함, 0:설정)
                    m_dlgTabBarBtnPopWnd.setOutsideTouchable(true);

                    m_dlgTabBarBtnPopWnd.showAtLocation(m_vwMultiFragment, Gravity.BOTTOM | Gravity.LEFT, nXYLocation[0] + (int)m_fTouchDnTabBtnPos[0], nBtnHeight - (int)m_fTouchDnTabBtnPos[1]);

                    Message messageDismiss = new Message();
                    messageDismiss.what = 1;
                    HandlerTabBarPopUp.sendMessageDelayed(messageDismiss, 3000);
                    break;
                case 1:
                    if (m_dlgTabBarBtnPopWnd != null && m_dlgTabBarBtnPopWnd.isShowing())
                        m_dlgTabBarBtnPopWnd.dismiss();
                    break;
            }
        }
    };

    @SuppressLint("HandlerLeak")
    private Handler HandlerANALSelPopUp = new Handler() {
        public void handleMessage(Message m) {
            switch (m.what) {
                case GD_HANDLER_ANAL_POPUP_SHOW:
                    int[] nLocationPosArray = new int[2];
                    m_cChartCtrl.GetCurrentChartView().getLocationInWindow(nLocationPosArray);
                    int nViewTop = nLocationPosArray[1];

                    Bundle bundle = m.getData();
                    final ENANALToolType enAnalTool = ENANALToolType.GetEnumValue(bundle.getInt("ENANALToolType"));

                    int nX = bundle.getInt("PointX");
                    int nY = bundle.getInt("PointY");
                    nY += nViewTop;

                    //ShowAllDropCtrls(true,ENDropCtrlType.LE_LONG_PRESS_POPUP_DLG);

                    View vwPopup = LayoutInflater.from(m_contMultiFragment).inflate(R.layout.dialog_longpress_function, null);
                    TextView tvTitle = vwPopup.findViewById(R.id.Tv_LongPress_Title);
                    Button btnFunc1 = vwPopup.findViewById(R.id.Btn_LongPress_Func1);

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
                        btnFunc1.setBackground(FrameSkins.m_gradBtnUnsel26);
                    else
                        btnFunc1.setBackgroundDrawable(FrameSkins.m_gradBtnUnsel26);

                    Button btnFunc2 = vwPopup.findViewById(R.id.Btn_LongPress_Func2);

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
                        btnFunc2.setBackground(FrameSkins.m_gradBtnUnsel26);
                    else
                        btnFunc2.setBackgroundDrawable(FrameSkins.m_gradBtnUnsel26);

                    Button btnFunc3 = vwPopup.findViewById(R.id.Btn_LongPress_Func3);

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
                        btnFunc3.setBackground(FrameSkins.m_gradBtnUnsel26);
                    else
                        btnFunc3.setBackgroundDrawable(FrameSkins.m_gradBtnUnsel26);

                    Button btnFunc4 = vwPopup.findViewById(R.id.Btn_LongPress_Func4);

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
                        btnFunc4.setBackground(FrameSkins.m_gradBtnUnsel26);
                    else
                        btnFunc4.setBackgroundDrawable(FrameSkins.m_gradBtnUnsel26);

                    if (enAnalTool != ENANALToolType.GE_ANALTOOL_NONE) {
                        tvTitle.setText(GlobalEnums.GE_ANALTOOLTYPENAMES[enAnalTool.GetIndexValue()]);
                        btnFunc1.setText("삭제");
                        btnFunc2.setText("속성");
                        btnFunc3.setText("복사");
                        btnFunc4.setText("닫기");

                        btnFunc1.setOnClickListener(new OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                m_cChartCtrl.GetCurrentChartView().ChangeANALToolType(ENANALToolType.GE_ANALTOOL_DELETE, false, false);
                                m_dlgLongPressPopWnd.dismiss();
                            }
                        });

                        btnFunc2.setOnClickListener(new OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = null;
                                ConfigObjParam chartConfigInfoObj = new ConfigObjParam(m_strPathChtSys, m_strPathChtUser, m_strChtTRNumber);

                                intent = new Intent(m_contMultiFragment, ConfigSingleFragmentActivity.class);
                                chartConfigInfoObj.m_strConfigType = ConfigObjParam.CONFIG_TYPE_ANAL;
                                chartConfigInfoObj.m_enAnalToolType = enAnalTool;
                                chartConfigInfoObj.m_strObjSelectName = GlobalEnums.GE_ANALTOOLTYPENAMES[enAnalTool.GetIndexValue()];

                                intent.putExtra(ConfigObjParam.KEY_CONFIGINFOOBJ, chartConfigInfoObj);
                                intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                                //m_bOverActivityMode = true;
                                startActivityForResult(intent, GD_ANALYSIS_CONFIG_RESULT_CODE);

                                m_dlgLongPressPopWnd.dismiss();
                            }
                        });

                        btnFunc3.setOnClickListener(new OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                m_cChartCtrl.GetCurrentChartView().CopyANALSELTool();
                                m_cChartCtrl.GetCurrentChartView().Redraw();
                                m_dlgLongPressPopWnd.dismiss();
                            }
                        });

                        btnFunc4.setOnClickListener(new OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                m_dlgLongPressPopWnd.dismiss();
                            }
                        });
                    }

                    //if(m_dlgLongPressPopWnd == null)
                    //{
                        m_dlgLongPressPopWnd = new PopupWindow(vwPopup, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
                        m_dlgLongPressPopWnd.setAnimationStyle(-1); // 애니메이션 설정(-1:설정안함, 0:설정)
                        m_dlgLongPressPopWnd.setOutsideTouchable(true);
                    //}

                    m_dlgLongPressPopWnd.showAtLocation(m_cChartCtrl.GetChartView(0), Gravity.TOP | Gravity.LEFT, nX - 200, nY - 100);
                    m_cChartCtrl.GetCurrentChartView().SetPopWin(m_dlgLongPressPopWnd);

                    //주의 : 디버깅에서 아래 로직 타면 무조건 죽음(3초안에 클릭시 현재 추세선 선택 정보가 -1로 셋팅됨)
                    if(BuildConfig.DEBUG == false)
                    {
                        Message messageDismiss = new Message();
                        messageDismiss.what = GD_HANDLER_ANAL_POPUP_HIDE;
                        HandlerANALSelPopUp.sendMessageDelayed(messageDismiss, 3000);
                    }
                    break;
                case GD_HANDLER_ANAL_POPUP_HIDE:
                    if (m_dlgLongPressPopWnd != null && m_dlgLongPressPopWnd.isShowing())
                        m_dlgLongPressPopWnd.dismiss();
                    break;
            }
        }
    };

    /*
     *********************
     **
     ** Button Event Functions
     **
     *********************/
    @Override
    public void onClick(View v) {

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
               //2020/01/14 - PopupWnd Toggle처리가 안되서 ConfigImgBtn에서 ClickListener로 직접 처리
                break;
            //2018/04/13 조원상 종료방식 수정에 따른 종료버튼 삭제
            case R.id.Btn_TitleBar_Back:
                getActivity().finish();
                getActivity().overridePendingTransition(R.anim.fadein, R.anim.fadeout);
                break;
            case R.id.Btn_TopBar_ChartSync:
                //2020/01/14 - PopupWnd Toggle처리가 안되서 ConfigImgBtn에서 ClickListener로 직접 처리
                break;
            case R.id.Btn_TopBar_LandInitSaveTool:
                //2020/01/14 - PopupWnd Toggle처리가 안되서 ConfigImgBtn에서 ClickListener로 직접 처리
                break;
            case R.id.Btn_TopBar_CrossHair:
                if (m_bDataTraceVisible == false) {
                    m_btnCrossHair.setBackgroundColor(FrameSkins.m_crBtnSel);
                    m_bDataTraceVisible = true;
                    for (int i = 0; i < m_cChartCtrl.GetChartFrameSize(); i++)
                        m_cChartCtrl.GetChartView(i).SetCrosshair(m_bDataTraceVisible == true ? 3 : 0);
                } else {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
                        m_btnCrossHair.setBackground(FrameSkins.m_gradBtnUnsel26);
                    else
                        m_btnCrossHair.setBackgroundDrawable(FrameSkins.m_gradBtnUnsel26);
                    m_bDataTraceVisible = false;
                    for (int i = 0; i < m_cChartCtrl.GetChartFrameSize(); i++)
                        m_cChartCtrl.GetChartView(i).SetCrosshair(m_bDataTraceVisible == true ? 3 : 0);
                }
                break;
            case R.id.Btn_TopBar_SaveLoad:
                ShowDlgPopupEvent("ChartSaveLoad");
                break;
            case R.id.Btn_TopBar_PrdTick:
                m_bNeedHandlerPopupWnd = true;
                if(m_dlgPrdValueTickPopWnd == null || m_dlgPrdValueTickPopWnd.isShowing() == false) {
                    ShowAllDropCtrls(true,ENDropCtrlType.LE_PERIOD_VALUE_TICK,true);
                    ShowDlgPopupEvent("PrdValueTickList");
                }
                else {
                    m_dlgPrdValueTickPopWnd.dismiss();
                }
                break;
            case R.id.Btn_TopBar_PrdMinute:
                m_bNeedHandlerPopupWnd = true;
                if(m_dlgPrdValueMinPopWnd == null || m_dlgPrdValueMinPopWnd.isShowing() == false) {
                    ShowAllDropCtrls(true,ENDropCtrlType.LE_PERIOD_VALUE_MIN,true);
                    ShowDlgPopupEvent("PrdValueMinuteList");
                }
                else {
                    m_dlgPrdValueMinPopWnd.dismiss();
                }
                break;
            case R.id.Btn_TopBar_PrdWeek:
                //m_dlgDataProgress.SetMessage("데이터 로딩중...");
                //m_dlgDataProgress.show();
                m_enPacketPeriod = ENPacketPeriodType.GE_PACKET_PERIOD_WEEKLY;
                bPeriodBtnClick = true;
                break;
            case R.id.Btn_TopBar_PrdDay:
                //m_dlgDataProgress.SetMessage("데이터 로딩중...");
                //m_dlgDataProgress.show();
                m_enPacketPeriod = ENPacketPeriodType.GE_PACKET_PERIOD_DAILY;
                bPeriodBtnClick = true;
                break;
            case R.id.Btn_TopBar_PrdMonth:
                //m_dlgDataProgress.SetMessage("데이터 로딩중...");
                //m_dlgDataProgress.show();
                m_enPacketPeriod = ENPacketPeriodType.GE_PACKET_PERIOD_MONTHLY;
                bPeriodBtnClick = true;
                break;
            default:
                break;
        }

        if (bPeriodBtnClick == true)
        {
            boolean bPeriodChange = m_stNowCommRqInfo.nPeriod != m_enPacketPeriod.GetIndexValue();
            m_stNowCommRqInfo.nPeriod = m_enPacketPeriod.GetIndexValue();
            ChangePeriodButtonChecked(m_enPacketPeriod, true);
            if (m_cChartCtrl.GetChartFrameSize() > 1 && m_cChartCtrl.GetPeriodSync() == true) {
                //new ChartInfoChangeAsyncTask(m_contMultiFragment).execute("PeriodSyncAll");
                m_cChartCtrl.MakeSameAllConfig(ENChartDataEventType.GE_DATA_EVENT_SAME_PERIOD_ALL);
            } else {
                int nCurrentChartID = m_cChartCtrl.GetCurrentChartView().GetViewCtrlID();
                if (m_stNowCommRqInfo.nPeriod < 0)
                    Toast.makeText(m_contMultiFragment, "오류", Toast.LENGTH_SHORT).show();

                int nPrdValue = m_stNowCommRqInfo.nPrdValue[m_stNowCommRqInfo.nPeriod];

                try {
                    m_cChartCtrl.ChangeDataPeriodInfo(nCurrentChartID, m_stNowCommRqInfo.nPeriod, nPrdValue, true);
                    if (bPeriodChange == true && m_cChartCtrl.IsEachPeriodProperty() == true) {
                        ResetTaskBarInfo();
                        //m_cChartCtrl.GetCurrentChartView().ChangeTaskBarInfo(true);
                        ResetToolBarInfo();

                        ENPriceKindType enPriceKind = m_cChartCtrl.GetCurrentChartView().GetPriceKindType();
                        if (enPriceKind.GetIndexValue() >= ENPriceKindType.GE_PRICE_KIND_THREEBREAK.GetIndexValue()) {
                            m_llTaskBar.ChangeIndicaEnableAll(enPriceKind,false, true);
                            m_llTaskBar.ChangeSignalRegionEnableAll(false, true);
                        } else {
                            m_llTaskBar.ChangeIndicaEnableAll(enPriceKind,false, true);
                            m_llTaskBar.ChangeSignalRegionEnableAll(false, true);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                m_cDataHandler.RequestBaseData(nCurrentChartID, m_stNowCommRqInfo.strCode, m_stNowCommRqInfo.strName,
                        m_stNowCommRqInfo.dStartDT[m_stNowCommRqInfo.nPeriod],m_stNowCommRqInfo.dEndDT[m_stNowCommRqInfo.nPeriod],
                        m_stNowCommRqInfo.nTotCount[m_stNowCommRqInfo.nPeriod],m_stNowCommRqInfo.nMarket, m_stNowCommRqInfo.nPeriod,
                        m_stNowCommRqInfo.nPrdValue[m_stNowCommRqInfo.nPeriod], m_stNowCommRqInfo.nRealInfo,
                        m_stNowCommRqInfo.nMarketGubun,ENRequestQueryType.GE_REQUEST_QUERY_ALL.GetIndexValue());

                ResetTabBarSubButtonTitle(-1);
            }
        }
    }

    private void ChangeScreenMode()
    {
        if (m_bViewFullScreen) {
            m_btnFullScreen.setBackgroundResource(R.drawable.button_reduce_selector);
            m_vwMultiFragment.findViewById(R.id.Relative_TopBar1).setVisibility(View.GONE);
            m_vwMultiFragment.findViewById(R.id.Relative_TopBar2).setVisibility(View.GONE);
        } else {
            m_btnFullScreen.setBackgroundResource(R.drawable.button_expand_seletor);
            m_vwMultiFragment.findViewById(R.id.Relative_TopBar1).setVisibility(View.VISIBLE);
            m_vwMultiFragment.findViewById(R.id.Relative_TopBar2).setVisibility(View.VISIBLE);
        }
    }

    public boolean dispatchTouchEvent(MotionEvent ev)
    {
        //2020/01/15 - 상위 화면(BaseFragmentActivity)에서 TouchDown시 PopupWnd를 모두 해재하는 루팅을 여기에 넣었는데
        //문제는 팝업을 띄워야할 위치에서도 해제를 먼저하고 다시 띄우니깐 문제가 발생(1.토글 동작X,2.닫혔다 다시 열리는 깜박임)
        //그래도 여기서 닫는 동작을 처리해야 하는 이유는 빈화면등 클릭시 열린 PopupWnd를 닫아줘야 함
        //결론적으로 Flag(m_bNeedHandlerPopupWnd)를 줘서 Handler로 PopupWnd동작시키는 Event다음에 호출되게 처리함

        if(m_enANALToolType != ENANALToolType.GE_ANALTOOL_NONE) return true;

        //PGLog lg = new PGLog();
        //lg.i("Fragment touch Down" + GlobalUtils.GetCurrentDateTime() );

        Message messageShow = new Message();
        messageShow.what = 0;
        m_bNeedHandlerPopupWnd = false;
        //2020/02/03 - 분틱 주기 나온후 Fling시 주기값 DropWnd가 남아 있는것 처럼 보여서 Handler 시간을 300 -> 150으로 줄임
        HandlerFragmentTouchDown.sendMessageDelayed(messageShow,150);
        m_vwMultiFragment.findViewById(R.id.Relative_TopBar1).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                GetFragmentMain().OnTaskBarHideEvent();
                GetFragmentMain().OnToolBarHideEvent();
            }
        });

        if(m_bIsLandScape == false)
        {
            m_vwMultiFragment.findViewById(R.id.Relative_TopBar2).setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    GetFragmentMain().OnTaskBarHideEvent();
                    GetFragmentMain().OnToolBarHideEvent();
                }
            });
        }
        //ShowAllDropCtrls(true,ENDropCtrlType.LE_DROP_CTRL_ALL);

        //return super.dispatchTouchEvent(ev);
        return true;
    }

    public boolean DoChangeFocus(ST_COMM_REQUEST stCommRequest, boolean bChangeCode) {
        return DoChangeFocus(stCommRequest, bChangeCode,true);
    }

    public boolean DoChangeFocus(ST_COMM_REQUEST stCommRequest, boolean bChangeCode,boolean bForceReload)
    {
        //1 - 코드 연동
        if (bChangeCode == true) {
            m_iconCodeCtrl.setText(stCommRequest.strName);
            m_iconCodeCtrl.setValue(stCommRequest.strCode);
        }

        String strNowPrdValue = "";
        if(m_stNowCommRqInfo.nPeriod < ENPacketPeriodType.GE_PACKET_PERIOD_DAILY.GetIndexValue())
        {
            if(m_stNowCommRqInfo.nPeriod == ENPacketPeriodType.GE_PACKET_PERIOD_MINUTE.GetIndexValue())
            {
                String strMinPrdValue = m_btnPeriodList[GD_BTN_PERIOD_MINUTE].getText().toString();

                strNowPrdValue = m_stNowCommRqInfo.nPrdValue[m_stNowCommRqInfo.nPeriod] + "분";
                if(GlobalUtils.CompareString(strMinPrdValue,strNowPrdValue) != 0) m_btnPeriodList[GD_BTN_PERIOD_MINUTE].setText(strNowPrdValue);
            }
            else if(m_stNowCommRqInfo.nPeriod == ENPacketPeriodType.GE_PACKET_PERIOD_TICK.GetIndexValue())
            {
                String strTickPrdValue = m_btnPeriodList[GD_BTN_PERIOD_TICK].getText().toString();

                strNowPrdValue = m_stNowCommRqInfo.nPrdValue[m_stNowCommRqInfo.nPeriod] + "틱";
                if(GlobalUtils.CompareString(strTickPrdValue,strNowPrdValue) != 0) m_btnPeriodList[GD_BTN_PERIOD_TICK].setText(strNowPrdValue);
            }
        }

        //2 - 주기(값) 연동, Daily이상이면 주기값콤보 Enable/Disable등처리
        ChangePeriodButtonChecked(ENPacketPeriodType.GetEnumValue(stCommRequest.nPeriod), false);

        ResetToolBarInfo();
        DoChangeCurPrice(0);

        //분석차트는 추세선 지원이 안됨
        if(m_cChartCtrl.GetCurrentChartView().GetPriceKindType().GetIndexValue() >= ENPriceKindType.GE_PRICE_KIND_THREEBREAK.GetIndexValue())
        {
            SetToolBarBtnStatus(false);

            ShowAllDropCtrls(true,ENDropCtrlType.LE_DROP_CTRL_ALL);

            if(m_enANALToolType != ENANALToolType.GE_ANALTOOL_NONE)
                ShowAllDropCtrls(true,ENDropCtrlType.LE_DROP_CTRL_ALL,false);
        }
        else
        {
            SetToolBarBtnStatus(true);
        }

        //2020/02/28 - 아이폰처럼 화면에서 직접 TaskBar 정보 셋팅
        boolean bReload = false;
        if(m_cChartCtrl.GetChartFrameSize() > 1 || bForceReload == true) bReload = true;

        ResetTaskBarInfo();
        return true;
    }

    private void ResetTaskBarInfo()
    {
        ResetTaskBarInfo(false);
    }

    private void ResetTaskBarInfo(boolean bExceptPrice)
    {
        if (m_llTaskBar == null) return;

        //주의 : 초기화시 가격 차트는 기본 캔들 차트로 고정
        //m_llTaskBar.ChangeIndicaEnableAll(ENPriceKindType.GE_PRICE_KIND_CANDLE,true, false);
        //m_llTaskBar.ChangeIndicaCheckAll(false, false);
        //m_llTaskBar.ChangeSignalRegionEnableAll(true, false);
        //m_llTaskBar.ChangeSignalRegionCheckAll(false, false);

        ChartView chtView = m_cChartCtrl.GetCurrentChartView();

        ENPriceKindType enPriceKind = chtView.GetPriceKindType();
        if(bExceptPrice == false) m_llTaskBar.ChangePriceKind(enPriceKind,true);

        boolean bEnableIndica = true;
        //분석 차트인 경우
        if (enPriceKind.GetIndexValue() > ENPriceKindType.GE_PRICE_KIND_POINT.GetIndexValue())
            bEnableIndica = false;

        m_llTaskBar.ChangeIndicaCheckAll(false, false);
        m_llTaskBar.ChangeIndicaEnableAll(enPriceKind ,bEnableIndica, true);
        m_llTaskBar.ChangeSignalRegionCheckAll(false, false);
        m_llTaskBar.ChangeSignalRegionEnableAll(bEnableIndica, true);

        String strIndicaName = "";
        ENObjectKindType enObjectKindType = ENObjectKindType.GE_OBJECT_KIND_INDICA;
        int nObjTotCount = chtView.GetChartKindObjCount(enObjectKindType, false);
        int nCurrentObjCount = nObjTotCount;
        for(int i=0;i<nObjTotCount;i++)
        {
            strIndicaName = chtView.GetChartKindObjName(enObjectKindType, i);
            m_llTaskBar.ChangeIndicaCheck(strIndicaName, true, true);
        }

        enObjectKindType = ENObjectKindType.GE_OBJECT_KIND_SIGNAL;
        nObjTotCount = chtView.GetChartKindObjCount(enObjectKindType, false);
        nCurrentObjCount += nObjTotCount;
        for(int i=0;i<nObjTotCount;i++)
        {
            strIndicaName = chtView.GetChartKindObjName(enObjectKindType, i);
            m_llTaskBar.ChangeSignalRegionCheck(strIndicaName, true, true);
        }

        enObjectKindType = ENObjectKindType.GE_OBJECT_KIND_REGION;
        nObjTotCount = chtView.GetChartKindObjCount(enObjectKindType, false);
        nCurrentObjCount += nObjTotCount;
        for(int i=0;i<nObjTotCount;i++)
        {
            strIndicaName = chtView.GetChartKindObjName(enObjectKindType, i);
            m_llTaskBar.ChangeSignalRegionCheck(strIndicaName, true, true);
        }

        //주의:거래량도 처리해야 한다
        enObjectKindType = ENObjectKindType.GE_OBJECT_KIND_VOLUME;
        nObjTotCount = chtView.GetChartKindObjCount(enObjectKindType, false);
        nCurrentObjCount += nObjTotCount;
        if(nObjTotCount > 0)  m_llTaskBar.ChangeIndicaCheck(strIndicaName, true, true);

        m_llTaskBar.SetCurObjectCount(nCurrentObjCount);
    }

    private void SetToolBarBtnStatus(boolean bEnable)
    {
        m_imgVwToolBar.setEnabled(bEnable);
        if(bEnable == true)
        {
            m_imgVwToolBar.setImageResource(R.drawable.img_button_tookbar);
            m_imgVwToolBar.invalidate();
        }
        else
        {
            m_imgVwToolBar.setImageResource(R.drawable.img_button_tookbar_disable);
            m_imgVwToolBar.invalidate();
        }
    }

    //종목코드,주기값등 목록
    private void ShowAllDropCtrls(boolean bHidden,ENDropCtrlType enShowDropCtrl)
    {
        ShowAllDropCtrls(bHidden,enShowDropCtrl,false);
    }

    private void ShowAllDropCtrls(boolean bHidden,ENDropCtrlType enShowDropCtrl,boolean bExcept) {

        ShowAllDropCtrls(bHidden,enShowDropCtrl,bExcept,false);
    }

    private void ShowAllDropCtrls(boolean bHidden,ENDropCtrlType enShowDropCtrl,boolean bExcept,boolean bTouchDown)
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

            if (enShowDropCtrl != ENDropCtrlType.LE_TASK_BAR)
                GetFragmentMain().OnTaskBarHideEvent();

            if(bTouchDown == false && m_enANALToolType != ENANALToolType.GE_ANALTOOL_NONE)
            {
                m_cChartCtrl.ChangeANALToolType(ENANALToolType.GE_ANALTOOL_CONTINUE, false, true);
                m_llToolBar.InitAnalysisPress(true);
                m_enANALToolType = ENANALToolType.GE_ANALTOOL_NONE;
            }

            if (enShowDropCtrl != ENDropCtrlType.LE_TOOL_BAR)
                GetFragmentMain().OnToolBarHideEvent();

            if (enShowDropCtrl != ENDropCtrlType.LE_LAND_SAVE_TOOL) {
                if (m_dlgLandBtnsPopWnd != null && m_dlgLandBtnsPopWnd.isShowing() == true) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
                        m_btnLandInitSaveTool.setBackground(FrameSkins.m_gradBtnUnsel26);
                    else
                        m_btnLandInitSaveTool.setBackgroundDrawable(FrameSkins.m_gradBtnUnsel26);
                    m_dlgLandBtnsPopWnd.dismiss();
                }
            }

            if(enShowDropCtrl != ENDropCtrlType.LE_CHART_SYNC) {
                if (m_dlgSyncPopWnd != null && m_dlgSyncPopWnd.isShowing() == true) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
                        m_btnChartSync.setBackground(FrameSkins.m_gradBtnUnsel26);
                    else
                        m_btnChartSync.setBackgroundDrawable(FrameSkins.m_gradBtnUnsel26);
                    m_dlgSyncPopWnd.dismiss();
                }
            }

            if(enShowDropCtrl != ENDropCtrlType.LE_PERIOD_VALUE_MIN) {
                if (m_dlgPrdValueMinPopWnd != null && m_dlgPrdValueMinPopWnd.isShowing() == true) {
                    m_dlgPrdValueMinPopWnd.dismiss();
                }
            }

            if(enShowDropCtrl != ENDropCtrlType.LE_PERIOD_VALUE_TICK) {
                if (m_dlgPrdValueTickPopWnd != null && m_dlgPrdValueTickPopWnd.isShowing() == true) {
                    m_dlgPrdValueTickPopWnd.dismiss();
                }
            }

            if(enShowDropCtrl != ENDropCtrlType.LE_CODE_COMBO_LIST) {
                if (m_iconCodeCtrl != null && m_iconCodeCtrl.isShown() == true) {
                    m_iconCodeCtrl.dismiss();
                }
            }

            if(enShowDropCtrl != ENDropCtrlType.LE_MULTI_FRAME_DLG) {
                if (m_dlgFrameType != null && m_dlgFrameType.isShowing() == true) {
                    m_dlgFrameType.dismiss();
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

            if(bTouchDown == false && m_enANALToolType != ENANALToolType.GE_ANALTOOL_NONE)
            {
                m_cChartCtrl.ChangeANALToolType(ENANALToolType.GE_ANALTOOL_CONTINUE, false, true);
                m_llToolBar.InitAnalysisPress(true);
                m_enANALToolType = ENANALToolType.GE_ANALTOOL_NONE;
            }

            //2020/01/15 - TaskBar/ToolBar는 각자 동작이나 필요한 곳에서 개별적으로 처리한다
            //여기서 이렇게 처리하면 지표 항목하나 선택해도 바로 사라진다
            /*if (enShowDropCtrl == ENDropCtrlType.LE_DROP_CTRL_ALL || enShowDropCtrl == ENDropCtrlType.LE_TASK_BAR) {
                GetFragmentMain().OnTaskBarHideEvent();
                if (enShowDropCtrl == ENDropCtrlType.LE_TASK_BAR) return;
            }

            if (enShowDropCtrl == ENDropCtrlType.LE_DROP_CTRL_ALL || enShowDropCtrl == ENDropCtrlType.LE_TOOL_BAR) {
                GetFragmentMain().OnToolBarHideEvent();
                if (enShowDropCtrl == ENDropCtrlType.LE_TOOL_BAR) return;
            }*/

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

            if (enShowDropCtrl == ENDropCtrlType.LE_DROP_CTRL_ALL || enShowDropCtrl == ENDropCtrlType.LE_CHART_SYNC) {
                if (m_dlgSyncPopWnd != null && m_dlgSyncPopWnd.isShowing() == true) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
                        m_btnChartSync.setBackground(FrameSkins.m_gradBtnUnsel26);
                    else
                        m_btnChartSync.setBackgroundDrawable(FrameSkins.m_gradBtnUnsel26);
                    m_dlgSyncPopWnd.dismiss();
                    if (enShowDropCtrl == ENDropCtrlType.LE_CHART_SYNC) return;
                }
            }

            if (enShowDropCtrl == ENDropCtrlType.LE_DROP_CTRL_ALL || enShowDropCtrl == ENDropCtrlType.LE_PERIOD_VALUE_MIN) {
                if (m_dlgPrdValueMinPopWnd != null && m_dlgPrdValueMinPopWnd.isShowing() == true) {
                    m_dlgPrdValueMinPopWnd.dismiss();
                    if (enShowDropCtrl == ENDropCtrlType.LE_PERIOD_VALUE_MIN) return;
                }
            }

            if (enShowDropCtrl == ENDropCtrlType.LE_DROP_CTRL_ALL || enShowDropCtrl == ENDropCtrlType.LE_PERIOD_VALUE_TICK) {
                if (m_dlgPrdValueTickPopWnd != null && m_dlgPrdValueTickPopWnd.isShowing() == true) {
                    m_dlgPrdValueTickPopWnd.dismiss();
                    if (enShowDropCtrl == ENDropCtrlType.LE_PERIOD_VALUE_TICK) return;
                }
            }

            if (enShowDropCtrl == ENDropCtrlType.LE_DROP_CTRL_ALL || enShowDropCtrl == ENDropCtrlType.LE_CODE_COMBO_LIST) {
                if (m_iconCodeCtrl != null && m_iconCodeCtrl.isShown() == true) {
                    m_iconCodeCtrl.dismiss();
                    if (enShowDropCtrl == ENDropCtrlType.LE_CODE_COMBO_LIST) return;
                }
            }

            if (enShowDropCtrl == ENDropCtrlType.LE_DROP_CTRL_ALL || enShowDropCtrl == ENDropCtrlType.LE_MULTI_FRAME_DLG) {
                if (m_dlgFrameType != null && m_dlgFrameType.isShowing() == true) {
                    m_dlgFrameType.dismiss();
                    if (enShowDropCtrl == ENDropCtrlType.LE_MULTI_FRAME_DLG) return;
                }
            }
        }
    }

    /*********************
     **
     ** Period & PrdValue Change Functions
     **
     *********************/
    ////////////////////////////////////////////////////////////////////////////////////
    //TopBar 현재가 정보
    //nEventType:0 - Query ,1 - Real
    public void DoChangeCurPrice(int nEventType) {
        int nID = m_cChartCtrl.GetCurrentChartID();
        String strCurrentPrice = m_cChartCtrl.GetCurrentPrice(nID);
        String strVolume = m_cChartCtrl.GetCurrentVolume(nID);

        String strContrast = m_cChartCtrl.GetCurrentContrast(nID);
        String strContrastR = m_cChartCtrl.GetCurrentContrastR(nID);

        SetCurPriceInfo(strCurrentPrice,strContrast,strContrastR, strVolume);
    }

    private void SetCurPriceInfo(String strCurrentPrice, String strContrast, String strContrastR, String strVolume) {
        int crUpDnColor = Color.parseColor("#AAAAAA");
        if (strContrast.contains("▲")) {
            crUpDnColor = ColorNames.GC_Red;
        } else if (strContrast.contains("▼")) {
            crUpDnColor = ColorNames.GC_Blue;
        } else if (strContrast.contains("↑")) {
            crUpDnColor = ColorNames.GC_Red;
        } else if (strContrast.contains("↓")) {
            crUpDnColor = ColorNames.GC_Blue;
        }

        m_tvTopBarCurPrice.setText(strCurrentPrice);
        m_tvTopBarDiff.setText(strContrast);
        m_tvTopBarRatio.setText(strContrastR);
        m_tvTopBarVol.setText(strVolume);

        m_tvTopBarCurPrice.setTextColor(crUpDnColor);
        m_tvTopBarDiff.setTextColor(crUpDnColor);
        m_tvTopBarRatio.setTextColor(crUpDnColor);
    }

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
            if (bChangePrdValue == false && m_nTickPrdValueList.size() != nTickPrdValueList.size())
                bChangePrdValue = true;

            if (bChangePrdValue == false) {
                for (i = 0; i < m_nTickPrdValueList.size(); i++) {
                    if (m_nTickPrdValueList.get(i) != nTickPrdValueList.get(i)) {
                        bChangePrdValue = true;
                        break;
                    }

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

            if (nTickPrdValueList.isEmpty() == false) {
                m_nTickPrdValueList.clear();
                m_nTickPrdValueList.addAll(nTickPrdValueList);
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
                } else if (enPacketPeriod == ENPacketPeriodType.GE_PACKET_PERIOD_TICK) {
                    nPrdTickValue = m_nTickPrdValueList.get(i);
                    strFilePath = strDirPath + strCodeName + strSeparator + strCode + strSeparator + "틱" + nPrdTickValue + ".csv";
                    bEnable = GlobalDirFile.isFileExist(strFilePath);

                    m_iconPrdValueItemList.add(new IconListItem(bEnable, 0, String.valueOf(nPrdTickValue), String.valueOf(i)));
                }
                else //분,틱이 아닌경우는 1값만 입력,셋팅
                {
                    m_iconPrdValueItemList.add(new IconListItem(0, "1", String.valueOf(i)));
                    break;
                }
            }
        }
    }

    public void ChangePeriodButtonChecked(ENPacketPeriodType enPacketPeriod, boolean bChangePeriod) {
        int nPeriodBtnSize = m_btnPeriodList.length;
        String strMTName = "";
        int nPrdValue = -1;
        int nTag = -1;
        for(int i = 0; i < nPeriodBtnSize; i++)
        {
            nTag = (int)m_btnPeriodList[i].getTag();
            if (nTag == enPacketPeriod.GetIndexValue()) {
                m_btnPeriodList[i].setChecked(true);
                //m_btnPeriodList[i].setBackgroundDrawable(FrameSkins.m_gradSegControlSel);
                m_btnPeriodList[i].setBackgroundColor(FrameSkins.m_crBtnSel);
            } else {
                m_btnPeriodList[i].setChecked(false);
                //m_btnPeriodList[i].setBackgroundDrawable(FrameSkins.m_gradSegControlUnsel);
                m_btnPeriodList[i].setBackgroundColor(FrameSkins.m_crBtnUnsel);
            }

            if ((enPacketPeriod == ENPacketPeriodType.GE_PACKET_PERIOD_MINUTE || enPacketPeriod == ENPacketPeriodType.GE_PACKET_PERIOD_TICK) &&
                    (i == GD_BTN_PERIOD_MINUTE || i == GD_BTN_PERIOD_TICK))
            {
                nPrdValue = m_stNowCommRqInfo.nPrdValue[nTag];
                if (i == GD_BTN_PERIOD_MINUTE) {
                    if (nPrdValue == -1)
                        strMTName = "분";
                    else
                        strMTName = nPrdValue + "분";
                } else {
                    if (nPrdValue == -1)
                        strMTName = "틱";
                    else
                        strMTName = nPrdValue + "틱";
                }

                m_btnPeriodList[i].setText(strMTName);
                m_btnPeriodList[i].setTextOn(strMTName);
                m_btnPeriodList[i].setTextOff(strMTName);
            }
        }
    }

    public void ChangeTabBarSubItem(int nSelectNum)
    {
        ShowAllDropCtrls(true,ENDropCtrlType.LE_DROP_CTRL_ALL);

        int nNowChartIndex = m_cChartCtrl.GetFocusFrameIndex();

        if (nNowChartIndex == nSelectNum) {
            return;
        }

        if(m_cChartCtrl.IsFrameMaxStatus() == false) return;

        for (int i = 0; i < m_cChartCtrl.GetChartFrameSize(); i++) {

            m_btnTabBarList[i].setChecked(i == nSelectNum);
            //m_btnTabBarList[i].setEnabled(!(i == nSelectNum));

            if(i == nSelectNum)
                m_btnTabBarList[i].setBackgroundColor(FrameSkins.m_crBtnSel);
            else
                m_btnTabBarList[i].setBackgroundColor(FrameSkins.m_crBtnUnsel);

            m_cChartCtrl.GetChartView(i).SetFrameFocus(nSelectNum == i, false);
        }

        m_cChartCtrl.SetFocusFrameIndex(nSelectNum);

        m_cChartCtrl.GetCurrentChartView().setVisibility(View.GONE);
        m_cChartCtrl.GetCurrentChartView().setVisibility(View.VISIBLE);
        ResetChartFrame();
        m_cChartCtrl.GetCurrentChartView().Redraw();

        m_stNowCommRqInfo = m_cChartCtrl.GetCurrentCommInfo();
        //m_cChartCtrl.GetCurrentChartView().ChangeTaskBarInfo(true);
        ResetToolBarInfo();
        DoChangeCurPrice(0);

        m_enPacketPeriod = m_cChartCtrl.GetCurrentChartView().GetPacketPeriodType();
        m_iconCodeCtrl.setText(m_cChartCtrl.GetCurrentChartView().GetBaseCodeName());

        //LoadXMLPrdValueList(m_enPacketPeriod, (m_nMinPrdValueList.size() <= 0), true);
        ChangePeriodButtonChecked(m_enPacketPeriod, false);
    }

    public void ChangeJongMokCode(String strName, String strCode, int nMarket) {
        if (strCode.isEmpty() == true)
            if (m_stNowCommRqInfo.strCode.equals(strCode)) return;

        int nID = m_cChartCtrl.GetCurrentChartID();
        m_cChartCtrl.SetQueryEvent(nID, false);

        m_stNowCommRqInfo.strName = strName;
        m_stNowCommRqInfo.strCode = strCode;
        m_stNowCommRqInfo.nMarket = nMarket;
		/*if(ENMarketCategoryType.GE_MARKET_CATE_STOCK.Compare(m_stNowCommRqInfo.m_nMarket))
			m_ctrlBtnMarketType.setText("주식");
		else if(ENMarketCategoryType.GE_MARKET_CATE_INDUSTRY.Compare(m_stNowCommRqInfo.m_nMarket))
			m_ctrlBtnMarketType.setText("업종");*/

        //주의:데이터 요청하기 전에 초기화
        SetCurPriceInfo("0", "0", "0.00%", "0");

        m_cChartCtrl.SetCommRequestByID(nID, m_stNowCommRqInfo, ENChartDataEventType.GE_DATA_EVENT_CODE_CHANGE);

        m_cChartCtrl.SetFullSizeStatus(nID, false);
        m_cChartCtrl.SetPacketViewSize(nID, m_stNowCommRqInfo.nViewCount[m_stNowCommRqInfo.nPeriod]);
        m_cChartCtrl.SetCommRequestByID(nID, m_stNowCommRqInfo, ENChartDataEventType.GE_DATA_EVENT_CHANGE_VIEW);
        m_cChartCtrl.SetMainFloatingPoint(nID,0);

//        //SetCurCodeName(m_stNowCommRqInfo.m_strName);
//
//        //2015-02-23 nPeriod값 설정안된경우 Out of Index발생 - daily default
//        if (m_stNowCommRqInfo.nPeriod == -1) {
//            m_stNowCommRqInfo.nPeriod = ENPacketPeriodType.GE_PACKET_PERIOD_DAILY.GetIndexValue();
//            //ChangePeriodButtonChecked(ENPacketPeriodType.GE_PACKET_PERIOD_DAILY);
//            //ChangePeriodButton(m_stNowCommRqInfo.m_nPeriod, -1, false);
//        }
//
//        int nPrdVal = m_stNowCommRqInfo.nPrdValue[m_stNowCommRqInfo.nPeriod];
//        m_cChartCtrl.SetCommRequestByID(nID, m_stNowCommRqInfo, ENChartDataEventType.GE_DATA_EVENT_CODE_CHANGE);
//
//        m_cChartCtrl.SetFullSizeStatus(nID, false);
//        m_cChartCtrl.SetPacketViewSize(nID, m_stNowCommRqInfo.nViewCount[m_stNowCommRqInfo.nPeriod]);
//        m_cChartCtrl.SetCommRequestByID(nID, m_stNowCommRqInfo, ENChartDataEventType.GE_DATA_EVENT_CHANGE_VIEW);
//        m_cChartCtrl.SetMainFloatingPoint(nID, 0);

        //new ChartInfoChangeAsyncTask(m_contMultiFragment).execute("CodeChange", m_strName, m_strCode);
        if (m_cChartCtrl.GetChartFrameSize() > 1 && m_cChartCtrl.GetCodeSync() == true) {
            //MakeSameAllConfig 에서 OnGetChartLinkEvent 를 통해 Request
            m_cChartCtrl.MakeSameAllConfig(ENChartDataEventType.GE_DATA_EVENT_SAME_ITEM_ALL);
        } else {
            int nPrdVal = m_stNowCommRqInfo.nPrdValue[m_stNowCommRqInfo.nPeriod];
            m_cDataHandler.RequestBaseData(nID, m_stNowCommRqInfo.strCode, m_stNowCommRqInfo.strName,
                    m_stNowCommRqInfo.dStartDT[m_stNowCommRqInfo.nPeriod],m_stNowCommRqInfo.dEndDT[m_stNowCommRqInfo.nPeriod],
                    m_stNowCommRqInfo.nTotCount[m_stNowCommRqInfo.nPeriod],m_stNowCommRqInfo.nMarket, m_stNowCommRqInfo.nPeriod,
                    nPrdVal, m_stNowCommRqInfo.nRealInfo,
                    m_stNowCommRqInfo.nMarketGubun,ENRequestQueryType.GE_REQUEST_QUERY_ALL.GetIndexValue());
        }
    }

    /*
     *********************
     **

     ** Property Result Functions
     **
     *********************/
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void onActivityResult(int nResultType, int nReturnType, Intent data) {

        if (nResultType == GD_CONFIG_RESULT_CODE) {
            if (nReturnType == Activity.RESULT_OK)
            {
                m_cChartCtrl.ChangeCurrentChartGlobalProperty(ENConfigProperty.GE_CONFIG_PROPERTY_NONE, ENPropertyAfterType.GE_PROPERTY_AFTER_NONE);
                LoadXMLPrdValueList(m_enPacketPeriod,false,true);

               ResetToolBarInfo();

                //new ChartInfoChangeAsyncTask(m_contMulti).execute("ChangeProperty");
            }
        } else if (nResultType == GD_ANALYSIS_CONFIG_RESULT_CODE) {
            if (nReturnType == Activity.RESULT_OK) {
            }
        }
        else if (nResultType == GD_CONFIG_MAINPROPERTY)
        {
            if (nReturnType == Activity.RESULT_OK)
            {
                //2020/01/20 - MainProperty적용시 화면 닫고 열지 않고 바로 처리한다
                int nFrameSkin = data.getIntExtra("FrameSkin",0);
                int nMaxObjectCount = data.getIntExtra("MaxObjectCount",10);
                boolean bPriceScroll = data.getBooleanExtra("PriceScroll",false);
                m_bIsAnalContinue = data.getBooleanExtra("AnalContinue",false);
                double dDataTraceAlpha = data.getDoubleExtra("DataTraceAlpha",GlobalDefines.GD_NAVALUE_DOUBLE);

                //주의 : Task바는 Config타입에 따라 실시간으로 변경하지 않는다(화면 닫고 열때 다시 적)
//                int nConfigType = data.getIntExtra("ConfigType",0);
//                if(m_nMainConfigType != nConfigType)
//                {
//                    if (m_llTaskBar != null) 용.BuildLeftTaskBar();
//                    m_nMainConfigType = nConfigType;
//                }

                if(nFrameSkin != -1)
                {
                    if(FrameSkins.m_nFrameSkin != nFrameSkin)
                    {
                        FrameSkins.SetFrameSkin(nFrameSkin);
                        if (m_llTaskBar != null)
                        {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
                                m_imgVwTaskBar.setBackground(FrameSkins.m_gradBtnUnsel30);
                            else
                                m_imgVwTaskBar.setBackgroundDrawable(FrameSkins.m_gradBtnUnsel30);

                            m_llTaskBar.ResetFrameSkin();

                            m_llTaskBar.setBackgroundDrawable(FrameSkins.m_gradTASKBack);
                            m_llTaskBar.invalidate();
                        }

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
                            m_imgVwTaskBar.setBackground(FrameSkins.m_gradBtnUnsel30);
                        else
                            m_imgVwTaskBar.setBackgroundDrawable(FrameSkins.m_gradBtnUnsel30);
                        m_imgVwTaskBar.invalidate();

                        if (m_llToolBar != null)
                        {
                            m_llToolBar.SetBackground(FrameSkins.m_crTOOLBackList, FrameSkins.m_crTOOLSelBackList);
                            m_llToolBar.ResetFrameSkin();
                        }

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
                            m_imgVwToolBar.setBackground(FrameSkins.m_gradBtnUnsel26);
                        else
                            m_imgVwToolBar.setBackgroundDrawable(FrameSkins.m_gradBtnUnsel26);
                        m_imgVwToolBar.invalidate();

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
                            m_btnChartSync.setBackground(FrameSkins.m_gradBtnUnsel26);
                        else
                            m_btnChartSync.setBackgroundDrawable(FrameSkins.m_gradBtnUnsel26);
                        m_btnChartSync.invalidate();
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
                            m_btnCrossHair.setBackground(FrameSkins.m_gradBtnUnsel26);
                        else
                            m_btnCrossHair.setBackgroundDrawable(FrameSkins.m_gradBtnUnsel26);
                        m_btnCrossHair.invalidate();

                        if(m_llTabBar != null) {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
                                m_llTabBar.setBackground(FrameSkins.m_gradSegControlUnsel);
                            else
                                m_llTabBar.setBackgroundDrawable(FrameSkins.m_gradSegControlUnsel);
                            m_llTabBar.invalidate();
                        }

                        int i = 0;
                        for (i = 0;i<m_btnTabBarList.length;i++) {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
                                m_btnTabBarList[i].setBackground(FrameSkins.m_gradSegControlSel);
                            else
                                m_btnTabBarList[i].setBackgroundDrawable(FrameSkins.m_gradSegControlSel);

                            m_btnTabBarList[i].invalidate();
                        }

                        if(m_bIsLandScape == false) {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
                                m_btnSaveLoad.setBackground(FrameSkins.m_gradBtnUnsel26);
                            else
                                m_btnSaveLoad.setBackgroundDrawable(FrameSkins.m_gradBtnUnsel26);
                            m_btnSaveLoad.invalidate();
                        }
                        else {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
                                m_btnLandInitSaveTool.setBackground(FrameSkins.m_gradBtnUnsel26);
                            else
                                m_btnLandInitSaveTool.setBackgroundDrawable(FrameSkins.m_gradBtnUnsel26);
                            m_btnLandInitSaveTool.invalidate();
                        }

                        for(i = 0; i < m_btnPeriodList.length; i++) {
                            if (m_btnPeriodList[i].isChecked() == true) {
                                m_btnPeriodList[i].setBackgroundColor(FrameSkins.m_crBtnSel);
                            } else {
                                m_btnPeriodList[i].setBackgroundColor(FrameSkins.m_crBtnUnsel);
                            }

                            m_btnPeriodList[i].invalidate();
                        }

                        m_iconCodeCtrl.SetBackground(FrameSkins.m_crBtnUnselList);
                        m_iconFrameType.SetBackground(FrameSkins.m_crBtnUnselList);

                        //TopBar,TitleBar
                        RelativeLayout rlTitleBar = m_vwMultiFragment.findViewById(R.id.Relative_TitleBar);
                        rlTitleBar.setBackgroundColor(FrameSkins.m_crNAVIBack);
                        //타이틀바 변수
                        TextView tvTitle = m_vwMultiFragment.findViewById(R.id.Tv_TitleBar_Title);
                        tvTitle.setTextColor(FrameSkins.m_crNAVIText);
                    }
                }

                if(m_llTaskBar != null) m_llTaskBar.SetMaxObjectCount(nMaxObjectCount);

                //다른 속성 적용
                m_cChartCtrl.ChangePriceScroll(bPriceScroll);

                if(dDataTraceAlpha != GlobalDefines.GD_NAVALUE_DOUBLE)
                    m_cChartCtrl.ChangeDataTraceAlpha(dDataTraceAlpha);
            }
        }
        //super.onActivityResult(requestCode, resultCode, data);
    }

    /*
     *********************
     **

     ** Task & ToolBar Functions
     **
     *********************/
    //주의:추세선은 별도로 관리 되기 때문에 여기서처리 되지 않음(function은 Status를 가지고 있어야 함)
    private void ResetToolBarInfo() {

        if (m_llToolBar == null) return;

        boolean bPressed = false;
        ENFUNCToolType enfuncToolType = ENFUNCToolType.GE_FUNCTOOL_NONE;
        int nFirstIndex = ENFUNCToolType.GE_FUNCTOOL_INIT_CREATE.GetIndexValue();
        for (int i = 0; i < GlobalEnums.GE_FUNCTOOLTYPENAMES.length; i++) {
            enfuncToolType = ENFUNCToolType.GetEnumValue(i + nFirstIndex);
            if (enfuncToolType == ENFUNCToolType.GE_FUNCTOOL_PRICE_TOPMOST)    //가격차트최우선표시
                bPressed = m_cChartCtrl.GetCurrentChartView().IsPriceTopMost();
            else if (enfuncToolType == ENFUNCToolType.GE_FUNCTOOL_PRICE_YSCALE)    //주가우선스케일
                bPressed = m_cChartCtrl.GetCurrentChartView().IsPriceDependYScale();
            else if (enfuncToolType == ENFUNCToolType.GE_FUNCTOOL_TITLE_NLINE)    //타이틀N라인표시
                bPressed = m_cChartCtrl.GetCurrentChartView().IsTitleNLineDsp();
            else if (enfuncToolType == ENFUNCToolType.GE_FUNCTOOL_TITLE_DATA)    //타이틀수치표시
                bPressed = (m_cChartCtrl.GetCurrentChartView().GetTitleDataDsp() != ENTitleDspType.GE_TITLE_DISPLAY_NONE);
            else if (enfuncToolType == ENFUNCToolType.GE_FUNCTOOL_MAXMIN_SIGNAL)    //최대최소신호
                bPressed = m_cChartCtrl.GetCurrentChartView().IsShowPriceMaxMin();
            else if (enfuncToolType == ENFUNCToolType.GE_FUNCTOOL_H_L_C_RATIO)    //고/저/종 등락률 표시
                bPressed = m_cChartCtrl.GetCurrentChartView().IsShowPriceHLCRatio();
            else if (enfuncToolType == ENFUNCToolType.GE_FUNCTOOL_HIGHEST_LOWEST)    //상하한가
                bPressed = m_cChartCtrl.GetCurrentChartView().IsShowPriceUpperLower();
            else if (enfuncToolType == ENFUNCToolType.GE_FUNCTOOL_PHOTO_ZOOM)    //사진Zoom 확대축소
                bPressed = m_cChartCtrl.GetCurrentChartView().IsPhotoZoomType();
            else if (enfuncToolType == ENFUNCToolType.GE_FUNCTOOL_MOUSETRACE_CLOSE)    //수치조회 자석 기능
                bPressed = m_cChartCtrl.GetCurrentChartView().IsMouseTraceSnapClose();
            else if (enfuncToolType == ENFUNCToolType.GE_FUNCTOOL_YAXIS_DRAG)    //Y축 영역 교환/View확대
                bPressed = m_cChartCtrl.GetCurrentChartView().IsYAxisScaleChg();
            else if (enfuncToolType == ENFUNCToolType.GE_FUNCTOOL_CHART_BUTTON)    //차트영역버튼
                bPressed = m_cChartCtrl.GetCurrentChartView().IsEnableExp_MoveBtns();
            else if (enfuncToolType == ENFUNCToolType.GE_FUNCTOOL_YAXIS_BUTTON)    //차트Y축버튼
                bPressed = m_cChartCtrl.GetCurrentChartView().IsShowBlockExBtn();
            else if (enfuncToolType == ENFUNCToolType.GE_FUNCTOOL_XYAXIS_HIDE)    //XY축 숨기기
                bPressed = m_cChartCtrl.GetCurrentChartView().IsXYAxisHide();
            else if (enfuncToolType == ENFUNCToolType.GE_FUNCTOOL_SCRBAR_SHOW) //하단스크롤수치표시
                bPressed = m_cChartCtrl.GetCurrentChartView().IsShowScrollBar();
            else if (enfuncToolType == ENFUNCToolType.GE_FUNCTOOL_SCROLL_SYNC) //스크롤 동기화
                bPressed = m_cChartCtrl.IsScrollSync();
            else
                continue;

            m_llToolBar.ChangeFunctionPress(i + 1, bPressed, true);
        }
    }

    private int GetFrameStatusImgIndex(int nFrameStatus)
    {
        int nFindIndex = -1;
        int nIndex = -1;
        for(int i=0;i<GD_FRAME_LIST_COUNT;i++)
        {
            if(i==3) { nIndex = 10; }
            else if(i==9) { nIndex = 20; }
            else if(i==16) { nIndex = 30; }
            else if(i==20) { nIndex = 40; }

            if(nFrameStatus == nIndex)
            {
                //2020/01/14 - Image Index +1하면 안
                //nFindIndex = i + 1;
                nFindIndex = i;
                break;
            }

            nIndex += 1;
        }

        return nFindIndex;
    }

    /////////////////////////////////////////////////////////////////////////////////////////
    // 데이터 Handling 함수
    public void SetLinkCode_Name(String strLinkCode, String strLinkCodeName) {
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
        ShowAllDropCtrls(true,ENDropCtrlType.LE_DROP_CTRL_ALL);
    }

    @Override
    public void onDestroyView() {
        if (m_cMessageBoxHandler != null && m_cMessageBoxHandler.IsShowing())
            m_cMessageBoxHandler.RemoveAllMessage();

        FrameSkins.InitFrameSkin();
        ShowAllDropCtrls(true,ENDropCtrlType.LE_DROP_CTRL_ALL);

        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        SerializeChart("", 0, -1, true);

        m_cDataHandler.CloseDataHandler();
        //m_cDataHandler.DoStop();
        super.onDestroy();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        ShowAllDropCtrls(true,ENDropCtrlType.LE_LONG_PRESS_POPUP_DLG);
        //LongPressPopWndDismiss();

        //회전할때 이전 상태를 저장한다
        SerializeChart("", 0, -1, true);

        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            m_bIsLandScape = true;
            // 기기가 가로로 회전할때 할 작업
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            m_bIsLandScape = false;
            // 기기가 세로로 회전할때 할 작업
        }

        LayoutInflater inflater2 = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        m_vwMultiFragment = inflater2.inflate(R.layout.fragment_multi, null);
        getActivity().setContentView(m_vwMultiFragment);

        InitMultiFragment();

        if (m_dlgFrameLoad != null) {
            if (m_dlgFrameLoad.isShowing()) {
                m_dlgFrameLoad.dismiss();
                ShowFrameLoadDlg();
            }
        }
        if (m_dlgFrameSave != null) {
            if (m_dlgFrameSave.isShowing()) {
                m_dlgFrameSave.dismiss();
                ShowFrameSaveDlg();
            }
        }
        if (m_dlgFrameType != null) {
            if (m_dlgFrameType.isShowing()) {
                m_dlgFrameType.dismiss();
                ShowDialogMultiPopWnd();
            }
        }
    }

    public boolean getLeftTaskBarVisible() {
        return m_bTaskBarVisible;
    }

    public void setLeftTaskBarVisible(boolean m_bLeftTaskBarVisible) {
        this.m_bTaskBarVisible = m_bLeftTaskBarVisible;
    }

    public ImageView getImageViewTaskBarLeft() {
        return m_imgVwTaskBar;
    }

    public TaskBarLinearLayout getPowerMChartLeftTaskBar() {
        return m_llTaskBar;
    }

    public boolean getRightTaskBarVisible() {
        return m_bToolBarVisible;
    }

    public void setRightTaskBarVisible(boolean m_bRightTaskBarVisible) {
        this.m_bToolBarVisible = m_bRightTaskBarVisible;
    }

    public ImageView getImageViewTaskBarRight() {
        return m_imgVwToolBar;
    }

    public ToolBarLinearLayout getPowerMChartRightTaskBar() {
        return m_llToolBar;
    }

    private void SetUpConfigInfoObj(String strConfigType,boolean bFromMenuPopup,int nSelPos,ENObjectKindType enSelObjKind,String strDBName)
    {
        m_pConfigInfoObj.m_strConfigType = strConfigType;
        m_pConfigInfoObj.m_strPathChtSys = m_strPathChtSys;
        m_pConfigInfoObj.m_strPathChtUser = m_strPathChtUser;
        m_pConfigInfoObj.m_strChtTRNumber = m_strChtTRNumber;

        ChartView chtView = m_cChartCtrl.GetCurrentChartView();
        m_pConfigInfoObj.m_dPacketMaxValue = chtView.GetPacketMaxValue(true);
        m_pConfigInfoObj.m_dPacketMinValue = chtView.GetPacketMinValue(true);

        if(m_pConfigInfoObj.m_dPacketMaxValue == GlobalDefines.GD_NAVALUE_DOUBLE || m_pConfigInfoObj.m_dPacketMaxValue == GlobalDefines.GD_POSITIVE_D)
        {
            m_pConfigInfoObj.m_dPacketMaxValue = chtView.GetUserPrcValue(ENPacketDataType.GE_PACKET_CORE_CLOSE,0);
            m_pConfigInfoObj.m_dPacketMinValue = chtView.GetUserPrcValue(ENPacketDataType.GE_PACKET_CORE_CLOSE,0);
        }

        //View에서 Event(DoubleTab등)을 통해서 속성 설정 Popup
        m_pConfigInfoObj.m_bPopupType = bFromMenuPopup;
        if(bFromMenuPopup == true)
        {
            m_pConfigInfoObj.m_nChartSelectPos = nSelPos;
            m_pConfigInfoObj.m_enChartSeledKind = enSelObjKind;
            m_pConfigInfoObj.m_strObjSelectName = strDBName;
        }
    }
}

////////////////////////////////////////////////////////////////////////////////////////////////////
//사용하지는 않지만 추후를 고려해서 소스 유지

    /*public void ShowObjectSelPopupDlg(ENObjectKindType enObjectType, Point ptPos, String strDBName) {
        Message messageShow = new Message();
        Bundle bundle = new Bundle();
        bundle.putInt("ENObjectKind", enObjectType.GetIndexValue());

        bundle.putInt("PointX", ptPos.x);
        bundle.putInt("PointY", ptPos.y);
        bundle.putString("DBName", strDBName);

        messageShow.setData(bundle);
        //messageShow.what = GD_HANDLER_OBJECT_POPUP_SHOW;
        //HandlerObjectSelPopup.sendMessage(messageShow);
    }*/
    /*
     *********************
     **

     ** ChartView & Ctrl에서 Event Functions
     **
     *********************/
    /*public int OnSetChartLinkEvent(ENChartDataEventType enDataEvent, ST_COMM_REQUEST pstCommRqInfo) {
		int nCodeRotate = 0;	//1:Next,;-1:Prev
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
		case GE_DATA_EVENT_END_DT:	//일봉이상 기간조회 처리
			DoChangeDateTime(0.0,pstCommRqInfo.dEndDT[pstCommRqInfo.m_nPeriod],pstCommRqInfo.nTotCount[pstCommRqInfo.m_nPeriod]);
			break;
		case GE_DATA_EVENT_START_END_DT:	//일봉이상 기간조회 처리
			DoChangeDateTime(pstCommRqInfo.dStartDT[pstCommRqInfo.m_nPeriod],pstCommRqInfo.dEndDT[pstCommRqInfo.m_nPeriod],pstCommRqInfo.nTotCount[pstCommRqInfo.m_nPeriod]);
			break;
		case GE_DATA_EVENT_TODAY:			//분봉이하 당일조회 처리
			if (nID == -1) break;

			m_stNowCommRqInfo.bToday[m_stNowCommRqInfo.m_nPeriod] = pstCommRqInfo.bToday[pstCommRqInfo.m_nPeriod];
			stCommRequest = m_stNowCommRqInfo;
			if(stCommRequest.bToday[pstCommRqInfo.m_nPeriod] == true)
			{
				stCommRequest.dStartDT[m_stNowCommRqInfo.m_nPeriod] = pstCommRqInfo.dStartDT[pstCommRqInfo.m_nPeriod];
				stCommRequest.dEndDT[m_stNowCommRqInfo.m_nPeriod] = pstCommRqInfo.dEndDT[pstCommRqInfo.m_nPeriod];
				m_cChartCtrl.RequestAllChart(nID,ENChartDataEventType.GE_DATA_EVENT_TODAY,stCommRequest);
			}
			else
			{
				if(stCommRequest.nTotCountUser[m_stNowCommRqInfo.m_nPeriod] >= 0)
					stCommRequest.nTotCount[stCommRequest.m_nPeriod] = stCommRequest.nTotCountUser[stCommRequest.m_nPeriod];

				m_cChartCtrl.RequestAllChart(nID,ENChartDataEventType.GE_DATA_EVENT_REREQUEST,stCommRequest);
			}
			break;
		case GE_DATA_EVENT_CYCLE:			//주기차트 N일/N년 콤보 처리
			if (nID == -1) break;

			if(m_stNowCommRqInfo.m_nPeriod < ENPacketPeriodType.GE_PACKET_PERIOD_DAILY.GetIndexValue())
				m_stNowCommRqInfo.nCycleDays = pstCommRqInfo.nCycleDays;
			else
				m_stNowCommRqInfo.nCycleYears = pstCommRqInfo.nCycleYears;

			//m_enChartNavigate = GE_CHART_NAVIGATE_CHANGE;
			//m_cChartCtrl.SetChartNavigate(GE_CHART_NAVIGATE_CHANGE);
			m_cChartCtrl.SetCommRequestByID(nID,m_stNowCommRqInfo,ENChartDataEventType.GE_DATA_EVENT_CYCLE);
			StartRequestData(nID, m_stNowCommRqInfo.m_strCode, m_stNowCommRqInfo.m_strName, m_stNowCommRqInfo.m_nMarket, m_stNowCommRqInfo.m_nMarketGubun);
			//m_enChartNavigate = enChartNavigate;
			//m_cChartCtrl.SetChartNavigate(m_enChartNavigate);
			break;
		case GE_DATA_EVENT_NAVIGATE:		//추가/변경/중복 콤보 변경(nAddData를 해당 필드로 사용)
			//m_enChartNavigate = (EnChartNavigateType)pstCommRqInfo.nAddData;
			//m_cChartCtrl.SetChartNavigate(m_enChartNavigate);
			//m_enChartNavigate = m_enChartNavigate;
			break;
		case GE_DATA_EVENT_DATA_CONTINUE:
			if (nID == -1) break;

			//m_enChartNavigate = GE_CHART_NAVIGATE_CHANGE;
			//m_cChartCtrl.SetChartNavigate(GE_CHART_NAVIGATE_CHANGE);
			StartRequestData(nID, m_stNowCommRqInfo.m_strCode, m_stNowCommRqInfo.m_strName, m_stNowCommRqInfo.m_nMarket, m_stNowCommRqInfo.m_nMarketGubun,0,1);
			//m_enChartNavigate = enChartNavigate;
			//m_cChartCtrl.SetChartNavigate(m_enChartNavigate);
			break;
		case GE_DATA_EVENT_CODE_ROTATE:
			if (nID == -1) break;
			break;
		}

        return 1;
    }*/

//2020/01/13 - 가격차트,지표,신호,구간등 선택 처리 하지 않음
    /*@SuppressLint("HandlerLeak")
    private Handler HandlerObjectSelPopup = new Handler() {
        @SuppressLint("RtlHardcoded")
        public void handleMessage(Message m) {
            switch (m.what) {
                case GD_HANDLER_OBJECT_POPUP_SHOW:
                    int[] nLocationPosArray = new int[2];
                    m_cChartCtrl.GetCurrentChartView().getLocationInWindow(nLocationPosArray);
                    int nViewTop = nLocationPosArray[1];

                    Bundle bundle = m.getData();

                    int nX = bundle.getInt("PointX");
                    int nY = bundle.getInt("PointY");
                    nY += nViewTop;

                    ShowAllDropCtrls(true,ENDropCtrlType.LE_LONG_PRESS_POPUP_DLG);

                    TextView tvTitle = (TextView) vwPopup.findViewById(R.id.Tv_LongPress_Title);
                    Button btnFunc1 = (Button) vwPopup.findViewById(R.id.Btn_LongPress_Func1);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
                        btnFunc1.setBackground(FrameSkins.m_gradBtnUnsel26);
                    else
                        btnFunc1.setBackgroundDrawable(FrameSkins.m_gradBtnUnsel26);
                    Button btnFunc2 = (Button) vwPopup.findViewById(R.id.Btn_LongPress_Func2);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
                        btnFunc2.setBackground(FrameSkins.m_gradBtnUnsel26);
                    else
                        btnFunc2.setBackgroundDrawable(FrameSkins.m_gradBtnUnsel26);
                    Button btnFunc3 = (Button) vwPopup.findViewById(R.id.Btn_LongPress_Func3);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
                        btnFunc3.setBackground(FrameSkins.m_gradBtnUnsel26);
                    else
                        btnFunc3.setBackgroundDrawable(FrameSkins.m_gradBtnUnsel26);

                    final ENObjectKindType enObjectKindType = ENObjectKindType.GetEnumValue(bundle.getInt("ENObjectKind"));
                    final String strDBName = bundle.getString("DBName");
                    if (enObjectKindType == ENObjectKindType.GE_OBJECT_KIND_PRICE) {
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
                    } else if (enObjectKindType == ENObjectKindType.GE_OBJECT_KIND_INDICA ||
                            enObjectKindType == ENObjectKindType.GE_OBJECT_KIND_VOLUME) {
                        if (enObjectKindType == ENObjectKindType.GE_OBJECT_KIND_INDICA)
                            tvTitle.setText("지표");
                        else tvTitle.setText("거래량");

                        btnFunc1.setText("삭제");
                        btnFunc2.setText("속성");
                        btnFunc3.setText("닫기");

                        btnFunc1.setOnClickListener(new OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                m_cChartCtrl.GetCurrentChartView().DelFmIndicaObj(ENObjectYScaleType.GE_OBJECT_YSCALE_LOGIC, strDBName, -1, true);
                                GetFragmentMain().OnTaskBarItemSelEvent(enObjectKindType, strDBName, false, true);
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

                    if(m_dlgLongPressPopWnd == null)
                    {
                        View vwPopup = LayoutInflater.from(m_contMultiFragment).inflate(R.layout.dialog_longpress_function, null);
                        m_dlgLongPressPopWnd = new PopupWindow(vwPopup, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
                        m_dlgLongPressPopWnd.setAnimationStyle(-1); // 애니메이션 설정(-1:설정안함, 0:설정)
                        m_dlgLongPressPopWnd.setOutsideTouchable(true);
                    }

                    m_dlgLongPressPopWnd.showAtLocation(m_cChartCtrl.GetChartView(0), Gravity.TOP | Gravity.LEFT, nX - 200, nY - 100);
                    m_cChartCtrl.GetCurrentChartView().SetPopWin(m_dlgLongPressPopWnd);

                    Message messageDismiss = new Message();
                    messageDismiss.what = 1;
                    HandlerObjectSelPopup.sendMessageDelayed(messageDismiss, 3000);
                    break;
                case GD_HANDLER_OBJECT_POPUP_HIDE:
                    ShowAllDropCtrls(true,ENDropCtrlType.LE_LONG_PRESS_POPUP_DLG);
                    break;
            }
        }
    };*/

/*
 *********************
 **
 ** Dialog Event & Progress Functions
 **
 *********************/
/*@SuppressLint("StaticFieldLeak")
private class ChartFrameChangeAsyncTask extends AsyncTask<String, String, String> {
    private Context contProgress = null;

    public ChartFrameChangeAsyncTask(Context contProgress) {
        this.contProgress = contProgress;
    }

    @Override
    protected void onCancelled() {
        m_vwMultiFragment.findViewById(R.id.Relative_Chart).setVisibility(View.VISIBLE);
        m_vwMultiFragment.findViewById(R.id.Relative_Progress).setVisibility(View.GONE);
        super.onCancelled();
    }

    @Override
    protected void onPreExecute() {
        // 작업을 시작하기 전 할일
        m_vwMultiFragment.findViewById(R.id.Relative_Progress).setVisibility(View.VISIBLE);
        m_vwMultiFragment.findViewById(R.id.Relative_Chart).setVisibility(View.GONE);

        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... params) {
        if (this.isCancelled()) return null;
        if (params[0].equals("ChartFrameLoading")) {
            publishProgress(params[0], params[1]);
            return params[0];
        } else if (params[0].equals("ChangeFrameSize")) {
            publishProgress(params[0], params[1]);
            return params[0];
        }
        return null;
    }

    @Override
    protected void onProgressUpdate(String... values) {
        if (values[0].equals("ChartFrameLoading")) {
            //m_tvProgressMsg.setText("차트 로딩중...");

            String strLCWPath = values[1];
            SerializeChart(strLCWPath, 1, 0, true);

            m_cChartCtrl.RequestAllChart(-1);
        } else if (values[0].equals("ChangeFrameSize")) {
            //m_tvProgressMsg.setText("차트 로딩중...");

            int nFrameStatus = Integer.parseInt(values[1]);
            int nPrevID = m_cChartCtrl.GetCurrentChartID();
            if (nFrameStatus == -1 && m_cChartCtrl.IsFrameMaxStatus() == true) {
                m_llTabBar.setVisibility(View.GONE);

                m_cChartCtrl.SetFrameMaxStatus(false);
                ResetTabBarSubButton(1);
            }

            int nID = 0;
            try {
                nID = m_cChartCtrl.OnImpFrameResize(ENResizeObjectList.GE_RESIZE_FRAME_COUNT, nFrameStatus);
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (nID < 0) return;

            if (nPrevID != nID)
                m_stNowCommRqInfo = m_cChartCtrl.GetCurrentCommInfo();

            if (m_cChartCtrl.GetChartFrameSize() > 1 && m_cChartCtrl.IsFrameMaxStatus() == true) {
                m_cChartCtrl.SetFrameMaxStatus(false);
                ChangeTabBarMaxMinStatus(false);
            } else {
                ResetChartFrame();
                ResetTabBarSubButton(m_cChartCtrl.GetChartFrameSize());
            }
        }

        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(String result) {
        // 작업이 완료 된후 할일
        if (result != null && (result.equals("ChartFrameLoading") || result.equals("ChangeFrameSize"))) {
            m_vwMultiFragment.findViewById(R.id.Relative_Chart).setVisibility(View.VISIBLE);
            m_vwMultiFragment.findViewById(R.id.Relative_Progress).setVisibility(View.GONE);
        }
        super.onPostExecute(result);
    }
}*/

/*private class ChartInfoChangeAsyncTask extends AsyncTask<String, String, String> {
    private Context contProgress = null;
    private ProgressDlg dlgProgress = null;
    private ChartView chartViewCurrent = null;
    private int nCurrentChartID = -1;
    private boolean m_bOldScrollBarShow = false;

    public ChartInfoChangeAsyncTask(Context contProgress) {
        this.contProgress = contProgress;
        dlgProgress = new ProgressDlg(contProgress);
        dlgProgress.SetBackColorDrawable(Color.TRANSPARENT);

        chartViewCurrent = m_cChartCtrl.GetCurrentChartView();
        m_bOldScrollBarShow = m_cChartCtrl.GetCurrentChartView().IsShowScrollBar();
        nCurrentChartID = chartViewCurrent.GetViewCtrlID();
    }

    @Override
    protected void onCancelled() {
        if (dlgProgress != null) dlgProgress.dismiss();
        super.onCancelled();
    }

    @Override
    protected void onPreExecute() {
        if (dlgProgress != null || dlgProgress.isShowing() == false) {
            dlgProgress.SetMessageTextColor(Color.WHITE);
            dlgProgress.SetMessage("");
            dlgProgress.show();
        }

        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... params) {
        if (this.isCancelled()) return null;

        if (params[0].equals("ChangeProperty")) {
            m_cChartCtrl.ChangeCurrentChartGlobalProperty(ENConfigProperty.GE_CONFIG_PROPERTY_NONE, ENPropertyAfterType.GE_PROPERTY_AFTER_NONE);
            publishProgress(params[0]);
            return params[0];
        } else if (params[0].equals("ChangeAnalProperty")) {
            //m_cChartCtrl.ChangeCurrentChartGlobalProperty(ENConfigProperty.GE_CONFIG_PROPERTY_NONE, ENPropertyAfterType.GE_PROPERTY_AFTER_NONE);
            publishProgress(params[0]);
            return params[0];
        } else if (params[0].equals("CodeSync")) {
            if (m_cChartCtrl.GetChartFrameSize() > 1 && m_cChartCtrl.GetCodeSync() == true)
                m_cChartCtrl.MakeSameAllConfig(ENChartDataEventType.GE_DATA_EVENT_SAME_PERIOD);
            publishProgress(params[0]);
        } else if (params[0].equals("CodeSyncAll")) {
            publishProgress(params[0]);
        } else if (params[0].equals("PeriodSync")) {
            if (m_cChartCtrl.GetChartFrameSize() > 1 && m_cChartCtrl.GetPeriodSync() == true)
                m_cChartCtrl.MakeSameAllConfig(ENChartDataEventType.GE_DATA_EVENT_SAME_PERIOD);
            //m_cChartCtrl.SetPacketPeriodTypeByID(nCurrentChartID, m_enPacketPeriod);
            publishProgress(params[0]);
        } else if (params[0].equals("PeriodSyncAll")) {
            m_cChartCtrl.SetPacketPeriodTypeByID(nCurrentChartID, m_enPacketPeriod);
            publishProgress(params[0]);
        } else if (params[0].equals("ConfigSync")) {
            publishProgress(params[0]);
        }
        return null;
    }

    @Override
    protected void onProgressUpdate(String... values) {
        if (values[0].equals("ChangeProperty")) {
            int i = 0;
            int nNowChartIndex = m_cChartCtrl.GetFocusFrameIndex();
            boolean bScrollBarShow = m_cChartCtrl.GetCurrentChartView().IsShowScrollBar();
            if (m_bOldScrollBarShow != bScrollBarShow) {
                for (i = 0; i < m_cChartCtrl.GetChartFrameSize(); i++) {
                    if (i == nNowChartIndex) continue;

                    if (m_bOldScrollBarShow != bScrollBarShow)
                        m_cChartCtrl.GetChartView(i).SetShowScrollBar(bScrollBarShow);

                    m_cChartCtrl.GetChartView(i).DoSizeChange();
                    m_cChartCtrl.GetChartView(i).Redraw();
                }
            }

            //ChangeChartGlobalProperty 에서 제외한 DrawBitmap 여기서 호출
            m_cChartCtrl.RedrawCurrentChart();
        } else if (values[0].equals("ChangeAnalProperty")) {
        } else if (values[0].equals("CodeSync")) {
            if (m_cChartCtrl.GetChartFrameSize() > 1 && m_cChartCtrl.GetCodeSync() == true)
                m_cChartCtrl.MakeSameAllConfig(ENChartDataEventType.GE_DATA_EVENT_SAME_ITEM);
        } else if (values[0].equals("CodeSyncAll")) {
            m_cChartCtrl.MakeSameAllConfig(ENChartDataEventType.GE_DATA_EVENT_SAME_ITEM_ALL);
        } else if (values[0].equals("PeriodSync")) {
            if (m_cChartCtrl.GetChartFrameSize() > 1 && m_cChartCtrl.GetPeriodSync() == true)
                m_cChartCtrl.MakeSameAllConfig(ENChartDataEventType.GE_DATA_EVENT_SAME_PERIOD);
        } else if (values[0].equals("PeriodSyncAll")) {
            m_cChartCtrl.MakeSameAllConfig(ENChartDataEventType.GE_DATA_EVENT_SAME_PERIOD_ALL);
        } else if (values[0].equals("ConfigSync")) {
            m_cChartCtrl.MakeSameAllConfig(ENChartDataEventType.GE_DATA_EVENT_SAME_CONFIG);
        }

        if (values[0].equals("CodeChange"))
            ResetTabBarSubButtonTitle(-1);
        else if (values[0].equals("CodeSync") || values[0].equals("CodeSyncAll") || values[0].equals("PeriodSync") || values[0].equals("PeriodSyncAll"))
            ResetTabBarSubButtonTitle(-1);

        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(String result) {
        if (result != null && result.equals("ChangeProperty")) {
            //주의:bFileRead부분을 다른데서는 없을때만 true로 셋팅되는데
            //속성에서 주기값이 변경되었는지 체킹이 어려워서 강제로 true설정
            //LoadXMLPrdValueList(m_enPacketPeriod, true, true);
            ResetToolBarInfo();
        }
        if (dlgProgress != null) dlgProgress.dismiss();
        super.onPostExecute(result);
    }
}*/

//참고 Emulator File - View/Tool Windows/Device File Explorer
//Storage/Emulated/0/PowerMChart/~~~


    /*
    //private boolean m_bOverActivityMode = false;
    @Override
    public void onStart() {
        super.onStart();

        //2016/07/11 [강민석] 속성설정 Activty Over하는경우 데이터 처리
        if(m_bOverActivityMode == false)
        {
            InitChartRequests(false);
            m_cDataHandler.DoStart();
        }

        //2016/07/11 onActivityResult 이후에 onResume 호출됨
        if(m_bOverActivityMode == true) m_bOverActivityMode = false;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {

        super.onPause();
        //2016/07/11 [강민석] 속성설정 Activty Over하는경우 데이터 처리
        if (m_bOverActivityMode == false) {
            m_cDataHandler.CloseDataHandler();
            m_cDataHandler.DoStop();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
    }*/