package com.projetounika;


import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.Model;


public class MenssagemFed extends FeedbackPanel {
    public MenssagemFed(String id, boolean sucesso, String mensagem) {
        super(id);
        setVisible(false);


        if (sucesso) {
            success(mensagem);
            add(new AttributeAppender ("class", "alert alert-success"));

        } else {
            error(mensagem);
            add(new AttributeAppender ("class", "alert alert-danger"));

        }

        add(new AttributeAppender("style", "position: absolute; top: 0; left: 0; width: 100%; height: 100%; display: flex; justify-content: center; align-items: center; z-index: 1000; opacity : 0.5"));

    }

}