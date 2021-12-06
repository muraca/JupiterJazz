package muraca.JupiterJazz.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public abstract class Instrument {
    private Session session;

    public String clefShape;
    public String clefStaffStep;

    public int minPitch;
    public int maxPitch;

    public String name;

    public int getMinPitch() {
        return Math.max(minPitch, session.getMinPitch());
    }

    public int getMaxPitch() {
        return Math.min(maxPitch, session.getMaxPitch());
    }
}
