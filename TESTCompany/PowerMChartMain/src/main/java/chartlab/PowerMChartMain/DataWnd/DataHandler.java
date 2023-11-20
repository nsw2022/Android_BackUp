package chartlab.PowerMChartMain.DataWnd;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.widget.Toast;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.StringTokenizer;

import chartlab.PowerMChartApp.FrameWnd.ChartCtrl;
import chartlab.PowerMChartApp.Dialog.ChartSupport.ProgressDlg;
import chartlab.PowerMChartApp.Util.FragmentAppMain;
import chartlab.PowerMChartEngine.KernelCore.GlobalDefines;
import chartlab.PowerMChartEngine.KernelCore.GlobalDirFile;
import chartlab.PowerMChartEngine.KernelCore.GlobalEnums.ENBaseLineType;
import chartlab.PowerMChartEngine.KernelCore.GlobalEnums.ENFragmentFrameType;
import chartlab.PowerMChartEngine.KernelCore.GlobalEnums.ENPacketDataType;
import chartlab.PowerMChartEngine.KernelCore.GlobalEnums.ENPacketPeriodType;
import chartlab.PowerMChartEngine.KernelCore.GlobalEnums.ENRequestQueryType;
import chartlab.PowerMChartEngine.KernelCore.GlobalEnums.ENMarketCategoryType;
import chartlab.PowerMChartEngine.KernelCore.GlobalEnums.ENMarketSatusType;
import chartlab.PowerMChartEngine.KernelCore.GlobalEnums.ENChartOCXEventType;
import chartlab.PowerMChartEngine.KernelCore.GlobalStructs;
import chartlab.PowerMChartEngine.KernelCore.GlobalStructs.ST_PACKET_COREDATA;
import chartlab.PowerMChartEngine.KernelCore.GlobalUtils;
import chartlab.PowerMChartEngine.Util.CSVManager;
import chartlab.PowerMChartEngine.Util.PGLog;

public class DataHandler
{
    //Real Data Test
    private boolean m_bRealTimer = false;
    private int m_nRealDataIndex = -1;
    private String m_strRealDataCode = "";
    private int m_nQueryStartIndex = 0;
    private int m_nQueryDataIndex = 200;
    private int m_nRealTimerDelay = 200;
    private ArrayList<ST_PACKET_COREDATA> m_cPacketList = new ArrayList<ST_PACKET_COREDATA>();

    ////////////////////////////////////////////////////////////////////////////
    // Define Values
    private final int GD_HANDLER_REAL_PROC = 0;
    private final int GD_HANDLER_RECEIVE_REAL = 0;
    private final int GD_MAX_REAL_COUNT = 100;
    private final String GD_CANCEL_RQ_ALL = "ALL";
    private final static int GD_MAX_REQUEST_CTRL_ID		= 126;

    private Context m_contParent = null;
    private ChartCtrl m_cChartCtrl = null;

    private ENFragmentFrameType m_enFragmentFrame = ENFragmentFrameType.GE_FRAGMENT_FRAME_NONE;

    private double m_dLoginDate = GlobalDefines.GD_NAVALUE_DOUBLE;
    private int m_nRequestData = -1;

    private int m_nDateTimeIndex = -1;
    private int m_nCloseIndex = -1;
    private int m_nOpenIndex = -1;
    private int m_nHighIndex = -1;
    private int m_nLowIndex = -1;
    private int m_nVolumeIndex = -1;
    private int m_nValueIndex = -1;

    private int m_nRequestNum = 0;
    public GlobalStructs.ST_COMM_REQUEST m_stNowCommRqInfo = null;

    private ArrayList<RequestInfo> m_queRequest = null;

    private HashMap<Integer, Integer> m_mapRQWaiting = null;
    private ArrayList<GlobalStructs.ST_REAL_RECV_DATA_NUM> m_lstSendBuffer = null;

    public ProgressDlg m_dlgProgress = null;

    ////////////////////////////////////////////////////////////////////////////
    // ++오름스탁 메세지 정보
    private final static int MSG_PROGRESSDLG_SHOW       = 10;
    private final static int MSG_RDS_REAL               = 11;
    private final static int MSG_RDS_DISCONNECTED       = 12;

    private final static int MSG_CTS_CHT_QUERY          = 21;
    private final static int MSG_CTS_CHT_DELAYED        = 22;
    //--오름스탁 메세지 정보

    public DataHandler(Context context, ChartCtrl cChartCtrl, ENFragmentFrameType enFragmentFrame){

        m_contParent = context;

        m_cChartCtrl = cChartCtrl;
        m_enFragmentFrame = enFragmentFrame;
    }

    public void InitDataHandler(int nRealtimeDelay)
    {
        m_nRealTimerDelay = nRealtimeDelay;

        String strToday = GlobalUtils.GetToday();
        m_dLoginDate = Double.parseDouble(strToday);

        m_cChartCtrl.SetLoginToDate((int)m_dLoginDate);

        m_dlgProgress = new ProgressDlg(m_contParent);

        m_queRequest = new ArrayList<RequestInfo>();
        m_lstSendBuffer = new ArrayList<GlobalStructs.ST_REAL_RECV_DATA_NUM>();

        //속대 개선을 위해서 받아서 재사용
        m_nDateTimeIndex = ENPacketDataType.GE_PACKET_CORE_DATETIME.GetIndexValue();
        m_nCloseIndex = ENPacketDataType.GE_PACKET_CORE_CLOSE.GetIndexValue();
        m_nOpenIndex = ENPacketDataType.GE_PACKET_CORE_OPEN.GetIndexValue();
        m_nHighIndex = ENPacketDataType.GE_PACKET_CORE_HIGH.GetIndexValue();
        m_nLowIndex = ENPacketDataType.GE_PACKET_CORE_LOW.GetIndexValue();
        m_nVolumeIndex = ENPacketDataType.GE_PACKET_CORE_VOLUME.GetIndexValue();
        m_nValueIndex = ENPacketDataType.GE_PACKET_CORE_VALUE.GetIndexValue();

        m_nRequestNum = 0;
        m_stNowCommRqInfo = GlobalStructs.AllocCommRequest();
        m_mapRQWaiting = new HashMap<Integer, Integer>();

        Message msgReal = new Message();
        msgReal.what = GD_HANDLER_REAL_PROC;
        //HandlerRealTimer.sendMessageDelayed(msgReal, 3000);
        HandlerRealProc.sendMessageDelayed(msgReal, m_nRealTimerDelay);
    }

