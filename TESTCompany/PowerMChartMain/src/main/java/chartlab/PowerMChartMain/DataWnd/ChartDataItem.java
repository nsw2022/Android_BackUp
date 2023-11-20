package chartlab.PowerMChartMain.DataWnd;

import java.io.Serializable;

public class ChartDataItem implements Serializable
{
    public double m_dDateTime;
    public double m_dOpen;
    public double m_dHigh;
    public double m_dLow;
    public double m_dClose;
    public double m_dVolume;

    public ChartDataItem()
    {
        m_dDateTime = 0.0;
        m_dOpen = 0.0;
        m_dHigh = 0.0;
        m_dLow = 0.0;
        m_dClose = 0.0;
        m_dVolume = 0.0;
    }

    public ChartDataItem(double dDateTime, double dOpen, double dHigh, double dLow, double dClose, double dVolume)
    {
        this.m_dDateTime = dDateTime;
        this.m_dOpen = dOpen;
        this.m_dHigh = dHigh;
        this.m_dLow = dLow;
        this.m_dClose = dClose;
        this.m_dVolume = dVolume;
    }
}
