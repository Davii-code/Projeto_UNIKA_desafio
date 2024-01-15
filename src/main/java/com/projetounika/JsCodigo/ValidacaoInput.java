package com.projetounika.JsCodigo;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.head.OnDomReadyHeaderItem;

public class ValidacaoInput extends Behavior {


    public ValidacaoInput() {
    }

    @Override
    public void renderHead(Component component, IHeaderResponse response) {
        super.renderHead(component, response);

        // Adicione o script JavaScript para validar a entrada no lado do cliente
        response.render(OnDomReadyHeaderItem.forScript(""
                + "$(document).ready(function() {"
                + "    $('#" + component.getMarkupId() + "').on('input', function() {"
                + "        var inputValue = $(this).val();"
                + "        var numericValue = inputValue.replace(/[^0-9]/g, '');"
                + "        $(this).val(numericValue);"
                + "    });"
                + "});"));
    }

    @Override
    public void bind(Component component) {
        component.setOutputMarkupId(true);
    }
}
