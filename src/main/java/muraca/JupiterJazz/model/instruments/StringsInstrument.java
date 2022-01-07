package muraca.JupiterJazz.model.instruments;

import jm.constants.Pitches;
import jm.constants.ProgramChanges;

public class StringsInstrument extends Instrument {
    @Override
    public void resetParameterValues() {
        setMinPitch(Pitches.E3);
        setMaxPitch(Pitches.G7);

        setSelectedMinPitch(Pitches.E3);
        setSelectedMaxPitch(Pitches.G7);
    }

    @Override
    public String getClefShape() { return "G"; }

    @Override
    public String getClefStaffStep() { return "2"; }

    @Override
    public String getName() { return "Strings"; }

    @Override
    public int getMIDIInstrumentID() { return ProgramChanges.STRING_ENSEMBLE_1; }

}
