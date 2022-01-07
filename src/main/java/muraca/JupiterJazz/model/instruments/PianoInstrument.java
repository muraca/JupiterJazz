package muraca.JupiterJazz.model.instruments;


import jm.constants.Pitches;
import jm.constants.ProgramChanges;

public class PianoInstrument extends Instrument {
    @Override
    public void resetParameterValues() {
        setSelectedMinPitch(Pitches.C1);
        setSelectedMaxPitch(Pitches.B6);
    }

    @Override
    public String getClefShape() { return "G"; }

    @Override
    public String getClefStaffStep() { return "2"; }

    @Override
    public String getName() { return "Piano"; }

    @Override
    public int getMIDIInstrumentID() { return ProgramChanges.PIANO; }

}
