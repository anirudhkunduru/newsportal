package com.karthik.newsportal.core.models;

import java.util.Date;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

@Model(
    adaptables = Resource.class,
    defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL
)
public class HeaderModel1 {

    @ValueMapValue
    private String textField2;

    @ValueMapValue
    private Date datePicker;

    public String getTextField2() {
        return textField2;
    }

    public Date getDatePicker() {
        return datePicker;
    }
}