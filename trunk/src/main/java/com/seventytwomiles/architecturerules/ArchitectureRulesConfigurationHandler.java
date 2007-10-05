package com.seventytwomiles.architecturerules;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.HashSet;
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

    private Set rules = new HashSet();
    private List sources = new ArrayList();
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


    public ArchitectureRulesConfigurationHandler() {

        super();
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


    /**
     * Getter for property 'rules'.
     *
     * @return Value for property 'rules'.
     */
    public Set getRules() {
        return rules;
    }


    /**
     * Getter for property 'sources'.
     *
     * @return Value for property 'sources'.
     */
    public List getSources() {
        return sources;
    }


    /**
     * Getter for property 'throwExceptionWhenNoPackages'.
     *
     * @return Value for property 'throwExceptionWhenNoPackages'.
     */
    public boolean isThrowExceptionWhenNoPackages() {
        return throwExceptionWhenNoPackages;
    }


    /**
     * Getter for property 'doCyclicDependencyTest'.
     *
     * @return Value for property 'doCyclicDependencyTest'.
     */
    public boolean isDoCyclicDependencyTest() {
        return doCyclicDependencyTest;
    }
}
