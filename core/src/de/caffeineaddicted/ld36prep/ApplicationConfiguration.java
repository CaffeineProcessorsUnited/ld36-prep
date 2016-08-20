package de.caffeineaddicted.ld36prep;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Malte Heinzelmann
 */
public class ApplicationConfiguration {
    public enum Attribute {
        WIDTH(Integer.class),
        HEIGHT(Integer.class),
        ;

        public final Class type;
        public final String name;

        Attribute(Class type) {
            this.type = type;
            this.name = name().toLowerCase();
        }

        Attribute(Class type, String name) {
            this.type = type;
            this.name = name;
        }
    }
    protected final Map<Attribute, Object> attributes = new HashMap<Attribute, Object>();

    public ApplicationConfiguration() {
    }

    public ApplicationConfiguration(ApplicationConfiguration configuration) {
        for (Attribute a: configuration.attributes.keySet()) {
            attributes.put(a, configuration.get(a));
        }
    }

    public <T> T get(Attribute attribute) {
        return (T) attributes.get(attribute);
    }

    public <T> T get(Attribute attribute, Class<T> c) {
        return (T) attributes.get(attribute);
    }

    public <T> ApplicationConfiguration set(Attribute attribute, T value) {
        attributes.put(attribute, value);
        return this;
    }
}
