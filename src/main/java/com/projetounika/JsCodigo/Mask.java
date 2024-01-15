package com.projetounika.JsCodigo;

import org.apache.wicket.Component;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.head.OnDomReadyHeaderItem;

public class Mask extends Behavior {

    private String js;

    public Mask(String js) {
        this.js = js;
    }

    @Override
    public void renderHead(Component component, IHeaderResponse response){
        super.renderHead (component,response);
        response.render(OnDomReadyHeaderItem.forScript("jQuery(document).ready(function(){" +
                "jQuery('#" + component.getMarkupId() + "').mask('" + js + "');" +
                "});"));

        response.render (JavaScriptHeaderItem.forUrl ("mask.js"));
    }
    @Override
    public void bind(Component component) {
        component.setOutputMarkupId(true);
    }

}
