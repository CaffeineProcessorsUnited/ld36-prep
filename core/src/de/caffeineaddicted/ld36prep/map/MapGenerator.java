package de.caffeineaddicted.ld36prep.map;

import de.caffeineaddicted.ld36prep.util.MathUtils;

/**
 * @author Malte Heinzelmann
 */
public class MapGenerator {



    public static Map.GroundType[][] gen(int cols, int rows) {
        Map.GroundType[][] map = new Map.GroundType[cols][rows];
        int mT = MathUtils.random(0, 2);
        int mB = MathUtils.random(0, 2);
        int mL = MathUtils.random(0, 2);
        int mR = MathUtils.random(0, 2);
        int sr = mT;
        int sc = 0;
        int fr = (((cols - mL - mR - 1) % 4) < 2) ? rows - mB - 1 : mT;
        int fc = cols - 1;
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                Map.GroundType t = Map.GroundType.PATH;
                if (r == sr && c < mL) {
                    if (c == sc) { // starting point
                        t = Map.GroundType.START;
                    } else { // connect start to existing path
                        t = Map.GroundType.PATH;
                    }
                } else if (r == fr && c >= (cols - mR - 1)) {
                    if (c == fc) { // finish point
                        t = Map.GroundType.FINISH;
                    } else { // connect finish to existing path
                        t = Map.GroundType.PATH;
                    }
                } else {
                    if (r < mT || r >= (rows - mB) || c < mL || c >= (cols - mR)) {
                        t = Map.GroundType.TOWER;
                    } else {
                        switch ((c - mL) % 4) {
                            case 0:
                            case 2:
                                t = Map.GroundType.PATH;
                                break;
                            case 1:
                                t = (r == (rows - mB - 1)) ? Map.GroundType.PATH : Map.GroundType.TOWER;
                                break;
                            case 3:
                                t = (r == (mT)) ? Map.GroundType.PATH : Map.GroundType.TOWER;
                                break;
                        }
                    }
                }
                map[c][r] = t;
            }
        }

        return map;
    }
}
