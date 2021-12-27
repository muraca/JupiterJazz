package muraca.JupiterJazz.model.instruments;


public class PianoInstrument extends Instrument {
    public PianoInstrument() {
        setSelectedMinPitch(24);
        setSelectedMaxPitch(88);
    }

    @Override
    public String getClefShape() { return "G"; }

    @Override
    public String getClefStaffStep() { return "2"; }

    @Override
    public String getName() { return "Piano"; }
}
