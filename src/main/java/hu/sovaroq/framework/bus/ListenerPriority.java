package hu.sovaroq.framework.bus;

/**
 * Created by Oryk on 2017. 01. 27..
 */
public enum ListenerPriority {
    HIGH((short) 0),
    MEDIUM((short) 1),
    LOW((short) 2);

    private final short priority;
    ListenerPriority(short i) {
        priority = i;
    }

    public short getValue(){
        return priority;
    }
}
