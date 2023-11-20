package chartlab.PowerMChartMain.DataWnd;

import java.io.Serializable;

public class ChartRealData implements Serializable
{
    public int m_nMarket;
    public String m_strCode;
    public double m_dTime;
    public double m_dOpen;
    public double m_dHigh;
    public double m_dLow;
    public double m_dClose;
    public double m_dVolume;
    public double m_dAccumVolume;
    public double m_dOrganization;
    public double m_dForeigner;
    public double m_dIndividual;

    public ChartRealData()
    {
        m_nMarket = -1;
        m_strCode = "";
        m_dTime = 0.0;
        m_dOpen = 0.0;
        m_dHigh = 0.0;
        m_dLow = 0.0;
        m_dClose = 0.0;
        m_dVolume = 0.0;
        m_dAccumVolume = 0.0;
        m_dOrganization = 0.0;
        m_dForeigner = 0.0;
        m_dIndividual = 0.0;
    }

    /*
    public ChartRealData(StockBatchPacket sbp)
    {
        if (sbp == null) return;

        this.Market = Integer.parseInt(sbp.InfoGB);
        this.Code = CodeStd.GetStockCodeByExpStockCode(sbp.ExpStockCode);
        this.Time = Double.parseDouble(sbp.Time);
        this.Open = Double.parseDouble(sbp.Open);
        this.High = Double.parseDouble(sbp.High);
        this.Low = Double.parseDouble(sbp.Low);
        this.Close = Double.parseDouble(sbp.Close);
        this.Volume = Double.parseDouble(sbp.Amount);
        this.AccumVolume = Double.parseDouble(sbp.TotalAmount);
        this.Organization = 0.0D;
        this.Foreigner = 0.0D;
        this.Individual = 0.0D;
    }

    public ChartRealData(JisuPacket jp) {
        this.Market = Integer.parseInt(jp.InfoGB);
        this.Code = jp.StockCode;
        this.Time = Double.parseDouble(jp.Time);

        this.Close = Double.parseDouble(jp.Jisu);

        this.Volume = Double.parseDouble(jp.TotalAmount);
        this.AccumVolume = Double.parseDouble(jp.TotalBalance);
    }
    */
}
