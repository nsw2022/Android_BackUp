package chartlab.PowerMChartMain.Main;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

import chartlab.PowerMChartApp.Config.ConfigFormGraphActivity;
import chartlab.PowerMChartApp.FrameWnd.ChartView;
import chartlab.PowerMChartApp.FrameWnd.GraphCtrl;
import chartlab.PowerMChartApp.Util.FragmentAppMain;
import chartlab.PowerMChartApp.Util.BaseApplication;
import chartlab.PowerMChartEngine.Util.FrameSkins;
import chartlab.PowerMChartEngine.KernelCore.GlobalDefines;
import chartlab.PowerMChartEngine.KernelCore.GlobalProperty;
import chartlab.PowerMChartEngine.KernelCore.GlobalStructs;
import chartlab.PowerMChartEngine.KernelCore.GlobalStructs.ST_PACKET_COREDATA;
import chartlab.PowerMChartApp.Widget.UtilControl.MessageBox;
import chartlab.PowerMChartEngine.KernelCore.GlobalUtils;
import chartlab.PowerMChartEngine.Util.ColorNames;

import chartlab.PowerMChartEngine.KernelCore.GlobalEnums;
import chartlab.PowerMChartEngine.KernelCore.GlobalEnums.ENGradationPosType;
import chartlab.PowerMChartEngine.KernelCore.GlobalEnums.ENPriceKindType;
import chartlab.PowerMChartEngine.KernelCore.GlobalEnums.ENObjectKindType;
import chartlab.PowerMChartEngine.KernelCore.GlobalEnums.ENScatterDrawType;
import chartlab.PowerMChartEngine.KernelCore.GlobalEnums.ENBlockResizeType;
import chartlab.PowerMChartEngine.KernelCore.GlobalEnums.ENXAxisDataFormat;
import chartlab.PowerMChartEngine.KernelCore.GlobalEnums.ENLogoImgPosType;
import chartlab.PowerMChartEngine.KernelCore.GlobalEnums.ENXLayerVertDspType;
import chartlab.PowerMChartEngine.KernelCore.GlobalEnums.ENObjectIndicaType;
import chartlab.PowerMChartEngine.KernelCore.GlobalEnums.ENGradationDirectType;
import chartlab.PowerMChartEngine.KernelCore.GlobalEnums.ENFUNCToolType;
import chartlab.PowerMChartEngine.KernelCore.GlobalEnums.ENLineStyleType;
import chartlab.PowerMChartEngine.KernelCore.GlobalEnums.ENBaseLineType;
import chartlab.PowerMChartEngine.KernelCore.GlobalEnums.ENYLayerExistType;
import chartlab.PowerMChartEngine.KernelCore.GlobalEnums.ENChartFrameType;
import chartlab.PowerMChartEngine.KernelCore.GlobalEnums.ENTitleDspType;
import chartlab.PowerMChartEngine.KernelCore.GlobalEnums.ENObjectYScaleType;
import chartlab.PowerMChartEngine.KernelCore.GlobalEnums.ENDISABLEEventType;
import chartlab.PowerMChartEngine.KernelCore.GlobalEnums.ENBlockDeleteType;
import chartlab.PowerMChartEngine.KernelCore.GlobalEnums.ENPacketDataType;
import chartlab.PowerMChartEngine.KernelCore.GlobalEnums.ENYAxisDataFormat;
import chartlab.PowerMChartEngine.KernelCore.GlobalEnums.ENYLayerPosType;
import chartlab.PowerMChartEngine.KernelCore.GlobalEnums.ENChartOCXEventType;
import chartlab.PowerMChartEngine.KernelCore.GlobalEnums.ENANALToolType;
import chartlab.PowerMChartEngine.KernelCore.GlobalEnums.ENLabelTextXPos;
import chartlab.PowerMChartEngine.KernelCore.GlobalEnums.ENLabelTextYPos;
import chartlab.PowerMChartEngine.KernelCore.GlobalEnums.ENXAxisTextAlign;
import chartlab.PowerMChartEngine.KernelCore.GlobalStructs.ST_COMM_REQUEST;

public class FormGraphFragmentApp extends FragmentAppMain implements OnClickListener
{
    //파일경로 관련 변수
    private String m_strPathChtUserRoot = "";
    private String m_strPathChtSys = "";
    private String m_strPathChtUser = "";
    private String m_strChtTRNumber = "";

    //데이터 관련 변수
    private ST_COMM_REQUEST m_stNowCommRqInfo = null;

    private MessageBox m_cMessageBoxHandler = null;

    // 화면 모드 관련 변수
    private ENPriceKindType m_enPriceKind = ENPriceKindType.GE_PRICE_KIND_NONE;
    private String m_strGraphType = "";
    private int m_nMixGraphType = -1;
    private boolean m_bIsAttachFormGraph = false;

    private int m_nTimeCircle = 21;

    //0:Query,1:Add,2:Update,3:Shift
    private int m_nQueryDataEvent = 0;

    //주의: X,Y축등 해상도 파일에 설정된 값 변경시는 무조건 해상도 값을 곱해야 하고
    //아닌 부분도 해상도 정보를 곱해야 한다(EX : SetSpacePixcel,SetBongMargin)
    //근데 SetBongMargin은 진짜로 1을 줄때와 1*GD_DEVICE_DPI_WEIGHT_INT를 구분해서 사용해야 한다
    private int m_nDefaultYLayerWidth = GlobalDefines.GD_DEFAULT_YLAYER_WIDTH;
    private int m_nDefaultXLayerHeight = GlobalDefines.GD_DEFAULT_XLAYER_HEIGHT;

    private GraphCtrl m_cGraphCtrl = null;

    boolean m_bIsLandScape = false;
    int m_nDateTimeIndex = ENPacketDataType.GE_PACKET_CORE_DATETIME.GetIndexValue();
    int m_nOpenIndex = ENPacketDataType.GE_PACKET_CORE_OPEN.GetIndexValue();
    int m_nHighIndex = ENPacketDataType.GE_PACKET_CORE_HIGH.GetIndexValue();
    int m_nLowIndex = ENPacketDataType.GE_PACKET_CORE_LOW.GetIndexValue();
    int m_nCloseIndex = ENPacketDataType.GE_PACKET_CORE_CLOSE.GetIndexValue();
    int m_nVolumeIndex = ENPacketDataType.GE_PACKET_CORE_VOLUME.GetIndexValue();

    int m_nCurrentID = -1;

    final  static  int DELAY = 3000;
    final  static  int TIME_DELAY = 1000;
    final  static  int PLAY = 1;
    final  static  int TIME_PLAY = 2;
    final  static  int STOP = 0;

    //--IOS 누락
    private int m_nHandlerStatus = -1;
    
    private ImageButton m_btnBack = null;
    private ImageButton m_btnConfig = null;

    public static Context m_contFormGraph = null;
    private View m_vwFormGraph = null;

    private float    m_fDeviceDPIWeight = 1.0f;

    //ChartView
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        m_contFormGraph = getActivity();

