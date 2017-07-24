/*
 * Copyright 2017 the original author or authors.
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

package org.gradle.ide.xcode.internal;

import org.gradle.ide.xcode.XcodeProject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DefaultXcodeProject implements XcodeProject {
    private final List<XcodeTarget> targets = new ArrayList<XcodeTarget>();
    private final List<XcodeScheme> schemes = new ArrayList<XcodeScheme>();
    private final Set<File> sources = new HashSet<File>();
    private File locationDir;

    public Set<File> getSources() {
        return sources;
    }

    public List<XcodeTarget> getTargets() {
        return targets;
    }

    public List<XcodeScheme> getSchemes() {
        return schemes;
    }

    public File getLocationDir() {
        return locationDir;
    }

    public void setLocationDir(File locationDir) {
        this.locationDir = locationDir;
    }
}
