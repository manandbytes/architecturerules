package com.nereson.architecturerules;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.List;
import java.util.Set;


/**
 * <p>todo: javadocs</p>
 *
 * @author mnereson
 * @see ContentHandler
 */
class ArchitectureRulesConfigurationHandler extends DefaultHandler {


    private static final Log log = LogFactory.getLog(ArchitectureRulesConfigurationHandler.class);

    private Set rules;
    private List sources;
    private boolean throwExceptionWhenNoPackages;
    private boolean doCyclicDependencyTest;

    private Rule rule;

    boolean nextValueIsSources = false;
    boolean nextValueIsSource = false;
    boolean nextValueIsCyclicalDependencyTest = false;
    boolean nextValueStartsRule = false;
    boolean nextValueIsRuleId = false;
    boolean nextValueIsRulePackage = false;
    boolean nextValueIsRuleViolation = false;
    boolean nextValueIsRuleComment = false;
    boolean nextValueEndsRule = false;
    private String lastSourceNotFoundExceptionFlag;


    public ArchitectureRulesConfigurationHandler(Set rules, List packages, boolean throwExceptionWhenNoPackages, boolean doCyclicDependencyTest) {

        super();

        this.rules = rules;
        this.sources = packages;
        this.throwExceptionWhenNoPackages = throwExceptionWhenNoPackages;
        this.doCyclicDependencyTest = doCyclicDependencyTest;
    }


    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        super.startElement(uri, localName, qName, attributes);

        if (localName.equalsIgnoreCase("sources")) {

            this.nextValueIsSources = true;
            String attributeValue = attributes.getValue(0).toLowerCase();
            this.throwExceptionWhenNoPackages = !attributeValue.equals("ignore");


        } else if (localName.equalsIgnoreCase("source")) {

            this.nextValueIsSource = true;
            this.lastSourceNotFoundExceptionFlag = attributes.getValue(0).toLowerCase();


        } else if (localName.equalsIgnoreCase("cyclicalDependency")) {

            String attributeValue = attributes.getValue(0).toLowerCase();
            this.doCyclicDependencyTest = attributeValue.equals("true");


        } else if (localName.equalsIgnoreCase("rule")) {

            this.nextValueStartsRule = true;


        } else if (localName.equalsIgnoreCase("id")) {

            this.nextValueIsRuleId = true;


        } else if (localName.equalsIgnoreCase("package")) {

            this.nextValueIsRulePackage = true;


        } else if (localName.equalsIgnoreCase("violation")) {

            this.nextValueIsRuleViolation = true;


        } else if (localName.equalsIgnoreCase("comment")) {

            this.nextValueIsRuleComment = true;
        }

    }


    public void endElement(String uri, String localName, String qName) throws SAXException {
        super.endElement(uri, localName, qName);

        //log.trace("end:" + localName);

        if (localName.equalsIgnoreCase("rule"))
            this.nextValueEndsRule = true;
    }


    public void characters(char ch[], int start, int length) throws SAXException {
        super.characters(ch, start, length);

        final String value = new String(ch).substring(start, start + length).trim();

        if (nextValueIsSources) {


        } else if (nextValueIsSource) {

            if (lastSourceNotFoundExceptionFlag.equals("ignore")) {

                this.sources.add(new String[]{value, "false"});

            } else {

                this.sources.add(new String[]{value, "true"});
            }

        } else if (nextValueStartsRule) {

            rule = new Rule();


        } else if (nextValueIsRuleId) {

            rule.setId(value);


        } else if (nextValueIsRulePackage) {

            rule.setPackageName(value);


        } else if (nextValueIsRuleViolation) {

            rule.addViolation(value);


        } else if (nextValueIsRuleComment) {

            rule.setComment(value);


        } else if (nextValueEndsRule) {

            this.rules.add(rule);
        }

        resetFlags();
    }


    private void resetFlags() {

        this.nextValueIsSources = false;
        this.nextValueIsSource = false;
        this.nextValueIsCyclicalDependencyTest = false;
        this.nextValueStartsRule = false;
        this.nextValueIsRuleId = false;
        this.nextValueIsRulePackage = false;
        this.nextValueIsRuleViolation = false;
        this.nextValueIsRuleComment = false;
        this.nextValueEndsRule = false;
    }
}
