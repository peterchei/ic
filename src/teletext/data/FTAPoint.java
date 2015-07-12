package teletext.data;


public class FTAPoint {

	
    public boolean isValid() {
		return isValid;
	}

	public void setValid(boolean isValid) {
		this.isValid = isValid;
	}

	public boolean isValid = false;

    // for SMA and WMA, EMA
    public float MA1 = 0f;
    public float MA2 = 0f;
    public float MA3 = 0f;

    // for EMA
    // float EMA = 0f;

    // for Bollinger's Band
    public float UB = 0f;
    public float LB = 0f;

    // for RSI
    public float RSI = 0f;

    // for STC
    public float K = 0f;
    public float D = 0f;

    // for William's %R
    public float R = 0f;

    // for OBV
    public double OBV = 0f; //in K unit

    // for MACD
    public float MACD1 = 0f;
    public float MACD2 = 0f;
    public float MACDdiff = 0f;

    // for RMI
    public float RMI = 0f;

}