package muraca.JupiterJazz.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TimeSignature {
    public static final String[] TIME_SIGNATURES = {"2/4", "3/4", "4/4", "5/4", "6/4", "7/4", "3/8", "4/8", "5/8", "6/8", "7/8"};

    private int beatsPerMeasure;
    private int inverseBeatNoteLength;

    public TimeSignature() {
        this(4,4);
    }

    public float getFloatValue() {
        return beatsPerMeasure / inverseBeatNoteLength;
    }

    public static TimeSignature parseString(String s) {
        int num = 4, den = 4;
        try {
            String[] split = s.split("/");
            num = Integer.parseInt(split[0]);
            den = Integer.parseInt(split[1]);
        }
        catch (Exception e) {}

        return new TimeSignature(num, den);
    }
}
