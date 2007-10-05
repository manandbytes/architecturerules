package com.seventytwomiles.architecturerules;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * <p>Reads and processes arcitechture rules configuration file</p>
 *
 * @author mnereson
 * @see DefaultHandler
 */
class ConfigurationHandler extends DefaultHandler {


    /**
     * <p>Log to log with</p>
     *
     * @parameter
     */
    private static final Log log = LogFactory.getLog(ConfigurationHandler.class);

    /**
     * <p>Rules that are read from the configuration file</p>
     *
     * @parameter
     */
    private final Set rules = new HashSet();

    /**
     * <p>source directories that are read from the configuration file</p>
     *
     * @parameter
     */
    private final List sources = new ArrayList();

    /**
     * <p>sets to true when <samp>&lt;sources no-packages="exception"&gt;</samp>,
     * false when <samp>&lt;sources no-packages="ignore"&gt;</samp>
     *
     * </p>
     *
     * @parameter throwExceptionWhenNoPackages boolean
     */
    private boolean throwExceptionWhenNoPackages;

    /**
     * <p>sets to true when <samp>&lt;cyclicalDependency test="true"/> </samp>,
     * false when <samp>&lt;cyclicalDependency test="false"/> </samp>
     *
     * @parameter doCyclicDependencyTest boolean
     */
    private boolean doCyclicDependencyTest;

    /**
     * <p>Holds the <code>Rule</code> that is currently being processed as the
     * configuraiton file is being read</p>
     *
     * @parameter
     */
    private Rule rule;

    /**
     * <p>flag that indicates what the current element that is being processed
     * is</p>
     *
     * @parameter
     */
    boolean nextValueIsSources = false;

    /**
     * <p>flag that indicates what the current element that is being processed
     * is</p>
     *
     * @parameter
     */
    boolean nextValueIsSource = false;

    /**
     * <p>flag that indicates what the current element that is being processed
     * is</p>
     *
     * @parameter
     */
    boolean nextValueIsCyclicalDependencyTest = false;

    /**
     * <p>flag that indicates what the current element that is being processed
     * is</p>
     *
     * @parameter
     */
    boolean nextValueStartsRule = false;

    /**
     * <p>flag that indicates what the current element that is being processed
     * is</p>
     *
     * @parameter
     */
    boolean nextValueIsRuleId = false;

    /**
     * <p>flag that indicates what the current element that is being processed
     * is</p>
     *
     * @parameter
     */
    boolean nextValueIsRulePackage = false;

    /**
     * <p>flag that indicates what the current element that is being processed
     * is</p>
     *
     * @parameter
     */
    boolean nextValueIsRuleViolation = false;

    /**
     * <p>flag that indicates what the current element that is being processed
     * is</p>
     *
     * @parameter
     */
    boolean nextValueIsRuleComment = false;

    /**
     * <p>flag that indicates what the current element that is being processed
     * is</p>
     *
     * @parameter
     */
    boolean nextValueEndsRule = false;

    /**
     * <p>stores the value of the last <samp>&lt;sources not-found</samp>
     * property</p>
     *
     * @parameter
     */
    private String lastSourceNotFoundExceptionFlag;


    /**
     * <p>Instanciate a new ConfigurationHandler</p>
     */
    public ConfigurationHandler() {

        super();
    }


    /**
     * @see DefaultHandler#startElement(String,String,String,Attributes)
     */
    public void startElement(final String uri, final String localName, final String qName, final Attributes attributes) throws SAXException {

        super.startElement(uri, localName, qName, attributes);

        if (localName.equalsIgnoreCase("sources")) {

            nextValueIsSources = true;

            final String attributeValue = attributes.getValue(0).toLowerCase();

            throwExceptionWhenNoPackages = !attributeValue.equals("ignore");


        } else if (localName.equalsIgnoreCase("source")) {

            nextValueIsSource = true;
            lastSourceNotFoundExceptionFlag = attributes.getValue(0).toLowerCase();

        } else if (localName.equalsIgnoreCase("cyclicalDependency")) {

            final String attributeValue = attributes.getValue(0).toLowerCase();
            doCyclicDependencyTest = attributeValue.equals("true");

        } else if (localName.equalsIgnoreCase("rule")) {

            nextValueStartsRule = true;

        } else if (localName.equalsIgnoreCase("id")) {

            nextValueIsRuleId = true;

        } else if (localName.equalsIgnoreCase("package")) {

            nextValueIsRulePackage = true;

        } else if (localName.equalsIgnoreCase("violation")) {

            nextValueIsRuleViolation = true;

        } else if (localName.equalsIgnoreCase("comment")) {

            nextValueIsRuleComment = true;
        }
    }


    /**
     * @see DefaultHandler#endElement(String,String,String)
     */
    public void endElement(final String uri, final String localName, final String qName) throws SAXException {

        super.endElement(uri, localName, qName);

        if (localName.equalsIgnoreCase("rule"))
            this.nextValueEndsRule = true;
    }


    /**
     * @see DefaultHandler#characters(char[],int,int)
     */
    public void characters(final char ch[], final int start, final int length) throws SAXException {

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


    /**
     * <p>Clear all fields which indicate what propery is being read</p>
     */
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
     * Getter for property {@link #rules}.
     *
     * @return Value for property <tt>rules</tt>.
     */
    public Set getRules() {
        return rules;
    }


    /**
     * Getter for property {@link #sources}.
     *
     * @return Value for property <tt>sources</tt>.
     */
    public List getSources() {
        return sources;
    }


    /**
     * Getter for property {@link #throwExceptionWhenNoPackages}.
     *
     * @return Value for property <tt>throwExceptionWhenNoPackages</tt>.
     */
    public boolean isThrowExceptionWhenNoPackages() {
        return throwExceptionWhenNoPackages;
    }


    /**
     * Getter for property {@link #doCyclicDependencyTest}.
     *
     * @return Value for property <tt>doCyclicDependencyTest</tt>.
     */
    public boolean isDoCyclicDependencyTest() {
        return doCyclicDependencyTest;
    }
}
