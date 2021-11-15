package muraca.JupiterJazz.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Fraction {
    private int num;
    private int den;

    public float getFloatValue() {
        return num / den;
    }

    public static Fraction parseString(String s) {
        int num = 4, den = 4;
        try {
            String[] split = s.split("/");
            num = Integer.parseInt(split[0]);
            den = Integer.parseInt(split[1]);
        }
        catch (Exception e) {}

        return new Fraction(num, den);
    }

    public static Fraction min(Fraction... fractions) {
        int minIndex = 0;
        for (int i = 1; i < fractions.length; ++i) {
            if (fractions[i].getFloatValue() < fractions[minIndex].getFloatValue()) {
                minIndex = i;
            }
        }
        return fractions[minIndex];
    }

    public static Fraction max(Fraction... fractions) {
        int maxIndex = 0;
        for (int i = 1; i < fractions.length; ++i) {
            if (fractions[i].getFloatValue() > fractions[maxIndex].getFloatValue()) {
                maxIndex = i;
            }
        }
        return fractions[maxIndex];
    }

    public String toString() {
        return String.valueOf(num) + "/" + String.valueOf(den);
    }
}
