package dev.sinxkyuna.valium.module.setting.impl;

import dev.sinxkyuna.valium.module.setting.Setting;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StringSetting extends Setting {
    private String value;

    public StringSetting(String name, String value) {
        super(name);
        this.value = value;
    }
}
