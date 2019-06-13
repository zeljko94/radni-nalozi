package models;

import android.util.Log;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class UpdateProps {
    private String propName;
    private String value;

    public UpdateProps(String propName, String value){
        this.propName = propName;
        this.value = value;
    }

    public static ArrayList<UpdateProps> objectToProps(Object obj){
        ArrayList<UpdateProps> props = new ArrayList<>();
        for(Field f : obj.getClass().getFields()){
            String fieldName = f.getName();
            f.setAccessible(true);
            Object value = null;
            try {
                 value = f.get(obj);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            if(!fieldName.equals("uid"))
                props.add(new UpdateProps(fieldName, value.toString()));
        }
        return props;
    }

    public String getPropName() {
        return propName;
    }

    public void setPropName(String propName) {
        this.propName = propName;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
