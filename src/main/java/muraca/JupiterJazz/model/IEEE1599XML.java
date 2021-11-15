package muraca.JupiterJazz.model;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.util.Random;

public class IEEE1599XML {

    private static Random random = new Random();

    public static void readFromXML(File f) {

    }

    public static void saveToXML(Session s) {
        try {
            Document doc = DocumentUtils.newDocument();

            Element root = doc.createElement("ieee1599");
            root.setAttribute("creator", s.getAuthor());
            root.setAttribute("version", "1.0");

            Element general = doc.createElement("general");
            root.appendChild(general);
            Element description = doc.createElement("description");
            general.appendChild(description);
            Element main_title = doc.createElement("main_title");
            main_title.setTextContent(s.getTitle());
            description.appendChild(main_title);
            Element author = doc.createElement("author");
            author.setTextContent(s.getAuthor());
            description.appendChild(author);

            Element logic = doc.createElement("logic");
            root.appendChild(logic);
            Element spine = doc.createElement("spine");
            logic.appendChild(spine);
            Element los = doc.createElement("los");
            logic.appendChild(los);

            Element staff_list = doc.createElement("staff_list");
            los.appendChild(staff_list);

            int id = 0;
            final int measureDurationInVTU = Constants.TIME_SIGNATURES_VTU.get(Constants.TIME_SIGNATURES_FRAC.indexOf(s.getTimeSignature().toString()));
            for (Instrument instrumentInInstruments: s.getInstruments()) {
                id++;
                Element staff = doc.createElement("staff");
                staff_list.appendChild(staff);
                staff_list.setAttribute("id", "Instrument_"+id+"_staff");
                staff_list.setAttribute("line_number", "5");

                Element time_signature = doc.createElement("time_signature");
                staff.appendChild(time_signature);
                time_signature.setAttribute("event_ref", "TimeSignature_Instrument_"+id+"_1");
                Element time_indication = doc.createElement("time_indication");
                time_signature.appendChild(time_indication);
                time_indication.setAttribute("num", String.valueOf(s.getTimeSignature().getNum()));
                time_indication.setAttribute("den", String.valueOf(s.getTimeSignature().getDen()));

                Element time_signatureEvent = doc.createElement("event");
                spine.appendChild(time_signatureEvent);
                time_signatureEvent.setAttribute("id", "TimeSignature_Instrument_"+id+"_1");
                time_signatureEvent.setAttribute("hpos", "0");
                time_signatureEvent.setAttribute("timing", "0");

                Element clef = doc.createElement("clef");
                staff.appendChild(clef);
                clef.setAttribute("event_ref", "Clef_Instrument_"+id+"_1");
                clef.setAttribute("octave_num", "0");
                clef.setAttribute("shape", instrumentInInstruments.getClefShape());
                clef.setAttribute("staff_step", instrumentInInstruments.getClefStaffStep());

                Element clefEvent = doc.createElement("event");
                spine.appendChild(clefEvent);
                clefEvent.setAttribute("id", "Clef_Instrument_"+id+"_1");
                clefEvent.setAttribute("hpos", "0");
                clefEvent.setAttribute("timing", "0");

                Element part = doc.createElement("part");
                los.appendChild(part);
                Element voice_list = doc.createElement("voice_list");
                part.appendChild(voice_list);
                Element voice_item = doc.createElement("voice_item");
                voice_list.appendChild(voice_item);
                voice_item.setAttribute("id", "Instrument_"+id+"_0_voice");
                voice_item.setAttribute("staff_ref", "Instrument_"+id+"_staff");

                for (int i = 1; i <= s.getDurationInMeasures(); ++i) {
                    Element measure = doc.createElement("measure");
                    part.appendChild(measure);
                    measure.setAttribute("number", String.valueOf(i));

                    Element voice = doc.createElement("voice");
                    measure.appendChild(measure);
                    voice.setAttribute("voice_item_ref", "Instrument_"+id+"_0_voice");

                    int currentMeasureRemainingDuration = measureDurationInVTU;
                    while (currentMeasureRemainingDuration != 0) {
                        String eventId = "Instrument_"+id+"_voice0_measure1_ev"+voice.getChildNodes().getLength();

                        boolean isRest = random.nextInt(100) < s.getPauseProbability();
                        if (isRest && currentMeasureRemainingDuration > s.getMaxPauseDurationVTU() && //min pause is longer than remaining time
                                currentMeasureRemainingDuration <= s.getMinNoteDurationVTU()) { //min note is not longer than remaining time
                            isRest = false; //must be a note
                        }


                        int minDurationIndex = isRest ? s.getMinPauseDurationIndex() : s.getMinNoteDurationIndex();
                        int maxDurationIndex = Math.min(Constants.EVENT_DURATIONS_VTU.indexOf(currentMeasureRemainingDuration),
                                                   isRest ? s.getMaxPauseDurationIndex() : s.getMaxNoteDurationIndex());;

                        int eventDurationIndex = random.nextInt(maxDurationIndex - minDurationIndex + 1) + minDurationIndex;
                        int eventDurationVTU = Constants.EVENT_DURATIONS_VTU.get(eventDurationIndex);
                        Fraction eventDuration = Fraction.parseString(Constants.EVENT_DURATIONS_FRAC.get(eventDurationIndex));

                        Element event = doc.createElement("event");
                        spine.appendChild(event);
                        event.setAttribute("id", eventId);
                        event.setAttribute("hpos", String.valueOf(eventDurationVTU));
                        event.setAttribute("timing", String.valueOf(eventDurationVTU));

                        Element partEvent = doc.createElement(isRest ? "rest" : "chord");
                        voice.appendChild(partEvent);
                        partEvent.setAttribute("event_ref", eventId);
                        Element partEventDuration = doc.createElement("duration");
                        partEvent.appendChild(partEventDuration);
                        partEventDuration.setAttribute("num", String.valueOf(eventDuration.getNum()));
                        partEventDuration.setAttribute("den", String.valueOf(eventDuration.getDen()));

                        if (!isRest) {
                            //TODO randomize note
                        }
                    }
                }
            }


        } catch (ParserConfigurationException e) {
            //TODO
        }
    }
}
