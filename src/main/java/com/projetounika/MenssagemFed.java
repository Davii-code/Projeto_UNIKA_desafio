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
    }

}