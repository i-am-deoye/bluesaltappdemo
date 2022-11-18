package com.bluesaltapp.common;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

public class ReflectionUtils {

    static public Optional<Object> getPropertyValue(String name, Class object ) {
        Optional<Field> fieldOptional = getField( name, object );
        try {
            return fieldOptional.isPresent() ? Optional.of( fieldOptional.get().get( object) ) : Optional.empty();
        } catch (IllegalAccessException e) {
            return Optional.empty();
        }
    }

    static public Set<Field> getFields(Set<String> properties, Class object) {
        return Arrays.stream( object.getFields() )
                .filter( Objects::nonNull )
                .filter( properties::contains )
                .collect( Collectors.toSet());
    }

    static public Optional<Field> getField(String name, Class object) {
        Set<Field> fields = getFields( Collections.singleton( name ), object );
        Iterator<Field> iterator =  fields.iterator();
        Optional<Field> fieldOptional = iterator.hasNext() ? Optional.of( iterator.next() ) : Optional.empty();
        return  fieldOptional;
    }
}