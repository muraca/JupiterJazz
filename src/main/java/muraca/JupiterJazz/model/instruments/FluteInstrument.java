package muraca.JupiterJazz.model.instruments;

import jm.constants.Pitches;
import jm.constants.ProgramChanges;

public class FluteInstrument extends Instrument {
    @Override
    public void resetParameterValues() {
        setMinPitch(Pitches.C4);
        setMaxPitch(Pitches.C6);

        setSelectedMinPitch(Pitches.C4);
        setSelectedMaxPitch(Pitches.C6);
    }

    @Override
    public String getClefShape() { return "G"; }

    @Override
    public String getClefStaffStep() { return "2"; }

    @Override
    public String getName() { return "Flute"; }

    @Override
    public int getMIDIInstrumentID() { return ProgramChanges.FLUTE; }

}