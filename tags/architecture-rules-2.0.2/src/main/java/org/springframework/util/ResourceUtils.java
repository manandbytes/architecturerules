package org.springframework.util;

/*
 * Copyright 2007 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * For more infomration visit
 * http://architecturerules.googlecode.com/svn/docs/index.html
 */


import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.net.URLDecoder;


/**
 * <p>Utility methods for resolving resource locations to files in the file
 * system. Mainly for internal use within the framework. <a
 * href="http://code.google.com/p/architecturerules/issues/detail?id=2&can=1">
 * issue 2 (remove unneccessary dependencies)</a></p>
 *
 * @author Juergen Hoeller
 */
public abstract class ResourceUtils {


    /**
     * <p>URL protocol for a file in the file system: "file"</p>
     *
     * @parameter
     */
    private static final String URL_PROTOCOL_FILE = "file";


    /**
     * <p>Resolve the given resource URL to a <code>java.io.File</code>, i.e. to
     * a file in the file system.</p>
     *
     * @param resourceUrl the resource URL to resolve
     * @param description a description of the original resource that the URL
     * was created for (for example, a class path location)
     * @return a corresponding File object
     * @throws FileNotFoundException if the URL cannot be resolved to a file in
     * the file system
     */
    public static File getFile(final URL resourceUrl, final String description) throws FileNotFoundException {

        if (null == resourceUrl || "".equals(resourceUrl))
            throw new IllegalArgumentException("resourceUrl must not be null");

        if (!URL_PROTOCOL_FILE.equals(resourceUrl.getProtocol()))
            throw new FileNotFoundException(description + " cannot be resolved to absolute file path  because it does not reside in the file system: " + resourceUrl);

        //noinspection deprecation
        return new File(URLDecoder.decode(resourceUrl.getFile()));
    }


}