    public void CloseDataHandler() {

        InitSendBufferList(GD_CANCEL_RQ_ALL);

        int nFrameSize = m_cChartCtrl.GetChartFrameSize();
        int nID = 0;
        for (int i = 0; i < nFrameSize; i++)
        {
            nID = m_cChartCtrl.GetChartID(i);
            CancelAllRequest(nID);
        }

        if(m_dlgProgress != null && m_dlgProgress.isShowing())
            m_dlgProgress.dismiss();

        HandlerRealProc.removeMessages(GD_HANDLER_REAL_PROC);
        HandlerReceiveReal.removeMessages(GD_HANDLER_RECEIVE_REAL);
    }

    public int RequestBaseData(int nID, String szCode, String szName,double dStartDT,double dEndDT,int nCount, int nMarket,
                                int nPeriod,  int nPrdVal, int nRealInfo,int nGubun,int nRQType)
    {
        if(szCode.isEmpty() == true) return -1;

        //주의:조회요청후 리얼이 False로 떨어지면 무한루프 도는 현상 방지위해서
        m_nRequestData = -1;
        m_strRealDataCode = "";

        RequestInfo pRQInfo = null;
        if(nRQType == ENRequestQueryType.GE_REQUEST_QUERY_ALL.GetIndexValue()) {
            m_stNowCommRqInfo.strCode = szCode;
            m_stNowCommRqInfo.nMarket = GlobalUtils.GetIntValue(GetCodeMarket(szCode));
            //코넥스 별도 처리
            //if(m_stNowCommRqInfo.nMarket == ENMarketCategoryType.PNX_CODE_TYPE_KOSDAQ_CODE.GetIndexValue())
            //{ m_stNowCommRqInfo.nMarketGubun = GlobalUtils.GetIntValue(GetCodeMarketGubun(szCode)) }

            CancelAllRequest(nID);
            m_nQueryStartIndex = 0;

            m_cChartCtrl.SetQueryEvent(nID, true);
            //m_cChartCtrl.ClearHeadQueryData(nID);
        }

        boolean bContinue = false;
        if(nRQType == 1 || nRQType == ENRequestQueryType.GE_REQUEST_QUERY_OVERLAP_CONT.GetIndexValue() || nRQType == ENRequestQueryType.GE_REQUEST_QUERY_MARKET_CONT.GetIndexValue())
        {
            bContinue = true;
            pRQInfo = GetRequestInfoOfName(nID,szName);
        }

        //int nDsoID = -1;
        //int nAutoID = GetMarketAutoID(nMarket,m_nMenuIndex);

        //int nCtrlID = -1;
        //String strAutoCode = GetAUTOCode(GetOriginalCode(szCode),nCtrlID,nMarket,nGubun);
        String strAutoCode = "";
//        var nIsConnectData = 0
//        var nIsModifyData = (m_cChartCtrl.IsModifiedPrcData(nID) == true  ? 1 : 0)
//        var nRQCount = nCount
//        var nEndTime = 23595999
//        var nEndDate = -1
//        var nStartTime = 0
//        var nStartDate = -1

        //중간에 수많은 입력 값 셋팅들
        //증권사마다 형식이 다르니깐 스킵
        //............................

        String strContCode = "";
//        String strMarket = "";
        String strRealKey = "";
//        String strTRCode = "";

        StartWaiting(m_nRequestNum,nID);
        if(pRQInfo != null && nRealInfo == 1 &&
                (nRQType == 1 || nRQType == ENRequestQueryType.GE_REQUEST_QUERY_OVERLAP_CONT.GetIndexValue()))
        {
            pRQInfo.bIsAddRQ = true;
            pRQInfo.nRequestID = m_nRequestNum;
            pRQInfo.enRQQuery = ENRequestQueryType.GetEnumValue(nRQType);
        }
        else
        {
            pRQInfo = new RequestInfo();

            pRQInfo.nChartID = nID;
            pRQInfo.strCode = szCode;
            //pRQInfo.strAutoCode = strAutoCode;
            pRQInfo.strAutoCode = szCode;
            pRQInfo.strORGCode = szCode;
            pRQInfo.strName = szName;
            pRQInfo.nMarket = nMarket;

            pRQInfo.dStartDT = dStartDT;
            pRQInfo.dEndDT = dEndDT;

            pRQInfo.nPeriod = nPeriod;
            pRQInfo.nPrdValue = nPrdVal;
            pRQInfo.nRQCount = nCount;

            pRQInfo.nRealInfo = nRealInfo;
            pRQInfo.nRequestID = nID;
            pRQInfo.strTRQuery = "";
            pRQInfo.strTRReal = "";
            pRQInfo.bIsAddRQ = (boolean)(nRQType == 1);
            pRQInfo.enRQQuery = ENRequestQueryType.GetEnumValue(nRQType);

            m_queRequest.add(pRQInfo);
        }

        if (m_nRequestNum > GD_MAX_REQUEST_CTRL_ID)   { m_nRequestNum = 0; }
        else                       { m_nRequestNum += 1; }

        ////////////////////////////////////////////////////////////////////////////////////////////////////////
        //++ 임시로 데이터 넣는 부분 - 원래 통신로직을 통해서 요청해야 하고 데이터를 서버에서 받아서 처리해야 함
        double dPrevClose = 0.0;
        if(m_enFragmentFrame != ENFragmentFrameType.GE_FRAGMENT_FRAME_COMPARE)
        {
            if(nRQType == 0)
            {
                m_cPacketList = ReadCSVLocalData(szName,szCode,nPeriod,nPrdVal);
                if(m_cPacketList == null)
                {
                    GlobalUtils.ToastHandlerMessage(m_contParent, "데이터 파일을 로딩할수 없습니다");
                    return -1;
                }

                if(nPeriod == ENPacketPeriodType.GE_PACKET_PERIOD_DAILY.GetIndexValue())
                {
                    int nPacketSize = m_cPacketList.size();
                    if(nPacketSize > 2) {
                        dPrevClose = m_cPacketList.get(nPacketSize - 2).dValueList[ENPacketDataType.GE_PACKET_CORE_CLOSE.GetIndexValue()];
                        pRQInfo.dPrevClose = dPrevClose;
                        pRQInfo.dUpperValue = dPrevClose * 1.3;
                        pRQInfo.dLowerValue = dPrevClose * 0.7;
                    }
                }
            }
            else if(nRQType == 1)
            {
                if((m_nQueryStartIndex+m_nQueryDataIndex) >= m_cPacketList.size())
                {
                    GlobalUtils.ToastHandlerMessage(m_contParent, "Do not Exist More Data!");
                    return -1;
                }

                m_nQueryStartIndex += 200;
            }

            if(m_nQueryStartIndex >= m_cPacketList.size())
            {
                m_nQueryStartIndex = m_cPacketList.size();
                m_nRealDataIndex = -1;
                ReceiveQuery(nID,szCode,m_cPacketList,dPrevClose,pRQInfo.dUpperValue,pRQInfo.dLowerValue);
            }
            else
            {
                //for(int i=(m_nQueryStartIndex+m_nQueryDataIndex-1);i>=0;i--)
                ArrayList<ST_PACKET_COREDATA> cPacketList = new ArrayList<ST_PACKET_COREDATA>();
                for(int i=m_nQueryStartIndex;i<(m_nQueryStartIndex+m_nQueryDataIndex);i++)
                {
                    if(i >= m_cPacketList.size()) break;
                    cPacketList.add(m_cPacketList.get(i));
                }

                m_nRealDataIndex = m_nQueryStartIndex+m_nQueryDataIndex;
                ReceiveQuery(nID,szCode,cPacketList,dPrevClose,pRQInfo.dUpperValue,pRQInfo.dLowerValue);
                //-- 임시로 데이터 넣는 부분 - 원래 통신로직을 통해서 요청해야 하고 데이터를 서버에서 받아서 처리해야 함

                if(nRQType == 1)
                    m_cChartCtrl.GetFragment().GetFragmentMain().OnGetChartLinkEvent(ENChartOCXEventType.GE_OCX_EVENT_CHANGE_COUNT, nID, null,-1,"");
            }

            m_strRealDataCode = szCode;
        }
        else
        {
            ArrayList<ST_PACKET_COREDATA> cPacketList = ReadCSVLocalData(pRQInfo.strName,pRQInfo.strCode,pRQInfo.nPeriod,pRQInfo.nPrdValue);
            if(nRQType == 0 && nPeriod == ENPacketPeriodType.GE_PACKET_PERIOD_DAILY.GetIndexValue())
            {
                int nPacketSize = m_cPacketList.size();
                if(nPacketSize > 2) {
                    dPrevClose = m_cPacketList.get(nPacketSize - 2).dValueList[ENPacketDataType.GE_PACKET_CORE_CLOSE.GetIndexValue()];
                    pRQInfo.dPrevClose = dPrevClose;
                    pRQInfo.dUpperValue = dPrevClose * 1.3;
                    pRQInfo.dLowerValue = dPrevClose * 0.7;
                }
            }

            ReceiveQuery(nID,szCode,cPacketList,dPrevClose,pRQInfo.dUpperValue,pRQInfo.dLowerValue);
        }

        return m_nRequestNum;
    }

