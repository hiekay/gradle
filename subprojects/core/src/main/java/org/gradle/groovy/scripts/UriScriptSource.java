/*
 * Copyright 2010 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.gradle.groovy.scripts;

import org.gradle.internal.hash.HashUtil;
import org.gradle.internal.resource.ResourceLocation;
import org.gradle.internal.resource.TextResource;
import org.gradle.internal.resource.TextResourceLoader;

import java.io.File;
import java.net.URI;

import static java.lang.Character.isJavaIdentifierPart;
import static java.lang.Character.isJavaIdentifierStart;
import static org.apache.commons.lang.StringUtils.substringAfterLast;
import static org.apache.commons.lang.StringUtils.substringBeforeLast;

/**
 * A {@link ScriptSource} which loads the script from a URI.
 */
public class UriScriptSource implements ScriptSource {
    private final TextResource resource;
    private String className;

    public static ScriptSource file(String description, File sourceFile) {
        return new UriScriptSource(TextResourceLoader.forFile(description, sourceFile));
    }

    public static ScriptSource uri(String description, URI source) {
        return new UriScriptSource(TextResourceLoader.forUri(description, source));
    }

    private UriScriptSource(TextResource resource) {
        this.resource = resource;
    }

    public TextResource getResource() {
        return resource;
    }

    public String getFileName() {
        ResourceLocation location = resource.getLocation();
        File sourceFile = location.getFile();
        URI sourceUri = location.getURI();
        return sourceFile != null ? sourceFile.getPath() : sourceUri.toString();
    }

    public String getDisplayName() {
        return resource.getDisplayName();
    }

    /**
     * Returns the class name for use for this script source.  The name is intended to be unique to support mapping
     * class names to source files even if many sources have the same file name (e.g. build.gradle).
     */
    public String getClassName() {
        if (className == null) {
            URI sourceUri = getResource().getLocation().getURI();
            String path = sourceUri.toString();
            this.className = classNameFromPath(path);
        }
        return className;
    }

    private String classNameFromPath(String path) {
        String name = substringBeforeLast(substringAfterLast(path, "/"), ".");

        StringBuilder className = new StringBuilder(name.length());
        for (int i = 0; i < name.length(); i++) {
            char ch = name.charAt(i);
            className.append(
                isJavaIdentifierPart(ch) ? ch : '_');
        }
        if (!isJavaIdentifierStart(className.charAt(0))) {
            className.insert(0, '_');
        }
        className.setLength(Math.min(className.length(), 30));
        className.append('_');
        className.append(HashUtil.createCompactMD5(path));

        return className.toString();
    }

}
