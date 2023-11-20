package chartlab.PowerMChartMain.DataWnd;

import chartlab.PowerMChartEngine.KernelCore.GlobalDefines;
import chartlab.PowerMChartEngine.KernelCore.GlobalEnums.ENRequestQueryType;

public class RequestInfo
{
	public int nChartID = -1;
	public String strCode = "";
	public String strORGCode = "";
	public String strName = "";
	public String strAutoCode = "";
	
	public int nMarket = -1;
	public int nMarketGubun = -1;

	//ios 추가
	public double dStartDT = GlobalDefines.GD_NAVALUE_DOUBLE;
	public double dEndDT = GlobalDefines.GD_NAVALUE_DOUBLE;

	public int nRQCount = -1;
	
	public int nPeriod = -1;
	public int nPrdValue = -1;
	
	public String strTRQuery = "";
	public String strTRReal = "";
	
	public int nRequestID = -1;
	public int nRealInfo = -1;
	public boolean bIsAddRQ = false;

	public  double dPrevClose = GlobalDefines.GD_NAVALUE_DOUBLE;
	public  double dUpperValue = GlobalDefines.GD_NAVALUE_DOUBLE;
	public  double dLowerValue = GlobalDefines.GD_NAVALUE_DOUBLE;
	
	ENRequestQueryType enRQQuery = ENRequestQueryType.GE_REQUEST_QUERY_NONE;

	public RequestInfo()
	{
		nChartID = -1;
		strCode = "";
		strORGCode = "";
		strName = "";
		strAutoCode = "";
		
		nMarket = -1;
		nMarketGubun = -1;

		//ios 추가
		dStartDT = GlobalDefines.GD_NAVALUE_DOUBLE;
		dEndDT = GlobalDefines.GD_NAVALUE_DOUBLE;

		nRQCount = -1;
		
		nPeriod = -1;
		nPrdValue = -1;
		
		strTRQuery = "";
		strTRReal = "";
		
		nRequestID = -1;
		nRealInfo = -1;
		bIsAddRQ = false;

		dPrevClose = GlobalDefines.GD_NAVALUE_DOUBLE;
		dUpperValue = GlobalDefines.GD_NAVALUE_DOUBLE;
		dLowerValue = GlobalDefines.GD_NAVALUE_DOUBLE;

		enRQQuery = ENRequestQueryType.GE_REQUEST_QUERY_NONE;
	}
}
