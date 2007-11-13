package com.seventytwomiles.architecturerules.exceptions;


import java.util.Arrays;
import java.util.List;


/**
 * <p>Thrown to indicate that a cyclic redendency was found.</p>
 *
 * TODO: report which packages were cyclical
 *
 * @author mikenereson
 * @noinspection JavaDoc
 * @see RuntimeException
 */
public class CyclicRedundancyException extends RuntimeException {


    /**
     * @see RuntimeException#RuntimeException()
     */
    public CyclicRedundancyException() {
        super("cyclic redundancy");
    }


    /**
     * @see RuntimeException#RuntimeException(String)
     */
    public CyclicRedundancyException(String message) {
        super(message);
    }


    /**
     * @see RuntimeException#RuntimeException(Throwable)
     */
    public CyclicRedundancyException(Throwable cause) {
        super("cyclic redundancy", cause);
    }


    /**
     * @see RuntimeException#RuntimeException(String,Throwable)
     */
    public CyclicRedundancyException(String message, Throwable cause) {
        super(message, cause);
    }


    public CyclicRedundancyException(final String packageName, final String efferentPackage) {

        super("'{0}' is involved in an cyclically redundant dependency with '{1}'"
                .replace("{0}", packageName)
                .replace("{1}", efferentPackage));
    }


    public CyclicRedundancyException(final List cycles) {

        super("cyclic redundencies were found in each of the following packages: "
              + Arrays.deepToString(cycles.toArray()));
    }

}
