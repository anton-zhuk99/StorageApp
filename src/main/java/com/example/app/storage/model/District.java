package com.example.app.storage.model;

public enum District {

    AL("Alabama"),
    AK("Alaska"),
    AZ("Arizona"),
    AR("Arkansas"),
    CA("California"),
    CO("Colorado"),
    CT("Connecticut"),
    DE("Delaware"),
    FL("Florida"),
    GA("Georgia"),
    HI("Hawaii"),
    ID("Idaho"),
    IL("Illinois"),
    IN("Indiana"),
    IA("Iowa"),
    KS("Kansas"),
    KY("Kentucky"),
    LA("Louisiana"),
    ME("Maine"),
    MD("Maryland"),
    MA("Massachusetts"),
    MI("Michigan"),
    MN("Minnesota"),
    MS("Mississippi"),
    MO("Missouri"),
    MT("Montana"),
    NE("Nebraska"),
    NV("Nevada"),
    NH("New Hampshire"),
    NJ("New Jersey"),
    NM("New Mexico"),
    NY("New York"),
    NC("North Carolina"),
    ND("North Dakota"),
    OH("Ohio"),
    OK("Oklahoma"),
    OR("Oregon"),
    PA("Pennsylvania"),
    RI("Rhode Island"),
    SC("South Carolina"),
    SD("South Dakota"),
    TN("Tennessee"),
    TX("Texas"),
    UT("Utah"),
    VT("Vermont"),
    VA("Virginia"),
    WA("Washington"),
    WV("West Virginia"),
    WI("Wisconsin"),
    WY("Wyoming");

    private final String fullName;

    District(String fullName) {
        this.fullName = fullName;
    }

    public static District getByFullName(String fullName) {
        for (District d: values()) {
            if (d.fullName.equals(fullName)) {
                return d;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return fullName;
    }
}
