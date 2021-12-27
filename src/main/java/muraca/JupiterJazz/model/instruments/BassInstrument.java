package muraca.JupiterJazz.model.instruments;

public class BassInstrument extends Instrument {
    public BassInstrument() {
        setMinPitch(28);
        setMaxPitch(55);
        setSelectedMinPitch(28);
        setSelectedMaxPitch(55);
    }

    @Override
    public String getClefShape() { return "F"; }

    @Override
    public String getClefStaffStep() { return "6"; }

    @Override
    public String getName() { return "Bass"; }
}