    private boolean ReceiveQuery(int nID,String strCode,ArrayList<ST_PACKET_COREDATA> cPacketList,double dPreClose,double dUpperPrice,double dLowerPrice)
    {
        if (cPacketList == null) return false;

        //let nFloatingPoint = 0;
        //서버에서 계산되어 보내짐
        //if(cdd.Market == 업종) nFloatingPoint = 2

        //원래 RequestNum같은 Unique한 값으로 ChartID를 받아와야 하는데 현재는
        //분할 차트가 없다고 가정한다(RequestNum를 서버에 보내고 그값을 받아와야함)
        //int nID = m_cChartCtrl.GetCurrentChartID()

        RequestInfo pRQInfo = GetRequestInfoOfCode(nID, strCode);
        if(pRQInfo == null) return false;

        //if(pRQInfo.nMarket == ENMarketCategoryType.GE_MARKET_CATE_STOCK.GetIndexValue())
        //	m_cChartCtrl.SetSubFloatPoint(pRQInfo.nChartID,0);

        if(pRQInfo.enRQQuery == ENRequestQueryType.GE_REQUEST_QUERY_ALL)
        {
            m_cChartCtrl.SetQueryEvent(pRQInfo.nChartID,true);
            m_cChartCtrl.SetMainQueryRecieved(pRQInfo.nChartID,true);

            m_cChartCtrl.SetChartBaseData(nID,ENBaseLineType.GE_BASELINE_PREV_CLOSE,dPreClose);
            m_cChartCtrl.SetChartBaseData(nID,ENBaseLineType.GE_BASELINE_HIGHEST,dUpperPrice);
            m_cChartCtrl.SetChartBaseData(nID,ENBaseLineType.GE_BASELINE_LOWEST,dLowerPrice);
        }

        int nMarketStatus = ENMarketSatusType.GE_MKTIME_DUR.GetIndexValue();
        m_cChartCtrl.ReceiveQuery(nID,pRQInfo.strCode,pRQInfo.strName,0,pRQInfo.nMarket,
                cPacketList,null,null,null,null,pRQInfo.enRQQuery.GetIndexValue(),nMarketStatus);

        //리얼시 상단 수치 변경
        if(pRQInfo.enRQQuery == ENRequestQueryType.GE_REQUEST_QUERY_ALL)
        {
            if(m_enFragmentFrame == ENFragmentFrameType.GE_FRAGMENT_FRAME_MULTI)
                m_cChartCtrl.GetFragment().GetFragmentMain().OnGetChartLinkEvent(ENChartOCXEventType.GE_OCX_EVENT_CURPRICE,nID,null,0,"");
        }

        //주의 - 연속일 경우 리얼을 다시 등록할 이유가 없다
        if(pRQInfo.enRQQuery == ENRequestQueryType.GE_REQUEST_QUERY_ALL && pRQInfo.nRealInfo == 1 && pRQInfo.strCode.isEmpty() == false && pRQInfo.bIsAddRQ == false)
            RegisterRealData(pRQInfo.strORGCode);

        //주의 - 리얼등록 조회일경우 - 등록/아니면 조회정보에서 제거
        if(pRQInfo.nRealInfo != 1) DeleteRequestInfo(pRQInfo.nRequestID, pRQInfo.nChartID);

        return true;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////
    // RequestQueue 관리
    private RequestInfo GetRequestInfoOfCode(int nID,String strCode) {
        RequestInfo pRQInfo = null;
        for(int i=0;i<m_queRequest.size();i++)
        {

            pRQInfo = m_queRequest.get(i);
            if(pRQInfo == null) continue;

            if(pRQInfo.strCode.isEmpty() == true) break;

            if(pRQInfo.nChartID == nID && (pRQInfo.strCode.equalsIgnoreCase(strCode) || pRQInfo.strORGCode.equalsIgnoreCase(strCode)))
                return pRQInfo;
        }

        return null;
    }

    private RequestInfo GetRequestInfoOfName(int nID,String strName) {
        RequestInfo pRQInfo = null;
        for(int i=0;i<m_queRequest.size();i++)
        {
            pRQInfo = m_queRequest.get(i);
            if(pRQInfo == null) continue;

            if(pRQInfo.strName.isEmpty() == true) break;

            //if(pRQInfo.strTRReal.isEmpty() == true) break;

            if(pRQInfo.nChartID == nID && pRQInfo.strName.equalsIgnoreCase(strName))
                return pRQInfo;
        }

        return null;
    }

    private RequestInfo GetRequestInfoOfRealKey(int nID,String strKey) {
        RequestInfo pRQInfo = null;
        for(int i=0;i<m_queRequest.size();i++)
        {
            pRQInfo = m_queRequest.get(i);
            if(pRQInfo == null) continue;
            if(pRQInfo.strCode.isEmpty() == true) break;

            if( pRQInfo.nChartID == nID && pRQInfo.strTRReal.equals(strKey) == true) return pRQInfo;
        }

        return null;
    }

    private RequestInfo GetRequestInfoOfRQType(int nID,int nRQType) {
        RequestInfo pRQInfo = null;
        for(int i=0;i<m_queRequest.size();i++)
        {
            pRQInfo = m_queRequest.get(i);
            if(pRQInfo == null) continue;
            if(pRQInfo.strCode.isEmpty() == true) break;

            if( pRQInfo.nChartID == nID && pRQInfo.enRQQuery.GetIndexValue() == nRQType) return pRQInfo;
        }

        return null;
    }

    private RequestInfo GetRequestInfoOfRQID(int nID,int nRQID) {
        RequestInfo pRQInfo = null;

        for(int i=0;i<m_queRequest.size();i++)
        {
            pRQInfo = m_queRequest.get(i);
            if(pRQInfo == null) continue;

            if( pRQInfo.nRequestID == nRQID && (pRQInfo.nChartID == nID || nID == -1))
                return pRQInfo;
        }

        return null;
    }

    public boolean CancelAllRequest(int nID)
    {
        return CancelRequest(nID,GD_CANCEL_RQ_ALL);
    }

    public boolean CancelRequest(int nID,String szKey) {
        if(m_queRequest.size() <= 0) return false;

        boolean bIDAllClear = false;
        if(szKey.equalsIgnoreCase(GD_CANCEL_RQ_ALL)) bIDAllClear = true;

        RequestInfo pRQInfo = null;
        boolean bEnableRealCancel = true;
        if(bIDAllClear == false)
        {
            pRQInfo = GetRequestInfoOfRealKey(nID,szKey);
            if(pRQInfo == null) bEnableRealCancel = false;
        }

        int nQueueSize = m_queRequest.size();
        int i = 0;
        ArrayList<String> strKeyLists = new ArrayList<String>();
        ArrayList<String> strCodeLists = new ArrayList<String>();
        for(i=nQueueSize-1;i>=0;i--)
        {
            pRQInfo = m_queRequest.get(i);

            strKeyLists.add(pRQInfo.strTRReal);
            strCodeLists.add(pRQInfo.strCode);
        }

        int j=0;
        int nSameCount = 0;
        int nKeyIndex = -1;
        for(i=nQueueSize-1;i>=0;i--)
        {
            nKeyIndex = i;
            nSameCount = 0;
            pRQInfo = m_queRequest.get(i);

            if(pRQInfo.nChartID != nID) continue;

            if(bIDAllClear || szKey.equals(pRQInfo.strTRReal))
            {
                // 실시간 해제. - 과거코드
                if(bEnableRealCancel == true)
                {
                    for(j=0;j<strKeyLists.size();j++)
                    {
                        if(strKeyLists.get(j).equals(pRQInfo.strTRReal) && strCodeLists.get(j).equals(pRQInfo.strCode))
                        {
                            nKeyIndex = j;
                            nSameCount++;
                        }
                    }

                    if(nSameCount <= 1)
                    {
                        UnRegisterRealData(pRQInfo.strORGCode);		//해당 종목 리얼 해제
                        InitSendBufferList(pRQInfo.strORGCode);
                    }
                }

                // 차트가 여러개인 경우 request가 사라져서 조회가 안되는 경우가 발생함

                EndWaiting(pRQInfo.nRequestID);
                m_queRequest.remove(i);

                strKeyLists.remove(nKeyIndex);
                strCodeLists.remove(nKeyIndex);
            }
        }
        return true;
    }

    private int InitSendBufferList(String strORGCode )
    {
        int nRemoveCount = 0;
        if (m_lstSendBuffer.size() <= 0 || strORGCode.isEmpty() == true) { return  -1; }

        if(strORGCode == GD_CANCEL_RQ_ALL)
        {
            nRemoveCount = m_lstSendBuffer.size();
            if (nRemoveCount > 0) { m_lstSendBuffer.clear(); }

            return nRemoveCount;
        }

        String strRealCode;
        for(int j=m_lstSendBuffer.size ()-1;j>=0;j--)
        {
            strRealCode = String.valueOf(m_lstSendBuffer.get(j).sCode);
            if(strORGCode.equals(strRealCode))
            {
                m_lstSendBuffer.remove(j);
                nRemoveCount += 1;
            }
        }

        return nRemoveCount;
    }

    private boolean DeleteRequestInfo(int nRQID) {
        int nIndex = -1;
        RequestInfo pRQInfo = null;
        for(int i=0;i<m_queRequest.size();i++)
        {
            pRQInfo = m_queRequest.get(i);

            if( pRQInfo.nRequestID == nRQID)
            {
                nIndex = i;
                break;
            }
        }

        if(nIndex < 0) return false;

        EndWaiting(pRQInfo.nRequestID);

        m_queRequest.remove(nIndex);

        return true;
    }

    private boolean DeleteRequestInfo(int nRQID, int nChartID)
    {
        int nIndex = -1;
        RequestInfo pRQInfo = null;

        for(int i=0;i<m_queRequest.size();i++)
        {
            pRQInfo = m_queRequest.get(i);

            if( pRQInfo.nRequestID == nRQID && pRQInfo.nChartID == nChartID)
            {
                nIndex = i;
                break;
            }
        }

        if(nIndex < 0) return false;

        EndWaiting(pRQInfo.nRequestID);

        m_queRequest.remove(nIndex);

        return true;
    }

    ////////////////////////////////////////////////////////////////////////////////////////
    //MTS 차트 리얼 등록,해지,재등록등
    private void RegisterRealData(String strCode) {

        //2020/03/10 - 리얼타임 등록 안함
        /*if(m_bRealTimer == false)
        {
            Message msgReal = new Message();
            msgReal.what = GD_HANDLER_RECEIVE_REAL;
            m_bRealTimer = true;
            HandlerReceiveReal.sendMessageDelayed(msgReal, m_nRealTimerDelay);
        }*/

        /*if(!m_fragmentParent.IsVewChart()){
            // 폼차트가 아닌 경우에만 RDS 등록
            if(CodeStd.IsContainsCodeStd(strCode)){
                RDSJisuReceiver.RemoveAllRDSJisu();
                RDSCheReceiver.RemoveAllRDSChe();
                RDSCheReceiver.AddRDSChe(strCode);
            }
            else if(UpjongStd.IsContainsUpjongStd(strCode)){
                RDSCheReceiver.RemoveAllRDSChe();
                RDSJisuReceiver.RemoveAllRDSJisu();
                RDSJisuReceiver.AddRDSJisu(strCode);
            }
            else{
                ;
            }

            PGLog.e(strCode +" 실시간 데이터 요청이 등록");
        }*/
    }

    private void ResetRealData(String strCode) {
        /*if(!m_fragmentParent.IsVewChart()){
            // 폼차트가 아닌 경우에만 RDS 등록
            if(CodeStd.IsContainsCodeStd(strCode)){
                RDSJisuReceiver.RemoveAllRDSJisu();
                RDSCheReceiver.RemoveAllRDSChe();
                RDSCheReceiver.AddRDSChe(strCode);
            }
            else if(UpjongStd.IsContainsUpjongStd(strCode)){
                RDSCheReceiver.RemoveAllRDSChe();
                RDSJisuReceiver.RemoveAllRDSJisu();
                RDSJisuReceiver.AddRDSJisu(strCode);
            }
            else{
                ;
            }

            PGLog.e(strCode +" 실시간 데이터 요청이 재 등록");
        }*/
    }

    private void UnRegisterRealData(String strCode)
    {
        HandlerReceiveReal.removeMessages(GD_HANDLER_RECEIVE_REAL);
        /*if(!m_fragmentParent.IsVewChart()){
            // 폼차트가 아닌 경우에만 RDS 해제
            if(CodeStd.IsContainsCodeStd(strCode)){
                RDSCheReceiver.RemoveRDSChe(strCode);
            }
            else if(UpjongStd.IsContainsUpjongStd(strCode)){
                RDSJisuReceiver.RemoveRDSJisu(strCode);
            }
            else{
                ;
            }

            PGLog.e(strCode +" 실시간 데이터 요청이 해제");
        }*/
    }

    @SuppressLint("HandlerLeak")
    private Handler HandlerRealProc = new Handler() {
        public void handleMessage(Message m) {
            switch (m.what)
            {
                case GD_HANDLER_REAL_PROC:
                if (m_queRequest.size() > 0 && m_lstSendBuffer.size() > 0)
                {
                    String strContCode = "";
                    RequestInfo pRQInfo = null;
                    GlobalStructs.ST_REAL_RECV_DATA_NUM pData = null;

                    ArrayList<GlobalStructs.ST_REAL_RECV_DATA_NUM> arrSendBuffer = new ArrayList<GlobalStructs.ST_REAL_RECV_DATA_NUM>();
                    for (int i = 0; i < m_queRequest.size(); i++) {
                        pRQInfo = m_queRequest.get(i);
                        if (pRQInfo == null) continue;

                        for (int j = m_lstSendBuffer.size() - 1; j >= 0; j--) {
                            pData = m_lstSendBuffer.get(j);

                            String strRealCode = String.valueOf(pData.sCode);
                            //if(pRQInfo.strCode == strRealCode || pRQInfo.strORGCode == strRealCode)
                            if (pRQInfo.strAutoCode.equals(strRealCode) == true) {
                                arrSendBuffer.add(pData);
                                m_lstSendBuffer.remove(j);

                                if (pRQInfo.strORGCode.equals("10199")) {
                                    strContCode = pRQInfo.strCode;
                                }
                            }

                            if (arrSendBuffer.size() >= GD_MAX_REAL_COUNT) break;
                        }

                        if (arrSendBuffer.size() > 0 && pRQInfo.nRealInfo == 1) {
                            m_cChartCtrl.ReceiveReal(pRQInfo.strORGCode, arrSendBuffer, ENMarketSatusType.GE_MKTIME_DUR.GetIndexValue(), strContCode);
                            strContCode = "";

                            if (m_enFragmentFrame == ENFragmentFrameType.GE_FRAGMENT_FRAME_MULTI) {
                                if (pRQInfo.nChartID == m_cChartCtrl.GetCurrentChartID())
                                    FragmentAppMain.GetFragmentMain().OnGetChartLinkEvent(ENChartOCXEventType.GE_OCX_EVENT_CURPRICE, pRQInfo.nChartID, null, 1, "");
                            }
                        }
                    }
                }

                if(m_nRealDataIndex < m_cPacketList.size())
                    HandlerRealProc.sendEmptyMessageDelayed(GD_HANDLER_REAL_PROC, m_nRealTimerDelay);

                break;
            }
        }
    };

    private Handler HandlerReceiveReal = new Handler() {
        public void handleMessage(Message m) {
            switch (m.what)
            {
                case GD_HANDLER_RECEIVE_REAL:

                    if (m_cChartCtrl == null || m_nRealDataIndex < 0) return;

                    String strValue = "";
                    int nRandomCount = (int)GlobalUtils.GetRandomValue(3.0, 1.0);
                    for(int i=0;i<nRandomCount;i++)
                    {
                        if(m_nRealDataIndex >= m_cPacketList.size())
                        {
                            GlobalUtils.ToastHandlerMessage(m_contParent, "Complete Real Data Test!");

                            HandlerRealProc.removeMessages(GD_HANDLER_REAL_PROC);
                            HandlerReceiveReal.removeMessages(GD_HANDLER_RECEIVE_REAL);
                            return;
                        }

                        GlobalStructs.ST_REAL_RECV_DATA_NUM realData = GlobalStructs.AllocRealRecvDataNum();
                        realData.sCode = m_strRealDataCode.toCharArray();

                        strValue = String.format("%d",GlobalUtils.GetYearMonthDay(m_cPacketList.get(m_nRealDataIndex).dValueList[m_nDateTimeIndex]));
                        realData.sDate = strValue.toCharArray();
                        if(m_cPacketList.get(m_nRealDataIndex).dValueList[m_nDateTimeIndex] >= 10000000000000.0)
                        {
                            strValue = String.format("%d",GlobalUtils.GetTime(m_cPacketList.get(m_nRealDataIndex).dValueList[m_nDateTimeIndex]));
                            realData.sTime = strValue.toCharArray();
                        }

                        realData.dOpen = m_cPacketList.get(m_nRealDataIndex).dValueList[m_nOpenIndex];
                        realData.dHigh = m_cPacketList.get(m_nRealDataIndex).dValueList[m_nHighIndex];
                        realData.dLow = m_cPacketList.get(m_nRealDataIndex).dValueList[m_nLowIndex];
                        realData.dClose = m_cPacketList.get(m_nRealDataIndex).dValueList[m_nCloseIndex];
                        realData.dVolume = m_cPacketList.get(m_nRealDataIndex).dValueList[m_nVolumeIndex];
                        //realData.dTrdVol = m_cPacketList.get(m_nRealDataIndex).dValueList[m_nValueIndex];
                        realData.dTrdVol = GlobalUtils.GetRandomValue(10.0, 1.0);

                        m_lstSendBuffer.add(realData);

                        m_nRealDataIndex += 1;
                    }

                    if(m_nRealDataIndex < m_cPacketList.size())
                    {
                        //시간도 Random하게
                        int nRandomDelay = (int)GlobalUtils.GetRandomValue(2000.0, 200.0);
                        HandlerReceiveReal.sendEmptyMessageDelayed(GD_HANDLER_RECEIVE_REAL, nRandomDelay);
                    }

                    break;
            }
        }
    };

    //임시 리얼 데이터 들어오는 부분
    private void ReceiveReal()
    {

    }

    public ArrayList<ST_PACKET_COREDATA> ReadCSVLocalData(String strName,String strCode,int nPeriod,int nPrdValue) {
        final int 		X_DATE = 0; // 날짜 test
        final int 		X_OPEN = 1; // 시가
        final int 		X_HIGH = 2; // 고가
        final int 		X_LOW = 3; // 저가
        final int 		X_CLOSE = 4; // 종가
        final int 		X_VOLUME = 5; // 거래량
        //final int 		G_MAXDATASIZE = 100000;

        if(strCode.isEmpty() == true)
        {
            GlobalUtils.ToastHandlerMessage(m_contParent,"데이터를 정상적으로 불러올수 없습니다");
            return null;
        }

        // 임시로 데이터를 읽어옴
        String strPrdValue = "1";
        if(nPeriod != -1)
        {
            if(nPrdValue > 1) strPrdValue = String.valueOf(nPrdValue);
        }

        final String strSeparator = "_";
        String strFilePath = "";
        String strDirPath = GlobalUtils.GetStorageDirPath()+GlobalDefines.GD_CHARTINFO_DIRECTORY+"Temp_Data/files/";
        if (ENPacketPeriodType.GE_PACKET_PERIOD_DAILY.GetIndexValue() == nPeriod)
        {
            strFilePath = strDirPath+strName+strSeparator+strCode+strSeparator+"일.csv";
        }
        else if (ENPacketPeriodType.GE_PACKET_PERIOD_WEEKLY.GetIndexValue() == nPeriod)
        {
            strFilePath = strDirPath+strName+strSeparator+strCode+strSeparator+"주.csv";
        }
        else if (ENPacketPeriodType.GE_PACKET_PERIOD_MONTHLY.GetIndexValue() == nPeriod)
        {
            strFilePath = strDirPath+strName+strSeparator+strCode+strSeparator+"월.csv";
        }
        else if (ENPacketPeriodType.GE_PACKET_PERIOD_MINUTE.GetIndexValue() == nPeriod)
        {
            strFilePath = strDirPath+strName+strSeparator+strCode+strSeparator+"분"+strPrdValue+".csv";
        }
        else if (ENPacketPeriodType.GE_PACKET_PERIOD_TICK.GetIndexValue() == nPeriod)
        {
            strFilePath = strDirPath+strName+strSeparator+strCode+strSeparator+"틱"+strPrdValue+".csv";
        }

        if(GlobalDirFile.isFileExist(strFilePath) == false) {
            GlobalUtils.ToastHandlerMessage(m_contParent,strFilePath + "경로에 파일이 존재 하지 않습니다");
            return null;
        }

        ArrayList<ST_PACKET_COREDATA> stPacketArrayList = CSVManager.ReadCSVFileToPackData(strName, strFilePath);
        if(stPacketArrayList == null)
        {
            GlobalUtils.ToastHandlerMessage(m_contParent,"데이터를 정상적으로 불러올수 없습니다");
            return null;
        }

        return stPacketArrayList;
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////
    //시장 조회,Head 조회,Bottom조회 처리
    //시장지표일 경우 해당코드의 기초코드가 필요할 경우가 있다
    public String GetMarketBaseCode(String strName ,String strCode )
    {
        String strBaseCode = strCode;
        return strBaseCode;
    }

    public int RequestMarketData(int nID ,String szDBName ,String szQueryTR ,String szRealTR ,String szCode ,
                                 Double dStartDT ,Double dEndDT  ,int nCount ,int nPeriod,
                                 int nPrdVal ,int nDays ,int nMarket,String strETCInfo ,
                                 int nETCInfo  ,int nUseGubun ,int nDataGubun ,
                                 int nRQType)
    {

        return m_nRequestNum;
    }

    public int RequestHeadData(int nID,String strCode ,String strName ,int nMarket,int nMarketGubun)
    {
        return 0;
    }

    public int RequestBottomQuery(int nID ,String strCode ,String strName ,int nMarket ,int nMarketGubun )
    {
        return 0;
    }

    public String GetCodeMarket(String strCode )
    {
        //var nMarket = -1
        int nMarket = ENMarketCategoryType.PNX_CODE_TYPE_KOSPI_CODE.GetIndexValue();
        String strMarket = "";

        ////////////////////////////////////////////////////////////////////
        //코드로 시장값 얻어오는 로직은 증권사 마다 다르고 여기서 적용하는게 의미가 없다
        //EX: 유안타 증권 - 코드 테이블에서 해당 코드의 시장값 받아옴,
        //유진선물 - strQuery.Format(_T("select kospi_key from series where series='%s' and cme_tp = 'night'"), strCode);
        //증권사 마다 로직이 다르다

        strMarket = String.format( "%d", nMarket);
        return strMarket;
    }

    //코드로 시장구분값 얻어오는 로직은 증권사 마다 다르고 여기서 적용하는게 의미가 없다
    //시장구분값의 의미 - 코스탁 코드일경우(코넥스여부),
    public String GetCodeMarketGubun(String strCode )
    {
        int nMarketGubun = -1;
        String strMarketGubun = "";

        strMarketGubun = String.format( "%d",nMarketGubun);
        return strMarketGubun;
    }

    private void StartWaiting(int nReqID,int nChartID)
    {
        if(m_mapRQWaiting.get(nReqID) != null) { return; }

        m_mapRQWaiting.put(nReqID,nChartID);
    }

    private void EndWaiting(int nReqID )
    {
        if(m_mapRQWaiting.get(nReqID) == null) { return; }

        m_mapRQWaiting.remove(nReqID);
    }
}

////////////////////////////////////////////////////////////////////////////////////////////////////
//사용하지는 않지만 추후를 고려해서 소스 유지
/*    public void DoStart() {
        /*if(!m_fragmentParent.IsVewChart()){
            // 폼차트가 아닌 경우에만 RDS를 등록한다
            if(rdscheListener == null)
            {
                rdscheListener = new RDSCheListener() {
                    @Override
                    public void OnRDSCheListener(RDSCheEvent rdsCheEvent) {
                        if(rdsCheEvent.isConnected == true){
                            if(rdsCheEvent.chartRealData != null){
                                ChartRealData chartRealData = rdsCheEvent.chartRealData;

                                Message msg = new Message();
                                msg.what = MSG_RDS_REAL;
                                Bundle bundle = new Bundle();
                                bundle.putSerializable("rdsData", chartRealData);
                                msg.setData(bundle);
                                mHandler.sendMessage(msg);

                                //정상적으로 들어오면 다시 Setting한다
                                m_nRequestData = -1;
                            }
                        }
                        else
                        {
                            //주의:조회요청후 리얼이 False로 떨어지면 무한루프 도는 현상 방지위해서
                            if(m_nRequestData < 2)
                            {
                                PGLog.e("socket is cloed");

                                //주의:리얼재 재등록한다
                                int nQueueSize = m_queRequest.size();
                                RequestInfo pRQInfoORG = null;
                                for(int i=0;i<nQueueSize;i++)
                                {
                                    pRQInfoORG = m_queRequest.get(i);

                                    if(nQueueSize == 1)
                                    {
                                        ResetRealData(pRQInfoORG.strORGCode);
                                    }
                                    else
                                    {
                                        //지우고 다시 할당한다
                                        UnRegisterRealData(pRQInfoORG.strORGCode);
                                        RegisterRealData(pRQInfoORG.strORGCode);
                                    }
                                }

                                m_nRequestData++;
                            }
                            else if(m_nRequestData == 2)
                            {
                                //메세지는 한번 만 보여준다
                                mHandler.sendEmptyMessage(MSG_RDS_DISCONNECTED);
                                m_nRequestData++;
                            }
                        }
                    }
                };
            }

            RDSCheReceiver.AddRDSCheListener(rdscheListener);

            if(rdsJisuListener == null)
            {
                rdsJisuListener = new RDSJisuListener() {
                    @Override
                    public void OnRDSJisuListener(RDSJisuEvent rdsJisuEvent) {

                        if(rdsJisuEvent.isConnected == true){
                            if(rdsJisuEvent.jisuPacket != null){
                                JisuPacket jisuPacket = rdsJisuEvent.jisuPacket;

                                double openPrice = m_fragmentParent.GetLastPrcValue(ENPacketDataType.GE_PACKET_CORE_OPEN);
                                double highPrice = m_fragmentParent.GetLastPrcValue(ENPacketDataType.GE_PACKET_CORE_HIGH);
                                double lowPrice = m_fragmentParent.GetLastPrcValue(ENPacketDataType.GE_PACKET_CORE_LOW);
                                double closePrice = Double.valueOf(jisuPacket.Jisu) / 100.0 ;

                                ChartRealData chartRealData = new ChartRealData();
                                chartRealData.m_Code = jisuPacket.StockCode;
                                chartRealData.m_Time = Double.parseDouble(jisuPacket.Time);
                                chartRealData.m_Open = openPrice;
                                chartRealData.m_Close = closePrice;

                                if(highPrice > closePrice)
                                    chartRealData.m_High = highPrice;
                                else
                                    chartRealData.m_High = closePrice;

                                if(lowPrice < closePrice)
                                    chartRealData.m_Low = lowPrice;
                                else
                                    chartRealData.m_Low = closePrice;

                                if(m_fragmentParent.GetNPeriod() == ENPacketPeriodType.GE_PACKET_PERIOD_DAILY.GetIndexValue()){
                                    double totalVolume = Double.parseDouble(jisuPacket.TotalAmount);
                                    chartRealData.m_Volume = totalVolume - m_fragmentParent.GetLastPrcValue(ENPacketDataType.GE_PACKET_CORE_VOLUME);
                                    chartRealData.m_AccumVolume = totalVolume;
                                }
                                else{
                                    chartRealData.m_Volume = 0;
                                    chartRealData.m_AccumVolume = 0;
                                }

                                Message msg = new Message();
                                msg.what = MSG_RDS_REAL;
                                Bundle bundle = new Bundle();
                                bundle.putSerializable("rdsData", chartRealData);
                                msg.setData(bundle);
                                mHandler.sendMessage(msg);

                                //정상적으로 들어오면 다시 Setting한다
                                m_nRequestData = -1;
                            }
                        }
                        else
                        {
                            //주의:조회요청후 리얼이 False로 떨어지면 무한루프 도는 현상 방지위해서
                            if(m_nRequestData < 2)
                            {
                                PGLog.e("socket is cloed");

                                //주의:리얼재 재등록한다
                                int nQueueSize = m_queRequest.size();
                                RequestInfo pRQInfoORG = null;
                                for(int i=0;i<nQueueSize;i++)
                                {
                                    pRQInfoORG = m_queRequest.get(i);

                                    if(nQueueSize == 1)
                                    {
                                        ResetRealData(pRQInfoORG.strORGCode);
                                    }
                                    else
                                    {
                                        //지우고 다시 할당한다
                                        UnRegisterRealData(pRQInfoORG.strORGCode);
                                        RegisterRealData(pRQInfoORG.strORGCode);
                                    }
                                }

                                m_nRequestData++;
                            }
                            else if(m_nRequestData == 2)
                            {
                                //메세지는 한번 만 보여준다
                                mHandler.sendEmptyMessage(MSG_RDS_DISCONNECTED);
                                m_nRequestData++;
                            }
                        }
                    }
                };
            }
            RDSJisuReceiver.AddRDSJisuListener(rdsJisuListener);
        }
    }

public void DoStop() {
        if(!m_fragmentParent.IsVewChart()){
            RDSCheReceiver.RemoveAllRDSChe();
            RDSCheReceiver.RemoveRDSCheListener(rdscheListener);
            RDSJisuReceiver.RemoveAllRDSJisu();
            RDSJisuReceiver.RemoveRDSJisuListener(rdsJisuListener);
        }
        }

        private class RequestBaseDataAsyncTask extends AsyncTask<RequestInfo, String, ArrayList<ST_PACKET_COREDATA>> {
        private Context contProgress = null;
        RequestInfo requestInfo = null;

        public RequestBaseDataAsyncTask(Context contProgress) {
            this.contProgress = contProgress;
        }

        @Override
        protected void onCancelled()
        {
            super.onCancelled();
        }

        @Override
        protected void onPreExecute() {
            //Async 처리에서 MessageDialog처리하면서 Try Catch로 튕김 현상발생
            if(m_dlgProgress == null)
                m_dlgProgress = new ProgressDlg(m_contParent);

            try {
                if (m_dlgProgress.isShowing() == false)
                {
                    m_dlgProgress.SetMessage("데이터 조회중...");
                    m_dlgProgress.show();
                }
            }
            catch (Exception e)
            {
                PGLog.e(e.getMessage());
            }
            super.onPreExecute();
        }

        @Override
        protected ArrayList<ST_PACKET_COREDATA> doInBackground(RequestInfo... params) {
            if(this.isCancelled()){
                return null;
            }
            requestInfo = params[0];
            if(requestInfo != null){
                publishProgress("");

                return ReadCSVLocalData(requestInfo);
            }

            return null;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            if(values[0] != null)
            {
            }
            super.onProgressUpdate(values);
        }

        protected void onPostExecute(ArrayList<ST_PACKET_COREDATA> arrPacketList) {
            if(arrPacketList != null)
            {
                if(requestInfo != null)
                    ReceiveQuery(requestInfo.m_nChartID,requestInfo.m_strCode,arrPacketList,requestInfo.m_dPrevClose,requestInfo.m_dUpperValue,requestInfo.m_dLowerValue);
            }
            else {
                Toast.makeText(m_contParent, "데이터를 조회할수 없습니다", Toast.LENGTH_SHORT).show();
            }

            m_dlgProgress.dismiss();

            super.onPostExecute(arrPacketList);
        }
    }
 */

/*public void ToastHandlerMessage(String strMSG) {
        Message msg = new Message();
        msg.what = 100;
        Bundle bundle = new Bundle();
        bundle.putString(String.valueOf(msg.what), strMSG);
        msg.setData(bundle);

        final HandlerThread handlerThread = new HandlerThread("HandlerToast");
        handlerThread.start();
        new Handler(handlerThread.getLooper()){
            @Override
            public void handleMessage(Message msg) {
                String strMsg = msg.getData().getString(String.valueOf(msg.what));
                Toast.makeText(m_contParent, strMsg, Toast.LENGTH_LONG).show();
                handlerThread.quit();
                super.handleMessage(msg);
            }
        }.sendMessage(msg);
    }*/