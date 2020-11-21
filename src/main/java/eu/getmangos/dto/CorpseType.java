package eu.getmangos.dto;

public enum CorpseType {
    BONES(0),
    RESURRECTABLE_PVE(1),
    RESURRECTABLE_PVP(2);

    public final byte code;

    private CorpseType(int code) {
        this.code = (byte) code;
    }

    public static CorpseType convert(byte code) {
        for(CorpseType e : values()) {
            if(e.code == code) {
                return e;
            }
        }
        return null;
    }
}