package Train;

import java.io.Serializable;

public class ProbabilitySet implements Serializable {
    public int totalSkinCount, totalNonSkinCount;
    public double[][][] probabilitySkin = new double[256][256][256];
    public double[][][] probabilityNonSkin = new double[256][256][256];

    public ProbabilitySet (int skinCount, int nonSkinCount) {
        this.totalSkinCount = skinCount;
        this.totalNonSkinCount = nonSkinCount;
    }
}
