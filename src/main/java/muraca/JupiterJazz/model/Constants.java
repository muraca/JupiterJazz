package muraca.JupiterJazz.model;

import java.util.List;

public class Constants {
    public static final List<String> TIME_SIGNATURES_FRAC = List.of("2/4", "3/4", "4/4", "5/4", "6/4", "7/4", "3/8", "4/8", "5/8", "6/8", "7/8");
    public static final List<Integer> TIME_SIGNATURES_VTU =  List.of(64,    96,    128,   160,   192,   224,   48,    64,    80,    96,    112);
    public static final int TIME_SIGNATURES_SIZE = TIME_SIGNATURES_FRAC.size();

    public static final List<String> EVENT_DURATIONS_FRAC = List.of("1/128", "1/64", "1/32", "1/16", "1/8", "3/16", "1/4", "5/16", "3/8", "7/16", "1/2", "9/16", "5/8", "11/16", "3/4", "13/16", "7/8", "15/16", "1", "3/2", "2", "5/2", "3", "7/2", "4", "5", "6", "7", "8");
    public static final List<Integer> EVENT_DURATIONS_VTU =  List.of(1,       2,      4,      8,      16,    24,     32,    40,     48,    56,     64,    72,     80,    88,      96,    104,     112,   120,     128, 192,   256, 320,   384, 448,   512, 640, 768, 896, 1024);
    public static final int EVENT_DURATIONS_SIZE = EVENT_DURATIONS_FRAC.size();
}
