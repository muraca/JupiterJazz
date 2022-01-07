package muraca.JupiterJazz.model.instruments;

import jm.constants.Pitches;
import jm.constants.ProgramChanges;

public class BassInstrument extends Instrument {
    @Override
    public void resetParameterValues() {
        setMinPitch(Pitches.E0);
        setMaxPitch(Pitches.G3);

        setSelectedMinPitch(Pitches.E0);
        setSelectedMaxPitch(Pitches.G2);
    }

    @Override
    public String getClefShape() { return "F"; }

    @Override
    public String getClefStaffStep() { return "6"; }

    @Override
    public String getName() { return "Bass"; }

    @Override
    public int getMIDIInstrumentID() { return ProgramChanges.ACOUSTIC_BASS; }

}
