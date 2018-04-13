package com.ybyc.skids.common.helper;

import org.dozer.DozerBeanMapper;
import org.dozer.loader.api.BeanMappingBuilder;

import static org.dozer.loader.api.TypeMappingOptions.mapEmptyString;
import static org.dozer.loader.api.TypeMappingOptions.mapNull;

public class BeanHelper {

    public static void map(Object source,Object destination){
        DozerBeanMapper beanMapper = new DozerBeanMapper();
        beanMapper.addMapping(new BeanMappingBuilder() {
            @Override
            protected void configure() {
                mapping(source.getClass(),
                        destination.getClass(),
                        mapNull(false),
                        mapEmptyString(false));
            }
        });
        beanMapper.map(source,destination);
    }

    public static <T> T map(Object source,Class<T> destinationClzz){
        DozerBeanMapper beanMapper = new DozerBeanMapper();
        beanMapper.addMapping(new BeanMappingBuilder() {
            @Override
            protected void configure() {
                mapping(source.getClass(),
                        destinationClzz,
                        mapNull(false),
                        mapEmptyString(false));
            }
        });
        return beanMapper.map(source,destinationClzz);
    }

}
