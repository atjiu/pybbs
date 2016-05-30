/**
 * Copyright (c) 2011-2013, kidzhou 周磊 (zhouleib1412@gmail.com)
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cn.tomoya.utils.ext.plugin.tablebind;

public class ParamNameStyles {

    public static INameStyle module(String moduleName) {
        return new ModuleNameStyle(moduleName);
    }

    public static INameStyle lowerModule(String moduleName) {
        return new LowerModuleNameStyle(moduleName);
    }

    public static INameStyle upModule(String moduleName) {
        return new UpModuleNameStyle(moduleName);
    }

    public static INameStyle upUnderlineModule(String moduleName) {
        return new UpUnderlineModuleNameStyle(moduleName);
    }

    public static INameStyle lowerUnderlineModule(String moduleName) {
        return new LowerUnderlineModuleNameStyle(moduleName);
    }
}

class ModuleNameStyle implements INameStyle {
    private String moduleName;

    public ModuleNameStyle(String moduleName) {
        this.moduleName = moduleName;
    }

    public ModuleNameStyle() {
    }

    @Override
    public String name(String className) {
        return moduleName + className;
    }
}

class LowerModuleNameStyle implements INameStyle {
    private String moduleName;

    public LowerModuleNameStyle(String moduleName) {
        this.moduleName = moduleName;
    }

    public LowerModuleNameStyle() {
    }

    @Override
    public String name(String className) {
        return moduleName + className.toLowerCase();
    }
}

class UpModuleNameStyle implements INameStyle {
    private String moduleName;

    public UpModuleNameStyle(String moduleName) {
        this.moduleName = moduleName;
    }

    public UpModuleNameStyle() {
    }

    @Override
    public String name(String className) {
        return moduleName + className.toUpperCase();
    }
}

class UpUnderlineModuleNameStyle implements INameStyle {
    private String moduleName;

    public UpUnderlineModuleNameStyle(String moduleName) {
        this.moduleName = moduleName;
    }

    @Override
    public String name(String className) {
        String tableName = "";
        for (int i = 0; i < className.length(); i++) {
            char ch = className.charAt(i);
            if (Character.isUpperCase(ch)) {
                tableName += "_" + ch;
            } else {
                tableName += Character.toUpperCase(ch);
            }
        }
        return moduleName + tableName;
    }

}

class LowerUnderlineModuleNameStyle implements INameStyle {
    private String moduleName;

    public LowerUnderlineModuleNameStyle(String moduleName) {
        this.moduleName = moduleName;
    }

    @Override
    public String name(String className) {
        String tableName = "";
        for (int i = 0; i < className.length(); i++) {
            char ch = className.charAt(i);
            if (Character.isUpperCase(ch)) {
                tableName += "_" + Character.toLowerCase(ch);
            } else {
                tableName += ch;
            }
        }
        return moduleName + tableName;
    }
}
