package muraca.JupiterJazz.model.instruments;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public abstract class Instrument {
    public abstract String getClefShape();
    public abstract String getClefStaffStep();

    private int minPitch = 21;
    private int maxPitch = 108;

    protected void setMinPitch(int pitch) { minPitch = pitch; }
    protected void setMaxPitch(int pitch) { maxPitch = pitch; }

    public int getMinPitch() { return Math.max(21, minPitch); }
    public int getMaxPitch() { return Math.min(108, maxPitch); }

    private int selectedMinPitch = 21;
    private int selectedMaxPitch = 108;

    public void setSelectedMinPitch(int pitch) { selectedMinPitch = Math.max(pitch, getMinPitch()); }
    public void setSelectedMaxPitch(int pitch) { selectedMaxPitch = Math.min(pitch, getMaxPitch()); }

    public abstract String getName();

    private boolean enabled = true;
}
