package muraca.JupiterJazz.model.instruments;

import java.util.Arrays;
import java.util.List;

public class InstrumentsManager {
    private static final List<Instrument> instruments = Arrays.asList(
            new PianoInstrument(),
            new BassInstrument(),
            new FluteInstrument(),
            new MarimbaInstrument(),
            new StringsInstrument()
    );

    public static final List<Instrument> getInstruments() {
        return instruments;
    }

    public static final List<Instrument> getInstrumentsAfterReset() {
        for (Instrument i: instruments) {
            i.resetParameterValues();
            i.setEnabled(false);
        }
        return instruments;
    }

    public static Instrument getInstrumentByName(String name) {
        for (Instrument i : instruments) {
            if (i.getName().equalsIgnoreCase(name)) {
                return i;
            }
        }
        return null;
    }

    public static boolean isNoInstrumentEnabled() {
        for (Instrument i: instruments) {
            if (i.isEnabled())
                return false;
        }
        return true;
    }
}
