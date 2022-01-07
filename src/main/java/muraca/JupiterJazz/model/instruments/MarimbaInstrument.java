package muraca.JupiterJazz.model.instruments;

import jm.constants.Pitches;
import jm.constants.ProgramChanges;

public class MarimbaInstrument extends Instrument {
    @Override
    public void resetParameterValues() {
        setMinPitch(Pitches.C4);
        setMaxPitch(Pitches.B7);

        setSelectedMinPitch(Pitches.C4);
        setSelectedMaxPitch(Pitches.B7);
    }

    @Override
    public String getClefShape() { return "G"; }

    @Override
    public String getClefStaffStep() { return "2"; }

    @Override
    public String getName() { return "Marimba"; }

    @Override
    public int getMIDIInstrumentID() { return ProgramChanges.MARIMBA; }
}
