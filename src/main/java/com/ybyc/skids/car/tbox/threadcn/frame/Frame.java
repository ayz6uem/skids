package com.ybyc.skids.car.tbox.threadcn.frame;

import lombok.Data;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Data
public class Frame {

    public static DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private String head = "$E6";
    private String id;
    private String directive;
    private int length;
    private String content;

    public Frame(String id, Directive payload) {
        this.id = id;
        this.content = join(payload);
        this.directive = payload.getDirective();
        if(Objects.nonNull(content)){
            length = content.length();
        }
    }

    @Override
    public String toString() {
        return join(this)+"\r\n";
    }

    public static String join(Object obj){
        if(Objects.isNull(obj)){
            return null;
        }
        Field[] fields = obj.getClass().getDeclaredFields();
        Collection<Object> collection = new ArrayList<>();
        for(Field field: fields){
            if(isDataField(field,obj.getClass())){
                field.setAccessible(true);
                try {
                    collection.add(field.get(obj));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        return collection.stream().filter(Objects::nonNull).map(Object::toString).collect(Collectors.joining(","));
    }

    public static boolean isDataField(Field field,Class<?> clzz){
        if(field.isSynthetic()){
            return false;
        }
        if(Modifier.isStatic(field.getModifiers())){
            return false;
        }
        try {
            PropertyDescriptor descriptor = new PropertyDescriptor(field.getName(),clzz);
            if(descriptor.getReadMethod()!=null&&descriptor.getWriteMethod()!=null){
                return true;
            }
        } catch (IntrospectionException e) {
            e.printStackTrace();
        }
        return false;
    }

}
