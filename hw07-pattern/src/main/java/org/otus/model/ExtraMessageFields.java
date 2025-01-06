package org.otus.model;

public class ExtraMessageFields {
    private final String field11;
    private final String field12;
    private final ObjectForMessage field13;

    public String getField11() {
        return field11;
    }

    public String getField12() {
        return field12;
    }

    public ObjectForMessage getField13() {
        return field13;
    }

    public ExtraMessageFields(String field11, String field12, ObjectForMessage field13) {
        this.field11 = field11;
        this.field12 = field12;
        this.field13 = field13;
    }

    @Override
    public String toString() {
        return "ExtraMessageFields{" +
                "field11='" + field11 + '\'' +
                ", field12='" + field12 + '\'' +
                ", field13=" + field13 +
                '}';
    }

    public ExtraMessageFields process() {
        return new ExtraMessageFields(field12, field11, field13);
    }
}
