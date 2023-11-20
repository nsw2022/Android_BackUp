package chartlab.PowerMChartMain.DataWnd;

import java.io.Serializable;
import java.util.ArrayList;

public class ChartDBData implements Serializable
{
    public int m_nChartID;
    public int m_nRQID;
    public int m_nMarket;
    public String m_strCode;
    public String m_strCodeName;
    public int m_nPeriod;
    public int m_nPeriodValue;
    public double m_dUpperPrice;
    public double m_dLowerPrice;
    public double m_dPreClose;
    public ArrayList<ChartDataItem> m_cChartDataItemList;

    public ChartDBData()
    {
        m_nChartID = -1;
        m_nRQID = -1;
        m_nMarket = -1;
        m_strCode = "";
        m_strCodeName = "";
        m_nPeriod = -1;
        m_nPeriodValue = -1;
        m_dUpperPrice = 0.0;
        m_dLowerPrice = 0.0;
        m_dPreClose = 0.0;
        m_cChartDataItemList = new ArrayList<ChartDataItem>();
    }

    public int GetDataCount()
    {
        if(m_cChartDataItemList == null && m_cChartDataItemList.isEmpty()) return 0;

        return m_cChartDataItemList.size();
    }

    public ChartDataItem GetDBDataItem(int nIndex)
    {
        if(m_cChartDataItemList == null && m_cChartDataItemList.size() >= nIndex)
            return null;

        return m_cChartDataItemList.get(nIndex);
    }
    /*
    this.Code = cdr.code;
    this.CodeName = cdr.codeName;
    this.Period = cdr.period;
    this.PeriodValue = cdr.periodValue;

    if (UpjongStd.IsContainsUpjongStd(this.Code)) {
        this.m_nMarket = 1;
    }
    else {
        this.m_nMarket = 0;
    }
    PGLog.d("m_nMarket : " + String.valueOf(this.m_nMarket));

    if (UpjongStd.IsContainsUpjongStd(this.Code)) {
        this.UpperPrice = (cdr.upperPrice / 100.0D);
        this.LowerPrice = (cdr.lowerPrice / 100.0D);
        this.PreClose = (cdr.preClose / 100.0D);
    }
    else {
        this.UpperPrice = cdr.upperPrice;
        this.LowerPrice = cdr.lowerPrice;
        this.PreClose = cdr.preClose;
    }

    this.IsKospi = cdr.isKospi;
    this.Count = cdr.count;
    this.Series = new ArrayList();
    for (Series s : cdr.series)
        if (UpjongStd.IsContainsUpjongStd(this.Code)) {
            s.img_ico_close_white /= 100.0D;
            s.open /= 100.0D;
            s.high /= 100.0D;
            s.low /= 100.0D;

            ChartDBDataItem dbi = new ChartDBDataItem(s, this.Period);
            this.Series.add(dbi);
        }
        else {
            ChartDBDataItem dbi = new ChartDBDataItem(s, this.Period);
            this.Series.add(dbi);
        }
    */
}

