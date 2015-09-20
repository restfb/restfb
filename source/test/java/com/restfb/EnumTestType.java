package com.restfb;

public class EnumTestType {

    @Facebook
    public String id;
    
    @Facebook("test_enum")
    public EnumTestEnum testEnum;
    
    @Facebook("test_enum")
    public String testEnumString;
    
}
