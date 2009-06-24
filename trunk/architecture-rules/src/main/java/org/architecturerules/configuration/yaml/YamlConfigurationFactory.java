/**
 * Copyright 2007 - 2009 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * For more information visit
 *         http://wiki.architecturerules.org/ and
 *         http://blog.architecturerules.org/
 */
package org.architecturerules.configuration.yaml;


import java.util.Properties;
import java.util.Set;
import java.util.TreeSet;

import org.architecturerules.configuration.AbstractConfigurationFactory;
import org.architecturerules.domain.Rule;
import org.architecturerules.domain.SourceDirectory;

import org.yaml.snakeyaml.Loader;
import org.yaml.snakeyaml.TypeDescription;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;


public class YamlConfigurationFactory extends AbstractConfigurationFactory {

    public YamlConfigurationFactory(String fileName) {
        super(fileName);
    }


    public YamlConfigurationFactory() {
        super();
    }

    @Override
    protected void validateConfiguration(String configuration) {

        // TODO Auto-generated method stub
    }


    @Override
    protected void processConfiguration(final String configuration) {

        final Constructor rootConstructor = new Constructor(YamlConfiguration.class);
        TypeDescription typeDescription = new TypeDescription(YamlConfiguration.class);
        typeDescription.putListPropertyType("rules", YamlRule.class);
        typeDescription.putMapPropertyType("properties", String.class, String.class);
        typeDescription.putListPropertyType("sources", SourceDirectory.class);
        typeDescription.putListPropertyType("listeners", String.class);
        rootConstructor.addTypeDescription(typeDescription);

        YamlConfiguration loadedConfguration = (YamlConfiguration) new Yaml(new Loader(rootConstructor)).load(configuration);

        setDoCyclicDependencyTest(loadedConfguration.isDoCyclicDependencyTest());
        setThrowExceptionWhenNoPackages(loadedConfguration.isThrowExceptionWhenNoPackages());

        for (YamlRule rule : loadedConfguration.getRules()) {

            final Rule ruleToAdd = new Rule(rule.getId());

            for (String packageToAdd : rule.getPackages()) {

                ruleToAdd.addPackage(packageToAdd);
            }

            for (String violation : rule.getViolations()) {

                ruleToAdd.addViolation(violation);
            }

            getRules().add(ruleToAdd);
        }

        final Properties loadedProperties = new Properties();
        loadedProperties.putAll(loadedConfguration.getProperties());
        addProperties(loadedProperties);

        getSources().addAll(loadedConfguration.getSources());

        getIncludedListeners().addAll(loadedConfguration.getListeners());

        // TODO should we support exclusion of listeners?!  
    }

    private static final Set<String> SUPPORTED_FILE_TYPES = new TreeSet<String>(String.CASE_INSENSITIVE_ORDER) {

        {

            add("yml");
            add("yaml");
        }
    };

    @Override
    protected Set<String> getSupportedFileTypes() {

        return SUPPORTED_FILE_TYPES;
    }
}