        m_bIsLandScape = BaseApplication.GetOrientationLandScape(m_contFormGraph);
        m_fDeviceDPIWeight = GlobalUtils.GetDeviceDPIWeight(m_contFormGraph,m_bIsLandScape);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        m_vwFormGraph = inflater.inflate(R.layout.fragment_formgraph, container, false);
        return m_vwFormGraph;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        InitFormGraphFragment();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);

        InitFormGraphFragment();
    }

    private void InitFormGraphFragment()
    {
        InitGraphCtrl();
        InitControls(); //버튼 셋팅

        m_vwFormGraph.findViewById(R.id.Relative_TitleBar).setBackgroundColor(FrameSkins.m_crNAVIBack);
        InitDataGraphCtrl();

        this.SetFragmentMain(new InterfaceFragmentMainEvent()
        {
            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) { }

            @Override
            public void OnGetChartLinkEvent(ENChartOCXEventType enOCXEvent, int m_nCurrentID, GlobalStructs.ST_COMM_REQUEST stCommRequest, int nValue,String strValue)
            {
                int nRQType = 0;
                int nViewSize = -1;
                int	nTotCount = -1;

                switch(enOCXEvent)
                {
                    case GE_OCX_EVENT_REQUEST:
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
                        //if(nValue == 100) m_cDataHandler.CancelRequest(m_nCurrentID,"ALL");
                        //else m_cDataHandler.CancelRequest(m_nCurrentID,nValue + "");
                        break;
                    case GE_OCX_EVENT_OVERLAP_DELETE:
                        break;
                    case GE_OCX_EVENT_QUERY_RECEIVE:
                        break;
                    case GE_OCX_EVENT_QUERY_COMPLETE:
                        break;
                    case GE_OCX_EVENT_CHANGE_COUNT:
                        if(m_nCurrentID == m_cGraphCtrl.GetCurrentChartID())
                        {
                            nViewSize = m_cGraphCtrl.GetCurrentChartView().GetPacketViewSize();
                            if(m_stNowCommRqInfo.nPeriod  >= 0)
                                m_stNowCommRqInfo.nViewCount[m_stNowCommRqInfo.nPeriod] = nViewSize;

                            nTotCount = m_cGraphCtrl.GetCurrentChartView().GetPacketTotSize();
                            //m_cMessageBoxHandler.SetMessageBoxStatus("View Size - " + nViewSize, 1000, Gravity.CENTER, 0, 300).ShowMessage();
                            m_cMessageBoxHandler.SetMessageBoxStatus( nViewSize+ "/" + nTotCount, 1000, Gravity.CENTER, 0, 300).ShowMessage();

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
            public void OnChangeBtmBarStatusEvent() { }

            @Override
            public void OnTaskBarHideEvent() { }

            @Override
            public void OnToolBarHideEvent() { }

            @Override
            public void OnToggleToolBarEvent(int nToolType, int nIndex, boolean bCheck) {  }

            @Override
            public void OnANALSelPopupDlgEvent(ENANALToolType enANALSelectType, Point ptPos) { }

            @Override
            public GlobalProperty.CProperty_BASE OnGetChartGlobalPropertyEvent(boolean bReloading, boolean bWorkspace) { return null; }

            @Override
            public GlobalStructs.ST_ANALPOINT_INFO OnGetSelANALInfoEvent() {
                return null;
            }

            @Override
            public void OnSetSelANALInfoEvent(GlobalStructs.ST_ANALPOINT_INFO stANALToolInfo) { }

            @Override
            public int OnFindXDateIndexEvent(double dXDateTime, int nStart, int nIfNoEqualNearIndex) { return 0; }
        });
    }

    /*
     *********************
     **
     ** Init Functions
     **
     *********************/
    private void InitGraphCtrl()
    {
        m_cGraphCtrl = new GraphCtrl();
        m_cGraphCtrl.InitGraphCtrl(this, m_contFormGraph);

        m_strPathChtUserRoot = GlobalUtils.GetStorageDirPath() + GlobalDefines.GD_ROOT_DIRECTORY;
        m_strPathChtSys = GlobalUtils.GetStorageDirPath()+ GlobalDefines.GD_CHARTINFO_DIRECTORY;
        m_strPathChtUser = GlobalUtils.GetStorageDirPath() + GlobalDefines.GD_CHARTINFO_USER_DIRECTORY;
        m_strChtTRNumber = GlobalDefines.GD_GRAPH_TR_FORM;

        m_cGraphCtrl.SetPathChtUserRoot(m_strPathChtUserRoot);
        m_cGraphCtrl.SetPathChtSys(m_strPathChtSys);
        m_cGraphCtrl.SetPathChtUser(m_strPathChtUser);
        m_cGraphCtrl.SetChartTRNumber(m_strChtTRNumber);

        m_cGraphCtrl.CreateDirectory();

        m_cGraphCtrl.InitGraphCtrl(this, m_contFormGraph);
        ChartView cChartView = (ChartView) m_vwFormGraph.findViewById(R.id.ChartView_FormGraph);
        m_cGraphCtrl.AddChartView(cChartView, true);

        m_nCurrentID =  m_cGraphCtrl.GetCurrentChartID();
        m_nDefaultYLayerWidth = m_cGraphCtrl.GetYLayerDefWidth(m_nCurrentID, ENYLayerPosType.GE_YLAYER_POS_RIGHT);
        m_nDefaultXLayerHeight = m_cGraphCtrl.GetXLayerDefHeight(m_nCurrentID);


        m_stNowCommRqInfo = new GlobalStructs.ST_COMM_REQUEST();

        //--ios 누락
        m_nDateTimeIndex = ENPacketDataType.GE_PACKET_CORE_DATETIME.GetIndexValue();
        m_nOpenIndex = ENPacketDataType.GE_PACKET_CORE_OPEN.GetIndexValue();
        m_nHighIndex = ENPacketDataType.GE_PACKET_CORE_HIGH.GetIndexValue();
        m_nLowIndex = ENPacketDataType.GE_PACKET_CORE_LOW.GetIndexValue();
        m_nCloseIndex = ENPacketDataType.GE_PACKET_CORE_CLOSE.GetIndexValue();
        m_nVolumeIndex = ENPacketDataType.GE_PACKET_CORE_VOLUME.GetIndexValue();
        m_nHandlerStatus = STOP;

        m_cMessageBoxHandler = new MessageBox(m_contFormGraph, m_vwFormGraph);
        //--

    }

    private void InitControls()
    {
        m_btnBack = (ImageButton) m_vwFormGraph.findViewById(R.id.Btn_TitleBar_Back);
        m_btnBack.setOnClickListener(this);

        m_btnConfig = (ImageButton) m_vwFormGraph.findViewById(R.id.Btn_TitleBar_Config);
        m_btnConfig.setOnClickListener(this);

        if(m_bIsAttachFormGraph == true)
        {
            RelativeLayout relativeLayoutTitleBar = (RelativeLayout) m_vwFormGraph.findViewById(R.id.Relative_TitleBar);
            relativeLayoutTitleBar.setVisibility(View.GONE);
        }
    }

    public void SetFormGraphType(String strGraphType) {
        m_strGraphType = strGraphType;
    }

    public void SetAttachFormGraph(boolean bIsAttachFormGraph)
    {       m_bIsAttachFormGraph = bIsAttachFormGraph;      }

    public  void SetMixGraphType(int nMixGraphType) {
        m_nMixGraphType = nMixGraphType;
    }

    public void InitDataGraphCtrl()
    {
        String strPriceName = "";
        m_enPriceKind = ENPriceKindType.GE_PRICE_KIND_NONE;
        if(m_nMixGraphType >= 0)
        {
            switch (m_nMixGraphType)
            {
                ///////////////////////////////////
                case 0: m_enPriceKind = ENPriceKindType.GE_PRICE_KIND_OSCBAR;  InitGraphMixOscBarLine(); break;
                case 1: m_enPriceKind = ENPriceKindType.GE_PRICE_KIND_LINEDOT; InitGraphMixMultiLineDots(); break;
                case 2: m_enPriceKind = ENPriceKindType.GE_PRICE_KIND_STICKBAR; InitGraphMixStickBar_Stair(); break;
                case 3: m_enPriceKind = ENPriceKindType.GE_PRICE_KIND_GROUPBAR; InitGraphMixGroupBar_LineDots(); break;
                case 4: m_enPriceKind = ENPriceKindType.GE_PRICE_KIND_2SIDEBAR; InitGraphMix2SideBar_LineDot(); break;
                case 5: m_enPriceKind = ENPriceKindType.GE_PRICE_KIND_HORZ_BAR; InitGraphMixHorzBar_MT(); break;
                case 6: m_enPriceKind = ENPriceKindType.GE_PRICE_KIND_STACKBAR; InitGraphMixStackBar_OSCBar(); break;
                default:break;
            }

            return;
        }

        for(int i=16;i<GlobalEnums.GE_OBJPRICETYPENAMES.length;i++)
        {
            strPriceName = "Graph-" + GlobalEnums.GE_OBJPRICETYPENAMES[i];
            if(m_strGraphType.equals(strPriceName) == true)
            {
                m_enPriceKind = ENPriceKindType.GetEnumValue(i);
                break;
            }
        }


        if(m_enPriceKind == ENPriceKindType.GE_PRICE_KIND_NONE)
        {
            int nGraphKind =  Integer.parseInt(m_strGraphType);
            switch (nGraphKind)
            {
                ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                case 4: m_enPriceKind = ENPriceKindType.GE_PRICE_KIND_CANDLE;   InitGraphCandle(); break;
                case 5: m_enPriceKind = ENPriceKindType.GE_PRICE_KIND_LINE;   InitGraphLine(); break;
                case 6: m_enPriceKind = ENPriceKindType.GE_PRICE_KIND_MOUNTAIN; InitGraphMountain(); break;
                case 7: m_enPriceKind = ENPriceKindType.GE_PRICE_KIND_FLOWBAR;  InitGraphFlow(); break;
                case 8: m_enPriceKind = ENPriceKindType.GE_PRICE_KIND_STAIR;    InitGraphStair(); break;
                case 9: m_enPriceKind = ENPriceKindType.GE_PRICE_KIND_SCATTER;  InitGraphScatter(); break;
            }
        }
        else
        {
            switch (m_enPriceKind) {
                case GE_PRICE_KIND_RISEFALL:
                    InitGraphRiseFall();
                    break;
                case GE_PRICE_KIND_OSCBAR:
                    InitGraphOscBar();
                    break;
                case GE_PRICE_KIND_STICKBAR:
                    InitGraphStickBar();
                    break;
                case GE_PRICE_KIND_2SIDEBAR:
                    InitGraph2SideBar();
                    break;
                case GE_PRICE_KIND_STACKBAR:
                    InitGraphStackBar();
                    break;
                case GE_PRICE_KIND_GROUPBAR:
                    InitGraphGroupBar();
                    break;
                case GE_PRICE_KIND_HORZ_BAR:
                    InitGraphHorizontalBar();
                    break;
                case GE_PRICE_KIND_HORZ_OSCBAR:
                    InitGraphHorizontalOscBar();
                    break;
                case GE_PRICE_KIND_HORZ_2SIDEBAR:
                    InitGraphHorizontal2sideBar();
                    break;
                case GE_PRICE_KIND_HORZ_STACKBAR:
                    InitGraphHorzStackBar();
                    break;
                case GE_PRICE_KIND_PIE:
                    InitGraphPie();
                    //20178/05/04 - 3DPie는 Drawing오류로 동작 불가
                    //InitGraph3DPie();
                    break;
                case GE_PRICE_KIND_DONUT:
                    InitGraphDonut();
                    break;
                case GE_PRICE_KIND_RADAR:
                    InitGraphRadar();
                    break;
                case GE_PRICE_KIND_TIME_CIRCLE:
                    InitGraphTimeCircle();
                    break;
                case GE_PRICE_KIND_LINEDOT:
                    InitGraphLineDot();
                    break;
                case GE_PRICE_KIND_STACK_MOUNTAIN:
                    InitGraphStackMT();
                    break;
                case GE_PRICE_KIND_BUBBLE_SCATTER:
                    InitGraphBubbleScatter();
                    break;

                    default: break;
            }
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Graph Mix
    public void InitGraphMixStackBar_OSCBar()
    {
        if(m_cGraphCtrl == null) return;

        InitGraphProperty(ENPriceKindType.GE_PRICE_KIND_STACKBAR,"매출액/영업이익/순이익",10,0,false,false);

        //라벨 텍스트
        //주의:StackBar,MT,HorzBar,HorzMT등은 SubLabel만 설정한다
        //최종합산 값을 구하려고 하면 마지막 값 인덱스만 나오게 하면되고
        //개별적으로 모두 나오게 하려면 기본값(-1)로 설정하면 된다
        //m_cGraphCtrl.SetShowLabelText(m_nCurrentID, true)
        m_cGraphCtrl.SetShowSubLabelText(m_nCurrentID, true);
        m_cGraphCtrl.SetSubLabelTextIndex(m_nCurrentID, 2);

        //X,Y축 설정
        m_cGraphCtrl.SetXAxisDataFormat(m_nCurrentID, ENXAxisDataFormat.GE_XAXIS_DATA_FORMAT_STRING);
        m_cGraphCtrl.SetYAxisDataFormat(m_nCurrentID, ENYAxisDataFormat.GE_YAXIS_DATA_FORMAT_VALUE);

        //Y측 우측에 보기
        m_cGraphCtrl.SetYLayerExist(m_nCurrentID,ENYLayerExistType.GE_YLAYER_EXIST_RIGHT,false,false);

        //가격차트 그라데이션 설정
        m_cGraphCtrl.SetPriceGradation(m_nCurrentID, true);
        //m_cGraphCtrl.SetGradientDirectType(m_nCurrentID,ENGradationDirectType.GE_GRADATION_LEFT_RIGHT);
        m_cGraphCtrl.SetGradientDirectType(m_nCurrentID, ENGradationDirectType.GE_GRADATION_HCENTER_OUT);
        m_cGraphCtrl.SetGradientPosType(m_nCurrentID, ENGradationPosType.GE_GRADATION_POS_START);
        m_cGraphCtrl.SetGradientPosColor(m_nCurrentID, 0, GlobalUtils.GetRGBToUIColor(200, 200, 255));

        //기본소숫점
        m_cGraphCtrl.SetMainFloatingPoint(m_nCurrentID,0);

        //봉간격
        //m_cGraphCtrl.SetBongMargin(m_nCurrentID, 2)
        m_cGraphCtrl.SetBongMargin(m_nCurrentID,(int)(2*m_fDeviceDPIWeight));

        //그룹 개수
        m_cGraphCtrl.SetGroupDataCount(m_nCurrentID,3);

        //상단 색상
        m_cGraphCtrl.SetGroupBodyFill(m_nCurrentID, 0,true);
        m_cGraphCtrl.SetGroupBodyColor(m_nCurrentID, 0,Color.rgb(34,76,126));
        m_cGraphCtrl.SetGroupTextColor(m_nCurrentID, 0,ColorNames.GC_Black);


        m_cGraphCtrl.SetGroupBodyFill(m_nCurrentID, 1,true);
        m_cGraphCtrl.SetGroupBodyColor(m_nCurrentID, 1,Color.rgb(45,94,155));
        m_cGraphCtrl.SetGroupTextColor(m_nCurrentID, 1,ColorNames.GC_Black);

        m_cGraphCtrl.SetGroupTextColor(m_nCurrentID, 1,Color.rgb(45,94,155));
        m_cGraphCtrl.SetGradientPosColor(m_nCurrentID,1, Color.rgb(200,200,255));

        m_cGraphCtrl.SetGroupBodyFill(m_nCurrentID, 2,true);
        m_cGraphCtrl.SetGroupBodyColor(m_nCurrentID, 2,Color.rgb(124,140,169));
        m_cGraphCtrl.SetGroupTextColor(m_nCurrentID, 2,ColorNames.GC_Black);

        m_cGraphCtrl.SetGroupTextColor(m_nCurrentID, 2,Color.rgb(124,140,169));
        m_cGraphCtrl.SetGradientPosColor(m_nCurrentID,2, Color.rgb(200,200,255));

        //Y축 BlockButton Hide
        m_cGraphCtrl.ChangeFUNCToolType(ENFUNCToolType.GE_FUNCTOOL_YAXIS_BUTTON,0);
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //데이터 입력
        int  nDataCount = 12;
        ArrayList<ST_PACKET_COREDATA> cPacketList = new ArrayList<ST_PACKET_COREDATA>();
        ST_PACKET_COREDATA stPacketBasicDataP = null;

        int i=0;
//        String strXDate = "";
        String strYDate = "";        
        for(i=0;i<nDataCount;i++)
        {
            stPacketBasicDataP = new GlobalStructs.ST_PACKET_COREDATA();

            strYDate = GlobalUtils.GetEnglishNameOfMonth(1+i);
            m_cGraphCtrl.AddUserXDTString(m_nCurrentID, strYDate);

            stPacketBasicDataP.dValueList[m_nOpenIndex] = GetRandomValue(30000.0, 1000.0);
            stPacketBasicDataP.dValueList[m_nHighIndex] = GetRandomValue(30000.0, 1000.0);
            stPacketBasicDataP.dValueList[m_nLowIndex] = GetRandomValue(30000.0, 1000.0);

            cPacketList.add(stPacketBasicDataP);
        }

        m_cGraphCtrl.ReceiveQuery(m_nCurrentID,"StackBar","", 0, 0,0,cPacketList,0);
//        if(m_bIsAttachFormGraph == true)
//        {
//            m_cGraphCtrl.ResizeChartBlock(m_nCurrentID, ENBlockResizeType.GE_BLOCK_RESIZE_INIT);
//            m_cGraphCtrl.RedrawCurrentChart();
//        }

        ////////////////////////////////////////////////////////////////////////////////////////////////////////
        //하위 Line추가
        //주의:IndicaDrawType이 OSC이면 뒤에 선색,굵기,유형은 동작하지 않음
        //별도 상승하락색상을 지정해 줘야 한다
        m_cGraphCtrl.AddUserChartObject(m_nCurrentID,"재고율",ENObjectKindType.GE_OBJECT_KIND_INDICA, ENObjectYScaleType.GE_OBJECT_YSCALE_DERIVE,
                GlobalDefines.GD_OBJECT_NEW_REGION_LAST, ENObjectIndicaType.GE_OBJECT_INDICA_OSCBAR, ColorNames.GC_FireBrick,ENLineStyleType.PS_SOLID,3,0);
        //상승색
        m_cGraphCtrl.SetObject_PropertyInfo(m_nCurrentID,"재고율",0, 14, ColorNames.GC_Orange);
        //하락색
        m_cGraphCtrl.SetObject_PropertyInfo(m_nCurrentID,"재고율",0, 16, ColorNames.GC_BlueViolet);

        //텍스트표시
        m_cGraphCtrl.SetLabelTextXPos(m_nCurrentID, "재고율", ENLabelTextXPos.GE_LABEL_TEXT_XPOS_LEFT);
        m_cGraphCtrl.SetObjLabelText(m_nCurrentID,"재고율",true);

        int nBlock_ObjIndexs[] = new int[2];
        boolean bFind = m_cGraphCtrl.GetObjectBlockIndex(m_nCurrentID,ENObjectKindType.GE_OBJECT_KIND_INDICA,"재고율",nBlock_ObjIndexs);
        if(bFind == true)
        {
            for (i = 0; i < nDataCount; i++)
            {
                stPacketBasicDataP = ST_PACKET_COREDATA.AllocMemory();

                stPacketBasicDataP.dValueList[m_nCloseIndex] = GetRandomValue(1000.0, -1000.0);
                m_cGraphCtrl.AddUserDataBasic(m_nCurrentID, nBlock_ObjIndexs[0],nBlock_ObjIndexs[1], 0, stPacketBasicDataP);
            }

            m_cGraphCtrl.RedrawCurrentChart();
        }
        m_cGraphCtrl.SetChartBlockRatio(m_nCurrentID, 0, 0.0, 0.67, false);
        m_cGraphCtrl.SetChartBlockRatio(m_nCurrentID, 1, 0.67,1.0, false);
        m_cGraphCtrl.ResizeChartBlock(m_nCurrentID, ENBlockResizeType.GE_BLOCK_RESIZE_INIT);
        m_cGraphCtrl.RedrawCurrentChart();

        //3초 대기 핸들러
        if(m_bIsAttachFormGraph == false)
        {
            if (m_nHandlerStatus == STOP)
            {
                m_nHandlerStatus = PLAY;
                handler.sendEmptyMessageDelayed(0, DELAY);
            }
        }
    }

    public void InitGraphMixHorzBar_MT()
    {
        if(m_cGraphCtrl == null) return;

        InitGraphProperty(ENPriceKindType.GE_PRICE_KIND_HORZ_BAR,"매출액",10,0,false,false);

        //X,Y축 설정
        m_cGraphCtrl.SetXAxisDataFormat(m_nCurrentID, ENXAxisDataFormat.GE_XAXIS_DATA_FORMAT_VALUE);
        m_cGraphCtrl.SetYAxisDataFormat(m_nCurrentID, ENYAxisDataFormat.GE_YAXIS_DATA_FORMAT_STRING);

        //Y측 양측에 보기
        m_cGraphCtrl.SetYLayerExist(m_nCurrentID,ENYLayerExistType.GE_YLAYER_EXIST_LEFT,false,false);

        m_cGraphCtrl.SetXLayerGridShow(m_nCurrentID, true);

        //X/Y축스케일 100%로변경
        m_cGraphCtrl.SetChartYScaleRatio(m_nCurrentID,0,1.0);

        //최소값 설정
        m_cGraphCtrl.SetPriceXMaxMinValue(m_nCurrentID,GlobalDefines.GD_NAVALUE_DOUBLE,400.0);

        //가격차트 그라데이션 설정
        m_cGraphCtrl.SetPriceGradation(m_nCurrentID, true);
        m_cGraphCtrl.SetGradientDirectType(m_nCurrentID,ENGradationDirectType.GE_GRADATION_LEFT_RIGHT);
        m_cGraphCtrl.SetGradientPosType(m_nCurrentID,ENGradationPosType.GE_GRADATION_POS_START);
        m_cGraphCtrl.SetGradientPosColor(m_nCurrentID,0, ColorNames.GC_LightGreen);

        //LabelTextXPos
        m_cGraphCtrl.SetPrice_PropertyInfo(m_nCurrentID, 21, ENLabelTextXPos.GE_LABEL_TEXT_XPOS_CENTER.GetIndexValue());
        //LabelTextYPos
        m_cGraphCtrl.SetPrice_PropertyInfo(m_nCurrentID, 22, ENLabelTextYPos.GE_LABEL_TEXT_YPOS_CENTER.GetIndexValue());

        //OffIn
        m_cGraphCtrl.SetPrice_PropertyInfo(m_nCurrentID, 7, ColorNames.GC_CadetBlue);
        //OffFill
        m_cGraphCtrl.SetPrice_PropertyInfo(m_nCurrentID, 8, 1);

        //데이터 사이 간격
        m_cGraphCtrl.SetBongMargin(m_nCurrentID, (int)(5*m_fDeviceDPIWeight));

        /*//2012/10/05 - 3D그래프 표현
        m_cGraphCtrl.Set3DGraphDraw(m_nCurrentID, true);
        m_cGraphCtrl.Set3DDepthRatio_Pixel(m_nCurrentID, true);	//TRUE:Ratio/FALSE:Pixel
        m_cGraphCtrl.Set3DDepthRatio(m_nCurrentID, 0.02);
        m_cGraphCtrl.Set3DDepthXAxisPixel(m_nCurrentID, 5);
        m_cGraphCtrl.Set3DDepthYAxisPixel(m_nCurrentID, 10);
        m_cGraphCtrl.Set3DHorzDepthUpDn(m_nCurrentID,true);		//TRUE:Up/FALSE:Down

        //2012/10/25 - 3D축 배경색 설정
        m_cGraphCtrl.Set3DYAxisBkFill(m_nCurrentID, true);
        m_cGraphCtrl.Set3DYAxisBkColor(m_nCurrentID, ColorNames.GC_LightLightLightGray);
        m_cGraphCtrl.Set3DXAxisBkFill(m_nCurrentID, true);
        m_cGraphCtrl.Set3DXAxisBkColor(m_nCurrentID, ColorNames.GC_LightPink);*/

        m_cGraphCtrl.SetShowLabelText(m_nCurrentID, true);

        //Y축 BlockButton Hide
        m_cGraphCtrl.ChangeFUNCToolType(ENFUNCToolType.GE_FUNCTOOL_YAXIS_BUTTON,0);

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //데이터 입력
        int  nDataCount = 12;
        ArrayList<ST_PACKET_COREDATA> cPacketList = new ArrayList<ST_PACKET_COREDATA>();

        ST_PACKET_COREDATA stPacketBasicDataP = null;

        int i=0;
//        String strXDate = "";
        String strYDate = "";        
        for(i=0;i<nDataCount;i++)
        {
            stPacketBasicDataP = new GlobalStructs.ST_PACKET_COREDATA();

            strYDate = GlobalUtils.GetEnglishNameOfMonth(1+i);
            m_cGraphCtrl.AddUserYDTString(m_nCurrentID, strYDate);

            stPacketBasicDataP.dValueList[m_nCloseIndex] = GetRandomValue(0.0, 1000.0);
            stPacketBasicDataP.dValueList[m_nVolumeIndex] = GetRandomValue(500.0, 2000.0);
            cPacketList.add(stPacketBasicDataP);
        }

        m_cGraphCtrl.ReceiveQuery(m_nCurrentID,"HorizontalBar","", 0, 0,0,cPacketList,0);
//        if(m_bIsAttachFormGraph == true)
//        {
//            m_cGraphCtrl.ResizeChartBlock(m_nCurrentID, ENBlockResizeType.GE_BLOCK_RESIZE_INIT);
//            m_cGraphCtrl.RedrawCurrentChart();
//        }

        ////////////////////////////////////////////////////////////////////////////////////////////////////////
        //하위 Line추가
        m_cGraphCtrl.AddUserChartObject(m_nCurrentID,"재고율",ENObjectKindType.GE_OBJECT_KIND_INDICA, ENObjectYScaleType.GE_OBJECT_YSCALE_DERIVE,
                GlobalDefines.GD_OBJECT_NEW_REGION_LAST, ENObjectIndicaType.GE_OBJECT_INDICA_MOUNT, ColorNames.GC_FireBrick,ENLineStyleType.PS_SOLID,3,0);
        m_cGraphCtrl.SetLabelTextXPos(m_nCurrentID, "재고율", ENLabelTextXPos.GE_LABEL_TEXT_XPOS_RIGHT);
        m_cGraphCtrl.SetObjLabelText(m_nCurrentID, "재고율", true);
//        m_cGraphCtrl.SetObjLabelTextPos(m_nCurrentID,"재고율",1);
//        m_cGraphCtrl.SetObjLabelText(m_nCurrentID,"재고율",true);
        //Dot표시 여부
        m_cGraphCtrl.SetObject_PropertyInfo(m_nCurrentID,"재고율",0, 13, 1);
        //DotColor
        m_cGraphCtrl.SetObject_PropertyInfo(m_nCurrentID,"재고율",0, 14, ColorNames.GC_Black);
        //Dot표시 타입
        m_cGraphCtrl.SetObject_PropertyInfo(m_nCurrentID,"재고율",0, 15, ENScatterDrawType.GE_SCATTER_DRAW_RECT.GetIndexValue());

        int []nBlock_ObjIndexs = new int[2];
        boolean bFind = m_cGraphCtrl.GetObjectBlockIndex(m_nCurrentID,ENObjectKindType.GE_OBJECT_KIND_INDICA,"재고율",nBlock_ObjIndexs);
        if(bFind == true)
        {
            for (i = 0; i < nDataCount; i++)
            {
                stPacketBasicDataP = ST_PACKET_COREDATA.AllocMemory();

                stPacketBasicDataP.dValueList[m_nCloseIndex] = GetRandomValue(3000.0, 100.0);
                m_cGraphCtrl.AddUserDataBasic(m_nCurrentID, nBlock_ObjIndexs[0],nBlock_ObjIndexs[1], 0, stPacketBasicDataP);
            }

            m_cGraphCtrl.SetChartBlockRatio(m_nCurrentID, 0, 0.0, 0.67, false);
            m_cGraphCtrl.SetChartBlockRatio(m_nCurrentID, 1, 0.67,1.0, false);
            m_cGraphCtrl.ResizeChartBlock(m_nCurrentID, ENBlockResizeType.GE_BLOCK_RESIZE_INIT);
            m_cGraphCtrl.RedrawCurrentChart();
        }
    }

    public void InitGraphMix2SideBar_LineDot()
    {
        if(m_cGraphCtrl == null) return;

        InitGraphProperty(ENPriceKindType.GE_PRICE_KIND_2SIDEBAR,"영업이익/영업손실",5,2,false,false);

        //Y측 양측에 보기
        m_cGraphCtrl.SetYLayerExist(m_nCurrentID,ENYLayerExistType.GE_YLAYER_EXIST_BOTH,false,false);

        m_cGraphCtrl.SetXAxisDataFormat(m_nCurrentID, ENXAxisDataFormat.GE_XAXIS_DATA_FORMAT_STRING);

        m_cGraphCtrl.SetXAxisDspType(m_nCurrentID,true, ENXLayerVertDspType.GE_XLAYER_VERT_DSP_45ANGLE, 8);
        //m_cGraphCtrl.SetXAxisDspType(m_nCurrentID,true, ENXLayerVertDspType.GE_XLAYER_VERT_DSP_MULLINES	, 8);

        //가격차트 그라데이션 설정
        m_cGraphCtrl.SetPriceGradation(m_nCurrentID, true);
        m_cGraphCtrl.SetGradientDirectType(m_nCurrentID,ENGradationDirectType.GE_GRADATION_HCENTER_OUT);

        m_cGraphCtrl.SetDrawOscBaseLine(m_nCurrentID,true);

        m_cGraphCtrl.SetGradientPosColor(m_nCurrentID, 1, ColorNames.GC_LightCyan);
        //UpIn
        m_cGraphCtrl.SetGradientPosColor(m_nCurrentID, 0, ColorNames.GC_WhiteSmoke);

        //주의:SetGradientPosType이 0이면 SetGradientPosColor(m_nCurrentID,0 번째 Color를 Gradient색상으로 사용
        //     SetGradientPosType이 1이면 SetGradientPosColor(m_nCurrentID,1 번째 Color를 Gradient색상으로 사용
        //위의 값으로 설정하지 않은 색상은 원래의 Default색상사용
        //SetGradientPosType이 2이면 SetGradientPosColor(m_nCurrentID,0 과 SetGradientPosColor(m_nCurrentID,1 둘다를 사용한다
        m_cGraphCtrl.SetGradientPosType(m_nCurrentID, ENGradationPosType.GE_GRADATION_POS_END);
        //m_cGraphCtrl.SetGradientPosColor(m_nCurrentID,0, ColorNames.GC_LightCyan);
        //m_cGraphCtrl.SetGradientPosColor(m_nCurrentID,1, ColorNames.GC_WhiteSmoke);

        m_cGraphCtrl.SetMainFloatingPoint(m_nCurrentID,0);

        //Zero위치 Center
        m_cGraphCtrl.SetZeroPosCenter(m_nCurrentID,true);

        //최대최소 표시 보기
        m_cGraphCtrl.SetShowPriceMaxMin(m_nCurrentID,true);

        //봉간격
        m_cGraphCtrl.SetBongMargin(m_nCurrentID,(int)(2*m_fDeviceDPIWeight));

        //그룹 개수
        m_cGraphCtrl.SetGroupDataCount(m_nCurrentID,2);

        //상단 색상
        m_cGraphCtrl.SetGroupBodyFill(m_nCurrentID, 0,true);
        m_cGraphCtrl.SetGroupBodyColor(m_nCurrentID, 0,ColorNames.GC_Red);
        m_cGraphCtrl.SetGroupLineColor(m_nCurrentID, 0,ColorNames.GC_Red);
        m_cGraphCtrl.SetGroupTextColor(m_nCurrentID, 0,ColorNames.GC_Red);

        //하단 색상
        m_cGraphCtrl.SetGroupBodyFill(m_nCurrentID, 1,true);
        m_cGraphCtrl.SetGroupBodyColor(m_nCurrentID, 1,ColorNames.GC_Blue);
        m_cGraphCtrl.SetGroupLineColor(m_nCurrentID, 1,ColorNames.GC_Blue);
        m_cGraphCtrl.SetGroupTextColor(m_nCurrentID, 1,ColorNames.GC_Blue);

        //UpIn
        m_cGraphCtrl.SetPrice_PropertyInfo(m_nCurrentID, 1,ColorNames.GC_Red);
        //UpFill
        m_cGraphCtrl.SetPrice_PropertyInfo(m_nCurrentID, 2,1);
        //DnIn
        m_cGraphCtrl.SetPrice_PropertyInfo(m_nCurrentID, 4,ColorNames.GC_Blue);
        //DnFill
        m_cGraphCtrl.SetPrice_PropertyInfo(m_nCurrentID, 5,1);

        //Y축 BlockButton Hide
        m_cGraphCtrl.ChangeFUNCToolType(ENFUNCToolType.GE_FUNCTOOL_YAXIS_BUTTON,0);

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //데이터 입력
        int  nDataCount = 12;
        ArrayList<ST_PACKET_COREDATA> cPacketList = new ArrayList<ST_PACKET_COREDATA>();

        ST_PACKET_COREDATA stPacketBasicDataP = null;

        int i=0;
        String strXDate = "";
        for(i=0;i<nDataCount;i++)
        {
            strXDate = String.format("%d년",i+2006);
            m_cGraphCtrl.AddUserXDTString(m_nCurrentID, strXDate);

            stPacketBasicDataP = ST_PACKET_COREDATA.AllocMemory();

            stPacketBasicDataP.dValueList[m_nLowIndex] = GetRandomValue(0.0, -10000.0);
            stPacketBasicDataP.dValueList[m_nHighIndex] = GetRandomValue(10000.0, 0.0);

            cPacketList.add(stPacketBasicDataP);
        }

        m_cGraphCtrl.ReceiveQuery(m_nCurrentID,"2SideBar","", 0, 0,0,cPacketList,0);
//        if(m_bIsAttachFormGraph == true)
//        {
//            m_cGraphCtrl.ResizeChartBlock(m_nCurrentID, ENBlockResizeType.GE_BLOCK_RESIZE_INIT);
//            m_cGraphCtrl.RedrawCurrentChart();
//        }

        ////////////////////////////////////////////////////////////////////////////////////////////////////////
        //하위 Line추가
        m_cGraphCtrl.AddUserChartObject(m_nCurrentID,"재고율",ENObjectKindType.GE_OBJECT_KIND_INDICA, ENObjectYScaleType.GE_OBJECT_YSCALE_DERIVE,
                GlobalDefines.GD_OBJECT_NEW_REGION_LAST, ENObjectIndicaType.GE_OBJECT_INDICA_LINEDOT, ColorNames.GC_Black,ENLineStyleType.PS_SOLID,3,0);

        //Dot표시 여부
        m_cGraphCtrl.SetObject_PropertyInfo(m_nCurrentID,"재고율",0, 13, 1);
        //DotColor
        m_cGraphCtrl.SetObject_PropertyInfo(m_nCurrentID,"재고율",0, 14, ColorNames.GC_Red);
        //Dot표시 타입
        m_cGraphCtrl.SetObject_PropertyInfo(m_nCurrentID,"재고율", 0, 15, ENScatterDrawType.GE_SCATTER_DRAW_CIRCLE.GetIndexValue());

        int nBlock_ObjIndexs[] = new int[2];
        boolean bFind = m_cGraphCtrl.GetObjectBlockIndex(m_nCurrentID,ENObjectKindType.GE_OBJECT_KIND_INDICA,"재고율",nBlock_ObjIndexs);
        if(bFind == true)
        {
            for (i = 0; i < nDataCount; i++)
            {
                stPacketBasicDataP = ST_PACKET_COREDATA.AllocMemory();

                stPacketBasicDataP.dValueList[m_nCloseIndex] = GetRandomValue(3000.0, 100.0);
                m_cGraphCtrl.AddUserDataBasic(m_nCurrentID, nBlock_ObjIndexs[0],nBlock_ObjIndexs[1], 0, stPacketBasicDataP);
            }

            m_cGraphCtrl.SetChartBlockRatio(m_nCurrentID, 0, 0.0, 0.67, false);
            m_cGraphCtrl.SetChartBlockRatio(m_nCurrentID, 1, 0.67,1.0, false);
            m_cGraphCtrl.ResizeChartBlock(m_nCurrentID, ENBlockResizeType.GE_BLOCK_RESIZE_INIT);
            m_cGraphCtrl.RedrawCurrentChart();
        }

//        //3초 대기 핸들러
//        if(m_bIsAttachFormGraph == false)
//        {
//            if (m_nHandlerStatus == STOP)
//            {
//                m_nHandlerStatus = PLAY;
//                handler.sendEmptyMessageDelayed(0, DELAY);
//            }
//        }
    }

    public void InitGraphMixGroupBar_LineDots()
    {
        if(m_cGraphCtrl == null) return;

        InitGraphProperty(ENPriceKindType.GE_PRICE_KIND_GROUPBAR,"개인/기관/외국인",10,2,false,false);

        //Y측 양측에 보기
        m_cGraphCtrl.SetYLayerExist(m_nCurrentID,ENYLayerExistType.GE_YLAYER_EXIST_BOTH,false,false);

        //라벨 텍스트
        m_cGraphCtrl.SetShowLabelText(m_nCurrentID, false);
        m_cGraphCtrl.SetShowSubLabelText(m_nCurrentID, true);

        //X,Y축 설정
        m_cGraphCtrl.SetXAxisDataFormat(m_nCurrentID, ENXAxisDataFormat.GE_XAXIS_DATA_FORMAT_STRING);
        m_cGraphCtrl.SetYAxisDataFormat(m_nCurrentID, ENYAxisDataFormat.GE_YAXIS_DATA_FORMAT_VALUE);
        m_cGraphCtrl.SetXAxisDspType(m_nCurrentID,true, ENXLayerVertDspType.GE_XLAYER_VERT_DSP_90ANGLE, 8);

        //X축 격자 보기
        m_cGraphCtrl.SetXLayerGridShow(m_nCurrentID, true);
        //X축 격자 점선
        m_cGraphCtrl.SetXLayer_PropertyInfo(m_nCurrentID, 2,ENLineStyleType.PS_DOT.GetIndexValue());
        //X축 격자 굵기
        m_cGraphCtrl.SetXLayer_PropertyInfo(m_nCurrentID, 3,2);

        //가격차트 그라데이션 설정
//        m_cGraphCtrl.SetPriceGradation(m_nCurrentID, true);
        m_cGraphCtrl.SetPriceGradation(m_nCurrentID, false);
        //m_cGraphCtrl.SetGradientDirectType(m_nCurrentID,ENGradationDirectType.GE_GRADATION_LEFT_RIGHT);
//        m_cGraphCtrl.SetGradientDirectType(m_nCurrentID,ENGradationDirectType.GE_GRADATION_HCENTER_OUT);

//        m_cGraphCtrl.SetGradientPosType(m_nCurrentID, ENGradationPosType.GE_GRADATION_POS_START);
//        m_cGraphCtrl.SetGradientPosColor(m_nCurrentID,0, Color.rgb(200,200,255));

        //m_cGraphCtrl.SetBongMargin(m_nCurrentID, 5)
        m_cGraphCtrl.SetBongMargin(m_nCurrentID, (int)(5.0*m_fDeviceDPIWeight));
//        m_cGraphCtrl.SetBongMargin(m_nCurrentID,(int)(20*m_fDeviceDPIWeight));

        m_cGraphCtrl.SetMainFloatingPoint(m_nCurrentID,0);

        //그룹 개수
        m_cGraphCtrl.SetGroupDataCount(m_nCurrentID,3);

        //상단 색상
        m_cGraphCtrl.SetGroupBodyFill(m_nCurrentID, 0,true);
        m_cGraphCtrl.SetGroupBodyColor(m_nCurrentID, 0,ColorNames.GC_Aqua);
        //GroupText를 Body와 같이 해줘야 "/"로 구분했을때 텍스트가 봉색상따라 간다
        m_cGraphCtrl.SetGroupTextColor(m_nCurrentID, 0,ColorNames.GC_Aqua);

        m_cGraphCtrl.SetGroupBodyFill(m_nCurrentID, 1,true);
        m_cGraphCtrl.SetGroupBodyColor(m_nCurrentID, 1,ColorNames.GC_Magenta);
        m_cGraphCtrl.SetGroupTextColor(m_nCurrentID, 1,ColorNames.GC_Magenta);

        m_cGraphCtrl.SetGroupBodyFill(m_nCurrentID, 2,true);
        m_cGraphCtrl.SetGroupBodyColor(m_nCurrentID, 2,ColorNames.GC_ForestGreen);
        m_cGraphCtrl.SetGroupTextColor(m_nCurrentID, 2,ColorNames.GC_ForestGreen);

        //Y축 BlockButton Hide
        m_cGraphCtrl.ChangeFUNCToolType(ENFUNCToolType.GE_FUNCTOOL_YAXIS_BUTTON,0);

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //데이터 입력
        int  nDataCount = 5;
        ArrayList<GlobalStructs.ST_PACKET_COREDATA> cPacketList = new ArrayList<GlobalStructs.ST_PACKET_COREDATA>();
        GlobalStructs.ST_PACKET_COREDATA stPacketBasicDataP = null;
        int i=0;
        String strXDate = "";
        for(i=0;i<nDataCount;i++)
        {
            stPacketBasicDataP = new GlobalStructs.ST_PACKET_COREDATA();

            strXDate = String.format("%d년",i+2014);
            m_cGraphCtrl.AddUserXDTString(m_nCurrentID, strXDate);

            stPacketBasicDataP.dValueList[m_nOpenIndex] = GetRandomValue(3000.0, 1000.0);
            stPacketBasicDataP.dValueList[m_nHighIndex] = GetRandomValue(3000.0, 1000.0);
            stPacketBasicDataP.dValueList[m_nLowIndex] = GetRandomValue(3000.0, 1000.0);

            cPacketList.add(stPacketBasicDataP);
        }

        m_cGraphCtrl.ReceiveQuery(m_nCurrentID,"GroupBar","", 0, 0,0,cPacketList,0);

        ////////////////////////////////////////////////////////////////////////////////////////////////////////
        //하위 Line추가
        m_cGraphCtrl.AddUserChartObject(m_nCurrentID,"영업비용",ENObjectKindType.GE_OBJECT_KIND_INDICA, ENObjectYScaleType.GE_OBJECT_YSCALE_LOGIC,
                GlobalDefines.GD_OBJECT_NEW_REGION_LAST, ENObjectIndicaType.GE_OBJECT_INDICA_LINEDOT, ColorNames.GC_Red,ENLineStyleType.PS_SOLID,3,0);
        m_cGraphCtrl.SetLabelTextXPos(m_nCurrentID, "영업비용", ENLabelTextXPos.GE_LABEL_TEXT_XPOS_CENTER);
        m_cGraphCtrl.SetObjLabelText(m_nCurrentID,"영업비용",true);
        //Dot표시 여부
        m_cGraphCtrl.SetObject_PropertyInfo(m_nCurrentID,"영업비용",0, 13, 1);
        //Dot표시 타입
        m_cGraphCtrl.SetObject_PropertyInfo(m_nCurrentID,"영업비용",0, 4, ENScatterDrawType.GE_SCATTER_DRAW_CIRCLE.GetIndexValue());

        m_cGraphCtrl.AddUserChartObject(m_nCurrentID,"총매출",ENObjectKindType.GE_OBJECT_KIND_INDICA, ENObjectYScaleType.GE_OBJECT_YSCALE_LOGIC,
                1, ENObjectIndicaType.GE_OBJECT_INDICA_LINEDOT, ColorNames.GC_Blue,ENLineStyleType.PS_SOLID,3,0);
        m_cGraphCtrl.SetLabelTextXPos(m_nCurrentID, "총매출", ENLabelTextXPos.GE_LABEL_TEXT_XPOS_CENTER);
        m_cGraphCtrl.SetObjLabelText(m_nCurrentID, "총매출", true);
        //Dot표시 여부
        m_cGraphCtrl.SetObject_PropertyInfo(m_nCurrentID,"총매출",0, 13, 1);
        //DotColor
        m_cGraphCtrl.SetObject_PropertyInfo(m_nCurrentID,"총매출",0, 14, ColorNames.GC_Red);
        //Dot표시 타입
        m_cGraphCtrl.SetObject_PropertyInfo(m_nCurrentID, "총매출", 0, 15, ENScatterDrawType.GE_SCATTER_DRAW_RECT.GetIndexValue());

        for (i = 0; i < nDataCount; i++)
        {
            stPacketBasicDataP = new GlobalStructs.ST_PACKET_COREDATA();

            stPacketBasicDataP.dValueList[m_nCloseIndex] = GetRandomValue(3000.0, 100.0);
            m_cGraphCtrl.AddUserDataBasic(m_nCurrentID, "영업비용", 0, stPacketBasicDataP);

            stPacketBasicDataP.dValueList[m_nCloseIndex] = GetRandomValue(10000.0, 5000.0);
            m_cGraphCtrl.AddUserDataBasic(m_nCurrentID, "총매출", 0, stPacketBasicDataP);
        }

        m_cGraphCtrl.SetChartBlockRatio(m_nCurrentID, 0, 0.0, 0.67, false);
        m_cGraphCtrl.SetChartBlockRatio(m_nCurrentID, 1, 0.67,1.0, false);
        m_cGraphCtrl.ResizeChartBlock(m_nCurrentID, ENBlockResizeType.GE_BLOCK_RESIZE_INIT);
        m_cGraphCtrl.RedrawCurrentChart();
    }

    public void InitGraphMixStickBar_Stair()
    {
        if(m_cGraphCtrl == null) return;

        InitGraphProperty(ENPriceKindType.GE_PRICE_KIND_STICKBAR,"매출증가율",5,2,false,false);

        m_cGraphCtrl.SetXAxisDataFormat(m_nCurrentID, ENXAxisDataFormat.GE_XAXIS_DATA_FORMAT_STRING);

        //OffIn
        //m_cGraphCtrl.SetPrice_PropertyInfo(m_nCurrentID, 7, ColorNames.GC_CadetBlue);

        //가격차트 그라데이션 설정
        m_cGraphCtrl.SetPriceGradation(m_nCurrentID, true);
        //m_cGraphCtrl.SetGradientDirectType(m_nCurrentID,ENGradationDirectType.GE_GRADATION_TOPLEFT_RIGHTBOTTOM);
        m_cGraphCtrl.SetGradientDirectType(m_nCurrentID,ENGradationDirectType.GE_GRADATION_VCENTER_OUT);

        m_cGraphCtrl.SetGradientPosType(m_nCurrentID, ENGradationPosType.GE_GRADATION_POS_START);
        m_cGraphCtrl.SetGradientPosColor(m_nCurrentID,0, ColorNames.GC_WhiteSmoke);
        //m_cGraphCtrl.SetGradientPosColor(m_nCurrentID,1, Color.rgb(169,206,220));

//        //확대,축소/좌우 이동 보기
//        m_cGraphCtrl.SetEnableExp_MoveBtns(m_nCurrentID, true);

        //최대/최소표시
        m_cGraphCtrl.SetShowPriceMaxMin(m_nCurrentID, false);

        //라벨 표시
        m_cGraphCtrl.SetShowLabelText(m_nCurrentID,true);

        //데이터 사이 간격
        m_cGraphCtrl.SetBongMargin(m_nCurrentID, (int)(3*m_fDeviceDPIWeight));

        //가격 우선 스케일
        m_cGraphCtrl.SetPriceTopMost(m_nCurrentID, true);

        //최대최소 표시 보기
        m_cGraphCtrl.SetShowPriceMaxMin(m_nCurrentID,true);

        //Y축 범례 크기 비율
        m_cGraphCtrl.SetChartYScaleRatio(m_nCurrentID, 0, 0.8);

        //가로 격자
        m_cGraphCtrl.SetYLayer_PropertyInfo(m_nCurrentID,0,0,1.0);

        //Y축 BlockButton Hide
        m_cGraphCtrl.ChangeFUNCToolType(ENFUNCToolType.GE_FUNCTOOL_YAXIS_BUTTON,0);

        /*//2012/10/05 - 3D그래프 표현
        m_cGraphCtrl.Set3DGraphDraw(m_nCurrentID, true);
        m_cGraphCtrl.Set3DDepthRatio_Pixel(m_nCurrentID, true);	//TRUE:Ratio/FALSE:Pixel
        m_cGraphCtrl.Set3DDepthRatio(m_nCurrentID, 0.02);
        m_cGraphCtrl.Set3DDepthXAxisPixel(m_nCurrentID, 5);
        m_cGraphCtrl.Set3DDepthYAxisPixel(m_nCurrentID, 15);
        m_cGraphCtrl.Set3DHorzDepthUpDn(m_nCurrentID,true);		//TRUE:Up/FALSE:Down

        //2012/10/25 - 3D축 배경색 설정
        m_cGraphCtrl.Set3DYAxisBkFill(m_nCurrentID, true);
        m_cGraphCtrl.Set3DYAxisBkColor(m_nCurrentID, ColorNames.GC_LightLightLightGray);
        m_cGraphCtrl.Set3DXAxisBkFill(m_nCurrentID, true);
        m_cGraphCtrl.Set3DXAxisBkColor(m_nCurrentID, ColorNames.GC_LightPink);*/

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //데이터 입력
        int  nDataCount = 12;
        ArrayList<GlobalStructs.ST_PACKET_COREDATA> cPacketList = new ArrayList<GlobalStructs.ST_PACKET_COREDATA>();

        GlobalStructs gStruct = new GlobalStructs();
        GlobalStructs.ST_PACKET_COREDATA stPacketBasicDataP = null;

        int i=0;
        String strXDate = "";
        for(i=0;i<nDataCount;i++)
        {
            stPacketBasicDataP = new GlobalStructs.ST_PACKET_COREDATA();

            strXDate = String.format("%d월",i+1);
            m_cGraphCtrl.AddUserXDTString(m_nCurrentID, strXDate);

            stPacketBasicDataP.dValueList[m_nCloseIndex] = GetRandomValue(100.0,0.0);

            cPacketList.add(stPacketBasicDataP);
        }

        m_cGraphCtrl.ReceiveQuery(m_nCurrentID,"StickBar","", 2, 0,0,cPacketList,0);
//        if(m_bIsAttachFormGraph == true)
//        {
//            m_cGraphCtrl.ResizeChartBlock(m_nCurrentID, ENBlockResizeType.GE_BLOCK_RESIZE_INIT);
//            m_cGraphCtrl.RedrawCurrentChart();
//        }

        ////////////////////////////////////////////////////////////////////////////////////////////////////////
        //하위 Line추가
        m_cGraphCtrl.AddUserChartObject(m_nCurrentID,"재고율",ENObjectKindType.GE_OBJECT_KIND_INDICA, ENObjectYScaleType.GE_OBJECT_YSCALE_DERIVE,
                GlobalDefines.GD_OBJECT_NEW_REGION_LAST, ENObjectIndicaType.GE_OBJECT_INDICA_STAIRS, ColorNames.GC_Black,ENLineStyleType.PS_SOLID,3,2);
        m_cGraphCtrl.SetLabelTextXPos(m_nCurrentID, "재고율", ENLabelTextXPos.GE_LABEL_TEXT_XPOS_CENTER);
        m_cGraphCtrl.SetObjLabelText(m_nCurrentID, "재고율", true);
//        m_cGraphCtrl.SetObjLabelTextPos(m_nCurrentID,"재고율",1);
        m_cGraphCtrl.SetObjLabelText(m_nCurrentID,"재고율",true);
        //Dot표시 여부
        m_cGraphCtrl.SetObject_PropertyInfo(m_nCurrentID,"재고율",0, 13, 1);
        //DotColor
        m_cGraphCtrl.SetObject_PropertyInfo(m_nCurrentID,"재고율",0, 14, ColorNames.GC_Blue);
        //Dot표시 타입
        m_cGraphCtrl.SetObject_PropertyInfo(m_nCurrentID, "재고율", 0, 15, ENScatterDrawType.GE_SCATTER_DRAW_CIRCLE.GetIndexValue());
        //Dot표시 타입
//        m_cGraphCtrl.SetObject_PropertyInfo(m_nCurrentID,"재고율",0, 4, ENScatterDrawType.GE_SCATTER_DRAW_CIRCLE.GetIndexValue());

        int []nBlock_ObjIndexs = new int[2];
        boolean bFind = m_cGraphCtrl.GetObjectBlockIndex(m_nCurrentID,ENObjectKindType.GE_OBJECT_KIND_INDICA,"재고율",nBlock_ObjIndexs);
        if(bFind == true)
        {
            for (i = 0; i < 12; i++)
            {
                stPacketBasicDataP = ST_PACKET_COREDATA.AllocMemory();

                stPacketBasicDataP.dValueList[m_nCloseIndex] = GetRandomValue(100.0, 0.0);
                m_cGraphCtrl.AddUserDataBasic(m_nCurrentID, nBlock_ObjIndexs[0],nBlock_ObjIndexs[1], 0, stPacketBasicDataP);
            }

            //Y축 범례 크기 비율
//            m_cGraphCtrl.SetChartYScaleRatio(m_nCurrentID,0,0.8);

            //분할 영역 크기 비율
            m_cGraphCtrl.SetChartBlockRatio(m_nCurrentID,0,0.0,0.6,false);
            m_cGraphCtrl.SetChartBlockRatio(m_nCurrentID,1,0.6,1.0,true);
            m_cGraphCtrl.ResizeChartBlock(m_nCurrentID, ENBlockResizeType.GE_BLOCK_RESIZE_INIT);
            m_cGraphCtrl.RedrawCurrentChart();
        }
    }

    public void InitGraphMixOscBarLine()
    {
        if(m_cGraphCtrl == null) return;

        InitGraphProperty(ENPriceKindType.GE_PRICE_KIND_OSCBAR,"매출액증가율",10,0,false,false);

        m_cGraphCtrl.SetXAxisDataFormat(m_nCurrentID, ENXAxisDataFormat.GE_XAXIS_DATA_FORMAT_STRING);
        m_cGraphCtrl.SetXAxisDspType(m_nCurrentID,true, ENXLayerVertDspType.GE_XLAYER_VERT_DSP_MULLINES	, 8);

        //Y측 양측에 보기
        m_cGraphCtrl.SetYLayerExist(m_nCurrentID,ENYLayerExistType.GE_YLAYER_EXIST_BOTH,false,false);
        //Y측 우측에 보기
        //m_cGraphCtrl.SetYLayerExist(m_nCurrentID,ENYLayerExistType.GE_YLAYER_EXIST_RIGHT,false,false);

        m_cGraphCtrl.SetMainFloatingPoint(m_nCurrentID,2);

        //X축 격자 보기
        m_cGraphCtrl.SetXLayerGridShow(m_nCurrentID, true);
        //X축 격자 실선
        //m_cGraphCtrl.SetXLayer_PropertyInfo(m_nCurrentID, 2,ENLineStyleType.PS_SOLID.GetIndexValue());
        //X축 격자 굵기
        m_cGraphCtrl.SetXLayer_PropertyInfo(m_nCurrentID, 3,2);

        //가격차트 그라데이션 설정
        m_cGraphCtrl.SetPriceGradation(m_nCurrentID, true);
        m_cGraphCtrl.SetGradientDirectType(m_nCurrentID,ENGradationDirectType.GE_GRADATION_LEFT_RIGHT);

        m_cGraphCtrl.SetDrawOscBaseLine(m_nCurrentID,true);
        m_cGraphCtrl.SetGradientPosType(m_nCurrentID, ENGradationPosType.GE_GRADATION_POS_START);
        m_cGraphCtrl.SetGradientPosColor(m_nCurrentID,0, ColorNames.GC_Black);
        //m_cGraphCtrl.SetGradientPosColor(m_nCurrentID,0, Color.rgb(169,206,220));

        //데이터 사이 간격
        m_cGraphCtrl.SetBongMargin(m_nCurrentID, (int)(3*m_fDeviceDPIWeight));

        //마지막 값 보기
        m_cGraphCtrl.SetYLastDataInfo(m_nCurrentID,true,false,false);

        //0선 기준선 표현
        m_cGraphCtrl.SetRiseFallBaseType(m_nCurrentID, ENBaseLineType.GE_BASELINE_USER1);
        m_cGraphCtrl.SetBaseLineValue(m_nCurrentID, ENBaseLineType.GE_BASELINE_USER1, 0.0);

        //Zero위치 Center
        m_cGraphCtrl.SetZeroPosCenter(m_nCurrentID,true);

        //라벨 표시
        m_cGraphCtrl.SetShowLabelText(m_nCurrentID,true);

        //최대최소 표시 보기
        m_cGraphCtrl.SetShowPriceMaxMin(m_nCurrentID,true);

        //확대,축소/좌우 이동 보기
        m_cGraphCtrl.SetEnableExp_MoveBtns(m_nCurrentID, true);

        //UpIn
        m_cGraphCtrl.SetPrice_PropertyInfo(m_nCurrentID, 1,ColorNames.GC_Red);
        //UpFill
        m_cGraphCtrl.SetPrice_PropertyInfo(m_nCurrentID, 2,1);
        //DnIn
        m_cGraphCtrl.SetPrice_PropertyInfo(m_nCurrentID, 4,ColorNames.GC_Blue);
        //DnFill
        m_cGraphCtrl.SetPrice_PropertyInfo(m_nCurrentID, 5,1);

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //데이터 입력
        int  nDataCount = 12;
        ArrayList<GlobalStructs.ST_PACKET_COREDATA> cPacketList = new ArrayList<GlobalStructs.ST_PACKET_COREDATA>();
        GlobalStructs.ST_PACKET_COREDATA stPacketBasicDataP = null;
        int i=0;
        String strXDate = "";        
        for(i=0;i<nDataCount;i++)
        {
            stPacketBasicDataP = new GlobalStructs.ST_PACKET_COREDATA();

            strXDate = String.format("%d년",i+2006);
            m_cGraphCtrl.AddUserXDTString(m_nCurrentID, strXDate);

            stPacketBasicDataP.dValueList[m_nCloseIndex] = GetRandomValue(100.0,-100.0);
            //if(i == 3) stPacketBasicDataP.dValueList[m_nCloseIndex] = 0.0;

            cPacketList.add(stPacketBasicDataP);
        }

        m_cGraphCtrl.ReceiveQuery(m_nCurrentID,"OSCBar","", 0, 0,0,cPacketList,0);
        if(m_bIsAttachFormGraph == true)
        {
            m_cGraphCtrl.ResizeChartBlock(m_nCurrentID, ENBlockResizeType.GE_BLOCK_RESIZE_INIT);
            m_cGraphCtrl.RedrawCurrentChart();
        }

        ////////////////////////////////////////////////////////////////////////////////////////////////////////
        //하위 Line추가

        m_cGraphCtrl.AddUserChartObject(m_nCurrentID,"영업이익률",ENObjectKindType.GE_OBJECT_KIND_INDICA, ENObjectYScaleType.GE_OBJECT_YSCALE_PRICE,
                GlobalDefines.GD_OBJECT_PRICE_REGION, ENObjectIndicaType.GE_OBJECT_INDICA_LINE, ColorNames.GC_Black,ENLineStyleType.PS_SOLID,3,2);
        //m_cGraphCtrl.SetObjLabelTextPos(m_nCurrentID,"영업이익률",1);
        //m_cGraphCtrl.SetObjLabelText(m_nCurrentID,"영업이익률",true);

        int []nBlock_ObjIndexs = new int[2];
        boolean bFind = m_cGraphCtrl.GetObjectBlockIndex(m_nCurrentID,ENObjectKindType.GE_OBJECT_KIND_INDICA,"영업이익률",nBlock_ObjIndexs);
        if(bFind == true)
        {
            for (i = 0; i < 12; i++)
            {
                stPacketBasicDataP = ST_PACKET_COREDATA.AllocMemory();

                stPacketBasicDataP.dValueList[m_nCloseIndex] = GetRandomValue(1000.0, 800.0);
                m_cGraphCtrl.AddUserDataBasic(m_nCurrentID, nBlock_ObjIndexs[0],nBlock_ObjIndexs[1], 0, stPacketBasicDataP);
            }
            m_cGraphCtrl.RedrawCurrentChart();
        }
    }

    public void InitGraphMixMultiLineDots()
    {
        if(m_cGraphCtrl == null) return;

        InitGraphProperty(ENPriceKindType.GE_PRICE_KIND_LINEDOT,"작년순이익",10,0,false,false);

        //프레임 유형 변경
        m_cGraphCtrl.SetChartFrameType(m_nCurrentID, ENChartFrameType.GE_CHART_FRAME_MINI_BASIC);

        //X축 설정
        m_cGraphCtrl.SetXAxisDataFormat(m_nCurrentID, ENXAxisDataFormat.GE_XAXIS_DATA_FORMAT_STRING);

        //Y측 우측에 보기
        m_cGraphCtrl.SetYLayerExist(m_nCurrentID,ENYLayerExistType.GE_YLAYER_EXIST_RIGHT,false,false);
        m_cGraphCtrl.SetMainFloatingPoint(m_nCurrentID,2);

        //Dot Color
        m_cGraphCtrl.SetPrice_PropertyInfo(m_nCurrentID, 24, ColorNames.GC_Black);
        //Dot표시 타입
        m_cGraphCtrl.SetPrice_PropertyInfo(m_nCurrentID, 14, GlobalEnums.ENScatterDrawType.GE_SCATTER_DRAW_CIRCLE.GetIndexValue());

        //Label보기
        m_cGraphCtrl.SetShowLabelText(m_nCurrentID,true);

        //배경에 Logo이미지 삽입(내부에 저장되어 있음)
        m_cGraphCtrl.SetBackGroundImgLogo(m_nCurrentID,true);

        Bitmap imgLogo = BitmapFactory.decodeResource(getResources(), R.drawable.img_ico_company_logo);
        m_cGraphCtrl.SetBGImgLogoInfo(m_nCurrentID,imgLogo,new Point(),100*(int)m_fDeviceDPIWeight,
            100*(int)m_fDeviceDPIWeight, ENLogoImgPosType.GE_LOGO_IMG_POS_LB,255/2);

        //Line Color
        m_cGraphCtrl.SetPrice_PropertyInfo(m_nCurrentID, 10, ColorNames.GC_Red);
        //Line Weight
        m_cGraphCtrl.SetPrice_PropertyInfo(m_nCurrentID, 12, 3);

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //데이터 입력
        int nDataCount = 12;
        ArrayList<GlobalStructs.ST_PACKET_COREDATA> cPacketList = new ArrayList<GlobalStructs.ST_PACKET_COREDATA>();

        GlobalStructs gStruct = new GlobalStructs();
        GlobalStructs.ST_PACKET_COREDATA stPacketBasicDataP = null;

        int i=0;
        String strXDate = "";
        for(i=0;i<nDataCount;i++)
        {
            stPacketBasicDataP = new GlobalStructs.ST_PACKET_COREDATA();
            strXDate = GlobalUtils.GetEnglishNameOfMonth(1+i);
            m_cGraphCtrl.AddUserXDTString(m_nCurrentID, strXDate);

            stPacketBasicDataP.dValueList[m_nCloseIndex] = GetRandomValue(1100.0, 900.0);

            cPacketList.add(stPacketBasicDataP);
        }

        m_cGraphCtrl.ReceiveQuery(m_nCurrentID,"LineDot","", 0, 0,0,cPacketList,0);
//        if(m_bIsAttachFormGraph == true)
//        {
//            m_cGraphCtrl.ResizeChartBlock(m_nCurrentID, ENBlockResizeType.GE_BLOCK_RESIZE_INIT);
//            m_cGraphCtrl.RedrawCurrentChart();
//        }

        ////////////////////////////////////////////////////////////////////////////////////////////////////////
        //하위 Line추가
        m_cGraphCtrl.AddUserChartObject(m_nCurrentID,"총매출",ENObjectKindType.GE_OBJECT_KIND_INDICA, ENObjectYScaleType.GE_OBJECT_YSCALE_PRICE,
                GlobalDefines.GD_OBJECT_PRICE_REGION, ENObjectIndicaType.GE_OBJECT_INDICA_LINEDOT, ColorNames.GC_Black,ENLineStyleType.PS_SOLID,3,2);
        m_cGraphCtrl.SetLabelTextXPos(m_nCurrentID, "총매출", ENLabelTextXPos.GE_LABEL_TEXT_XPOS_RIGHT);
//        m_cGraphCtrl.SetObjLabelTextPos(m_nCurrentID,"총매출",1);
        m_cGraphCtrl.SetObjLabelText(m_nCurrentID,"총매출",true);
        //Dot표시 여부
        m_cGraphCtrl.SetObject_PropertyInfo(m_nCurrentID,"총매출",0, 13, 1);
        //DotColor
        m_cGraphCtrl.SetObject_PropertyInfo(m_nCurrentID,"총매출",0, 14, ColorNames.GC_Red);
        //Dot표시 타입
        m_cGraphCtrl.SetObject_PropertyInfo(m_nCurrentID, "총매출", 0, 15, ENScatterDrawType.GE_SCATTER_DRAW_CIRCLE.GetIndexValue());
        //Dot표시 타입
//        m_cGraphCtrl.SetObject_PropertyInfo(m_nCurrentID,"총매출",0, 4, ENScatterDrawType.GE_SCATTER_DRAW_CIRCLE.GetIndexValue());

        m_cGraphCtrl.AddUserChartObject(m_nCurrentID,"영업이익",ENObjectKindType.GE_OBJECT_KIND_INDICA, ENObjectYScaleType.GE_OBJECT_YSCALE_PRICE,
                GlobalDefines.GD_OBJECT_PRICE_REGION, ENObjectIndicaType.GE_OBJECT_INDICA_LINEDOT, ColorNames.GC_Blue,ENLineStyleType.PS_SOLID,3,2);
//        m_cGraphCtrl.SetObjLabelTextPos(m_nCurrentID,"영업이익",1);
        m_cGraphCtrl.SetLabelTextXPos(m_nCurrentID, "영업이익", ENLabelTextXPos.GE_LABEL_TEXT_XPOS_LEFT);
        m_cGraphCtrl.SetObjLabelText(m_nCurrentID,"영업이익",true);
        //Dot표시 여부
        m_cGraphCtrl.SetObject_PropertyInfo(m_nCurrentID,"영업이익",0, 13, 1);
        //DotColor
        m_cGraphCtrl.SetObject_PropertyInfo(m_nCurrentID,"영업이익",0, 14, ColorNames.GC_Green);
        //Dot표시 타입
        m_cGraphCtrl.SetObject_PropertyInfo(m_nCurrentID, "영업이익", 0, 15, GlobalEnums.ENScatterDrawType.GE_SCATTER_DRAW_CIRCLE.GetIndexValue());

        m_cGraphCtrl.AddUserChartObject(m_nCurrentID,"순이익",ENObjectKindType.GE_OBJECT_KIND_INDICA, ENObjectYScaleType.GE_OBJECT_YSCALE_PRICE,
                GlobalDefines.GD_OBJECT_PRICE_REGION, ENObjectIndicaType.GE_OBJECT_INDICA_LINEDOT, ColorNames.GC_Green,ENLineStyleType.PS_SOLID,3,2);
//        m_cGraphCtrl.SetObjLabelTextPos(m_nCurrentID,"순이익",1);
        m_cGraphCtrl.SetLabelTextXPos(m_nCurrentID, "순이익", ENLabelTextXPos.GE_LABEL_TEXT_XPOS_CENTER);
        m_cGraphCtrl.SetObjLabelText(m_nCurrentID,"순이익",true);
        //Dot표시 여부
        m_cGraphCtrl.SetObject_PropertyInfo(m_nCurrentID,"순이익",0, 13, 1);
        //DotColor
        m_cGraphCtrl.SetObject_PropertyInfo(m_nCurrentID,"순이익",0, 14, ColorNames.GC_Black);
        //Dot표시 타입
        m_cGraphCtrl.SetObject_PropertyInfo(m_nCurrentID, "순이익", 0, 15, GlobalEnums.ENScatterDrawType.GE_SCATTER_DRAW_CIRCLE.GetIndexValue());


        for(i=0;i<12;i++)
        {
            stPacketBasicDataP = GlobalStructs.AllocPacketCore();

            if(i>=10)
                stPacketBasicDataP.dValueList[m_nCloseIndex] = GlobalDefines.GD_NAVALUE_DOUBLE;
            else
                stPacketBasicDataP.dValueList[m_nCloseIndex] = GetRandomValue(1200.0,1100.0);
            m_cGraphCtrl.AddUserDataBasic(m_nCurrentID,"총매출",0,stPacketBasicDataP);

            if(i>=10)
                stPacketBasicDataP.dValueList[m_nCloseIndex] = GlobalDefines.GD_NAVALUE_DOUBLE;
            else
                stPacketBasicDataP.dValueList[m_nCloseIndex] = GetRandomValue(900.0,800.0);
            m_cGraphCtrl.AddUserDataBasic(m_nCurrentID,"영업이익",0,stPacketBasicDataP);

            if(i>=10)
                stPacketBasicDataP.dValueList[m_nCloseIndex] = GlobalDefines.GD_NAVALUE_DOUBLE;
            else
                stPacketBasicDataP.dValueList[m_nCloseIndex] = GetRandomValue(800.0,700.0);
            m_cGraphCtrl.AddUserDataBasic(m_nCurrentID,"순이익",0,stPacketBasicDataP);
        }

        m_cGraphCtrl.RedrawCurrentChart();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // General Graph
    public void InitGraphTimeCircle()
    {
        if(m_cGraphCtrl == null) return;

        InitGraphProperty(ENPriceKindType.GE_PRICE_KIND_TIME_CIRCLE,"HKEX",10,0,false,false);

        //스케일여백을 90%로변경(상하좌우 5%씩 띄움)
        m_cGraphCtrl.SetChartYScaleRatio(m_nCurrentID,0,0.85);

        //Label보기
        m_cGraphCtrl.SetShowLabelText(m_nCurrentID,true);

        m_cGraphCtrl.SetGroupDataCount(m_nCurrentID,3);
        //외부 원색
        m_cGraphCtrl.SetGroupBodyColor(m_nCurrentID,0, ColorNames.GC_Green);
        //외부 원색
        m_cGraphCtrl.SetGroupBodyColor(m_nCurrentID,1, ColorNames.GC_Blue);
        //현재 색상
        m_cGraphCtrl.SetGroupBodyColor(m_nCurrentID,2, ColorNames.GC_Red);

        //Circle Band Width
        m_cGraphCtrl.SetBongMargin(m_nCurrentID, 22);
//        m_cGraphCtrl.SetBongMargin(m_nCurrentID,(int)(50*m_fDeviceDPIWeight));

        //CurrentPos Line Weight
        m_cGraphCtrl.SetPrice_PropertyInfo(m_nCurrentID, 12, 5);

        //주의:현재 위치 표시를 아래 변수로 처리
        String strTitle = String.format("HKEX-%02d:00",m_nTimeCircle);
        m_cGraphCtrl.SetBaseCodeName(m_nCurrentID,strTitle);
        //주의:현재 위치 표시를 아래 변수로 처리
        //24시간 운영체제에서 21시는 270도가 아니고 315도이다
        m_cGraphCtrl.Set3DDepthRatio(m_nCurrentID,315.0);

        //Event동작막기
        m_cGraphCtrl.AddDisableChartEvent(m_nCurrentID,ENDISABLEEventType.GE_DISABLE_EVENT_ALL);
        /*GE_DISABLE_EVENT_ALL(0),
        GE_DISABLE_EVENT_TOUCH_DOWN(1),
        GE_DISABLE_EVENT_TOUCH_UP(2),
        GE_DISABLE_EVENT_DOUBLE_TAB(3),
        GE_DISABLE_EVENT_TROUCH_DRAG(4),
        GE_DISABLE_EVENT_MULTI_TROUCH(5),
        GE_DISABLE_EVENT_LONG_PRESS(6),
        GE_DISABLE_EVENT_FLING(7),
        GE_DISABLE_EVENT_BLOCK_RESIZE(8),
        GE_DISABLE_EVENT_BLOCK_CHANGE(9),
        GE_DISABLE_EVENT_ZOOM(10);*/

        //////////////////////////////////////////////////////////////////////////////////////////
        //데이터 입력
        int nDataCount = 3;

        //주의:데이터는 각도 표현(00:00을 0각도)
        double dOValueList[] = { 135.0, 195.0, 275.0 };
        double dCValueList[] = { 190.0, 270.0, 10.0 };

        ArrayList<GlobalStructs.ST_PACKET_COREDATA> cPacketList = new ArrayList<GlobalStructs.ST_PACKET_COREDATA>();
        GlobalStructs.ST_PACKET_COREDATA stPacketBasicDataP = null;

        for(int i=0;i<nDataCount;i++)
        {
            stPacketBasicDataP = new GlobalStructs.ST_PACKET_COREDATA();

            stPacketBasicDataP.dValueList[m_nOpenIndex] = dOValueList[i];
            stPacketBasicDataP.dValueList[m_nCloseIndex] = dCValueList[i];

            cPacketList.add(stPacketBasicDataP);
        }

        m_cGraphCtrl.ReceiveQuery(m_nCurrentID,"TimeCircle","", 0, 0,0,cPacketList,0);
        m_cGraphCtrl.ResizeChartBlock(m_nCurrentID, ENBlockResizeType.GE_BLOCK_RESIZE_INIT);
        m_cGraphCtrl.RedrawCurrentChart();

        //3초 대기 핸들러
        if(m_bIsAttachFormGraph == false)
        {
            if (m_nHandlerStatus == STOP)
            {
                m_nHandlerStatus = PLAY;
                handler.sendEmptyMessageDelayed(0, DELAY);
            }
        }
    }

    public void InitGraphBubbleScatter()
    {
        if(m_cGraphCtrl == null) return;

        InitGraphProperty(ENPriceKindType.GE_PRICE_KIND_BUBBLE_SCATTER,"매출분포도",10,0,false,false);

        //프레임 유형 변경
        m_cGraphCtrl.SetChartFrameType(m_nCurrentID, ENChartFrameType.GE_CHART_FRAME_MINI_BASIC);

//        //확대,축소/좌우 이동 보기
//        m_cGraphCtrl.SetEnableExp_MoveBtns(m_nCurrentID, true);

        //X축 설정
        m_cGraphCtrl.SetXAxisDataFormat(m_nCurrentID, ENXAxisDataFormat.GE_XAXIS_DATA_FORMAT_VALUE);
        //Y축 설정
        m_cGraphCtrl.SetYAxisDataFormat(m_nCurrentID, ENYAxisDataFormat.GE_YAXIS_DATA_FORMAT_VALUE);
        //X축 격자
        m_cGraphCtrl.SetXLayerGridShow(m_nCurrentID, true);
        //Y축 격자
        //m_cGraphCtrl.SetYLayer_PropertyInfo(m_nCurrentID,0,0,1.0);

        //선형회귀선 표현
        m_cGraphCtrl.SetShowScatterLinearLine(m_nCurrentID, true);

        //최종값 표현
        m_cGraphCtrl.SetShowScatterLastPos(m_nCurrentID, true);

        //X축 소숫점 설정
        m_cGraphCtrl.SetXLayerFloatPoint(m_nCurrentID,2);

        //Scatter Drawing Type
        //GE_SCATTER_DRAW_CIRCLE,GE_SCATTER_DRAW_RECT,GE_SCATTER_DRAW_XMARKER,GE_SCATTER_DRAW_PLUS,GE_SCATTER_DRAW_DIAMOND,
        //m_cGraphCtrl.SetPrice_PropertyInfo(m_nCurrentID, 14, ENScatterDrawType.GE_SCATTER_DRAW_RECT.GetIndexValue());
        m_cGraphCtrl.SetPrice_PropertyInfo(m_nCurrentID, 14, ENScatterDrawType.GE_SCATTER_DRAW_DIAMOND.GetIndexValue());

        //Scatter 색상
        m_cGraphCtrl.SetPrice_PropertyInfo(m_nCurrentID, 1,ColorNames.GC_Red);		    //crPriceUpIn
        m_cGraphCtrl.SetPrice_PropertyInfo(m_nCurrentID, 2,1);			//bPriceUpFill
        m_cGraphCtrl.SetPrice_PropertyInfo(m_nCurrentID, 4,ColorNames.GC_Blue);		//crPriceDownIn
        m_cGraphCtrl.SetPrice_PropertyInfo(m_nCurrentID, 5,1);			//bPriceDownFill

        //선굵기
        m_cGraphCtrl.SetPrice_PropertyInfo(m_nCurrentID, 12,2);		    //crPriceUpIn

        //if(m_bChkShowCenterLine == true)
        {
            //선형회귀선 - 가로 기준선
            m_cGraphCtrl.SetHorzBaseLine(m_nCurrentID, 0.0,0,ENLineStyleType.PS_SOLID,1,false);
            //선형회귀선 - 세로 기준선
            m_cGraphCtrl.SetVertBaseLine(m_nCurrentID, 0.0,0,ENLineStyleType.PS_SOLID,1,true,false);
        }

        //데이터 전체 보기
        m_cGraphCtrl.SetPacketViewSize(m_nCurrentID,-1);
       ChangeGraphData();

       /* //3초 대기 핸들러
        if(m_bIsAttachFormGraph == false) {
            if (m_nHandlerStatus == STOP) {
                m_nHandlerStatus = PLAY;
                handler.sendEmptyMessageDelayed(0, DELAY);
            }
        }*/
    }

    public void InitGraphStackMT()
    {
        if(m_cGraphCtrl == null) return;

        InitGraphProperty(ENPriceKindType.GE_PRICE_KIND_STACK_MOUNTAIN,"출고가격/영업가격/판매가격",10,0,false,false);

        //X,Y축 설정
        m_cGraphCtrl.SetXAxisDataFormat(m_nCurrentID, ENXAxisDataFormat.GE_XAXIS_DATA_FORMAT_STRING);
        m_cGraphCtrl.SetYAxisDataFormat(m_nCurrentID, ENYAxisDataFormat.GE_YAXIS_DATA_FORMAT_VALUE);
        m_cGraphCtrl.SetXAxisDspType(m_nCurrentID, true, ENXLayerVertDspType.GE_XLAYER_VERT_DSP_235ANGLE, 8);

        m_cGraphCtrl.SetXLayerGridShow(m_nCurrentID, true);
        m_cGraphCtrl.SetYLayerExist(m_nCurrentID,ENYLayerExistType.GE_YLAYER_EXIST_LEFT,false,false);

        //X/Y축스케일 94%로변경
        m_cGraphCtrl.SetChartYScaleRatio(m_nCurrentID,0,0.94);
        //m_cGraphCtrl.SetChartYScaleRatio(m_nCurrentID,0,1.0);

        //가격차트 그라데이션 설정
        m_cGraphCtrl.SetPriceGradation(m_nCurrentID, true);
        m_cGraphCtrl.SetGradientDirectType(m_nCurrentID,ENGradationDirectType.GE_GRADATION_LEFT_RIGHT);

        m_cGraphCtrl.SetGradientPosType(m_nCurrentID, ENGradationPosType.GE_GRADATION_POS_END);
        m_cGraphCtrl.SetGradientPosColor(m_nCurrentID,1, ColorNames.GC_Silver);

        //배경
        m_cGraphCtrl.SetBlockBGColor(m_nCurrentID,ColorNames.GC_LightGoldenRodYellow);

        m_cGraphCtrl.SetAlphaValue(m_nCurrentID,(int)(0.6*255));

        //Label보기
        m_cGraphCtrl.SetShowLabelText(m_nCurrentID, true);
        m_cGraphCtrl.SetSubLabelTextIndex(m_nCurrentID, 1);

        //SubLabel보기
        m_cGraphCtrl.SetShowSubLabelText(m_nCurrentID, true);
        m_cGraphCtrl.SetSubLineDotIndex(m_nCurrentID,1);
        //LabelTextXPos
        m_cGraphCtrl.SetPrice_PropertyInfo(m_nCurrentID, 21, ENLabelTextXPos.GE_LABEL_TEXT_XPOS_CENTER.GetIndexValue());
        //LabelTextYPos
        m_cGraphCtrl.SetPrice_PropertyInfo(m_nCurrentID, 22, ENLabelTextYPos.GE_LABEL_TEXT_YPOS_UP.GetIndexValue());

        //Dot Draw
        m_cGraphCtrl.SetPrice_PropertyInfo(m_nCurrentID, 23, 1);
        //Dot Color
        m_cGraphCtrl.SetPrice_PropertyInfo(m_nCurrentID, 24, ColorNames.GC_Red);
        //Dot표시 타입
        m_cGraphCtrl.SetPrice_PropertyInfo(m_nCurrentID, 14, GlobalEnums.ENScatterDrawType.GE_SCATTER_DRAW_CIRCLE.GetIndexValue());

        //그룹 개수
        m_cGraphCtrl.SetGroupDataCount(m_nCurrentID,3);
        //상단 색상
        m_cGraphCtrl.SetGroupBodyFill(m_nCurrentID,0, true);
        m_cGraphCtrl.SetGroupBodyFill(m_nCurrentID,1, true);
        m_cGraphCtrl.SetGroupBodyFill(m_nCurrentID,2, true);

        m_cGraphCtrl.SetGroupBodyColor(m_nCurrentID,0, Color.rgb(84, 59, 115));
        m_cGraphCtrl.SetGroupBodyColor(m_nCurrentID,1, Color.rgb(123, 88, 167));
        m_cGraphCtrl.SetGroupBodyColor(m_nCurrentID,2, Color.rgb(139, 129, 155));

        //Y축 Grid Show
        m_cGraphCtrl.SetYLayer_PropertyInfo(m_nCurrentID,0,0,1.0);

        //X축 Grid Show
        m_cGraphCtrl.SetXLayer_PropertyInfo(m_nCurrentID, 0,1);

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //데이터 입력
        int nDataCount = 12;
        ArrayList<ST_PACKET_COREDATA> cPacketList = new ArrayList<ST_PACKET_COREDATA>();

        ST_PACKET_COREDATA stPacketBasicDataP = null;

        String strXDate = "";
        for(int i=0;i<nDataCount;i++)
        {
            stPacketBasicDataP = GlobalStructs.AllocPacketCore();

            strXDate = GlobalUtils.GetEnglishNameOfMonth(1+i);
            m_cGraphCtrl.AddUserXDTString(m_nCurrentID, strXDate);

            stPacketBasicDataP.dValueList[m_nOpenIndex] = GetRandomValue(3000.0, 1000.0);
            stPacketBasicDataP.dValueList[m_nHighIndex] = GetRandomValue(3000.0, 1000.0);
            stPacketBasicDataP.dValueList[m_nLowIndex] = GetRandomValue(3000.0, 1000.0);

            cPacketList.add(stPacketBasicDataP);
        }

        m_cGraphCtrl.ReceiveQuery(m_nCurrentID,"StackMountain","", 0, 0,0,cPacketList,0);
    }

    public void InitGraphLineDot()
    {
        if(m_cGraphCtrl == null) return;

        InitGraphProperty(ENPriceKindType.GE_PRICE_KIND_LINEDOT,"월간손익추이",10,0,false,false);

        //프레임 유형 변경
        m_cGraphCtrl.SetChartFrameType(m_nCurrentID, ENChartFrameType.GE_CHART_FRAME_MINI_BASIC);

        //X축 설정
        m_cGraphCtrl.SetXAxisDataFormat(m_nCurrentID, ENXAxisDataFormat.GE_XAXIS_DATA_FORMAT_STRING);

        //Y측 우측에 보기
        m_cGraphCtrl.SetYLayerExist(m_nCurrentID,ENYLayerExistType.GE_YLAYER_EXIST_RIGHT,false,false);
        m_cGraphCtrl.SetMainFloatingPoint(m_nCurrentID,2);

        //Label보기
        m_cGraphCtrl.SetShowLabelText(m_nCurrentID,true);
        //LabelTextXPos
        m_cGraphCtrl.SetPrice_PropertyInfo(m_nCurrentID, 21, ENLabelTextXPos.GE_LABEL_TEXT_XPOS_CENTER.GetIndexValue());
        //LabelTextYPos
        m_cGraphCtrl.SetPrice_PropertyInfo(m_nCurrentID, 22, ENLabelTextYPos.GE_LABEL_TEXT_YPOS_UP.GetIndexValue());

        //Dot표시 타입
        m_cGraphCtrl.SetPrice_PropertyInfo(m_nCurrentID, 14, ENScatterDrawType.GE_SCATTER_DRAW_CIRCLE.GetIndexValue());
        //LineDotColor
        m_cGraphCtrl.SetPrice_PropertyInfo(m_nCurrentID, 24, ColorNames.GC_Black);

        //배경에 Logo이미지 삽입(내부에 저장되어 있음)
        m_cGraphCtrl.SetBackGroundImgLogo(m_nCurrentID,true);

        Bitmap imgLogo = BitmapFactory.decodeResource(getResources(), R.drawable.img_ico_company_logo);
//        m_cGraphCtrl.SetBGImgLogoInfo(m_nCurrentID,imgLogo,new Point(),150*(int)m_fDeviceDPIWeight,
//                150*(int)m_fDeviceDPIWeight, ENLogoImgPosType.GE_LOGO_IMG_POS_RB,255/2);
        m_cGraphCtrl.SetBGImgLogoInfo(m_nCurrentID,imgLogo,new Point(),150,150, ENLogoImgPosType.GE_LOGO_IMG_POS_RB,255/2);

        //Line Color
        m_cGraphCtrl.SetPrice_PropertyInfo(m_nCurrentID, 10, ColorNames.GC_Red);
        //Line Weight
        m_cGraphCtrl.SetPrice_PropertyInfo(m_nCurrentID, 12, 3);

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //데이터 입력
        int nDataCount = 12;
        ArrayList<ST_PACKET_COREDATA> cPacketList = new ArrayList<ST_PACKET_COREDATA>();

        ST_PACKET_COREDATA stPacketBasicDataP = null;

        String strXDate = "";
        for(int i=0;i<nDataCount;i++)
        {
            stPacketBasicDataP = new GlobalStructs.ST_PACKET_COREDATA();
            strXDate = GlobalUtils.GetEnglishNameOfMonth(1+i);
            m_cGraphCtrl.AddUserXDTString(m_nCurrentID, strXDate);

            stPacketBasicDataP.dValueList[m_nCloseIndex] = GetRandomValue(1000.0, 0.0);

            cPacketList.add(stPacketBasicDataP);
        }

        m_cGraphCtrl.ReceiveQuery(m_nCurrentID,"LineDot","", 0, 0,0,cPacketList,0);
    }

    public void InitGraphScatter()
    {
        if(m_cGraphCtrl == null) return;

        InitGraphProperty(ENPriceKindType.GE_PRICE_KIND_SCATTER,"매출분포도",10,0,false,false);

        //프레임 유형 변경
        m_cGraphCtrl.SetChartFrameType(m_nCurrentID, ENChartFrameType.GE_CHART_FRAME_MINI_BASIC);

        //확대,축소/좌우 이동 보기
        m_cGraphCtrl.SetEnableExp_MoveBtns(m_nCurrentID, true);

        //X축 설정
        m_cGraphCtrl.SetXAxisDataFormat(m_nCurrentID, ENXAxisDataFormat.GE_XAXIS_DATA_FORMAT_VALUE);
        //Y축 설정
        m_cGraphCtrl.SetYAxisDataFormat(m_nCurrentID, ENYAxisDataFormat.GE_YAXIS_DATA_FORMAT_VALUE);
        //X축 격자
        //m_cGraphCtrl.SetXLayerGridShow(m_nCurrentID, true);
        //Y축 격자
        //m_cGraphCtrl.SetYLayer_PropertyInfo(m_nCurrentID,0,0,1.0);

        //선형회귀선 표현
        m_cGraphCtrl.SetShowScatterLinearLine(m_nCurrentID, true);

        //최종값 표현
        m_cGraphCtrl.SetShowScatterLastPos(m_nCurrentID, true);

        //X축 소숫점 설정
//        m_cGraphCtrl.SetXLayerFloatPoint(m_nCurrentID,2);

        //Scatter Drawing Type
        //GE_SCATTER_DRAW_CIRCLE,GE_SCATTER_DRAW_RECT,GE_SCATTER_DRAW_XMARKER,GE_SCATTER_DRAW_PLUS,GE_SCATTER_DRAW_DIAMOND,
        //m_cGraphCtrl.SetPrice_PropertyInfo(m_nCurrentID, 14, ENScatterDrawType.GE_SCATTER_DRAW_RECT.GetIndexValue());
        m_cGraphCtrl.SetPrice_PropertyInfo(m_nCurrentID, 14, ENScatterDrawType.GE_SCATTER_DRAW_DIAMOND.GetIndexValue());

        //Scatter 색상
        m_cGraphCtrl.SetPrice_PropertyInfo(m_nCurrentID, 1,ColorNames.GC_Red);		    //crPriceUpIn
        m_cGraphCtrl.SetPrice_PropertyInfo(m_nCurrentID, 2,1);			//bPriceUpFill
        m_cGraphCtrl.SetPrice_PropertyInfo(m_nCurrentID, 4,ColorNames.GC_Blue);		//crPriceDownIn
        m_cGraphCtrl.SetPrice_PropertyInfo(m_nCurrentID, 5,1);			//bPriceDownFill

        //선굵기
        m_cGraphCtrl.SetPrice_PropertyInfo(m_nCurrentID, 12,2);		    //crPriceUpIn

        //if(m_bChkShowCenterLine == true)
        {
            //선형회귀선 - 가로 기준선
            m_cGraphCtrl.SetHorzBaseLine(m_nCurrentID, 0.0,0,ENLineStyleType.PS_SOLID,1,false);
            //선형회귀선 - 세로 기준선
            m_cGraphCtrl.SetVertBaseLine(m_nCurrentID, 0.0,0,ENLineStyleType.PS_SOLID,1,true,false);
        }

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //데이터 입력
        int nDataCount = 120;
        ArrayList<ST_PACKET_COREDATA> cPacketList = new ArrayList<ST_PACKET_COREDATA>();

        ST_PACKET_COREDATA stPacketBasicDataP = null;

        for(int i=0;i<nDataCount;i++)
        {
            stPacketBasicDataP = GlobalStructs.AllocPacketCore();

            //stPacketBasicDataP.dValueList[m_nDateTimeIndex] = GetRandomValue(20180531.0,20180501.0);

            //단색으로 나올려면 막을것(하락색만 적용됨)
            stPacketBasicDataP.dValueList[m_nOpenIndex] = GetRandomValue(5000.0,-3000.0);

            stPacketBasicDataP.dValueList[m_nCloseIndex] = GetRandomValue(5000.0,-3000.0);
            stPacketBasicDataP.dValueList[m_nVolumeIndex] = GetRandomValue(200.0,-200.0);

            cPacketList.add(stPacketBasicDataP);
        }

        m_cGraphCtrl.ReceiveQuery(m_nCurrentID,"Scatter","", 0, 0,0,cPacketList,0);
    }

    public void InitGraphStair()
    {
        if(m_cGraphCtrl == null) return;

        InitGraphProperty(ENPriceKindType.GE_PRICE_KIND_STAIR,"매출증가율",10,0,false,false);

        //프레임 유형 변경
        m_cGraphCtrl.SetChartFrameType(m_nCurrentID, ENChartFrameType.GE_CHART_FRAME_MINI_BASIC);

        //X축 설정
        m_cGraphCtrl.SetXAxisDataFormat(m_nCurrentID, ENXAxisDataFormat.GE_XAXIS_DATA_FORMAT_STRING);
        //X축 가료 표시
        m_cGraphCtrl.SetXAxisDspType(m_nCurrentID,true, ENXLayerVertDspType.GE_XLAYER_VERT_DSP_90ANGLE, 10);

        //X축 격자보기
        m_cGraphCtrl.SetXLayerGridShow(m_nCurrentID,true);

        //Y측 우측에 보기
        m_cGraphCtrl.SetYLayerExist(m_nCurrentID,ENYLayerExistType.GE_YLAYER_EXIST_RIGHT,false,false);

        //LineDotDraw
        m_cGraphCtrl.SetPrice_PropertyInfo(m_nCurrentID, 23, 1);
        //LineDotColor
        m_cGraphCtrl.SetPrice_PropertyInfo(m_nCurrentID, 24, ColorNames.GC_Red);
        //Dot표시 타입
        m_cGraphCtrl.SetPrice_PropertyInfo(m_nCurrentID, 14, GlobalEnums.ENScatterDrawType.GE_SCATTER_DRAW_RECT.GetIndexValue());

//        //LineDot 보기
//        m_cGraphCtrl.SetLineDotDraw(m_nCurrentID, true);
//        //Dot의 색상
//        m_cGraphCtrl.SetLineDotColor(m_nCurrentID,ColorNames.GC_Red);

        //Label보기
        m_cGraphCtrl.SetShowLabelText(m_nCurrentID,true);

        //Line Up Color
        m_cGraphCtrl.SetPrice_PropertyInfo(m_nCurrentID, 1, ColorNames.GC_Orange);
        //Line Dn Color
        m_cGraphCtrl.SetPrice_PropertyInfo(m_nCurrentID, 4, ColorNames.GC_Magenta);

        //Line Weight
        m_cGraphCtrl.SetPrice_PropertyInfo(m_nCurrentID, 12, 3);

        m_cGraphCtrl.SetSpacePixelSize(m_nCurrentID, 0);

//        //Dot표시 타입
//        m_cGraphCtrl.SetPrice_PropertyInfo(m_nCurrentID, 14, ENScatterDrawType.GE_SCATTER_DRAW_RECT.GetIndexValue());

        //배경에 Logo이미지 삽입(내부에 저장되어 있음)
        m_cGraphCtrl.SetBackGroundImgLogo(m_nCurrentID,true);

        Bitmap imgLogo = BitmapFactory.decodeResource(getResources(), R.drawable.img_ico_company_logo);
        m_cGraphCtrl.SetBGImgLogoInfo(m_nCurrentID,imgLogo,new Point(),100*(int)m_fDeviceDPIWeight,
                100*(int)m_fDeviceDPIWeight, ENLogoImgPosType.GE_LOGO_IMG_POS_RT,(int)(255*0.8));

        //마지막 값 보기
        m_cGraphCtrl.SetYLastDataInfo(m_nCurrentID,true,false,false);
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //데이터 입력
        int nDataCount = 12;
        ArrayList<ST_PACKET_COREDATA> cPacketList = new ArrayList<ST_PACKET_COREDATA>();

        ST_PACKET_COREDATA stPacketBasicDataP = null;

        String strXDate = "";
        for(int i=0;i<nDataCount;i++)
        {
            stPacketBasicDataP = new GlobalStructs.ST_PACKET_COREDATA();

            strXDate = GlobalUtils.GetEnglishNameOfMonth(1+i);
            m_cGraphCtrl.AddUserXDTString(m_nCurrentID, strXDate);

            stPacketBasicDataP.dValueList[m_nCloseIndex] = GetRandomValue(2000.0, 1500.0);

            cPacketList.add(stPacketBasicDataP);
        }

        m_cGraphCtrl.ReceiveQuery(m_nCurrentID,"Stair","", 0, 0,0,cPacketList,0);
    }

    public void InitGraphFlow()
    {
        if(m_cGraphCtrl == null) return;

        InitGraphProperty(ENPriceKindType.GE_PRICE_KIND_FLOWBAR,"월간손익추이",10,0,false,false);

        //프레임 유형 변경
        m_cGraphCtrl.SetChartFrameType(m_nCurrentID, ENChartFrameType.GE_CHART_FRAME_MINI_BASIC);

        //X축 설정
        m_cGraphCtrl.SetXAxisDataFormat(m_nCurrentID, ENXAxisDataFormat.GE_XAXIS_DATA_FORMAT_STRING);

        //X축 격자보기
        m_cGraphCtrl.SetXLayerGridShow(m_nCurrentID,true);

        //LineDotDraw
        m_cGraphCtrl.SetPrice_PropertyInfo(m_nCurrentID, 23, 1);
        //LineDotColor
        m_cGraphCtrl.SetPrice_PropertyInfo(m_nCurrentID, 24, ColorNames.GC_Red);
        //Dot표시 타입
        m_cGraphCtrl.SetPrice_PropertyInfo(m_nCurrentID, 14, GlobalEnums.ENScatterDrawType.GE_SCATTER_DRAW_CIRCLE.GetIndexValue());

//        //LineDot 보기
//        m_cGraphCtrl.SetLineDotDraw(m_nCurrentID, true);
//        //Dot의 색상
//        m_cGraphCtrl.SetLineDotColor(m_nCurrentID,ColorNames.GC_Red);

        //Label보기
        m_cGraphCtrl.SetShowLabelText(m_nCurrentID,true);

        //투명도 설정
        m_cGraphCtrl.SetAlphaValue(m_nCurrentID,80);

        //마지막 값 보기
        m_cGraphCtrl.SetYLastDataInfo(m_nCurrentID,true,false,false);

        //Band In Color
        m_cGraphCtrl.SetPrice_PropertyInfo(m_nCurrentID, 7, ColorNames.GC_LightGreen);
        //Dot In Fill
        m_cGraphCtrl.SetPrice_PropertyInfo(m_nCurrentID, 8, 1);

//        //Dot표시 타입
//        m_cGraphCtrl.SetPrice_PropertyInfo(m_nCurrentID, 14, ENScatterDrawType.GE_SCATTER_DRAW_RECT.GetIndexValue());

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //데이터 입력
        int nDataCount = 12;
        ArrayList<ST_PACKET_COREDATA> cPacketList = new ArrayList<ST_PACKET_COREDATA>();

        ST_PACKET_COREDATA stPacketBasicDataP = null;

        String strXDate = "";

        for(int i=0;i<nDataCount;i++)
        {
            stPacketBasicDataP = new GlobalStructs.ST_PACKET_COREDATA();

            strXDate = String.format("%d월",1+i);
            m_cGraphCtrl.AddUserXDTString(m_nCurrentID, strXDate);

            stPacketBasicDataP.dValueList[m_nHighIndex] = GetRandomValue(2000.0, 1500.0);
            stPacketBasicDataP.dValueList[m_nLowIndex] = GetRandomValue(1500.0, 1000.0);

            cPacketList.add(stPacketBasicDataP);
        }

        m_cGraphCtrl.ReceiveQuery(m_nCurrentID,"Flow","", 0, 0,0,cPacketList,0);
    }

    public void InitGraphLine()
    {
        if(m_cGraphCtrl == null) return;

        InitGraphProperty(ENPriceKindType.GE_PRICE_KIND_LINE,"주가추이",10,0,false,false);

        //프레임 유형 변경
        m_cGraphCtrl.SetChartFrameType(m_nCurrentID, ENChartFrameType.GE_CHART_FRAME_MINI_BASIC);

        //X축 설정
        m_cGraphCtrl.SetXAxisDataFormat(m_nCurrentID, ENXAxisDataFormat.GE_XAXIS_DATA_FORMAT_STRING);

        //Y측 우측에 보기
        m_cGraphCtrl.SetYLayerExist(m_nCurrentID,ENYLayerExistType.GE_YLAYER_EXIST_RIGHT,false,false);
        m_cGraphCtrl.SetMainFloatingPoint(m_nCurrentID,2);

        //배경에 Logo이미지 삽입(내부에 저장되어 있음)
        m_cGraphCtrl.SetBackGroundImgLogo(m_nCurrentID,true);

        Bitmap imgLogo = BitmapFactory.decodeResource(getResources(), R.drawable.img_ico_company_logo);
        m_cGraphCtrl.SetBGImgLogoInfo(m_nCurrentID,imgLogo,new Point(),100*(int)m_fDeviceDPIWeight,
                100*(int)m_fDeviceDPIWeight, ENLogoImgPosType.GE_LOGO_IMG_POS_CT,(int)(255*0.5));

        //배경색상
        m_cGraphCtrl.SetBlockBGColor(m_nCurrentID,ColorNames.GC_Black);
        //차트바탕 텍스트색상
        m_cGraphCtrl.SetBlockTextColor(m_nCurrentID,ColorNames.GC_White);
        //X축 텍스트색상
        m_cGraphCtrl.SetXLayer_PropertyInfo(m_nCurrentID,4,ColorNames.GC_White);
        //Y축 텍스트색상
        m_cGraphCtrl.SetYLayer_PropertyInfo(m_nCurrentID,0,4,ColorNames.GC_White);

        //마지막 값 보기
        m_cGraphCtrl.SetYLastDataInfo(m_nCurrentID,true,false,false);

        //Line Color
        m_cGraphCtrl.SetPrice_PropertyInfo(m_nCurrentID, 10, ColorNames.GC_White);
        //Line Weight
        m_cGraphCtrl.SetPrice_PropertyInfo(m_nCurrentID, 12, 5);

        //해상도에 따른 Font에 가중치를 준다 - 2배로
        //m_cGraphCtrl.SetVarFontWeightSize(m_nCurrentID,2.0);
        int nVarFontSize = (int)(m_cGraphCtrl.GetVarBlockFontSize(m_nCurrentID)*1.2);
        m_cGraphCtrl.SetVarBlockFontSize(m_nCurrentID,nVarFontSize);

        //Font굵게
        m_cGraphCtrl.SetBlockFontWeight(m_nCurrentID,GlobalDefines.FW_BOLD);

        m_cGraphCtrl.SetYLayerWidth(m_nCurrentID,-1,m_nDefaultYLayerWidth+20*(int)m_fDeviceDPIWeight,false,false);
        m_cGraphCtrl.SetXLayerHeight(m_nCurrentID,m_nDefaultXLayerHeight);
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //데이터 입력
        int nDataCount = 12;
        ArrayList<ST_PACKET_COREDATA> cPacketList = new ArrayList<ST_PACKET_COREDATA>();

        ST_PACKET_COREDATA stPacketBasicDataP = null;

        String strXDate = "";
        for(int i=0;i<nDataCount;i++)
        {
            stPacketBasicDataP = new GlobalStructs.ST_PACKET_COREDATA();

            strXDate = GlobalUtils.GetEnglishNameOfMonth(1+i);
            m_cGraphCtrl.AddUserXDTString(m_nCurrentID, strXDate);

            stPacketBasicDataP.dValueList[m_nCloseIndex] = GetRandomValue(1000.0, 0.0);

            cPacketList.add(stPacketBasicDataP);
        }

        m_cGraphCtrl.ReceiveQuery(m_nCurrentID,"Line","", 0, 0,0,cPacketList,0);
    }

    public void InitGraphCandle()
    {
        if(m_cGraphCtrl == null) return;

        InitGraphProperty(ENPriceKindType.GE_PRICE_KIND_CANDLE,"주식차트",10,0,false,false);

        //프레임 유형 변경
        m_cGraphCtrl.SetChartFrameType(m_nCurrentID, ENChartFrameType.GE_CHART_FRAME_MINI_BASIC);

        //Y측 우측에 보기
        m_cGraphCtrl.SetYLayerExist(m_nCurrentID,ENYLayerExistType.GE_YLAYER_EXIST_RIGHT,false,false);

        //확대,축소/좌우 이동 보기
        m_cGraphCtrl.SetEnableExp_MoveBtns(m_nCurrentID, true);

        //최대최소 표시 보기
        m_cGraphCtrl.SetShowPriceMaxMin(m_nCurrentID,true);

        //X축 가료 표시
        m_cGraphCtrl.SetXAxisDspType(m_nCurrentID,true, ENXLayerVertDspType.GE_XLAYER_VERT_DSP_90ANGLE, 10);

        //가격차트 그라데이션 설정
        m_cGraphCtrl.SetPriceGradation(m_nCurrentID, true);
        m_cGraphCtrl.SetGradientDirectType(m_nCurrentID,ENGradationDirectType.GE_GRADATION_HCENTER_OUT);
        m_cGraphCtrl.SetGradientPosType(m_nCurrentID, ENGradationPosType.GE_GRADATION_POS_END);
        //m_cGraphCtrl.SetGradientPosColor(m_nCurrentID,0, Color.rgb(206,148,148));
        m_cGraphCtrl.SetGradientPosColor(m_nCurrentID,1, Color.rgb(169,206,220));

        //마지막 값 보기
        m_cGraphCtrl.SetYLastDataInfo(m_nCurrentID,true,false,false);
        /////////////////////////////////////////////////////////////////////////////////////////////////////////
        //데이터 직접 처리
        int nDataCount = 12;
        ArrayList<ST_PACKET_COREDATA> cPacketList = new ArrayList<ST_PACKET_COREDATA>();

        ST_PACKET_COREDATA stPacketBasicDataP = null;
        String strXDate;

        for(int i=0;i<nDataCount;i++)
        {
            stPacketBasicDataP = new GlobalStructs.ST_PACKET_COREDATA();

            strXDate = GlobalUtils.GetEnglishNameOfMonth(1+i);
            m_cGraphCtrl.AddUserXDTString(m_nCurrentID, strXDate);

            stPacketBasicDataP.dValueList[m_nDateTimeIndex] = 20180508+i;
            if(stPacketBasicDataP.dValueList[m_nDateTimeIndex]==20180512) stPacketBasicDataP.dValueList[m_nDateTimeIndex] = 20180508+i+1;
            else if(stPacketBasicDataP.dValueList[m_nDateTimeIndex]>=20180513) stPacketBasicDataP.dValueList[m_nDateTimeIndex] = 20180508+i+2;

            stPacketBasicDataP.dValueList[m_nOpenIndex] = GetRandomValue(10000.0, 8000.0);
            stPacketBasicDataP.dValueList[m_nCloseIndex] = GetRandomValue(10000.0, 8000.0);
            stPacketBasicDataP.dValueList[m_nHighIndex] = Math.max(stPacketBasicDataP.dValueList[m_nOpenIndex],stPacketBasicDataP.dValueList[m_nCloseIndex]) + GetRandomValue(800.0, 400.0);
            stPacketBasicDataP.dValueList[m_nLowIndex] = Math.min(stPacketBasicDataP.dValueList[m_nOpenIndex],stPacketBasicDataP.dValueList[m_nCloseIndex]) - GetRandomValue(800.0, 400.0);

            cPacketList.add(stPacketBasicDataP);
        }

        m_cGraphCtrl.ReceiveQuery(m_nCurrentID,"Candle","", 0, 0,0,cPacketList,0);
    }

    public void InitGraphMountain()
    {
        if(m_cGraphCtrl == null) return;

        InitGraphProperty(ENPriceKindType.GE_PRICE_KIND_MOUNTAIN,"매출액",10,0,false,false);

        //프레임 유형 변경
        m_cGraphCtrl.SetChartFrameType(m_nCurrentID, ENChartFrameType.GE_CHART_FRAME_MINI_BASIC);

        //X,Y축 설정
        m_cGraphCtrl.SetXAxisDataFormat(m_nCurrentID, ENXAxisDataFormat.GE_XAXIS_DATA_FORMAT_STRING);


        //Label 보기
        m_cGraphCtrl.SetShowLabelText(m_nCurrentID, true);
        //LabelXPos
        m_cGraphCtrl.SetPrice_PropertyInfo(m_nCurrentID, 21, GlobalEnums.ENLabelTextXPos.GE_LABEL_TEXT_XPOS_CENTER.GetIndexValue());
        //LabelYPos
        m_cGraphCtrl.SetPrice_PropertyInfo(m_nCurrentID, 22, GlobalEnums.ENLabelTextYPos.GE_LABEL_TEXT_YPOS_UP.GetIndexValue());

//        //LineDot 보기
//        m_cGraphCtrl.SetLineDotDraw(m_nCurrentID, true);
//        //Dot의 색상
//        m_cGraphCtrl.SetLineDotColor(m_nCurrentID,ColorNames.GC_Red);
//
//        //Label 보기
//        m_cGraphCtrl.SetShowLabelText(m_nCurrentID,true);

        //투명도 설정
        m_cGraphCtrl.SetAlphaValue(m_nCurrentID,80);

        //Mountain In Color
        m_cGraphCtrl.SetPrice_PropertyInfo(m_nCurrentID, 7, Color.rgb(180,50,100));
        //Fill
        m_cGraphCtrl.SetPrice_PropertyInfo(m_nCurrentID, 8, 1);


        //LineDotDraw
        m_cGraphCtrl.SetPrice_PropertyInfo(m_nCurrentID, 23, 1);
        //LineDotColor
        m_cGraphCtrl.SetPrice_PropertyInfo(m_nCurrentID, 24, ColorNames.GC_Red);
        //Dot표시 타입
        m_cGraphCtrl.SetPrice_PropertyInfo(m_nCurrentID, 14, ENScatterDrawType.GE_SCATTER_DRAW_CIRCLE.GetIndexValue());

        //데이터 전체 보기
        m_cGraphCtrl.SetPacketViewSize(m_nCurrentID,-1);
        ChangeGraphData();

        //3초 대기 핸들러
        if(m_bIsAttachFormGraph == false)
        {
            if (m_nHandlerStatus == STOP)
            {
                m_nHandlerStatus = PLAY;
                handler.sendEmptyMessageDelayed(0, DELAY);
            }
        }
    }

    public void InitGraphRadar()
    {
        if(m_cGraphCtrl == null) return;

        InitGraphProperty(ENPriceKindType.GE_PRICE_KIND_RADAR,"재무현황(자본/부채/유보금)",10,0,false,false);

        m_cGraphCtrl.SetYLayerExist(m_nCurrentID,ENYLayerExistType.GE_YLAYER_EXIST_NONE,false,false);
        m_cGraphCtrl.ChangeShowXLayer(m_nCurrentID,false);


        //Gradation설정
        m_cGraphCtrl.SetPriceGradation(m_nCurrentID, true);
        //m_cGraphCtrl.SetPriceGradation(m_nCurrentID, false)

        //레이더 외부선색(crSame)
        m_cGraphCtrl.SetPrice_PropertyInfo(m_nCurrentID,10, ColorNames.GC_Gray);
        //레이더 내부배경색(crLine)
        m_cGraphCtrl.SetPrice_PropertyInfo(m_nCurrentID, 9, ColorNames.GC_Khaki);
        //Gradation표시 안할때 배경 채움여부(OffFill)
        m_cGraphCtrl.SetPrice_PropertyInfo(m_nCurrentID, 8, 1);

        //레이더내부선색표시
        m_cGraphCtrl.SetYLayer_PropertyInfo(m_nCurrentID, 0, 0, 1.0);
        //Y축 GridColor
        m_cGraphCtrl.SetYLayer_PropertyInfo(m_nCurrentID, 0, 1, (double)ColorNames.GC_LightGray);
        //Y축 GridStyle
        m_cGraphCtrl.SetYLayer_PropertyInfo(m_nCurrentID, 0, 2, (double)ENLineStyleType.PS_DOT.GetIndexValue());
        //Y축 GridWeight
        m_cGraphCtrl.SetYLayer_PropertyInfo(m_nCurrentID, 0, 3, 1.0);

        //원형 보기 유무
        //m_cGraphCtrl.SetRadarFixCircle(m_nCurrentID,true);
        m_cGraphCtrl.SetRadarFixCircle(m_nCurrentID,false);

        //레이더 값표시 선 3개(유형,굵기)
        //Weight
        m_cGraphCtrl.SetPrice_PropertyInfo(m_nCurrentID,12, 3);
        //Style
        m_cGraphCtrl.SetPrice_PropertyInfo(m_nCurrentID,11, ENLineStyleType.PS_SOLID.GetIndexValue());

        m_cGraphCtrl.SetGroupDataCount(m_nCurrentID, 3);

        m_cGraphCtrl.SetGroupLineColor(m_nCurrentID, 0, Color.rgb(0, 0, 255));
        m_cGraphCtrl.SetGroupLineColor(m_nCurrentID, 1, Color.rgb(0, 255, 0));
        m_cGraphCtrl.SetGroupLineColor(m_nCurrentID, 2, Color.rgb(255, 0, 0));

        m_cGraphCtrl.SetGroupTextColor(m_nCurrentID, 0, Color.rgb(0, 0, 255));
        m_cGraphCtrl.SetGroupTextColor(m_nCurrentID, 1, Color.rgb(0, 255, 0));
        m_cGraphCtrl.SetGroupTextColor(m_nCurrentID, 2, Color.rgb(255, 0, 0));

        //텍스트 보기
        m_cGraphCtrl.SetShowLabelText(m_nCurrentID, true);
        //레이더 비율로 보기(주의 : 0에서 100의 값을 입력)
        m_cGraphCtrl.SetShowRatioRadar(m_nCurrentID, true);

        //2012/10/26 - 레이더 외부를 원으로 감싸기
        //m_cGraphCtrl.SetShowCircleRadar(m_nCurrentID,true)

        //Event동작막기
        m_cGraphCtrl.AddDisableChartEvent(m_nCurrentID, ENDISABLEEventType.GE_DISABLE_EVENT_ALL);

        //데이터 전체 보기
        m_cGraphCtrl.SetPacketViewSize(m_nCurrentID, -1);
        ChangeGraphData();

        //3초 대기 핸들러
        if(m_bIsAttachFormGraph == false)
        {
            if (m_nHandlerStatus == STOP)
            {
                m_nHandlerStatus = PLAY;
                handler.sendEmptyMessageDelayed(0, DELAY);
            }
        }
    }

    public void InitGraphPie()
    {
        if(m_cGraphCtrl == null) return;

        InitGraphProperty(ENPriceKindType.GE_PRICE_KIND_PIE,"인원비율",10,0,false,false);

        m_cGraphCtrl.SetYLayerExist(m_nCurrentID,ENYLayerExistType.GE_YLAYER_EXIST_NONE,false,false);
        m_cGraphCtrl.ChangeShowXLayer(m_nCurrentID,false);

        //스케일여백을 80%로변경(상하좌우 5%씩 띄움)
        m_cGraphCtrl.SetChartYScaleRatio(m_nCurrentID,0,0.8);

        m_cGraphCtrl.SetShowLabelText(m_nCurrentID,true);

        //Data Sorting
        m_cGraphCtrl.SetPieDataSorting(m_nCurrentID,true);

        m_cGraphCtrl.SetGroupBodyColor(m_nCurrentID,0, Color.rgb(0,192,0));
        m_cGraphCtrl.SetGroupBodyColor(m_nCurrentID,1, Color.rgb(142,255,255));
        m_cGraphCtrl.SetGroupBodyFill(m_nCurrentID, 2, false);
        m_cGraphCtrl.SetGroupBodyColor(m_nCurrentID,2, Color.rgb(255,143,107));
        m_cGraphCtrl.SetGroupBodyColor(m_nCurrentID,3, Color.rgb(255,51,153));
        m_cGraphCtrl.SetGroupBodyColor(m_nCurrentID,4, Color.rgb(199,177,255));
        m_cGraphCtrl.SetGroupBodyColor(m_nCurrentID,5, Color.rgb(0,0,192));
        m_cGraphCtrl.SetGroupBodyColor(m_nCurrentID,6, Color.rgb(80,50,0));
        m_cGraphCtrl.SetGroupBodyColor(m_nCurrentID,7, Color.rgb(128,0,0));
        m_cGraphCtrl.SetGroupBodyColor(m_nCurrentID,8, Color.rgb(192,192,192));
        m_cGraphCtrl.SetGroupBodyColor(m_nCurrentID,9, Color.rgb(255,198,107));

        //Event동작막기
        m_cGraphCtrl.AddDisableChartEvent(m_nCurrentID,ENDISABLEEventType.GE_DISABLE_EVENT_ALL);
        /*GE_DISABLE_EVENT_ALL(0),
        GE_DISABLE_EVENT_TOUCH_DOWN(1),
        GE_DISABLE_EVENT_TOUCH_UP(2),
        GE_DISABLE_EVENT_DOUBLE_TAB(3),
        GE_DISABLE_EVENT_TROUCH_DRAG(4),
        GE_DISABLE_EVENT_MULTI_TROUCH(5),
        GE_DISABLE_EVENT_LONG_PRESS(6),
        GE_DISABLE_EVENT_FLING(7),
        GE_DISABLE_EVENT_BLOCK_RESIZE(8),
        GE_DISABLE_EVENT_BLOCK_CHANGE(9),
        GE_DISABLE_EVENT_ZOOM(10);*/

        //데이터 전체 보기
        m_cGraphCtrl.SetPacketViewSize(m_nCurrentID,-1);
        ChangeGraphData();
    }

    public void InitGraph3DPie()
    {
        if(m_cGraphCtrl == null) return;

        InitGraphProperty(ENPriceKindType.GE_PRICE_KIND_PIE,"인원비율",10,0,false,false);

        m_cGraphCtrl.SetYLayerExist(m_nCurrentID,ENYLayerExistType.GE_YLAYER_EXIST_NONE,false,false);
        m_cGraphCtrl.ChangeShowXLayer(m_nCurrentID,false);

        //2012/10/05 - 3D그래프 표현
        m_cGraphCtrl.Set3DGraphDraw(m_nCurrentID, true);
        m_cGraphCtrl.Set3DDepthRatio_Pixel(m_nCurrentID, true);	//true:Ratio/false:Pixel
        m_cGraphCtrl.Set3DDepthRatio(m_nCurrentID, 0.05);

        //스케일여백을 90%로변경(상하좌우 5%씩 띄움)
        m_cGraphCtrl.SetChartYScaleRatio(m_nCurrentID,0,0.85);

        m_cGraphCtrl.SetShowLabelText(m_nCurrentID,true);

        //Data Sorting
        m_cGraphCtrl.SetPieDataSorting(m_nCurrentID,true);

        m_cGraphCtrl.SetGroupBodyColor(m_nCurrentID,0, Color.rgb(0,192,0));
        m_cGraphCtrl.SetGroupBodyColor(m_nCurrentID,1, Color.rgb(142,255,255));
        m_cGraphCtrl.SetGroupBodyColor(m_nCurrentID,2, Color.rgb(255,143,107));
        m_cGraphCtrl.SetGroupBodyColor(m_nCurrentID,3, Color.rgb(255,51,153));
        m_cGraphCtrl.SetGroupBodyColor(m_nCurrentID,4, Color.rgb(199,177,255));
        m_cGraphCtrl.SetGroupBodyColor(m_nCurrentID,5, Color.rgb(0,0,192));
        m_cGraphCtrl.SetGroupBodyColor(m_nCurrentID,6, Color.rgb(80,50,0));
        m_cGraphCtrl.SetGroupBodyColor(m_nCurrentID,7, Color.rgb(128,0,0));
        m_cGraphCtrl.SetGroupBodyColor(m_nCurrentID,8, Color.rgb(192,192,192));
        m_cGraphCtrl.SetGroupBodyColor(m_nCurrentID,9, Color.rgb(255,198,107));

        //데이터 전체 보기
        m_cGraphCtrl.SetPacketViewSize(m_nCurrentID,-1);
        ChangeGraphData();
    }

    public void InitGraphDonut()
    {
        if(m_cGraphCtrl == null) return;

        InitGraphProperty(ENPriceKindType.GE_PRICE_KIND_DONUT,"인원비율",10,0,false,false);

        m_cGraphCtrl.SetYLayerExist(m_nCurrentID,ENYLayerExistType.GE_YLAYER_EXIST_NONE,false,false);
        m_cGraphCtrl.ChangeShowXLayer(m_nCurrentID,false);

        //스케일여백을 90%로변경(상하좌우 5%씩 띄움)
        m_cGraphCtrl.SetChartYScaleRatio(m_nCurrentID,0,0.8);

        m_cGraphCtrl.SetShowLabelText(m_nCurrentID,true);

        //Data Sorting
        m_cGraphCtrl.SetPieDataSorting(m_nCurrentID,true);

        //2019/04/18 - 수치최소최소비율(설정안하면 N/A,로 전체 표현 ~ 겹칠수 있다)
        m_cGraphCtrl.SetPieLabelDataLimit(m_nCurrentID,5.0);

        //Line Weight
        m_cGraphCtrl.SetPrice_PropertyInfo(m_nCurrentID, 12, 2);

        //Donut Pie 속성
        m_cGraphCtrl.SetPieDonutDraw(m_nCurrentID,true);
        m_cGraphCtrl.ChangeFUNCToolType(ENFUNCToolType.GE_FUNCTOOL_TITLE_DATA,ENTitleDspType.GE_TITLE_DISPLAY_NONE.GetIndexValue());
        //m_cGraphCtrl.ChangeFUNCToolType(ENFUNCToolType.GE_FUNCTOOL_TITLE_DATA,ENTitleDspType.GE_TITLE_DISPLAY_NAME.GetIndexValue());
        m_cGraphCtrl.SetPieDonutSize(m_nCurrentID, 400);

        //Slice 처리
        m_cGraphCtrl.SetPieSliceIndex(m_nCurrentID, 2);

//        //Donut 내부 색상 그라데이션 처리
//        m_cGraphCtrl.SetPriceGradation(m_nCurrentID,true);
//        //0:From,1:To
//        m_cGraphCtrl.SetGradientPosColor(m_nCurrentID,0, ColorNames.GC_WhiteSmoke);
//        m_cGraphCtrl.SetGradientPosColor(m_nCurrentID,1, ColorNames.GC_LightBlue);

        m_cGraphCtrl.SetGroupBodyColor(m_nCurrentID,0, Color.rgb(0,192,0));
        m_cGraphCtrl.SetGroupBodyColor(m_nCurrentID,1, Color.rgb(142,255,255));
        m_cGraphCtrl.SetGroupBodyColor(m_nCurrentID,2, Color.rgb(255,143,107));
        m_cGraphCtrl.SetGroupBodyColor(m_nCurrentID,3, Color.rgb(255,51,153));
        m_cGraphCtrl.SetGroupBodyColor(m_nCurrentID,4, Color.rgb(199,177,255));
        m_cGraphCtrl.SetGroupBodyColor(m_nCurrentID,5, Color.rgb(0,0,192));
        m_cGraphCtrl.SetGroupBodyColor(m_nCurrentID,6, Color.rgb(80,50,0));
        m_cGraphCtrl.SetGroupBodyColor(m_nCurrentID,7, Color.rgb(128,0,0));
        m_cGraphCtrl.SetGroupBodyColor(m_nCurrentID,8, Color.rgb(192,192,192));
        m_cGraphCtrl.SetGroupBodyColor(m_nCurrentID,9, Color.rgb(255,198,107));


        //Event동작막기
        m_cGraphCtrl.AddDisableChartEvent(m_nCurrentID,ENDISABLEEventType.GE_DISABLE_EVENT_ALL);
        /*GE_DISABLE_EVENT_ALL(0),
        GE_DISABLE_EVENT_TOUCH_DOWN(1),
        GE_DISABLE_EVENT_TOUCH_UP(2),
        GE_DISABLE_EVENT_DOUBLE_TAB(3),
        GE_DISABLE_EVENT_TROUCH_DRAG(4),
        GE_DISABLE_EVENT_MULTI_TROUCH(5),
        GE_DISABLE_EVENT_LONG_PRESS(6),
        GE_DISABLE_EVENT_FLING(7),
        GE_DISABLE_EVENT_BLOCK_RESIZE(8),
        GE_DISABLE_EVENT_BLOCK_CHANGE(9),
        GE_DISABLE_EVENT_ZOOM(10);*/

        //데이터 전체 보기
        m_cGraphCtrl.SetPacketViewSize(m_nCurrentID,-1);
        ChangeGraphData();

        //3초 대기 핸들러
        if(m_bIsAttachFormGraph == false)
        {
            if (m_nHandlerStatus == STOP)
            {
                m_nHandlerStatus = PLAY;
                handler.sendEmptyMessageDelayed(0, DELAY);
            }
        }
    }

    public void InitGraphHorzStackBar()
    {
        if(m_cGraphCtrl == null) return;

        InitGraphProperty(ENPriceKindType.GE_PRICE_KIND_HORZ_STACKBAR,"매출액/영업이익/순이익",10,0,false,false);

        //X,Y축 설정
        m_cGraphCtrl.SetXAxisDataFormat(m_nCurrentID, ENXAxisDataFormat.GE_XAXIS_DATA_FORMAT_VALUE);
        m_cGraphCtrl.SetYAxisDataFormat(m_nCurrentID, ENYAxisDataFormat.GE_YAXIS_DATA_FORMAT_STRING);

        m_cGraphCtrl.SetXLayerGridShow(m_nCurrentID, true);
        m_cGraphCtrl.SetYLayerExist(m_nCurrentID,ENYLayerExistType.GE_YLAYER_EXIST_LEFT,false,false);

        //X/Y축스케일 94%로변경
        m_cGraphCtrl.SetChartYScaleRatio(m_nCurrentID,0,0.94);
        //m_cGraphCtrl.SetChartYScaleRatio(m_nCurrentID,0,1.0);

        //가격차트 그라데이션 설정
        m_cGraphCtrl.SetPriceGradation(m_nCurrentID, true);
        m_cGraphCtrl.SetGradientDirectType(m_nCurrentID,ENGradationDirectType.GE_GRADATION_LEFT_RIGHT);

        m_cGraphCtrl.SetGradientPosType(m_nCurrentID, ENGradationPosType.GE_GRADATION_POS_END);
        m_cGraphCtrl.SetGradientPosColor(m_nCurrentID,1, ColorNames.GC_Silver);

        //배경
        m_cGraphCtrl.SetBlockBGColor(m_nCurrentID,ColorNames.GC_LightGoldenRodYellow);

//        //Label보기
//        //m_cGraphCtrl.SetShowLabelText(m_nCurrentID, true);
//
//        //SubLabel보기
//        m_cGraphCtrl.SetShowSubLabelText(m_nCurrentID, true);

        //라벨 텍스트
        m_cGraphCtrl.SetShowLabelText(m_nCurrentID, true);
        m_cGraphCtrl.SetShowSubLabelText(m_nCurrentID, true);
        m_cGraphCtrl.SetSubLabelTextIndex(m_nCurrentID, 0);

        //LabelTextXPos
        m_cGraphCtrl.SetPrice_PropertyInfo(m_nCurrentID, 21, GlobalEnums.ENLabelTextXPos.GE_LABEL_TEXT_XPOS_LEFT.GetIndexValue());
        //LabelTextYPos
        m_cGraphCtrl.SetPrice_PropertyInfo(m_nCurrentID, 22, GlobalEnums.ENLabelTextYPos.GE_LABEL_TEXT_YPOS_CENTER.GetIndexValue());

        //데이터 사이 간격
        //m_cGraphCtrl.SetBongMargin(m_nCurrentID, 5)
        m_cGraphCtrl.SetBongMargin(m_nCurrentID, (int)(5.0*m_fDeviceDPIWeight));

        //그룹 개수
        m_cGraphCtrl.SetGroupDataCount(m_nCurrentID,3);
        //상단 색상
        m_cGraphCtrl.SetGroupBodyFill(m_nCurrentID,0, true);
        m_cGraphCtrl.SetGroupBodyFill(m_nCurrentID,1, true);
        m_cGraphCtrl.SetGroupBodyFill(m_nCurrentID,2, true);

        m_cGraphCtrl.SetGroupBodyColor(m_nCurrentID,0, Color.rgb(84, 59, 115));
        m_cGraphCtrl.SetGroupBodyColor(m_nCurrentID,1, Color.rgb(123, 88, 167));
        m_cGraphCtrl.SetGroupBodyColor(m_nCurrentID,2, Color.rgb(139, 129, 155));
        m_cGraphCtrl.SetGroupTextColor(m_nCurrentID,0,Color.rgb(84, 59, 115));
        m_cGraphCtrl.SetGroupTextColor(m_nCurrentID,1,Color.rgb(123, 88, 167));
        m_cGraphCtrl.SetGroupTextColor(m_nCurrentID,2,Color.rgb(139, 129, 155));

        //Y축 Grid Show
        m_cGraphCtrl.SetYLayer_PropertyInfo(m_nCurrentID,0,0,1.0);

        //X축 Grid Show
        m_cGraphCtrl.SetXLayer_PropertyInfo(m_nCurrentID, 0,1);

        //Event동작막기
        m_cGraphCtrl.AddDisableChartEvent(m_nCurrentID,ENDISABLEEventType.GE_DISABLE_EVENT_ALL);
        /*GE_DISABLE_EVENT_ALL(0),
        GE_DISABLE_EVENT_TOUCH_DOWN(1),
        GE_DISABLE_EVENT_TOUCH_UP(2),
        GE_DISABLE_EVENT_DOUBLE_TAB(3),
        GE_DISABLE_EVENT_TROUCH_DRAG(4),
        GE_DISABLE_EVENT_MULTI_TROUCH(5),
        GE_DISABLE_EVENT_LONG_PRESS(6),
        GE_DISABLE_EVENT_FLING(7),
        GE_DISABLE_EVENT_BLOCK_RESIZE(8),
        GE_DISABLE_EVENT_BLOCK_CHANGE(9),
        GE_DISABLE_EVENT_ZOOM(10);*/

        //데이터 전체 보기
        m_cGraphCtrl.SetPacketViewSize(m_nCurrentID,-1);
        ChangeGraphData();

        //3초 대기 핸들러
        if(m_bIsAttachFormGraph == false)
        {
            if (m_nHandlerStatus == STOP)
            {
                m_nHandlerStatus = PLAY;
                handler.sendEmptyMessageDelayed(0, DELAY);
            }
        }
    }

    public void InitGraphHorizontal2sideBar()
    {
        if(m_cGraphCtrl == null) return;

        InitGraphProperty(ENPriceKindType.GE_PRICE_KIND_HORZ_2SIDEBAR,"매출/매입",10,0,false,false);

        //X,Y축 설정
        m_cGraphCtrl.SetXAxisDataFormat(m_nCurrentID, ENXAxisDataFormat.GE_XAXIS_DATA_FORMAT_VALUE);
        m_cGraphCtrl.SetYAxisDataFormat(m_nCurrentID, ENYAxisDataFormat.GE_YAXIS_DATA_FORMAT_STRING);

        m_cGraphCtrl.SetXLayerGridShow(m_nCurrentID, true);

        //X/Y축스케일 94%로변경
        m_cGraphCtrl.SetChartYScaleRatio(m_nCurrentID,0,0.94);
        //m_cGraphCtrl.SetChartYScaleRatio(m_nCurrentID,0,1.0);

        //가격차트 그라데이션 설정
        /*m_cGraphCtrl.SetPriceGradation(m_nCurrentID, true);
        m_cGraphCtrl.SetGradientDirectType(m_nCurrentID,ENGradationDirectType.GE_GRADATION_HCENTER_OUT);
        //m_cGraphCtrl.SetGradientPosType(m_nCurrentID,2);
        m_cGraphCtrl.SetGradientPosType(m_nCurrentID, ENGradationPosType.GE_GRADATION_POS_END);
        m_cGraphCtrl.SetGradientPosColor(m_nCurrentID,0, ColorNames.GC_WhiteSmoke);
        m_cGraphCtrl.SetGradientPosColor(m_nCurrentID,1, ColorNames.GC_WhiteSmoke);*/

        //배경 옅은 회색
        m_cGraphCtrl.SetBlockBGColor(m_nCurrentID,ColorNames.GC_LightLightGray);

        //Label보기
        m_cGraphCtrl.SetShowLabelText(m_nCurrentID, true);
        //LabelTextXPos
        m_cGraphCtrl.SetPrice_PropertyInfo(m_nCurrentID, 21, ENLabelTextXPos.GE_LABEL_TEXT_XPOS_LEFT.GetIndexValue());
        //LabelTextYPos
        m_cGraphCtrl.SetPrice_PropertyInfo(m_nCurrentID, 22, ENLabelTextYPos.GE_LABEL_TEXT_YPOS_CENTER.GetIndexValue());

        //데이터 사이 간격
        //m_cGraphCtrl.SetBongMargin(m_nCurrentID, 5)
        m_cGraphCtrl.SetBongMargin(m_nCurrentID, (int)(5*m_fDeviceDPIWeight));

        //Zero위치 Center
        m_cGraphCtrl.SetZeroPosCenter(m_nCurrentID,true);

        //중심선 표시
        m_cGraphCtrl.SetDrawOscBaseLine(m_nCurrentID,true);

        //그룹 개수
        m_cGraphCtrl.SetGroupDataCount(m_nCurrentID,1);
        //상단 색상
        m_cGraphCtrl.SetGroupBodyColor(m_nCurrentID,0, ColorNames.GC_Red);
        m_cGraphCtrl.SetGroupBodyColor(m_nCurrentID,1, ColorNames.GC_DarkBlue);
        m_cGraphCtrl.SetGroupTextColor(m_nCurrentID,0,ColorNames.GC_Red);
        m_cGraphCtrl.SetGroupTextColor(m_nCurrentID,1,ColorNames.GC_Blue);

        //Y축 Grid Show
        m_cGraphCtrl.SetYLayer_PropertyInfo(m_nCurrentID,0,0,1.0);

        //X축 Grid Show
        m_cGraphCtrl.SetXLayer_PropertyInfo(m_nCurrentID, 0,1);

        //UpIn
        m_cGraphCtrl.SetPrice_PropertyInfo(m_nCurrentID, 1,ColorNames.GC_IndianRed);
        //UpFill
        m_cGraphCtrl.SetPrice_PropertyInfo(m_nCurrentID, 2,1);
        //DnIn
        m_cGraphCtrl.SetPrice_PropertyInfo(m_nCurrentID, 4,ColorNames.GC_CadetBlue);
        //DnFill
        m_cGraphCtrl.SetPrice_PropertyInfo(m_nCurrentID, 5,1);

        //Event동작막기
        m_cGraphCtrl.AddDisableChartEvent(m_nCurrentID,ENDISABLEEventType.GE_DISABLE_EVENT_ALL);

        /*GE_DISABLE_EVENT_ALL(0),
        GE_DISABLE_EVENT_TOUCH_DOWN(1),
        GE_DISABLE_EVENT_TOUCH_UP(2),
        GE_DISABLE_EVENT_DOUBLE_TAB(3),
        GE_DISABLE_EVENT_TROUCH_DRAG(4),
        GE_DISABLE_EVENT_MULTI_TROUCH(5),
        GE_DISABLE_EVENT_LONG_PRESS(6),
        GE_DISABLE_EVENT_FLING(7),
        GE_DISABLE_EVENT_BLOCK_RESIZE(8),
        GE_DISABLE_EVENT_BLOCK_CHANGE(9),
        GE_DISABLE_EVENT_ZOOM(10);*/

        //데이터 전체 보기
        m_cGraphCtrl.SetPacketViewSize(m_nCurrentID,-1);
        ChangeGraphData();

        //Update
        /*m_nQueryDataEvent = 2;

        //3초 대기 핸들러
        if(m_bIsAttachFormGraph == false)
        {
            if (m_nHandlerStatus == STOP)
            {
                m_nHandlerStatus = PLAY;
                handler.sendEmptyMessageDelayed(0, DELAY);
            }
        }*/
    }

    public void InitGraphHorizontalOscBar()
    {
        if(m_cGraphCtrl == null) return;

        InitGraphProperty(ENPriceKindType.GE_PRICE_KIND_HORZ_OSCBAR,"매출액증가율",10,0,false,false);

        //X,Y축 설정
        m_cGraphCtrl.SetXAxisDataFormat(m_nCurrentID, ENXAxisDataFormat.GE_XAXIS_DATA_FORMAT_VALUE);
        m_cGraphCtrl.SetYAxisDataFormat(m_nCurrentID, ENYAxisDataFormat.GE_YAXIS_DATA_FORMAT_STRING);

        m_cGraphCtrl.SetXLayerGridShow(m_nCurrentID, true);

        //X/Y축스케일 94%로변경
        //m_cGraphCtrl.SetChartYScaleRatio(m_nCurrentID,0,0.94);
        m_cGraphCtrl.SetChartYScaleRatio(m_nCurrentID,0,1.0);

        //가격차트 그라데이션 설정
        m_cGraphCtrl.SetPriceGradation(m_nCurrentID, true);
        m_cGraphCtrl.SetGradientDirectType(m_nCurrentID,ENGradationDirectType.GE_GRADATION_HCENTER_OUT);
        m_cGraphCtrl.SetGradientPosType(m_nCurrentID, ENGradationPosType.GE_GRADATION_POS_END);
        //m_cGraphCtrl.SetGradientPosColor(m_nCurrentID,0, ColorNames.GC_WhiteSmoke);
        m_cGraphCtrl.SetGradientPosColor(m_nCurrentID,1, ColorNames.GC_Silver);

        //배경 검정색
        m_cGraphCtrl.SetBlockBGColor(m_nCurrentID,ColorNames.GC_Black);

        //텍스트 흰색
        m_cGraphCtrl.SetBlockTextColor(m_nCurrentID,ColorNames.GC_White);
        //X축 텍스트색상
        m_cGraphCtrl.SetXLayer_PropertyInfo(m_nCurrentID,4,ColorNames.GC_White);
        //Y축 텍스트색상
        m_cGraphCtrl.SetYLayer_PropertyInfo(m_nCurrentID,0,4,ColorNames.GC_White);

        //Label보기
        m_cGraphCtrl.SetShowLabelText(m_nCurrentID, true);
        //LabelTextXPos
        m_cGraphCtrl.SetPrice_PropertyInfo(m_nCurrentID, 21, GlobalEnums.ENLabelTextXPos.GE_LABEL_TEXT_XPOS_LEFT.GetIndexValue());
        //LabelTextYPos
        m_cGraphCtrl.SetPrice_PropertyInfo(m_nCurrentID, 22, GlobalEnums.ENLabelTextYPos.GE_LABEL_TEXT_YPOS_CENTER.GetIndexValue());

        //데이터 사이 간격
        m_cGraphCtrl.SetBongMargin(m_nCurrentID, (int)(5*m_fDeviceDPIWeight));

        //Zero위치 Center
        m_cGraphCtrl.SetZeroPosCenter(m_nCurrentID,true);

        //중심선 표시
        m_cGraphCtrl.SetDrawOscBaseLine(m_nCurrentID,true);

        //Y축 Grid Show
        m_cGraphCtrl.SetYLayer_PropertyInfo(m_nCurrentID,0,0,1.0);
        //Y축 GridColor
        m_cGraphCtrl.SetYLayer_PropertyInfo(m_nCurrentID,0,1,ColorNames.GC_DarkGray);
        //Y축 TextColor
        m_cGraphCtrl.SetYLayer_PropertyInfo(m_nCurrentID,0,4,ColorNames.GC_White);

        //X축 Grid Show
        m_cGraphCtrl.SetXLayer_PropertyInfo(m_nCurrentID, 0,1);
        //X축 GridColor
        m_cGraphCtrl.SetXLayer_PropertyInfo(m_nCurrentID, 1,ColorNames.GC_DarkGray);
        //X축 TextColor
        m_cGraphCtrl.SetXLayer_PropertyInfo(m_nCurrentID, 4,ColorNames.GC_White);

        //UpIn
        m_cGraphCtrl.SetPrice_PropertyInfo(m_nCurrentID, 1,ColorNames.GC_IndianRed);
        //UpFill
        m_cGraphCtrl.SetPrice_PropertyInfo(m_nCurrentID, 2,1);
        //DnIn
        m_cGraphCtrl.SetPrice_PropertyInfo(m_nCurrentID, 4,ColorNames.GC_CadetBlue);
        //DnFill
        m_cGraphCtrl.SetPrice_PropertyInfo(m_nCurrentID, 5,1);

        //Event동작막기
        m_cGraphCtrl.AddDisableChartEvent(m_nCurrentID,ENDISABLEEventType.GE_DISABLE_EVENT_ALL);
        /*GE_DISABLE_EVENT_ALL(0),
        GE_DISABLE_EVENT_TOUCH_DOWN(1),
        GE_DISABLE_EVENT_TOUCH_UP(2),
        GE_DISABLE_EVENT_DOUBLE_TAB(3),
        GE_DISABLE_EVENT_TROUCH_DRAG(4),
        GE_DISABLE_EVENT_MULTI_TROUCH(5),
        GE_DISABLE_EVENT_LONG_PRESS(6),
        GE_DISABLE_EVENT_FLING(7),
        GE_DISABLE_EVENT_BLOCK_RESIZE(8),
        GE_DISABLE_EVENT_BLOCK_CHANGE(9),
        GE_DISABLE_EVENT_ZOOM(10);*/

        //데이터 전체 보기
        m_cGraphCtrl.SetPacketViewSize(m_nCurrentID,-1);
        ChangeGraphData();
    }

    private void InitGraphHorizontalBar()
    {
        if(m_cGraphCtrl == null) return;

        InitGraphProperty(ENPriceKindType.GE_PRICE_KIND_HORZ_BAR,"매출액",10,0,false,false);

        //X,Y축 설정
        m_cGraphCtrl.SetXAxisDataFormat(m_nCurrentID, ENXAxisDataFormat.GE_XAXIS_DATA_FORMAT_VALUE);
        m_cGraphCtrl.SetYAxisDataFormat(m_nCurrentID, ENYAxisDataFormat.GE_YAXIS_DATA_FORMAT_STRING);

        m_cGraphCtrl.SetXLayerGridShow(m_nCurrentID, true);

        //X/Y축스케일 100%로변경
        m_cGraphCtrl.SetChartYScaleRatio(m_nCurrentID,0,1.0);

        //최소값 설정
        m_cGraphCtrl.SetPriceXMaxMinValue(m_nCurrentID,GlobalDefines.GD_NAVALUE_DOUBLE,400.0);

        //가격차트 그라데이션 설정
        m_cGraphCtrl.SetPriceGradation(m_nCurrentID, true);
        m_cGraphCtrl.SetGradientDirectType(m_nCurrentID,ENGradationDirectType.GE_GRADATION_LEFT_RIGHT);
        m_cGraphCtrl.SetGradientPosType(m_nCurrentID, ENGradationPosType.GE_GRADATION_POS_START);
        m_cGraphCtrl.SetGradientPosColor(m_nCurrentID,0, ColorNames.GC_BlueViolet);

        //OffIn
        m_cGraphCtrl.SetPrice_PropertyInfo(m_nCurrentID, 7, ColorNames.GC_SkyBlue);
        //OffFill
        m_cGraphCtrl.SetPrice_PropertyInfo(m_nCurrentID, 8, 1);

        //데이터 사이 간격
        m_cGraphCtrl.SetBongMargin(m_nCurrentID, (int)(5*m_fDeviceDPIWeight));

        //2012/10/05 - 3D그래프 표현
        m_cGraphCtrl.Set3DGraphDraw(m_nCurrentID, true);
        m_cGraphCtrl.Set3DDepthRatio_Pixel(m_nCurrentID, true);	//TRUE:Ratio/FALSE:Pixel
        m_cGraphCtrl.Set3DDepthRatio(m_nCurrentID, 0.015);
        m_cGraphCtrl.Set3DDepthXAxisPixel(m_nCurrentID, 3);
        m_cGraphCtrl.Set3DDepthYAxisPixel(m_nCurrentID, 3);
        m_cGraphCtrl.Set3DHorzDepthUpDn(m_nCurrentID,true);		//TRUE:Up/FALSE:Down

        //2012/10/25 - 3D축 배경색 설정
        m_cGraphCtrl.Set3DYAxisBkFill(m_nCurrentID, true);
        m_cGraphCtrl.Set3DYAxisBkColor(m_nCurrentID, ColorNames.GC_LightGray);
        m_cGraphCtrl.Set3DXAxisBkFill(m_nCurrentID, true);
        m_cGraphCtrl.Set3DXAxisBkColor(m_nCurrentID, ColorNames.GC_LightGray);

        //Text색상
        m_cGraphCtrl.SetBlockTextColor(m_nCurrentID,ColorNames.GC_Black);
        //X축 텍스트색상
        m_cGraphCtrl.SetXLayer_PropertyInfo(m_nCurrentID,4,ColorNames.GC_Black);
        //Y축 텍스트색상
        m_cGraphCtrl.SetYLayer_PropertyInfo(m_nCurrentID,0,4,ColorNames.GC_Black);

        //Label보기
        m_cGraphCtrl.SetShowLabelText(m_nCurrentID, true);
        //LabelTextXPos
        m_cGraphCtrl.SetPrice_PropertyInfo(m_nCurrentID, 21, GlobalEnums.ENLabelTextXPos.GE_LABEL_TEXT_XPOS_CENTER.GetIndexValue());
        //LabelTextYPos
        m_cGraphCtrl.SetPrice_PropertyInfo(m_nCurrentID, 22, GlobalEnums.ENLabelTextYPos.GE_LABEL_TEXT_YPOS_CENTER.GetIndexValue());

        //Event동작막기
        m_cGraphCtrl.AddDisableChartEvent(m_nCurrentID,ENDISABLEEventType.GE_DISABLE_EVENT_ALL);
        /*GE_DISABLE_EVENT_ALL(0),
        GE_DISABLE_EVENT_TOUCH_DOWN(1),
        GE_DISABLE_EVENT_TOUCH_UP(2),
        GE_DISABLE_EVENT_DOUBLE_TAB(3),
        GE_DISABLE_EVENT_TROUCH_DRAG(4),
        GE_DISABLE_EVENT_MULTI_TROUCH(5),
        GE_DISABLE_EVENT_LONG_PRESS(6),
        GE_DISABLE_EVENT_FLING(7),
        GE_DISABLE_EVENT_BLOCK_RESIZE(8),
        GE_DISABLE_EVENT_BLOCK_CHANGE(9),
        GE_DISABLE_EVENT_ZOOM(10);*/

        //데이터 전체 보기
        m_cGraphCtrl.SetPacketViewSize(m_nCurrentID,-1);
        ChangeGraphData();
    }

    private void InitGraphGroupBar()
    {
        if(m_cGraphCtrl == null) return;

        InitGraphProperty(ENPriceKindType.GE_PRICE_KIND_GROUPBAR,"개인/기관/외국인",10,2,false,false);

        //라벨 텍스트
        m_cGraphCtrl.SetShowLabelText(m_nCurrentID, false);
        m_cGraphCtrl.SetShowSubLabelText(m_nCurrentID, true);
        //2019/04/10 - 그래픽 기능 추가(하위 항목중 한값만 텍스트 표현)
        //-1 : 전체 표현(Default),Index(해당 SubLabel만 표현)
        m_cGraphCtrl.SetSubLabelTextIndex(m_nCurrentID, 1);

        //X,Y축 설정
        m_cGraphCtrl.SetXAxisDataFormat(m_nCurrentID, ENXAxisDataFormat.GE_XAXIS_DATA_FORMAT_STRING);
        m_cGraphCtrl.SetYAxisDataFormat(m_nCurrentID, ENYAxisDataFormat.GE_YAXIS_DATA_FORMAT_VALUE);
        m_cGraphCtrl.SetXAxisDspType(m_nCurrentID,true, ENXLayerVertDspType.GE_XLAYER_VERT_DSP_90ANGLE, 8);

        //X축 격자 보기
        m_cGraphCtrl.SetXLayerGridShow(m_nCurrentID, true);
        //X축 격자 점선
        m_cGraphCtrl.SetXLayer_PropertyInfo(m_nCurrentID, 2,ENLineStyleType.PS_DOT.GetIndexValue());
        //X축 격자 굵기
        m_cGraphCtrl.SetXLayer_PropertyInfo(m_nCurrentID, 3,2);

        //가격차트 그라데이션 설정
        //m_cGraphCtrl.SetPriceGradation(m_nCurrentID, true);
        //m_cGraphCtrl.SetGradientDirectType(m_nCurrentID, GlobalEnums.ENGradationType.GE_GRADATION_LEFT_RIGHT);
        //m_cGraphCtrl.SetGradientDirectType(m_nCurrentID, .GE_GRADATION_HCENTER_OUT);

        //m_cGraphCtrl.SetGradientPosType(m_nCurrentID, .GE_GRADATION_POS_START);
        //m_cGraphCtrl.SetGradientPosColor(m_nCurrentID, 0, GlobalUtils.GetUIColorToInt(GlobalUtils.GetRGBToUIColor(200,200,255)));

        //m_cGraphCtrl.SetBongMargin(m_nCurrentID, 20);
        //m_cGraphCtrl.SetBongMargin(m_nCurrentID, 10);

        m_cGraphCtrl.SetBongMargin(m_nCurrentID,(int)(10*m_fDeviceDPIWeight));

        m_cGraphCtrl.SetMainFloatingPoint(m_nCurrentID,0);

        //그룹 개수
        m_cGraphCtrl.SetGroupDataCount(m_nCurrentID,3);

        //상단 색상
        m_cGraphCtrl.SetGroupBodyFill(m_nCurrentID, 0,true);
        m_cGraphCtrl.SetGroupBodyColor(m_nCurrentID, 0,ColorNames.GC_Red);
        //GroupText를 Body와 같이 해줘야 "/"로 구분했을때 텍스트가 봉색상따라 간다
        m_cGraphCtrl.SetGroupTextColor(m_nCurrentID, 0,ColorNames.GC_Red);

        m_cGraphCtrl.SetGroupBodyFill(m_nCurrentID, 1,true);
        m_cGraphCtrl.SetGroupBodyColor(m_nCurrentID, 1,ColorNames.GC_Magenta);
        m_cGraphCtrl.SetGroupTextColor(m_nCurrentID, 1,ColorNames.GC_Magenta);

        m_cGraphCtrl.SetGroupBodyFill(m_nCurrentID, 2,true);
        m_cGraphCtrl.SetGroupBodyColor(m_nCurrentID, 2,ColorNames.GC_ForestGreen);
        m_cGraphCtrl.SetGroupTextColor(m_nCurrentID, 2,ColorNames.GC_ForestGreen);

        //데이터 전체 보기
        m_cGraphCtrl.SetPacketViewSize(m_nCurrentID,-1);
        ChangeGraphData();

        //3초 대기 핸들러
        if(m_bIsAttachFormGraph == false)
        {
            if (m_nHandlerStatus == STOP)
            {
                m_nHandlerStatus = PLAY;
                handler.sendEmptyMessageDelayed(0, DELAY);
            }
        }
    }

    private void InitGraphStackBar()
    {
        if(m_cGraphCtrl == null) return;

        InitGraphProperty(ENPriceKindType.GE_PRICE_KIND_STACKBAR,"매출(최대/최소/평균)",10,2,false,false);

        //라벨 텍스트
        m_cGraphCtrl.SetShowLabelText(m_nCurrentID, true);
        m_cGraphCtrl.SetShowSubLabelText(m_nCurrentID, true);
        //2019/04/10 - 그래픽 기능 추가(하위 항목중 한값만 텍스트 표현)
        //-1 : 전체 표현(Default),Index(해당 SubLabel만 표현)
        m_cGraphCtrl.SetSubLabelTextIndex(m_nCurrentID, 1);

        //X,Y축 설정
        m_cGraphCtrl.SetXAxisDataFormat(m_nCurrentID, ENXAxisDataFormat.GE_XAXIS_DATA_FORMAT_STRING);
        m_cGraphCtrl.SetYAxisDataFormat(m_nCurrentID, ENYAxisDataFormat.GE_YAXIS_DATA_FORMAT_VALUE);

        //Y측 우측에 보기
        m_cGraphCtrl.SetYLayerExist(m_nCurrentID,ENYLayerExistType.GE_YLAYER_EXIST_RIGHT,false,false);

        //가격차트 그라데이션 설정
        m_cGraphCtrl.SetPriceGradation(m_nCurrentID, true);
        //m_cGraphCtrl.SetGradientDirectType(m_nCurrentID,ENGradationDirectType.GE_GRADATION_LEFT_RIGHT);
        m_cGraphCtrl.SetGradientDirectType(m_nCurrentID, ENGradationDirectType.GE_GRADATION_HCENTER_OUT);
        m_cGraphCtrl.SetGradientPosType(m_nCurrentID, ENGradationPosType.GE_GRADATION_POS_START);

        m_cGraphCtrl.SetMainFloatingPoint(m_nCurrentID,0);

        //그룹 개수
        m_cGraphCtrl.SetGroupDataCount(m_nCurrentID,3);

        //상단 색상
        m_cGraphCtrl.SetGroupBodyFill(m_nCurrentID, 0,true);
        m_cGraphCtrl.SetGroupBodyColor(m_nCurrentID, 0,Color.rgb(34,76,126));
        m_cGraphCtrl.SetGroupTextColor(m_nCurrentID, 0,ColorNames.GC_Black);
        m_cGraphCtrl.SetGradientPosColor(m_nCurrentID, 0, Color.rgb(200, 200, 255));


        m_cGraphCtrl.SetGroupBodyFill(m_nCurrentID, 1,true);
        m_cGraphCtrl.SetGroupBodyColor(m_nCurrentID, 1,Color.rgb(45,94,155));
        m_cGraphCtrl.SetGroupTextColor(m_nCurrentID, 1,ColorNames.GC_Black);
        m_cGraphCtrl.SetGradientPosColor(m_nCurrentID, 1, Color.rgb(200, 200, 255));


        m_cGraphCtrl.SetGroupBodyFill(m_nCurrentID, 2,true);
        m_cGraphCtrl.SetGroupBodyColor(m_nCurrentID, 2,Color.rgb(124,140,169));
        m_cGraphCtrl.SetGroupTextColor(m_nCurrentID, 2,ColorNames.GC_Black);
        m_cGraphCtrl.SetGradientPosColor(m_nCurrentID,2, Color.rgb(200,200,255));

        //데이터 전체 보기
        m_cGraphCtrl.SetPacketViewSize(m_nCurrentID,-1);
        ChangeGraphData();

        //3초 대기 핸들러
        if(m_bIsAttachFormGraph == false)
        {
            if (m_nHandlerStatus == STOP)
            {
                m_nHandlerStatus = PLAY;
                handler.sendEmptyMessageDelayed(0, DELAY);
            }
        }
    }

    private void InitGraph2SideBar()
    {
        if(m_cGraphCtrl == null) return;

        InitGraphProperty(ENPriceKindType.GE_PRICE_KIND_2SIDEBAR,"영업이익/영업손실",5,2,false,false);

        m_cGraphCtrl.SetXAxisDataFormat(m_nCurrentID, ENXAxisDataFormat.GE_XAXIS_DATA_FORMAT_STRING);

        m_cGraphCtrl.SetXAxisDspType(m_nCurrentID,true, ENXLayerVertDspType.GE_XLAYER_VERT_DSP_45ANGLE, 8);
        //m_cGraphCtrl.SetXAxisDspType(m_nCurrentID,true, ENXLayerVertDspType.GE_XLAYER_VERT_DSP_MULLINES	, 8);

        //가격차트 그라데이션 설정
        //m_cGraphCtrl.SetPriceGradation(m_nCurrentID, true);
        m_cGraphCtrl.SetGradientDirectType(m_nCurrentID,ENGradationDirectType.GE_GRADATION_HCENTER_OUT);
        //m_cGraphCtrl.SetGradientDirectType(m_nCurrentID,ENGradationDirectType.GE_GRADATION_TOPRIGHT_LEFTBOTTOM);

        m_cGraphCtrl.SetDrawOscBaseLine(m_nCurrentID,true);

        //주의:SetGradientPosType이 0이면 SetGradientPosColor(m_nCurrentID,0 번째 Color를 Gradient색상으로 사용
        //     SetGradientPosType이 1이면 SetGradientPosColor(m_nCurrentID,1 번째 Color를 Gradient색상으로 사용
        //위의 값으로 설정하지 않은 색상은 원래의 Default색상사용
        //SetGradientPosType이 2이면 SetGradientPosColor(m_nCurrentID,0 과 SetGradientPosColor(m_nCurrentID,1 둘다를 사용한다
        m_cGraphCtrl.SetGradientPosType(m_nCurrentID, ENGradationPosType.GE_GRADATION_POS_END);
        //m_cGraphCtrl.SetGradientPosColor(m_nCurrentID,0, Color.rgb(206,148,148));
        //m_cGraphCtrl.SetGradientPosColor(m_nCurrentID,1, Color.rgb(169,206,220));
        m_cGraphCtrl.SetGradientPosColor(m_nCurrentID, 1, GlobalUtils.LightenColor(ColorNames.GC_Blue,0.8));

        m_cGraphCtrl.SetMainFloatingPoint(m_nCurrentID,0);

        m_cGraphCtrl.SetShowLabelText(m_nCurrentID, true);

        //Zero위치 Center
        m_cGraphCtrl.SetZeroPosCenter(m_nCurrentID,true);

        //최대최소 표시 보기
        m_cGraphCtrl.SetShowPriceMaxMin(m_nCurrentID,true);

        //그룹 개수
        m_cGraphCtrl.SetGroupDataCount(m_nCurrentID,2);

        //타이틀 텍스트 색상
        m_cGraphCtrl.SetGroupTextColor(m_nCurrentID,0,ColorNames.GC_Red);
        m_cGraphCtrl.SetGroupTextColor(m_nCurrentID,1,ColorNames.GC_Blue);
        //GradationPos(End이면 - [0](UpColor)->[1](GradeintPos(0)Color))
        //GradationPos(Start이면 - [0](UpColor)<-[1](GradeintPos(0)Color))
        //UpIn

        //UpFill
        m_cGraphCtrl.SetPrice_PropertyInfo(m_nCurrentID, 2, 1);

        //GradationPos(End이면 - [0](DnColor)->[1](GradeintPos(1)Color))
        //GradationPos(Start이면 - [0](DnColor)<-[1](GradeintPos(1)Color))
        //DnIn
        m_cGraphCtrl.SetPrice_PropertyInfo(m_nCurrentID, 4, GlobalUtils.LightenColor(ColorNames.GC_Blue,0.8));
        m_cGraphCtrl.SetGradientPosColor(m_nCurrentID, 1, ColorNames.GC_Blue);
        //DnFill
        m_cGraphCtrl.SetPrice_PropertyInfo(m_nCurrentID, 5, 1);

        //LabelXPos
        m_cGraphCtrl.SetPrice_PropertyInfo(m_nCurrentID, 21, ENLabelTextXPos.GE_LABEL_TEXT_XPOS_CENTER.GetIndexValue());

        //데이터 전체 보기
        m_cGraphCtrl.SetPacketViewSize(m_nCurrentID,-1);
        ChangeGraphData();

        //3초 대기 핸들러
        if(m_bIsAttachFormGraph == false)
        {
            if (m_nHandlerStatus == STOP)
            {
                m_nHandlerStatus = PLAY;
                handler.sendEmptyMessageDelayed(0, DELAY);
            }
        }
    }

    private void InitGraphStickBar()
    {
        if(m_cGraphCtrl == null) return;

        InitGraphProperty(ENPriceKindType.GE_PRICE_KIND_STICKBAR,"매출증가율",5,2,false,false);

        m_cGraphCtrl.SetXAxisDataFormat(m_nCurrentID, ENXAxisDataFormat.GE_XAXIS_DATA_FORMAT_STRING);

        //OffIn
        //m_cGraphCtrl.SetPrice_PropertyInfo(m_nCurrentID, 7, ColorNames.GC_CadetBlue);

        m_cGraphCtrl.SetBlockBGColor(m_nCurrentID, ColorNames.GC_Black);
        m_cGraphCtrl.SetBlockTextColor(m_nCurrentID, ColorNames.GC_White);
        //X축 텍스트색상
        m_cGraphCtrl.SetXLayer_PropertyInfo(m_nCurrentID, 4, ColorNames.GC_White);
        //Y축 텍스트색상
        m_cGraphCtrl.SetYLayer_PropertyInfo(m_nCurrentID, 0, 4, ColorNames.GC_White);

        //가격차트 그라데이션 설정
        m_cGraphCtrl.SetPriceGradation(m_nCurrentID, true);
        //m_cGraphCtrl.SetGradientDirectType(m_nCurrentID,ENGradationDirectType.GE_GRADATION_TOPLEFT_RIGHTBOTTOM);
        m_cGraphCtrl.SetGradientDirectType(m_nCurrentID,ENGradationDirectType.GE_GRADATION_VCENTER_OUT);

        m_cGraphCtrl.SetGradientPosType(m_nCurrentID, ENGradationPosType.GE_GRADATION_POS_START);
        m_cGraphCtrl.SetGradientPosColor(m_nCurrentID,0, ColorNames.GC_WhiteSmoke);
        //m_cGraphCtrl.SetGradientPosColor(m_nCurrentID,1, Color.rgb(169,206,220));

        m_cGraphCtrl.SetVerticalXLayer(m_nCurrentID, true);
        m_cGraphCtrl.SetXAxisDspType(m_nCurrentID, true, ENXLayerVertDspType.GE_XLAYER_VERT_DSP_90ANGLE, 3);
        //텍스트 중간위치(2019/04/05 - 동작X - 미완성)
        m_cGraphCtrl.SetXLayer_PropertyInfo(m_nCurrentID, 11, ENXAxisTextAlign.GE_XAXIS_TEXT_ALIGN_CENTER.GetIndexValue());
        //m_cGraphCtrl.SetXLayer_PropertyInfo(m_nCurrentID, 11, ENXAxisTextAlign.GE_XAXIS_TEXT_ALIGN_LEFT.rawValue);

        //최대/최소표시
        m_cGraphCtrl.SetShowPriceMaxMin(m_nCurrentID, false);

        //라벨 표시
        m_cGraphCtrl.SetShowLabelText(m_nCurrentID,true);

        //데이터 사이 간격
        m_cGraphCtrl.SetBongMargin(m_nCurrentID, (int)(3*m_fDeviceDPIWeight));

        //가격 우선 스케일
        m_cGraphCtrl.SetPriceTopMost(m_nCurrentID, true);

        //최대최소 표시 보기
        m_cGraphCtrl.SetShowPriceMaxMin(m_nCurrentID,true);

        //가로 격자
        m_cGraphCtrl.SetYLayer_PropertyInfo(m_nCurrentID,0,0,1.0);

        //2012/10/05 - 3D그래프 표현
        m_cGraphCtrl.Set3DGraphDraw(m_nCurrentID, true);
        m_cGraphCtrl.Set3DDepthRatio_Pixel(m_nCurrentID, true);	//TRUE:Ratio/FALSE:Pixel
        m_cGraphCtrl.Set3DDepthRatio(m_nCurrentID, 0.02);
        m_cGraphCtrl.Set3DDepthXAxisPixel(m_nCurrentID, 5);
        m_cGraphCtrl.Set3DDepthYAxisPixel(m_nCurrentID, 15);
        m_cGraphCtrl.Set3DHorzDepthUpDn(m_nCurrentID,true);		//TRUE:Up/FALSE:Down

        //2012/10/25 - 3D축 배경색 설정	
        m_cGraphCtrl.Set3DYAxisBkFill(m_nCurrentID, true);
        m_cGraphCtrl.Set3DYAxisBkColor(m_nCurrentID, ColorNames.GC_LightLightLightGray);
        m_cGraphCtrl.Set3DXAxisBkFill(m_nCurrentID, true);
        m_cGraphCtrl.Set3DXAxisBkColor(m_nCurrentID, ColorNames.GC_LightPink);

        m_cGraphCtrl.SetPriceTopMost(m_nCurrentID, false);

        //데이터 전체 보기
        m_cGraphCtrl.SetPacketViewSize(m_nCurrentID,-1);
        ChangeGraphData();
    }

    private void InitGraphOscBar()
    {
        if(m_cGraphCtrl == null) return;

        InitGraphProperty(ENPriceKindType.GE_PRICE_KIND_OSCBAR,"매출증가율",5,2,false,false);

        //공통속성
        m_cGraphCtrl.SetXAxisDataFormat(m_nCurrentID, ENXAxisDataFormat.GE_XAXIS_DATA_FORMAT_STRING);
        m_cGraphCtrl.SetXAxisDspType(m_nCurrentID,true, ENXLayerVertDspType.GE_XLAYER_VERT_DSP_MULLINES	, 8);

        //데이터 사이 간격
        m_cGraphCtrl.SetBongMargin(m_nCurrentID, (int)(3*m_fDeviceDPIWeight));

        //마지막 값 보기
        m_cGraphCtrl.SetYLastDataInfo(m_nCurrentID,true,false,false);

        //Y측 우측에 보기
        m_cGraphCtrl.SetYLayerExist(m_nCurrentID,ENYLayerExistType.GE_YLAYER_EXIST_RIGHT,false,false);

        //X축 격자 보기
        m_cGraphCtrl.SetXLayerGridShow(m_nCurrentID, true);
        //X축 격자 실선
        //m_cGraphCtrl.SetXLayer_PropertyInfo(m_nCurrentID, 2,ENLineStyleType.PS_SOLID.GetIndexValue());
        //X축 격자 굵기
        m_cGraphCtrl.SetXLayer_PropertyInfo(m_nCurrentID, 3,2);

        //가격차트 그라데이션 설정
        m_cGraphCtrl.SetPriceGradation(m_nCurrentID, true);
        m_cGraphCtrl.SetGradientDirectType(m_nCurrentID,ENGradationDirectType.GE_GRADATION_LEFT_RIGHT);

        m_cGraphCtrl.SetGradientPosType(m_nCurrentID, ENGradationPosType.GE_GRADATION_POS_START);
        m_cGraphCtrl.SetGradientPosColor(m_nCurrentID,0, ColorNames.GC_Black);
        //m_cGraphCtrl.SetGradientPosColor(m_nCurrentID,0, Color.rgb(169,206,220));

        //최대최소 표시 보기
        m_cGraphCtrl.SetShowPriceMaxMin(m_nCurrentID,true);

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //개별속성
        m_cGraphCtrl.SetDrawOscBaseLine(m_nCurrentID,true);

        //0선 기준선 표현
        m_cGraphCtrl.SetRiseFallBaseType(m_nCurrentID, ENBaseLineType.GE_BASELINE_USER1);
        m_cGraphCtrl.SetBaseLineValue(m_nCurrentID, ENBaseLineType.GE_BASELINE_USER1, 0.0);

        //Zero위치 Center
        m_cGraphCtrl.SetZeroPosCenter(m_nCurrentID,true);

        //라벨 표시
        m_cGraphCtrl.SetShowLabelText(m_nCurrentID,true);

        //UpIn
        m_cGraphCtrl.SetPrice_PropertyInfo(m_nCurrentID, 1,ColorNames.GC_Red);
        //UpFill
        m_cGraphCtrl.SetPrice_PropertyInfo(m_nCurrentID, 2,1);
        //DnIn
        m_cGraphCtrl.SetPrice_PropertyInfo(m_nCurrentID, 4,ColorNames.GC_Blue);
        //DnFill
        m_cGraphCtrl.SetPrice_PropertyInfo(m_nCurrentID, 5,1);

        ///////////////////////////////////////////////////////////////////////////
        //데이터 전체 보기
        m_cGraphCtrl.SetPacketViewSize(m_nCurrentID,-1);
        ChangeGraphData();

        //3초 대기 핸들러
        if(m_bIsAttachFormGraph == false)
        {
            if (m_nHandlerStatus == STOP)
            {
                m_nHandlerStatus = PLAY;
                handler.sendEmptyMessageDelayed(0, DELAY);
            }
        }
    }

    private void InitGraphRiseFall()
    {
        if(m_cGraphCtrl == null) return;

        InitGraphProperty(ENPriceKindType.GE_PRICE_KIND_RISEFALL,"순이익율",5,2,false,false);

        m_cGraphCtrl.SetChartFrameType(m_nCurrentID, ENChartFrameType.GE_CHART_FRAME_MINI_BASIC);

        m_cGraphCtrl.SetXAxisDataFormat(m_nCurrentID, ENXAxisDataFormat.GE_XAXIS_DATA_FORMAT_STRING);

        //투명도 조절(0~255 - 255값은 완전 불투명)
        m_cGraphCtrl.SetAlphaValue(m_nCurrentID, 80);

        //Zero위치 Center
        m_cGraphCtrl.SetZeroPosCenter(m_nCurrentID,true);

        //마지막 값 보기
        m_cGraphCtrl.SetYLastDataInfo(m_nCurrentID,true,false,false);

        //최대최소 표시 보기
        m_cGraphCtrl.SetShowPriceMaxMin(m_nCurrentID,true);

        //확대,축소/좌우 이동 보기
        m_cGraphCtrl.SetEnableExp_MoveBtns(m_nCurrentID, true);

        /////////////////////////////////////////////////////////////////////////////////////////////////////
        //개별속성
        m_cGraphCtrl.SetDrawOscBaseLine(m_nCurrentID, true);

        //0선 기준선 표현
        m_cGraphCtrl.SetRiseFallBaseType(m_nCurrentID, ENBaseLineType.GE_BASELINE_USER1);
        m_cGraphCtrl.SetBaseLineValue(m_nCurrentID, ENBaseLineType.GE_BASELINE_USER1, 0.0);

        //Zero위치 Center
        m_cGraphCtrl.SetZeroPosCenter(m_nCurrentID, true);

        //라벨 표시
        m_cGraphCtrl.SetShowLabelText(m_nCurrentID, true);

        //LineDotDraw
        m_cGraphCtrl.SetPrice_PropertyInfo(m_nCurrentID, 23, 1);
        //LineDotColor
        m_cGraphCtrl.SetPrice_PropertyInfo(m_nCurrentID, 24, ColorNames.GC_Black);
        //Dot표시 타입
        m_cGraphCtrl.SetPrice_PropertyInfo(m_nCurrentID, 14, ENScatterDrawType.GE_SCATTER_DRAW_RECT.GetIndexValue());

        //UpIn
        m_cGraphCtrl.SetPrice_PropertyInfo(m_nCurrentID, 1,ColorNames.GC_Red);
        //UpFill
        m_cGraphCtrl.SetPrice_PropertyInfo(m_nCurrentID, 2,1);
        //DnIn
        m_cGraphCtrl.SetPrice_PropertyInfo(m_nCurrentID, 4,ColorNames.GC_Blue);
        //DnFill
        m_cGraphCtrl.SetPrice_PropertyInfo(m_nCurrentID, 5,1);

        //데이터 전체 보기
        m_cGraphCtrl.SetPacketViewSize(m_nCurrentID,-1);
        ChangeGraphData();

        //        //가격차트 그라데이션 설정
//        m_cGraphCtrl.SetPriceGradation(m_nCurrentID, true);
//        m_cGraphCtrl.SetGradientDirectType(m_nCurrentID,ENGradationDirectType.GE_GRADATION_VCENTER_OUT);
//
//        m_cGraphCtrl.SetDrawOscBaseLine(m_nCurrentID,true);
//        m_cGraphCtrl.SetGradientPosType(m_nCurrentID, ENGradationPosType.GE_GRADATION_POS_START);
//        m_cGraphCtrl.SetGradientPosColor(m_nCurrentID,0, ColorNames.GC_Black);
//        //m_cGraphCtrl.SetGradientPosColor(m_nCurrentID,0, Color.rgb(169,206,220));
    }

    private void InitGraphProperty(ENPriceKindType enGraphType,String strName,int nDrawMargin,int nFloatPoint,boolean bResize,boolean bRedraw)
    {
        if (m_nCurrentID == -1) return;

        //프레임 유형 변경
        m_cGraphCtrl.SetChartFrameType(m_nCurrentID, ENChartFrameType.GE_CHART_FRAME_GRAPH);

        //가격차트 이외 영역 없애기
        m_cGraphCtrl.DeleteBasicBlockAll(m_nCurrentID, ENBlockDeleteType.GE_NORMAL_EXCEPT_PRC_DELETE_ALL);

        //모든 데이터 삭제
        m_cGraphCtrl.ClearAllChartData(m_nCurrentID,false);

        //데이터 처리형태(0:Query,1:Add,2:Update,3:Shift)
        m_nQueryDataEvent = 0;
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //소숫점 2자리 입력
        m_cGraphCtrl.SetMainFloatingPoint(m_nCurrentID, nFloatPoint);

        //그래프 타입 변경
        m_cGraphCtrl.SetMainPriceType(m_nCurrentID,enGraphType);

        //타이틀표현
        m_cGraphCtrl.SetBaseCodeName(m_nCurrentID,strName);

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //가격차트 초기화
        //UpIn
        m_cGraphCtrl.SetPrice_PropertyInfo(m_nCurrentID, 1,ColorNames.GC_Red);
        //UpFill
        m_cGraphCtrl.SetPrice_PropertyInfo(m_nCurrentID, 2,1);
        //DnIn
        m_cGraphCtrl.SetPrice_PropertyInfo(m_nCurrentID, 4,ColorNames.GC_Blue);
        //DnFill
        m_cGraphCtrl.SetPrice_PropertyInfo(m_nCurrentID, 5,1);
        //OffIn
        m_cGraphCtrl.SetPrice_PropertyInfo(m_nCurrentID, 7, ColorNames.GC_Green);
        //OffFill
        m_cGraphCtrl.SetPrice_PropertyInfo(m_nCurrentID, 8, 1);
        //Line Color
        m_cGraphCtrl.SetPrice_PropertyInfo(m_nCurrentID, 10, ColorNames.GC_Red);
        //Style
        m_cGraphCtrl.SetPrice_PropertyInfo(m_nCurrentID,11, ENLineStyleType.PS_SOLID.GetIndexValue());
        //Line Weight
        m_cGraphCtrl.SetPrice_PropertyInfo(m_nCurrentID, 12, 1);

        //Band In Color
        m_cGraphCtrl.SetPrice_PropertyInfo(m_nCurrentID, 7, ColorNames.GC_Green);
        //Dot In Fill
        m_cGraphCtrl.SetPrice_PropertyInfo(m_nCurrentID, 8, 1);

        //LineDotDraw
        m_cGraphCtrl.SetPrice_PropertyInfo(m_nCurrentID, 23, 0);
        //LineDotColor
        m_cGraphCtrl.SetPrice_PropertyInfo(m_nCurrentID, 24, ColorNames.GC_Black);
        //Dot표시 타입
        m_cGraphCtrl.SetPrice_PropertyInfo(m_nCurrentID, 14, ENScatterDrawType.GE_SCATTER_DRAW_NONE.GetIndexValue());
        m_cGraphCtrl.SetSubLineDotIndex(m_nCurrentID,-1);

        //상하한가 감추기
        m_cGraphCtrl.ChangeFUNCToolType(ENFUNCToolType.GE_FUNCTOOL_HIGHEST_LOWEST, 0);

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //3D 표현
        m_cGraphCtrl.Set3DGraphDraw(m_nCurrentID, false);
        m_cGraphCtrl.Set3DDepthRatio_Pixel(m_nCurrentID, true);	//TRUE:Ratio/FALSE:Pixel
        m_cGraphCtrl.Set3DDepthRatio(m_nCurrentID, 0.0);
        m_cGraphCtrl.Set3DDepthXAxisPixel(m_nCurrentID, 0);
        m_cGraphCtrl.Set3DDepthYAxisPixel(m_nCurrentID, 0);
        m_cGraphCtrl.Set3DHorzDepthUpDn(m_nCurrentID,true);		//TRUE:Up/FALSE:Down

        //2012/10/25 - 3D축 배경색 설정
        m_cGraphCtrl.Set3DYAxisBkFill(m_nCurrentID, false);
        m_cGraphCtrl.Set3DYAxisBkColor(m_nCurrentID, ColorNames.GC_White);
        m_cGraphCtrl.Set3DXAxisBkFill(m_nCurrentID, false);
        m_cGraphCtrl.Set3DXAxisBkColor(m_nCurrentID, ColorNames.GC_White);

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //영역 크기 초기화
        m_cGraphCtrl.SetChartBlockRatio(m_nCurrentID,0,0.0,1.0,false);

        m_cGraphCtrl.SetYLayerExist(m_nCurrentID, ENYLayerExistType.GE_YLAYER_EXIST_LEFT,bResize,bRedraw);
        //Y축 넓이 초기화
        m_cGraphCtrl.SetYLayerWidth(m_nCurrentID,m_nDefaultYLayerWidth,m_nDefaultYLayerWidth,false,false);
        m_cGraphCtrl.ChangeShowXLayer(m_nCurrentID, true);

        //X/Y축스케일 90%로변경
        m_cGraphCtrl.SetChartYScaleRatio(m_nCurrentID,0,0.9);

        //ScrollBar 숨기기
        m_cGraphCtrl.SetShowScrollBar(m_nCurrentID, false);
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //색상,배경 초기화
        m_cGraphCtrl.SetBlockBGColor(m_nCurrentID,ColorNames.GC_White);
        m_cGraphCtrl.SetBlockTextColor(m_nCurrentID,ColorNames.GC_Black);
        //X축 텍스트색상
        m_cGraphCtrl.SetXLayer_PropertyInfo(m_nCurrentID,4,ColorNames.GC_Black);
        //Y축 텍스트색상
        m_cGraphCtrl.SetYLayer_PropertyInfo(m_nCurrentID,0,4,ColorNames.GC_Black);

        //배경에 Logo이미지 삽입(내부에 저장되어 있음)
        m_cGraphCtrl.SetBackGroundImgLogo(m_nCurrentID,false);
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //X,Y축 설정
        m_cGraphCtrl.SetVerticalXLayer(m_nCurrentID, false);
        m_cGraphCtrl.SetXAxisDataFormat(m_nCurrentID, ENXAxisDataFormat.GE_XAXIS_DATA_FORMAT_DATETIME);
        m_cGraphCtrl.SetYAxisDataFormat(m_nCurrentID, ENYAxisDataFormat.GE_YAXIS_DATA_FORMAT_VALUE);
        m_cGraphCtrl.SetXLayerGridShow(m_nCurrentID, false);
        m_cGraphCtrl.SetYLastDataInfo(m_nCurrentID, false,false,false);
        //Y축 Grid Hide
        m_cGraphCtrl.SetYLayer_PropertyInfo(m_nCurrentID,0,0,0.0);

        //우측 Y축 마지막 값 보기
        m_cGraphCtrl.SetYLastDataInfo(m_nCurrentID,false,false,false);

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //기타 가격 차트 속성
        //최대최소 표시 숨기기
        m_cGraphCtrl.SetShowPriceMaxMin(m_nCurrentID,false);

        //주가우선 스케일 FALSE
        m_cGraphCtrl.ChangeFUNCToolType(ENFUNCToolType.GE_FUNCTOOL_PRICE_YSCALE,0);

        //가격차트 그라데이션 설정
        m_cGraphCtrl.SetPriceGradation(m_nCurrentID, false);
        m_cGraphCtrl.SetGradientDirectType(m_nCurrentID,ENGradationDirectType.GE_GRADATION_DIRECT_NONE);

        //주가차트 최우선 표시 해제
        m_cGraphCtrl.SetPriceTopMost(m_nCurrentID,false);

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //중심선 지우기
        m_cGraphCtrl.SetHorzBaseLine(m_nCurrentID, GlobalDefines.GD_NAVALUE_DOUBLE,0, ENLineStyleType.PS_NULL,1,false);
        m_cGraphCtrl.SetVertBaseLine(m_nCurrentID, GlobalDefines.GD_NAVALUE_DOUBLE,0,ENLineStyleType.PS_NULL,1,true,false);

        //Zero위치 Center
        m_cGraphCtrl.SetZeroPosCenter(m_nCurrentID,false);

        //BaseLine 제거
        m_cGraphCtrl.SetRiseFallBaseType(m_nCurrentID, ENBaseLineType.GE_BASELINE_NONE);

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //기능,동작 초기화
        //확대,축소/좌우 이동 숨기기
        m_cGraphCtrl.SetEnableExp_MoveBtns(m_nCurrentID, false);
        //Disable Event초기화
        m_cGraphCtrl.InitDisableChartEvent(m_nCurrentID);

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //타이틀,라벨
        //데이터값 텍스트 숨기기
        m_cGraphCtrl.SetShowLabelText(m_nCurrentID, false);
        m_cGraphCtrl.SetShowSubLabelText(m_nCurrentID, false);

        m_cGraphCtrl.SetSubLabelTextIndex(m_nCurrentID, -1);

        //타이틀 보이기 (-1 : 감추기, 0 : 이름보이기 ... )
        m_cGraphCtrl.SetTitleDspType(m_nCurrentID, ENTitleDspType.GE_TITLE_DISPLAY_NAME);

        //LabelTextXPos
        m_cGraphCtrl.SetPrice_PropertyInfo(m_nCurrentID, 21, ENLabelTextXPos.GE_LABEL_TEXT_XPOS_LEFT.GetIndexValue());
        //LabelTextYPos
        m_cGraphCtrl.SetPrice_PropertyInfo(m_nCurrentID, 22, ENLabelTextYPos.GE_LABEL_TEXT_YPOS_UP.GetIndexValue());

        //Data Sorting
        m_cGraphCtrl.SetPieDataSorting(m_nCurrentID, true);

        //2019/04/18 - 수치최소최소비율(설정안하면 N/A,로 전체 표현 - 겹칠수 있다)
        m_cGraphCtrl.SetPieLabelDataLimit(m_nCurrentID,GlobalDefines.GD_NAVALUE_DOUBLE);

//        //Text표시위치(현재 작업중) - 0:Right,1:Left,2:Up,3:Dn
//        m_cGraphCtrl.SetPrice_PropertyInfo(m_nCurrentID, 21, 0);

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //Font초기화
        m_cGraphCtrl.SetBlockFontWeight(m_nCurrentID,GlobalDefines.FW_NORMAL);
        m_cGraphCtrl.SetBlockFontItalic(m_nCurrentID,false);
//        m_cGraphCtrl.SetBlockTypeFace(m_nCurrentID,null);
        m_cGraphCtrl.SetBlockFontName(m_nCurrentID, "SANS_SERIF");
//        m_cGraphCtrl.SetBlockFontName(m_nCurrentID,GlobalDefines.GD_DEFAULT_FONT_NORMAL);

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //그룹 정보 초기화
        m_cGraphCtrl.SetGradientPosType(m_nCurrentID, ENGradationPosType.GE_GRADATION_POS_NONE);
        m_cGraphCtrl.SetGroupDataCount(m_nCurrentID,0);
        for(int i=0;i<GlobalDefines.GD_MAX_GROUP_DATA;i++)
        {
            m_cGraphCtrl.SetGroupBodyFill(m_nCurrentID, i,true);
            m_cGraphCtrl.SetGroupBodyColor(m_nCurrentID, i,Color.rgb(34,76,126));
            m_cGraphCtrl.SetGroupLineColor(m_nCurrentID, i,Color.rgb(34,76,126));
            m_cGraphCtrl.SetGroupTextColor(m_nCurrentID, i,ColorNames.GC_Black);
            m_cGraphCtrl.SetGradientPosColor(m_nCurrentID, i,ColorNames.GC_White);
        }

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //기타 효과,속성 초기화
        //투명도 설정(불투명)
        m_cGraphCtrl.SetAlphaValue(m_nCurrentID,255);

        //봉간격
        m_cGraphCtrl.SetBongMargin(m_nCurrentID,(int)m_fDeviceDPIWeight);

        if(m_nHandlerStatus != STOP)
        {
            m_nHandlerStatus = STOP;
            handler.removeMessages(0);
        }
    }

    /*
     *********************
     **
     ** Button Event Functions
     **
     *********************/
    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.Btn_TitleBar_Back:
                getActivity().finish();
                getActivity().overridePendingTransition(R.anim.fadein, R.anim.fadeout);
                break;
            case R.id.Btn_TitleBar_Config:
                Intent intent = new Intent(m_contFormGraph, ConfigFormGraphActivity.class);
                startActivity(intent);
                break;
        }
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
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT)
        {
            // 기기가 세로로 회전할때 할 작업
        }
    }

    /*
     *********************
     **
     ** ChartView & Ctrl에서 Event Functions
     **
     *********************/
    public void ChangeGraphData()
    {
        int m_nCurrentID = m_cGraphCtrl.GetCurrentChartID();
        if (m_nCurrentID == -1) return;

        ArrayList<ST_PACKET_COREDATA> cPacketList = new ArrayList<ST_PACKET_COREDATA>();

        ST_PACKET_COREDATA stPacketBasicDataP = null;

        String strXDate = "";
        String strYDate = "";

        String strData = "";
        String strData1 = "";
        String strData2 = "";

        if(m_enPriceKind != ENPriceKindType.GE_PRICE_KIND_TIME_CIRCLE && m_nMixGraphType != 4 && m_nMixGraphType != 6)
        {
            //조회일때만 데이터 초기화 삭제
            if(m_nQueryDataEvent == 0) m_cGraphCtrl.ClearAllChartData(m_nCurrentID, false);
        }

        int i=0;
        int nDataCount = 12;
        switch (m_enPriceKind)
        {
            case GE_PRICE_KIND_RISEFALL:
            case GE_PRICE_KIND_OSCBAR:
                if(m_enPriceKind == ENPriceKindType.GE_PRICE_KIND_RISEFALL)
                    nDataCount = 20;
                else
                    nDataCount = 12;
                for(i=0;i<nDataCount;i++)
                {
                    if(m_enPriceKind == ENPriceKindType.GE_PRICE_KIND_RISEFALL)
                        strXDate = String.format("%d년",i+1998);
                    else
                        strXDate = String.format("%d년",i+2006);
                    m_cGraphCtrl.AddUserXDTString(m_nCurrentID, strXDate);

                    stPacketBasicDataP = GlobalStructs.AllocPacketCore();

                    stPacketBasicDataP.dValueList[m_nCloseIndex] = GetRandomValue(100.0,-100.0);
                    //if(i == 3) stPacketBasicDataP.dValueList[m_nCloseIndex] = 0.0;

                    cPacketList.add(stPacketBasicDataP);
                }

                m_cGraphCtrl.ReceiveQuery(m_nCurrentID,"OSCBar","", 0, 0,0,cPacketList,0);
                if(m_bIsAttachFormGraph == true)
                {
                    m_cGraphCtrl.ResizeChartBlock(m_nCurrentID, ENBlockResizeType.GE_BLOCK_RESIZE_INIT);
                    m_cGraphCtrl.RedrawCurrentChart();
                }
                break;
            case GE_PRICE_KIND_STICKBAR:
                nDataCount = 12;
                for(i=0;i<nDataCount;i++)
                {
                    strXDate = String.format("%d월",i+1);
                    m_cGraphCtrl.AddUserXDTString(m_nCurrentID, strXDate);

                    stPacketBasicDataP = GlobalStructs.AllocPacketCore();

                    stPacketBasicDataP.dValueList[m_nCloseIndex] = GetRandomValue(100.0,0.0);

                    cPacketList.add(stPacketBasicDataP);
                }

                //2018/05/25 - 리얼시 전체 데이터 변경후 ViewSize유지 하기 위해서
                //m_cGraphCtrl.ReceiveQuery(m_nCurrentID,"StickBar","", 2, 0,0,cPacketList,0);
                m_cGraphCtrl.ReceiveQuery(m_nCurrentID,"StickBar","", 2, 0,0,cPacketList,0);
                if(m_bIsAttachFormGraph == true)
                {
                    m_cGraphCtrl.ResizeChartBlock(m_nCurrentID, ENBlockResizeType.GE_BLOCK_RESIZE_INIT);
                    m_cGraphCtrl.RedrawCurrentChart();
                }
                break;
            case GE_PRICE_KIND_2SIDEBAR:
                if(m_nMixGraphType == 4)
                {
                    //한 값만 Update처리(-1이면 마지막값),주의:0부터시작
                    int nUpdateIndex = -1;
                    nUpdateIndex = (int)(GetRandomValue(12.0, 0.0));
                    if(nUpdateIndex == 12) nUpdateIndex = 11;

                    m_cGraphCtrl.UpdateUserPrcValue(m_nCurrentID,GetRandomValue(0.0, -10000.0),ENPacketDataType.GE_PACKET_CORE_LOW,nUpdateIndex);
                    m_cGraphCtrl.UpdateUserPrcValue(m_nCurrentID,GetRandomValue(10000.0, 0.0),ENPacketDataType.GE_PACKET_CORE_HIGH,nUpdateIndex);

                    m_cGraphCtrl.UpdateUserDataValue(m_nCurrentID,"재고율",0,GetRandomValue(3000.0, 100.0),ENPacketDataType.GE_PACKET_CORE_CLOSE,nUpdateIndex);

                    m_cGraphCtrl.RedrawCurrentChart();
                }
                else
                {
                    nDataCount = 12;
                    for (i = 0; i < nDataCount; i++)
                    {
                        strXDate = String.format("%d년", i + 2006);
                        m_cGraphCtrl.AddUserXDTString(m_nCurrentID, strXDate);

                        stPacketBasicDataP = GlobalStructs.AllocPacketCore();

                        stPacketBasicDataP.dValueList[m_nLowIndex] = GetRandomValue(0.0, -10000.0);
                        stPacketBasicDataP.dValueList[m_nHighIndex] = GetRandomValue(10000.0, 0.0);

                        cPacketList.add(stPacketBasicDataP);
                    }

                    //2018/05/25 - 리얼시 전체 데이터 변경후 ViewSize유지 하기 위해서
                    m_cGraphCtrl.ReceiveQuery(m_nCurrentID, "2SideBar", "", 0, 0, 0, cPacketList,0);
                    if(m_bIsAttachFormGraph == true)
                    {
                        m_cGraphCtrl.ResizeChartBlock(m_nCurrentID, ENBlockResizeType.GE_BLOCK_RESIZE_INIT);
                        m_cGraphCtrl.RedrawCurrentChart();
                    }
                }
                break;
            case GE_PRICE_KIND_STACKBAR:
            case GE_PRICE_KIND_GROUPBAR:
                if(m_nMixGraphType == 6)
                {
                    //한 값만 Update처리(-1이면 마지막값),주의:0부터시작
                    int nUpdateIndex = -1;
                    nUpdateIndex = (int)(GetRandomValue(12.0, 0.0));
                    if(nUpdateIndex == 12) nUpdateIndex = 11;

                    stPacketBasicDataP = GlobalStructs.AllocPacketCore();

                    stPacketBasicDataP.dValueList[m_nOpenIndex] = GetRandomValue(30000.0, 1000.0);
                    stPacketBasicDataP.dValueList[m_nHighIndex] = GetRandomValue(30000.0, 1000.0);
                    stPacketBasicDataP.dValueList[m_nLowIndex] = GetRandomValue(30000.0, 1000.0);

                    m_cGraphCtrl.UpdateUserPrcBasic(m_nCurrentID,stPacketBasicDataP,nUpdateIndex);

                    m_cGraphCtrl.UpdateUserDataValue(m_nCurrentID,"재고율",0,GetRandomValue(3000.0, 100.0),ENPacketDataType.GE_PACKET_CORE_CLOSE,nUpdateIndex);

                    m_cGraphCtrl.RedrawCurrentChart();
                }
                else
                {
                    nDataCount = 5;
                    for (i = 0; i < nDataCount; i++)
                    {
                        strXDate = String.format("%d년", i + 2014);
                        m_cGraphCtrl.AddUserXDTString(m_nCurrentID, strXDate);

                        stPacketBasicDataP = GlobalStructs.AllocPacketCore();

                        if (m_enPriceKind == ENPriceKindType.GE_PRICE_KIND_STACKBAR)
                        {
                            stPacketBasicDataP.dValueList[m_nOpenIndex] = GetRandomValue(30000.0, 1000.0);
                            stPacketBasicDataP.dValueList[m_nHighIndex] = GetRandomValue(30000.0, 1000.0);
                            stPacketBasicDataP.dValueList[m_nLowIndex] = GetRandomValue(30000.0, 1000.0);
                        }
                        else
                        {
                            //Update GroupBar일때
                            /*stPacketBasicDataP.dValueList[m_nOpenIndex] = GetRandomValue(3000.0, -1000.0);
                            stPacketBasicDataP.dValueList[m_nHighIndex] = GetRandomValue(3000.0, -3000.0);
                            stPacketBasicDataP.dValueList[m_nLowIndex] = GetRandomValue(3000.0, -2000.0);*/

                            stPacketBasicDataP.dValueList[m_nOpenIndex] = GetRandomValue(3000.0, 1000.0);
                            stPacketBasicDataP.dValueList[m_nHighIndex] = GetRandomValue(3000.0, 1000.0);
                            stPacketBasicDataP.dValueList[m_nLowIndex] = GetRandomValue(3000.0, 1000.0);
                        }

                        cPacketList.add(stPacketBasicDataP);
                    }

                    //2018/05/25 - 리얼시 전체 데이터 변경후 ViewSize유지 하기 위해서
                    m_cGraphCtrl.ReceiveQuery(m_nCurrentID, "StackBar", "", 0, 0, 0, cPacketList,0);
                    if(m_bIsAttachFormGraph == true)
                    {
                        m_cGraphCtrl.ResizeChartBlock(m_nCurrentID, ENBlockResizeType.GE_BLOCK_RESIZE_INIT);
                        m_cGraphCtrl.RedrawCurrentChart();
                    }
                }
                break;
            case GE_PRICE_KIND_HORZ_BAR:
            case GE_PRICE_KIND_HORZ_OSCBAR:
            case GE_PRICE_KIND_HORZ_2SIDEBAR:
            case GE_PRICE_KIND_HORZ_STACKBAR:

                //Update
                if(m_nQueryDataEvent == 2)
                {
                    //한 값만 Update처리(-1이면 마지막값),주의:0부터시작
                    int nUpdateIndex = -1;
                    nUpdateIndex = (int)(GetRandomValue(12.0, 0.0));
                    if(nUpdateIndex == 12) nUpdateIndex = 11;

                    stPacketBasicDataP = GlobalStructs.AllocPacketCore();

                    if(m_enPriceKind == ENPriceKindType.GE_PRICE_KIND_HORZ_2SIDEBAR)
                    {
                        stPacketBasicDataP.dValueList[m_nLowIndex] = GetRandomValue(0.0, -1000000.0);
                        stPacketBasicDataP.dValueList[m_nHighIndex] = GetRandomValue(1000000.0, 0.0);
                    }

                    m_cGraphCtrl.UpdateUserPrcBasic(m_nCurrentID,stPacketBasicDataP,nUpdateIndex);
                    m_cGraphCtrl.RedrawCurrentChart();
                }
                else
                {
                    nDataCount = 12;

                    for (i = 0; i < nDataCount; i++) {
                        if (m_enPriceKind == ENPriceKindType.GE_PRICE_KIND_HORZ_BAR ||
                                m_enPriceKind == ENPriceKindType.GE_PRICE_KIND_HORZ_2SIDEBAR ||
                                m_enPriceKind == ENPriceKindType.GE_PRICE_KIND_HORZ_STACKBAR)
                            strYDate = GlobalUtils.GetEnglishNameOfMonth(1 + i);
                        else
                            strYDate = String.format("%d월", 1 + i);
                        m_cGraphCtrl.AddUserYDTString(m_nCurrentID, strYDate);

                        stPacketBasicDataP = GlobalStructs.AllocPacketCore();

                        if (m_enPriceKind == ENPriceKindType.GE_PRICE_KIND_HORZ_BAR)
                        {
                            stPacketBasicDataP.dValueList[m_nCloseIndex] = GetRandomValue(0.0, 1000.0);
                            stPacketBasicDataP.dValueList[m_nVolumeIndex] = GetRandomValue(500.0, 2000.0);
                        }
                        else if (m_enPriceKind == ENPriceKindType.GE_PRICE_KIND_HORZ_OSCBAR)
                        {
                            stPacketBasicDataP.dValueList[m_nCloseIndex] = GetRandomValue(100.0, -100.0);
                            stPacketBasicDataP.dValueList[m_nVolumeIndex] = GetRandomValue(100.0, -100.0);
                        }
                        else if (m_enPriceKind == ENPriceKindType.GE_PRICE_KIND_HORZ_2SIDEBAR)
                        {
                            stPacketBasicDataP.dValueList[m_nLowIndex] = GetRandomValue(0.0, -1000000.0);
                            stPacketBasicDataP.dValueList[m_nHighIndex] = GetRandomValue(1000000.0, 0.0);
                        }
                        else if (m_enPriceKind == ENPriceKindType.GE_PRICE_KIND_HORZ_STACKBAR)
                        {
                            stPacketBasicDataP.dValueList[m_nOpenIndex] = GetRandomValue(10000.0, 7000.0);
                            stPacketBasicDataP.dValueList[m_nLowIndex] = GetRandomValue(6000.0, 4000.0);
                            stPacketBasicDataP.dValueList[m_nHighIndex] = GetRandomValue(3000.0, 1000.0);
                        }

                        cPacketList.add(stPacketBasicDataP);
                    }

                    m_cGraphCtrl.ReceiveQuery(m_nCurrentID,"HorizontalBar","", 0, 0,0,cPacketList,0);
                    if(m_bIsAttachFormGraph == true)
                    {
                        m_cGraphCtrl.ResizeChartBlock(m_nCurrentID, ENBlockResizeType.GE_BLOCK_RESIZE_INIT);
                        m_cGraphCtrl.RedrawCurrentChart();
                    }
                }
                break;
            case GE_PRICE_KIND_PIE:
            case GE_PRICE_KIND_DONUT:
                nDataCount = 8;

                for(i=0;i<nDataCount;i++)
                {
                    stPacketBasicDataP = GlobalStructs.AllocPacketCore();

                    stPacketBasicDataP.dValueList[m_nCloseIndex] = GetRandomValue(1000.0, 100.0);
                    cPacketList.add(stPacketBasicDataP);
                }

                m_cGraphCtrl.AddUserXDTString(m_nCurrentID, "아르바이트");
                m_cGraphCtrl.AddUserXDTString(m_nCurrentID, "임원");
                m_cGraphCtrl.AddUserXDTString(m_nCurrentID, "관리직");
                m_cGraphCtrl.AddUserXDTString(m_nCurrentID, "사무직");
                m_cGraphCtrl.AddUserXDTString(m_nCurrentID, "생산직");
                m_cGraphCtrl.AddUserXDTString(m_nCurrentID, "계약직");
                m_cGraphCtrl.AddUserXDTString(m_nCurrentID, "파견직");
                m_cGraphCtrl.AddUserXDTString(m_nCurrentID, "프리랜서");

                m_cGraphCtrl.ReceiveQuery(m_nCurrentID,"Pie","", 0, 0,0,cPacketList,0);
                if(m_bIsAttachFormGraph == true)
                {
                    m_cGraphCtrl.ResizeChartBlock(m_nCurrentID, ENBlockResizeType.GE_BLOCK_RESIZE_INIT);
                    m_cGraphCtrl.RedrawCurrentChart();
                }
                break;
            case GE_PRICE_KIND_MOUNTAIN:
                /////////////////////////////////////////////////////////////////////////////////////////////////////////
                //데이터 직접 처리
                nDataCount = 12;

                for(i=0;i<nDataCount;i++)
                {
                    strXDate = String.format("%d월",1+i);
                    m_cGraphCtrl.AddUserXDTString(m_nCurrentID, strXDate);

                    stPacketBasicDataP = GlobalStructs.AllocPacketCore();

                    stPacketBasicDataP.dValueList[m_nCloseIndex] = GetRandomValue(10000.0, 1000.0);
                    cPacketList.add(stPacketBasicDataP);
                }

                m_cGraphCtrl.ReceiveQuery(m_nCurrentID,"Mountain","", 0, 0,0,cPacketList,0);
                if(m_bIsAttachFormGraph == true)
                {
                    m_cGraphCtrl.ResizeChartBlock(m_nCurrentID, ENBlockResizeType.GE_BLOCK_RESIZE_INIT);
                    m_cGraphCtrl.RedrawCurrentChart();
                }
                break;
            case GE_PRICE_KIND_RADAR:
                nDataCount = 11;

                for(i=0;i<nDataCount;i++)
                {
                    stPacketBasicDataP = GlobalStructs.AllocPacketCore();

                    stPacketBasicDataP.dValueList[m_nOpenIndex] = GetRandomValue(100.0, 0.0);
                    stPacketBasicDataP.dValueList[m_nHighIndex] = GetRandomValue(100.0, 0.0);
                    stPacketBasicDataP.dValueList[m_nLowIndex] = GetRandomValue(100.0, 0.0);
                    cPacketList.add(stPacketBasicDataP);

                    strXDate = String.format("%d년",i+2006);
                    m_cGraphCtrl.AddUserXDTString(m_nCurrentID, strXDate);
                }

                m_cGraphCtrl.ReceiveQuery(m_nCurrentID,"Radar","", 0, 0,0,cPacketList,0);
                if(m_bIsAttachFormGraph == true)
                {
                    m_cGraphCtrl.ResizeChartBlock(m_nCurrentID, ENBlockResizeType.GE_BLOCK_RESIZE_INIT);
                    m_cGraphCtrl.RedrawCurrentChart();
                }
                break;
            case GE_PRICE_KIND_BUBBLE_SCATTER:
                ////////////////////////////////////////////////////////////////////////////////////////////////////////////
                //데이터 입력
                nDataCount = 30;

                for(i=0;i<nDataCount;i++)
                {
                    stPacketBasicDataP = GlobalStructs.AllocPacketCore();

                    //단색으로 나올려면 막을것(하락색만 적용됨)
                    stPacketBasicDataP.dValueList[m_nOpenIndex] = GetRandomValue(5000.0,-3000.0);

                    stPacketBasicDataP.dValueList[m_nCloseIndex] = GetRandomValue(5000.0,-3000.0);
                    stPacketBasicDataP.dValueList[m_nVolumeIndex] = GetRandomValue(200.0,-200.0);

                    if(i%10==0)
                        stPacketBasicDataP.dValueList[m_nDateTimeIndex] = GetRandomValue(10000.0,10.0)*5;
                    else if(i%5==0)
                        stPacketBasicDataP.dValueList[m_nDateTimeIndex] = GetRandomValue(10000.0,10.0)*3;
                    else
                        stPacketBasicDataP.dValueList[m_nDateTimeIndex] = GetRandomValue(10000.0,10.0);

                    cPacketList.add(stPacketBasicDataP);
                }

                m_cGraphCtrl.ReceiveQuery(m_nCurrentID,"BubbleScatter","", 0, 0,0,cPacketList,0);
                if(m_bIsAttachFormGraph == true)
                {
                    m_cGraphCtrl.ResizeChartBlock(m_nCurrentID, ENBlockResizeType.GE_BLOCK_RESIZE_INIT);
                    m_cGraphCtrl.RedrawCurrentChart();
                }
                break;
            case GE_PRICE_KIND_TIME_CIRCLE:
                m_nTimeCircle++;
                if(m_nTimeCircle >= 24) m_nTimeCircle = 0;
                String strTitle = String.format("%02d:00",m_nTimeCircle);
                m_cGraphCtrl.SetBaseCodeName(m_nCurrentID,"HKEX-" + strTitle);
                m_cGraphCtrl.Set3DDepthRatio(m_nCurrentID,GetTimeToDegree(strTitle,false));
                m_cGraphCtrl.RedrawCurrentChart();
                break;
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

    Handler handler = new Handler()
    {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what == 0)
            {
                ChangeGraphData();

                if(m_nHandlerStatus == PLAY)
                    handler.sendEmptyMessageDelayed(0, DELAY);
                else if(m_nHandlerStatus == TIME_PLAY)
                    handler.sendEmptyMessageDelayed(0, TIME_DELAY);
            }
        }
    };

    //2016/12/09 강민석 - Time To Degree
    private double GetTimeToDegree(String strTime,boolean bIs12Hour)
    {
        if (strTime.indexOf(':') == -1 && strTime.length() < 4) return GlobalDefines.GD_NAVALUE_DOUBLE;
        double dDegree = 0;
        String strHour, strMin;
        int nHour, nMin;
        int nIndex = strTime.indexOf(':');
        if (nIndex == -1)
        {
            strHour = strTime.substring(0, 2);
            strMin = strTime.substring(2);
        }
        else
        {
            strHour = strTime.substring(0, nIndex);
            strMin = strTime.substring(nIndex + 1);
        }

        nHour = Integer.parseInt(strHour);
        nMin = Integer.parseInt(strMin);

        if(bIs12Hour == true)
        {
            //TimeCircle이 12시간 표기 일대 360도 이기떄문에 1H = 30도, 1M = 0.5도 로 계산
            if (nHour >= 12) nHour -= 12;

            dDegree += nHour * 30;
            dDegree += nMin * 0.5;
        }
        else
        {
            //TimeCircle이 24시간 표기 일대 360도 이기떄문에 1H = 15도, 1M = 0.25도 로 계산
            dDegree += nHour * 15;
            dDegree += nMin * 0.25;
        }

        return dDegree;
    }

}
